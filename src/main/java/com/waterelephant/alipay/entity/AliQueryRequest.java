package com.waterelephant.alipay.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
public class AliQueryRequest {
	private String method;
	private String version;
	private String charset;
	private String sign_type;
	private String return_code; // 通信标识服
	private String return_msg; // 返回信息，如非空，为错误原因
	private String appid;
	private String mch_id;
	private String nonce_str;
	private String sign;
	private String result_code;
	private String err_code; 		// 详见错误码列表
	private String err_code_des; 	// 错误信息描述
	private String openid; 			// 买家支付宝用户 ID
	private String fee_type; 		// 货币类型，符合 ISO4217 标准的三位字母代码，默认人 民币：CNY
	private String total_fee; 		// 本次交易支付的订单金额，单位为人民币（分）
	private String coupon_fee;
	private String transaction_id;
	private String out_trade_no;
	private String time_end;	   // yyyyMMddhhmmss
	private String buyer_logon_id; // 买家支付宝账号
	private String fund_bill_list; // 支付成功的各个渠道金额信息，

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	public String getAppid() {
		return appid;
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

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getCoupon_fee() {
		return coupon_fee;
	}

	public void setCoupon_fee(String coupon_fee) {
		this.coupon_fee = coupon_fee;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public String getBuyer_logon_id() {
		return buyer_logon_id;
	}

	public void setBuyer_logon_id(String buyer_logon_id) {
		this.buyer_logon_id = buyer_logon_id;
	}

	public String getFund_bill_list() {
		return fund_bill_list;
	}

	public void setFund_bill_list(String fund_bill_list) {
		this.fund_bill_list = fund_bill_list;
	}

	@Override
	public String toString() {
		return "AliQueryRequest [method=" + method + ", version=" + version + ", charset=" + charset + ", sign_type="
				+ sign_type + ", return_code=" + return_code + ", return_msg=" + return_msg + ", appid=" + appid
				+ ", mch_id=" + mch_id + ", nonce_str=" + nonce_str + ", sign=" + sign + ", result_code=" + result_code
				+ ", err_code=" + err_code + ", err_code_des=" + err_code_des + ", openid=" + openid + ", fee_type="
				+ fee_type + ", total_fee=" + total_fee + ", coupon_fee=" + coupon_fee + ", transaction_id="
				+ transaction_id + ", out_trade_no=" + out_trade_no + ", time_end=" + time_end + ", buyer_logon_id="
				+ buyer_logon_id + ", fund_bill_list=" + fund_bill_list + "]";
	}
}
