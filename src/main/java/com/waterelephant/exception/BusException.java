package com.waterelephant.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

//import com.waterelephant.service.ManageSevice;

/**
 * 业务异常
 * @author 刘自宾
 *
 */
public class BusException extends BaseException {
	
//	@Autowired
//	private ManageSevice loginSevice;

	/**
	 * 序列化UID
	 */
	private static final long serialVersionUID = -7706258653529610771L;

	/**
	 * 构造器
	 */
	public BusException() {

	}
	
	/**
	 * 构造器
	 * @param message
	 */
	public BusException(String message) {
		super(message);
	}

	
}
