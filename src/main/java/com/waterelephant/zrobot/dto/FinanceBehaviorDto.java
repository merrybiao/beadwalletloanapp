package com.waterelephant.zrobot.dto;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class FinanceBehaviorDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1998362926613128679L;
	/**
	 * 借记卡数量
	 */
	@JSONField(name="debit_card_num")
	private String debitCardNum;
	/**
	 * 贷记卡数量
	 */
	@JSONField(name ="credit_card_num")
	private String creditCardNum;
	/**
	 * 近一个月发生过逾期
	 */
	@JSONField(name="overdue_status_1m")
	private String overdueStatus1m;
	/**
	 * 近一个月还款总金额
	 */
	@JSONField(name="payment_sum_1m")
	private String paymentSum1m;
	/**
	 * 近一个月放款机构数量
	 */
	@JSONField(name="debt_lender_num_1m")
	private String debtLenderNum1m;
	/**
	 * 近一个月放款金额
	 */
	@JSONField(name="loan_sum_1m")
	private String loanSum1m;
	/**
	 * 近一个月欠款机构数量
	 */
	@JSONField(name="loan_lender_num_1m")
	private String loanLenderNum1m;
	/**
	 * 近三个月发生过逾期
	 */
	@JSONField(name="overdue_status_3m")
	private String overdueStatus3m;
	/**
	 * 近三个月还款总金额
	 */
	@JSONField(name="payment_sum_3m")
	private String paymentSum3m;
	/**
	 * 近三个月放款机构数量
	 */
	@JSONField(name="debt_lender_num_3m")
	private String debtLenderNum3m;
	/**
	 * 近三个月放款金额
	 */
	@JSONField(name="loan_sum_3m")
	private String loanSum3m;
	/**
	 * 近三个月欠款机构数量
	 */
	@JSONField(name="loan_lender_num_3m")
	private String loanLenderNum3m;
	/**
	 * 近六个月发生过逾期
	 */
	@JSONField(name="overdue_status_6m")
	private String overdueStatus6m;
	/**
	 * 近六个月还款总金额
	 */
	@JSONField(name="payment_sum_6m")
	private String paymentSum6m;
	/**
	 * 近六个月放款机构数量
	 */
	@JSONField(name="debt_lender_num_6m")
	private String debtLenderNum6m;
	/**
	 * 近六个月放款金额
	 */
	@JSONField(name="loan_sum_6m")
	private String loanSum6m;
	/**
	 * 近六个月欠款机构数量
	 */
	@JSONField(name="loan_lender_num_6m")
	private String loanLenderNum6m;
	/**
	 * 近十二个月发生过逾期
	 */
	@JSONField(name="overdue_status_12m")
	private String overdueStatus12m;
	/**
	 * 近十二个月还款总金额
	 */
	@JSONField(name="payment_sum_12m")
	private String paymentSum12m;
	/**
	 * 近十二个月放款机构数量
	 */
	@JSONField(name="debtlender_num_12m")
	private String debtLenderNum12m;
	/**
	 * 近十二个月放款金额
	 */
	@JSONField(name="loan_sum_12m")
	private String loanSum12m;
	/**
	 * 近十二个月欠款机构数量
	 */
	@JSONField(name="loan_lender_num_12m")
	private String loanLenderNum12m;
	public String getDebitCardNum() {
		return debitCardNum;
	}
	public void setDebitCardNum(String debitCardNum) {
		this.debitCardNum = debitCardNum;
	}
	public String getCreditCardNum() {
		return creditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	public String getOverdueStatus1m() {
		return overdueStatus1m;
	}
	public void setOverdueStatus1m(String overdueStatus1m) {
		this.overdueStatus1m = overdueStatus1m;
	}
	public String getPaymentSum1m() {
		return paymentSum1m;
	}
	public void setPaymentSum1m(String paymentSum1m) {
		this.paymentSum1m = paymentSum1m;
	}
	public String getDebtLenderNum1m() {
		return debtLenderNum1m;
	}
	public void setDebtLenderNum1m(String debtLenderNum1m) {
		this.debtLenderNum1m = debtLenderNum1m;
	}
	public String getLoanSum1m() {
		return loanSum1m;
	}
	public void setLoanSum1m(String loanSum1m) {
		this.loanSum1m = loanSum1m;
	}
	public String getLoanLenderNum1m() {
		return loanLenderNum1m;
	}
	public void setLoanLenderNum1m(String loanLenderNum1m) {
		this.loanLenderNum1m = loanLenderNum1m;
	}
	public String getOverdueStatus3m() {
		return overdueStatus3m;
	}
	public void setOverdueStatus3m(String overdueStatus3m) {
		this.overdueStatus3m = overdueStatus3m;
	}
	public String getPaymentSum3m() {
		return paymentSum3m;
	}
	public void setPaymentSum3m(String paymentSum3m) {
		this.paymentSum3m = paymentSum3m;
	}
	public String getDebtLenderNum3m() {
		return debtLenderNum3m;
	}
	public void setDebtLenderNum3m(String debtLenderNum3m) {
		this.debtLenderNum3m = debtLenderNum3m;
	}
	public String getLoanSum3m() {
		return loanSum3m;
	}
	public void setLoanSum3m(String loanSum3m) {
		this.loanSum3m = loanSum3m;
	}
	public String getLoanLenderNum3m() {
		return loanLenderNum3m;
	}
	public void setLoanLenderNum3m(String loanLenderNum3m) {
		this.loanLenderNum3m = loanLenderNum3m;
	}
	public String getOverdueStatus6m() {
		return overdueStatus6m;
	}
	public void setOverdueStatus6m(String overdueStatus6m) {
		this.overdueStatus6m = overdueStatus6m;
	}
	public String getPaymentSum6m() {
		return paymentSum6m;
	}
	public void setPaymentSum6m(String paymentSum6m) {
		this.paymentSum6m = paymentSum6m;
	}
	public String getDebtLenderNum6m() {
		return debtLenderNum6m;
	}
	public void setDebtLenderNum6m(String debtLenderNum6m) {
		this.debtLenderNum6m = debtLenderNum6m;
	}
	public String getLoanSum6m() {
		return loanSum6m;
	}
	public void setLoanSum6m(String loanSum6m) {
		this.loanSum6m = loanSum6m;
	}
	public String getLoanLenderNum6m() {
		return loanLenderNum6m;
	}
	public void setLoanLenderNum6m(String loanLenderNum6m) {
		this.loanLenderNum6m = loanLenderNum6m;
	}
	public String getOverdueStatus12m() {
		return overdueStatus12m;
	}
	public void setOverdueStatus12m(String overdueStatus12m) {
		this.overdueStatus12m = overdueStatus12m;
	}
	public String getPaymentSum12m() {
		return paymentSum12m;
	}
	public void setPaymentSum12m(String paymentSum12m) {
		this.paymentSum12m = paymentSum12m;
	}
	public String getDebtLenderNum12m() {
		return debtLenderNum12m;
	}
	public void setDebtLenderNum12m(String debtLenderNum12m) {
		this.debtLenderNum12m = debtLenderNum12m;
	}
	public String getLoanSum12m() {
		return loanSum12m;
	}
	public void setLoanSum12m(String loanSum12m) {
		this.loanSum12m = loanSum12m;
	}
	public String getLoanLenderNum12m() {
		return loanLenderNum12m;
	}
	public void setLoanLenderNum12m(String loanLenderNum12m) {
		this.loanLenderNum12m = loanLenderNum12m;
	}
}
