package com.waterelephant.third.entity.request;

/**
 * 统一对外接口 - 订单推送 - 基本信息（code0091）
 * 
 * 
 * Module:
 * 
 * BasicInfo.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class BasicInfo {
	private String thirdOrderNo; // 机构订单流水号

	private String name; // 借款人姓名
	private String registerPhone; // 注册电话
	private String idCard; // 身份证

	private int isInstalment; // 是否分期产品（1单期产品、 2 分期产品）
	private int loanAmount; // 预借款金额(单位分)
	private int period; // 预借款周期(单位:单期表示天，分期表示期数)
	private String applyDate; // 借款日期(yyyy-MM-dd HH:mm:ss)
	private String desc; // 借款用途

	private String bankCardNum; // 银行卡号
	private String bankName; // 银行
	private String bankPhone; // 银行卡预留电话
	private String bankProvince; // 开户行省编号
	private String bankCity; // 开户行市编号

	private int sesameScore; // 芝麻分

	private String houseProvince; // 所在省
	private String houseCity; // 所在市
	private String houseDistrict; // 所在区
	private String houseAddress; // 所在地址
	private int marriage; // 婚姻状况
	private String email; // 电子邮箱
	private int haveCar; // 是否有车
	private int haveHouse; // 是否有房
	private String firstName; // 联系人姓名（1）
	private String firstPhone; // 联系人电话（1）
	private String secondName; // 联系人姓名（2）
	private String secondPhone; // 联系人电话（2）

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThirdOrderNo() {
		return thirdOrderNo;
	}

	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}

	public int getIsInstalment() {
		return isInstalment;
	}

	public void setIsInstalment(int isInstalment) {
		this.isInstalment = isInstalment;
	}

	public String getRegisterPhone() {
		return registerPhone;
	}

	public void setRegisterPhone(String registerPhone) {
		this.registerPhone = registerPhone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public int getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(int loanAmount) {
		this.loanAmount = loanAmount;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getSesameScore() {
		return sesameScore;
	}

	public void setSesameScore(int sesameScore) {
		this.sesameScore = sesameScore;
	}

	public String getHouseProvince() {
		return houseProvince;
	}

	public void setHouseProvince(String houseProvince) {
		this.houseProvince = houseProvince;
	}

	public String getHouseCity() {
		return houseCity;
	}

	public void setHouseCity(String houseCity) {
		this.houseCity = houseCity;
	}

	public String getHouseDistrict() {
		return houseDistrict;
	}

	public void setHouseDistrict(String houseDistrict) {
		this.houseDistrict = houseDistrict;
	}

	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	public int getMarriage() {
		return marriage;
	}

	public void setMarriage(int marriage) {
		this.marriage = marriage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstPhone() {
		return firstPhone;
	}

	public void setFirstPhone(String firstPhone) {
		this.firstPhone = firstPhone;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getSecondPhone() {
		return secondPhone;
	}

	public void setSecondPhone(String secondPhone) {
		this.secondPhone = secondPhone;
	}

	public int getHaveCar() {
		return haveCar;
	}

	public void setHaveCar(int haveCar) {
		this.haveCar = haveCar;
	}

	public int getHaveHouse() {
		return haveHouse;
	}

	public void setHaveHouse(int haveHouse) {
		this.haveHouse = haveHouse;
	}

}
