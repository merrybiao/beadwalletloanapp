///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.kaNiu;
//
//import java.util.List;
//
//public class KNOperatorBasicInfo {
//
//	private String phone;
//	private String business;
//	private String crawlName;
//	private String crawlIdCardNo;
//	private String enterName;
//	private String enterIdCardNo;
//	private String age;
//	private String sex;
//	private String origin;
//	private String province;
//	private String place;
//	private String linkAddr;
//	private String isRealName;
//	private Double onlineTime;
//	private Double totalContactTime;
//	private Double threeMonthContactTime;
//	private Double threaMonthContactCount;
//	private String isCheckName;
//	private String isIdCardNo;
//	private String isCallFriendOne;
//	private String isCallFriendTwo;
//	private String isCallFriendThree;
//	private String friendProvince;
//	private String provincePercent;
//	private String concatMacao;
//	private Integer contact110;
//	private Integer contact120;
//	private Integer isBlacklist;
//	private Integer isPhoneOneBlackList;
//	private Integer isPhoneTwoBlackList;
//	private Integer isPhoneThreeBlackList;
//	private Integer mutualContactCount;
//	private String stateOne;
//	private String stateTwo;
//	private String stateThree;
//	private Double aveInCall;
//	private Double aveOutCall;
//	private Double aveInCallCount;
//	private Double aveOutCallCount;
//	private List<PhoneCountPojos> phoneCountPojos;
//	private List<ProvincePojos> provincePojos;
//	private List<BillResults> billResults;
//	private List<CreditCalcuteResults> creditCalcuteResults;
//	private List<BankCalcuteResults> bankCalcuteResults;
//	private List<FriendResultsVo> FriendResults;
//
//	public String getPhone() {
//		return phone;
//	}
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//	public String getBusiness() {
//		return business;
//	}
//	public void setBusiness(String business) {
//		this.business = business;
//	}
//	public String getCrawlName() {
//		return crawlName;
//	}
//	public void setCrawlName(String crawlName) {
//		this.crawlName = crawlName;
//	}
//	public String getCrawlIdCardNo() {
//		return crawlIdCardNo;
//	}
//	public void setCrawlIdCardNo(String crawlIdCardNo) {
//		this.crawlIdCardNo = crawlIdCardNo;
//	}
//	public String getEnterName() {
//		return enterName;
//	}
//	public void setEnterName(String enterName) {
//		this.enterName = enterName;
//	}
//	public String getEnterIdCardNo() {
//		return enterIdCardNo;
//	}
//	public void setEnterIdCardNo(String enterIdCardNo) {
//		this.enterIdCardNo = enterIdCardNo;
//	}
//	public String getAge() {
//		return age;
//	}
//	public void setAge(String age) {
//		this.age = age;
//	}
//	public String getSex() {
//		return sex;
//	}
//	public void setSex(String sex) {
//		this.sex = sex;
//	}
//	public String getOrigin() {
//		return origin;
//	}
//	public void setOrigin(String origin) {
//		this.origin = origin;
//	}
//	public String getProvince() {
//		return province;
//	}
//	public void setProvince(String province) {
//		this.province = province;
//	}
//	public String getPlace() {
//		return place;
//	}
//	public void setPlace(String place) {
//		this.place = place;
//	}
//	public String getLinkAddr() {
//		return linkAddr;
//	}
//	public void setLinkAddr(String linkAddr) {
//		this.linkAddr = linkAddr;
//	}
//	public String getIsRealName() {
//		return isRealName;
//	}
//	public void setIsRealName(String isRealName) {
//		this.isRealName = isRealName;
//	}
//	public Double getOnlineTime() {
//		return onlineTime;
//	}
//	public void setOnlineTime(Double onlineTime) {
//		this.onlineTime = onlineTime;
//	}
//	public Double getTotalContactTime() {
//		return totalContactTime;
//	}
//	public void setTotalContactTime(Double totalContactTime) {
//		this.totalContactTime = totalContactTime;
//	}
//	public Double getThreeMonthContactTime() {
//		return threeMonthContactTime;
//	}
//	public void setThreeMonthContactTime(Double threeMonthContactTime) {
//		this.threeMonthContactTime = threeMonthContactTime;
//	}
//	public Double getThreaMonthContactCount() {
//		return threaMonthContactCount;
//	}
//	public void setThreaMonthContactCount(Double threaMonthContactCount) {
//		this.threaMonthContactCount = threaMonthContactCount;
//	}
//	public String getIsCheckName() {
//		return isCheckName;
//	}
//	public void setIsCheckName(String isCheckName) {
//		this.isCheckName = isCheckName;
//	}
//	public String getIsIdCardNo() {
//		return isIdCardNo;
//	}
//	public void setIsIdCardNo(String isIdCardNo) {
//		this.isIdCardNo = isIdCardNo;
//	}
//	public String getIsCallFriendOne() {
//		return isCallFriendOne;
//	}
//	public void setIsCallFriendOne(String isCallFriendOne) {
//		this.isCallFriendOne = isCallFriendOne;
//	}
//	public String getIsCallFriendTwo() {
//		return isCallFriendTwo;
//	}
//	public void setIsCallFriendTwo(String isCallFriendTwo) {
//		this.isCallFriendTwo = isCallFriendTwo;
//	}
//	public String getIsCallFriendThree() {
//		return isCallFriendThree;
//	}
//	public void setIsCallFriendThree(String isCallFriendThree) {
//		this.isCallFriendThree = isCallFriendThree;
//	}
//	public String getFriendProvince() {
//		return friendProvince;
//	}
//	public void setFriendProvince(String friendProvince) {
//		this.friendProvince = friendProvince;
//	}
//	public String getProvincePercent() {
//		return provincePercent;
//	}
//	public void setProvincePercent(String provincePercent) {
//		this.provincePercent = provincePercent;
//	}
//	public String getConcatMacao() {
//		return concatMacao;
//	}
//	public void setConcatMacao(String concatMacao) {
//		this.concatMacao = concatMacao;
//	}
//
//	public Integer getContact110() {
//		return contact110;
//	}
//
//	public void setContact110(Integer contact110) {
//		this.contact110 = contact110;
//	}
//
//	public Integer getContact120() {
//		return contact120;
//	}
//
//	public void setContact120(Integer contact120) {
//		this.contact120 = contact120;
//	}
//
//	public Integer getIsBlacklist() {
//		return isBlacklist;
//	}
//
//	public void setIsBlacklist(Integer isBlacklist) {
//		this.isBlacklist = isBlacklist;
//	}
//
//	public Integer getIsPhoneOneBlackList() {
//		return isPhoneOneBlackList;
//	}
//
//	public void setIsPhoneOneBlackList(Integer isPhoneOneBlackList) {
//		this.isPhoneOneBlackList = isPhoneOneBlackList;
//	}
//
//	public Integer getIsPhoneTwoBlackList() {
//		return isPhoneTwoBlackList;
//	}
//
//	public void setIsPhoneTwoBlackList(Integer isPhoneTwoBlackList) {
//		this.isPhoneTwoBlackList = isPhoneTwoBlackList;
//	}
//
//	public Integer getIsPhoneThreeBlackList() {
//		return isPhoneThreeBlackList;
//	}
//
//	public void setIsPhoneThreeBlackList(Integer isPhoneThreeBlackList) {
//		this.isPhoneThreeBlackList = isPhoneThreeBlackList;
//	}
//
//	public Integer getMutualContactCount() {
//		return mutualContactCount;
//	}
//
//	public void setMutualContactCount(Integer mutualContactCount) {
//		this.mutualContactCount = mutualContactCount;
//	}
//	public String getStateOne() {
//		return stateOne;
//	}
//	public void setStateOne(String stateOne) {
//		this.stateOne = stateOne;
//	}
//	public String getStateTwo() {
//		return stateTwo;
//	}
//	public void setStateTwo(String stateTwo) {
//		this.stateTwo = stateTwo;
//	}
//	public String getStateThree() {
//		return stateThree;
//	}
//	public void setStateThree(String stateThree) {
//		this.stateThree = stateThree;
//	}
//	public Double getAveInCall() {
//		return aveInCall;
//	}
//	public void setAveInCall(Double aveInCall) {
//		this.aveInCall = aveInCall;
//	}
//	public Double getAveOutCall() {
//		return aveOutCall;
//	}
//	public void setAveOutCall(Double aveOutCall) {
//		this.aveOutCall = aveOutCall;
//	}
//	public Double getAveInCallCount() {
//		return aveInCallCount;
//	}
//	public void setAveInCallCount(Double aveInCallCount) {
//		this.aveInCallCount = aveInCallCount;
//	}
//	public Double getAveOutCallCount() {
//		return aveOutCallCount;
//	}
//	public void setAveOutCallCount(Double aveOutCallCount) {
//		this.aveOutCallCount = aveOutCallCount;
//	}
//	public List<PhoneCountPojos> getPhoneCountPojos() {
//		return phoneCountPojos;
//	}
//	public void setPhoneCountPojos(List<PhoneCountPojos> phoneCountPojos) {
//		this.phoneCountPojos = phoneCountPojos;
//	}
//	public List<ProvincePojos> getProvincePojos() {
//		return provincePojos;
//	}
//	public void setProvincePojos(List<ProvincePojos> provincePojos) {
//		this.provincePojos = provincePojos;
//	}
//	public List<BillResults> getBillResults() {
//		return billResults;
//	}
//	public void setBillResults(List<BillResults> billResults) {
//		this.billResults = billResults;
//	}
//	public List<CreditCalcuteResults> getCreditCalcuteResults() {
//		return creditCalcuteResults;
//	}
//	public void setCreditCalcuteResults(List<CreditCalcuteResults> creditCalcuteResults) {
//		this.creditCalcuteResults = creditCalcuteResults;
//	}
//	public List<BankCalcuteResults> getBankCalcuteResults() {
//		return bankCalcuteResults;
//	}
//	public void setBankCalcuteResults(List<BankCalcuteResults> bankCalcuteResults) {
//		this.bankCalcuteResults = bankCalcuteResults;
//	}
//
//	public List<FriendResultsVo> getFriendResults() {
//		return FriendResults;
//	}
//
//	public void setFriendResults(List<FriendResultsVo> friendResults) {
//		FriendResults = friendResults;
//	}
//
//}
