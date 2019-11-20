package com.waterelephant.yeepay.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
@Table(name="bw_yeepay_refund_detail")
public class YeepayRefundDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String requestNo; //退款请求号
	
	private String paymentYborderId; //原单笔支付流水号：批量扣款查询接口获得
	
	private String amount; //退款金额
	
	private String remark; //备注
	
	private String status; //状态
	
	private String yborderId; //该笔退款业务的易宝流水号
	
	private String errorCode; //错误代码
	
	private String errorMsg; //错误信息
	
	private String free1;
	
	private String free2;
	
	private String free3;
	
	private String cardTop; //银行卡前6位
	
	private String cardLast; //银行卡后4位
	
	private String bankCode; //银行编码

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getPaymentYborderId() {
		return paymentYborderId;
	}

	public void setPaymentYborderId(String paymentYborderId) {
		this.paymentYborderId = paymentYborderId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getYborderId() {
		return yborderId;
	}

	public void setYborderId(String yborderId) {
		this.yborderId = yborderId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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

	public String getCardTop() {
		return cardTop;
	}

	public void setCardTop(String cardTop) {
		this.cardTop = cardTop;
	}

	public String getCardLast() {
		return cardLast;
	}

	public void setCardLast(String cardLast) {
		this.cardLast = cardLast;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	

	
	
	
	
	
}
