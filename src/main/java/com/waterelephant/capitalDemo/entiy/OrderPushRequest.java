package com.waterelephant.capitalDemo.entiy;

public class OrderPushRequest {
	private String thridNo; // 水象订单号
	private String userName; // 姓名
	private String idCard; // 身份证
	private String mobile; // 手机号
	private String bankCard; // 银行卡号
	private String money; // 金额

	public String getThridNo() {
		return thridNo;
	}

	public void setThridNo(String thridNo) {
		this.thridNo = thridNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "OrderPushRequest [thridNo=" + thridNo + ", userName=" + userName + ", idCard=" + idCard + ", mobile="
				+ mobile + ", bankCard=" + bankCard + ", money=" + money + "]";
	}

}
