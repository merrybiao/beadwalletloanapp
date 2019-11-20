package com.waterelephant.drainage.entity.youyu;

/***
 * 
 * 
 * 
 * Module:
 * 
 * YouyuRequest.java
 * 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class YouyuRequest {
	private String sign; // 签名
	private String code; // 1成功，其他失败
	private String channel; // 渠道ID
	private String biz_enc; // 是否加密：1加密 其他不加密
	private String biz_data; // 业务数据
	private String des_key; // rsa加密后的密钥

	public String getDes_key() {
		return des_key;
	}

	public void setDes_key(String des_key) {
		this.des_key = des_key;
	}
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getBiz_enc() {
		return biz_enc;
	}

	public void setBiz_enc(String biz_enc) {
		this.biz_enc = biz_enc;
	}

	public String getBiz_data() {
		return biz_data;
	}

	public void setBiz_data(String biz_data) {
		this.biz_data = biz_data;
	}

}
