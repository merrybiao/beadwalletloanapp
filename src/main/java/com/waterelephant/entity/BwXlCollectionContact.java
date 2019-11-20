// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_xl_collection_contact")
// public class BwXlCollectionContact implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;// 订单id
// private String contactName;// 联系人姓名
// private String beginDate;// 最早出现时间
// private String endDate;// 最晚出现时间
// private Integer totalCount;// 电商送货总数
// private Double totalAmount;// 电商送货总金额
// private String contactDetails;// 呼叫信息统计
// private String phoneNum;// 电话号码
// private String phoneNumLoc;// 号码归属地
// private Integer callCnt;// 呼叫次数
// private Double callLen;// 呼叫时长
// private Integer callOutCnt;// 呼出次数
// private Integer callInCnt;// 呼入次数
// private Integer smsCnt;// 短信条数
// private String transStart;// 最早沟通时间
// private String transEnd;// 最早沟通时间
// private Date createTime;//
// private Date updateTime;//
//
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public Long getId() {
// return id;
// }
//
// public void setOrderId(Long orderId) {
// this.orderId = orderId;
// }
//
// public Long getOrderId() {
// return orderId;
// }
//
// public void setContactName(String contactName) {
// this.contactName = contactName;
// }
//
// public String getContactName() {
// return contactName;
// }
//
// public void setBeginDate(String beginDate) {
// this.beginDate = beginDate;
// }
//
// public String getBeginDate() {
// return beginDate;
// }
//
// public void setEndDate(String endDate) {
// this.endDate = endDate;
// }
//
// public String getEndDate() {
// return endDate;
// }
//
// public void setTotalCount(Integer totalCount) {
// this.totalCount = totalCount;
// }
//
// public Integer getTotalCount() {
// return totalCount;
// }
//
// public void setTotalAmount(Double totalAmount) {
// this.totalAmount = totalAmount;
// }
//
// public Double getTotalAmount() {
// return totalAmount;
// }
//
// public void setContactDetails(String contactDetails) {
// this.contactDetails = contactDetails;
// }
//
// public String getContactDetails() {
// return contactDetails;
// }
//
// public void setPhoneNum(String phoneNum) {
// this.phoneNum = phoneNum;
// }
//
// public String getPhoneNum() {
// return phoneNum;
// }
//
// public void setPhoneNumLoc(String phoneNumLoc) {
// this.phoneNumLoc = phoneNumLoc;
// }
//
// public String getPhoneNumLoc() {
// return phoneNumLoc;
// }
//
// public void setCallCnt(Integer callCnt) {
// this.callCnt = callCnt;
// }
//
// public Integer getCallCnt() {
// return callCnt;
// }
//
// public void setCallLen(Double callLen) {
// this.callLen = callLen;
// }
//
// public Double getCallLen() {
// return callLen;
// }
//
// public void setCallOutCnt(Integer callOutCnt) {
// this.callOutCnt = callOutCnt;
// }
//
// public Integer getCallOutCnt() {
// return callOutCnt;
// }
//
// public void setCallInCnt(Integer callInCnt) {
// this.callInCnt = callInCnt;
// }
//
// public Integer getCallInCnt() {
// return callInCnt;
// }
//
// public void setSmsCnt(Integer smsCnt) {
// this.smsCnt = smsCnt;
// }
//
// public Integer getSmsCnt() {
// return smsCnt;
// }
//
// public void setTransStart(String transStart) {
// this.transStart = transStart;
// }
//
// public String getTransStart() {
// return transStart;
// }
//
// public void setTransEnd(String transEnd) {
// this.transEnd = transEnd;
// }
//
// public String getTransEnd() {
// return transEnd;
// }
//
// public void setCreateTime(Date createTime) {
// this.createTime = createTime;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
//
// public void setUpdateTime(Date updateTime) {
// this.updateTime = updateTime;
// }
//
// public Date getUpdateTime() {
// return updateTime;
// }
// }
