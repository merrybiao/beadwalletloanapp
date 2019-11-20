/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.activity.service;

import com.beadwallet.entity.lianlian.NotifyNotice;
import com.waterelephant.entity.ActivityDiscountDistribute;
import com.waterelephant.entity.ActivityInfo;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwPlatformRecord;

/**
 * 活动处理业务类
 * 
 * Module:
 * 
 * AcvitiService.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface ActivityService {
	// ======================================王坤==================================================
	/**
	 * 连连支付使用优惠券
	 * 
	 * @param bwOrder 工单实体
	 * @param bwPlatformRecord 交易流水号实体
	 * @param isDelRedis 是否删除redis
	 * @return NotifyNotice 返回交易信息
	 */
	public NotifyNotice addParticipationActivity(BwOrder bwOrder, BwPlatformRecord bwPlatformRecord,
			boolean isDelRedis);

	/**
	 * 更新使用最大优惠券
	 * 
	 * @param bwOrder
	 * @param repayMoney 计划还款金额
	 */
	void updateAndUseCoupon(BwOrder bwOrder, String repayMoney);

	/**
	 * 获取 isUseCoupon 是否支付 借贷人划款计划金额和借贷人id 返回借贷人实际支付金额
	 * 
	 * @param isUseCoupon 是否使用优惠券的标示
	 * @param amount 借贷人计划还款金额
	 * @param borrowerId 借贷人ID
	 * @return String 借贷人实际还款金额
	 */
	public String getRealityTrandeAmount(String isUseCoupon, String amount, Long borrowerId);

	/**
	 * 新手活动派发
	 * 
	 * @param order
	 */
	public void addActivityDistribution(BwOrder order);

	// ======================================王坤==================================================
	/**
	 * 根据活动Id查询活动
	 * 
	 * @param activityId
	 * @return
	 */
	public Object getActivity(ActivityInfo entity);

	/**
	 * 我的邀请
	 * 
	 * @param entity
	 * @return
	 */
	public Object getMyInvitation(ActivityDiscountDistribute entity);

	/**
	 * 查询用户所有劵
	 * 
	 * @return
	 */
	public Object getCoupon(ActivityDiscountDistribute entity);

	/**
	 * 查询用户最大能使用卷
	 * 
	 * @param entity
	 * @return
	 */
	public Object getMaxCoupon(ActivityDiscountDistribute entity);

	/**
	 * 给邀请好友并满足派发优惠券条件的老用户派发券(每日凌晨0点执行) <br />
	 * 1.查询最新已结束的邀请好友活动<br />
	 * 2.查询活动对应的优惠信息<br />
	 * 3.统计借款人邀请人数量(需满足派发条件：1.邀请好友数量达标2.要派发的券未收到 3.活动期间邀请好友注册并借款(根据注册时间和借款时间判断) 4.活动已结束)<br />
	 * 4.根据活动优惠信息和统计的邀请人数量获取可派发给用户最高金额的优惠券<br />
	 * 5.添加活动优惠券派发记录<br />
	 */
	public void updateAndDistributeToInviteFriends();

	public BwBorrower addInitBorrow();

	public void pendingRepayment();

	/**
	 * 根据借款人id查询出所有的优惠券 以最大优惠券和获取时间进行牌序
	 * 
	 * @param borrowId
	 * @return
	 */
	public Object findListCoupon(Integer borrowId);

}
