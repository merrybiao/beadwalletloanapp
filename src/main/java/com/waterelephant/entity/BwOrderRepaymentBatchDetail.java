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
 * @date 2017-05-15 10:17:58
 * @description: <描述>
 * @log 2017-05-15 10:17:58 chengpan 新建
 */

@Table(name = "bw_order_repayment_batch_detail")
public class BwOrderRepaymentBatchDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 主键
    private Long id;

    // 工单ID
    private Long orderId;

    // 批次
    private Integer number;

    // 金额
    private Double amount;

    /**
     * 还款后剩余金额
     */
    private Double residualAmount;

    /**
     * 总共应还金额(借款金额+逾期金额)
     */
    private Double totalAmount;
    /**
     * 还款时逾期金额(若有免罚息，则减去免罚息)
     */
    private Double overdueAmount;

    /**
     * 还款时逾期天数
     */
    private Integer overdueDay;

    // 还款渠道（1.宝付，2.连连）
    private Integer repaymentChannel;

    // 交易时间
    private Date tradeTime;

    // 创建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;

    // 是否最后一批
    private Boolean lastRepayment;

    /**
     * 终端类型：0系统后台 1Android 2ios 3WAP 4外部渠道
     */
    private Integer terminalType;

    // Constructors

    /** default constructor */
    public BwOrderRepaymentBatchDetail() {}

    // Property accessors

    /**
     * @return 设置主键 属性值
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 获取主键 属性值
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @return 设置工单ID 属性值
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return 获取工单ID 属性值
     */
    public Long getOrderId() {
        return this.orderId;
    }

    /**
     * @return 设置批次 属性值
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * @return 获取批次 属性值
     */
    public Integer getNumber() {
        return this.number;
    }

    /**
     * @return 设置金额 属性值
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * @return 获取金额 属性值
     */
    public Double getAmount() {
        return this.amount;
    }

    public Double getResidualAmount() {
        return residualAmount;
    }

    public void setResidualAmount(Double residualAmount) {
        this.residualAmount = residualAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(Double overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public Integer getOverdueDay() {
        return overdueDay;
    }

    public void setOverdueDay(Integer overdueDay) {
        this.overdueDay = overdueDay;
    }

    /**
     * @return 设置还款渠道 属性值
     */
    public void setRepaymentChannel(Integer repaymentChannel) {
        this.repaymentChannel = repaymentChannel;
    }

    /**
     * @return 获取还款渠道 属性值
     */
    public Integer getRepaymentChannel() {
        return this.repaymentChannel;
    }

    /**
     * @return 设置交易时间 属性值
     */
    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    /**
     * @return 获取交易时间 属性值
     */
    public Date getTradeTime() {
        return this.tradeTime;
    }

    /**
     * @return 设置创建时间 属性值
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return 获取更新时间 属性值
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public Boolean getLastRepayment() {
        return lastRepayment;
    }

    public void setLastRepayment(Boolean lastRepayment) {
        this.lastRepayment = lastRepayment;
    }

    public Integer getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BwOrderRepaymentBatchDetail[");
        builder.append(" 主键[id] = ");
        builder.append(id);
        builder.append(" 工单ID[orderId] = ");
        builder.append(orderId);
        builder.append(" 批次[number] = ");
        builder.append(number);
        builder.append(" 金额[amount] = ");
        builder.append(amount);
        builder.append(" 还款渠道[repaymentChannel] = ");
        builder.append(repaymentChannel);
        builder.append(" 交易时间[tradeTime] = ");
        builder.append(tradeTime);
        builder.append(" 创建时间[createTime] = ");
        builder.append(createTime);
        builder.append(" 更新时间[updateTime] = ");
        builder.append(updateTime);
        builder.append("]");
        return builder.toString();
    }
}
