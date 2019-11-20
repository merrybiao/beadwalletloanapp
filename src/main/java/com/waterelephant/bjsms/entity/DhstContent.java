package com.waterelephant.bjsms.entity;

public class DhstContent {
	
	/*{"phones": "15827167589","content": "您好，您的手机验证码为：876542。", "sign": "【速秒钱包】"}*/

	private String phones;
	
	private String content;
	
	private String sign;

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
