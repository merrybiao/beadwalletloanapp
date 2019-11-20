package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfAllContactStats;
import com.waterelephant.mapper.BwMfAllContactStatsMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfAllContactStatsService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfAllContactStatsServiceImpl extends BaseService<BwMfAllContactStats, Long>
		implements BwMfAllContactStatsService {

	@Autowired
	private BwMfAllContactStatsMapper bwMfAllContactStatsMapper;

	@Override
	public void saveAllContactStats(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfAllContactStats.class);
		
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		
		this.bwMfAllContactStatsMapper.deleteByExample(example);
		
		if(!reportData.containsKey("all_contact_stats")) return;
		
		JSONObject all_contact_stats = reportData.getJSONObject("all_contact_stats");
		
		if(null == all_contact_stats || all_contact_stats.isEmpty()) return;
		
		BwMfAllContactStats record = all_contact_stats.toJavaObject(BwMfAllContactStats.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		this.bwMfAllContactStatsMapper.insert(record);
	}
	
	
	
}
