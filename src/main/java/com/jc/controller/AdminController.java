package com.jc.controller;

import com.github.pagehelper.PageInfo;
import com.jc.constant.ResultModel;
import com.jc.model.Activity;
import com.jc.service.ActivityService;
import com.jc.service.impl.ActivityServiceImpl;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by jasonzhu on 2017/7/14.
 */
@Api(description = "管理员使用接口")
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {
    @Autowired
    private ActivityService activityService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("currentTime", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return "admin";
    }

    @RequestMapping(value = "/addActivity", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel addActivity(@RequestParam String activityName, @RequestParam Date startTime, @RequestParam Date applyBeginTime, @RequestParam Date applyEndTime, String remark) {
        Activity record = new Activity();
        record.setActivityName(activityName);
        record.setStartTime(startTime);
        record.setApplyBeginTime(applyBeginTime);
        record.setApplyEndTime(applyEndTime);
        record.setRemark(remark);
        record = activityService.addActivity(record);
        return buildSuccessResponse(record);
    }

    @RequestMapping(value = "/addDefaultOvertimeMeals", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel addDefaultOvertimeMeals() {
        return buildSuccessResponse(activityService.addDefaultOvertimeMeals());
    }

    @RequestMapping(value = "/allActivity", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel allActivity(Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageInfo<Activity> pageInfo = activityService.getActivity(pageNum, pageSize);
        pageInfo.getList().forEach(ActivityServiceImpl::setStatus);
        return buildSuccessResponse(pageInfo);
    }

}
