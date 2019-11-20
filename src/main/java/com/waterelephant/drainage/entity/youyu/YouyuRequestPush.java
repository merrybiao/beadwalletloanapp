/**
  * Copyright 2017 bejson.com 
  */
package com.waterelephant.drainage.entity.youyu;

/***
 * 
 * 
 * 
 * Module: 订单详情
 * 
 * YouyuRequestPush.java 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class YouyuRequestPush {

    private Orderinfo orderinfo;//订单基本信息
    private Applydetail applydetail;//申请的详细信息
    private Idinfo idinfo;//身份信息
    private Cardinfo cardinfo;//银行卡号信息
    
    private Mobileinfo mobileinfo;//手机号码信息
    
    public void setOrderinfo(Orderinfo orderinfo) {
         this.orderinfo = orderinfo;
     }
     public Orderinfo getOrderinfo() {
         return orderinfo;
     }

    public void setApplydetail(Applydetail applydetail) {
         this.applydetail = applydetail;
     }
     public Applydetail getApplydetail() {
         return applydetail;
     }

    public void setIdinfo(Idinfo idinfo) {
         this.idinfo = idinfo;
     }
     public Idinfo getIdinfo() {
         return idinfo;
     }

    public void setCardinfo(Cardinfo cardinfo) {
         this.cardinfo = cardinfo;
     }
     public Cardinfo getCardinfo() {
         return cardinfo;
     }

    public void setMobileinfo(Mobileinfo mobileinfo) {
         this.mobileinfo = mobileinfo;
     }
     public Mobileinfo getMobileinfo() {
         return mobileinfo;
     }

}