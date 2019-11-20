///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuBindCardReq;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuCheckUserReq;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuCommonReq;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuPushUserReq;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuResponse;
//import com.waterelephant.sxyDrainage.entity.kaNiu.SupplyInfoReq;
//
///**
// * 
// * Module: KaNiuService.java
// * 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public interface KaNiuService {
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param kaNiuCheckUserReq
//	 * @return
//	 */
//	KaNiuResponse checkUser(long sessionId, KaNiuCheckUserReq kaNiuCheckUserReq);
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param kaNiuCheckUserPushUser
//	 * @return
//	 */
//	KaNiuResponse savePushUser(long sessionId, KaNiuPushUserReq kaNiuUserPushUser);
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param kaNiuBindCardReq
//	 * @return
//	 */
//	KaNiuResponse saveBindCard(long sessionId, KaNiuBindCardReq kaNiuBindCardReq);
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param kaNiuOrderStateReq
//	 * @return
//	 */
//	KaNiuResponse getOrderState(long sessionId, KaNiuCommonReq kaNiuCommonReq);
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param kaNiuCommonReq
//	 * @return
//	 */
//	KaNiuResponse calculate(long sessionId, KaNiuCommonReq kaNiuCommonReq);
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param kaNiuCommonReq
//	 * @return
//	 */
//	KaNiuResponse updateRepayment(long sessionId, KaNiuCommonReq kaNiuCommonReq);
//
//	/**
//	 *
//	 * @param sessionId
//	 * @param kaNiuBindCardReq
//	 * @return
//	 */
//	KaNiuResponse getNextReqUrl(long sessionId, KaNiuBindCardReq kaNiuBindCardReq);
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param supplyInfoReq
//	 * @return
//	 */
//	KaNiuResponse saveSupplyInfo(long sessionId, SupplyInfoReq supplyInfoReq);
//
//}
