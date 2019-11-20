package com.waterelephant.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 调用支付接口、回调、查询日志记录
 */
@Table(name = "bw_pay_log")
public class BwPayLog implements Serializable {
    private static final long serialVersionUID = 1588489094678366701L;
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
     * 交易流水号，商户订单号
     */
    private String tradeNo;
    /**
     * 日志类型（1.调用接口 2.回调 3.查询）
     */
    private Integer logType;
    /**
     * 支付类型(1.还款 2.展期)
     */
    private Integer payType;
    /**
     * 支付通道（1.宝付，2.连连）
     */
    private Integer payChannel;
    /**
     * 交易状态（1.成功 2.处理中 3.失败）
     */
    private Integer tradeStatus;
    /**
     * 返回的code
     */
    private String tradeCode;
    /**
     * 交易金额（单位：元）
     */
    private Double tradeMoney;
    /**
     * 终端类型：0系统后台 1Android 2ios 3WAP 4外部渠道
     */
    private Integer terminalType;
    /**
     * 支付接口请求数据
     */
    private String requestData;
    /**
     * 支付接口返回的数据
     */
    private String resultData;
    /**
     * 交易时间
     */
    private Date tradeTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

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

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public Double getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(Double tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public Integer getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
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
}
