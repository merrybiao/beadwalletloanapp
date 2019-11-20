package com.waterelephant.dto;

public class BwOperateVoiceDto {
	private String tradeType;   //通信类型
	private String tradeTime;	//通话时长 
	private String callTime;	//通话时间
	private String tradeAddr;	//通话地点
	private String receivePhone;//对方号码
	private String callType;	//呼叫类型（主叫：1，被叫：2',
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
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
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}

}
