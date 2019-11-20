package com.waterelephant.drainage.entity.qihu360;

public class QiHu360Request {
	private String sign;// 签名值
	private String biz_enc;// biz_data 加密方式（0 不加密，1 加密:采用 DES 加密算法）
	private String des_key;// RSA 加密后的密钥（biz_enc 为 1 时为必传）
	private String merchant_id;// 合作机构给 360 分配的商户号
	private String biz_data;// 业务参数

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBiz_enc() {
		return biz_enc;
	}

	public void setBiz_enc(String biz_enc) {
		this.biz_enc = biz_enc;
	}

	public String getDes_key() {
		return des_key;
	}

	public void setDes_key(String des_key) {
		this.des_key = des_key;
	}

	public String getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}

	public String getBiz_data() {
		return biz_data;
	}

	public void setBiz_data(String biz_data) {
		this.biz_data = biz_data;
	}

}
