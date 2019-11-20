/**
  * Copyright 2017 bejson.com 
  */
package com.waterelephant.drainage.entity.youyu;

/***
 * 
 * 
 * 
 * Module: 
 * 
 * Cardinfo.java 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class Cardinfo {

    private String open_bank;//开户行
    private String user_name;// 持卡人姓名
    private String user_mobile;//预留手机号
    private String bank_card;// 银行卡号
    private String bank_province;//银行省份
    private String bank_city;// 银行城市
    public void setOpen_bank(String open_bank) {
         this.open_bank = open_bank;
     }
     public String getOpen_bank() {
         return open_bank;
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

    public void setBank_card(String bank_card) {
         this.bank_card = bank_card;
     }
     public String getBank_card() {
         return bank_card;
     }

    public void setBank_province(String bank_province) {
         this.bank_province = bank_province;
     }
     public String getBank_province() {
         return bank_province;
     }

    public void setBank_city(String bank_city) {
         this.bank_city = bank_city;
     }
     public String getBank_city() {
         return bank_city;
     }

}