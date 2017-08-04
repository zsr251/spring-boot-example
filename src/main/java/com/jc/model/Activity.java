package com.jc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Activity implements Serializable {
    private static final long serialVersionUID = 8737272002167661539L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 活动名
     */
    @Column(name = "activity_name")
    private String activityName;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startTime;

    /**
     * 报名开始时间
     */
    @Column(name = "apply_begin_time")
    private Date applyBeginTime;

    /**
     * 报名结束时间
     */
    @Column(name = "apply_end_time")
    private Date applyEndTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 报名人数
     */
    @Column(name = "apply_num")
    private Integer applyNum;

    /**
     * 0 正常 1 关闭
     */
    private String status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Transient
    private String other;

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取活动名
     *
     * @return activity_name - 活动名
     */
    public String getActivityName() {
        return activityName;
    }

    /**
     * 设置活动名
     *
     * @param activityName 活动名
     */
    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    /**
     * 获取开始时间
     *
     * @return start_time - 开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间
     *
     * @param startTime 开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取报名开始时间
     *
     * @return apply_begin_time - 报名开始时间
     */
    public Date getApplyBeginTime() {
        return applyBeginTime;
    }

    /**
     * 设置报名开始时间
     *
     * @param applyBeginTime 报名开始时间
     */
    public void setApplyBeginTime(Date applyBeginTime) {
        this.applyBeginTime = applyBeginTime;
    }

    /**
     * 获取报名结束时间
     *
     * @return apply_end_time - 报名结束时间
     */
    public Date getApplyEndTime() {
        return applyEndTime;
    }

    /**
     * 设置报名结束时间
     *
     * @param applyEndTime 报名结束时间
     */
    public void setApplyEndTime(Date applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取报名人数
     *
     * @return apply_num - 报名人数
     */
    public Integer getApplyNum() {
        return applyNum;
    }

    /**
     * 设置报名人数
     *
     * @param applyNum 报名人数
     */
    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    /**
     * 获取0 正常 1 关闭
     *
     * @return status - 0 正常 1 关闭
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置0 正常 1 关闭
     *
     * @param status 0 正常 1 关闭
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}