package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfFinanceContactDetailService {

	void saveFinanceContactDetail(String taskId, Long orderId, JSONObject reportData);

}
