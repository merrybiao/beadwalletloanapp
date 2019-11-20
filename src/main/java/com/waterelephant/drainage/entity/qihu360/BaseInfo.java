package com.waterelephant.drainage.entity.qihu360;

/**
 * 
 * 
 * Module: 用户基本信息（applyDetail）
 * 
 * BaseInfo.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class BaseInfo {
	private String user_name;// 本人姓名
	private String user_id;// 本人身份证号
	private Integer user_education;// 教育程度 1=硕士及以上,2=本科,3=大专,4=高中及中专以下
	private Integer is_on_type;// 职业类别 1=企业主 2=个体户 4=上班族 10=自由职业
	private Integer work_period;// 现单位工作年限 1=0-5 个月,2=6-12 个月,3=1-3 年,4=3-7 年,5=7年以上(is_on_type=4有效)
	private String user_income_by_card;// 月工资收入（元）(is_on_type=4有效)
	private Integer operating_year;// 经营年限 1=0-3个月,2=3-6 个月,3=7-12 个月,4=1-2 年,5=3-4 年,6=5年以上(is_on_type=1 或 2有效 )
	private String corporate_flow;// 经营流水（万元）(is_on_type=1或2有效)
	private String monthly_average_income;// 平均月收入（元）(is_on_type=10有效)
	private Integer user_social_security;// 是否缴纳社保 1=缴纳本地社保,2=未缴纳本地社保

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Integer getUser_education() {
		return user_education;
	}

	public void setUser_education(Integer user_education) {
		this.user_education = user_education;
	}

	public Integer getIs_on_type() {
		return is_on_type;
	}

	public void setIs_on_type(Integer is_on_type) {
		this.is_on_type = is_on_type;
	}

	public Integer getWork_period() {
		return work_period;
	}

	public void setWork_period(Integer work_period) {
		this.work_period = work_period;
	}

	public String getUser_income_by_card() {
		return user_income_by_card;
	}

	public void setUser_income_by_card(String user_income_by_card) {
		this.user_income_by_card = user_income_by_card;
	}

	public Integer getOperating_year() {
		return operating_year;
	}

	public void setOperating_year(Integer operating_year) {
		this.operating_year = operating_year;
	}

	public String getCorporate_flow() {
		return corporate_flow;
	}

	public void setCorporate_flow(String corporate_flow) {
		this.corporate_flow = corporate_flow;
	}

	public String getMonthly_average_income() {
		return monthly_average_income;
	}

	public void setMonthly_average_income(String monthly_average_income) {
		this.monthly_average_income = monthly_average_income;
	}

	public Integer getUser_social_security() {
		return user_social_security;
	}

	public void setUser_social_security(Integer user_social_security) {
		this.user_social_security = user_social_security;
	}

}
