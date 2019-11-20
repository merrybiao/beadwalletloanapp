/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 本软件为武汉水象股份有限公司开发研制。
 * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chengpan
 * @date 2017-07-05 11:26:13
 * @description: 工单处理记录
 * @log 2017-07-05 11:26:13 chengpan 新建
 */

@Table(name = "bw_order_process_record")
public class BwOrderProcessRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 借款人Id */
    private Long borrowerId;
    /** 借款人注册时间 */
    private Date registerTime;
    /** 注册渠道 */
    private Integer registerChannel;
    /** 工单渠道 */
    private Integer orderChannel;
    /** 产品类型(1单期 2分期) */
    private Integer productType;
    /** 工单Id */
    private Long orderId;
    /** 草稿时间 */
    private Date draftTime;
    /** 提交申请时间 */
    private Date submitTime;
    /** 系统审核时间 */
    private Date systemAuditTime;
    /** 系统审核状态(0 拒绝 1通过) */
    private Integer systemAuditType;
    /** 人工初审时间 */
    private Date artifiAuditTrialTime;
    /** 人工初审结果(0 拒绝 1通过) */
    private Integer artifiAuditTrialType;
    /** 人工终审时间 */
    private Date artifiAuditFinalTime;
    /** 人工终审结果(0 拒绝 1通过) */
    private Integer artifiAuditFinalType;
    /** 签约时间 */
    private Date signTime;
    /** 生成合同时间 */
    private Date contractTime;
    /** 放款时间 */
    private Date loanTime;
    /** 结束时间(还款时间) */
    private Date endTime;
    /** 已借款次数 */
    private Integer borrowCount;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

    // Constructors

    /** default constructor */
    public BwOrderProcessRecord() {}

    // Property accessors

    /**
     * @return 设置主键 属性值
     */
    public BwOrderProcessRecord setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * @return 获取主键 属性值
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @return 设置借款人Id 属性值
     */
    public BwOrderProcessRecord setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
        return this;
    }

    /**
     * @return 获取借款人Id 属性值
     */
    public Long getBorrowerId() {
        return this.borrowerId;
    }

    /**
     * @return 设置借款人注册时间 属性值
     */
    public BwOrderProcessRecord setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
        return this;
    }

    /**
     * @return 获取借款人注册时间 属性值
     */
    public Date getRegisterTime() {
        return this.registerTime;
    }

    /**
     * @return 设置注册渠道 属性值
     */
    public BwOrderProcessRecord setRegisterChannel(Integer registerChannel) {
        this.registerChannel = registerChannel;
        return this;
    }

    /**
     * @return 获取注册渠道 属性值
     */
    public Integer getRegisterChannel() {
        return this.registerChannel;
    }

    /**
     * @return 设置工单渠道 属性值
     */
    public BwOrderProcessRecord setOrderChannel(Integer orderChannel) {
        this.orderChannel = orderChannel;
        return this;
    }

    /**
     * @return 获取工单渠道 属性值
     */
    public Integer getOrderChannel() {
        return this.orderChannel;
    }

    /**
     * @return 设置产品类型(1单期 2分期) 属性值
     */
    public BwOrderProcessRecord setProductType(Integer productType) {
        this.productType = productType;
        return this;
    }

    /**
     * @return 获取产品类型(1单期 2分期) 属性值
     */
    public Integer getProductType() {
        return this.productType;
    }

    /**
     * @return 设置工单Id 属性值
     */
    public BwOrderProcessRecord setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    /**
     * @return 获取工单Id 属性值
     */
    public Long getOrderId() {
        return this.orderId;
    }

    /**
     * @return 设置草稿时间 属性值
     */
    public BwOrderProcessRecord setDraftTime(Date draftTime) {
        this.draftTime = draftTime;
        return this;
    }

    /**
     * @return 获取草稿时间 属性值
     */
    public Date getDraftTime() {
        return this.draftTime;
    }

    /**
     * @return 设置提交申请时间 属性值
     */
    public BwOrderProcessRecord setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
        return this;
    }

    /**
     * @return 获取提交申请时间 属性值
     */
    public Date getSubmitTime() {
        return this.submitTime;
    }

    /**
     * @return 设置系统审核时间 属性值
     */
    public BwOrderProcessRecord setSystemAuditTime(Date systemAuditTime) {
        this.systemAuditTime = systemAuditTime;
        return this;
    }

    /**
     * @return 获取系统审核时间 属性值
     */
    public Date getSystemAuditTime() {
        return this.systemAuditTime;
    }

    /**
     * @return 设置系统审核状态(0 拒绝 1通过) 属性值
     */
    public BwOrderProcessRecord setSystemAuditType(Integer systemAuditType) {
        this.systemAuditType = systemAuditType;
        return this;
    }

    /**
     * @return 获取系统审核状态(0 拒绝 1通过) 属性值
     */
    public Integer getSystemAuditType() {
        return this.systemAuditType;
    }

    /**
     * @return 设置人工初审时间 属性值
     */
    public BwOrderProcessRecord setArtifiAuditTrialTime(Date artifiAuditTrialTime) {
        this.artifiAuditTrialTime = artifiAuditTrialTime;
        return this;
    }

    /**
     * @return 获取人工初审时间 属性值
     */
    public Date getArtifiAuditTrialTime() {
        return this.artifiAuditTrialTime;
    }

    /**
     * @return 设置人工初审结果(0 拒绝 1通过) 属性值
     */
    public BwOrderProcessRecord setArtifiAuditTrialType(Integer artifiAuditTrialType) {
        this.artifiAuditTrialType = artifiAuditTrialType;
        return this;
    }

    /**
     * @return 获取人工初审结果(0 拒绝 1通过) 属性值
     */
    public Integer getArtifiAuditTrialType() {
        return this.artifiAuditTrialType;
    }

    /**
     * @return 设置人工终审时间 属性值
     */
    public BwOrderProcessRecord setArtifiAuditFinalTime(Date artifiAuditFinalTime) {
        this.artifiAuditFinalTime = artifiAuditFinalTime;
        return this;
    }

    /**
     * @return 获取人工终审时间 属性值
     */
    public Date getArtifiAuditFinalTime() {
        return this.artifiAuditFinalTime;
    }

    /**
     * @return 设置人工终审结果(0 拒绝 1通过) 属性值
     */
    public BwOrderProcessRecord setArtifiAuditFinalType(Integer artifiAuditFinalType) {
        this.artifiAuditFinalType = artifiAuditFinalType;
        return this;
    }

    /**
     * @return 获取人工终审结果(0 拒绝 1通过) 属性值
     */
    public Integer getArtifiAuditFinalType() {
        return this.artifiAuditFinalType;
    }

    /**
     * @return 设置签约时间 属性值
     */
    public BwOrderProcessRecord setSignTime(Date signTime) {
        this.signTime = signTime;
        return this;
    }

    /**
     * @return 获取签约时间 属性值
     */
    public Date getSignTime() {
        return this.signTime;
    }

    /**
     * @return 设置生成合同时间 属性值
     */
    public BwOrderProcessRecord setContractTime(Date contractTime) {
        this.contractTime = contractTime;
        return this;
    }

    /**
     * @return 获取生成合同时间 属性值
     */
    public Date getContractTime() {
        return this.contractTime;
    }

    /**
     * @return 设置放款时间 属性值
     */
    public BwOrderProcessRecord setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
        return this;
    }

    /**
     * @return 获取放款时间 属性值
     */
    public Date getLoanTime() {
        return this.loanTime;
    }

    /**
     * @return 设置结束时间(还款时间) 属性值
     */
    public BwOrderProcessRecord setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    /**
     * @return 获取结束时间(还款时间) 属性值
     */
    public Date getEndTime() {
        return this.endTime;
    }

    /**
     * @return 设置已借款次数 属性值
     */
    public BwOrderProcessRecord setBorrowCount(Integer borrowCount) {
        this.borrowCount = borrowCount;
        return this;
    }

    /**
     * @return 获取已借款次数 属性值
     */
    public Integer getBorrowCount() {
        return this.borrowCount;
    }

    /**
     * @return 设置创建时间 属性值
     */
    public BwOrderProcessRecord setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * @return 获取创建时间 属性值
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * @return 设置更新时间 属性值
     */
    public BwOrderProcessRecord setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    /**
     * @return 获取更新时间 属性值
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BwOrderProcessRecord[");
        builder.append(" 主键[id] = ");
        builder.append(id);
        builder.append(" 借款人Id[borrowerId] = ");
        builder.append(borrowerId);
        builder.append(" 借款人注册时间[registerTime] = ");
        builder.append(registerTime);
        builder.append(" 注册渠道[registerChannel] = ");
        builder.append(registerChannel);
        builder.append(" 工单渠道[orderChannel] = ");
        builder.append(orderChannel);
        builder.append(" 产品类型(1单期 2分期)[productType] = ");
        builder.append(productType);
        builder.append(" 工单Id[orderId] = ");
        builder.append(orderId);
        builder.append(" 草稿时间[draftTime] = ");
        builder.append(draftTime);
        builder.append(" 提交申请时间[submitTime] = ");
        builder.append(submitTime);
        builder.append(" 系统审核时间[systemAuditTime] = ");
        builder.append(systemAuditTime);
        builder.append(" 系统审核状态(0 拒绝 1通过)[systemAuditType] = ");
        builder.append(systemAuditType);
        builder.append(" 人工初审时间[artifiAuditTrialTime] = ");
        builder.append(artifiAuditTrialTime);
        builder.append(" 人工初审结果(0 拒绝 1通过)[artifiAuditTrialType] = ");
        builder.append(artifiAuditTrialType);
        builder.append(" 人工终审时间[artifiAuditFinalTime] = ");
        builder.append(artifiAuditFinalTime);
        builder.append(" 人工终审结果(0 拒绝 1通过)[artifiAuditFinalType] = ");
        builder.append(artifiAuditFinalType);
        builder.append(" 签约时间[signTime] = ");
        builder.append(signTime);
        builder.append(" 生成合同时间[contractTime] = ");
        builder.append(contractTime);
        builder.append(" 放款时间[loanTime] = ");
        builder.append(loanTime);
        builder.append(" 结束时间(还款时间)[endTime] = ");
        builder.append(endTime);
        builder.append(" 已借款次数[borrowCount] = ");
        builder.append(borrowCount);
        builder.append(" 创建时间[createTime] = ");
        builder.append(createTime);
        builder.append(" 更新时间[updateTime] = ");
        builder.append(updateTime);
        builder.append("]");
        return builder.toString();
    }
}
