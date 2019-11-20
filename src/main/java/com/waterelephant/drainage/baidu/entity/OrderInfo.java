package com.waterelephant.drainage.baidu.entity;

import org.hibernate.validator.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 订单信息
 * 
 * @author dengyan
 *
 */
public class OrderInfo extends CommonEntity {

	@JSONField(name = "auth_code")
	@NotBlank(message = "商户平台授权码不能为空")
	private String auth_code; // 授权唯一凭证

	@JSONField(name = "order_id")
	@NotBlank(message = "百度金融商城订单id不能为空")
	private String order_id; // 百度金融商城订单id

	@JSONField(name = "apply_id")
	@NotBlank(message = "商户系统订单id不能为空")
	private String apply_id; // 商户系统订单id

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

}
