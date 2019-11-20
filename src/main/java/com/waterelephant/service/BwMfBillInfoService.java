package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfBillInfoService {
	
	void saveBillInfo(String taskId, Long orderId, JSONObject taskData);

}
