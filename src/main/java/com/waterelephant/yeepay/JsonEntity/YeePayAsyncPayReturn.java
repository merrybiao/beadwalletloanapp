package com.waterelephant.yeepay.JsonEntity;

/**
 * 批量扣款接口异步返回数据
 * @author Administrator
 *
 */
public class YeePayAsyncPayReturn {

	private String errorcode;
	private String errormsg;
	private String free1;
	private String free2;
	private String free3;
	private String merchantbatchno;
	private String merchantno;
	private String resultdetails;
	private String status;
	private String ybbatchno;
	
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
	public String getMerchantbatchno() {
		return merchantbatchno;
	}
	public void setMerchantbatchno(String merchantbatchno) {
		this.merchantbatchno = merchantbatchno;
	}
	public String getMerchantno() {
		return merchantno;
	}
	public void setMerchantno(String merchantno) {
		this.merchantno = merchantno;
	}
	public String getResultdetails() {
		return resultdetails;
	}
	public void setResultdetails(String resultdetails) {
		this.resultdetails = resultdetails;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getYbbatchno() {
		return ybbatchno;
	}
	public void setYbbatchno(String ybbatchno) {
		this.ybbatchno = ybbatchno;
	}
	
	
}
