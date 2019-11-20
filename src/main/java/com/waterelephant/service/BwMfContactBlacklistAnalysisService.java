package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfContactBlacklistAnalysisService {

	void saveContactBlacklistAnalysis(String taskId, Long orderId, JSONObject reportData);

}
