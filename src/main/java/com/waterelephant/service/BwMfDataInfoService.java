package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfDataInfoService {
	
	void saveDataInfo(String taskId, Long orderId, JSONObject taskData);

}
