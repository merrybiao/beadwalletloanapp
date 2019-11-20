package com.waterelephant.service;

import com.alibaba.fastjson.JSONObject;

public interface BwMfReportInfoService {

	void saveReportInfo(String taskId, Long orderId, JSONObject reportData);

}
