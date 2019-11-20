package com.waterelephant.drainage.jsonentity.fqgj;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/22 17:03
 */
@SuppressWarnings("serial")
public class FqgjApplyDetail implements Serializable {

	@JSONField(name="asset_auto_type")
    private Integer assetAutoType;

    @JSONField(name="user_education")
    private Integer userEducation;

    @JSONField(name="is_op_type")
    private Integer isOpType;

    @JSONField(name="work_period")
    private Integer workPeriod;

    @JSONField(name="corporate_flow")
    private BigDecimal corporateFlow;

    @JSONField(name="user_income_by_card")
    private BigDecimal userIncomeByCard;

    @JSONField(name="max_monthly_repayment")
    private BigDecimal maxMonthlyRepayment;

    @JSONField(name="operating_year")
    private Integer operatingYear;

    @JSONField(name="id_card")
    private String idCard;

    @JSONField(name="bureau_user_name")
    private String bureauUserName;

    @JSONField(name="user_social_security")
    private Integer userSocialSecurity;

    @JSONField(name="phone_number_house")
    private String phoneNumberHouse;

    @JSONField(name="monthly_average_income")
    private BigDecimal monthlyAverageIncome;

    @JSONField(name="gps_location")
    private String gpsLocation;

    @JSONField(name="channel")
    private String channel;

    @JSONField(name="register_time")
    private Date registerTime;

    @JSONField(name="ip_address")
    private String ipAddress;

    public Integer getAssetAutoType() {
        return assetAutoType;
    }

    public void setAssetAutoType(Integer assetAutoType) {
        this.assetAutoType = assetAutoType;
    }

    public Integer getUserEducation() {
        return userEducation;
    }

    public void setUserEducation(Integer userEducation) {
        this.userEducation = userEducation;
    }

    public Integer getIsOpType() {
        return isOpType;
    }

    public void setIsOpType(Integer isOpType) {
        this.isOpType = isOpType;
    }

    public Integer getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Integer workPeriod) {
        this.workPeriod = workPeriod;
    }

    public BigDecimal getCorporateFlow() {
        return corporateFlow;
    }

    public void setCorporateFlow(BigDecimal corporateFlow) {
        this.corporateFlow = corporateFlow;
    }

    public BigDecimal getUserIncomeByCard() {
        return userIncomeByCard;
    }

    public void setUserIncomeByCard(BigDecimal userIncomeByCard) {
        this.userIncomeByCard = userIncomeByCard;
    }

    public BigDecimal getMaxMonthlyRepayment() {
        return maxMonthlyRepayment;
    }

    public void setMaxMonthlyRepayment(BigDecimal maxMonthlyRepayment) {
        this.maxMonthlyRepayment = maxMonthlyRepayment;
    }

    public Integer getOperatingYear() {
        return operatingYear;
    }

    public void setOperatingYear(Integer operatingYear) {
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

    public Integer getUserSocialSecurity() {
        return userSocialSecurity;
    }

    public void setUserSocialSecurity(Integer userSocialSecurity) {
        this.userSocialSecurity = userSocialSecurity;
    }

    public String getPhoneNumberHouse() {
        return phoneNumberHouse;
    }

    public void setPhoneNumberHouse(String phoneNumberHouse) {
        this.phoneNumberHouse = phoneNumberHouse;
    }

    public BigDecimal getMonthlyAverageIncome() {
        return monthlyAverageIncome;
    }

    public void setMonthlyAverageIncome(BigDecimal monthlyAverageIncome) {
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

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
