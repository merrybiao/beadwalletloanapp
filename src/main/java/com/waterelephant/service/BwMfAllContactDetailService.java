package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfAllContactDetailService {

	void saveAllContactDetail(String taskId, Long orderId, JSONObject reportData);

}
