package com.waterelephant.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;


public interface BwDhstreportSmsService {
	
	public boolean saveDaHanSanTongSms(List<JSONObject> jsonArray) throws Exception;
	
}
