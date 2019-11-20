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
public class RepaymentBatch {

	// 分批已还款总额
	private Double alreadyTotalBatchMoney;
	// 分批总期数
	private int totalNumber;
	// 分批当前期数
	private int currentNumber;

	/**
	 * @return 获取 alreadyTotalBatchMoney属性值
	 */
	public Double getAlreadyTotalBatchMoney() {
		return alreadyTotalBatchMoney;
	}

	/**
	 * @param alreadyTotalBatchMoney 设置 alreadyTotalBatchMoney 属性值为参数值 alreadyTotalBatchMoney
	 */
	public void setAlreadyTotalBatchMoney(Double alreadyTotalBatchMoney) {
		this.alreadyTotalBatchMoney = alreadyTotalBatchMoney;
	}

	/**
	 * @return 获取 totalNumber属性值
	 */
	public int getTotalNumber() {
		return totalNumber;
	}

	/**
	 * @param totalNumber 设置 totalNumber 属性值为参数值 totalNumber
	 */
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	/**
	 * @return 获取 currentNumber属性值
	 */
	public int getCurrentNumber() {
		return currentNumber;
	}

	/**
	 * @param currentNumber 设置 currentNumber 属性值为参数值 currentNumber
	 */
	public void setCurrentNumber(int currentNumber) {
		this.currentNumber = currentNumber;
	}

}
