package com.waterelephant.cashloan.entity;

public class CashLoanRes {			//响应
	private String code;			//状态码 200成功
	private String message;			//success
	private String data;			//url  加密
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CashLoanRes [");
		if (code != null) {
			builder.append("code=");
			builder.append(code);
			builder.append(", ");
		}
		if (message != null) {
			builder.append("message=");
			builder.append(message);
			builder.append(", ");
		}
		if (data != null) {
			builder.append("data=");
			builder.append(data);
		}
		builder.append("]");
		return builder.toString();
	}
}