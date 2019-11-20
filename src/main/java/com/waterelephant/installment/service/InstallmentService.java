/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.installment.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * Module:
 * 
 * InstallmentService.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface InstallmentService {

	/**
	 * 查询分期信息
	 * 
	 * @return
	 */
	Map<String, Object> getInstallementInfo(String borrowerId, String productType);

	/**
	 * 查询分期列表
	 * 
	 * @param borrowerId
	 * @return
	 */
	List<Map<String, Object>> getInstallmentList(Long orderId);

}
