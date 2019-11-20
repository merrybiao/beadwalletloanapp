/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.entity.xianJinCard;

/**
 * Module: RepaymentPlan.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class RepaymentPlan {
	private String period_no;// 还款期号
	private int principle;// 本期还款本金; 单位: 分
	private int interest;// 本期还款利息; 单位: 分
	private int service_fee;// 是 本期服务费用; 单位: 分
	private int bill_status;// 本期账单状态; -1: 未出账; 0: 未还款; 1: 已还款
	private int total_amount;// 本期还款总额; 单位: 分
	private int already_paid;// 本期已还金额; 单位: 分
	private int loan_time;// 实际起息时间
	private int due_time;// 最迟还款时间（精确到秒超过该时间就算逾期）
	private int can_pay_time;// 可以还款时间
	private int finish_pay_time;// 实际完成还款时间
	private int overdue_day;// 逾期天数
	private int overdue_fee;// 逾期费用; 单位: 分
	private String period_fee_desc;// 本期费用描述
	private int pay_type;// 还款支付方式; 如: 0.未还款 1. 主动还款 2.系统扣款 3. 支付宝转账 4. 银行转账或其它方式

	public String getPeriod_no() {
		return period_no;
	}

	public void setPeriod_no(String period_no) {
		this.period_no = period_no;
	}

	public int getPrinciple() {
		return principle;
	}

	public void setPrinciple(int principle) {
		this.principle = principle;
	}

	public int getInterest() {
		return interest;
	}

	public void setInterest(int interest) {
		this.interest = interest;
	}

	public int getService_fee() {
		return service_fee;
	}

	public void setService_fee(int service_fee) {
		this.service_fee = service_fee;
	}

	public int getBill_status() {
		return bill_status;
	}

	public void setBill_status(int bill_status) {
		this.bill_status = bill_status;
	}

	public int getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}

	public int getAlready_paid() {
		return already_paid;
	}

	public void setAlready_paid(int already_paid) {
		this.already_paid = already_paid;
	}

	public int getLoan_time() {
		return loan_time;
	}

	public void setLoan_time(int loan_time) {
		this.loan_time = loan_time;
	}

	public int getDue_time() {
		return due_time;
	}

	public void setDue_time(int due_time) {
		this.due_time = due_time;
	}

	public int getCan_pay_time() {
		return can_pay_time;
	}

	public void setCan_pay_time(int can_pay_time) {
		this.can_pay_time = can_pay_time;
	}

	public int getFinish_pay_time() {
		return finish_pay_time;
	}

	public void setFinish_pay_time(int finish_pay_time) {
		this.finish_pay_time = finish_pay_time;
	}

	public int getOverdue_day() {
		return overdue_day;
	}

	public void setOverdue_day(int overdue_day) {
		this.overdue_day = overdue_day;
	}

	public int getOverdue_fee() {
		return overdue_fee;
	}

	public void setOverdue_fee(int overdue_fee) {
		this.overdue_fee = overdue_fee;
	}

	public String getPeriod_fee_desc() {
		return period_fee_desc;
	}

	public void setPeriod_fee_desc(String period_fee_desc) {
		this.period_fee_desc = period_fee_desc;
	}

	public int getPay_type() {
		return pay_type;
	}

	public void setPay_type(int pay_type) {
		this.pay_type = pay_type;
	}

}
