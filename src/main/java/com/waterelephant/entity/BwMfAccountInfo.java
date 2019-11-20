package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_account_info
 * @author 
 */
@Table(name = "bw_mf_account_info")
public class BwMfAccountInfo implements Serializable {
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
     * 账户余额。整形数字精确到分
     */
    private String accountBalance;

    /**
     * 当前话费。整形数字精确到分
     */
    private String currentFee;

    /**
     * 账户星级。0-5或未知
     */
    private String creditLevel;

    /**
     * 账户状态。正常、欠费、停机、销户、未激活、未知
     */
    private String mobileStatus;

    /**
     * 入网时间。YYYY-MM-DD或未知
     */
    private String netTime;

    /**
     * 网龄。整形数字精确到月
     */
    private String netAge;

    /**
     * 实名制信息。已登记、未登记、未知
     */
    private String realInfo;

    /**
     * 积分。整形数字
     */
    private String creditPoint;

    /**
     * 品牌
     */
    private String brandName;

    /**
     * 缴费类型。预付费、后付费、未知
     */
    private String payType;

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

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getCurrentFee() {
        return currentFee;
    }

    public void setCurrentFee(String currentFee) {
        this.currentFee = currentFee;
    }

    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    public String getMobileStatus() {
        return mobileStatus;
    }

    public void setMobileStatus(String mobileStatus) {
        this.mobileStatus = mobileStatus;
    }

    public String getNetTime() {
        return netTime;
    }

    public void setNetTime(String netTime) {
        this.netTime = netTime;
    }

    public String getNetAge() {
        return netAge;
    }

    public void setNetAge(String netAge) {
        this.netAge = netAge;
    }

    public String getRealInfo() {
        return realInfo;
    }

    public void setRealInfo(String realInfo) {
        this.realInfo = realInfo;
    }

    public String getCreditPoint() {
        return creditPoint;
    }

    public void setCreditPoint(String creditPoint) {
        this.creditPoint = creditPoint;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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