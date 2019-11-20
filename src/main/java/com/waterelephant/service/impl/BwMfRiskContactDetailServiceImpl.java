package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfRiskContactDetail;
import com.waterelephant.mapper.BwMfRiskContactDetailMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfRiskContactDetailService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfRiskContactDetailServiceImpl extends BaseService<BwMfRiskContactDetail, Long>
		implements BwMfRiskContactDetailService {

	@Autowired
	private BwMfRiskContactDetailMapper bwMfRiskContactDetailMapper;

	@Override
	public void saveRiskContactDetail(String taskId, Long orderId, JSONObject reportData) {
		Example example = new Example(BwMfRiskContactDetail.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfRiskContactDetailMapper.deleteByExample(example);
		
		if(!reportData.containsKey("risk_contact_detail")) return;
		
		JSONArray risk_contact_detailList = reportData.getJSONArray("risk_contact_detail");
		
		if(null == risk_contact_detailList || risk_contact_detailList.isEmpty()) return;
		
		List<BwMfRiskContactDetail> list = new ArrayList<>();
		
		for(int i=0;i <risk_contact_detailList.size();i++) {
			JSONObject risk_contact_detail = risk_contact_detailList.getJSONObject(i);
			if(risk_contact_detail.isEmpty()) continue;
			BwMfRiskContactDetail record = risk_contact_detail.toJavaObject(BwMfRiskContactDetail.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			list.add(record);
		}
		if(!list.isEmpty()) this.bwMfRiskContactDetailMapper.insertList(list);
	}
	
	
}
