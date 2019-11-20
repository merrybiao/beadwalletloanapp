//package com.waterelephant.sxyDrainage.entity.beijingDrainage;
//
///**
// * 统一对外接口 - 订单推送 - 基本信息（code0091）
// * 
// * 
// * Module:
// * 
// * BasicInfo.java
// * 
// * @author liuDaodao
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class SxyBasicInfo {
//	private String thirdOrderNo; // 机构订单流水号
//
//	private String name; // 借款人姓名
//	private String phone; // 电话
//	private String idCard; // 身份证
//
//	private String password;//
//
//	private int loanAmount; // 预借款金额(单位分)
//	private String applyDate; // 借款日期(yyyy-MM-dd HH:mm:ss)
//	private String desc; // 借款用途
//
//
//	private int sesameScore; // 芝麻分
//
//	private String houseProvince; // 所在省
//	private String houseCity; // 所在市
//	private String houseDistrict; // 所在区
//	private String houseAddress; // 所在地址
//	private int marriage; // 婚姻状况
//	private String email; // 电子邮箱
//	private int haveCar; // 是否有车
//	private int haveHouse; // 是否有房
//	private String firstName; // 联系人姓名（1）
//	private String firstPhone; // 联系人电话（1）
//	private String secondName; // 联系人姓名（2）
//	private String secondPhone; // 联系人电话（2）
//
//	private String colleagueName;
//	private String colleaguePhone;
//	private String friend1Name;
//	private String friend1Phone;
//	private String friend2Name;
//	private String friend2Phone;
//	private String qqchat;
//	private String wechat;
//
//	public String getColleagueName() {
//		return colleagueName;
//	}
//
//	public void setColleagueName(String colleagueName) {
//		this.colleagueName = colleagueName;
//	}
//
//	public String getColleaguePhone() {
//		return colleaguePhone;
//	}
//
//	public void setColleaguePhone(String colleaguePhone) {
//		this.colleaguePhone = colleaguePhone;
//	}
//
//	public String getFriend1Name() {
//		return friend1Name;
//	}
//
//	public void setFriend1Name(String friend1Name) {
//		this.friend1Name = friend1Name;
//	}
//
//	public String getFriend1Phone() {
//		return friend1Phone;
//	}
//
//	public void setFriend1Phone(String friend1Phone) {
//		this.friend1Phone = friend1Phone;
//	}
//
//	public String getFriend2Name() {
//		return friend2Name;
//	}
//
//	public void setFriend2Name(String friend2Name) {
//		this.friend2Name = friend2Name;
//	}
//
//	public String getFriend2Phone() {
//		return friend2Phone;
//	}
//
//	public void setFriend2Phone(String friend2Phone) {
//		this.friend2Phone = friend2Phone;
//	}
//
//	public String getQqchat() {
//		return qqchat;
//	}
//
//	public void setQqchat(String qqchat) {
//		this.qqchat = qqchat;
//	}
//
//	public String getWechat() {
//		return wechat;
//	}
//
//	public void setWechat(String wechat) {
//		this.wechat = wechat;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public String getThirdOrderNo() {
//		return thirdOrderNo;
//	}
//	public void setThirdOrderNo(String thirdOrderNo) {
//		this.thirdOrderNo = thirdOrderNo;
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
//	public String getPhone() {
//		return phone;
//	}
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//	public String getIdCard() {
//		return idCard;
//	}
//	public void setIdCard(String idCard) {
//		this.idCard = idCard;
//	}
//	public int getLoanAmount() {
//		return loanAmount;
//	}
//	public void setLoanAmount(int loanAmount) {
//		this.loanAmount = loanAmount;
//	}
//	public String getApplyDate() {
//		return applyDate;
//	}
//	public void setApplyDate(String applyDate) {
//		this.applyDate = applyDate;
//	}
//	public String getDesc() {
//		return desc;
//	}
//	public void setDesc(String desc) {
//		this.desc = desc;
//	}
//	public int getSesameScore() {
//		return sesameScore;
//	}
//	public void setSesameScore(int sesameScore) {
//		this.sesameScore = sesameScore;
//	}
//	public String getHouseProvince() {
//		return houseProvince;
//	}
//	public void setHouseProvince(String houseProvince) {
//		this.houseProvince = houseProvince;
//	}
//	public String getHouseCity() {
//		return houseCity;
//	}
//	public void setHouseCity(String houseCity) {
//		this.houseCity = houseCity;
//	}
//	public String getHouseDistrict() {
//		return houseDistrict;
//	}
//	public void setHouseDistrict(String houseDistrict) {
//		this.houseDistrict = houseDistrict;
//	}
//	public String getHouseAddress() {
//		return houseAddress;
//	}
//	public void setHouseAddress(String houseAddress) {
//		this.houseAddress = houseAddress;
//	}
//	public int getMarriage() {
//		return marriage;
//	}
//	public void setMarriage(int marriage) {
//		this.marriage = marriage;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public int getHaveCar() {
//		return haveCar;
//	}
//
//	public void setHaveCar(int haveCar) {
//		this.haveCar = haveCar;
//	}
//
//	public int getHaveHouse() {
//		return haveHouse;
//	}
//
//	public void setHaveHouse(int haveHouse) {
//		this.haveHouse = haveHouse;
//	}
//	public String getFirstName() {
//		return firstName;
//	}
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//	public String getFirstPhone() {
//		return firstPhone;
//	}
//	public void setFirstPhone(String firstPhone) {
//		this.firstPhone = firstPhone;
//	}
//	public String getSecondName() {
//		return secondName;
//	}
//	public void setSecondName(String secondName) {
//		this.secondName = secondName;
//	}
//	public String getSecondPhone() {
//		return secondPhone;
//	}
//	public void setSecondPhone(String secondPhone) {
//		this.secondPhone = secondPhone;
//	}
//}
