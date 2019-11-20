package com.waterelephant.drainage.entity.rongShu;

/**
 * 榕树 - 5.3 进件推送 - 入参（紧急联系人信息）（code0087503）
 * 
 * 
 * Module:
 * 
 * Contract.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <榕树 - 5.3 进件推送 - 入参（紧急联系人信息）>
 */
public class Contract {
	private String firstName; // 联系人姓名（1）

	private String firstPhone; // 联系人电话（1）

	private String firstRelation; // 联系人关系（1）

	private String secondName; // 联系人姓名（2）

	private String secondPhone; // 联系人电话（2）

	private String secondRelation; // 联系人关系（2）

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstPhone(String firstPhone) {
		this.firstPhone = firstPhone;
	}

	public String getFirstPhone() {
		return this.firstPhone;
	}

	public void setFirstRelation(String firstRelation) {
		this.firstRelation = firstRelation;
	}

	public String getFirstRelation() {
		return this.firstRelation;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getSecondName() {
		return this.secondName;
	}

	public void setSecondPhone(String secondPhone) {
		this.secondPhone = secondPhone;
	}

	public String getSecondPhone() {
		return this.secondPhone;
	}

	public void setSecondRelation(String secondRelation) {
		this.secondRelation = secondRelation;
	}

	public String getSecondRelation() {
		return this.secondRelation;
	}
}
