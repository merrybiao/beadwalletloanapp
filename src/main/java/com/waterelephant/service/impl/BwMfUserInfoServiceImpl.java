package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfUserInfo;
import com.waterelephant.mapper.BwMfUserInfoMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfUserInfoService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfUserInfoServiceImpl extends BaseService<BwMfUserInfo, Long> implements BwMfUserInfoService {

	
	@Autowired
	private BwMfUserInfoMapper bwMfUserInfoMapper;

	@Override
	public void saveUserInfo(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfUserInfo.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfUserInfoMapper.deleteByExample(example);
		
		if(!reportData.containsKey("user_info")) return ;
		
		JSONObject user_info = reportData.getJSONObject("user_info");
		
		if(null == user_info || user_info.isEmpty()) return;
		
		BwMfUserInfo record = user_info.toJavaObject(BwMfUserInfo.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		this.bwMfUserInfoMapper.insert(record);
		
	}
	
	
	
	
}
