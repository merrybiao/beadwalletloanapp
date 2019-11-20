//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.XygjResponse;
//
///**
// * 
// * <p>Title: XinYongGuanJiaService</p>  
// * <p>Description: 信用管家</p>
// * @since JDK 1.8  
// * @author xiongfeng
// */
//public interface XinYongGuanJiaService {
//
//	/**
//	 * 4.1.1存量用户检验接口
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	XygjResponse checkUser(long sessionId, String appId, String aesRequest);
//
//	/**
//	 * 4.1.2进件推送接口
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	XygjResponse saveOrder(long sessionId, String appId, String aesRequest);
//
//	/**
//	 * 4.1.3银行卡预绑卡接口
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	XygjResponse saveBindCardReady(long sessionId, String appId, String aesRequest);
//
//	/**
//	 * 4.1.4银行卡确认绑卡接口
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	XygjResponse saveBindCardSure(long sessionId, String appId, String aesRequest);
//
//	/**
//	 * 4.1.5 主动还款接口
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	XygjResponse updateActiveRepayment(long sessionId, String appId, String aesRequest);
//
//	/**
//	 * 4.1.6 拉取还款计划接口
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	XygjResponse getRepayPlan(long sessionId, String appId, String aesRequest);
//
//	/**
//	 * 4.1.7 拉取合同接口
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	XygjResponse getContract(long sessionId, String appId, String aesRequest);
//
//	/**
//	 * 拉取订单状态接口
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	XygjResponse getOrderStatus(long sessionId, String appId, String aesRequest);
//
//	/**
//	 * 拉取还款结果接口
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	XygjResponse getRepaymentResult(long sessionId, String appId, String aesRequest);
//	
//	
//	/**
//	 * 贷款试算接口
//	 * @param sessionId
//	 * @param appId
//	 * @param aesRequest
//	 * @return
//	 */
//	XygjResponse loanCalculation(long sessionId, String appId, String aesRequest);
//
//}
