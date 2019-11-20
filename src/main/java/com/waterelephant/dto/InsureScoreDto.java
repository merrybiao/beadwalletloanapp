package com.waterelephant.dto;

public class InsureScoreDto {

	//户口所在城市
	private String accCity;
	//申请城市
	private String sqCity;
	//文化程度(学历)
	private String degree;
	//参保人电话
	private String insurePhone;
	//实名认证电话
	private String phone;
	//医疗参保人员类别
	private String workNation;
	//本单位社保连续缴纳月数
	private int continuityMonthInCom;
	//社保连续缴纳月数
	private int continuityMonth;
	
	public String getAccCity() {
		return accCity;
	}
	public void setAccCity(String accCity) {
		this.accCity = accCity;
	}
	public String getSqCity() {
		return sqCity;
	}
	public void setSqCity(String sqCity) {
		this.sqCity = sqCity;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	public String getInsurePhone() {
		return insurePhone;
	}
	public void setInsurePhone(String insurePhone) {
		this.insurePhone = insurePhone;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWorkNation() {
		return workNation;
	}
	public void setWorkNation(String workNation) {
		this.workNation = workNation;
	}
	public int getContinuityMonthInCom() {
		return continuityMonthInCom;
	}
	public void setContinuityMonthInCom(int continuityMonthInCom) {
		this.continuityMonthInCom = continuityMonthInCom;
	}
	public int getContinuityMonth() {
		return continuityMonth;
	}
	public void setContinuityMonth(int continuityMonth) {
		this.continuityMonth = continuityMonth;
	}
	
	
}
