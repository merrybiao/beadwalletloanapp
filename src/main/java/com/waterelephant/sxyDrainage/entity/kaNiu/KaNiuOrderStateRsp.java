///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.kaNiu;
//
//import java.util.List;
//
///**
// * Module: KaNiuOrderStateRsp.java
// * 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class KaNiuOrderStateRsp {
//
//	private String productType = "1";
//	private String loanNo;
//	private String ssjLoanNo;
//	private Double applyAmount = 0D;
//	private Double loanAmount = 0D;
//	private Double creditAmount = 0D;
//	private Double contractAmount = 0D;
//	private Integer term = 4;
//	private String termUnit = "0";
//	private String loanStatus;
//	private String approveTime = "";
//	private String applyTime = "";
//	private String lendingTime = "";
//	private Double repaymentAmount = 0D;
//	private Double currentRepaymentAmount = 0D;
//	private String currentDueTime = "";
//	private String rate = "";
//	private Double poundage = 0D;
//	private Integer overdueDays;
//	private Double demurrage = 0D;
//	private Double delayFee = 0D;
//	private String returnReason;
//	private String rejectReason;
//	private String channel;
//	private String repayUrl;
//	private List<KaNiuPlanVo> planlist;
//
//	public String getApplyTime() {
//		return applyTime;
//	}
//
//	public void setApplyTime(String applyTime) {
//		this.applyTime = applyTime;
//	}
//
//	public String getProductType() {
//		return productType;
//	}
//
//	public void setProductType(String productType) {
//		this.productType = productType;
//	}
//
//	public String getLoanNo() {
//		return loanNo;
//	}
//
//	public void setLoanNo(String loanNo) {
//		this.loanNo = loanNo;
//	}
//
//	public String getSsjLoanNo() {
//		return ssjLoanNo;
//	}
//
//	public void setSsjLoanNo(String ssjLoanNo) {
//		this.ssjLoanNo = ssjLoanNo;
//	}
//
//	public Double getApplyAmount() {
//		return applyAmount;
//	}
//
//	public void setApplyAmount(Double applyAmount) {
//		this.applyAmount = applyAmount;
//	}
//
//	public Double getLoanAmount() {
//		return loanAmount;
//	}
//
//	public void setLoanAmount(Double loanAmount) {
//		this.loanAmount = loanAmount;
//	}
//
//	public Double getCreditAmount() {
//		return creditAmount;
//	}
//
//	public void setCreditAmount(Double creditAmount) {
//		this.creditAmount = creditAmount;
//	}
//
//	public Double getContractAmount() {
//		return contractAmount;
//	}
//
//	public void setContractAmount(Double contractAmount) {
//		this.contractAmount = contractAmount;
//	}
//
//	public Integer getTerm() {
//		return term;
//	}
//
//	public void setTerm(Integer term) {
//		this.term = term;
//	}
//
//	public String getTermUnit() {
//		return termUnit;
//	}
//
//	public void setTermUnit(String termUnit) {
//		this.termUnit = termUnit;
//	}
//
//	public String getLoanStatus() {
//		return loanStatus;
//	}
//
//	public void setLoanStatus(String loanStatus) {
//		this.loanStatus = loanStatus;
//	}
//
//	public String getApproveTime() {
//		return approveTime;
//	}
//
//	public void setApproveTime(String approveTime) {
//		this.approveTime = approveTime;
//	}
//
//	public String getLendingTime() {
//		return lendingTime;
//	}
//
//	public void setLendingTime(String lendingTime) {
//		this.lendingTime = lendingTime;
//	}
//
//	public Double getRepaymentAmount() {
//		return repaymentAmount;
//	}
//
//	public void setRepaymentAmount(Double repaymentAmount) {
//		this.repaymentAmount = repaymentAmount;
//	}
//
//	public Double getCurrentRepaymentAmount() {
//		return currentRepaymentAmount;
//	}
//
//	public void setCurrentRepaymentAmount(Double currentRepaymentAmount) {
//		this.currentRepaymentAmount = currentRepaymentAmount;
//	}
//
//	public String getCurrentDueTime() {
//		return currentDueTime;
//	}
//
//	public void setCurrentDueTime(String currentDueTime) {
//		this.currentDueTime = currentDueTime;
//	}
//
//	public String getRate() {
//		return rate;
//	}
//
//	public void setRate(String rate) {
//		this.rate = rate;
//	}
//
//	public Double getPoundage() {
//		return poundage;
//	}
//
//	public void setPoundage(Double poundage) {
//		this.poundage = poundage;
//	}
//
//	public Integer getOverdueDays() {
//		return overdueDays;
//	}
//
//	public void setOverdueDays(Integer overdueDays) {
//		this.overdueDays = overdueDays;
//	}
//
//	public Double getDemurrage() {
//		return demurrage;
//	}
//
//	public void setDemurrage(Double demurrage) {
//		this.demurrage = demurrage;
//	}
//
//	public Double getDelayFee() {
//		return delayFee;
//	}
//
//	public void setDelayFee(Double delayFee) {
//		this.delayFee = delayFee;
//	}
//
//	public String getReturnReason() {
//		return returnReason;
//	}
//
//	public void setReturnReason(String returnReason) {
//		this.returnReason = returnReason;
//	}
//
//	public String getRejectReason() {
//		return rejectReason;
//	}
//
//	public void setRejectReason(String rejectReason) {
//		this.rejectReason = rejectReason;
//	}
//
//	public String getChannel() {
//		return channel;
//	}
//
//	public void setChannel(String channel) {
//		this.channel = channel;
//	}
//
//	public String getRepayUrl() {
//		return repayUrl;
//	}
//
//	public void setRepayUrl(String repayUrl) {
//		this.repayUrl = repayUrl;
//	}
//
//	public List<KaNiuPlanVo> getPlanlist() {
//		return planlist;
//	}
//
//	public void setPlanlist(List<KaNiuPlanVo> planlist) {
//		this.planlist = planlist;
//	}
//
//
//}
