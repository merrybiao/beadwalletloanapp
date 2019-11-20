package com.waterelephant.bjsms.entity;

public class PushResponse {
	
	private String status;

	public PushResponse(String status) {
		super();
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
