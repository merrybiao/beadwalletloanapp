package com.waterelephant.drainage.entity.rongShu;

/**
 * @author xiaoXingWu
 * @time 2017年8月24日
 * @since JDK1.8
 * @description
 */
public class BasicUserInfo {
	private String realName;//真实姓名
	private String idcardNumber;//身份证号
	private String verifyState;//
	private String operator;//运营商
	private String inNetDate;//开户时间
	private String phoneNumber;//手机号
	private String updateTime;//更新时间
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdcardNumber() {
		return idcardNumber;
	}
	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}
	public String getVerifyState() {
		return verifyState;
	}
	public void setVerifyState(String verifyState) {
		this.verifyState = verifyState;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getInNetDate() {
		return inNetDate;
	}
	public void setInNetDate(String inNetDate) {
		this.inNetDate = inNetDate;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	

}
