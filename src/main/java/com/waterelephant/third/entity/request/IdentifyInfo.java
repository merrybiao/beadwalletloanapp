package com.waterelephant.third.entity.request;

/**
 * 统一对外接口 - 订单推送 - 身份证信息（code0091）
 * 
 * 
 * Module:
 * 
 * IdentifyInfo.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class IdentifyInfo {
	private String idCard; // 身份证号
	private String name; // 姓名
	private String frontFile; // 正面
	private String backFile; // 反面
	private String natureFile; // 生活照或手持照
	private String nation;// 民族
	private String address; // 地址
	private String issuedBy; // 发证机构
	private String validDate; // 有效期

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFrontFile() {
		return frontFile;
	}

	public void setFrontFile(String frontFile) {
		this.frontFile = frontFile;
	}

	public String getBackFile() {
		return backFile;
	}

	public void setBackFile(String backFile) {
		this.backFile = backFile;
	}

	public String getNatureFile() {
		return natureFile;
	}

	public void setNatureFile(String natureFile) {
		this.natureFile = natureFile;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

}
