package com.waterelephant.drainage.entity.rongShu;
/**
 * @author xiaoXingWu
 * @time 2017年8月24日
 * @since JDK1.8
 * @description
 */
public class SmsDetailList {
	
	private  String  dialType;//业务类型
	private  String  bizName;//业务名称
	private  String  time;//起始时间
	private  String  location;//地点
	private  String  peerOperator;//对方运营商
	private  String  peerNumber;//对方号码
	private  String  isSms;//是否是彩信
	private  String  messageLen;//信息长度
	private  String   fee;//总费用
	public String getDialType() {
		return dialType;
	}
	public void setDialType(String dialType) {
		this.dialType = dialType;
	}
	public String getBizName() {
		return bizName;
	}
	public void setBizName(String bizName) {
		this.bizName = bizName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPeerOperator() {
		return peerOperator;
	}
	public void setPeerOperator(String peerOperator) {
		this.peerOperator = peerOperator;
	}
	public String getPeerNumber() {
		return peerNumber;
	}
	public void setPeerNumber(String peerNumber) {
		this.peerNumber = peerNumber;
	}
	public String getIsSms() {
		return isSms;
	}
	public void setIsSms(String isSms) {
		this.isSms = isSms;
	}
	public String getMessageLen() {
		return messageLen;
	}
	public void setMessageLen(String messageLen) {
		this.messageLen = messageLen;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	
	

}

