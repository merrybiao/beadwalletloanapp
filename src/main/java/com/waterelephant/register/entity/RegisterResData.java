package com.waterelephant.register.entity;

public class RegisterResData {
	private String userType;
	private String returnUrl;
	private String phone;
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
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
		builder.append("RegisterResData [");
		if (userType != null) {
			builder.append("userType=");
			builder.append(userType);
			builder.append(", ");
		}
		if (returnUrl != null) {
			builder.append("returnUrl=");
			builder.append(returnUrl);
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