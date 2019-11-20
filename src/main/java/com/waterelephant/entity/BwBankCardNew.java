package com.waterelephant.entity;

import java.io.Serializable;

public class BwBankCardNew implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;
    /**
     * 富友账号
     */
    private String fuiouAcct;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 城市名称
     */

    private String cityName;
    /**
     * 省名称
     */

    private String provinceName;
    /**
     * 银行名称
     */

    private String bankName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFuiouAcct() {
        return fuiouAcct;
    }

    public void setFuiouAcct(String fuiouAcct) {
        this.fuiouAcct = fuiouAcct;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
