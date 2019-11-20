package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfInfoCheck;
import com.waterelephant.mapper.BwMfInfoCheckMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfInfoCheckService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfInfoCheckServiceImpl extends BaseService<BwMfInfoCheck, Long> implements BwMfInfoCheckService {

	
	@Autowired
	private BwMfInfoCheckMapper bwMfInfoCheckMapper;

	@Override
	public void saveInfoCheck(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfInfoCheck.class);
		
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		
		this.bwMfInfoCheckMapper.deleteByExample(example);
		
		if(!reportData.containsKey("info_check")) return;
		
		JSONObject info_check = reportData.getJSONObject("info_check");
		
		if(null == info_check || info_check.isEmpty()) return;
		
		BwMfInfoCheck record = info_check.toJavaObject(BwMfInfoCheck.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		this.bwMfInfoCheckMapper.insert(record);
	}
	
	
}
