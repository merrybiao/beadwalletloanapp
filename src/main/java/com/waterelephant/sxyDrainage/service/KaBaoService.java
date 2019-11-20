//
//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.sxyDrainage.entity.kabao.KaBaoResponse;
//
///**
// * 
// * @ClassName: KaBaoService  
// * @Description:   
// * @author liwanliang  
// *
// */
//public interface KaBaoService {
//
//	/**
//	 * @Title: checkUser  
//	 * @Description:  4.1.1 存量用户检验接口
//	 * @param @param sessionId
//	 * @param @param appId
//	 * @param @param request
//	 * @return KaBaoResponse      
//	 * @throws
//	 */
//	KaBaoResponse checkUser(long sessionId, String appId, String request);
//
//	/**
//	 * @Title: pushOrder  
//	 * @Description:  4.1.4 进件推送接口
//	 * @param @param sessionId
//	 * @param @param appId
//	 * @param @param request
//	 * @return KaBaoResponse      
//	 * @throws
//	 */
//	KaBaoResponse savePushOrder(long sessionId, String appId, String request);
//
//	
//	/**
//	 * @Title: saveBindCardReady  
//	 * @Description:  4.1.2 银行卡预绑卡接口（API）
//	 * @param @param sessionId
//	 * @param @param appId
//	 * @param @param request
//	 * @return KaBaoResponse      
//	 * @throws
//	 */
//	KaBaoResponse saveBindCardReady(long sessionId, String appId, String request);
//
//	
//	/**
//	 * @Title: saveBindCardSure  
//	 * @Description:  4.1.3 银行卡确认绑卡接口（API）
//	 * @param @param sessionId
//	 * @param @param appId
//	 * @param @param request
//	 * @return KaBaoResponse      
//	 * @throws
//	 */
//	KaBaoResponse saveBindCardSure(long sessionId, String appId, String request);
//
//	
//	/**
//	 * @Title: updateActiveRepayment  
//	 * @Description: 4.1.6 主动还款接口 
//	 * @param @param sessionId
//	 * @param @param appId
//	 * @param @param request
//	 * @return KaBaoResponse      
//	 * @throws
//	 */
//	KaBaoResponse updateActiveRepayment(long sessionId, String appId, String request);
//
//	
//	/**
//	 * @Title: getRepayPlan  
//	 * @Description:  4.1.7 拉取还款计划接口
//	 * @param @param sessionId
//	 * @param @param appId
//	 * @param @param request
//	 * @return KaBaoResponse      
//	 * @throws
//	 */
//	
//	/**
//	 * @Title: getContract  
//	 * @Description:  4.1.8 拉取合同接口（可选）
//	 * @param @param sessionId
//	 * @param @param appId
//	 * @param @param request
//	 * @return KaBaoResponse      
//	 * @throws
//	 */
//	
//	/**
//	 * @Title: getOrderStatus  
//	 * @Description:  拉取订单状态接口（可选）
//	 * @param @param sessionId
//	 * @param @param appId
//	 * @param @param request
//	 * @return KaBaoResponse      
//	 * @throws
//	 */
//	KaBaoResponse getOrderStatus(long sessionId, String appId, String request);
//	
//}
//
