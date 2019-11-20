package com.waterelephant.operatorData.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class OperatorBillDataDto {
	
	@JSONField(name="month",ordinal =1)
	private String month = "";
	
	@JSONField(name="call_pay",ordinal =2)
	private String callPay = "";
	
	@JSONField(name="package_fee",ordinal =3)
	private String packageFee = "";
	
	@JSONField(name="msg_fee",ordinal =4)
	private String msgFee = "";
	
	@JSONField(name="tel_fee",ordinal =5)
	private String telFee = "";
	
	@JSONField(name="net_fee",ordinal =6)
	private String netFee = "";

	@JSONField(name="addtional_fee",ordinal =7)
	private String addtionalFee = "";
	
	@JSONField(name="preferential_fee",ordinal =8)
	private String preferentialFee = "";
	
	@JSONField(name="generation_fee",ordinal =9)
	private String generationFee = "";
	
	@JSONField(name="other_fee",ordinal =10)
	private String otherFee = "";
	
	@JSONField(name="score",ordinal =11)
	private String score = "";
	
	@JSONField(name="otherspaid_fee",ordinal =12)
	private String otherspaidFee = "";
	
	@JSONField(name="pay_fee",ordinal =13)
	private String payFee = "";

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getCallPay() {
		return callPay;
	}

	public void setCallPay(String callPay) {
		this.callPay = callPay;
	}

	public String getPackageFee() {
		return packageFee;
	}

	public void setPackageFee(String packageFee) {
		this.packageFee = packageFee;
	}

	public String getMsgFee() {
		return msgFee;
	}

	public void setMsgFee(String msgFee) {
		this.msgFee = msgFee;
	}

	public String getTelFee() {
		return telFee;
	}

	public void setTelFee(String telFee) {
		this.telFee = telFee;
	}

	public String getNetFee() {
		return netFee;
	}

	public void setNetFee(String netFee) {
		this.netFee = netFee;
	}

	public String getAddtionalFee() {
		return addtionalFee;
	}

	public void setAddtionalFee(String addtionalFee) {
		this.addtionalFee = addtionalFee;
	}

	public String getPreferentialFee() {
		return preferentialFee;
	}

	public void setPreferentialFee(String preferentialFee) {
		this.preferentialFee = preferentialFee;
	}

	public String getGenerationFee() {
		return generationFee;
	}

	public void setGenerationFee(String generationFee) {
		this.generationFee = generationFee;
	}

	public String getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getOtherspaidFee() {
		return otherspaidFee;
	}

	public void setOtherspaidFee(String otherspaidFee) {
		this.otherspaidFee = otherspaidFee;
	}

	public String getPayFee() {
		return payFee;
	}

	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}
	
	
}
