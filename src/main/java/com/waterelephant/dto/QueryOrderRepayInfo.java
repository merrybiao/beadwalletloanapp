package com.waterelephant.dto;

import java.io.Serializable;

/**
 * 微信审核界面显示
 * 
 * @author wrh
 *
 */
@SuppressWarnings("serial")
public class QueryOrderRepayInfo implements Serializable {
	/** 借款金额 */
	private Double borrowAmount;
	/** 到账金额 */
	private Double receivedAmount;
	/** 还款时间 */
	private String repayTime;
	/** 银行卡 */
	private String cardString;
	/** 快速审核费 */
	private Double pFastReviewCost;
	/** 平台使用费 */
	private Double pPlatformUseCost;
	/** 账户管理费 */
	private Double pNumberManageCost;
	/** 代收通道费 */
	private Double pCollectionPassagewayCost;
	/** 资金使用费 */
	private Double pCapitalUseCost;
	/** 综合费用 */
	private Double loanAmount;

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(Double borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	public Double getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(Double receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public String getCardString() {
		return cardString;
	}

	public void setCardString(String cardString) {
		this.cardString = cardString;
	}

	public Double getpFastReviewCost() {
		return pFastReviewCost;
	}

	public void setpFastReviewCost(Double pFastReviewCost) {
		this.pFastReviewCost = pFastReviewCost;
	}

	public Double getpPlatformUseCost() {
		return pPlatformUseCost;
	}

	public void setpPlatformUseCost(Double pPlatformUseCost) {
		this.pPlatformUseCost = pPlatformUseCost;
	}

	public Double getpNumberManageCost() {
		return pNumberManageCost;
	}

	public void setpNumberManageCost(Double pNumberManageCost) {
		this.pNumberManageCost = pNumberManageCost;
	}

	public Double getpCollectionPassagewayCost() {
		return pCollectionPassagewayCost;
	}

	public void setpCollectionPassagewayCost(Double pCollectionPassagewayCost) {
		this.pCollectionPassagewayCost = pCollectionPassagewayCost;
	}

	public Double getpCapitalUseCost() {
		return pCapitalUseCost;
	}

	public void setpCapitalUseCost(Double pCapitalUseCost) {
		this.pCapitalUseCost = pCapitalUseCost;
	}

}