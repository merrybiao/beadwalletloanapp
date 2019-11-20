package com.waterelephant.drainage.entity.youyu;

/***
 * 
 * 
 * 
 * Module: 绑卡返回的实体类
 * 
 * ResponseBindCard.java 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */

public class ResponseBindCard {

	public static int BINDCARD_CODE_SUCCESS = 0; // 接口调用成功
	public static int BINDCARD_CODE_FAIL = 1; // 接口调用成功
	private int code;	    //  状态码,0表示绑卡成功，1表示绑卡失败
	private String desc;		// 信息描述
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
	
	
}
