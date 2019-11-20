package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfServiceContactStats;
import com.waterelephant.mapper.BwMfServiceContactStatsMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfServiceContactStatsService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfServiceContactStatsServiceImpl extends BaseService<BwMfServiceContactStats, Long>
		implements BwMfServiceContactStatsService {

	
	@Autowired
	private BwMfServiceContactStatsMapper bwMfServiceContactStatsMapper;

	@Override
	public void saveServiceContactStats(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfServiceContactStats.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfServiceContactStatsMapper.deleteByExample(example);
		
		if(!reportData.containsKey("service_contact_stats")) return;
		
		JSONArray service_contact_statsList = reportData.getJSONArray("service_contact_stats");
		
		if(null == service_contact_statsList ||service_contact_statsList.isEmpty()) return;
		
		List<BwMfServiceContactStats> list = new ArrayList<>();
		
		for(int i =0;i<service_contact_statsList.size();i++) {
			JSONObject service_contact_stats = service_contact_statsList.getJSONObject(i);
			if(service_contact_stats.isEmpty()) continue;
			BwMfServiceContactStats record = service_contact_stats.toJavaObject(BwMfServiceContactStats.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			list.add(record);
		}
		if(!list.isEmpty()) this.bwMfServiceContactStatsMapper.insertList(list);
		
	}
	
	
}
