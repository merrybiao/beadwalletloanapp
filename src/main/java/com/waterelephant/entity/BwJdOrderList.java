package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_jd_order_list")
public class BwJdOrderList implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 主键
    private String receiver;// 收货人
    private Date createTime;// 数据创建时间
    private String buyWay;// 支付方式
    private Long buyTime;// 下单时间
    private String orderStatus;// 订单状态
    private Date updateTime;// 数据更新时间
    private String loginName;// 登录账户
    private Long borrowerId;// 水象用户id
    private Double money;// 订单金额
    private String productNames;// 商品名称，多个商品名称由分号分隔
    private String invoiceContent;// 发票内容
    private String receiverFixPhone;// 固定电话
    private String invoiceType;// 发票类型
    private String receiverTelephone;// 手机号
    private String orderId;// 订单id
    private String receiverAddr;// 收货地址
    private String invoiceHeader;// 发票抬头

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBuyWay() {
        return this.buyWay;
    }

    public void setBuyWay(String buyWay) {
        this.buyWay = buyWay;
    }

    public Long getBuyTime() {
        return this.buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getBorrowerId() {
        return this.borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Double getMoney() {
        return this.money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getProductNames() {
        return this.productNames;
    }

    public void setProductNames(String productNames) {
        this.productNames = productNames;
    }

    public String getInvoiceContent() {
        return this.invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getReceiverFixPhone() {
        return this.receiverFixPhone;
    }

    public void setReceiverFixPhone(String receiverFixPhone) {
        this.receiverFixPhone = receiverFixPhone;
    }

    public String getInvoiceType() {
        return this.invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getReceiverTelephone() {
        return this.receiverTelephone;
    }

    public void setReceiverTelephone(String receiverTelephone) {
        this.receiverTelephone = receiverTelephone;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReceiverAddr() {
        return this.receiverAddr;
    }

    public void setReceiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr;
    }

    public String getInvoiceHeader() {
        return this.invoiceHeader;
    }

    public void setInvoiceHeader(String invoiceHeader) {
        this.invoiceHeader = invoiceHeader;
    }

}
