package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfAllContactStatsPerMonth;
import com.waterelephant.mapper.BwMfAllContactStatsPerMonthMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfAllContactStatsPerMonthService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfAllContactStatsPerMonthServiceImpl extends BaseService<BwMfAllContactStatsPerMonth, Long>
		implements BwMfAllContactStatsPerMonthService {

	@Autowired
	private BwMfAllContactStatsPerMonthMapper bwMfAllContactStatsPerMonthMapper;

	@Override
	public void saveAllContactStatsPerMonth(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfAllContactStatsPerMonth.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		
		this.bwMfAllContactStatsPerMonthMapper.deleteByExample(example);
		
		if(!reportData.containsKey("all_contact_stats_per_month")) return;
		
		JSONArray all_contact_stats_per_monthList = reportData.getJSONArray("all_contact_stats_per_month");
		
		if(null == all_contact_stats_per_monthList || all_contact_stats_per_monthList.isEmpty()) return;
		
		List<BwMfAllContactStatsPerMonth> list = new ArrayList<>();
		
		for(int i =0;i<all_contact_stats_per_monthList.size();i++) {
			
			JSONObject all_contact_stats_per_month = all_contact_stats_per_monthList.getJSONObject(i);
			
			if(all_contact_stats_per_month.isEmpty()) continue;
			BwMfAllContactStatsPerMonth record = all_contact_stats_per_month.toJavaObject(BwMfAllContactStatsPerMonth.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			
			list.add(record);
		}
		if(!list.isEmpty()) this.bwMfAllContactStatsPerMonthMapper.insertList(list);
		
	}
	
	
}
