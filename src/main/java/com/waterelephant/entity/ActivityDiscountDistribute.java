/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 本软件为武汉水象科技有限公司开发研制。
 * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 活动优惠派发
 *
 * @author chengpan
 * @date 2017-03-09 12:35:21
 * @description: <描述>
 * @log 2017-03-09 12:35:21 chengpan 新建
 */
@Table(name = "activity_discount_distribute")
public class ActivityDiscountDistribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /** 主键 */
    private Integer distributeId;
    /** 借款人信息表主键 */
    private Integer borrowId;
    /** 活动基本信息表主键 */
    private Integer activityId;
    /** 优惠信息表主键 */
    private Integer discountId;
    /** 派发类型(1、新手券，2.邀请券，3、抽奖，4、实物) */
    private String distributeType;
    /** 剩余数量 */
    private Integer number;
    /** 优惠金额 */
    private Double amount;
    /** 限定金额 */
    private Double loanAmount;
    /** 是否满足使用条件(0:不生效,1生效) */
    private Integer effective;
    /** 实物物品名称 */
    private String itemName;
    /** 创建时间 */
    private Date createTime;
    /** 失效时间 */
    private Date expiryTime;
    /** 已使用张数 */
    private Integer useNumber;
    /** 总张数 */
    private Integer totalNumber;
    // Constructors
    @Transient
    /** 是否过期（0，未过期；1.过期） */
    private Integer isOverTime;

    /**
     * 获取是否过期
     *
     * @return
     */
    public Integer getIsOverTime() {
        return isOverTime;
    }

    /**
     * 设置是否过期
     *
     * @param isOverTime
     */
    public void setIsOverTime(Integer isOverTime) {
        this.isOverTime = isOverTime;
    }

    /** default constructor */
    public ActivityDiscountDistribute() {}

    // Property accessors

    /**
     * @return 设置主键 属性值
     */
    public void setDistributeId(Integer distributeId) {
        this.distributeId = distributeId;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    /**
     * @return 获取主键 属性值
     */
    public Integer getDistributeId() {
        return this.distributeId;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    /**
     * @return 设置借款人信息表主键 属性值
     */
    public void setBorrowId(Integer borrowId) {
        this.borrowId = borrowId;
    }

    /**
     * @return 获取借款人信息表主键 属性值
     */
    public Integer getBorrowId() {
        return this.borrowId;
    }

    /**
     * @return 设置活动基本信息表主键 属性值
     */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
     * @return 获取活动基本信息表主键 属性值
     */
    public Integer getActivityId() {
        return this.activityId;
    }

    /**
     * @return 设置优惠信息表主键 属性值
     */
    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    /**
     * @return 获取优惠信息表主键 属性值
     */
    public Integer getDiscountId() {
        return this.discountId;
    }

    /**
     * @return 设置派发类型(1、新手券，2.邀请券，3、抽奖，4、实物) 属性值
     */
    public void setDistributeType(String distributeType) {
        this.distributeType = distributeType;
    }

    /**
     * @return 获取派发类型(1、新手券，2.邀请券，3、抽奖，4、实物) 属性值
     */
    public String getDistributeType() {
        return this.distributeType;
    }

    /**
     * @return 设置数量 属性值
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * @return 获取数量 属性值
     */
    public Integer getNumber() {
        return this.number;
    }

    /**
     * @return 设置优惠金额 属性值
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * @return 获取优惠金额 属性值
     */
    public Double getAmount() {
        return this.amount;
    }

    /**
     * @return 设置是否满足使用条件(0:不生效,1生效) 属性值
     */
    public void setEffective(Integer effective) {
        this.effective = effective;
    }

    /**
     * @return 获取是否满足使用条件(0:不生效,1生效) 属性值
     */
    public Integer getEffective() {
        return this.effective;
    }

    /**
     * @return 设置实物物品名称 属性值
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return 获取实物物品名称 属性值
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * @return 获取 createTime属性值
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 设置 createTime 属性值为参数值 createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return 获取 expiryTime属性值
     */
    public Date getExpiryTime() {
        return expiryTime;
    }

    /**
     * @param expiryTime 设置 expiryTime 属性值为参数值 expiryTime
     */
    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Integer getUseNumber() {
        return useNumber;
    }

    public void setUseNumber(Integer useNumber) {
        this.useNumber = useNumber;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ActivityDiscountDistribute[");
        builder.append(" 主键[distributeId] = ");
        builder.append(distributeId);
        builder.append(" 借款人信息表主键[borrowId] = ");
        builder.append(borrowId);
        builder.append(" 活动基本信息表主键[activityId] = ");
        builder.append(activityId);
        builder.append(" 优惠信息表主键[discountId] = ");
        builder.append(discountId);
        builder.append(" 派发类型(1、新手券，2.邀请券，3、抽奖，4、实物)[distributeType] = ");
        builder.append(distributeType);
        builder.append(" 数量[number] = ");
        builder.append(number);
        builder.append(" 优惠金额[amount] = ");
        builder.append(amount);
        builder.append(" 是否满足使用条件(0:不生效,1生效)[effective] = ");
        builder.append(effective);
        builder.append(" 实物物品名称[itemName] = ");
        builder.append(itemName);
        builder.append(" 创建时间[createTime] = ");
        builder.append(createTime);
        builder.append(" 失效时间[expiryTime] = ");
        builder.append(expiryTime);
        builder.append("]");
        return builder.toString();
    }
}
