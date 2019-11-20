package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.dataSource.DataSource;
import com.waterelephant.dataSource.DataSourceHolderManager;
import com.waterelephant.entity.BwMfBillInfo;
import com.waterelephant.entity.BwMfBillInfoRecord;
import com.waterelephant.mapper.BwMfBillInfoMapper;
import com.waterelephant.mapper.BwMfBillInfoRecordMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfBillInfoService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfBillInfoServiceImpl extends BaseService<BwMfBillInfo, Long> implements BwMfBillInfoService {
	
	@Autowired
	private BwMfBillInfoMapper bwMfBillInfoMapper;
	
	@Autowired
	private BwMfBillInfoRecordMapper bwMfBillInfoRecordMapper;
	
	@Override
	public void saveBillInfo(String taskId, Long orderId, JSONObject taskData) {DataSourceHolderManager.set(DataSource.MASTER_NEW);
		Example example = new Example(BwMfBillInfo.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		bwMfBillInfoMapper.deleteByExample(example);
		example = new Example(BwMfBillInfoRecord.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		bwMfBillInfoRecordMapper.deleteByExample(example);
		if(taskData.containsKey("bill_info")) {
			JSONArray billInfoList = taskData.getJSONArray("bill_info");
			
			if(null != billInfoList && !billInfoList.isEmpty()) {
				for(int a =0;a<billInfoList.size();a++) {
					JSONObject bill_info = billInfoList.getJSONObject(a);
					if(bill_info.isEmpty())continue;
					BwMfBillInfo bwMfBillInfo = bill_info.toJavaObject(BwMfBillInfo.class);
					bwMfBillInfo.setTaskId(taskId);
					bwMfBillInfo.setOrderId(orderId);
					bwMfBillInfo.setCreateTime(Calendar.getInstance().getTime());
					bwMfBillInfo.setUpdateTime(Calendar.getInstance().getTime());
					bwMfBillInfoMapper.insert(bwMfBillInfo);
					
					JSONArray billRecordList = bill_info.getJSONArray("bill_record");
					if(null != billRecordList && !billRecordList.isEmpty()) {
						List<BwMfBillInfoRecord> list = new ArrayList<>();
						for(int b =0;b < billRecordList.size();b++ ) {
							JSONObject bill_record = billRecordList.getJSONObject(b);
							if(bill_record.isEmpty()) continue;
							BwMfBillInfoRecord bwMfBillInfoRecord = bill_record.toJavaObject(BwMfBillInfoRecord.class);
							bwMfBillInfoRecord.setTaskId(taskId);
							bwMfBillInfoRecord.setOrderId(orderId);
							bwMfBillInfoRecord.setBillInfoId(bwMfBillInfo.getId());
							bwMfBillInfoRecord.setCreateTime(Calendar.getInstance().getTime());
							bwMfBillInfoRecord.setUpdateTime(Calendar.getInstance().getTime());
							list.add(bwMfBillInfoRecord);
						}
						if(!list.isEmpty())bwMfBillInfoRecordMapper.insertList(list);
					}
				}
			}
		}
	}

}
