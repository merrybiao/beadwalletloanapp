package com.waterelephant.service;

import java.util.Date;

import com.waterelephant.entity.BwOrderPushInfo;

import tk.mybatis.mapper.entity.Example;

public interface IBwOrderPushInfoService {

	/**
	 * 保存工单推送信息
	 * 
	 * @param bwOrderPushInfo 工单推送信息
	 */
	void saveOrderPushInfo(BwOrderPushInfo bwOrderPushInfo);

	/**
	 * 保存工单推送信息同时更新工单状态
	 * 
	 * @param bwOrderPushInfo 工单推送信息
	 */
	void saveOrderPushInfoAndUpdateOrder(BwOrderPushInfo bwOrderPushInfo);

	/**
	 * 根据条件获得唯一的工单推送信息
	 * 
	 * @param example 查询条件
	 * @return 工单推送信息
	 * @throws Exception 查询到多个信息时抛出异常
	 */
	BwOrderPushInfo getUniqueOrderPushInfoByExample(Example example) throws Exception;

	/**
	 * 更新工单推送信息
	 * 
	 * @param bwOrderPushInfo 工单推送信息
	 */
	void updateOrderPushInfo(BwOrderPushInfo bwOrderPushInfo);

	/**
	 * 保存一起好材料
	 * 
	 * @param orderId 工单编号
	 * @param filename 保存文件名称
	 */
	void saveYiQiHaoAttachment(Long orderId, String filename);

	/**
	 * 根据工单id查询创建时间
	 * 
	 * @param orderId 工单id
	 * @return
	 */
	Date findCreateTimeByOrderId(Long orderId);

	/**
	 * 根据工单id查询放款时间
	 * 
	 * @param orderId 工单id
	 * @return
	 */
	Date findLoanTimeByOrderId(Long orderId);

	BwOrderPushInfo getOrderPushInfo(Long orderId, int channel);
	
	BwOrderPushInfo getOrderPushInfoByOrderId(Long orderId);
	
	
	
}
