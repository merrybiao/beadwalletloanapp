package com.waterelephant.drainage.entity.fqgj;

public class BaseResp {
	private String error;
	private String msg;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return "BaseResp [" + (error != null ? "error=" + error + ", " : "") + (msg != null ? "msg=" + msg : "") + "]";
	}
}