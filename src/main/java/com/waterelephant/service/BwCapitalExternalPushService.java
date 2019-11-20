package com.waterelephant.service;

import com.waterelephant.entity.BwCapitalExternalPush;

/**
 * 资方订单推送记录
 * 
 * @author 崔雄健
 * @date 2018年4月16日
 * @description
 */
public interface BwCapitalExternalPushService {

	/**
	 * @author 崔雄健
	 * @date 2018年4月16日
	 * @description 保存资方订单信息
	 * @param
	 * @return
	 */
	void save(BwCapitalExternalPush bwCapitalExternalPush);

	/**
	 * 
	 * @author 崔雄健
	 * @date 2018年4月16日
	 * @description 查询已存在推送信息
	 * @param
	 * @return
	 */
	BwCapitalExternalPush queryBwCapitalExternalPush(String orderNo);

	/**
	 * 
	 * @author 崔雄健
	 * @date 2018年4月16日
	 * @description 更新推送信息
	 * @param
	 * @return
	 */
	int updateBwCapitalExternalPush(BwCapitalExternalPush bwCapitalExternalPush);

	BwCapitalExternalPush queryBwCapitalExternalPush(String orderNo, int capitalId);

	Integer queryBwCapitalExternalPushCount(String orderNo);
}
