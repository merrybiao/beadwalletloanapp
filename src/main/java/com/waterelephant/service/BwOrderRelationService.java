package com.waterelephant.service;

import com.waterelephant.entity.BwOrderRelation;

/**
 * 工单关系数据接口
 * @author song
 *
 */
public interface BwOrderRelationService {
	
			int addBwOrderRelation(Long orderId);

			BwOrderRelation queryOrderRelation(Long orderId, Long orderStatus);

			int add(BwOrderRelation orderRelation);

			int updateOrderRelation(BwOrderRelation bwOrderRelation);

			BwOrderRelation getOrderRelation(Long orderId, Long orderStatus, Long borrowerId);
}
