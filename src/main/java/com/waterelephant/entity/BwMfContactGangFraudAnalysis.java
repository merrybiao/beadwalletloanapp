package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_contact_gang_fraud_analysis
 * @author 
 */
@Table(name = "bw_mf_contact_gang_fraud_analysis")
public class BwMfContactGangFraudAnalysis implements Serializable {
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
     * 一阶联系人总数
     */
    private String contactFirstCount;

    /**
     * 一阶联系黑名单总数
     */
    private String contactFirstBlackCount;

    /**
     * 一阶联系黑名单总数/一阶联系人总数
     */
    private String contactFirstBlackProportion;

    /**
     * 引起黑名单的一阶联系人数
     */
    private String contactFirstCauseBlackCount;

    /**
     * 引起黑名单的一阶联系人占比
     */
    private String contactFirstCauseBlackProportion;

    /**
     * 二阶联系黑名单总数
     */
    private String contactSecondBlackCount;

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

    public String getContactFirstCount() {
        return contactFirstCount;
    }

    public void setContactFirstCount(String contactFirstCount) {
        this.contactFirstCount = contactFirstCount;
    }

    public String getContactFirstBlackCount() {
        return contactFirstBlackCount;
    }

    public void setContactFirstBlackCount(String contactFirstBlackCount) {
        this.contactFirstBlackCount = contactFirstBlackCount;
    }

    public String getContactFirstBlackProportion() {
        return contactFirstBlackProportion;
    }

    public void setContactFirstBlackProportion(String contactFirstBlackProportion) {
        this.contactFirstBlackProportion = contactFirstBlackProportion;
    }

    public String getContactFirstCauseBlackCount() {
        return contactFirstCauseBlackCount;
    }

    public void setContactFirstCauseBlackCount(String contactFirstCauseBlackCount) {
        this.contactFirstCauseBlackCount = contactFirstCauseBlackCount;
    }

    public String getContactFirstCauseBlackProportion() {
        return contactFirstCauseBlackProportion;
    }

    public void setContactFirstCauseBlackProportion(String contactFirstCauseBlackProportion) {
        this.contactFirstCauseBlackProportion = contactFirstCauseBlackProportion;
    }

    public String getContactSecondBlackCount() {
        return contactSecondBlackCount;
    }

    public void setContactSecondBlackCount(String contactSecondBlackCount) {
        this.contactSecondBlackCount = contactSecondBlackCount;
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