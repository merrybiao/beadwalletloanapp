package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfBehaviorAnalysis;
import com.waterelephant.mapper.BwMfBehaviorAnalysisMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfBehaviorAnalysisService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfBehaviorAnalysisServiceImpl extends BaseService<BwMfBehaviorAnalysis, Long>
		implements BwMfBehaviorAnalysisService {

	
	@Autowired
	private BwMfBehaviorAnalysisMapper bwMfBehaviorAnalysisMapper;

	@Override
	public void saveBehaviorAnalysis(String taskId, Long orderId, JSONObject reportData) {

		Example example = new Example(BwMfBehaviorAnalysis.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		
		this.bwMfBehaviorAnalysisMapper.deleteByExample(example);
		
		if(!reportData.containsKey("behavior_analysis")) return;
		
		JSONObject behavior_analysis = reportData.getJSONObject("behavior_analysis");
		
		if(null == behavior_analysis || behavior_analysis.isEmpty()) return;
		
		BwMfBehaviorAnalysis record = behavior_analysis.toJavaObject(BwMfBehaviorAnalysis.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		this.bwMfBehaviorAnalysisMapper.insert(record);
	}
	
	
	
}
