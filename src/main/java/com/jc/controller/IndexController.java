package com.jc.controller;

import com.jc.constant.DepartmentEnum;
import com.jc.constant.ResultModel;
import com.jc.hystrix.GetEmployeeCommand;
import com.jc.model.Activity;
import com.jc.model.ActivityApply;
import com.jc.model.Employee;
import com.jc.service.ActivityService;
import com.jc.service.ApplyService;
import com.jc.service.EmployeeService;
import com.jc.service.impl.ActivityServiceImpl;
import com.jc.util.RateLimitUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/*
     * @Api：用在类上，说明该类的作用
	 *
	 * @ApiOperation：用在方法上，说明方法的作用
	 *
	 * @ApiImplicitParams：用在方法上包含一组参数说明
	 *
	 * @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
	 *      paramType：参数放在哪个地方 header-->请求参数的获取：@RequestHeader
	 *      query-->请求参数的获取：@RequestParam path（用于restful接口）-->请求参数的获取：@PathVariable
	 *      body（不常用） form（不常用） name：参数名 dataType：参数类型 required：参数是否必须传 value：参数的意思
	 *      defaultValue：参数的默认值
	 *
	 * @ApiResponses：用于表示一组响应
	 *
	 * @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息 code：数字，例如400
	 *      message：信息，例如"请求参数没填好" response：抛出异常的类
	 *
	 * @ApiModel：描述一个Model的信息（这种一般用在post创建的时候，使用@RequestBody这样的场景，
	 *      请求参数无法使用@ApiImplicitParam注解进行描述的时候）
	 *
	 * @ApiModelProperty：描述一个model的属性
	 */

@Api(description = "员工使用接口")
@Controller
public class IndexController extends BaseController {
    @Autowired
    private ApplyService applyService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private EmployeeService employeeService;
    private Logger logger = LoggerFactory.getLogger(IndexController.class);
    //每秒五条请求 等待1秒
//    private RateLimitUtil rateLimitUtil = new RateLimitUtil(5, 1);
    @Resource(name = "applyRateLimitUtil")
    private RateLimitUtil rateLimitUtil;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model, Integer id) {
        List<Activity> activityList = null;
        if (id != null) {
            Activity record = new Activity();
            record.setId(id);
            activityList = activityService.getActivity(record);
        }
        if (activityList == null || activityList.size() < 1) {
            activityList = activityService.getCanApplyActivity();
        }
        //设置状态
        activityList.forEach(ActivityServiceImpl::setStatus);
        model.addAttribute("activityList", activityList);
        model.addAttribute("currentTime", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return "index";
    }

    /**
     * 预约
     *
     * @param englishName 英文名
     * @param activityId  活动ID
     * @return
     */
    @ApiOperation(value = "申请预约", notes = "备注非必填")
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel apply(HttpServletRequest request, @ApiParam(value = "英文名", defaultValue = "jasonzhu") @RequestParam String englishName, @RequestParam Integer activityId, String remark) {
        Future<Boolean> future = CompletableFuture.supplyAsync(rateLimitUtil::tryAcquire);
        logger.debug("用户【{}】申请预约【{}】备注【{}】ip【{}】", englishName, activityId, remark, getRemoteIp(request));
        try {
            if (!future.get(3, TimeUnit.SECONDS)) {
                logger.warn("访问频次限制触发，用户【{}】申请预约【{}】", englishName, activityId);
                return buildLimitResponse();
            }
        } catch (Exception e) {
            return buildLimitResponse();
        }
        GetEmployeeCommand getEmployeeCommand = new GetEmployeeCommand(employeeService::getEmployee, englishName);
        Future<Employee> getEmployeeFuture = getEmployeeCommand.queue();
        logger.debug("查询用户【{}】是否存在", englishName);
        Employee employee;
        try {
            employee = getEmployeeFuture.get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.warn("查询用户【{}】失败 原因:{}", englishName, e.getMessage());
            return buildErrorResponse("查询用户失败");
        }
        if (employee == null) return buildErrorResponse("请先注册");
        ActivityApply apply = applyService.addApply(activityId, employee.getId(), remark);
        return buildSuccessResponse(apply);
    }

    /**
     * 取消预约
     *
     * @param englishName 英文名
     * @param activityId  活动ID
     * @return
     */
    @ApiOperation(value = "取消预约")
    @RequestMapping(value = "/cancelApply", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel cancelApply(@RequestParam String englishName, @RequestParam Integer activityId) {
        Employee employee = employeeService.getEmployee(englishName);
        if (employee == null) return buildErrorResponse("请先注册");
        if (applyService.cancelApply(activityId, employee.getId())) {
            return buildSuccessResponse("取消成功");
        }
        return buildErrorResponse("取消失败");
    }

    /**
     * 增加员工
     *
     * @param department  部门
     * @param realName    姓名
     * @param englishName 英文名
     * @return
     */
    @ApiOperation(value = "增加员工")
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel addEmployee(@RequestParam String department, @RequestParam String realName, @RequestParam String englishName) {
        return buildSuccessResponse(employeeService.addEmployee(DepartmentEnum.match(department), realName, englishName));
    }

    /**
     * 获得可预约活动
     *
     * @return
     */
    @ApiOperation(value = "获得可预约活动")
    @RequestMapping(value = "/getCanApply", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel getCanApply() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("list", activityService.getCanApplyActivity());
        return buildSuccessResponse(result);
    }

}
