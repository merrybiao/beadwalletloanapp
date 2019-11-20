package com.waterelephant.service;

import java.util.Map;

import com.waterelephant.entity.BwCapitalOrder;

/**
 * 资方订单
 * 
 * @author 崔雄健
 * @date 2017年4月10日
 * @description
 */
public interface BwCapitalOrderService {

	/**
	 * @author 崔雄健
	 * @date 2017年4月6日
	 * @description 保存资方订单信息
	 * @param
	 * @return
	 */
	void save(BwCapitalOrder bwCapitalOrder);

	/**
	 * 
	 * @author 崔雄健
	 * @date 2017年4月26日
	 * @description 查询所有需要主动获取的工单
	 * @param
	 * @return
	 */
	int updateBwCapitalOrder(int type, String status, String message, String orderNo);

	BwCapitalOrder queryBwCapitalOrder(Long orderId, int capitalType);

	BwCapitalOrder queryBwCapitalOrder(String capitalNo, int capitalId);

	Map queryBwCapitalOrderOrderId(String caiptalNo, int capitalId);

	BwCapitalOrder queryBwCapitalOrderByNo(String orderNo, int capitalId);

	void update(BwCapitalOrder bwCapitalOrder);

}
