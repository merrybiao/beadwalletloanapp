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

/**
 * 活动优惠使用
 *
 * @author chengpan
 * @date 2017-03-09 12:35:21
 * @description: <描述>
 * @log 2017-03-09 12:35:21 chengpan 新建
 */
@Table(name = "activity_discount_useage")
public class ActivityDiscountUseage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /** 主键 */
    private Integer useageId;
    /** 使用方式(1、还款，2、续贷，3、过期，4、实物领取) */
    private Integer useageType;
    /** 正常使用时间或过期时间 */
    private Date useageTime;
    /** 还款计划表主键 */
    private Integer repaymentPlanId;
    /** 创建时间 */
    private Date createTime;
    /** 工单Id */
    private Integer orderId;
    // Constructors
    /** 优惠派发表表主键 */
    private Integer distributeId;
    /** 红包实际使用金额 */
    private Double useAmount;

    /** default constructor */
    public ActivityDiscountUseage() {}

    // Property accessors

    /**
     * @return 获取优惠派发表主键ID
     */
    public Integer getDistributeId() {
        return distributeId;
    }

    /**
     * @return 设置优惠派发表主键ID
     */
    public void setDistributeId(Integer distributeId) {
        this.distributeId = distributeId;
    }

    /**
     * @return 获取 orderId属性值
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * @param orderId 设置 orderId 属性值为参数值 orderId
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * @return 设置主键 属性值
     */
    public void setUseageId(Integer useageId) {
        this.useageId = useageId;
    }

    /**
     * @return 获取主键 属性值
     */
    public Integer getUseageId() {
        return this.useageId;
    }

    /**
     * @return 设置使用方式(1、还款，2、续贷，3、过期，4、实物领取) 属性值
     */
    public void setUseageType(Integer useageType) {
        this.useageType = useageType;
    }

    /**
     * @return 获取使用方式(1、还款，2、续贷，3、过期，4、实物领取) 属性值
     */
    public Integer getUseageType() {
        return this.useageType;
    }

    /**
     * @return 设置正常使用时间或过期时间 属性值
     */
    public void setUseageTime(Date useageTime) {
        this.useageTime = useageTime;
    }

    /**
     * @return 获取正常使用时间或过期时间 属性值
     */
    public Date getUseageTime() {
        return this.useageTime;
    }

    /**
     * @return 设置还款计划表主键 属性值
     */
    public void setRepaymentPlanId(Integer repaymentPlanId) {
        this.repaymentPlanId = repaymentPlanId;
    }

    /**
     * @return 获取还款计划表主键 属性值
     */
    public Integer getRepaymentPlanId() {
        return this.repaymentPlanId;
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
     * @return 获取 useAmount属性值
     */
    public Double getUseAmount() {
        return useAmount;
    }

    /**
     * @param useAmount 设置 useAmount 属性值为参数值 useAmount
     */
    public void setUseAmount(Double useAmount) {
        this.useAmount = useAmount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ActivityDiscountUseage[");
        builder.append(" 主键[useageId] = ");
        builder.append(useageId);
        builder.append(" 使用方式(1、还款，2、续贷，3、过期，4、实物领取)[useageType] = ");
        builder.append(useageType);
        builder.append(" 正常使用时间或过期时间[useageTime] = ");
        builder.append(useageTime);
        builder.append(" 还款计划表主键[repaymentPlanId] = ");
        builder.append(repaymentPlanId);
        builder.append(" 创建时间[createTime] = ");
        builder.append(createTime);
        builder.append("]");
        return builder.toString();
    }
}
