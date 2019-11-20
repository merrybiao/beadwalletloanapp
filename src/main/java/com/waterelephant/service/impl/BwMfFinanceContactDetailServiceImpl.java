package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfFinanceContactDetail;
import com.waterelephant.mapper.BwMfFinanceContactDetailMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfFinanceContactDetailService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfFinanceContactDetailServiceImpl extends BaseService<BwMfFinanceContactDetail, Long>
		implements BwMfFinanceContactDetailService {

	
	@Autowired
	private BwMfFinanceContactDetailMapper bwMfFinanceContactDetailMapper;

	@Override
	public void saveFinanceContactDetail(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfFinanceContactDetail.class);
		
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		
		this.bwMfFinanceContactDetailMapper.deleteByExample(example);
		//不存在该节点，则返回
		if(!reportData.containsKey("finance_contact_detail")) return;
		
		JSONArray finance_contact_detailList = reportData.getJSONArray("finance_contact_detail");
		
		if(null == finance_contact_detailList || finance_contact_detailList.isEmpty()) return;
		
		List<BwMfFinanceContactDetail> list = new ArrayList<>();
		
		for(int i =0;i< finance_contact_detailList.size();i++) {
			JSONObject finance_contact_detail = finance_contact_detailList.getJSONObject(i);
			if(finance_contact_detail.isEmpty()) continue;
			BwMfFinanceContactDetail record = finance_contact_detail.toJavaObject(BwMfFinanceContactDetail.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			list.add(record);
		}
		
		if(!list.isEmpty()) this.bwMfFinanceContactDetailMapper.insertList(list);
		
	}
	
}
