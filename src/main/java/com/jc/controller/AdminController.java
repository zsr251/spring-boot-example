package com.jc.controller;

import com.jc.constant.ResultModel;
import com.jc.model.Activity;
import com.jc.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by jasonzhu on 2017/7/14.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {
    @Autowired
    private ActivityService activityService;

    @RequestMapping(value = {"/", "/index"})
    @ResponseBody
    public String index() {
        return "欢迎使用管理页面";
    }

    @RequestMapping(value = "/addActivity")
    @ResponseBody
//    public ResultModel addActivity(@RequestParam String activityName, @RequestParam Date startTime, @RequestParam Date applyBeginTime, @RequestParam Date applyEndTime, String remark) {
    public ResultModel addActivity(Activity record) {
//        Activity record = new Activity();
//        record.setActivityName(activityName);
//        record.setStartTime(startTime);
//        record.setApplyBeginTime(applyBeginTime);
//        record.setApplyEndTime(applyEndTime);
//        record.setRemark(remark);
        record = activityService.addActivity(record);
        return buildSuccessResponse(record);
    }

    @RequestMapping(value = "/addDefaultOvertimeMeals")
    @ResponseBody
    public ResultModel addDefaultOvertimeMeals() {
        return buildSuccessResponse(activityService.addDefaultOvertimeMeals());
    }

    @RequestMapping(value = "/allActivity")
    @ResponseBody
    public ResultModel allActivity(Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        return buildSuccessResponse(activityService.getActivity(pageSize,pageNum));
    }

}
