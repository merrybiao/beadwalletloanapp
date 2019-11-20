/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.utils.HttpClientHelper;
import com.waterelephant.drainage.entity.qihu360.AddInfo;
import com.waterelephant.drainage.entity.qihu360.AddInfoCredit;
import com.waterelephant.drainage.entity.qihu360.AddInfoCreditTrustScore;
import com.waterelephant.drainage.entity.qihu360.AddInfoMobile;
import com.waterelephant.drainage.entity.qihu360.AddInfoMobileTel;
import com.waterelephant.drainage.entity.qihu360.AddInfoMobileTelTeldata;
import com.waterelephant.drainage.entity.qihu360.AddInfoMobileUser;
import com.waterelephant.drainage.entity.qihu360.BaseInfo;
import com.waterelephant.drainage.entity.qihu360.ExtraInfoContacts;
import com.waterelephant.drainage.entity.qihu360.ExtraInfoContactsPhoneItem;
import com.waterelephant.drainage.entity.qihu360.OrderInfo;
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
import com.waterelephant.drainage.service.QiHu360Service;
import com.waterelephant.drainage.util.qihu360.QiHu360Constant;
import com.waterelephant.drainage.util.qihu360.QiHu360Utils;
import com.waterelephant.dto.SystemAuditDto;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBlacklist;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwContactList;
import com.waterelephant.entity.BwOperateBasic;
import com.waterelephant.entity.BwOperateVoice;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderProcessRecord;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.entity.BwZmxyGrade;
import com.waterelephant.service.BwBlacklistService;
import com.waterelephant.service.BwOperateBasicService;
import com.waterelephant.service.BwOperateVoiceService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.BwZmxyGradeService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwPersonInfoService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.IBwWorkInfoService;
import com.waterelephant.service.impl.BwContactListService;
import com.waterelephant.third.service.ThirdCommonService;
import com.waterelephant.third.utils.ThirdUtil;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.JsonUtils;
import com.waterelephant.utils.OrderUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;
import com.waterelephant.utils.UploadToCssUtils;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * 
 * Module:奇虎360 - (code360)
 * 
 * QiHu360ServiceImpl.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class QiHu360ServiceImpl implements QiHu360Service {
	private Logger logger = LoggerFactory.getLogger(QiHu360ServiceImpl.class);
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private BwBlacklistService bwBlacklistService;
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private BwRejectRecordService bwRejectRecordService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private ThirdCommonService thirdCommonService;
	@Autowired
	private IBwWorkInfoService bwWorkInfoService;
	@Autowired
	private BwZmxyGradeService bwZmxyGradeService;
	@Autowired
	private BwOperateBasicService bwOperateBasicService;
	@Autowired
	private BwOperateVoiceService bwOperateVoiceService;
	@Autowired
	private IBwPersonInfoService bwPersonInfoService;
	@Autowired
	private BwContactListService bwContactListService;
	@Autowired
	private BwOrderProcessRecordService bwOrderProcessRecordService;
	@Autowired
	private IBwAdjunctService bwAdjunctService;
	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;

	private static final String QIHU_360_CHANNEL = "channelId";

	/**
	 * 奇虎360 - 1.查询复贷和黑名单信息(code360)
	 * 
	 * @see com.waterelephant.drainage.service.QiHu360Service#checkUser(long,
	 *      com.waterelephant.drainage.entity.qihu360.PushUserInfo)
	 */
	@Override
	public QiHu360Response checkUser(long sessionId, PushUserInfo pushUserInfo) {
		logger.info(sessionId + "：开始QiHu360ServiceImpl.checkUser method：" + JSON.toJSONString(pushUserInfo));
		QiHu360Response qiHu360Response = new QiHu360Response();
		Map<String, Integer> data = new HashMap<>();

		try {
			String id_card = pushUserInfo.getId_card();
			String user_mobile = pushUserInfo.getUser_mobile();
			String user_name = pushUserInfo.getUser_name();

			if (StringUtils.isEmpty(id_card)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("身份证为空");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			if (StringUtils.isEmpty(user_mobile)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("电话为空");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			if (StringUtils.isEmpty(user_name)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("姓名为空");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 1判断是否存在借款人信息，如果不存在则是新用户,返回200+is_reloan=0，否则查询绑卡信息
			BwBorrower bwBorrower = bwBorrowerService.oldUserFilter(user_mobile.substring(0, user_mobile.length() - 4),
					id_card.substring(0, id_card.length() - 4), user_name);

			if (CommUtils.isNull(bwBorrower)) {
				qiHu360Response.setCode(QiHu360Response.CODE_SUCCESS);
				qiHu360Response.setMsg("可以申请");
				data.put("is_reloan", 0);
				qiHu360Response.setData(data);
				logger.info(sessionId + "：结束QiHu360ServiceImpl.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 2判断是否有绑卡成功，如果没有则是新用户,返回200+is_reloan=0，如果有则查询是否有黑名单，正在进行中的订单，被拒绝记录
			BwBankCard bwBankCard = new BwBankCard();
			bwBankCard.setBorrowerId(bwBorrower.getId());
			bwBankCard.setSignStatus(2);
			bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
			if (CommUtils.isNull(bwBankCard)) {
				qiHu360Response.setCode(QiHu360Response.CODE_SUCCESS);
				qiHu360Response.setMsg("可以申请");
				data.put("is_reloan", 0);
				qiHu360Response.setData(data);
				logger.info(sessionId + "：结束QiHu360ServiceImpl.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 3判断是否是黑名单，如果存在则返回400，如果没有则查询是否有进行中的订单
			Example example = new Example(BwBlacklist.class);
			String idNo = bwBorrower.getIdCard();
			example.createCriteria().andEqualTo("sort", 1).andEqualTo("status", 1).andEqualTo("card",
					idNo.toUpperCase());
			List<BwBlacklist> desList = bwBlacklistService.findBwBlacklistByExample(example);
			if (!CommUtils.isNull(desList)) {
				qiHu360Response.setCode(QiHu360Response.CODE_NOTLOAN);
				qiHu360Response.setMsg("不可申请");
				qiHu360Response.setReason("C002");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 4查询是否有进行中的订单，如果存在则返回400，否则查询是否有被拒记录
			Long count = bwOrderService.findProOrder(String.valueOf(bwBorrower.getId()));
			if (!CommUtils.isNull(count) && count.intValue() > 0) {
				qiHu360Response.setCode(QiHu360Response.CODE_NOTLOAN);
				qiHu360Response.setMsg("不可申请");
				qiHu360Response.setReason("C001");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.checkUser method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 5查询是否有被拒记录，如果有则判断被拒类型，如果是永久拒绝则返回400，如果是临时拒绝则判断是否到期
			BwRejectRecord record = bwRejectRecordService.findBwRejectRecordByBid(bwBorrower.getId());
			if (!CommUtils.isNull(record)) {
				// 永久拒绝
				if ("0".equals(String.valueOf(record.getRejectType()))) {
					qiHu360Response.setCode(QiHu360Response.CODE_NOTLOAN);
					qiHu360Response.setMsg("永久拒绝");
					qiHu360Response.setReason("C003");
					logger.info(
							sessionId + "：结束QiHu360ServiceImpl.checkUser method：" + JSON.toJSONString(qiHu360Response));
					return qiHu360Response;
				} else {
					Date rejectDate = record.getCreateTime();
					long day = (Calendar.getInstance().getTime().getTime() - rejectDate.getTime())
							/ (24 * 60 * 60 * 1000);
					if (day <= 7) {
						qiHu360Response.setCode(QiHu360Response.CODE_NOTLOAN);
						qiHu360Response.setMsg("不可申请");
						qiHu360Response.setReason("C003");
						logger.info(sessionId + "：结束QiHu360ServiceImpl.checkUser method："
								+ JSON.toJSONString(qiHu360Response));
						return qiHu360Response;
					}
				}
			}

			data.put("is_reloan", 0);
			qiHu360Response.setCode(QiHu360Response.CODE_SUCCESS);
			qiHu360Response.setMsg("可以申请");
			qiHu360Response.setData(data);

		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360ServiceImpl.checkUser method：异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(sessionId + "：结束QiHu360ServiceImpl.checkUser method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 3.推送用户借款基本信息(code360)
	 * 
	 * @see com.waterelephant.drainage.service.QiHu360Service#pushLoanBaseInfo(long,
	 *      com.waterelephant.drainage.entity.qihu360.PushUserLoanBasicInfo)
	 */
	@Override
	public QiHu360Response savePushLoanBaseInfo(long sessionId, PushUserLoanBasicInfo pushUserLoanBasicInfo) {
		logger.info(sessionId + "：开始QiHu360ServiceImpl.savePushLoanBaseInfo method："
				+ JSON.toJSONString(pushUserLoanBasicInfo));
		QiHu360Response qiHu360Response = new QiHu360Response();
		try {
			OrderInfo orderInfo = pushUserLoanBasicInfo.getOrderInfo();
			if (orderInfo == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第一步：判断奇虎360订单是否已存在
			String thirdOrderNo = orderInfo.getOrder_no();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (bwOrderRong != null) {
				qiHu360Response.setCode(QiHu360Response.CODE_DUPLICATECALL);
				qiHu360Response.setMsg("第三方订单已存在，请勿重复推送");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			BaseInfo baseInfo = pushUserLoanBasicInfo.getApplyDetail();
			if (baseInfo == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第二步：新增或修改借款人
			String channelId = QiHu360Constant.get(QIHU_360_CHANNEL); // 渠道ID
			String name = orderInfo.getUser_name(); // 姓名
			String phone = orderInfo.getUser_mobile();// 注册手机号
			String idCard = baseInfo.getUser_id(); // 身份证号
			BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, name, idCard, phone,
					Integer.parseInt(channelId));

			// 第三步：查询是否有进行中的订单
			long count = bwOrderService.findProOrder(String.valueOf(borrower.getId()));
			if (count > 0) {
				qiHu360Response.setCode(QiHu360Response.CODE_DUPLICATECALL);
				qiHu360Response.setMsg("存在进行中的订单，请勿重复推送");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 获得产品
			BwProductDictionary bwProductDictionary = thirdCommonService.getProduct(name, idCard, phone,
					orderInfo.getApplication_term());
			logger.info("========productId=" + bwProductDictionary.getId());

			// 第四步：判断是否有草稿状态的订单
			BwOrder bwOrder = new BwOrder();
			bwOrder.setBorrowerId(borrower.getId());
			bwOrder.setStatusId(1L);
			bwOrder.setProductType(1);
			List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);
			if (boList != null && boList.size() > 0) {
				bwOrder = boList.get(0);
				bwOrder.setChannel(Integer.valueOf(channelId));
				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
				bwOrder.setProductType(1);
				bwOrder.setProductId(bwProductDictionary.getId().intValue());
				bwOrder.setExpectMoney(Double.valueOf(orderInfo.getApplication_amount()));
				bwOrder.setSubmitTime(Calendar.getInstance().getTime());
				bwOrderService.updateBwOrder(bwOrder);
			} else {
				bwOrder = new BwOrder();
				bwOrder.setOrderNo(OrderUtil.generateOrderNo());
				bwOrder.setBorrowerId(borrower.getId());
				bwOrder.setStatusId(1L);
				bwOrder.setCreateTime(Calendar.getInstance().getTime());
				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
				bwOrder.setChannel(Integer.valueOf(channelId));
				bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
				bwOrder.setApplyPayStatus(0);
				bwOrder.setProductId(bwProductDictionary.getId().intValue());
				bwOrder.setProductType(1);
				bwOrder.setExpectMoney(Double.valueOf(orderInfo.getApplication_amount()));
				bwOrder.setSubmitTime(Calendar.getInstance().getTime());
				bwOrderService.addBwOrder(bwOrder);
			}

			// 第五步：判断是否有奇虎360订单
			BwOrderRong bwOrderRong2 = new BwOrderRong();
			bwOrderRong2.setOrderId(bwOrder.getId());
			bwOrderRong2 = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong2);
			if (bwOrderRong2 == null) {
				bwOrderRong2 = new BwOrderRong();
				bwOrderRong2.setOrderId(bwOrder.getId());
				bwOrderRong2.setThirdOrderNo(thirdOrderNo);
				bwOrderRong2.setChannelId(Long.valueOf(channelId));
				bwOrderRong2.setCreateTime(Calendar.getInstance().getTime());
				bwOrderRongService.save(bwOrderRong2);
			} else {
				bwOrderRong2.setChannelId(Long.valueOf(channelId));
				bwOrderRong2.setThirdOrderNo(thirdOrderNo);
				bwOrderRongService.update(bwOrderRong2);
			}

			// 第六步：工作信息
			BwWorkInfo bwWorkInfo = new BwWorkInfo();
			bwWorkInfo.setOrderId(bwOrder.getId());
			bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
			if (bwWorkInfo == null) {
				bwWorkInfo = new BwWorkInfo();
				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
				bwWorkInfo.setCreateTime(Calendar.getInstance().getTime());
				bwWorkInfo.setWorkYears(String.valueOf(baseInfo.getWork_period()));
				bwWorkInfo.setIncome(baseInfo.getUser_income_by_card());
				bwWorkInfo.setOrderId(bwOrder.getId());
				bwWorkInfoService.save(bwWorkInfo, borrower.getId());
			} else {
				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
				bwWorkInfo.setWorkYears(String.valueOf(baseInfo.getWork_period()));
				bwWorkInfo.setIncome(baseInfo.getUser_income_by_card());
				bwWorkInfoService.update(bwWorkInfo);
			}

			AddInfo addInfo = pushUserLoanBasicInfo.getAddInfo();
			if (addInfo == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第七步：芝麻信用
			AddInfoCredit addInfoCredit = addInfo.getCredit();
			if (addInfoCredit == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			AddInfoCreditTrustScore trustScore = addInfoCredit.getTrust_score();
			if (!CommUtils.isNull(trustScore)) {
				BwZmxyGrade bwZmxyGrade = bwZmxyGradeService.findZmxyGradeByBorrowerId(borrower.getId());
				if (bwZmxyGrade == null) {
					bwZmxyGrade = new BwZmxyGrade();
					bwZmxyGrade.setBorrowerId(borrower.getId());
					bwZmxyGrade.setZmScore(trustScore.getScore());
					bwZmxyGrade.setCreateTime(Calendar.getInstance().getTime());
					bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
					bwZmxyGradeService.saveBwZmxyGrade(bwZmxyGrade);
				} else {
					bwZmxyGrade.setZmScore(trustScore.getScore());
					bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
					bwZmxyGradeService.updateBwZmxyGrade(bwZmxyGrade);
				}
				thirdCommonService.addOrUpdateBwOrderAuth(sessionId, bwOrder.getId(), 4, Integer.valueOf(channelId));// 插入芝麻认证记录
			}

			// 第八步：运营商
			AddInfoMobile addInfoMobile = addInfo.getMobile();
			if (addInfoMobile == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			AddInfoMobileUser addInfoMobileUser = addInfoMobile.getUser();
			if (addInfoMobileUser == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushLoanBaseInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrower.getId());
			if (bwOperateBasic == null) {
				bwOperateBasic = new BwOperateBasic();
				bwOperateBasic.setBorrowerId(borrower.getId());
				bwOperateBasic.setUserSource(addInfoMobileUser.getUser_source());
				bwOperateBasic.setIdCard(
						CommUtils.isNull(addInfoMobileUser.getId_card()) ? "" : addInfoMobileUser.getId_card());
				bwOperateBasic
						.setAddr(CommUtils.isNull(addInfoMobileUser.getAddr()) ? "" : addInfoMobileUser.getAddr());
				bwOperateBasic.setRealName(
						CommUtils.isNull(addInfoMobileUser.getReal_name()) ? "" : addInfoMobileUser.getReal_name());
				bwOperateBasic.setPhoneRemain(CommUtils.isNull(addInfoMobileUser.getPhone_remain()) ? ""
						: addInfoMobileUser.getPhone_remain());
				bwOperateBasic
						.setPhone(CommUtils.isNull(addInfoMobileUser.getPhone()) ? "" : addInfoMobileUser.getPhone());
				if (CommUtils.isNull(addInfoMobileUser.getReg_time()) == false) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					bwOperateBasic.setRegTime(sdf.parse(addInfoMobileUser.getReg_time()));
				}
				bwOperateBasic
						.setScore(CommUtils.isNull(addInfoMobileUser.getScore()) ? "" : addInfoMobileUser.getScore());
				bwOperateBasic.setContactPhone(CommUtils.isNull(addInfoMobileUser.getContact_phone()) ? ""
						: addInfoMobileUser.getContact_phone());
				bwOperateBasic.setStarLevel(
						CommUtils.isNull(addInfoMobileUser.getStar_level()) ? "" : addInfoMobileUser.getStar_level());
				bwOperateBasic.setAuthentication(CommUtils.isNull(addInfoMobileUser.getAuthentication()) ? ""
						: addInfoMobileUser.getAuthentication());
				bwOperateBasic.setPhoneStatus(CommUtils.isNull(addInfoMobileUser.getPhone_status()) ? ""
						: addInfoMobileUser.getPhone_status());
				bwOperateBasic.setPackageName(CommUtils.isNull(addInfoMobileUser.getPackage_name()) ? ""
						: addInfoMobileUser.getPackage_name());
				bwOperateBasic.setCreateTime(new Date());
				bwOperateBasic.setUpdateTime(new Date());

				bwOperateBasicService.save(bwOperateBasic);
			} else {
				bwOperateBasic.setBorrowerId(borrower.getId());
				bwOperateBasic.setUserSource(addInfoMobileUser.getUser_source());
				bwOperateBasic.setIdCard(
						CommUtils.isNull(addInfoMobileUser.getId_card()) ? "" : addInfoMobileUser.getId_card());
				bwOperateBasic
						.setAddr(CommUtils.isNull(addInfoMobileUser.getAddr()) ? "" : addInfoMobileUser.getAddr());
				bwOperateBasic.setRealName(
						CommUtils.isNull(addInfoMobileUser.getReal_name()) ? "" : addInfoMobileUser.getReal_name());
				bwOperateBasic.setPhoneRemain(CommUtils.isNull(addInfoMobileUser.getPhone_remain()) ? ""
						: addInfoMobileUser.getPhone_remain());
				bwOperateBasic
						.setPhone(CommUtils.isNull(addInfoMobileUser.getPhone()) ? "" : addInfoMobileUser.getPhone());
				if (CommUtils.isNull(addInfoMobileUser.getReg_time()) == false) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					bwOperateBasic.setRegTime(sdf.parse(addInfoMobileUser.getReg_time()));
				}
				bwOperateBasic
						.setScore(CommUtils.isNull(addInfoMobileUser.getScore()) ? "" : addInfoMobileUser.getScore());
				bwOperateBasic.setContactPhone(CommUtils.isNull(addInfoMobileUser.getContact_phone()) ? ""
						: addInfoMobileUser.getContact_phone());
				bwOperateBasic.setStarLevel(
						CommUtils.isNull(addInfoMobileUser.getStar_level()) ? "" : addInfoMobileUser.getStar_level());
				bwOperateBasic.setAuthentication(CommUtils.isNull(addInfoMobileUser.getAuthentication()) ? ""
						: addInfoMobileUser.getAuthentication());
				bwOperateBasic.setPhoneStatus(CommUtils.isNull(addInfoMobileUser.getPhone_status()) ? ""
						: addInfoMobileUser.getPhone_status());
				bwOperateBasic.setPackageName(CommUtils.isNull(addInfoMobileUser.getPackage_name()) ? ""
						: addInfoMobileUser.getPackage_name());
				bwOperateBasic.setUpdateTime(new Date());

				bwOperateBasicService.update(bwOperateBasic);
			}

			// 第九步：通话记录
			Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrower.getId());
			SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
			AddInfoMobileTel addInfoMobileTel = addInfoMobile.getTel();
			if (addInfoMobileTel != null) {
				List<AddInfoMobileTelTeldata> addInfoMobileTelTeldatas = addInfoMobileTel.getTeldata();
				if (addInfoMobileTelTeldatas != null) {
					for (AddInfoMobileTelTeldata addInfoMobileTelTeldata : addInfoMobileTelTeldatas) {
						if (addInfoMobileTelTeldata != null) {
							try {
								Date jsonCallData = sdf_hms.parse(addInfoMobileTelTeldata.getCall_time());
								if (callDate == null || jsonCallData.after(callDate)) { // 通话记录采取最新追加的方式
									BwOperateVoice bwOperateVoice = new BwOperateVoice();
									bwOperateVoice.setUpdateTime(new Date());
									bwOperateVoice.setBorrower_id(borrower.getId());
									bwOperateVoice
											.setTrade_type(Integer.valueOf(addInfoMobileTelTeldata.getTrade_type()));
									bwOperateVoice.setTrade_time(addInfoMobileTelTeldata.getTrade_time());
									bwOperateVoice.setCall_time(addInfoMobileTelTeldata.getCall_time());
									bwOperateVoice.setTrade_addr(addInfoMobileTelTeldata.getTrade_addr());
									bwOperateVoice.setReceive_phone(addInfoMobileTelTeldata.getReceive_phone());
									bwOperateVoice
											.setCall_type(Integer.valueOf(addInfoMobileTelTeldata.getCall_type()));

									bwOperateVoiceService.save(bwOperateVoice);
								}
							} catch (Exception e) {
								logger.error(sessionId + "插入单条通话记录异常，忽略该条记录");
							}
						}
					}
				}
			}

			// 第十步：短信信息
			// 第十一步：月账单记录
			// 第十二步：流量信息
			// 第十三步：充值记录

			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, bwOrder.getId(), 1, Integer.valueOf(channelId));// 插入运营商认证记录

			qiHu360Response.setCode(QiHu360Response.CODE_SUCCESS);
			qiHu360Response.setMsg("请求成功");
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360ServiceImpl.savePushLoanBaseInfo method：异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(
				sessionId + "：结束QiHu360ServiceImpl.savePushLoanBaseInfo method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 4.推送用户借款补充信息(code360)
	 * 
	 * @see com.waterelephant.drainage.service.QiHu360Service#savePushLoanAddInfo(long,
	 *      com.waterelephant.drainage.entity.qihu360.PushUserLoanAddInfo)
	 */
	@Override
	public QiHu360Response savePushLoanAddInfo(long sessionId, PushUserLoanAddInfo pushUserLoanAddInfo) {
		logger.info(sessionId + "：开始QiHu360ServiceImpl.savePushLoanAddInfo method："
				+ JSON.toJSONString(pushUserLoanAddInfo));
		QiHu360Response qiHu360Response = new QiHu360Response();
		try {
			String thirdOrderNo = pushUserLoanAddInfo.getOrder_no();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushLoanAddInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (bwOrderRong == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单不存在");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushLoanAddInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
			// 如果当前定单不存在表示定单基本信息未推送
			if (CommUtils.isNull(bwOrder)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("基本信息未填写");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushLoanAddInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第一步：身份证信息
			List<String> positive = pushUserLoanAddInfo.getID_Positive();
			String front = positive.get(positive.size() - 1);
			List<String> negative = pushUserLoanAddInfo.getID_Negative();
			String back = negative.get(negative.size() - 1);
			List<String> photoHand = pushUserLoanAddInfo.getPhoto_hand_ID();
			String hand = photoHand.get(photoHand.size() - 1);

			String frontImage = UploadToCssUtils.urlUpload(front, bwOrder.getId() + "_01"); // 身份证正面照
			logger.info("======身份证正面照" + frontImage);
			String backImage = UploadToCssUtils.urlUpload(back, bwOrder.getId() + "_02"); // 身份证反面照
			logger.info("======身份证反面照" + backImage);
			String handerImage = UploadToCssUtils.urlUpload(hand, bwOrder.getId() + "_03"); // 手持照
			logger.info("======手持照" + handerImage);

			thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, bwOrder.getId(),
					bwOrder.getBorrowerId(), 0); // 保存身份证正面照
			thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, bwOrder.getId(),
					bwOrder.getBorrowerId(), 0); // 保存身份证反面照
			thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, bwOrder.getId(),
					bwOrder.getBorrowerId(), 0); // 保存手持照

			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, bwOrder.getId(), 3, bwOrder.getChannel());// 插入身份认证记录

			// 第二步：更新公司信息
			BwWorkInfo bwWorkInfo = new BwWorkInfo();
			bwWorkInfo.setOrderId(bwOrderRong.getOrderId());
			bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
			bwWorkInfo.setComName(pushUserLoanAddInfo.getCompany_name());
			bwWorkInfo.setIndustry(QiHu360Utils.getIndustry(String.valueOf(pushUserLoanAddInfo.getIndustry_type())));
			bwWorkInfo.setUpdateTime(new Date());
			bwWorkInfoService.update(bwWorkInfo);

			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, bwOrder.getId(), 2, bwOrder.getChannel()); // 插入个人认证记录

			// 第三步：亲属联系人
			BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(bwOrder.getId());
			if (bwPersonInfo == null) {
				bwPersonInfo = new BwPersonInfo();
				bwPersonInfo.setAddress(pushUserLoanAddInfo.getAddr_detail());
				bwPersonInfo.setCarStatus(
						Integer.valueOf(QiHu360Utils.getCar(String.valueOf(pushUserLoanAddInfo.getAsset_auto_type()))));
				bwPersonInfo.setEmail(pushUserLoanAddInfo.getUser_email());
				bwPersonInfo.setHouseStatus(Integer
						.valueOf(QiHu360Utils.getHouse(String.valueOf(pushUserLoanAddInfo.getFamily_live_type()))));
				bwPersonInfo.setMarryStatus(
						Integer.valueOf(QiHu360Utils.getMarry(String.valueOf(pushUserLoanAddInfo.getUser_marriage()))));
				bwPersonInfo.setRelationName(pushUserLoanAddInfo.getContact1A_name());
				bwPersonInfo.setRelationPhone(pushUserLoanAddInfo.getContact1A_number());
				bwPersonInfo.setUnrelationName(pushUserLoanAddInfo.getEmergency_contact_personA_name());
				bwPersonInfo.setUnrelationPhone(pushUserLoanAddInfo.getEmergency_contact_personA_phone());
				bwPersonInfo.setOrderId(bwOrder.getId());
				bwPersonInfo.setCreateTime(new Date());
				bwPersonInfo.setUpdateTime(new Date());

				bwPersonInfoService.add(bwPersonInfo);
			} else {
				bwPersonInfo.setAddress(pushUserLoanAddInfo.getAddr_detail());
				bwPersonInfo.setCarStatus(
						Integer.valueOf(QiHu360Utils.getCar(String.valueOf(pushUserLoanAddInfo.getAsset_auto_type()))));
				bwPersonInfo.setEmail(pushUserLoanAddInfo.getUser_email());
				bwPersonInfo.setHouseStatus(Integer
						.valueOf(QiHu360Utils.getHouse(String.valueOf(pushUserLoanAddInfo.getFamily_live_type()))));
				bwPersonInfo.setMarryStatus(
						Integer.valueOf(QiHu360Utils.getMarry(String.valueOf(pushUserLoanAddInfo.getUser_marriage()))));
				bwPersonInfo.setRelationName(pushUserLoanAddInfo.getContact1A_name());
				bwPersonInfo.setRelationPhone(pushUserLoanAddInfo.getContact1A_number());
				bwPersonInfo.setUnrelationName(pushUserLoanAddInfo.getEmergency_contact_personA_name());
				bwPersonInfo.setUnrelationPhone(pushUserLoanAddInfo.getEmergency_contact_personA_phone());
				bwPersonInfo.setOrderId(bwOrder.getId());
				bwPersonInfo.setUpdateTime(new Date());

				bwPersonInfoService.update(bwPersonInfo);
			}

			ExtraInfoContacts contacts = pushUserLoanAddInfo.getContacts();
			if (contacts == null) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("入参不合法");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushLoanAddInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 第四步：通讯录
			List<ExtraInfoContactsPhoneItem> phoneList = contacts.getPhone_list();
			List<BwContactList> list = new ArrayList<>();
			if (phoneList != null && phoneList.size() > 0) {
				for (ExtraInfoContactsPhoneItem phoneContact : phoneList) {
					if (CommUtils.isNull(phoneContact.getName())) {
						continue;
					}
					if (CommUtils.isNull(phoneContact.getPhone())) {
						continue;
					}
					BwContactList bwContactList = new BwContactList();
					bwContactList.setBorrowerId(bwOrder.getBorrowerId());
					bwContactList.setPhone(phoneContact.getPhone());
					bwContactList.setName(phoneContact.getName());
					list.add(bwContactList);
				}
				bwContactListService.addOrUpdateBwContactLists(list, bwOrder.getBorrowerId());
			}

			qiHu360Response.setCode(QiHu360Response.CODE_SUCCESS);
			qiHu360Response.setMsg("请求成功");
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360ServiceImpl.savePushLoanAddInfo method：异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(
				sessionId + "：结束QiHu360ServiceImpl.savePushLoanAddInfo method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 6.推送用户绑定银行卡 （1.2）跳转机构页面绑卡(code360)
	 * 
	 * @see com.waterelephant.drainage.service.QiHu360Service#savePushUserBankCard(long,
	 *      com.waterelephant.drainage.entity.qihu360.PushUserBankCard)
	 */
	@Override
	public QiHu360Response savePushUserBankCard(long sessionId, PushUserBankCard pushUserBankCard) {
		logger.info(
				sessionId + "：开始QiHu360ServiceImpl.savePushUserBankCard method：" + JSON.toJSONString(pushUserBankCard));
		QiHu360Response qiHu360Response = new QiHu360Response();
		try {
			String thirdOrderNo = pushUserBankCard.getOrder_no();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushUserBankCard method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 根据第三方订单编号获取订单id
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushUserBankCard method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			Long orderId = bwOrderRong.getOrderId();

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("订单为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.savePushUserBankCard method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 获取银行卡信息
			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
			if (CommUtils.isNull(bwBankCard)) {
				bwBankCard = new BwBankCard();
				bwBankCard.setBankName(pushUserBankCard.getOpen_bank());
				bwBankCard.setBankCode(ThirdUtil.convertToBankCode(pushUserBankCard.getOpen_bank()));
				bwBankCard.setCardNo(pushUserBankCard.getBank_card());
				bwBankCard.setPhone(pushUserBankCard.getUser_mobile());
				bwBankCard.setSignStatus(0);
				bwBankCard.setBorrowerId(bwOrder.getBorrowerId());
				bwBankCard.setCreateTime(Calendar.getInstance().getTime());
				bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
				bwBankCardService.saveBwBankCard(bwBankCard, bwOrder.getBorrowerId());
			} else if (!CommUtils.isNull(bwBankCard) && 0 == bwBankCard.getSignStatus()) {
				bwBankCard.setBankName(pushUserBankCard.getOpen_bank());
				bwBankCard.setBankCode(ThirdUtil.convertToBankCode(pushUserBankCard.getOpen_bank()));
				bwBankCard.setCardNo(pushUserBankCard.getBank_card());
				bwBankCard.setPhone(pushUserBankCard.getUser_mobile());
				bwBankCard.setSignStatus(0);
				bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
				bwBankCardService.updateBwBankCard(bwBankCard);
			} else if (!CommUtils.isNull(bwBankCard) && 2 == bwBankCard.getSignStatus()) {
				// 修改认证状态为4
				BwBorrower borrower = new BwBorrower();
				borrower.setId(bwOrder.getBorrowerId());
				borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
				borrower.setAuthStep(4);
				bwBorrowerService.updateBwBorrower(borrower);

				// 修改工单状态为2L
				bwOrder.setStatusId(2L);
				bwOrder.setSubmitTime(Calendar.getInstance().getTime());
				bwOrderService.updateBwOrder(bwOrder);

				// 第三方通知-------------code0093
				logger.info("初审===" + bwOrder.getId());
				HashMap<String, String> hm = new HashMap<>();
				hm.put("channelId", CommUtils.toString(bwOrder.getChannel()));
				hm.put("orderId", String.valueOf(bwOrder.getId()));
				hm.put("orderStatus", "2");
				hm.put("result", "审核");
				String hmData = JSON.toJSONString(hm);
				RedisUtils.rpush("tripartite:orderStatusNotify:" + bwOrder.getChannel(), hmData);

				// 修改订单提交时间
				BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
				bwOrderProcessRecord.setOrderId(bwOrder.getId());
				bwOrderProcessRecord.setSubmitTime(new Date());
				bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);

				// 将待审核信息放入Redis中
				SystemAuditDto systemAuditDto = new SystemAuditDto();
				systemAuditDto.setIncludeAddressBook(0);
				systemAuditDto.setOrderId(bwOrder.getId());
				systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
				systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
				systemAuditDto.setName(borrower.getName());
				systemAuditDto.setPhone(borrower.getPhone());
				systemAuditDto.setIdCard(borrower.getIdCard());
				systemAuditDto.setChannel(bwOrderRong.getChannelId().intValue());
				systemAuditDto.setThirdOrderId(bwOrderRong.getThirdOrderNo());
				RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
						JsonUtils.toJson(systemAuditDto));
			}

			RedisUtils.hset("third:bindCard:successReturnUrl:" + bwOrder.getChannel(),
					"orderNO_" + bwOrder.getOrderNo(), pushUserBankCard.getReturn_url());
			RedisUtils.hset("third:bindCard:failReturnUrl:" + bwOrder.getChannel(), "orderNO_" + bwOrder.getOrderNo(),
					pushUserBankCard.getReturn_url());

			Map<String, String> data = new HashMap<>();
			String url = SystemConstant.NOTIRY_URL + "/bindCard/common/bindCard.do?borrowerId="
					+ bwOrder.getBorrowerId() + "&orderNO=" + bwOrder.getOrderNo() + "&bankCardNO="
					+ pushUserBankCard.getBank_card();

			if (!CommUtils.isNull(bwBankCard) && 2 == bwBankCard.getSignStatus()) {
				data.put("bind_card_url", pushUserBankCard.getReturn_url());
			} else {
				data.put("bind_card_url", url);
			}

			qiHu360Response.setCode(QiHu360Response.CODE_SUCCESS);
			qiHu360Response.setMsg("请求成功");
			qiHu360Response.setData(data);
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360ServiceImpl.savePushUserBankCard method：异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(
				sessionId + "：结束QiHu360ServiceImpl.savePushUserBankCard method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 8.查询审批结论(code360)
	 * 
	 * @see com.waterelephant.drainage.service.QiHu360Service#saveQueryApprovalStatus(long,
	 *      com.waterelephant.drainage.entity.qihu360.QueryApprovalStatus)
	 */
	@Override
	public QiHu360Response queryApprovalStatus(long sessionId, QueryApprovalStatus queryApprovalStatus) {
		logger.info(sessionId + "：开始QiHu360ServiceImpl.queryApprovalStatus method："
				+ JSON.toJSONString(queryApprovalStatus));
		QiHu360Response qiHu360Response = new QiHu360Response();
		try {
			String thirdOrderNo = queryApprovalStatus.getOrder_no();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.queryApprovalStatus method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 根据第三方订单编号获取订单id
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.queryApprovalStatus method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			Long orderId = bwOrderRong.getOrderId();

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("订单为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.queryApprovalStatus method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			String approveStatus = QiHu360Utils.getApprovalStatus(String.valueOf(bwOrder.getStatusId()));
			logger.info("映射为奇虎360审批状态为：" + approveStatus);

			Map<String, String> map = new HashMap<>();

			if ("10".equals(approveStatus)) {

				BwProductDictionary bwProductDictionary = bwProductDictionaryService
						.findBwProductDictionaryById(bwOrder.getProductId());

				map.put("order_no", thirdOrderNo);
				map.put("conclusion", approveStatus);
				map.put("approval_time", String.valueOf(bwOrder.getUpdateTime().getTime() / 1000));

				map.put("term_unit", "1");
				map.put("amount_type", "0");
				map.put("term_type", "0");
				map.put("approval_amount", String.valueOf(bwOrder.getBorrowAmount()));
				map.put("approval_term", bwProductDictionary.getpTerm());
			} else if ("40".equals(approveStatus)) {
				map.put("order_no", thirdOrderNo);
				map.put("conclusion", approveStatus);
				map.put("remark", "信用评分过低#拒绝客户");
				map.put("refuse_time", String.valueOf(bwOrder.getUpdateTime().getTime() / 1000));

			}

			qiHu360Response.setCode(QiHu360Response.CODE_SUCCESS);
			qiHu360Response.setMsg("请求成功");
			qiHu360Response.setData(map);
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360ServiceImpl.queryApprovalStatus method：异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(
				sessionId + "：结束QiHu360ServiceImpl.queryApprovalStatus method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 9.试算接口(code360)
	 * 
	 * @see com.waterelephant.drainage.service.QiHu360Service#trial(long,
	 *      com.waterelephant.drainage.entity.qihu360.TrialInfo)
	 */
	@Override
	public QiHu360Response trial(long sessionId, TrialInfo trialInfo) {
		logger.info(sessionId + "：开始QiHu360ServiceImpl.trial method：" + JSON.toJSONString(trialInfo));
		QiHu360Response qiHu360Response = new QiHu360Response();
		try {
			String thirdOrderNo = trialInfo.getOrder_no();
			String loanAmount = trialInfo.getLoan_amount();
			Integer loanTerm = trialInfo.getLoan_term();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.trial method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			if (StringUtils.isEmpty(loanAmount)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("借款金额为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.trial method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			if (CommUtils.isNull(loanTerm)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("借款期限为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.trial method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 根据第三方订单编号获取订单id
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.trial method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			Long orderId = bwOrderRong.getOrderId();

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("订单为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.trial method：" + JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// // 获取付款信息
			// PaymentRespDto paymentRespDto = productService.getNeedPaymentInfo(orderId);
			// // 到账金额
			// Double arrivelAmount = paymentRespDto.getArrivelAmount();
			// // 预扣除手续费
			// Double paymentFee = paymentRespDto.getPaymentFee();
			// // 总还款额
			// Double realityRepayMoney = paymentRespDto.getRealityRepayMoney();

			long productId = 0;
			if (loanTerm == 30) {
				productId = 3;
			} else {
				productId = 5;
			}
			// 获取利率字典表信息
			BwProductDictionary bwProductDictionary = bwProductDictionaryService.findById(productId);
			Double fee = bwProductDictionary.getpFastReviewCost() + bwProductDictionary.getpPlatformUseCost()
					+ bwProductDictionary.getpNumberManageCost() + bwProductDictionary.getpCapitalUseCost()
					+ bwProductDictionary.getpCollectionPassagewayCost();
			Double zjw = bwProductDictionary.getZjwCost();
			Double loan_amount = Double.valueOf(loanAmount);

			Map<String, String> data = new HashMap<>();
			data.put("receive_amount", String.valueOf(loan_amount - loan_amount * fee));
			data.put("service_fee", String.valueOf(loan_amount * fee));
			data.put("pay_amount", String.valueOf(loan_amount + loan_amount * zjw));

			qiHu360Response.setCode(QiHu360Response.CODE_SUCCESS);
			qiHu360Response.setMsg("请求成功");
			qiHu360Response.setData(data);

		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360ServiceImpl.trial method：异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(sessionId + "：结束QiHu360ServiceImpl.trial method：" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 10.推送用户确认收款信息(code360)
	 * 
	 * @see com.waterelephant.drainage.service.QiHu360Service#pushApprovalConfirm(long,
	 *      com.waterelephant.drainage.entity.qihu360.PushApprovalConfirmInfo)
	 */
	@Override
	public QiHu360Response updatePushApprovalConfirm(long sessionId, PushApprovalConfirmInfo pushApprovalConfirmInfo) {
		logger.info(sessionId + "：开始QiHu360ServiceImpl.queryApprovalStatus method："
				+ JSON.toJSONString(pushApprovalConfirmInfo));
		QiHu360Response qiHu360Response = new QiHu360Response();
		try {
			String thirdOrderNo = pushApprovalConfirmInfo.getOrder_no();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.pushApprovalConfirmInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 根据第三方订单编号获取订单id
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.pushApprovalConfirmInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			Long orderId = bwOrderRong.getOrderId();

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("订单为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.pushApprovalConfirmInfo method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 获取利率字典表信息
			BwProductDictionary bwProductDictionary = bwProductDictionaryService
					.findBwProductDictionaryById(bwOrder.getProductId());

			if (bwOrder.getStatusId() == 4) {// 待签约
				bwOrder.setRepayTerm(Integer.parseInt(bwProductDictionary.getpTerm()));
				bwOrder.setRepayType(1);
				bwOrder.setBorrowRate(bwProductDictionary.getpInvestRateMonth());
				bwOrder.setContractRate(bwProductDictionary.getpInvesstRateYear());
				bwOrder.setContractMonthRate(bwProductDictionary.getpBorrowRateMonth());
				bwOrder.setStatusId(11L);
				bwOrder.setUpdateTime(new Date());
				bwOrderService.updateBwOrder(bwOrder);

				// 第三方通知-------------code0093
				logger.info("签约成功===" + bwOrder.getId());
				HashMap<String, String> hm = new HashMap<>();
				hm.put("channelId", CommUtils.toString(bwOrder.getChannel()));
				hm.put("orderId", String.valueOf(bwOrder.getId()));
				hm.put("orderStatus", "11");
				hm.put("result", "签约成功");
				String hmData = JSON.toJSONString(hm);
				// 存入Redis中，通知第三方机构
				RedisUtils.rpush("tripartite:orderStatusNotify:" + bwOrder.getChannel(), hmData);

				// 生成合同
				RedisUtils.rpush("system:contract", String.valueOf(orderId));

				BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
				bwOrderProcessRecord.setOrderId(bwOrder.getId());
				bwOrderProcessRecord.setContractTime(new Date());
				bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
			}

			qiHu360Response.setCode(QiHu360Response.CODE_SUCCESS);
			qiHu360Response.setMsg("请求成功");
		} catch (Exception e) {
			logger.error(sessionId + "：执行QiHu360ServiceImpl.pushApprovalConfirmInfo method：异常：", e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(sessionId + "：结束QiHu360ServiceImpl.pushApprovalConfirmInfo method："
				+ JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 11.查询借款合同(code360)
	 * 
	 * @see com.waterelephant.drainage.service.QiHu360Service#queryContractUrl(long,
	 *      com.waterelephant.drainage.entity.qihu360.QueryContractUrl)
	 */
	@Override
	public QiHu360Response queryContractUrl(long sessionId, QueryContractUrl queryContractUrl) {
		logger.info(sessionId + "：开始QiHu360ServiceImpl.queryContractUrl method：" + JSON.toJSONString(queryContractUrl));
		QiHu360Response qiHu360Response = new QiHu360Response();
		try {
			String thirdOrderNo = queryContractUrl.getOrder_no();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "-结束QiHu360ServiceImpl.queryContractUrl method-"
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 2.根据第三方订单编号获取订单id
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单为空！");
				logger.info(sessionId + "-结束QiHu360ServiceImpl.queryContractUrl method-"
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			Long orderId = bwOrderRong.getOrderId();

			// 3.获取合同地址
			BwAdjunct bwAdjunct = new BwAdjunct();
			bwAdjunct.setOrderId(orderId);
			bwAdjunct.setAdjunctType(13);
			bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
			if (CommUtils.isNull(bwAdjunct)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("附件信息为空！");
				logger.info(sessionId + "-结束QiHu360ServiceImpl.queryContractUrl method-"
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			String contract_url = SystemConstant.PDF_URL + bwAdjunct.getAdjunctPath();

			Map<String, String> map = new HashMap<String, String>();
			map.put("contract_url", contract_url);

			qiHu360Response.setCode(QiHu360Response.CODE_SUCCESS);
			qiHu360Response.setMsg("获取成功");
			qiHu360Response.setData(map);
		} catch (Exception e) {
			logger.error(sessionId + "-结束QiHu360ServiceImpl.queryContractUrl method-" + e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(sessionId + "-结束QiHu360ServiceImpl.queryContractUrl method-" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 12.查询还款计划(code360)
	 * 
	 * @see com.waterelephant.drainage.service.QiHu360Service#queryRepaymentPlan(long,
	 *      com.waterelephant.drainage.entity.qihu360.QueryRepaymentPlan)
	 */
	@Override
	public QiHu360Response queryRepaymentPlan(long sessionId, QueryRepaymentPlan queryRepaymentPlan) {
		logger.info(
				sessionId + "：开始QiHu360ServiceImpl.queryRepaymentPlan method：" + JSON.toJSONString(queryRepaymentPlan));
		QiHu360Response qiHu360Response = new QiHu360Response();
		try {
			String thirdOrderNo = queryRepaymentPlan.getOrder_no();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "-结束QiHu360ServiceImpl.queryRepaymentPlan method-"
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 2.根据第三方订单编号获取订单id
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单为空！");
				logger.info(sessionId + "-结束QiHu360ServiceImpl.queryRepaymentPlan method-"
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			Long orderId = bwOrderRong.getOrderId();

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("订单为空！");
				logger.info(sessionId + "-结束QiHu360ServiceImpl.queryRepaymentPlan method-"
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			// 获取银行卡信息
			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
			if (CommUtils.isNull(bwBankCard)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("银行卡信息为空！");
				logger.info(sessionId + "-结束QiHu360ServiceImpl.queryRepaymentPlan method-"
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			// 获取还款计划
			BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(bwOrder.getId());
			if (CommUtils.isNull(bwRepaymentPlan)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("还款计划为空！");
				logger.info(sessionId + "-结束QiHu360ServiceImpl.queryRepaymentPlan method-"
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			String billStatus = QiHu360Utils.getBillStatus(String.valueOf(bwOrder.getStatusId()));
			logger.info("映射为奇虎360账单状态为：" + billStatus);

			Map<String, Object> data = new HashMap<>();
			Map<String, String> planMap = new HashMap<>();

			BigDecimal allAmount = new BigDecimal(bwRepaymentPlan.getRealityRepayMoney());
			logger.info("金额:" + allAmount);

			// 获取逾期记录
			BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
			bwOverdueRecord.setOrderId(orderId);
			bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);

			if ("3".equals(billStatus)) {
				if (!CommUtils.isNull(bwOverdueRecord) && bwOverdueRecord.getOverdueAccrualMoney() != null) {
					allAmount = allAmount.add(new BigDecimal(bwOverdueRecord.getOverdueAccrualMoney()));
					planMap.put("overdue_fee", String.valueOf(bwOverdueRecord.getOverdueAccrualMoney()));
					planMap.put("amount",
							allAmount.subtract(new BigDecimal(bwRepaymentPlan.getAlreadyRepayMoney())).toString());
				}
			} else if ("1".equals(billStatus)) {
				planMap.put("overdue_fee", "0");
				planMap.put("amount",
						allAmount.subtract(new BigDecimal(bwRepaymentPlan.getAlreadyRepayMoney())).toString());

			} else {
				if (!CommUtils.isNull(bwOverdueRecord) && bwOverdueRecord.getOverdueAccrualMoney() != null) {
					planMap.put("remark", "含本金" + bwRepaymentPlan.getRepayCorpusMoney() + "元，律师费"
							+ bwRepaymentPlan.getZjw() + "元，逾期 " + bwOverdueRecord.getOverdueAccrualMoney() + "元");
					planMap.put("amount", allAmount.add(new BigDecimal(bwOverdueRecord.getOverdueAccrualMoney()))
							.subtract(new BigDecimal(bwRepaymentPlan.getAlreadyRepayMoney())).toString());
				} else {
					planMap.put("remark",
							"含本金" + bwRepaymentPlan.getRepayCorpusMoney() + "元，律师费" + bwRepaymentPlan.getZjw() + "元");
					planMap.put("amount",
							allAmount.subtract(new BigDecimal(bwRepaymentPlan.getAlreadyRepayMoney())).toString());
				}

			}
			if (bwRepaymentPlan.getAlreadyRepayMoney() > 0) {
				planMap.put("paid_amount", String.valueOf(bwRepaymentPlan.getAlreadyRepayMoney()));
				planMap.put("success_time", String.valueOf(bwRepaymentPlan.getUpdateTime().getTime() / 1000));
			}
			planMap.put("period_no", String.valueOf(bwRepaymentPlan.getId()));
			planMap.put("bill_status", billStatus);
			planMap.put("due_time", String.valueOf(bwRepaymentPlan.getRepayTime().getTime() / 1000));
			planMap.put("can_repay_time", String.valueOf(bwRepaymentPlan.getCreateTime().getTime() / 1000));
			planMap.put("pay_type", "5");

			List<Map<String, String>> planMaps = new ArrayList<Map<String, String>>();
			planMaps.add(planMap);

			data.put("order_no", thirdOrderNo);
			data.put("open_bank", bwBankCard.getBankName());
			data.put("bank_card", bwBankCard.getCardNo());
			data.put("repayment_plan", planMaps);

			qiHu360Response.setCode(QiHu360Response.CODE_SUCCESS);
			qiHu360Response.setMsg("获取成功");
			qiHu360Response.setData(data);
		} catch (Exception e) {
			logger.error(sessionId + "-结束QiHu360ServiceImpl.queryRepaymentPlan method-" + e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(
				sessionId + "-结束QiHu360ServiceImpl.queryRepaymentPlan method-" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 13.查询还款详情(code360)
	 * 
	 * @see com.waterelephant.drainage.service.QiHu360Service#queryRepayInfo(long,
	 *      com.waterelephant.drainage.entity.qihu360.QueryRepayInfo)
	 */
	@Override
	public QiHu360Response queryRepayInfo(long sessionId, QueryRepayInfo queryRepayInfo) {
		logger.info(sessionId + "：开始QiHu360ServiceImpl.queryRepayInfo method：" + JSON.toJSONString(queryRepayInfo));
		QiHu360Response qiHu360Response = new QiHu360Response();
		try {
			String thirdOrderNo = queryRepayInfo.getOrder_no();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "-结束QiHu360ServiceImpl.queryRepayInfo method-"
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 2.根据第三方订单编号获取订单id
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单为空！");
				logger.info(sessionId + "-结束QiHu360ServiceImpl.queryRepayInfo method-"
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			Long orderId = bwOrderRong.getOrderId();

			// 获取还款计划
			BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(orderId);
			if (CommUtils.isNull(bwRepaymentPlan)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("还款计划为空！");
				logger.info(sessionId + "-结束QiHu360ServiceImpl.queryRepayInfo method-"
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			Map<String, String> data = new HashMap<>();
			// 获取逾期记录
			BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
			bwOverdueRecord.setOrderId(orderId);
			bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
			if (!CommUtils.isNull(bwOverdueRecord) && bwOverdueRecord.getOverdueAccrualMoney() != null) {
				data.put("overdue_amount", String.valueOf(bwOverdueRecord.getOverdueAccrualMoney()));
			}

			data.put("period_nos", "1");
			data.put("amount", String.valueOf(bwRepaymentPlan.getRealityRepayMoney()));

			qiHu360Response.setCode(QiHu360Response.CODE_SUCCESS);
			qiHu360Response.setMsg("获取成功");
			qiHu360Response.setData(data);
		} catch (Exception e) {
			logger.error(sessionId + "-结束QiHu360ServiceImpl.queryRepayInfo method-" + e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");

		}
		logger.info(sessionId + "-结束QiHu360ServiceImpl.queryRepayInfo method-" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 14.推送用户还款信息(code360)
	 *
	 * @see com.waterelephant.drainage.service.QiHu360Service#pushUserRepayInfo(long,
	 *      com.waterelephant.drainage.entity.qihu360.PushUserRepayInfo)
	 */
	@Override
	public QiHu360Response pushUserRepayInfo(long sessionId, PushUserRepayInfo pushUserRepayInfo) {
		logger.info(sessionId + "：开始QiHu360ServiceImpl.queryRepayInfo method：" + JSON.toJSONString(pushUserRepayInfo));
		QiHu360Response qiHu360Response = new QiHu360Response();
		try {
			String thirdOrderNo = pushUserRepayInfo.getOrder_no();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "-结束QiHu360ServiceImpl.pushUserRepayInfo method-"
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 2.根据第三方订单编号获取订单id
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单为空！");
				logger.info(sessionId + "-结束QiHu360ServiceImpl.pushUserRepayInfo method-"
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			Long orderId = bwOrderRong.getOrderId();

			// 还款
			String url = SystemConstant.NOTIRY_URL + "/thirdPay/payment.do";
			Map<String, String> params = new HashMap<>();
			params.put("orderId", orderId + "");
			String jsonStr = HttpClientHelper.post(url, "utf-8", params);
			JSONObject json = JSON.parseObject(jsonStr);
			if ("000".equals(json.get("code"))) {
				qiHu360Response.setCode(QiHu360Response.CODE_SUCCESS);
				qiHu360Response.setMsg("还款成功");
			}
		} catch (Exception e) {
			logger.error(sessionId + "-结束QiHu360ServiceImpl.pushUserRepayInfo method-" + e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");
		}
		logger.info(sessionId + "-结束QiHu360ServiceImpl.pushUserRepayInfo method-" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

	/**
	 * 奇虎360 - 15.查询订单状态(code360)
	 * 
	 * @see com.waterelephant.drainage.service.QiHu360Service#queryOrderStatus(long,
	 *      com.waterelephant.drainage.entity.qihu360.QueryOrderStatus)
	 */
	@Override
	public QiHu360Response queryOrderStatus(long sessionId, QueryOrderStatus queryOrderStatus) {
		logger.info(sessionId + "：开始QiHu360ServiceImpl.queryOrderStatus method：" + JSON.toJSONString(queryOrderStatus));
		QiHu360Response qiHu360Response = new QiHu360Response();
		try {
			String thirdOrderNo = queryOrderStatus.getOrder_no();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "-结束QiHu360ServiceImpl.queryOrderStatus method-"
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			// 2.根据第三方订单编号获取订单id
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("第三方订单为空！");
				logger.info(sessionId + "-结束QiHu360ServiceImpl.queryOrderStatus method-"
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}
			Long orderId = bwOrderRong.getOrderId();

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				qiHu360Response.setCode(QiHu360Response.CODE_PARAMETER);
				qiHu360Response.setMsg("订单为空！");
				logger.info(sessionId + "：结束QiHu360ServiceImpl.queryOrderStatus method："
						+ JSON.toJSONString(qiHu360Response));
				return qiHu360Response;
			}

			String orderStatus = QiHu360Utils.getOrderStatus(String.valueOf(bwOrder.getStatusId()));
			logger.info("映射为奇虎360订单状态为：" + orderStatus);

			Map<String, String> map = new HashMap<>();
			map.put("order_no", thirdOrderNo);
			map.put("order_status", orderStatus);
			map.put("update_time", String.valueOf(bwOrder.getUpdateTime().getTime() / 1000));
			qiHu360Response.setCode(QiHu360Response.CODE_SUCCESS);
			qiHu360Response.setMsg("获取成功");
			qiHu360Response.setData(map);
		} catch (Exception e) {
			logger.error(sessionId + "-结束QiHu360ServiceImpl.queryOrderStatus method-" + e);
			qiHu360Response.setCode(QiHu360Response.CODE_FAILURE);
			qiHu360Response.setMsg("请求失败");

		}
		logger.info(sessionId + "-结束QiHu360ServiceImpl.queryOrderStatus method-" + JSON.toJSONString(qiHu360Response));
		return qiHu360Response;
	}

}
