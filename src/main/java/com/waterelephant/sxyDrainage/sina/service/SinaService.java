//package com.waterelephant.sxyDrainage.sina.service;
//
//import com.waterelephant.sxyDrainage.sina.entity.SinaResponse;
//
///**
// * 
// * <p>
// * Title: SinaService
// * </p>
// * <p>
// * Description: 新浪
// * </p>
// * 
// * @since JDK 1.8
// * @author YANHUI
// */
//public interface SinaService {
//	/**
//	 * 4.1.1用户准入检验接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	SinaResponse checkUser(long sessionId, String appId, String request);
//
//	/**
//	 * 4.1.2银行卡预绑卡接口（API）
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	SinaResponse saveBindCardReady(long sessionId, String appId, String request);
//
//	/**
//	 * 4.1.3银行卡确认绑卡接口（API）
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	SinaResponse saveBindCardSure(long sessionId, String appId, String request);
//
//	/**
//	 * 4.1.4进件推送接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	SinaResponse savePushOrder(long sessionId, String appId, String request);
//	/**
//	 * 4.1.6签约（提现）接口（API）
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	// SinaResponse getContract(long sessionId, String appId, String
//	// aesRequest);
//
//	/**
//	 * 4.1.7 主动还款接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	SinaResponse updateActiveRepayment(long sessionId, String appId, String request);
//
//	/**
//	 * 4.1.7 拉取订单状态接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	SinaResponse getOrderStatus(long sessionId, String appId, String request);
//
//	/**
//	 * 4.1.9 还款试算接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	SinaResponse loanCalculation(long sessionId, String appId, String request);
//
//	/**
//	 * 4.1.9 签约接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//
//	SinaResponse updateSignContract(long sessionId, String appId, String request);
//
//	/**
//	 * 获取签约信息
//	 * @param request
//	 * @param appId
//	 * @param sessionId
//	 * @return
//	 */
//	SinaResponse getSignInfo(String request, String appId, long sessionId);
//
//	/**
//	 * 获取合同信息
//	 * 
//	 * @param request
//	 * @param appId
//	 * @param sessionId
//	 * @return
//	 */
//	SinaResponse getContractInfo(String request, String appId, long sessionId);
//
//}
