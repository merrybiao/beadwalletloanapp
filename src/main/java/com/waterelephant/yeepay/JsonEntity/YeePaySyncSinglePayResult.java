package com.waterelephant.yeepay.JsonEntity;

/**
 * 单笔扣款同步返回数据
 * @author Administrator
 *
 */
public class YeePaySyncSinglePayResult {
	
	private String merchantno; //商户编号
	private String requestno; //还款请求号(单笔还款唯一标识符)
	private String codesender; //短信发送方
	private String smstype; //实际短信发送类型
	private String status; //订单状态
	private String errorcode; //错误码
	private String errormsg; //错误信息
	public String getMerchantno() {
		return merchantno;
	}
	public void setMerchantno(String merchantno) {
		this.merchantno = merchantno;
	}
	public String getRequestno() {
		return requestno;
	}
	public void setRequestno(String requestno) {
		this.requestno = requestno;
	}
	public String getCodesender() {
		return codesender;
	}
	public void setCodesender(String codesender) {
		this.codesender = codesender;
	}
	public String getSmstype() {
		return smstype;
	}
	public void setSmstype(String smstype) {
		this.smstype = smstype;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	
}
