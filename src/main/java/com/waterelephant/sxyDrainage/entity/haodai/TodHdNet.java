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
// * TodHdNet.java
// *
// * @author: 王亚楠
// * @since: JDK 1.8
// * @version: 1.0
// * @date: 2018年5月18日
// * @Description: <流量详情>
// * 
// */
//@Table(name = "bw_haodai_net")
//public class TodHdNet {
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
//	private String billMonth;// 详情月份，格式 yyyy-MM
//	private Integer totalSize;// 记录总数
//	private String detailsId;// 详情标识
//	private Date time;// 流量使用时间
//	private Integer duration;// 流量使用时长(单位:秒)
//	private String location;// 流量使用地点
//	private Integer subflow;// 流量使用量，单位:KB
//	private String netType;// 网络类型
//	private String serviceName;// 业务名称
//	private Integer fee;// 通信费(单位:分)
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
//	public Date getTime() {
//		return time;
//	}
//
//	public void setTime(Date time) {
//		this.time = time;
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
//	public String getLocation() {
//		return location;
//	}
//
//	public void setLocation(String location) {
//		this.location = location;
//	}
//
//	public Integer getSubflow() {
//		return subflow;
//	}
//
//	public void setSubflow(Integer subflow) {
//		this.subflow = subflow;
//	}
//
//	public String getNetType() {
//		return netType;
//	}
//
//	public void setNetType(String netType) {
//		this.netType = netType;
//	}
//
//	public String getServiceName() {
//		return serviceName;
//	}
//
//	public void setServiceName(String serviceName) {
//		this.serviceName = serviceName;
//	}
//
//	public Integer getFee() {
//		return fee;
//	}
//
//	public void setFee(Integer fee) {
//		this.fee = fee;
//	}
//}
