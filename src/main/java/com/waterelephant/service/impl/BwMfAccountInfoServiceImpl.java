package com.waterelephant.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfAccountInfo;
import com.waterelephant.mapper.BwMfAccountInfoMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfAccountInfoService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfAccountInfoServiceImpl extends BaseService<BwMfAccountInfo, Long> implements BwMfAccountInfoService {

	@Autowired
	private BwMfAccountInfoMapper bwMfAccountInfoMapper;
	
	@Override
	public void saveAccountInfo(String taskId, Long orderId, JSONObject taskData) {
		Example example = new Example(BwMfAccountInfo.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfAccountInfoMapper.deleteByExample(example);
		if(taskData.containsKey("account_info")) {
			JSONObject account_info = taskData.getJSONObject("account_info");
			if(null != account_info && !account_info.isEmpty()) {
				BwMfAccountInfo record = account_info.toJavaObject(BwMfAccountInfo.class);
				record.setTaskId(taskId);
				record.setOrderId(orderId);
				record.setCreateTime(Calendar.getInstance().getTime());
				record.setUpdateTime(Calendar.getInstance().getTime());
				this.bwMfAccountInfoMapper.insert(record);
			}
		}
	}
	
	@Override
	public BwMfAccountInfo saveQueryBwMfAccountInfo(String taskId,Long orderId) {
		Example example = new Example(BwMfAccountInfo.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		List<BwMfAccountInfo> list = selectByExample(example);
		return list == null || list.isEmpty() ? null : list.get(0);
	}

}
