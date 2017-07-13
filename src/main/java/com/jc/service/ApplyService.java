package com.jc.service;

import com.github.pagehelper.PageInfo;
import com.jc.exception.ApplyException;
import com.jc.model.ActivityApply;

import java.util.List;
import java.util.Map;

/**
 * Created by jasonzhu on 2017/7/13.
 */
public interface ApplyService {
    /**
     * 新增申请
     * @param activityId
     * @param employeeId
     * @return
     */
    ActivityApply addApply(Integer activityId,Integer employeeId,String remark) throws ApplyException;

    /**
     * 删除预约
     * @param id
     * @return
     */
    boolean delteApply(Integer id);

    /**
     * 获得申请
     * @param record
     * @return
     */
    List<ActivityApply> getApply(ActivityApply record);
    /**
     * 分页获得申请
     * @param record
     * @return
     */
    PageInfo<ActivityApply> selectApply(ActivityApply record,int pageNum,int pageSize);

    /**
     * 获得申请所有活动申请人数
     * @return
     */
    List<Map<String,Object>> selectApplyCount();

    /**
     * 获得活动申请人数
     * @param activityId
     * @return
     */
    Map<String,Object> selectApplyCount(Integer activityId);

}
