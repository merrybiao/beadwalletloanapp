/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 本软件为武汉水象科技有限公司开发研制。
 * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Module:
 *
 * BwPaymentDetail.java
 *
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: 还款或续贷支付明细
 */
@Table(name = "bw_payment_detail")
public class BwPaymentDetail implements Serializable {

    private static final long serialVersionUID = 4249787457558006685L;

    /**
     * 构造函数
     */
    public BwPaymentDetail() {
        super();
    }

    /**
     * 构造函数
     *
     * @param id
     * @param orderId
     * @param repayId
     * @param tradeType
     * @param tradeAmount
     * @param xudaiAmount
     * @param borrowAmount
     * @param overdueAmount
     * @param couponAmount
     * @param createTime
     * @param updateTime
     */
    public BwPaymentDetail(Long id, Long orderId, Long repayId, Integer tradeType, Double tradeAmount, Double xudaiAmount, Double borrowAmount, Double overdueAmount, Double couponAmount,
            Date createTime, Date updateTime) {
        super();
        this.id = id;
        this.orderId = orderId;
        this.repayId = repayId;
        this.tradeType = tradeType;
        this.tradeAmount = tradeAmount;
        this.xudaiAmount = xudaiAmount;
        this.borrowAmount = borrowAmount;
        this.overdueAmount = overdueAmount;
        this.couponAmount = couponAmount;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 工单ID
     */
    private Long orderId;
    /**
     * 借款人ID
     */
    private Long borrowerId;
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 银行编号
     */
    private String bankCode;
    /**
     * 还款计划ID
     */
    private Long repayId;
    /**
     * 交易类型(1.还款 2.续贷)
     */
    private Integer tradeType;
    /**
     * 支付通道（1.宝付，2.连连）
     */
    private Integer payChannel;
    /**
     * 支付状态(0.未支付 1.已支付)
     */
    private Integer payStatus;
    /**
     * 逾期天数
     */
    private Integer overdueDay;
    /**
     * 第几次续贷
     */
    private Integer xudaiTimes;
    /**
     * 实际交易金额
     */
    private Double tradeAmount;
    /**
     * 续贷工本费
     */
    private Double xudaiAmount;
    /**
     * 借款本金
     */
    private Double borrowAmount;
    /**
     * 逾期罚息金额
     */
    private Double overdueAmount;
    /**
     * 免罚息金额
     */
    private Double noOverdueAmount;
    /**
     * 实际逾期罚息金额
     */
    private Double realOverdueAmount;
    /**
     * 优惠券优惠金额
     */
    private Double couponAmount;
    /**
     * 交易时间
     */
    private Date tradeTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 终端类型：0系统后台 1Android 2ios 3WAP 4外部渠道
     */
    private Integer terminalType;
    /**
     * 湛江委律师函手续费(单期总金额3%)
     */
    private Double zjwAmount;

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

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Long getRepayId() {
        return repayId;
    }

    public void setRepayId(Long repayId) {
        this.repayId = repayId;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getOverdueDay() {
        return overdueDay;
    }

    public void setOverdueDay(Integer overdueDay) {
        this.overdueDay = overdueDay;
    }

    public Integer getXudaiTimes() {
        return xudaiTimes;
    }

    public void setXudaiTimes(Integer xudaiTimes) {
        this.xudaiTimes = xudaiTimes;
    }

    public Double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Double getXudaiAmount() {
        return xudaiAmount;
    }

    public void setXudaiAmount(Double xudaiAmount) {
        this.xudaiAmount = xudaiAmount;
    }

    public Double getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(Double borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public Double getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(Double overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public Double getNoOverdueAmount() {
        return noOverdueAmount;
    }

    public void setNoOverdueAmount(Double noOverdueAmount) {
        this.noOverdueAmount = noOverdueAmount;
    }

    public Double getRealOverdueAmount() {
        return realOverdueAmount;
    }

    public void setRealOverdueAmount(Double realOverdueAmount) {
        this.realOverdueAmount = realOverdueAmount;
    }

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
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

    /**
     * @return 获取 terminalType属性值
     */
    public Integer getTerminalType() {
        return terminalType;
    }

    /**
     * @param terminalType 设置 terminalType 属性值为参数值 terminalType
     */
    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
    }

    public Double getZjwAmount() {
        return zjwAmount;
    }

    public void setZjwAmount(Double zjwAmount) {
        this.zjwAmount = zjwAmount;
    }

    @Override
    public String toString() {
        return "BwPaymentDetail [id=" + id + ", orderId=" + orderId + ", borrowerId=" + borrowerId + ", cardNo=" + cardNo + ", bankCode=" + bankCode + ", repayId=" + repayId + ", tradeType="
                + tradeType + ", payChannel=" + payChannel + ", payStatus=" + payStatus + ", overdueDay=" + overdueDay + ", xudaiTimes=" + xudaiTimes + ", tradeAmount=" + tradeAmount
                + ", xudaiAmount=" + xudaiAmount + ", borrowAmount=" + borrowAmount + ", overdueAmount=" + overdueAmount + ", noOverdueAmount=" + noOverdueAmount + ", realOverdueAmount="
                + realOverdueAmount + ", couponAmount=" + couponAmount + ", tradeTime=" + tradeTime + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
    }
}
