package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfActiveSilenceStats;
import com.waterelephant.mapper.BwMfActiveSilenceStatsMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfActiveSilenceStatsService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfActiveSilenceStatsServiceImpl extends BaseService<BwMfActiveSilenceStats, Long>
		implements BwMfActiveSilenceStatsService {

	
	@Autowired
	private BwMfActiveSilenceStatsMapper bwMfActiveSilenceStatsMapper;

	@Override
	public void saveActiveSilenceStats(String taskId, Long orderId, JSONObject reportData) {

		Example example = new Example(BwMfActiveSilenceStats.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfActiveSilenceStatsMapper.deleteByExample(example);
		
		if(!reportData.containsKey("active_silence_stats")) return;
		
		JSONObject active_silence_stats = reportData.getJSONObject("active_silence_stats");
		
		if(null == active_silence_stats || active_silence_stats.isEmpty()) return;
		
		BwMfActiveSilenceStats record = active_silence_stats.toJavaObject(BwMfActiveSilenceStats.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		this.bwMfActiveSilenceStatsMapper.insert(record);
		
		
		
	}
	
	
}
