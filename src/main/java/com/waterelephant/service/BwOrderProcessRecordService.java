/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service;

import java.util.Date;

import com.waterelephant.entity.BwOrderProcessRecord;

/**
 * 
 * Module:
 * 
 * BwOrderProcessRecordService.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: 工单处理记录service
 */
public interface BwOrderProcessRecordService extends BaseCommonService<BwOrderProcessRecord, Long> {

	/**
	 * 根据orderId查询工单处理记录
	 * 
	 * @param orderId
	 * @return
	 */
	BwOrderProcessRecord selectByOrderId(Long orderId);

	/**
	 * 根据工单orderId查询处理记录，存在修改，不存在则新增
	 * 
	 * @param bwOrderProcessRecord
	 * @return
	 */
	BwOrderProcessRecord saveOrUpdateByOrderId(BwOrderProcessRecord bwOrderProcessRecord);

	BwOrderProcessRecord saveOrUpdateByOrderIdRepay(Long orderId, Date loanDate);

}