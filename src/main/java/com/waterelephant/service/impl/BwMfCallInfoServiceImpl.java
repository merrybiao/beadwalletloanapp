package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfCallInfo;
import com.waterelephant.entity.BwMfCallInfoRecord;
import com.waterelephant.mapper.BwMfCallInfoMapper;
import com.waterelephant.mapper.BwMfCallInfoRecordMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfCallInfoService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfCallInfoServiceImpl extends BaseService<BwMfCallInfo, Long> implements BwMfCallInfoService {

	@Autowired
	private BwMfCallInfoMapper bwMfCallInfoMapper;
	
	@Autowired
	private BwMfCallInfoRecordMapper bwMfCallInfoRecordMapper;
	
	@Override
	public void saveCallInfo(String taskId, Long orderId, JSONObject taskData) {
		//先删除表数据
		Example example = new Example(BwMfCallInfo.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfCallInfoMapper.deleteByExample(example);
		example = new Example(BwMfCallInfoRecord.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfCallInfoRecordMapper.deleteByExample(example);
		//判断节点call_info是否存在
		if(taskData.containsKey("call_info")) {
			JSONArray call_infoList = taskData.getJSONArray("call_info");
			//判断json数组是否为空
			if(null != call_infoList && !call_infoList.isEmpty()) {
				for(int i =0;i<call_infoList.size();i++) {
					JSONObject call_info = call_infoList.getJSONObject(i);
					if(call_info.isEmpty()) continue;
					BwMfCallInfo bwMfCallInfo = call_info.toJavaObject(BwMfCallInfo.class);
					bwMfCallInfo.setTaskId(taskId);
					bwMfCallInfo.setOrderId(orderId);
					bwMfCallInfo.setCreateTime(Calendar.getInstance().getTime());
					bwMfCallInfo.setUpdateTime(Calendar.getInstance().getTime());
					this.bwMfCallInfoMapper.insert(bwMfCallInfo);
					//判断节点call_record是否存在
					if(call_info.containsKey("call_record")) {
						JSONArray call_recordList = call_info.getJSONArray("call_record");
						List<BwMfCallInfoRecord> list = new ArrayList<>();
						for(int j =0;j< call_recordList.size();j++) {
							JSONObject call_record = call_recordList.getJSONObject(j);
							if(call_record.isEmpty()) continue;
							BwMfCallInfoRecord record = call_record.toJavaObject(BwMfCallInfoRecord.class);
							record.setTaskId(taskId);
							record.setOrderId(orderId);
							record.setCallInfoId(bwMfCallInfo.getId());
							record.setCreateTime(Calendar.getInstance().getTime());
							record.setUpdateTime(Calendar.getInstance().getTime());
							list.add(record);
						}
						if(!list.isEmpty())this.bwMfCallInfoRecordMapper.insertList(list);
					}
				}
			}
		}
	}

}
