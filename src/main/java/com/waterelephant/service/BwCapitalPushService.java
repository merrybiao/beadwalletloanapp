package com.waterelephant.service;

import com.waterelephant.entity.BwCapitalPush;

/**
 * 资方订单推送记录
 * 
 * @author 崔雄健
 * @date 2017年4月10日
 * @description
 */
public interface BwCapitalPushService {

	/**
	 * @author 崔雄健
	 * @date 2017年4月6日
	 * @description 保存资方订单信息
	 * @param
	 * @return
	 */
	void save(BwCapitalPush bwCapitalPush);

	/**
	 * 
	 * @author 崔雄健
	 * @date 2017年4月26日
	 * @description 查询已存在推送信息
	 * @param
	 * @return
	 */
	BwCapitalPush queryBwCapitalPush(Long orderId);

	/**
	 * 
	 * @author 崔雄健
	 * @date 2017年4月26日
	 * @description 更新推送信息
	 * @param
	 * @return
	 */
	int updateBwCapitalPush(BwCapitalPush bwCapitalPush);

	BwCapitalPush queryBwCapitalPush(Long orderId, int capitalId);
}
