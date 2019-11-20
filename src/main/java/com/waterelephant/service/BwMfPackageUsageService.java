package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfPackageUsageService {

	void savePackageUsage(String taskId, Long orderId, JSONObject taskData);

}
