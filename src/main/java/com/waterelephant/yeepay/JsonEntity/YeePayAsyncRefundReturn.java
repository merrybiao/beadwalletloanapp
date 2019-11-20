package com.waterelephant.yeepay.JsonEntity;

/**
 * 退款请求的异步通知
 * @author Administrator
 *
 */
public class YeePayAsyncRefundReturn {
	private String amount; //退款金额
	private String bankcode; //银行编码
	private String cardlast; //银行卡后6位
	private String cardtop; //银行卡前4位
	private String errorcode; //错误代码
	private String errormsg; //错误信息
	private String free1;
	private String free2;
	private String free3;
	private String merchantno; //商户编号
	private String requestno; //退款请求号
	private String status; //退款状态
	private String yborderid; //退款易宝流水号
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getCardlast() {
		return cardlast;
	}
	public void setCardlast(String cardlast) {
		this.cardlast = cardlast;
	}
	public String getCardtop() {
		return cardtop;
	}
	public void setCardtop(String cardtop) {
		this.cardtop = cardtop;
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
	public String getFree1() {
		return free1;
	}
	public void setFree1(String free1) {
		this.free1 = free1;
	}
	public String getFree2() {
		return free2;
	}
	public void setFree2(String free2) {
		this.free2 = free2;
	}
	public String getFree3() {
		return free3;
	}
	public void setFree3(String free3) {
		this.free3 = free3;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getYborderid() {
		return yborderid;
	}
	public void setYborderid(String yborderid) {
		this.yborderid = yborderid;
	}
	
	
}
