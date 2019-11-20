/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.entity.qihu360;

/**
 * 
 * 
 * Module: 推送用户确认收款信息
 * 
 * PushApprovalConfirmInfo.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class PushApprovalConfirmInfo {
	private String order_no;// 奇虎360订单编号
	private String loan_amount;// 用户确认的借款金额
	private Integer loan_term;// 用户确认的借款期限

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getLoan_amount() {
		return loan_amount;
	}

	public void setLoan_amount(String loan_amount) {
		this.loan_amount = loan_amount;
	}

	public Integer getLoan_term() {
		return loan_term;
	}

	public void setLoan_term(Integer loan_term) {
		this.loan_term = loan_term;
	}
}
