package com.waterelephant.third.entity;

/**
 * 统一对外接口 - 入参（code0091）
 * 
 * 
 * Module:
 * 
 * ThirdRequest.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class ThirdRequest {
	private String appId; // 机构标识
	private String request; // 请求参数

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

}
