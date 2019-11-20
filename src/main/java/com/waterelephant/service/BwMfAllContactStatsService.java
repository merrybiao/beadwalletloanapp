package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfAllContactStatsService {

	void saveAllContactStats(String taskId, Long orderId, JSONObject reportData);

}
