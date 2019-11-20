package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfContactSuspectCollectionAnalysis;
import com.waterelephant.mapper.BwMfContactSuspectCollectionAnalysisMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfContactSuspectCollectionAnalysisService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfContactSuspectCollectionAnalysisServiceImpl extends
		BaseService<BwMfContactSuspectCollectionAnalysis, Long> implements BwMfContactSuspectCollectionAnalysisService {
	
	@Autowired
	private BwMfContactSuspectCollectionAnalysisMapper bwMfContactSuspectCollectionAnalysisMapper;

	@Override
	public void saveContactSuspectCollectionAnalysis(String taskId, Long orderId, JSONObject reportData) {
		Example example = new Example(BwMfContactSuspectCollectionAnalysis.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfContactSuspectCollectionAnalysisMapper.deleteByExample(example);
		
		if(!reportData.containsKey("contact_suspect_collection_analysis")) return;
		
		JSONArray contact_suspect_collection_analysisList = reportData.getJSONArray("contact_suspect_collection_analysis");
		
		if(null == contact_suspect_collection_analysisList || contact_suspect_collection_analysisList.isEmpty()) return;
		
		List<BwMfContactSuspectCollectionAnalysis> list = new ArrayList<>();
		
		for(int i=0;i< contact_suspect_collection_analysisList.size();i++) {
			JSONObject contact_suspect_collection_analysis = contact_suspect_collection_analysisList.getJSONObject(i);
			if(contact_suspect_collection_analysis.isEmpty()) continue;
			BwMfContactSuspectCollectionAnalysis record = contact_suspect_collection_analysis.toJavaObject(BwMfContactSuspectCollectionAnalysis.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			list.add(record);
		}
		if(!list.isEmpty()) this.bwMfContactSuspectCollectionAnalysisMapper.insertList(list);
	}
	
	
}
