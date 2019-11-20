package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfInfoCheckService {

	void saveInfoCheck(String taskId, Long orderId, JSONObject reportData);

}
