package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfFinanceContactStatsService {

	void saveFinanceContactStats(String taskId, Long orderId, JSONObject reportData);

}
