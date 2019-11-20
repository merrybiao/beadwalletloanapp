// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kabao_report")
// public class BwKabaoReport implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Integer lastMonthContactsCount;//
// private String account;//
// private Long contactsCallOutTotalTime;//
// private Long last90DaysCcMobileTime;//
// private Long callTimeInThreeMonth;// 三月内呼入总时长
// private Double usualPhoneEvalValue;// 呼入呼出匹配情况 最小0，最大6
// private Integer callCntInOneMonth;// 一月内通话总次数
// private Integer last90DaysCcMobileCount;//
// private Integer contactTotalCount;// 通话总次数
// private Integer totalContactLabelMarkCount;//
// private String idCardAddr;// 身份证上地址
// private Integer contactsWecashHighriskUserCount;//
// private Integer contactsWecashUserCount;//
// private Integer lastMonthCallInTime;//
// private String isVip;// 是否为vip 1为vip, 0为非vip
// private String billInfoMsg;//
// private String isIdVerified;// 是否实名认证 1表示实名认证， 0表示非实名认证
// private String baseInfoMsg;//
// private Integer callCntThreeMonth;// 三月内通话总次数
// private String phone;//
// private Integer totalCallDayCount;// 有通话总天数
// private Integer callCntOutOneMonth;// 一月内呼出总次数
// private String idCardName;//
// private Integer callCntOutThreeMonth;// 三月内呼出总次数
// private Integer contactsWecashOverdueCount;//
// private Integer regCrawlDays;//
// private String callInfoMsg;// 详单获取情况
// private Double callinfoContactsMatchValue;//
// private Integer contactBlackCount;//
// private Double mobileCostPerMonth;// 账单月平均消费
// private Date registerTime;// 注册时间
// private Date updateTime;//
// private Long contactsCallInTotalTime;// 呼入总时长
// private Long contactTotalTime;// 通话总时长
// private String callPlaceMsg;// 通话地获取情况
// private Integer friendsTotalCount;// 有联系的电话号码数目
// private String phoneNumType;// 运营商类型
// private String idCardNum;//
// private Integer contactsCallInTotalCount;// 呼入总次数
// private Long topThreeContactsAvgTotalTime;// 通话记录往来最频繁前三位号码平均总通话时长，单位为秒
// private Integer contactsCallOutTotalCount;// 呼出总次数
// private Long lastMonthContactsTime;// 一月内通话时长
// private Integer regDays;// 入网天数
// private Long callTimeThreeMonth;// 三月内通话总时长
// private Long callTimeOutThreeMonth;// 三月内呼出总时长
// private Integer callCntInThreeMonth;// 一月内呼入总次数
// private String isOpenDateCrawled;// 运营商入网时间是否爬取
// private Integer totalContactLabelFinanceMarkCount;//
// private Long lastMonthCallOutTime;// 一月内呼出时长
// private String operatorIsAuth;// 是否授权 1为授权, 0为未授权
// private Long orderId;//
// private Date createTime;//
//
// public Integer getLastMonthContactsCount() {
// return lastMonthContactsCount;
// }
//
// public void setLastMonthContactsCount(Integer lastMonthContactsCount) {
// this.lastMonthContactsCount = lastMonthContactsCount;
// }
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public Long getId() {
// return id;
// }
//
// public void setAccount(String account) {
// this.account = account;
// }
//
// public String getAccount() {
// return account;
// }
//
// public void setContactsCallOutTotalTime(Long contactsCallOutTotalTime) {
// this.contactsCallOutTotalTime = contactsCallOutTotalTime;
// }
//
// public Long getContactsCallOutTotalTime() {
// return contactsCallOutTotalTime;
// }
//
// public void setLast90DaysCcMobileTime(Long last90DaysCcMobileTime) {
// this.last90DaysCcMobileTime = last90DaysCcMobileTime;
// }
//
// public Long getLast90DaysCcMobileTime() {
// return last90DaysCcMobileTime;
// }
//
// public void setCallTimeInThreeMonth(Long callTimeInThreeMonth) {
// this.callTimeInThreeMonth = callTimeInThreeMonth;
// }
//
// public Long getCallTimeInThreeMonth() {
// return callTimeInThreeMonth;
// }
//
// public void setUsualPhoneEvalValue(Double usualPhoneEvalValue) {
// this.usualPhoneEvalValue = usualPhoneEvalValue;
// }
//
// public Double getUsualPhoneEvalValue() {
// return usualPhoneEvalValue;
// }
//
// public void setCallCntInOneMonth(Integer callCntInOneMonth) {
// this.callCntInOneMonth = callCntInOneMonth;
// }
//
// public Integer getCallCntInOneMonth() {
// return callCntInOneMonth;
// }
//
// public void setLast90DaysCcMobileCount(Integer last90DaysCcMobileCount) {
// this.last90DaysCcMobileCount = last90DaysCcMobileCount;
// }
//
// public Integer getLast90DaysCcMobileCount() {
// return last90DaysCcMobileCount;
// }
//
// public void setContactTotalCount(Integer contactTotalCount) {
// this.contactTotalCount = contactTotalCount;
// }
//
// public Integer getContactTotalCount() {
// return contactTotalCount;
// }
//
// public void setTotalContactLabelMarkCount(Integer totalContactLabelMarkCount) {
// this.totalContactLabelMarkCount = totalContactLabelMarkCount;
// }
//
// public Integer getTotalContactLabelMarkCount() {
// return totalContactLabelMarkCount;
// }
//
// public void setIdCardAddr(String idCardAddr) {
// this.idCardAddr = idCardAddr;
// }
//
// public String getIdCardAddr() {
// return idCardAddr;
// }
//
// public void setContactsWecashHighriskUserCount(Integer contactsWecashHighriskUserCount) {
// this.contactsWecashHighriskUserCount = contactsWecashHighriskUserCount;
// }
//
// public Integer getContactsWecashHighriskUserCount() {
// return contactsWecashHighriskUserCount;
// }
//
// public void setContactsWecashUserCount(Integer contactsWecashUserCount) {
// this.contactsWecashUserCount = contactsWecashUserCount;
// }
//
// public Integer getContactsWecashUserCount() {
// return contactsWecashUserCount;
// }
//
// public void setLastMonthCallInTime(Integer lastMonthCallInTime) {
// this.lastMonthCallInTime = lastMonthCallInTime;
// }
//
// public Integer getLastMonthCallInTime() {
// return lastMonthCallInTime;
// }
//
// public void setIsVip(String isVip) {
// this.isVip = isVip;
// }
//
// public String getIsVip() {
// return isVip;
// }
//
// public void setBillInfoMsg(String billInfoMsg) {
// this.billInfoMsg = billInfoMsg;
// }
//
// public String getBillInfoMsg() {
// return billInfoMsg;
// }
//
// public void setIsIdVerified(String isIdVerified) {
// this.isIdVerified = isIdVerified;
// }
//
// public String getIsIdVerified() {
// return isIdVerified;
// }
//
// public void setBaseInfoMsg(String baseInfoMsg) {
// this.baseInfoMsg = baseInfoMsg;
// }
//
// public String getBaseInfoMsg() {
// return baseInfoMsg;
// }
//
// public void setCallCntThreeMonth(Integer callCntThreeMonth) {
// this.callCntThreeMonth = callCntThreeMonth;
// }
//
// public Integer getCallCntThreeMonth() {
// return callCntThreeMonth;
// }
//
// public void setPhone(String phone) {
// this.phone = phone;
// }
//
// public String getPhone() {
// return phone;
// }
//
// public void setTotalCallDayCount(Integer totalCallDayCount) {
// this.totalCallDayCount = totalCallDayCount;
// }
//
// public Integer getTotalCallDayCount() {
// return totalCallDayCount;
// }
//
// public void setCallCntOutOneMonth(Integer callCntOutOneMonth) {
// this.callCntOutOneMonth = callCntOutOneMonth;
// }
//
// public Integer getCallCntOutOneMonth() {
// return callCntOutOneMonth;
// }
//
// public void setIdCardName(String idCardName) {
// this.idCardName = idCardName;
// }
//
// public String getIdCardName() {
// return idCardName;
// }
//
// public void setCallCntOutThreeMonth(Integer callCntOutThreeMonth) {
// this.callCntOutThreeMonth = callCntOutThreeMonth;
// }
//
// public Integer getCallCntOutThreeMonth() {
// return callCntOutThreeMonth;
// }
//
// public void setContactsWecashOverdueCount(Integer contactsWecashOverdueCount) {
// this.contactsWecashOverdueCount = contactsWecashOverdueCount;
// }
//
// public Integer getContactsWecashOverdueCount() {
// return contactsWecashOverdueCount;
// }
//
// public void setRegCrawlDays(Integer regCrawlDays) {
// this.regCrawlDays = regCrawlDays;
// }
//
// public Integer getRegCrawlDays() {
// return regCrawlDays;
// }
//
// public void setCallInfoMsg(String callInfoMsg) {
// this.callInfoMsg = callInfoMsg;
// }
//
// public String getCallInfoMsg() {
// return callInfoMsg;
// }
//
// public void setCallinfoContactsMatchValue(Double callinfoContactsMatchValue) {
// this.callinfoContactsMatchValue = callinfoContactsMatchValue;
// }
//
// public Double getCallinfoContactsMatchValue() {
// return callinfoContactsMatchValue;
// }
//
// public void setContactBlackCount(Integer contactBlackCount) {
// this.contactBlackCount = contactBlackCount;
// }
//
// public Integer getContactBlackCount() {
// return contactBlackCount;
// }
//
// public void setMobileCostPerMonth(Double mobileCostPerMonth) {
// this.mobileCostPerMonth = mobileCostPerMonth;
// }
//
// public Double getMobileCostPerMonth() {
// return mobileCostPerMonth;
// }
//
// public void setRegisterTime(Date registerTime) {
// this.registerTime = registerTime;
// }
//
// public Date getRegisterTime() {
// return registerTime;
// }
//
// public void setUpdateTime(Date updateTime) {
// this.updateTime = updateTime;
// }
//
// public Date getUpdateTime() {
// return updateTime;
// }
//
// public void setContactsCallInTotalTime(Long contactsCallInTotalTime) {
// this.contactsCallInTotalTime = contactsCallInTotalTime;
// }
//
// public Long getContactsCallInTotalTime() {
// return contactsCallInTotalTime;
// }
//
// public void setContactTotalTime(Long contactTotalTime) {
// this.contactTotalTime = contactTotalTime;
// }
//
// public Long getContactTotalTime() {
// return contactTotalTime;
// }
//
// public void setCallPlaceMsg(String callPlaceMsg) {
// this.callPlaceMsg = callPlaceMsg;
// }
//
// public String getCallPlaceMsg() {
// return callPlaceMsg;
// }
//
// public void setFriendsTotalCount(Integer friendsTotalCount) {
// this.friendsTotalCount = friendsTotalCount;
// }
//
// public Integer getFriendsTotalCount() {
// return friendsTotalCount;
// }
//
// public void setPhoneNumType(String phoneNumType) {
// this.phoneNumType = phoneNumType;
// }
//
// public String getPhoneNumType() {
// return phoneNumType;
// }
//
// public void setIdCardNum(String idCardNum) {
// this.idCardNum = idCardNum;
// }
//
// public String getIdCardNum() {
// return idCardNum;
// }
//
// public void setContactsCallInTotalCount(Integer contactsCallInTotalCount) {
// this.contactsCallInTotalCount = contactsCallInTotalCount;
// }
//
// public Integer getContactsCallInTotalCount() {
// return contactsCallInTotalCount;
// }
//
// public void setTopThreeContactsAvgTotalTime(Long topThreeContactsAvgTotalTime) {
// this.topThreeContactsAvgTotalTime = topThreeContactsAvgTotalTime;
// }
//
// public Long getTopThreeContactsAvgTotalTime() {
// return topThreeContactsAvgTotalTime;
// }
//
// public void setContactsCallOutTotalCount(Integer contactsCallOutTotalCount) {
// this.contactsCallOutTotalCount = contactsCallOutTotalCount;
// }
//
// public Integer getContactsCallOutTotalCount() {
// return contactsCallOutTotalCount;
// }
//
// public void setLastMonthContactsTime(Long lastMonthContactsTime) {
// this.lastMonthContactsTime = lastMonthContactsTime;
// }
//
// public Long getLastMonthContactsTime() {
// return lastMonthContactsTime;
// }
//
// public void setRegDays(Integer regDays) {
// this.regDays = regDays;
// }
//
// public Integer getRegDays() {
// return regDays;
// }
//
// public void setCallTimeThreeMonth(Long callTimeThreeMonth) {
// this.callTimeThreeMonth = callTimeThreeMonth;
// }
//
// public Long getCallTimeThreeMonth() {
// return callTimeThreeMonth;
// }
//
// public void setCallTimeOutThreeMonth(Long callTimeOutThreeMonth) {
// this.callTimeOutThreeMonth = callTimeOutThreeMonth;
// }
//
// public Long getCallTimeOutThreeMonth() {
// return callTimeOutThreeMonth;
// }
//
// public void setCallCntInThreeMonth(Integer callCntInThreeMonth) {
// this.callCntInThreeMonth = callCntInThreeMonth;
// }
//
// public Integer getCallCntInThreeMonth() {
// return callCntInThreeMonth;
// }
//
// public void setIsOpenDateCrawled(String isOpenDateCrawled) {
// this.isOpenDateCrawled = isOpenDateCrawled;
// }
//
// public String getIsOpenDateCrawled() {
// return isOpenDateCrawled;
// }
//
// public void setTotalContactLabelFinanceMarkCount(Integer totalContactLabelFinanceMarkCount) {
// this.totalContactLabelFinanceMarkCount = totalContactLabelFinanceMarkCount;
// }
//
// public Integer getTotalContactLabelFinanceMarkCount() {
// return totalContactLabelFinanceMarkCount;
// }
//
// public void setLastMonthCallOutTime(Long lastMonthCallOutTime) {
// this.lastMonthCallOutTime = lastMonthCallOutTime;
// }
//
// public Long getLastMonthCallOutTime() {
// return lastMonthCallOutTime;
// }
//
// public void setOperatorIsAuth(String operatorIsAuth) {
// this.operatorIsAuth = operatorIsAuth;
// }
//
// public String getOperatorIsAuth() {
// return operatorIsAuth;
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
// public void setCreateTime(Date createTime) {
// this.createTime = createTime;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
// }
