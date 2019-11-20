package com.waterelephant.drainage.baidu.entity;

import org.hibernate.validator.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;

public class AuthStatuEntity extends CommonEntity {

	@JSONField(name = "auth_code")
	@NotBlank(message = "商户平台授权码不能为空")
	private String auth_code; // 商户平台授权码，与百度金融对接的唯一凭证

	@JSONField(name = "order_id")
	@NotBlank(message = "百度金融商城订单不能为空")
	private String order_id; // 百度金融商城订单

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

}
