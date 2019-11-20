package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfEmergencyContactDetail;
import com.waterelephant.mapper.BwMfEmergencyContactDetailMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfEmergencyContactDetailService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfEmergencyContactDetailServiceImpl extends BaseService<BwMfEmergencyContactDetail, Long>
		implements BwMfEmergencyContactDetailService {

	@Autowired
	private BwMfEmergencyContactDetailMapper bwMfEmergencyContactDetailMapper;
	
	@Override
	public void saveEmergencyContactDetail(String taskId, Long orderId, JSONObject reportData) {
		//紧急联系人最多有5个节点
		
		//1、先删除历史数据
		Example example = new Example(BwMfEmergencyContactDetail.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		bwMfEmergencyContactDetailMapper.deleteByExample(example);
		
		List<BwMfEmergencyContactDetail> list = new ArrayList<>();
		//判断节点是否存在且是否有有效值
		if(reportData.containsKey("emergency_contact1_detail") && null != reportData.get("emergency_contact1_detail")) {
			JSONObject json_detail =  reportData.getJSONObject("emergency_contact1_detail");
			BwMfEmergencyContactDetail record = json_detail.toJavaObject(BwMfEmergencyContactDetail.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			list.add(record);//添加到集合
		}
		
		if(reportData.containsKey("emergency_contact2_detail") && null != reportData.get("emergency_contact2_detail")) {
			JSONObject json_detail =  reportData.getJSONObject("emergency_contact2_detail");
			BwMfEmergencyContactDetail record = json_detail.toJavaObject(BwMfEmergencyContactDetail.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			list.add(record);//添加到集合
		}

		if(reportData.containsKey("emergency_contact3_detail") && null != reportData.get("emergency_contact3_detail")) {
			JSONObject json_detail =  reportData.getJSONObject("emergency_contact3_detail");
			BwMfEmergencyContactDetail record = json_detail.toJavaObject(BwMfEmergencyContactDetail.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			list.add(record);//添加到集合
		}
		
		if(reportData.containsKey("emergency_contact4_detail") && null != reportData.get("emergency_contact4_detail")) {
			JSONObject json_detail =  reportData.getJSONObject("emergency_contact4_detail");
			BwMfEmergencyContactDetail record = json_detail.toJavaObject(BwMfEmergencyContactDetail.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			list.add(record);//添加到集合
		}
		
		if(reportData.containsKey("emergency_contact5_detail") && null != reportData.get("emergency_contact5_detail")) {
			JSONObject json_detail =  reportData.getJSONObject("emergency_contact5_detail");
			BwMfEmergencyContactDetail record = json_detail.toJavaObject(BwMfEmergencyContactDetail.class);
			record.setTaskId(taskId);
			record.setOrderId(orderId);
			record.setCreateTime(Calendar.getInstance().getTime());
			record.setUpdateTime(Calendar.getInstance().getTime());
			list.add(record);//添加到集合
		}
		//批量添加到数据库
		if(!list.isEmpty()) this.bwMfEmergencyContactDetailMapper.insertList(list);
	}

	
}
