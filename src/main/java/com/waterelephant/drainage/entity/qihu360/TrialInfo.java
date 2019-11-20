/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.entity.qihu360;

/**
 * 
 * 
 * Module:试算接口
 * 
 * TrialInfo.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class TrialInfo {
	private String order_no;// String 奇虎360订单编号
	private String loan_amount;// String 用户选择的借款金额，若金额固定则无法选择
	private Integer loan_term;// Int 用户选择的借款期限，若期限固定则无法选择

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
