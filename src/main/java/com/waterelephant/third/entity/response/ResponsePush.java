package com.waterelephant.third.entity.response;

/**
 * 统一对外接口 - 订单推送 - 响应体（code0091）
 * 
 * 
 * Module:
 * 
 * ResponsePush.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class ResponsePush {
	private String userId; // 用户ID
	private String thirdOrderNo; // 机构订单流水号
	private Object penetrate; // 透传信息

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getThirdOrderNo() {
		return thirdOrderNo;
	}

	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}

	public Object getPenetrate() {
		return penetrate;
	}

	public void setPenetrate(Object penetrate) {
		this.penetrate = penetrate;
	}

}
