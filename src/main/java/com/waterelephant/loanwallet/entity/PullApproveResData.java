package com.waterelephant.loanwallet.entity;

public class PullApproveResData {
	private String userId;
	private String orderNo;
	private String conclusion;
	private String amount;
	private String poundage;
	private String rate;
	private String overdueFine;
	private String poundageType;
	private String isEditAmount;
	private String isEditLoanDate;
	private String loanDate;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getConclusion() {
		return conclusion;
	}
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPoundage() {
		return poundage;
	}
	public void setPoundage(String poundage) {
		this.poundage = poundage;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getOverdueFine() {
		return overdueFine;
	}
	public void setOverdueFine(String overdueFine) {
		this.overdueFine = overdueFine;
	}
	public String getPoundageType() {
		return poundageType;
	}
	public void setPoundageType(String poundageType) {
		this.poundageType = poundageType;
	}
	public String getIsEditAmount() {
		return isEditAmount;
	}
	public void setIsEditAmount(String isEditAmount) {
		this.isEditAmount = isEditAmount;
	}
	public String getIsEditLoanDate() {
		return isEditLoanDate;
	}
	public void setIsEditLoanDate(String isEditLoanDate) {
		this.isEditLoanDate = isEditLoanDate;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
}