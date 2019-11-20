package com.waterelephant.dto;

import java.io.Serializable;

/**
 * 根据借款金额计算产品费用明细
 */
public class ProductFeeDto implements Serializable {

	private static final long serialVersionUID = 4657331215365894657L;
	/**
	 * 贷前信用管理费
	 */
	private Double preServiceFee;

	/**
	 * 贷后信用管理费
	 */
	private Double afterLoanFee;

	/**
	 * 信息服务费
	 */
	private Double capitalUseFee;

	/**
	 * 资金使用费
	 */
	private Double fundUtilizationFee;

	/**
	 * 借款工本费
	 */
	private Double loanAmount;

	/**
	 * 到账金额
	 */
	private Double arrivalAmount;

	/**
	 * 还款利息，分期用
	 */
	private Double interest;

	/**
	 * 逾期费
	 */
	private Double overdueFee;

	public Double getPreServiceFee() {
		return preServiceFee;
	}

	public void setPreServiceFee(Double preServiceFee) {
		this.preServiceFee = preServiceFee;
	}

	public Double getAfterLoanFee() {
		return afterLoanFee;
	}

	public void setAfterLoanFee(Double afterLoanFee) {
		this.afterLoanFee = afterLoanFee;
	}

	public Double getCapitalUseFee() {
		return capitalUseFee;
	}

	public void setCapitalUseFee(Double capitalUseFee) {
		this.capitalUseFee = capitalUseFee;
	}

	public Double getFundUtilizationFee() {
		return fundUtilizationFee;
	}

	public void setFundUtilizationFee(Double fundUtilizationFee) {
		this.fundUtilizationFee = fundUtilizationFee;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getArrivalAmount() {
		return arrivalAmount;
	}

	public void setArrivalAmount(Double arrivalAmount) {
		this.arrivalAmount = arrivalAmount;
	}

	public Double getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(Double overdueFee) {
		this.overdueFee = overdueFee;
	}
}