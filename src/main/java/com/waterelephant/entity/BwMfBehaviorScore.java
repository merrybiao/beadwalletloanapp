package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_behavior_score
 * @author 
 */
@Table(name = "bw_mf_behavior_score")
public class BwMfBehaviorScore implements Serializable {
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
     * 联系人评分
     */
    private String riskContactInfoScore;

    /**
     * 基本信息评分
     */
    private String baseInfoScore;

    /**
     * 缴费消费评分
     */
    private String billInfoScore;

    /**
     * 综合评分
     */
    private String totalScore;

    /**
     * 通话行为评分
     */
    private String callInfoScore;

    /**
     * 历史借贷行为评分
     */
    private String multapplyScore;

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

    public String getRiskContactInfoScore() {
        return riskContactInfoScore;
    }

    public void setRiskContactInfoScore(String riskContactInfoScore) {
        this.riskContactInfoScore = riskContactInfoScore;
    }

    public String getBaseInfoScore() {
        return baseInfoScore;
    }

    public void setBaseInfoScore(String baseInfoScore) {
        this.baseInfoScore = baseInfoScore;
    }

    public String getBillInfoScore() {
        return billInfoScore;
    }

    public void setBillInfoScore(String billInfoScore) {
        this.billInfoScore = billInfoScore;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getCallInfoScore() {
        return callInfoScore;
    }

    public void setCallInfoScore(String callInfoScore) {
        this.callInfoScore = callInfoScore;
    }

    public String getMultapplyScore() {
        return multapplyScore;
    }

    public void setMultapplyScore(String multapplyScore) {
        this.multapplyScore = multapplyScore;
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