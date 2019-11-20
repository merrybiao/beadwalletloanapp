package com.waterelephant.dto;

import java.io.Serializable;

/**
 * 逾期信息
 * @author lujilong
 *
 */
public class BwOverdueRecordDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long orderId;
	private Integer overdueDay;
	private Double overdueAccrualMoney;
	private Double repayMoney;
	private Double borrowAmonunt;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getOverdueDay() {
		return overdueDay;
	}
	public void setOverdueDay(Integer overdueDay) {
		this.overdueDay = overdueDay;
	}
	public Double getOverdueAccrualMoney() {
		return overdueAccrualMoney;
	}
	public void setOverdueAccrualMoney(Double overdueAccrualMoney) {
		this.overdueAccrualMoney = overdueAccrualMoney;
	}
	public Double getRepayMoney() {
		return repayMoney;
	}
	public void setRepayMoney(Double repayMoney) {
		this.repayMoney = repayMoney;
	}
	public Double getBorrowAmonunt() {
		return borrowAmonunt;
	}
	public void setBorrowAmonunt(Double borrowAmonunt) {
		this.borrowAmonunt = borrowAmonunt;
	}
	
}
