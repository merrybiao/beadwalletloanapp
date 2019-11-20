package com.waterelephant.yixin.dto;


/**
 * 接口返回值
 * @Description:TODO
 * @author:yanfuxing
 * @time:2016年12月15日 下午5:46:56
 */
public class BackYiXinDto {

	private String errorCode;
	
	private String message;
	
	private String params;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	
}
