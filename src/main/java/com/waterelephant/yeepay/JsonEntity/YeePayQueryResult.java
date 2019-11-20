package com.waterelephant.yeepay.JsonEntity;


import java.util.List;

/**
 * 批量扣款返回结果
 * @author Administrator
 *
 */
public class YeePayQueryResult {
	private String merchantno; //商户编号
	private String merchantbatchno; //商户批次号
	private String ybbatchno; //易宝批次号
	private String batchrecordcount; //批次总笔数
	private String batchtotalamount ; //批次总金额
	private String status; //批次状态
	private String errorcode; //错误代码
	private String errormsg; //错误信息
	private List<YeePaySingleCharge> resultdetails ; //结果明细
//	private String processcount; //处理中的笔数
//	private List<String> requestnoprocess; //处理中的订单号
//	private String successcount; //处理成功的笔数
//	private List<String> requestnosuccess; //处理成功的订单号
//	private String timeoutcount; //超时的总笔数
//	private String requestnotimeout; //处理超时的订单号
//	private String failedcount; //失败的总笔数
//	private String requestnofailed; //失败订单分类信息
	public String getMerchantno() {
		return merchantno;
	}
	public void setMerchantno(String merchantno) {
		this.merchantno = merchantno;
	}
	public String getMerchantbatchno() {
		return merchantbatchno;
	}
	public void setMerchantbatchno(String merchantbatchno) {
		this.merchantbatchno = merchantbatchno;
	}
	public String getYbbatchno() {
		return ybbatchno;
	}
	public void setYbbatchno(String ybbatchno) {
		this.ybbatchno = ybbatchno;
	}
	public String getBatchrecordcount() {
		return batchrecordcount;
	}
	public void setBatchrecordcount(String batchrecordcount) {
		this.batchrecordcount = batchrecordcount;
	}
	public String getBatchtotalamount() {
		return batchtotalamount;
	}
	public void setBatchtotalamount(String batchtotalamount) {
		this.batchtotalamount = batchtotalamount;
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
	public List<YeePaySingleCharge> getResultdetails() {
		return resultdetails;
	}
	public void setResultdetails(List<YeePaySingleCharge> resultdetails) {
		this.resultdetails = resultdetails;
	}
//	public String getProcesscount() {
//		return processcount;
//	}
//	public void setProcesscount(String processcount) {
//		this.processcount = processcount;
//	}
//	public List<String> getRequestnoprocess() {
//		return requestnoprocess;
//	}
//	public void setRequestnoprocess(List<String> requestnoprocess) {
//		this.requestnoprocess = requestnoprocess;
//	}
//	public String getSuccesscount() {
//		return successcount;
//	}
//	public void setSuccesscount(String successcount) {
//		this.successcount = successcount;
//	}
//	public List<String> getRequestnosuccess() {
//		return requestnosuccess;
//	}
//	public String getRequestnofailed() {
//		return requestnofailed;
//	}
//	public void setRequestnofailed(String requestnofailed) {
//		this.requestnofailed = requestnofailed;
//	}
//	public void setRequestnosuccess(List<String> requestnosuccess) {
//		this.requestnosuccess = requestnosuccess;
//	}
//	public String getTimeoutcount() {
//		return timeoutcount;
//	}
//	public void setTimeoutcount(String timeoutcount) {
//		this.timeoutcount = timeoutcount;
//	}
//	public String getRequestnotimeout() {
//		return requestnotimeout;
//	}
//	public void setRequestnotimeout(String requestnotimeout) {
//		this.requestnotimeout = requestnotimeout;
//	}
//	public String getFailedcount() {
//		return failedcount;
//	}
//	public void setFailedcount(String failedcount) {
//		this.failedcount = failedcount;
//	}

	
	
	
}
