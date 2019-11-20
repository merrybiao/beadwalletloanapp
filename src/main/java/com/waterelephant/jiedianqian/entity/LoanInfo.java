package com.waterelephant.jiedianqian.entity;

/**
 * 
 * 
 * Module:
 * 
 * LoanInfo.java
 * 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <贷款信息>
 */
public class LoanInfo {
	private String money; // 贷款金额，单位元
	private String day; // 贷款天数

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

}
