///**
//  * Copyright 2018 aTool.org 
//  */
//package com.waterelephant.sxyDrainage.entity.kabao;
//import org.codehaus.jackson.annotate.JsonProperty;
///**
// * Auto-generated: 2018-06-07 14:28:14
// *
// * @author aTool.org (i@aTool.org)
// * @website http://www.atool.org/json2javabean.php
// */
//public class PhoneTagInfo {
//
//    @JsonProperty("call_in_count")
//    private Integer callInCount;
//    private String phone;
//    @JsonProperty("total_count")
//    private int totalCount;
//    @JsonProperty("call_out_count")
//    private int callOutCount;
//    @JsonProperty("call_out_time")
//    private int callOutTime;
//    @JsonProperty("last_call_time")
//    private String lastCallTime;
//    private String tag;
//    @JsonProperty("total_time")
//    private int totalTime;
//    @JsonProperty("call_in_time")
//    private int callInTime;
//    public void setCallInCount(int callInCount) {
//         this.callInCount = callInCount;
//     }
//     public int getCallInCount() {
//         return callInCount;
//     }
//
//    public void setPhone(String phone) {
//         this.phone = phone;
//     }
//     public String getPhone() {
//         return phone;
//     }
//
//    public void setTotalCount(int totalCount) {
//         this.totalCount = totalCount;
//     }
//     public int getTotalCount() {
//         return totalCount;
//     }
//
//    public void setCallOutCount(int callOutCount) {
//         this.callOutCount = callOutCount;
//     }
//     public int getCallOutCount() {
//         return callOutCount;
//     }
//
//    public void setCallOutTime(int callOutTime) {
//         this.callOutTime = callOutTime;
//     }
//     public int getCallOutTime() {
//         return callOutTime;
//     }
//
//    public void setLastCallTime(String lastCallTime) {
//         this.lastCallTime = lastCallTime;
//     }
//     public String getLastCallTime() {
//         return lastCallTime;
//     }
//
//    public void setTag(String tag) {
//         this.tag = tag;
//     }
//     public String getTag() {
//    	 if(this.tag.length() > 100){
//    		 this.tag = this.tag.substring(0, 100);
//    	 }
//         return tag;
//     }
//
//    public void setTotalTime(int totalTime) {
//         this.totalTime = totalTime;
//     }
//     public int getTotalTime() {
//         return totalTime;
//     }
//
//    public void setCallInTime(int callInTime) {
//         this.callInTime = callInTime;
//     }
//     public int getCallInTime() {
//         return callInTime;
//     }
//	@Override
//	public String toString() {
//		return "PhoneTagInfo [callInCount=" + callInCount + ", phone=" + phone + ", totalCount=" + totalCount
//				+ ", callOutCount=" + callOutCount + ", callOutTime=" + callOutTime + ", lastCallTime=" + lastCallTime
//				+ ", tag=" + tag + ", totalTime=" + totalTime + ", callInTime=" + callInTime + "]";
//	}
//
//}
