/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.waterelephant.drainage.entity.qihu360.PushApprovalConfirmInfo;
import com.waterelephant.drainage.entity.qihu360.PushUserBankCard;
import com.waterelephant.drainage.entity.qihu360.PushUserInfo;
import com.waterelephant.drainage.entity.qihu360.PushUserLoanAddInfo;
import com.waterelephant.drainage.entity.qihu360.PushUserLoanBasicInfo;
import com.waterelephant.drainage.entity.qihu360.PushUserRepayInfo;
import com.waterelephant.drainage.entity.qihu360.QiHu360Request;
import com.waterelephant.drainage.entity.qihu360.QiHu360Response;
import com.waterelephant.drainage.entity.qihu360.QueryApprovalStatus;
import com.waterelephant.drainage.entity.qihu360.QueryContractUrl;
import com.waterelephant.drainage.entity.qihu360.QueryOrderStatus;
import com.waterelephant.drainage.entity.qihu360.QueryRepayInfo;
import com.waterelephant.drainage.entity.qihu360.QueryRepaymentPlan;
import com.waterelephant.drainage.entity.qihu360.TrialInfo;
import com.waterelephant.drainage.service.QiHu360Service;
import com.waterelephant.drainage.util.qihu360.QiHu360Utils;
import com.waterelephant.entity.BwOrderChannel;
import com.waterelephant.service.IBwOrderChannelService;
import com.waterelephant.utils.CommUtils;

/**
 * 
 * 
 * Module:奇虎360接口 - （code360）
 * 
 * QiHu360Controller.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>奇虎360
 */
@Controller
public class QiHu360Controller {
	private Logger logger = LoggerFactory.getLogger(QiHu360Controller.class);
	@Autowired
	private QiHu360Service qiHu360ServiceImpl;
	@Autowired
	IBwOrderChannelService bwOrderChannelService;

