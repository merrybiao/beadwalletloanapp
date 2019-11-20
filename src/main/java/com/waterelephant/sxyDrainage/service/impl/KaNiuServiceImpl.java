///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.math.NumberUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.beadwallet.service.baofu.entity.BindCardRequest;
//import com.beadwallet.service.baofu.entity.BindCardResult;
//import com.beadwallet.service.baofu.service.BaofuServiceSDK;
//import com.beadwallet.utils.CommUtils;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBankCardChange;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwCheckRecord;
//import com.waterelephant.entity.BwContactList;
//import com.waterelephant.entity.BwIdentityCard2;
//import com.waterelephant.entity.BwKaniuAllphonecategory;
//import com.waterelephant.entity.BwKaniuApp;
//import com.waterelephant.entity.BwKaniuBehavior;
//import com.waterelephant.entity.BwKaniuContactsinfo;
//import com.waterelephant.entity.BwKaniuDeviceinfo1;
//import com.waterelephant.entity.BwKaniuDeviceinfo2;
//import com.waterelephant.entity.BwKaniuHitrules;
//import com.waterelephant.entity.BwKaniuLbs;
//import com.waterelephant.entity.BwKaniuRelationshipstatistical;
//import com.waterelephant.entity.BwKaniuUserphonewithmark;
//import com.waterelephant.entity.BwMerchantOrder;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwOverdueRecord;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.entity.BwProductDictionary;
//import com.waterelephant.entity.BwRepaymentPlan;
//import com.waterelephant.entity.BwWorkInfo;
//import com.waterelephant.entity.BwZmxyGrade;
//import com.waterelephant.service.BwBankCardChangeService;
//import com.waterelephant.service.BwCheckRecordService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.BwProductDictionaryService;
//import com.waterelephant.service.BwRejectRecordService;
//import com.waterelephant.service.BwZmxyGradeService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.IBwBorrowerService;
//import com.waterelephant.service.IBwMerchantOrderService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.service.IBwWorkInfoService;
//import com.waterelephant.service.impl.BwContactListService;
//import com.waterelephant.service.impl.BwIdentityCardServiceImpl;
//import com.waterelephant.service.impl.BwKaniuAllphonecategoryServiceImpl;
//import com.waterelephant.service.impl.BwKaniuAppServiceImpl;
//import com.waterelephant.service.impl.BwKaniuBehaviorServiceImpl;
//import com.waterelephant.service.impl.BwKaniuContactsinfoServiceImpl;
//import com.waterelephant.service.impl.BwKaniuDeviceinfo1ServiceImpl;
//import com.waterelephant.service.impl.BwKaniuDeviceinfo2ServiceImpl;
//import com.waterelephant.service.impl.BwKaniuHitrulesServiceImpl;
//import com.waterelephant.service.impl.BwKaniuLbsServiceImpl;
//import com.waterelephant.service.impl.BwKaniuRelationshipstatisticalServiceImpl;
//import com.waterelephant.service.impl.BwKaniuUserphonewithmarkServiceImpl;
//import com.waterelephant.service.impl.BwOrderService;
//import com.waterelephant.service.impl.BwRepaymentPlanService;
//import com.waterelephant.sxyDrainage.entity.DrainageEnum;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.kaNiu.DeviceInfoVo;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuBindCardReq;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuCheckUserReq;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuCommonReq;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuOrderStateRsp;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuPlanVo;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuPushUserReq;
//import com.waterelephant.sxyDrainage.entity.kaNiu.KaNiuResponse;
//import com.waterelephant.sxyDrainage.entity.kaNiu.SupAllPhoneCategory;
//import com.waterelephant.sxyDrainage.entity.kaNiu.SupContactsInfo;
//import com.waterelephant.sxyDrainage.entity.kaNiu.SupDeviceInfo;
//import com.waterelephant.sxyDrainage.entity.kaNiu.SupHitRulesVo;
//import com.waterelephant.sxyDrainage.entity.kaNiu.SupRelationshipStatistical;
//import com.waterelephant.sxyDrainage.entity.kaNiu.SupRiskInfo;
//import com.waterelephant.sxyDrainage.entity.kaNiu.SupUserPhoneWithMark;
//import com.waterelephant.sxyDrainage.entity.kaNiu.SupplyInfoReq;
//import com.waterelephant.sxyDrainage.service.AsyncKaNiuService;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.KaNiuService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.SxyDrainageConstant;
//import com.waterelephant.sxyDrainage.utils.kaNiuUtil.KaNiuConstant;
//import com.waterelephant.sxyDrainage.utils.kaNiuUtil.KaNiuUtil;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.MD5Util;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//
//import tk.mybatis.mapper.entity.Example;
//
///**
// * 
// * Module: KaNiuServiceImpl.java
// * 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//@Service
//public class KaNiuServiceImpl implements KaNiuService {
//	private Logger logger = LoggerFactory.getLogger(KaNiuServiceImpl.class);
//	@Autowired
//	private IBwBorrowerService bwBorrowerService;
//	@Autowired
//	private ThirdCommonService thirdCommonService;
//	@Autowired
//	private CommonService commonService;
//	@Autowired
//	private IBwBankCardService bwBankCardService;
//	@Autowired
//	private BwBankCardChangeService bwBankCardChangeService;
//	@Autowired
//	private BwOrderService bwOrderService;
//	@Autowired
//	private BwCheckRecordService bwCheckRecordService;
//	@Autowired
//	private BwRepaymentPlanService bwRepaymentPlanService;
//	@Autowired
//	private BwOverdueRecordService bwOverdueRecordService;
//	@Autowired
//	private BwProductDictionaryService bwProductDictionaryService;
//	@Autowired
//	private IBwMerchantOrderService bwMerchantOrderService;
//	@Autowired
//	private IBwWorkInfoService bwWorkInfoService;
//	@Autowired
//	private BwOrderRongService bwOrderRongService;
//	@Autowired
//	private BwIdentityCardServiceImpl bwIdentityCardServiceImpl;
//	@Autowired
//	private IBwPersonInfoService bwPersonInfoService;
//	@Autowired
//	private BwContactListService bwContactListService;
//	@Autowired
//	private BwZmxyGradeService bwZmxyGradeService;
//	@Autowired
//	private BwRejectRecordService bwRejectRecordService;
//	@Autowired
//	private AsyncKaNiuService asyncKaNiuService;
//	@Autowired
//	private BwKaniuLbsServiceImpl bwKaniuLbsService;
//	@Autowired
//	private BwKaniuAppServiceImpl bwKaniuAppService;
//	@Autowired
//	private BwKaniuBehaviorServiceImpl bwKaniuBehaviorService;
//	@Autowired
//	private BwKaniuDeviceinfo1ServiceImpl bwKaniuDeviceinfo1Service;
//	@Autowired
//	private BwKaniuDeviceinfo2ServiceImpl bwKaniuDeviceinfo2Service;
//	@Autowired
//	private BwKaniuContactsinfoServiceImpl bwKaniuContactsinfoService;
//	@Autowired
//	private BwKaniuAllphonecategoryServiceImpl bwKaniuAllphonecategoryService;
//	@Autowired
//	private BwKaniuRelationshipstatisticalServiceImpl bwKaniuRelationshipstatisticalService;
//	@Autowired
//	private BwKaniuUserphonewithmarkServiceImpl bwKaniuUserphonewithmarkService;
//	@Autowired
//	private BwKaniuHitrulesServiceImpl bwKaniuHitrulesService;
//	// @Autowired
//	// private BwOrderProcessRecordService bwOrderProcessRecordService;
//
//	/**
//	 * 	
//	 */
//	@Override
//	public KaNiuResponse checkUser(long sessionId, KaNiuCheckUserReq kaNiuCheckUserReq) {
//		logger.info(sessionId + "开始卡牛过滤接口:");
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		try {
//			String idCard = kaNiuCheckUserReq.getIdCardNo();
//			String name = kaNiuCheckUserReq.getName();
//			String phone = kaNiuCheckUserReq.getMobile();
//			DrainageRsp drainageRsp = commonService.checkUser(sessionId, name, phone, idCard);
//			Map<String, Object> map = new HashMap<>();
//			if (!"0000".equals(drainageRsp.getCode())) {
//				// map.put("isAvailable", false);// 是否可借
//				if (drainageRsp.getCode().equals(DrainageEnum.CODE_PARAMETER.getCode())
//						|| drainageRsp.getCode().equals(DrainageEnum.CODE_PARAMSETER.getCode())) {
//					kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_FAIL);
//					kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//					kaNiuResponse.setResultCodeDescription("缺少必要参数");
//					return kaNiuResponse;
//				}
//				map.put("blackListFlag", "2");
//				map.put("state", "S");
//				if (drainageRsp.getCode().equals(DrainageEnum.CODE_RULE_BLACKLIST.getCode())) {
//					map.put("blackListFlag", "1");// 1黑名单用户、2非黑名单用户
//					map.put("state", "E01");
//					map.put("message", drainageRsp.getMessage());
//				} else if (drainageRsp.getCode().equals(DrainageEnum.CODE_RULE_ISPROCESSING.getCode())) {
//					map.put("state", "E03");
//					map.put("message", drainageRsp.getMessage());
//				} else if (drainageRsp.getCode().equals(DrainageEnum.CODE_RULE_ISREJECT.getCode())) {
//					map.put("state", "E04");
//					map.put("message", drainageRsp.getMessage());
//				} else {
//					map.put("state", "E10");
//					map.put("message", drainageRsp.getMessage());
//				}
//			} else {
//				map.put("state", "S");
//				// map.put("isAvailable", true);// 是否可借
//				map.put("blackListFlag", "2");
//				map.put("message", "");
//			}
//			BwBorrower bwBorrower = bwBorrowerService.oldUserFilter2(name, phone, idCard);
//			if (null == bwBorrower) {
//				map.put("userFlag", "1");// 1新用户、0老用户
//			} else {
//				map.put("userFlag", "0");// 1新用户、0老用户
//			}
//			map.put("channelFlag", "");//
//			map.put("loanFlag", "");//
//			map.put("loanLimit", "");//
//			map.put("rate", "");//
//			map.put("term", "");//
//			map.put("termUnit", "");//
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			kaNiuResponse.setResultCode(KaNiuResponse.SUCCESS);
//			kaNiuResponse.setResultCodeDescription("请求成功");
//			kaNiuResponse.setData(map);
//			logger.info(sessionId + "结束卡牛过滤接口");
//			return kaNiuResponse;
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束卡牛 过滤接口：>>>> 异常：", e);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("系统异常");
//			return kaNiuResponse;
//		}
//	}
//
//	/**
//	 * 进件
//	 */
//	@Override
//	public KaNiuResponse savePushUser(long sessionId, KaNiuPushUserReq kaNiuPushUserReq) {
//		logger.info(sessionId + "开始卡牛进件接口:");
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		try {
//			String thirdOrderNo = kaNiuPushUserReq.getSsjLoanNo();
//			String name = kaNiuPushUserReq.getName();
//			String phone = kaNiuPushUserReq.getMobilePhone();
//			String idCard = kaNiuPushUserReq.getIdCard();
//			if (StringUtils.isBlank(name) || StringUtils.isBlank(phone) || StringUtils.isBlank(idCard)
//					|| StringUtils.isBlank(thirdOrderNo)) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//				kaNiuResponse.setResultCodeDescription("缺少必要参数");
//				return kaNiuResponse;
//			}
//			boolean flag = thirdCommonService.checkUserAccountProgressOrder(sessionId, idCard);
//			if (flag) {
//				logger.info(sessionId + ">>> 多个订单idCard：" + idCard);
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_ISPROCESSING);
//				kaNiuResponse.setResultCodeDescription("尚有未结清或审核中的订单，不能重复进件");
//				return kaNiuResponse;
//			}
//			String channelIdstr = KaNiuConstant.SX_CHANNELID;
//			Integer channelId = Integer.valueOf(channelIdstr);
//			BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, name, idCard, phone, channelId);
//			long borrowerId = borrower.getId();
//			logger.info(sessionId + ">>> 新增/更新借款人ID：" + borrowerId);
//			BwOrder bwOrder = new BwOrder();
//			// bwOrder.setStatusId(6L);
//			// bwOrder.setChannel(Integer.valueOf(channelId));
//			// bwOrder.setBorrowerId(borrowerId);
//			// List<BwOrder> list = bwOrderService.findBwOrderListByAttr(bwOrder);
//			// if (CollectionUtils.isEmpty(list)) {
//			// logger.info(sessionId + ">>> 非续贷用户：borrowerId=" + borrowerId);
//			// kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			// kaNiuResponse.setResultCode(KaNiuResponse.ERR_ISPROCESSING);
//			// kaNiuResponse.setResultCodeDescription("仅支持续贷用户");
//			// return kaNiuResponse;
//			// }
//
//			Integer productId = Integer.valueOf(SxyDrainageConstant.productId);
//			// 判断是否有草稿状态的订单
//			// bwOrder = new BwOrder();
//			bwOrder.setBorrowerId(borrowerId);
//			bwOrder.setStatusId(1L);
//			bwOrder.setProductType(2);
//			bwOrder.setChannel(channelId);
//			List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);// 先查询草稿状态的订单
//			bwOrder.setStatusId(8L);
//			List<BwOrder> boList8 = bwOrderService.findBwOrderListByAttr(bwOrder);// 再查询撤回状态的订单
//			boList.addAll(boList8); // 第一次进件被审批撤回后，再次进件时，更新第一次的订单
//			if (boList != null && boList.size() > 0) {
//				bwOrder = boList.get(boList.size() - 1);
//				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwOrder.setProductType(2);
//				bwOrder.setStatusId(1L);
//				bwOrder.setRepayType(2);
//				bwOrder.setProductId(productId);
//				bwOrder.setExpectMoney(
//						kaNiuPushUserReq.getLoanMoney() != null ? NumberUtils.toDouble(kaNiuPushUserReq.getLoanMoney())
//								: 0D);
//				bwOrder.setExpectNumber(4);
//				bwOrder.setCreateTime(Calendar.getInstance().getTime());
//				bwOrderService.updateBwOrder(bwOrder);
//			} else {
//				bwOrder = new BwOrder();
//				bwOrder.setOrderNo(DrainageUtils.generateOrderNo());
//				bwOrder.setBorrowerId(borrower.getId());
//				bwOrder.setStatusId(1L);
//				bwOrder.setRepayType(2);
//				bwOrder.setCreateTime(Calendar.getInstance().getTime());
//				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwOrder.setChannel(channelId);
//				bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//				bwOrder.setApplyPayStatus(0);
//				bwOrder.setBorrowUse(kaNiuPushUserReq.getLoanPurpose());
//				bwOrder.setExpectNumber(4);
//				bwOrder.setProductId(productId);
//				bwOrder.setExpectMoney(
//						kaNiuPushUserReq.getLoanMoney() != null ? NumberUtils.toDouble(kaNiuPushUserReq.getLoanMoney())
//								: 0D);
//				bwOrder.setProductType(2);
//				bwOrderService.addBwOrder(bwOrder);
//			}
//			long orderId = bwOrder.getId();
//			/**********************************************/
//			String key = "phone_apply";
//			Map<String, Object> params = new HashMap<>();
//			params.put("mobile", phone);
//			params.put("order_id", orderId);
//			params.put("borrower_id", borrower.getId());
//			String value = JSON.toJSONString(params);
//			RedisUtils.rpush(key, value);
//			/**********************************************/
//			logger.info(sessionId + ">>> 判断是否有草稿状态的订单：ID = " + orderId);
//			// 判断是否有融360订单
//			BwOrderRong bwOrderRong = new BwOrderRong();
//			bwOrderRong.setThirdOrderNo(thirdOrderNo);
//			bwOrderRong.setChannelId(Long.valueOf(channelId));
//			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//			if (null != bwOrderRong) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_ISPROCESSING);
//				kaNiuResponse.setResultCodeDescription("订单已存在，不能重复进件");
//				return kaNiuResponse;
//			}
//			bwOrderRong = bwOrderRongService.findBwOrderRongByOrderIdAndChannel(bwOrder.getId(),
//					Long.valueOf(channelId));
//			if (bwOrderRong == null) {
//				bwOrderRong = new BwOrderRong();
//				bwOrderRong.setOrderId(orderId);
//				bwOrderRong.setThirdOrderNo(thirdOrderNo);
//				bwOrderRong.setChannelId(Long.valueOf(channelId));
//				bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//				bwOrderRongService.save(bwOrderRong);
//			} else {
//				bwOrderRong.setChannelId(Long.valueOf(channelId));
//				bwOrderRong.setThirdOrderNo(thirdOrderNo);
//				bwOrderRong.setCreateTime(new Date());
//				bwOrderRongService.update(bwOrderRong);
//			}
//			logger.info(sessionId + ">>> 判断是否有融360订单");
//
//			// 判断是否有商户订单信息
//			BwMerchantOrder bwMerchantOrder = new BwMerchantOrder();
//			bwMerchantOrder.setOrderId(orderId);
//			bwMerchantOrder = bwMerchantOrderService.selectByAtt(bwMerchantOrder);
//			if (bwMerchantOrder == null) {
//				bwMerchantOrder = new BwMerchantOrder();
//				bwMerchantOrder.setBorrowerId(borrowerId);
//				bwMerchantOrder.setCreateTime(Calendar.getInstance().getTime());
//				bwMerchantOrder.setExt3("0");
//				bwMerchantOrder.setMerchantId(0L);
//				bwMerchantOrder.setOrderId(orderId);
//				bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwMerchantOrderService.insertByAtt(bwMerchantOrder);
//			} else {
//				bwMerchantOrder.setBorrowerId(borrowerId);
//				bwMerchantOrder.setExt3("0");
//				bwMerchantOrder.setMerchantId(0L);
//				bwMerchantOrder.setOrderId(orderId);
//				bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwMerchantOrderService.updateByAtt(bwMerchantOrder);
//			}
//			// 判断是否有工作信息
//			BwWorkInfo bwWorkInfo = bwWorkInfoService.findBwWorkInfoByOrderId(orderId);
//			if (null == bwWorkInfo) {
//				bwWorkInfo = new BwWorkInfo();
//				bwWorkInfo.setOrderId(orderId);
//				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
//				bwWorkInfo.setIndustry(kaNiuPushUserReq.getIndustry());
//				bwWorkInfo.setWorkYears(kaNiuPushUserReq.getJobYears());
//				bwWorkInfo.setComName(kaNiuPushUserReq.getCompanyName());
//				bwWorkInfo.setIncome(kaNiuPushUserReq.getSalary());
//				bwWorkInfo.setCreateTime(new Date());
//				bwWorkInfo.setUpdateTime(new Date());
//				bwWorkInfoService.save(bwWorkInfo, borrowerId);
//			} else {
//				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
//				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//				bwWorkInfo.setIndustry(kaNiuPushUserReq.getIndustry());
//				bwWorkInfo.setWorkYears(kaNiuPushUserReq.getJobYears());
//				bwWorkInfo.setComName(kaNiuPushUserReq.getCompanyName());
//				bwWorkInfo.setIncome(kaNiuPushUserReq.getSalary());
//				bwWorkInfoService.update(bwWorkInfo);
//			}
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, channelId); // 插入个人认证记录
//			logger.info(sessionId + ">>> 判断是否有工作信息");
//
//			// 通讯录
//			List<DeviceInfoVo> deviceInfoVoList = kaNiuPushUserReq.getDeviceInfo();
//			if (CollectionUtils.isNotEmpty(deviceInfoVoList)) {
//				List<BwContactList> listConS = new ArrayList<>();
//				for (DeviceInfoVo deviceInfoVo : deviceInfoVoList) {
//					if (CommUtils.isNull(deviceInfoVo.getN()) || CommUtils.isNull(deviceInfoVo.getP())) {
//						continue;
//					}
//					BwContactList bwContactList = new BwContactList();
//					bwContactList.setBorrowerId(borrowerId);
//					bwContactList.setPhone(deviceInfoVo.getP().get(0));
//					bwContactList.setName(deviceInfoVo.getN());
//					listConS.add(bwContactList);
//				}
//				bwContactListService.addOrUpdateBwContactLists(listConS, borrowerId);
//				logger.info(sessionId + ">>> 处理通讯录信息 ");
//			}
//
//			// // 芝麻信用
//			String zmScore = kaNiuPushUserReq.getZmScore();
//			if (StringUtils.isNotBlank(zmScore)) {
//				BwZmxyGrade bwZmxyGrade = bwZmxyGradeService.findZmxyGradeByBorrowerId(borrowerId);
//				if (bwZmxyGrade == null) {
//					bwZmxyGrade = new BwZmxyGrade();
//					bwZmxyGrade.setBorrowerId(borrowerId);
//					bwZmxyGrade.setZmScore(NumberUtils.toInt(zmScore));
//					bwZmxyGrade.setCreateTime(Calendar.getInstance().getTime());
//					bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
//					bwZmxyGradeService.saveBwZmxyGrade(bwZmxyGrade);
//				} else {
//					bwZmxyGrade.setZmScore(NumberUtils.toInt(zmScore));
//					bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
//					bwZmxyGradeService.updateBwZmxyGrade(bwZmxyGrade);
//				}
//				thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 4, channelId);// 插入芝麻认证记录
//				logger.info(sessionId + ">>> 处理芝麻信用信息 ");
//			}
//
//			// 保存身份证信息
//			BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
//			bwIdentityCard.setBorrowerId(borrowerId);
//			bwIdentityCard = bwIdentityCardServiceImpl.findBwIdentityCardByAttr(bwIdentityCard);
//			logger.info(sessionId + ">>> borrowerId =" + borrowerId);
//			if (bwIdentityCard == null) {
//				bwIdentityCard = new BwIdentityCard2();
//				bwIdentityCard.setBorrowerId(borrowerId);
//				bwIdentityCard.setAddress(kaNiuPushUserReq.getAddress());
//				bwIdentityCard.setBirthday(kaNiuPushUserReq.getBirthYear() + kaNiuPushUserReq.getBirthMonth()
//						+ kaNiuPushUserReq.getBirthDay());
//				bwIdentityCard.setGender(KaNiuUtil.toSex(kaNiuPushUserReq.getSex()));
//				bwIdentityCard.setIssuedBy(kaNiuPushUserReq.getSignOrg());
//				bwIdentityCard.setRace(kaNiuPushUserReq.getNation());
//				bwIdentityCard.setIdCardNumber(idCard);
//				bwIdentityCard.setName(name);
//				bwIdentityCard.setCreateTime(new Date());
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCard.setValidDate(KaNiuUtil.toValidType(kaNiuPushUserReq.getValid_date_start(),
//						kaNiuPushUserReq.getValid_date_end()));
//				bwIdentityCardServiceImpl.saveBwIdentityCard(bwIdentityCard);
//			} else {
//				bwIdentityCard.setAddress(kaNiuPushUserReq.getAddress());
//				bwIdentityCard.setBirthday(kaNiuPushUserReq.getBirthYear() + kaNiuPushUserReq.getBirthMonth()
//						+ kaNiuPushUserReq.getBirthDay());
//				bwIdentityCard.setGender(KaNiuUtil.toSex(kaNiuPushUserReq.getSex()));
//				bwIdentityCard.setIssuedBy(kaNiuPushUserReq.getSignOrg());
//				bwIdentityCard.setRace(kaNiuPushUserReq.getNation());
//				bwIdentityCard.setIdCardNumber(idCard);
//				bwIdentityCard.setName(name);
//				bwIdentityCard.setCreateTime(new Date());
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCard.setValidDate(KaNiuUtil.toValidType(kaNiuPushUserReq.getValid_date_start(),
//						kaNiuPushUserReq.getValid_date_end()));
//				bwIdentityCardServiceImpl.updateBwIdentityCard(bwIdentityCard);
//			}
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, channelId);// 插入身份认证记录
//			logger.info(sessionId + ">>> 处理身份证信息");
//			// 亲属联系人
//			BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//			if (bwPersonInfo == null) {
//				bwPersonInfo = new BwPersonInfo();
//				bwPersonInfo.setAddress(kaNiuPushUserReq.getAddressDetailInfo());
//				bwPersonInfo.setOrderId(orderId);
//				bwPersonInfo.setCityName(kaNiuPushUserReq.getAddressInfo());
//				bwPersonInfo.setEmail(kaNiuPushUserReq.getEmail());
//				bwPersonInfo.setRelationName(kaNiuPushUserReq.getContactsAname());
//				bwPersonInfo.setRelationPhone(kaNiuPushUserReq.getContactsAphone());
//				bwPersonInfo.setUnrelationName(kaNiuPushUserReq.getContactsBname());
//				bwPersonInfo.setUnrelationPhone(kaNiuPushUserReq.getContactsBphone());
//				bwPersonInfo.setCreateTime(new Date());
//				bwPersonInfo.setUpdateTime(new Date());
//				bwPersonInfo.setColleagueName(kaNiuPushUserReq.getContactsCname());
//				bwPersonInfo.setColleaguePhone(kaNiuPushUserReq.getContactsCphone());
//				bwPersonInfo.setFriend1Name(kaNiuPushUserReq.getContactsDname());
//				bwPersonInfo.setFriend1Phone(kaNiuPushUserReq.getContactsDphone());
//				bwPersonInfo.setFriend2Name(kaNiuPushUserReq.getContactsEname());
//				bwPersonInfo.setFriend2Phone(kaNiuPushUserReq.getContactsEphone());
//				bwPersonInfo.setQqchat(kaNiuPushUserReq.getQq());
//				bwPersonInfo.setWechat(kaNiuPushUserReq.getWeChat());
//				bwPersonInfo.setMarryStatus(KaNiuUtil.toMarryStatus(kaNiuPushUserReq.getMarriageInfo()));
//				bwPersonInfoService.add(bwPersonInfo);
//			} else {
//				bwPersonInfo.setCityName(kaNiuPushUserReq.getAddressInfo());
//				bwPersonInfo.setEmail(kaNiuPushUserReq.getEmail());
//				bwPersonInfo.setRelationName(kaNiuPushUserReq.getContactsAname());
//				bwPersonInfo.setRelationPhone(kaNiuPushUserReq.getContactsAphone());
//				bwPersonInfo.setUnrelationName(kaNiuPushUserReq.getContactsBname());
//				bwPersonInfo.setUnrelationPhone(kaNiuPushUserReq.getContactsBphone());
//				bwPersonInfo.setCreateTime(new Date());
//				bwPersonInfo.setUpdateTime(new Date());
//				bwPersonInfo.setColleagueName(kaNiuPushUserReq.getContactsCname());
//				bwPersonInfo.setColleaguePhone(kaNiuPushUserReq.getContactsCphone());
//				bwPersonInfo.setFriend1Name(kaNiuPushUserReq.getContactsDname());
//				bwPersonInfo.setFriend1Phone(kaNiuPushUserReq.getContactsDphone());
//				bwPersonInfo.setFriend2Name(kaNiuPushUserReq.getContactsEname());
//				bwPersonInfo.setFriend2Phone(kaNiuPushUserReq.getContactsEphone());
//				bwPersonInfo.setQqchat(kaNiuPushUserReq.getQq());
//				bwPersonInfo.setWechat(kaNiuPushUserReq.getWeChat());
//				bwPersonInfo.setMarryStatus(KaNiuUtil.toMarryStatus(kaNiuPushUserReq.getMarriageInfo()));
//				bwPersonInfoService.update(bwPersonInfo);
//			}
//			logger.info(sessionId + ">>> 处理亲属联系人信息");
//
//			// 异步保存运营商数据
//			asyncKaNiuService.addAsynkaNiuOperator(sessionId, thirdOrderNo, bwOrder, borrower, kaNiuPushUserReq);
//			logger.info(sessionId + ">>> 异步处理运营商报告信息 ");
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			kaNiuResponse.setResultCode(KaNiuResponse.SUCCESS);
//			kaNiuResponse.setResultCodeDescription("进件成功");
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束卡牛进件接口：>>>> 异常：", e);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("进件失败");
//		}
//		return kaNiuResponse;
//	}
//
//	/**
//	 * 
//	 */
//	@Override
//	public KaNiuResponse saveBindCard(long sessionId, KaNiuBindCardReq kaNiuBindCardReq) {
//		logger.info(sessionId + "开始卡牛绑卡接口:");
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		try {
//			String channelIdstr = KaNiuConstant.SX_CHANNELID;
//			if (StringUtils.isBlank(channelIdstr)) {
//				logger.info(sessionId + "卡牛 渠道配置 为空");
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//				kaNiuResponse.setResultCodeDescription("请求异常");
//				return kaNiuResponse;
//			}
//			Integer channelId = Integer.valueOf(channelIdstr);
//			logger.info(sessionId + "卡牛绑卡接口channelId:" + channelId);
//			String userName = kaNiuBindCardReq.getName();
//			String idCard = kaNiuBindCardReq.getIdNo();
//			String phone = kaNiuBindCardReq.getMobile();
//			String bankCardNo = kaNiuBindCardReq.getBankCardNo();
//			String bankCardCode = kaNiuBindCardReq.getBankCode();
//			if (StringUtils.isBlank(userName) || StringUtils.isBlank(phone) || StringUtils.isBlank(idCard)
//					|| StringUtils.isBlank(bankCardCode)) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//				kaNiuResponse.setResultCodeDescription("缺少必要参数");
//				return kaNiuResponse;
//			}
//			bankCardCode = "SPABANK".equals(bankCardCode) ? "PAB" : bankCardCode;
//			bankCardCode = "BCM".equals(bankCardCode) ? "BCOM" : bankCardCode;
//			BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, userName, idCard, phone, channelId);
//			long borrowerId = borrower.getId();
//			logger.info(sessionId + "卡牛绑卡接口borrowerId:" + borrowerId);
//			String bankNum = DrainageUtils.convertBankCodeToFuiou(bankCardCode);
//			String bankName = DrainageUtils.convertFuiouBankCodeToBankName(bankNum);
//			if (StringUtils.isBlank(bankNum) || StringUtils.isBlank(bankName)) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_UN_SUPPO);
//				kaNiuResponse.setResultCodeDescription("不支持银行");
//				return kaNiuResponse;
//			}
//			String regPhone = kaNiuBindCardReq.getBankmobile();
//			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
//			logger.info(sessionId + ":绑卡接口>>>bankName=" + bankName + ",bankNum=" + bankNum);
//			boolean flag = false;
//			if (bwBankCard != null && bwBankCard.getSignStatus() > 0) {
//				if (bankCardNo.equals(bwBankCard.getCardNo())) {
//					kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//					kaNiuResponse.setResultCode(KaNiuResponse.SUCCESS);
//					kaNiuResponse.setResultCodeDescription("绑卡成功");
//					return kaNiuResponse;
//				} else {
//					flag = true;
//				}
//			}
//			// bankCode
//			// 开始绑卡
//			BindCardRequest bcr = new BindCardRequest();
//			bcr.setPay_code(bankCardCode);
//			bcr.setBorrowerId(borrowerId + "");
//			bcr.setId_card(idCard);
//			bcr.setMobile(regPhone);
//			bcr.setId_holder(userName);
//			bcr.setAcc_no(bankCardNo);
//			BindCardResult bindCardResult = BaofuServiceSDK.directBind(bcr);
//			if (null != bindCardResult) {
//				if ("0000".equals(bindCardResult.getResp_code())) {
//					logger.info(sessionId + "结束绑卡接口 >>>绑卡成功");
//					if (null != bwBankCard) {
//						bwBankCard.setBankName(bankName);
//						bwBankCard.setBankCode(bankNum);
//						bwBankCard.setCardNo(bankCardNo);
//						bwBankCard.setPhone(regPhone);
//						bwBankCard.setSignStatus(1);
//						bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
//						bwBankCardService.updateBwBankCard(bwBankCard);
//					} else {
//						bwBankCard = new BwBankCard();
//						bwBankCard.setBankName(bankName);
//						bwBankCard.setBankCode(bankNum);
//						bwBankCard.setCardNo(bankCardNo);
//						bwBankCard.setPhone(regPhone);
//						bwBankCard.setBorrowerId(borrowerId);
//						bwBankCard.setSignStatus(1);
//						bwBankCard.setCreateTime(Calendar.getInstance().getTime());
//						bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
//						bwBankCardService.saveBwBankCard(bwBankCard, borrowerId);
//					}
//					if (flag) {
//						BwBankCardChange bwBankCardChange = new BwBankCardChange();
//						bwBankCardChange.setBorrowerId(borrowerId);
//						bwBankCardChange.setBankName(bwBankCard.getBankName());
//						bwBankCardChange.setBankCode(bwBankCard.getBankCode());
//						bwBankCardChange.setPhone(bwBankCard.getPhone());
//						bwBankCardChange.setCardNo(bwBankCard.getCardNo());
//						bwBankCardChange.setCreatedTime(new Date());
//						Integer insertNumber = bwBankCardChangeService.insertByAtt(bwBankCardChange);
//						if (insertNumber > 0) {
//							logger.info("成功插入" + insertNumber + "借款人" + borrowerId + "换卡信息成功,卡号为："
//									+ bwBankCard.getCardNo());
//						}
//					}
//					kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//					kaNiuResponse.setResultCode(KaNiuResponse.SUCCESS);
//					kaNiuResponse.setResultCodeDescription("绑卡成功");
//					return kaNiuResponse;
//				} else {
//					logger.info(sessionId + "结束绑卡接口 >>> 绑卡失败:" + bindCardResult.getResp_msg());
//					kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//					kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//					kaNiuResponse.setResultCodeDescription(
//							"绑卡失败：" + null == bindCardResult.getResp_msg() ? "请重试" : bindCardResult.getResp_msg());
//					return kaNiuResponse;
//				}
//			} else {
//				logger.info(sessionId + "结束绑卡接口 >>> 绑卡失败:");
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//				kaNiuResponse.setResultCodeDescription("绑卡失败");
//				return kaNiuResponse;
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束卡牛 绑卡接口：>>>> 异常：", e);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("绑卡失败");
//			return kaNiuResponse;
//		}
//	}
//
//	@Override
//	public KaNiuResponse getOrderState(long sessionId, KaNiuCommonReq kaNiuCommonReq) {
//		logger.info(sessionId + "开始卡牛获取订单状态接口:");
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		try {
//			String OrderNo = kaNiuCommonReq.getLoanNo();
//			String thirdOrderNo = kaNiuCommonReq.getSsjLoanNo();
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//				kaNiuResponse.setResultCodeDescription("缺少必要参数");
//				return kaNiuResponse;
//			}
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder || (StringUtils.isNotBlank(OrderNo) && !OrderNo.equals(bwOrder.getOrderNo()))) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_UN_SUPPO);
//				kaNiuResponse.setResultCodeDescription("查询订单为空");
//				return kaNiuResponse;
//			}
//
//			int statusId = bwOrder.getStatusId().intValue();
//			Long orderId = bwOrder.getId();
//			List<KaNiuPlanVo> repayList = getRepayList(sessionId, statusId, orderId);
//			KaNiuOrderStateRsp kaNiuOrderStateRsp = new KaNiuOrderStateRsp();
//			// 审核时间
//			List<BwCheckRecord> bwCheckRecordList = bwCheckRecordService.queryCheck(orderId + "");
//			String approveTime = "", lendingTime = "", applyTime = "";
//			double repaymentAmount = 0d;
//			if (CollectionUtils.isNotEmpty(bwCheckRecordList)) {
//				approveTime = bwCheckRecordList.get(0).getCreateTime().getTime() + "";// 审核时间
//			}
//			if (StringUtils.isBlank(approveTime)) {
//				Date approveDate = bwRejectRecordService.findCreateTimeByOrderId(bwOrder.getId());
//				if (null != approveDate) {
//					approveTime = DrainageUtils.formatToStr(approveDate, "yyyy-MM-dd HH:mm:ss");
//				}
//			}
//			applyTime = bwOrder.getCreateTime().getTime() + "";
//			if (9 == statusId || 13 == statusId || 6 == statusId) {
//				BwRepaymentPlan bwRepaymentPlan_ = null;
//				Example example = new Example(BwRepaymentPlan.class);
//				example.createCriteria().andEqualTo("orderId", orderId);
//				example.setOrderByClause("createTime asc");
//				List<BwRepaymentPlan> bwRepaymentPlanlist = bwRepaymentPlanService.findRepaymentPlanByExample(example);
//				if (CollectionUtils.isNotEmpty(bwRepaymentPlanlist)) {
//					lendingTime = bwRepaymentPlanlist.get(0).getCreateTime().getTime() + "";// 放款时间
//					for (BwRepaymentPlan bwRepaymentPlan : bwRepaymentPlanlist) {
//						if (6 != statusId) {
//							double repayPenaltyInt = 0d;
//							if ("2".equals(bwRepaymentPlan.getRepayType() + "")) {
//								BwOverdueRecord bwOverdueRecord = bwOverdueRecordService
//										.queryBwOverdueByRepayId(bwRepaymentPlan.getId());
//								if (null != bwOverdueRecord) {
//									double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0D
//											: bwOverdueRecord.getOverdueAccrualMoney();
//									double advance = bwOverdueRecord.getAdvance() == null ? 0D
//											: bwOverdueRecord.getAdvance();
//									repayPenaltyInt = overdueAccrualMoney - advance;
//								}
//							}
//							// 应还金额
//							repaymentAmount = repaymentAmount + bwRepaymentPlan.getRealityRepayMoney()
//									+ repayPenaltyInt;
//							if (null == bwRepaymentPlan_ && "1".equals(bwRepaymentPlan.getRepayStatus() + "")) {
//								bwRepaymentPlan_ = bwRepaymentPlan;
//							}
//						}
//					}
//					if (null != bwRepaymentPlan_) {
//						double repayoverdue = 0d;
//						int repayoverday = 0;
//						if ("2".equals(bwRepaymentPlan_.getRepayType() + "")) {
//							BwOverdueRecord bwOverdueRecord = bwOverdueRecordService
//									.queryBwOverdueByRepayId(bwRepaymentPlan_.getId());
//							if (null != bwOverdueRecord) {
//								Double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0D
//										: bwOverdueRecord.getOverdueAccrualMoney();
//								Double advance = bwOverdueRecord.getAdvance() == null ? 0D
//										: bwOverdueRecord.getAdvance();
//								repayoverdue = overdueAccrualMoney - advance;
//								repayoverday = bwOverdueRecord.getOverdueDay();
//							}
//						}
//						Double currentRepaymentAmount = bwRepaymentPlan_.getRealityRepayMoney() + repayoverdue;
//						if ("2".equals(bwRepaymentPlan_.getRepayStatus() + "")) {
//							currentRepaymentAmount = 0D;
//						}
//						kaNiuOrderStateRsp.setCurrentRepaymentAmount(currentRepaymentAmount);// 当期还款金额
//						kaNiuOrderStateRsp.setCurrentDueTime(
//								DrainageUtils.formatToStr(bwRepaymentPlan_.getRepayTime(), "yyyy-MM-dd"));
//						kaNiuOrderStateRsp.setOverdueDays(repayoverday);// 逾期天数 否
//						kaNiuOrderStateRsp.setDemurrage(repayoverdue);// 逾期费用 否
//						kaNiuOrderStateRsp.setDelayFee(0d);// 滞纳金
//					}
//				}
//			}
//			String productId = SxyDrainageConstant.productId;
//			BwProductDictionary bwProductDictionary = bwProductDictionaryService
//					.findById(NumberUtils.toLong(productId));
//			Double interestRate = bwProductDictionary.getInterestRate();
//			kaNiuOrderStateRsp.setProductType("1");// 产品类型：1分期、2 payday
//			kaNiuOrderStateRsp.setLoanNo(bwOrder.getOrderNo());// 借款编号（贷款合作方）
//			kaNiuOrderStateRsp.setSsjLoanNo(thirdOrderNo);// 订单编号（随手科技内部订单号）
//			kaNiuOrderStateRsp.setApplyAmount(bwOrder.getExpectMoney() == null ? 0.0D : bwOrder.getExpectMoney());// 申请金额
//			if (4 <= statusId && 8 != statusId && 7 != statusId) {
//				kaNiuOrderStateRsp
//						.setCreditAmount(bwOrder.getBorrowAmount() == null ? 0.0D : bwOrder.getBorrowAmount());// 审批金额（授信金额）
//			}
//			if (9 == statusId || 6 == statusId || 11 < statusId) {
//				kaNiuOrderStateRsp
//						.setContractAmount(bwOrder.getBorrowAmount() == null ? 0.0D : bwOrder.getBorrowAmount());// 合同金额
//			}
//			if (9 == statusId || 6 == statusId || 13 == statusId) {
//				kaNiuOrderStateRsp.setLoanAmount(bwOrder.getBorrowAmount() == null ? 0.0D : bwOrder.getBorrowAmount());// 申请金额
//			}
//			kaNiuOrderStateRsp.setLoanStatus(KaNiuUtil.toKNState(statusId));
//			kaNiuOrderStateRsp.setApproveTime(approveTime);// 审核时间
//			kaNiuOrderStateRsp.setLendingTime(lendingTime);// 放款时间
//			kaNiuOrderStateRsp.setApplyTime(applyTime);// 申请时间
//			DecimalFormat df = new DecimalFormat("0.0000");
//			kaNiuOrderStateRsp.setRate(df.format(interestRate / 7) + "");// 日利率
//			kaNiuOrderStateRsp.setReturnReason("");// 退回原因
//			kaNiuOrderStateRsp.setRejectReason("");// 拒绝原因 是
//			kaNiuOrderStateRsp.setChannel("");// 平台渠道 否
//			kaNiuOrderStateRsp.setRepayUrl("");// 还款地址 否
//			kaNiuOrderStateRsp.setPlanlist(repayList);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			kaNiuResponse.setResultCode(KaNiuResponse.SUCCESS);
//			kaNiuResponse.setResultCodeDescription("请求成功");
//			kaNiuResponse.setData(kaNiuOrderStateRsp);
//			logger.info(sessionId + "结束卡牛过滤接口");
//			return kaNiuResponse;
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束卡牛 获取订单状态接口：>>>> 异常：", e);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("系统异常");
//			return kaNiuResponse;
//		}
//	}
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param statusId
//	 * @param orderId
//	 * @return
//	 */
//	private List<KaNiuPlanVo> getRepayList(long sessionId, int statusId, Long orderId) {
//		if (9 == statusId || 13 == statusId || 6 == statusId) {
//			List<KaNiuPlanVo> rspList = new ArrayList<>();
//			logger.info(sessionId + "卡牛获取还款计划");
//			List<BwRepaymentPlan> list = bwRepaymentPlanService.findRepaymentPlanListByOrderId(orderId);
//			if (CollectionUtils.isNotEmpty(list)) {
//				KaNiuPlanVo kaNiuPlanVo = null;
//				for (BwRepaymentPlan bwRepaymentPlan : list) {
//					kaNiuPlanVo = new KaNiuPlanVo();
//					kaNiuPlanVo.setTotalTerm(4);
//					kaNiuPlanVo.setCurrentTerm(bwRepaymentPlan.getNumber());
//					double repayPenaltyInt = 0d;
//					if ("2".equals(bwRepaymentPlan.getRepayType() + "")) {
//						BwOverdueRecord bwOverdueRecord = bwOverdueRecordService
//								.queryBwOverdueByRepayId(bwRepaymentPlan.getId());
//						if (null != bwOverdueRecord) {
//							double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0D
//									: bwOverdueRecord.getOverdueAccrualMoney();
//							double advance = bwOverdueRecord.getAdvance() == null ? 0D : bwOverdueRecord.getAdvance();
//							repayPenaltyInt = overdueAccrualMoney - advance;
//						}
//					}
//					if ("2".equals(bwRepaymentPlan.getRepayStatus() + "")) {
//						kaNiuPlanVo.setRepayStatus("已还清");
//						kaNiuPlanVo.setActuRepayDate(bwRepaymentPlan.getUpdateTime().getTime());// 实际还款时间
//						kaNiuPlanVo.setActuRepayTotalAmt(bwRepaymentPlan.getRealityRepayMoney() + repayPenaltyInt);
//						kaNiuPlanVo.setActuRepayPrincipal(bwRepaymentPlan.getRepayCorpusMoney());
//						kaNiuPlanVo.setActuRepayInterest(bwRepaymentPlan.getRepayAccrualMoney());
//						kaNiuPlanVo.setActuRepayServiceFee(0D);
//						kaNiuPlanVo.setActuRepayMgtFee(0D);
//						kaNiuPlanVo.setActuRepayPenaltyInt(repayPenaltyInt);
//						kaNiuPlanVo.setActuRepayLatefee(0D);
//					} else if ("1".equals(bwRepaymentPlan.getRepayStatus() + "")
//							&& "1".equals(bwRepaymentPlan.getRepayType() + "")) {
//						kaNiuPlanVo.setRepayStatus("未到期");
//					} else if ("2".equals(bwRepaymentPlan.getRepayType() + "")) {
//						kaNiuPlanVo.setRepayStatus("已逾期");
//					} else {
//						kaNiuPlanVo.setRepayStatus("未还清");
//					}
//					kaNiuPlanVo.setRepayDate(new SimpleDateFormat("yyyy-MM-dd").format(bwRepaymentPlan.getRepayTime()));
//					kaNiuPlanVo.setRepayTotalAmt(bwRepaymentPlan.getRealityRepayMoney() + repayPenaltyInt);
//					kaNiuPlanVo.setRepayPrincipal(bwRepaymentPlan.getRepayCorpusMoney());
//					kaNiuPlanVo.setRepayInterest(bwRepaymentPlan.getRepayAccrualMoney());
//					kaNiuPlanVo.setRepayServiceFee(0D);
//					kaNiuPlanVo.setRepayMgtFee(0D);
//					kaNiuPlanVo.setRepayPenaltyInt(repayPenaltyInt);// 罚息/逾期
//					kaNiuPlanVo.setRepayLatefee(0D);
//					rspList.add(kaNiuPlanVo);
//				}
//				return rspList;
//			}
//			return null;
//		} else {
//			return null;
//		}
//	}
//
//	/**
//	 * 还款试算
//	 */
//	@Override
//	public KaNiuResponse calculate(long sessionId, KaNiuCommonReq kaNiuCommonReq) {
//		logger.info(sessionId + "开始卡牛还款试算接口:");
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		try {
//			String OrderNo = kaNiuCommonReq.getLoanNo();
//			String thirdOrderNo = kaNiuCommonReq.getSsjLoanNo();
//			String currentTerm = kaNiuCommonReq.getTerm();
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//				kaNiuResponse.setResultCodeDescription("缺少必要参数");
//				return kaNiuResponse;
//			}
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder || (StringUtils.isNotBlank(OrderNo) && !OrderNo.equals(bwOrder.getOrderNo()))) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_UN_SUPPO);
//				kaNiuResponse.setResultCodeDescription("查询订单为空");
//				return kaNiuResponse;
//			}
//			int statusId = bwOrder.getStatusId().intValue();
//			Long orderId = bwOrder.getId();
//			if (9 == statusId || 13 == statusId) {
//				Example example = new Example(BwRepaymentPlan.class);
//				example.createCriteria().andEqualTo("orderId", orderId);
//				example.setOrderByClause("createTime asc");
//				List<BwRepaymentPlan> bwRepaymentPlanlist = bwRepaymentPlanService.findRepaymentPlanByExample(example);
//				if (CollectionUtils.isNotEmpty(bwRepaymentPlanlist)) {
//					for (BwRepaymentPlan bwRepaymentPlan : bwRepaymentPlanlist) {
//						// 传入期数就返回查询期数，没有传返回当前期数
//						if (StringUtils.isNotBlank(currentTerm)
//								&& !currentTerm.equals(bwRepaymentPlan.getNumber() + "")) {
//							continue;
//						}
//						if (!"2".equals(bwRepaymentPlan.getRepayStatus() + "")) {
//							Double overdueFee = 0D;// 管理费
//							if ("2".equals(bwRepaymentPlan.getRepayType() + "")) {
//								BwOverdueRecord bwOverdueRecord = bwOverdueRecordService
//										.queryBwOverdueByRepayId(bwRepaymentPlan.getId());
//								if (null != bwOverdueRecord) {
//									double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0D
//											: bwOverdueRecord.getOverdueAccrualMoney();
//									double advance = bwOverdueRecord.getAdvance() == null ? 0D
//											: bwOverdueRecord.getAdvance();
//									overdueFee = overdueAccrualMoney - advance;
//								}
//							}
//							Map<String, Double> map = new HashMap<>();
//							map.put("repayAmount", bwRepaymentPlan.getRealityRepayMoney() + overdueFee);
//							map.put("principalAmount", bwRepaymentPlan.getRepayCorpusMoney());// 本金
//							map.put("serviceFee", 0D);// 服务费
//							map.put("interest", bwRepaymentPlan.getRepayAccrualMoney());// 利息
//							map.put("mgtFee", 0D);// 管理费
//							map.put("overdueFee", overdueFee);// 逾期费
//							kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//							kaNiuResponse.setResultCode(KaNiuResponse.SUCCESS);
//							kaNiuResponse.setResultCodeDescription("请求成功");
//							kaNiuResponse.setData(map);
//							return kaNiuResponse;
//						}
//					}
//				}
//			}
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			kaNiuResponse.setResultCode(KaNiuResponse.SUCCESS);
//			kaNiuResponse.setResultCodeDescription("请求成功");
//			logger.info(sessionId + "结束卡牛还款试算接口");
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束卡牛还款试算接口：>>>> 异常：", e);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("系统异常");
//		}
//		return kaNiuResponse;
//	}
//
//	/**
//	 * 还款申请
//	 */
//	@Override
//	public KaNiuResponse updateRepayment(long sessionId, KaNiuCommonReq kaNiuCommonReq) {
//		logger.info(sessionId + "开始卡牛还款申请接口:");
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		try {
//			String OrderNo = kaNiuCommonReq.getLoanNo();
//			String thirdOrderNo = kaNiuCommonReq.getSsjLoanNo();
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//				kaNiuResponse.setResultCodeDescription("缺少必要参数");
//				return kaNiuResponse;
//			}
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder || (StringUtils.isNotBlank(OrderNo) && !OrderNo.equals(bwOrder.getOrderNo()))) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_UN_SUPPO);
//				kaNiuResponse.setResultCodeDescription("查询订单为空");
//				return kaNiuResponse;
//			}
//			DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, thirdOrderNo);
//			String errMsg = "还款申请失败！";
//			if (null != drainageRsp) {
//				if ("000".equals(drainageRsp.getCode())) {
//					kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//					kaNiuResponse.setResultCode(KaNiuResponse.SUCCESS);
//					kaNiuResponse.setResultCodeDescription("还款申请成功");
//					return kaNiuResponse;
//				} else if ("106".equals(drainageRsp.getCode())) {
//					kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//					kaNiuResponse.setResultCode(KaNiuResponse.ERR_ISPROCESSING);
//					kaNiuResponse.setResultCodeDescription("交易处理中");
//					return kaNiuResponse;
//				} else {
//					errMsg = drainageRsp.getMessage();
//				}
//			}
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			kaNiuResponse.setResultCode(KaNiuResponse.ERR_REPAY_FAIL);
//			kaNiuResponse.setResultCodeDescription(errMsg);
//			logger.info(sessionId + "结束还款申请接口");
//		} catch (Exception e) {
//			logger.error(sessionId + ">结束卡牛 还款申请接口：>>>> 异常：", e);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("还款申请异常");
//		}
//		return kaNiuResponse;
//	}
//
//	/**
//	*
//	*/
//	@Override
//	public KaNiuResponse getNextReqUrl(long sessionId, KaNiuBindCardReq kaNiuBindCardReq) {
//		logger.info(sessionId + "开始卡牛绑卡签约跳转接口:");
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		try {
//			String userId = kaNiuBindCardReq.getUserId();
//			String bankCardNo = kaNiuBindCardReq.getBankCardNo();
//			String mobile = kaNiuBindCardReq.getMobile();
//			String idNo = kaNiuBindCardReq.getIdNo();
//			String name = kaNiuBindCardReq.getName();
//			String thirdOrderNo = kaNiuBindCardReq.getInnerNo();
//			if (StringUtils.isBlank(bankCardNo) || StringUtils.isBlank(mobile) || StringUtils.isBlank(idNo)
//					|| StringUtils.isBlank(name) || StringUtils.isBlank(thirdOrderNo)) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//				kaNiuResponse.setResultCodeDescription("缺少必要参数");
//				return kaNiuResponse;
//			}
//			String channelIdstr = KaNiuConstant.SX_CHANNELID;
//			Integer channelId = Integer.valueOf(channelIdstr);
//			BwOrderRong bwOrderRong = new BwOrderRong();
//			bwOrderRong.setThirdOrderNo(thirdOrderNo);
//			bwOrderRong.setChannelId(Long.valueOf(channelId));
//			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//			if (null == bwOrderRong) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_ISPROCESSING);
//				kaNiuResponse.setResultCodeDescription("订单不存在");
//				return kaNiuResponse;
//			}
//			BwOrder bwOrder = bwOrderService.findBwOrderById(bwOrderRong.getOrderId() + "");
//			if (null == bwOrder) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_ISPROCESSING);
//				kaNiuResponse.setResultCodeDescription("订单不存在");
//				return kaNiuResponse;
//			}
//			BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerByOrderId(bwOrder.getId());
//			if (null == bwBorrower) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_ISPROCESSING);
//				kaNiuResponse.setResultCodeDescription("用户不存在");
//				return kaNiuResponse;
//			}
//			Long borrowerId = bwOrder.getBorrowerId();
//			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
//			if (null != bwBankCard) {
//				if (null != bwBankCard.getSignStatus() && null != bwBankCard.getCardNo()
//						&& bwBankCard.getSignStatus() > 0 && bankCardNo.equals(bwBankCard.getCardNo())) {
//					StringBuffer stringBuffer = new StringBuffer();
//					stringBuffer.append(KaNiuConstant.CONTRACT_URL + "?");
//					stringBuffer.append("phone=" + bwBorrower.getPhone());
//					stringBuffer.append("&order_no=" + thirdOrderNo);
//					stringBuffer.append("&platform=4");
//					stringBuffer.append(
//							"&params=" + MD5Util.md5("phone=" + bwBorrower.getPhone() + "&order_no=" + thirdOrderNo));
//					kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//					kaNiuResponse.setResultCode(KaNiuResponse.SUCCESS);
//					Map<String, String> map = new HashMap<>();
//					map.put("url", stringBuffer.toString());
//					kaNiuResponse.setData(map);
//					kaNiuResponse.setResultCodeDescription("签约");
//					return kaNiuResponse;
//				}
//			}
//			Map<String, Object> map = new HashMap<>();
//			map.put("phone", mobile);
//			map.put("name", name);
//			map.put("idCard", idNo);
//			map.put("relPhone", bwBorrower.getPhone());
//			map.put("cardNo", bankCardNo);
//			map.put("userId", userId);
//			map.put("bankCode", kaNiuBindCardReq.getBankCode());
//			String json = JSON.toJSONString(map);
//			RedisUtils.setex("tripartite:bindCardURL:" + thirdOrderNo, json, 24 * 60 * 60);
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			kaNiuResponse.setResultCode(KaNiuResponse.SUCCESS);
//			map = new HashMap<>();
//			map.put("url", KaNiuConstant.BINDCARD_URL + "/sxyDrainage/cloud/bindCardPage.do?orderNo=" + thirdOrderNo);
//			kaNiuResponse.setData(map);
//			kaNiuResponse.setResultCodeDescription("绑卡");
//			return kaNiuResponse;
//		} catch (Exception e) {
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("卡牛绑卡签约跳转异常");
//		}
//		return kaNiuResponse;
//	}
//
//	/**
//	 * 推送补充信息
//	 */
//	@Override
//	public KaNiuResponse saveSupplyInfo(long sessionId, SupplyInfoReq supplyInfoReq) {
//		logger.info(sessionId + " >>>>>>处理卡牛推送补充信息:");
//		KaNiuResponse kaNiuResponse = new KaNiuResponse();
//		try {
//			String thirdOrderNo = supplyInfoReq.getSsjLoanNo();
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_PARAM_EMPTY);
//				kaNiuResponse.setResultCodeDescription("缺少必要参数");
//				return kaNiuResponse;
//			}
//			String channelIdstr = KaNiuConstant.SX_CHANNELID;
//			Integer channelId = Integer.valueOf(channelIdstr);
//			BwOrderRong bwOrderRong = new BwOrderRong();
//			bwOrderRong.setThirdOrderNo(thirdOrderNo);
//			bwOrderRong.setChannelId(Long.valueOf(channelId));
//			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//			if (null == bwOrderRong) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_ISPROCESSING);
//				kaNiuResponse.setResultCodeDescription("订单不存在");
//				return kaNiuResponse;
//			}
//			BwOrder bwOrder = bwOrderService.findBwOrderById(bwOrderRong.getOrderId() + "");
//			if (null == bwOrder) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_ISPROCESSING);
//				kaNiuResponse.setResultCodeDescription("订单不存在");
//				return kaNiuResponse;
//			}
//			BwBorrower borrower = new BwBorrower();
//			borrower.setId(bwOrder.getBorrowerId());
//			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			if (null == borrower) {
//				kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//				kaNiuResponse.setResultCode(KaNiuResponse.ERR_UN_SUPPO);
//				kaNiuResponse.setResultCodeDescription("用户不存在");
//				return kaNiuResponse;
//			}
//			logger.info(sessionId + " >>>>>>处理卡牛推送补充信息 >> 通讯录变量:orderId = " + bwOrder.getId());
//			// 通讯录变量
//			SupContactsInfo contactsInfo = supplyInfoReq.getContactsInfo();
//			if (null != contactsInfo) {
//				saveContactsInfo(sessionId, contactsInfo, bwOrder.getId());
//			} else {
//				logger.info(sessionId + " >>>>>>处理卡牛推送补充信息 >> [无] 通讯录变量:orderId = " + bwOrder.getId());
//			}
//			// 设备信息
//			SupDeviceInfo deviceInfo = supplyInfoReq.getDeviceInfo();
//			if (null != deviceInfo) {
//				saveDeviceInfo(sessionId, deviceInfo, bwOrder.getId());
//			} else {
//				logger.info(sessionId + " >>>>>>处理卡牛推送补充信息 >> [无] 设备信息:orderId = " + bwOrder.getId());
//			}
//			// 行为数据
//			SupRiskInfo riskInfo = supplyInfoReq.getRiskInfo();
//			if (null != riskInfo) {
//				saveRiskInfo(sessionId, riskInfo, bwOrder.getId());
//			} else {
//				logger.info(sessionId + " >>>>>>处理卡牛推送补充信息 >> [无] 行为数据:orderId = " + bwOrder.getId());
//			}
//			// 同盾数据
//			List<SupHitRulesVo> hitRuless = supplyInfoReq.getHit_rules();
//			if (CollectionUtils.isNotEmpty(hitRuless)) {
//				saveHitRuless(sessionId, hitRuless, bwOrder.getId());
//			} else {
//				logger.info(sessionId + " >>>>>>处理卡牛推送补充信息 >> [无] 同盾数据:orderId = " + bwOrder.getId());
//			}
//			String isExists = RedisUtils.getset("tripartite:kaniu:pushorder:" + bwOrder.getId(), "1");
//			logger.info(sessionId + ">>> 添加 KEY > tripartite:kaniu:pushorder:" + bwOrder.getId());
//			// if (StringUtils.isBlank(isExists)) {
//			// RedisUtils.setNxAndEx("tripartite:kaniu:pushorder:" + bwOrder.getId(), "1", 60 * 60 * 12);
//			// logger.info(sessionId + ">>> 添加 KEY > tripartite:kaniu:pushorder:" + bwOrder.getId());
//			// }
//			if ("2".equals(isExists)) {
//				asyncKaNiuService.doSubmit(sessionId, bwOrder, borrower, thirdOrderNo);
//			}
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			kaNiuResponse.setResultCode(KaNiuResponse.SUCCESS);
//			kaNiuResponse.setResultCodeDescription("success");
//		} catch (Exception e) {
//			logger.error(sessionId + " >>>>>>处理卡牛推送补充信息 >> 异常  " + e.getMessage());
//			kaNiuResponse.setResultSuccess(KaNiuResponse.RESULT_SUCCESS);
//			kaNiuResponse.setResultCode(KaNiuResponse.EXCEPTION);
//			kaNiuResponse.setResultCodeDescription("卡牛推送补充信息异常");
//		}
//		return kaNiuResponse;
//	}
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param hitRuless
//	 * @param id
//	 */
//	private void saveHitRuless(long sessionId, List<SupHitRulesVo> hitRuless, Long orderId) {
//		try {
//			BwKaniuHitrules bwKaniuHitrules = new BwKaniuHitrules();
//			bwKaniuHitrules.setOrderId(orderId);
//			int count = bwKaniuHitrulesService.selectCount(bwKaniuHitrules);
//			if (count > 0) {
//				bwKaniuHitrulesService.delete(bwKaniuHitrules);
//			}
//			for (SupHitRulesVo shr : hitRuless) {
//				bwKaniuHitrules = new BwKaniuHitrules();
//				bwKaniuHitrules.setCreateTime(new Date());
//				bwKaniuHitrules.setOrderId(orderId);
//				bwKaniuHitrules.setDecision(shr.getDecision());
//				bwKaniuHitrules.setHid(shr.getId());
//				bwKaniuHitrules.setName(shr.getName());
//				bwKaniuHitrules.setUuid(shr.getUuid());
//				bwKaniuHitrules.setParentuuid(shr.getParentUuid());
//				bwKaniuHitrules.setScore(shr.getScore());
//				bwKaniuHitrulesService.save(bwKaniuHitrules);
//			}
//		} catch (Exception e) {
//			logger.error(
//					sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存同盾数据异常  >> orderId = " + orderId + ">>>" + e.getMessage());
//		}
//		logger.info(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存同盾数据完成 orderId = " + orderId);
//	}
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param riskInfo
//	 */
//	private void saveRiskInfo(long sessionId, SupRiskInfo riskInfo, Long orderId) {
//		try {
//			BwKaniuLbs bwKaniuLbs = new BwKaniuLbs();
//			bwKaniuLbs.setOrderId(orderId);
//			List<BwKaniuLbs> bwKaniuLbsS = bwKaniuLbsService.select(bwKaniuLbs);
//			if (CollectionUtils.isNotEmpty(bwKaniuLbsS)) {
//				bwKaniuLbs = bwKaniuLbsS.get(0);
//				bwKaniuLbs.setUserkn90dChangecityNum(riskInfo.getUserkn_90d_changecity_num());// userkn90d_Changecity_Num
//				bwKaniuLbs.setUserkn90dChangeprovinceNum(riskInfo.getUserkn_90d_changeprovince_num());
//				bwKaniuLbs.setUserknLastcity(riskInfo.getUserkn_lastcity());
//				bwKaniuLbs.setUserknLastprovince(riskInfo.getUserkn_lastprovince());
//				bwKaniuLbs.setUserknPhoneCity(riskInfo.getUserkn_phone_city());
//				bwKaniuLbs.setUserknPhoneProvince(riskInfo.getUserkn_phone_province());
//				bwKaniuLbs.setUserknLastlocation(riskInfo.getUserkn_lastlocation());
//				bwKaniuLbs.setUserknWorkLocation(riskInfo.getUserkn_work_location());
//				bwKaniuLbs.setUserknNoworkLocation(riskInfo.getUserkn_nowork_location());
//				bwKaniuLbs.setCreateTime(new Date());
//				bwKaniuLbsService.updateByPrimaryKey(bwKaniuLbs);
//			} else {
//				bwKaniuLbs = new BwKaniuLbs();
//				bwKaniuLbs.setOrderId(orderId);
//				bwKaniuLbs.setUserkn90dChangecityNum(riskInfo.getUserkn_90d_changecity_num());
//				bwKaniuLbs.setUserkn90dChangeprovinceNum(riskInfo.getUserkn_90d_changeprovince_num());
//				bwKaniuLbs.setUserknLastcity(riskInfo.getUserkn_lastcity());
//				bwKaniuLbs.setUserknLastprovince(riskInfo.getUserkn_lastprovince());
//				bwKaniuLbs.setUserknPhoneCity(riskInfo.getUserkn_phone_city());
//				bwKaniuLbs.setUserknPhoneProvince(riskInfo.getUserkn_phone_province());
//				bwKaniuLbs.setUserknLastlocation(riskInfo.getUserkn_lastlocation());
//				bwKaniuLbs.setUserknWorkLocation(riskInfo.getUserkn_work_location());
//				bwKaniuLbs.setUserknNoworkLocation(riskInfo.getUserkn_nowork_location());
//				bwKaniuLbs.setCreateTime(new Date());
//				bwKaniuLbsService.save(bwKaniuLbs);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存LBS异常  >> orderId = " + orderId + ">>>" + e.getMessage());
//		}
//		logger.info(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存LBS完成 orderId = " + orderId);
//		try {
//			BwKaniuApp bwKaniuApp = new BwKaniuApp();
//			bwKaniuApp.setOrderId(orderId);
//			List<BwKaniuApp> bwKaniuApps = bwKaniuAppService.select(bwKaniuApp);
//			if (CollectionUtils.isNotEmpty(bwKaniuApps)) {
//				bwKaniuApp = bwKaniuApps.get(0);
//				bwKaniuApp.setCreateTime(new Date());
//				bwKaniuApp.setUserknAppGamblingCnt(riskInfo.getUserkn_app_gambling_cnt());
//				bwKaniuApp.setUserknAppCarOwnerCnt(riskInfo.getUserkn_app_car_owner_cnt());
//				bwKaniuApp.setUserknAppOnSaleCnt(riskInfo.getUserkn_app_on_sale_cnt());
//				bwKaniuApp.setUserknAppInstalmentCnt(riskInfo.getUserkn_app_instalment_cnt());
//				bwKaniuApp.setUserknAppStockFundCnt(riskInfo.getUserkn_app_stock_fund_cnt());
//				bwKaniuApp.setUserknAppHomeDecorationCnt(riskInfo.getUserkn_app_home_decoration_cnt());
//				bwKaniuApp.setUserknAppBuyCarCnt(riskInfo.getUserkn_app_buy_car_cnt());
//				bwKaniuApp.setUserknAppBuyHouseCnt(riskInfo.getUserkn_app_buy_house_cnt());
//				bwKaniuApp.setUserknAppJobSearchCnt(riskInfo.getUserkn_app_job_search_cnt());
//				bwKaniuApp.setUserknAppShoppingCnt(riskInfo.getUserkn_app_shopping_cnt());
//				bwKaniuApp.setUserknAppSaveMoneyCnt(riskInfo.getUserkn_app_save_money_cnt());
//				bwKaniuApp.setUserknAppCashLoanCnt(riskInfo.getUserkn_app_cash_loan_cnt());
//				bwKaniuApp.setUserknAppBankCnt(riskInfo.getUserkn_app_bank_cnt());
//				bwKaniuApp.setUserknAppCreditInformationCnt(riskInfo.getUserkn_app_credit_information_cnt());
//				bwKaniuApp.setUserknAppPaymentCnt(riskInfo.getUserkn_app_payment_cnt());
//				bwKaniuApp.setUserknAppRentingCnt(riskInfo.getUserkn_app_renting_cnt());
//				bwKaniuApp.setUserknAppBankLoanCnt(riskInfo.getUserkn_app_bank_loan_cnt());
//				bwKaniuApp.setUserknAppP2pFinancialCnt(riskInfo.getUserkn_app_p2p_financial_cnt());
//				bwKaniuApp.setUserknAppCashLoanCnt(riskInfo.getUserkn_app_cash_loan_cnt());
//				bwKaniuAppService.updateByPrimaryKey(bwKaniuApp);
//			} else {
//				bwKaniuApp = new BwKaniuApp();
//				bwKaniuApp.setOrderId(orderId);
//				bwKaniuApp.setCreateTime(new Date());
//				bwKaniuApp.setUserknAppGamblingCnt(riskInfo.getUserkn_app_gambling_cnt());
//				bwKaniuApp.setUserknAppCarOwnerCnt(riskInfo.getUserkn_app_car_owner_cnt());
//				bwKaniuApp.setUserknAppOnSaleCnt(riskInfo.getUserkn_app_on_sale_cnt());
//				bwKaniuApp.setUserknAppInstalmentCnt(riskInfo.getUserkn_app_instalment_cnt());
//				bwKaniuApp.setUserknAppStockFundCnt(riskInfo.getUserkn_app_stock_fund_cnt());
//				bwKaniuApp.setUserknAppHomeDecorationCnt(riskInfo.getUserkn_app_home_decoration_cnt());
//				bwKaniuApp.setUserknAppBuyCarCnt(riskInfo.getUserkn_app_buy_car_cnt());
//				bwKaniuApp.setUserknAppBuyHouseCnt(riskInfo.getUserkn_app_buy_house_cnt());
//				bwKaniuApp.setUserknAppJobSearchCnt(riskInfo.getUserkn_app_job_search_cnt());
//				bwKaniuApp.setUserknAppShoppingCnt(riskInfo.getUserkn_app_shopping_cnt());
//				bwKaniuApp.setUserknAppSaveMoneyCnt(riskInfo.getUserkn_app_save_money_cnt());
//				bwKaniuApp.setUserknAppCashLoanCnt(riskInfo.getUserkn_app_cash_loan_cnt());
//				bwKaniuApp.setUserknAppBankCnt(riskInfo.getUserkn_app_bank_cnt());
//				bwKaniuApp.setUserknAppCreditInformationCnt(riskInfo.getUserkn_app_credit_information_cnt());
//				bwKaniuApp.setUserknAppPaymentCnt(riskInfo.getUserkn_app_payment_cnt());
//				bwKaniuApp.setUserknAppRentingCnt(riskInfo.getUserkn_app_renting_cnt());
//				bwKaniuApp.setUserknAppBankLoanCnt(riskInfo.getUserkn_app_bank_loan_cnt());
//				bwKaniuApp.setUserknAppP2pFinancialCnt(riskInfo.getUserkn_app_p2p_financial_cnt());
//				bwKaniuApp.setUserknAppCashLoanCnt(riskInfo.getUserkn_app_cash_loan_cnt());
//				bwKaniuAppService.save(bwKaniuApp);
//			}
//		} catch (Exception e) {
//			logger.error(
//					sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存APP异常   >> orderId = " + orderId + ">>>" + e.getMessage());
//		}
//		logger.info(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存APP完成 orderId = " + orderId);
//		try {
//			BwKaniuBehavior bwKaniuBehavior = new BwKaniuBehavior();
//			bwKaniuBehavior.setOrderId(orderId);
//			List<BwKaniuBehavior> bwKaniuBehaviors = bwKaniuBehaviorService.select(bwKaniuBehavior);
//			if (CollectionUtils.isNotEmpty(bwKaniuBehaviors)) {
//				bwKaniuBehavior = bwKaniuBehaviors.get(0);
//				bwKaniuBehavior.setUserknUserReggap(riskInfo.getUserkn_user_reggap());
//				bwKaniuBehavior.setUserknAvgperdayOpencardniuCnt(riskInfo.getUserkn_avgperday_opencardniu_cnt());
//				bwKaniuBehavior.setUserknClickLoanproductFirsttime(riskInfo.getUserkn_click_loanproduct_firsttime());
//				bwKaniuBehavior.setUserknClickLoanproductLasttime(riskInfo.getUserkn_click_loanproduct_lasttime());
//				bwKaniuBehavior.setUserknFirstOpencardniuTime(riskInfo.getUserkn_first_opencardniu_time());
//				bwKaniuBehavior.setUserkn6mOpencardniuDays(riskInfo.getUserkn_6m_opencardniu_days());
//				bwKaniuBehavior.setUserkn30dOpencardniuDays(riskInfo.getUserkn_30d_opencardniu_days());
//				bwKaniuBehavior.setUserkn60dOpencardniuDays(riskInfo.getUserkn_60d_opencardniu_days());
//				bwKaniuBehavior.setUserknActionEnterapplyloanpageFirsttimegap(
//						riskInfo.getUserkn_action_enterapplyloanpage_firsttimegap());
//				bwKaniuBehavior.setUserknActionOpencardniuCntFirstapplyday(
//						riskInfo.getUserkn_action_opencardniu_cnt_firstapplyday());
//				bwKaniuBehavior.setUserknDaygapImportcardOpen(riskInfo.getUserkn_daygap_importcard_open());
//				bwKaniuBehavior
//						.setUserknHourgapAenterapplyloanpageOpen(riskInfo.getUserkn_hourgap_aenterapplyloanpage_open());
//				bwKaniuBehavior.setUserknHourgapImportcardOpen(riskInfo.getUserkn_hourgap_importcard_open());
//				bwKaniuBehavior.setUserknImportbillcntFirstday(riskInfo.getUserkn_importbillcnt_firstday());
//				bwKaniuBehavior.setUserknImportbillrateFirstday(riskInfo.getUserkn_importbillrate_firstday());
//				bwKaniuBehavior.setUserknImportcardcntFirstday(riskInfo.getUserkn_importcardcnt_firstday());
//				bwKaniuBehavior.setUserknImportcardrateFirstday(riskInfo.getUserkn_importcardrate_firstday());
//				bwKaniuBehavior.setUserknRegistercnt(riskInfo.getUserkn_registercnt());
//				bwKaniuBehavior.setUserknUdiduseridcnt(riskInfo.getUserkn_udiduseridcnt());
//				bwKaniuBehavior.setUserknRcCreditCardNum(riskInfo.getUserkn_rc_credit_card_num());
//				bwKaniuBehavior.setUserknAlipaybillNum(riskInfo.getUserkn_alipaybill_num());
//				bwKaniuBehavior.setUserknRcRepositCardNum(riskInfo.getUserkn_rc_reposit_card_num());
//				bwKaniuBehavior.setUserknNewfirstcreditDate(riskInfo.getUserkn_newfirstcredit_date());
//				bwKaniuBehavior.setUserknNewfirstdepositDate(riskInfo.getUserkn_newfirstdeposit_date());
//				bwKaniuBehavior.setUserknFirstcardtype(riskInfo.getUserkn_firstcardtype());
//				bwKaniuBehavior.setDevknDeviceAtime(riskInfo.getDevkn_device_atime());
//				bwKaniuBehavior.setCreateTime(new Date());
//				bwKaniuBehaviorService.updateByPrimaryKey(bwKaniuBehavior);
//			} else {
//				bwKaniuBehavior = new BwKaniuBehavior();
//				bwKaniuBehavior.setOrderId(orderId);
//				bwKaniuBehavior.setUserknUserReggap(riskInfo.getUserkn_user_reggap());
//				bwKaniuBehavior.setUserknAvgperdayOpencardniuCnt(riskInfo.getUserkn_avgperday_opencardniu_cnt());
//				bwKaniuBehavior.setUserknClickLoanproductFirsttime(riskInfo.getUserkn_click_loanproduct_firsttime());
//				bwKaniuBehavior.setUserknClickLoanproductLasttime(riskInfo.getUserkn_click_loanproduct_lasttime());
//				bwKaniuBehavior.setUserknFirstOpencardniuTime(riskInfo.getUserkn_first_opencardniu_time());
//				bwKaniuBehavior.setUserkn6mOpencardniuDays(riskInfo.getUserkn_6m_opencardniu_days());
//				bwKaniuBehavior.setUserkn30dOpencardniuDays(riskInfo.getUserkn_30d_opencardniu_days());
//				bwKaniuBehavior.setUserkn60dOpencardniuDays(riskInfo.getUserkn_60d_opencardniu_days());
//				bwKaniuBehavior.setUserknActionEnterapplyloanpageFirsttimegap(
//						riskInfo.getUserkn_action_enterapplyloanpage_firsttimegap());
//				bwKaniuBehavior.setUserknActionOpencardniuCntFirstapplyday(
//						riskInfo.getUserkn_action_opencardniu_cnt_firstapplyday());
//				bwKaniuBehavior.setUserknDaygapImportcardOpen(riskInfo.getUserkn_daygap_importcard_open());
//				bwKaniuBehavior
//						.setUserknHourgapAenterapplyloanpageOpen(riskInfo.getUserkn_hourgap_aenterapplyloanpage_open());
//				bwKaniuBehavior.setUserknHourgapImportcardOpen(riskInfo.getUserkn_hourgap_importcard_open());
//				bwKaniuBehavior.setUserknImportbillcntFirstday(riskInfo.getUserkn_importbillcnt_firstday());
//				bwKaniuBehavior.setUserknImportbillrateFirstday(riskInfo.getUserkn_importbillrate_firstday());
//				bwKaniuBehavior.setUserknImportcardcntFirstday(riskInfo.getUserkn_importcardcnt_firstday());
//				bwKaniuBehavior.setUserknImportcardrateFirstday(riskInfo.getUserkn_importcardrate_firstday());
//				bwKaniuBehavior.setUserknRegistercnt(riskInfo.getUserkn_registercnt());
//				bwKaniuBehavior.setUserknUdiduseridcnt(riskInfo.getUserkn_udiduseridcnt());
//				bwKaniuBehavior.setUserknRcCreditCardNum(riskInfo.getUserkn_rc_credit_card_num());
//				bwKaniuBehavior.setUserknAlipaybillNum(riskInfo.getUserkn_alipaybill_num());
//				bwKaniuBehavior.setUserknRcRepositCardNum(riskInfo.getUserkn_rc_reposit_card_num());
//				bwKaniuBehavior.setUserknNewfirstcreditDate(riskInfo.getUserkn_newfirstcredit_date());
//				bwKaniuBehavior.setUserknNewfirstdepositDate(riskInfo.getUserkn_newfirstdeposit_date());
//				bwKaniuBehavior.setUserknFirstcardtype(riskInfo.getUserkn_firstcardtype());
//				bwKaniuBehavior.setDevknDeviceAtime(riskInfo.getDevkn_device_atime());
//				bwKaniuBehavior.setCreateTime(new Date());
//				bwKaniuBehaviorService.save(bwKaniuBehavior);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存Behavior异常   >> orderId = " + orderId + ">>>"
//					+ e.getMessage());
//		}
//		logger.info(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存Behavior完成 orderId = " + orderId);
//	}
//
//	/**
//	 * 
//	 * @param sessionId
//	 * @param deviceInfo
//	 * @param orderId
//	 */
//	private void saveDeviceInfo(long sessionId, SupDeviceInfo deviceInfo, Long orderId) {
//		try {
//			BwKaniuDeviceinfo1 bwKaniuDeviceinfo1 = new BwKaniuDeviceinfo1();
//			bwKaniuDeviceinfo1.setOrderId(orderId);
//			List<BwKaniuDeviceinfo1> BwKaniuDeviceinfo1s = bwKaniuDeviceinfo1Service.select(bwKaniuDeviceinfo1);
//			if (CollectionUtils.isNotEmpty(BwKaniuDeviceinfo1s)) {
//				bwKaniuDeviceinfo1 = BwKaniuDeviceinfo1s.get(0);
//				bwKaniuDeviceinfo1.setAccuracy(deviceInfo.getAccuracy());// 定位精度（水平）
//				bwKaniuDeviceinfo1.setActivetime(deviceInfo.getActiveTime());// 从开机到目前的毫秒数不包括休眠时间
//				bwKaniuDeviceinfo1.setAllowmocklocation(deviceInfo.getAllowMockLocation());// 是否允许位置模拟 true 允许 fase 不允许
//				bwKaniuDeviceinfo1.setAltitude(deviceInfo.getAltitude());// 海拔
//				bwKaniuDeviceinfo1.setAltitudeaccuracy(deviceInfo.getAltitudeaccuracy());// 海拔精度（垂直）
//				bwKaniuDeviceinfo1.setApkversion(deviceInfo.getApkVersion());// 应用版本号 卡牛或者随手记的产品版本号
//				bwKaniuDeviceinfo1.setAppos(deviceInfo.getAppOs());// 设备类型如：“android”
//				bwKaniuDeviceinfo1.setAvailablememory(deviceInfo.getAvailableMemory());// 可用内存字节数
//				bwKaniuDeviceinfo1.setAvailablestorage(deviceInfo.getAvailableStorage());// 可用存储空间字节数
//				bwKaniuDeviceinfo1.setBasebandversion(deviceInfo.getBasebandVersion());// 基带版本
//				bwKaniuDeviceinfo1.setBatterylevel(deviceInfo.getBatteryLevel());// 电池电量 百分比
//				bwKaniuDeviceinfo1.setBatterystatus(deviceInfo.getBatteryStatus());// 当前充电状态 0未知 1未充电 2正在充电
//				bwKaniuDeviceinfo1.setBluemac(deviceInfo.getBlueMac());// 蓝牙MAC地址
//				bwKaniuDeviceinfo1.setBoottime(deviceInfo.getBootTime());// 开机时刻的时间戳(毫秒)
//				bwKaniuDeviceinfo1.setBrand(deviceInfo.getBrand());// 获取设备品牌
//				bwKaniuDeviceinfo1.setBrightness(deviceInfo.getBrightness());// 当前屏幕亮度 单位 cd/m2
//				bwKaniuDeviceinfo1.setBulename(deviceInfo.getBuleName());// 蓝牙名称
//				bwKaniuDeviceinfo1.setBundleid(deviceInfo.getBundleId());// 应用的BundleId
//				bwKaniuDeviceinfo1.setBusinesscode(deviceInfo.getBusinessCode());// 业务产品
//				bwKaniuDeviceinfo1.setCellip(deviceInfo.getCellIp());// 2G/3G/4G网络连接的本地IP地址
//				bwKaniuDeviceinfo1.setCity(deviceInfo.getCity());// 城市
//				bwKaniuDeviceinfo1.setCountry(deviceInfo.getCountry());// 区县
//				bwKaniuDeviceinfo1.setCurrenttime(deviceInfo.getCurrentTime());// 采集时的当前时间戳(毫秒)
//				bwKaniuDeviceinfo1.setDeviceid(deviceInfo.getDeviceId());// 设备ID
//				bwKaniuDeviceinfo1.setDevicename(deviceInfo.getDeviceName());// 出厂时的设备名称
//				bwKaniuDeviceinfo1.setDevicesv(deviceInfo.getDeviceSV());// 设备的软件版本号
//				bwKaniuDeviceinfo1.setDiskmemory(deviceInfo.getDiskMemory());// 磁盘容量 （单位：kb）
//				bwKaniuDeviceinfo1.setFactorytime(deviceInfo.getFactoryTime());// 出厂时间
//				bwKaniuDeviceinfo1.setFingerprint(deviceInfo.getFingerPrint());// 生成指纹唯一标示
//				bwKaniuDeviceinfo1.setFpversion(deviceInfo.getFpVersion());// 集成sdk版本号
//				bwKaniuDeviceinfo1.setGpslocation(deviceInfo.getGpsLocation());// 当前GPS坐标
//				bwKaniuDeviceinfo1.setIdfa(deviceInfo.getIdfa());// 广告追踪标示符 ios才有
//				bwKaniuDeviceinfo1.setIdfv(deviceInfo.getIdfv());// 厂商追踪标示符 ios 才有
//				bwKaniuDeviceinfo1.setIsdebug(deviceInfo.getIsDebug());// 手机是否调试状态 true 调试状态 fase 非调试状态
//				bwKaniuDeviceinfo1.setIsemulator(deviceInfo.getIsEmulator());// 是否模拟器 true 是 fase 启
//				bwKaniuDeviceinfo1.setIsusb(deviceInfo.getIsUsb());// 是否开启usb调试 true 开启 fase 未开启
//				bwKaniuDeviceinfo1.setIsvpn(deviceInfo.getIsVpn());// 是否使用vpn true 是 fase 启
//				bwKaniuDeviceinfo1.setLanguage(deviceInfo.getLanguage());// 当前配置的语言
//				bwKaniuDeviceinfo1.setLatitude(deviceInfo.getLatitude());// 客户LBS 纬度
//				bwKaniuDeviceinfo1.setLongitude(deviceInfo.getLongitude());// 客户LBS 经度
//				bwKaniuDeviceinfo1.setModel(deviceInfo.getModel());// 获取手机的型号设备名称
//				bwKaniuDeviceinfo1.setNetworktype(deviceInfo.getNetworkType());// 当前使用的网络连接类型
//				bwKaniuDeviceinfo1.setOs(deviceInfo.getOs());// 操作系统的类型
//				bwKaniuDeviceinfo1.setOsversion(deviceInfo.getOsVersion());// 操作系统发行版本
//				bwKaniuDeviceinfo1.setOwnername(deviceInfo.getOwnerName());// 设备所有者名称
//				bwKaniuDeviceinfo1.setPackagename(deviceInfo.getPackageName());// 应用包名 卡牛或者随手记的产品名称
//				bwKaniuDeviceinfo1.setPixelx(deviceInfo.getPixelX());// 屏幕x方向每英寸像素点数
//				bwKaniuDeviceinfo1.setPixely(deviceInfo.getPixelY());// 屏幕y方向每英寸像素点数
//				bwKaniuDeviceinfo1.setProvince(deviceInfo.getProvince());// 省份
//				bwKaniuDeviceinfo1.setRoot(deviceInfo.getRoot());// 是否ROOT true 是 fase 启
//				bwKaniuDeviceinfo1.setScreenheight(deviceInfo.getScreenHeight());// 屏幕分辨率长度 单位 mm
//				bwKaniuDeviceinfo1.setScreenwidth(deviceInfo.getScreenWidth());// 屏幕分辨率宽度 单位 mm
//				bwKaniuDeviceinfo1.setSsid(deviceInfo.getSsid());// 当前连接的无线网络名称
//				bwKaniuDeviceinfo1.setStreet(deviceInfo.getStreet());// 详细街道
//				bwKaniuDeviceinfo1.setStreetnumber(deviceInfo.getStreetNumber());// 门牌号
//				bwKaniuDeviceinfo1.setTimezone(deviceInfo.getTimeZone());// 当前时区
//				bwKaniuDeviceinfo1.setTokenid(deviceInfo.getTokenId());// 采集请求唯一标示
//				bwKaniuDeviceinfo1.setTotalmemory(deviceInfo.getTotalMemory());// 总内存字节数
//				bwKaniuDeviceinfo1.setTotalstorage(deviceInfo.getTotalStorage());// 总存储空间字节数
//				bwKaniuDeviceinfo1.setTransducer(deviceInfo.getTransducer());// 电容传感器
//				bwKaniuDeviceinfo1.setTrueip(deviceInfo.getTrueIp());// sdk采集的IP地址
//				bwKaniuDeviceinfo1.setUdid(deviceInfo.getUdid());// deviceudid取不到时使用自己生成的id（固定不变））的md5值
//				bwKaniuDeviceinfo1.setUptime(deviceInfo.getUpTime());// 从开机到目前的毫秒数包括休眠时间
//				bwKaniuDeviceinfo1.setWifiip(deviceInfo.getWifiIp());// 当前连接的无线网络的本地IP地址
//				bwKaniuDeviceinfo1.setWifimac(deviceInfo.getWifiMac());// 无线网卡的mac地址
//				bwKaniuDeviceinfo1.setJailbreak(deviceInfo.getJailBreak());// 是否越狱true 是 fase 启
//				bwKaniuDeviceinfo1.setHavephoto(deviceInfo.getHavePhoto());// 是否有摄像头 true 是 fase 启
//				bwKaniuDeviceinfo1.setVirtualmachine(deviceInfo.getVirtualMachine());// 是否虚拟机 true 是 fase 启
//				bwKaniuDeviceinfo1.setHelicalaccelerator(deviceInfo.getHelicalAccelerator());// 螺旋加速器
//				bwKaniuDeviceinfo1.setCreateTime(new Date());
//				bwKaniuDeviceinfo1Service.updateByPrimaryKey(bwKaniuDeviceinfo1);
//			} else {
//				bwKaniuDeviceinfo1 = new BwKaniuDeviceinfo1();
//				bwKaniuDeviceinfo1.setOrderId(orderId);
//				bwKaniuDeviceinfo1.setAccuracy(deviceInfo.getAccuracy());// 定位精度（水平）
//				bwKaniuDeviceinfo1.setActivetime(deviceInfo.getActiveTime());// 从开机到目前的毫秒数不包括休眠时间
//				bwKaniuDeviceinfo1.setAllowmocklocation(deviceInfo.getAllowMockLocation());// 是否允许位置模拟 true 允许 fase 不允许
//				bwKaniuDeviceinfo1.setAltitude(deviceInfo.getAltitude());// 海拔
//				bwKaniuDeviceinfo1.setAltitudeaccuracy(deviceInfo.getAltitudeaccuracy());// 海拔精度（垂直）
//				bwKaniuDeviceinfo1.setApkversion(deviceInfo.getApkVersion());// 应用版本号 卡牛或者随手记的产品版本号
//				bwKaniuDeviceinfo1.setAppos(deviceInfo.getAppOs());// 设备类型如：“android”
//				bwKaniuDeviceinfo1.setAvailablememory(deviceInfo.getAvailableMemory());// 可用内存字节数
//				bwKaniuDeviceinfo1.setAvailablestorage(deviceInfo.getAvailableStorage());// 可用存储空间字节数
//				bwKaniuDeviceinfo1.setBasebandversion(deviceInfo.getBasebandVersion());// 基带版本
//				bwKaniuDeviceinfo1.setBatterylevel(deviceInfo.getBatteryLevel());// 电池电量 百分比
//				bwKaniuDeviceinfo1.setBatterystatus(deviceInfo.getBatteryStatus());// 当前充电状态 0未知 1未充电 2正在充电
//				bwKaniuDeviceinfo1.setBluemac(deviceInfo.getBlueMac());// 蓝牙MAC地址
//				bwKaniuDeviceinfo1.setBoottime(deviceInfo.getBootTime());// 开机时刻的时间戳(毫秒)
//				bwKaniuDeviceinfo1.setBrand(deviceInfo.getBrand());// 获取设备品牌
//				bwKaniuDeviceinfo1.setBrightness(deviceInfo.getBrightness());// 当前屏幕亮度 单位 cd/m2
//				bwKaniuDeviceinfo1.setBulename(deviceInfo.getBuleName());// 蓝牙名称
//				bwKaniuDeviceinfo1.setBundleid(deviceInfo.getBundleId());// 应用的BundleId
//				bwKaniuDeviceinfo1.setBusinesscode(deviceInfo.getBusinessCode());// 业务产品
//				bwKaniuDeviceinfo1.setCellip(deviceInfo.getCellIp());// 2G/3G/4G网络连接的本地IP地址
//				bwKaniuDeviceinfo1.setCity(deviceInfo.getCity());// 城市
//				bwKaniuDeviceinfo1.setCountry(deviceInfo.getCountry());// 区县
//				bwKaniuDeviceinfo1.setCurrenttime(deviceInfo.getCurrentTime());// 采集时的当前时间戳(毫秒)
//				bwKaniuDeviceinfo1.setDeviceid(deviceInfo.getDeviceId());// 设备ID
//				bwKaniuDeviceinfo1.setDevicename(deviceInfo.getDeviceName());// 出厂时的设备名称
//				bwKaniuDeviceinfo1.setDevicesv(deviceInfo.getDeviceSV());// 设备的软件版本号
//				bwKaniuDeviceinfo1.setDiskmemory(deviceInfo.getDiskMemory());// 磁盘容量 （单位：kb）
//				bwKaniuDeviceinfo1.setFactorytime(deviceInfo.getFactoryTime());// 出厂时间
//				bwKaniuDeviceinfo1.setFingerprint(deviceInfo.getFingerPrint());// 生成指纹唯一标示
//				bwKaniuDeviceinfo1.setFpversion(deviceInfo.getFpVersion());// 集成sdk版本号
//				bwKaniuDeviceinfo1.setGpslocation(deviceInfo.getGpsLocation());// 当前GPS坐标
//				bwKaniuDeviceinfo1.setIdfa(deviceInfo.getIdfa());// 广告追踪标示符 ios才有
//				bwKaniuDeviceinfo1.setIdfv(deviceInfo.getIdfv());// 厂商追踪标示符 ios 才有
//				bwKaniuDeviceinfo1.setIsdebug(deviceInfo.getIsDebug());// 手机是否调试状态 true 调试状态 fase 非调试状态
//				bwKaniuDeviceinfo1.setIsemulator(deviceInfo.getIsEmulator());// 是否模拟器 true 是 fase 启
//				bwKaniuDeviceinfo1.setIsusb(deviceInfo.getIsUsb());// 是否开启usb调试 true 开启 fase 未开启
//				bwKaniuDeviceinfo1.setIsvpn(deviceInfo.getIsVpn());// 是否使用vpn true 是 fase 启
//				bwKaniuDeviceinfo1.setLanguage(deviceInfo.getLanguage());// 当前配置的语言
//				bwKaniuDeviceinfo1.setLatitude(deviceInfo.getLatitude());// 客户LBS 纬度
//				bwKaniuDeviceinfo1.setLongitude(deviceInfo.getLongitude());// 客户LBS 经度
//				bwKaniuDeviceinfo1.setModel(deviceInfo.getModel());// 获取手机的型号设备名称
//				bwKaniuDeviceinfo1.setNetworktype(deviceInfo.getNetworkType());// 当前使用的网络连接类型
//				bwKaniuDeviceinfo1.setOs(deviceInfo.getOs());// 操作系统的类型
//				bwKaniuDeviceinfo1.setOsversion(deviceInfo.getOsVersion());// 操作系统发行版本
//				bwKaniuDeviceinfo1.setOwnername(deviceInfo.getOwnerName());// 设备所有者名称
//				bwKaniuDeviceinfo1.setPackagename(deviceInfo.getPackageName());// 应用包名 卡牛或者随手记的产品名称
//				bwKaniuDeviceinfo1.setPixelx(deviceInfo.getPixelX());// 屏幕x方向每英寸像素点数
//				bwKaniuDeviceinfo1.setPixely(deviceInfo.getPixelY());// 屏幕y方向每英寸像素点数
//				bwKaniuDeviceinfo1.setProvince(deviceInfo.getProvince());// 省份
//				bwKaniuDeviceinfo1.setRoot(deviceInfo.getRoot());// 是否ROOT true 是 fase 启
//				bwKaniuDeviceinfo1.setScreenheight(deviceInfo.getScreenHeight());// 屏幕分辨率长度 单位 mm
//				bwKaniuDeviceinfo1.setScreenwidth(deviceInfo.getScreenWidth());// 屏幕分辨率宽度 单位 mm
//				bwKaniuDeviceinfo1.setSsid(deviceInfo.getSsid());// 当前连接的无线网络名称
//				bwKaniuDeviceinfo1.setStreet(deviceInfo.getStreet());// 详细街道
//				bwKaniuDeviceinfo1.setStreetnumber(deviceInfo.getStreetNumber());// 门牌号
//				bwKaniuDeviceinfo1.setTimezone(deviceInfo.getTimeZone());// 当前时区
//				bwKaniuDeviceinfo1.setTokenid(deviceInfo.getTokenId());// 采集请求唯一标示
//				bwKaniuDeviceinfo1.setTotalmemory(deviceInfo.getTotalMemory());// 总内存字节数
//				bwKaniuDeviceinfo1.setTotalstorage(deviceInfo.getTotalStorage());// 总存储空间字节数
//				bwKaniuDeviceinfo1.setTransducer(deviceInfo.getTransducer());// 电容传感器
//				bwKaniuDeviceinfo1.setTrueip(deviceInfo.getTrueIp());// sdk采集的IP地址
//				bwKaniuDeviceinfo1.setUdid(deviceInfo.getUdid());// deviceudid取不到时使用自己生成的id（固定不变））的md5值
//				bwKaniuDeviceinfo1.setUptime(deviceInfo.getUpTime());// 从开机到目前的毫秒数包括休眠时间
//				bwKaniuDeviceinfo1.setWifiip(deviceInfo.getWifiIp());// 当前连接的无线网络的本地IP地址
//				bwKaniuDeviceinfo1.setWifimac(deviceInfo.getWifiMac());// 无线网卡的mac地址
//				bwKaniuDeviceinfo1.setJailbreak(deviceInfo.getJailBreak());// 是否越狱true 是 fase 启
//				bwKaniuDeviceinfo1.setHavephoto(deviceInfo.getHavePhoto());// 是否有摄像头 true 是 fase 启
//				bwKaniuDeviceinfo1.setVirtualmachine(deviceInfo.getVirtualMachine());// 是否虚拟机 true 是 fase 启
//				bwKaniuDeviceinfo1.setHelicalaccelerator(deviceInfo.getHelicalAccelerator());// 螺旋加速器
//				bwKaniuDeviceinfo1.setCreateTime(new Date());
//				bwKaniuDeviceinfo1Service.save(bwKaniuDeviceinfo1);
//			}
//		} catch (Exception e) {
//			logger.error(
//					sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存设备信息1异常   >> orderId = " + orderId + ">>>" + e.getMessage());
//		}
//		logger.info(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存设备信息1完成>> orderId = " + orderId);
//		try {
//			BwKaniuDeviceinfo2 bwKaniuDeviceinfo2 = new BwKaniuDeviceinfo2();
//			bwKaniuDeviceinfo2.setOrderId(orderId);
//			List<BwKaniuDeviceinfo2> BwKaniuDeviceinfo2s = bwKaniuDeviceinfo2Service.select(bwKaniuDeviceinfo2);
//			if (CollectionUtils.isNotEmpty(BwKaniuDeviceinfo2s)) {
//				bwKaniuDeviceinfo2 = BwKaniuDeviceinfo2s.get(0);
//				bwKaniuDeviceinfo2.setCpuabi(deviceInfo.getCpuAbi());
//				bwKaniuDeviceinfo2.setAppnum(deviceInfo.getAppNum());
//				bwKaniuDeviceinfo2.setAttached(deviceInfo.getAttached());
//				bwKaniuDeviceinfo2.setBatterytemp(deviceInfo.getBatteryTemp());
//				bwKaniuDeviceinfo2.setBssid(deviceInfo.getBssid());
//				bwKaniuDeviceinfo2.setCputype(deviceInfo.getCpuType());
//				bwKaniuDeviceinfo2.setCpuhardware(deviceInfo.getCpuHardware());
//				bwKaniuDeviceinfo2.setCpufrequency(deviceInfo.getCpuFrequency());
//				bwKaniuDeviceinfo2.setCpuabi2(deviceInfo.getCpuAbi2());
//				bwKaniuDeviceinfo2.setCreatetime(deviceInfo.getCreateTime());
//				bwKaniuDeviceinfo2.setDevicedrive(deviceInfo.getDeviceDrive());
//				bwKaniuDeviceinfo2.setDeviceip(deviceInfo.getDeviceIp());
//				bwKaniuDeviceinfo2.setDeviceua(deviceInfo.getDeviceUA());
//				bwKaniuDeviceinfo2.setEnv(deviceInfo.getEnv());
//				bwKaniuDeviceinfo2.setDisplay(deviceInfo.getDisplay());
//				bwKaniuDeviceinfo2.setDnsaddress(deviceInfo.getDnsAddress());
//				bwKaniuDeviceinfo2.setFonthash(deviceInfo.getFontHash());
//				bwKaniuDeviceinfo2.setFnam(deviceInfo.getFnam());
//				bwKaniuDeviceinfo2.setGateway(deviceInfo.getGateway());
//				bwKaniuDeviceinfo2.setHardware(deviceInfo.getHardware());
//				bwKaniuDeviceinfo2.setHost(deviceInfo.getHost());
//				bwKaniuDeviceinfo2.setDid(deviceInfo.getId());
//				bwKaniuDeviceinfo2.setIccid(deviceInfo.getIccid());
//				bwKaniuDeviceinfo2.setAndroidid(deviceInfo.getAndroidId());
//				bwKaniuDeviceinfo2.setApkmd5(deviceInfo.getApkMD5());
//				bwKaniuDeviceinfo2.setUpdatetime(deviceInfo.getUpdateTime());
//				bwKaniuDeviceinfo2.setUserid(deviceInfo.getUserId());
//				bwKaniuDeviceinfo2.setUserId(deviceInfo.getUser_id());
//				bwKaniuDeviceinfo2.setUid(deviceInfo.getUid());
//				bwKaniuDeviceinfo2.setTmp1(deviceInfo.getTmp1());
//				bwKaniuDeviceinfo2.setTmp2(deviceInfo.getTmp2());
//				bwKaniuDeviceinfo2.setTmp3(deviceInfo.getTmp3());
//				bwKaniuDeviceinfo2.setTmp4(deviceInfo.getTmp4());
//				bwKaniuDeviceinfo2.setTags(deviceInfo.getTags());
//				bwKaniuDeviceinfo2.setSdkmd5(deviceInfo.getSdkMD5());
//				bwKaniuDeviceinfo2.setSdkversion(deviceInfo.getSdkVersion());
//				bwKaniuDeviceinfo2.setSerialno(deviceInfo.getSerialNo());
//				bwKaniuDeviceinfo2.setSignmd5(deviceInfo.getSignMD5());
//				bwKaniuDeviceinfo2.setProxyinfo(deviceInfo.getProxyInfo());
//				bwKaniuDeviceinfo2.setProxytype(deviceInfo.getProxyType());
//				bwKaniuDeviceinfo2.setProxyurl(deviceInfo.getProxyUrl());
//				bwKaniuDeviceinfo2.setProduct(deviceInfo.getProduct());
//				bwKaniuDeviceinfo2.setPartner(deviceInfo.getPartner());
//				bwKaniuDeviceinfo2.setKernelversion(deviceInfo.getKernelVersion());
//				bwKaniuDeviceinfo2.setOperatecode(deviceInfo.getOperateCode());
//				bwKaniuDeviceinfo2.setVpnip(deviceInfo.getVpnIp());
//				bwKaniuDeviceinfo2.setWifinetmask(deviceInfo.getWifiNetmask());
//				bwKaniuDeviceinfo2.setVpnnetmask(deviceInfo.getVpnNetmask());
//				bwKaniuDeviceinfo2.setCreateTime(new Date());
//				bwKaniuDeviceinfo2Service.updateByPrimaryKey(bwKaniuDeviceinfo2);
//			} else {
//				bwKaniuDeviceinfo2 = new BwKaniuDeviceinfo2();
//				bwKaniuDeviceinfo2.setOrderId(orderId);
//				bwKaniuDeviceinfo2.setCpuabi(deviceInfo.getCpuAbi());
//				bwKaniuDeviceinfo2.setAppnum(deviceInfo.getAppNum());
//				bwKaniuDeviceinfo2.setAttached(deviceInfo.getAttached());
//				bwKaniuDeviceinfo2.setBatterytemp(deviceInfo.getBatteryTemp());
//				bwKaniuDeviceinfo2.setBssid(deviceInfo.getBssid());
//				bwKaniuDeviceinfo2.setCputype(deviceInfo.getCpuType());
//				bwKaniuDeviceinfo2.setCpuhardware(deviceInfo.getCpuHardware());
//				bwKaniuDeviceinfo2.setCpufrequency(deviceInfo.getCpuFrequency());
//				bwKaniuDeviceinfo2.setCpuabi2(deviceInfo.getCpuAbi2());
//				bwKaniuDeviceinfo2.setCreatetime(deviceInfo.getCreateTime());
//				bwKaniuDeviceinfo2.setDevicedrive(deviceInfo.getDeviceDrive());
//				bwKaniuDeviceinfo2.setDeviceip(deviceInfo.getDeviceIp());
//				bwKaniuDeviceinfo2.setDeviceua(deviceInfo.getDeviceUA());
//				bwKaniuDeviceinfo2.setEnv(deviceInfo.getEnv());
//				bwKaniuDeviceinfo2.setDisplay(deviceInfo.getDisplay());
//				bwKaniuDeviceinfo2.setDnsaddress(deviceInfo.getDnsAddress());
//				bwKaniuDeviceinfo2.setFonthash(deviceInfo.getFontHash());
//				bwKaniuDeviceinfo2.setFnam(deviceInfo.getFnam());
//				bwKaniuDeviceinfo2.setGateway(deviceInfo.getGateway());
//				bwKaniuDeviceinfo2.setHardware(deviceInfo.getHardware());
//				bwKaniuDeviceinfo2.setHost(deviceInfo.getHost());
//				bwKaniuDeviceinfo2.setDid(deviceInfo.getId());
//				bwKaniuDeviceinfo2.setIccid(deviceInfo.getIccid());
//				bwKaniuDeviceinfo2.setAndroidid(deviceInfo.getAndroidId());
//				bwKaniuDeviceinfo2.setApkmd5(deviceInfo.getApkMD5());
//				bwKaniuDeviceinfo2.setUpdatetime(deviceInfo.getUpdateTime());
//				bwKaniuDeviceinfo2.setUserid(deviceInfo.getUserId());
//				bwKaniuDeviceinfo2.setUserId(deviceInfo.getUser_id());
//				bwKaniuDeviceinfo2.setUid(deviceInfo.getUid());
//				bwKaniuDeviceinfo2.setTmp1(deviceInfo.getTmp1());
//				bwKaniuDeviceinfo2.setTmp2(deviceInfo.getTmp2());
//				bwKaniuDeviceinfo2.setTmp3(deviceInfo.getTmp3());
//				bwKaniuDeviceinfo2.setTmp4(deviceInfo.getTmp4());
//				bwKaniuDeviceinfo2.setTags(deviceInfo.getTags());
//				bwKaniuDeviceinfo2.setSdkmd5(deviceInfo.getSdkMD5());
//				bwKaniuDeviceinfo2.setSdkversion(deviceInfo.getSdkVersion());
//				bwKaniuDeviceinfo2.setSerialno(deviceInfo.getSerialNo());
//				bwKaniuDeviceinfo2.setSignmd5(deviceInfo.getSignMD5());
//				bwKaniuDeviceinfo2.setProxyinfo(deviceInfo.getProxyInfo());
//				bwKaniuDeviceinfo2.setProxytype(deviceInfo.getProxyType());
//				bwKaniuDeviceinfo2.setProxyurl(deviceInfo.getProxyUrl());
//				bwKaniuDeviceinfo2.setProduct(deviceInfo.getProduct());
//				bwKaniuDeviceinfo2.setPartner(deviceInfo.getPartner());
//				bwKaniuDeviceinfo2.setKernelversion(deviceInfo.getKernelVersion());
//				bwKaniuDeviceinfo2.setOperatecode(deviceInfo.getOperateCode());
//				bwKaniuDeviceinfo2.setVpnip(deviceInfo.getVpnIp());
//				bwKaniuDeviceinfo2.setWifinetmask(deviceInfo.getWifiNetmask());
//				bwKaniuDeviceinfo2.setVpnnetmask(deviceInfo.getVpnNetmask());
//				bwKaniuDeviceinfo2.setCreateTime(new Date());
//				bwKaniuDeviceinfo2Service.save(bwKaniuDeviceinfo2);
//			}
//		} catch (Exception e) {
//			logger.error(
//					sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存设备信息2异常   >> orderId = " + orderId + ">>>" + e.getMessage());
//		}
//		logger.info(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存设备信息2完成>> orderId = " + orderId);
//	}
//
//	/**
//	 * @param orderId
//	 * 
//	 */
//	private void saveContactsInfo(long sessionId, SupContactsInfo contactsInfo, Long orderId) {
//		Long pid = null;
//		try {
//			BwKaniuContactsinfo bwKaniuContactsinfo = new BwKaniuContactsinfo();
//			bwKaniuContactsinfo.setOrderId(orderId);
//			List<BwKaniuContactsinfo> bwKaniuContactsinfos = bwKaniuContactsinfoService.select(bwKaniuContactsinfo);
//			if (CollectionUtils.isNotEmpty(bwKaniuContactsinfos)) {
//				bwKaniuContactsinfo = bwKaniuContactsinfos.get(0);
//				bwKaniuContactsinfo.setCreateTime(new Date());
//				bwKaniuContactsinfo.setAllcontactsnumber(contactsInfo.getAllContactsNumber());
//				bwKaniuContactsinfo.setAnomalynotecontactnumber(contactsInfo.getAnomalyNoteContactNumber());
//				bwKaniuContactsinfo.setUsercalllogsource(contactsInfo.getUserCallLogSource());
//				bwKaniuContactsinfo.setRepeatcontactsnumber(contactsInfo.getRepeatContactsNumber());
//				bwKaniuContactsinfo.setInterflowcontactsnumber(contactsInfo.getInterflowContactsNumber());
//				bwKaniuContactsinfo.setOnlyacceptcontactsnumber(contactsInfo.getOnlyAcceptContactsNumber());
//				bwKaniuContactsinfo.setOnlysendcontactsnumber(contactsInfo.getOnlySendContactsNumber());
//				bwKaniuContactsinfo.setIntimacycontactsnumbers(contactsInfo.getIntimacyContactsNumbers());
//				bwKaniuContactsinfo.setMaxcalltimescontactsnumber(contactsInfo.getMaxCallTimesContactsNumber());
//				bwKaniuContactsinfo.setMaxcalldurationcontactsnumber(contactsInfo.getMaxCallDurationContactsNumber());
//				bwKaniuContactsinfo.setAllcalltimescontactsnumber(contactsInfo.getAllCallTimesContactsNumber());
//				bwKaniuContactsinfo.setAllcalldurationcontactsnumber(contactsInfo.getAllCallDurationContactsNumber());
//				bwKaniuContactsinfoService.updateByPrimaryKey(bwKaniuContactsinfo);
//			} else {
//				bwKaniuContactsinfo = new BwKaniuContactsinfo();
//				bwKaniuContactsinfo.setOrderId(orderId);
//				bwKaniuContactsinfo.setCreateTime(new Date());
//				bwKaniuContactsinfo.setAllcontactsnumber(contactsInfo.getAllContactsNumber());
//				bwKaniuContactsinfo.setAnomalynotecontactnumber(contactsInfo.getAnomalyNoteContactNumber());
//				bwKaniuContactsinfo.setUsercalllogsource(contactsInfo.getUserCallLogSource());
//				bwKaniuContactsinfo.setRepeatcontactsnumber(contactsInfo.getRepeatContactsNumber());
//				bwKaniuContactsinfo.setInterflowcontactsnumber(contactsInfo.getInterflowContactsNumber());
//				bwKaniuContactsinfo.setOnlyacceptcontactsnumber(contactsInfo.getOnlyAcceptContactsNumber());
//				bwKaniuContactsinfo.setOnlysendcontactsnumber(contactsInfo.getOnlySendContactsNumber());
//				bwKaniuContactsinfo.setIntimacycontactsnumbers(contactsInfo.getIntimacyContactsNumbers());
//				bwKaniuContactsinfo.setMaxcalltimescontactsnumber(contactsInfo.getMaxCallTimesContactsNumber());
//				bwKaniuContactsinfo.setMaxcalldurationcontactsnumber(contactsInfo.getMaxCallDurationContactsNumber());
//				bwKaniuContactsinfo.setAllcalltimescontactsnumber(contactsInfo.getAllCallTimesContactsNumber());
//				bwKaniuContactsinfo.setAllcalldurationcontactsnumber(contactsInfo.getAllCallDurationContactsNumber());
//				bwKaniuContactsinfoService.save(bwKaniuContactsinfo);
//			}
//			pid = bwKaniuContactsinfo.getId();
//		} catch (Exception e) {
//			logger.error(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存APP异常  >> orderId = " + orderId + ">>>" + e.getMessage());
//		}
//		logger.info(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存通讯信息完成 >> orderId = " + orderId);
//		List<SupAllPhoneCategory> allPhoneCategorys = contactsInfo.getAllPhoneCategory();
//		if (CollectionUtils.isNotEmpty(allPhoneCategorys)) {
//			BwKaniuAllphonecategory bwKaniuAllphonecategory = new BwKaniuAllphonecategory();
//			bwKaniuAllphonecategory.setOrderId(orderId);
//			int count = bwKaniuAllphonecategoryService.selectCount(bwKaniuAllphonecategory);
//			if (count > 0) {
//				bwKaniuAllphonecategoryService.delete(bwKaniuAllphonecategory);
//			}
//			for (SupAllPhoneCategory supAllPhoneCategory : allPhoneCategorys) {
//				bwKaniuAllphonecategory = new BwKaniuAllphonecategory();
//				bwKaniuAllphonecategory.setOrderId(orderId);
//				bwKaniuAllphonecategory.setPid(pid);
//				bwKaniuAllphonecategory.setAllcallduration(supAllPhoneCategory.getAllCallDuration());
//				bwKaniuAllphonecategory.setAllcalltimes(supAllPhoneCategory.getAllCallTimes());
//				bwKaniuAllphonecategory.setMaxcallduration(supAllPhoneCategory.getMaxCallDuration());
//				bwKaniuAllphonecategory.setMaxcalltimes(supAllPhoneCategory.getMaxCallTimes());
//				bwKaniuAllphonecategory.setNinetydayscallduration(supAllPhoneCategory.getNinetyDaysCallDuration());
//				bwKaniuAllphonecategory.setNinetydayscalltimes(supAllPhoneCategory.getNinetyDaysCallTimes());
//				bwKaniuAllphonecategory.setPhonecategorynumber(supAllPhoneCategory.getPhoneCategoryNumber());
//				bwKaniuAllphonecategory.setSevendayscallduration(supAllPhoneCategory.getSevenDaysCallDuration());
//				bwKaniuAllphonecategory.setSevendayscalltimes(supAllPhoneCategory.getSevenDaysCallTimes());
//				bwKaniuAllphonecategory.setThirtydayscallduration(supAllPhoneCategory.getThirtyDaysCallDuration());
//				bwKaniuAllphonecategory.setThirtydayscalltimes(supAllPhoneCategory.getThirtyDaysCallTimes());
//				bwKaniuAllphonecategory.setType(supAllPhoneCategory.getType());
//				bwKaniuAllphonecategory.setCreateTime(new Date());
//				bwKaniuAllphonecategoryService.save(bwKaniuAllphonecategory);
//			}
//			logger.info(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存通讯信息 >> orderId = " + orderId
//					+ ">BwKaniuAllphonecategory保存完成");
//		} else {
//			logger.info(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存通讯信息 >> orderId = " + orderId + ">无allPhoneCategory节点");
//		}
//		List<SupRelationshipStatistical> relationshipStatisticalss = contactsInfo.getRelationshipStatistical();
//		if (CollectionUtils.isNotEmpty(relationshipStatisticalss)) {
//			BwKaniuRelationshipstatistical bwKaniuRelationshipstatistical = new BwKaniuRelationshipstatistical();
//			bwKaniuRelationshipstatistical.setOrderId(orderId);
//			int count = bwKaniuRelationshipstatisticalService.selectCount(bwKaniuRelationshipstatistical);
//			if (count > 0) {
//				bwKaniuRelationshipstatisticalService.delete(bwKaniuRelationshipstatistical);
//			}
//			for (SupRelationshipStatistical srs : relationshipStatisticalss) {
//				bwKaniuRelationshipstatistical = new BwKaniuRelationshipstatistical();
//				bwKaniuRelationshipstatistical.setOrderId(orderId);
//				bwKaniuRelationshipstatistical.setPid(pid);
//				bwKaniuRelationshipstatistical.setRelationship(srs.getRelationship());
//				bwKaniuRelationshipstatistical.setPhonenum(srs.getPhoneNum());
//				bwKaniuRelationshipstatistical.setCallnum7day(srs.getCallNum7Day());
//				bwKaniuRelationshipstatistical.setCalltime7day(srs.getCallTime7Day());
//				bwKaniuRelationshipstatistical.setCallnum30day(srs.getCallNum30Day());
//				bwKaniuRelationshipstatistical.setCalltime30day(srs.getCallTime30Day());
//				bwKaniuRelationshipstatistical.setCallnum90day(srs.getCallNum90Day());
//				bwKaniuRelationshipstatistical.setCalltime90day(srs.getCallTime90Day());
//				bwKaniuRelationshipstatistical.setSumcallnum(srs.getSumCallNum());
//				bwKaniuRelationshipstatistical.setSumcalltime(srs.getSumCallTime());
//				bwKaniuRelationshipstatistical.setSinglephonehighnum(srs.getSinglePhoneHighNum());
//				bwKaniuRelationshipstatistical.setSinglephonehightime(srs.getSinglePhoneHighTime());
//				bwKaniuRelationshipstatistical.setPhoneonecalltime(srs.getPhoneOneCallTime());
//				bwKaniuRelationshipstatistical.setPhonesumcalltime(srs.getPhoneSumCallTime());
//				bwKaniuRelationshipstatistical.setPhonesumcallnum(srs.getPhoneSumCallNum());
//				bwKaniuRelationshipstatistical.setPhoneintervalday(srs.getPhoneIntervalDay());
//				bwKaniuRelationshipstatistical.setCreateTime(new Date());
//				bwKaniuRelationshipstatisticalService.save(bwKaniuRelationshipstatistical);
//			}
//			logger.info(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存通讯信息 >> orderId = " + orderId
//					+ ">bwKaniuRelationshipstatistical保存完成");
//		} else {
//			logger.info(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存通讯信息 >> orderId = " + orderId
//					+ ">无relationshipStatisticalss节点");
//		}
//		List<SupUserPhoneWithMark> userPhoneWithMarks = contactsInfo.getUserPhoneWithMark();
//		if (CollectionUtils.isNotEmpty(userPhoneWithMarks)) {
//			BwKaniuUserphonewithmark bwKaniuUserphonewithmark = new BwKaniuUserphonewithmark();
//			bwKaniuUserphonewithmark.setOrderId(orderId);
//			int count = bwKaniuUserphonewithmarkService.selectCount(bwKaniuUserphonewithmark);
//			if (count > 0) {
//				bwKaniuUserphonewithmarkService.delete(bwKaniuUserphonewithmark);
//			}
//			for (SupUserPhoneWithMark supwm : userPhoneWithMarks) {
//				bwKaniuUserphonewithmark = new BwKaniuUserphonewithmark();
//				bwKaniuUserphonewithmark.setOrderId(orderId);
//				bwKaniuUserphonewithmark.setPid(pid);
//				bwKaniuUserphonewithmark.setPhonevalue(supwm.getPhoneValue());
//				bwKaniuUserphonewithmark.setUsername(supwm.getUserName());
//				bwKaniuUserphonewithmark.setRemark(supwm.getRemark());
//				bwKaniuUserphonewithmark.setCreateTime(new Date());
//				bwKaniuUserphonewithmarkService.save(bwKaniuUserphonewithmark);
//			}
//			logger.info(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存通讯信息 >> orderId = " + orderId
//					+ ">bwKaniuUserphonewithmark保存完成");
//		} else {
//			logger.info(sessionId + " >>>>>> 保存卡牛推送补充信息 >> 保存通讯信息 >> orderId = " + orderId + ">无userPhoneWithMarks节点");
//		}
//	}
//}
