package com.waterelephant.drainage.baidu.response;

/**
 * 回调响应
 * @author DIY
 *
 */
public class HttpResult {

	/**
	 * http请求码
	 */
	private int code;
	
	/**
	 * http请求错误信息
	 */
	private String msg;
	
	/**
	 * http请求数据
	 */
	private Object data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
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
