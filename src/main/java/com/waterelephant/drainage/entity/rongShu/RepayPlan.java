package com.waterelephant.drainage.entity.rongShu;
/**
 * @author xiaoXingWu
 * @time 2017年10月25日
 * @since JDK1.8
 * @description
 */
public class RepayPlan {
	private String  amount;
	private String periodNo;
	private String canRepayTime;
	private String dueTime;
	private String payType;
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPeriodNo() {
		return periodNo;
	}
	public void setPeriodNo(String periodNo) {
		this.periodNo = periodNo;
	}
	public String getCanRepayTime() {
		return canRepayTime;
	}
	public void setCanRepayTime(String canRepayTime) {
		this.canRepayTime = canRepayTime;
	}
	public String getDueTime() {
		return dueTime;
	}
	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	

}

