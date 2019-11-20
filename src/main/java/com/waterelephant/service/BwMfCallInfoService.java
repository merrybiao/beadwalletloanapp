package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfCallInfoService {

	void saveCallInfo(String taskId, Long orderId, JSONObject taskData);

}
