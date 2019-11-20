//package com.waterelephant.sxyDrainage.entity.dkdh360;
//
//import java.util.List;
//
///**
// * (code:dkdh001)
// *
// * @Author: zhangchong
// * @Date: 2018/7/26 18:11
// * @Description: 用户借款补充信息
// */
//public class LoanAddInfo {
//    /**
//     * 订单编号
//     */
//    private String order_no;
//    /**
//     * 身份证正面照（可供下载url）数组最后一张为最新
//     */
//    private List<String> ID_Positive;
//    /**
//     * 身份证反面照（可供下载url）数组最后一张为最新
//     */
//    private List<String> ID_Negative;
//    /**
//     * 活体（可供下载url）数组最后一张为最新
//     */
//    private List<String> photo_assay;
//    /**
//     * OCR 识别结果-姓名
//     */
//    private String Name_OCR;
//    /**
//     * OCR 识别结果-民族
//     */
//    private String ID_Ethnic_OCR;
//    /**
//     * OCR 识别结果-地址
//     */
//    private String ID_Address_OCR;
//    /**
//     * OCR 识别结果-身份证号
//     */
//    private String ID_Number_OCR;
//    /**
//     * OCR 识别结果-性别
//     */
//    private String ID_Sex_OCR;
//    /**
//     * OCR 识别结果-签发机关
//     */
//    private String ID_Issue_Org_OCR;
//    /**
//     * OCR 识别结果-有效截止时间
//     */
//    private String ID_Due_time_OCR;
//    /**
//     * OCR 识别结果-有效开始时间
//     */
//    private String ID_Effect_time_OCR;
//    /**
//     * 居住地址
//     */
//    private String addr_detail;
//    /**
//     * 居住方式 1.自有住房，无贷款2.自有住房、有贷款3.与父母/配偶同住4.租房5.公司宿舍6.自建房 7.其他
//     */
//    private Integer family_live_type;
//    /**
//     * 常用邮箱地址
//     */
//    private String user_email;
//    /**
//     * 常用 qq
//     */
//    private String user_qq;
//    /**
//     * 常用微信号
//     */
//    private String user_wechat;
//    /**
//     * 婚姻状态 1=未婚,2=已婚无子女,3=已婚有子女,4=离异,5=丧偶,6=复婚
//     */
//    private Integer user_marriage;
//    /**
//     * 车辆情况 1-无车,2-本人名下有车，无贷款,3-本人名下有车，有按揭贷款,4- 本人名下有车，但已被抵押,5-其它
//     */
//    private Integer asset_auto_type;
//    /**
//     * 亲属联系人姓名
//     */
//    private String contact1A_name;
//    /**
//     * 亲属联系人电话
//     */
//    private String contact1A_number;
//    /**
//     * 紧急联系人姓名
//     */
//    private String emergency_contact_personA_name;
//    /**
//     * 紧急联系人电话
//     */
//    private String emergency_contact_personA_phone;
//    /**
//     * 公司名称
//     */
//    private String company_name;
//    /**
//     * 公司地址 省市区详细地址
//     */
//    private String company_addr_detail;
//    /**
//     * 行业类型1=批发/零售业 2=制造业3=金融业/保险/证券 4=商业服务业/娱乐/艺术/体育 5=计算机/互联网6=通讯/电子
//     */
//    private Integer industry_type;
//    /**
//     * 设备基本信息（联系人、APP 列表等）
//     */
//    private ExtraInfoContacts contacts;
//    /**
//     * 其他设备信息
//     */
//    private String device_info_all;
//    /**
//     * 是否模拟器 1=设备是模拟器0=设备非模拟器
//     */
//    private String is_simulator;
//
//    public String getOrder_no() {
//        return order_no;
//    }
//
//    public void setOrder_no(String order_no) {
//        this.order_no = order_no;
//    }
//
//    public List<String> getID_Positive() {
//        return ID_Positive;
//    }
//
//    public void setID_Positive(List<String> ID_Positive) {
//        this.ID_Positive = ID_Positive;
//    }
//
//    public List<String> getID_Negative() {
//        return ID_Negative;
//    }
//
//    public void setID_Negative(List<String> ID_Negative) {
//        this.ID_Negative = ID_Negative;
//    }
//
//    public List<String> getPhoto_assay() {
//        return photo_assay;
//    }
//
//    public void setPhoto_assay(List<String> photo_assay) {
//        this.photo_assay = photo_assay;
//    }
//
//    public String getName_OCR() {
//        return Name_OCR;
//    }
//
//    public void setName_OCR(String name_OCR) {
//        Name_OCR = name_OCR;
//    }
//
//    public String getID_Ethnic_OCR() {
//        return ID_Ethnic_OCR;
//    }
//
//    public void setID_Ethnic_OCR(String ID_Ethnic_OCR) {
//        this.ID_Ethnic_OCR = ID_Ethnic_OCR;
//    }
//
//    public String getID_Address_OCR() {
//        return ID_Address_OCR;
//    }
//
//    public void setID_Address_OCR(String ID_Address_OCR) {
//        this.ID_Address_OCR = ID_Address_OCR;
//    }
//
//    public String getID_Number_OCR() {
//        return ID_Number_OCR;
//    }
//
//    public void setID_Number_OCR(String ID_Number_OCR) {
//        this.ID_Number_OCR = ID_Number_OCR;
//    }
//
//    public String getID_Sex_OCR() {
//        return ID_Sex_OCR;
//    }
//
//    public void setID_Sex_OCR(String ID_Sex_OCR) {
//        this.ID_Sex_OCR = ID_Sex_OCR;
//    }
//
//    public String getID_Issue_Org_OCR() {
//        return ID_Issue_Org_OCR;
//    }
//
//    public void setID_Issue_Org_OCR(String ID_Issue_Org_OCR) {
//        this.ID_Issue_Org_OCR = ID_Issue_Org_OCR;
//    }
//
//    public String getID_Due_time_OCR() {
//        return ID_Due_time_OCR;
//    }
//
//    public void setID_Due_time_OCR(String ID_Due_time_OCR) {
//        this.ID_Due_time_OCR = ID_Due_time_OCR;
//    }
//
//    public String getID_Effect_time_OCR() {
//        return ID_Effect_time_OCR;
//    }
//
//    public void setID_Effect_time_OCR(String ID_Effect_time_OCR) {
//        this.ID_Effect_time_OCR = ID_Effect_time_OCR;
//    }
//
//    public String getAddr_detail() {
//        return addr_detail;
//    }
//
//    public void setAddr_detail(String addr_detail) {
//        this.addr_detail = addr_detail;
//    }
//
//    public Integer getFamily_live_type() {
//        return family_live_type;
//    }
//
//    public void setFamily_live_type(Integer family_live_type) {
//        this.family_live_type = family_live_type;
//    }
//
//    public String getUser_email() {
//        return user_email;
//    }
//
//    public void setUser_email(String user_email) {
//        this.user_email = user_email;
//    }
//
//    public String getUser_qq() {
//        return user_qq;
//    }
//
//    public void setUser_qq(String user_qq) {
//        this.user_qq = user_qq;
//    }
//
//    public String getUser_wechat() {
//        return user_wechat;
//    }
//
//    public void setUser_wechat(String user_wechat) {
//        this.user_wechat = user_wechat;
//    }
//
//    public Integer getUser_marriage() {
//        return user_marriage;
//    }
//
//    public void setUser_marriage(Integer user_marriage) {
//        this.user_marriage = user_marriage;
//    }
//
//    public Integer getAsset_auto_type() {
//        return asset_auto_type;
//    }
//
//    public void setAsset_auto_type(Integer asset_auto_type) {
//        this.asset_auto_type = asset_auto_type;
//    }
//
//    public String getContact1A_name() {
//        return contact1A_name;
//    }
//
//    public void setContact1A_name(String contact1A_name) {
//        this.contact1A_name = contact1A_name;
//    }
//
//    public String getContact1A_number() {
//        return contact1A_number;
//    }
//
//    public void setContact1A_number(String contact1A_number) {
//        this.contact1A_number = contact1A_number;
//    }
//
//    public String getEmergency_contact_personA_name() {
//        return emergency_contact_personA_name;
//    }
//
//    public void setEmergency_contact_personA_name(String emergency_contact_personA_name) {
//        this.emergency_contact_personA_name = emergency_contact_personA_name;
//    }
//
//    public String getEmergency_contact_personA_phone() {
//        return emergency_contact_personA_phone;
//    }
//
//    public void setEmergency_contact_personA_phone(String emergency_contact_personA_phone) {
//        this.emergency_contact_personA_phone = emergency_contact_personA_phone;
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
//    public String getCompany_addr_detail() {
//        return company_addr_detail;
//    }
//
//    public void setCompany_addr_detail(String company_addr_detail) {
//        this.company_addr_detail = company_addr_detail;
//    }
//
//    public Integer getIndustry_type() {
//        return industry_type;
//    }
//
//    public void setIndustry_type(Integer industry_type) {
//        this.industry_type = industry_type;
//    }
//
//    public ExtraInfoContacts getContacts() {
//        return contacts;
//    }
//
//    public void setContacts(ExtraInfoContacts contacts) {
//        this.contacts = contacts;
//    }
//
//    public String getDevice_info_all() {
//        return device_info_all;
//    }
//
//    public void setDevice_info_all(String device_info_all) {
//        this.device_info_all = device_info_all;
//    }
//
//    public String getIs_simulator() {
//        return is_simulator;
//    }
//
//    public void setIs_simulator(String is_simulator) {
//        this.is_simulator = is_simulator;
//    }
//}
