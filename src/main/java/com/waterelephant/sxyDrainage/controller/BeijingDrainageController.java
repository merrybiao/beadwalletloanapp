//package com.waterelephant.sxyDrainage.controller;
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
//import com.waterelephant.sxyDrainage.entity.beijingDrainage.BeijingDrainageRequest;
//import com.waterelephant.sxyDrainage.entity.beijingDrainage.BeijingDrainageResponse;
//import com.waterelephant.sxyDrainage.service.BeijingDrainageService;
//
///**
// * 
// * Module: BeijingDrainageController.java
// * 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description:
// */
//@Controller
//public class BeijingDrainageController {
//	private Logger logger = LoggerFactory.getLogger(BeijingDrainageController.class);
//	@Autowired
//	BeijingDrainageService beijingDrainageService;
//
//	/**
//	 * 存量用户检验接口 过滤
//	 * 
//	 * @param beijingDrainageRequest
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/third/cloud/interface/checkUser.do")
//	public BeijingDrainageResponse checkUser(BeijingDrainageRequest beijingDrainageRequest) {
//		long sessionId = System.currentTimeMillis();
//		BeijingDrainageResponse beijingDrainageResponse = new BeijingDrainageResponse();
//		logger.info(sessionId + "：开始存量用户检验接口>>>" + JSON.toJSONString(beijingDrainageRequest));
//		try {
//			// 第一步：检查参数
//			if (beijingDrainageRequest == null) {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingDrainageResponse.setMsg("请求参数为空");
//				logger.info(sessionId + "：存量用户检验接口>>>" + JSON.toJSONString(beijingDrainageResponse));
//				return beijingDrainageResponse;
//			}
//			String request = beijingDrainageRequest.getRequest();
//			String channelCode = beijingDrainageRequest.getAppId();
//			if (StringUtils.isBlank(channelCode) || StringUtils.isBlank(request)) {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingDrainageResponse.setMsg("请求参数为空");
//				logger.info(sessionId + "：存量用户检验接口>>>" + JSON.toJSONString(beijingDrainageResponse));
//				return beijingDrainageResponse;
//			}
//			// 第二步：处理业务
//			beijingDrainageResponse = beijingDrainageService.checkUser(sessionId, beijingDrainageRequest);
//		} catch (Exception e) {
//			logger.error(sessionId + "执行存量用户检验接口异常:", e);
//			beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_NETERROR);
//			beijingDrainageResponse.setMsg("系统异常");
//		}
//		logger.info(sessionId + "结束执行存量用户检验接口>>>" + JSON.toJSONString(beijingDrainageResponse));
//		return beijingDrainageResponse;
//	}
//
//	/**
//	 * 进件推送接口
//	 * 
//	 * @param beijingDrainageRequest
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/third/cloud/interface/pushOrder.do")
//	public BeijingDrainageResponse pushOrder(BeijingDrainageRequest beijingDrainageRequest) {
//		long sessionId = System.currentTimeMillis();
//		BeijingDrainageResponse beijingDrainageResponse = new BeijingDrainageResponse();
//		logger.info(sessionId + "：开始进件推送接口>>>" + JSON.toJSONString(beijingDrainageRequest));
//		try {
//			// 第一步：检查参数
//			if (beijingDrainageRequest == null) {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingDrainageResponse.setMsg("请求参数为空");
//				logger.info(sessionId + "：进件推送接口>>>" + JSON.toJSONString(beijingDrainageResponse));
//				return beijingDrainageResponse;
//			}
//			String request = beijingDrainageRequest.getRequest();
//			String channelCode = beijingDrainageRequest.getAppId();
//			if (StringUtils.isBlank(channelCode) || StringUtils.isBlank(request)) {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingDrainageResponse.setMsg("请求参数为空");
//				logger.info(sessionId + "：进件推送接口>>>" + JSON.toJSONString(beijingDrainageResponse));
//				return beijingDrainageResponse;
//			}
//			// 第二步：处理业务
//			beijingDrainageResponse = beijingDrainageService.savePushOrder(sessionId, beijingDrainageRequest);
//		} catch (Exception e) {
//			logger.error(sessionId + "执行进件推送接口异常:", e);
//			beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_NETERROR);
//			beijingDrainageResponse.setMsg("系统异常");
//		}
//		logger.info(sessionId + "结束进件推送接口>>>" + JSON.toJSONString(beijingDrainageResponse));
//		return beijingDrainageResponse;
//	}
//
//	/**
//	 * 绑卡
//	 * 
//	 * @param beijingDrainageRequest
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/third/cloud/interface/bindCard.do")
//	public BeijingDrainageResponse bindCard(BeijingDrainageRequest beijingDrainageRequest) {
//		long sessionId = System.currentTimeMillis();
//		BeijingDrainageResponse beijingDrainageResponse = new BeijingDrainageResponse();
//		logger.info(sessionId + "：开始 绑卡接口>>>" + JSON.toJSONString(beijingDrainageRequest));
//		try {
//			// 第一步：检查参数
//			if (beijingDrainageRequest == null) {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingDrainageResponse.setMsg("请求参数为空");
//				logger.info(sessionId + "： 绑卡接口>>>" + JSON.toJSONString(beijingDrainageResponse));
//				return beijingDrainageResponse;
//			}
//			String request = beijingDrainageRequest.getRequest();
//			String channelCode = beijingDrainageRequest.getAppId();
//			if (StringUtils.isBlank(channelCode) || StringUtils.isBlank(request)) {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingDrainageResponse.setMsg("请求参数为空");
//				logger.info(sessionId + "：绑卡接口>>>" + JSON.toJSONString(beijingDrainageResponse));
//				return beijingDrainageResponse;
//			}
//			// 第二步：处理业务
//			beijingDrainageResponse = beijingDrainageService.saveBindCard(sessionId, beijingDrainageRequest);
//		} catch (Exception e) {
//			logger.error(sessionId + "执行绑卡接口异常:", e);
//			beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_NETERROR);
//			beijingDrainageResponse.setMsg("系统异常");
//		}
//		logger.info(sessionId + "结束绑卡接口>>>" + JSON.toJSONString(beijingDrainageResponse));
//		return beijingDrainageResponse;
//	}
//
//	// /**
//	// * 结果推送
//	// *
//	// * @param beijingDrainageRequest
//	// * @return
//	// */
//	// @ResponseBody
//	// @RequestMapping("/third/cloud/interface/pushResult.do")
//	// public BeijingDrainageResponse pushResult(BeijingDrainageRequest beijingDrainageRequest) {
//	// long sessionId = System.currentTimeMillis();
//	// BeijingDrainageResponse beijingDrainageResponse = new BeijingDrainageResponse();
//	// logger.info(sessionId + "：开始 绑卡接口>>>" + JSON.toJSONString(beijingDrainageRequest));
//	// try {
//	// // 第一步：检查参数
//	// if (beijingDrainageRequest == null) {
//	// beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//	// beijingDrainageResponse.setMsg("请求参数为空");
//	// logger.info(sessionId + "： 绑卡接口>>>" + JSON.toJSONString(beijingDrainageResponse));
//	// return beijingDrainageResponse;
//	// }
//	// String request = beijingDrainageRequest.getRequest();
//	// String channelCode = beijingDrainageRequest.getAppId();
//	// if (StringUtils.isBlank(channelCode) || StringUtils.isBlank(request)) {
//	// beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//	// beijingDrainageResponse.setMsg("请求参数为空");
//	// logger.info(sessionId + "：绑卡接口>>>" + JSON.toJSONString(beijingDrainageResponse));
//	// return beijingDrainageResponse;
//	// }
//	// // 第二步：处理业务
//	// beijingDrainageResponse = beijingDrainageService.pushResult(sessionId, beijingDrainageRequest);
//	// } catch (Exception e) {
//	// logger.error(sessionId + "执行绑卡接口异常:", e);
//	// beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_NETERROR);
//	// beijingDrainageResponse.setMsg("系统异常");
//	// }
//	// logger.info(sessionId + "结束绑卡接口>>>" + JSON.toJSONString(beijingDrainageRequest));
//	// return beijingDrainageResponse;
//	// }
//}
