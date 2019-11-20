package com.waterelephant.service;

import com.waterelephant.entity.ActivityDiscountUseage;

/**
 * 活动优惠使用
 * 
 * 
 * Module:
 * 
 * ActivityDiscountUseageService.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface ActivityDiscountUseageService {

	/**
	 * 添加优惠券使用记录
	 * 
	 * @param activityDiscountUseageService
	 */
	boolean addActivityDiscountUseageService(ActivityDiscountUseage ActivityDiscountUseage);

	/**
	 * 根据工单id查询使用的优惠券额度
	 * 
	 * @param orderId
	 * @return
	 */
	Double getDiscountUseageByRepaymentPlanId(Long orderId);

	void deleteActivityDiscountUseage(Long orderId);

}
