package com.waterelephant.installment.dto;

public class AuthInfoDto {

	private Integer authType;
	private Integer authStatus;
	private Integer photoState;
	private Integer productType;
	private Integer orderId;

	public Integer getAuthType() {
		return authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	public Integer getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
	}

	public Integer getPhotoState() {
		return photoState;
	}

	public void setPhotoState(Integer photoState) {
		this.photoState = photoState;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

}
