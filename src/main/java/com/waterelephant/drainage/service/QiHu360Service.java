/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.service;

import com.waterelephant.drainage.entity.qihu360.PushApprovalConfirmInfo;
import com.waterelephant.drainage.entity.qihu360.PushUserBankCard;
import com.waterelephant.drainage.entity.qihu360.PushUserInfo;
import com.waterelephant.drainage.entity.qihu360.PushUserLoanAddInfo;
import com.waterelephant.drainage.entity.qihu360.PushUserLoanBasicInfo;
import com.waterelephant.drainage.entity.qihu360.PushUserRepayInfo;
import com.waterelephant.drainage.entity.qihu360.QiHu360Response;
import com.waterelephant.drainage.entity.qihu360.QueryApprovalStatus;
import com.waterelephant.drainage.entity.qihu360.QueryContractUrl;
import com.waterelephant.drainage.entity.qihu360.QueryOrderStatus;
import com.waterelephant.drainage.entity.qihu360.QueryRepayInfo;
import com.waterelephant.drainage.entity.qihu360.QueryRepaymentPlan;
import com.waterelephant.drainage.entity.qihu360.TrialInfo;

/**
 * 
 * 
 * Module:
 * 
 * QiHu360Service.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public interface QiHu360Service {
	/**
	 * 查询复贷和黑名单信息（code360）
	 * 
	 * @param pushUserInfo
	 * @author zhangChong
	 * @return QiHu360Response
	 */
	QiHu360Response checkUser(long sessionId, PushUserInfo pushUserInfo);

	/**
	 * 推送用户借款基本信息(code360)
	 * 
	 * @param sessionId
	 * @param pushUserLoanBasicInfo
	 * @return QiHu360Response
	 */
	QiHu360Response savePushLoanBaseInfo(long sessionId, PushUserLoanBasicInfo pushUserLoanBasicInfo);

	/**
	 * 推送用户借款补充信息(code360)
	 * 
	 * @param sessionId
	 * @param pushUserLoanAddInfo
	 * @return QiHu360Response
	 */
	QiHu360Response savePushLoanAddInfo(long sessionId, PushUserLoanAddInfo pushUserLoanAddInfo);

	/**
	 * 推送用户绑定银行卡(code360)
	 * 
	 * @param sessionId
	 * @param pushUserBankCard
	 * @return QiHu360Response
	 */
	QiHu360Response savePushUserBankCard(long sessionId, PushUserBankCard pushUserBankCard);

	/**
	 * 查询审批结论(code360)
	 * 
	 * @param sessionId
	 * @param queryApprovalStatus
	 * @return QiHu360Response
	 */
	QiHu360Response queryApprovalStatus(long sessionId, QueryApprovalStatus queryApprovalStatus);

	/**
	 * 试算接口(code360)
	 * 
	 * @param sessionId
	 * @param trialInfo
	 * @return QiHu360Response
	 */
	QiHu360Response trial(long sessionId, TrialInfo trialInfo);

	/**
	 * 推送用户确认收款信息(code360)
	 * 
	 * @param sessionId
	 * @param queryApprovalStatus
	 * @return QiHu360Response
	 */
	QiHu360Response updatePushApprovalConfirm(long sessionId, PushApprovalConfirmInfo pushApprovalConfirmInfo);

	/**
	 * 查询借款合同(code360)
	 * 
	 * @param sessionId
	 * @param queryContractUrl
	 * @return QiHu360Response
	 */
	QiHu360Response queryContractUrl(long sessionId, QueryContractUrl queryContractUrl);

	/**
	 * 查询还款计划(code360)
	 * 
	 * @param sessionId
	 * @param queryRepaymentPlan
	 * @return QiHu360Response
	 */
	QiHu360Response queryRepaymentPlan(long sessionId, QueryRepaymentPlan queryRepaymentPlan);

	/**
	 * 查询还款详情(code360)
	 * 
	 * @param sessionId
	 * @param queryRepayInfo
	 * @return QiHu360Response
	 */
	QiHu360Response queryRepayInfo(long sessionId, QueryRepayInfo queryRepayInfo);

	/**
	 * 推送用户还款信息(code360)
	 *
	 * @param sessionId
	 * @param pushUserRepayInfo
	 * @return QiHu360Response
	 */
	QiHu360Response pushUserRepayInfo(long sessionId, PushUserRepayInfo pushUserRepayInfo);

	/**
	 * 查询订单状态(code360)
	 * 
	 * @param sessionId
	 * @param queryOrderStatus
	 * @return QiHu360Response
	 */
	QiHu360Response queryOrderStatus(long sessionId, QueryOrderStatus queryOrderStatus);
}
