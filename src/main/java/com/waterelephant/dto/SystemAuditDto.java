package com.waterelephant.dto;

import java.util.Date;

/**
 * 系统审核任务DTO 20161214
 * @author duxiaoyong
 *
 */
public class SystemAuditDto {
	
	private Long orderId;// 工单ID
	private Long borrowerId; // 借款人ID
	private Date createTime; // 创建时间
	private String name;     
	private String phone;
	private String idCard;
	private Integer includeAddressBook=0;// 是否包含通讯录；0：不包含，1：包含
	private Integer Channel=0;
	private String thirdOrderId="";     //第三方订单号
	public SystemAuditDto(){
		
	}
	
	public SystemAuditDto(Long orderId, Long borrowerId, Date createTime, Integer includeAddressBook) {
		super();
		this.orderId = orderId;
		this.borrowerId = borrowerId;
		this.createTime = createTime;
		this.includeAddressBook = includeAddressBook;
	}
	@Override
	public String toString() {
		return "SystemAuditDto [orderId=" + orderId + ", borrowerId=" + borrowerId + ", createTime=" + createTime
				+ ", name=" + name + ", phone=" + phone + ", idCard=" + idCard + ", includeAddressBook="
				+ includeAddressBook + "]";
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getIncludeAddressBook() {
		return includeAddressBook;
	}

	public void setIncludeAddressBook(Integer includeAddressBook) {
		this.includeAddressBook = includeAddressBook;
	}

	public Integer getChannel() {
		return Channel;
	}

	public void setChannel(Integer channel) {
		Channel = channel;
	}

	public String getThirdOrderId() {
		return thirdOrderId;
	}

	public void setThirdOrderId(String thirdOrderId) {
		this.thirdOrderId = thirdOrderId;
	}

	
	
	
	
	
	
}
