package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfMobileInfo;
import com.waterelephant.mapper.BwMfMobileInfoMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfMobileInfoService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfMobileInfoServiceImpl extends BaseService<BwMfMobileInfo, Long> implements BwMfMobileInfoService {

	
	@Autowired
	private BwMfMobileInfoMapper bwMfMobileInfoMapper;

	@Override
	public void saveMobileInfo(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfMobileInfo.class);
		
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		
		this.bwMfMobileInfoMapper.deleteByExample(example);
		
		if(!reportData.containsKey("mobile_info")) return;
		
		JSONObject mobile_info = reportData.getJSONObject("mobile_info");
		
		if(null == mobile_info || mobile_info.isEmpty()) return;
		
		BwMfMobileInfo record = mobile_info.toJavaObject(BwMfMobileInfo.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		
		this.bwMfMobileInfoMapper.insert(record);
	}
	
	
}
