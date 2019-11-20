package com.waterelephant.fudata.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class TaskInfoResponse extends DefaultFudataResponse {
	
	private static final long serialVersionUID = -8057899679187846192L;
	
	@JSONField(name = "task_info")
	private Object taskInfo;

	public Object getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(Object taskInfo) {
		this.taskInfo = taskInfo;
	}
	
}
