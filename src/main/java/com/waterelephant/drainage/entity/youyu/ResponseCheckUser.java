package com.waterelephant.drainage.entity.youyu;

/****
 * 
 * 
 * 
 * Module: 有鱼的返回实体类
 * 
 * ResponseCheckUser.java 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */

public class ResponseCheckUser {
	public static int CODE_NEW_USER=1;
	public static int CODE_OLD_USER=2;
	public static int CODE_UNAPPLIED=3;
	private int code;//1表示可申请新用户,2表示可申请老用户,3表示不可申请
	private String desc;//描述
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
