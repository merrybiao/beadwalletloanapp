/**
  * Copyright 2017 bejson.com 
  */
package com.waterelephant.drainage.entity.youyu;

/**
 * 
 * 
 * 
 * Module: 订单实体类
 * 
 * Orderinfo.java 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class Orderinfo {

    private String order_no;//订单号
    private String user_name;//用户名
    private String user_mobile;//用户手机号
    private String application_amount;//贷款金额
    private String application_term;//贷款期限
    private String order_time;//下单时间
    private String status;//订单状态
    private String city;
    private String company;
    private String product;//产品
    private String user_id;//用户ID
    private int term_unit;//期限单位 1-天，2-月
    public void setOrder_no(String order_no) {
         this.order_no = order_no;
     }
     public String getOrder_no() {
         return order_no;
     }

    public void setUser_name(String user_name) {
         this.user_name = user_name;
     }
     public String getUser_name() {
         return user_name;
     }

    public void setUser_mobile(String user_mobile) {
         this.user_mobile = user_mobile;
     }
     public String getUser_mobile() {
         return user_mobile;
     }

    public void setApplication_amount(String application_amount) {
         this.application_amount = application_amount;
     }
     public String getApplication_amount() {
         return application_amount;
     }

    public void setApplication_term(String application_term) {
         this.application_term = application_term;
     }
     public String getApplication_term() {
         return application_term;
     }

    public void setOrder_time(String order_time) {
         this.order_time = order_time;
     }
     public String getOrder_time() {
         return order_time;
     }

    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setCity(String city) {
         this.city = city;
     }
     public String getCity() {
         return city;
     }

    public void setCompany(String company) {
         this.company = company;
     }
     public String getCompany() {
         return company;
     }

    public void setProduct(String product) {
         this.product = product;
     }
     public String getProduct() {
         return product;
     }

    public void setUser_id(String user_id) {
         this.user_id = user_id;
     }
     public String getUser_id() {
         return user_id;
     }

    public void setTerm_unit(int term_unit) {
         this.term_unit = term_unit;
     }
     public int getTerm_unit() {
         return term_unit;
     }

}