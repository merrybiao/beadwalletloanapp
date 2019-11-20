package com.waterelephant.yeepay.JsonEntity;

/**
 * 扣款对账接口返回的扣款明细单个实体对象
 * 手续费具体分为鉴权手续费，和支付手续费
 * 鉴权手续费为0.00
 * @author 张诚
 *
 */
public class YeePayPayBill {
	private String merchantno;//商户编号
	private String requestno;//商户请求号
	private String yborderid;//扣款交易的易宝流水号，唯一标识
	private String requsttime;//请求时间
	private String amount;//扣款金额
	private String AAA;//鉴权费用
	private String procedureFee;//支付手续费
	private String closetime;//清算时间
	private String merchantbatchno;//商户批次号
	private String ybbatchno;//易宝批次号
	private String remark;//备注（free1，2，3）
	public String getAAA() {
		return AAA;
	}
	public void setAAA(String aAA) {
		AAA = aAA;
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
	public String getYborderid() {
		return yborderid;
	}
	public void setYborderid(String yborderid) {
		this.yborderid = yborderid;
	}
	public String getRequsttime() {
		return requsttime;
	}
	public void setRequsttime(String requsttime) {
		this.requsttime = requsttime;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getProcedureFee() {
		return procedureFee;
	}
	public void setProcedureFee(String procedureFee) {
		this.procedureFee = procedureFee;
	}
	public String getClosetime() {
		return closetime;
	}
	public void setClosetime(String closetime) {
		this.closetime = closetime;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
