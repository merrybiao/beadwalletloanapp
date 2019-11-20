package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfRiskContactDetailService {

	void saveRiskContactDetail(String taskId, Long orderId, JSONObject reportData);

}
