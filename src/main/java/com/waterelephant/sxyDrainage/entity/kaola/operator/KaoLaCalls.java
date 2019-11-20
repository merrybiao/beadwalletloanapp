//package com.waterelephant.sxyDrainage.entity.kaola.operator;
//
//import java.util.Date;
//
///**
// * @author wangfei
// * @version 1.0
// * @date 2018/7/12
// * @Description 考拉通话详单
// * @since JDK 1.8
// */
//public class KaoLaCalls {
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
//    /**是否有效通话*/
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
//
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
//
//    public int getCallDuration() {
//        return callDuration;
//    }
//
//    public void setCallDuration(int callDuration) {
//        this.callDuration = callDuration;
//    }
//
//    public int getSerialNum() {
//        return serialNum;
//    }
//
//    public void setSerialNum(int serialNum) {
//        this.serialNum = serialNum;
//    }
//
//    public long getCallMonth() {
//        return callMonth;
//    }
//
//    public void setCallMonth(long callMonth) {
//        this.callMonth = callMonth;
//    }
//
//    public int getOtherFee() {
//        return otherFee;
//    }
//
//    public void setOtherFee(int otherFee) {
//        this.otherFee = otherFee;
//    }
//
//    public long getCallStartTime() {
//        return callStartTime;
//    }
//
//    public void setCallStartTime(long callStartTime) {
//        this.callStartTime = callStartTime;
//    }
//
//    public String getOtherTelNum() {
//        return otherTelNum;
//    }
//
//    public void setOtherTelNum(String otherTelNum) {
//        this.otherTelNum = otherTelNum;
//    }
//
//    public int getRoamingFee() {
//        return roamingFee;
//    }
//
//    public void setRoamingFee(int roamingFee) {
//        this.roamingFee = roamingFee;
//    }
//
//    public String getCallLocation() {
//        return callLocation;
//    }
//
//    public void setCallLocation(String callLocation) {
//        this.callLocation = callLocation;
//    }
//
//    public int getCallType() {
//        return callType;
//    }
//
//    public void setCallType(int callType) {
//        this.callType = callType;
//    }
//
//    public int getIsEffectiveCall() {
//        return isEffectiveCall;
//    }
//
//    public void setIsEffectiveCall(int isEffectiveCall) {
//        this.isEffectiveCall = isEffectiveCall;
//    }
//
//    public int getIsFamiliarityMemberCall() {
//        return isFamiliarityMemberCall;
//    }
//
//    public void setIsFamiliarityMemberCall(int isFamiliarityMemberCall) {
//        this.isFamiliarityMemberCall = isFamiliarityMemberCall;
//    }
//
//    public Date getOtherFullTelNum() {
//        return otherFullTelNum;
//    }
//
//    public void setOtherFullTelNum(Date otherFullTelNum) {
//        this.otherFullTelNum = otherFullTelNum;
//    }
//
//    public int getTotalFee() {
//        return totalFee;
//    }
//
//    public void setTotalFee(int totalFee) {
//        this.totalFee = totalFee;
//    }
//
//    public String getCallTypeDetail() {
//        return callTypeDetail;
//    }
//
//    public void setCallTypeDetail(String callTypeDetail) {
//        this.callTypeDetail = callTypeDetail;
//    }
//
//    public int getLandFee() {
//        return landFee;
//    }
//
//    public void setLandFee(int landFee) {
//        this.landFee = landFee;
//    }
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
//
//
//    public int getBaseFee() {
//        return baseFee;
//    }
//
//    public void setBaseFee(int baseFee) {
//        this.baseFee = baseFee;
//    }
//
//    public String getBusinessType() {
//        return businessType;
//    }
//
//    public void setBusinessType(String businessType) {
//        this.businessType = businessType;
//    }
//
//    public int getOtherTelStatus() {
//        return otherTelStatus;
//    }
//
//    public void setOtherTelStatus(int otherTelStatus) {
//        this.otherTelStatus = otherTelStatus;
//    }
//}
