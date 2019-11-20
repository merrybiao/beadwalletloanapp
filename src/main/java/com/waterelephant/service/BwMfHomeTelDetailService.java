package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfHomeTelDetailService {

	void saveHomeTelDetail(String taskId, Long orderId, JSONObject reportData);

}
