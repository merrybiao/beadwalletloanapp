package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfDataInfo;
import com.waterelephant.mapper.BwMfDataInfoMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfDataInfoService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfDataInfoServiceImpl extends BaseService<BwMfDataInfo, Long> implements BwMfDataInfoService {

	@Autowired
	private BwMfDataInfoMapper bwMfDataInfoMapper;
	
	@Override
	public void saveDataInfo(String taskId, Long orderId, JSONObject taskData) {
		Example example = new Example(BwMfDataInfo.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		bwMfDataInfoMapper.deleteByExample(example);
		if(taskData.containsKey("data_info")) {
			JSONArray dataInfoList = taskData.getJSONArray("data_info");
			if(null != dataInfoList && !dataInfoList.isEmpty()) {
				List<BwMfDataInfo> list = new ArrayList<>();
				for(int d =0; d< dataInfoList.size();d++) {
					JSONObject data_info = dataInfoList.getJSONObject(d);
					if(data_info.isEmpty()) continue;//节点为空
					BwMfDataInfo bwMfDataInfo = data_info.toJavaObject(BwMfDataInfo.class);
					bwMfDataInfo.setTaskId(taskId);
					bwMfDataInfo.setOrderId(orderId);
					bwMfDataInfo.setCreateTime(Calendar.getInstance().getTime());
					bwMfDataInfo.setUpdateTime(Calendar.getInstance().getTime());
					list.add(bwMfDataInfo);
				}
				if(!list.isEmpty()) bwMfDataInfoMapper.insertList(list);
			}
		}
	}
}
