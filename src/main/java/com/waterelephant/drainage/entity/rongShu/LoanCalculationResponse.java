package com.waterelephant.drainage.entity.rongShu;

/**
 * 榕树 - 5.2 贷款试算器接口响应体（code0087502）
 * 
 * 
 * Module:
 * 
 * LoanCalculationResponse.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class LoanCalculationResponse {
	private int isCanLoan; // 是否可借款
	private double refundAmount; // 还款总金额
	private double actualAmount; // 实际到账金额

	public int getIsCanLoan() {
		return isCanLoan;
	}

	public void setIsCanLoan(int isCanLoan) {
		this.isCanLoan = isCanLoan;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(double actualAmount) {
		this.actualAmount = actualAmount;
	}

}
