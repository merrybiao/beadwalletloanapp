/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service;

import java.util.Map;

import com.waterelephant.dto.RepaymentDto;
import com.waterelephant.utils.AppResponseResult;

/**
 * 还款派发业务处理
 * 
 * Module:
 * 
 * RepaymentDistributeService.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface RepaymentDistributeService {

	/**
	 * 参数验证
	 * 
	 * @param paramMap
	 * @return
	 */
	AppResponseResult paramVerfy(Map<String, String> paramMap, RepaymentDto dto);

	/**
	 * 判断是否能还款（1、是否进行银行卡签约，2、工单是否正在处理中）
	 * 
	 * @param borrowerId
	 * @param orderId
	 * @param type
	 * @return
	 */
	AppResponseResult canRepayment(RepaymentDto dto);

	/**
	 * 还款派发
	 * 
	 * @param paramMap
	 * @return
	 */
	AppResponseResult repaymentDistribute(RepaymentDto dto);
}
