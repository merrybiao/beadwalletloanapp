package com.waterelephant.bjsms.entity;

public class RequestShopIngSmsData {
	
	private String secretkey;
	
	private SmsContent data;

	public String getSecretkey() {
		return secretkey;
	}

	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}

	public SmsContent getData() {
		return data;
	}

	public void setData(SmsContent data) {
		this.data = data;
	}

}
