package com.waterelephant.third.entity.request;

/**
 * 统一对外接口 - 签约入参（code0091）
 * 
 * 
 * Module:
 * 
 * RequestSign.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class RequestSign {
	private String userId; // 用户ID
	private String idCard; // 身份证号
	private String accountName; // 账户姓名
	private String bankCardNo; // 银行卡号
	private String thirdOrderNo;// 第三方订单编号
	private String url;// 绑卡成功调转地址

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getThirdOrderNo() {
		return thirdOrderNo;
	}

	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
