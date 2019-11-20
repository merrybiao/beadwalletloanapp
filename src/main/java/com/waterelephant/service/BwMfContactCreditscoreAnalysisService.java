package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfContactCreditscoreAnalysisService {

	void saveContactCreditscoreAnalysis(String taskId, Long orderId, JSONObject reportData);

}
