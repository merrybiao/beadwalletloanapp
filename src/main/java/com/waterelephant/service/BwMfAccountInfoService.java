package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfAccountInfo;

public interface BwMfAccountInfoService {

	void saveAccountInfo(String taskId, Long orderId, JSONObject taskData);
	
	BwMfAccountInfo saveQueryBwMfAccountInfo(String taskId,Long orderId);

}
