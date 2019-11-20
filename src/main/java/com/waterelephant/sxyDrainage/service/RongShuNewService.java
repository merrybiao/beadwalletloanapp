///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service;
//
//import java.util.Map;
//
//import com.waterelephant.sxyDrainage.entity.rongshu.RsRequest;
//import com.waterelephant.sxyDrainage.entity.rongshu.RsResponse;
//
///**
// * 
// * 
// * Module:
// * 
// * RongShuNewService.java
// * 
// * @author zy
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//
//public interface RongShuNewService {
//	/**
//	 * 用户过滤接口
//	 * 
//	 * @param sessionId
//	 * @param user_name
//	 * @param user_mobile
//	 * @param id_card
//	 * @return
//	 */	
//	public RsResponse checkUser(Long sessionId, String user_name, String user_mobile, String id_card);
//
//	/**
//	 * 进件接口
//	 * 
//	 * @param sessionId
//	 * @param fenLingUserStatus
//	 * @return
//	 */
//	public RsResponse savePushOrder(Long sessionId, RsRequest rsRequest);
//
//	
//	/**
//	 * 获取审核结果
//	 * @param sessionId
//	 * @param mapData
//	 * @return
//	 */
//	public RsResponse getApprovalResult(long sessionId, Map<String,String> mapData);
//	
//	/**
//	 * 获取还款计划接口
//	 * @param sessionId
//	 * @param mapData
//	 * @return
//	 */
//	public RsResponse getRepayPlan(long sessionId, Map<String,String> mapData);
//	
//	/**
//	 * 放款结果查询接口
//	 * @param sessionId
//	 * @param mapData
//	 * @return
//	 */
//	public RsResponse queryFk(long sessionId, Map<String,String> mapData);
//	
//	/**
//	 * 主动还款试算
//	 * @param sessionId
//	 * @param mapData
//	 * @return
//	 */
//	public RsResponse getPaymentCell(long sessionId, Map<String,String> mapData);
//	
//    /**
//     * 主动还款
//     * @param sessionId
//     * @param mapData
//     * @return
//     */
//	public RsResponse updateRepayment(long sessionId, Map<String,String> mapData); 
//	
//	/**
//	 * 还款结果查询
//	 * @param sessionId
//	 * @param mapData
//	 * @return
//	 */
//	public RsResponse getRepaymentResult(long sessionId, Map<String,String> mapData);
//	
//	/**
//	 * 合同接口
//	 * @param sessionId
//	 * @param mapData
//	 * @return
//	 */
//	public RsResponse getContracts(long sessionId, Map<String,String> mapData);
//	
//	/**
//	 * 确认贷款接口
//	 * @param sessionId
//	 * @param mapData
//	 * @return
//	 */
//	public RsResponse confirmLoan(long sessionId, Map<String,String> mapData);
//}
