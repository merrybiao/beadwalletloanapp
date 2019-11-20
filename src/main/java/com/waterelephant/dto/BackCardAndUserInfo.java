package com.waterelephant.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BackCardAndUserInfo implements Serializable {
	private String bwId;// 借款人id
	private String phone;// 手机号
	private String name;// 真实姓名
	private String idCard;// 身份证
	private String cardNo;// 银行卡号
	private String bankCode;// 银行编码
	private String bankName;// 开户行名称

	public String getBwId() {
		return bwId;
	}

	public void setBwId(String bwId) {
		this.bwId = bwId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public String toString() {
		return "BackCardAndUserInfo [bwId=" + bwId + ", phone=" + phone + ", name=" + name + ", idCard=" + idCard
				+ ", cardNo=" + cardNo + ", bankCode=" + bankCode + ", bankName=" + bankName + "]";
	}

}
