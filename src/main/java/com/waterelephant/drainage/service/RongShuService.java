package com.waterelephant.drainage.service;

import com.waterelephant.drainage.entity.rongShu.OrderPushRequest;
import com.waterelephant.drainage.entity.rongShu.RongShuResponse;

/**
 * 榕树（code0087）
 * 
 * 
 * Module:
 * 
 * RongShuService.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <榕树>
 */
public interface RongShuService {

	/**
	 * 榕树 - 5.1 存量用户检验接口（code0087501）
	 * 
	 * @author liuDaodao
	 * @param cid
	 * @param phone
	 * @param name
	 * @return RongShuResponse
	 */
	RongShuResponse userCheck(String sessionId, String cid, String phone, String name);

	/**
	 * 榕树 - 5.2 贷款试算器接口（code0087502）
	 * 
	 * @author liuDaodao
	 * @param loanAmountNum
	 * @param loanPeriodNum
	 * @param periodUnitNum
	 * @return RongShuResponse
	 */
	RongShuResponse loanCalculation(String sessionId,int loanAmountNum, int loanPeriodNum, int periodUnitNum);
	
	
	/***
	 * 保存用户信息
	 * @param orderPush
	 * @return
	 */
	RongShuResponse  saveUserInfo(OrderPushRequest  orderPush);
	
	
	
	/**
	 * 5.13.主动还款试算器接口
	 * @param sessionId
	 * @param orderId
	 * @param periods
	 * @return
	 */
	RongShuResponse figureRepayMent(String sessionId,String orderId,String periods);
	
	
}
