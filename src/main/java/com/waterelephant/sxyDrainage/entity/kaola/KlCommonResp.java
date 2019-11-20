///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.kaola;
//
///**
// * 
// * 
// * Module:
// * 
// * KlCommonResp.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <考拉共同返回实体数据>
// */
//public class KlCommonResp {
//
//	private Integer result;// 处理返回结果 1-接收成功 0-接收失败
//	private String message;// 处理返回消息
//	private Integer isSmsVerify;// 是否需要短信验证码 1-需要 0-不需要
//	private String loanId;// 贷款编号
//
//	// 协议接口
//	private String agreementUrl;// 协议地址
//	private Integer agreementShow; // 1-绑卡页面 2-确认借款页面
//
//	// 绑卡结果回调
//	private Integer bindingStatus; // 1-成功；0-失
//	private Integer cardType; // 1-信用卡；2-储蓄卡；
//	private String failReason; //
//
//	// 用户检查接口
//	private Integer isAllow;// 1-允许；其他不允许：2-存在对方有未结清申请；3-存在对方有严重逾期申请；4-命中对方黑名单；5-用户30 天内被对方拒绝过；6-其他；
//	private String refuseReason;// 拒绝原因
//
//	public Integer getIsAllow() {
//		return isAllow;
//	}
//
//	public void setIsAllow(Integer isAllow) {
//		this.isAllow = isAllow;
//	}
//
//	public String getRefuseReason() {
//		return refuseReason;
//	}
//
//	public void setRefuseReason(String refuseReason) {
//		this.refuseReason = refuseReason;
//	}
//
//	public Integer getBindingStatus() {
//		return bindingStatus;
//	}
//
//	public void setBindingStatus(Integer bindingStatus) {
//		this.bindingStatus = bindingStatus;
//	}
//
//	public Integer getCardType() {
//		return cardType;
//	}
//
//	public void setCardType(Integer cardType) {
//		this.cardType = cardType;
//	}
//
//	public String getFailReason() {
//		return failReason;
//	}
//
//	public void setFailReason(String failReason) {
//		this.failReason = failReason;
//	}
//
//	public String getAgreementUrl() {
//		return agreementUrl;
//	}
//
//	public void setAgreementUrl(String agreementUrl) {
//		this.agreementUrl = agreementUrl;
//	}
//
//	public Integer getAgreementShow() {
//		return agreementShow;
//	}
//
//	public void setAgreementShow(Integer agreementShow) {
//		this.agreementShow = agreementShow;
//	}
//
//	public Integer getResult() {
//		return result;
//	}
//
//	public void setResult(Integer result) {
//		this.result = result;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//	public Integer getIsSmsVerify() {
//		return isSmsVerify;
//	}
//
//	public void setIsSmsVerify(Integer isSmsVerify) {
//		this.isSmsVerify = isSmsVerify;
//	}
//
//	public String getLoanId() {
//		return loanId;
//	}
//
//	public void setLoanId(String loanId) {
//		this.loanId = loanId;
//	}
//}
