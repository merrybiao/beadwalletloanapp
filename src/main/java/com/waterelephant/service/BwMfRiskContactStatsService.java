package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfRiskContactStatsService {

	void saveRiskContactStats(String taskId, Long orderId, JSONObject reportData);

}
