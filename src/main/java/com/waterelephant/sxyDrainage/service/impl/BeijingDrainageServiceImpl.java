///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.beadwallet.service.baofu.entity.BindCardRequest;
//import com.beadwallet.service.baofu.entity.BindCardResult;
//import com.beadwallet.service.baofu.service.BaofuServiceSDK;
//import com.beadwallet.utils.JsonUtils;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwIdentityCard2;
//import com.waterelephant.entity.BwMerchantOrder;
//import com.waterelephant.entity.BwOperateBasic;
//import com.waterelephant.entity.BwOperateVoice;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderChannel;
//import com.waterelephant.entity.BwOrderProcessRecord;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.entity.BwWorkInfo;
//import com.waterelephant.loanwallet.utils.BankUtil;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwOperateVoiceService;
//import com.waterelephant.service.BwOrderProcessRecordService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.IBwBorrowerService;
//import com.waterelephant.service.IBwMerchantOrderService;
//import com.waterelephant.service.IBwOrderChannelService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.service.IBwWorkInfoService;
//import com.waterelephant.service.impl.BwIdentityCardServiceImpl;
//import com.waterelephant.sxyDrainage.entity.DrainageEnum;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.beijingDrainage.BeijingDrainageRequest;
//import com.waterelephant.sxyDrainage.entity.beijingDrainage.BeijingDrainageResponse;
//import com.waterelephant.sxyDrainage.entity.beijingDrainage.SxyBasicInfo;
//import com.waterelephant.sxyDrainage.entity.beijingDrainage.SxyBindCardRequest;
//import com.waterelephant.sxyDrainage.entity.beijingDrainage.SxyCallRecord;
//import com.waterelephant.sxyDrainage.entity.beijingDrainage.SxyCheckUserRequest;
//import com.waterelephant.sxyDrainage.entity.beijingDrainage.SxyCompanyInfo;
//import com.waterelephant.sxyDrainage.entity.beijingDrainage.SxyIdentifyInfo;
//import com.waterelephant.sxyDrainage.entity.beijingDrainage.SxyOperator;
//import com.waterelephant.sxyDrainage.entity.beijingDrainage.SxyRequestPush;
//import com.waterelephant.sxyDrainage.service.BeijingDrainageService;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.SxyDrainageConstant;
//import com.waterelephant.sxyDrainage.utils.beijingDrainageUtil.BeijingDrainageUtil;
//import com.waterelephant.third.entity.MessageDto;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.third.utils.ThirdUtil;
//import com.waterelephant.utils.AESUtil;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//import com.waterelephant.utils.UploadToCssUtils;
//
///**
// * Module: BeijingDrainageServiceImpl.java
// * 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//@Service
//public class BeijingDrainageServiceImpl implements BeijingDrainageService {
//	private Logger logger = LoggerFactory.getLogger(BeijingDrainageServiceImpl.class);
//	private static String AES_KEY = "key.channel_";
//
//	@Autowired
//	private CommonService commonService;
//	@Autowired
//	private IBwOrderChannelService bwOrderChannelService;
//	@Autowired
//	private IBwBorrowerService bwBorrowerService;
//	@Autowired
//	private IBwBankCardService bwBankCardService;
//	@Autowired
//	private ThirdCommonService thirdCommonService;
//	@Autowired
//	private IBwOrderService bwOrderService;
//	@Autowired
//	private BwOrderRongService bwOrderRongService;
//	@Autowired
//	private BwOrderProcessRecordService bwOrderProcessRecordService;
//	@Autowired
//	private IBwWorkInfoService bwWorkInfoService;
//	@Autowired
//	private BwOperateBasicService bwOperateBasicService;
//	@Autowired
//	private BwOperateVoiceService bwOperateVoiceService;
//	@Autowired
//	private BwIdentityCardServiceImpl bwIdentityCardServiceImpl;
//	@Autowired
//	private IBwPersonInfoService bwPersonInfoService;
//	@Autowired
//	private IBwMerchantOrderService bwMerchantOrderServiceImpl;
//
//	/**
//	 * 
//	 */
//	@Override
//	public BeijingDrainageResponse checkUser(long sessionId, BeijingDrainageRequest beijingDrainageRequest) {
//		BeijingDrainageResponse beijingDrainageResponse = new BeijingDrainageResponse();
//		logger.info(sessionId + ":存量用户检验接口>>>");
//		try {
//			// AES解密请求参数
//			BwOrderChannel orderChannel = bwOrderChannelService
//					.getOrderChannelByCode(beijingDrainageRequest.getAppId());
//			if (null == orderChannel) {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingDrainageResponse.setMsg("渠道不存在");
//				return beijingDrainageResponse;
//			}
//			String AESKey = SxyDrainageConstant.sxyConfig.getString(AES_KEY + orderChannel.getId());
//			String AESRequest = AESUtil.Decrypt(beijingDrainageRequest.getRequest(), AESKey);
//			if (StringUtils.isBlank(AESRequest)) {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingDrainageResponse.setMsg("请求参数为空");
//				return beijingDrainageResponse;
//			}
//			SxyCheckUserRequest requestCheckUser = JSON.parseObject(AESRequest, SxyCheckUserRequest.class);
//			if (requestCheckUser == null) {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingDrainageResponse.setMsg("请求参数为空");
//				return beijingDrainageResponse;
//			}
//			Map<String, Object> map = new HashMap<>();
//			String name = requestCheckUser.getName();
//			String idCard = requestCheckUser.getIdCard();
//			String mobile = requestCheckUser.getMobile();
//			DrainageRsp drainageRsp = commonService.checkUser(sessionId, name, mobile, idCard);
//			if (!"0000".equals(drainageRsp.getCode())) {
//				map.put("isCanLoan", 0);
//				if (drainageRsp.getCode().equals(DrainageEnum.CODE_RULE_BLACKLIST.getCode())) {
//					map.put("rejectReason", 1);
//					map.put("remark", drainageRsp.getMessage());
//				} else if (drainageRsp.getCode().equals(DrainageEnum.CODE_RULE_ISPROCESSING.getCode())) {
//					map.put("rejectReason", 2);
//					map.put("remark", drainageRsp.getMessage());
//				} else {
//					map.put("rejectReason", 3);
//					map.put("remark", drainageRsp.getMessage());
//				}
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_NETERROR);
//				beijingDrainageResponse.setMsg("失败");
//			} else {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_SUCCESS);
//				beijingDrainageResponse.setMsg("success");
//				map.put("isCanLoan", 1);
//			}
//			BwBorrower bwBorrower = bwBorrowerService.oldUserFilter2(name, mobile, idCard);
//			if (null == bwBorrower) {
//				map.put("isStock", 0);
//			} else {
//				map.put("isStock", 1);
//			}
//			// 是否绑卡
//			BwBankCard bwBankCard = new BwBankCard();
//			if (bwBorrower != null) {
//				bwBankCard.setBorrowerId(bwBorrower.getId());
//				bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
//				// 对于已绑卡用户要回传银行名、卡号、预留手机号
//				if (bwBankCard != null && (bwBankCard.getSignStatus() == 1)) {
//					map.put("bank", bwBankCard.getBankName());
//					map.put("bankCardNum", bwBankCard.getCardNo());
//					map.put("phone", bwBankCard.getPhone());
//				}
//			}
//			// 最高、低额度；最大、小可借周期后期是否改为从产品表中获取
//			// BwProductDictionary bwProductDictionary = bwProductDictionaryService.findById(Long.valueOf(productId));//
//			map.put("maxLimit", 5000 * 100);// 最高额度
//			map.put("minLimit", 1000 * 100);// 最低额度
//			map.put("number", 4);// 期数
//			map.put("periodNum", 28);// 可贷期限
//			map.put("periodUnit", 1);// 1 天 2 月
//			String AESResponseCheckUser = AESUtil.Encrypt(JSON.toJSONString(map), AESKey);
//			beijingDrainageResponse.setResponse(AESResponseCheckUser);
//		} catch (Exception e) {
//			logger.error(sessionId + "执行service层检查用户接口异常", e);
//			beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_NETERROR);
//			beijingDrainageResponse.setMsg("失败");
//		}
//		return beijingDrainageResponse;
//	}
//
//	/**
//	 * 进件推送接口
//	 */
//	@Override
//	public BeijingDrainageResponse savePushOrder(long sessionId, BeijingDrainageRequest beijingDrainageRequest) {
//		BeijingDrainageResponse beijingResponse = new BeijingDrainageResponse();
//		logger.info(sessionId + ":进件推送接口>>>");
//		try {
//			// AES解密请求参数
//			BwOrderChannel orderChannel = bwOrderChannelService
//					.getOrderChannelByCode(beijingDrainageRequest.getAppId());
//			if (null == orderChannel) {
//				beijingResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingResponse.setMsg("渠道不存在");
//				return beijingResponse;
//			}
//			String AESKey = SxyDrainageConstant.sxyConfig.getString(AES_KEY + orderChannel.getId());
//			String AESRequest = AESUtil.Decrypt(beijingDrainageRequest.getRequest(), AESKey);
//			if (StringUtils.isBlank(AESRequest)) {
//				beijingResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingResponse.setMsg("请求参数为空");
//				return beijingResponse;
//			}
//			SxyRequestPush sxyRequestPush = JSON.parseObject(AESRequest, SxyRequestPush.class);
//			if (sxyRequestPush == null) {
//				beijingResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingResponse.setMsg("请求参数为空");
//				return beijingResponse;
//			}
//			SxyBasicInfo sxyBasicInfo = sxyRequestPush.getBasicInfo();
//			SxyIdentifyInfo sxyIdentifyInfo = sxyRequestPush.getIdentifyInfo();
//			SxyCompanyInfo sxyCompanyInfo = sxyRequestPush.getCompanyInfo();
//			List<SxyCallRecord> callRecords = sxyRequestPush.getCallRecords();
//			SxyOperator sxyOperator = sxyRequestPush.getOperator();
//			if (null == sxyBasicInfo || null == sxyIdentifyInfo || null == sxyCompanyInfo || null == sxyOperator
//					|| CollectionUtils.isEmpty(callRecords)) {
//				beijingResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingResponse.setMsg("请求参数为空");
//				return beijingResponse;
//			}
//			String userName = sxyBasicInfo.getName();
//			String idCard = sxyBasicInfo.getIdCard();
//			String phone = sxyBasicInfo.getPhone();
//			Integer channelId = orderChannel.getId();
//			String thirdOrderNo = sxyBasicInfo.getThirdOrderNo();
//			String passworde = sxyBasicInfo.getPassword();
//			if (StringUtils.isBlank(userName)) {
//				beijingResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingResponse.setMsg("姓名为空");
//				return beijingResponse;
//			}
//			if (StringUtils.isBlank(idCard)) {
//				beijingResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingResponse.setMsg("身份证号码为空");
//				return beijingResponse;
//			}
//			if (StringUtils.isBlank(phone)) {
//				beijingResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingResponse.setMsg("手机号为空");
//				return beijingResponse;
//			}
//			if (CommUtils.isNull(channelId)) {
//				logger.info(sessionId + " 结束CommonServiceImpl.saveOrder()方法，返回结果：渠道编码为空");
//				beijingResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingResponse.setMsg("渠道编码为空");
//				return beijingResponse;
//			}
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				logger.info(sessionId + " 结束CommonServiceImpl.saveOrder()方法，返回结果:三方订单号为空");
//				beijingResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingResponse.setMsg("三方订单号为空");
//				return beijingResponse;
//			}
//			
//			// 新增或更新借款人
//			BwBorrower borrower = addOrUpdateBorrower(sessionId, userName, idCard, phone, channelId, passworde);
//			long borrowerId = borrower.getId();
//			logger.info(sessionId + ">>> 新增/更新借款人ID：" + borrowerId);
//			
//			// 判断该渠道是否有撤回的订单
//			BwOrder order = new BwOrder();
//			order.setBorrowerId(borrowerId);
//			order.setStatusId(8L);
//			order.setChannel(channelId);
//			order = bwOrderService.findBwOrderByAttr(order);
//
//			if (order == null) {
//				// 查询是否有进行中的订单
//				long count = bwOrderService.findProOrder(borrowerId + "");
//				logger.info(sessionId + ">>> 进行中的订单校验：" + count);
//				if (count > 0) {
//					beijingResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//					beijingResponse.setMsg("存在进行中的订单，请勿重复推送");
//					return beijingResponse;
//				}
//			}
//			Integer productId = Integer.valueOf(SxyDrainageConstant.productId);
//			// 判断是否有草稿状态的订单
//			BwOrder bwOrder = new BwOrder();
//			bwOrder.setBorrowerId(borrowerId);
//			bwOrder.setStatusId(1L);
//			bwOrder.setProductType(2);
//			bwOrder.setChannel(channelId);
//			bwOrder.setProductId(productId);
//			List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);// 先查询草稿状态的订单
//			bwOrder.setStatusId(8L);
//			List<BwOrder> boList8 = bwOrderService.findBwOrderListByAttr(bwOrder);// 再查询撤回状态的订单
//			boList.addAll(boList8); // 第一次进件被审批撤回后，再次进件时，更新第一次的订单
//			if (boList != null && boList.size() > 0) {
//				bwOrder = boList.get(boList.size() - 1);
//				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwOrder.setProductType(2);
//				bwOrder.setStatusId(1L);
//				bwOrder.setExpectMoney(sxyBasicInfo.getLoanAmount() / 100D);//
//				bwOrder.setExpectNumber(4);
//				bwOrder.setRepayType(2);
//				bwOrderService.updateBwOrder(bwOrder);
//			} else {
//				bwOrder = new BwOrder();
//				bwOrder.setOrderNo(DrainageUtils.generateOrderNo());
//				bwOrder.setBorrowerId(borrower.getId());
//				bwOrder.setStatusId(1L);
//				bwOrder.setCreateTime(Calendar.getInstance().getTime());
//				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwOrder.setChannel(channelId);
//				bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//				bwOrder.setApplyPayStatus(0);
//				bwOrder.setProductId(productId);
//				bwOrder.setProductType(2);
//				bwOrder.setRepayType(2);
//				bwOrder.setExpectMoney(sxyBasicInfo.getLoanAmount() / 100D);
//				bwOrder.setExpectNumber(4);
//				bwOrderService.addBwOrder(bwOrder);
//			}
//			long orderId = bwOrder.getId();
//			logger.info(sessionId + ">>> 判断是否有草稿状态的订单：ID = " + orderId);
//			
//			// 判断是否有订单
//			BwOrderRong bwOrderRong = new BwOrderRong();
//			bwOrderRong.setOrderId(orderId);
//			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//			if (bwOrderRong == null) {
//				bwOrderRong = new BwOrderRong();
//				bwOrderRong.setOrderId(orderId);
//				bwOrderRong.setThirdOrderNo(thirdOrderNo);
//				bwOrderRong.setChannelId(Long.valueOf(channelId));
//				bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//				bwOrderRongService.save(bwOrderRong);
//			} else {
//				if (null == bwOrderRong.getChannelId()) {
//					bwOrderRong.setChannelId(Long.valueOf(channelId));
//				}
//				bwOrderRong.setThirdOrderNo(thirdOrderNo);
//				bwOrderRongService.update(bwOrderRong);
//			}
//			logger.info(sessionId + ">>> 判断是否有订单");
//			
//			// 判断是否有商户订单信息
//			BwMerchantOrder bwMerchantOrder = new BwMerchantOrder();
//			bwMerchantOrder.setOrderId(orderId);
//			bwMerchantOrder = bwMerchantOrderServiceImpl.selectByAtt(bwMerchantOrder);
//			if (bwMerchantOrder == null) {
//				bwMerchantOrder = new BwMerchantOrder();
//				bwMerchantOrder.setBorrowerId(borrowerId);
//				bwMerchantOrder.setCreateTime(Calendar.getInstance().getTime());
//				bwMerchantOrder.setExt3("0");
//				bwMerchantOrder.setMerchantId(0L);
//				bwMerchantOrder.setOrderId(orderId);
//				bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwMerchantOrderServiceImpl.insertByAtt(bwMerchantOrder);
//			} else {
//				bwMerchantOrder.setBorrowerId(borrowerId);
//				bwMerchantOrder.setExt3("0");
//				bwMerchantOrder.setMerchantId(0L);
//				bwMerchantOrder.setOrderId(orderId);
//				bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwMerchantOrderServiceImpl.updateByAtt(bwMerchantOrder);
//			}
//
//			// 判断是否有工作信息
//			BwWorkInfo bwWorkInfo = new BwWorkInfo();
//			bwWorkInfo.setOrderId(orderId);
//			bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//			if (null == bwWorkInfo) {
//				bwWorkInfo = new BwWorkInfo();
//				bwWorkInfo.setOrderId(orderId);
//				bwWorkInfo.setIncome(sxyCompanyInfo.getIncome());
//				bwWorkInfo.setComName(sxyCompanyInfo.getCompanyName());
//				bwWorkInfo.setIndustry(BeijingDrainageUtil.getIndustry(sxyCompanyInfo.getIndustry()));
//				bwWorkInfo.setWorkYears(BeijingDrainageUtil.getWorkYear(sxyCompanyInfo.getJobTime()));
//				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
//				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//				bwWorkInfo.setCreateTime(Calendar.getInstance().getTime());
//				bwWorkInfoService.save(bwWorkInfo, borrowerId);
//			} else {
//				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
//				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//				bwWorkInfo.setIncome(sxyCompanyInfo.getIncome());
//				bwWorkInfo.setComName(sxyCompanyInfo.getCompanyName());
//				bwWorkInfo.setIndustry(BeijingDrainageUtil.getIndustry(sxyCompanyInfo.getIndustry()));
//				bwWorkInfo.setWorkYears(BeijingDrainageUtil.getWorkYear(sxyCompanyInfo.getJobTime()));
//				bwWorkInfoService.update(bwWorkInfo);
//			}
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, channelId); // 插入个人认证记录
//			logger.info(sessionId + ">>> 判断是否有工作信息");
//
//			// 运营商
//			BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
//			if (null == bwOperateBasic) {
//				bwOperateBasic = new BwOperateBasic();
//				bwOperateBasic.setBorrowerId(borrowerId);
//				bwOperateBasic.setUserSource(sxyOperator.getSource());
//				bwOperateBasic.setIdCard(sxyBasicInfo.getIdCard());
//				bwOperateBasic.setAddr(sxyOperator.getAddr());
//				bwOperateBasic.setPhone(sxyBasicInfo.getPhone());
//				bwOperateBasic.setRealName(sxyOperator.getRealName());
//				bwOperateBasic.setCreateTime(new Date());
//				bwOperateBasic.setUpdateTime(new Date());
//				bwOperateBasicService.save(bwOperateBasic);
//			} else {
//				bwOperateBasic.setBorrowerId(borrowerId);
//				bwOperateBasic.setUpdateTime(new Date());
//				bwOperateBasic.setBorrowerId(borrower.getId());
//				bwOperateBasic.setUserSource(sxyOperator.getSource());
//				bwOperateBasic.setIdCard(sxyBasicInfo.getIdCard());
//				bwOperateBasic.setAddr(sxyOperator.getAddr());
//				bwOperateBasic.setPhone(sxyBasicInfo.getPhone());
//				bwOperateBasic.setRealName(sxyOperator.getRealName());
//				bwOperateBasic.setUpdateTime(new Date());
//				bwOperateBasic
//						.setRegTime(BeijingDrainageUtil.formatToDate(sxyOperator.getRegTime(), "yyyy-MM-dd hh:mm:ss"));
//				bwOperateBasicService.update(bwOperateBasic);
//			}
//			logger.info(sessionId + ">>> 处理运营商信息");
//
//			// 通话记录
//			if (CollectionUtils.isNotEmpty(callRecords)) {
//				Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
//				SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//				BwOperateVoice bwOperateVoice = null;
//				for (SxyCallRecord sxyCallRecord : callRecords) {
//					try {
//						Date jsonCallData = sdf_hms.parse(sxyCallRecord.getCallTime());
//						if (callDate == null || jsonCallData.after(callDate)) { // 通话记录采取最新追加的方式
//							bwOperateVoice = new BwOperateVoice();
//							bwOperateVoice.setUpdateTime(new Date());
//							bwOperateVoice.setBorrower_id(borrowerId);
//							bwOperateVoice.setReceive_phone(sxyCallRecord.getReceivePhone());
//							bwOperateVoice.setCall_type(sxyCallRecord.getCallType());
//							bwOperateVoice.setCall_time(sxyCallRecord.getCallTime());
//							bwOperateVoice.setTrade_addr(sxyCallRecord.getTradeAddr());
//							bwOperateVoice.setTrade_time(sxyCallRecord.getTradeTime());
//							bwOperateVoice.setTrade_type(sxyCallRecord.getTradeType());
//							bwOperateVoiceService.save(bwOperateVoice);
//						}
//					} catch (Exception e) {
//						logger.error(sessionId + ">>> 插入单条通话记录异常，忽略该条记录" + e.getMessage());
//					}
//				}
//			}
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 1, channelId);// 插入运营商认证记录
//			logger.info(sessionId + ">>> 处理通话记录信息 ");
//
//			// 认证图片
//			String frontFile = sxyIdentifyInfo.getFrontFile();
//			String backFile = sxyIdentifyInfo.getBackFile();
//			String natureFile = sxyIdentifyInfo.getNatureFile();
//			if (StringUtils.isNotBlank(frontFile)) {
//				String frontImage = UploadToCssUtils.urlUpload(frontFile, orderId + "_01"); // 身份证正面照
//				logger.info(sessionId + ">>> 身份证正面 " + frontImage);
//				thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, orderId, borrowerId, 0); // 保存身份证正面照
//			}
//			if (StringUtils.isNotBlank(backFile)) {
//				String backImage = UploadToCssUtils.urlUpload(backFile, orderId + "_02"); // 身份证反面照
//				logger.info(sessionId + ">>> 身份证反面 " + backImage);
//				thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, orderId, borrowerId, 0); // 保存身份证反面照
//			}
//			if (StringUtils.isNotBlank(natureFile)) {
//				String handerImage = UploadToCssUtils.urlUpload(natureFile, orderId + "_03"); // 手持照
//				logger.info(sessionId + ">>> 手持照/人脸 " + handerImage);
//				thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, orderId, borrowerId, 0); // 保存手持照
//			}
//			logger.info(sessionId + ">>> 处理认证图片 ");
//
//			// 保存身份证信息
//			BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
//			bwIdentityCard.setBorrowerId(borrowerId);
//			bwIdentityCard = bwIdentityCardServiceImpl.findBwIdentityCardByAttr(bwIdentityCard);
//			if (null == bwIdentityCard) {
//				bwIdentityCard = new BwIdentityCard2();
//				bwIdentityCard.setAddress(sxyIdentifyInfo.getAddress());
//				bwIdentityCard.setIdCardNumber(sxyBasicInfo.getIdCard());
//				bwIdentityCard.setName(sxyBasicInfo.getName());
//				bwIdentityCard.setIssuedBy(sxyIdentifyInfo.getIssuedBy());
//				bwIdentityCard.setRace(sxyIdentifyInfo.getNation());
//				bwIdentityCard.setValidDate(sxyIdentifyInfo.getValidDate());
//				bwIdentityCard.setBorrowerId(borrowerId);
//				bwIdentityCard.setCreateTime(new Date());
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCardServiceImpl.saveBwIdentityCard(bwIdentityCard);
//			} else {
//				bwIdentityCard.setAddress(sxyIdentifyInfo.getAddress());
//				bwIdentityCard.setIdCardNumber(sxyBasicInfo.getIdCard());
//				bwIdentityCard.setName(sxyBasicInfo.getName());
//				bwIdentityCard.setIssuedBy(sxyIdentifyInfo.getIssuedBy());
//				bwIdentityCard.setRace(sxyIdentifyInfo.getNation());
//				bwIdentityCard.setValidDate(sxyIdentifyInfo.getValidDate());
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCardServiceImpl.updateBwIdentityCard(bwIdentityCard);
//			}
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, channelId);// 插入身份认证记录
//			logger.info(sessionId + ">>> 处理身份证信息");
//
//			// 亲属联系人
//			BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//			if (null == bwPersonInfo) {
//				bwPersonInfo = new BwPersonInfo();
//				bwPersonInfo.setCreateTime(new Date());
//				bwPersonInfo.setOrderId(orderId);
//				bwPersonInfo.setUpdateTime(new Date());
//				bwPersonInfo.setCityName(sxyBasicInfo.getHouseCity());
//				bwPersonInfo.setAddress(sxyBasicInfo.getHouseAddress());
//				bwPersonInfo.setRelationName(sxyBasicInfo.getFirstName());
//				bwPersonInfo.setRelationPhone(sxyBasicInfo.getFirstPhone());
//				bwPersonInfo.setUnrelationName(sxyBasicInfo.getSecondName());
//				bwPersonInfo.setUnrelationPhone(sxyBasicInfo.getSecondPhone());
//				bwPersonInfo.setMarryStatus(sxyBasicInfo.getMarriage());
//				bwPersonInfo.setEmail(sxyBasicInfo.getEmail());
//				bwPersonInfo.setHouseStatus(sxyBasicInfo.getHaveHouse());
//				bwPersonInfo.setCarStatus(sxyBasicInfo.getHaveCar());
//				bwPersonInfo.setColleagueName(sxyBasicInfo.getColleagueName());
//				bwPersonInfo.setColleaguePhone(sxyBasicInfo.getColleaguePhone());
//				bwPersonInfo.setFriend1Name(sxyBasicInfo.getFriend1Name());
//				bwPersonInfo.setFriend1Phone(sxyBasicInfo.getFriend1Phone());
//				bwPersonInfo.setFriend2Name(sxyBasicInfo.getFriend2Name());
//				bwPersonInfo.setFriend2Phone(sxyBasicInfo.getFriend2Phone());
//				bwPersonInfo.setQqchat(sxyBasicInfo.getQqchat());
//				bwPersonInfo.setWechat(sxyBasicInfo.getWechat());
//				bwPersonInfoService.add(bwPersonInfo);
//			} else {
//				bwPersonInfo.setCityName(sxyBasicInfo.getHouseCity());
//				bwPersonInfo.setAddress(sxyBasicInfo.getHouseAddress());
//				bwPersonInfo.setRelationName(sxyBasicInfo.getFirstName());
//				bwPersonInfo.setRelationPhone(sxyBasicInfo.getFirstPhone());
//				bwPersonInfo.setUnrelationName(sxyBasicInfo.getSecondName());
//				bwPersonInfo.setUnrelationPhone(sxyBasicInfo.getSecondPhone());
//				bwPersonInfo.setMarryStatus(sxyBasicInfo.getMarriage());
//				bwPersonInfo.setEmail(sxyBasicInfo.getEmail());
//				bwPersonInfo.setHouseStatus(sxyBasicInfo.getHaveHouse());
//				bwPersonInfo.setCarStatus(sxyBasicInfo.getHaveCar());
//				bwPersonInfo.setUpdateTime(new Date());
//				bwPersonInfo.setColleagueName(sxyBasicInfo.getColleagueName());
//				bwPersonInfo.setColleaguePhone(sxyBasicInfo.getColleaguePhone());
//				bwPersonInfo.setFriend1Name(sxyBasicInfo.getFriend1Name());
//				bwPersonInfo.setFriend1Phone(sxyBasicInfo.getFriend1Phone());
//				bwPersonInfo.setFriend2Name(sxyBasicInfo.getFriend2Name());
//				bwPersonInfo.setFriend2Phone(sxyBasicInfo.getFriend2Phone());
//				bwPersonInfo.setQqchat(sxyBasicInfo.getQqchat());
//				bwPersonInfo.setWechat(sxyBasicInfo.getWechat());
//				bwPersonInfoService.update(bwPersonInfo);
//			}
//			logger.info(sessionId + ">>> 处理亲属联系人信息");
//			BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//			bwOrderProcessRecord.setSubmitTime(new Date());
//			bwOrderProcessRecord.setOrderId(bwOrder.getId());
//			bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//			logger.info(sessionId + ">>> 更改订单进行时间");
//			// 修改订单状态
//			bwOrder.setStatusId(2L);
//			bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//			bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//			bwOrderService.updateBwOrder(bwOrder);
//
//			// 审核 放入redis
//			SystemAuditDto systemAuditDto = new SystemAuditDto();
//			systemAuditDto.setIncludeAddressBook(0);
//			systemAuditDto.setOrderId(orderId);
//			systemAuditDto.setBorrowerId(borrowerId);
//			systemAuditDto.setName(userName);
//			systemAuditDto.setPhone(phone);
//			systemAuditDto.setIdCard(idCard);
//			systemAuditDto.setChannel(channelId);
//			systemAuditDto.setThirdOrderId(thirdOrderNo);
//			systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//			RedisUtils.hset(SystemConstant.AUDIT_KEY, orderId + "", JsonUtils.toJson(systemAuditDto));
//			logger.info(sessionId + ">>> 修改订单状态，并放入redis");
//
//			beijingResponse.setCode(BeijingDrainageResponse.CODE_SUCCESS);
//			beijingResponse.setMsg("success");
//		} catch (Exception e) {
//			logger.error(sessionId + "执行进件推送接口异常", e);
//			beijingResponse.setCode(BeijingDrainageResponse.CODE_NETERROR);
//			beijingResponse.setMsg("失败");
//		}
//		return beijingResponse;
//	}
//
//	/**
//	 * 绑卡接口
//	 */
//	@Override
//	public BeijingDrainageResponse saveBindCard(long sessionId, BeijingDrainageRequest beijingDrainageRequest) {
//		BeijingDrainageResponse beijingDrainageResponse = new BeijingDrainageResponse();
//		logger.info(sessionId + ":绑卡接口>>>");
//		try {
//			// AES解密请求参数
//			BwOrderChannel orderChannel = bwOrderChannelService
//					.getOrderChannelByCode(beijingDrainageRequest.getAppId());
//			if (null == orderChannel) {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingDrainageResponse.setMsg("渠道不存在");
//				return beijingDrainageResponse;
//			}
//			String AESKey = SxyDrainageConstant.sxyConfig.getString(AES_KEY + orderChannel.getId());
//			String AESRequest = AESUtil.Decrypt(beijingDrainageRequest.getRequest(), AESKey);
//			if (StringUtils.isBlank(AESRequest)) {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingDrainageResponse.setMsg("请求参数为空");
//				return beijingDrainageResponse;
//			}
//			SxyBindCardRequest sxyBindCardRequest = JSON.parseObject(AESRequest, SxyBindCardRequest.class);
//			if (sxyBindCardRequest == null) {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingDrainageResponse.setMsg("请求参数为空");
//				return beijingDrainageResponse;
//			}
//			Integer channelId = orderChannel.getId();
//			String userName = sxyBindCardRequest.getName();
//			String idCard = sxyBindCardRequest.getIdCard();
//			String phone = sxyBindCardRequest.getPhone();
//			String bankCode = sxyBindCardRequest.getBankName();
//			String password = sxyBindCardRequest.getPassword();
//			if (StringUtils.isBlank(userName)) {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingDrainageResponse.setMsg("姓名不能为空");
//				return beijingDrainageResponse;
//			}
//			if (StringUtils.isBlank(idCard)) {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingDrainageResponse.setMsg("身份证号码不能为空");
//				return beijingDrainageResponse;
//			}
//			if (StringUtils.isBlank(phone)) {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//				beijingDrainageResponse.setMsg("联系人手机不能为空");
//				return beijingDrainageResponse;
//			}
//			BwBorrower borrower = addOrUpdateBorrower(sessionId, userName, idCard, phone, channelId, password);
//			long borrowerId = borrower.getId();
//			String bankCardNo = sxyBindCardRequest.getBankCardNo();
//			String regPhone = sxyBindCardRequest.getBankPhone();
//			// String thirdOrderNo = sxyBindCardRequest.getThirdOrderNo();
//			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
//			String cardName = BankUtil.getname(bankCardNo);
//			String cardCode = DrainageUtils.convertToBankCode(cardName);
//			logger.info(sessionId + ":绑卡接口>>>cardCode=" + cardCode);
//			if (bwBankCard != null && bwBankCard.getSignStatus() == 1) {
//				if (bankCardNo.equals(bwBankCard.getCardNo())) {
//					beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_SUCCESS);
//					beijingDrainageResponse.setMsg("绑卡成功");
//					return beijingDrainageResponse;
//				}
//			}
//			// 开始绑卡
//			if (StringUtils.isBlank(bankCode)) {
//				bankCode = DrainageUtils.convertFuiouBankCodeToBaofu(cardCode);
//			}
//			BindCardRequest bcr = new BindCardRequest();
//			bcr.setPay_code(bankCode);
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
//						bwBankCard.setBankName(cardName);
//						bwBankCard.setBankCode(cardCode);
//						bwBankCard.setCardNo(bankCardNo);
//						bwBankCard.setPhone(regPhone);
//						bwBankCard.setSignStatus(1);
//						bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
//						bwBankCardService.updateBwBankCard(bwBankCard);
//					} else {
//						bwBankCard = new BwBankCard();
//						bwBankCard.setBankName(cardName);
//						bwBankCard.setBankCode(cardCode);
//						bwBankCard.setCardNo(bankCardNo);
//						bwBankCard.setPhone(regPhone);
//						bwBankCard.setBorrowerId(borrowerId);
//						bwBankCard.setSignStatus(1);
//						bwBankCard.setCreateTime(Calendar.getInstance().getTime());
//						bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
//						bwBankCardService.saveBwBankCard(bwBankCard, borrowerId);
//					}
//					beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_SUCCESS);
//					beijingDrainageResponse.setMsg("绑卡成功");
//					return beijingDrainageResponse;
//				} else {
//					logger.info(sessionId + "结束绑卡接口 >>> 绑卡失败:" + bindCardResult.getResp_msg());
//					beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_NETERROR);
//					beijingDrainageResponse.setMsg("绑卡失败:" + bindCardResult.getResp_msg());
//					return beijingDrainageResponse;
//				}
//			} else {
//				beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_NETERROR);
//				beijingDrainageResponse.setMsg("绑卡失败");
//				return beijingDrainageResponse;
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + "执行绑卡接口异常", e);
//			beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_NETERROR);
//			beijingDrainageResponse.setMsg("失败");
//		}
//		return beijingDrainageResponse;
//	}
//
//	public BwBorrower addOrUpdateBorrower(long sessionId, String name, String idCard, String phone, int channelId,
//			String password) throws Exception {
//		// 根据手机号查询
//		BwBorrower borrower = new BwBorrower();
//		borrower.setPhone(phone);
//		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//		if (!CommUtils.isNull(borrower)) {
//			borrower.setPhone(phone);
//			borrower.setAuthStep(1);
//			borrower.setFlag(1);
//			borrower.setState(1);
//			// borrower.setChannel(channelId); // 表示该借款人来源
//			borrower.setIdCard(idCard);
//			borrower.setName(name);
//			borrower.setAge(ThirdUtil.getAgeByIdCard(idCard));
//			borrower.setSex(ThirdUtil.getSexByIdCard(idCard));
//			borrower.setUpdateTime(Calendar.getInstance().getTime());
//			bwBorrowerService.updateBwBorrower(borrower);
//		} else {
//			borrower = new BwBorrower();
//			boolean flag = true;
//			if (StringUtils.isBlank(password)) {
//				flag = false;
//				password = CommUtils.getMD5(ThirdUtil.getRandomPwd().getBytes());
//			}
//			// 创建借款人
//			borrower.setPhone(phone);
//			borrower.setPassword(password);
//			borrower.setAuthStep(1);
//			borrower.setFlag(1);
//			borrower.setState(1);
//			borrower.setChannel(channelId); // 表示该借款人来源
//			borrower.setIdCard(idCard);
//			borrower.setName(name);
//			borrower.setAge(ThirdUtil.getAgeByIdCard(idCard));
//			borrower.setSex(ThirdUtil.getSexByIdCard(idCard));
//			borrower.setCreateTime(Calendar.getInstance().getTime());
//			borrower.setUpdateTime(Calendar.getInstance().getTime());
//			bwBorrowerService.addBwBorrower(borrower);
//			if (!flag) {
//				// 发送短信
//				try {
//					if (RedisUtils.exists("tripartite:smsFilter:registerPassword:" + channelId) == false) {
//						String message = ThirdUtil.getMsg(password);
//						MessageDto messageDto = new MessageDto();
//						messageDto.setBusinessScenario("2");
//						messageDto.setPhone(phone);
//						messageDto.setMsg(message);
//						messageDto.setType("1");
//						RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
//					}
//				} catch (Exception e) {
//					logger.error(sessionId + "发送短信异常:", e);
//				}
//			}
//		}
//		return borrower;
//	}
//
//	// /**
//	// * 接收结果
//	// */
//	// @Override
//	// public BeijingDrainageResponse pushResult(long sessionId, BeijingDrainageRequest beijingDrainageRequest) {
//	// BeijingDrainageResponse beijingDrainageResponse = new BeijingDrainageResponse();
//	// logger.info(sessionId + ":接收结果>>>");
//	// try {
//	// // AES解密请求参数
//	// BwOrderChannel orderChannel = bwOrderChannelService
//	// .getOrderChannelByCode(beijingDrainageRequest.getAppId());
//	// if (null == orderChannel) {
//	// beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//	// beijingDrainageResponse.setMsg("渠道不存在");
//	// return beijingDrainageResponse;
//	// }
//	// String AESKey = SxyDrainageConstant.sxyConfig.getString(AES_KEY + orderChannel.getId());
//	// String AESRequest = AESUtil.Decrypt(beijingDrainageRequest.getRequest(), AESKey);
//	// if (StringUtils.isBlank(AESRequest)) {
//	// beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//	// beijingDrainageResponse.setMsg("请求参数为空");
//	// return beijingDrainageResponse;
//	// }
//	// JSONObject jsonObject = JSON.parseObject(AESRequest);
//	// if (jsonObject == null) {
//	// beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//	// beijingDrainageResponse.setMsg("请求参数为空");
//	// return beijingDrainageResponse;
//	// }
//	// String thirdOrderNo = jsonObject.getString("thirdOrderNo");
//	// String status = jsonObject.getString("status");
//	// if (StringUtils.isBlank(thirdOrderNo) || StringUtils.isBlank(status)) {
//	// beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//	// beijingDrainageResponse.setMsg("请求参数为空");
//	// return beijingDrainageResponse;
//	// }
//	// BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//	// if (null == bwOrder) {
//	// beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//	// beijingDrainageResponse.setMsg("订单不存在");
//	// return beijingDrainageResponse;
//	// }
//	// Long orderId = bwOrder.getId();
//	// BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerByOrderId(orderId);
//	// if (null == bwBorrower) {
//	// beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_PARAMETER);
//	// beijingDrainageResponse.setMsg("用户不存在");
//	// return beijingDrainageResponse;
//	// }
//	// // 放入机审redis
//	// SystemAuditDto systemAuditDto = new SystemAuditDto();
//	// systemAuditDto.setIncludeAddressBook(0);
//	// systemAuditDto.setOrderId(bwOrder.getId());
//	// systemAuditDto.setBorrowerId(bwBorrower.getId());
//	// systemAuditDto.setName(bwBorrower.getName());
//	// systemAuditDto.setPhone(bwBorrower.getPhone());
//	// systemAuditDto.setIdCard(bwBorrower.getIdCard());
//	// systemAuditDto.setChannel(orderChannel.getId());
//	// systemAuditDto.setThirdOrderId(thirdOrderNo);
//	// systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//	// RedisUtils.hset(SystemConstant.AUDIT_KEY, orderId + "", JsonUtils.toJson(systemAuditDto));
//	// // status 1 审批通过 2 审批拒绝
//	// RedisUtils.hset("tripartite:callback", "result_" + orderChannel.getId(), status);
//	// beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_SUCCESS);
//	// beijingDrainageResponse.setMsg("success");
//	// } catch (Exception e) {
//	// logger.error(sessionId + "接收结果异常", e);
//	// beijingDrainageResponse.setCode(BeijingDrainageResponse.CODE_NETERROR);
//	// beijingDrainageResponse.setMsg("失败");
//	// }
//	// return beijingDrainageResponse;
//	//
//	// }
//}
