package com.waterelephant.drainage.baidu.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 银行卡绑定
 */
public class AuthCodeEntity extends CommonEntity {

	@JSONField(name = "auth_code")
	@NotBlank(message = "商户平台授权码不能为空")
	private String auth_code; // 用户在商户系统的授权码，与百度金融对接的唯一凭证

	@JSONField(name = "order_status")
	@NotNull(message = "百度金融订单状态不能为空")
	private int order_status; // 百度金融订单状态

	@JSONField(name = "order_id")
	@NotBlank(message = "百度金融商城订单不能为空")
	private String order_id; // 百度金融商城订单

	@JSONField(name = "merchant_order_status")
	@NotBlank(message = "商户平台授权码不能为空")
	private String merchant_order_status;

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
	 * @return 获取 order_status属性值
	 */
	public int getOrder_status() {
		return order_status;
	}

	/**
	 * @param order_status 设置 order_status 属性值为参数值 order_status
	 */
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
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
	 * @return 获取 merchant_order_status属性值
	 */
	public String getMerchant_order_status() {
		return merchant_order_status;
	}

	/**
	 * @param merchant_order_status 设置 merchant_order_status 属性值为参数值 merchant_order_status
	 */
	public void setMerchant_order_status(String merchant_order_status) {
		this.merchant_order_status = merchant_order_status;
	}

}
