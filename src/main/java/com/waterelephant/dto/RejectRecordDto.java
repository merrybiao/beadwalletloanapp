package com.waterelephant.dto;

import java.util.Date;

public class RejectRecordDto {

	private Long id;
	private String rejectInfo;
	private String rejectCode;
	private Long orderId;
	private Date createTime;
	private Integer limiteTime;
	private Integer rejectType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRejectInfo() {
		return rejectInfo;
	}
	public void setRejectInfo(String rejectInfo) {
		this.rejectInfo = rejectInfo;
	}
	public String getRejectCode() {
		return rejectCode;
	}
	public void setRejectCode(String rejectCode) {
		this.rejectCode = rejectCode;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Integer getLimiteTime() {
		return limiteTime;
	}
	public void setLimiteTime(Integer limiteTime) {
		this.limiteTime = limiteTime;
	}
	public Integer getRejectType() {
		return rejectType;
	}
	public void setRejectType(Integer rejectType) {
		this.rejectType = rejectType;
	}
	
}
