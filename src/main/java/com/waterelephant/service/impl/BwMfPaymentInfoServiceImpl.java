package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfPaymentInfo;
import com.waterelephant.mapper.BwMfPaymentInfoMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfPaymentInfoService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfPaymentInfoServiceImpl extends BaseService<BwMfPaymentInfo, Long> implements BwMfPaymentInfoService {

	@Autowired
	private BwMfPaymentInfoMapper bwMfPaymentInfoMapper;
	
	@Override
	public void savePaymentInfo(String taskId, Long orderId, JSONObject taskData) {
		Example example = new Example(BwMfPaymentInfo.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfPaymentInfoMapper.deleteByExample(example);
		if(taskData.containsKey("payment_info")) {
			JSONArray paymentInfoList = taskData.getJSONArray("payment_info");
			if(null != paymentInfoList && !paymentInfoList.isEmpty()) {
				List<BwMfPaymentInfo> list = new ArrayList<>();
				
				for(int f=0;f<paymentInfoList.size();f++) {
					JSONObject payment_info =  paymentInfoList.getJSONObject(f);
					if(payment_info.isEmpty()) continue;
					BwMfPaymentInfo bwMfPaymentInfo = payment_info.toJavaObject(BwMfPaymentInfo.class);
					bwMfPaymentInfo.setTaskId(taskId);
					bwMfPaymentInfo.setOrderId(orderId);
					bwMfPaymentInfo.setCreateTime(Calendar.getInstance().getTime());
					bwMfPaymentInfo.setUpdateTime(Calendar.getInstance().getTime());
					list.add(bwMfPaymentInfo);
				}
				if(!list.isEmpty())this.bwMfPaymentInfoMapper.insertList(list);
			}
		}
	}

}
