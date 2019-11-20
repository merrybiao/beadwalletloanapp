/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.third.entity.response;

import java.util.Date;

/**
 * 
 * 
 * Module:
 * 
 * RepaymentPlan.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class RepaymentPlan {
	private int periodNo;// 期数
	private Date dueTime;// 到期时间
	private double amount;// 还款总金额
	private int billStatus;// 账单状态(1未到期；2已还款；3逾期)
	private int payType;// 支持的还款方式类型(1=主动还款；2=跳转H5；4=银行代扣；5=同时支持主动还款和银行代扣)

	public int getPeriodNo() {
		return periodNo;
	}

	public void setPeriodNo(int periodNo) {
		this.periodNo = periodNo;
	}

	public Date getDueTime() {
		return dueTime;
	}

	public void setDueTime(Date dueTime) {
		this.dueTime = dueTime;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(int billStatus) {
		this.billStatus = billStatus;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}
}
