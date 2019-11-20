///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.sxyDrainage.entity.haodai.HaoDaiResponse;
//import com.waterelephant.sxyDrainage.entity.haodai.HdBaseInfoReq;
//import com.waterelephant.sxyDrainage.entity.haodai.HdBindCardCheckReq;
//import com.waterelephant.sxyDrainage.entity.haodai.HdCheckUserReq;
//import com.waterelephant.sxyDrainage.entity.haodai.HdCommonReq;
//import com.waterelephant.sxyDrainage.entity.haodai.HdSupplementInfoReq;
//import com.waterelephant.sxyDrainage.entity.haodai.HdSureLoanReq;
//import com.waterelephant.sxyDrainage.entity.haodai.HdTrialReq;
//
///**
// * 
// * 
// * Module:
// * 
// * HaoDaiNewService.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <好贷网接口>
// */
//public interface HaoDaiNewService {
//	// 用户检查接口
//	HaoDaiResponse checkUser(HdCheckUserReq hdCheckUserReq, long sessionId);
//
//	// 用户进件接口
//	HaoDaiResponse saveOrder(HdBaseInfoReq hdBaseInfoReq, long sessionId,Object object);
//
//	// 用户补充信息接口
//	HaoDaiResponse saveAddOrder(HdSupplementInfoReq hdSupplementInfoReq, long sessionId);
//
//	// 获取银行卡列表
//	HaoDaiResponse getBankCard(HdCommonReq hdCommonReq, long sessionId);
//
//	// 验证银行卡
//	HaoDaiResponse saveBankCardInfo(HdBindCardCheckReq hdBindCardCheckReq, long sessionId);
//
//	// 绑卡接口(验证码)
//	HaoDaiResponse saveBankCardWithCode(HdBindCardCheckReq hdBindCardCheckReq, long sessionId);
//
//	// 推送确认金额及期限
//	HaoDaiResponse withDraw(HdSureLoanReq hdSureLoanReq, long sessionId);
//
//	// 合同接口
//	HaoDaiResponse getContract(HdCommonReq hdCommonReq, long sessionId);
//
//	// 请求试算接口
//	HaoDaiResponse trial(HdTrialReq hdTrialReq, long sessionId);
//
//	// 主动还款接口
//	HaoDaiResponse updateActiveRepayment(HdCommonReq hdCommonReq, long sessionId);
//
//	// 审批结论反馈
//	HaoDaiResponse pullApprove(HdCommonReq hdCommonReq, long sessionId);
//
//	// 拉取订单状态接口
//	HaoDaiResponse pullOrderStatus(HdCommonReq hdCommonReq, long sessionId);
//
//	// 拉取订单状态接口
//	HaoDaiResponse pullRepaymentPlan(HdCommonReq hdCommonReq, long sessionId);
//
//}
