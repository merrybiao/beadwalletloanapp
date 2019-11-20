package com.waterelephant.service;

import com.waterelephant.entity.BwCreditRecord;

public interface BwCreditRecordService {
	
	/**
	 * 根据授信单ID查询认证记录
	 * @param creditId 授信单ID
	 * @param creditType 认证类型(1：运营商 2：个人信息 3：身份认证 4：芝麻信用 5：社保 6：公积金 7：邮箱 8：淘宝 9：京东  10:单位信息  11：用款确认 12.支付宝 13.滴滴 14.美团 15.学生)
	 * @return
	 */
	BwCreditRecord queryByCreditId(Long creditId,Integer creditType);
	
	/**
	 * 根据工单ID查询认证记录
	 * @param orderId 工单ID
	 * @param creditType 认证类型(1：运营商 2：个人信息 3：身份认证 4：芝麻信用 5：社保 6：公积金 7：邮箱 8：淘宝 9：京东  10:单位信息  11：用款确认 12.支付宝 13.滴滴 14.美团 15.学生)
	 * @return
	 */
	BwCreditRecord queryByOrderId(Long orderId,Integer creditType);
	
	
}
