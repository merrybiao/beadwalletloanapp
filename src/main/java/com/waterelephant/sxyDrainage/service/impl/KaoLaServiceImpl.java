///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageEnum;
//import com.waterelephant.utils.*;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.beadwallet.utils.JsonUtils;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwIdentityCard2;
//import com.waterelephant.entity.BwMerchantOrder;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderProcessRecord;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwOverdueRecord;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.entity.BwRepaymentPlan;
//import com.waterelephant.entity.BwWorkInfo;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwOperateVoiceService;
//import com.waterelephant.service.BwOrderProcessRecordService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.BwProductDictionaryService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.IBwBorrowerService;
//import com.waterelephant.service.IBwMerchantOrderService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.service.IBwRepaymentPlanService;
//import com.waterelephant.service.IBwRepaymentService;
//import com.waterelephant.service.IBwWorkInfoService;
//import com.waterelephant.service.impl.BwAdjunctServiceImpl;
//import com.waterelephant.service.impl.BwIdentityCardServiceImpl;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.kaola.BaseInfo;
//import com.waterelephant.sxyDrainage.entity.kaola.BindCardReq;
//import com.waterelephant.sxyDrainage.entity.kaola.IdentityVerificationInfo;
//import com.waterelephant.sxyDrainage.entity.kaola.JobAndLinkInfo;
//import com.waterelephant.sxyDrainage.entity.kaola.KaoLaResponse;
//import com.waterelephant.sxyDrainage.entity.kaola.KlCommonResp;
//import com.waterelephant.sxyDrainage.entity.kaola.KlContractReq;
//import com.waterelephant.sxyDrainage.entity.kaola.KlRepayReq;
//import com.waterelephant.sxyDrainage.entity.kaola.KlScoreAndLevelInfo;
//import com.waterelephant.sxyDrainage.entity.kaola.KlRepaymentPlanResp;
//import com.waterelephant.sxyDrainage.entity.kaola.UserApplyInfo;
//import com.waterelephant.sxyDrainage.entity.kaola.UserConditionReq;
//import com.waterelephant.sxyDrainage.entity.kaola.VerifyCodeReq;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.KaoLaService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.SxyDrainageConstant;
//import com.waterelephant.sxyDrainage.utils.jdq.Md5Utils;
//import com.waterelephant.sxyDrainage.utils.kaola.KaoLaConstant;
//import com.waterelephant.sxyDrainage.utils.kaola.KaoLaUtils;
//import com.waterelephant.third.service.ThirdCommonService;
//
///**
// * 
// * 
// * Module:
// * 
// * KaoLaServiceImpl.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//@Service
//public class KaoLaServiceImpl implements KaoLaService {
//
//	private static Logger logger = Logger.getLogger(KaNiuServiceImpl.class);
//
//	private static final Integer CHANNEL_ID= Integer.valueOf(KaoLaConstant.CHANNEL_ID);
//
//	@Autowired
//	private CommonService commonService;
//	@Autowired
//	private IBwBorrowerService bwBorrowerService;
//	@Autowired
//	private ThirdCommonService thirdCommonService;
//	@Autowired
//	private BwAdjunctServiceImpl bwAdjunctService;
//	@Autowired
//	private IBwRepaymentPlanService bwRepaymentPlanService;
//	@Autowired
//	private BwOverdueRecordService bwOverdueRecordService;
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
//	private IBwBankCardService bwBankCardService;
//	@Autowired
//	private BwProductDictionaryService bwProductDictionaryService;
//	@Autowired
//	private IBwMerchantOrderService bwMerchantOrderServiceImpl;
//	@Autowired
//	private IBwRepaymentService bwRepaymentService;
//
//	/**
//	 * 用户检查接口
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.KaoLaService#checkUser(long,
//	 *      com.waterelephant.sxyDrainage.entity.kaola.UserConditionReq)
//	 */
//	@Override
//	public KaoLaResponse checkUser(long sessionId, UserConditionReq userConditionReq) {
//		logger.info(sessionId + "开始进入用户检查接口," + JSON.toJSONString(userConditionReq));
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//		// KlCommonResp klCommonResp = new KlCommonResp();
//		KlCommonResp klCommonResp = new KlCommonResp();
//
//		try {
//			if (CommUtils.isNull(userConditionReq)) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg("用户检查请求数据为空");
//				logger.info(sessionId + "用户检查请求数据为空");
//				return kaoLaResponse;
//			}
//			String idCard = userConditionReq.getIdentifier();
//			String phone = userConditionReq.getMobilephone();
//			String name = userConditionReq.getName();
//			DrainageRsp drainageRsp = commonService.checkUser(sessionId, name, phone, idCard);
//
//			// 1-允许；其他不允许：2-存在对方有未结清申请；3-存在对方有严重逾期申请；4-命中对方黑名单；5-用户30 天内被对方拒绝过；6-其他；
//
//			// 2001 命中黑名单规则
//			// 2002 存在进行中的订单
//			// 2003 命中被拒规则
//			// 2004 用户年龄超限
//			// 1002 参数为空
//			if ("1002".equals(drainageRsp.getCode())) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg(drainageRsp.getMessage());
//				logger.info(sessionId + "用户请求参数为空");
//				return kaoLaResponse;
//			}
//
//			if ("2001".equals(drainageRsp.getCode())) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//				kaoLaResponse.setRetMsg("SUCCESS");
//				klCommonResp.setIsAllow(4);
//				klCommonResp.setRefuseReason(drainageRsp.getMessage());
//				kaoLaResponse.setRetData(klCommonResp);
//				logger.info("KaoLaServiceImpl.checkUser()返回数据为：" + JSON.toJSONString(kaoLaResponse));
//				return kaoLaResponse;
//			}
//			if ("2002".equals(drainageRsp.getCode())) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//				kaoLaResponse.setRetMsg("SUCCESS");
//				klCommonResp.setIsAllow(2);
//				klCommonResp.setRefuseReason(drainageRsp.getMessage());
//				kaoLaResponse.setRetData(klCommonResp);
//				logger.info("KaoLaServiceImpl.checkUser()返回数据为：" + JSON.toJSONString(kaoLaResponse));
//				return kaoLaResponse;
//			}
//
//			if ("2003".equals(drainageRsp.getCode())) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//				kaoLaResponse.setRetMsg("SUCCESS");
//				klCommonResp.setIsAllow(5);
//				klCommonResp.setRefuseReason(drainageRsp.getMessage());
//				kaoLaResponse.setRetData(klCommonResp);
//				logger.info("KaoLaServiceImpl.checkUser()返回数据为：" + JSON.toJSONString(kaoLaResponse));
//				return kaoLaResponse;
//			}
//			if ("2004".equals(drainageRsp.getCode())) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//				kaoLaResponse.setRetMsg("SUCCESS");
//				klCommonResp.setIsAllow(6);
//				klCommonResp.setRefuseReason(drainageRsp.getMessage());
//				kaoLaResponse.setRetData(klCommonResp);
//				logger.info("KaoLaServiceImpl.checkUser()返回数据为：" + JSON.toJSONString(kaoLaResponse));
//				return kaoLaResponse;
//			}
//
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//			kaoLaResponse.setRetMsg("SUCCESS");
//			klCommonResp.setIsAllow(1);
//			kaoLaResponse.setRetData(klCommonResp);
//
//		} catch (Exception e) {
//			logger.error(sessionId+"用户检查接口出现异常", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//			kaoLaResponse.setRetMsg("用户检查接口出现异常");
//			klCommonResp.setIsAllow(6);
//			kaoLaResponse.setRetData(klCommonResp);
//		}
//		logger.info(sessionId+	"KaoLaServiceImpl.checkUser()返回数据为：" + JSON.toJSONString(kaoLaResponse));
//		return kaoLaResponse;
//	}
//
//	/**
//	 * 进件接口
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.KaoLaService#saveOrder(long,
//	 *      com.waterelephant.sxyDrainage.entity.kaola.UserApplyInfo)
//	 */
//	@Override
//	public KaoLaResponse saveOrder(long sessionId, UserApplyInfo userApplyInfo) {
//		logger.info(sessionId + "开始进入KaoLaServiceImpl.saveOrder()方法");
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//		// KlSaveOrderResp klCommonResp = new KlSaveOrderResp();
//		KlCommonResp klCommonResp = new KlCommonResp();
//		String loanId = "";
//		// 1：成功；0：失败
//		try {
//			if (userApplyInfo == null) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//				kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//				klCommonResp.setResult(0);
//				klCommonResp.setLoanId(loanId);
//				klCommonResp.setMessage("userApplyInfo为空");
//				kaoLaResponse.setRetData(klCommonResp);
//				return kaoLaResponse;
//			}
//			BaseInfo baseInfo = userApplyInfo.getBaseInfo();
//			IdentityVerificationInfo identityVerificationInfo = userApplyInfo.getIdentityVerificationInfo();
//			JobAndLinkInfo jobAndLinkInfo = userApplyInfo.getJobAndLinkInfo();
//			KlScoreAndLevelInfo klScoreAndLevelInfo = userApplyInfo.getKlScoreAndLevelInfo();
//			if (baseInfo == null || identityVerificationInfo == null || jobAndLinkInfo == null) {
//				logger.info(sessionId + "KaoLaServiceImpl.saveOrder()参数为空");
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//				kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//				klCommonResp.setResult(0);
//				klCommonResp.setLoanId(loanId);
//				klCommonResp.setMessage("进件参数为空");
//				kaoLaResponse.setRetData(klCommonResp);
//				return kaoLaResponse;
//			}
//			loanId = baseInfo.getLoanId();
//			String userName = baseInfo.getCustomerName();
//			String phone = baseInfo.getMobilePhone();
//			String idCard = baseInfo.getIdentifier();
//
//			Integer channelId = Integer.valueOf(KaoLaConstant.CHANNEL_ID);
//			// 新增或更新借款人
//			BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, userName, idCard, phone, channelId);
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
//					logger.info(sessionId + " 结束KaoLaServiceImpl.save()方法，返回结果：存在进行中的订单，请勿重复推送");
//					kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//					kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//					klCommonResp.setResult(0);
//					klCommonResp.setLoanId(loanId);
//					klCommonResp.setMessage("存在进行中的订单");
//					kaoLaResponse.setRetData(klCommonResp);
//					return kaoLaResponse;
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
//			List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);
//			// 第一次进件被审批撤回后，再次进件时，更新第一次的订单
//			if (boList != null && boList.size() > 0) {
//				bwOrder = boList.get(boList.size() - 1);
//				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwOrder.setProductType(2);
//				bwOrder.setExpectMoney(baseInfo.getApplyAmount());
//				bwOrder.setExpectNumber(4);
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
//				bwOrder.setRepayType(2);
//				bwOrder.setProductType(2);
//				bwOrder.setExpectMoney(baseInfo.getApplyAmount());
//				bwOrder.setExpectNumber(4);
//				bwOrderService.addBwOrder(bwOrder);
//			}
//			long orderId = bwOrder.getId();
//			logger.info(sessionId + ">>> 判断是否有草稿状态的订单：ID = " + orderId);
//
//			// 判断是否有融360订单
//			BwOrderRong bwOrderRong = new BwOrderRong();
//			bwOrderRong.setOrderId(orderId);
//			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//			if (bwOrderRong == null) {
//				bwOrderRong = new BwOrderRong();
//				bwOrderRong.setOrderId(orderId);
//				bwOrderRong.setThirdOrderNo(loanId);
//				bwOrderRong.setChannelId(Long.valueOf(channelId));
//				bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//				bwOrderRongService.save(bwOrderRong);
//			} else {
//				if (null == bwOrderRong.getChannelId()) {
//					bwOrderRong.setChannelId(Long.valueOf(channelId));
//				}
//				bwOrderRong.setThirdOrderNo(loanId);
//				bwOrderRongService.update(bwOrderRong);
//			}
//			logger.info(sessionId + ">>> 判断是否有融360订单");
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
//			// 工作信息
//			BwWorkInfo bwWorkInfo = new BwWorkInfo();
//			bwWorkInfo.setOrderId(orderId);
//			bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//			Integer salNowCompanyWorkYear = jobAndLinkInfo.getSalNowCompanyWorkYear();
//			// 找出对应的工作年限
//			String workYear = KaoLaUtils.workYear(salNowCompanyWorkYear);
//			String salCompanyName = jobAndLinkInfo.getSalCompanyName();
//			Integer monthIncome = baseInfo.getMonthIncome();
//			// 找出对应的月收入
//			String income = KaoLaUtils.income(monthIncome);
//			// TODO 工作类型待添加
//
//			if (null == bwWorkInfo) {
//				bwWorkInfo = new BwWorkInfo();
//				bwWorkInfo.setOrderId(orderId);
//				bwWorkInfo.setCallTime("10:00 - 12:00");
//				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//				bwWorkInfo.setCreateTime(Calendar.getInstance().getTime());
//				bwWorkInfo.setWorkYears(CommUtils.isNull(workYear) ? "" : workYear + "");
//				bwWorkInfo.setComName(CommUtils.isNull(salCompanyName) ? "" : salCompanyName);
//				bwWorkInfo.setIncome(CommUtils.isNull(income) ? "" : income + "");
//				// bwWorkInfo.setIndustry(CommUtils.isNull(workProfession) ? "" :
//				// workProfession);
//				bwWorkInfoService.save(bwWorkInfo, borrowerId);
//				logger.info("保存借款人" + borrowerId + "工作信息成功");
//			} else {
//				bwWorkInfo.setCallTime("10:00 - 12:00");
//				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//				bwWorkInfo.setWorkYears(CommUtils.isNull(workYear) ? "" : workYear + "");
//				bwWorkInfo.setComName(CommUtils.isNull(salCompanyName) ? "" : salCompanyName);
//				bwWorkInfo.setIncome(CommUtils.isNull(income) ? "" : income + "");
//				// bwWorkInfo.setIndustry(bwWorkInfo.getIndustry());
//				bwWorkInfoService.update(bwWorkInfo);
//				logger.info("更新借款人" + borrowerId + "工作信息成功");
//			}
//			// 插入个人认证记录
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, channelId);
//
//			logger.info(sessionId + ">>> 判断是否有工作信息");
//
//			// 运营商 TODO 待添加
//
//			// 通话记录 TODO 待添加
//
//			// 认证图片
//			String legalIdf = identityVerificationInfo.getLegalIdf();
//			// 身份证反面照
//			String legalIdp = identityVerificationInfo.getLegalIdp();
//			// 身份证正面照
//			String idUpperPhoto = identityVerificationInfo.getIdUpperPhoto();
//			// 手持身份证照
//			// String faceVerifyPhoto =
//			// identityVerificationInfo.getFaceVerifyPhoto();//活体验证抓取照
//
//			if (StringUtils.isNotBlank(legalIdp)) {
//				String frontImage = UploadToCssUtils.urlUpload(legalIdp, orderId + "_01");
//				// 身份证正面照
//				logger.info(sessionId + ">>> 身份证正面 " + frontImage);
//				// 保存身份证正面照
//				thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, orderId, borrowerId, 0);
//
//			}
//			if (StringUtils.isNotBlank(legalIdf)) {
//				String backImage = UploadToCssUtils.urlUpload(legalIdf, orderId + "_02");
//				// 身份证反面照
//				logger.info(sessionId + ">>> 身份证反面 " + backImage);
//				// 保存身份证反面照
//				thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, orderId, borrowerId, 0);
//
//			}
//			if (StringUtils.isNotBlank(idUpperPhoto)) {
//				String handerImage = UploadToCssUtils.urlUpload(idUpperPhoto, orderId + "_03");
//				// 手持照
//				logger.info(sessionId + ">>> 手持照/人脸 " + handerImage);
//				// 保存手持照
//				thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, orderId, borrowerId, 0);
//
//			}
//			logger.info(sessionId + ">>> 处理认证图片 ");
//
//			// 保存身份证信息
//			BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
//			bwIdentityCard.setBorrowerId(borrowerId);
//			bwIdentityCard = bwIdentityCardServiceImpl.findBwIdentityCardByAttr(bwIdentityCard);
//			logger.info(sessionId + ">>> borrowerId =" + borrowerId);
//			if (bwIdentityCard == null) {
//				bwIdentityCard = new BwIdentityCard2();
//				bwIdentityCard.setBorrowerId(borrowerId);
//				bwIdentityCard.setCreateTime(new Date());
//				bwIdentityCard.setIdCardNumber(idCard);
//				bwIdentityCard.setName(userName);
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCardServiceImpl.saveBwIdentityCard(bwIdentityCard);
//			} else {
//				bwIdentityCard.setIdCardNumber(idCard);
//				bwIdentityCard.setName(userName);
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCardServiceImpl.updateBwIdentityCard(bwIdentityCard);
//			}
//			// 插入身份认证记录
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, channelId);
//
//			logger.info(sessionId + ">>> 处理身份证信息");
//
//			// 亲属联系人
//			BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//			if (bwPersonInfo == null) {
//				bwPersonInfo = new BwPersonInfo();
//				bwPersonInfo.setCreateTime(new Date());
//				bwPersonInfo.setUpdateTime(new Date());
//				bwPersonInfo.setOrderId(orderId);
//				bwPersonInfo.setAddress(baseInfo.getAddr() );
//				bwPersonInfo.setEmail(baseInfo.getEmail() );
//				bwPersonInfo.setRelationName(jobAndLinkInfo.getHomeLinkmanName());
//				bwPersonInfo.setRelationPhone(jobAndLinkInfo.getHomeLinkmanPhone());
//				bwPersonInfo.setUnrelationName(jobAndLinkInfo.getEmergentLinkmanName());
//				bwPersonInfo.setUnrelationPhone(jobAndLinkInfo.getEmergentLinkmanPhone());
//				bwPersonInfoService.add(bwPersonInfo);
//			} else {
//				bwPersonInfo.setAddress(baseInfo.getAddr() );
//				bwPersonInfo.setEmail(baseInfo.getEmail() );
//				bwPersonInfo.setRelationName(jobAndLinkInfo.getHomeLinkmanName());
//				bwPersonInfo.setRelationPhone(jobAndLinkInfo.getHomeLinkmanPhone());
//				bwPersonInfo.setUnrelationName(jobAndLinkInfo.getEmergentLinkmanName());
//				bwPersonInfo.setUnrelationPhone(jobAndLinkInfo.getEmergentLinkmanPhone());
//				bwPersonInfo.setUpdateTime(new Date());
//				bwPersonInfoService.update(bwPersonInfo);
//
//			}
//			logger.info(sessionId + ">>> 处理亲属联系人信息");
//
//			bwOrder.setStatusId(2L);
//			bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//			bwOrderService.updateBwOrder(bwOrder);
//			logger.info(sessionId + ">>> 开始修改订单提交时间");
//
//			SystemAuditDto systemAuditDto = new SystemAuditDto();
//			systemAuditDto.setIncludeAddressBook(0);
//			systemAuditDto.setOrderId(bwOrder.getId());
//			systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
//			systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//			systemAuditDto.setName(borrower.getName());
//			systemAuditDto.setPhone(borrower.getPhone());
//			systemAuditDto.setIdCard(borrower.getIdCard());
//			systemAuditDto.setChannel(bwOrderRong.getChannelId().intValue());
//			systemAuditDto.setThirdOrderId(bwOrderRong.getThirdOrderNo());
//
//			Long reLong = RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(), JsonUtils.toJson(systemAuditDto));
//
//			logger.info(sessionId + ">>> 修改订单状态，并放入" + reLong + "条redis");
//
//			// 修改工单进程表
//			BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//			bwOrderProcessRecord.setOrderId(bwOrder.getId());
//			bwOrderProcessRecord.setSubmitTime(new Date());
//			bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//			logger.info(sessionId + ">>> 开始修改订单进程");
//
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//			kaoLaResponse.setRetData(KaoLaResponse.MSG);
//			klCommonResp.setLoanId(loanId);
//			klCommonResp.setResult(1);
//			klCommonResp.setMessage("进件成功");
//			kaoLaResponse.setRetData(klCommonResp);
//
//		} catch (Exception e) {
//			logger.error(sessionId + "进件接口系统异常", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//			kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//			klCommonResp.setResult(0);
//			klCommonResp.setLoanId(loanId);
//			klCommonResp.setMessage("进件接口系统异常");
//			kaoLaResponse.setRetData(klCommonResp);
//
//		}
//		logger.info("KaoLaServiceImpl.saveOrder()返回数据为：" + JSON.toJSONString(kaoLaResponse));
//		return kaoLaResponse;
//	}
//
//	/**
//	 * 绑卡接口
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.KaoLaService#saveBindCard(long,
//	 *      com.waterelephant.sxyDrainage.entity.kaola.BindCardReq)
//	 */
//	@Override
//	public KaoLaResponse saveBindCard(long sessionId, BindCardReq bindCardReq) {
//		logger.info("开始进入KaoLaServiceImpl.saveBindCard()方法-->>" + JSON.toJSONString(bindCardReq));
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//		// KlBinkcardResp klBinkcardResp = new KlBinkcardResp();
//		KlCommonResp klCommonResp = new KlCommonResp();
//		// 1-需要 0-不需要 验证码
//		// 结果 1-接收成功 0-接收失败
//
//		try {
//			if (CommUtils.isNull(bindCardReq)) {
//				logger.info(sessionId + "-->>>bindCardReq为空");
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//				kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//				klCommonResp.setIsSmsVerify(0);
//				klCommonResp.setMessage(sessionId + "-->>>绑卡请求数据为空");
//				klCommonResp.setResult(0);
//				kaoLaResponse.setRetData(klCommonResp);
//				return kaoLaResponse;
//			}
//
//			String cardNo = bindCardReq.getCardNo();
//			String identifier = bindCardReq.getIdentifier();
//			String loanId = bindCardReq.getLoanId();
//			String name = bindCardReq.getName();
//			String preMobilephone = bindCardReq.getPreMobilephone();
//			String bankName = bindCardReq.getBankName();
//			logger.info("三方订单号为:" + loanId);
//			//解析银行编码
//			//TODO 待处理
//			String bankCode=null;
//
//
//			//添加新的绑卡方式
//			DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//			drainageBindCardVO.setBankCardNo(cardNo);
//			drainageBindCardVO.setBindType("2");
//			drainageBindCardVO.setChannelId(CHANNEL_ID);
//			drainageBindCardVO.setNotify(false);
//			drainageBindCardVO.setIdCardNo(identifier);
//			drainageBindCardVO.setName(name);
//			drainageBindCardVO.setThirdOrderNo(loanId);
//			drainageBindCardVO.setRegPhone(preMobilephone);
//			drainageBindCardVO.setBankCode(bankCode);
//			logger.info("考拉预绑卡之前参数"+JSON.toJSONString(drainageBindCardVO));
//
//			DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(sessionId, drainageBindCardVO);
//
//			if (drainageRsp.getCode().equals(DrainageEnum.CODE_SUCCESS.getCode())) {
//
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//				kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//				klCommonResp.setIsSmsVerify(1);
//				klCommonResp.setMessage("预绑卡成功");
//				klCommonResp.setResult(1);
//				kaoLaResponse.setRetData(klCommonResp);
//
//			} else {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//				kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//				klCommonResp.setIsSmsVerify(0);
//				klCommonResp.setMessage(drainageRsp.getMessage());
//				klCommonResp.setResult(0);
//				kaoLaResponse.setRetData(klCommonResp);
//			}
//
//		} catch (Exception e) {
//			logger.error(sessionId + "绑卡接口系统异常", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//			kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//			klCommonResp.setIsSmsVerify(0);
//			klCommonResp.setMessage(sessionId + "-->>>绑卡接口异常");
//			klCommonResp.setResult(0);
//			kaoLaResponse.setRetData(klCommonResp);
//		}
//		logger.info("KaoLaServiceImpl.saveBindCard()返回数据为：" + JSON.toJSONString(kaoLaResponse));
//		return kaoLaResponse;
//	}
//
//	/**
//	 * 获取验证码(暂时保留)
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.KaoLaService#queryVerifyCode(long,
//	 *      com.waterelephant.sxyDrainage.entity.kaola.VerifyCodeReq)
//	 */
//	@Override
//	public KaoLaResponse queryVerifyCode(long sessionId, VerifyCodeReq verifyCodeReq) {
//		logger.info("开始进入KaoLaServiceImpl.saveBindCard()方法-->>" + JSON.toJSONString(verifyCodeReq));
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//		KlCommonResp klCommonResp = new KlCommonResp();
//		try {
//
//			if (verifyCodeReq == null) {
//				logger.info(sessionId + "-->>>获取验证码接口请求参数为空");
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//				kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//				klCommonResp.setMessage("获取验证码接口请求参数为空");
//				klCommonResp.setResult(0);
//				kaoLaResponse.setRetData(klCommonResp);
//				return kaoLaResponse;
//			}
//			String loanId = verifyCodeReq.getLoanId();
//			String verifyCode = verifyCodeReq.getVerifyCode();
//			DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//			drainageBindCardVO.setThirdOrderNo(loanId);
//			drainageBindCardVO.setChannelId(CHANNEL_ID);
//			drainageBindCardVO.setNotify(false);
//			drainageBindCardVO.setBindType("2");
//			drainageBindCardVO.setVerifyCode(verifyCode);
//			DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(sessionId, drainageBindCardVO);
//			if(drainageRsp!=null){
//				if(drainageRsp.getCode().equals(DrainageEnum.CODE_SUCCESS.getCode())){
//					kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//					kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//					klCommonResp.setMessage("确认绑卡成功");
//					klCommonResp.setResult(1);
//				}else{
//					kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//					kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//					klCommonResp.setMessage(drainageRsp.getMessage());
//					klCommonResp.setResult(0);
//				}
//
//			}else{
//				logger.info(loanId+"考拉确认绑卡返回信息为空");
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//				kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//				klCommonResp.setResult(0);
//				klCommonResp.setMessage(loanId+"绑卡接口返回数据为空");
//			}
//
//		} catch (Exception e) {
//			logger.error(sessionId + "获取验证码接口系统异常", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//			kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//			klCommonResp.setMessage(sessionId + "-->>>获取验证码接口异常");
//			klCommonResp.setResult(0);
//			kaoLaResponse.setRetData(klCommonResp);
//		}
//		logger.info("KaoLaServiceImpl.queryVerifyCode()返回数据为：" + JSON.toJSONString(kaoLaResponse));
//		return kaoLaResponse;
//	}
//
//	/**
//	 * 协议接口(暂时保留)
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.KaoLaService#queryContract(long,
//	 *      com.waterelephant.sxyDrainage.entity.kaola.KlContractReq)
//	 */
//	@Override
//	public KaoLaResponse queryContract(long sessionId, KlContractReq klContractReq) {
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//		KlCommonResp klCommonResp = new KlCommonResp();
//		try {
//			if (klContractReq == null) {
//				logger.info(sessionId + "协议接口请求参数klContractReq为空");
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg("协议接口请求参数为空");
//				klCommonResp.setAgreementShow(1);
//				klCommonResp.setAgreementUrl("");
//				return kaoLaResponse;
//			}
//			String loanId = klContractReq.getLoanId();
//			if (CommUtils.isNull(loanId)) {
//				logger.info(sessionId + "协议接口请求参数loanId为空");
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg("协议接口三方订单编号为空");
//				klCommonResp.setAgreementShow(1);
//				klCommonResp.setAgreementUrl("");
//				kaoLaResponse.setRetData(klCommonResp);
//				return kaoLaResponse;
//
//			}
//			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(loanId);
//			if (bwOrderRong == null) {
//				logger.info(sessionId + "协议接口请求参数bwOrderRong为空");
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg("协议接口三方订单为空");
//				klCommonResp.setAgreementShow(1);
//				klCommonResp.setAgreementUrl("");
//				kaoLaResponse.setRetData(klCommonResp);
//				return kaoLaResponse;
//			}
//			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//			// 如果当前订单不存在表示订单基本信息未推送
//			if (CommUtils.isNull(bwOrder)) {
//				logger.info(sessionId + "协议接口请求参数bwOrder为空");
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg("协议接口订单为空");
//				klCommonResp.setAgreementShow(1);
//				klCommonResp.setAgreementUrl("");
//				kaoLaResponse.setRetData(klCommonResp);
//				return kaoLaResponse;
//			}
//			BwBorrower bwBorrower = new BwBorrower();
//			bwBorrower.setId(bwOrder.getBorrowerId());
//			bwBorrower = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//			if (CommUtils.isNull(bwBorrower)) {
//				logger.info("根据对应的" + bwOrder.getBorrowerId() + "查询不到对应的借款人");
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg("根据手机号查询不到对应的借款人");
//				klCommonResp.setAgreementShow(1);
//				klCommonResp.setAgreementUrl("");
//				kaoLaResponse.setRetData(klCommonResp);
//				return kaoLaResponse;
//
//			}
//
//			String phone = bwBorrower.getPhone();
//			String md5Data=MD5Util.getMd5Value("phone=" + phone + "&order_no=" + loanId);
//			String contractUrl = KaoLaConstant.CONTRACT_URL + "phone=" + phone + "&order_no=" + loanId + "&platform=4&params=" + md5Data + "&returnUrl=" + KaoLaConstant.RETURN_URL;
//			// TODO 返回地址待定
//			logger.info("回调的地址：" + contractUrl);
//
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//			kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//			klCommonResp.setAgreementShow(1);
//			klCommonResp.setAgreementUrl(contractUrl);
//			kaoLaResponse.setRetData(klCommonResp);
//
//		} catch (Exception e) {
//			logger.error("协议接口系统异常", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//			kaoLaResponse.setRetMsg("协议接口系统异常,请稍后重试！");
//
//		}
//		logger.info("KaoLaServiceImpl.queryContract()返回数据为：" + JSON.toJSONString(kaoLaResponse));
//		return kaoLaResponse;
//	}
//
//	/**
//	 * 主动还款接口
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.KaoLaService#updateActiveRepayment(long,
//	 *      com.waterelephant.sxyDrainage.entity.kaola.KlRepayReq)
//	 */
//	@Override
//	public KaoLaResponse updateActiveRepayment(long sessionId, KlRepayReq klRepayReq) {
//		logger.info(sessionId + "-->>开始进入还款接口:" + JSON.toJSONString(klRepayReq));
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//
//		try {
//			if (klRepayReq == null) {
//				logger.info(sessionId + "-->>还款接口请求参数为空");
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg("还款接口请求参数为空");
//				return kaoLaResponse;
//			}
//			String loanId = klRepayReq.getLoanId();
//
//			DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, loanId);
//			logger.info(loanId + "-->>返回还款结果为:" + JSON.toJSONString(drainageRsp));
//			if (drainageRsp != null) {
//				if ("000".equals(drainageRsp.getCode())) {
//					kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//					kaoLaResponse.setRetMsg(drainageRsp.getMessage());
//				} else {
//					kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//					kaoLaResponse.setRetMsg(drainageRsp.getMessage());
//				}
//			} else {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg("接收信息为空");
//			}
//
//		} catch (Exception e) {
//			logger.error(sessionId + "还款接口异常", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//			kaoLaResponse.setRetMsg("还款接口系统异常");
//
//		}
//		logger.info("KaoLaServiceImpl.updateActiveRepayment()返回数据为：" + JSON.toJSONString(kaoLaResponse));
//		return kaoLaResponse;
//	}
//
//	/**
//	 * 6.5. 绑卡结果反馈接口
//	 */
//
//	@Override
//	public KaoLaResponse bindingCardResult(long sessionId, JSONObject parseObject) {
//		logger.info("考拉>>>" + sessionId + " 进入KaoLaServiceImpl bindingCardResult()绑卡结果反馈接口");
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//		// 绑卡信息反馈
//		KlCommonResp klCommonResp = new KlCommonResp();
//
//		try {
//			// 获得请求参数（订单编号）
//			String loanId = parseObject.getString("loanId");
//			String cardNo = parseObject.getString("银行卡号字段");
//			// TODO 待改
//			if (CommUtils.isNull(loanId) || CommUtils.isNull(cardNo)) {
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg("订单号或银行卡号为空");
//				return kaoLaResponse;
//			}
//
//			// 查找银行卡信息
//			BwBankCard bankCard = bwBankCardService.findBwBankCardByThirdOrderNoAndCardNo(loanId, cardNo);
//			// TODO 新方法，打版本
//			if (bankCard == null) {
//				// 绑卡失败
//				// 绑卡状态 0失败
//				klCommonResp.setBindingStatus(0);
//				// 绑卡类型 2储蓄卡
//				klCommonResp.setCardType(2);
//
//			} else {
//				// 绑卡成功
//				// 绑卡状态 1成功
//				klCommonResp.setBindingStatus(1);
//				// 绑卡类型 2储蓄卡
//				klCommonResp.setCardType(2);
//
//			}
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//			kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//			kaoLaResponse.setRetData(klCommonResp);
//		} catch (Exception e) {
//			logger.error("考拉>>>" + sessionId + " 异常结束KaoLaServiceImpl bindingCardResult()绑卡结果反馈接口", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//			kaoLaResponse.setRetMsg("绑卡结果反馈接口异常");
//		}
//		logger.info("KaoLaServiceImpl.bindingCardResult()返回数据为：" + JSON.toJSONString(kaoLaResponse));
//		return kaoLaResponse;
//	}
//
//	/**
//	 * 6.9. 还款计划表拉取接口
//	 */
//
//	@Override
//	public KaoLaResponse qryRepaymentPlanRecord(long sessionId, JSONObject parseObject) {
//		logger.info("考拉>>>" + sessionId + " 进入KaoLaServiceImpl qryRepaymentPlanRecord()还款计划表拉取接口");
//		KaoLaResponse kaoLaResponse = new KaoLaResponse();
//
//		try {
//			// 获得请求参数（订单编号）
//			String loanId = parseObject.getString("loanId");
//
//			// 查询订单Id
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(loanId);
//			if (bwOrder == null) {
//				logger.info("考拉>>>" + sessionId + "订单不存在>>>>>");
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg("订单查询失败");
//				return kaoLaResponse;
//			}
//			// 状态判断
//			// Long statusId = bwOrder.getStatusId();
//			// if (9 != statusId && 13 != statusId && 6 != statusId) {
//			// logger.info("考拉>>>" + sessionId + "订单状态[" + statusId + "]无法拉取还款计划>>>>>");
//			// kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//			// kaoLaResponse.setRetMsg("无法拉取还款计划");
//			// return kaoLaResponse;
//			// }
//			// 查询还款计划
//			List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder.getId());
//			if (bwRepaymentPlans == null || bwRepaymentPlans.size() == 0) {
//				logger.info("考拉>>>" + sessionId + "还款计划不存在>>>>>");
//				kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//				kaoLaResponse.setRetMsg("还款计划查询失败");
//				return kaoLaResponse;
//			}
//
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//			// 封装计划
//			ArrayList<KlRepaymentPlanResp> planList = new ArrayList<>();
//
//			for (BwRepaymentPlan plan : bwRepaymentPlans) {
//				// 封装每期
//				KlRepaymentPlanResp klRepaymentPlanResp = new KlRepaymentPlanResp();
//				// 订单编号
//				klRepaymentPlanResp.setLoanId(loanId);
//				// 期数
//				klRepaymentPlanResp.setPeroidNum(plan.getNumber());
//				// 到期时间
//				klRepaymentPlanResp.setExpirTime(sdf.format(plan.getRepayTime()));
//				// 本金
//				klRepaymentPlanResp.setAmount(plan.getRepayCorpusMoney() + "");
//				// 利息
//				klRepaymentPlanResp.setInterest(plan.getRepayAccrualMoney() + "");
//				// 手续费
//				klRepaymentPlanResp.setFee("0.00");
//				// 可以还款时间
//				klRepaymentPlanResp.setRepayTime(sdf.format(plan.getCreateTime()));
//
//				// repaymentPlanResp.setRepaidAmount("");// 当前已还金额
//
//				// 获取还款状态
//				Integer repayStatus = plan.getRepayStatus();
//				// 定义逾期金额
//				Double overdueFee = 0.0;
//				// 1 未还款
//				if (repayStatus == 1) {
//					// 没有逾期，未还款
//					klRepaymentPlanResp.setBillState(0);
//					// 账单状态 0：未到期
//					klRepaymentPlanResp.setOverdueAmount("0.00");
//					// 逾期费用
//				}
//				// 2 已还款
//				if (repayStatus == 2) {
//					// 账单状态 1：已还款
//					klRepaymentPlanResp.setBillState(1);
//					// 逾期费用
//					klRepaymentPlanResp.setOverdueAmount("0.00");
//
//					// hashMap.put("repaySuccTime", sdf.format(plan.getUpdateTime()));// 还款成功时间
//
//					// 逾期还款
//					if (plan.getRepayType() == 2) {
//						// 查看逾期表记录
//						BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//						bwOverdueRecord.setRepayId(plan.getId());
//						bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//						if (bwOverdueRecord == null) {
//							logger.info("考拉>>>" + sessionId + "还款计划表推送：获取逾期信息失败>>>>>");
//							kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//							kaoLaResponse.setRetMsg("账单逾期，但未查到逾期记录!");
//							return kaoLaResponse;
//						}
//						// 计算逾期费用
//						// 逾期利息
//						Double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0.0D : bwOverdueRecord.getOverdueAccrualMoney();
//						// 免罚息金额
//						Double advance = bwOverdueRecord.getAdvance();
//						// 逾期费用
//						overdueFee = DoubleUtil.sub(overdueAccrualMoney, advance);
//						klRepaymentPlanResp.setOverdueAmount(overdueFee + "");
//
//					}
//				}
//				// 3垫付 逾期
//				if (repayStatus == 3) {
//					// 账单状态 1：已还款
//					klRepaymentPlanResp.setBillState(2);
//					// 查看逾期表记录
//					BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//					bwOverdueRecord.setRepayId(plan.getId());
//					bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//					if (bwOverdueRecord == null) {
//						logger.info("考拉>>>" + sessionId + "还款计划表推送：获取逾期信息失败>>>>>");
//						kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//						kaoLaResponse.setRetMsg("账单逾期，但未查到逾期记录");
//						return kaoLaResponse;
//					}
//					// 计算逾期费用
//					// 逾期利息
//					Double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0.0D : bwOverdueRecord.getOverdueAccrualMoney();
//					// 免罚息金额
//					Double advance = bwOverdueRecord.getAdvance();
//					// 逾期费用
//					overdueFee = DoubleUtil.sub(overdueAccrualMoney, advance);
//					klRepaymentPlanResp.setOverdueAmount(overdueFee + "");
//
//				}
//				// 当前应还金额 ，本金+利息+费用+逾期费
//				Double repayAmount = DoubleUtil.add(DoubleUtil.add(plan.getRepayCorpusMoney(), plan.getRepayAccrualMoney()), overdueFee);
//				klRepaymentPlanResp.setRepayAmount(repayAmount + "");
//				// 还款计划添加到集合
//				planList.add(klRepaymentPlanResp);
//			}
//
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_SUCCESS);
//			kaoLaResponse.setRetMsg(KaoLaResponse.MSG);
//			kaoLaResponse.setRetData(planList);
//			// TODO list字段待改
//			
//		} catch (Exception e) {
//			logger.error("考拉>>>" + sessionId + " 异常结束KaoLaServiceImpl bindingCardResult()绑卡结果反馈接口", e);
//			kaoLaResponse.setRetCode(KaoLaResponse.CODE_FAIL);
//			kaoLaResponse.setRetMsg("还款计划表拉取接口异常");
//		}
//		logger.info("KaoLaServiceImpl.qryRepaymentPlanRecord()返回数据为：" + JSON.toJSONString(kaoLaResponse));
//		return kaoLaResponse;
//	}
//
//}
