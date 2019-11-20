package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 银行卡信息
 * Created by dengyan on 2017/7/20.
 */
public class BankCard {

    @JSONField(name = "order_no")
    private String orderNo; // 三方订单号

    @JSONField(name = "bank_card_number")
    private String bankCardNumber; // 绑卡卡号

    @JSONField(name = "open_bank")
    private String openBank; // 开卡银行

    @JSONField(name = "user_name")
    private String userName; // 卡主姓名

    @JSONField(name = "id_number")
    private String idNumber; // 卡主身份证号

    @JSONField(name = "user_mobile")
    private String userMobile; // 卡主手机号

    @JSONField(name = "bank_address")
    private String bankAddress; // 开户行地址

    @JSONField(name = "return_url")
    private String returnUrl; // 回调地址

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
