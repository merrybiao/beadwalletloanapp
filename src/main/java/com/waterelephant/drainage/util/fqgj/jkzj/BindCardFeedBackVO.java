package com.waterelephant.drainage.util.fqgj.jkzj;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/19 0019.
 */
public class BindCardFeedBackVO implements Serializable {

    @JsonProperty("order_no")
    private String orderNo;


    @JsonProperty("bank_card")
    private String bankCard;

    @JsonProperty("open_bank")
    private String openBank;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("id_number")
    private String idNumber;

    @JsonProperty("user_mobile")
    private String userMobile;

    @JsonProperty("bank_address")
    private String bankAddress;

    @JsonProperty("return_url")
    private String platformReturnUrl;

    @JsonProperty("verify_code")
    private Integer verifyCode;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }



    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
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

    public String getPlatformReturnUrl() {
        return platformReturnUrl;
    }

    public void setPlatformReturnUrl(String platformReturnUrl) {
        this.platformReturnUrl = platformReturnUrl;
    }

    public Integer getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(Integer verifyCode) {
        this.verifyCode = verifyCode;
    }
}
