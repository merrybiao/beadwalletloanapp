package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 产品信息
 * Created by DIY on 2017/7/20.
 */
public class ApplyDetail {

	
    @JSONField(name = "asset_auto_type")
    private int assetAutoType; // 车辆情况 1：无车2：本人名下有车，无贷款3：本人名下有车，有按揭贷款 4：本人名下有车，但已被抵押5：其他

    @JSONField(name = "user_education")
    private int userEducation; // 受教育情况 1：硕士及以上2：本科3：大专 4：中专/高中以下

    @JSONField(name = "is_op_type")
    private int isOpType; // 职业类别 1.：企业主 2：个体户 4：工薪族 5：学生 10：自由职业

    @JSONField(name = "work_period")
    private int workPeriod; // 现单位工作年限 1：0-5个月 2：6-11个月 3：1-3年 4：3-7年 5： 7年以上

    @JSONField(name = "corporate_flow")
    private float corporateFlow; // 经营流水

    @JSONField(name = "user_income_by_card")
    private float userIncomeByCard; // 月工资收入

    @JSONField(name = "max_monthly_repayment")
    private float maxMonthlyRepayment; // 可接受月最高还款额

    @JSONField(name = "operating_year")
    private int operatingYear; // 经营年限

    @JSONField(name = "id_card")
    private String idCard; // 本人身份证号

    @JSONField(name = "bureau_user_name")
    private String bureauUserName; // 本人姓名

    @JSONField(name = "user_social_security")
    private int userSocialSecurity; // 现单位是否缴纳社保 1:缴纳本地社保2：无社保

    @JSONField(name = "phone_number_house")
    private String phoneNumberHouse; // 手机号

    @JSONField(name = "monthly_average_income")
    private float monthlyAverageIncome; // 月平均收入

    @JSONField(name = "gps_location")
    private String gpsLocation; // GPS信息

    @JSONField(name = "channel")
    private String channel; // 渠道

    @JSONField(name = "register_time")
    private String registerTime; // 注册app时间，时间戳10位

    @JSONField(name = "ip_address")
    private String ipAddress; // Ip地址

    public int getAssetAutoType() {
        return assetAutoType;
    }

    public void setAssetAutoType(int assetAutoType) {
        this.assetAutoType = assetAutoType;
    }

    public int getUserEducation() {
        return userEducation;
    }

    public void setUserEducation(int userEducation) {
        this.userEducation = userEducation;
    }

    public int getIsOpType() {
        return isOpType;
    }

    public void setIsOpType(int isOpType) {
        this.isOpType = isOpType;
    }

    public int getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(int workPeriod) {
        this.workPeriod = workPeriod;
    }

    public float getCorporateFlow() {
        return corporateFlow;
    }

    public void setCorporateFlow(float corporateFlow) {
        this.corporateFlow = corporateFlow;
    }

    public float getUserIncomeByCard() {
        return userIncomeByCard;
    }

    public void setUserIncomeByCard(float userIncomeByCard) {
        this.userIncomeByCard = userIncomeByCard;
    }

    public float getMaxMonthlyRepayment() {
        return maxMonthlyRepayment;
    }

    public void setMaxMonthlyRepayment(float maxMonthlyRepayment) {
        this.maxMonthlyRepayment = maxMonthlyRepayment;
    }

    public int getOperatingYear() {
        return operatingYear;
    }

    public void setOperatingYear(int operatingYear) {
        this.operatingYear = operatingYear;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBureauUserName() {
        return bureauUserName;
    }

    public void setBureauUserName(String bureauUserName) {
        this.bureauUserName = bureauUserName;
    }

    public int getUserSocialSecurity() {
        return userSocialSecurity;
    }

    public void setUserSocialSecurity(int userSocialSecurity) {
        this.userSocialSecurity = userSocialSecurity;
    }

    public String getPhoneNumberHouse() {
        return phoneNumberHouse;
    }

    public void setPhoneNumberHouse(String phoneNumberHouse) {
        this.phoneNumberHouse = phoneNumberHouse;
    }

    public float getMonthlyAverageIncome() {
        return monthlyAverageIncome;
    }

    public void setMonthlyAverageIncome(float monthlyAverageIncome) {
        this.monthlyAverageIncome = monthlyAverageIncome;
    }

    public String getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
