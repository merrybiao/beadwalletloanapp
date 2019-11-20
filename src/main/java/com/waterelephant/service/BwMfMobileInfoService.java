package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfMobileInfoService {

	void saveMobileInfo(String taskId, Long orderId, JSONObject reportData);

}
