package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfCarrierConsumptionStats;
import com.waterelephant.mapper.BwMfCarrierConsumptionStatsMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfCarrierConsumptionStatsService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfCarrierConsumptionStatsServiceImpl extends BaseService<BwMfCarrierConsumptionStats, Long>
		implements BwMfCarrierConsumptionStatsService {
	
	@Autowired
	private BwMfCarrierConsumptionStatsMapper bwMfCarrierConsumptionStatsMapper;

	@Override
	public void saveCarrierConsumptionStats(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfCarrierConsumptionStats.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfCarrierConsumptionStatsMapper.deleteByExample(example);
		
		if(!reportData.containsKey("carrier_consumption_stats")) return;
		
		JSONObject carrier_consumption_stats = reportData.getJSONObject("carrier_consumption_stats");
		
		if(null == carrier_consumption_stats || carrier_consumption_stats.isEmpty()) return;
		
		BwMfCarrierConsumptionStats record = carrier_consumption_stats.toJavaObject(BwMfCarrierConsumptionStats.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		this.bwMfCarrierConsumptionStatsMapper.insert(record);
		
	}
	
	
}
