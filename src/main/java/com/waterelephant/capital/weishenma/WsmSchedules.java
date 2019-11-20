package com.waterelephant.capital.weishenma;

public class WsmSchedules {
	public Integer periodStage;//当前期数
	public String dueAt;//应还日期
	public String repayAtPartial;//本息还清日期
	public String repayAt;//实际全部还清日期（+罚息）
	public Integer status;//还款状态（1.还款中.2.本息已完清，罚息未还清，3.全部已还清）
	public Double principal;//应还本金
	public Double repaiCapital;//已还本金
	public Double interest;//应还利息
	public Double repaidInterest;//已还利息
	public Double repaidPenalty;//已还罚息
	public Integer overdues;//逾期天数
	public Integer getPeriodStage() {
		return periodStage;
	}
	public void setPeriodStage(Integer periodStage) {
		this.periodStage = periodStage;
	}
	public String getDueAt() {
		return dueAt;
	}
	public void setDueAt(String dueAt) {
		this.dueAt = dueAt;
	}
	public String getRepayAtPartial() {
		return repayAtPartial;
	}
	public void setRepayAtPartial(String repayAtPartial) {
		this.repayAtPartial = repayAtPartial;
	}
	public String getRepayAt() {
		return repayAt;
	}
	public void setRepayAt(String repayAt) {
		this.repayAt = repayAt;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Double getPrincipal() {
		return principal;
	}
	public void setPrincipal(Double principal) {
		this.principal = principal;
	}
	public Double getRepaiCapital() {
		return repaiCapital;
	}
	public void setRepaiCapital(Double repaiCapital) {
		this.repaiCapital = repaiCapital;
	}
	public Double getInterest() {
		return interest;
	}
	public void setInterest(Double interest) {
		this.interest = interest;
	}
	public Double getRepaidInterest() {
		return repaidInterest;
	}
	public void setRepaidInterest(Double repaidInterest) {
		this.repaidInterest = repaidInterest;
	}
	public Double getRepaidPenalty() {
		return repaidPenalty;
	}
	public void setRepaidPenalty(Double repaidPenalty) {
		this.repaidPenalty = repaidPenalty;
	}
	public Integer getOverdues() {
		return overdues;
	}
	public void setOverdues(Integer overdues) {
		this.overdues = overdues;
	}
	
	
	
}
