///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.haodai;
//
///**
// * HdApplyDetail.java
// *
// * @author: 王亚楠
// * @since: JDK 1.8
// * @version: 1.0
// * @date: 2018年5月7日
// * @Description: <申请人补充信息说明>
// * 
// */
//public class HdApplyDetail {
//	private Integer marriage;// 婚姻状况
//	private Integer profession;// 职业类型
//	private String position_name;// 岗位名称
//	private Integer type;// 身份类型
//	private Integer education;// 最高学历
//	private Integer industry;// 公司所在行业
//	private Integer has_loan;// 有无贷款成功记录
//	private String company_name;// 学校名称,公司名称
//	private String dorm_address;// 宿舍地址
//	private String graduation_time;// 毕业年月
//	private Long to_company_time;// 入学时间，公司入职时间
//	private String company_address;// 单位地址
//	private Integer company_type;// 公司类型
//	private Long first_work_time;// 首次参加工作日期
//	private Integer grade;// 在职岗位级别/学制
//	private Integer salary_bank_public;// 工作收入
//	private Integer salary_bank_other;// 其他收入
//	private Integer house_loan;// 有无房贷
//	private Integer child_status;// 子女
//	private Integer register_type;// 户口类型
//	private String sos_user;// 紧急联系人信息
//	private String iden_opposite_side;// 身份证背面url地址
//	private String iden_correct_side;// 身份证正面url地址
//	private String iden_scene;// 手持身份证照
//	private Long iden_outdate;// 身份证有效期
//	private String qq;// qq
//	private String email;// 电子邮件
//	private Integer is_security;// 社保
//	private Integer otherloan;// 其他贷款
//	private String department;// 当前就职部门全称
//	private String company_phone;// 单位电话
//	private Integer work_experience;// 工作年限
//	private String house_photo;// 住房证明照片
//	private String work_photo;// 工作证明照片
//	private Integer phone_time;// 手机在网时长
//	private String school_name;// 学校名称
//	private Integer to_school_time;// 入学时间
//	private String source_income;// 收入来源
//	private String extra;// 补充信息
//
//	public Integer getMarriage() {
//		return marriage;
//	}
//
//	public void setMarriage(Integer marriage) {
//		this.marriage = marriage;
//	}
//
//	public Integer getProfession() {
//		return profession;
//	}
//
//	public void setProfession(Integer profession) {
//		this.profession = profession;
//	}
//
//	public String getPosition_name() {
//		return position_name;
//	}
//
//	public void setPosition_name(String position_name) {
//		this.position_name = position_name;
//	}
//
//	public Integer getType() {
//		return type;
//	}
//
//	public void setType(Integer type) {
//		this.type = type;
//	}
//
//	public Integer getEducation() {
//		return education;
//	}
//
//	public void setEducation(Integer education) {
//		this.education = education;
//	}
//
//	public Integer getIndustry() {
//		return industry;
//	}
//
//	public void setIndustry(Integer industry) {
//		this.industry = industry;
//	}
//
//	public Integer getHas_loan() {
//		return has_loan;
//	}
//
//	public void setHas_loan(Integer has_loan) {
//		this.has_loan = has_loan;
//	}
//
//	public String getCompany_name() {
//		return company_name;
//	}
//
//	public void setCompany_name(String company_name) {
//		this.company_name = company_name;
//	}
//
//	public String getDorm_address() {
//		return dorm_address;
//	}
//
//	public void setDorm_address(String dorm_address) {
//		this.dorm_address = dorm_address;
//	}
//
//	public String getGraduation_time() {
//		return graduation_time;
//	}
//
//	public void setGraduation_time(String graduation_time) {
//		this.graduation_time = graduation_time;
//	}
//
//	public Long getTo_company_time() {
//		return to_company_time;
//	}
//
//	public void setTo_company_time(Long to_company_time) {
//		this.to_company_time = to_company_time;
//	}
//
//	public String getCompany_address() {
//		return company_address;
//	}
//
//	public void setCompany_address(String company_address) {
//		this.company_address = company_address;
//	}
//
//	public Integer getCompany_type() {
//		return company_type;
//	}
//
//	public void setCompany_type(Integer company_type) {
//		this.company_type = company_type;
//	}
//
//	public Long getFirst_work_time() {
//		return first_work_time;
//	}
//
//	public void setFirst_work_time(Long first_work_time) {
//		this.first_work_time = first_work_time;
//	}
//
//	public Integer getGrade() {
//		return grade;
//	}
//
//	public void setGrade(Integer grade) {
//		this.grade = grade;
//	}
//
//	public Integer getSalary_bank_public() {
//		return salary_bank_public;
//	}
//
//	public void setSalary_bank_public(Integer salary_bank_public) {
//		this.salary_bank_public = salary_bank_public;
//	}
//
//	public Integer getSalary_bank_other() {
//		return salary_bank_other;
//	}
//
//	public void setSalary_bank_other(Integer salary_bank_other) {
//		this.salary_bank_other = salary_bank_other;
//	}
//
//	public Integer getHouse_loan() {
//		return house_loan;
//	}
//
//	public void setHouse_loan(Integer house_loan) {
//		this.house_loan = house_loan;
//	}
//
//	public Integer getChild_status() {
//		return child_status;
//	}
//
//	public void setChild_status(Integer child_status) {
//		this.child_status = child_status;
//	}
//
//	public Integer getRegister_type() {
//		return register_type;
//	}
//
//	public void setRegister_type(Integer register_type) {
//		this.register_type = register_type;
//	}
//
//	public String getSos_user() {
//		return sos_user;
//	}
//
//	public void setSos_user(String sos_user) {
//		this.sos_user = sos_user;
//	}
//
//	public String getIden_opposite_side() {
//		return iden_opposite_side;
//	}
//
//	public void setIden_opposite_side(String iden_opposite_side) {
//		this.iden_opposite_side = iden_opposite_side;
//	}
//
//	public String getIden_correct_side() {
//		return iden_correct_side;
//	}
//
//	public void setIden_correct_side(String iden_correct_side) {
//		this.iden_correct_side = iden_correct_side;
//	}
//
//	public String getIden_scene() {
//		return iden_scene;
//	}
//
//	public void setIden_scene(String iden_scene) {
//		this.iden_scene = iden_scene;
//	}
//
//	public Long getIden_outdate() {
//		return iden_outdate;
//	}
//
//	public void setIden_outdate(Long iden_outdate) {
//		this.iden_outdate = iden_outdate;
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
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public Integer getIs_security() {
//		return is_security;
//	}
//
//	public void setIs_security(Integer is_security) {
//		this.is_security = is_security;
//	}
//
//	public Integer getOtherloan() {
//		return otherloan;
//	}
//
//	public void setOtherloan(Integer otherloan) {
//		this.otherloan = otherloan;
//	}
//
//	public String getDepartment() {
//		return department;
//	}
//
//	public void setDepartment(String department) {
//		this.department = department;
//	}
//
//	public String getCompany_phone() {
//		return company_phone;
//	}
//
//	public void setCompany_phone(String company_phone) {
//		this.company_phone = company_phone;
//	}
//
//	public Integer getWork_experience() {
//		return work_experience;
//	}
//
//	public void setWork_experience(Integer work_experience) {
//		this.work_experience = work_experience;
//	}
//
//	public String getHouse_photo() {
//		return house_photo;
//	}
//
//	public void setHouse_photo(String house_photo) {
//		this.house_photo = house_photo;
//	}
//
//	public String getWork_photo() {
//		return work_photo;
//	}
//
//	public void setWork_photo(String work_photo) {
//		this.work_photo = work_photo;
//	}
//
//	public Integer getPhone_time() {
//		return phone_time;
//	}
//
//	public void setPhone_time(Integer phone_time) {
//		this.phone_time = phone_time;
//	}
//
//	public String getSchool_name() {
//		return school_name;
//	}
//
//	public void setSchool_name(String school_name) {
//		this.school_name = school_name;
//	}
//
//	public Integer getTo_school_time() {
//		return to_school_time;
//	}
//
//	public void setTo_school_time(Integer to_school_time) {
//		this.to_school_time = to_school_time;
//	}
//
//	public String getSource_income() {
//		return source_income;
//	}
//
//	public void setSource_income(String source_income) {
//		this.source_income = source_income;
//	}
//
//	public String getExtra() {
//		return extra;
//	}
//
//	public void setExtra(String extra) {
//		this.extra = extra;
//	}
//
//}
