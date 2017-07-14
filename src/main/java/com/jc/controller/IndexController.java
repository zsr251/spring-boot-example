package com.jc.controller;

import com.jc.constant.DepartmentEnum;
import com.jc.constant.ResultModel;
import com.jc.model.ActivityApply;
import com.jc.model.Employee;
import com.jc.service.ActivityService;
import com.jc.service.ApplyService;
import com.jc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Created by jasonzhu on 2017/7/14.
 */
@Controller
public class IndexController extends BaseController {
    @Autowired
    private ApplyService applyService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = {"/", "/index"})
    @ResponseBody
    public String index() {
        return "欢迎使用";
    }

    /**
     * 预约
     * @param englishName 英文名
     * @param activityId 活动ID
     * @return
     */
    @RequestMapping(value = "/apply")
    @ResponseBody
    public ResultModel apply(@RequestParam String englishName, @RequestParam Integer activityId,String remark) {
        Employee employee = employeeService.getEmployee(englishName);
        if (employee ==null) return buildErrorResponse("请先注册");
        ActivityApply apply = applyService.addApply(activityId,employee.getId(),remark);
        return buildSuccessResponse(apply);
    }

    /**
     * 取消预约
     * @param englishName 英文名
     * @param activityId 活动ID
     * @return
     */
    @RequestMapping(value = "/cancelApply")
    @ResponseBody
    public ResultModel cancelApply(@RequestParam String englishName, @RequestParam Integer activityId) {
        Employee employee = employeeService.getEmployee(englishName);
        if (employee ==null) return buildErrorResponse("请先注册");
        if(applyService.cancelApply(activityId,employee.getId())){
            return buildSuccessResponse("取消成功");
        }
        return buildErrorResponse("取消失败");
    }

    /**
     * 增加员工
     * @param department 部门
     * @param realName 姓名
     * @param englishName 英文名
     * @return
     */
    @RequestMapping(value = "/addEmployee")
    @ResponseBody
    public ResultModel addEmployee(@RequestParam String department,@RequestParam String realName,@RequestParam String englishName){
        return buildSuccessResponse(employeeService.addEmployee(DepartmentEnum.match(department),realName,englishName));
    }

    /**
     * 获得可预约活动
     * @return
     */
    @RequestMapping(value = "/getCanApply")
    @ResponseBody
    public ResultModel getCanApply(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",activityService.getCanApplyActivity());
        return buildSuccessResponse(result);
    }

}
