package com.waterelephant.third.entity.request;

/**
 * 统一对外接口 - 订单推送 - 公司信息（code0091）
 * 
 * 
 * Module:
 * 
 * CompanyInfo.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class CompanyInfo {
	private String companyName; // 公司名称
	private String industry; // 行业
	private String jobTime; // 工作年限
	private String income; // 月收入

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getJobTime() {
		return jobTime;
	}

	public void setJobTime(String jobTime) {
		this.jobTime = jobTime;
	}
}
