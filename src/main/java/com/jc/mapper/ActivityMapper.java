package com.jc.mapper;

import com.jc.model.Activity;
import com.jc.util.mybatis.MyMapper;

public interface ActivityMapper extends MyMapper<Activity> {
    /**
     * 增加报名人数
     *  id 活动id
     *  applyNum 正数增加 负数减少
     * @return
     */
    int addApplyNum(Activity record);
}