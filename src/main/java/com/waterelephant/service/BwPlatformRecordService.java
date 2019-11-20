package com.waterelephant.service;

import java.util.Date;

import com.waterelephant.entity.BwPlatformRecord;

public interface BwPlatformRecordService {

	public int saveBwPlatFormRecord(BwPlatformRecord bwPlatformRecord);

	/**
	 * 根据tradeNo保存或更新
	 * 
	 * @param bwPlatformRecord
	 * @return
	 */
	int saveOrUpdateByTradeNo(BwPlatformRecord bwPlatformRecord);

	/**
	 * 
	 * @param orderId
	 * @param tradeNo
	 * @param tradeAmount
	 * @param tradeType
	 * @param outAccount
	 * @param outName
	 * @param inAccount
	 * @param inName
	 * @param tradeTime
	 * @param tradeRemark
	 * @param tradeChannel
	 * @return
	 */
	BwPlatformRecord saveBwPlatFormRecordByPayNotify(Long orderId, String tradeNo, Double tradeAmount,
			Integer tradeType, String outAccount, String outName, String inAccount, String inName, Date tradeTime,
			String tradeRemark, Integer tradeChannel);

	/**
	 * 查询已还总额
	 * 
	 * @param orderId
	 * @return
	 */
	Double getAlreadyTotal(Long orderId);

	void deleteBwPlatformRecord(Long orderId);

	int getBwPlatformRecordCount(BwPlatformRecord entity);

}
