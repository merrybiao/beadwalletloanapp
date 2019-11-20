package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfSmsInfo;
import com.waterelephant.mapper.BwMfSmsInfoMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfSmsInfoService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfSmsInfoServiceImpl extends BaseService<BwMfSmsInfo, Long> implements BwMfSmsInfoService {
	
	@Autowired
	private BwMfSmsInfoMapper bwMfSmsInfoMapper;

	@Override
	public void saveSmsInfo(String taskId, Long orderId, JSONObject taskData) {
		Example example = new Example(BwMfSmsInfo.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfSmsInfoMapper.deleteByExample(example);
		if(taskData.containsKey("sms_info")) {
			JSONArray smsInfoList = taskData.getJSONArray("sms_info");
			if(null != smsInfoList && !smsInfoList.isEmpty()) {
				List<BwMfSmsInfo> list = new ArrayList<>();
				for(int e=0;e<smsInfoList.size();e++) {
					JSONObject sms_info =  smsInfoList.getJSONObject(e);
					if(sms_info.isEmpty()) continue;
					BwMfSmsInfo bwMfSmsInfo = sms_info.toJavaObject(BwMfSmsInfo.class);
					bwMfSmsInfo.setTaskId(taskId);
					bwMfSmsInfo.setOrderId(orderId);
					bwMfSmsInfo.setCreateTime(Calendar.getInstance().getTime());
					bwMfSmsInfo.setUpdateTime(Calendar.getInstance().getTime());
					list.add(bwMfSmsInfo);
				}
				if(!list.isEmpty())this.bwMfSmsInfoMapper.insertList(list);
			}
		}
	}

}
