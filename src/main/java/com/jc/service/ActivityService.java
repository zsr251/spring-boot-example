package com.jc.service;

import com.github.pagehelper.PageInfo;
import com.jc.model.Activity;

import java.util.List;

/**
 * Created by jasonzhu on 2017/7/13.
 */
public interface ActivityService {
    /**
     * 增加活动
      * @param record
     * @return
     */
    Activity addActivity(Activity record);

    /**
     * 增加默认加班晚餐
     * @return
     */
    Activity addDefaultOvertimeMeals();

    /**
     * 修改活动
     * @param record
     * @return
     */
    boolean updateActivity(Activity record);

    /**
     * 获得可预约活动
     * @return
     */
    List<Activity> getCanApplyActivity();

    /**
     * 刷新可预约活动缓存
     * @return
     */
    boolean updateCanApplyActivityCache();
    /**
     * 获得活动
     * @param record
     * @return
     */
    List<Activity> getActivity(Activity record);

    /**
     * 获得活动
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Activity> getActivity(int pageNum,int pageSize);
}
