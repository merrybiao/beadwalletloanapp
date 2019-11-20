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
 * 活动优惠信息
 *
 * @author chengpan
 * @date 2017-03-09 12:35:21
 * @description: <描述>
 * @log 2017-03-09 12:35:21 chengpan 新建
 */
@Table(name = "activity_discount_info")
public class ActivityDiscountInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /** 主键 */
    private Integer discountId;
    /** 活动基本信息表主键 */
    private Integer activityId;
    /** 奖励金额 */
    private Double bonusAmount;
    /** 优惠券数量 */
    private Integer number;
    /** 限定金额 */
    private Double loanAmount;
    /** 受邀请人数 */
    private Integer invitedNumber;
    /** 创建时间 */
    private Date createTime;
    /** 使用说明 */
    private String instructions;
    private Double probability; // float(10,3) DEFAULT NULL COMMENT '中奖概率',
    private String img; // varchar(255) DEFAULT NULL COMMENT '实物图片',
    private Integer type; // int(1) DEFAULT NULL COMMENT '奖品类型 1优惠券；2实物',
    private Date updateTime; // datetime DEFAULT NULL,
    private Integer prizeTotal; // int(11) DEFAULT NULL COMMENT '奖品总数',
    private Integer prizeSurplus; // int(11) DEFAULT NULL COMMENT '奖品剩余数量',
    private String prizeName; // varchar(255) DEFAULT NULL COMMENT '奖品名称'
    private Integer isOpen;// 是否开启每天至少中一次免息功能 0不开启，1开启
    private Integer sort;// 排序

    // Constructors

    /** default constructor */
    public ActivityDiscountInfo() {}

    // Property accessors

    /**
     * @return 设置主键 属性值
     */
    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    /**
     * @return 获取主键 属性值
     */
    public Integer getDiscountId() {
        return this.discountId;
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
     * @return 设置奖励金额 属性值
     */
    public void setBonusAmount(Double bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    /**
     * @return 获取奖励金额 属性值
     */
    public Double getBonusAmount() {
        return this.bonusAmount;
    }

    /**
     * @return 设置优惠券数量 属性值
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * @return 获取优惠券数量 属性值
     */
    public Integer getNumber() {
        return this.number;
    }

    /**
     * @return 设置放款金额 属性值
     */
    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    /**
     * @return 获取放款金额 属性值
     */
    public Double getLoanAmount() {
        return this.loanAmount;
    }

    /**
     * @return 设置受邀请人数 属性值
     */
    public void setInvitedNumber(Integer invitedNumber) {
        this.invitedNumber = invitedNumber;
    }

    /**
     * @return 获取受邀请人数 属性值
     */
    public Integer getInvitedNumber() {
        return this.invitedNumber;
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
     * @return 获取 instructions属性值
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * @param instructions 设置 instructions 属性值为参数值 instructions
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPrizeTotal() {
        return prizeTotal;
    }

    public void setPrizeTotal(Integer prizeTotal) {
        this.prizeTotal = prizeTotal;
    }

    public Integer getPrizeSurplus() {
        return prizeSurplus;
    }

    public void setPrizeSurplus(Integer prizeSurplus) {
        this.prizeSurplus = prizeSurplus;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ActivityDiscountInfo[");
        builder.append(" 主键[discountId] = ");
        builder.append(discountId);
        builder.append(" 活动基本信息表主键[activityId] = ");
        builder.append(activityId);
        builder.append(" 奖励金额[bonusAmount] = ");
        builder.append(bonusAmount);
        builder.append(" 优惠券数量[number] = ");
        builder.append(number);
        builder.append(" 放款金额[loanAmount] = ");
        builder.append(loanAmount);
        builder.append(" 受邀请人数[invitedNumber] = ");
        builder.append(invitedNumber);
        builder.append(" 创建时间[createTime] = ");
        builder.append(createTime);
        builder.append(" 使用说明[instructions] = ");
        builder.append(instructions);
        builder.append(" 中奖概率[probability] = ");
        builder.append(probability);
        builder.append(" 实物图片[img] = ");
        builder.append(img);
        builder.append(" 奖品类型 1优惠券；2实物；3未中奖；4免息券[type] = ");
        builder.append(type);
        builder.append(" 奖品总数[prizeTotal] = ");
        builder.append(prizeTotal);
        builder.append(" 奖品剩余数量[prizeSurplus] = ");
        builder.append(prizeSurplus);
        builder.append(" 更新时间[updateTime] = ");
        builder.append(updateTime);
        builder.append(" 奖品名称[prizeName] = ");
        builder.append(prizeName);
        builder.append(" 是否开启每天至少中一次免息功能 0不开启，1开启[isOpen] = ");
        builder.append(isOpen);
        builder.append(" 排序[sort] = ");
        builder.append(sort);
        builder.append("]");
        return builder.toString();
    }
}
