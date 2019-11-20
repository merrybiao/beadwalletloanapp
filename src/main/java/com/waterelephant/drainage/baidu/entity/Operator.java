package com.waterelephant.drainage.baidu.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 运营商信息
 * 
 * @author dengyan
 *
 */
public class Operator {

	@JSONField(name = "call_history")
	private String call_history; // 文件地址查询URL

	@JSONField(name = "mobile")
	private String mobile; // 手机号

	@JSONField(name = "isp")
	private int isp; // 运营商：0、移动，1、连通，2、电信

	@JSONField(name = "user_name")
	private String user_name; // 用户名

	@JSONField(name = "id_card")
	private String id_card; // 身份证号

	@JSONField(name = "get_time")
	private String get_time; // 获取时间

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getIsp() {
		return isp;
	}

	public void setIsp(int isp) {
		this.isp = isp;
	}

	/**
	 * @return 获取 call_history属性值
	 */
	public String getCall_history() {
		return call_history;
	}

	/**
	 * @param call_history 设置 call_history 属性值为参数值 call_history
	 */
	public void setCall_history(String call_history) {
		this.call_history = call_history;
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
	 * @return 获取 get_time属性值
	 */
	public String getGet_time() {
		return get_time;
	}

	/**
	 * @param get_time 设置 get_time 属性值为参数值 get_time
	 */
	public void setGet_time(String get_time) {
		this.get_time = get_time;
	}

}
