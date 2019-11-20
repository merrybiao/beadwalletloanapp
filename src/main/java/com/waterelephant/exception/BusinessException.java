package com.waterelephant.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -7002807248929593277L;

	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
	
	public BusinessException(String message) {
		
		super(message);
	}
	
	public BusinessException(String code,String message) {
		
		super(message+"["+code+"]");
	}
	
}
