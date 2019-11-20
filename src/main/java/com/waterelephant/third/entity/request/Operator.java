package com.waterelephant.third.entity.request;

/**
 * 统一对外接口 - 订单推送 - 运营商（code0091）
 * 
 * 
 * Module:
 * 
 * Operator.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class Operator {
	private String source; // 号码来源（移动，联通，电信）
	private String idCard; // 身份证号
	private String addr; // 注册该号码所在地
	private String realName; // 实名用户姓名
	private String balance; // 当前账户余额
	private String phone; // 电话号码
	private String regTime; // 入网时间

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

}
