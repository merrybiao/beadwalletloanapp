package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfCallAreaStatsPerCity;
import com.waterelephant.mapper.BwMfCallAreaStatsPerCityMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfCallAreaStatsPerCityService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfCallAreaStatsPerCityServiceImpl extends BaseService<BwMfCallAreaStatsPerCity, Long> implements BwMfCallAreaStatsPerCityService {

	
	@Autowired
	private BwMfCallAreaStatsPerCityMapper bwMfCallAreaStatsPerCityMapper;
	
	@Override
	public void saveCallAreaStatsPerCity(String taskId, Long orderId, JSONObject reportData) {
		//先删除历史记录
		Example example = new Example(BwMfCallAreaStatsPerCity.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfCallAreaStatsPerCityMapper.deleteByExample(example);
		
		if(reportData.containsKey("call_area_stats_per_city")) {
			JSONArray call_area_stats_per_cityList = reportData.getJSONArray("call_area_stats_per_city");
			if(null == call_area_stats_per_cityList ||  call_area_stats_per_cityList.isEmpty()) return;
			
			List<BwMfCallAreaStatsPerCity> list = new ArrayList<>();
			for(int i=0;i<call_area_stats_per_cityList.size();i++) {
				JSONObject call_area_stats_per_city = call_area_stats_per_cityList.getJSONObject(i);
				if(call_area_stats_per_city.isEmpty()) continue;
				BwMfCallAreaStatsPerCity record = call_area_stats_per_city.toJavaObject(BwMfCallAreaStatsPerCity.class);
				record.setTaskId(taskId);
				record.setOrderId(orderId);
				record.setCreateTime(Calendar.getInstance().getTime());
				record.setUpdateTime(Calendar.getInstance().getTime());
				list.add(record);
			}
			if(!list.isEmpty())this.bwMfCallAreaStatsPerCityMapper.insertList(list);
		}
		
	}

	
	
}
