package com.waterelephant.drainage.entity.xianJinCard;

/**
 * 
 * Module:
 * 
 * XianJin360Response.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class XianJinCardResponse {
	public static final int CODE_SUCCESS = 1; // 接口调用成功
	public static final int CODE_FAILURE = 0; // 接口调用失败

	private int status;
	private String message;
	private Object response;

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
	 * @return 获取 message属性值
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message 设置 message 属性值为参数值 message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return 获取 response属性值
	 */
	public Object getResponse() {
		return response;
	}

	/**
	 * @param response 设置 response 属性值为参数值 response
	 */
	public void setResponse(Object response) {
		this.response = response;
	}
}
