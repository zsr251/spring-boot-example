package com.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jc.exception.ApplyException;
import com.jc.mapper.ActivityMapper;
import com.jc.model.Activity;
import com.jc.service.ActivityService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jasonzhu on 2017/7/13.
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public Activity addActivity(Activity record) {
        Preconditions.checkNotNull(record, "参数不能为空");
        Preconditions.checkNotNull(record.getActivityName(), "活动名不能为空");
        Preconditions.checkNotNull(record.getStartTime(), "活动开始时间不呢为空");
        Preconditions.checkNotNull(record.getApplyBeginTime(), "报名开始时间不呢为空");
        Preconditions.checkNotNull(record.getApplyEndTime(), "报名结束时间不呢为空");
        record.setStatus("0");
        record.setApplyNum(0);
        record.setCreateTime(new Date());

        Activity re = new Activity();
        re.setActivityName(record.getActivityName());
        re.setStartTime(record.getStartTime());
        re.setStatus("0");
        List<Activity> list = getActivity(re);
        if (list != null && list.size() > 0)
            throw new ApplyException("活动已存在");

        activityMapper.insertUseGeneratedKeys(record);
        return record;
    }

    @Override
    public Activity addDefaultOvertimeMeals() {
        Activity record = new Activity();
        record.setActivityName("加班餐（晚餐）");
        Date d = DateUtils.setMilliseconds(DateUtils.setMinutes(DateUtils.setSeconds(new Date(), 0), 0),0);
        record.setStartTime(DateUtils.setHours(d, 18));
        record.setApplyBeginTime(DateUtils.setHours(d, 9));
        record.setApplyEndTime(DateUtils.setHours(d, 16));
        record.setRemark("加班餐，未报名但加班用餐者扣十元！");
        return addActivity(record);
    }

    @Override
    @CacheEvict(value = "demoCache",key = "#record.id",condition = "#record.id != null")
    public boolean updateActivity(Activity record) {
        Preconditions.checkNotNull(record, "参数不能为空");
        Preconditions.checkNotNull(record.getId(), "活动ID不能为空");
        return activityMapper.updateByPrimaryKeySelective(record) > 0;
    }

    @Override
    @Cacheable(value = "demoCache")
    public List<Activity> getCanApplyActivity() {
        Date now = new Date();
        Example example = new Example(Activity.class);
        example.createCriteria().andEqualTo("status", "0").andLessThan("applyBeginTime", now).andGreaterThan("applyEndTime", now);
        return activityMapper.selectByExample(example);
    }

    @Override
    @CacheEvict(value = "demoCache")
    public boolean updateCanApplyActivityCache() {
        return true;
    }

    @Override
    @Cacheable(value = "demoCache",key = "#record.id",condition = "#record.id != null")
    public List<Activity> getActivity(Activity record) {
        Preconditions.checkNotNull(record, "参数不能为空");
        List<Activity> activities = Lists.newArrayList();
        if (record.getId() != null) {
            Activity activity = activityMapper.selectByPrimaryKey(record.getId());
            if (activity != null) activities.add(activity);
            return activities;
        }
        return activityMapper.select(record);
    }

    @Override
    public PageInfo<Activity> getActivity(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(Activity.class);
        example.orderBy("id").desc();
        List<Activity> list = activityMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    /**
     * 根据数据修改状态
     * @param activity
     */
    public static void setStatus(Activity activity){
        if (activity==null) return;
        if ("1".equals(activity.getStatus())) {
            activity.setStatus("已取消");
            return;
        }
        Date now = new Date();
        if ("0".equals(activity.getStatus())){
            if (activity.getApplyBeginTime().after(now)) {
                activity.setStatus("报名未开始");
                return;
            }
            if (activity.getApplyEndTime().after(now)){
                activity.setStatus("报名中");
                return;
            }
            if (activity.getApplyEndTime().before(now)){
                activity.setStatus("报名已结束");
                return;
            }
        }
    }
}
