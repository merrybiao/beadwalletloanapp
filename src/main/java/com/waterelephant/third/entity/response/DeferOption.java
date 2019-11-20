/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.third.entity.response;

/**
 * 
 * 
 * Module:
 * 
 * DeferOption.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class DeferOption {
	private int deferDay;// 延期期限
	private Double afterDeferAmount;// 延期一定期限后应还总金额
	private Long deferDueTime;// 延期后还款时间
	private Double manageFee;// 延期期间的管理费
	private Double interest;// 延期期间利息
	private Double otherFee;// 其它费用

	public int getDeferDay() {
		return deferDay;
	}

	public void setDeferDay(int deferDay) {
		this.deferDay = deferDay;
	}

	public Double getAfterDeferAmount() {
		return afterDeferAmount;
	}

	public void setAfterDeferAmount(Double afterDeferAmount) {
		this.afterDeferAmount = afterDeferAmount;
	}

	public Long getDeferDueTime() {
		return deferDueTime;
	}

	public void setDeferDueTime(Long deferDueTime) {
		this.deferDueTime = deferDueTime;
	}

	public Double getManageFee() {
		return manageFee;
	}

	public void setManageFee(Double manageFee) {
		this.manageFee = manageFee;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}

}
