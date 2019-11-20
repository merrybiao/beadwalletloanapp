/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.entity.qihu360;

import java.util.List;

/**
 * 
 * 
 * Module: 推送用户借款补充信息
 * 
 * PushUserLoanAddInfo.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class PushUserLoanAddInfo {
	private String order_no;// 奇虎360订单编号
	private List<String> ID_Positive; // 身份证正面照（可供下载url）数组最后一张为最新
	private List<String> ID_Negative; // 身份证反面照（可供下载url）数组最后一张为最新
	private List<String> photo_hand_ID; // 手持身份证照片（可供下载url）数组最后一张为最新
	private List<String> photo_assay; // 活体（可供下载url）数组最后一张为最新
	private String addr_detail; // 居住地址
	private Integer family_live_type; // 居住方式 1.自有住房，无贷款2.自有住房、有贷款3.与父母/配偶同住4.租房5.公司宿舍6.自建房 7.其他8.学生公寓
	private Integer household_type;// 户口类型 1=本地城镇,2=本地农村,3=外地城镇,4=外地农村
	private String user_email; // 常用邮箱地址
	private Integer user_marriage; // 婚姻状态 1=未婚,2=已婚无子女,3=已婚有子女,4=离异,5=丧偶,6=复婚
	private Integer loan_use;// 贷款用途 1=短期周转,2=购物,3=装修,4=买房,5=买车,6=旅游,7=医疗
	private Integer credite_status;// 信用状况,1=无信用卡或贷款,2=信用良好，无逾期,3=1 年内逾期少于 3次且少于 90 天,4=1 年内逾期超过 3次或超过 90 天
	private Integer asset_auto_type; // 车辆情况 1-无车,2-本人名下有车，无贷款,3-本人名下有车，有按揭贷款,4- 本人名下有车，但已被抵押,5-其它
	private Integer contact1A_relationship; // 亲属联系人关系 1：父母,2：配偶,3：兄弟姐妹,4：子女
	private String contact1A_name; // 亲属联系人姓名
	private String contact1A_number; // 亲属联系人电话
	private Integer emergency_contact_personA_relationship; // 紧急联系人关系 1=父母,2=配偶,3=兄弟姐妹,4=子女,5=同事,6=同学,7=朋友
	private String emergency_contact_personA_name; // 紧急联系人姓名
	private String emergency_contact_personA_phone; // 紧急联系人电话
	private String company_name; // 公司名称
	private String company_addr_detail; // 公司地址 省市区详细地址
	private String company_number; // 公司固话 包含区号、座机号、分机号传参会以“-”做区分
	private Integer company_type;// 公司类型 1=政府事业单位2=大型垄断国企3=世界 500 强企业4=上市企业5=普通企业
	private Integer position;// 公司职位 1=普通员工/干部 2=中基层管理人员/科级3=高层管理人员/处 级4=公司法人/股东5=其他/非合同工
	private String hiredate;// 入职时间
	private Integer income_type;// 收入类型 1=打卡工薪 2=现金工薪3=经营人士
	private Integer industry_type;// 行业类型1=批发/零售业 2=制造业3=金融业/保险/证券 4=商业服务业/娱乐/艺术/体育 5=计算机/互联网6=通讯/电子
	private String amount_of_staff;// 公司人数
	private ExtraInfoContacts contacts;// 设备基本信息（联系人、APP 列表等）
	private DeviceInfo device_info_all;// 其他设备信息
	private String is_simulator;// 是否模拟器 1=设备是模拟器0=设备非模拟器

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public List<String> getID_Positive() {
		return ID_Positive;
	}

	public void setID_Positive(List<String> iD_Positive) {
		ID_Positive = iD_Positive;
	}

	public List<String> getID_Negative() {
		return ID_Negative;
	}

	public void setID_Negative(List<String> iD_Negative) {
		ID_Negative = iD_Negative;
	}

	public List<String> getPhoto_hand_ID() {
		return photo_hand_ID;
	}

	public void setPhoto_hand_ID(List<String> photo_hand_ID) {
		this.photo_hand_ID = photo_hand_ID;
	}

	public List<String> getPhoto_assay() {
		return photo_assay;
	}

	public void setPhoto_assay(List<String> photo_assay) {
		this.photo_assay = photo_assay;
	}

	public String getAddr_detail() {
		return addr_detail;
	}

	public void setAddr_detail(String addr_detail) {
		this.addr_detail = addr_detail;
	}

	public Integer getFamily_live_type() {
		return family_live_type;
	}

	public void setFamily_live_type(Integer family_live_type) {
		this.family_live_type = family_live_type;
	}

	public Integer getHousehold_type() {
		return household_type;
	}

	public void setHousehold_type(Integer household_type) {
		this.household_type = household_type;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public Integer getUser_marriage() {
		return user_marriage;
	}

	public void setUser_marriage(Integer user_marriage) {
		this.user_marriage = user_marriage;
	}

	public Integer getLoan_use() {
		return loan_use;
	}

	public void setLoan_use(Integer loan_use) {
		this.loan_use = loan_use;
	}

	public Integer getCredite_status() {
		return credite_status;
	}

	public void setCredite_status(Integer credite_status) {
		this.credite_status = credite_status;
	}

	public Integer getAsset_auto_type() {
		return asset_auto_type;
	}

	public void setAsset_auto_type(Integer asset_auto_type) {
		this.asset_auto_type = asset_auto_type;
	}

	public Integer getContact1A_relationship() {
		return contact1A_relationship;
	}

	public void setContact1A_relationship(Integer contact1a_relationship) {
		contact1A_relationship = contact1a_relationship;
	}

	public String getContact1A_name() {
		return contact1A_name;
	}

	public void setContact1A_name(String contact1a_name) {
		contact1A_name = contact1a_name;
	}

	public String getContact1A_number() {
		return contact1A_number;
	}

	public void setContact1A_number(String contact1a_number) {
		contact1A_number = contact1a_number;
	}

	public Integer getEmergency_contact_personA_relationship() {
		return emergency_contact_personA_relationship;
	}

	public void setEmergency_contact_personA_relationship(Integer emergency_contact_personA_relationship) {
		this.emergency_contact_personA_relationship = emergency_contact_personA_relationship;
	}

	public String getEmergency_contact_personA_name() {
		return emergency_contact_personA_name;
	}

	public void setEmergency_contact_personA_name(String emergency_contact_personA_name) {
		this.emergency_contact_personA_name = emergency_contact_personA_name;
	}

	public String getEmergency_contact_personA_phone() {
		return emergency_contact_personA_phone;
	}

	public void setEmergency_contact_personA_phone(String emergency_contact_personA_phone) {
		this.emergency_contact_personA_phone = emergency_contact_personA_phone;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_addr_detail() {
		return company_addr_detail;
	}

	public void setCompany_addr_detail(String company_addr_detail) {
		this.company_addr_detail = company_addr_detail;
	}

	public String getCompany_number() {
		return company_number;
	}

	public void setCompany_number(String company_number) {
		this.company_number = company_number;
	}

	public Integer getCompany_type() {
		return company_type;
	}

	public void setCompany_type(Integer company_type) {
		this.company_type = company_type;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getHiredate() {
		return hiredate;
	}

	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}

	public Integer getIncome_type() {
		return income_type;
	}

	public void setIncome_type(Integer income_type) {
		this.income_type = income_type;
	}

	public Integer getIndustry_type() {
		return industry_type;
	}

	public void setIndustry_type(Integer industry_type) {
		this.industry_type = industry_type;
	}

	public String getAmount_of_staff() {
		return amount_of_staff;
	}

	public void setAmount_of_staff(String amount_of_staff) {
		this.amount_of_staff = amount_of_staff;
	}

	public ExtraInfoContacts getContacts() {
		return contacts;
	}

	public void setContacts(ExtraInfoContacts contacts) {
		this.contacts = contacts;
	}

	public DeviceInfo getDevice_info_all() {
		return device_info_all;
	}

	public void setDevice_info_all(DeviceInfo device_info_all) {
		this.device_info_all = device_info_all;
	}

	public String getIs_simulator() {
		return is_simulator;
	}

	public void setIs_simulator(String is_simulator) {
		this.is_simulator = is_simulator;
	}

}
