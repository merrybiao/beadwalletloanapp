package com.waterelephant.exception;

/**
 * 基类异常
 */
public class BaseException extends Exception {

	/**
	 * 序列化UID
	 */
	private static final long serialVersionUID = 7880284507046615977L;

	/**
	 * 错误码
	 */
	private Integer errCode = 0;
	
	/**
	 * 构造器
	 */
	public BaseException() {
		
	}
	
	/**
	 * 构造器
	 * @param message
	 */
	public BaseException(String message) {
		super(message);
	}
	
	/**
	 * 外部调用
	 * @return
	 */
	public Integer getErrCode() {
		return errCode;
	}
	
}
