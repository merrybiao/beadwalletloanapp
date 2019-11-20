package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfHomeTelDetail;
import com.waterelephant.mapper.BwMfHomeTelDetailMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfHomeTelDetailService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfHomeTelDetailServiceImpl extends BaseService<BwMfHomeTelDetail, Long>
		implements BwMfHomeTelDetailService {

	
	@Autowired
	private BwMfHomeTelDetailMapper bwMfHomeTelDetailMapper;

	@Override
	public void saveHomeTelDetail(String taskId, Long orderId, JSONObject reportData) {
		Example example = new Example(BwMfHomeTelDetail.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfHomeTelDetailMapper.deleteByExample(example);
		
		if(!reportData.containsKey("home_tel_detail")) return;
		
		JSONObject home_tel_detail = reportData.getJSONObject("home_tel_detail");
		
		if(null == home_tel_detail || home_tel_detail.isEmpty()) return;
		
		BwMfHomeTelDetail record = home_tel_detail.toJavaObject(BwMfHomeTelDetail.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		this.bwMfHomeTelDetailMapper.insert(record);
	}
	
}
