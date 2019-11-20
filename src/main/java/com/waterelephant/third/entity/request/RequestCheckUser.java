package com.waterelephant.third.entity.request;

/**
 * 统一对外接口 - 用户检测（code0091）
 * 
 * @author zhangchong
 *
 */
public class RequestCheckUser {
	private String name; // 姓名
	private String idCard; // 身份证号
	private String mobile; // 手机号
	private String phone;
	private String orderNo;
	private String OrderId;
	private String loanAmount;
	private Object penetrate; // 透传信息

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Object getPenetrate() {
		return penetrate;
	}

	public void setPenetrate(Object penetrate) {
		this.penetrate = penetrate;
	}

}
