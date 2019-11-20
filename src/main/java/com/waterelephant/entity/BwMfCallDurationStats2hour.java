package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_call_duration_stats_2hour
 * @author 
 */
@Table(name = "bw_mf_call_duration_stats_2hour")
public class BwMfCallDurationStats2hour implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 工单id
     */
    private Long orderId;

    /**
     * 认证任务id
     */
    private String taskId;

    /**
     * 最近3个自然月工作日各时间段平均通话时长
     */
    @Column(name="call_duration_workday_3month")
    private String callDurationWorkday3month;

    /**
     * 最近3个自然月节假日各时间段平均通话时长
     */
    @Column(name="call_duration_holiday_3month")
    private String callDurationHoliday3month;

    /**
     * 最近6个自然月工作日各时间段平均通话时长
     */
    @Column(name="call_duration_workday_6month")
    private String callDurationWorkday6month;

    /**
     * 最近6个自然月节假日各时间段平均通话时长
     */
    @Column(name="call_duration_holiday_6month")
    private String callDurationHoliday6month;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCallDurationWorkday3month() {
        return callDurationWorkday3month;
    }

    public void setCallDurationWorkday3month(String callDurationWorkday3month) {
        this.callDurationWorkday3month = callDurationWorkday3month;
    }

    public String getCallDurationHoliday3month() {
        return callDurationHoliday3month;
    }

    public void setCallDurationHoliday3month(String callDurationHoliday3month) {
        this.callDurationHoliday3month = callDurationHoliday3month;
    }

    public String getCallDurationWorkday6month() {
        return callDurationWorkday6month;
    }

    public void setCallDurationWorkday6month(String callDurationWorkday6month) {
        this.callDurationWorkday6month = callDurationWorkday6month;
    }

    public String getCallDurationHoliday6month() {
        return callDurationHoliday6month;
    }

    public void setCallDurationHoliday6month(String callDurationHoliday6month) {
        this.callDurationHoliday6month = callDurationHoliday6month;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}