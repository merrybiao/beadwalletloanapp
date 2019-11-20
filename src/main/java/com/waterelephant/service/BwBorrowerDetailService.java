/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service;

import com.waterelephant.entity.BwBorrowerDetail;

/**
 * 
 * Module: 
 * 
 * BwBorrowerDetailService.java 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: 借款人详情service
 */
public interface BwBorrowerDetailService extends BaseCommonService<BwBorrowerDetail, Long> {

	/**
	 * 根据借款人ID查询借款人详情
	 * 
	 * @param borrowerId
	 * @return
	 */
	BwBorrowerDetail selectByBorrowerId(Long borrowerId);

}