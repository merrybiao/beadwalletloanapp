package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfBehaviorScoreService {

	void saveBehaviorScore(String taskId, Long orderId, JSONObject reportData);

}
