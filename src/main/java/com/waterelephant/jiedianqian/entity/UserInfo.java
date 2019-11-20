package com.waterelephant.jiedianqian.entity;

/**
 * 
 * 
 * Module:
 * 
 * UserInfo.java
 * 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <用户基本信息>
 */
public class UserInfo {
	private String id_positive; // 身份证照片URL
	private String id_negative; // 身份证反面URL
	private String hand_id_photo; // 手持身份证照片
	private String face; // 人脸照片url
	private String company_name; // 工作单位
	private String company_address; // 公司详细地址
	private String industry; // 公司所属行业
	private String living_address; // 居住地址
	private String phone; // 手机号
	private String id_card; // 身份证号码
	private String name; // 姓名
	private String marry; // 婚姻状况（已婚、未婚）
	private String educate; // 学历 (大专以下；大专；本科；研究生及以上)
	private String company_work_year; // 当前公司工作年限
	private String id_card_address; // 户籍地址，身份证上地址
	private String hiredate; // 当前公司入职日期，格式“2017年7月12日”
	private String role; // 身份信息，工薪族、企业主、学生族、自由职业
	private String salary_range; // 月收入范围
	private String loan_usage; // 贷款用途
	private String city; // 所在城市
	private String company_city; // 公司所在城市

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getId_card_address() {
		return id_card_address;
	}

	public void setId_card_address(String id_card_address) {
		this.id_card_address = id_card_address;
	}

	public String getHiredate() {
		return hiredate;
	}

	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSalary_range() {
		return salary_range;
	}

	public void setSalary_range(String salary_range) {
		this.salary_range = salary_range;
	}

	public String getLoan_usage() {
		return loan_usage;
	}

	public void setLoan_usage(String loan_usage) {
		this.loan_usage = loan_usage;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompany_city() {
		return company_city;
	}

	public void setCompany_city(String company_city) {
		this.company_city = company_city;
	}

	public String getCompany_work_year() {
		return company_work_year;
	}

	public void setCompany_work_year(String company_work_year) {
		this.company_work_year = company_work_year;
	}

	public String getId_positive() {
		return id_positive;
	}

	public void setId_positive(String id_positive) {
		this.id_positive = id_positive;
	}

	public String getId_negative() {
		return id_negative;
	}

	public void setId_negative(String id_negative) {
		this.id_negative = id_negative;
	}

	public String getHand_id_photo() {
		return hand_id_photo;
	}

	public void setHand_id_photo(String hand_id_photo) {
		this.hand_id_photo = hand_id_photo;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_address() {
		return company_address;
	}

	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getLiving_address() {
		return living_address;
	}

	public void setLiving_address(String living_address) {
		this.living_address = living_address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMarry() {
		return marry;
	}

	public void setMarry(String marry) {
		this.marry = marry;
	}

	public String getEducate() {
		return educate;
	}

	public void setEducate(String educate) {
		this.educate = educate;
	}

}
