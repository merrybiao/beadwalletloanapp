package com.waterelephant.service;

import com.waterelephant.entity.BwWinpayOrder;

/**
 * 支付宝账单信息
 * 
 * @author 崔雄健
 * @date 2017年5月10日
 * @description
 */
public interface BwWinpayOrderService {

	void save(BwWinpayOrder bwWinpayOrder);

	BwWinpayOrder queryBwWinpayOrder(Long orderId);

	int update(BwWinpayOrder bwWinpayOrder);

	BwWinpayOrder queryBwWinpayOrderById(Long id);

	BwWinpayOrder queryBwWinpayOrderType(Long orderId, int type, String money);

	BwWinpayOrder queryBwWinpayOrderById(Long id, int type);

}
