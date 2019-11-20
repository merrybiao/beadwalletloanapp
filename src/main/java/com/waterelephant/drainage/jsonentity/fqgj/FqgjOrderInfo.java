package com.waterelephant.drainage.jsonentity.fqgj;


import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/22 17:03
 */
@SuppressWarnings("serial")
public class FqgjOrderInfo implements Serializable {

    @JSONField(name="order_no")
    private String orderNo;

    @JSONField(name="user_name")
    private String userName;

    @JSONField(name="user_mobile")
    private String userMobile;

    @JSONField(name="application_amount")
    private BigDecimal amount;

    @JSONField(name="application_unit")
    private Integer unit;

    @JSONField(name="application_term")
    private Integer term;

    @JSONField(name="order_time")
    private Date orderTime;

    @JSONField(name="status")
    private Integer status;

    @JSONField(name="city")
    private String city;

    @JSONField(name="bank")
    private String bank;

    @JSONField(name="product")
    private String product;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

}
