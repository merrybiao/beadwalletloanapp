package com.waterelephant.drainage.entity.rongShu;

/**
 * @author xiaoXingWu
 * @time 2017年8月28日
 * @since JDK1.8
 * @description 银行卡信息实体类
 */
public class BankCardInfo {
	private String name;// 姓名
	private String cid;// 身份证
	private String bank;// 银行
	private String bankCardNum;// 卡号
	private String phone;// 预留手机号
	private String uid;// 用户id
	private String registerPhone;// 注册电话
	private String subBank;// 开户行支行
	private String bankProvince;// 开户行省
	private String bankProvinceId;// 开户行省编码
	private String bankCity;// 开户行市
	private String bankCityId;// 开户行市编码
	private String bankDistrict;// 开户行区
	private String bankDistrictId;// 开户行区编码
	private String callbackurl;

	
	public String getCallbackurl() {
		return callbackurl;
	}

	public void setCallbackurl(String callbackurl) {
		this.callbackurl = callbackurl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRegisterPhone() {
		return registerPhone;
	}

	public void setRegisterPhone(String registerPhone) {
		this.registerPhone = registerPhone;
	}

	public String getSubBank() {
		return subBank;
	}

	public void setSubBank(String subBank) {
		this.subBank = subBank;
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

}
