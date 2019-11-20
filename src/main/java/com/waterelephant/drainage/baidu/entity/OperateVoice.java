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
 * OperateVoice.java
 * 
 * @author 甘立思
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class OperateVoice {
	private String target_phone; // 对方手机号
	private String type; // 业务类型
	private String init_type; // 呼叫类型
	private String call_type; // 通话类型
	private String net_type; // 流量类型
	private String place; // 使用地点
	private String flow; // 使用流量（kb）
	private String amount; // 本次费用(分)
	private String total_amount; // 总费用（分）
	private String pay_amount; // 实际缴费（分）
	private String plan_amount;// 套餐及固定费（分）
	private String start_time; // 使用时间
	private String use_time; // 使用时长（秒）

	/**
	 * @return 获取 target_phone属性值
	 */
	public String getTarget_phone() {
		return target_phone;
	}

	/**
	 * @param target_phone 设置 target_phone 属性值为参数值 target_phone
	 */
	public void setTarget_phone(String target_phone) {
		this.target_phone = target_phone;
	}

	/**
	 * @return 获取 type属性值
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type 设置 type 属性值为参数值 type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return 获取 init_type属性值
	 */
	public String getInit_type() {
		return init_type;
	}

	/**
	 * @param init_type 设置 init_type 属性值为参数值 init_type
	 */
	public void setInit_type(String init_type) {
		this.init_type = init_type;
	}

	/**
	 * @return 获取 call_type属性值
	 */
	public String getCall_type() {
		return call_type;
	}

	/**
	 * @param call_type 设置 call_type 属性值为参数值 call_type
	 */
	public void setCall_type(String call_type) {
		this.call_type = call_type;
	}

	/**
	 * @return 获取 net_type属性值
	 */
	public String getNet_type() {
		return net_type;
	}

	/**
	 * @param net_type 设置 net_type 属性值为参数值 net_type
	 */
	public void setNet_type(String net_type) {
		this.net_type = net_type;
	}

	/**
	 * @return 获取 place属性值
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @param place 设置 place 属性值为参数值 place
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * @return 获取 flow属性值
	 */
	public String getFlow() {
		return flow;
	}

	/**
	 * @param flow 设置 flow 属性值为参数值 flow
	 */
	public void setFlow(String flow) {
		this.flow = flow;
	}

	/**
	 * @return 获取 amount属性值
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount 设置 amount 属性值为参数值 amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return 获取 total_amount属性值
	 */
	public String getTotal_amount() {
		return total_amount;
	}

	/**
	 * @param total_amount 设置 total_amount 属性值为参数值 total_amount
	 */
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	/**
	 * @return 获取 pay_amount属性值
	 */
	public String getPay_amount() {
		return pay_amount;
	}

	/**
	 * @param pay_amount 设置 pay_amount 属性值为参数值 pay_amount
	 */
	public void setPay_amount(String pay_amount) {
		this.pay_amount = pay_amount;
	}

	/**
	 * @return 获取 plan_amount属性值
	 */
	public String getPlan_amount() {
		return plan_amount;
	}

	/**
	 * @param plan_amount 设置 plan_amount 属性值为参数值 plan_amount
	 */
	public void setPlan_amount(String plan_amount) {
		this.plan_amount = plan_amount;
	}

	/**
	 * @return 获取 start_time属性值
	 */
	public String getStart_time() {
		return start_time;
	}

	/**
	 * @param start_time 设置 start_time 属性值为参数值 start_time
	 */
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	/**
	 * @return 获取 use_time属性值
	 */
	public String getUse_time() {
		return use_time;
	}

	/**
	 * @param use_time 设置 use_time 属性值为参数值 use_time
	 */
	public void setUse_time(String use_time) {
		this.use_time = use_time;
	}
}
