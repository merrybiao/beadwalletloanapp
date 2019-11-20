///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.ActiveRepayment;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.CalculateFeeReq;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.ContractUrlReq;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.FenQiGuanJiaResponse;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.OrderAddInfoReq;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.OrderInfoRequest;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.PreApprovalReq;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.PullApprovalResult;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.PullOrderStatus;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.PullRepaymentPlan;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.UserBindCardData;
//
///**
// * 
// * 
// * Module:
// * 
// * FenQiGuanJiaService.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//
//public interface FenQiGuanJiaService {
//	/**
//	 * 3.1用户过滤接口
//	 * 
//	 * @param sessionId
//	 * @param user_name
//	 * @param user_mobile
//	 * @param id_card
//	 * @return
//	 */
//	public FenQiGuanJiaResponse checkUser(long sessionId, String user_name, String user_mobile, String id_card);
//
//	/**
//	 * 3.2保存用户信息接口
//	 * 
//	 * @param sessionId
//	 * @param orderInfoRequest
//	 * @return
//	 */
//	public FenQiGuanJiaResponse saveOrder(long sessionId, OrderInfoRequest orderInfoRequest);
//
//	/**
//	 * 3.3接收补充用户信息接口
//	 * 
//	 * @param sessionId
//	 * @param orderAddInfoReq
//	 * @return
//	 */
//	public FenQiGuanJiaResponse saveAddOrder(long sessionId, OrderAddInfoReq orderAddInfoReq);
//
//	/**
//	 * 3.4 旧绑卡接口
//	 * 
//	 * @param sessionId
//	 * @param userBindCardData
//	 * @return
//	 */
//	public FenQiGuanJiaResponse saveBindCard(long sessionId, UserBindCardData userBindCardData);
//
//	/**
//	 * 3.4 新预绑卡接口
//	 * @param sesseionId
//	 * @param userBindCardData
//	 * @return
//	 */
//	public FenQiGuanJiaResponse  saveNewBindCard(long sesseionId,UserBindCardData userBindCardData);
//
//	/**
//	 * 3.4 验证码（新绑卡）
//	 * @param sessionId
//	 * @param userBindCardData
//	 * @return
//	 */
//	public FenQiGuanJiaResponse saveBindCardWithCode(long sessionId,UserBindCardData userBindCardData);
//
//
//
//	/**
//	 * 3.8 预授信接口
//	 * 
//	 * @param sessionId
//	 * @param preApprovalReq
//	 * @return
//	 */
//	public FenQiGuanJiaResponse queryPreApproavl(long sessionId, PreApprovalReq preApprovalReq);
//
//	/**
//	 * 3.9 试算接口
//	 * 
//	 * @param sessionId
//	 * @param calculateFeeReq
//	 * @return
//	 */
//	public FenQiGuanJiaResponse queryCalculate(long sessionId, CalculateFeeReq calculateFeeReq);
//
//	/**
//	 * 3.11 合同接口
//	 * 
//	 * @param sessionId
//	 * @param contractUrlReq
//	 * @return
//	 */
//	public FenQiGuanJiaResponse queryContractUrl(long sessionId, ContractUrlReq contractUrlReq);
//
//	/**
//	 * 3.7 拉取审批结论
//	 * 
//	 * @param sessionId
//	 * @param pullApprovalResult
//	 * @return
//	 */
//	FenQiGuanJiaResponse pullApprovalResult(long sessionId, PullApprovalResult pullApprovalResult);
//
//	/**
//	 * 3.13 拉取还款计划接口
//	 * 
//	 * @param sessionId
//	 * @param pullRepaymentPlan
//	 * @return
//	 */
//	FenQiGuanJiaResponse pullRepaymentPlan(long sessionId, PullRepaymentPlan pullRepaymentPlan);
//
//	/**
//	 * 3.15 拉取订单状态接口
//	 * 
//	 * @param sessionId
//	 * @param pullOrderStatus
//	 * @return
//	 */
//	FenQiGuanJiaResponse pullOrderStatus(long sessionId, PullOrderStatus pullOrderStatus);
//
//	/**
//	 * 3.16 主动还款接口
//	 * 
//	 * @param sessionId
//	 * @param activeRepayment
//	 * @return
//	 */
//	FenQiGuanJiaResponse updateActiveRepayment(long sessionId, ActiveRepayment activeRepayment);
//
//}
