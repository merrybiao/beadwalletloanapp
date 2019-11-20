package com.waterelephant.dto;

/**
 * 工单信息封装
 * @author duxiaoyong
 *
 */
public class BwOrderInfoDto {
	private Long orderId; // 编号
	private Double borrowAmount; // 借款金额
	private Short repayTerm; // 还款期限
	private Integer repayType; // 还款方式 1:先息后本 2:等额本息
	private Integer periodNum; //剩余还款期数
	private Double repayMoneyAmount; //剩余还款金额

	public BwOrderInfoDto() {
	}

	public BwOrderInfoDto(Long orderId, Double borrowAmount, Short repayTerm, Integer repayType, Integer periodNum,
			Double repayMoneyAmount) {
		super();
		this.orderId = orderId;
		this.borrowAmount = borrowAmount;
		this.repayTerm = repayTerm;
		this.repayType = repayType;
		this.periodNum = periodNum;
		this.repayMoneyAmount = repayMoneyAmount;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Double getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(Double borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	public Short getRepayTerm() {
		return repayTerm;
	}

	public void setRepayTerm(Short repayTerm) {
		this.repayTerm = repayTerm;
	}

	public Integer getRepayType() {
		return repayType;
	}

	public void setRepayType(Integer repayType) {
		this.repayType = repayType;
	}

	public Integer getPeriodNum() {
		return periodNum;
	}

	public void setPeriodNum(Integer periodNum) {
		this.periodNum = periodNum;
	}

	public Double getRepayMoneyAmount() {
		return repayMoneyAmount;
	}

	public void setRepayMoneyAmount(Double repayMoneyAmount) {
		this.repayMoneyAmount = repayMoneyAmount;
	}

}
