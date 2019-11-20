package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfDataCompletenessService {

	void saveDataCompleteness(String taskId, Long orderId, JSONObject reportData);

}
