package com.waterelephant.bjsms.entity;

public class RequestSmsData {
	
	private String secretkey;
	
	private SmsContent content;

	public String getSecretkey() {
		return secretkey;
	}

	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}

	public SmsContent getContent() {
		return content;
	}

	public void setContent(SmsContent content) {
		this.content = content;
	}

	
}
