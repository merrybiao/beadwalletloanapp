/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.dto;

/**
 * 
 * 
 * Module:
 * 
 * RepaymentBatch.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class RepaymentBatchDetail {

	// 批次
	private Integer number;
	// 金额
	private String amount;
	// 交易时间
	private String tradeTime;
	// 剩余金额
	private String residualAmount;

	/**
	 * @return 获取 number属性值
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * @param number 设置 number 属性值为参数值 number
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * @return 获取 amount属性值
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount 设置 amount 属性值为参数值 amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return 获取 tradeTime属性值
	 */
	public String getTradeTime() {
		return tradeTime;
	}

	/**
	 * @param tradeTime 设置 tradeTime 属性值为参数值 tradeTime
	 */
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	/**
	 * @return 获取 residualAmount属性值
	 */
	public String getResidualAmount() {
		return residualAmount;
	}

	/**
	 * @param residualAmount 设置 residualAmount 属性值为参数值 residualAmount
	 */
	public void setResidualAmount(String residualAmount) {
		this.residualAmount = residualAmount;
	}

}
