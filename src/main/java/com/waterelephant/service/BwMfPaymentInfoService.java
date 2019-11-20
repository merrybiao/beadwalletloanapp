package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfPaymentInfoService {

	void savePaymentInfo(String taskId, Long orderId, JSONObject taskData);

}
