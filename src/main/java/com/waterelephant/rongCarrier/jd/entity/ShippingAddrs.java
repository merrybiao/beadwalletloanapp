package com.waterelephant.rongCarrier.jd.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_jd_shipping_addrs")
public class ShippingAddrs {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	private int id;
	private long borrowerId;
	private String loginName;
	private String addrId;
	private String receiver;
	private String region;
	private String address;
	private String mobilePhone;
	private String fixedPhone;
	private String emailAddr;
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
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getAddrId() {
		return addrId;
	}
	public void setAddrId(String addrId) {
		this.addrId = addrId;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getFixedPhone() {
		return fixedPhone;
	}
	public void setFixedPhone(String fixedPhone) {
		this.fixedPhone = fixedPhone;
	}
	public String getEmailAddr() {
		return emailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
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
		return "ShippingAddrs [id=" + id + ", borrowerId=" + borrowerId
				+ ", loginName=" + loginName + ", addrId=" + addrId
				+ ", receiver=" + receiver + ", region=" + region
				+ ", address=" + address + ", mobilePhone=" + mobilePhone
				+ ", fixedPhone=" + fixedPhone + ", emailAddr=" + emailAddr
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}
}
