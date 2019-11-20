package com.waterelephant.yeepay.JsonEntity;

/**
 * 批量扣款异步通知的明细
 * @author Administrator
 *
 */
public class YeePayAsyncPaySingle {

	private String amount;
	private String bankcode;
	private String banksuccessdate;
	private String cardlast;
	private String cardtop;
	private String errorcode;
	private String errormsg;
	private String requestno;
	private String status;
	private String yborderid;
	

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
	public String getBanksuccessdate() {
		return banksuccessdate;
	}
	public void setBanksuccessdate(String banksuccessdate) {
		this.banksuccessdate = banksuccessdate;
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
