package com.waterelephant.drainage.entity.rongShu;

/**
 * 榕树 - 5.3 进件推送 - 入参（实名认证信息）（code0087503）
 * 
 * 
 * Module:
 * 
 * IdInfo.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <榕树 - 5.3 进件推送 - 入参（实名认证信息>
 */
public class IdInfo {
	private String address; // 身份证地址

	private String backFile; // 身份证反面照片URL

	private String cid; // 身份证号

	private String frontFile; // 身份证正面照片URL

	private int gender; // 性别

	private String issuedBy; // 身份证发证处

	private String name; // 姓名

	private String natureFile; // 生活照URL

	private String validDate; // 身份证有效期

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setBackFile(String backFile) {
		this.backFile = backFile;
	}

	public String getBackFile() {
		return this.backFile;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCid() {
		return this.cid;
	}

	public void setFrontFile(String frontFile) {
		this.frontFile = frontFile;
	}

	public String getFrontFile() {
		return this.frontFile;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getGender() {
		return this.gender;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getIssuedBy() {
		return this.issuedBy;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setNatureFile(String natureFile) {
		this.natureFile = natureFile;
	}

	public String getNatureFile() {
		return this.natureFile;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getValidDate() {
		return this.validDate;
	}
}
