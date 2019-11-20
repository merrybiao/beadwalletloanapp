package com.waterelephant.third.entity.request;

/**
 * 统一对外接口 - 订单推送 - 通话记录（code0091）
 * 
 * 
 * Module:
 * 
 * CallRecords.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class CallRecord {
	private int tradeType; // 通信类型（1：本地，2：国内漫游，3：其它）
	private String tradeTime; // 通话时长
	private String callTime; // 通话时间（精确到秒）
	private String tradeAddr; // 通话地点
	private String receivePhone; // 对方号码
	private int callType; // 呼叫类型（1：主叫，2：被叫，3：未识别状态）

	public int getTradeType() {
		return tradeType;
	}

	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public String getTradeAddr() {
		return tradeAddr;
	}

	public void setTradeAddr(String tradeAddr) {
		this.tradeAddr = tradeAddr;
	}

	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}

	public int getCallType() {
		return callType;
	}

	public void setCallType(int callType) {
		this.callType = callType;
	}

}
