package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfAllContactDetail;
import com.waterelephant.mapper.BwMfAllContactDetailMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfAllContactDetailService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfAllContactDetailServiceImpl extends BaseService<BwMfAllContactDetail, Long>
		implements BwMfAllContactDetailService {

	@Autowired
	private BwMfAllContactDetailMapper bwMfAllContactDetailMapper;

	@Override
	public void saveAllContactDetail(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfAllContactDetail.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		
		this.bwMfAllContactDetailMapper.deleteByExample(example);
		
		if(!reportData.containsKey("all_contact_detail")) return;
		
		JSONArray all_contact_detailList = reportData.getJSONArray("all_contact_detail");
		
		if(null == all_contact_detailList || all_contact_detailList.isEmpty()) return;
		
		List<BwMfAllContactDetail> list = new ArrayList<>();
		for(int i=0;i<all_contact_detailList.size();i++) {
			JSONObject all_contact_detail = all_contact_detailList.getJSONObject(i);
			
			if(all_contact_detail.isEmpty()) continue;
			
			BwMfAllContactDetail record = all_contact_detail.toJavaObject(BwMfAllContactDetail.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			
			list.add(record);
		}
		if(!list.isEmpty()) this.bwMfAllContactDetailMapper.insertList(list);
	}
	
	
	
}
