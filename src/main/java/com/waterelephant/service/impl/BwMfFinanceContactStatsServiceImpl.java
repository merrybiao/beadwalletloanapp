package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfFinanceContactStats;
import com.waterelephant.mapper.BwMfFinanceContactStatsMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfFinanceContactStatsService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfFinanceContactStatsServiceImpl extends BaseService<BwMfFinanceContactStats, Long>
		implements BwMfFinanceContactStatsService {
	
	@Autowired
	private BwMfFinanceContactStatsMapper bwMfFinanceContactStatsMapper;

	@Override
	public void saveFinanceContactStats(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfFinanceContactStats.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		
		this.bwMfFinanceContactStatsMapper.deleteByExample(example);
		
		if(!reportData.containsKey("finance_contact_stats")) return;
		
		JSONArray finance_contact_statsList = reportData.getJSONArray("finance_contact_stats");
		
		if(null == finance_contact_statsList || finance_contact_statsList.isEmpty()) return;
		
		List<BwMfFinanceContactStats> list = new ArrayList<>();
		for(int i=0;i<finance_contact_statsList.size();i++) {
			JSONObject finance_contact_stats = finance_contact_statsList.getJSONObject(i);
			if(finance_contact_stats.isEmpty())continue;
			BwMfFinanceContactStats record = finance_contact_stats.toJavaObject(BwMfFinanceContactStats.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			list.add(record);
		}
		if(!list.isEmpty()) this.bwMfFinanceContactStatsMapper.insertList(list);
	}

}
