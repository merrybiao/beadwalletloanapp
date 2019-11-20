package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_tb_order")
public class BwTbOrder implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String actualFee;// 订单金额
    private String paymentTime;// 付款时间
    private String phoneOrder;// 是否手机订单
    private String confirmationTime;// 确认时间
    private String products;// 订单包含的商品 Json
    private Long bwOrderId;// 水象订单号
    private Long borrowerId;// 借款人ID
    private String transactionTime;// 成交时间
    private String receiverName;// 收货人姓名
    private String receiverAddress;// 收货地址
    private String receiverTelephone;// 收货人手机号
    private Date createDate;
    private String orderId;// 淘宝订单号
    private String status;// 订单状态

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActualFee() {
        return this.actualFee;
    }

    public void setActualFee(String actualFee) {
        this.actualFee = actualFee;
    }

    public String getPaymentTime() {
        return this.paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPhoneOrder() {
        return this.phoneOrder;
    }

    public void setPhoneOrder(String phoneOrder) {
        this.phoneOrder = phoneOrder;
    }

    public String getConfirmationTime() {
        return this.confirmationTime;
    }

    public void setConfirmationTime(String confirmationTime) {
        this.confirmationTime = confirmationTime;
    }

    public String getProducts() {
        return this.products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public Long getBwOrderId() {
        return this.bwOrderId;
    }

    public void setBwOrderId(Long bwOrderId) {
        this.bwOrderId = bwOrderId;
    }

    public Long getBorrowerId() {
        return this.borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getTransactionTime() {
        return this.transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getReceiverName() {
        return this.receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return this.receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverTelephone() {
        return this.receiverTelephone;
    }

    public void setReceiverTelephone(String receiverTelephone) {
        this.receiverTelephone = receiverTelephone;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
