package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_travel_track_analysis_per_city
 * @author 
 */
@Table(name = "bw_mf_travel_track_analysis_per_city")
public class BwMfTravelTrackAnalysisPerCity implements Serializable {
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
     * 到达城市
     */
    private String arriveCity;

    /**
     * 到达日类型
     */
    private String arriveDayType;

    /**
     * 出发城市
     */
    private String leaveCity;

    /**
     * 出发日类型
     */
    private String leaveDayType;

    /**
     * 到达日期
     */
    private String arriveDay;

    /**
     * 出发日期
     */
    private String leaveDay;

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

    public String getArriveCity() {
        return arriveCity;
    }

    public void setArriveCity(String arriveCity) {
        this.arriveCity = arriveCity;
    }

    public String getArriveDayType() {
        return arriveDayType;
    }

    public void setArriveDayType(String arriveDayType) {
        this.arriveDayType = arriveDayType;
    }

    public String getLeaveCity() {
        return leaveCity;
    }

    public void setLeaveCity(String leaveCity) {
        this.leaveCity = leaveCity;
    }

    public String getLeaveDayType() {
        return leaveDayType;
    }

    public void setLeaveDayType(String leaveDayType) {
        this.leaveDayType = leaveDayType;
    }

    public String getArriveDay() {
        return arriveDay;
    }

    public void setArriveDay(String arriveDay) {
        this.arriveDay = arriveDay;
    }

    public String getLeaveDay() {
        return leaveDay;
    }

    public void setLeaveDay(String leaveDay) {
        this.leaveDay = leaveDay;
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