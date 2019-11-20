package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfReportInfo;
import com.waterelephant.mapper.BwMfReportInfoMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfReportInfoService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfReportInfoServiceImpl extends BaseService<BwMfReportInfo, Long> implements BwMfReportInfoService {

	@Autowired
	private BwMfReportInfoMapper bwMfReportInfoMapper;

	@Override
	public void saveReportInfo(String taskId, Long orderId, JSONObject reportData) {

		Example example = new Example(BwMfReportInfo.class);
		example.createCriteria().andEqualTo("taskId",taskId).andEqualTo("orderId", orderId);
		this.bwMfReportInfoMapper.deleteByExample(example);
		
		if(!reportData.containsKey("report_info")) return;
		
		JSONObject report_info = reportData.getJSONObject("report_info");
		
		if(null == report_info || report_info.isEmpty()) return;
		
		BwMfReportInfo record = report_info.toJavaObject(BwMfReportInfo.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		this.bwMfReportInfoMapper.insert(record);
	}
	
	
}
