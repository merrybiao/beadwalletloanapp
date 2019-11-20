package com.waterelephant.drainage.entity.youyu;
/***
 * 
 * 
 * 
 * Module: 有鱼返回检查用户实体类
 * 
 * YouyuRequestCheckUser.java 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */

public class YouyuRequestCheckUser {
	private String user_id;// 掩码身份证
	private String user_name;//用户的姓名
	private String user_phone;//用户的手机号
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
}
