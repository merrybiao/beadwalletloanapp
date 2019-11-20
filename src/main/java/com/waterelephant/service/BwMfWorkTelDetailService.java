package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfWorkTelDetailService {

	void saveWorkTelDetail(String taskId, Long orderId, JSONObject reportData);

}
