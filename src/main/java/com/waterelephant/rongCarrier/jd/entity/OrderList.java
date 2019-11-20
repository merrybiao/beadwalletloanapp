package com.waterelephant.rongCarrier.jd.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_jd_order_list")
public class OrderList {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	private int id;
	private long borrowerId;
	private String orderId;
	private String receiver;
	private BigDecimal money;
	private String buyWay;
	private long buyTime;
	private String orderStatus;
	private String loginName;
	private String receiverAddr;
	private String receiverFixPhone;
	private String receiverTelephone;
	private String productNames;
	private String invoiceType;
	private String invoiceHeader;
	private String invoiceContent;
	private Date createTime;
	private Date updateTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getBorrowerId() {
		return borrowerId;
	}
	public void setBorrowerId(long borrowerId) {
		this.borrowerId = borrowerId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getBuyWay() {
		return buyWay;
	}
	public void setBuyWay(String buyWay) {
		this.buyWay = buyWay;
	}
	public long getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(long buyTime) {
		this.buyTime = buyTime;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getReceiverAddr() {
		return receiverAddr;
	}
	public void setReceiverAddr(String receiverAddr) {
		this.receiverAddr = receiverAddr;
	}
	public String getReceiverFixPhone() {
		return receiverFixPhone;
	}
	public void setReceiverFixPhone(String receiverFixPhone) {
		this.receiverFixPhone = receiverFixPhone;
	}
	public String getReceiverTelephone() {
		return receiverTelephone;
	}
	public void setReceiverTelephone(String receiverTelephone) {
		this.receiverTelephone = receiverTelephone;
	}
	public String getProductNames() {
		return productNames;
	}
	public void setProductNames(String productNames) {
		this.productNames = productNames;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getInvoiceHeader() {
		return invoiceHeader;
	}
	public void setInvoiceHeader(String invoiceHeader) {
		this.invoiceHeader = invoiceHeader;
	}
	public String getInvoiceContent() {
		return invoiceContent;
	}
	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "OrderList [id=" + id + ", borrowerId=" + borrowerId
				+ ", orderId=" + orderId + ", receiver=" + receiver
				+ ", money=" + money + ", buyWay=" + buyWay + ", buyTime="
				+ buyTime + ", orderStatus=" + orderStatus + ", loginName="
				+ loginName + ", receiverAddr=" + receiverAddr
				+ ", receiverFixPhone=" + receiverFixPhone
				+ ", receiverTelephone=" + receiverTelephone
				+ ", productNames=" + productNames + ", invoiceType="
				+ invoiceType + ", invoiceHeader=" + invoiceHeader
				+ ", invoiceContent=" + invoiceContent + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}
}
