package com.waterelephant.dto;

import java.io.Serializable;

/**
 * 计算支付还款或展期金额返回DTO
 * 
 * @author maoenqi
 */
public class PaymentRespDto implements Serializable {
	private static final long serialVersionUID = -998933268543393159L;
	/**
	 * 借款本金
	 */
	private Double borrowAmount;
	/**
	 * 到账金额
	 */
	private Double arrivelAmount;
	/**
	 * 已还款金额
	 */
	private Double alreadyTotalBatchMoney;
	/**
	 * 还款总额，真实还款金额(包含湛江委金额)+(逾期金额-免罚息金额)
	 */
	private Double totalRepaymentAmount;
	/**
	 * 待还金额，总共剩余还款金额，真实还款金额(包含湛江委金额)+(逾期金额-免罚息金额)-已还金额
	 */
	private Double totalLeftRepaymentAmount;
	/**
	 * 总还款金额，包含湛江委金额，多期则所有还款计划相加，不算逾期金额
	 */
	private Double realityRepayMoney;
	/**
	 * 18%手续费，综合费
	 */
	private Double paymentFee;
	/**
	 * 是否逾期
	 */
	private Boolean overdue;
	/**
	 * 实际逾期罚息金额
	 */
	private Double realOverdueAmount;

	public Double getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(Double borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	public Double getArrivelAmount() {
		return arrivelAmount;
	}

	public void setArrivelAmount(Double arrivelAmount) {
		this.arrivelAmount = arrivelAmount;
	}

	public Double getAlreadyTotalBatchMoney() {
		return alreadyTotalBatchMoney;
	}

	public void setAlreadyTotalBatchMoney(Double alreadyTotalBatchMoney) {
		this.alreadyTotalBatchMoney = alreadyTotalBatchMoney;
	}

	public Double getTotalRepaymentAmount() {
		return totalRepaymentAmount;
	}

	public void setTotalRepaymentAmount(Double totalRepaymentAmount) {
		this.totalRepaymentAmount = totalRepaymentAmount;
	}

	public Double getTotalLeftRepaymentAmount() {
		return totalLeftRepaymentAmount;
	}

	public void setTotalLeftRepaymentAmount(Double totalLeftRepaymentAmount) {
		this.totalLeftRepaymentAmount = totalLeftRepaymentAmount;
	}

	public Double getRealityRepayMoney() {
		return realityRepayMoney;
	}

	public void setRealityRepayMoney(Double realityRepayMoney) {
		this.realityRepayMoney = realityRepayMoney;
	}

	public Double getPaymentFee() {
		return paymentFee;
	}

	public void setPaymentFee(Double paymentFee) {
		this.paymentFee = paymentFee;
	}

	public Boolean getOverdue() {
		return overdue;
	}

	public void setOverdue(Boolean overdue) {
		this.overdue = overdue;
	}

	public Double getRealOverdueAmount() {
		return realOverdueAmount;
	}

	public void setRealOverdueAmount(Double realOverdueAmount) {
		this.realOverdueAmount = realOverdueAmount;
	}

	@Override
	public String toString() {
		return "PaymentRespDto{" +
				"borrowAmount=" + borrowAmount +
				", arrivelAmount=" + arrivelAmount +
				", alreadyTotalBatchMoney=" + alreadyTotalBatchMoney +
				", totalRepaymentAmount=" + totalRepaymentAmount +
				", totalLeftRepaymentAmount=" + totalLeftRepaymentAmount +
				", realityRepayMoney=" + realityRepayMoney +
				", paymentFee=" + paymentFee +
				", overdue=" + overdue +
				", realOverdueAmount=" + realOverdueAmount +
				'}';
	}
}