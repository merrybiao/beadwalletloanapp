package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfFamilyInfo;
import com.waterelephant.mapper.BwMfFamilyInfoMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfFamilyInfoService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfFamilyInfoServiceImpl extends BaseService<BwMfFamilyInfo, Long> implements BwMfFamilyInfoService {

	@Autowired
	private BwMfFamilyInfoMapper bwMfFamilyInfoMapper;
	
	@Override
	public void saveFamilyInfo(String taskId, Long orderId, JSONObject taskData) {
		Example example = new Example(BwMfFamilyInfo.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		bwMfFamilyInfoMapper.deleteByExample(example);
		if(taskData.containsKey("family_info")) {
			JSONArray familyInfoList = taskData.getJSONArray("family_info");
			if(null != familyInfoList && !familyInfoList.isEmpty()) {
				List<BwMfFamilyInfo> list = new ArrayList<>();
				for(int c = 0;c< familyInfoList.size();c++) {
					JSONObject family_info = familyInfoList.getJSONObject(c);
					if(family_info.isEmpty()) continue;
					BwMfFamilyInfo bwMfFamilyInfo = family_info.toJavaObject(BwMfFamilyInfo.class);
					bwMfFamilyInfo.setTaskId(taskId);
					bwMfFamilyInfo.setOrderId(orderId);
					bwMfFamilyInfo.setCreateTime(Calendar.getInstance().getTime());
					bwMfFamilyInfo.setUpdateTime(Calendar.getInstance().getTime());
					list.add(bwMfFamilyInfo);
				}
				if(!list.isEmpty())bwMfFamilyInfoMapper.insertList(list);
			}
		}
	}
}
