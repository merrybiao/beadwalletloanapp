package com.waterelephant.service;

/**
 * 
 * Module:
 * 
 * TestAnalogService.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface TestAnalogService {

	/**
	 * 绑定银行卡
	 * 
	 * @param name
	 * @param phone
	 * @param idCard
	 * @param cardNo
	 * @return
	 */
	String updateAndSignBank(String name, String phone, String idCard, String cardNo);

	/**
	 * 认证
	 * 
	 * @param orderId
	 * @param authType
	 * @return
	 */
	String updateAndOrderAuth(String orderId, String authType);

	/**
	 * 更新工单状态为认证通过，即待签约
	 * 
	 * @param phone
	 * @param orderIdStr
	 * @return
	 */
	String updateAndAuditSuccess(String phone, String orderIdStr);

	/**
	 * 删除借款人
	 * 
	 * @param phone
	 * @return
	 */
	String deleteBorrower(String phone);

	/**
	 * 放款
	 * 
	 * @param orderId
	 * @param repayDate
	 * @return
	 */
	String updateAndAnalogLoan(String orderId, String repayDate);

	/**
	 * 生成逾期记录
	 * 
	 * @param orderId
	 * @return
	 */
	String updateAndAnalogOverDue(String orderId);

	/**
	 * 清除工单相关信息，并更改工单状态为9
	 * 
	 * @param orderId
	 * @return
	 */
	String updateAndClearOrder(String orderId);

	/**
	 * 删除工单信息及关联表记录
	 * 
	 * @param phone
	 * @param orderIdStr
	 * @return
	 */
	String deleteOrderInfo(String phone, String orderIdStr);

}