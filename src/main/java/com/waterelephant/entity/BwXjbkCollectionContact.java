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
// * BwXjbkCollectionContact.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Table(name = "bw_xjbk_collection_contact")
// public class BwXjbkCollectionContact implements Serializable {
//
// /** */
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
// private Long id;
// private Long orderId;
// /**
// * // string 联系人姓名 collection_contact
// */
// private String contactName;
// /**
// * // string 最早出现时间 collection_contact
// */
// private String beginDate;
// /**
// * // string 最晚出现时间 collection_contact
// */
// private String endDate;
// /**
// * // int 电商送货总数 collection_contact
// */
// private Integer totalCount;
// /**
// * // float 电商送货总金额 collection_contact
// */
// private Double totalAmount;
// /**
// * // list 呼叫信息统计 collection_contact
// */
// private String contactDetails;
// /**
// * // string 电话号码 collection_contact
// */
// private String phoneNum;
// /**
// * // string 号码归属地 collection_contact
// */
// private String phoneNumLoc;
// /**
// * // int 呼叫次数 collection_contact
// */
// private Integer callCnt;
// /**
// * // float 呼叫时长 collection_contact
// */
// private Double callLen;
// /**
// * // int 呼出次数 collection_contact
// */
// private Integer callOutCnt;
// /**
// * // int 呼入次数 collection_contact
// */
// private Integer callInCnt;
// /**
// * // int 短信条数 collection_contact
// */
// private Integer smsCnt;
// /**
// * // string 最早沟通时间 collection_contact
// */
// private String transStart;
// /**
// * // string 最早沟通时间 collection_contact
// */
// private String transEnd;
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
// public String getContactName() {
// return contactName;
// }
//
// public void setContactName(String contactName) {
// this.contactName = contactName;
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
// public Integer getTotalCount() {
// return totalCount;
// }
//
// public void setTotalCount(Integer totalCount) {
// this.totalCount = totalCount;
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
// public String getContactDetails() {
// return contactDetails;
// }
//
// public void setContactDetails(String contactDetails) {
// this.contactDetails = contactDetails;
// }
//
// public String getPhoneNum() {
// return phoneNum;
// }
//
// public void setPhoneNum(String phoneNum) {
// this.phoneNum = phoneNum;
// }
//
// public String getPhoneNumLoc() {
// return phoneNumLoc;
// }
//
// public void setPhoneNumLoc(String phoneNumLoc) {
// this.phoneNumLoc = phoneNumLoc;
// }
//
// public Integer getCallCnt() {
// return callCnt;
// }
//
// public void setCallCnt(Integer callCnt) {
// this.callCnt = callCnt;
// }
//
// public Double getCallLen() {
// return callLen;
// }
//
// public void setCallLen(Double callLen) {
// this.callLen = callLen;
// }
//
// public Integer getCallOutCnt() {
// return callOutCnt;
// }
//
// public void setCallOutCnt(Integer callOutCnt) {
// this.callOutCnt = callOutCnt;
// }
//
// public Integer getCallInCnt() {
// return callInCnt;
// }
//
// public void setCallInCnt(Integer callInCnt) {
// this.callInCnt = callInCnt;
// }
//
// public Integer getSmsCnt() {
// return smsCnt;
// }
//
// public void setSmsCnt(Integer smsCnt) {
// this.smsCnt = smsCnt;
// }
//
// public String getTransStart() {
// return transStart;
// }
//
// public void setTransStart(String transStart) {
// this.transStart = transStart;
// }
//
// public String getTransEnd() {
// return transEnd;
// }
//
// public void setTransEnd(String transEnd) {
// this.transEnd = transEnd;
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
