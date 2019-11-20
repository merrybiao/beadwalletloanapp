package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfSmsInfoService {

	void saveSmsInfo(String taskId, Long orderId, JSONObject taskData);

}
