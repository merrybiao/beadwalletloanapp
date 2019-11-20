package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.ActivityParticipate;
import com.waterelephant.service.ActivityParticipateService;
import com.waterelephant.service.BaseService;

@Service
public class ActivityParticipateServiceImpl extends BaseService<ActivityParticipate, Long>
		implements ActivityParticipateService {

	@Override
	public void addParticipate(ActivityParticipate activityParticipate) {
		this.mapper.insert(activityParticipate);
	}

	@Override
	public boolean isAlreadyParticipate(Integer borrowId, Integer activityId) {
		String sql = "select * from activity_participate where borrow_id = " + borrowId + " and activity_id = "
				+ activityId;
		ActivityParticipate selectOne = this.sqlMapper.selectOne(sql, ActivityParticipate.class);
		if (null != selectOne && null != selectOne.getUaId()) {
			return true;
		}
		return false;

	}

}
