package com.waterelephant.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.entity.BwOrder;

public interface OrderAndBlacklistService {

	/**
	 * 查询首页显示内容
	 * 
	 * @param bwId 用户ID
	 * @return
	 */
	Map<String, Object> getOrderAndBlacklist(String bwId);

	/** 查询最新的十条成功放款的工单记录 */
	List<BwOrder> getTopTenBwOrder();
}
