//package com.waterelephant.sxyDrainage.sina.controller;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.sxyDrainage.sina.entity.SinaRequest;
//import com.waterelephant.sxyDrainage.sina.entity.SinaResponse;
//import com.waterelephant.sxyDrainage.sina.service.SinaService;
//
///**
// * 
// * <p>Title: SinaController</p>  
// * <p>Description: 新浪</p>
// * @since JDK 1.8  
// * @author YANHUI
// */
//@Controller
//@RequestMapping("/sxy/sina")
//public class SinaController {
//	private Logger logger = LoggerFactory.getLogger(SinaController.class);
//	@Autowired
//	private SinaService sinaService;
//	
//	/**
//	 * 4.1.1存量用户检验接口
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/checkUser.do")
//	@ResponseBody
//	public SinaResponse checkUser(@RequestBody SinaRequest sinaRequest){
//		long sessionId = System.currentTimeMillis();
//		// response entity
//		SinaResponse sinaResponse = new SinaResponse();
//		logger.info(sessionId + ":新浪>>>开始存量用户检验接口>>>" + JSON.toJSONString(sinaRequest));
//		try {
//			if(null == sinaRequest){
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>存量用户检验接口sinaRequest为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			// get request data
//			String request = sinaRequest.getRequest();
//			String appId = sinaRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(request)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>存量用户检验接口appId或aesRequest为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			// Step Two:processing data
//			sinaResponse = sinaService.checkUser(sessionId, appId, request);
//			
//			logger.info(sessionId + ":结束执行新浪>>>存量用户检验接口>>>" + JSON.toJSONString(sinaResponse));
//			return sinaResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>执行存量用户检验接口Controller异常:", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("存量用户检验接口异常，请稍后重试");
//			return sinaResponse;
//		}
//		
//	}
//	/**
//	 * 4.1.2银行卡预绑卡接口（API）
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/bindCardReady.do")
//	@ResponseBody
//	public SinaResponse bindCardReady(@RequestBody SinaRequest sinaRequest){
//		long sessionId = System.currentTimeMillis();
//		// response entity
//		SinaResponse sinaResponse = new SinaResponse();
//		logger.info(sessionId + ":新浪>>>开始预绑卡接口>>>" + JSON.toJSONString(sinaRequest));
//		try {
//			// Step One:check param
//			if(null == sinaRequest){
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>预绑卡接口sinaRequest为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			// get request data
//			String request = sinaRequest.getRequest();
//			String appId = sinaRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(request)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>预绑卡接口appId或aesRequest>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			// Step Two:processing data
//			sinaResponse = sinaService.saveBindCardReady(sessionId, appId, request);
//			logger.info(sessionId + ":新浪>>>结束预绑卡接口>>>" + JSON.toJSONString(sinaResponse));
//			return sinaResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>执行预绑卡接口Controller异常:", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("银行卡预绑卡接口异常，请稍后重试");
//			return sinaResponse;
//		}
//		
//	}
//	/**
//	 * 4.1.3银行卡确认绑卡接口（API）
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/bindCardSure.do")
//	@ResponseBody
//	public SinaResponse bindCardSure(@RequestBody SinaRequest sinaRequest){
//		long sessionId = System.currentTimeMillis();
//		// response entity
//		SinaResponse sinaResponse = new SinaResponse();
//		logger.info(sessionId + ":新浪>>>确认绑卡接口>>>" + JSON.toJSONString(sinaRequest));
//		try {
//			// Step One:check param
//			if(null == sinaRequest){
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":洗新浪>>>确认绑卡接口sinaRequest为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			// get request data
//			String request = sinaRequest.getRequest();
//			String appId = sinaRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(request)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>确认绑卡接口appId或aesRequest>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			// Step Two:processing data
//			sinaResponse = sinaService.saveBindCardSure(sessionId, appId, request);
//			logger.info(sessionId + ":新浪>>>结束确认绑卡接口>>>" + JSON.toJSONString(sinaResponse));
//			return sinaResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>执行确认绑卡接口Controller异常:", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("银行卡确认绑卡接口异常，请稍后重试");
//			return sinaResponse;
//		}
//		
//	}
//	/**
//	 * 4.1.2进件推送接口
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/pushOrder.do")
//	@ResponseBody
//	public SinaResponse pushOrder(@RequestBody SinaRequest sinaRequest){
//		long sessionId = System.currentTimeMillis();
//		// response entity
//		SinaResponse sinaResponse = new SinaResponse();
//		logger.info(sessionId + ":新浪>>>开始进件推送接口>>>");
//		try {
//			// Step One:check param
//			if(null == sinaRequest){
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>进件推送接口sinaRequest为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			// get request data
//			String request = sinaRequest.getRequest();
//			String appId = sinaRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(request)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>进件推送接口>>>appId或aesRequest为空" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			// Step Two:processing data
//			sinaResponse = sinaService.savePushOrder(sessionId, appId, request);
//			logger.info(sessionId + ":新浪>>>结束进件推送接口>>>" + JSON.toJSONString(sinaResponse));
//			return sinaResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>执行进件推送接口Controller异常:", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("进件推送接口异常，请稍后重试");
//			return sinaResponse;
//		}
//		
//	}
//	/**
//	 * 4.1.7 主动还款接口
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/repayment.do")
//	@ResponseBody
//	public SinaResponse repayment(@RequestBody SinaRequest sinaRequest){
//		long sessionId = System.currentTimeMillis();
//		SinaResponse sinaResponse = new SinaResponse();
//		logger.info(sessionId + ":新浪>>>主动还款接口>>>" + JSON.toJSONString(sinaRequest));
//		try {
//			if(null == sinaRequest){
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>主动还款接口sinaRequest为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			String request = sinaRequest.getRequest();
//			String appId = sinaRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(request)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>主动还款接口appId或aesRequest为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			sinaResponse = sinaService.updateActiveRepayment(sessionId, appId, request);
//			logger.info(sessionId + ":新浪>>>结束主动还款接口>>>" + JSON.toJSONString(sinaResponse));
//			return sinaResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>执行主动还款接口Controller异常:", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("主动还款接口异常，请稍后重试");
//			return sinaResponse;
//		}
//	}
//	/**
//	 * 4.1.6 拉取订单状态接口
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/getRepayPlan.do")
//	@ResponseBody
//	public SinaResponse getRepayPlan(@RequestBody SinaRequest sinaRequest){
//		long sessionId = System.currentTimeMillis();
//		SinaResponse sinaResponse = new SinaResponse();
//		logger.info(sessionId + ":新浪>>>拉取还款计划接口>>>" + JSON.toJSONString(sinaRequest));
//		try {
//			if(null == sinaRequest){
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>拉取还款计划接口sinaRequest为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			String request = sinaRequest.getRequest();
//			String appId = sinaRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(request)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>拉取还款计划接口appId或request为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			sinaResponse = sinaService.getOrderStatus(sessionId, appId, request);
//			logger.info(sessionId + ":新浪>>>结束拉取还款计划接口>>>" + JSON.toJSONString(sinaResponse));
//			return sinaResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>执行拉取还款计划接口Controller异常:", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("拉取还款计划接口异常，请稍后重试");
//			return sinaResponse;
//		}
//		
//	}
//	/**
//	 * 新浪-贷款试算器接口
//	 *
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/loanCalculation.do")
//	@ResponseBody
//	public SinaResponse loanCalculation(@RequestBody SinaRequest sinaRequest) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入新浪贷款试算接口");
//		SinaResponse sinaResponse = new SinaResponse();
//		logger.info(sessionId + ":新浪>>>贷款试算接口入参>>>" + JSON.toJSONString(sinaRequest));
//		try {
//			// Step One:check param
//			if(null == sinaRequest){
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>贷款试算接口sinaRequest为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			// get request data
//			String request = sinaRequest.getRequest();
//			String appId = sinaRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(request)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>贷款试算接口appId或request为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			sinaResponse = sinaService.loanCalculation(sessionId, appId, request);
//			logger.info(sessionId + ":新浪>>>结束贷款试算接口>>>" + JSON.toJSONString(sinaResponse));
//			return sinaResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>执行贷款试算接口Controller异常:", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("拉取贷款试算接口异常，请稍后重试");
//			return sinaResponse;
//		}
//	}
//	/**
//	 * 新浪-签约提现接口
//	 *
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/getContract.do")
//	@ResponseBody
//	public SinaResponse getContract(@RequestBody SinaRequest sinaRequest) {
//		long sessionId = System.currentTimeMillis();
//		SinaResponse sinaResponse = new SinaResponse();
//		logger.info(sessionId + ":新浪>>>开始签约提现接口>>>" + JSON.toJSONString(sinaRequest));
//		try {
//			if(null == sinaRequest){
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>签约提现接口sinaRequest为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			String request = sinaRequest.getRequest();
//			String appId = sinaRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(request)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>签约提现接口appId或aesRequest为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			sinaResponse = sinaService.updateSignContract(sessionId, appId, request);
//			logger.info(sessionId + ":新浪>>>结束签约提现接口>>>" + JSON.toJSONString(sinaResponse));
//			return sinaResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>执行签约提现接口Controller异常:", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("签约提现接口异常，请稍后重试");
//			return sinaResponse;
//		}
//
//	}
//	
//	
//	/**
//	 * 获取签约信息
//	 * 
//	 * @param sinaRequest
//	 * @return
//	 */
//	@RequestMapping("/getSignInfo.do")
//	@ResponseBody
//	public SinaResponse getSignInfo(@RequestBody SinaRequest sinaRequest){
//		long sessionId = System.currentTimeMillis();
//		SinaResponse response = new SinaResponse();
//		logger.info(sessionId + ":新浪>>>开始获取签约信息接口>>>" + JSON.toJSONString(sinaRequest));
//		try {
//			if(null == sinaRequest){
//				response.setCode(SinaResponse.RESULT_PARMERR);
//				response.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>获取签约信息接口sinaRequest为空>>>");
//				return response;
//			}
//			String request = sinaRequest.getRequest();
//			String appId = sinaRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(request)) {
//				response.setCode(SinaResponse.RESULT_PARMERR);
//				response.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>获取签约信息接口appId或aesRequest为空>>>");
//				return response;
//			}
//			response = sinaService.getSignInfo(request,appId,sessionId);
//		} catch (Exception e) {
//			response.setCode(SinaResponse.RESULT_SIGNERR);
//			response.setMsg("系统异常,请稍后在再试");
//			logger.info(sessionId + ":新浪>>>获取签约信息接口异常>>>",e);
//		}
//		return response;
//	}
//	
//	
//	/**
//	 * 获取合同信息
//	 * 
//	 * @param sinaRequest
//	 * @return
//	 */
//	@RequestMapping("/getContractInfo.do")
//	@ResponseBody
//	public SinaResponse getContractInfo(@RequestBody SinaRequest sinaRequest){
//		long sessionId = System.currentTimeMillis();
//		SinaResponse response = new SinaResponse();
//		logger.info(sessionId + ":新浪>>>开始获取合同信息接口>>>" + JSON.toJSONString(sinaRequest));
//		try {
//			if(null == sinaRequest){
//				response.setCode(SinaResponse.RESULT_PARMERR);
//				response.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>获取合同信息接口sinaRequest为空>>>");
//				return response;
//			}
//			String request = sinaRequest.getRequest();
//			String appId = sinaRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(request)) {
//				response.setCode(SinaResponse.RESULT_PARMERR);
//				response.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>获取合同信息接口appId或aesRequest为空>>>");
//				return response;
//			}
//			response = sinaService.getContractInfo(request,appId,sessionId);
//		} catch (Exception e) {
//			response.setCode(SinaResponse.RESULT_SIGNERR);
//			response.setMsg("系统异常,请稍后在再试");
//			logger.info(sessionId + ":新浪>>>获取合同信息接口异常>>>",e);
//		}
//		return response;
//	}
//	
//}
