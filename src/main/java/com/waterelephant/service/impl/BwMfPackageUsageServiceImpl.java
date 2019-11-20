package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfPackageUsage;
import com.waterelephant.entity.BwMfPackageUsageRecord;
import com.waterelephant.mapper.BwMfPackageUsageMapper;
import com.waterelephant.mapper.BwMfPackageUsageRecordMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfPackageUsageService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfPackageUsageServiceImpl extends BaseService<BwMfPackageUsage, Long> implements BwMfPackageUsageService {
	
	@Autowired
	private BwMfPackageUsageMapper bwMfPackageUsageMapper;
	
	@Autowired
	private BwMfPackageUsageRecordMapper bwMfPackageUsageRecordMapper;

	@Override
	public void savePackageUsage(String taskId, Long orderId,JSONObject taskData) {
		Example example = new Example(BwMfPackageUsage.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfPackageUsageMapper.deleteByExample(example);
		example = new Example(BwMfPackageUsageRecord.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfPackageUsageRecordMapper.deleteByExample(example);
		if(taskData.containsKey("package_usage")) {
			JSONArray package_usageList = taskData.getJSONArray("package_usage");
			if(null != package_usageList && !package_usageList.isEmpty()) {
				
				for(int g=0;g<package_usageList.size();g++) {
					JSONObject package_usage = package_usageList.getJSONObject(g);
					if(package_usage.isEmpty()) continue;
					BwMfPackageUsage bwMfPackageUsage = new BwMfPackageUsage();
					bwMfPackageUsage.setTaskId(taskId);
					bwMfPackageUsage.setOrderId(orderId);
					bwMfPackageUsage.setUsageCycle(package_usage.getString("usage_cycle"));
					bwMfPackageUsage.setCreateTime(Calendar.getInstance().getTime());
					bwMfPackageUsage.setUpdateTime(Calendar.getInstance().getTime());
					this.bwMfPackageUsageMapper.insert(bwMfPackageUsage);
					
					if(package_usage.containsKey("usage_detail")) {
						JSONArray  usage_detailList =  package_usage.getJSONArray("usage_detail");
						if(null != usage_detailList && !usage_detailList.isEmpty()) {
							List<BwMfPackageUsageRecord> list = new ArrayList<>();
							for(int h =0;h<usage_detailList.size();h++) {
								JSONObject usage_detail= usage_detailList.getJSONObject(h);
								if(usage_detail.isEmpty()) continue;
								BwMfPackageUsageRecord bwMfPackageUsageRecord = usage_detail.toJavaObject(BwMfPackageUsageRecord.class);
								bwMfPackageUsageRecord.setTaskId(taskId);
								bwMfPackageUsageRecord.setOrderId(orderId);
								bwMfPackageUsageRecord.setPackageUsageId(bwMfPackageUsage.getId());
								bwMfPackageUsageRecord.setCreateTime(Calendar.getInstance().getTime());
								bwMfPackageUsageRecord.setUpdateTime(Calendar.getInstance().getTime());
								list.add(bwMfPackageUsageRecord);
							}
							if(!list.isEmpty())this.bwMfPackageUsageRecordMapper.insertList(list);
						}
					}
				}
				
			}
		}
	}

}
