package com.waterelephant.drainage.baidu.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 用户基本信息，包括工作，居所，联系方式
 * 
 * @author dengyan
 *
 */
public class BasicInfo {

	@JSONField(name = "email")
	private String email; // 邮箱

	@JSONField(name = "city_code")
	private String city_code; // 居住地国标码

	@JSONField(name = "address")
	private String address; // 居住地址

	@JSONField(name = "marriage")
	private String marriage; // 婚姻状况

	@JSONField(name = "company_name")
	private String company_name; // 公司名称

	@JSONField(name = "seniority")
	private int seniority; // 工作年限

	@JSONField(name = "monthly_salary")
	private int monthly_salary; // 月收入

	@JSONField(name = "idcard_img")
	private String idcard_img; // 文件地址查询接口

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return 获取 city_code属性值
	 */
	public String getCity_code() {
		return city_code;
	}

	/**
	 * @param city_code 设置 city_code 属性值为参数值 city_code
	 */
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	/**
	 * @return 获取 company_name属性值
	 */
	public String getCompany_name() {
		return company_name;
	}

	/**
	 * @param company_name 设置 company_name 属性值为参数值 company_name
	 */
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	/**
	 * @return 获取 monthly_salary属性值
	 */
	public int getMonthly_salary() {
		return monthly_salary;
	}

	/**
	 * @param monthly_salary 设置 monthly_salary 属性值为参数值 monthly_salary
	 */
	public void setMonthly_salary(int monthly_salary) {
		this.monthly_salary = monthly_salary;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public int getSeniority() {
		return seniority;
	}

	public void setSeniority(int seniority) {
		this.seniority = seniority;
	}

	/**
	 * @return 获取 idcard_img属性值
	 */
	public String getIdcard_img() {
		return idcard_img;
	}

	/**
	 * @param idcard_img 设置 idcard_img 属性值为参数值 idcard_img
	 */
	public void setIdcard_img(String idcard_img) {
		this.idcard_img = idcard_img;
	}

}
