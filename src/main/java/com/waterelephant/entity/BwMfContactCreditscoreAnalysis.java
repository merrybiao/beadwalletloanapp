package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_contact_creditscore_analysis
 * @author 
 */
@Table(name = "bw_mf_contact_creditscore_analysis")
public class BwMfContactCreditscoreAnalysis implements Serializable {
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
     * 前10联系人匹配智信分成功人数
     */
    private String creditscoreTop10ContactCount;

    /**
     * 前10联系人智信分中位数
     */
    private String creditscoreTop10ContactMedian;

    /**
     * 前10联系人平均智信分
     */
    private String creditscoreTop10ContactAvg;

    /**
     * 前10联系人最小智信分
     */
    private String creditscoreTop10ContactMin;

    /**
     * 前10联系人最大智信分
     */
    private String creditscoreTop10ContactMax;

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

    public String getCreditscoreTop10ContactCount() {
        return creditscoreTop10ContactCount;
    }

    public void setCreditscoreTop10ContactCount(String creditscoreTop10ContactCount) {
        this.creditscoreTop10ContactCount = creditscoreTop10ContactCount;
    }

    public String getCreditscoreTop10ContactMedian() {
        return creditscoreTop10ContactMedian;
    }

    public void setCreditscoreTop10ContactMedian(String creditscoreTop10ContactMedian) {
        this.creditscoreTop10ContactMedian = creditscoreTop10ContactMedian;
    }

    public String getCreditscoreTop10ContactAvg() {
        return creditscoreTop10ContactAvg;
    }

    public void setCreditscoreTop10ContactAvg(String creditscoreTop10ContactAvg) {
        this.creditscoreTop10ContactAvg = creditscoreTop10ContactAvg;
    }

    public String getCreditscoreTop10ContactMin() {
        return creditscoreTop10ContactMin;
    }

    public void setCreditscoreTop10ContactMin(String creditscoreTop10ContactMin) {
        this.creditscoreTop10ContactMin = creditscoreTop10ContactMin;
    }

    public String getCreditscoreTop10ContactMax() {
        return creditscoreTop10ContactMax;
    }

    public void setCreditscoreTop10ContactMax(String creditscoreTop10ContactMax) {
        this.creditscoreTop10ContactMax = creditscoreTop10ContactMax;
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