package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfContactGangFraudAnalysis;
import com.waterelephant.mapper.BwMfContactGangFraudAnalysisMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfContactGangFraudAnalysisService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfContactGangFraudAnalysisServiceImpl extends BaseService<BwMfContactGangFraudAnalysis, Long>
		implements BwMfContactGangFraudAnalysisService {

	@Autowired
	private BwMfContactGangFraudAnalysisMapper bwMfContactGangFraudAnalysisMapper;

	@Override
	public void saveContactGangFraudAnalysis(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfContactGangFraudAnalysis.class);
		
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		
		this.bwMfContactGangFraudAnalysisMapper.deleteByExample(example);
		
		if(!reportData.containsKey("contact_gang_fraud_analysis")) return;
		
		JSONObject contact_gang_fraud_analysis = reportData.getJSONObject("contact_gang_fraud_analysis");
		
		if(null == contact_gang_fraud_analysis || contact_gang_fraud_analysis.isEmpty()) return;
		
		BwMfContactGangFraudAnalysis record = contact_gang_fraud_analysis.toJavaObject(BwMfContactGangFraudAnalysis.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		this.bwMfContactGangFraudAnalysisMapper.insert(record);
		
	}
	
	
}
