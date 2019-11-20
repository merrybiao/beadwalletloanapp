package com.waterelephant.loanwallet.entity;

import com.alibaba.fastjson.JSONObject;

public class LoanWalletResp<T> {
	private String code;
	private String message;
	private T result;
	
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
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoanWalletResp [");
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
		if (result != null) {
			builder.append("result=");
			builder.append(JSONObject.toJSONString(result));
		}
		builder.append("]");
		return builder.toString();
	} 
	
	
}