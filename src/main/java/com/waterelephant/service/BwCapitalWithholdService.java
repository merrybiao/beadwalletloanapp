package com.waterelephant.service;

import com.waterelephant.entity.BwCapitalWithhold;

/**
 * 资方扣款推送记录
 * 
 * @author 崔雄健
 * @date 2017年4月10日
 * @description
 */
public interface BwCapitalWithholdService {

	/**
	 * @author 崔雄健
	 * @date 2017年4月6日
	 * @description 保存资方订单信息
	 * @param
	 * @return
	 */
	void save(BwCapitalWithhold bwCapitalWithhold);

	/**
	 * 
	 * @author 崔雄健
	 * @date 2017年4月26日
	 * @description 查询已存在推送信息
	 * @param
	 * @return
	 */
	BwCapitalWithhold queryBwCapitalWithhold(Long orderId, Long id);

	/**
	 * 
	 * @author 崔雄健
	 * @date 2017年4月26日
	 * @description 更新推送信息
	 * @param
	 * @return
	 */
	int updateBwCapitalWithhold(BwCapitalWithhold bwCapitalWithhold);

	BwCapitalWithhold queryBwCapitalWithhold(Long id);
	
	BwCapitalWithhold queryBwCapitalWithhold(String otherOrderNo);
	
}
