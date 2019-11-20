package com.waterelephant.fudata.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class TaskReportResponse extends TaskInfoResponse {
	
	private static final long serialVersionUID = -8763758346744816711L;
	
	@JSONField(name="report_info")
	private Object reportInfo;

	public Object getReportInfo() {
		return reportInfo;
	}

	public void setReportInfo(Object reportInfo) {
		this.reportInfo = reportInfo;
	}

}
