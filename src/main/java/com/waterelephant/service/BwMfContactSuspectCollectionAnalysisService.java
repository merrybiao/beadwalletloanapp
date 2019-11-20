package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfContactSuspectCollectionAnalysisService {

	void saveContactSuspectCollectionAnalysis(String taskId, Long orderId, JSONObject reportData);

}
