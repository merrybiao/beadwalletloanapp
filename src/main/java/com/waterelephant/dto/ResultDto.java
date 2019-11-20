package com.waterelephant.dto;

public class ResultDto {

	private String code; // 1 成功  0 失败
	private String msg;//返回消息
	private Object data ;//返回结果集
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}


	
	
}
