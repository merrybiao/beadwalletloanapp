package com.waterelephant.bjsms.entity;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class DhstResponse {
	
	private String result;
	
	private String desc;
	
	private List<BwDhstreportSms> reports;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<BwDhstreportSms> getReports() {
		return reports;
	}

	public void setReports(List<BwDhstreportSms> reports) {
		this.reports = reports;
	}
}
