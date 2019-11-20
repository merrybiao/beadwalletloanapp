package com.waterelephant.dto;

/**
 * 我要展期界面显示
 * 
 * @author wrh
 *
 */
public class LoanInfo {

	/**
	 * 利息，总费用，18%手续费+展期服务费+逾期费
	 */
	private String amt;
	private String applyTime;// 申请时间
	/**
	 * 展期期限，单位由termType决定
	 */
	private String term;
	/**
	 * 展期期限类型，1.月 2.天
	 */
	private String termType;
	private String termStr;
	private String borrowAmount;// 展期金额
	/**
	 * 展期总费用，18%手续费+展期服务费
	 */
	private String serviceAmount;
	/**
	 * 是否逾期
	 */
	private Boolean isOverdue;
	/**
	 * 逾期费用，1%
	 */
	private String overdueAmount;
	/**
	 * 逾期天数
	 */
	private Integer overdueDay;
	/**
	 * 免罚息天数
	 */
	private Integer avoidFineDate;
	/**
	 * 免罚息金额
	 */
	private String noOverdueAmount;
	/**
	 * 实际逾期罚息金额
	 */
	private String realOverdueAmount;
	/**
	 * 湛江委金额
	 */
	private String zjwAmount;
	private String delayTime;// 延期时间
	private Double realityRepayMoney;
	private String zhanqiRepayTime;// 展期后还款日期
	private String repayTime;// 还款日期

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getTermStr() {
		return termStr;
	}

	public void setTermStr(String termStr) {
		this.termStr = termStr;
	}

	public String getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(String borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	public String getServiceAmount() {
		return serviceAmount;
	}

	public void setServiceAmount(String serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	public Boolean getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(Boolean isOverdue) {
		this.isOverdue = isOverdue;
	}

	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public Integer getOverdueDay() {
		return overdueDay;
	}

	public void setOverdueDay(Integer overdueDay) {
		this.overdueDay = overdueDay;
	}

	public Integer getAvoidFineDate() {
		return avoidFineDate;
	}

	public void setAvoidFineDate(Integer avoidFineDate) {
		this.avoidFineDate = avoidFineDate;
	}

	public String getNoOverdueAmount() {
		return noOverdueAmount;
	}

	public void setNoOverdueAmount(String noOverdueAmount) {
		this.noOverdueAmount = noOverdueAmount;
	}

	public String getRealOverdueAmount() {
		return realOverdueAmount;
	}

	public void setRealOverdueAmount(String realOverdueAmount) {
		this.realOverdueAmount = realOverdueAmount;
	}

	public String getZjwAmount() {
		return zjwAmount;
	}

	public void setZjwAmount(String zjwAmount) {
		this.zjwAmount = zjwAmount;
	}

	public String getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}

	public Double getRealityRepayMoney() {
		return realityRepayMoney;
	}

	public void setRealityRepayMoney(Double realityRepayMoney) {
		this.realityRepayMoney = realityRepayMoney;
	}

	public String getZhanqiRepayTime() {
		return zhanqiRepayTime;
	}

	public void setZhanqiRepayTime(String zhanqiRepayTime) {
		this.zhanqiRepayTime = zhanqiRepayTime;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}
}