package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfCallAreaStatsPerCityService {

	void saveCallAreaStatsPerCity(String taskId, Long orderId, JSONObject reportData);

}
