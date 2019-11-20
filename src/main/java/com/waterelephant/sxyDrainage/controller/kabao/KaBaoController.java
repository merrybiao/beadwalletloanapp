//
//package com.waterelephant.sxyDrainage.controller.kabao;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.sxyDrainage.entity.kabao.KaBaoRequest;
//import com.waterelephant.sxyDrainage.entity.kabao.KaBaoResponse;
//import com.waterelephant.sxyDrainage.service.KaBaoService;
//
///**
// * 
// * @ClassName: KaBaoController  
// * @Description:   
// * @author liwanliang  
// *
// */
//@Controller
//public class KaBaoController {
//
//	private Logger logger = LoggerFactory.getLogger(KaBaoController.class);
//	
//	@Autowired
//	private KaBaoService kaBaoService;
//	
//	/**
//	 * @Title: checkUser  
//	 * @Description:  4.1.1 用户过滤
//	 * @param @param kaBaoRequest
//	 * @return KaBaoResponse      
//	 * @throws
//	 */
//	@RequestMapping("/sxyDrainage/interface/kabao/checkUser.do")
//	@ResponseBody
//	public KaBaoResponse checkUser(KaBaoRequest kaBaoRequest){
//		long sessionId = System.currentTimeMillis();
//		//51卡宝响应数据对象
//		KaBaoResponse kaBaoResponse = new KaBaoResponse();
//		logger.info(sessionId + ":51卡宝>>>开始准入用户检验接口>>>" + JSON.toJSONString(kaBaoRequest));
//		try {
//			KaBaoResponse result = checkRequestParam(kaBaoRequest, sessionId);
//			if(null == result){
//				logger.info("51卡宝>>>准入用户检验接口请求接口入参为空>>>");
//				return result;
//			}
//			
//			String request = kaBaoRequest.getRequest();
//			String appId = kaBaoRequest.getAppId();
//			//处理用户过滤
//			kaBaoResponse =kaBaoService.checkUser(sessionId, appId, request);
//			
//			logger.info(sessionId + ":结束执行51卡宝>>>准入用户检验接口>>>" + JSON.toJSONString(kaBaoResponse));
//			return kaBaoResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":51卡宝>>>执行准入用户检验接口Controller异常:", e);
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_FAILERR);
//			kaBaoResponse.setMsg("准入用户检验接口异常，请稍后重试");
//			return kaBaoResponse;
//		}
//		
//	}
//	
//	/**
//	 * @Title: pushOrder  
//	 * @Description:  4.1.4 进件推送接口
//	 * @param @param kaBaoRequest
//	 * @return KaBaoResponse      
//	 * @throws
//	 */
//	@RequestMapping("/sxyDrainage/interface/kabao/pushOrder.do")
//	@ResponseBody
//	public KaBaoResponse pushOrder(KaBaoRequest kaBaoRequest){
//		long sessionId = System.currentTimeMillis();
//		KaBaoResponse kaBaoResponse = new KaBaoResponse();
//		//logger.info(sessionId + ":51卡宝>>>开始进件推送接口>>>" + JSON.toJSONString(kaBaoRequest));
//		logger.info(sessionId + ":51卡宝>>>开始进件推送接口>>>");
//		try {
//			KaBaoResponse result = checkRequestParam(kaBaoRequest, sessionId);
//			if(null == result){
//				logger.info("51卡宝>>>进件推送接口请求接口入参为空>>>");
//				return result;
//			}
//			
//			String request = kaBaoRequest.getRequest();
//			String appId = kaBaoRequest.getAppId();
//			//处理进件推送
//			kaBaoResponse = kaBaoService.savePushOrder(sessionId, appId, request);
//			logger.info(sessionId + ":51卡宝>>>结束进件推送接口>>>" + JSON.toJSONString(kaBaoResponse));
//		} catch (Exception e) {
//			logger.error(sessionId + ":51卡宝>>>执行进件推送接口Controller异常:", e);
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_FAILERR);
//			kaBaoResponse.setMsg("进件推送接口异常，请稍后重试");
//		}
//		return kaBaoResponse;
//	}
//	
//	
//
//	/**
//	 * 
//	 * @Title: bindCardReady  
//	 * @Description:  4.1.2 银行卡预绑卡接口（API）
//	 * @param @param kaBaoRequest
//	 * @return KaBaoResponse      
//	 * @throws
//	 */
//	@RequestMapping("/sxyDrainage/interface/kabao/bindCardReady.do")
//	@ResponseBody
//	public KaBaoResponse bindCardReady(KaBaoRequest kaBaoRequest){
//		long sessionId = System.currentTimeMillis();
//		KaBaoResponse kaBaoResponse = new KaBaoResponse();
//		logger.info(sessionId + ":51卡宝>>>预绑卡接口>>>" + JSON.toJSONString(kaBaoRequest));
//		try {
//			KaBaoResponse result = checkRequestParam(kaBaoRequest, sessionId);
//			if(null == result){
//				logger.info("51卡宝>>>预绑卡接口请求接口入参为空>>>");
//				return result;
//			}
//			
//			String request = kaBaoRequest.getRequest();
//			String appId = kaBaoRequest.getAppId();
//			//处理预绑卡
//			kaBaoResponse = kaBaoService.saveBindCardReady(sessionId, appId, request);
//			logger.info(sessionId + ":51卡宝>>>结束预绑卡接口>>>" + JSON.toJSONString(kaBaoResponse));
//		} catch (Exception e) {
//			logger.error(sessionId + ":51卡宝>>>执行预绑卡接口Controller异常:", e);
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_FAILERR);
//			kaBaoResponse.setMsg("银行卡预绑卡接口异常，请稍后重试");
//		}
//		return kaBaoResponse;
//	}
//	
//	/**
//	 * @Title: bindCardSure  
//	 * @Description:  4.1.3 银行卡确认绑卡接口（API）
//	 * @param @param kaBaoRequest
//	 * @return KaBaoResponse      
//	 * @throws
//	 */
//	@RequestMapping("/sxyDrainage/interface/kabao/bindCardSure.do")
//	@ResponseBody
//	public KaBaoResponse bindCardSure(KaBaoRequest kaBaoRequest){
//		long sessionId = System.currentTimeMillis();
//		KaBaoResponse kaBaoResponse = new KaBaoResponse();
//		logger.info(sessionId + ":51卡宝>>>确认绑卡接口>>>" + JSON.toJSONString(kaBaoRequest));
//		try {
//			KaBaoResponse result = checkRequestParam(kaBaoRequest, sessionId);
//			if(null == result){
//				logger.info("51卡宝>>>确认绑卡接口请求接口入参为空>>>");
//				return result;
//			}
//			
//			String request = kaBaoRequest.getRequest();
//			String appId = kaBaoRequest.getAppId();
//			//处理确认绑卡
//			kaBaoResponse = kaBaoService.saveBindCardSure(sessionId, appId, request);
//			logger.info(sessionId + ":51卡宝>>>结束确认绑卡接口>>>" + JSON.toJSONString(kaBaoResponse));
//		} catch (Exception e) {
//			logger.error(sessionId + ":51卡宝>>>执行确认绑卡接口Controller异常:", e);
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_FAILERR);
//			kaBaoResponse.setMsg("银行卡确认绑卡接口异常，请稍后重试");
//		}
//		return kaBaoResponse;
//	}
//	
//		
//	/**
//	 * @Title: repayment  
//	 * @Description:  4.1.6 主动还款接口
//	 * @param @param kaBaoRequest
//	 * @return KaBaoResponse      
//	 * @throws
//	 */
//	@RequestMapping("/sxyDrainage/interface/kabao/repayment.do")
//	@ResponseBody
//	public KaBaoResponse repayment(KaBaoRequest kaBaoRequest){
//		long sessionId = System.currentTimeMillis();
//		KaBaoResponse kaBaoResponse = new KaBaoResponse();
//		logger.info(sessionId + ":51卡宝>>>主动还款接口>>>" + JSON.toJSONString(kaBaoRequest));
//		try {
//			KaBaoResponse result = checkRequestParam(kaBaoRequest, sessionId);
//			if(null == result){
//				logger.info("51卡宝>>>主动还款接口请求接口入参为空>>>");
//				return result;
//			}
//			
//			String request = kaBaoRequest.getRequest();
//			String appId = kaBaoRequest.getAppId();
//			//处理主动还款
//			kaBaoResponse = kaBaoService.updateActiveRepayment(sessionId, appId, request);
//			logger.info(sessionId + ":51卡宝>>>结束主动还款接口>>>" + JSON.toJSONString(kaBaoResponse));
//		} catch (Exception e) {
//			logger.error(sessionId + ":51卡宝>>>执行主动还款接口Controller异常:", e);
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_FAILERR);
//			kaBaoResponse.setMsg("主动还款接口异常，请稍后重试");
//		}
//		return kaBaoResponse;
//	}
//	
//	
//	/**
//	 * @Title: getOrderStatus  
//	 * @Description:  拉取订单状态接口
//	 * @param @param kaBaoRequest
//	 * @return KaBaoResponse      
//	 * @throws
//	 */
//	@RequestMapping("/sxyDrainage/interface/kabao/getOrderStatus.do")
//	@ResponseBody
//	public KaBaoResponse getOrderStatus(KaBaoRequest kaBaoRequest){
//		long sessionId = System.currentTimeMillis();
//		KaBaoResponse kaBaoResponse = new KaBaoResponse();
//		logger.info(sessionId + ":51卡宝>>>拉取订单状态接口>>>" + JSON.toJSONString(kaBaoRequest));
//		try {
//			KaBaoResponse result = checkRequestParam(kaBaoRequest, sessionId);
//			if(null == result){
//				logger.info("51卡宝>>>拉取订单状态接口请求接口入参为空>>>");
//				return result;
//			}
//			
//			String request = kaBaoRequest.getRequest();
//			String appId = kaBaoRequest.getAppId();
//			//处理拉取订单状态
//			kaBaoResponse = kaBaoService.getOrderStatus(sessionId, appId, request);
//			logger.info(sessionId + ":51卡宝>>>结束拉取订单状态接口>>>" + JSON.toJSONString(kaBaoResponse));
//			return kaBaoResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":51卡宝>>>拉取订单状态接口Controller异常:", e);
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_FAILERR);
//			kaBaoResponse.setMsg("拉取订单状态异常，请稍后重试");
//			return kaBaoResponse;
//		}
//	}
//	
//	
//	//=========================检验参数====================================================
//	private KaBaoResponse checkRequestParam(KaBaoRequest kaBaoRequest, Long sessionId){
//		KaBaoResponse kaBaoResponse = new KaBaoResponse();
//		if(null == kaBaoRequest){
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//			kaBaoResponse.setMsg("请求参数为空");
//			logger.info(sessionId + ":51卡宝>>>【KaBaoController.checkRequestParam】校验入参方法中kaBaoRequest为空>>>" + JSON.toJSONString(kaBaoResponse));
//			return kaBaoResponse;
//		}
//		//获取请求参数
//		String request = kaBaoRequest.getRequest();
//		String appId = kaBaoRequest.getAppId();
//		if (StringUtils.isBlank(appId) || StringUtils.isBlank(request)) {
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//			kaBaoResponse.setMsg("请求参数为空");
//			logger.info(sessionId + ":51卡宝>>>校验入参方法中appId或request>>>" + JSON.toJSONString(kaBaoResponse));
//			return kaBaoResponse;
//		}
//		kaBaoResponse.setCode(KaBaoResponse.CHECK_RESULT);
//		return kaBaoResponse;
//	}
//}
