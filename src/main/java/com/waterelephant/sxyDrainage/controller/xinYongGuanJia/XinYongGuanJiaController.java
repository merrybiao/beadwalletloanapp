//package com.waterelephant.sxyDrainage.controller.xinYongGuanJia;
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
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.XygjRequest;
//import com.waterelephant.sxyDrainage.entity.xinYongGuanJia.XygjResponse;
//import com.waterelephant.sxyDrainage.service.XinYongGuanJiaService;
//
///**
// * 
// * <p>Title: XinYongGuanJiaController</p>  
// * <p>Description: 信用管家</p>
// * @since JDK 1.8  
// * @author xiongfeng
// */
//@Controller
//public class XinYongGuanJiaController {
//	private Logger logger = LoggerFactory.getLogger(XinYongGuanJiaController.class);
//	@Autowired
//	private XinYongGuanJiaService xygjService;
//	
//	/**
//	 * 4.1.1存量用户检验接口
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/sxy/xygj/checkUser.do")
//	@ResponseBody
//	public XygjResponse checkUser(@RequestBody XygjRequest xygjRequest){
//		long sessionId = System.currentTimeMillis();
//		// response entity
//		XygjResponse xygjResponse = new XygjResponse();
//		logger.info(sessionId + ":信用管家>>>开始存量用户检验接口>>>" + JSON.toJSONString(xygjRequest));
//		try {
//			// Step One:check param
//			//xygjResponse = checkRequest(sessionId,xygjRequest);
//			
//			if(null == xygjRequest){
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>存量用户检验接口xygjRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// get request data
//			String aesRequest = xygjRequest.getRequest();
//			String appId = xygjRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(aesRequest)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>存量用户检验接口appId或aesRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// Step Two:processing data
//			xygjResponse = xygjService.checkUser(sessionId, appId, aesRequest);
//			
//			logger.info(sessionId + ":结束执行信用管家>>>存量用户检验接口>>>" + JSON.toJSONString(xygjResponse));
//			return xygjResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":信用管家>>>执行存量用户检验接口Controller异常:", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("存量用户检验接口异常，请稍后重试");
//			return xygjResponse;
//		}
//		
//	}
//	
//	/**
//	 * 4.1.2进件推送接口
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/sxy/xygj/pushOrder.do")
//	@ResponseBody
//	public XygjResponse pushOrder(@RequestBody XygjRequest xygjRequest){
//		long sessionId = System.currentTimeMillis();
//		// response entity
//		XygjResponse xygjResponse = new XygjResponse();
//		logger.info(sessionId + ":信用管家>>>开始进件推送接口>>>" + JSON.toJSONString(xygjRequest));
//		try {
//			// Step One:check param
//			if(null == xygjRequest){
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>进件推送接口xygjRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// get request data
//			String aesRequest = xygjRequest.getRequest();
//			String appId = xygjRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(aesRequest)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>进件推送接口>>>appId或aesRequest为空" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// Step Two:processing data
//			xygjResponse = xygjService.saveOrder(sessionId, appId, aesRequest);
//			logger.info(sessionId + ":信用管家>>>结束进件推送接口>>>" + JSON.toJSONString(xygjResponse));
//			return xygjResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":信用管家>>>执行进件推送接口Controller异常:", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("进件推送接口异常，请稍后重试");
//			return xygjResponse;
//		}
//		
//	}
//	
//	/**
//	 * 4.1.3银行卡预绑卡接口（API）
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/sxy/xygj/bindCardReady.do")
//	@ResponseBody
//	public XygjResponse bindCardReady(@RequestBody XygjRequest xygjRequest){
//		long sessionId = System.currentTimeMillis();
//		// response entity
//		XygjResponse xygjResponse = new XygjResponse();
//		logger.info(sessionId + ":信用管家>>>开始预绑卡接口>>>" + JSON.toJSONString(xygjRequest));
//		try {
//			// Step One:check param
//			if(null == xygjRequest){
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>预绑卡接口xygjRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// get request data
//			String aesRequest = xygjRequest.getRequest();
//			String appId = xygjRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(aesRequest)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>预绑卡接口appId或aesRequest>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// Step Two:processing data
//			xygjResponse = xygjService.saveBindCardReady(sessionId, appId, aesRequest);
//			logger.info(sessionId + ":信用管家>>>结束预绑卡接口>>>" + JSON.toJSONString(xygjResponse));
//			return xygjResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":信用管家>>>执行预绑卡接口Controller异常:", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("银行卡预绑卡接口异常，请稍后重试");
//			return xygjResponse;
//		}
//		
//	}
//	
//	/**
//	 * 4.1.4银行卡确认绑卡接口（API）
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/sxy/xygj/bindCardSure.do")
//	@ResponseBody
//	public XygjResponse bindCardSure(@RequestBody XygjRequest xygjRequest){
//		long sessionId = System.currentTimeMillis();
//		// response entity
//		XygjResponse xygjResponse = new XygjResponse();
//		logger.info(sessionId + ":信用管家>>>确认绑卡接口>>>" + JSON.toJSONString(xygjRequest));
//		try {
//			// Step One:check param
//			if(null == xygjRequest){
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>确认绑卡接口xygjRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// get request data
//			String aesRequest = xygjRequest.getRequest();
//			String appId = xygjRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(aesRequest)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>确认绑卡接口appId或aesRequest>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// Step Two:processing data
//			xygjResponse = xygjService.saveBindCardSure(sessionId, appId, aesRequest);
//			logger.info(sessionId + ":信用管家>>>结束确认绑卡接口>>>" + JSON.toJSONString(xygjResponse));
//			return xygjResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":信用管家>>>执行确认绑卡接口Controller异常:", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("银行卡确认绑卡接口异常，请稍后重试");
//			return xygjResponse;
//		}
//		
//	}
//	
//	/**
//	 * 4.1.5 主动还款接口
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/sxy/xygj/repayment.do")
//	@ResponseBody
//	public XygjResponse repayment(@RequestBody XygjRequest xygjRequest){
//		long sessionId = System.currentTimeMillis();
//		// response entity
//		XygjResponse xygjResponse = new XygjResponse();
//		logger.info(sessionId + ":信用管家>>>开始主动还款接口>>>" + JSON.toJSONString(xygjRequest));
//		try {
//			// Step One:check param
//			if(null == xygjRequest){
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>主动还款接口xygjRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// get request data
//			String aesRequest = xygjRequest.getRequest();
//			String appId = xygjRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(aesRequest)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>主动还款接口appId或aesRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// Step Two:processing data
//			xygjResponse = xygjService.updateActiveRepayment(sessionId, appId, aesRequest);
//			logger.info(sessionId + ":信用管家>>>结束主动还款接口>>>" + JSON.toJSONString(xygjResponse));
//			return xygjResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":信用管家>>>执行主动还款接口Controller异常:", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("主动还款接口异常，请稍后重试");
//			return xygjResponse;
//		}
//		
//	}
//	
//	/**
//	 * 4.1.6 拉取还款计划接口
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/sxy/xygj/getRepayPlan.do")
//	@ResponseBody
//	public XygjResponse getRepayPlan(@RequestBody XygjRequest xygjRequest){
//		long sessionId = System.currentTimeMillis();
//		// response entity
//		XygjResponse xygjResponse = new XygjResponse();
//		logger.info(sessionId + ":信用管家>>>拉取还款计划接口>>>" + JSON.toJSONString(xygjRequest));
//		try {
//			// Step One:check param
//			if(null == xygjRequest){
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取还款计划接口xygjRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// get request data
//			String aesRequest = xygjRequest.getRequest();
//			String appId = xygjRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(aesRequest)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取还款计划接口appId或aesRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// Step Two:processing data
//			xygjResponse = xygjService.getRepayPlan(sessionId, appId, aesRequest);
//			logger.info(sessionId + ":信用管家>>>结束拉取还款计划接口>>>" + JSON.toJSONString(xygjResponse));
//			return xygjResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":信用管家>>>执行拉取还款计划接口Controller异常:", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("拉取还款计划接口异常，请稍后重试");
//			return xygjResponse;
//		}
//		
//	}
//	
//	/**
//	 * 4.1.7 拉取合同接口（可选）
//	 * @param xygjRequest
//	 * @return
//	 */
//	@RequestMapping("/sxy/xygj/getContract.do")
//	@ResponseBody
//	public XygjResponse getContract(@RequestBody XygjRequest xygjRequest){
//		long sessionId = System.currentTimeMillis();
//		// response entity
//		XygjResponse xygjResponse = new XygjResponse();
//		logger.info(sessionId + ":信用管家>>>拉取合同接口>>>" + JSON.toJSONString(xygjRequest));
//		try {
//			// Step One:check param
//			if(null == xygjRequest){
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取合同接口xygjRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// get request data
//			String aesRequest = xygjRequest.getRequest();
//			String appId = xygjRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(aesRequest)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取合同接口appId或aesRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// Step Two:processing data
//			xygjResponse = xygjService.getContract(sessionId, appId, aesRequest);
//			logger.info(sessionId + ":信用管家>>>结束拉取合同接口>>>" + JSON.toJSONString(xygjResponse));
//			return xygjResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":信用管家>>>执行拉取合同接口Controller异常:", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("拉取合同接口异常，请稍后重试");
//			return xygjResponse;
//		}
//		
//	}
//	
//	/**
//	 * 拉取订单状态接口
//	 * @param xygjRequest
//	 * @return
//	 */
//	@RequestMapping("/sxy/xygj/getOrderStatus.do")
//	@ResponseBody
//	public XygjResponse getOrderStatus(@RequestBody XygjRequest xygjRequest){
//		long sessionId = System.currentTimeMillis();
//		// response entity
//		XygjResponse xygjResponse = new XygjResponse();
//		logger.info(sessionId + ":信用管家>>>拉取订单状态接口>>>" + JSON.toJSONString(xygjRequest));
//		try {
//			// Step One:check param
//			if(null == xygjRequest){
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取订单状态接口xygjRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// get request data
//			String aesRequest = xygjRequest.getRequest();
//			String appId = xygjRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(aesRequest)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取订单状态接口appId或aesRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			xygjResponse = xygjService.getOrderStatus(sessionId, appId, aesRequest);
//			logger.info(sessionId + ":信用管家>>>结束拉取订单状态接口>>>" + JSON.toJSONString(xygjResponse));
//			return xygjResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":信用管家>>>执行拉取订单状态接口Controller异常:", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("拉取订单状态接口异常，请稍后重试");
//			return xygjResponse;
//		}
//		
//	}
//	
//	/**
//	 * 拉取还款结果接口
//	 * @param xygjRequest
//	 * @return
//	 */
//	@RequestMapping("/sxy/xygj/getRepaymentResult.do")
//	@ResponseBody
//	public XygjResponse getRepaymentResult(@RequestBody XygjRequest xygjRequest){
//		long sessionId = System.currentTimeMillis();
//		// response entity
//		XygjResponse xygjResponse = new XygjResponse();
//		logger.info(sessionId + ":信用管家>>>拉取还款结果接口>>>" + JSON.toJSONString(xygjRequest));
//		try {
//			// Step One:check param
//			if(null == xygjRequest){
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取还款结果接口xygjRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// get request data
//			String aesRequest = xygjRequest.getRequest();
//			String appId = xygjRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(aesRequest)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>拉取还款结果接口appId或aesRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			xygjResponse = xygjService.getRepaymentResult(sessionId, appId, aesRequest);
//			logger.info(sessionId + ":信用管家>>>结束还款结果接口接口>>>" + JSON.toJSONString(xygjResponse));
//			return xygjResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":信用管家>>>执行还款结果接口Controller异常:", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("拉取还款结果接口异常，请稍后重试");
//			return xygjResponse;
//		}
//		
//	}
//	
//	
//	/**
//	 * 信用管家-贷款试算器接口
//	 *
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/sxy/xygj/loanCalculation.do")
//	@ResponseBody
//	public XygjResponse loanCalculation(@RequestBody XygjRequest xygjRequest) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入信用管家贷款试算接口");
//		XygjResponse xygjResponse = new XygjResponse();
//		logger.info(sessionId + ":信用管家>>>贷款试算接口入参>>>" + JSON.toJSONString(xygjRequest));
//		try {
//			// Step One:check param
//			if(null == xygjRequest){
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>贷款试算接口xygjRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			// get request data
//			String aesRequest = xygjRequest.getRequest();
//			String appId = xygjRequest.getAppId();
//			if (StringUtils.isBlank(appId) || StringUtils.isBlank(aesRequest)) {
//				xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//				xygjResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":信用管家>>>贷款试算接口appId或aesRequest为空>>>" + JSON.toJSONString(xygjResponse));
//				return xygjResponse;
//			}
//			xygjResponse = xygjService.loanCalculation(sessionId, appId, aesRequest);
//			logger.info(sessionId + ":信用管家>>>结束贷款试算接口>>>" + JSON.toJSONString(xygjResponse));
//			return xygjResponse;
//			
//		} catch (Exception e) {
//			logger.error(sessionId + ":信用管家>>>执行贷款试算接口Controller异常:", e);
//			xygjResponse.setCode(XygjResponse.RESULT_FAILERR);
//			xygjResponse.setMsg("拉取贷款试算接口异常，请稍后重试");
//			return xygjResponse;
//		}
//	}
//	
//	
//	
//	// --------------------------------------参数校验--------------------------------------//
///*	public XygjResponse checkRequest(long sessionId, XygjRequest xygjRequest) throws Exception{
//		XygjResponse xygjResponse = new XygjResponse();
//		// Step One:check param
//		if(null == xygjRequest){
//			xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//			xygjResponse.setMsg("请求参数为空");
//			logger.info(sessionId + "：银行卡预绑卡接口>>>" + JSON.toJSONString(xygjResponse));
//			return xygjResponse;
//		}
//		// get request data
//		String aesRequest = xygjRequest.getRequest();
//		String appId = xygjRequest.getAppId();
//		if (StringUtils.isBlank(appId) || StringUtils.isBlank(aesRequest)) {
//			xygjResponse.setCode(XygjResponse.RESULT_PARMERR);
//			xygjResponse.setMsg("请求参数为空");
//			logger.info(sessionId + "：银行卡预绑卡接口>>>" + JSON.toJSONString(xygjResponse));
//			return xygjResponse;
//		}
//		return null;
//	}*/
//}
