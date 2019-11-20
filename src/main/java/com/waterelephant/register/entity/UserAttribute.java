package com.waterelephant.register.entity;

public class UserAttribute {
	private String mobilephone;
	private String name;
	private String idcard;
	private String timestamp;
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserAttribute [");
		if (mobilephone != null) {
			builder.append("mobilephone=");
			builder.append(mobilephone);
			builder.append(", ");
		}
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (idcard != null) {
			builder.append("idcard=");
			builder.append(idcard);
			builder.append(", ");
		}
		if (timestamp != null) {
			builder.append("timestamp=");
			builder.append(timestamp);
		}
		builder.append("]");
		return builder.toString();
	}
}