	/**
	 * 奇虎360 - 1.查询复贷和黑名单信息(code360)
	 * 
	 * @param qiHu360Request
	 * @return QiHu360Response
	 */
	@ResponseBody
	@RequestMapping("/drainage/qihu360/checkUser.do")
	public QiHu360Response checkUser(QiHu360Request qiHu360Request) {
		long sessionId = System.currentTimeMillis();
		QiHu360Response qiHu360Response = new QiHu360Response();
		logger.info(sessionId + "：开始QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Request));
		try {
			// 校验参数
			String check = QiHu360Utils.checkParam(qiHu360Request);
			if (StringUtils.isNotBlank(check)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg(check);
				logger.info(sessionId + "：结束QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 商户号 - 查询数据库中是否存在
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(qiHu360Request.getMerchant_id());
			if (CommUtils.isNull(orderChannel)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("merchant_id不存在");
				logger.info(sessionId + "：结束QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第一步：解密参数
			String biz_data = QiHu360Utils.decodeBizData(qiHu360Request);

			PushUserInfo pushUserInfo = JSON.parseObject(biz_data, PushUserInfo.class);
			if (pushUserInfo == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第二步：处理业务
			qiHu360Response = qiHu360ServiceImpl.checkUser(sessionId, pushUserInfo);
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360Controller.checkUser method 异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(sessionId + "：结束QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 3.推送用户借款基本信息(code360)
	 *
	 * @param qiHu360Request
	 * @return QiHu360Response
	 */
	@ResponseBody
	@RequestMapping("/drainage/qihu360/pushLoanBaseInfo.do")
	public QiHu360Response pushLoanBaseInfo(QiHu360Request qiHu360Request) {
		long sessionId = System.currentTimeMillis();
		QiHu360Response qiHu360Response = new QiHu360Response();
		logger.info(sessionId + "：开始QiHu360Controller.pushLoanBaseInfo method：" + JSON.toJSONString(qiHu360Request));
		try {
			// 校验参数
			String check = QiHu360Utils.checkParam(qiHu360Request);
			if (StringUtils.isNotBlank(check)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg(check);
				logger.info(sessionId + "：结束QiHu360Controller.pushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 商户号 - 查询数据库中是否存在
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(qiHu360Request.getMerchant_id());
			if (CommUtils.isNull(orderChannel)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("merchant_id不存在");
				logger.info(sessionId + "：结束QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第一步：解密参数
			String biz_data = QiHu360Utils.decodeBizData(qiHu360Request);

			PushUserLoanBasicInfo pushUserLoanBasicInfo = JSON.parseObject(biz_data, PushUserLoanBasicInfo.class);
			if (pushUserLoanBasicInfo == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360Controller.pushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第二步：处理业务
			qiHu360Response = qiHu360ServiceImpl.savePushLoanBaseInfo(sessionId, pushUserLoanBasicInfo);
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360Controller.pushLoanBaseInfo method 异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(sessionId + "：结束QiHu360Controller.pushLoanBaseInfo method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 4.推送用户借款补充信息(code360)
	 *
	 * @param qiHu360Request
	 * @return QiHu360Response
	 */
	@ResponseBody
	@RequestMapping("/drainage/qihu360/pushLoanAddInfo.do")
	public QiHu360Response pushLoanAddInfo(QiHu360Request qiHu360Request) {
		long sessionId = System.currentTimeMillis();
		QiHu360Response qiHu360Response = new QiHu360Response();
		logger.info(sessionId + "：开始QiHu360Controller.pushLoanAddInfo method：" + JSON.toJSONString(qiHu360Request));
		try {
			// 校验参数
			String check = QiHu360Utils.checkParam(qiHu360Request);
			if (StringUtils.isNotBlank(check)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg(check);
				logger.info(sessionId + "：结束QiHu360Controller.pushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 商户号 - 查询数据库中是否存在
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(qiHu360Request.getMerchant_id());
			if (CommUtils.isNull(orderChannel)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("merchant_id不存在");
				logger.info(sessionId + "：结束QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第一步：解密参数
			String biz_data = QiHu360Utils.decodeBizData(qiHu360Request);

			PushUserLoanAddInfo pushUserLoanAddInfo = JSON.parseObject(biz_data, PushUserLoanAddInfo.class);
			if (pushUserLoanAddInfo == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360Controller.pushLoanAddInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第二步：处理业务
			qiHu360Response = qiHu360ServiceImpl.savePushLoanAddInfo(sessionId, pushUserLoanAddInfo);
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360Controller.pushLoanAddInfo method 异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(sessionId + "：结束QiHu360Controller.pushLoanAddInfo method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 6.推送用户绑定银行卡 （1.2）跳转机构页面绑卡(code360)
	 *
	 * @param qiHu360Request
	 * @return QiHu360Response
	 */
	@ResponseBody
	@RequestMapping("/drainage/qihu360/pushBankCard.do")
	public QiHu360Response pushBankCard(QiHu360Request qiHu360Request) {
		long sessionId = System.currentTimeMillis();
		QiHu360Response qiHu360Response = new QiHu360Response();
		logger.info(sessionId + "：开始QiHu360Controller.pushBankCard method：" + JSON.toJSONString(qiHu360Request));
		try {
			// 校验参数
			String check = QiHu360Utils.checkParam(qiHu360Request);
			if (StringUtils.isNotBlank(check)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg(check);
				logger.info(sessionId + "：结束QiHu360Controller.pushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 商户号 - 查询数据库中是否存在
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(qiHu360Request.getMerchant_id());
			if (CommUtils.isNull(orderChannel)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("merchant_id不存在");
				logger.info(sessionId + "：结束QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第一步：解密参数
			String biz_data = QiHu360Utils.decodeBizData(qiHu360Request);

			PushUserBankCard pushUserBankCard = JSON.parseObject(biz_data, PushUserBankCard.class);
			if (pushUserBankCard == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(
						sessionId + "：结束QiHu360Controller.pushBankCard method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第二步：处理业务
			qiHu360Response = qiHu360ServiceImpl.savePushUserBankCard(sessionId, pushUserBankCard);
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360Controller.pushBankCard method 异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(sessionId + "：结束QiHu360Controller.pushBankCard method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 8.查询审批结论(code360)
	 *
	 * @param qiHu360Request
	 * @return QiHu360Response
	 */
	@ResponseBody
	@RequestMapping("/drainage/qihu360/queryApprovalStatus.do")
	public QiHu360Response queryApprovalStatus(QiHu360Request qiHu360Request) {
		long sessionId = System.currentTimeMillis();
		QiHu360Response qiHu360Response = new QiHu360Response();
		logger.info(sessionId + "：开始QiHu360Controller.queryApprovalStatus method：" + JSON.toJSONString(qiHu360Request));
		try {
			// 校验参数
			String check = QiHu360Utils.checkParam(qiHu360Request);
			if (StringUtils.isNotBlank(check)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg(check);
				logger.info(sessionId + "：结束QiHu360Controller.pushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 商户号 - 查询数据库中是否存在
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(qiHu360Request.getMerchant_id());
			if (CommUtils.isNull(orderChannel)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("merchant_id不存在");
				logger.info(sessionId + "：结束QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第一步：解密参数
			String biz_data = QiHu360Utils.decodeBizData(qiHu360Request);

			QueryApprovalStatus queryApprovalStatus = JSON.parseObject(biz_data, QueryApprovalStatus.class);
			if (queryApprovalStatus == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360Controller.queryApprovalStatus method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第二步：处理业务
			qiHu360Response = qiHu360ServiceImpl.queryApprovalStatus(sessionId, queryApprovalStatus);
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360Controller.queryApprovalStatus method 异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(
				sessionId + "：结束QiHu360Controller.queryApprovalStatus method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 9.试算接口(code360)
	 *
	 * @param qiHu360Request
	 * @return QiHu360Response
	 */
	@ResponseBody
	@RequestMapping("/drainage/qihu360/trial.do")
	public QiHu360Response trial(QiHu360Request qiHu360Request) {
		long sessionId = System.currentTimeMillis();
		QiHu360Response qiHu360Response = new QiHu360Response();
		logger.info(sessionId + "：开始QiHu360Controller.trial method：" + JSON.toJSONString(qiHu360Request));
		try {
			// 校验参数
			String check = QiHu360Utils.checkParam(qiHu360Request);
			if (StringUtils.isNotBlank(check)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg(check);
				logger.info(sessionId + "：结束QiHu360Controller.pushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 商户号 - 查询数据库中是否存在
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(qiHu360Request.getMerchant_id());
			if (CommUtils.isNull(orderChannel)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("merchant_id不存在");
				logger.info(sessionId + "：结束QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第一步：解密参数
			String biz_data = QiHu360Utils.decodeBizData(qiHu360Request);

			TrialInfo trialInfo = JSON.parseObject(biz_data, TrialInfo.class);
			if (trialInfo == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360Controller.trial method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第二步：处理业务
			qiHu360Response = qiHu360ServiceImpl.trial(sessionId, trialInfo);
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360Controller.trialInfo method 异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(sessionId + "：结束QiHu360Controller.trialInfo method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 10.推送用户确认收款信息(code360)
	 *
	 * @param qiHu360Request
	 * @return QiHu360Response
	 */
	@ResponseBody
	@RequestMapping("/drainage/qihu360/pushApprovalConfirm.do")
	public QiHu360Response pushApprovalConfirm(QiHu360Request qiHu360Request) {
		long sessionId = System.currentTimeMillis();
		QiHu360Response qiHu360Response = new QiHu360Response();
		logger.info(sessionId + "：开始QiHu360Controller.pushApprovalConfirm method：" + JSON.toJSONString(qiHu360Request));
		try {
			// 校验参数
			String check = QiHu360Utils.checkParam(qiHu360Request);
			if (StringUtils.isNotBlank(check)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg(check);
				logger.info(sessionId + "：结束QiHu360Controller.pushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 商户号 - 查询数据库中是否存在
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(qiHu360Request.getMerchant_id());
			if (CommUtils.isNull(orderChannel)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("merchant_id不存在");
				logger.info(sessionId + "：结束QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第一步：解密参数
			String biz_data = QiHu360Utils.decodeBizData(qiHu360Request);

			PushApprovalConfirmInfo pushApprovalConfirmInfo = JSON.parseObject(biz_data, PushApprovalConfirmInfo.class);
			if (pushApprovalConfirmInfo == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360Controller.pushApprovalConfirm method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第二步：处理业务
			qiHu360Response = qiHu360ServiceImpl.updatePushApprovalConfirm(sessionId, pushApprovalConfirmInfo);
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360Controller.pushApprovalConfirm method 异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(
				sessionId + "：结束QiHu360Controller.pushApprovalConfirm method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 11.查询借款合同(code360)
	 *
	 * @param qiHu360Request
	 * @return QiHu360Response
	 */
	@ResponseBody
	@RequestMapping("/drainage/qihu360/queryContractUrl.do")
	public QiHu360Response queryContractUrl(QiHu360Request qiHu360Request) {
		long sessionId = System.currentTimeMillis();
		QiHu360Response qiHu360Response = new QiHu360Response();
		logger.info(sessionId + "：开始QiHu360Controller.queryContractUrl method：" + JSON.toJSONString(qiHu360Request));
		try {
			// 校验参数
			String check = QiHu360Utils.checkParam(qiHu360Request);
			if (StringUtils.isNotBlank(check)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg(check);
				logger.info(sessionId + "：结束QiHu360Controller.pushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 商户号 - 查询数据库中是否存在
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(qiHu360Request.getMerchant_id());
			if (CommUtils.isNull(orderChannel)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("merchant_id不存在");
				logger.info(sessionId + "：结束QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第一步：解密参数
			String biz_data = QiHu360Utils.decodeBizData(qiHu360Request);

			QueryContractUrl queryContractUrl = JSON.parseObject(biz_data, QueryContractUrl.class);
			if (queryContractUrl == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360Controller.queryContractUrl method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第二步：处理业务
			qiHu360Response = qiHu360ServiceImpl.queryContractUrl(sessionId, queryContractUrl);
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360Controller.queryContractUrl method 异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(sessionId + "：结束QiHu360Controller.queryContractUrl method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 12.查询还款计划(code360)
	 *
	 * @param qiHu360Request
	 * @return QiHu360Response
	 */
	@ResponseBody
	@RequestMapping("/drainage/qihu360/queryRepaymentPlan.do")
	public QiHu360Response queryRepaymentPlan(QiHu360Request qiHu360Request) {
		long sessionId = System.currentTimeMillis();
		QiHu360Response qiHu360Response = new QiHu360Response();
		logger.info(sessionId + "：开始QiHu360Controller.queryRepaymentPlan method：" + JSON.toJSONString(qiHu360Request));
		try {
			// 校验参数
			String check = QiHu360Utils.checkParam(qiHu360Request);
			if (StringUtils.isNotBlank(check)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg(check);
				logger.info(sessionId + "：结束QiHu360Controller.pushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 商户号 - 查询数据库中是否存在
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(qiHu360Request.getMerchant_id());
			if (CommUtils.isNull(orderChannel)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("merchant_id不存在");
				logger.info(sessionId + "：结束QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第一步：解密参数
			String biz_data = QiHu360Utils.decodeBizData(qiHu360Request);

			QueryRepaymentPlan queryRepaymentPlan = JSON.parseObject(biz_data, QueryRepaymentPlan.class);
			if (queryRepaymentPlan == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360Controller.queryRepaymentPlan method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第二步：处理业务
			qiHu360Response = qiHu360ServiceImpl.queryRepaymentPlan(sessionId, queryRepaymentPlan);
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360Controller.queryRepaymentPlan method 异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(sessionId + "：结束QiHu360Controller.queryRepaymentPlan method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 13.查询还款详情(code360)
	 *
	 * @param qiHu360Request
	 * @return QiHu360Response
	 */
	@ResponseBody
	@RequestMapping("/drainage/qihu360/queryRepayInfo.do")
	public QiHu360Response queryRepayInfo(QiHu360Request qiHu360Request) {
		long sessionId = System.currentTimeMillis();
		QiHu360Response qiHu360Response = new QiHu360Response();
		logger.info(sessionId + "：开始QiHu360Controller.queryRepayInfo method：" + JSON.toJSONString(qiHu360Request));
		try {
			// 校验参数
			String check = QiHu360Utils.checkParam(qiHu360Request);
			if (StringUtils.isNotBlank(check)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg(check);
				logger.info(sessionId + "：结束QiHu360Controller.pushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 商户号 - 查询数据库中是否存在
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(qiHu360Request.getMerchant_id());
			if (CommUtils.isNull(orderChannel)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("merchant_id不存在");
				logger.info(sessionId + "：结束QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第一步：解密参数
			String biz_data = QiHu360Utils.decodeBizData(qiHu360Request);

			QueryRepayInfo queryRepayInfo = JSON.parseObject(biz_data, QueryRepayInfo.class);
			if (queryRepayInfo == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(
						sessionId + "：结束QiHu360Controller.queryRepayInfo method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第二步：处理业务
			qiHu360Response = qiHu360ServiceImpl.queryRepayInfo(sessionId, queryRepayInfo);
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360Controller.queryRepayInfo method 异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(sessionId + "：结束QiHu360Controller.queryRepayInfo method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 14.推送用户还款信息(code360)
	 *
	 * @param qiHu360Request
	 * @return QiHu360Response
	 */
	@ResponseBody
	@RequestMapping("/drainage/qihu360/pushRepayInfo.do")
	public QiHu360Response pushRepayInfo(QiHu360Request qiHu360Request) {
		long sessionId = System.currentTimeMillis();
		QiHu360Response qiHu360Response = new QiHu360Response();
		logger.info(sessionId + "：开始QiHu360Controller.pushRepayInfo method：" + JSON.toJSONString(qiHu360Request));
		try {
			// 校验参数
			String check = QiHu360Utils.checkParam(qiHu360Request);
			if (StringUtils.isNotBlank(check)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg(check);
				logger.info(sessionId + "：结束QiHu360Controller.pushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 商户号 - 查询数据库中是否存在
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(qiHu360Request.getMerchant_id());
			if (CommUtils.isNull(orderChannel)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("merchant_id不存在");
				logger.info(sessionId + "：结束QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			// 第一步：解密参数
			String biz_data = QiHu360Utils.decodeBizData(qiHu360Request);

			PushUserRepayInfo pushUserRepayInfo = JSON.parseObject(biz_data, PushUserRepayInfo.class);
			if (pushUserRepayInfo == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(
						sessionId + "：结束QiHu360Controller.pushRepayInfo method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第二步：处理业务
			qiHu360Response = qiHu360ServiceImpl.pushUserRepayInfo(sessionId, pushUserRepayInfo);
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360Controller.pushRepayInfo method 异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(sessionId + "：结束QiHu360Controller.pushRepayInfo method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 15.查询订单状态(code360)
	 *
	 * @param qiHu360Request
	 * @return QiHu360Response
	 */
	@ResponseBody
	@RequestMapping("/drainage/qihu360/queryOrderStatus.do")
	public QiHu360Response queryOrderStatus(QiHu360Request qiHu360Request) {
		long sessionId = System.currentTimeMillis();
		QiHu360Response qiHu360Response = new QiHu360Response();
		logger.info(sessionId + "：开始QiHu360Controller.queryOrderStatus method：" + JSON.toJSONString(qiHu360Request));
		try {
			// 校验参数
			String check = QiHu360Utils.checkParam(qiHu360Request);
			if (StringUtils.isNotBlank(check)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg(check);
				logger.info(sessionId + "：结束QiHu360Controller.pushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 商户号 - 查询数据库中是否存在
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(qiHu360Request.getMerchant_id());
			if (CommUtils.isNull(orderChannel)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("merchant_id不存在");
				logger.info(sessionId + "：结束QiHu360Controller.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第一步：解密参数
			String biz_data = QiHu360Utils.decodeBizData(qiHu360Request);

			QueryOrderStatus queryOrderStatus = JSON.parseObject(biz_data, QueryOrderStatus.class);
			if (queryOrderStatus == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360Controller.queryOrderStatus method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第二步：处理业务
			qiHu360Response = qiHu360ServiceImpl.queryOrderStatus(sessionId, queryOrderStatus);
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360Controller.queryOrderStatus method 异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(sessionId + "：结束QiHu360Controller.queryOrderStatus method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}
}
