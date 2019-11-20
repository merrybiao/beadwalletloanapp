/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.loansupermarket.service;

import com.waterelephant.entity.BwBorrower;
import com.waterelephant.service.BaseCommonService;
import com.waterelephant.utils.AppResponseResult;

/**
 * 
 * 
 * Module: 
 * 
 * MarketBorrowerService.java 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface MarketBorrowerService extends BaseCommonService<BwBorrower, Long> {

	/**
	 * 
	 * @param phone
	 * @return
	 */
	BwBorrower findByPhone(String phone);

	/**
	 * 
	 * @param phone
	 * @param password
	 * @param loginType
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	AppResponseResult updateLoginOrRegister(String phone, String password, Integer loginType, Integer channel)
			throws Exception;

}