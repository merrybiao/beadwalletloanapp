/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.entity.qihu360;

import java.util.Date;

/**
 * 
 * 
 * Module: 还款计划
 * 
 * RepaymentPlan.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class RepaymentPlan {

	private String period_no;// string 还款计划编号
	private Integer bill_status;// Int账单状态：1=未到期待还款2=已还款3=逾期
	private Date due_time;// Timestamp 账单到期时间
	private Date can_repay_time;// Timestamp 当期最早可以还款的时间
	private Integer pay_type;// Int还款方式1=主动还款2=跳转机构 H5 还款4=银行代扣5=主动还款+银行代扣
	private Float amount;// Float 当前所需的还款金额，单位元该金额应该是本金利息加上逾期金额减去已还款金额的结果，逾期金额、已还款金额可能为零
	private Float paid_amount;// Float 已还款金额，单位元无论 bill_status 取值为多少，用户有过还款记录，参数非空
	private Float overdue_fee;// Float 逾期费用，单位元bill_status=1 是，传 0bill_status=3 时，参数非空
	private Date success_time;// Timestamp 还款成功的时间paid_amount 有值时，参数非空显示最后一次还款操作成功的时间
	private String remark;// string 当期还款金额描述bill_status=2 时，参数非空eg：含本金 xxx 元，利息&手续费xxxx 元，逾期 xxx 元

	public String getPeriod_no() {
		return period_no;
	}

	public void setPeriod_no(String period_no) {
		this.period_no = period_no;
	}

	public Integer getBill_status() {
		return bill_status;
	}

	public void setBill_status(Integer bill_status) {
		this.bill_status = bill_status;
	}

	public Date getDue_time() {
		return due_time;
	}

	public void setDue_time(Date due_time) {
		this.due_time = due_time;
	}

	public Date getCan_repay_time() {
		return can_repay_time;
	}

	public void setCan_repay_time(Date can_repay_time) {
		this.can_repay_time = can_repay_time;
	}

	public Integer getPay_type() {
		return pay_type;
	}

	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Float getPaid_amount() {
		return paid_amount;
	}

	public void setPaid_amount(Float paid_amount) {
		this.paid_amount = paid_amount;
	}

	public Float getOverdue_fee() {
		return overdue_fee;
	}

	public void setOverdue_fee(Float overdue_fee) {
		this.overdue_fee = overdue_fee;
	}

	public Date getSuccess_time() {
		return success_time;
	}

	public void setSuccess_time(Date success_time) {
		this.success_time = success_time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
