package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfRiskContactStats;
import com.waterelephant.mapper.BwMfRiskContactStatsMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfRiskContactStatsService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfRiskContactStatsServiceImpl extends BaseService<BwMfRiskContactStats, Long>
		implements BwMfRiskContactStatsService {
	
	@Autowired
	private BwMfRiskContactStatsMapper bwMfRiskContactStatsMapper;

	@Override
	public void saveRiskContactStats(String taskId, Long orderId, JSONObject reportData) {
	
		Example example = new Example(BwMfRiskContactStats.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfRiskContactStatsMapper.deleteByExample(example);
		
		if(!reportData.containsKey("risk_contact_stats")) return ;
		
		JSONArray risk_contact_statsList = reportData.getJSONArray("risk_contact_stats");
		
		if(null == risk_contact_statsList || risk_contact_statsList.isEmpty()) return;
		
		List<BwMfRiskContactStats> list = new ArrayList<>();
		
		for(int i= 0;i < risk_contact_statsList.size();i++) {
			JSONObject risk_contact_stats = risk_contact_statsList.getJSONObject(i);
			if(risk_contact_stats.isEmpty()) continue;
			
			BwMfRiskContactStats record = risk_contact_stats.toJavaObject(BwMfRiskContactStats.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			list.add(record);
		}
		if(!list.isEmpty()) this.bwMfRiskContactStatsMapper.insertList(list);
	}
	
	
	

}
