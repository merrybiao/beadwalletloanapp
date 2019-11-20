/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 本软件为武汉水象科技有限公司开发研制。
 * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
 package com.waterelephant.operatorData.xjbk.entity;

 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 import java.io.Serializable;
 import java.util.Date;

/**
 * Module:(code:xjbk001)
 * <p>
 * BwXjbkTripInfo.java
 *
 * @author zhangchong
 * @version 1.0
 * @description: <描述>
 * @since JDK 1.8
 */
 @Table(name = "bw_xjbk_trip_info")
 public class BwXjbkTripInfo implements Serializable {

	 /** */
	 private static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
	 private Long id;
	 private Long orderId;
	 /**
	 * // string 出发地 trip_info
	 */
	 private String tripLeave;
	 /**
	 * // string 目的地 trip_info
	 */
	 private String tripDest;
	 /**
	 * // string 出行时间类型 trip_info
	 */
	 private String tripType;
	 /**
	 * // string 出行开始时间 trip_info
	 */
	 private String tripStartTime;
	 /**
	 * // string 出行结束时间 trip_info
	 */
	 private String tripEndTime;
	 private Date createTime;
	 private Date updateTime;
	
	 public Long getId() {
	 return id;
	 }
	
	 public void setId(Long id) {
	 this.id = id;
	 }
	
	 public Long getOrderId() {
	 return orderId;
	 }
	
	 public void setOrderId(Long orderId) {
	 this.orderId = orderId;
	 }
	
	 public String getTripLeave() {
	 return tripLeave;
	 }
	
	 public void setTripLeave(String tripLeave) {
	 this.tripLeave = tripLeave;
	 }
	
	 public String getTripDest() {
	 return tripDest;
	 }
	
	 public void setTripDest(String tripDest) {
	 this.tripDest = tripDest;
	 }
	
	 public String getTripType() {
	 return tripType;
	 }
	
	 public void setTripType(String tripType) {
	 this.tripType = tripType;
	 }
	
	 public String getTripStartTime() {
	 return tripStartTime;
	 }
	
	 public void setTripStartTime(String tripStartTime) {
	 this.tripStartTime = tripStartTime;
	 }
	
	 public String getTripEndTime() {
	 return tripEndTime;
	 }
	
	 public void setTripEndTime(String tripEndTime) {
	 this.tripEndTime = tripEndTime;
	 }
	
	 public Date getCreateTime() {
	 return createTime;
	 }
	
	 public void setCreateTime(Date createTime) {
	 this.createTime = createTime;
	 }
	
	 public Date getUpdateTime() {
	 return updateTime;
	 }
	
	 public void setUpdateTime(Date updateTime) {
	 this.updateTime = updateTime;
	 }
 }
