package com.waterelephant.drainage.entity.qihu360;

public class PushUserInfo {
	private String id_card;// 掩码身份证（隐藏后 5 位）
	private String user_mobile;// 掩码手机号（隐藏后 4 位）
	private String user_name;// 用户真实姓名
	private String product_id;// 产品 id，由 360 分配给机构产品的 ID 号

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

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

}
