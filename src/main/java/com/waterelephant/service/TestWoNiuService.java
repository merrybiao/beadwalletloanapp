package com.waterelephant.service;

import com.waterelephant.entity.WoNiuOrder;

/**
 * 
 * 
 * Module: 
 * 
 * BwWoNiuService.java 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <蜗牛聚财>
 */
public interface TestWoNiuService {
	/**
	 * 查找用户基本信息
	 * @param woNiuOrder
	 * @return
	 */
	WoNiuOrder findWoNiuByAttr(WoNiuOrder woNiuOrder);
	
	/**
	 * 根据水象订单号查询订单
	 * @param thirdNo
	 * @return
	 */
	WoNiuOrder findWoNiuByThirdNo(String thirdNo);
	
	/**
	 * 添加蜗牛订单
	 * @param woNiuOrder
	 * @return
	 */
	boolean addWoNiuOrder(WoNiuOrder woNiuOrder);
	
	/**
	 * 删除蜗牛订单
	 * @param woNiuOrder
	 * @return
	 */
	int deleteWoNiuOrder(WoNiuOrder woNiuOrder);
	
	/**
	 * 修改
	 * @param woNiuOrder
	 * @return
	 */
	int updateWoNiuOrderSelective(WoNiuOrder woNiuOrder);
}
