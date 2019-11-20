package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfAllContactStatsPerMonthService {

	void saveAllContactStatsPerMonth(String taskId, Long orderId, JSONObject reportData);

}
