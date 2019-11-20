//package com.waterelephant.sxyDrainage.entity.shandiandai.pushorder;
//
///**
// * 用户基本信息
// *
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/1
// * @since JDK 1.8
// */
//public class SddPersonalInfo {
//    /** 用户姓名 */
//    private String name;
//    /** 用户手机号 */
//    private String phone;
//    /** 身份证，json格式 */
//    private SddIdCard idCard;
//    /** 所在地区，json格式 */
//    private SddLocation location;
//    /** 婚姻状态 */
//    private int marriage;
//    /** 职业类别： */
//    private String job;
//    /** 公司名称 */
//    private String companyName;
//    /** 公司地址，json格式 */
//    private SddCompanyAddress companyAddress;
//    /** 工作年限 年 */
//    private Integer workYear;
//    /** 收入 */
//    private String income;
//    /** 紧急联系人1 姓名 */
//    private String firstEmergencyName;
//    /** 紧急联系人1 电话 */
//    private String firstEmergencyPhone;
//    /** 紧急联系人1 关系 */
//    private int firstEmergencyRelationship;
//    /** 紧急联系人2 姓名 */
//    private String secondEmergencyName;
//    /** 紧急联系人2 电话 */
//    private String secondEmergencyPhone;
//    /** 紧急联系人2 关系 */
//    private int secondEmergencyRelationship;
//
//    /** 微信号 */
//    private String wechatNumber;
//    /** qq */
//    private String qq;
//    /** email */
//    private String email;
//
//    /** 同事姓名 */
//    private String workmateName;
//    /** 同事电话 */
//    private String workmatePhone;
//    /** 朋友1姓名 */
//    private String friendFirstName;
//    /** 朋友1电话 */
//    private String friendFirstPhone;
//    /** 朋友2姓名 */
//    private String friendSecondName;
//    /** 朋友2电话 */
//    private String friendSecondPhone;
//
//
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
//    public SddIdCard getIdCard() {
//        return idCard;
//    }
//
//    public void setIdCard(SddIdCard idCard) {
//        this.idCard = idCard;
//    }
//
//    public SddLocation getLocation() {
//        return location;
//    }
//
//    public void setLocation(SddLocation location) {
//        this.location = location;
//    }
//
//    public int getMarriage() {
//        return marriage;
//    }
//
//    public void setMarriage(int marriage) {
//        this.marriage = marriage;
//    }
//
//    public String getJob() {
//        return job;
//    }
//
//    public void setJob(String job) {
//        this.job = job;
//    }
//
//    public String getCompanyName() {
//        return companyName;
//    }
//
//    public void setCompanyName(String companyName) {
//        this.companyName = companyName;
//    }
//
//    public SddCompanyAddress getCompanyAddress() {
//        return companyAddress;
//    }
//
//    public void setCompanyAddress(SddCompanyAddress companyAddress) {
//        this.companyAddress = companyAddress;
//    }
//
//    public Integer getWorkYear() {
//        return workYear;
//    }
//
//    public void setWorkYear(Integer workYear) {
//        this.workYear = workYear;
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
//    public String getFirstEmergencyName() {
//        return firstEmergencyName;
//    }
//
//    public void setFirstEmergencyName(String firstEmergencyName) {
//        this.firstEmergencyName = firstEmergencyName;
//    }
//
//    public String getFirstEmergencyPhone() {
//        return firstEmergencyPhone;
//    }
//
//    public void setFirstEmergencyPhone(String firstEmergencyPhone) {
//        this.firstEmergencyPhone = firstEmergencyPhone;
//    }
//
//    public int getFirstEmergencyRelationship() {
//        return firstEmergencyRelationship;
//    }
//
//    public void setFirstEmergencyRelationship(int firstEmergencyRelationship) {
//        this.firstEmergencyRelationship = firstEmergencyRelationship;
//    }
//
//    public String getSecondEmergencyName() {
//        return secondEmergencyName;
//    }
//
//    public void setSecondEmergencyName(String secondEmergencyName) {
//        this.secondEmergencyName = secondEmergencyName;
//    }
//
//    public String getSecondEmergencyPhone() {
//        return secondEmergencyPhone;
//    }
//
//    public void setSecondEmergencyPhone(String secondEmergencyPhone) {
//        this.secondEmergencyPhone = secondEmergencyPhone;
//    }
//
//    public int getSecondEmergencyRelationship() {
//        return secondEmergencyRelationship;
//    }
//
//    public void setSecondEmergencyRelationship(int secondEmergencyRelationship) {
//        this.secondEmergencyRelationship = secondEmergencyRelationship;
//    }
//
//    public String getWorkmateName() {
//        return workmateName;
//    }
//
//    public void setWorkmateName(String workmateName) {
//        this.workmateName = workmateName;
//    }
//
//    public String getWorkmatePhone() {
//        return workmatePhone;
//    }
//
//    public void setWorkmatePhone(String workmatePhone) {
//        this.workmatePhone = workmatePhone;
//    }
//
//    public String getFriendFirstName() {
//        return friendFirstName;
//    }
//
//    public void setFriendFirstName(String friendFirstName) {
//        this.friendFirstName = friendFirstName;
//    }
//
//    public String getFriendFirstPhone() {
//        return friendFirstPhone;
//    }
//
//    public void setFriendFirstPhone(String friendFirstPhone) {
//        this.friendFirstPhone = friendFirstPhone;
//    }
//
//    public String getFriendSecondName() {
//        return friendSecondName;
//    }
//
//    public void setFriendSecondName(String friendSecondName) {
//        this.friendSecondName = friendSecondName;
//    }
//
//    public String getFriendSecondPhone() {
//        return friendSecondPhone;
//    }
//
//    public void setFriendSecondPhone(String friendSecondPhone) {
//        this.friendSecondPhone = friendSecondPhone;
//    }
//
//    public String getWechatNumber() {
//        return wechatNumber;
//    }
//
//    public void setWechatNumber(String wechatNumber) {
//        this.wechatNumber = wechatNumber;
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
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//}
