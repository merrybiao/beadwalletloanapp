package com.waterelephant.jiedianqian.entity;

/**
 * 
 * 
 * Module:
 * 
 * BankInfo.java
 * 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <银行卡信息>
 */
public class BankInfo {
	private String code; // 银行代码
	private String card_no; // 卡号
	private String phone; // 预留手机号
	private String bank_name; // 银行名称

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

}
