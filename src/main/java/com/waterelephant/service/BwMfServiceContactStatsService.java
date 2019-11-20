package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfServiceContactStatsService {

	void saveServiceContactStats(String taskId, Long orderId, JSONObject reportData);

}
