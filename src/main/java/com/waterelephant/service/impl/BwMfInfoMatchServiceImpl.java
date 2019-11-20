package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfInfoMatch;
import com.waterelephant.mapper.BwMfInfoMatchMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfInfoMatchService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfInfoMatchServiceImpl extends BaseService<BwMfInfoMatch, Long> implements BwMfInfoMatchService {

	@Autowired
	private BwMfInfoMatchMapper bwMfInfoMatchMapper;

	@Override
	public void saveInfoMatch(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfInfoMatch.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfInfoMatchMapper.deleteByExample(example);
		
		if(!reportData.containsKey("info_match")) return;
		
		JSONObject info_match = reportData.getJSONObject("info_match");
		
		if(null == info_match || info_match.isEmpty()) return;
		
		BwMfInfoMatch record = info_match.toJavaObject(BwMfInfoMatch.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		this.bwMfInfoMatchMapper.insert(record);
	}
	
	
}
