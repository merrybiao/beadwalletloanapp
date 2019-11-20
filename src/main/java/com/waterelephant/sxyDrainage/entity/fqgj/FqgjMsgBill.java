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
// * FqgjMsgBill.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//@Table(name = "bw_fqgj_msg_bill")
//public class FqgjMsgBill implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//	@Id
//	private Long id;// 主键ID
//	private Long orderId;// 订单Id
//	private String sendTime;// 发送时间
//	private String tradeWay;// 短信状态（发送：1；接收：2；未识别状态：3 ）
//	private String receiverPhone;// 对方号码
//	private String businessName;// 业务类型
//	private String fee;// 费用
//	private String tradeAddr;// 短信地址
//	private String tradeType;// 类型1：短信 2：彩信 3：其他
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
//	public String getSendTime() {
//		return sendTime;
//	}
//
//	public void setSendTime(String sendTime) {
//		this.sendTime = sendTime;
//	}
//
//	public String getTradeWay() {
//		return tradeWay;
//	}
//
//	public void setTradeWay(String tradeWay) {
//		this.tradeWay = tradeWay;
//	}
//
//	public String getReceiverPhone() {
//		return receiverPhone;
//	}
//
//	public void setReceiverPhone(String receiverPhone) {
//		this.receiverPhone = receiverPhone;
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
//	public String getFee() {
//		return fee;
//	}
//
//	public void setFee(String fee) {
//		this.fee = fee;
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
//	public String getTradeType() {
//		return tradeType;
//	}
//
//	public void setTradeType(String tradeType) {
//		this.tradeType = tradeType;
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
//}
