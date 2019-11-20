package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfBaseInfo;
import com.waterelephant.mapper.BwMfBaseInfoMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfBaseInfoService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfBaseInfoServiceImpl extends BaseService<BwMfBaseInfo, Long> implements BwMfBaseInfoService {

	@Autowired
	private BwMfBaseInfoMapper bwMfBaseInfoMapper;
	
	@Override
	public void saveBaseInfo(String taskId, Long orderId, JSONObject taskData) {
		Example example = new Example(BwMfBaseInfo.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfBaseInfoMapper.deleteByExample(example);
		if(taskData.containsKey("base_info")) {
			JSONObject base_info = taskData.getJSONObject("base_info");
			if(null != base_info && !base_info.isEmpty()) {
				BwMfBaseInfo record = base_info.toJavaObject(BwMfBaseInfo.class);
				record.setTaskId(taskId);
				record.setOrderId(orderId);
				record.setCreateTime(Calendar.getInstance().getTime());
				record.setUpdateTime(Calendar.getInstance().getTime());
				this.bwMfBaseInfoMapper.insert(record);
			}
		}
	}

}
