package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfEmergencyContactDetailService {

	void saveEmergencyContactDetail(String taskId, Long orderId, JSONObject reportData);

}
