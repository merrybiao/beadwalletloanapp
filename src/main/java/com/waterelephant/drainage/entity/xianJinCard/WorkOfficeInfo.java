package com.waterelephant.drainage.entity.xianJinCard;

/**
 * Module: WorkOfficeInfo.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class WorkOfficeInfo {

	private String company_name;// 公司名称
	private String areas;// 公司地区：省,市,区 (英文逗号区隔)
	private String address;// 公司地址
	private String type;// 公司类型：1.政府或企事业单位，2.央企国企，3.外资企业，4.上市公司，5.民营企业，6.个体工商户
	private String work_age;// 当前单位工龄: 1.0-5个月,2.6-11个月; 3.1-3年; 4.3-7年 5.7年以上
	private String pay_type;// 工资发放形式: 1.银行代发 2.现金发放 3.部分银行部分现金
	private String revenue;// 月收入

	public void setWorkAge(String workAge) {
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWork_age() {
		return work_age;
	}

	public void setWork_age(String work_age) {
		this.work_age = work_age;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getRevenue() {
		return revenue;
	}

	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}

}