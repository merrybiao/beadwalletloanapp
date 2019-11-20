///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.controller.fenqiguanjia;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.StopWatch;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONObject;
//import com.waterelephant.drainage.util.fqgj.ThreadLocalUtil;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.ActiveRepayment;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.CalculateFeeReq;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.ContractUrlReq;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.FenQiGuanJiaResponse;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.FengQiGuanJiaRequest;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.OrderAddInfoReq;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.OrderInfoRequest;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.PreApprovalReq;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.PullApprovalResult;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.PullOrderStatus;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.PullRepaymentPlan;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.UserBindCardData;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.UserCheckData;
//import com.waterelephant.sxyDrainage.service.FenQiGuanJiaService;
//import com.waterelephant.sxyDrainage.utils.fenqiguanjia.FenQiGuanJiaUtils;
//
///**
// * 
// * 
// * Module:
// * 
// * FenQiGuanJiaController.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <分期管家>
// */
//@Controller
//public class FenQiGuanJiaController {
//
//	private Logger logger = Logger.getLogger(FenQiGuanJiaController.class);
//
//	@Autowired
//	private FenQiGuanJiaService fenQiGuanJiaService;
//
//	/**
//	 * 1.1用户过滤接口
//	 */
//	@RequestMapping("/sxyDrainage/fenqiguanjia/checkUser.do")
//	@ResponseBody
//	public FenQiGuanJiaResponse checkUser(@RequestBody FengQiGuanJiaRequest fengQiGuanJiaRequest) {
//		long sessionId = System.currentTimeMillis();
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		String methodName = "FenQiGuanJiaController.checkUser:";
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		try {
//
//			String check = FenQiGuanJiaUtils.checkDataFilter(fengQiGuanJiaRequest);
//			if (StringUtils.isNotBlank(check)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg(check);
//				logger.info("FenQiGuanJiaController.checkUser验证参数失败:" + check);
//				methodEnd(stopWatch, methodName, check, fengQiGuanJiaRequest);
//				return fenQiGuanJiaResponse;
//			}
//			// 请求数据
//			String biz_data = fengQiGuanJiaRequest.getBiz_data();
//			UserCheckData userCheckData = JSONObject.parseObject(biz_data, UserCheckData.class);
//			String user_mobile = userCheckData.getUser_mobile();
//			String id_card = userCheckData.getId_card();
//			String user_name = userCheckData.getUser_name();
//
//			fenQiGuanJiaResponse = fenQiGuanJiaService.checkUser(sessionId, user_name, user_mobile.replace("****", "%"),
//					id_card.replace("****", "%"));
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaController.checkUser出现异常");
//			logger.error("FenQiGuanJiaController.checkUser出现异常", e);
//		}
//		stopWatch.stop();
//		logger.info(methodName + " end, costTime=" + stopWatch.getTotalTimeMillis());
//
//		return fenQiGuanJiaResponse;
//	}
//
//	/**
//	 * 1.2订单信息接收
//	 */
//
//	@RequestMapping("/sxyDrainage/fenqiguanjia/orderPush.do")
//	@ResponseBody
//	public FenQiGuanJiaResponse orderPush(@RequestBody FengQiGuanJiaRequest fengQiGuanJiaRequest) {
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		StopWatch stopWatch = new StopWatch();
//		String methodName = "FenQiGuanJiaController.orderPush";
//		try {
//			long sessionId = System.currentTimeMillis();
//
//			stopWatch.start();
//
//			 //验证参数
//			String check = FenQiGuanJiaUtils.checkDataFilter(fengQiGuanJiaRequest);
//			if (StringUtils.isNotBlank(check)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);// TODO:返回code待定
//				fenQiGuanJiaResponse.setMsg(check);
//				logger.info("FenQiGuanJiaController.orderPush验证参数失败:" + check);
//				methodEnd(stopWatch, methodName, check, fengQiGuanJiaRequest);
//				return fenQiGuanJiaResponse;
//			}
//
//			String biz_data = fengQiGuanJiaRequest.getBiz_data();
//			OrderInfoRequest orderInfoRequest = JSONObject.parseObject(biz_data, OrderInfoRequest.class);
//			fenQiGuanJiaResponse = fenQiGuanJiaService.saveOrder(sessionId, orderInfoRequest);
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaController.orderPush接收数据异常");
//			logger.error("FenQiGuanJiaController.orderPush接收数据异常", e);
//		}
//		stopWatch.stop();
//		logger.info(methodName + " end, costTime=" + stopWatch.getTotalTimeMillis());
//
//		return fenQiGuanJiaResponse;
//	}
//
//	/**
//	 * 1.3用户补充信息
//	 * 
//	 */
//	@RequestMapping("/sxyDrainage/fenqiguanjia/orderAddPush.do")
//	@ResponseBody
//	public FenQiGuanJiaResponse orderAddPushInfo(@RequestBody FengQiGuanJiaRequest fengQiGuanJiaRequest) {
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		StopWatch stopWatch = new StopWatch();
//		String methodName = "FenQiGuanJiaController.orderPush";
//		try {
//			long sessionId = System.currentTimeMillis();
//
//			stopWatch.start();
//
//			 //验证参数
//			String check = FenQiGuanJiaUtils.checkDataFilter(fengQiGuanJiaRequest);
//			if (StringUtils.isNotBlank(check)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg(check);
//				logger.info("FenQiGuanJiaController.orderPush验证参数失败:" + check);
//				methodEnd(stopWatch, methodName, check, fengQiGuanJiaRequest);
//				return fenQiGuanJiaResponse;
//			}
//			String biz_data = fengQiGuanJiaRequest.getBiz_data();
//			OrderAddInfoReq orderAddInfoReq = JSONObject.parseObject(biz_data, OrderAddInfoReq.class);
//			fenQiGuanJiaResponse = fenQiGuanJiaService.saveAddOrder(sessionId, orderAddInfoReq);
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaController.orderAddPushInfo接收数据异常");
//			logger.error("FenQiGuanJiaController.orderAddPushInfo接收数据异常", e);
//		}
//		stopWatch.stop();
//		logger.info(methodName + " end, costTime=" + stopWatch.getTotalTimeMillis());
//
//		return fenQiGuanJiaResponse;
//	}
//
//
//
//
//	/**
//	 * 1.4 用户绑卡接口(新绑卡接口)
//	 *
//	 */
//	@RequestMapping("/sxyDrainage/fenqiguanjia/newBindCard.do")
//	@ResponseBody
//
//	public FenQiGuanJiaResponse saveNewBankCard(@RequestBody FengQiGuanJiaRequest fengQiGuanJiaRequest) {
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		StopWatch stopWatch = new StopWatch();
//		String methodName = "FenQiGuanJiaController.saveNewBankCard";
//		try {
//			long sessionId = System.currentTimeMillis();
//
//			stopWatch.start();
//
//			// 验证参数
//			String check = FenQiGuanJiaUtils.checkDataFilter(fengQiGuanJiaRequest);
//			if (StringUtils.isNotBlank(check)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg(check);
//				logger.info("FenQiGuanJiaController.saveNewBankCard验证参数失败:" + check);
//				methodEnd(stopWatch, methodName, check, fengQiGuanJiaRequest);
//				return fenQiGuanJiaResponse;
//			}
//			String biz_data = fengQiGuanJiaRequest.getBiz_data();
//			UserBindCardData userBindCardData = JSONObject.parseObject(biz_data, UserBindCardData.class);
//
//			fenQiGuanJiaResponse = fenQiGuanJiaService.saveNewBindCard(sessionId,userBindCardData);
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaController.saveNewBankCard接收数据异常");
//			logger.error("FenQiGuanJiaController.saveNewBankCard接收数据异常", e);
//		}
//		stopWatch.stop();
//		logger.info(methodName + " end, costTime=" + stopWatch.getTotalTimeMillis());
//
//		return fenQiGuanJiaResponse;
//
//	}
//
//	/**
//	 * 1.4 用户绑卡接口(新绑卡接口,带验证码)
//	 *
//	 */
//	@RequestMapping("/sxyDrainage/fenqiguanjia/saveCardCode.do")
//	@ResponseBody
//
//	public FenQiGuanJiaResponse saveCardWithCode(@RequestBody FengQiGuanJiaRequest fengQiGuanJiaRequest) {
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		StopWatch stopWatch = new StopWatch();
//		String methodName = "FenQiGuanJiaController.saveCardWithCode";
//		try {
//			long sessionId = System.currentTimeMillis();
//
//			stopWatch.start();
//
//			// 验证参数
//			String check = FenQiGuanJiaUtils.checkDataFilter(fengQiGuanJiaRequest);
//			if (StringUtils.isNotBlank(check)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);// TODO:返回code待定
//				fenQiGuanJiaResponse.setMsg(check);
//				logger.info("FenQiGuanJiaController.saveCardWithCode验证参数失败:" + check);
//				methodEnd(stopWatch, methodName, check, fengQiGuanJiaRequest);
//				return fenQiGuanJiaResponse;
//			}
//			String biz_data = fengQiGuanJiaRequest.getBiz_data();
//			UserBindCardData userBindCardData = JSONObject.parseObject(biz_data, UserBindCardData.class);
//
//			fenQiGuanJiaResponse = fenQiGuanJiaService.saveBindCardWithCode(sessionId,userBindCardData);
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaController.saveCardWithCode接收数据异常");
//			logger.error("FenQiGuanJiaController.saveCardWithCode接收数据异常", e);
//		}
//		stopWatch.stop();
//		logger.info(methodName + " end, costTime=" + stopWatch.getTotalTimeMillis());
//
//		return fenQiGuanJiaResponse;
//
//	}
//
//
//
//
//
//
//	@RequestMapping("/sxyDrainage/fenqiguanjia/pullApproval.do")
//	@ResponseBody
//	public FenQiGuanJiaResponse userPullApprovalResult(@RequestBody FengQiGuanJiaRequest fengQiGuanJiaRequest) {
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		StopWatch stopWatch = new StopWatch();
//		String methodName = "FenQiGuanJiaController.userApproval";
//		try {
//			long sessionId = System.currentTimeMillis();
//
//			stopWatch.start();
//
//			// 验证参数
//			String check = FenQiGuanJiaUtils.checkDataFilter(fengQiGuanJiaRequest);
//			if (StringUtils.isNotBlank(check)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);// TODO:返回code待定
//				fenQiGuanJiaResponse.setMsg(check);
//				logger.info("FenQiGuanJiaController.userPullApprovalResult验证参数失败:" + check);
//				methodEnd(stopWatch, methodName, check, fengQiGuanJiaRequest);
//				return fenQiGuanJiaResponse;
//			}
//			String biz_data = fengQiGuanJiaRequest.getBiz_data();
//			PullApprovalResult pullApprovalResult = JSONObject.parseObject(biz_data, PullApprovalResult.class);
//			fenQiGuanJiaResponse = fenQiGuanJiaService.pullApprovalResult(sessionId, pullApprovalResult);
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaController.userPullApprovalResult接收数据异常");
//			logger.error("FenQiGuanJiaController.userPullApprovalResult接收数据异常", e);
//		}
//		stopWatch.stop();
//		logger.info(methodName + " end, costTime=" + stopWatch.getTotalTimeMillis());
//
//		return fenQiGuanJiaResponse;
//
//	}
//
//	/**
//	 * 1.8 预授信接口
//	 * 
//	 */
//	@RequestMapping("/sxyDrainage/fenqiguanjia/preApproval.do")
//	@ResponseBody
//
//	public FenQiGuanJiaResponse userApproval(@RequestBody FengQiGuanJiaRequest fengQiGuanJiaRequest) {
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		StopWatch stopWatch = new StopWatch();
//		String methodName = "FenQiGuanJiaController.userApproval";
//		try {
//			long sessionId = System.currentTimeMillis();
//
//			stopWatch.start();
//
//			// 验证参数
//			String check = FenQiGuanJiaUtils.checkDataFilter(fengQiGuanJiaRequest);
//			if (StringUtils.isNotBlank(check)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);// TODO:返回code待定
//				fenQiGuanJiaResponse.setMsg(check);
//				logger.info("FenQiGuanJiaController.userApproval验证参数失败:" + check);
//				methodEnd(stopWatch, methodName, check, fengQiGuanJiaRequest);
//				return fenQiGuanJiaResponse;
//			}
//			String biz_data = fengQiGuanJiaRequest.getBiz_data();
//			PreApprovalReq preApprovalReq = JSONObject.parseObject(biz_data, PreApprovalReq.class);
//			fenQiGuanJiaResponse = fenQiGuanJiaService.queryPreApproavl(sessionId, preApprovalReq);
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaController.userApproval接收数据异常");
//			logger.error("FenQiGuanJiaController.userApproval接收数据异常", e);
//		}
//		stopWatch.stop();
//		logger.info(methodName + " end, costTime=" + stopWatch.getTotalTimeMillis());
//
//		return fenQiGuanJiaResponse;
//
//	}
//
//	/**
//	 * 3.9 试算接口
//	 * 
//	 */
//	@RequestMapping("/sxyDrainage/fenqiguanjia/calculate.do")
//	@ResponseBody
//
//	public FenQiGuanJiaResponse userCalculate(@RequestBody FengQiGuanJiaRequest fengQiGuanJiaRequest) {
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		StopWatch stopWatch = new StopWatch();
//		String methodName = "FenQiGuanJiaController.userCalculate";
//		try {
//			long sessionId = System.currentTimeMillis();
//
//			stopWatch.start();
//
//			// 验证参数
//			String check = FenQiGuanJiaUtils.checkDataFilter(fengQiGuanJiaRequest);
//			if (StringUtils.isNotBlank(check)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg(check);
//				logger.info("FenQiGuanJiaController.userCalculate验证参数失败:" + check);
//				methodEnd(stopWatch, methodName, check, fengQiGuanJiaRequest);
//				return fenQiGuanJiaResponse;
//			}
//			String biz_data = fengQiGuanJiaRequest.getBiz_data();
//			CalculateFeeReq calculateFeeReq = JSONObject.parseObject(biz_data, CalculateFeeReq.class);
//			fenQiGuanJiaResponse = fenQiGuanJiaService.queryCalculate(sessionId, calculateFeeReq);
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaController.userCalculate接收数据异常");
//			logger.error("FenQiGuanJiaController.userCalculate接收数据异常", e);
//		}
//		stopWatch.stop();
//		logger.info(methodName + " end, costTime=" + stopWatch.getTotalTimeMillis());
//
//		return fenQiGuanJiaResponse;
//
//	}
//
//	/**
//	 * 3.11 合同接口
//	 * 
//	 * @param fengQiGuanJiaRequest
//	 * @return
//	 */
//	@RequestMapping("/sxyDrainage/fenqiguanjia/contractUrl.do")
//	@ResponseBody
//	public FenQiGuanJiaResponse userContract(@RequestBody FengQiGuanJiaRequest fengQiGuanJiaRequest) {
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		StopWatch stopWatch = new StopWatch();
//		String methodName = "FenQiGuanJiaController.userContract";
//		try {
//			long sessionId = System.currentTimeMillis();
//			stopWatch.start();
//			// 验证参数
//			String check = FenQiGuanJiaUtils.checkDataFilter(fengQiGuanJiaRequest);
//			if (StringUtils.isNotBlank(check)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg(check);
//				logger.info("FenQiGuanJiaController.userContract验证参数失败:" + check);
//				methodEnd(stopWatch, methodName, check, fengQiGuanJiaRequest);
//				return fenQiGuanJiaResponse;
//			}
//			String biz_data = fengQiGuanJiaRequest.getBiz_data();
//			ContractUrlReq contractUrlReq = JSONObject.parseObject(biz_data, ContractUrlReq.class);
//			fenQiGuanJiaResponse = fenQiGuanJiaService.queryContractUrl(sessionId, contractUrlReq);
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaController.userContract接收数据异常");
//			logger.error("FenQiGuanJiaController.userContract接收数据异常", e);
//		}
//		stopWatch.stop();
//		logger.info(methodName + " end, costTime=" + stopWatch.getTotalTimeMillis());
//
//		return fenQiGuanJiaResponse;
//
//	}
//
//	/**
//	 * 3.13 拉取还款计划接口
//	 * 
//	 * @param fengQiGuanJiaRequest
//	 * @return
//	 */
//	@RequestMapping("/sxyDrainage/fenqiguanjia/pullRepaymentPlan.do")
//	@ResponseBody
//	public FenQiGuanJiaResponse queryRepaymentPlan(@RequestBody FengQiGuanJiaRequest fengQiGuanJiaRequest) {
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		StopWatch stopWatch = new StopWatch();
//		String methodName = "FenQiGuanJiaController.queryRepaymentPlan";
//		try {
//			long sessionId = System.currentTimeMillis();
//			stopWatch.start();
//			// 验证参数
//			String check = FenQiGuanJiaUtils.checkDataFilter(fengQiGuanJiaRequest);
//			if (StringUtils.isNotBlank(check)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg(check);
//				logger.info("FenQiGuanJiaController.queryRepaymentPlan验证参数失败:" + check);
//				methodEnd(stopWatch, methodName, check, fengQiGuanJiaRequest);
//				return fenQiGuanJiaResponse;
//			}
//			String biz_data = fengQiGuanJiaRequest.getBiz_data();
//			PullRepaymentPlan pullRepaymentPlan = JSONObject.parseObject(biz_data, PullRepaymentPlan.class);
//			fenQiGuanJiaResponse = fenQiGuanJiaService.pullRepaymentPlan(sessionId, pullRepaymentPlan);
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaController.queryRepaymentPlan接收数据异常");
//			logger.error("FenQiGuanJiaController.queryRepaymentPlan接收数据异常", e);
//		}
//		stopWatch.stop();
//		logger.info(methodName + " end, costTime=" + stopWatch.getTotalTimeMillis());
//
//		return fenQiGuanJiaResponse;
//
//	}
//
//	/**
//	 * 3.15 拉取订单状态接口
//	 * 
//	 * @param fengQiGuanJiaRequest
//	 * @return
//	 */
//	@RequestMapping("/sxyDrainage/fenqiguanjia/pullOrderStatus.do")
//	@ResponseBody
//	public FenQiGuanJiaResponse queryOrderStatus(@RequestBody FengQiGuanJiaRequest fengQiGuanJiaRequest) {
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		StopWatch stopWatch = new StopWatch();
//		String methodName = "FenQiGuanJiaController.queryOrderStatus";
//		try {
//			long sessionId = System.currentTimeMillis();
//			stopWatch.start();
//			// 验证参数
//			String check = FenQiGuanJiaUtils.checkDataFilter(fengQiGuanJiaRequest);
//			if (StringUtils.isNotBlank(check)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg(check);
//				logger.info("FenQiGuanJiaController.queryOrderStatus验证参数失败:" + check);
//				methodEnd(stopWatch, methodName, check, fengQiGuanJiaRequest);
//				return fenQiGuanJiaResponse;
//			}
//			String biz_data = fengQiGuanJiaRequest.getBiz_data();
//			PullOrderStatus pullOrderStatus = JSONObject.parseObject(biz_data, PullOrderStatus.class);
//			fenQiGuanJiaResponse = fenQiGuanJiaService.pullOrderStatus(sessionId, pullOrderStatus);
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaController.queryOrderStatus接收数据异常");
//			logger.error("FenQiGuanJiaController.queryOrderStatus接收数据异常", e);
//		}
//		stopWatch.stop();
//		logger.info(methodName + " end, costTime=" + stopWatch.getTotalTimeMillis());
//
//		return fenQiGuanJiaResponse;
//
//	}
//
//	/**
//	 * 3.16 主动还款接口
//	 * 
//	 * @param fengQiGuanJiaRequest
//	 * @return
//	 */
//	@RequestMapping("/sxyDrainage/fenqiguanjia/activeRepayment.do")
//	@ResponseBody
//	public FenQiGuanJiaResponse userActiveRepayment(@RequestBody FengQiGuanJiaRequest fengQiGuanJiaRequest) {
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		StopWatch stopWatch = new StopWatch();
//		String methodName = "FenQiGuanJiaController.userActiveRepayment";
//		try {
//			long sessionId = System.currentTimeMillis();
//			stopWatch.start();
//			// 验证参数
//			String check = FenQiGuanJiaUtils.checkDataFilter(fengQiGuanJiaRequest);
//			if (StringUtils.isNotBlank(check)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg(check);
//				logger.info("FenQiGuanJiaController.userActiveRepayment验证参数失败:" + check);
//				methodEnd(stopWatch, methodName, check, fengQiGuanJiaRequest);
//				return fenQiGuanJiaResponse;
//			}
//			String biz_data = fengQiGuanJiaRequest.getBiz_data();
//			ActiveRepayment activeRepayment = JSONObject.parseObject(biz_data, ActiveRepayment.class);
//			logger.info("activeRepayment数据为：" + JSONObject.toJSONString(activeRepayment));
//			fenQiGuanJiaResponse = fenQiGuanJiaService.updateActiveRepayment(sessionId, activeRepayment);
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaController.userActiveRepayment接收数据异常");
//			logger.error("FenQiGuanJiaController.userActiveRepayment接收数据异常", e);
//		}
//		stopWatch.stop();
//		logger.info(methodName + " end, costTime=" + stopWatch.getTotalTimeMillis());
//
//		return fenQiGuanJiaResponse;
//
//	}
//
//	private void methodEnd(StopWatch stopWatch, String methodName, String message,
//			FengQiGuanJiaRequest fengQiGuanJiaRequest) {
//		stopWatch.stop();
//		logger.info(methodName + " end,costTime=" + stopWatch.getTotalTimeMillis() + "," + message
//				+ ",FengQiGuanJiaRequest=" + fengQiGuanJiaRequest);
//		ThreadLocalUtil.remove();
//	}
//
//}
