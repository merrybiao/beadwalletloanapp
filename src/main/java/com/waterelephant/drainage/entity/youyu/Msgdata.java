/**
  * Copyright 2017 bejson.com 
  */
package com.waterelephant.drainage.entity.youyu;

/***
 * 
 * 
 * 
 * Module: 短信数据实体类
 * 
 * Msgdata.java 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class Msgdata {

    private String send_phone;//短信手机号
    private String send_time;//短信发送时间
    public void setSend_phone(String send_phone) {
         this.send_phone = send_phone;
     }
     public String getSend_phone() {
         return send_phone;
     }

    public void setSend_time(String send_time) {
         this.send_time = send_time;
     }
     public String getSend_time() {
         return send_time;
     }

}