package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfTravelTrackAnalysisPerCityService {

	void saveTravelTrackAnalysisPerCity(String taskId, Long orderId, JSONObject reportData);

}
