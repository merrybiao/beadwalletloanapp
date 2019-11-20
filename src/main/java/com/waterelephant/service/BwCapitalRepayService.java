package com.waterelephant.service;

import com.waterelephant.entity.BwCapitalRepay;

/**
 * Module:
 * 
 * BwCapitalRepayService.java
 * 
 * @author 崔雄健
 * @since JDK 1.8
 * @version 1.0
 * @description: 资方还款
 * @date 2018年3月5日
 */
public interface BwCapitalRepayService {

	/**
	 * 
	 * @param bwCapitalPush
	 */
	void save(BwCapitalRepay bwCapitalRepay);

	/**
	 * 
	 * @author 崔雄健
	 * @date 2017年4月26日
	 * @description 查询已存在推送信息
	 * @param
	 * @return
	 */
	BwCapitalRepay queryBwCapitalRepay(Long orderId);

	/**
	 * 
	 * @author 崔雄健
	 * @date 2017年4月26日
	 * @description 更新推送信息
	 * @param
	 * @return
	 */
	int updateBwCapitalRepay(BwCapitalRepay bwCapitalRepay);

	BwCapitalRepay queryBwCapitalRepay(Long orderId, int capitalId);
}
