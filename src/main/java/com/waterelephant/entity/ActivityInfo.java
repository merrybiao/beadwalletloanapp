/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 本软件为武汉水象科技有限公司开发研制。
 * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 活动基本信息
 *
 * @author chengpan
 * @date 2017-03-09 12:35:20
 * @description: <描述>
 * @log 2017-03-09 12:35:20 chengpan 新建
 */
@Table(name = "activity_info")
public class ActivityInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /** 主键 */
    private Integer activityId;
    /** 活动主题 */
    private String activityTitle;
    /** 活动开始时间 */
    private Date startTime;
    /** 活动结束时间 */
    private Date endTime;
    /** 指定哪些用户可以参与活动 */
    private String participant;
    /** 活动内容 */
    private String content;
    /** 活动规则 */
    private String activityRule;
    /** 是否启用活动（0、否，1、是） */
    private Integer status;
    /** 限制时间(单位为分钟) */
    private Integer limitedTime;
    /** 限制金额(单位为元) */
    private Double limitedAmount;
    /** 创建时间 */
    private Date createTime;
    /** 活动类型（1、新用户注册，2、邀请好友） */
    private String activityType;
    /** 有效期(单位年) */
    private Integer validYear;
    /** 有效期(单位月) */
    private Integer validMonth;
    /** 有效期(单位日) */
    private Integer validDay;
    /** 活动图片 */
    @Column(name = "activity_img")
    private String activityImg;
    /** 活动图片 */
    @Column(name = "activity_url")
    private String activityUrl;

    // Constructors

    /** default constructor */
    public ActivityInfo() {}

    public String getActivityImg() {
        return activityImg;
    }

    public void setActivityImg(String activityImg) {
        this.activityImg = activityImg;
    }

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    /**
     * @return 获取 activityId属性值
     */
    public Integer getActivityId() {
        return activityId;
    }

    /**
     * @param activityId 设置 activityId 属性值为参数值 activityId
     */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
     * @return 获取 activityTitle属性值
     */
    public String getActivityTitle() {
        return activityTitle;
    }

    /**
     * @param activityTitle 设置 activityTitle 属性值为参数值 activityTitle
     */
    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    /**
     * @return 获取 startTime属性值
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime 设置 startTime 属性值为参数值 startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return 获取 endTime属性值
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime 设置 endTime 属性值为参数值 endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return 获取 participant属性值
     */
    public String getParticipant() {
        return participant;
    }

    /**
     * @param participant 设置 participant 属性值为参数值 participant
     */
    public void setParticipant(String participant) {
        this.participant = participant;
    }

    /**
     * @return 获取 content属性值
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content 设置 content 属性值为参数值 content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return 获取 activityRule属性值
     */
    public String getActivityRule() {
        return activityRule;
    }

    /**
     * @param activityRule 设置 activityRule 属性值为参数值 activityRule
     */
    public void setActivityRule(String activityRule) {
        this.activityRule = activityRule;
    }

    /**
     * @return 获取 status属性值
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status 设置 status 属性值为参数值 status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return 获取 limitedTime属性值
     */
    public Integer getLimitedTime() {
        return limitedTime;
    }

    /**
     * @param limitedTime 设置 limitedTime 属性值为参数值 limitedTime
     */
    public void setLimitedTime(Integer limitedTime) {
        this.limitedTime = limitedTime;
    }

    /**
     * @return 获取 limitedAmount属性值
     */
    public Double getLimitedAmount() {
        return limitedAmount;
    }

    /**
     * @param limitedAmount 设置 limitedAmount 属性值为参数值 limitedAmount
     */
    public void setLimitedAmount(Double limitedAmount) {
        this.limitedAmount = limitedAmount;
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

    // Property accessors

    /**
     * @return 获取 activityType属性值
     */
    public String getActivityType() {
        return activityType;
    }

    /**
     * @param activityType 设置 activityType 属性值为参数值 activityType
     */
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    /**
     * @return 获取 validYear属性值
     */
    public Integer getValidYear() {
        return validYear;
    }

    /**
     * @param validYear 设置 validYear 属性值为参数值 validYear
     */
    public void setValidYear(Integer validYear) {
        this.validYear = validYear;
    }

    /**
     * @return 获取 validMonth属性值
     */
    public Integer getValidMonth() {
        return validMonth;
    }

    /**
     * @param validMonth 设置 validMonth 属性值为参数值 validMonth
     */
    public void setValidMonth(Integer validMonth) {
        this.validMonth = validMonth;
    }

    /**
     * @return 获取 validDay属性值
     */
    public Integer getValidDay() {
        return validDay;
    }

    /**
     * @param validDay 设置 validDay 属性值为参数值 validDay
     */
    public void setValidDay(Integer validDay) {
        this.validDay = validDay;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ActivityInfo[");
        builder.append(" 主键[activityId] = ");
        builder.append(activityId);
        builder.append(" 活动主题[activityTitle] = ");
        builder.append(activityTitle);
        builder.append(" 活动开始时间[startTime] = ");
        builder.append(startTime);
        builder.append(" 活动结束时间[endTime] = ");
        builder.append(endTime);
        builder.append(" 指定哪些用户可以参与活动[participant] = ");
        builder.append(participant);
        builder.append(" 活动内容[content] = ");
        builder.append(content);
        builder.append(" 是否启用活动（0、否，1、是）[status] = ");
        builder.append(status);
        builder.append(" 限制时间(单位为分钟)[limitedTime] = ");
        builder.append(limitedTime);
        builder.append(" 限制金额(单位为元)[limitedAmount] = ");
        builder.append(limitedAmount);
        builder.append(" 创建时间[createTime] = ");
        builder.append(createTime);
        builder.append(" 活动类型[activityType] = ");
        builder.append(activityType);
        builder.append("]");
        return builder.toString();
    }
}
