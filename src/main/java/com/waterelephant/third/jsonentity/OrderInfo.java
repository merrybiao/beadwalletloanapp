package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 * 订单基本信息
 * Created by dengyan on 2017/7/20.
 */
public class OrderInfo {

	@NotNull(message = "订单号为空")
    @JSONField(name = "order_no")
    private String orderNo; // 三方订单号

	@NotNull(message = "用户名为空")
    @JSONField(name = "user_name")
    private String userName; // 用户名

	@NotNull(message = "用户手机号为空")
    @JSONField(name = "user_mobile")
    private String userMobile; // 用户手机号

	@NotNull(message = "申请金额为空")
    @JSONField(name = "application_amount")
    private BigDecimal applicationAmount; // 申请金额

	@NotNull(message = "申请期限单位为空")
    @JSONField(name = "application_unit")
    private int applicationUnit; // 申请期限单位 期限单位 1:日 2：月

	@NotNull(message = "申请期限为空")
    @JSONField(name = "application_term")
    private int applicationTerm; // 申请期限

	@NotNull(message = "订单创建时间为空")
    @JSONField(name = "order_time")
    private long orderTime; // 订单创建时间 13位时间戳

	@NotNull(message = "订单状态为空")
    @JSONField(name = "status")
    private int status; // 订单状态

	@NotNull(message = "城市为空")
    @JSONField(name = "city")
    private String city; // 城市名

	@NotNull(message = "机构为空")
    @JSONField(name = "bank")
    private String bank; // 机构名

	@NotNull(message = "产品为空")
    @JSONField(name = "product")
    private String product; // 产品名

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

    public BigDecimal getApplicationAmount() {
        return applicationAmount;
    }

    public void setApplicationAmount(BigDecimal applicationAmount) {
        this.applicationAmount = applicationAmount;
    }

    public int getApplicationUnit() {
        return applicationUnit;
    }

    public void setApplicationUnit(int applicationUnit) {
        this.applicationUnit = applicationUnit;
    }

    public int getApplicationTerm() {
        return applicationTerm;
    }

    public void setApplicationTerm(int applicationTerm) {
        this.applicationTerm = applicationTerm;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
