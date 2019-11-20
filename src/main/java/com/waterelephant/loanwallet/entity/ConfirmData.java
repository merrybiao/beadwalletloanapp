package com.waterelephant.loanwallet.entity;

public class ConfirmData {
	private String userId;
	private String orderNo;
	private String bankCard;
	private String realName;
	private String cardNo;
	private String phone;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConfirmData [");
		if (userId != null) {
			builder.append("userId=");
			builder.append(userId);
			builder.append(", ");
		}
		if (bankCard != null) {
			builder.append("bankCard=");
			builder.append(bankCard);
			builder.append(", ");
		}
		if (realName != null) {
			builder.append("realName=");
			builder.append(realName);
			builder.append(", ");
		}
		if (cardNo != null) {
			builder.append("cardNo=");
			builder.append(cardNo);
			builder.append(", ");
		}
		if (phone != null) {
			builder.append("phone=");
			builder.append(phone);
		}
		builder.append("]");
		return builder.toString();
	}
}