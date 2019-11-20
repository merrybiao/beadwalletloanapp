package com.waterelephant.drainage.entity.rongShu;

/**
 * 榕树 - 5.1 存量用户检验接口响应体（code0087501）
 * 
 * 
 * Module:
 * 
 * UserCheckResponse.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <榕树 - 5.1 存量用户检验接口响应体>
 */
public class UserCheckResponse {
	private int isStock; // 是否存量用户 （0：否，1：是）
	private int isCanLoan; // 是否可以借款（0：否，1：是）
	private int isBlackList; // 是否命中黑名单（0：否，1：是）
	private int maxLimit; // 最高额度（单位：元）
	private int minLimit; // 最低额度（单位：元）
	private int minPeriod; // 最小可借周期
	private int maxPeriod; // 最大可借周期
	private int periodUnit; // 周期单位（1：天，2：月）
	private int rejectReason; // 拒绝原因（1：黑名单，2：在贷）

	public int getIsStock() {
		return isStock;
	}

	public void setIsStock(int isStock) {
		this.isStock = isStock;
	}

	public int getIsCanLoan() {
		return isCanLoan;
	}

	public void setIsCanLoan(int isCanLoan) {
		this.isCanLoan = isCanLoan;
	}

	public int getIsBlackList() {
		return isBlackList;
	}

	public void setIsBlackList(int isBlackList) {
		this.isBlackList = isBlackList;
	}

	public int getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(int maxLimit) {
		this.maxLimit = maxLimit;
	}

	public int getMinLimit() {
		return minLimit;
	}

	public void setMinLimit(int minLimit) {
		this.minLimit = minLimit;
	}

	public int getMinPeriod() {
		return minPeriod;
	}

	public void setMinPeriod(int minPeriod) {
		this.minPeriod = minPeriod;
	}

	public int getMaxPeriod() {
		return maxPeriod;
	}

	public void setMaxPeriod(int maxPeriod) {
		this.maxPeriod = maxPeriod;
	}

	public int getPeriodUnit() {
		return periodUnit;
	}

	public void setPeriodUnit(int periodUnit) {
		this.periodUnit = periodUnit;
	}

	public int getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(int rejectReason) {
		this.rejectReason = rejectReason;
	}

}
