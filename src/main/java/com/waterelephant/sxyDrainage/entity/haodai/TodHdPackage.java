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
// * TodHdPackage.java
// *
// * @author: 王亚楠
// * @since: JDK 1.8
// * @version: 1.0
// * @date: 2018年5月18日
// * @Description: <运营商套餐字段>
// * 
// */
//@Table(name = "bw_haodai_package")
//public class TodHdPackage {
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
//	private Date billStartDate;// 账单起始日, 格式为yyyy-MM-dd
//	private Date billEndDate;// 账单结束日, 格式为yyyy-MM-dd
//	private String item;// 套餐项目名称
//	private String total;// 套餐项目总量
//	private String used;// 套餐项目已使用量
//	private String unit;// 套餐项目单位：语音-分; 流量-KB; 短/彩信-条
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
//	public Date getBillStartDate() {
//		return billStartDate;
//	}
//
//	public void setBillStartDate(Date billStartDate) {
//		this.billStartDate = billStartDate;
//	}
//
//	public Date getBillEndDate() {
//		return billEndDate;
//	}
//
//	public void setBillEndDate(Date billEndDate) {
//		this.billEndDate = billEndDate;
//	}
//
//	public String getItem() {
//		return item;
//	}
//
//	public void setItem(String item) {
//		this.item = item;
//	}
//
//	public String getTotal() {
//		return total;
//	}
//
//	public void setTotal(String total) {
//		this.total = total;
//	}
//
//	public String getUsed() {
//		return used;
//	}
//
//	public void setUsed(String used) {
//		this.used = used;
//	}
//
//	public String getUnit() {
//		return unit;
//	}
//
//	public void setUnit(String unit) {
//		this.unit = unit;
//	}
//
//}
