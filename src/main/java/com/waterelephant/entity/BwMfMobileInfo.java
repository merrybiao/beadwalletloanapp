package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_mobile_info
 * @author 
 */
@Table(name = "bw_mf_mobile_info")
public class BwMfMobileInfo implements Serializable {
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
     * 运营商登记身份证号码
     */
    private String identityCode;

    /**
     * 联系地址
     */
    private String contactAddr;

    /**
     * 账户余额
     */
    private String accountBalance;

    /**
     * 手机号
     */
    private String userMobile;

    /**
     * 号码归属地
     */
    private String mobileNetAddr;

    /**
     * 运营商
     */
    private String mobileCarrier;

    /**
     * 运营商登记姓名
     */
    private String realName;

    /**
     * 手机号账户状态
     */
    private String accountStatus;

    /**
     * 入网时间
     */
    private String mobileNetTime;

    /**
     * 套餐名称
     */
    private String packageType;

    /**
     * 入网时长
     */
    private String mobileNetAge;

    /**
     * 邮箱
     */
    private String email;

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

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getContactAddr() {
        return contactAddr;
    }

    public void setContactAddr(String contactAddr) {
        this.contactAddr = contactAddr;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getMobileNetAddr() {
        return mobileNetAddr;
    }

    public void setMobileNetAddr(String mobileNetAddr) {
        this.mobileNetAddr = mobileNetAddr;
    }

    public String getMobileCarrier() {
        return mobileCarrier;
    }

    public void setMobileCarrier(String mobileCarrier) {
        this.mobileCarrier = mobileCarrier;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getMobileNetTime() {
        return mobileNetTime;
    }

    public void setMobileNetTime(String mobileNetTime) {
        this.mobileNetTime = mobileNetTime;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getMobileNetAge() {
        return mobileNetAge;
    }

    public void setMobileNetAge(String mobileNetAge) {
        this.mobileNetAge = mobileNetAge;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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