package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfUserInfoService {

	void saveUserInfo(String taskId, Long orderId, JSONObject reportData);

}
