///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.sxyDrainage.entity.rongYiTui.BindCardRequest;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.CheckUserRequest;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.ConfirmOrderRequest;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.RtyResponse;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.RtyResponseNew;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.RytPushOrderVo;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.RytRequest;
//
///**
// * Module: 
// * RongYiTuiService.java 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public interface RongYiTuiService {
//
//	/**
//	 * 
//	 * @param checkUserRequest
//	 */
//	RtyResponse checkUser(Long sessionId, CheckUserRequest checkUserRequest);
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param rytPushOrderVo
//	 * @return
//	 */
//	RtyResponse savePushOrder(long sessionId, RytPushOrderVo rytPushOrderVo);
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param bindCardRequest
//	 * @return
//	 */
//	RtyResponse savesBindCard(long sessionId, BindCardRequest bindCardRequest);
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param mobile
//	 * @return
//	 */
//	RtyResponse getOrderStatus(long sessionId, RytRequest rytRequest);
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param confirmOrderRequest
//	 * @return
//	 */
//	RtyResponse updateConfirmOrder(long sessionId, ConfirmOrderRequest confirmOrderRequest);
//
//	// /**
//	// *
//	// * @param sessionId
//	// * @param rytRequest
//	// * @return
//	// */
//	// RtyResponse updateSignContract(long sessionId, RytRequest rytRequest);
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param rytRequest
//	 * @return
//	 */
//	RtyResponse getRepayPlan(long sessionId, RytRequest rytRequest);
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param rytRequest
//	 * @return
//	 */
//	RtyResponse updateRepayment(long sessionId, RytRequest rytRequest);
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param rytRequest
//	 * @return
//	 */
//	RtyResponse getApprovalResult(long sessionId, RytRequest rytRequest);
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param bindCardRequest
//	 * @return
//	 */
//	RtyResponse savesBindCardReady(long sessionId, BindCardRequest bindCardRequest);
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param bindCardRequest
//	 * @return
//	 */
//	RtyResponse savesBindCardSure(long sessionId, BindCardRequest bindCardRequest);
//
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param phone
//	 * @param id_card
//	 * @param name
//	 * @return
//	 */
//	RtyResponseNew addCheckUser(long sessionId, String phone, String id_card, String name);
//
//}
