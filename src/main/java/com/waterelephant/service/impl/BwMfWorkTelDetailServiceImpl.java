package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfWorkTelDetail;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfWorkTelDetailService;

@Service
public class BwMfWorkTelDetailServiceImpl extends BaseService<BwMfWorkTelDetail, Long>
		implements BwMfWorkTelDetailService {

	@Override
	public void saveWorkTelDetail(String taskId, Long orderId, JSONObject reportData) {
		//不存在该节点
		if(!reportData.containsKey("work_tel_detail")) return;
		
		
		
		
	}
	
	
}
