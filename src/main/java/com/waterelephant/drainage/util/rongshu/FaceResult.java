package com.waterelephant.drainage.util.rongshu;

public class FaceResult {

	/**
	 * 响应编码
	 */
	private String code;
	/**
	 * 响应信息
	 */
	private String msg;
	
	public FaceResult(){
		
	}
	
	public FaceResult(String code, String msg){
		this.code = code;
		this.msg = msg;
	}

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
}
