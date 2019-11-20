/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service;

import java.util.List;

import com.waterelephant.dto.RepaymentBatch;
import com.waterelephant.dto.RepaymentBatchDetail;
import com.waterelephant.entity.BwOrderRepaymentBatchDetail;
import com.waterelephant.entity.BwPaymentDetail;

/**
 * 分批还款service
 * 
 * Module:
 * 
 * BwOrderRepaymentBatchDetail.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface BwOrderRepaymentBatchDetailService {

	/**
	 * 保存分批还款明细
	 * 
	 * @param entity
	 * @param orderId
	 */
	void saveBwOrderRepaymentBatchDetail(BwOrderRepaymentBatchDetail entity, Long orderId);

	/**
	 * 查询分批还款信息
	 * 
	 * @return
	 */
	RepaymentBatch getRepaymentBatch(Long orderId);

	/**
	 * 查询分批还款明细
	 * 
	 * @param orderId
	 * @return
	 */
	List<RepaymentBatchDetail> getRepaymentBatchDetailList(Long orderId);

	/**
	 * 记录分批还款明细，全额一次还款或最后一批还款则记录支付金额明细
	 * 
	 * @param orderId
	 * @param repayId
	 * @param isDelRedis
	 */
	BwPaymentDetail saveBatchDetailAndRepaymentDetailByRedis(Long orderId, Long repayId, boolean isDelRedis);

	/**
	 * 删除redis中的分批和明细
	 * 
	 * @param orderId
	 * @param repayId
	 */
	void deleteBatchDetailAndRepaymentDetailRedis(Long orderId, Long repayId);

	/**
	 * 获取最近一批次还款
	 * 
	 * @param orderId
	 * @return
	 */
	BwOrderRepaymentBatchDetail getLastBatchDetail(Long orderId);

	Object getBatchInfo(Long orderId);

	Double getBatchDetailTotal(Long orderId);

	/**
	 * 根据orderId和number保存或修改
	 * 
	 * @param bwOrderRepaymentBatchDetail
	 */
	void saveOrUpdateByOrderAndNumber(BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail);

	void deleteBwOrderRepaymentBatchDetail(Long orderId);

	/**
	 * 查询工单是否分批还款
	 * 
	 * @param orderId
	 * @return
	 */
	boolean selectIsBatch(Long orderId);

}
