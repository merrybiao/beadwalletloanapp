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
// * BwXjbkContactList.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Table(name = "bw_xjbk_contact_list")
// public class BwXjbkContactList implements Serializable {
//
// /** */
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
// private Long id;
// private Long orderId;
// /**
// * // string 号码 contact_list
// */
// private String phoneNum;
// /**
// * // string 号码归属地 contact_list
// */
// private String phoneNumLoc;
// /**
// * // string 号码标注 contact_list
// */
// private String contactName;
// /**
// * // string 需求类别 contact_list
// */
// private String needsType;
// /**
// * // Integer 通话次数 contact_list
// */
// private Integer callCnt;
// /**
// * // float 通话时长 contact_list
// */
// private Double callLen;
// /**
// * // Integer 呼出次数 contact_list
// */
// private Integer callOutCnt;
// /**
// * // float 呼出时间 contact_list
// */
// private Double callOutLen;
// /**
// * // Integer 呼入次数 contact_list
// */
// private Integer callInCnt;
// /**
// * // float 呼入时间 contact_list
// */
// private Double callInLen;
// /**
// * // string 关系推测 contact_list
// */
// private String pRelation;
// /**
// * // Integer 最近一周联系次数 contact_list
// */
// private Integer contactOnew;
// /**
// * // Integer 最近一月联系次数 contact_list
// */
// private Integer contactOnem;
// /**
// * // Integer 最近三月联系次数 contact_list
// */
// private Integer contactThreem;
// /**
// * // Integer 三个月以上联系次数 contact_list
// */
// private Integer contactThreemPlus;
// /**
// * // Integer 凌晨联系次数 contact_list
// */
// private Integer contactEarlyMorning;
// /**
// * // Integer 上午联系次数 contact_list
// */
// private Integer contactMorning;
// /**
// * // Integer 中午联系次数 contact_list
// */
// private Integer contactNoon;
// /**
// * // Integer 下午联系次数 contact_list
// */
// private Integer contactAfternoon;
// /**
// * // Integer 晚上联系次数 contact_list
// */
// private Integer contactNight;
// /**
// * // bool 是否全天联系 contact_list
// */
// private Integer contactAllDay;
// /**
// * // int 周中联系次数 contact_list
// */
// private Integer contactWeekday;
// /**
// * // int 周末联系次数 contact_list
// */
// private Integer contactWeekend;
// /**
// * // int 节假日联系次数 contact_list
// */
// private Integer contactHoliday;
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
// public String getContactName() {
// return contactName;
// }
//
// public void setContactName(String contactName) {
// this.contactName = contactName;
// }
//
// public String getNeedsType() {
// return needsType;
// }
//
// public void setNeedsType(String needsType) {
// this.needsType = needsType;
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
// public Double getCallOutLen() {
// return callOutLen;
// }
//
// public void setCallOutLen(Double callOutLen) {
// this.callOutLen = callOutLen;
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
// public Double getCallInLen() {
// return callInLen;
// }
//
// public void setCallInLen(Double callInLen) {
// this.callInLen = callInLen;
// }
//
// public String getpRelation() {
// return pRelation;
// }
//
// public void setpRelation(String pRelation) {
// this.pRelation = pRelation;
// }
//
// public Integer getContactOnew() {
// return contactOnew;
// }
//
// public void setContactOnew(Integer contactOnew) {
// this.contactOnew = contactOnew;
// }
//
// public Integer getContactOnem() {
// return contactOnem;
// }
//
// public void setContactOnem(Integer contactOnem) {
// this.contactOnem = contactOnem;
// }
//
// public Integer getContactThreem() {
// return contactThreem;
// }
//
// public void setContactThreem(Integer contactThreem) {
// this.contactThreem = contactThreem;
// }
//
// public Integer getContactThreemPlus() {
// return contactThreemPlus;
// }
//
// public void setContactThreemPlus(Integer contactThreemPlus) {
// this.contactThreemPlus = contactThreemPlus;
// }
//
// public Integer getContactEarlyMorning() {
// return contactEarlyMorning;
// }
//
// public void setContactEarlyMorning(Integer contactEarlyMorning) {
// this.contactEarlyMorning = contactEarlyMorning;
// }
//
// public Integer getContactMorning() {
// return contactMorning;
// }
//
// public void setContactMorning(Integer contactMorning) {
// this.contactMorning = contactMorning;
// }
//
// public Integer getContactNoon() {
// return contactNoon;
// }
//
// public void setContactNoon(Integer contactNoon) {
// this.contactNoon = contactNoon;
// }
//
// public Integer getContactAfternoon() {
// return contactAfternoon;
// }
//
// public void setContactAfternoon(Integer contactAfternoon) {
// this.contactAfternoon = contactAfternoon;
// }
//
// public Integer getContactNight() {
// return contactNight;
// }
//
// public void setContactNight(Integer contactNight) {
// this.contactNight = contactNight;
// }
//
// public Integer getContactAllDay() {
// return contactAllDay;
// }
//
// public void setContactAllDay(Integer contactAllDay) {
// this.contactAllDay = contactAllDay;
// }
//
// public Integer getContactWeekday() {
// return contactWeekday;
// }
//
// public void setContactWeekday(Integer contactWeekday) {
// this.contactWeekday = contactWeekday;
// }
//
// public Integer getContactWeekend() {
// return contactWeekend;
// }
//
// public void setContactWeekend(Integer contactWeekend) {
// this.contactWeekend = contactWeekend;
// }
//
// public Integer getContactHoliday() {
// return contactHoliday;
// }
//
// public void setContactHoliday(Integer contactHoliday) {
// this.contactHoliday = contactHoliday;
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
