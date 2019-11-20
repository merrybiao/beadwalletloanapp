package com.waterelephant.drainage.baidu.entity;

import org.hibernate.validator.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 签约预览实体
 * 
 * @author dengyan
 *
 */
public class ApplyInfoEntity extends CommonEntity {

	@JSONField(name = "auth_code")
	@NotBlank(message = "授权凭证不能为空")
	private String auth_code; // 授权凭证

	@JSONField(name = "amount")
	@NotBlank(message = "贷款金额不能为空")
	private String amount; // 贷款金额

	@JSONField(name = "term")
	@NotBlank(message = "申请期限不能为空")
	private String term; // 申请期限

	@JSONField(name = "order_id")
	@NotBlank(message = "百度金融订单id不能为空")
	private String order_id; // 百度金融订单id

	@JSONField(name = "order_status")
	@NotBlank(message = "百度金融订单状态不能为空")
	private String order_status; // 百度金融订单状态

	@JSONField(name = "apply_status")
	@NotBlank(message = "商户系统订单状态不能为空")
	private String apply_status; // 商户系统订单状态

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
	 * @return 获取 term属性值
	 */
	public String getTerm() {
		return term;
	}

	/**
	 * @param term 设置 term 属性值为参数值 term
	 */
	public void setTerm(String term) {
		this.term = term;
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
	 * @return 获取 order_status属性值
	 */
	public String getOrder_status() {
		return order_status;
	}

	/**
	 * @param order_status 设置 order_status 属性值为参数值 order_status
	 */
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	/**
	 * @return 获取 apply_status属性值
	 */
	public String getApply_status() {
		return apply_status;
	}

	/**
	 * @param apply_status 设置 apply_status 属性值为参数值 apply_status
	 */
	public void setApply_status(String apply_status) {
		this.apply_status = apply_status;
	}

}
