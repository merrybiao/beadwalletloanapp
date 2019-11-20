///**
//  * Copyright 2018 aTool.org 
//  */
//package com.waterelephant.sxyDrainage.entity.kabao;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import org.codehaus.jackson.annotate.JsonProperty;
///**
// * Auto-generated: 2018-06-07 14:28:14
// *
// * @author aTool.org (i@aTool.org)
// * @website http://www.atool.org/json2javabean.php
// */
//public class OperatorProtypeData {
//
//    @JsonProperty("last_month_contacts_count")
//    private int lastMonthContactsCount;
//    @JsonProperty("contacts_call_out_total_time")
//    private int contactsCallOutTotalTime;
//    @JsonProperty("last_90_days_cc_mobile_time")
//    private int last90DaysCcMobileTime;
//    @JsonProperty("call_time_in_three_month")
//    private int callTimeInThreeMonth;
//    @JsonProperty("usual_phone_eval_value")
//    private double usualPhoneEvalValue;
//    @JsonProperty("call_cnt_in_one_month")
//    private int callCntInOneMonth;
//    @JsonProperty("last_90_days_cc_mobile_count")
//    private int last90DaysCcMobileCount;
//    @JsonProperty("contact_total_count")
//    private int contactTotalCount;
//    @JsonProperty("total_contact_label_mark_count")
//    private int totalContactLabelMarkCount;
//    @JsonProperty("ID_card_addr")
//    private String idCardAddr;
//    @JsonProperty("contacts_wecash_highrisk_user_count")
//    private int contactsWecashHighriskUserCount;
//    @JsonProperty("contacts_wecash_user_count")
//    private int contactsWecashUserCount;
//    @JsonProperty("last_month_call_in_time")
//    private int lastMonthCallInTime;
//    @JsonProperty("top_ten_call_in_phone_num_label_list")
//    private List<List<String>> topTenCallInPhoneNumLabelList;
//    @JsonProperty("is_vip")
//    private int isVip;
//    @JsonProperty("bill_info_msg")
//    private String billInfoMsg;
//    @JsonProperty("is_ID_verified")
//    private int isIdVerified;
//    @JsonProperty("base_info_msg")
//    private String baseInfoMsg;
//    @JsonProperty("total_contact_list")
//    private List<String> totalContactList;
//    @JsonProperty("call_cnt_three_month")
//    private int callCntThreeMonth;
//    private List<String> phone;
//    @JsonProperty("total_call_day_count")
//    private int totalCallDayCount;
//    @JsonProperty("call_cnt_out_one_month")
//    private int callCntOutOneMonth;
//    @JsonProperty("ID_card_name")
//    private String idCardName;
//    @JsonProperty("call_cnt_out_three_month")
//    private int callCntOutThreeMonth;
//    @JsonProperty("contacts_wecash_overdue_count")
//    private int contactsWecashOverdueCount;
//    @JsonProperty("reg_crawl_days")
//    private int regCrawlDays;
//    @JsonProperty("call_info_msg")
//    private String callInfoMsg;
//    @JsonProperty("callinfo_contacts_match_value")
//    private double callinfoContactsMatchValue;
//    @JsonProperty("contact_black_count")
//    private int contactBlackCount;
//    @JsonProperty("mobile_cost_per_month")
//    private double mobileCostPerMonth;
//    @JsonProperty("contacts_wecash_user_list")
//    private List<String> contactsWecashUserList;
//    @JsonProperty("top_ten_call_in_phone_num_tuple_list")
//    private List<List<String>> topTenCallInPhoneNumTupleList;
//    @JsonProperty("register_time")
//    private Date registerTime;
//    @JsonProperty("top_five_contacts_area_list")
//    private List<List<Map<String,Integer>>> topFiveContactsAreaList;
//    @JsonProperty("update_time")
//    private String updateTime;
//    @JsonProperty("contacts_call_in_total_time")
//    private int contactsCallInTotalTime;
//    @JsonProperty("contact_total_time")
//    private int contactTotalTime;
//    @JsonProperty("call_place_msg")
//    private String callPlaceMsg;
//    @JsonProperty("friends_total_count")
//    private int friendsTotalCount;
//    @JsonProperty("total_contact_info")
//    private List<TotalContactInfoProtypeData> totalContactInfo;
//    @JsonProperty("phone_num_type")
//    private String phoneNumType;
//    @JsonProperty("ID_card_num")
//    private String idCardNum;
//    @JsonProperty("contacts_call_in_total_count")
//    private int contactsCallInTotalCount;
//    @JsonProperty("top_three_contacts_avg_total_time")
//    private int topThreeContactsAvgTotalTime;
//    @JsonProperty("contacts_call_out_total_count")
//    private int contactsCallOutTotalCount;
//    @JsonProperty("contacts_wecash_total_info_list")
//    private List<List<ContactsWecashTotalInfoList>> contactsWecashTotalInfoList;
//    @JsonProperty("last_month_contacts_time")
//    private int lastMonthContactsTime;
//    @JsonProperty("reg_days")
//    private int regDays;
//    @JsonProperty("call_time_three_month")
//    private int callTimeThreeMonth;
//    @JsonProperty("call_time_out_three_month")
//    private int callTimeOutThreeMonth;
//    @JsonProperty("call_cnt_in_three_month")
//    private int callCntInThreeMonth;
//    @JsonProperty("is_open_date_crawled")
//    private int isOpenDateCrawled;
//    @JsonProperty("top_ten_call_out_phone_num_label_list")
//    private List<List<String>> topTenCallOutPhoneNumLabelList;
//    @JsonProperty("top_ten_call_location_tuple_list")
//    private List<String> topTenCallLocationTupleList;
//    @JsonProperty("total_contact_label_finance_mark_count")
//    private int totalContactLabelFinanceMarkCount;
//    @JsonProperty("last_month_call_out_time")
//    private int lastMonthCallOutTime;
//    @JsonProperty("operator_status")
//    private List<String> operatorStatus;
//    @JsonProperty("top_ten_call_out_phone_num_tuple_list")
//    private List<List<String>> topTenCallOutPhoneNumTupleList;
//    @JsonProperty("top_call_address_list")
//    private List<TopCallAddressList> topCallAddressList;
//    @JsonProperty("operator_is_auth")
//    private int operatorIsAuth;
//   
//	public void setLastMonthContactsCount(int lastMonthContactsCount) {
//         this.lastMonthContactsCount = lastMonthContactsCount;
//     }
//     public int getLastMonthContactsCount() {
//         return lastMonthContactsCount;
//     }
//
//    public void setContactsCallOutTotalTime(int contactsCallOutTotalTime) {
//         this.contactsCallOutTotalTime = contactsCallOutTotalTime;
//     }
//     public int getContactsCallOutTotalTime() {
//         return contactsCallOutTotalTime;
//     }
//
//    public void setLast90DaysCcMobileTime(int last90DaysCcMobileTime) {
//         this.last90DaysCcMobileTime = last90DaysCcMobileTime;
//     }
//     public int getLast90DaysCcMobileTime() {
//         return last90DaysCcMobileTime;
//     }
//
//    public void setCallTimeInThreeMonth(int callTimeInThreeMonth) {
//         this.callTimeInThreeMonth = callTimeInThreeMonth;
//     }
//     public int getCallTimeInThreeMonth() {
//         return callTimeInThreeMonth;
//     }
//
//    public void setUsualPhoneEvalValue(double usualPhoneEvalValue) {
//         this.usualPhoneEvalValue = usualPhoneEvalValue;
//     }
//     public double getUsualPhoneEvalValue() {
//         return usualPhoneEvalValue;
//     }
//
//    public void setCallCntInOneMonth(int callCntInOneMonth) {
//         this.callCntInOneMonth = callCntInOneMonth;
//     }
//     public int getCallCntInOneMonth() {
//         return callCntInOneMonth;
//     }
//
//    public void setLast90DaysCcMobileCount(int last90DaysCcMobileCount) {
//         this.last90DaysCcMobileCount = last90DaysCcMobileCount;
//     }
//     public int getLast90DaysCcMobileCount() {
//         return last90DaysCcMobileCount;
//     }
//
//    public void setContactTotalCount(int contactTotalCount) {
//         this.contactTotalCount = contactTotalCount;
//     }
//     public int getContactTotalCount() {
//         return contactTotalCount;
//     }
//
//    public void setTotalContactLabelMarkCount(int totalContactLabelMarkCount) {
//         this.totalContactLabelMarkCount = totalContactLabelMarkCount;
//     }
//     public int getTotalContactLabelMarkCount() {
//         return totalContactLabelMarkCount;
//     }
//
//    public void setIdCardAddr(String idCardAddr) {
//         this.idCardAddr = idCardAddr;
//     }
//     public String getIdCardAddr() {
//         return idCardAddr;
//     }
//
//    public void setContactsWecashHighriskUserCount(int contactsWecashHighriskUserCount) {
//         this.contactsWecashHighriskUserCount = contactsWecashHighriskUserCount;
//     }
//     public int getContactsWecashHighriskUserCount() {
//         return contactsWecashHighriskUserCount;
//     }
//
//    public void setContactsWecashUserCount(int contactsWecashUserCount) {
//         this.contactsWecashUserCount = contactsWecashUserCount;
//     }
//     public int getContactsWecashUserCount() {
//         return contactsWecashUserCount;
//     }
//
//    public void setLastMonthCallInTime(int lastMonthCallInTime) {
//         this.lastMonthCallInTime = lastMonthCallInTime;
//     }
//     public int getLastMonthCallInTime() {
//         return lastMonthCallInTime;
//     }
//
//    public void setTopTenCallInPhoneNumLabelList(List<List<String>> topTenCallInPhoneNumLabelList) {
//         this.topTenCallInPhoneNumLabelList = topTenCallInPhoneNumLabelList;
//     }
//     public List<List<String>> getTopTenCallInPhoneNumLabelList() {
//         return topTenCallInPhoneNumLabelList;
//     }
//
//    public void setIsVip(int isVip) {
//         this.isVip = isVip;
//     }
//     public int getIsVip() {
//         return isVip;
//     }
//
//    public void setBillInfoMsg(String billInfoMsg) {
//         this.billInfoMsg = billInfoMsg;
//     }
//     public String getBillInfoMsg() {
//         return billInfoMsg;
//     }
//
//    public void setIsIdVerified(int isIdVerified) {
//         this.isIdVerified = isIdVerified;
//     }
//     public int getIsIdVerified() {
//         return isIdVerified;
//     }
//
//    public void setBaseInfoMsg(String baseInfoMsg) {
//         this.baseInfoMsg = baseInfoMsg;
//     }
//     public String getBaseInfoMsg() {
//         return baseInfoMsg;
//     }
//
//    public void setTotalContactList(List<String> totalContactList) {
//         this.totalContactList = totalContactList;
//     }
//     public List<String> getTotalContactList() {
//         return totalContactList;
//     }
//
//    public void setCallCntThreeMonth(int callCntThreeMonth) {
//         this.callCntThreeMonth = callCntThreeMonth;
//     }
//     public int getCallCntThreeMonth() {
//         return callCntThreeMonth;
//     }
//
//    public void setPhone(List<String> phone) {
//         this.phone = phone;
//     }
//     public List<String> getPhone() {
//         return phone;
//     }
//
//    public void setTotalCallDayCount(int totalCallDayCount) {
//         this.totalCallDayCount = totalCallDayCount;
//     }
//     public int getTotalCallDayCount() {
//         return totalCallDayCount;
//     }
//
//    public void setCallCntOutOneMonth(int callCntOutOneMonth) {
//         this.callCntOutOneMonth = callCntOutOneMonth;
//     }
//     public int getCallCntOutOneMonth() {
//         return callCntOutOneMonth;
//     }
//
//    public void setIdCardName(String idCardName) {
//         this.idCardName = idCardName;
//     }
//     public String getIdCardName() {
//         return idCardName;
//     }
//
//    public void setCallCntOutThreeMonth(int callCntOutThreeMonth) {
//         this.callCntOutThreeMonth = callCntOutThreeMonth;
//     }
//     public int getCallCntOutThreeMonth() {
//         return callCntOutThreeMonth;
//     }
//
//    public void setContactsWecashOverdueCount(int contactsWecashOverdueCount) {
//         this.contactsWecashOverdueCount = contactsWecashOverdueCount;
//     }
//     public int getContactsWecashOverdueCount() {
//         return contactsWecashOverdueCount;
//     }
//
//    public void setRegCrawlDays(int regCrawlDays) {
//         this.regCrawlDays = regCrawlDays;
//     }
//     public int getRegCrawlDays() {
//         return regCrawlDays;
//     }
//    
//    public String getCallInfoMsg() {
//		return callInfoMsg;
//	}
//	public void setCallInfoMsg(String callInfoMsg) {
//		this.callInfoMsg = callInfoMsg;
//	}
//	public void setCallinfoContactsMatchValue(double callinfoContactsMatchValue) {
//         this.callinfoContactsMatchValue = callinfoContactsMatchValue;
//     }
//     public double getCallinfoContactsMatchValue() {
//         return callinfoContactsMatchValue;
//     }
//
//    public void setContactBlackCount(int contactBlackCount) {
//         this.contactBlackCount = contactBlackCount;
//     }
//     public int getContactBlackCount() {
//         return contactBlackCount;
//     }
//
//    public void setMobileCostPerMonth(double mobileCostPerMonth) {
//         this.mobileCostPerMonth = mobileCostPerMonth;
//     }
//     public double getMobileCostPerMonth() {
//         return mobileCostPerMonth;
//     }
//
//    public void setContactsWecashUserList(List<String> contactsWecashUserList) {
//         this.contactsWecashUserList = contactsWecashUserList;
//     }
//     public List<String> getContactsWecashUserList() {
//         return contactsWecashUserList;
//     }
//
//    public void setTopTenCallInPhoneNumTupleList(List<List<String>> topTenCallInPhoneNumTupleList) {
//         this.topTenCallInPhoneNumTupleList = topTenCallInPhoneNumTupleList;
//     }
//     public List<List<String>> getTopTenCallInPhoneNumTupleList() {
//         return topTenCallInPhoneNumTupleList;
//     }
//
//    public void setRegisterTime(Date registerTime) {
//         this.registerTime = registerTime;
//     }
//     public Date getRegisterTime() {
//         return registerTime;
//     }
//     
//    public List<List<Map<String, Integer>>> getTopFiveContactsAreaList() {
//		return topFiveContactsAreaList;
//	}
//	public void setTopFiveContactsAreaList(List<List<Map<String, Integer>>> topFiveContactsAreaList) {
//		this.topFiveContactsAreaList = topFiveContactsAreaList;
//	}
//
//	public void setUpdateTime(String updateTime) {
//         this.updateTime = updateTime;
//     }
//     public String getUpdateTime() {
//         return updateTime;
//     }
//
//    public void setContactsCallInTotalTime(int contactsCallInTotalTime) {
//         this.contactsCallInTotalTime = contactsCallInTotalTime;
//     }
//     public int getContactsCallInTotalTime() {
//         return contactsCallInTotalTime;
//     }
//
//    public void setContactTotalTime(int contactTotalTime) {
//         this.contactTotalTime = contactTotalTime;
//     }
//     public int getContactTotalTime() {
//         return contactTotalTime;
//     }
//
//    public void setCallPlaceMsg(String callPlaceMsg) {
//         this.callPlaceMsg = callPlaceMsg;
//     }
//     public String getCallPlaceMsg() {
//         return callPlaceMsg;
//     }
//
//    public void setFriendsTotalCount(int friendsTotalCount) {
//         this.friendsTotalCount = friendsTotalCount;
//     }
//     public int getFriendsTotalCount() {
//         return friendsTotalCount;
//     }
//
//    public List<TotalContactInfoProtypeData> getTotalContactInfo() {
//		return totalContactInfo;
//	}
//	public void setTotalContactInfo(List<TotalContactInfoProtypeData> totalContactInfo) {
//		this.totalContactInfo = totalContactInfo;
//	}
//	public void setPhoneNumType(String phoneNumType) {
//         this.phoneNumType = phoneNumType;
//     }
//     public String getPhoneNumType() {
//         return phoneNumType;
//     }
//
//    public void setIdCardNum(String idCardNum) {
//         this.idCardNum = idCardNum;
//     }
//     public String getIdCardNum() {
//         return idCardNum;
//     }
//
//    public void setContactsCallInTotalCount(int contactsCallInTotalCount) {
//         this.contactsCallInTotalCount = contactsCallInTotalCount;
//     }
//     public int getContactsCallInTotalCount() {
//         return contactsCallInTotalCount;
//     }
//
//    public void setTopThreeContactsAvgTotalTime(int topThreeContactsAvgTotalTime) {
//         this.topThreeContactsAvgTotalTime = topThreeContactsAvgTotalTime;
//     }
//     public int getTopThreeContactsAvgTotalTime() {
//         return topThreeContactsAvgTotalTime;
//     }
//
//    public void setContactsCallOutTotalCount(int contactsCallOutTotalCount) {
//         this.contactsCallOutTotalCount = contactsCallOutTotalCount;
//     }
//     public int getContactsCallOutTotalCount() {
//         return contactsCallOutTotalCount;
//     }
//
//    public void setContactsWecashTotalInfoList(List<List<ContactsWecashTotalInfoList>> contactsWecashTotalInfoList) {
//         this.contactsWecashTotalInfoList = contactsWecashTotalInfoList;
//     }
//     public List<List<ContactsWecashTotalInfoList>> getContactsWecashTotalInfoList() {
//         return contactsWecashTotalInfoList;
//     }
//
//    public void setLastMonthContactsTime(int lastMonthContactsTime) {
//         this.lastMonthContactsTime = lastMonthContactsTime;
//     }
//     public int getLastMonthContactsTime() {
//         return lastMonthContactsTime;
//     }
//
//    public void setRegDays(int regDays) {
//         this.regDays = regDays;
//     }
//     public int getRegDays() {
//         return regDays;
//     }
//
//    public void setCallTimeThreeMonth(int callTimeThreeMonth) {
//         this.callTimeThreeMonth = callTimeThreeMonth;
//     }
//     public int getCallTimeThreeMonth() {
//         return callTimeThreeMonth;
//     }
//
//    public void setCallTimeOutThreeMonth(int callTimeOutThreeMonth) {
//         this.callTimeOutThreeMonth = callTimeOutThreeMonth;
//     }
//     public int getCallTimeOutThreeMonth() {
//         return callTimeOutThreeMonth;
//     }
//
//    public void setCallCntInThreeMonth(int callCntInThreeMonth) {
//         this.callCntInThreeMonth = callCntInThreeMonth;
//     }
//     public int getCallCntInThreeMonth() {
//         return callCntInThreeMonth;
//     }
//
//    public void setIsOpenDateCrawled(int isOpenDateCrawled) {
//         this.isOpenDateCrawled = isOpenDateCrawled;
//     }
//     public int getIsOpenDateCrawled() {
//         return isOpenDateCrawled;
//     }
//
//    public void setTopTenCallOutPhoneNumLabelList(List<List<String>> topTenCallOutPhoneNumLabelList) {
//         this.topTenCallOutPhoneNumLabelList = topTenCallOutPhoneNumLabelList;
//     }
//     public List<List<String>> getTopTenCallOutPhoneNumLabelList() {
//         return topTenCallOutPhoneNumLabelList;
//     }
//
//    public void setTopTenCallLocationTupleList(List<String> topTenCallLocationTupleList) {
//         this.topTenCallLocationTupleList = topTenCallLocationTupleList;
//     }
//     public List<String> getTopTenCallLocationTupleList() {
//         return topTenCallLocationTupleList;
//     }
//
//    public void setTotalContactLabelFinanceMarkCount(int totalContactLabelFinanceMarkCount) {
//         this.totalContactLabelFinanceMarkCount = totalContactLabelFinanceMarkCount;
//     }
//     public int getTotalContactLabelFinanceMarkCount() {
//         return totalContactLabelFinanceMarkCount;
//     }
//
//    public void setLastMonthCallOutTime(int lastMonthCallOutTime) {
//         this.lastMonthCallOutTime = lastMonthCallOutTime;
//     }
//     public int getLastMonthCallOutTime() {
//         return lastMonthCallOutTime;
//     }
//
//    public void setOperatorStatus(List<String> operatorStatus) {
//         this.operatorStatus = operatorStatus;
//     }
//     public List<String> getOperatorStatus() {
//         return operatorStatus;
//     }
//
//    public void setTopTenCallOutPhoneNumTupleList(List<List<String>> topTenCallOutPhoneNumTupleList) {
//         this.topTenCallOutPhoneNumTupleList = topTenCallOutPhoneNumTupleList;
//     }
//     public List<List<String>> getTopTenCallOutPhoneNumTupleList() {
//         return topTenCallOutPhoneNumTupleList;
//     }
//
//    public void setTopCallAddressList(List<TopCallAddressList> topCallAddressList) {
//         this.topCallAddressList = topCallAddressList;
//     }
//     public List<TopCallAddressList> getTopCallAddressList() {
//         return topCallAddressList;
//     }
//
//    public void setOperatorIsAuth(int operatorIsAuth) {
//         this.operatorIsAuth = operatorIsAuth;
//     }
//     public int getOperatorIsAuth() {
//         return operatorIsAuth;
//     }
//
//}
