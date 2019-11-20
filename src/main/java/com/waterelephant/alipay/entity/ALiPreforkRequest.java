package com.waterelephant.alipay.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ALiPreforkRequest {
	private static final String method = "mbupay.alipay.jswap";
	private static final String charset = "UTF-8";
	private String appid; // 应用 ID
	private String mch_id; // 商户号
	private String nonce_str; // 随机字符串
	private String sign; // 签名
	private String body; // 商品描述
	private String out_trade_no; // 商户订单号
	private String total_fee; // 总金额
	private String notify_url; // 接收支付宝支付结果通知
	private String return_url; // 支付完成跳转地址

	public String getAppid() {
		return appid;
	}

	public static String getMethod() {
		return method;
	}

	public static String getCharset() {
		return charset;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

}
