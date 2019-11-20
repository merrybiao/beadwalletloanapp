package com.waterelephant.dto;

/**
 * 一起好借款申请DTO 20161216
 * @author duxiaoyong
 *
 */
public class YiQiHaoApplyRequestDto {

	private Long orderId;// 工单ID
	private String userRealname;// 借款人姓名
	private String userIdcard;// 借款人身份证号码
	private String bankMobile;// 银行卡预留手机号
	private String bankCode;// 银行卡开户行标识（一起好）
	private String bankCard;// 银行卡号
	private String loanSid;// 工单号
	private Double loanAmount;// 借款金额
	private String loanDeadline;// 借款期限
	private String loanDescription;// 借款描述
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getUserRealname() {
		return userRealname;
	}
	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}
	public String getUserIdcard() {
		return userIdcard;
	}
	public void setUserIdcard(String userIdcard) {
		this.userIdcard = userIdcard;
	}
	public String getBankMobile() {
		return bankMobile;
	}
	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getLoanSid() {
		return loanSid;
	}
	public void setLoanSid(String loanSid) {
		this.loanSid = loanSid;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getLoanDeadline() {
		return loanDeadline;
	}
	public void setLoanDeadline(String loanDeadline) {
		this.loanDeadline = loanDeadline;
	}
	public String getLoanDescription() {
		return loanDescription;
	}
	public void setLoanDescription(String loanDescription) {
		this.loanDescription = loanDescription;
	}
	
	
}
