package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_ocr_idcard_record")
public class BwOcrIdcardRecord implements Serializable {

	private static final long serialVersionUID = -8142284103684391210L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;//主键ID
	private String productNo;//产品编号(给请求方分配的产品编号)
	private String ocrSource;//OCR识别来源（1:face++ 2:商汤）
	private String side;//front：正面 back：反面
	private String idcardNumber;//身份证号
	private String name;//姓名
	private String gender;//性别
	private String race;//名族
	private String address;//地址
	private String year;//出生年
	private String month;//出生月
	private String day;//出生日
	private String issuedBy;//签发机关
	private String validDate;//有效期
	private String ocrResult;//OCR识别结果json
	private Date createTime;//创建时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getOcrSource() {
		return ocrSource;
	}
	public void setOcrSource(String ocrSource) {
		this.ocrSource = ocrSource;
	}
	public String getIdcardNumber() {
		return idcardNumber;
	}
	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
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
	public String getOcrResult() {
		return ocrResult;
	}
	public void setOcrResult(String ocrResult) {
		this.ocrResult = ocrResult;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}
}
