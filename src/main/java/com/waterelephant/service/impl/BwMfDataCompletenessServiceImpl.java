package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfDataCompleteness;
import com.waterelephant.entity.BwMfDataCompletenessPerMonth;
import com.waterelephant.mapper.BwMfDataCompletenessMapper;
import com.waterelephant.mapper.BwMfDataCompletenessPerMonthMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfDataCompletenessService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfDataCompletenessServiceImpl extends BaseService<BwMfDataCompleteness, Long>
		implements BwMfDataCompletenessService {

	
	@Autowired
	private BwMfDataCompletenessMapper bwMfDataCompletenessMapper;
	
	@Autowired
	private BwMfDataCompletenessPerMonthMapper bwMfDataCompletenessPerMonthMapper;

	@Override
	public void saveDataCompleteness(String taskId, Long orderId, JSONObject reportData) {
		Example example = new Example(BwMfDataCompleteness.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfDataCompletenessMapper.deleteByExample(example);
		example = new Example(BwMfDataCompletenessPerMonth.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfDataCompletenessPerMonthMapper.deleteByExample(example);
		
		if(!reportData.containsKey("data_completeness")) return;
		
		JSONObject data_completeness= reportData.getJSONObject("data_completeness");
		
		if(null == data_completeness || data_completeness.isEmpty()) return;
		
		BwMfDataCompleteness record = data_completeness.toJavaObject(BwMfDataCompleteness.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		bwMfDataCompletenessMapper.insert(record);
		
		if(!data_completeness.containsKey("data_completeness_per_month")) return;
		
		JSONArray data_completeness_per_monthList = data_completeness.getJSONArray("data_completeness_per_month");
		
		if(null == data_completeness_per_monthList || data_completeness_per_monthList.isEmpty()) return;
		
		List<BwMfDataCompletenessPerMonth> list = new ArrayList<>();
		
		for(int i=0;i<data_completeness_per_monthList.size();i++) {
			JSONObject data_completeness_per_month = data_completeness_per_monthList.getJSONObject(i);
			if(data_completeness_per_month.isEmpty())continue;
			
			BwMfDataCompletenessPerMonth perMonth = data_completeness_per_month.toJavaObject(BwMfDataCompletenessPerMonth.class);
			perMonth.setTaskId(taskId);
			perMonth.setOrderId(orderId);
			perMonth.setDataCompletenessId(record.getId());
			perMonth.setCreateTime(Calendar.getInstance().getTime());
			perMonth.setUpdateTime(Calendar.getInstance().getTime());
			list.add(perMonth);
		}
		if(!list.isEmpty()) this.bwMfDataCompletenessPerMonthMapper.insertList(list);
	}
	
	
}
