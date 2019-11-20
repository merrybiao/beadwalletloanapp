/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象股份有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.moxie.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author 崔雄健
 * @date 2017年3月24日
 * @description
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarrierBillTask {
	private String mobile;
	@JsonProperty("user_id")
	private String userId;
	@JsonProperty("task_id")
	private String taskId;
	private List<String> bills;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<String> getBills() {
		return bills;
	}

	public void setBills(List<String> bills) {
		this.bills = bills;
	}

}