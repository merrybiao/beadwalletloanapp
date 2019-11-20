package com.waterelephant.utils;

public class ResponseData extends DefaultResponse{
	
	private Object retData;
	
	public ResponseData(String retCode, String retMsg, Object retData) {
		super(retCode, retMsg);
		this.retData = retData;
	}
	
	public Object getRetData() {
		return retData;
	}

	public void setRetData(Object retData) {
		this.retData = retData;
	}

}
