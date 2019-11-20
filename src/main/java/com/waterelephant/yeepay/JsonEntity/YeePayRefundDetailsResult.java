package com.waterelephant.yeepay.JsonEntity;

import java.util.List;

/**
 * 退款对账详细结果
 * @author Administrator
 *
 */
public class YeePayRefundDetailsResult {
	private String merchantno;//商户编号
	private String startdate;//开始时间
	private String enddate;//结束时间
	private String resultdata;//具体结果
	private String errorcode;//错误代码
	private String errormsg;//错误信息
	private String totalRequstNum;	//退款总笔数
	private String totalRefundAmount; //退款总金额
	private List<YeePayRefundBill> list;//退款明细
	
	public String getTotalRequstNum() {
		return totalRequstNum;
	}
	public void setTotalRequstNum(String totalRequstNum) {
		this.totalRequstNum = totalRequstNum;
	}
	public String getTotalRefundAmount() {
		return totalRefundAmount;
	}
	public void setTotalRefundAmount(String totalRefundAmount) {
		this.totalRefundAmount = totalRefundAmount;
	}
	public List<YeePayRefundBill> getList() {
		return list;
	}
	public void setList(List<YeePayRefundBill> list) {
		this.list = list;
	}
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
	

}
