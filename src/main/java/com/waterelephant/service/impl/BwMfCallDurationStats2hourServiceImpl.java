package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfCallDurationStats2hour;
import com.waterelephant.mapper.BwMfCallDurationStats2hourMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfCallDurationStats2hourService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfCallDurationStats2hourServiceImpl extends BaseService<BwMfCallDurationStats2hour, Long>
		implements BwMfCallDurationStats2hourService {

	@Autowired
	private BwMfCallDurationStats2hourMapper bwMfCallDurationStats2hourMapper;

	@Override
	public void saveCallDurationStats2hour(String taskId, Long orderId, JSONObject reportData) {

		Example example = new Example(BwMfCallDurationStats2hour.class);
		
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		
		this.bwMfCallDurationStats2hourMapper.deleteByExample(example);
		
		if(!reportData.containsKey("call_duration_stats_2hour")) return;
		
		JSONObject call_duration_stats_2hour = reportData.getJSONObject("call_duration_stats_2hour");
		
		if(null == call_duration_stats_2hour || call_duration_stats_2hour.isEmpty()) return;
		
		BwMfCallDurationStats2hour record = call_duration_stats_2hour.toJavaObject(BwMfCallDurationStats2hour.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		this.bwMfCallDurationStats2hourMapper.insert(record);
	}
	
	
}
