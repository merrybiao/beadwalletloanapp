package com.waterelephant.installment.service;

import java.util.List;
import java.util.Map;

public interface AuthInfoService {
	/**
	 * 
	 * 查询工单认证信息(根据借款人borrowerId和 productType)
	 * 
	 * @param borrowerId
	 * @param productType
	 * @return
	 */
	Map<String, Object> findAutherized(Long borrowerId, int productType);

	/**
	 * 根据borrowerId 和 productType 查询用户的类型（） 灰名单返回 是否到期 和 到期时间
	 * 
	 * @param borrowerId
	 * @param productType
	 * @return
	 */
	Map<String, Object> getBorrowerType(Long borrowerId, int productType);

	/**
	 * 查询弹框
	 * 
	 * @param orderId
	 * @return
	 */
	List<Map<String, Object>> getBwOrderStatusRecordByOrderId(String orderId);

	/**
	 * 修改弹框记录状态
	 * 
	 * @param id
	 */
	void updateRecord(Integer id);
}
