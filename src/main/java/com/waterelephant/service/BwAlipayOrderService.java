package com.waterelephant.service;

import com.waterelephant.entity.BwAlipayOrder;

/**
 * 支付宝账单信息
 * 
 * @author 崔雄健
 * @date 2017年5月10日
 * @description
 */
public interface BwAlipayOrderService {

	/**
	 * 
	 * @author 崔雄健
	 * @date 2017年5月10日
	 * @description
	 * @param
	 * @return
	 */
	BwAlipayOrder checkBwAlipayOrder(String alipayNo);

	String getMaxTime();

	void save(BwAlipayOrder bwAlipayOrder);

	void updateType(BwAlipayOrder bwAlipayOrder);

	BwAlipayOrder queryBwAlipayOrder(Long orderId);

	BwAlipayOrder queryBwAlipayOrderById(Long id);

	int update(BwAlipayOrder bwAlipayOrder);

	BwAlipayOrder queryBwAlipayOrderType(Long orderId, int type, String money);

	BwAlipayOrder queryBwAlipayOrderById(Long id, int type);

}
