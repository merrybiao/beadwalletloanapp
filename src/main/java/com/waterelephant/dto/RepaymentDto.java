/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.dto;

import java.util.Date;

/**
 * 还款实体
 * 
 * Module:
 * 
 * RepaymentDto.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class RepaymentDto {

	// 借款人Id
	private Long borrowerId;

	// 工单Id
	private Long orderId;

	private String tradeNo;

	// 还款类型（1、还款，2、展期）
	private Integer type;

	// 终端类型（1、Android，2、ios 3、h5）
	private Integer terminalType;

	// 支付方式，1.主动支付，2.贷后代扣，3.自动代扣，4.对公转账
	private Integer payWay;

	// 是否使用优惠券
	private Boolean useCoupon;

	// 支付金额
	private Double amount;

	// 支付通道(1.宝付,2.连连,5支付宝,6.微信,7.口袋)
	private Integer payChannel;

	// 交易时间
	private Date tradeTime;
	
	// 交易类型 1:划拨 2:转账
	private Integer tradeType;
	
	// 交易返回code
	private String tradeCode;

	/**
	 * @return 获取 borrowerId属性值
	 */
	public Long getBorrowerId() {
		return borrowerId;
	}

	/**
	 * @param borrowerId
	 *            设置 borrowerId 属性值为参数值 borrowerId
	 */
	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
	}

	/**
	 * @return 获取 orderId属性值
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            设置 orderId 属性值为参数值 orderId
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	/**
	 * @return 获取 type属性值
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type
	 *            设置 type 属性值为参数值 type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return 获取 payChannel属性值
	 */
	public Integer getPayChannel() {
		return payChannel;
	}

	/**
	 * @param payChannel
	 *            设置 payChannel 属性值为参数值 payChannel
	 */
	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}

	/**
	 * @return 获取 terminalType属性值
	 */
	public Integer getTerminalType() {
		return terminalType;
	}

	/**
	 * @param terminalType
	 *            设置 terminalType 属性值为参数值 terminalType
	 */
	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	/**
	 * @return 获取 useCoupon属性值
	 */
	public Boolean getUseCoupon() {
		return useCoupon;
	}

	/**
	 * @param useCoupon
	 *            设置 useCoupon 属性值为参数值 useCoupon
	 */
	public void setUseCoupon(Boolean useCoupon) {
		this.useCoupon = useCoupon;
	}

	/**
	 * @return 获取 amount属性值
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            设置 amount 属性值为参数值 amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return 获取 tradeTime属性值
	 */
	public Date getTradeTime() {
		return tradeTime;
	}

	/**
	 * @param tradeTime
	 *            设置 tradeTime 属性值为参数值 tradeTime
	 */
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

}
