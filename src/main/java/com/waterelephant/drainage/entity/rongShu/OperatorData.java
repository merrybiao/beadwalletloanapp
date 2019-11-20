package com.waterelephant.drainage.entity.rongShu;

import java.util.List;

/**
 * @author xiaoXingWu
 * @time 2017年8月24日
 * @since JDK1.8
 * @description  运营商数据返回操作具体类
 */
public class OperatorData {
	private String  uid;
	private BasicUserInfo  basicInfo;
	private PhoneInfo  phoneInfo;
	private List<CallDetailList>  callDetailList;
	private List<SmsDetailList>  smsDetailList;
	private String createTime;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public BasicUserInfo getBasicInfo() {
		return basicInfo;
	}
	public void setBasicInfo(BasicUserInfo basicInfo) {
		this.basicInfo = basicInfo;
	}
	public PhoneInfo getPhoneInfo() {
		return phoneInfo;
	}
	public void setPhoneInfo(PhoneInfo phoneInfo) {
		this.phoneInfo = phoneInfo;
	}
	public List<CallDetailList> getCallDetailList() {
		return callDetailList;
	}
	public void setCallDetailList(List<CallDetailList> callDetailList) {
		this.callDetailList = callDetailList;
	}
	public List<SmsDetailList> getSmsDetailList() {
		return smsDetailList;
	}
	public void setSmsDetailList(List<SmsDetailList> smsDetailList) {
		this.smsDetailList = smsDetailList;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	

}

