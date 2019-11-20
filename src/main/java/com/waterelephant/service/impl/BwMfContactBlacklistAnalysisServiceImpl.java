package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfContactBlacklistAnalysis;
import com.waterelephant.mapper.BwMfContactBlacklistAnalysisMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfContactBlacklistAnalysisService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfContactBlacklistAnalysisServiceImpl extends BaseService<BwMfContactBlacklistAnalysis, Long>
		implements BwMfContactBlacklistAnalysisService {

	
	@Autowired
	private BwMfContactBlacklistAnalysisMapper bwMfContactBlacklistAnalysisMapper;

	@Override
	public void saveContactBlacklistAnalysis(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfContactBlacklistAnalysis.class);
		
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		
		this.bwMfContactBlacklistAnalysisMapper.deleteByExample(example);
		
		if(!reportData.containsKey("contact_blacklist_analysis")) return;
		
		JSONObject contact_blacklist_analysis = reportData.getJSONObject("contact_blacklist_analysis");
		
		if(null == contact_blacklist_analysis || contact_blacklist_analysis.isEmpty()) return;
		
		BwMfContactBlacklistAnalysis record = contact_blacklist_analysis.toJavaObject(BwMfContactBlacklistAnalysis.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		this.bwMfContactBlacklistAnalysisMapper.insert(record);
	}
	
	
}
