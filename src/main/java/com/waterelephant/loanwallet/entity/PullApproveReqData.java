package com.waterelephant.loanwallet.entity;

public class PullApproveReqData {
	private String userId;
	private String orderNo;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}