/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.baidu.entity;

/**
 * 
 * 
 * Module:
 * 
 * OrderInfoData.java
 * 
 * @author 甘立思
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class OrderInfoData {
	private int apply_status;
	private int balance;
	private int term;
	private int service_fee;
	private int real_balance;
	private int month_balance;
	private int remain_debt;
	private String bank_code;
	private String card_no;
	private String repay_time;

	/**
	 * @return 获取 apply_status属性值
	 */
	public int getApply_status() {
		return apply_status;
	}

	/**
	 * @param apply_status 设置 apply_status 属性值为参数值 apply_status
	 */
	public void setApply_status(int apply_status) {
		this.apply_status = apply_status;
	}

	/**
	 * @return 获取 balance属性值
	 */
	public int getBalance() {
		return balance;
	}

	/**
	 * @param balance 设置 balance 属性值为参数值 balance
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}

	/**
	 * @return 获取 term属性值
	 */
	public int getTerm() {
		return term;
	}

	/**
	 * @param term 设置 term 属性值为参数值 term
	 */
	public void setTerm(int term) {
		this.term = term;
	}

	/**
	 * @return 获取 service_fee属性值
	 */
	public int getService_fee() {
		return service_fee;
	}

	/**
	 * @param service_fee 设置 service_fee 属性值为参数值 service_fee
	 */
	public void setService_fee(int service_fee) {
		this.service_fee = service_fee;
	}

	/**
	 * @return 获取 real_balance属性值
	 */
	public int getReal_balance() {
		return real_balance;
	}

	/**
	 * @param real_balance 设置 real_balance 属性值为参数值 real_balance
	 */
	public void setReal_balance(int real_balance) {
		this.real_balance = real_balance;
	}

	/**
	 * @return 获取 month_balance属性值
	 */
	public int getMonth_balance() {
		return month_balance;
	}

	/**
	 * @param month_balance 设置 month_balance 属性值为参数值 month_balance
	 */
	public void setMonth_balance(int month_balance) {
		this.month_balance = month_balance;
	}

	/**
	 * @return 获取 remain_debt属性值
	 */
	public int getRemain_debt() {
		return remain_debt;
	}

	/**
	 * @param remain_debt 设置 remain_debt 属性值为参数值 remain_debt
	 */
	public void setRemain_debt(int remain_debt) {
		this.remain_debt = remain_debt;
	}

	/**
	 * @return 获取 bank_code属性值
	 */
	public String getBank_code() {
		return bank_code;
	}

	/**
	 * @param bank_code 设置 bank_code 属性值为参数值 bank_code
	 */
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	/**
	 * @return 获取 card_no属性值
	 */
	public String getCard_no() {
		return card_no;
	}

	/**
	 * @param card_no 设置 card_no 属性值为参数值 card_no
	 */
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	/**
	 * @return 获取 repay_time属性值
	 */
	public String getRepay_time() {
		return repay_time;
	}

	/**
	 * @param repay_time 设置 repay_time 属性值为参数值 repay_time
	 */
	public void setRepay_time(String repay_time) {
		this.repay_time = repay_time;
	}
}
