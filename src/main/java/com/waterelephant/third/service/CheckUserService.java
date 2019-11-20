/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.third.service;

/**
 * 统一对外接口 （code0091）
 * 
 * Module:
 * 
 * CheckUserService.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface CheckUserService {
	/**
	 * 公共方法 - 判断是否是老用户
	 * 
	 * @param idCard
	 * @param name
	 * @return
	 * @author zhangchong
	 * 
	 */
	boolean isOldUser(String idCard, String name, String mobile);

	/**
	 * 公共方法 - 判断是否黑名单
	 * 
	 * @param idCard
	 * @param name
	 * @return boolean
	 * @author zhangchong
	 * 
	 */
	boolean isBlackUser(String idCard, String name, String mobile);

	/**
	 * 公共方法 - 判断是否有进行中的订单
	 * 
	 * @param idCard
	 * @return boolean
	 * @author zhangchong
	 * 
	 */
	boolean isPocessingOrder(String idCard, String name, String mobile);

	/**
	 * 公共方法 - 是否被拒
	 * 
	 * @param idCard
	 * @param name
	 * @return boolean
	 * @author zhangchong
	 */
	boolean isRejectRecord(String idCard, String name, String mobile);
}
