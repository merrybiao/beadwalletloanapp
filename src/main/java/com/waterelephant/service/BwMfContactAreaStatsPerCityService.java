package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfContactAreaStatsPerCityService {

	void saveContactAreaStatsPerCity(String taskId, Long orderId, JSONObject reportData);

}
