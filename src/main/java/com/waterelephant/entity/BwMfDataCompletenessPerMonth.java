package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_data_completeness_per_month
 * @author 
 */
@Table(name = "bw_mf_data_completeness_per_month")
public class BwMfDataCompletenessPerMonth implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long dataCompletenessId;

    /**
     * 工单id
     */
    private Long orderId;

    /**
     * 认证任务id
     */
    private String taskId;

    /**
     * 月短信数据是否完整
     */
    private String isMsgDataComplete;

    /**
     * 月消费数据是否完整
     */
    private String isConsumeDataComplete;

    /**
     * 月份
     */
    private String month;

    /**
     * 月通话数据是否完整
     */
    private String isCallDataComplete;

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

    public Long getDataCompletenessId() {
        return dataCompletenessId;
    }

    public void setDataCompletenessId(Long dataCompletenessId) {
        this.dataCompletenessId = dataCompletenessId;
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

    public String getIsMsgDataComplete() {
        return isMsgDataComplete;
    }

    public void setIsMsgDataComplete(String isMsgDataComplete) {
        this.isMsgDataComplete = isMsgDataComplete;
    }

    public String getIsConsumeDataComplete() {
        return isConsumeDataComplete;
    }

    public void setIsConsumeDataComplete(String isConsumeDataComplete) {
        this.isConsumeDataComplete = isConsumeDataComplete;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getIsCallDataComplete() {
        return isCallDataComplete;
    }

    public void setIsCallDataComplete(String isCallDataComplete) {
        this.isCallDataComplete = isCallDataComplete;
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