/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service;

import com.waterelephant.entity.BwIdentityCard2;

/**
 * 身份信息接口类
 * 
 * Module:
 * 
 * IBwIdentityCardService.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface IBwIdentityCardService {
	/**
	 * 
	 * 查询身份证信息
	 * 
	 * @param bwIdentityCard
	 * @return
	 */
	BwIdentityCard2 findBwIdentityCardByAttr(BwIdentityCard2 bwIdentityCard);

	/**
	 * 
	 * 保存身份证信息
	 * 
	 * @param bwIdentityCard
	 * @return
	 */
	int saveBwIdentityCard(BwIdentityCard2 bwIdentityCard);

	/**
	 * 
	 * 更新身份证信息
	 * 
	 * @param bwIdentityCard
	 * @return
	 */
	int updateBwIdentityCard(BwIdentityCard2 bwIdentityCard);
	
	BwIdentityCard2 queryIdentityCardByIdcardNo(String idcardNumber);
}
