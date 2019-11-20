package com.waterelephant.third.entity.request;

import java.util.List;

/**
 * 统一对外接口 - 订单推送（code0091）
 * 
 * 
 * Module:
 * 
 * RequestPush.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class RequestPush {
	private BasicInfo basicInfo; // 基本信息
	private IdentifyInfo identifyInfo; // 身份证信息
	private CompanyInfo companyInfo; // 公司信息
	private List<Contact> contacts; // 通讯录
	private List<CallRecord> callRecords; // 通话记录
	private Operator operator; // 运营商信息
	private Object penetrate; // 透传信息
	private Object extendInfo; // 扩展信息

	public BasicInfo getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(BasicInfo basicInfo) {
		this.basicInfo = basicInfo;
	}

	public IdentifyInfo getIdentifyInfo() {
		return identifyInfo;
	}

	public void setIdentifyInfo(IdentifyInfo identifyInfo) {
		this.identifyInfo = identifyInfo;
	}

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public List<CallRecord> getCallRecords() {
		return callRecords;
	}

	public void setCallRecords(List<CallRecord> callRecords) {
		this.callRecords = callRecords;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Object getPenetrate() {
		return penetrate;
	}

	public void setPenetrate(Object penetrate) {
		this.penetrate = penetrate;
	}

	public Object getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(Object extendInfo) {
		this.extendInfo = extendInfo;
	}

}
