///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.xianjinbaika;
//
///**
// * Module:(code:xjbk)
// * <p>
// * CheckPoints.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//public class CheckPoints {
//    private String key_value;// string 申请表数据值 check_points
//    private String gender;// string 性别 check_points
//    private String age;// string 年龄 check_points
//    private String province;// string 省份 check_points
//    private String city;// string 城市 check_points
//    private String region;// string 区县 check_points
//    private String reg_time;// string 注册时间 application_check
//    private String check_name;// string 姓名检查 用户姓名与运营商提供的姓名[]匹配失败; 用户姓名与运营商提供的姓名[]匹配成功; 运营商未提供姓名 application_check
//    private String check_idcard;// string 身份证号检查 用户身份证号与运营商提供的身份证号码[]匹配成功;用户身份证号与运营商提供的身份证号码[]匹配失败 application_check
//    private String check_ebusiness;// string 电商使用号码检查 该号码在电商里面使用过[]个月，共[]次 ;该号码未在电商中使用过 ;无法判断该号码的电商使用情况(无电商数据)
//    // application_check ;无法判断该号码的电商使用情况(无号码) application_check
//    private String check_addr;// string 地址检查 居住地址可通过地图定位技术精确定位到，坐标(E，N) ;居住地址无法通过地图定位技术精确定位到 ;无法定位居住地址(未提供居住地址)
//    // application_check
//    private String relationship;// string 联系人关系 application_check
//    private String contact_name;// string 联系人姓名 application_check
//    private String check_xiaohao;// string 临时小号检查 该联系人号码为临时小号; 该联系人号码非临时小号 application_check
//    private String check_mobile;// （contact） string 运营商联系号码检查 有该联系人电话的通话记录，[*]天内[]次[]分钟，按时长计算排名第[]位 ;没有该联系人电话的通话记录
//    // application_check ;无法判断该%s的通话情况(无运营商数据) ;无法判断该%s的通话情况(无家庭电话) application_check
//    private CourtBlacklist court_blacklist;// （id_card） dict 法院黑名单检查 application_check
//    private FinancialBlacklist financial_blacklist;// （id_card、cell_phone） dict 金融服务类机构黑名单检查 application_check
//
//    public String getKey_value() {
//        return key_value;
//    }
//
//    public void setKey_value(String key_value) {
//        this.key_value = key_value;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getAge() {
//        return age;
//    }
//
//    public void setAge(String age) {
//        this.age = age;
//    }
//
//    public String getProvince() {
//        return province;
//    }
//
//    public void setProvince(String province) {
//        this.province = province;
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
//    public String getRegion() {
//        return region;
//    }
//
//    public void setRegion(String region) {
//        this.region = region;
//    }
//
//    public String getReg_time() {
//        return reg_time;
//    }
//
//    public void setReg_time(String reg_time) {
//        this.reg_time = reg_time;
//    }
//
//    public String getCheck_name() {
//        return check_name;
//    }
//
//    public void setCheck_name(String check_name) {
//        this.check_name = check_name;
//    }
//
//    public String getCheck_idcard() {
//        return check_idcard;
//    }
//
//    public void setCheck_idcard(String check_idcard) {
//        this.check_idcard = check_idcard;
//    }
//
//    public String getCheck_ebusiness() {
//        return check_ebusiness;
//    }
//
//    public void setCheck_ebusiness(String check_ebusiness) {
//        this.check_ebusiness = check_ebusiness;
//    }
//
//    public String getCheck_addr() {
//        return check_addr;
//    }
//
//    public void setCheck_addr(String check_addr) {
//        this.check_addr = check_addr;
//    }
//
//    public String getRelationship() {
//        return relationship;
//    }
//
//    public void setRelationship(String relationship) {
//        this.relationship = relationship;
//    }
//
//    public String getContact_name() {
//        return contact_name;
//    }
//
//    public void setContact_name(String contact_name) {
//        this.contact_name = contact_name;
//    }
//
//    public String getCheck_xiaohao() {
//        return check_xiaohao;
//    }
//
//    public void setCheck_xiaohao(String check_xiaohao) {
//        this.check_xiaohao = check_xiaohao;
//    }
//
//    public String getCheck_mobile() {
//        return check_mobile;
//    }
//
//    public void setCheck_mobile(String check_mobile) {
//        this.check_mobile = check_mobile;
//    }
//
//    public CourtBlacklist getCourt_blacklist() {
//        return court_blacklist;
//    }
//
//    public void setCourt_blacklist(CourtBlacklist court_blacklist) {
//        this.court_blacklist = court_blacklist;
//    }
//
//    public FinancialBlacklist getFinancial_blacklist() {
//        return financial_blacklist;
//    }
//
//    public void setFinancial_blacklist(FinancialBlacklist financial_blacklist) {
//        this.financial_blacklist = financial_blacklist;
//    }
//
//}
