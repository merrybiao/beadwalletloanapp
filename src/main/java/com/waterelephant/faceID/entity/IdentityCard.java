package com.waterelephant.faceID.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 身份证信息表
 * @author dengyan
 *
 */
@Table(name = "bw_identity_card")
public class IdentityCard {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id; //主键id
	private int borrowerId; //用户id
	private String address; //地址
	private String birthday; //生日
	private String gender; //性别
	private String idCardNumber; //身份证号码
	private String name;  //姓名
	private String issuedBy;  //签发机关
	private String validDate;  //身份证有效期
	private Date createTime;  //创建时间
	private Date updateTime;  //更新时间
	private String race;  //民族
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBorrowerId() {
		return borrowerId;
	}
	public void setBorrowerId(int borrowerId) {
		this.borrowerId = borrowerId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	
	@Override
	public String toString() {
		return "IdentityCard [id=" + id + ", borrowerId=" + borrowerId
				+ ", address=" + address + ", birthday=" + birthday
				+ ", gender=" + gender + ", idCardNumber=" + idCardNumber
				+ ", name=" + name + ", issuedBy=" + issuedBy + ", validDate="
				+ validDate + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", race=" + race + "]";
	}
}
