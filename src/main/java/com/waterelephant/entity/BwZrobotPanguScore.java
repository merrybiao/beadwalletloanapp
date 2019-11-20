package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BwZrobotPanguScore.java Zrobot盘古信用分
 *
 * @author dinglinhao
 * @date 2018年7月6日10:59:35
 *
 */
@Table(name = "bw_zrobot_pangu_score")
public class BwZrobotPanguScore implements Serializable {

    private static final long serialVersionUID = 1515491825266749955L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String idCardNum;
    /**
     * 手机号
     */
    private String cellPhoneNum;
    /**
     * 银行卡号
     */
    private String bankCardNum;
    /**
     * 信用分值 值为500至750分的浮点数，值为-1代表查询不到用户
     */
    private String score;
    /**
     * 稳定性 客户消费水平的稳定性 字段取值：极强、强、一般、弱、极弱，分别对应[1,2,3,4,5] 稳定想越强，模型可信度越强
     */
    private String stability;
    /**
     * 购买力指数 客户的消费能力 取值字段[0,100] 指数越高，潜在偿债能力越强
     */
    private String buyingIndex;
    private String riskIndex;
    private String performanceIndex;
    private String resonableConsuming;
    private String city;
    private String cityStability;
    private String onlineBuying;
    private String comsumingSocial;
    private String incomming;
    private String riskPeriodConsuming;
    private String riskCategoryConsuming;
    private String worktimeShopping;
    private String cellphonePreference;
    private String ecommerceActiveness;
    private String ecommerceAddressStability;
    private String ecommercecellphoneStability;
    private String ecommerceAccountHistory;
    private String cashPreference;
    private String riskPeriodPayment;
    private String riskCategoryPayment;
    private String bankcardStability;
    private String bankcardActiveness;
    private String bankcardHistory;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getCellPhoneNum() {
        return cellPhoneNum;
    }

    public void setCellPhoneNum(String cellPhoneNum) {
        this.cellPhoneNum = cellPhoneNum;
    }

    public String getBankCardNum() {
        return bankCardNum;
    }

    public void setBankCardNum(String bankCardNum) {
        this.bankCardNum = bankCardNum;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStability() {
        return stability;
    }

    public void setStability(String stability) {
        this.stability = stability;
    }

    public String getBuyingIndex() {
        return buyingIndex;
    }

    public void setBuyingIndex(String buyingIndex) {
        this.buyingIndex = buyingIndex;
    }

    public String getRiskIndex() {
        return riskIndex;
    }

    public void setRiskIndex(String riskIndex) {
        this.riskIndex = riskIndex;
    }

    public String getPerformanceIndex() {
        return performanceIndex;
    }

    public void setPerformanceIndex(String performanceIndex) {
        this.performanceIndex = performanceIndex;
    }

    public String getResonableConsuming() {
        return resonableConsuming;
    }

    public void setResonableConsuming(String resonableConsuming) {
        this.resonableConsuming = resonableConsuming;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityStability() {
        return cityStability;
    }

    public void setCityStability(String cityStability) {
        this.cityStability = cityStability;
    }

    public String getOnlineBuying() {
        return onlineBuying;
    }

    public void setOnlineBuying(String onlineBuying) {
        this.onlineBuying = onlineBuying;
    }

    public String getComsumingSocial() {
        return comsumingSocial;
    }

    public void setComsumingSocial(String comsumingSocial) {
        this.comsumingSocial = comsumingSocial;
    }

    public String getIncomming() {
        return incomming;
    }

    public void setIncomming(String incomming) {
        this.incomming = incomming;
    }

    public String getRiskPeriodConsuming() {
        return riskPeriodConsuming;
    }

    public void setRiskPeriodConsuming(String riskPeriodConsuming) {
        this.riskPeriodConsuming = riskPeriodConsuming;
    }

    public String getRiskCategoryConsuming() {
        return riskCategoryConsuming;
    }

    public void setRiskCategoryConsuming(String riskCategoryConsuming) {
        this.riskCategoryConsuming = riskCategoryConsuming;
    }

    public String getWorktimeShopping() {
        return worktimeShopping;
    }

    public void setWorktimeShopping(String worktimeShopping) {
        this.worktimeShopping = worktimeShopping;
    }

    public String getCellphonePreference() {
        return cellphonePreference;
    }

    public void setCellphonePreference(String cellphonePreference) {
        this.cellphonePreference = cellphonePreference;
    }

    public String getEcommerceActiveness() {
        return ecommerceActiveness;
    }

    public void setEcommerceActiveness(String ecommerceActiveness) {
        this.ecommerceActiveness = ecommerceActiveness;
    }

    public String getEcommerceAddressStability() {
        return ecommerceAddressStability;
    }

    public void setEcommerceAddressStability(String ecommerceAddressStability) {
        this.ecommerceAddressStability = ecommerceAddressStability;
    }

    public String getEcommercecellphoneStability() {
        return ecommercecellphoneStability;
    }

    public void setEcommercecellphoneStability(String ecommercecellphoneStability) {
        this.ecommercecellphoneStability = ecommercecellphoneStability;
    }

    public String getEcommerceAccountHistory() {
        return ecommerceAccountHistory;
    }

    public void setEcommerceAccountHistory(String ecommerceAccountHistory) {
        this.ecommerceAccountHistory = ecommerceAccountHistory;
    }

    public String getCashPreference() {
        return cashPreference;
    }

    public void setCashPreference(String cashPreference) {
        this.cashPreference = cashPreference;
    }

    public String getRiskPeriodPayment() {
        return riskPeriodPayment;
    }

    public void setRiskPeriodPayment(String riskPeriodPayment) {
        this.riskPeriodPayment = riskPeriodPayment;
    }

    public String getRiskCategoryPayment() {
        return riskCategoryPayment;
    }

    public void setRiskCategoryPayment(String riskCategoryPayment) {
        this.riskCategoryPayment = riskCategoryPayment;
    }

    public String getBankcardStability() {
        return bankcardStability;
    }

    public void setBankcardStability(String bankcardStability) {
        this.bankcardStability = bankcardStability;
    }

    public String getBankcardActiveness() {
        return bankcardActiveness;
    }

    public void setBankcardActiveness(String bankcardActiveness) {
        this.bankcardActiveness = bankcardActiveness;
    }

    public String getBankcardHistory() {
        return bankcardHistory;
    }

    public void setBankcardHistory(String bankcardHistory) {
        this.bankcardHistory = bankcardHistory;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
