//package com.waterelephant.sxyDrainage.entity.jieba;
//
//import java.util.List;
//
///**
// * (code:jb001)
// *
// * @Author: ZhangChong
// * @Description:
// * @Date: Created in 16:43 2018/6/14
// * @Modified By:
// */
//public class PushAddOrderInfo {
//    /**
//     * 订单编号
//     */
//    private String order_id;
//    /**
//     * 紧急联系人A关系
//     */
//    private String emergency_contact_A;
//    /**
//     * 紧急联系人A姓名
//     */
//    private String emergency_contact_A_name;
//    /**
//     * 紧急联系人A电话
//     */
//    private String emergency_contact_A_phone;
//    /**
//     * 紧急联系人B关系
//     */
//    private String emergency_contact_B;
//    /**
//     * 紧急联系人B姓名
//     */
//    private String emergency_contact_B_name;
//    /**
//     * 紧急联系人B电话
//     */
//    private String emergency_contact_B_phone;
//    /**
//     * 婚姻状况
//     */
//    private Integer user_marriage;
//    /**
//     * 身份证正面照
//     */
//    private List<String> obverse_img;
//    /**
//     * 身份证反面照
//     */
//    private List<String> reverse_img;
//    /**
//     * 手持身份证照片
//     */
//    private List<String> human_img;
//    /**
//     * 活体
//     */
//    private List<String> query_img;
//    /**
//     * 公司名称
//     */
//    private String company_name;
//    /**
//     * 公司地址
//     */
//    private String work_detail_address;
//    /**
//     * 公司电话
//     */
//    private String work_phone;
//    /**
//     * 入学时间
//     */
//    private String matriculation_time;
//    /**
//     * 学校名称
//     */
//    private String graduate_school;
//    /**
//     * 下单IP地址
//     */
//    private String ip_address;
//    /**
//     * 常用邮箱地址
//     */
//    private String email;
//    /**
//     * 居住地址
//     */
//    private String detail_address;
//    /**
//     * 设备信息
//     */
//    private Contacts contacts;
//
//    public String getOrder_id() {
//        return order_id;
//    }
//
//    public void setOrder_id(String order_id) {
//        this.order_id = order_id;
//    }
//
//    public String getEmergency_contact_A() {
//        return emergency_contact_A;
//    }
//
//    public void setEmergency_contact_A(String emergency_contact_A) {
//        this.emergency_contact_A = emergency_contact_A;
//    }
//
//    public String getEmergency_contact_A_name() {
//        return emergency_contact_A_name;
//    }
//
//    public void setEmergency_contact_A_name(String emergency_contact_A_name) {
//        this.emergency_contact_A_name = emergency_contact_A_name;
//    }
//
//    public String getEmergency_contact_A_phone() {
//        return emergency_contact_A_phone;
//    }
//
//    public void setEmergency_contact_A_phone(String emergency_contact_A_phone) {
//        this.emergency_contact_A_phone = emergency_contact_A_phone;
//    }
//
//    public String getEmergency_contact_B() {
//        return emergency_contact_B;
//    }
//
//    public void setEmergency_contact_B(String emergency_contact_B) {
//        this.emergency_contact_B = emergency_contact_B;
//    }
//
//    public String getEmergency_contact_B_name() {
//        return emergency_contact_B_name;
//    }
//
//    public void setEmergency_contact_B_name(String emergency_contact_B_name) {
//        this.emergency_contact_B_name = emergency_contact_B_name;
//    }
//
//    public String getEmergency_contact_B_phone() {
//        return emergency_contact_B_phone;
//    }
//
//    public void setEmergency_contact_B_phone(String emergency_contact_B_phone) {
//        this.emergency_contact_B_phone = emergency_contact_B_phone;
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
//    public List<String> getObverse_img() {
//        return obverse_img;
//    }
//
//    public void setObverse_img(List<String> obverse_img) {
//        this.obverse_img = obverse_img;
//    }
//
//    public List<String> getReverse_img() {
//        return reverse_img;
//    }
//
//    public void setReverse_img(List<String> reverse_img) {
//        this.reverse_img = reverse_img;
//    }
//
//    public List<String> getHuman_img() {
//        return human_img;
//    }
//
//    public void setHuman_img(List<String> human_img) {
//        this.human_img = human_img;
//    }
//
//    public List<String> getQuery_img() {
//        return query_img;
//    }
//
//    public void setQuery_img(List<String> query_img) {
//        this.query_img = query_img;
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
//    public String getWork_detail_address() {
//        return work_detail_address;
//    }
//
//    public void setWork_detail_address(String work_detail_address) {
//        this.work_detail_address = work_detail_address;
//    }
//
//    public String getWork_phone() {
//        return work_phone;
//    }
//
//    public void setWork_phone(String work_phone) {
//        this.work_phone = work_phone;
//    }
//
//    public String getMatriculation_time() {
//        return matriculation_time;
//    }
//
//    public void setMatriculation_time(String matriculation_time) {
//        this.matriculation_time = matriculation_time;
//    }
//
//    public String getGraduate_school() {
//        return graduate_school;
//    }
//
//    public void setGraduate_school(String graduate_school) {
//        this.graduate_school = graduate_school;
//    }
//
//    public String getIp_address() {
//        return ip_address;
//    }
//
//    public void setIp_address(String ip_address) {
//        this.ip_address = ip_address;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getDetail_address() {
//        return detail_address;
//    }
//
//    public void setDetail_address(String detail_address) {
//        this.detail_address = detail_address;
//    }
//
//    public Contacts getContacts() {
//        return contacts;
//    }
//
//    public void setContacts(Contacts contacts) {
//        this.contacts = contacts;
//    }
//}
