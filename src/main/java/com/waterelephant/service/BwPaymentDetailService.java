/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service;

import com.waterelephant.entity.BwPaymentDetail;

/**
 * Module:
 * 
 * BwPaymentDetailService.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: 还款或续贷支付明细Service
 */
public interface BwPaymentDetailService {

	/**
	 * 根据还款计划repayId查询
	 * 
	 * @param orderId
	 * @return
	 */
	BwPaymentDetail query(Long repayId);

	/**
	 * 新增或修改，根据repayId是否能查询出数据决定
	 * 
	 * @param bwPaymentDetail
	 * @return
	 */
	BwPaymentDetail saveOrUpdateByRepayId(BwPaymentDetail bwPaymentDetail);

	/**
	 * 支付回调从redis中取数据新增或修改，根据repayId是否能查询出数据决定
	 * 
	 * @param repayId
	 * @param isDelRedis
	 * @return
	 */
	BwPaymentDetail saveOrUpdateByRedis(Long repayId, boolean isDelRedis);

	/**
	 * 删除redis数据
	 * 
	 * @param repayId
	 */
	void deleteRedis(Long repayId);

	/**
	 * 先从redis查询，查询不到再查数据库
	 * 
	 * @param repayId
	 * @return
	 */
	BwPaymentDetail queryByRedisOrDB(Long repayId);

	void deleteBwPaymentDetail(Long repayId);

}