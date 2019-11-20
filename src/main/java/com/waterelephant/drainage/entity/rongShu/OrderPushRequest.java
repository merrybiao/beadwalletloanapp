package com.waterelephant.drainage.entity.rongShu;

import com.alibaba.fastjson.JSONObject;

/**
 * 榕树 - 5.3 进件推送 - 入参（code0087503）
 * 
 * 
 * Module:
 * 
 * OrderPushRequest.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <榕树 - 5.3 进件推送（code0087503）>
 */
public class OrderPushRequest {
	private String  appLyDate; // 借款日期

	private String bank; // 银行

	private String bankCardDataUrl; // 信用卡数据URL

	private String bankCardNum; // 银行卡

	private String bankCity; // 开户行市

	private String bankCityId; // 开户行市ID

	private String bankCode;// 银行编号

	private String bankDistrict; // 开户行区

	private String bankDistrictId; // 开户行区ID

	private String bankProvince; // 开户行省

	private String bankProvinceId; // 开户行省ID

	private BasicInfo basicInfo; // 基本信息

	private String cid; // 身份证

	private Contract contact; // 紧急联系人信息

	private String desc; // 借款原因

	private String deviceDataUrl; // 设备采集数据URL

	private JSONObject extendInfo; // 补充数据

	private IdInfo idInfo; // 实名认证信息

	private String jdDataUrl; // 京东URL

	private String loanAmount; // 借款金额（单位：分）

	private String name; // 借款人姓名

	private Operator operator; // 运营商数据

	private String orderId; // 平台订单流水号

	private String period; // 借款周期

	private String phone; // 银行卡预留电话

	private String registerPhone; // 注册电话

	private String sesameScore; // 芝麻分

	private String sub_bank; // 开户行支行

	private String tbDataUrl; // 淘宝URL

	private String uid; // 用户在平台的ID

	private  String sign;//加签

	private  String content;//加签内容

	private String isInstalment;//是否分期产品

	public String getAppLyDate() {
		return appLyDate;
	}

	public void setAppLyDate(String appLyDate) {
		this.appLyDate = appLyDate;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankCardDataUrl() {
		return bankCardDataUrl;
	}

	public void setBankCardDataUrl(String bankCardDataUrl) {
		this.bankCardDataUrl = bankCardDataUrl;
	}

	public String getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankCityId() {
		return bankCityId;
	}

	public void setBankCityId(String bankCityId) {
		this.bankCityId = bankCityId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankDistrict() {
		return bankDistrict;
	}

	public void setBankDistrict(String bankDistrict) {
		this.bankDistrict = bankDistrict;
	}

	public String getBankDistrictId() {
		return bankDistrictId;
	}

	public void setBankDistrictId(String bankDistrictId) {
		this.bankDistrictId = bankDistrictId;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankProvinceId() {
		return bankProvinceId;
	}

	public void setBankProvinceId(String bankProvinceId) {
		this.bankProvinceId = bankProvinceId;
	}

	public BasicInfo getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(BasicInfo basicInfo) {
		this.basicInfo = basicInfo;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Contract getContact() {
		return contact;
	}

	public void setContact(Contract contact) {
		this.contact = contact;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDeviceDataUrl() {
		return deviceDataUrl;
	}

	public void setDeviceDataUrl(String deviceDataUrl) {
		this.deviceDataUrl = deviceDataUrl;
	}

	public JSONObject getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(JSONObject extendInfo) {
		this.extendInfo = extendInfo;
	}

	public IdInfo getIdInfo() {
		return idInfo;
	}

	public void setIdInfo(IdInfo idInfo) {
		this.idInfo = idInfo;
	}

	public String getJdDataUrl() {
		return jdDataUrl;
	}

	public void setJdDataUrl(String jdDataUrl) {
		this.jdDataUrl = jdDataUrl;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRegisterPhone() {
		return registerPhone;
	}

	public void setRegisterPhone(String registerPhone) {
		this.registerPhone = registerPhone;
	}

	public String getSesameScore() {
		return sesameScore;
	}

	public void setSesameScore(String sesameScore) {
		this.sesameScore = sesameScore;
	}

	public String getSub_bank() {
		return sub_bank;
	}

	public void setSub_bank(String sub_bank) {
		this.sub_bank = sub_bank;
	}

	public String getTbDataUrl() {
		return tbDataUrl;
	}

	public void setTbDataUrl(String tbDataUrl) {
		this.tbDataUrl = tbDataUrl;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIsInstalment() {
		return isInstalment;
	}

	public void setIsInstalment(String isInstalment) {
		this.isInstalment = isInstalment;
	}



	
	

}
