package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfTravelTrackAnalysisPerCity;
import com.waterelephant.mapper.BwMfTravelTrackAnalysisPerCityMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfTravelTrackAnalysisPerCityService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfTravelTrackAnalysisPerCityServiceImpl extends BaseService<BwMfTravelTrackAnalysisPerCity, Long>
		implements BwMfTravelTrackAnalysisPerCityService {

	
	@Autowired
	private BwMfTravelTrackAnalysisPerCityMapper bwMfTravelTrackAnalysisPerCityMapper;

	@Override
	public void saveTravelTrackAnalysisPerCity(String taskId, Long orderId, JSONObject reportData) {
		Example example = new Example(BwMfTravelTrackAnalysisPerCity.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfTravelTrackAnalysisPerCityMapper.deleteByExample(example);
		
		if(!reportData.containsKey("travel_track_analysis_per_city")) return;
		
		JSONArray travel_track_analysis_per_cityList = reportData.getJSONArray("travel_track_analysis_per_city");
		
		if(null == travel_track_analysis_per_cityList || travel_track_analysis_per_cityList.isEmpty()) return;
		
		List<BwMfTravelTrackAnalysisPerCity> list = new ArrayList<>();
		
		for(int i =0;i < travel_track_analysis_per_cityList.size();i++) {
			JSONObject travel_track_analysis_per_city = travel_track_analysis_per_cityList.getJSONObject(i);
			
			if(travel_track_analysis_per_city.isEmpty()) continue;
			
			BwMfTravelTrackAnalysisPerCity record = travel_track_analysis_per_city.toJavaObject(BwMfTravelTrackAnalysisPerCity.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			list.add(record);
		}
		
		if(!list.isEmpty()) this.bwMfTravelTrackAnalysisPerCityMapper.insertList(list);
	} 
	
	
}
