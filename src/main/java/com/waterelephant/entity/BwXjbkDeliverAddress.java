/// ******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 本软件为武汉水象科技有限公司开发研制。
// * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
// package com.waterelephant.entity;
//
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
// import java.io.Serializable;
// import java.util.Date;
//
/// **
// * Module:(code:xjbk001)
// * <p>
// * BwXjbkDeliverAddress.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Table(name = "bw_xjbk_deliver_address")
// public class BwXjbkDeliverAddress implements Serializable {
//
// /** */
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
// private Long id;
// private Long orderId;
// /**
// * // string 收货地址 deliver_address
// */
// private String address;
// /**
// * // float 经度 deliver_address
// */
// private Double lng;
// /**
// * // float 纬度 deliver_address
// */
// private Double lat;
// /**
// * // string 地址类型 deliver_address
// */
// private String predictAddrType;
// /**
// * // string 开始送货时间 deliver_address
// */
// private String beginDate;
// /**
// * // string 结束送货时间 deliver_address
// */
// private String endDate;
// /**
// * // float 总送货金额 deliver_address
// */
// private Double totalAmount;
// /**
// * // int 总送货次数 deliver_address
// */
// private Integer totalCount;
// /**
// * // list 收货人列表 deliver_address
// */
// private String receiver;
// /**
// * // int 收获次数 deliver_address
// */
// private Integer count;
// /**
// * // float 收获金额 deliver_address
// */
// private Double amount;
// /**
// * // string 收货人名称 deliver_address
// */
// private String name;
// /**
// * // list.string 收货人号码 deliver_address
// */
// private String phoneNumList;
// private Date createTime;
// private Date updateTime;
//
// public Long getId() {
// return id;
// }
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public Long getOrderId() {
// return orderId;
// }
//
// public void setOrderId(Long orderId) {
// this.orderId = orderId;
// }
//
// public String getAddress() {
// return address;
// }
//
// public void setAddress(String address) {
// this.address = address;
// }
//
// public Double getLng() {
// return lng;
// }
//
// public void setLng(Double lng) {
// this.lng = lng;
// }
//
// public Double getLat() {
// return lat;
// }
//
// public void setLat(Double lat) {
// this.lat = lat;
// }
//
// public String getPredictAddrType() {
// return predictAddrType;
// }
//
// public void setPredictAddrType(String predictAddrType) {
// this.predictAddrType = predictAddrType;
// }
//
// public String getBeginDate() {
// return beginDate;
// }
//
// public void setBeginDate(String beginDate) {
// this.beginDate = beginDate;
// }
//
// public String getEndDate() {
// return endDate;
// }
//
// public void setEndDate(String endDate) {
// this.endDate = endDate;
// }
//
// public Double getTotalAmount() {
// return totalAmount;
// }
//
// public void setTotalAmount(Double totalAmount) {
// this.totalAmount = totalAmount;
// }
//
// public Integer getTotalCount() {
// return totalCount;
// }
//
// public void setTotalCount(Integer totalCount) {
// this.totalCount = totalCount;
// }
//
// public String getReceiver() {
// return receiver;
// }
//
// public void setReceiver(String receiver) {
// this.receiver = receiver;
// }
//
// public Integer getCount() {
// return count;
// }
//
// public void setCount(Integer count) {
// this.count = count;
// }
//
// public Double getAmount() {
// return amount;
// }
//
// public void setAmount(Double amount) {
// this.amount = amount;
// }
//
// public String getName() {
// return name;
// }
//
// public void setName(String name) {
// this.name = name;
// }
//
// public String getPhoneNumList() {
// return phoneNumList;
// }
//
// public void setPhoneNumList(String phoneNumList) {
// this.phoneNumList = phoneNumList;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
//
// public void setCreateTime(Date createTime) {
// this.createTime = createTime;
// }
//
// public Date getUpdateTime() {
// return updateTime;
// }
//
// public void setUpdateTime(Date updateTime) {
// this.updateTime = updateTime;
// }
// }
