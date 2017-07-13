package com.jc.service.impl;

import com.github.pagehelper.PageInfo;
import com.jc.exception.ApplyException;
import com.jc.model.ActivityApply;
import com.jc.service.ApplyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by jasonzhu on 2017/7/13.
 */
@Service
public class ApplyServiceImpl implements ApplyService {
    @Override
    public ActivityApply addApply(Integer activityId, Integer employeeId, String remark) throws ApplyException {
        return null;
    }

    @Override
    public boolean delteApply(Integer id) {
        return false;
    }

    @Override
    public List<ActivityApply> getApply(ActivityApply record) {
        return null;
    }

    @Override
    public PageInfo<ActivityApply> selectApply(ActivityApply record, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectApplyCount() {
        return null;
    }

    @Override
    public Map<String, Object> selectApplyCount(Integer activityId) {
        return null;
    }
}
