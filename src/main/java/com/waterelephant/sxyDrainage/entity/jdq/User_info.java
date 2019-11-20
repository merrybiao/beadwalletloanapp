///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.jdq;
//
///**
// * Module:
// * <p>
// * User_info.java
// *
// * @author 王飞
// * @version 1.0
// * @description: <描述>
// * @since 2018-07-20
// */
//public class User_info {
//    private String name;// 姓名
//    private String phone;// 手机号
//    private String id_card;// 身份证号
//    private String marry;// 婚姻状况
//    private String educate;// 学历
//    private String common_email;// 常用邮箱
//    private String id_card_address;// 户籍地址，身份证上地址
//    private String role;// 身份信息（以下几种情况：工薪族、企业主、自由职业）
//    private String nation;// 民族(如：汉)
//    private String id_negative;// 身份证反面URL
//    private String id_positive;// 身份证正面URL
//    private String hand_id_photo;// 手持身份证照片URL
//    private String face;// 人脸照片URL
//    private String living_address;// 居住详细地址，格式 “上海,上海市,静安区|西康路658弄9号2001”
//    private String company_address;// 公司详细地址，格式 “上海,上海市,杨浦区|政府路”
//    private String hiredate;// 当前公司入职日期，格式“2017年7月12日”
//    private String company_name;// 工作单位全称
//    private String company_tel;// 工作单位电话
//    private String industry;// 公司所属行业（以下几种情况：批发/零售、制造业、金融/保险/证券、住宿/餐饮/旅游、商业服务/娱乐/艺术/体育、计算机/互联网、通讯电子、建筑/房地产、法律/咨询、卫生/教育/社会服务、公共事业/社会团体、生物/制药、广告/媒体、能源、贸易、交通运输/仓储/物流）
//    private String company_work_year;// 当前公司工作年限（1-5个月、6-11个月、1-3年、4-7年、7年以上）
//    private String work_profession;// 工作职业（以下几种情况：农牧业、木材/森林业、矿业/采石业、交通运输业、餐旅业、建筑工程业、制造业、娱乐业、文教、金融业、服务业、治安人员、军人、其他）
//    private String income;// 月收入（数值，如：5000）
//    private String income_source;// 收入来源(以下几种情况：工资奖金、经营收入、投资理财、房租收入、其他)
//    private String debt_situation;// 负债情况（值为1(有负债)或0(无负债)）
//    private String loan_usage;// 贷款用途（以下几种情况：购车贷款、医疗美容、网购贷款、装修贷款、教育培训、旅游贷款、三农贷款、其他）
//    private String city;// 所在城市（如：上海市）
//    private String company_city;// 公司所在城市（如：苏州市）
//    private String living_house_type;// 住宅类型（以下几种情况：租房、产权房产、父母所有房产、公司宿舍、其他）
//    private String id_signing_authority;// 身份证签发机构（身份证上签发机构）
//    private String id_expiry_date;// 身份证有效期(格式：2036-09-01或长期)
//
//    /**
//     * 身份证有效起始日期(格式：2016-09-01)
//     */
//    private String id_start_date;
//    //QQ号
//    private String qq;
//
//    public String getId_start_date() {
//        return id_start_date;
//    }
//
//    public void setId_start_date(String id_start_date) {
//        this.id_start_date = id_start_date;
//    }
//
//    public String getQq() {
//        return qq;
//    }
//
//    public void setQq(String qq) {
//        this.qq = qq;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getId_card() {
//        return id_card;
//    }
//
//    public void setId_card(String id_card) {
//        this.id_card = id_card;
//    }
//
//    public String getMarry() {
//        return marry;
//    }
//
//    public void setMarry(String marry) {
//        this.marry = marry;
//    }
//
//    public String getEducate() {
//        return educate;
//    }
//
//    public void setEducate(String educate) {
//        this.educate = educate;
//    }
//
//    public String getCommon_email() {
//        return common_email;
//    }
//
//    public void setCommon_email(String common_email) {
//        this.common_email = common_email;
//    }
//
//    public String getId_card_address() {
//        return id_card_address;
//    }
//
//    public void setId_card_address(String id_card_address) {
//        this.id_card_address = id_card_address;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public String getNation() {
//        return nation;
//    }
//
//    public void setNation(String nation) {
//        this.nation = nation;
//    }
//
//    public String getId_negative() {
//        return id_negative;
//    }
//
//    public void setId_negative(String id_negative) {
//        this.id_negative = id_negative;
//    }
//
//    public String getId_positive() {
//        return id_positive;
//    }
//
//    public void setId_positive(String id_positive) {
//        this.id_positive = id_positive;
//    }
//
//    public String getHand_id_photo() {
//        return hand_id_photo;
//    }
//
//    public void setHand_id_photo(String hand_id_photo) {
//        this.hand_id_photo = hand_id_photo;
//    }
//
//    public String getFace() {
//        return face;
//    }
//
//    public void setFace(String face) {
//        this.face = face;
//    }
//
//    public String getLiving_address() {
//        return living_address;
//    }
//
//    public void setLiving_address(String living_address) {
//        this.living_address = living_address;
//    }
//
//    public String getCompany_address() {
//        return company_address;
//    }
//
//    public void setCompany_address(String company_address) {
//        this.company_address = company_address;
//    }
//
//    public String getHiredate() {
//        return hiredate;
//    }
//
//    public void setHiredate(String hiredate) {
//        this.hiredate = hiredate;
//    }
//
//    public String getCompany_name() {
//        return company_name;
//    }
//
//    public void setCompany_name(String company_name) {
//        this.company_name = company_name;
//    }
//
//    public String getCompany_tel() {
//        return company_tel;
//    }
//
//    public void setCompany_tel(String company_tel) {
//        this.company_tel = company_tel;
//    }
//
//    public String getIndustry() {
//        return industry;
//    }
//
//    public void setIndustry(String industry) {
//        this.industry = industry;
//    }
//
//    public String getCompany_work_year() {
//        return company_work_year;
//    }
//
//    public void setCompany_work_year(String company_work_year) {
//        this.company_work_year = company_work_year;
//    }
//
//    public String getWork_profession() {
//        return work_profession;
//    }
//
//    public void setWork_profession(String work_profession) {
//        this.work_profession = work_profession;
//    }
//
//    public String getIncome() {
//        return income;
//    }
//
//    public void setIncome(String income) {
//        this.income = income;
//    }
//
//    public String getIncome_source() {
//        return income_source;
//    }
//
//    public void setIncome_source(String income_source) {
//        this.income_source = income_source;
//    }
//
//    public String getDebt_situation() {
//        return debt_situation;
//    }
//
//    public void setDebt_situation(String debt_situation) {
//        this.debt_situation = debt_situation;
//    }
//
//    public String getLoan_usage() {
//        return loan_usage;
//    }
//
//    public void setLoan_usage(String loan_usage) {
//        this.loan_usage = loan_usage;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getCompany_city() {
//        return company_city;
//    }
//
//    public void setCompany_city(String company_city) {
//        this.company_city = company_city;
//    }
//
//    public String getLiving_house_type() {
//        return living_house_type;
//    }
//
//    public void setLiving_house_type(String living_house_type) {
//        this.living_house_type = living_house_type;
//    }
//
//    public String getId_signing_authority() {
//        return id_signing_authority;
//    }
//
//    public void setId_signing_authority(String id_signing_authority) {
//        this.id_signing_authority = id_signing_authority;
//    }
//
//    public String getId_expiry_date() {
//        return id_expiry_date;
//    }
//
//    public void setId_expiry_date(String id_expiry_date) {
//        this.id_expiry_date = id_expiry_date;
//    }
//
//}
