package com.waterelephant.capital.weishenma;

import java.util.List;

public class WeishenmaResponse {
	public String shddh;//商户订单号
	public String userName;//用户姓名
	public String idCard;//用户身份证
	public Integer periodTotal;//总分期
	public Double amount;//合同金额（没有就等于应还本金）
	public String lastRepayAt;//最近还款日期
	public Integer loanSuccess;//是否放款成功
	public List<WsmSchedules> schedules;
	public String getShddh() {
		return shddh;
	}
	public void setShddh(String shddh) {
		this.shddh = shddh;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Integer getPeriodTotal() {
		return periodTotal;
	}
	public void setPeriodTotal(Integer periodTotal) {
		this.periodTotal = periodTotal;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getLastRepayAt() {
		return lastRepayAt;
	}
	public void setLastRepayAt(String lastRepayAt) {
		this.lastRepayAt = lastRepayAt;
	}
	public Integer getLoanSuccess() {
		return loanSuccess;
	}
	public void setLoanSuccess(Integer loanSuccess) {
		this.loanSuccess = loanSuccess;
	}
	public List<WsmSchedules> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<WsmSchedules> schedules) {
		this.schedules = schedules;
	}
	
	
	
}
