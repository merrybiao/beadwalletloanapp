package com.waterelephant.service;

import com.waterelephant.entity.ActivityParticipate;

/**
 * 活动参加信息
 * 
 * 
 * Module:
 * 
 * ActivityParticipateService.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface ActivityParticipateService {

	/**
	 * 添加活动参与信息
	 * 
	 * @param activityParticipate
	 */
	public void addParticipate(ActivityParticipate activityParticipate);

	/**
	 * 是否已经参加活动
	 * 
	 * @param borrowId
	 * @param activityId
	 * @return
	 */
	public boolean isAlreadyParticipate(Integer borrowId, Integer activityId);

}
