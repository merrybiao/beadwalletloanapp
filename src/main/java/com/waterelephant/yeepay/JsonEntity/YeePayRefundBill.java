package com.waterelephant.yeepay.JsonEntity;

/**
 * 退款对账中的退款明细表
 * @author 张诚
 *
 */
public class YeePayRefundBill {
	private String merchantno; //商户编号
	private String requstno; //退款请求号
	private String yborderid; //退款易宝流水号
	private String requsttime; //请求时间
	private String closetime; //清算时间
	private String paymentyborderid; //原扣款易宝流水号
	private String status; //状态REFUND_SUCCESS成功
	private String amount; //金额
	private String remark; //备注
	public String getMerchantno() {
		return merchantno;
	}
	public void setMerchantno(String merchantno) {
		this.merchantno = merchantno;
	}
	public String getRequstno() {
		return requstno;
	}
	public void setRequstno(String requstno) {
		this.requstno = requstno;
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
	public String getClosetime() {
		return closetime;
	}
	public void setClosetime(String closetime) {
		this.closetime = closetime;
	}
	public String getPaymentyborderid() {
		return paymentyborderid;
	}
	public void setPaymentyborderid(String paymentyborderid) {
		this.paymentyborderid = paymentyborderid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
