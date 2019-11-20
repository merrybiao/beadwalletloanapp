package com.waterelephant.jiedianqian.entity;

public class JieDianQianResponse {
	private int code; 					// 返回0为请求处理成功，其他为失败
	
	private Object data;
	
	private String desc; 				// 描述

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}


	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
