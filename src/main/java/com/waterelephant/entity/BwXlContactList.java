// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_xl_contact_list")
// public class BwXlContactList implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;// 订单id
// private String phoneNum;// 号码
// private String phoneNumLoc;// 号码归属地
// private String contactName;// 号码标注
// private String needsType;// 需求类别
// private Integer callCnt;// 通话次数
// private Double callLen;// 通话时长
// private Integer callOutCnt;// 呼出次数
// private Double callOutLen;// 呼出时间
// private Integer callInCnt;// 呼入次数
// private Double callInLen;// 呼入时间
// private String pRelation;// 关系推测
// private Integer contactOnew;// 最近一周联系次数
// private Integer contactOnem;// 最近一月联系次数
// private Integer contactThreem;// 最近三月联系次数
// private Integer contactThreemPlus;// 三个月以上联系次数
// private Integer contactEarlyMorning;// 凌晨联系次数
// private Integer contactMorning;// 上午联系次数
// private Integer contactNoon;// 中午联系次数
// private Integer contactAfternoon;// 下午联系次数
// private Integer contactNight;// 晚上联系次数
// private Integer contactAllDay;// 是否全天联系 0:否，1是
// private Integer contactWeekday;// 周中联系次数
// private Integer contactWeekend;// 周末联系次数
// private Integer contactHoliday;// 节假日联系次数
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
// public void setContactName(String contactName) {
// this.contactName = contactName;
// }
//
// public String getContactName() {
// return contactName;
// }
//
// public void setNeedsType(String needsType) {
// this.needsType = needsType;
// }
//
// public String getNeedsType() {
// return needsType;
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
// public void setCallOutLen(Double callOutLen) {
// this.callOutLen = callOutLen;
// }
//
// public Double getCallOutLen() {
// return callOutLen;
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
// public void setCallInLen(Double callInLen) {
// this.callInLen = callInLen;
// }
//
// public Double getCallInLen() {
// return callInLen;
// }
//
// public void setPRelation(String pRelation) {
// this.pRelation = pRelation;
// }
//
// public String getPRelation() {
// return pRelation;
// }
//
// public void setContactOnew(Integer contactOnew) {
// this.contactOnew = contactOnew;
// }
//
// public Integer getContactOnew() {
// return contactOnew;
// }
//
// public void setContactOnem(Integer contactOnem) {
// this.contactOnem = contactOnem;
// }
//
// public Integer getContactOnem() {
// return contactOnem;
// }
//
// public void setContactThreem(Integer contactThreem) {
// this.contactThreem = contactThreem;
// }
//
// public Integer getContactThreem() {
// return contactThreem;
// }
//
// public void setContactThreemPlus(Integer contactThreemPlus) {
// this.contactThreemPlus = contactThreemPlus;
// }
//
// public Integer getContactThreemPlus() {
// return contactThreemPlus;
// }
//
// public void setContactEarlyMorning(Integer contactEarlyMorning) {
// this.contactEarlyMorning = contactEarlyMorning;
// }
//
// public Integer getContactEarlyMorning() {
// return contactEarlyMorning;
// }
//
// public void setContactMorning(Integer contactMorning) {
// this.contactMorning = contactMorning;
// }
//
// public Integer getContactMorning() {
// return contactMorning;
// }
//
// public void setContactNoon(Integer contactNoon) {
// this.contactNoon = contactNoon;
// }
//
// public Integer getContactNoon() {
// return contactNoon;
// }
//
// public void setContactAfternoon(Integer contactAfternoon) {
// this.contactAfternoon = contactAfternoon;
// }
//
// public Integer getContactAfternoon() {
// return contactAfternoon;
// }
//
// public void setContactNight(Integer contactNight) {
// this.contactNight = contactNight;
// }
//
// public Integer getContactNight() {
// return contactNight;
// }
//
// public void setContactAllDay(Integer contactAllDay) {
// this.contactAllDay = contactAllDay;
// }
//
// public Integer getContactAllDay() {
// return contactAllDay;
// }
//
// public void setContactWeekday(Integer contactWeekday) {
// this.contactWeekday = contactWeekday;
// }
//
// public Integer getContactWeekday() {
// return contactWeekday;
// }
//
// public void setContactWeekend(Integer contactWeekend) {
// this.contactWeekend = contactWeekend;
// }
//
// public Integer getContactWeekend() {
// return contactWeekend;
// }
//
// public void setContactHoliday(Integer contactHoliday) {
// this.contactHoliday = contactHoliday;
// }
//
// public Integer getContactHoliday() {
// return contactHoliday;
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
