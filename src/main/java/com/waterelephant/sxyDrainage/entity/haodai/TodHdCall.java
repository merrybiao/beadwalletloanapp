///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.haodai;
//
//import java.util.Date;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
///**
// * TodHdCall.java
// *
// * @author: 王亚楠
// * @since: JDK 1.8
// * @version: 1.0
// * @date: 2018年5月18日
// * @Description: <语音详情>
// * 
// */
//@Table(name = "bw_haodai_call")
//public class TodHdCall {
//
//	/**
//	 * 主键id
//	 */
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	private Long orderId;// 我方订单号
//	private Date createTime;
//	private Date updateTime;
//
//	private String billMonth;// 语音详情月份，格式 yyyy-MM
//	private Integer totalSize;// 该月详情记录总数
//	private String detailsId;// 详情唯一标识
//	private String time;// 通话时间，格式：yyyy-MM-dd HH:mm:ss
//	private String peerNumber;// 对方通话号码
//	private String location;// 通话地(自己的)
//	private String locationType;// 通话地类型. e.g.省内漫游
//	private Integer duration;// 通话时长(单位:秒)
//	private String dialType;// 呼叫类型. DIAL-主叫; DIALED-被叫
//	private Integer fee;// 通话费(单位:分)
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
//	public String getBillMonth() {
//		return billMonth;
//	}
//
//	public void setBillMonth(String billMonth) {
//		this.billMonth = billMonth;
//	}
//
//	public Integer getTotalSize() {
//		return totalSize;
//	}
//
//	public void setTotalSize(Integer totalSize) {
//		this.totalSize = totalSize;
//	}
//
//	public String getDetailsId() {
//		return detailsId;
//	}
//
//	public void setDetailsId(String detailsId) {
//		this.detailsId = detailsId;
//	}
//
//	public String getTime() {
//		return time;
//	}
//
//	public void setTime(String time) {
//		this.time = time;
//	}
//
//	public String getPeerNumber() {
//		return peerNumber;
//	}
//
//	public void setPeerNumber(String peerNumber) {
//		this.peerNumber = peerNumber;
//	}
//
//	public String getLocation() {
//		return location;
//	}
//
//	public void setLocation(String location) {
//		this.location = location;
//	}
//
//	public String getLocationType() {
//		return locationType;
//	}
//
//	public void setLocationType(String locationType) {
//		this.locationType = locationType;
//	}
//
//	public Integer getDuration() {
//		return duration;
//	}
//
//	public void setDuration(Integer duration) {
//		this.duration = duration;
//	}
//
//	public String getDialType() {
//		return dialType;
//	}
//
//	public void setDialType(String dialType) {
//		this.dialType = dialType;
//	}
//
//	public Integer getFee() {
//		return fee;
//	}
//
//	public void setFee(Integer fee) {
//		this.fee = fee;
//	}
//
//}
