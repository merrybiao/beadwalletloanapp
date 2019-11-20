/**
  * Copyright 2017 bejson.com 
  */
package com.waterelephant.drainage.entity.youyu;

/****
 * 
 * 
 * 
 * Module: 
 * 
 * Userdata.java 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class Userdata {

    private String user_source; // 1:移动 2:联通 3:电信
    private String user_id;//用户id
    private String addr;
    private String user_name;//用户的姓名
    private String phone_remain;//手机余额
    private String user_mobile;
    private String regist_date;//注册时间
    private String report_date;
    public void setUser_source(String user_source) {
         this.user_source = user_source;
     }
     public String getUser_source() {
         return user_source;
     }

    public void setUser_id(String user_id) {
         this.user_id = user_id;
     }
     public String getUser_id() {
         return user_id;
     }

    public void setAddr(String addr) {
         this.addr = addr;
     }
     public String getAddr() {
         return addr;
     }

    public void setUser_name(String user_name) {
         this.user_name = user_name;
     }
     public String getUser_name() {
         return user_name;
     }

    public void setPhone_remain(String phone_remain) {
         this.phone_remain = phone_remain;
     }
     public String getPhone_remain() {
         return phone_remain;
     }

    public void setUser_mobile(String user_mobile) {
         this.user_mobile = user_mobile;
     }
     public String getUser_mobile() {
         return user_mobile;
     }

    public void setRegist_date(String regist_date) {
         this.regist_date = regist_date;
     }
     public String getRegist_date() {
         return regist_date;
     }

    public void setReport_date(String report_date) {
         this.report_date = report_date;
     }
     public String getReport_date() {
         return report_date;
     }

}