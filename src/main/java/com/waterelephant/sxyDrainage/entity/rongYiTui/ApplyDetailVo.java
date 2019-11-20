///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.rongYiTui;
//
//import java.util.List;
//
///**
// * Module: 
// * RytPushUserInfo.java 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class ApplyDetailVo {
//	private String user_mobile;// user_mobile string Y 用户手机号码
//	private String city;// city string Y 城市名
//	private Integer auto_type;// auto_type int Y 车辆情况 1：无车 2：本人名下有车，无贷款 3：本人名下有车，有按揭贷款 4：本人名下有车，但已被抵押 5：其他
//	private String education;// education int Y 教育程度 1：硕士及以上 2：本科 3：大专 4：中专/高中以下
//	private Integer job_type;// job_type int Y 职业 1：企业主 2：个体户 3：工薪族 4：学生 5：自由职业
//	private Integer working_age;// working_age int Y 工作时间 1：0-5个月 2：6-11个月 3：1-3年 4： 3-7年 5：7年以上
//	private Double corporate_flow;// corporate_flow int Y 经营流水 单位是分
//	private Double income_monthly;// income_monthly int Y 月工资收入 单位是分
//	private Integer max_repayment_monthly;// max_repayment_monthly int Y 可接受的最高月还款额 单位是分
//	private Integer operating_years;// operating_years int Y 经营年限 1：不足三个月 2：3-6个月 3：7-12个月 4：1-2年 5：3-4年 6：5年以上
//	private String id_card;// id_card string Y 本人身份证号码
//	private String user_name;// user_name string Y 真实姓名
//	private Integer social_security;// social_security int Y 现单位是否缴纳社保 1：缴纳本地社保 2：无社保
//	private String home_phone;// home_phone string Y 手机号
//	private Double average_income_monthly;// average_income_monthly int Y 月平均收入
//	private Object gps_location;// gps_location string Y GPS信息
//	private String channel;// channel string N 渠道
//	private Integer created_at;// created_at int N 注册时间 时间戳 10位
//	private String ip_address;// ip_address
//
//	// private String user_mobile;//用户手机号
//	private Integer residence_type;// 居住方式 1:自有住房，无贷款、2:自有住房、有贷款、3:与父母/配偶同住、4:租房、5:公司宿舍、6:自建房7:其他 8:学生公寓
//	private Integer urgent_person1_relationship;// 紧急联系人 1：父母 2;配偶 3:兄弟姐妹
//	private String urgent_person1_name;// 紧急联系人1姓名
//	private Integer marriage_state;// 婚姻状况 1：未婚2：已婚无子女3：已婚有子女4：离异5：丧偶6：复婚
//	private String urgent_person1_phone;// 紧急联系人A电话
//	private Integer urgent_person2_relationship;// 紧急联系人A电话
//	private String urgent_person2_name;// 紧急联系人2姓名
//	private String urgent_person2_phone;// 紧急联系人2电话
//	private String id_card_front;// 身份证正面照 （可供下载url）
//	private String id_card_back;// 身份证反面照 （可供下载url）
//	private List<String> photo_hand_ids;// 手持身份证 （可供下载url）
//	private List<String> photo_assay;// 活体 （可供下载url）
//	private String company_name;// 公司名称
//	private String company_addr;// 公司地址
//	private String company_tel;// 公司电话
//	private String staff_count;// 员工人数
//	private String time_enrollment;// 入学时间
//	private String school_name;// 学校名称
//	// private String ip_address;// ip地址
//	private String email;// 常用邮箱地址
//	private String address;// 居住地址
//	private String money_use;// 贷款用途 1、短期周转 2、购物 3、装修 4、买房 5、买车 6、旅游 7、医疗 8、教育 9、还款 10、为他人贷款 11、结婚 12、投资经营 13、信用卡还款
//								// 14、购买家电 15、其他耐用消费品 16、垫付保费 17、付房租 18、日常生活消费 19、支付员工工资 20、购买货物/原材料/设备 21、其他
//	private String colleague_name;// 企业同事姓名
//	private String colleague_phone;// 企业同事电话
//	private String friend1_name;// 朋友1名字
//	private String friend1_phone;// 朋友1手机号
//	private String friend2_name;// 朋友2名字
//	private String friend2_phone;// 朋友2手机号
//	private String weixin;// 微信
//	private String qq;// QQ
//
//	public String getColleague_name() {
//		return colleague_name;
//	}
//
//	public void setColleague_name(String colleague_name) {
//		this.colleague_name = colleague_name;
//	}
//
//	public String getColleague_phone() {
//		return colleague_phone;
//	}
//
//	public void setColleague_phone(String colleague_phone) {
//		this.colleague_phone = colleague_phone;
//	}
//
//	public String getFriend1_name() {
//		return friend1_name;
//	}
//
//	public void setFriend1_name(String friend1_name) {
//		this.friend1_name = friend1_name;
//	}
//
//	public String getFriend1_phone() {
//		return friend1_phone;
//	}
//
//	public void setFriend1_phone(String friend1_phone) {
//		this.friend1_phone = friend1_phone;
//	}
//
//	public String getFriend2_name() {
//		return friend2_name;
//	}
//
//	public void setFriend2_name(String friend2_name) {
//		this.friend2_name = friend2_name;
//	}
//
//	public String getFriend2_phone() {
//		return friend2_phone;
//	}
//
//	public void setFriend2_phone(String friend2_phone) {
//		this.friend2_phone = friend2_phone;
//	}
//
//	public String getWeixin() {
//		return weixin;
//	}
//
//	public void setWeixin(String weixin) {
//		this.weixin = weixin;
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
//	public String getUser_mobile() {
//		return user_mobile;
//	}
//
//	public Integer getResidence_type() {
//		return residence_type;
//	}
//
//	public void setResidence_type(Integer residence_type) {
//		this.residence_type = residence_type;
//	}
//
//	public Integer getUrgent_person1_relationship() {
//		return urgent_person1_relationship;
//	}
//
//	public void setUrgent_person1_relationship(Integer urgent_person1_relationship) {
//		this.urgent_person1_relationship = urgent_person1_relationship;
//	}
//
//	public String getUrgent_person1_name() {
//		return urgent_person1_name;
//	}
//
//	public void setUrgent_person1_name(String urgent_person1_name) {
//		this.urgent_person1_name = urgent_person1_name;
//	}
//
//	public Integer getMarriage_state() {
//		return marriage_state;
//	}
//
//	public void setMarriage_state(Integer marriage_state) {
//		this.marriage_state = marriage_state;
//	}
//
//	public String getUrgent_person1_phone() {
//		return urgent_person1_phone;
//	}
//
//	public void setUrgent_person1_phone(String urgent_person1_phone) {
//		this.urgent_person1_phone = urgent_person1_phone;
//	}
//
//	public Integer getUrgent_person2_relationship() {
//		return urgent_person2_relationship;
//	}
//
//	public void setUrgent_person2_relationship(Integer urgent_person2_relationship) {
//		this.urgent_person2_relationship = urgent_person2_relationship;
//	}
//
//	public String getUrgent_person2_name() {
//		return urgent_person2_name;
//	}
//
//	public void setUrgent_person2_name(String urgent_person2_name) {
//		this.urgent_person2_name = urgent_person2_name;
//	}
//
//	public String getUrgent_person2_phone() {
//		return urgent_person2_phone;
//	}
//
//	public void setUrgent_person2_phone(String urgent_person2_phone) {
//		this.urgent_person2_phone = urgent_person2_phone;
//	}
//
//	public String getId_card_front() {
//		return id_card_front;
//	}
//
//	public void setId_card_front(String id_card_front) {
//		this.id_card_front = id_card_front;
//	}
//
//	public String getId_card_back() {
//		return id_card_back;
//	}
//
//	public void setId_card_back(String id_card_back) {
//		this.id_card_back = id_card_back;
//	}
//
//	public List<String> getPhoto_hand_ids() {
//		return photo_hand_ids;
//	}
//
//	public void setPhoto_hand_ids(List<String> photo_hand_ids) {
//		this.photo_hand_ids = photo_hand_ids;
//	}
//
//	public List<String> getPhoto_assay() {
//		return photo_assay;
//	}
//
//	public void setPhoto_assay(List<String> photo_assay) {
//		this.photo_assay = photo_assay;
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
//	public String getCompany_addr() {
//		return company_addr;
//	}
//
//	public void setCompany_addr(String company_addr) {
//		this.company_addr = company_addr;
//	}
//
//	public String getCompany_tel() {
//		return company_tel;
//	}
//
//	public void setCompany_tel(String company_tel) {
//		this.company_tel = company_tel;
//	}
//
//	public String getStaff_count() {
//		return staff_count;
//	}
//
//	public void setStaff_count(String staff_count) {
//		this.staff_count = staff_count;
//	}
//
//	public String getTime_enrollment() {
//		return time_enrollment;
//	}
//
//	public void setTime_enrollment(String time_enrollment) {
//		this.time_enrollment = time_enrollment;
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
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//	public String getMoney_use() {
//		return money_use;
//	}
//
//	public void setMoney_use(String money_use) {
//		this.money_use = money_use;
//	}
//
//	public void setUser_mobile(String user_mobile) {
//		this.user_mobile = user_mobile;
//	}
//
//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public Integer getAuto_type() {
//		return auto_type;
//	}
//
//	public void setAuto_type(Integer auto_type) {
//		this.auto_type = auto_type;
//	}
//
//	public String getEducation() {
//		return education;
//	}
//
//	public void setEducation(String education) {
//		this.education = education;
//	}
//
//	public Integer getJob_type() {
//		return job_type;
//	}
//
//	public void setJob_type(Integer job_type) {
//		this.job_type = job_type;
//	}
//
//	public Integer getWorking_age() {
//		return working_age;
//	}
//
//	public void setWorking_age(Integer working_age) {
//		this.working_age = working_age;
//	}
//
//
//	public Integer getMax_repayment_monthly() {
//		return max_repayment_monthly;
//	}
//
//	public void setMax_repayment_monthly(Integer max_repayment_monthly) {
//		this.max_repayment_monthly = max_repayment_monthly;
//	}
//
//	public Integer getOperating_years() {
//		return operating_years;
//	}
//
//	public void setOperating_years(Integer operating_years) {
//		this.operating_years = operating_years;
//	}
//
//	public String getId_card() {
//		return id_card;
//	}
//
//	public void setId_card(String id_card) {
//		this.id_card = id_card;
//	}
//
//	public String getUser_name() {
//		return user_name;
//	}
//
//	public void setUser_name(String user_name) {
//		this.user_name = user_name;
//	}
//
//	public Integer getSocial_security() {
//		return social_security;
//	}
//
//	public void setSocial_security(Integer social_security) {
//		this.social_security = social_security;
//	}
//
//	public String getHome_phone() {
//		return home_phone;
//	}
//
//	public void setHome_phone(String home_phone) {
//		this.home_phone = home_phone;
//	}
//
//	public Object getGps_location() {
//		return gps_location;
//	}
//
//	public void setGps_location(Object gps_location) {
//		this.gps_location = gps_location;
//	}
//
//	public String getChannel() {
//		return channel;
//	}
//
//	public void setChannel(String channel) {
//		this.channel = channel;
//	}
//
//	public Integer getCreated_at() {
//		return created_at;
//	}
//
//	public void setCreated_at(Integer created_at) {
//		this.created_at = created_at;
//	}
//
//	public String getIp_address() {
//		return ip_address;
//	}
//
//	public void setIp_address(String ip_address) {
//		this.ip_address = ip_address;
//	}
//
//	public Double getCorporate_flow() {
//		return corporate_flow;
//	}
//
//	public void setCorporate_flow(Double corporate_flow) {
//		this.corporate_flow = corporate_flow;
//	}
//
//	public Double getIncome_monthly() {
//		return income_monthly;
//	}
//
//	public void setIncome_monthly(Double income_monthly) {
//		this.income_monthly = income_monthly;
//	}
//
//	public Double getAverage_income_monthly() {
//		return average_income_monthly;
//	}
//
//	public void setAverage_income_monthly(Double average_income_monthly) {
//		this.average_income_monthly = average_income_monthly;
//	}
//
//}
