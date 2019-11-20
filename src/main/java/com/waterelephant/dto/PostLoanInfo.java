package com.waterelephant.dto;

/**
 * 贷后信息： 放款后放回给好贷model
 * 
 * @author song
 *
 */
public class PostLoanInfo {

	private String orderId; // 必须 订单号 20160513140001805750
	private Double contractMoney; // 否 合同金额(元) 5000
	private Double loanMoney; // 否 贷款金额 5000
	private int loanMonth; // 否 贷款期限 6
	private int repaymentDate; // 否 每月还款日 9
	private Double repaymentMoney; // 否 每月还款金额(元) 956.35
	private float poundage; // 否 一次性手续费(元) 300.26
	private Double interestRate; // 否 月贷款利率 5.8
	private float monthServiceCharge; // 否 月服务费率 1.5
	private Long firstTime; // 否 首次还款日(时间戳)1466145445
	private float overdueMoney; // 否 逾期金额(元) 500.36
	private int payTime; // 否 实际还款时间(时间戳)1466145445
	private float payMoney; // 否 实际还款金额(元) 506.35
	private float contract; // 否 合同内容 Html 格式的合同内
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(Double contractMoney) {
		this.contractMoney = contractMoney;
	}
	public Double getLoanMoney() {
		return loanMoney;
	}
	public void setLoanMoney(Double loanMoney) {
		this.loanMoney = loanMoney;
	}
	public int getLoanMonth() {
		return loanMonth;
	}
	public void setLoanMonth(int loanMonth) {
		this.loanMonth = loanMonth;
	}
	public int getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(int repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public Double getRepaymentMoney() {
		return repaymentMoney;
	}
	public void setRepaymentMoney(Double repaymentMoney) {
		this.repaymentMoney = repaymentMoney;
	}
	public float getPoundage() {
		return poundage;
	}
	public void setPoundage(float poundage) {
		this.poundage = poundage;
	}
	public Double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	public float getMonthServiceCharge() {
		return monthServiceCharge;
	}
	public void setMonthServiceCharge(float monthServiceCharge) {
		this.monthServiceCharge = monthServiceCharge;
	}
	public Long getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(Long firstTime) {
		this.firstTime = firstTime;
	}
	public float getOverdueMoney() {
		return overdueMoney;
	}
	public void setOverdueMoney(float overdueMoney) {
		this.overdueMoney = overdueMoney;
	}
	public int getPayTime() {
		return payTime;
	}
	public void setPayTime(int payTime) {
		this.payTime = payTime;
	}
	public float getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(float payMoney) {
		this.payMoney = payMoney;
	}
	public float getContract() {
		return contract;
	}
	public void setContract(float contract) {
		this.contract = contract;
	}
	
	
}
