package com.waterelephant.third.entity;

/**
 * Module:
 * 
 * MessageDto.java
 * 
 * @author dengyan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class MessageDto {

	private String phone;// 发送方手机号
	private String msg;// 发送内容
	private String type;// 类型（1：文字，2：语音）
	private String businessScenario;// 业务场景(1:普通，2还款)
	private Integer inviteCount;// 发送次数

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getInviteCount() {
		return inviteCount;
	}

	public void setInviteCount(Integer inviteCount) {
		this.inviteCount = inviteCount;
	}

	public String getBusinessScenario() {
		return businessScenario;
	}

	public void setBusinessScenario(String businessScenario) {
		this.businessScenario = businessScenario;
	}

}
