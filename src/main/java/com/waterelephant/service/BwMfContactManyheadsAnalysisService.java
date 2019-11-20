package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfContactManyheadsAnalysisService {

	void saveContactManyheadsAnalysis(String taskId, Long orderId, JSONObject reportData);

}
