package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfContactManyheadsAnalysis;
import com.waterelephant.mapper.BwMfContactManyheadsAnalysisMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfContactManyheadsAnalysisService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfContactManyheadsAnalysisServiceImpl extends BaseService<BwMfContactManyheadsAnalysis, Long>
		implements BwMfContactManyheadsAnalysisService {

	@Autowired
	private BwMfContactManyheadsAnalysisMapper bwMfContactManyheadsAnalysisMapper;

	@Override
	public void saveContactManyheadsAnalysis(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfContactManyheadsAnalysis.class);
		
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		
		this.bwMfContactManyheadsAnalysisMapper.deleteByExample(example);
		
		if(!reportData.containsKey("contact_manyheads_analysis")) return;
		
		JSONObject contact_manyheads_analysis = reportData.getJSONObject("contact_manyheads_analysis");
		
		if(null == contact_manyheads_analysis || contact_manyheads_analysis.isEmpty()) return;
		
		BwMfContactManyheadsAnalysis record = contact_manyheads_analysis.toJavaObject(BwMfContactManyheadsAnalysis.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		this.bwMfContactManyheadsAnalysisMapper.insert(record);
	}
}
