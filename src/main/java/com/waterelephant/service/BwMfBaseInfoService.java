package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfBaseInfoService {

	void saveBaseInfo(String taskId, Long orderId, JSONObject taskData);

}
