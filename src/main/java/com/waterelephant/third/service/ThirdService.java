package com.waterelephant.third.service;

import com.waterelephant.third.entity.ThirdRequest;
import com.waterelephant.third.entity.ThirdResponse;

/**
 * 统一对外接口（code0091）
 * 
 * 
 * Module:
 * 
 * ThirdService.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface ThirdService {

	/**
	 * 统一对外接口 - 检查用户（code）
	 * 
	 * @param thirdRequest
	 * @author zhangChong
	 * @return ThirdResponse
	 */
	ThirdResponse checkUser(long sessionId, ThirdRequest thirdRequest);

	/**
	 * 统一对外接口 - 保存推送工单信息（code0091）
	 * 
	 * @param thirdRequest
	 * @return
	 */
	ThirdResponse savePushOrder(long sessionId, ThirdRequest thirdRequest);

	/**
	 * 统一对外接口 - 修改绑卡回调（code0091）
	 * 
	 * @param sessionId
	 * @param status
	 * @param result
	 * @author liuDaodao
	 * @return
	 */
	boolean updateBindCardCallback(long sessionId, String status, String result);

	/**
	 * 统一对外接口 - 修改银行卡信息（code0091）
	 * 
	 * @param sessionId
	 * @param status
	 * @param result
	 * @author liuDaodao
	 * @return
	 */
	ThirdResponse updateBankCard(long sessionId, ThirdRequest thirdRequest);

	/**
	 * 统一对外接口-获取合同地址（code0091）
	 * 
	 * @param sessionId
	 * @param thirdRequest
	 * @return
	 */
	ThirdResponse contractUrl(long sessionId, ThirdRequest thirdRequest);

	/**
	 * 统一对外接口-订单状态（code0091）
	 * 
	 * @param sessionId
	 * @param thirdRequest
	 * @return
	 */
	ThirdResponse orderStatus(long sessionId, ThirdRequest thirdRequest);

	// /**
	// * 统一对外接口-审批结论（code0091）
	// *
	// * @param sessionId
	// * @param thirdRequest
	// * @return
	// */
	// ThirdResponse conclusion(long sessionId, ThirdRequest thirdRequest);

	/**
	 * 统一对外接口-签约（code0091）
	 * 
	 * @param sessionId
	 * @param thirdRequest
	 * @return
	 */
	ThirdResponse updateSignContract(long sessionId, ThirdRequest thirdRequest);

	/**
	 * 统一对外接口-还款计划（code0091）
	 * 
	 * @param sessionId
	 * @param thirdRequest
	 * @return
	 */
	ThirdResponse repaymentPlan(long sessionId, ThirdRequest thirdRequest);

	/**
	 * 统一对外接口-还款详情（code0091）
	 * 
	 * @param sessionId
	 * @param thirdRequest
	 * @return
	 */
	ThirdResponse repayInfo(long sessionId, ThirdRequest thirdRequest);

	/**
	 * 统一对外接口-主动还款（code0091）
	 * 
	 * @param sessionId
	 * @param thirdRequest
	 * @return
	 */
	ThirdResponse repayment(long sessionId, ThirdRequest thirdRequest);

	/**
	 * 统一对外接口-展期详情（code0091）
	 * 
	 * @param sessionId
	 * @param thirdRequest
	 * @return
	 */
	ThirdResponse deferInfo(long sessionId, ThirdRequest thirdRequest);

	/**
	 * 统一对外接口-展期（code0091）
	 * 
	 * @param sessionId
	 * @param thirdRequest
	 * @return
	 */
	ThirdResponse defer(long sessionId, ThirdRequest thirdRequest);
}
