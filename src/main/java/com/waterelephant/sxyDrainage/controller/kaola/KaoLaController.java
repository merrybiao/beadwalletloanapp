///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.controller.kaola;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.waterelephant.sxyDrainage.entity.kaola.BindCardReq;
//import com.waterelephant.sxyDrainage.entity.kaola.KaoLaRequest;
//import com.waterelephant.sxyDrainage.entity.kaola.KaoLaResponse;
//import com.waterelephant.sxyDrainage.entity.kaola.KlContractReq;
//import com.waterelephant.sxyDrainage.entity.kaola.KlRepayReq;
//import com.waterelephant.sxyDrainage.entity.kaola.UserApplyInfo;
//import com.waterelephant.sxyDrainage.entity.kaola.UserConditionReq;
//import com.waterelephant.sxyDrainage.entity.kaola.VerifyCodeReq;
//import com.waterelephant.sxyDrainage.service.KaoLaService;
//import com.waterelephant.sxyDrainage.utils.kaola.KaoLaUtils;
//import com.waterelephant.utils.CommUtils;
//
///**
// * 
// * 
// * Module:
// * 
// * KaoLaController.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//@Controller
//public class KaoLaController {
//	private static Logger logger = Logger.getLogger(KaoLaController.class);
//
//	@Autowired
//	private KaoLaService kaoLaService;
//
//	@RequestMapping("sxyDrainage/kaola/validUserCondition.do")
//	@ResponseBody
//	public KaoLaResponse checkUser(@RequestBody KaoLaRequest kaoLaRequest) {
//		long sessionId = System.currentTimeMillis();
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//		try {
//			String reqData = kaoLaRequest.getReqData();
//			// TODO 解密验签
//			String check = KaoLaUtils.checkFilter(kaoLaRequest);
//			if (!CommUtils.isNull(check)) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg(check);
//				logger.info("用户请求数据异常" + JSON.toJSONString(kaoLaResponse));
//				return kaoLaResponse;
//			}
//			String data = "";// 解密之后的数据
//			UserConditionReq userConditionReq = JSONObject.parseObject(data, UserConditionReq.class);
//			kaoLaResponse = kaoLaService.checkUser(sessionId, userConditionReq);
//
//		} catch (Exception e) {
//			logger.error(sessionId + "用户检查接口系统异常", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//			kaoLaResponse.setRetMsg("用户检查接口系统异常，请稍后重试");
//		}
//
//		return kaoLaResponse;
//	}
//
//	// 进件接口 UserApplyInfo pushUserApplyInfo.do
//	// 绑卡接口 BindCardReq bindingCard.do
//	// 获取验证码 VerifyCodeReq bindingCardVerifyCode
//	// 协议接口 KlContractReq qryLoanAgreement.do
//	// 主动还款接口 KlRepayReq repayH5Amount.do
//
//	/**
//	 * 进件接口
//	 * 
//	 * @param kaoLaRequest
//	 * @return
//	 */
//	@RequestMapping("sxyDrainage/kaola/pushUserApplyInfo.do")
//	@ResponseBody
//	public KaoLaResponse saveOrder(@RequestBody KaoLaRequest kaoLaRequest) {
//		long sessionId = System.currentTimeMillis();
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//		try {
//			String reqData = kaoLaRequest.getReqData();
//			// TODO 解密验签
//			String check = KaoLaUtils.checkFilter(kaoLaRequest);
//			if (!CommUtils.isNull(check)) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg(check);
//				logger.info("用户进件请求数据异常" + JSON.toJSONString(kaoLaResponse));
//				return kaoLaResponse;
//			}
//			String data = "";// 解密之后的数据
//			UserApplyInfo userApplyInfo = JSONObject.parseObject(data, UserApplyInfo.class);
//			kaoLaResponse = kaoLaService.saveOrder(sessionId, userApplyInfo);
//
//		} catch (Exception e) {
//			logger.error(sessionId + "用户进件接口系统异常", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//			kaoLaResponse.setRetMsg("用户进件接口系统异常，请稍后重试");
//		}
//
//		return kaoLaResponse;
//	}
//
//	/**
//	 * 绑卡接口
//	 * 
//	 * @param kaoLaRequest
//	 * @return
//	 */
//	@RequestMapping("sxyDrainage/kaola/bindingCard.do")
//	@ResponseBody
//	public KaoLaResponse saveBindCard(@RequestBody KaoLaRequest kaoLaRequest) {
//		long sessionId = System.currentTimeMillis();
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//		try {
//			String reqData = kaoLaRequest.getReqData();
//			// TODO 解密验签
//			String check = KaoLaUtils.checkFilter(kaoLaRequest);
//			if (!CommUtils.isNull(check)) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg(check);
//				logger.info("用户绑卡接口请求数据异常" + JSON.toJSONString(kaoLaResponse));
//				return kaoLaResponse;
//			}
//			String data = "";// 解密之后的数据
//			BindCardReq bindCardReq = JSONObject.parseObject(data, BindCardReq.class);
//			kaoLaResponse = kaoLaService.saveBindCard(sessionId, bindCardReq);
//
//		} catch (Exception e) {
//			logger.error(sessionId + "用户绑卡接口系统异常", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//			kaoLaResponse.setRetMsg("用户绑卡接口系统异常，请稍后重试");
//		}
//
//		return kaoLaResponse;
//	}
//
//	/**
//	 * 获取验证码
//	 * 
//	 * @param kaoLaRequest
//	 * @return
//	 */
//	@RequestMapping("sxyDrainage/kaola/bindingCardVerifyCode.do")
//	@ResponseBody
//	public KaoLaResponse queryBindCardVerifyCode(@RequestBody KaoLaRequest kaoLaRequest) {
//		long sessionId = System.currentTimeMillis();
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//		try {
//			String reqData = kaoLaRequest.getReqData();
//			// TODO 解密验签
//			String check = KaoLaUtils.checkFilter(kaoLaRequest);
//			if (!CommUtils.isNull(check)) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg(check);
//				logger.info("用户获取验证码接口请求数据异常" + JSON.toJSONString(kaoLaResponse));
//				return kaoLaResponse;
//			}
//			String data = "";// 解密之后的数据
//			VerifyCodeReq verifyCodeReq = JSONObject.parseObject(data, VerifyCodeReq.class);
//			kaoLaResponse = kaoLaService.queryVerifyCode(sessionId, verifyCodeReq);
//
//		} catch (Exception e) {
//			logger.error(sessionId + "用户获取验证码接口系统异常", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//			kaoLaResponse.setRetMsg("用户获取验证码接口系统异常，请稍后重试");
//		}
//
//		return kaoLaResponse;
//	}
//
//	/**
//	 * 协议接口
//	 * 
//	 * @param kaoLaRequest
//	 * @return
//	 */
//	@RequestMapping("sxyDrainage/kaola/qryLoanAgreement.do")
//	@ResponseBody
//	public KaoLaResponse queryOrderContract(@RequestBody KaoLaRequest kaoLaRequest) {
//		long sessionId = System.currentTimeMillis();
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//		try {
//			String reqData = kaoLaRequest.getReqData();
//			// TODO 解密验签
//			String check = KaoLaUtils.checkFilter(kaoLaRequest);
//			if (!CommUtils.isNull(check)) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg(check);
//				logger.info("协议接口请求数据异常" + JSON.toJSONString(kaoLaResponse));
//				return kaoLaResponse;
//			}
//			String data = "";// 解密之后的数据
//			KlContractReq klContractReq = JSONObject.parseObject(data, KlContractReq.class);
//			kaoLaResponse = kaoLaService.queryContract(sessionId, klContractReq);
//
//		} catch (Exception e) {
//			logger.error(sessionId + "获取协议接口系统异常", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//			kaoLaResponse.setRetMsg("协议验证码接口系统异常，请稍后重试");
//		}
//
//		return kaoLaResponse;
//	}
//
//	/**
//	 * 还款接口
//	 * 
//	 * @param kaoLaRequest
//	 * @return
//	 */
//	@RequestMapping("sxyDrainage/kaola/repayH5Amount.do")
//	@ResponseBody
//	public KaoLaResponse updateActiveRepay(@RequestBody KaoLaRequest kaoLaRequest) {
//		long sessionId = System.currentTimeMillis();
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//		try {
//			String reqData = kaoLaRequest.getReqData();
//			// TODO 解密验签
//			String check = KaoLaUtils.checkFilter(kaoLaRequest);
//			if (!CommUtils.isNull(check)) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg(check);
//				logger.info("还款接口请求数据异常" + JSON.toJSONString(kaoLaResponse));
//				return kaoLaResponse;
//			}
//			String data = "";// 解密之后的数据
//			KlRepayReq klRepayReq = JSONObject.parseObject(data, KlRepayReq.class);
//			kaoLaResponse = kaoLaService.updateActiveRepayment(sessionId, klRepayReq);
//
//		} catch (Exception e) {
//			logger.error(sessionId + "还款接口系统异常", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//			kaoLaResponse.setRetMsg("还款接口系统异常，请稍后重试");
//		}
//		return kaoLaResponse;
//	}
//
//	/**
//	 * 6.5. 绑卡结果反馈接口
//	 * 
//	 * @param kaoLaRequest
//	 * @return
//	 */
//
//	@RequestMapping("/sxyDrainage/kaola/bindingCardResult.do")
//	@ResponseBody
//	public KaoLaResponse bindingCardResult(@RequestBody KaoLaRequest kaoLaRequest) {
//		long sessionId = System.currentTimeMillis();
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//
//		try {
//			// 检查数据
//			String check = KaoLaUtils.checkFilter(kaoLaRequest);
//			if (!CommUtils.isNull(check)) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg(check);
//				logger.info("绑卡结果反馈接口请求数据异常" + JSON.toJSONString(kaoLaResponse));
//				return kaoLaResponse;
//			}
//			String reqData = kaoLaRequest.getReqData();
//			// 解密数据 TODO
//			String data = "";
//			// 获取参数（loanId,银行卡号）
//			JSONObject parseObject = JSONObject.parseObject(data);
//
//			kaoLaResponse = kaoLaService.bindingCardResult(sessionId, parseObject);
//		} catch (Exception e) {
//			logger.error(sessionId + "绑卡结果反馈接口系统异常", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//			kaoLaResponse.setRetMsg("绑卡结果反馈接口系统异常，请稍后重试");
//		}
//		return kaoLaResponse;
//	}
//
//	/**
//	 * 6.9. 还款计划表拉取接口
//	 * 
//	 * @param kaoLaRequest
//	 * @return kaoLaResponse
//	 */
//	@RequestMapping("/sxyDrainage/kaola/qryRepaymentPlanRecord.do")
//	@ResponseBody
//	public KaoLaResponse qryRepaymentPlanRecord(@RequestBody KaoLaRequest kaoLaRequest) {
//		long sessionId = System.currentTimeMillis();
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//		try {
//			// 检查数据
//			String check = KaoLaUtils.checkFilter(kaoLaRequest);
//			if (!CommUtils.isNull(check)) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg(check);
//				logger.info("还款计划表拉取接口请求数据异常" + JSON.toJSONString(kaoLaResponse));
//				return kaoLaResponse;
//			}
//			String reqData = kaoLaRequest.getReqData();
//			// 解密数据 TODO
//			String data = "";
//			// 获取参数（loanId）
//			JSONObject parseObject = JSONObject.parseObject(data);
//
//			kaoLaResponse = kaoLaService.qryRepaymentPlanRecord(sessionId, parseObject);
//		} catch (Exception e) {
//			logger.error(sessionId + "还款计划表拉取接口系统异常", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//			kaoLaResponse.setRetMsg("还款计划表拉取接口系统异常，请稍后重试");
//		}
//		return kaoLaResponse;
//
//	}
//
//}
