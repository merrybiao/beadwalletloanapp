package com.waterelephant.third.entity.request;

/**
 * 统一对外接口 - 订单推送 - 通讯录（code0091）
 * 
 * 
 * Module:
 * 
 * Contact.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class Contact {
	private String name; // 备注名
	private String phone; // 手机号

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
