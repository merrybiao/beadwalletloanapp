package com.waterelephant.fudata.entity;

/**
 * @author dinglinhao
 *
 */
public class FudataResponse extends DefaultFudataResponse {

	private static final long serialVersionUID = 4710627662955204403L;
	
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
  
}