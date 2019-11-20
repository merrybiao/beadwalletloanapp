package com.waterelephant.yeepay.JsonEntity;

import java.util.List;

/**
 * 失败订单分类信息
 * @author Administrator
 *
 */
public class YeePayQueryErrorMsg {
	private String errorcode;
	private String errormsg;
	private String recordnum;
	private List<String> details;
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
	public String getRecordnum() {
		return recordnum;
	}
	public void setRecordnum(String recordnum) {
		this.recordnum = recordnum;
	}
	public List<String> getDetails() {
		return details;
	}
	public void setDetails(List<String> details) {
		this.details = details;
	}
	

}
