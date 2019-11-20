package com.waterelephant.drainage.baidu.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 还款
 * 
 * @author dengyan
 *
 */
public class Apply extends CommonEntity {

	@JSONField(name = "auth_code")
	private String auth_code; // 授权唯一凭证

	@JSONField(name = "order_id")
	private String order_id; // 百度金融商城订单id

	@JSONField(name = "apply_id")
	private String apply_id; // 商户系统订单id

	@JSONField(name = "status")
	private int status; // 百度金融商城订单状态

	@JSONField(name = "apply_status")
	private int apply_status; // 商户系统订单状态

	@JSONField(name = "plan_id")
	private int plan_id; // 还款计划id;

	/**
	 * @return 获取 auth_code属性值
	 */
	public String getAuth_code() {
		return auth_code;
	}

	/**
	 * @param auth_code 设置 auth_code 属性值为参数值 auth_code
	 */
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	/**
	 * @return 获取 order_id属性值
	 */
	public String getOrder_id() {
		return order_id;
	}

	/**
	 * @param order_id 设置 order_id 属性值为参数值 order_id
	 */
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	/**
	 * @return 获取 apply_id属性值
	 */
	public String getApply_id() {
		return apply_id;
	}

	/**
	 * @param apply_id 设置 apply_id 属性值为参数值 apply_id
	 */
	public void setApply_id(String apply_id) {
		this.apply_id = apply_id;
	}

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
	 * @return 获取 plan_id属性值
	 */
	public int getPlan_id() {
		return plan_id;
	}

	/**
	 * @param plan_id 设置 plan_id 属性值为参数值 plan_id
	 */
	public void setPlan_id(int plan_id) {
		this.plan_id = plan_id;
	}
}
