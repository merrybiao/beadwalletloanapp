package com.waterelephant.jiedianqian.entity;

import java.math.BigDecimal;

public class UserCheckResp {
	private String if_can_loan; // 是否可以借款：0-否；1-是

	private String user_type; // 0-借点钱老用户 (定义：由借点钱渠道导流到合作方的用户，非第一次申请) 1-新用户 （定义：由借点钱渠道导流到合作方的用户，第一次申请） 2-合作方老用户
								// （定义：该用户为机构原有用户，且非借点钱渠道注册）
	private String can_loan_time;

	private BigDecimal amount; // 可贷额度，单位元

	public String getIf_can_loan() {
		return if_can_loan;
	}

	public void setIf_can_loan(String if_can_loan) {
		this.if_can_loan = if_can_loan;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getCan_loan_time() {
		return can_loan_time;
	}

	public void setCan_loan_time(String can_loan_time) {
		this.can_loan_time = can_loan_time;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
