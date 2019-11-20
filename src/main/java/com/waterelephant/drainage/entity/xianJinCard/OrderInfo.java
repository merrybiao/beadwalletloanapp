package com.waterelephant.drainage.entity.xianJinCard;

/**
 * Module: OrderInfo.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class OrderInfo {
	private String order_sn;// 订单编号
	private String platform;// 订单平台：1：app; 2：H5
	private String loan_amount;// 借款金额 单位（分）
	private String loan_term;// 借款期限
	private String term_type;// 期限单位：1: 按天; 2: 按月; 3: 按年;

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getLoan_amount() {
		return loan_amount;
	}

	public void setLoan_amount(String loan_amount) {
		this.loan_amount = loan_amount;
	}

	public String getLoan_term() {
		return loan_term;
	}

	public void setLoan_term(String loan_term) {
		this.loan_term = loan_term;
	}

	public String getTerm_type() {
		return term_type;
	}

	public void setTerm_type(String term_type) {
		this.term_type = term_type;
	}

}
