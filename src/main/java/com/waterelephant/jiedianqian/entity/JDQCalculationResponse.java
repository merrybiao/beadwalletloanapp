package com.waterelephant.jiedianqian.entity;

public class JDQCalculationResponse {
	private String min_amount; // 可贷最小金额，单位元
	private String max_amount; // 可贷最大金额，单位元
	private String multiple; // 可选倍数，比如提现金额数必须是100整数倍，即传100
	private String card_amount; // 到账金额，单位元
	private String[] first_repay_amount; // 数组，预计首次还款金额列表，和下面可选期数loan_terms一一对应，单位元
	private String[] loan_terms; // 数组，可选期数列表
	private String[] loan_days;//数组，可选天数列表

	public String[] getLoan_days() {
		return loan_days;
	}

	public void setLoan_days(String[] loan_days) {
		this.loan_days = loan_days;
	}

	public String getMin_amount() {
		return min_amount;
	}

	public void setMin_amount(String min_amount) {
		this.min_amount = min_amount;
	}

	public String getMax_amount() {
		return max_amount;
	}

	public void setMax_amount(String max_amount) {
		this.max_amount = max_amount;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getCard_amount() {
		return card_amount;
	}

	public void setCard_amount(String card_amount) {
		this.card_amount = card_amount;
	}

	public String[] getFirst_repay_amount() {
		return first_repay_amount;
	}

	public void setFirst_repay_amount(String[] first_repay_amount) {
		this.first_repay_amount = first_repay_amount;
	}

	public String[] getLoan_terms() {
		return loan_terms;
	}

	public void setLoan_terms(String[] loan_terms) {
		this.loan_terms = loan_terms;
	}

}
