package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfInfoMatchService {

	void saveInfoMatch(String taskId, Long orderId, JSONObject reportData);

}
