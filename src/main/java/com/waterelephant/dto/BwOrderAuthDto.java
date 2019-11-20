package com.waterelephant.dto;

public class BwOrderAuthDto {

	private String authType;
	private Object authStatus;

	public BwOrderAuthDto() {
	};

	public BwOrderAuthDto(String authType, Object authStatus) {
		super();
		this.authType = authType;
		this.authStatus = authStatus;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public Object getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(Object authStatus) {
		this.authStatus = authStatus;
	}

	@Override
	public String toString() {
		return "BwOrderAuthDto [authType=" + authType + ", authStatus=" + authStatus + "]";
	}

}
