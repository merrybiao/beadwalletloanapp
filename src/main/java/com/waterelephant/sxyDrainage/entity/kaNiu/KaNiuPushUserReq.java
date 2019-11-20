///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.kaNiu;
//
//import java.util.List;
//
///**
// * Module: 
// * KaNiuCheckPushUser.java 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class KaNiuPushUserReq {
//	private String ssjLoanNo;
//	private String address;
//	private String addressDetailInfo;
//	private String addressInfo;
//	private String backImgURL;
//	private String companyDetailAddress;
//	private String companyName;
//	private String companyPhone;
//	private String contactsAname;
//	private String contactsAphone;
//	private String contactsArelation;
//	private String contactsBname;
//	private String contactsBphone;
//	private String contactsBrelation;
//	private String contactsCname;
//	private String contactsCphone;
//	private String contactsCrelation;
//	private String contactsDname;
//	private String contactsDphone;
//	private String contactsDrelation;
//	private String contactsEname;
//	private String contactsEphone;
//	private String contactsErelation;
//	private String degree;
//	private String depoistBankName;
//	private String depoistCardNo;
//	private String depoistPersonName;
//	private String depoistPrePhoneNo;
//	private List<DeviceInfoVo> deviceInfo;
//	private String email;
//	private String faceImage;
//	private String frontImgURL;
//	private String idCard;
//	private String industry;
//	private String jobYears;
//	private String livingTime;
//	private String loanDeadLine;
//	private String loanMoney;
//	private String loanPurpose;
//	private String marriageInfo;
//	private String mobilePhone;
//	private String name;
//	private String nowCompanyAddress;
//	private OperatorInfoVo operatorInfo;
//	private KNOperatorBasicInfo operatorBasicInfo;
//	private String qq;
//	private String salary;
//	private String valid_date_end;
//	private String valid_date_start;
//	private String weChat;
//	private String zmScore;
//	private String uid;
//	private String channel;
//	private String sex;
//	private String nation;
//	private String birthYear;
//	private String birthMonth;
//	private String birthDay;
//	private String idCardNo;
//	private String signOrg;
//	private String validDate;
//	public KNOperatorBasicInfo getOperatorBasicInfo() {
//		return operatorBasicInfo;
//	}
//
//	public void setOperatorBasicInfo(KNOperatorBasicInfo operatorBasicInfo) {
//		this.operatorBasicInfo = operatorBasicInfo;
//	}
//
//	public String getZmScore() {
//		return zmScore;
//	}
//
//	public void setZmScore(String zmScore) {
//		this.zmScore = zmScore;
//	}
//
//	public String getUid() {
//		return uid;
//	}
//
//	public void setUid(String uid) {
//		this.uid = uid;
//	}
//
//	public String getChannel() {
//		return channel;
//	}
//
//	public void setChannel(String channel) {
//		this.channel = channel;
//	}
//
//	public String getSsjLoanNo() {
//		return ssjLoanNo;
//	}
//
//	public void setSsjLoanNo(String ssjLoanNo) {
//		this.ssjLoanNo = ssjLoanNo;
//	}
//
//	public List<DeviceInfoVo> getDeviceInfo() {
//		return deviceInfo;
//	}
//
//	public void setDeviceInfo(List<DeviceInfoVo> deviceInfo) {
//		this.deviceInfo = deviceInfo;
//	}
//
//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//	public String getAddressDetailInfo() {
//		return addressDetailInfo;
//	}
//
//	public void setAddressDetailInfo(String addressDetailInfo) {
//		this.addressDetailInfo = addressDetailInfo;
//	}
//
//	public String getAddressInfo() {
//		return addressInfo;
//	}
//
//	public void setAddressInfo(String addressInfo) {
//		this.addressInfo = addressInfo;
//	}
//
//	public String getBackImgURL() {
//		return backImgURL;
//	}
//
//	public void setBackImgURL(String backImgURL) {
//		this.backImgURL = backImgURL;
//	}
//
//	public String getCompanyDetailAddress() {
//		return companyDetailAddress;
//	}
//
//	public void setCompanyDetailAddress(String companyDetailAddress) {
//		this.companyDetailAddress = companyDetailAddress;
//	}
//
//	public String getCompanyName() {
//		return companyName;
//	}
//
//	public void setCompanyName(String companyName) {
//		this.companyName = companyName;
//	}
//
//	public String getCompanyPhone() {
//		return companyPhone;
//	}
//
//	public void setCompanyPhone(String companyPhone) {
//		this.companyPhone = companyPhone;
//	}
//
//	public String getContactsAname() {
//		return contactsAname;
//	}
//
//	public void setContactsAname(String contactsAname) {
//		this.contactsAname = contactsAname;
//	}
//
//	public String getContactsAphone() {
//		return contactsAphone;
//	}
//
//	public void setContactsAphone(String contactsAphone) {
//		this.contactsAphone = contactsAphone;
//	}
//
//	public String getContactsArelation() {
//		return contactsArelation;
//	}
//
//	public void setContactsArelation(String contactsArelation) {
//		this.contactsArelation = contactsArelation;
//	}
//
//	public String getContactsBname() {
//		return contactsBname;
//	}
//
//	public void setContactsBname(String contactsBname) {
//		this.contactsBname = contactsBname;
//	}
//
//	public String getContactsBphone() {
//		return contactsBphone;
//	}
//
//	public void setContactsBphone(String contactsBphone) {
//		this.contactsBphone = contactsBphone;
//	}
//
//	public String getContactsBrelation() {
//		return contactsBrelation;
//	}
//
//	public void setContactsBrelation(String contactsBrelation) {
//		this.contactsBrelation = contactsBrelation;
//	}
//
//	public String getContactsCname() {
//		return contactsCname;
//	}
//
//	public void setContactsCname(String contactsCname) {
//		this.contactsCname = contactsCname;
//	}
//
//	public String getContactsCphone() {
//		return contactsCphone;
//	}
//
//	public void setContactsCphone(String contactsCphone) {
//		this.contactsCphone = contactsCphone;
//	}
//
//	public String getContactsCrelation() {
//		return contactsCrelation;
//	}
//
//	public void setContactsCrelation(String contactsCrelation) {
//		this.contactsCrelation = contactsCrelation;
//	}
//
//	public String getContactsDname() {
//		return contactsDname;
//	}
//
//	public void setContactsDname(String contactsDname) {
//		this.contactsDname = contactsDname;
//	}
//
//	public String getContactsDphone() {
//		return contactsDphone;
//	}
//
//	public void setContactsDphone(String contactsDphone) {
//		this.contactsDphone = contactsDphone;
//	}
//
//	public String getContactsDrelation() {
//		return contactsDrelation;
//	}
//
//	public void setContactsDrelation(String contactsDrelation) {
//		this.contactsDrelation = contactsDrelation;
//	}
//
//	public String getContactsEname() {
//		return contactsEname;
//	}
//
//	public void setContactsEname(String contactsEname) {
//		this.contactsEname = contactsEname;
//	}
//
//	public String getContactsEphone() {
//		return contactsEphone;
//	}
//
//	public void setContactsEphone(String contactsEphone) {
//		this.contactsEphone = contactsEphone;
//	}
//
//	public String getContactsErelation() {
//		return contactsErelation;
//	}
//
//	public void setContactsErelation(String contactsErelation) {
//		this.contactsErelation = contactsErelation;
//	}
//
//	public String getDegree() {
//		return degree;
//	}
//
//	public void setDegree(String degree) {
//		this.degree = degree;
//	}
//
//	public String getDepoistBankName() {
//		return depoistBankName;
//	}
//
//	public void setDepoistBankName(String depoistBankName) {
//		this.depoistBankName = depoistBankName;
//	}
//
//	public String getDepoistCardNo() {
//		return depoistCardNo;
//	}
//
//	public void setDepoistCardNo(String depoistCardNo) {
//		this.depoistCardNo = depoistCardNo;
//	}
//
//	public String getDepoistPersonName() {
//		return depoistPersonName;
//	}
//
//	public void setDepoistPersonName(String depoistPersonName) {
//		this.depoistPersonName = depoistPersonName;
//	}
//
//	public String getDepoistPrePhoneNo() {
//		return depoistPrePhoneNo;
//	}
//
//	public void setDepoistPrePhoneNo(String depoistPrePhoneNo) {
//		this.depoistPrePhoneNo = depoistPrePhoneNo;
//	}
//
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getFaceImage() {
//		return faceImage;
//	}
//
//	public void setFaceImage(String faceImage) {
//		this.faceImage = faceImage;
//	}
//
//	public String getFrontImgURL() {
//		return frontImgURL;
//	}
//
//	public void setFrontImgURL(String frontImgURL) {
//		this.frontImgURL = frontImgURL;
//	}
//
//	public String getIdCard() {
//		return idCard;
//	}
//
//	public void setIdCard(String idCard) {
//		this.idCard = idCard;
//	}
//
//	public String getSex() {
//		return sex;
//	}
//
//	public void setSex(String sex) {
//		this.sex = sex;
//	}
//
//	public String getNation() {
//		return nation;
//	}
//
//	public void setNation(String nation) {
//		this.nation = nation;
//	}
//
//	public String getBirthYear() {
//		return birthYear;
//	}
//
//	public void setBirthYear(String birthYear) {
//		this.birthYear = birthYear;
//	}
//
//	public String getBirthMonth() {
//		return birthMonth;
//	}
//
//	public void setBirthMonth(String birthMonth) {
//		this.birthMonth = birthMonth;
//	}
//
//	public String getBirthDay() {
//		return birthDay;
//	}
//
//	public void setBirthDay(String birthDay) {
//		this.birthDay = birthDay;
//	}
//
//	public String getIdCardNo() {
//		return idCardNo;
//	}
//
//	public void setIdCardNo(String idCardNo) {
//		this.idCardNo = idCardNo;
//	}
//
//	public String getSignOrg() {
//		return signOrg;
//	}
//
//	public void setSignOrg(String signOrg) {
//		this.signOrg = signOrg;
//	}
//
//	public String getValidDate() {
//		return validDate;
//	}
//
//	public void setValidDate(String validDate) {
//		this.validDate = validDate;
//	}
//
//	public String getIndustry() {
//		return industry;
//	}
//
//	public void setIndustry(String industry) {
//		this.industry = industry;
//	}
//
//	public String getJobYears() {
//		return jobYears;
//	}
//
//	public void setJobYears(String jobYears) {
//		this.jobYears = jobYears;
//	}
//
//	public String getLivingTime() {
//		return livingTime;
//	}
//
//	public void setLivingTime(String livingTime) {
//		this.livingTime = livingTime;
//	}
//
//	public String getLoanDeadLine() {
//		return loanDeadLine;
//	}
//
//	public void setLoanDeadLine(String loanDeadLine) {
//		this.loanDeadLine = loanDeadLine;
//	}
//
//	public String getLoanMoney() {
//		return loanMoney;
//	}
//
//	public void setLoanMoney(String loanMoney) {
//		this.loanMoney = loanMoney;
//	}
//
//	public String getLoanPurpose() {
//		return loanPurpose;
//	}
//
//	public void setLoanPurpose(String loanPurpose) {
//		this.loanPurpose = loanPurpose;
//	}
//
//	public String getMarriageInfo() {
//		return marriageInfo;
//	}
//
//	public void setMarriageInfo(String marriageInfo) {
//		this.marriageInfo = marriageInfo;
//	}
//
//	public String getMobilePhone() {
//		return mobilePhone;
//	}
//
//	public void setMobilePhone(String mobilePhone) {
//		this.mobilePhone = mobilePhone;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getNowCompanyAddress() {
//		return nowCompanyAddress;
//	}
//
//	public void setNowCompanyAddress(String nowCompanyAddress) {
//		this.nowCompanyAddress = nowCompanyAddress;
//	}
//
//	public OperatorInfoVo getOperatorInfo() {
//		return operatorInfo;
//	}
//
//	public void setOperatorInfo(OperatorInfoVo operatorInfo) {
//		this.operatorInfo = operatorInfo;
//	}
//
//	public String getQq() {
//		return qq;
//	}
//
//	public void setQq(String qq) {
//		this.qq = qq;
//	}
//
//	public String getSalary() {
//		return salary;
//	}
//
//	public void setSalary(String salary) {
//		this.salary = salary;
//	}
//
//	public String getValid_date_end() {
//		return valid_date_end;
//	}
//
//	public void setValid_date_end(String valid_date_end) {
//		this.valid_date_end = valid_date_end;
//	}
//
//	public String getValid_date_start() {
//		return valid_date_start;
//	}
//
//	public void setValid_date_start(String valid_date_start) {
//		this.valid_date_start = valid_date_start;
//	}
//
//	public String getWeChat() {
//		return weChat;
//	}
//
//	public void setWeChat(String weChat) {
//		this.weChat = weChat;
//	}
//}
