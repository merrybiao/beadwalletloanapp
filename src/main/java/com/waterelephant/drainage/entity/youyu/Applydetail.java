/**
  * Copyright 2017 bejson.com 
  */
package com.waterelephant.drainage.entity.youyu;

/**
 *
 *Applydetail申请详情实体类
 * @author FanShenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class Applydetail {

    private String application_amount;//贷款金额
    private String loan_term;//贷款期限
    private String term_unit;//期限单位 1-天，2-月
    private String asset_auto_type;//车辆情况
    private String user_education;//教育程度
    private String work_period;//工作年限
    private String user_income_by_card;//固定职业者的月收入
    private String max_monthly_repayment;//可接受每月最高还款金额
    private String operating_year;//经营年限
    private String is_op_type;//职业类别
    private String user_id;//用户ID
    private String user_name;//用户姓名
    private String user_social_security;//社保缴纳
    private String user_mobile;//手机号码
    private String monthly_average_income;//无固定职业者的月收入
    public void setApplication_amount(String application_amount) {
         this.application_amount = application_amount;
     }
     public String getApplication_amount() {
         return application_amount;
     }

    public void setLoan_term(String loan_term) {
         this.loan_term = loan_term;
     }
     public String getLoan_term() {
         return loan_term;
     }

    public void setTerm_unit(String term_unit) {
         this.term_unit = term_unit;
     }
     public String getTerm_unit() {
         return term_unit;
     }

    public void setAsset_auto_type(String asset_auto_type) {
         this.asset_auto_type = asset_auto_type;
     }
     public String getAsset_auto_type() {
         return asset_auto_type;
     }

    public void setUser_education(String user_education) {
         this.user_education = user_education;
     }
     public String getUser_education() {
         return user_education;
     }

    public void setWork_period(String work_period) {
         this.work_period = work_period;
     }
     public String getWork_period() {
         return work_period;
     }

    public void setUser_income_by_card(String user_income_by_card) {
         this.user_income_by_card = user_income_by_card;
     }
     public String getUser_income_by_card() {
         return user_income_by_card;
     }

    public void setMax_monthly_repayment(String max_monthly_repayment) {
         this.max_monthly_repayment = max_monthly_repayment;
     }
     public String getMax_monthly_repayment() {
         return max_monthly_repayment;
     }

    public void setOperating_year(String operating_year) {
         this.operating_year = operating_year;
     }
     public String getOperating_year() {
         return operating_year;
     }

    public void setIs_op_type(String is_op_type) {
         this.is_op_type = is_op_type;
     }
     public String getIs_op_type() {
         return is_op_type;
     }

    public void setUser_id(String user_id) {
         this.user_id = user_id;
     }
     public String getUser_id() {
         return user_id;
     }

    public void setUser_name(String user_name) {
         this.user_name = user_name;
     }
     public String getUser_name() {
         return user_name;
     }

    public void setUser_social_security(String user_social_security) {
         this.user_social_security = user_social_security;
     }
     public String getUser_social_security() {
         return user_social_security;
     }

    public void setUser_mobile(String user_mobile) {
         this.user_mobile = user_mobile;
     }
     public String getUser_mobile() {
         return user_mobile;
     }

    public void setMonthly_average_income(String monthly_average_income) {
         this.monthly_average_income = monthly_average_income;
     }
     public String getMonthly_average_income() {
         return monthly_average_income;
     }

}