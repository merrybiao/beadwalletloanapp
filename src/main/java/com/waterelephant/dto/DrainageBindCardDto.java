
package com.waterelephant.dto;

/**
 * @ClassName: DrainageBindCardDto  
 * @Description:   
 * @author liwanliang  
 * @date 2018年5月16日  
 *
 */
public class DrainageBindCardDto {

	//"是否有绑卡"
	private Boolean hasBankCard;
	
	//"真实姓名"
	private String name;
	
	//"预留手机号"
	private String phone;
	
	//"身份证"
	private String idCard;
	
	//"银行卡号"
	private String cardNo;
	
	//"银行卡号后4位"
	private String cardNoEnd;
	
	//"银行名称"
	private String bankName;
	
	//"是否需要绑卡验证码(0 :否 1: 是)
	private String IsNeedBingCardCode;

	public Boolean getHasBankCard() {
		return hasBankCard;
	}

	public void setHasBankCard(Boolean hasBankCard) {
		this.hasBankCard = hasBankCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIsNeedBingCardCode() {
		return IsNeedBingCardCode;
	}

	public void setIsNeedBingCardCode(String isNeedBingCardCode) {
		IsNeedBingCardCode = isNeedBingCardCode;
	}

	public String getCardNoEnd() {
		return cardNoEnd;
	}

	public void setCardNoEnd(String cardNoEnd) {
		this.cardNoEnd = cardNoEnd;
	}
	
}

