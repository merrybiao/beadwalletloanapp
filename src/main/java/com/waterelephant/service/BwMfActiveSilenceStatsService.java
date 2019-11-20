package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfActiveSilenceStatsService {

	void saveActiveSilenceStats(String taskId, Long orderId, JSONObject reportData);

}
