package com.waterelephant.dto;

public class OrderAuthDto {
	
	private Integer authType;
	private Integer authStatus;
	
	public OrderAuthDto() {
	}

	public OrderAuthDto(Integer authType, Integer authStatus) {
		this.authType = authType;
		this.authStatus = authStatus;
	}

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

	@Override
	public String toString() {
		return "OrderAuthDto [authType=" + authType + ", authStatus=" + authStatus + "]";
	}

}
