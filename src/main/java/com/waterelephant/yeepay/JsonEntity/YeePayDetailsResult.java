package com.waterelephant.yeepay.JsonEntity;

import java.util.List;

/**
 * 批量扣款对账查询实际返回结果
 * @author 张诚
 *
 */
public class YeePayDetailsResult {
	private String merchantno;//商户编号
	private String startdate;//开始时间
	private String enddate;//结束时间
	private String resultdata;//具体结果
	private String errorcode;//错误代码
	private String errormsg;//错误信息
	private String totalRequstNum;	//扣款总笔数
	private String totalPayAmount; //扣款总金额
	private String totalProcedureFee; //总手续费
	private List<YeePayPayBill> list;//扣款明细
	public String getMerchantno() {
		return merchantno;
	}
	public void setMerchantno(String merchantno) {
		this.merchantno = merchantno;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getResultdata() {
		return resultdata;
	}
	public void setResultdata(String resultdata) {
		this.resultdata = resultdata;
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
	public String getTotalRequstNum() {
		return totalRequstNum;
	}
	public void setTotalRequstNum(String totalRequstNum) {
		this.totalRequstNum = totalRequstNum;
	}

	public String getTotalPayAmount() {
		return totalPayAmount;
	}
	public void setTotalPayAmount(String totalPayAmount) {
		this.totalPayAmount = totalPayAmount;
	}
	public String getTotalProcedureFee() {
		return totalProcedureFee;
	}
	public void setTotalProcedureFee(String totalProcedureFee) {
		this.totalProcedureFee = totalProcedureFee;
	}
	public List<YeePayPayBill> getList() {
		return list;
	}
	public void setList(List<YeePayPayBill> list) {
		this.list = list;
	}
	
	
}
