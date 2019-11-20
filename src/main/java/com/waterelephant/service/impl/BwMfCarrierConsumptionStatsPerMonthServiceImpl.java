package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfCarrierConsumptionStatsPerMonth;
import com.waterelephant.mapper.BwMfCarrierConsumptionStatsPerMonthMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfCarrierConsumptionStatsPerMonthService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfCarrierConsumptionStatsPerMonthServiceImpl extends
		BaseService<BwMfCarrierConsumptionStatsPerMonth, Long> implements BwMfCarrierConsumptionStatsPerMonthService {

	@Autowired
	private BwMfCarrierConsumptionStatsPerMonthMapper bwMfCarrierConsumptionStatsPerMonthMapper;

	@Override
	public void saveCarrierConsumptionStatsPerMonth(String taskId, Long orderId, JSONObject reportData) {
		Example example = new Example(BwMfCarrierConsumptionStatsPerMonth.class);
		
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		
		this.bwMfCarrierConsumptionStatsPerMonthMapper.deleteByExample(example);
		
		if(!reportData.containsKey("carrier_consumption_stats_per_month")) return;
		
		JSONArray carrier_consumption_stats_per_monthList = reportData.getJSONArray("carrier_consumption_stats_per_month");
		
		if(null == carrier_consumption_stats_per_monthList || carrier_consumption_stats_per_monthList.isEmpty()) return;
		
		List<BwMfCarrierConsumptionStatsPerMonth> list = new ArrayList<>();
		
		for(int i=0;i<carrier_consumption_stats_per_monthList.size();i++) {
			JSONObject carrier_consumption_stats_per_month = carrier_consumption_stats_per_monthList.getJSONObject(i);
			
			if(carrier_consumption_stats_per_month.isEmpty()) continue;
			BwMfCarrierConsumptionStatsPerMonth record = carrier_consumption_stats_per_month.toJavaObject(BwMfCarrierConsumptionStatsPerMonth.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			list.add(record);
		}
		if(!list.isEmpty())this.bwMfCarrierConsumptionStatsPerMonthMapper.insertList(list);
	}
	
}
