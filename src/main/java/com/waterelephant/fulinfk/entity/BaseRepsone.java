package com.waterelephant.fulinfk.entity;

public class BaseRepsone {
	
	private String status;
	
	private String msg;

	
	public BaseRepsone(String status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
