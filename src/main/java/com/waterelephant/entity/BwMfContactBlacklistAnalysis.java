package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_contact_blacklist_analysis
 * @author 
 */
@Table(name = "bw_mf_contact_blacklist_analysis")
public class BwMfContactBlacklistAnalysis implements Serializable {
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
     * 前10联系人车贷黑名单人数占比
     */
    private String blackTop10ContactCarloanBlacklistCountRatio;

    /**
     * 前10联系人虚假号码人数占比
     */
    private String blackTop10ContactFakemobileCountRatio;

    /**
     * 前10联系人2种及以上黑名单人数占比
     */
    private String blackTop10ContactOver2CountRatio;

    /**
     * 前10联系人游戏消费欠费人数占比
     */
    private String blackTop10ContactPaymentfraudCountRatio;

    /**
     * 前10联系人信贷逾期后还款人数占比
     */
    private String blackTop10ContactDiscreditrepayCountRatio;

    /**
     * 前10联系人黑名单人数占比
     */
    private String blackTop10ContactTotalCountRatio;

    /**
     * 前10联系人信贷逾期名单人数占比
     */
    private String blackTop10ContactCreditcrackCountRatio;

    /**
     * 前10联系人助学贷款逾期名单人数占比
     */
    private String blackTop10ContactStudentloansOverdueCountRatio;

    /**
     * 前10联系人刷单人数占比
     */
    private String blackTop10ContactScalpingCountRatio;

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

    public String getBlackTop10ContactCarloanBlacklistCountRatio() {
        return blackTop10ContactCarloanBlacklistCountRatio;
    }

    public void setBlackTop10ContactCarloanBlacklistCountRatio(String blackTop10ContactCarloanBlacklistCountRatio) {
        this.blackTop10ContactCarloanBlacklistCountRatio = blackTop10ContactCarloanBlacklistCountRatio;
    }

    public String getBlackTop10ContactFakemobileCountRatio() {
        return blackTop10ContactFakemobileCountRatio;
    }

    public void setBlackTop10ContactFakemobileCountRatio(String blackTop10ContactFakemobileCountRatio) {
        this.blackTop10ContactFakemobileCountRatio = blackTop10ContactFakemobileCountRatio;
    }

    public String getBlackTop10ContactOver2CountRatio() {
        return blackTop10ContactOver2CountRatio;
    }

    public void setBlackTop10ContactOver2CountRatio(String blackTop10ContactOver2CountRatio) {
        this.blackTop10ContactOver2CountRatio = blackTop10ContactOver2CountRatio;
    }

    public String getBlackTop10ContactPaymentfraudCountRatio() {
        return blackTop10ContactPaymentfraudCountRatio;
    }

    public void setBlackTop10ContactPaymentfraudCountRatio(String blackTop10ContactPaymentfraudCountRatio) {
        this.blackTop10ContactPaymentfraudCountRatio = blackTop10ContactPaymentfraudCountRatio;
    }

    public String getBlackTop10ContactDiscreditrepayCountRatio() {
        return blackTop10ContactDiscreditrepayCountRatio;
    }

    public void setBlackTop10ContactDiscreditrepayCountRatio(String blackTop10ContactDiscreditrepayCountRatio) {
        this.blackTop10ContactDiscreditrepayCountRatio = blackTop10ContactDiscreditrepayCountRatio;
    }

    public String getBlackTop10ContactTotalCountRatio() {
        return blackTop10ContactTotalCountRatio;
    }

    public void setBlackTop10ContactTotalCountRatio(String blackTop10ContactTotalCountRatio) {
        this.blackTop10ContactTotalCountRatio = blackTop10ContactTotalCountRatio;
    }

    public String getBlackTop10ContactCreditcrackCountRatio() {
        return blackTop10ContactCreditcrackCountRatio;
    }

    public void setBlackTop10ContactCreditcrackCountRatio(String blackTop10ContactCreditcrackCountRatio) {
        this.blackTop10ContactCreditcrackCountRatio = blackTop10ContactCreditcrackCountRatio;
    }

    public String getBlackTop10ContactStudentloansOverdueCountRatio() {
        return blackTop10ContactStudentloansOverdueCountRatio;
    }

    public void setBlackTop10ContactStudentloansOverdueCountRatio(String blackTop10ContactStudentloansOverdueCountRatio) {
        this.blackTop10ContactStudentloansOverdueCountRatio = blackTop10ContactStudentloansOverdueCountRatio;
    }

    public String getBlackTop10ContactScalpingCountRatio() {
        return blackTop10ContactScalpingCountRatio;
    }

    public void setBlackTop10ContactScalpingCountRatio(String blackTop10ContactScalpingCountRatio) {
        this.blackTop10ContactScalpingCountRatio = blackTop10ContactScalpingCountRatio;
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