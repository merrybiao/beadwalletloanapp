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
 * 活动参与
 *
 * @author chengpan
 * @date 2017-03-09 12:35:21
 * @description: <描述>
 * @log 2017-03-09 12:35:21 chengpan 新建
 */
@Table(name = "activity_participate")
public class ActivityParticipate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /** 主键 */
    private Integer uaId;
    /** 借款人信息表主键 */
    private Integer borrowId;
    /** 活动基本信息表主键 */
    private Integer activityId;
    /** 参加活动时间 */
    private Date participationTime;
    /** 创建时间 */
    private Date createTime;

    // Constructors

    /** default constructor */
    public ActivityParticipate() {}

    // Property accessors

    /**
     * @return 设置主键 属性值
     */
    public void setUaId(Integer uaId) {
        this.uaId = uaId;
    }

    /**
     * @return 获取主键 属性值
     */
    public Integer getUaId() {
        return this.uaId;
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
     * @return 设置参加活动时间 属性值
     */
    public void setParticipationTime(Date participationTime) {
        this.participationTime = participationTime;
    }

    /**
     * @return 获取参加活动时间 属性值
     */
    public Date getParticipationTime() {
        return this.participationTime;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ActivityParticipate[");
        builder.append(" 主键[uaId] = ");
        builder.append(uaId);
        builder.append(" 借款人信息表主键[borrowId] = ");
        builder.append(borrowId);
        builder.append(" 活动基本信息表主键[activityId] = ");
        builder.append(activityId);
        builder.append(" 参加活动时间[participationTime] = ");
        builder.append(participationTime);
        builder.append(" 创建时间[createTime] = ");
        builder.append(createTime);
        builder.append("]");
        return builder.toString();
    }
}
