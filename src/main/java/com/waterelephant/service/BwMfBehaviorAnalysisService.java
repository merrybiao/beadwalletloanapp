package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfBehaviorAnalysisService {

	void saveBehaviorAnalysis(String taskId, Long orderId, JSONObject reportData);

}
