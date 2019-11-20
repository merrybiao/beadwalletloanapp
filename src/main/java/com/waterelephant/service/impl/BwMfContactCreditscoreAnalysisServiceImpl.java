package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfContactCreditscoreAnalysis;
import com.waterelephant.mapper.BwMfContactCreditscoreAnalysisMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfContactCreditscoreAnalysisService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfContactCreditscoreAnalysisServiceImpl extends BaseService<BwMfContactCreditscoreAnalysis, Long>
		implements BwMfContactCreditscoreAnalysisService {

	@Autowired
	private BwMfContactCreditscoreAnalysisMapper bwMfContactCreditscoreAnalysisMapper;

	@Override
	public void saveContactCreditscoreAnalysis(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfContactCreditscoreAnalysis.class);
		
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		
		this.bwMfContactCreditscoreAnalysisMapper.deleteByExample(example);
		
		if(!reportData.containsKey("contact_creditscore_analysis")) return;
		
		JSONObject contact_creditscore_analysis = reportData.getJSONObject("contact_creditscore_analysis");
		
		if(null == contact_creditscore_analysis || contact_creditscore_analysis.isEmpty()) return;
		
		BwMfContactCreditscoreAnalysis record = contact_creditscore_analysis.toJavaObject(BwMfContactCreditscoreAnalysis.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		this.bwMfContactCreditscoreAnalysisMapper.insert(record);
	}
	
	
	
}
