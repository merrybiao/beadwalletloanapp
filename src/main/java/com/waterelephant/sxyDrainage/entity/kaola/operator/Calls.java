//package com.waterelephant.sxyDrainage.entity.kaola.operator;
//import java.util.Date;
//
///**
// *
// *
// * Module:
// *
// * Calls.java
// *
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class Calls {
//    /**
//     *通话时长
//     */
//    private int callDuration;
//    /**
//     * 序列号
//     */
//    private int serialNum;
//    /**
//     * 通话月份
//     */
//    private long callMonth;
//    /**
//     * 其他费
//     */
//    private int otherFee;
//    /**
//     * 通话起始时间（精确到秒）
//     */
//    private long callStartTime;
//    /**
//     * 对方号码
//     */
//    private String otherTelNum;
//    /**
//     * 漫游通话费
//     */
//    private int roamingFee;
//    /**
//     * 通话地点
//     */
//    private String callLocation;
//    /**
//     * 呼叫类型
//     */
//    private int callType;
//    private int isEffectiveCall;
//    /**
//     * 是否亲情号之间通讯
//     */
//    private int isFamiliarityMemberCall;
//    /**
//     * 对方号码全号
//     */
//    private Date otherFullTelNum;
//    /***
//     * 本次通话话费总金额
//     */
//    private int totalFee;
//    /**
//     * 通话类型
//     */
//    private String callTypeDetail;
//    /**
//     * 长途通话费
//     */
//    private int landFee;
//    /**
//     * 是否有效通话（通话时长>20秒）
//     */
//    private Integer isEffectiveFee;
//    /**
//     * 是否与常住地通话
//     */
//    private Integer isCallingUsualLocation;
//    /**
//     * 是否与老家通话
//     */
//    private Integer isCallingHome;
//    /**
//     * 手机号码
//     */
//    private String telNum;
//    /**
//     * 基本通话费
//     */
//    private int baseFee;
//    /**
//     * 业务类型
//     */
//    private String businessType;
//    /**
//     * 通话对方手机号是否有效标识
//     */
//    private int otherTelStatus;
//    public void setCallDuration(int callDuration) {
//         this.callDuration = callDuration;
//     }
//     public int getCallDuration() {
//         return callDuration;
//     }
//
//    public void setSerialNum(int serialNum) {
//         this.serialNum = serialNum;
//     }
//     public int getSerialNum() {
//         return serialNum;
//     }
//
//    public void setCallMonth(long callMonth) {
//         this.callMonth = callMonth;
//     }
//     public long getCallMonth() {
//         return callMonth;
//     }
//
//    public void setOtherFee(int otherFee) {
//         this.otherFee = otherFee;
//     }
//     public int getOtherFee() {
//         return otherFee;
//     }
//
//    public void setCallStartTime(long callStartTime) {
//         this.callStartTime = callStartTime;
//     }
//     public long getCallStartTime() {
//         return callStartTime;
//     }
//
//    public void setOtherTelNum(String otherTelNum) {
//         this.otherTelNum = otherTelNum;
//     }
//     public String getOtherTelNum() {
//         return otherTelNum;
//     }
//
//    public void setRoamingFee(int roamingFee) {
//         this.roamingFee = roamingFee;
//     }
//     public int getRoamingFee() {
//         return roamingFee;
//     }
//
//    public void setCallLocation(String callLocation) {
//         this.callLocation = callLocation;
//     }
//     public String getCallLocation() {
//         return callLocation;
//     }
//
//    public void setCallType(int callType) {
//         this.callType = callType;
//     }
//     public int getCallType() {
//         return callType;
//     }
//
//    public void setIsEffectiveCall(int isEffectiveCall) {
//         this.isEffectiveCall = isEffectiveCall;
//     }
//     public int getIsEffectiveCall() {
//         return isEffectiveCall;
//     }
//
//    public void setIsFamiliarityMemberCall(int isFamiliarityMemberCall) {
//         this.isFamiliarityMemberCall = isFamiliarityMemberCall;
//     }
//     public int getIsFamiliarityMemberCall() {
//         return isFamiliarityMemberCall;
//     }
//
//    public void setOtherFullTelNum(Date otherFullTelNum) {
//         this.otherFullTelNum = otherFullTelNum;
//     }
//     public Date getOtherFullTelNum() {
//         return otherFullTelNum;
//     }
//
//    public void setTotalFee(int totalFee) {
//         this.totalFee = totalFee;
//     }
//     public int getTotalFee() {
//         return totalFee;
//     }
//
//    public void setCallTypeDetail(String callTypeDetail) {
//         this.callTypeDetail = callTypeDetail;
//     }
//     public String getCallTypeDetail() {
//         return callTypeDetail;
//     }
//
//    public void setLandFee(int landFee) {
//         this.landFee = landFee;
//     }
//     public int getLandFee() {
//         return landFee;
//     }
//
//    public void setTelNum(String telNum) {
//         this.telNum = telNum;
//     }
//     public String getTelNum() {
//         return telNum;
//     }
//
//    public void setBaseFee(int baseFee) {
//         this.baseFee = baseFee;
//     }
//     public int getBaseFee() {
//         return baseFee;
//     }
//
//    public void setBusinessType(String businessType) {
//         this.businessType = businessType;
//     }
//     public String getBusinessType() {
//         return businessType;
//     }
//
//    public void setOtherTelStatus(int otherTelStatus) {
//         this.otherTelStatus = otherTelStatus;
//     }
//     public int getOtherTelStatus() {
//         return otherTelStatus;
//     }
//
//    public Integer getIsEffectiveFee() {
//        return isEffectiveFee;
//    }
//
//    public void setIsEffectiveFee(Integer isEffectiveFee) {
//        this.isEffectiveFee = isEffectiveFee;
//    }
//
//    public Integer getIsCallingUsualLocation() {
//        return isCallingUsualLocation;
//    }
//
//    public void setIsCallingUsualLocation(Integer isCallingUsualLocation) {
//        this.isCallingUsualLocation = isCallingUsualLocation;
//    }
//
//    public Integer getIsCallingHome() {
//        return isCallingHome;
//    }
//
//    public void setIsCallingHome(Integer isCallingHome) {
//        this.isCallingHome = isCallingHome;
//    }
//}
