package com.waterelephant.register.entity;

public class RegisterReqData {
	private String phone;

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegisterReqData [");
		if (phone != null) {
			builder.append("phone=");
			builder.append(phone);
		}
		builder.append("]");
		return builder.toString();
	}
}