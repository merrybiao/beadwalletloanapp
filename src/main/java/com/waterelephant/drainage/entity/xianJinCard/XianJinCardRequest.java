package com.waterelephant.drainage.entity.xianJinCard;

/**
 * Module:
 * 
 * XianJin360Request.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class XianJinCardRequest {

	private String ua; // 开放平台分配给合作方的唯一标识
	private String call;// 请求标识; 用来唯一标记当前调用的接口
	private String args;// 接口的调用参数, 要求必须为JSON String.
	private String sign;// 请求签名
	private String timestamp;// 以秒为单位的UnixTimestamp时间戳 1500693926

	/**
	 * @return 获取 ua属性值
	 */
	public String getUa() {
		return ua;
	}

	/**
	 * @param ua 设置 ua 属性值为参数值 ua
	 */
	public void setUa(String ua) {
		this.ua = ua;
	}

	/**
	 * @return 获取 call属性值
	 */
	public String getCall() {
		return call;
	}

	/**
	 * @param call 设置 call 属性值为参数值 call
	 */
	public void setCall(String call) {
		this.call = call;
	}

	/**
	 * @return 获取 args属性值
	 */
	public String getArgs() {
		return args;
	}

	/**
	 * @param args 设置 args 属性值为参数值 args
	 */
	public void setArgs(String args) {
		this.args = args;
	}

	/**
	 * @return 获取 sign属性值
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign 设置 sign 属性值为参数值 sign
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * @return 获取 timestamp属性值
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp 设置 timestamp 属性值为参数值 timestamp
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
