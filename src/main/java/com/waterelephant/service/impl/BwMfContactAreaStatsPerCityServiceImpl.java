package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfContactAreaStatsPerCity;
import com.waterelephant.mapper.BwMfContactAreaStatsPerCityMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfContactAreaStatsPerCityService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfContactAreaStatsPerCityServiceImpl extends BaseService<BwMfContactAreaStatsPerCity, Long>
		implements BwMfContactAreaStatsPerCityService {
	
	@Autowired
	private BwMfContactAreaStatsPerCityMapper bwMfContactAreaStatsPerCityMapper;

	@Override
	public void saveContactAreaStatsPerCity(String taskId, Long orderId, JSONObject reportData) {
		Example example = new Example(BwMfContactAreaStatsPerCity.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfContactAreaStatsPerCityMapper.deleteByExample(example);
		
		if(!reportData.containsKey("contact_area_stats_per_city")) return;
		
		JSONArray contact_area_stats_per_cityList = reportData.getJSONArray("contact_area_stats_per_city");
		
		if(null == contact_area_stats_per_cityList || contact_area_stats_per_cityList.isEmpty()) return;
		
		List<BwMfContactAreaStatsPerCity> list = new ArrayList<>();
		
		for(int i =0;i<contact_area_stats_per_cityList.size();i++) {
			JSONObject contact_area_stats_per_city = contact_area_stats_per_cityList.getJSONObject(i);
			if(contact_area_stats_per_city.isEmpty()) continue;
			BwMfContactAreaStatsPerCity record = contact_area_stats_per_city.toJavaObject(BwMfContactAreaStatsPerCity.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			list.add(record);
		}
		if(!list.isEmpty()) this.bwMfContactAreaStatsPerCityMapper.insertList(list);
	}

}
