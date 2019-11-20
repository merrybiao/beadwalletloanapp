package com.waterelephant.rongtaobao.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
/**
 * 实体类
 * @author GuoKun
 * @version 1.0
 * @create_date 2017-6-8 9:36:18
 */
@Table(name="bw_tb_order")
@JsonIgnoreProperties(ignoreUnknown=true)
public class TbOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //
    private Long borrowerId; // 借款人ID
    private Long bwOrderId; // 水象订单号
    @JSONField(name="order_id")
    private String orderId; // 淘宝订单号
    @JSONField(name="status")
    private String status; // 订单状态
    @JSONField(name="actual_fee")
    private String actualFee; // 订单金额
    @JSONField(name="phone_order")
    private String phoneOrder; // 是否手机订单
    @JSONField(name="transaction_time")
    private String transactionTime; // 成交时间
    @JSONField(name="payment_time")
    private String paymentTime; // 付款时间
    @JSONField(name="confirmation_time")
    private String confirmationTime; // 确认时间
    @JSONField(name="receiver_name")
    private String receiverName; // 收货人姓名
    @JSONField(name="receiver_telephone")
    private String receiverTelephone; // 收货人手机号
    @JSONField(name="receiver_address")
    private String receiverAddress; // 收货地址
    @JSONField(name="products")
    private String products; // 订单包含的商品  Json
    private Date createDate; //
    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }

    public void setBorrowerId(Long borrowerId){
        this.borrowerId=borrowerId;
    }
    public Long getBorrowerId(){
        return this.borrowerId;
    }

    public void setBwOrderId(Long bwOrderId){
        this.bwOrderId=bwOrderId;
    }
    public Long getBwOrderId(){
        return this.bwOrderId;
    }

    public void setOrderId(String orderId){
        this.orderId=orderId;
    }
    public String getOrderId(){
        return this.orderId;
    }

    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }

    public void setActualFee(String actualFee){
        this.actualFee=actualFee;
    }
    public String getActualFee(){
        return this.actualFee;
    }

    public void setPhoneOrder(String phoneOrder){
        this.phoneOrder=phoneOrder;
    }
    public String getPhoneOrder(){
        return this.phoneOrder;
    }

    public void setTransactionTime(String transactionTime){
        this.transactionTime=transactionTime;
    }
    public String getTransactionTime(){
        return this.transactionTime;
    }

    public void setPaymentTime(String paymentTime){
        this.paymentTime=paymentTime;
    }
    public String getPaymentTime(){
        return this.paymentTime;
    }

    public void setConfirmationTime(String confirmationTime){
        this.confirmationTime=confirmationTime;
    }
    public String getConfirmationTime(){
        return this.confirmationTime;
    }

    public void setReceiverName(String receiverName){
        this.receiverName=receiverName;
    }
    public String getReceiverName(){
        return this.receiverName;
    }

    public void setReceiverTelephone(String receiverTelephone){
        this.receiverTelephone=receiverTelephone;
    }
    public String getReceiverTelephone(){
        return this.receiverTelephone;
    }

    public void setReceiverAddress(String receiverAddress){
        this.receiverAddress=receiverAddress;
    }
    public String getReceiverAddress(){
        return this.receiverAddress;
    }

    public void setProducts(String products){
        this.products=products;
    }
    public String getProducts(){
        return this.products;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("TbOrder[");
        sb.append("id=");
        sb.append(id);
        sb.append(",borrowerId=");
        sb.append(borrowerId);
        sb.append(",bwOrderId=");
        sb.append(bwOrderId);
        sb.append(",orderId=");
        sb.append(orderId);
        sb.append(",status=");
        sb.append(status);
        sb.append(",actualFee=");
        sb.append(actualFee);
        sb.append(",phoneOrder=");
        sb.append(phoneOrder);
        sb.append(",transactionTime=");
        sb.append(transactionTime);
        sb.append(",paymentTime=");
        sb.append(paymentTime);
        sb.append(",confirmationTime=");
        sb.append(confirmationTime);
        sb.append(",receiverName=");
        sb.append(receiverName);
        sb.append(",receiverTelephone=");
        sb.append(receiverTelephone);
        sb.append(",receiverAddress=");
        sb.append(receiverAddress);
        sb.append(",products=");
        sb.append(products);
        sb.append("]");
        return sb.toString();
    }
}
