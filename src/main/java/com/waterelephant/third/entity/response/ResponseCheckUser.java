/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.third.entity.response;

/**
 * 统一对外接口 -响应体 （code0091）
 * 
 * Module:
 * 
 * ResponseCheckUser.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class ResponseCheckUser {
	private int isStock;// 是否存量用户（1是，0否）
	private int isCanLoan;// 是否可以借款（1是，0否）
	private int isBlackList;// 是否黑名单（1是，0否）
	private int rejectReason;// 拒绝原因（1 黑名单，2 在贷，3 其他）
	private String remark;// 备注（其他拒绝原因等数名）
	private int maxLimit;// 最高额度（元）
	private int minLimit;// 最低额度（元）
	private int minPeriod;// 最小可借周期
	private int maxPeriod;// 最大可借周期
	private int periodUnit;// 周期单位（1天，2月）
	private String bank;// 已绑银行
	private String bankCardNum;// 已绑银行卡号
	private String phone;// 银行预留手机号
	private Object penetrate;// 透传信息

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

	public int getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(int rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Object getPenetrate() {
		return penetrate;
	}

	public void setPenetrate(Object penetrate) {
		this.penetrate = penetrate;
	}

}
