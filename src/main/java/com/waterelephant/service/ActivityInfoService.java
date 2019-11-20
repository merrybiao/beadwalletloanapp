package com.waterelephant.service;

import java.util.Date;

import com.waterelephant.entity.ActivityInfo;

/**
 * 活动基本信息service
 * 
 * 
 * Module:
 * 
 * ActivityInfoService.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface ActivityInfoService {

	/**
	 * 查询活动基本信息
	 * 
	 * @param entity
	 */
	ActivityInfo queryActivityInfo(ActivityInfo entity);

	/**
	 * 查询最新的活动基本信息
	 * 
	 * @param entity
	 * @return
	 */
	ActivityInfo queryLastActivityInfo(ActivityInfo entity);

	/**
	 * 获取失效时间
	 * 
	 * @param startDate 开始计算时间，根据这个时间计算最后失效时间
	 * @param queryActivityInfo
	 * @return 返回失效时间
	 */
	Date getExpiryTime(Date startDate, ActivityInfo queryActivityInfo);

	/**
	 * 查询所有的活动
	 * 
	 * @return
	 */
	Object findListActivityInfo();

	/**
	 * 根据活动实体查询活动
	 * 
	 * @param activityInfo
	 * @return
	 */
	ActivityInfo findActivityInfoByEntry(ActivityInfo activityInfo);

}
