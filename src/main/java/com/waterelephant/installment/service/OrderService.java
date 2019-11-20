/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.installment.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.entity.BwOrder;
import com.waterelephant.utils.AppResponseResult;

/**
 * 活动处理业务类
 * 
 * Module:
 * 
 * AcvitiService.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface OrderService {

	/**
	 * 根据借款人Id和产品类型查询工单
	 * 
	 * @param borrowerId
	 * @param ProductType
	 * @return
	 */
	public BwOrder getOrderByProductType(Long borrowerId, Integer productType);

	/**
	 * 根据借款人Id查询借款人单期工单和分期工单
	 * 
	 * @param borrowerId
	 * @return
	 */
	/**
	 * 
	 * @param borrowerId
	 * @return
	 */
	public Map<String, Object> saveIndexOrders(Long borrowerId);

	/**
	 * 查询借款列表
	 * 
	 * @param borrowerId
	 * @return
	 */
	public List<Map<String, Object>> getOrderLoanList(Long borrowerId);

	/**
	 * 查询工单列表
	 * 
	 * @param borrowerId
	 * @return
	 */
	public List<Map<String, Object>> getOrderList(Long borrowerId);

	/**
	 * 查询工单分期详情
	 * 
	 * @param borrowerId
	 * @return
	 */
	public AppResponseResult getMultiOrderDetail(Long orderId);

	/**
	 * 查询分期还款
	 * 
	 * @param borrowerId
	 * @return
	 */
	public Map<String, Object> getMultiOrderRepay(Long orderId);

	/**
	 * 查询分期的还款记录
	 * 
	 * @param parseLong
	 * @return
	 */
	public List<Map<String, Object>> getInstallmentPayInfo(Long orderId);

	/**
	 * 判断用户是否有正在审核中的工单
	 * 
	 * @param borrowerId
	 * @return
	 */
	boolean hasInAuditOrder(Long borrowerId);

	/**
	 * 是否正在进行中的工单
	 * 
	 * @param borrowerId
	 * @param productType
	 * @return
	 */
	boolean hasProcessingOrder(Long borrowerId, Integer productType);
	
	public boolean hasStatusIdOrder(Long borrowerId, String statusIdStr, Integer productType);

}
