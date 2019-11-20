package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfCarrierConsumptionStatsService {

	void saveCarrierConsumptionStats(String taskId, Long orderId, JSONObject reportData);

}
