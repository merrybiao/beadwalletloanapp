package com.waterelephant.service;

import java.util.List;

import com.waterelephant.entity.ActivityDiscountInfo;

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
public interface ActivityDiscountInfoService {

	void add(ActivityDiscountInfo entity);

	void addList(List<ActivityDiscountInfo> list);

	/**
	 * 查询优惠信息
	 */
	List<ActivityDiscountInfo> getActinityDiscountInfo(Integer activityId);

	/**
	 * 查询可发放最大的活动优惠信息
	 * 
	 * @param discountInfoList
	 * @param invitedNumber
	 * @return
	 */
	ActivityDiscountInfo getCanInvitedMaxActinityDiscountInfo(List<ActivityDiscountInfo> discountInfoList,
			Integer invitedNumber);
}
