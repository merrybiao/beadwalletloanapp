package com.waterelephant.rong360.entity;
@Deprecated
public class RongOldUserBizData {
	private String id_card;
	private String user_mobile;
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public String getUser_mobile() {
		return user_mobile;
	}
	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OldUser [");
		if (id_card != null) {
			builder.append("id_card=");
			builder.append(id_card);
			builder.append(", ");
		}
		if (user_mobile != null) {
			builder.append("user_mobile=");
			builder.append(user_mobile);
		}
		builder.append("]");
		return builder.toString();
	}
}