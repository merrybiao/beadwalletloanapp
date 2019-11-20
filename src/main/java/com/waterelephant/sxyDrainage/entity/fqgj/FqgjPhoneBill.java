///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.fqgj;
//
//import java.io.Serializable;
//import java.util.Date;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
///**
// * 
// * 
// * Module:
// * 
// * FqgjPhoneBill.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//@Table(name = "bw_fqgj_phone_bill")
//public class FqgjPhoneBill implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//	@Id
//	private Long id;// 主键ID
//	private Long orderId;// 订单Id
//	private String phone; // 手机号
//	private String addtionalFee; // 增值业务费
//	private String callPay; // 当月话费
//	private String month; // 月份
//	private String msgFee; // 额外套餐费-短信
//	private String netFee; // 额外套餐费-流量
//	private String packageFee; // 套餐及固定费用
//	private String preferentialFee; // 优惠费
//	private String telFee; // 额外套餐费-通话
//	private String generationFee;// 代收费
//	private String otherFee;// 其它费用
//	private String score;// 当月积分
//	private Date createTime; // 创建时间
//	private Date updateTime; // 修改时间
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Long getOrderId() {
//		return orderId;
//	}
//
//	public void setOrderId(Long orderId) {
//		this.orderId = orderId;
//	}
//
//	public String getPhone() {
//		return phone;
//	}
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//
//	public String getAddtionalFee() {
//		return addtionalFee;
//	}
//
//	public void setAddtionalFee(String addtionalFee) {
//		this.addtionalFee = addtionalFee;
//	}
//
//	public String getCallPay() {
//		return callPay;
//	}
//
//	public void setCallPay(String callPay) {
//		this.callPay = callPay;
//	}
//
//	public String getMonth() {
//		return month;
//	}
//
//	public void setMonth(String month) {
//		this.month = month;
//	}
//
//	public String getMsgFee() {
//		return msgFee;
//	}
//
//	public void setMsgFee(String msgFee) {
//		this.msgFee = msgFee;
//	}
//
//	public String getNetFee() {
//		return netFee;
//	}
//
//	public void setNetFee(String netFee) {
//		this.netFee = netFee;
//	}
//
//	public String getPackageFee() {
//		return packageFee;
//	}
//
//	public void setPackageFee(String packageFee) {
//		this.packageFee = packageFee;
//	}
//
//	public String getPreferentialFee() {
//		return preferentialFee;
//	}
//
//	public void setPreferentialFee(String preferentialFee) {
//		this.preferentialFee = preferentialFee;
//	}
//
//	public String getTelFee() {
//		return telFee;
//	}
//
//	public void setTelFee(String telFee) {
//		this.telFee = telFee;
//	}
//
//	public Date getCreateTime() {
//		return createTime;
//	}
//
//	public void setCreateTime(Date createTime) {
//		this.createTime = createTime;
//	}
//
//	public Date getUpdateTime() {
//		return updateTime;
//	}
//
//	public void setUpdateTime(Date updateTime) {
//		this.updateTime = updateTime;
//	}
//
//	public String getGenerationFee() {
//		return generationFee;
//	}
//
//	public void setGenerationFee(String generationFee) {
//		this.generationFee = generationFee;
//	}
//
//	public String getOtherFee() {
//		return otherFee;
//	}
//
//	public void setOtherFee(String otherFee) {
//		this.otherFee = otherFee;
//	}
//
//	public String getScore() {
//		return score;
//	}
//
//	public void setScore(String score) {
//		this.score = score;
//	}
//
//}
