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
// * FqgjCall.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//@Table(name = "bw_fqgj_call")
//public class FqgjCall implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//	@Id
//	private Long id;
//	private Long orderId;// 订单Id
//	private String tradeType;// 通信类型（本地:1，漫游国内：2，其他：3）
//	private String tradeTime;// 通话时长
//	private String callTime;// 通话时间(精确到秒)
//	private String tradeAddr;// 通话地点
//	private String receivePhone;// 对方号码
//	private String callType;// 呼叫类型（主叫：1，被叫：2，未识别状态：3）
//	private String businessName;// 业务类型
//	private String fee;// 费用
//	private String specialOffer;// 特殊费用
//	private Date createTime; // 创建时间
//	private Date updateTime; // 修改时间
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
//	public String getFee() {
//		return fee;
//	}
//
//	public String getTradeType() {
//		return tradeType;
//	}
//
//	public void setTradeType(String tradeType) {
//		this.tradeType = tradeType;
//	}
//
//	public String getTradeTime() {
//		return tradeTime;
//	}
//
//	public void setTradeTime(String tradeTime) {
//		this.tradeTime = tradeTime;
//	}
//
//	public String getCallTime() {
//		return callTime;
//	}
//
//	public void setCallTime(String callTime) {
//		this.callTime = callTime;
//	}
//
//	public String getTradeAddr() {
//		return tradeAddr;
//	}
//
//	public void setTradeAddr(String tradeAddr) {
//		this.tradeAddr = tradeAddr;
//	}
//
//	public String getReceivePhone() {
//		return receivePhone;
//	}
//
//	public void setReceivePhone(String receivePhone) {
//		this.receivePhone = receivePhone;
//	}
//
//	public String getCallType() {
//		return callType;
//	}
//
//	public void setCallType(String callType) {
//		this.callType = callType;
//	}
//
//	public String getBusinessName() {
//		return businessName;
//	}
//
//	public void setBusinessName(String businessName) {
//		this.businessName = businessName;
//	}
//
//	public String getSpecialOffer() {
//		return specialOffer;
//	}
//
//	public void setSpecialOffer(String specialOffer) {
//		this.specialOffer = specialOffer;
//	}
//
//	public void setFee(String fee) {
//		this.fee = fee;
//	}
//
//}
