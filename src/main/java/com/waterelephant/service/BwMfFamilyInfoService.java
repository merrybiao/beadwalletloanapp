package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfFamilyInfoService {
	
	void saveFamilyInfo(String taskId, Long orderId, JSONObject taskData);

}
