package com.waterelephant.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwMfBehaviorScore;
import com.waterelephant.mapper.BwMfBehaviorScoreMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwMfBehaviorScoreService;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwMfBehaviorScoreServiceImpl extends BaseService<BwMfBehaviorScore, Long>
		implements BwMfBehaviorScoreService {

	@Autowired
	private BwMfBehaviorScoreMapper bwMfBehaviorScoreMapper;

	@Override
	public void saveBehaviorScore(String taskId, Long orderId, JSONObject reportData) {
		
		Example example = new Example(BwMfBehaviorScore.class);
		example.createCriteria().andEqualTo("taskId", taskId).andEqualTo("orderId", orderId);
		this.bwMfBehaviorScoreMapper.deleteByExample(example);
		
		if(!reportData.containsKey("behavior_score")) return;
		
		JSONObject behavior_score = reportData.getJSONObject("behavior_score");
		
		if(null == behavior_score || behavior_score.isEmpty()) return;
		
		BwMfBehaviorScore record = behavior_score.toJavaObject(BwMfBehaviorScore.class);
		record.setTaskId(taskId);
		record.setOrderId(orderId);
		record.setCreateTime(Calendar.getInstance().getTime());
		record.setUpdateTime(Calendar.getInstance().getTime());
		this.bwMfBehaviorScoreMapper.insert(record);
	}
	
	
	
	
}
