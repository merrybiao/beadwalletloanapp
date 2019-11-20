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
 * RepaymentPlanData.java
 * 
 * @author 甘立思
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class RepaymentPlanData {
	private int status;
	private int amount;
	private int due_time;
	private int real_repay_time;
	private String plan_id;

	/**
	 * @return 获取 status属性值
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status 设置 status 属性值为参数值 status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return 获取 amount属性值
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount 设置 amount 属性值为参数值 amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return 获取 due_time属性值
	 */
	public int getDue_time() {
		return due_time;
	}

	/**
	 * @param due_time 设置 due_time 属性值为参数值 due_time
	 */
	public void setDue_time(int due_time) {
		this.due_time = due_time;
	}

	/**
	 * @return 获取 real_repay_time属性值
	 */
	public int getReal_repay_time() {
		return real_repay_time;
	}

	/**
	 * @param real_repay_time 设置 real_repay_time 属性值为参数值 real_repay_time
	 */
	public void setReal_repay_time(int real_repay_time) {
		this.real_repay_time = real_repay_time;
	}

	/**
	 * @return 获取 plan_id属性值
	 */
	public String getPlan_id() {
		return plan_id;
	}

	/**
	 * @param plan_id 设置 plan_id 属性值为参数值 plan_id
	 */
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
}
