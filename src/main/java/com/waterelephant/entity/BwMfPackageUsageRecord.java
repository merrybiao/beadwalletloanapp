package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * bw_mf_package_usage_record
 * @author 
 */
@Table(name = "bw_mf_package_usage_record")
public class BwMfPackageUsageRecord implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long packageUsageId;

    /**
     * 工单id
     */
    private Long orderId;

    /**
     * 认证任务id
     */
    private String taskId;

    /**
     * 使用周期。YYYY-MM
     */
    private String itemName;

    /**
     * 项目类型。如：流量、语音、短信
     */
    private String itemType;

    /**
     * 用户号码
     */
    private String userNumber;

    /**
     * 剩余量。整形数字(KB/分钟/条)
     */
    private String itemBalance;

    /**
     * 使用量。整形数字(KB/分钟/条)
     */
    private String itemUsage;

    /**
     * 套餐总量。整形数字(KB/分钟/条)
     */
    private String itemTotal;

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

    public Long getPackageUsageId() {
        return packageUsageId;
    }

    public void setPackageUsageId(Long packageUsageId) {
        this.packageUsageId = packageUsageId;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getItemBalance() {
        return itemBalance;
    }

    public void setItemBalance(String itemBalance) {
        this.itemBalance = itemBalance;
    }

    public String getItemUsage() {
        return itemUsage;
    }

    public void setItemUsage(String itemUsage) {
        this.itemUsage = itemUsage;
    }

    public String getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(String itemTotal) {
        this.itemTotal = itemTotal;
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