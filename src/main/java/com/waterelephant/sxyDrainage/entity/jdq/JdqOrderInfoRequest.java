///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.jdq;
//
//import com.alibaba.fastjson.annotation.JSONField;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Module:
// * <p>
// * JdqOrderInfo.java
// *
// * @author 王飞
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//public class JdqOrderInfoRequest {
//    /**
//     * 用户基本信息
//     */
//    private User_info user_info;
//    /**
//     * 贷款信息
//     */
//    private Loan_info loan_info;
//    /**
//     * 联系人
//     */
//    private User_contact user_contact;
//    /**
//     * 通讯录
//     */
//    private List<Address_book> address_book;
//    /**
//     * gps
//     */
//    private User_login_upload_log user_login_upload_log;
//    /**
//     * 运营商数据
//     */
//    private String operator;
//    /**
//     * 借点钱订单号
//     */
//    private String jdq_order_id;
//
//    /**
//     * 设备信息
//     */
//    @JSONField(name = "device_info")
//    private DeviceInfo deviceInfo;
//
//    /**
//     * APP信息，列表
//     */
//    @JSONField(name = "app_data")
//    private Map<String, Object> appData;
//
//    public User_info getUser_info() {
//        return user_info;
//    }
//
//    public void setUser_info(User_info user_info) {
//        this.user_info = user_info;
//    }
//
//    public Loan_info getLoan_info() {
//        return loan_info;
//    }
//
//    public void setLoan_info(Loan_info loan_info) {
//        this.loan_info = loan_info;
//    }
//
//    public User_contact getUser_contact() {
//        return user_contact;
//    }
//
//    public void setUser_contact(User_contact user_contact) {
//        this.user_contact = user_contact;
//    }
//
//    public List<Address_book> getAddress_book() {
//        return address_book;
//    }
//
//    public void setAddress_book(List<Address_book> address_book) {
//        this.address_book = address_book;
//    }
//
//    public User_login_upload_log getUser_login_upload_log() {
//        return user_login_upload_log;
//    }
//
//    public void setUser_login_upload_log(User_login_upload_log user_login_upload_log) {
//        this.user_login_upload_log = user_login_upload_log;
//    }
//
//    public String getOperator() {
//        return operator;
//    }
//
//    public void setOperator(String operator) {
//        this.operator = operator;
//    }
//
//    public String getJdq_order_id() {
//        return jdq_order_id;
//    }
//
//    public void setJdq_order_id(String jdq_order_id) {
//        this.jdq_order_id = jdq_order_id;
//    }
//
//    public DeviceInfo getDeviceInfo() {
//        return deviceInfo;
//    }
//
//    public void setDeviceInfo(DeviceInfo deviceInfo) {
//        this.deviceInfo = deviceInfo;
//    }
//
//    public Map<String, Object> getAppData() {
//        return appData;
//    }
//
//    public void setAppData(Map<String, Object> appData) {
//        this.appData = appData;
//    }
//}
