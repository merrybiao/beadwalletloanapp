package com.waterelephant.drainage.service;

import java.util.Map;

import com.waterelephant.drainage.entity.xianJinCard.XianJinCardCommonRequest;
import com.waterelephant.drainage.entity.xianJinCard.XianJinCardResponse;

/**
 * 
 * Module:
 * 
 * XianJinCardService.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface XianJinCardService {
	/**
	 * 
	 * 用户过滤
	 * 
	 * @param sessionId
	 * @param xianJinCardCommonRequest
	 * @return
	 */
	XianJinCardResponse checkUser(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest);

	/**
	 * 信息推送
	 * 
	 * @param sessionId
	 * @param xianJin360PushOrderRequest
	 * @return
	 */
	XianJinCardResponse savePushOrder(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest);

	/**
	 * 绑卡
	 * 
	 */
	Map<String, String> saveBindCard(long sessionId, Map<String, String> map);

	/**
	 * 获取绑卡信息
	 * 
	 * @param sessionId
	 * @param xianJinCardBindCardRequest
	 * @return
	 */
	XianJinCardResponse getBindCard(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest);

	/**
	 * 签约
	 * 
	 * @param sessionId
	 * @param xianJinCardConfirmLoanRequest
	 * @return
	 */
	XianJinCardResponse updateSignContract(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest);

	/**
	 * 获取合同
	 * 
	 * @param sessionId
	 * @param xianJinCardConfirmLoanRequest
	 * @return
	 */
	XianJinCardResponse getContracts(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest);

	/**
	 * 获取还款计划
	 * 
	 * @param sessionId
	 * @param xianJinCardConfirmLoanRequest
	 * @return
	 */
	XianJinCardResponse getRepayplan(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest);

	/**
	 * 获取订单状态
	 * 
	 * @param sessionId
	 * @param xianJinCardCommonRequest
	 * @return
	 */
	XianJinCardResponse getOrderStatus(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest);

	/**
	 * 试算
	 * 
	 * @param sessionId
	 * @param xianJinCardCommonRequest
	 * @return
	 */
	XianJinCardResponse loanCalculate(long sessionId, XianJinCardCommonRequest xianJinCardCommonRequest);

	// /**
	// * 还款
	// * @param sessionId
	// * @param xianJinCardApplyRepayRequest
	// * @return
	// */
	// XianJinCardResponse applyRepay(long sessionId, XianJinCardApplyRepayRequest xianJinCardApplyRepayRequest);

}
