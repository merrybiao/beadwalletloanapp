/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.installment.service;

import java.util.Map;

import com.beadwallet.entity.lianlian.NotifyNotice;
import com.beadwallet.entity.lianlian.NotifyResult;
import com.waterelephant.dto.RepaymentDto;
import com.waterelephant.utils.AppResponseResult;

/**
 * 
 * 
 * Module:
 * 
 * AppPaymentwService.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface AppPaymentService {

	/**
	 * 支付
	 * 
	 * @param paramMap
	 * @return
	 */
	AppResponseResult updateAndPay(Map<String, String> paramMap);

	/**
	 * 连连还款回调
	 * 
	 * @param notifyResult
	 * @return
	 * @throws Exception 
	 */
	NotifyNotice updateForLianlianPaymentNotify(NotifyResult notifyResult) throws Exception;

	/**
	 * 模拟请求宝付回调
	 * 
	 * @param orderId
	 * @param transId
	 * @param payAmount
	 */
	void requestBaofooNotifyTest(Long orderId, String transId, double payAmount);

	/**
	 * 口袋用户代扣
	 *
	 * @param repaymentDto
	 * @return
	 */
    AppResponseResult updateAndKouDaiWithhold(RepaymentDto repaymentDto);
}