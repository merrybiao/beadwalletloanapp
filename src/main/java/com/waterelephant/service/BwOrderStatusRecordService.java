package com.waterelephant.service;

import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderStatusRecord;

/**
 *
 * Module: 记录工单状态表 BwOrderStatusRecordService.java
 * 
 * @author 胡林浩
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface BwOrderStatusRecordService {

	/** 往表里面添加一条工单状态记录 */
	void insertRecord(BwOrder order, String msg, String dialogStyle);

	/** 修改记录 */
	void updateRecord(BwOrderStatusRecord bwOrderStatusRecord);

	/** 根据工单ID最新一期41/0查询工单状态记录 **/
	BwOrderStatusRecord getBwOrderStatusRecordByOrderId(String orderId);

	void deleteBwOrderStatusRecord(Long orderId);
}
