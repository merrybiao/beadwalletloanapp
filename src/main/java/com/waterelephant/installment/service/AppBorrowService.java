/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.installment.service;

import java.util.Map;

import com.waterelephant.utils.AppResponseResult;

/**
 * 
 * 
 * Module:
 * 
 * AppBorrowService.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface AppBorrowService {

	/**
	 * 提交借款信息
	 * 
	 * @param paramMap
	 * @return
	 */
	public AppResponseResult updateAndCommitBorrowInfo(Map<String, String> paramMap);

	/**
	 * 
	 * @param paramMap
	 * @return
	 */
	public AppResponseResult queryBorrowInfo(Map<String, String> paramMap);
}