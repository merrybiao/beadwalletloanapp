package com.waterelephant.yeepay.JsonEntity;

/**
 * 单笔扣款异步通知结果和单笔扣款查询接口返回数据一样的格式
 * @author Administrator
 *
 */
public class YeePayAsyncSinglePayResult {

	private String merchantno; //商户编号
	private String requestno; //还款请求号
	private String yborderid; //易宝流水号
	private String status; //订单状态
	private String amount; //金额
	private String cardtop; //卡号前六位
	private String cardlast; //卡号后四位
	private String bankcode; //银行编码
	private String errorcode; //错误码
	private String errormsg; //错误信息
	private String banksuccessdate;//完成扣款时间
	public String getBanksuccessdate() {
		return banksuccessdate;
	}
	public void setBanksuccessdate(String banksuccessdate) {
		this.banksuccessdate = banksuccessdate;
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
	public String getCardtop() {
		return cardtop;
	}
	public void setCardtop(String cardtop) {
		this.cardtop = cardtop;
	}
	public String getCardlast() {
		return cardlast;
	}
	public void setCardlast(String cardlast) {
		this.cardlast = cardlast;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
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
