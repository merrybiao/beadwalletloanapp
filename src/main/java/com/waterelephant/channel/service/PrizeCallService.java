package com.waterelephant.channel.service;

import java.util.Map;

import com.waterelephant.channel.entity.ActivityDrawRecord;
import com.waterelephant.utils.AppResponseResult;

public interface PrizeCallService {

	/**
	 * 进入抽奖页面
	 */
	public AppResponseResult saveIntoPrizeView(Map<String, Object> params);

	/**
	 * 抽奖
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public AppResponseResult saveLuckDraw(Map<String, Object> params);

	/**
	 * 填写联系人和电话
	 */
	public AppResponseResult updateContacts(Map<String, Object> params);

	/**
	 * 查询我的中奖记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public AppResponseResult getMyWinningRecord(Map<String, Object> params);

	/**
	 * 确认收货
	 * 
	 * @param id 抽奖记录表id
	 */
	public int updateConfirmReceipt(ActivityDrawRecord activityDrawRecord);

}
