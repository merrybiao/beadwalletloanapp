package com.waterelephant.operatorData.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class OperatorUserDataDto {
	
	@JSONField(name="update_time",ordinal =1)
	private String updateTime="";
	
	@JSONField(name="user_source",ordinal =2)
	private String userSource="";
	
	@JSONField(name="id_card",ordinal=3)
	private String idCard="";
	
	@JSONField(name="addr",ordinal=4)
	private String addr="";
	
	@JSONField(name="real_name",ordinal=5)
	private String realName="";
	
	@JSONField(name="phone_remain",ordinal=6)
	private String phoneRemain="";
	
	@JSONField(name="phone",ordinal=7)
	private String phone="";
	
	@JSONField(name="reg_time",ordinal=8)
	private String regTime="";
	
	@JSONField(name="score",ordinal=9)
	private String score="";
	
	@JSONField(name="contact_phone",ordinal=10)
	private String contactPhone="";
	
	@JSONField(name="star_level",ordinal=11)
	private String starLevel="";
	
	@JSONField(name="authentication",ordinal=12)
	private String authentication="";
	
	@JSONField(name="phone_status",ordinal=13)
	private String phoneStatus="";
	
	@JSONField(name="package_name",ordinal=14)
	private String packageName="";
	
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUserSource() {
		return userSource;
	}
	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhoneRemain() {
		return phoneRemain;
	}
	public void setPhoneRemain(String phoneRemain) {
		this.phoneRemain = phoneRemain;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getStarLevel() {
		return starLevel;
	}
	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
	}
	public String getAuthentication() {
		return authentication;
	}
	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}
	public String getPhoneStatus() {
		return phoneStatus;
	}
	public void setPhoneStatus(String phoneStatus) {
		this.phoneStatus = phoneStatus;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
}
