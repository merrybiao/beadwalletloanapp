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
 * DeferAmountOption.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class DeferAmountOption {
	private Double deferAmount; // 展期应还款金额
	private Double manageFee;// 管理费用
	private Double interest;// 利息
	private Double otherFee;// 其它费用
	private Double overdueFee;// 逾期费用

	public Double getDeferAmount() {
		return deferAmount;
	}

	public void setDeferAmount(Double deferAmount) {
		this.deferAmount = deferAmount;
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

	public Double getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(Double overdueFee) {
		this.overdueFee = overdueFee;
	}

}
