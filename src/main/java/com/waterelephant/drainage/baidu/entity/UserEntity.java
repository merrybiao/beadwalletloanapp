package com.waterelephant.drainage.baidu.entity;

import org.hibernate.validator.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;

public class UserEntity extends CommonEntity {

	@JSONField(name = "id_card")
	@NotBlank(message = "身份证不能为空")
	private String id_card;

	@JSONField(name = "mobile")
	@NotBlank(message = "手机号不能为空")
	private String mobile;

	@JSONField(name = "user_name")
	@NotBlank(message = "用户名不能为空")
	private String user_name;

	/**
	 * @return 获取 id_card属性值
	 */
	public String getId_card() {
		return id_card;
	}

	/**
	 * @param id_card 设置 id_card 属性值为参数值 id_card
	 */
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	/**
	 * @return 获取 mobile属性值
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile 设置 mobile 属性值为参数值 mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return 获取 user_name属性值
	 */
	public String getUser_name() {
		return user_name;
	}

	/**
	 * @param user_name 设置 user_name 属性值为参数值 user_name
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

}
