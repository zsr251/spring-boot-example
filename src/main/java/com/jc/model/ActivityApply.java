package com.jc.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "activity_apply")
public class ActivityApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 活动id
     */
    @Column(name = "activity_id")
    private Integer activityId;

    /**
     * 员工id
     */
    @Column(name = "employee_id")
    private Integer employeeId;

    /**
     * 活动名
     */
    @Column(name = "activity_name")
    private String activityName;

    /**
     * 真是姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 0 报名 1 取消报名
     */
    private String status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取活动id
     *
     * @return activity_id - 活动id
     */
    public Integer getActivityId() {
        return activityId;
    }

    /**
     * 设置活动id
     *
     * @param activityId 活动id
     */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
     * 获取员工id
     *
     * @return employee_id - 员工id
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * 设置员工id
     *
     * @param employeeId 员工id
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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
     * 获取真是姓名
     *
     * @return real_name - 真是姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真是姓名
     *
     * @param realName 真是姓名
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
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
     * 获取0 报名 1 取消报名
     *
     * @return status - 0 报名 1 取消报名
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置0 报名 1 取消报名
     *
     * @param status 0 报名 1 取消报名
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