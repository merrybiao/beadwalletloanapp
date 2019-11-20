/**
  * Copyright 2017 bejson.com 
  */
package com.waterelephant.drainage.entity.youyu;

/***
 * 
 * 
 * 
 * Module: 通话数据的实体类
 * 
 * Teldata.java 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class Teldata {

    private String talk_time;//通话时长
    private String call_time;//拨打时间
    private String call_addr;//通话地址
    private String call_phone; // 通话手机号
    private String call_type;// 通话类型  0:主叫 1:被叫
    
    
    public void setTalk_time(String talk_time) {
         this.talk_time = talk_time;
     }
     public String getTalk_time() {
         return talk_time;
     }

    public void setCall_time(String call_time) {
         this.call_time = call_time;
     }
     public String getCall_time() {
         return call_time;
     }

    public void setCall_addr(String call_addr) {
         this.call_addr = call_addr;
     }
     public String getCall_addr() {
         return call_addr;
     }

    public void setCall_phone(String call_phone) {
         this.call_phone = call_phone;
     }
     public String getCall_phone() {
         return call_phone;
     }

    public void setCall_type(String call_type) {
         this.call_type = call_type;
     }
     public String getCall_type() {
         return call_type;
     }

}