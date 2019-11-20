package com.waterelephant.dto;

import java.util.Date;

public class AppPayDto {

	private Long orderId; // 工单编号
	private String type; // 1还款 2 展期
	private String appRequest; // 1 安卓 2 IOS
	private String batchMoney; // 支付金额
	private String tradeNo;// 交易号
	private String payType; // 5 支付宝 6微信
	private Date payDate; // 交易时间

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAppRequest() {
		return appRequest;
	}

	public void setAppRequest(String appRequest) {
		this.appRequest = appRequest;
	}

	public String getBatchMoney() {
		return batchMoney;
	}

	public void setBatchMoney(String batchMoney) {
		this.batchMoney = batchMoney;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

}
