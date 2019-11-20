package com.waterelephant.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.entity.ActivityDiscountDistribute;
import com.waterelephant.entity.BwRepaymentPlan;

/**
 * 活动派发信息service
 * 
 * 
 * Module:
 * 
 * ActivityDiscountDistribute.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface ActivityDiscountDistributeService {

	/**
	 * 新增活动优惠派发
	 * 
	 * @return
	 */
	public void addActivityDiscountDistribute(ActivityDiscountDistribute activityDiscountDistribute);

	/**
	 * 修改活动优惠派发表
	 * 
	 * @return
	 */
	public void updateActivityDiscountDistribute(ActivityDiscountDistribute activityDiscountDistribute);

	/**
	 * 查询活动优惠派发List params Map 条件查询
	 * 
	 * @return
	 */
	public List<ActivityDiscountDistribute> queryActivityDiscountDistribute(Map<String, Object> map);

	/**
	 * 通过借贷人ID查询最新一期还款计划中有效的最大面额的优惠券
	 * 
	 * @param activityDiscountDistribute
	 * @return
	 */
	public ActivityDiscountDistribute findMaxActivityDiscountDistribute(Long borrowId);

	/**
	 * 通过主键ID查询优惠信息派发
	 * 
	 * @param distributeId 主键
	 * @return
	 */
	public ActivityDiscountDistribute findActivityDiscountDistributeById(Long distributeId);

	/**
	 * 通过订单ID找到订单计划
	 */
	public BwRepaymentPlan findBwRepaymentPlanByAttr(Long orderId);

	/**
	 * 查询当期应还款金额
	 */
	public String findNewBwRepaymentPlanMoney(Long orderId);

	/**
	 * 通过借贷人id查询订单优惠派送表
	 * 
	 * @param borrowerId
	 */
	public ActivityDiscountDistribute findDiscountDistributeByBorrowerId(Long borrowerId);

}
