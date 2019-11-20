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
// * TodHdFamily.java
// *
// * @author: 王亚楠
// * @since: JDK 1.8
// * @version: 1.0
// * @date: 2018年5月18日
// * @Description: <亲情网>
// * 
// */
//@Table(name = "bw_haodai_family")
//public class TodHdFamily {
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
//	private String familyNum;// 亲情网编号
//	private String longNumber;// 亲情网手机号码
//	private String shortNumber;// 短号
//	private String memberType;// 成员类型. MASTER-家长, MEMBER-成员
//	private Date joinDate;// 加入日期, 格式yyyy-MM-dd
//	private Date expireDate;// 失效日期, 格式yyyy-MM-dd
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
//	public String getFamilyNum() {
//		return familyNum;
//	}
//
//	public void setFamilyNum(String familyNum) {
//		this.familyNum = familyNum;
//	}
//
//	public String getLongNumber() {
//		return longNumber;
//	}
//
//	public void setLongNumber(String longNumber) {
//		this.longNumber = longNumber;
//	}
//
//	public String getShortNumber() {
//		return shortNumber;
//	}
//
//	public void setShortNumber(String shortNumber) {
//		this.shortNumber = shortNumber;
//	}
//
//	public String getMemberType() {
//		return memberType;
//	}
//
//	public void setMemberType(String memberType) {
//		this.memberType = memberType;
//	}
//
//	public Date getJoinDate() {
//		return joinDate;
//	}
//
//	public void setJoinDate(Date joinDate) {
//		this.joinDate = joinDate;
//	}
//
//	public Date getExpireDate() {
//		return expireDate;
//	}
//
//	public void setExpireDate(Date expireDate) {
//		this.expireDate = expireDate;
//	}
//
//}
