package com.jc.mapper;

import com.jc.model.ActivityApply;
import com.jc.util.mybatis.MyMapper;

import java.util.List;

public interface ActivityApplyMapper extends MyMapper<ActivityApply> {
    List<ActivityApply> getApplyList(Integer activityId);
}