package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfCallDurationStats2hourService {

	void saveCallDurationStats2hour(String taskId, Long orderId, JSONObject reportData);

}
