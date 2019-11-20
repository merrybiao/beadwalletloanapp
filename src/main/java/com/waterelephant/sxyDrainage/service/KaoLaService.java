///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.waterelephant.sxyDrainage.entity.kaola.BindCardReq;
//import com.waterelephant.sxyDrainage.entity.kaola.KaoLaResponse;
//import com.waterelephant.sxyDrainage.entity.kaola.KlContractReq;
//import com.waterelephant.sxyDrainage.entity.kaola.KlRepayReq;
//import com.waterelephant.sxyDrainage.entity.kaola.UserApplyInfo;
//import com.waterelephant.sxyDrainage.entity.kaola.UserConditionReq;
//import com.waterelephant.sxyDrainage.entity.kaola.VerifyCodeReq;
//
///**
// * 
// * 
// * Module:
// * 
// * KaoLaService.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public interface KaoLaService {
//	/**
//	 * 用户检查接口
//	 * 
//	 * @param sessionId
//	 * @param userConditionReq
//	 * @return
//	 */
//	public KaoLaResponse checkUser(long sessionId, UserConditionReq userConditionReq);
//
//	/**
//	 * 用户进件接口
//	 * 
//	 * @param sessionId
//	 * @param userApplyInfo
//	 * @return
//	 */
//	public KaoLaResponse saveOrder(long sessionId, UserApplyInfo userApplyInfo);
//
//	/**
//	 * 绑卡接口
//	 * 
//	 * @param sessionId
//	 * @param bindCardReq
//	 * @return
//	 */
//	public KaoLaResponse saveBindCard(long sessionId, BindCardReq bindCardReq);
//
//	/**
//	 * 获取验证码接口
//	 * 
//	 * @param sessionId
//	 * @param verifyCodeReq
//	 * @return
//	 */
//	public KaoLaResponse queryVerifyCode(long sessionId, VerifyCodeReq verifyCodeReq);
//
//	/**
//	 * 协议调用接口
//	 * 
//	 * @param sessionId
//	 * @param klContractReq
//	 * @return
//	 */
//	public KaoLaResponse queryContract(long sessionId, KlContractReq klContractReq);
//
//	/**
//	 * 还款接口
//	 * 
//	 * @param sessionId
//	 * @param klRepayReq
//	 * @return
//	 */
//	public KaoLaResponse updateActiveRepayment(long sessionId, KlRepayReq klRepayReq);
//
//	/**
//	 * 6.5. 绑卡结果反馈接口
//	 * 
//	 * @param sessionId
//	 * @param parseObject
//	 * @return
//	 */
//	KaoLaResponse bindingCardResult(long sessionId, JSONObject parseObject);
//
//	/**
//	 * 6.9. 还款计划表拉取接口
//	 * 
//	 * @param sessionId
//	 * @param parseObject
//	 * @return
//	 */
//	KaoLaResponse qryRepaymentPlanRecord(long sessionId, JSONObject parseObject);
//
//}
