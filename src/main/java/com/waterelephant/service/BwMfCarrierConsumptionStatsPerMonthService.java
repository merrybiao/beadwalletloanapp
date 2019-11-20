package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfCarrierConsumptionStatsPerMonthService {

	void saveCarrierConsumptionStatsPerMonth(String taskId, Long orderId, JSONObject reportData);

}
