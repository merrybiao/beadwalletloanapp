///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
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
//import com.beadwallet.utils.JsonUtils;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.BwBlacklist;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwIdentityCard2;
//import com.waterelephant.entity.BwMerchantOrder;
//import com.waterelephant.entity.BwOperateBasic;
//import com.waterelephant.entity.BwOperateVoice;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwOverdueRecord;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.entity.BwProductDictionary;
//import com.waterelephant.entity.BwRepaymentPlan;
//import com.waterelephant.entity.BwWorkInfo;
//import com.waterelephant.entity.BwZmxyGrade;
//import com.waterelephant.service.BwBlacklistService;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwOperateVoiceService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.BwProductDictionaryService;
//import com.waterelephant.service.BwZmxyGradeService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.service.IBwRepaymentPlanService;
//import com.waterelephant.service.IBwWorkInfoService;
//import com.waterelephant.service.impl.BwBorrowerService;
//import com.waterelephant.service.impl.BwIdentityCardServiceImpl;
//import com.waterelephant.service.impl.BwMerchantOrderServiceImpl;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.AddInfoVo;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.ApplyDetailVo;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.BindCardRequest;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.Calls;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.CallsItems;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.CarrierVo;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.CheckUserRequest;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.ConfirmOrderRequest;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.RtyResponse;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.RtyResponseNew;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.RytPushOrderVo;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.RytRequest;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.UserPhotoVo;
//import com.waterelephant.sxyDrainage.entity.rongYiTui.Zhima;
//import com.waterelephant.sxyDrainage.service.BqsCheckService;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.RongYiTuiService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.rongYiTuiUtil.RongYiTuiConstant;
//import com.waterelephant.sxyDrainage.utils.rongYiTuiUtil.RongYiTuiUtil;
//import com.waterelephant.third.entity.MessageDto;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.third.utils.ThirdUtil;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.DoubleUtil;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//import com.waterelephant.utils.UploadToCssUtils;
//
//import tk.mybatis.mapper.entity.Example;
//
///**
// * Module: RongYiTuiServiceImpl.java
// * 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//@Service
//public class RongYiTuiServiceImpl implements RongYiTuiService {
//	private Logger logger = LoggerFactory.getLogger(RongYiTuiServiceImpl.class);
//
//	private static Integer productId = 7;
//
//	@Autowired
//	private CommonService commonService;
//	@Autowired
//	private BwBorrowerService bwBorrowerService;
//	@Autowired
//	private IBwOrderService bwOrderService;
//	@Autowired
//	private BwProductDictionaryService bwProductDictionaryService;
//	@Autowired
//	private IBwWorkInfoService bwWorkInfoService;
//	@Autowired
//	private BwOperateBasicService bwOperateBasicService;
//	@Autowired
//	private ThirdCommonService thirdCommonService;
//	@Autowired
//	private IBwRepaymentPlanService bwRepaymentPlanService;
//	@Autowired
//	private BwOverdueRecordService bwOverdueRecordService;
//	@Autowired
//	private BwOrderRongService bwOrderRongService;
//	@Autowired
//	private BwOperateVoiceService bwOperateVoiceService;
//	@Autowired
//	private BwZmxyGradeService bwZmxyGradeService;
//	@Autowired
//	private BwIdentityCardServiceImpl bwIdentityCardServiceImpl;
//	@Autowired
//	private IBwPersonInfoService bwPersonInfoService;
//	@Autowired
//	private BwMerchantOrderServiceImpl bwMerchantOrderService;
//	@Autowired
//	private BqsCheckService bqsCheckService;
//	@Autowired
//	private BwBlacklistService bwBlacklistService;
//
//	/**
//	 * 过滤接口
//	 */
//	@Override
//	public RtyResponse checkUser(Long sessionId, CheckUserRequest checkUserRequest) {
//		RtyResponse rtyResponse = new RtyResponse();
//		try {
//			String name = checkUserRequest.getName();
//			String phone = checkUserRequest.getMobile();
//			String idCard = checkUserRequest.getId_card();
//			String errorMsg = "请求失败";
//			DrainageRsp drainageRsp = commonService.checkUser(sessionId, name, phone, idCard);
//			Map<String, String> map = new HashMap<>();
//			map.put("mobile", phone);
//			map.put("name", name);
//			map.put("id_card", idCard);
//			if (null != drainageRsp) {
//				if ("0000".equals(drainageRsp.getCode())) {
//					rtyResponse.setCode(RtyResponse.CODE_SUCCESS);
//					rtyResponse.setMsg("success");
//					rtyResponse.setData(map);
//					return rtyResponse;
//				}
//				errorMsg = drainageRsp.getMessage();
//			}
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg(errorMsg);
//			rtyResponse.setData(map);
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求异常");
//		}
//		return rtyResponse;
//	}
//
//	/*
//	 * 推单接口
//	 */
//	@Override
//	public RtyResponse savePushOrder(long sessionId, RytPushOrderVo rytPushOrderVo) {
//		RtyResponse rtyResponse = new RtyResponse();
//		try {
//			ApplyDetailVo apply_Detail = rytPushOrderVo.getApply_detail();
//			AddInfoVo add_info = rytPushOrderVo.getAdd_info();
//			if (null == apply_Detail || null == add_info) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("请求参数为空");
//				return rtyResponse;
//			}
//			CarrierVo carrier = add_info.getCarrier();
//			if (null == carrier) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("运营商信息为空");
//				return rtyResponse;
//			}
//			List<Calls> calls = carrier.getCalls();
//			if (null == calls || calls.size() == 0) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("通话记录为空");
//				return rtyResponse;
//			}
//			Zhima zhima = add_info.getZhima();
//			if (null == zhima || null == zhima.getZhima_score()) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("芝麻分为空");
//				return rtyResponse;
//			}
//			// DeviceInfo deviceInfo = add_info.getDevice_info();
//			// if (null == deviceInfo || CollectionUtils.isEmpty(deviceInfo.getContacts())) {
//			// rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			// rtyResponse.setMsg("通讯录为空");
//			// return rtyResponse;
//			// }
//			String name = apply_Detail.getUser_name();
//			String phone = apply_Detail.getUser_mobile();
//			String idCard = apply_Detail.getId_card();
//			if (StringUtils.isBlank(name)) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("用户姓名为空");
//				return rtyResponse;
//			}
//			if (StringUtils.isBlank(phone)) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("手机号码为空");
//				return rtyResponse;
//			}
//			if (StringUtils.isBlank(idCard)) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("证件号码为空");
//				return rtyResponse;
//			}
//			String channelIdStr = RongYiTuiConstant.RYT_CHANNELID;
//			if (StringUtils.isBlank(channelIdStr)) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("渠道不存在");
//				return rtyResponse;
//			}
//			boolean flag = thirdCommonService.checkUserAccountProgressOrder(sessionId, idCard);
//			if (flag) {
//				logger.info(sessionId + ">>> 多个订单idCard：" + idCard);
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("存在进行中订单");
//				return rtyResponse;
//			}
//			Integer channelId = Integer.valueOf(channelIdStr);
//			BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, name, idCard, phone, channelId);
//			long borrowerId = borrower.getId();
//			logger.info(sessionId + ">>> 新增/更新借款人ID：" + borrowerId);
//
//			// BwOrder order = new BwOrder();
//			// order.setBorrowerId(borrowerId);
//			// order.setStatusId(8L);
//			// order.setChannel(channelId);
//			// order = bwOrderService.findBwOrderByAttr(order);
//			// if (order == null) {
//			// // 查询是否有进行中的订单
//			// long count = bwOrderService.findProOrder(borrowerId + "");
//			// logger.info(sessionId + ">>> 进行中的订单校验：" + count);
//			// if (count > 0) {
//			// rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			// rtyResponse.setMsg("存在进行中订单");
//			// return rtyResponse;
//			// }
//			// }
//			// 判断是否有草稿状态的订单
//			BwOrder bwOrder = new BwOrder();
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
//				bwOrder.setRepayType(2);
//				bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//				bwOrder.setApplyPayStatus(0);
//				bwOrder.setExpectNumber(4);
//				bwOrder.setProductId(productId);
//				bwOrder.setProductType(2);
//				bwOrderService.addBwOrder(bwOrder);
//			}
//			long orderId = bwOrder.getId();
//			logger.info(sessionId + ">>> 判断是否有草稿状态的订单：ID = " + orderId);
//			/**********************************************/
//			String key = "phone_apply";
//			Map<String, Object> params = new HashMap<>();
//			params.put("mobile", phone);
//			params.put("order_id", orderId);
//			params.put("borrower_id", borrower.getId());
//			String value = JSON.toJSONString(params);
//			RedisUtils.rpush(key, value);
//			/**********************************************/
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
//
//			// 判断是否有工作信息
//			BwWorkInfo bwWorkInfo = bwWorkInfoService.findBwWorkInfoByOrderId(orderId);
//			String workage = RongYiTuiUtil
//					.getWorkYear(null == apply_Detail.getWorking_age() ? 0 : apply_Detail.getWorking_age());
//			String jobType = RongYiTuiUtil.getJob(null == apply_Detail.getJob_type() ? 0 : apply_Detail.getJob_type());
//			if (null == bwWorkInfo) {
//				BwWorkInfo bwWorkInfo_ = new BwWorkInfo();
//				bwWorkInfo_.setOrderId(orderId);
//				bwWorkInfo_.setCallTime("10:00 - 12:00");// 默认值
//				bwWorkInfo_.setIndustry(jobType);
//				bwWorkInfo_.setWorkYears(workage);
//				bwWorkInfo_.setComName(apply_Detail.getCompany_name());
//				bwWorkInfo_.setIncome(apply_Detail.getIncome_monthly() + "分");
//				bwWorkInfo_.setCreateTime(new Date());
//				bwWorkInfo_.setUpdateTime(new Date());
//				bwWorkInfoService.save(bwWorkInfo_, borrowerId);
//			} else {
//				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
//				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//				bwWorkInfo.setWorkYears(workage);
//				bwWorkInfo.setComName(apply_Detail.getCompany_name());
//				bwWorkInfo.setIncome(apply_Detail.getIncome_monthly() + "分");
//				bwWorkInfo.setIndustry(jobType);
//				bwWorkInfoService.update(bwWorkInfo);
//			}
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, channelId); // 插入个人认证记录
//			logger.info(sessionId + ">>> 判断是否有工作信息");
//
//			// 通讯录
//			// List<ContactVo> ContactVoList_ = deviceInfo.getContacts();
//			// if (CollectionUtils.isNotEmpty(ContactVoList_)) {
//			// List<BwContactList> listConS = new ArrayList<BwContactList>();
//			// for (ContactVo contactVo : ContactVoList_) {
//			// if (CommUtils.isNull(contactVo.getName()) || CommUtils.isNull(contactVo.getPhone())) {
//			// continue;
//			// }
//			// BwContactList bwContactList = new BwContactList();
//			// bwContactList.setBorrowerId(borrowerId);
//			// bwContactList.setPhone(contactVo.getPhone());
//			// bwContactList.setName(contactVo.getName());
//			// listConS.add(bwContactList);
//			// }
//			// bwContactListService.addOrUpdateBwContactLists(listConS, borrowerId);
//			// logger.info(sessionId + ">>> 处理通讯录信息 ");
//			// }
//			// 运营商
//			BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
//			logger.info(sessionId + ">>> 处理运营商信息>>>" + bwOperateBasic);
//			if (bwOperateBasic == null) {
//				logger.info(sessionId + ">>> 处理运营商信息>>>1");
//				bwOperateBasic = new BwOperateBasic();
//				bwOperateBasic.setBorrowerId(borrowerId);
//				bwOperateBasic.setUserSource(carrier.getCarrier());
//				bwOperateBasic.setIdCard(carrier.getIdcard());
//				bwOperateBasic.setAddr(carrier.getProvince() + carrier.getCity());
//				bwOperateBasic.setRealName(carrier.getName());
//				bwOperateBasic.setPhoneRemain((carrier.getAvailable_balance()) / 100 + "");
//				bwOperateBasic.setPhone(carrier.getMobile());
//				bwOperateBasic.setRegTime(RongYiTuiUtil.formatToDate(carrier.getOpen_time(), "yyyy-MM-dd HH:mm:ss"));
//				bwOperateBasic.setPhoneStatus((carrier.getState() + 1) + "");
//				bwOperateBasic.setPackageName(carrier.getPackage_name());
//				bwOperateBasic.setStarLevel(carrier.getLevel());
//				bwOperateBasic.setCreateTime(new Date());
//				bwOperateBasic.setUpdateTime(new Date());
//				bwOperateBasicService.save(bwOperateBasic);
//			} else {
//				bwOperateBasic.setUserSource(carrier.getCarrier());
//				bwOperateBasic.setIdCard(carrier.getIdcard());
//				bwOperateBasic.setAddr(carrier.getProvince() + carrier.getCity());
//				bwOperateBasic.setRealName(carrier.getName());
//				bwOperateBasic.setPhoneRemain((carrier.getAvailable_balance()) / 100 + "");
//				bwOperateBasic.setPhone(carrier.getMobile());
//				bwOperateBasic.setRegTime(RongYiTuiUtil.formatToDate(carrier.getOpen_time(), "yyyy-MM-dd HH:mm:ss"));
//				bwOperateBasic.setPhoneStatus((carrier.getState() + 1) + "");
//				bwOperateBasic.setPackageName(carrier.getPackage_name());
//				bwOperateBasic.setStarLevel(carrier.getLevel());
//				bwOperateBasicService.update(bwOperateBasic);
//				logger.info(sessionId + ">>> 处理运营商信息>>>2");
//			}
//			logger.info(sessionId + ">>> 处理运营商信息");
//
//			// 通话记录
//			Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
//			SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//			BwOperateVoice bwOperateVoice = null;
//			for (Calls call : calls) {
//				List<CallsItems> callsItems = call.getItems();
//				if (CollectionUtils.isNotEmpty(callsItems)) {
//					for (CallsItems callsItem : callsItems) {
//						try {
//							bwOperateVoice = new BwOperateVoice();
//							Date jsonCallData = sdf_hms.parse(callsItem.getTime());
//							if (callDate == null || jsonCallData.after(callDate)) { // 通话记录采取最新追加的方式
//								if ((callsItem.getLocation_type().contains("本地"))) {
//									bwOperateVoice.setTrade_type(1);
//								} else {
//									bwOperateVoice.setTrade_type(2);
//								}
//								if (("DIAL").equals(callsItem.getDial_type())) {
//									bwOperateVoice.setCall_type(1);
//								} else if (("DIALED").equals(callsItem.getDial_type())) {
//									bwOperateVoice.setCall_type(2);
//								}
//								bwOperateVoice.setTrade_time(callsItem.getDuration());
//								bwOperateVoice.setCall_time(callsItem.getTime());
//								bwOperateVoice.setTrade_addr(callsItem.getLocation());
//								bwOperateVoice.setReceive_phone(callsItem.getPeer_number());
//								bwOperateVoice.setUpdateTime(new Date());
//								bwOperateVoice.setBorrower_id(borrowerId);
//								bwOperateVoiceService.save(bwOperateVoice);
//							}
//						} catch (Exception e) {
//							logger.error(sessionId + ">>> 插入单条通话记录异常，忽略该条记录" + e.getMessage());
//						}
//					}
//				}
//			}
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 1, channelId);// 插入运营商认证记录
//			logger.info(sessionId + ">>> 处理通话记录信息 ");
//
//			// 芝麻信用
//			Integer sesameScore_ = zhima.getZhima_score();
//			BwZmxyGrade bwZmxyGrade = bwZmxyGradeService.findZmxyGradeByBorrowerId(borrowerId);
//			if (bwZmxyGrade == null) {
//				bwZmxyGrade = new BwZmxyGrade();
//				bwZmxyGrade.setBorrowerId(borrowerId);
//				bwZmxyGrade.setZmScore(sesameScore_);
//				bwZmxyGrade.setCreateTime(Calendar.getInstance().getTime());
//				bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
//				bwZmxyGradeService.saveBwZmxyGrade(bwZmxyGrade);
//			} else {
//				bwZmxyGrade.setZmScore(sesameScore_);
//				bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
//				bwZmxyGradeService.updateBwZmxyGrade(bwZmxyGrade);
//			}
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 4, channelId);// 插入芝麻认证记录
//			logger.info(sessionId + ">>> 处理芝麻信用信息 ");
//
//			// 认证图片
//			UserPhotoVo userPhotoVo = add_info.getUser_photo();
//			if (null != userPhotoVo) {
//				String frontFile = userPhotoVo.getFront();
//				String backFile = userPhotoVo.getBack();
//				String natureFile = userPhotoVo.getUser();
//				if (StringUtils.isNotBlank(frontFile)) {
//					String frontImage = UploadToCssUtils.urlUpload(frontFile, orderId + "_01"); // 身份证正面照
//					logger.info(sessionId + ">>> 身份证正面 " + frontImage);
//					boolean result = thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, orderId,
//							borrowerId, 0); // 保存身份证正面照
//					logger.info(sessionId + ">>> 身份证正面保存 " + result);
//				}
//				if (StringUtils.isNotBlank(backFile)) {
//					String backImage = UploadToCssUtils.urlUpload(backFile, orderId + "_02"); // 身份证反面照
//					logger.info(sessionId + ">>> 身份证反面 " + backImage);
//					boolean result = thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, orderId,
//							borrowerId, 0); // 保存身份证反面照
//					logger.info(sessionId + ">>> 身份证反面保存 " + result);
//				}
//				if (StringUtils.isNotBlank(natureFile)) {
//					String handerImage = UploadToCssUtils.urlUpload(natureFile, orderId + "_03"); // 手持照
//					logger.info(sessionId + ">>> 手持照/人脸 " + handerImage);
//					boolean result = thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, orderId,
//							borrowerId, 0); // 保存手持照
//					logger.info(sessionId + ">>> 手持照/人脸保存 " + result);
//				}
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
//				bwIdentityCard.setCreateTime(new Date());
//				bwIdentityCard.setIdCardNumber(idCard);
//				bwIdentityCard.setName(name);
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCardServiceImpl.saveBwIdentityCard(bwIdentityCard);
//			} else {
//				bwIdentityCard.setIdCardNumber(idCard);
//				bwIdentityCard.setName(name);
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCardServiceImpl.updateBwIdentityCard(bwIdentityCard);
//			}
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, channelId);// 插入身份认证记录
//			logger.info(sessionId + ">>> 处理身份证信息");
//
//			// 亲属联系人
//			BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//			Integer CarStatus = null;
//			if (null != apply_Detail.getAuto_type()) {
//				CarStatus = (apply_Detail.getAuto_type() == 1) ? 0 : 1;
//			}
//			if (bwPersonInfo == null) {
//				bwPersonInfo = new BwPersonInfo();
//				bwPersonInfo.setAddress(apply_Detail.getAddress());
//				bwPersonInfo.setOrderId(orderId);
//				bwPersonInfo.setAddress(apply_Detail.getAddress());
//				bwPersonInfo.setCarStatus(CarStatus);
//				bwPersonInfo.setCityName(apply_Detail.getCity());
//				bwPersonInfo.setEmail(apply_Detail.getEmail());
//				bwPersonInfo.setRelationName(apply_Detail.getUrgent_person1_name());
//				bwPersonInfo.setRelationPhone(apply_Detail.getUrgent_person1_phone());
//				bwPersonInfo.setUnrelationName(apply_Detail.getUrgent_person2_name());
//				bwPersonInfo.setUnrelationPhone(apply_Detail.getUrgent_person2_phone());
//				bwPersonInfo.setCreateTime(new Date());
//				bwPersonInfo.setUpdateTime(new Date());
//				bwPersonInfo.setColleagueName(apply_Detail.getColleague_name());
//				bwPersonInfo.setColleaguePhone(apply_Detail.getColleague_phone());
//				bwPersonInfo.setFriend1Name(apply_Detail.getFriend1_name());
//				bwPersonInfo.setFriend1Phone(apply_Detail.getFriend1_phone());
//				bwPersonInfo.setFriend2Name(apply_Detail.getFriend2_name());
//				bwPersonInfo.setFriend2Phone(apply_Detail.getFriend2_phone());
//				bwPersonInfo.setQqchat(apply_Detail.getQq());
//				bwPersonInfo.setWechat(apply_Detail.getWeixin());
//				bwPersonInfoService.add(bwPersonInfo);
//			} else {
//				bwPersonInfo.setAddress(apply_Detail.getAddress());
//				bwPersonInfo.setCarStatus(CarStatus);
//				bwPersonInfo.setCityName(apply_Detail.getCity());
//				bwPersonInfo.setEmail(apply_Detail.getEmail());
//				bwPersonInfo.setRelationName(apply_Detail.getUrgent_person1_name());
//				bwPersonInfo.setRelationPhone(apply_Detail.getUrgent_person1_phone());
//				bwPersonInfo.setUnrelationName(apply_Detail.getUrgent_person2_name());
//				bwPersonInfo.setUnrelationPhone(apply_Detail.getUrgent_person2_phone());
//				bwPersonInfo.setUpdateTime(new Date());
//				bwPersonInfo.setColleagueName(apply_Detail.getColleague_name());
//				bwPersonInfo.setColleaguePhone(apply_Detail.getColleague_phone());
//				bwPersonInfo.setFriend1Name(apply_Detail.getFriend1_name());
//				bwPersonInfo.setFriend1Phone(apply_Detail.getFriend1_phone());
//				bwPersonInfo.setFriend2Name(apply_Detail.getFriend2_name());
//				bwPersonInfo.setFriend2Phone(apply_Detail.getFriend2_phone());
//				bwPersonInfo.setQqchat(apply_Detail.getQq());
//				bwPersonInfo.setWechat(apply_Detail.getWeixin());
//				bwPersonInfoService.update(bwPersonInfo);
//			}
//			logger.info(sessionId + ">>> 处理亲属联系人信息");
//			rtyResponse.setCode(RtyResponse.CODE_SUCCESS);
//			rtyResponse.setMsg("success");
//			RedisUtils.lpush("tripartite:approve:" + channelId, phone);
//		} catch (Exception e) {
//			logger.error("请求异常", e);
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求异常");
//		}
//		return rtyResponse;
//	}
//
//	/**
//	 * 绑卡接口
//	 */
//	@Override
//	public RtyResponse savesBindCard(long sessionId, BindCardRequest bindCardRequest) {
//		RtyResponse rtyResponse = new RtyResponse();
//		try {
//			String errorMsg = "绑卡失败";
//			String name = bindCardRequest.getUser_name();
//			String regPhone = bindCardRequest.getUser_mobile();
//			String idCard = bindCardRequest.getId_card();
//			String bankCard = bindCardRequest.getCard_no();
//			String thirdNo = bindCardRequest.getOrder_no();
//			DrainageRsp drainageRsp = commonService.saveBindCard(sessionId, idCard, bankCard, name, regPhone, thirdNo);
//			if (null != drainageRsp) {
//				if ("0000".equals(drainageRsp.getCode())) {
//					rtyResponse.setCode(RtyResponse.CODE_SUCCESS);
//					rtyResponse.setMsg("success");
//					return rtyResponse;
//				}
//				errorMsg = drainageRsp.getMessage();
//			}
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg(errorMsg);
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求异常");
//		}
//		return rtyResponse;
//	}
//
//	/**
//	 * 获取订单状态接口
//	 */
//	@Override
//	public RtyResponse getOrderStatus(long sessionId, RytRequest rytRequest) {
//		RtyResponse rtyResponse = new RtyResponse();
//		try {
//			String thirdOrderNo = rytRequest.getOrder_no();
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("订单编号为空");
//				return rtyResponse;
//			}
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("订单不存在");
//				return rtyResponse;
//			}
//			Map<String, Object> map = new HashMap<>();
//			map.put("order_no", thirdOrderNo);// 订单编号
//			int statusId = bwOrder.getStatusId().intValue();
//			int state = RongYiTuiUtil.toRytState(statusId);
//			map.put("state", state);
//			map.put("updated_at", RongYiTuiUtil.formatToStr(bwOrder.getUpdateTime(), "yyyy-MM-dd hh:mm:ss"));
//			if (statusId >= 4 && 7 != statusId && 8 != statusId && 13 != statusId) {
//				BwProductDictionary bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(7);
//				Double interestRate = bwProductDictionary.getInterestRate();
//				double loan_amount = bwOrder.getBorrowAmount() * 100;
//				Double num1 = DoubleUtil.round(loan_amount / 1 * interestRate, 0);
//				Double num2 = DoubleUtil.round(loan_amount / 2 * interestRate, 0);
//				Double num3 = DoubleUtil.round(loan_amount / 3 * interestRate, 0);
//				Double num4 = DoubleUtil.round(loan_amount / 4 * interestRate, 0);
//				map.put("amount", bwOrder.getBorrowAmount() * 100);
//				map.put("repayment_type", 2);// 还款方式 1 等额本息 2 等额本金
//				map.put("service_fee", 0);// 砍头息；单位分
//				map.put("receive_amount", bwOrder.getBorrowAmount() * 100);// 总到帐
//				map.put("month_fee_rate", 0);// 月管理费率
//				map.put("month_interest_rate", 0);// 月利率
//				map.put("pay_amount", loan_amount + num1 + num2 + num3 + num4);// 总还款
//				map.put("pay_extra_amount", 0);// 还款额外一次性费用
//				Map<String, Double> map_ = new HashMap<>();
//				map_.put("1", loan_amount / 4 + num1);
//				map_.put("2", loan_amount / 4 + num2);
//				map_.put("3", loan_amount / 4 + num3);
//				map_.put("4", loan_amount / 4 + num4);
//				map.put("period_amount_option", JSON.toJSONString(map_));// 每期应还金额
//				map.put("approved_at", bwOrder.getCreateTime().getTime() / 1000);// 申请时间
//				map.put("expired_at", RongYiTuiUtil.getPreDay(bwOrder.getUpdateTime(), 3).getTime() / 1000);// 过期时间
//				map.put("confirmed_at", bwOrder.getUpdateTime().getTime() / 1000);// 确认时间
//			}
//			rtyResponse.setCode(RtyResponse.CODE_SUCCESS);
//			rtyResponse.setMsg("success");
//			rtyResponse.setData(map);
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求异常");
//		}
//		return rtyResponse;
//	}
//
//	/**
//	 * 贷款确认接口
//	 */
//	@Override
//	public RtyResponse updateConfirmOrder(long sessionId, ConfirmOrderRequest confirmOrderRequest) {
//		RtyResponse rtyResponse = new RtyResponse();
//		try {
//			String mobile = confirmOrderRequest.getMobile();
//			Double loan_amount = confirmOrderRequest.getLoan_amount();
//			if (StringUtils.isBlank(mobile)) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("手机号码为空");
//				return rtyResponse;
//			}
//			if (null == loan_amount) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("借款金额为空");
//				return rtyResponse;
//			}
//			String thirdOrderNo = confirmOrderRequest.getOrder_no();
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("订单编号为空");
//				return rtyResponse;
//			}
//			String channelIdStr = RongYiTuiConstant.RYT_CHANNELID;
//			if (StringUtils.isBlank(channelIdStr)) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("渠道不存在");
//				return rtyResponse;
//			}
//			Integer channelId = Integer.valueOf(channelIdStr);
//			BwOrder bwOrder = bwOrderService.findBwOrderByPhoneAndChannel(mobile, channelId);
//			if (null == bwOrder) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("订单不存在");
//				return rtyResponse;
//			}
//			// 判断是否有融360订单
//			BwOrderRong bwOrderRong = bwOrderRongService.findBwOrderRongByOrderIdAndChannel(bwOrder.getId(),
//					Long.valueOf(channelId));
//			if (bwOrderRong == null) {
//				bwOrderRong = new BwOrderRong();
//				bwOrderRong.setOrderId(bwOrder.getId());
//				bwOrderRong.setThirdOrderNo(thirdOrderNo);
//				bwOrderRong.setChannelId(Long.valueOf(channelId));
//				bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//				bwOrderRongService.save(bwOrderRong);
//			} else {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("订单已存在");
//				return rtyResponse;
//			}
//			logger.info(sessionId + ">>> 判断是否有融360订单");
//
//			BwBorrower bwBorrower = bwBorrowerService.selectByPrimaryKey(bwOrder.getBorrowerId());
//			if (null == bwBorrower) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("借款人不存在");
//				return rtyResponse;
//			}
//
//			BwProductDictionary bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(7);
//			Double interestRate = bwProductDictionary.getInterestRate();
//			Double num1 = DoubleUtil.round(loan_amount / 1 * interestRate, 0);
//			Double num2 = DoubleUtil.round(loan_amount / 2 * interestRate, 0);
//			Double num3 = DoubleUtil.round(loan_amount / 3 * interestRate, 0);
//			Double num4 = DoubleUtil.round(loan_amount / 4 * interestRate, 0);
//			Map<String, Object> map = new HashMap<>();
//			map.put("order_no", thirdOrderNo);
//			map.put("mobile", mobile);
//			map.put("term_unit", 1);
//			map.put("loan_amount", loan_amount);
//			map.put("loan_term", 28);
//			map.put("repayment_type", 2);// 还款方式 1 等额本息 2 等额本金
//			map.put("service_fee", 0);// 砍头息；单位分
//			map.put("receive_amount", loan_amount);// 总到帐
//			map.put("month_fee_rate", 0);// 月管理费率
//			map.put("month_interest_rate", 0);// 月利率
//			map.put("pay_amount", loan_amount + num1 + num2 + num3 + num4);// 总还款
//			map.put("pay_extra_amount", 0);// 还款额外一次性费用
//			Map<String, Double> map_ = new HashMap<>();
//			map_.put("1", loan_amount / 4 + num1);
//			map_.put("2", loan_amount / 4 + num2);
//			map_.put("3", loan_amount / 4 + num3);
//			map_.put("4", loan_amount / 4 + num4);
//			map.put("period_amount", JSON.toJSONString(map_));// 每期应还金额
//			map.put("approved_at", bwOrder.getUpdateTime().getTime() / 1000);// 申请时间
//			map.put("expired_at", RongYiTuiUtil.getPreDay(bwOrder.getUpdateTime(), 3).getTime() / 1000);// 过期时间
//			// 放入redis中做审核处理
//			bwOrder.setStatusId(2L);
//			bwOrder.setExpectMoney(loan_amount / 100D);
//			bwOrder.setUpdateTime(new Date());
//			bwOrder.setSubmitTime(new Date());
//			bwOrderService.updateBwOrder(bwOrder);
//
//			try {
//				String res = bqsCheckService.doBqsCheck(sessionId, bwOrder.getId() + "");
//				logger.info(
//						sessionId + "白骑士校验>>orderId" + bwOrder.getId() + ">>>res" + ("0".equals(res) ? "成功" : "失败"));
//			} catch (Exception e) {
//				logger.error(sessionId + "调用白骑士校验异常" + e);
//			}
//
//			logger.info(sessionId + ">>> 修改工单状态为" + 2L);
//			HashMap<String, String> hm = new HashMap<>();
//			hm.put("channelId", channelId + "");
//			hm.put("orderId", String.valueOf(bwOrder.getId()));
//			hm.put("orderStatus", "2");
//			hm.put("result", "");
//			String hmData = JSON.toJSONString(hm);
//			RedisUtils.rpush("tripartite:orderStatusNotify:" + channelId, hmData);
//			SystemAuditDto systemAuditDto = new SystemAuditDto();
//			systemAuditDto.setIncludeAddressBook(0);
//			systemAuditDto.setOrderId(bwOrder.getId());
//			systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
//			systemAuditDto.setName(bwBorrower.getName());
//			systemAuditDto.setPhone(bwBorrower.getPhone());
//			systemAuditDto.setIdCard(bwBorrower.getIdCard());
//			systemAuditDto.setChannel(channelId);
//			systemAuditDto.setThirdOrderId(thirdOrderNo);
//			systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//			RedisUtils.hset(SystemConstant.AUDIT_KEY, bwOrder.getId() + "", JsonUtils.toJson(systemAuditDto));
//			logger.info(sessionId + ">>> 并放入redis");
//			rtyResponse.setCode(RtyResponse.CODE_SUCCESS);
//			rtyResponse.setMsg("success");
//			rtyResponse.setData(map);
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求异常");
//		}
//		return rtyResponse;
//	}
//
//	// /**
//	// * 签约接口 (提现接口)
//	// */
//	// @Override
//	// public RtyResponse updateSignContract(long sessionId, RytRequest rytRequest) {
//	// RtyResponse rtyResponse = new RtyResponse();
//	// try {
//	// String errorMsg = "请求失败";
//	// String thirdOrderNo = rytRequest.getOrder_no();
//	// if (StringUtils.isBlank(thirdOrderNo)) {
//	// rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//	// rtyResponse.setMsg("订单编号为空");
//	// return rtyResponse;
//	// }
//	// DrainageRsp drainageRsp = commonService.updateSignContract(sessionId, thirdOrderNo);
//	// if (null != drainageRsp) {
//	// if ("0000".equals(drainageRsp.getCode())) {
//	// rtyResponse.setCode(RtyResponse.CODE_SUCCESS);
//	// rtyResponse.setMsg("success");
//	// return rtyResponse;
//	// }
//	// errorMsg = drainageRsp.getMessage();
//	// }
//	// rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//	// rtyResponse.setMsg(errorMsg);
//	// } catch (Exception e) {
//	// logger.error("请求异常" + e.getMessage());
//	// rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//	// rtyResponse.setMsg("请求异常");
//	// }
//	// return rtyResponse;
//	// }
//
//	/**
//	 * 获取还款计划接口
//	 */
//	@Override
//	public RtyResponse getRepayPlan(long sessionId, RytRequest rytRequest) {
//		RtyResponse rtyResponse = new RtyResponse();
//		try {
//			String errorMsg = "请求失败";
//			String thirdOrderNo = rytRequest.getOrder_no();
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("订单编号为空");
//				return rtyResponse;
//			}
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(thirdOrderNo);
//			if (null == bwOrder) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("订单不存在");
//				return rtyResponse;
//			}
//			List<BwRepaymentPlan> list = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder.getId());
//			if (CollectionUtils.isEmpty(list)) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("还款计划不存在");
//				return rtyResponse;
//			}
//			List<Map<String, Object>> planList = new ArrayList<>();
//			Map<String, Object> map = null;
//			for (BwRepaymentPlan bwRepaymentPlan : list) {
//				map = new HashMap<>();
//				map.put("period_no", bwRepaymentPlan.getNumber());// 期数
//				map.put("total_amount", bwRepaymentPlan.getRealityRepayMoney() * 100);// 总还款金额
//				map.put("principal_amount", bwRepaymentPlan.getRepayCorpusMoney() * 100);// 本金金额
//				map.put("interest_amount", bwRepaymentPlan.getRepayAccrualMoney() * 100);// 利息金额
//				map.put("manage_fee", 0);// 管理费
//				map.put("other_fee", 0);// 其他费用
//
//				BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//				bwOverdueRecord.setRepayId(bwRepaymentPlan.getId());
//				bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//				if (null != bwOverdueRecord) {
//					map.put("overdue_fee", bwOverdueRecord.getAdvance() * 100);// 逾期费用
//				} else {
//					map.put("overdue_fee", 0);// 逾期费用
//				}
//				map.put("pay_type", 5);//
//				map.put("state", bwRepaymentPlan.getRepayStatus());// 账单状态 1 未到期 2 已还款 3 逾期
//				map.put("due_time", RongYiTuiUtil.formatToStr(bwRepaymentPlan.getRepayTime(), "yyyy-MM-dd HH:mm:ss"));// 到期时间
//				map.put("can_repay_time", RongYiTuiUtil.formatToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));// 可以还款的日期
//				if ("2".equals(bwRepaymentPlan.getRepayStatus() + "")) {
//					map.put("repaid_at",
//							RongYiTuiUtil.formatToStr(bwRepaymentPlan.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));// 成功还款时间
//				}
//				map.put("remark", "");
//				planList.add(map);
//			}
//			rtyResponse.setCode(RtyResponse.CODE_SUCCESS);
//			rtyResponse.setMsg(errorMsg);
//			rtyResponse.setData(planList);
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求异常");
//		}
//		return rtyResponse;
//	}
//
//	/**
//	 * 主动还款
//	 */
//	@Override
//	public RtyResponse updateRepayment(long sessionId, RytRequest rytRequest) {
//		RtyResponse rtyResponse = new RtyResponse();
//		try {
//			String errorMsg = "操作失败";
//			String thirdOrderNo = rytRequest.getOrder_no();
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("订单编号为空");
//				return rtyResponse;
//			}
//			DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, thirdOrderNo);
//
//			if (null != drainageRsp) {
//				if ("000".equals(drainageRsp.getCode())) {
//					rtyResponse.setCode(RtyResponse.CODE_SUCCESS);
//					rtyResponse.setMsg("success");
//					errorMsg = "success";
//
//				} else {
//					rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//					rtyResponse.setMsg("操作失败");
//					errorMsg = drainageRsp.getMessage();
//				}
//			} else {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("操作失败");
//			}
//			Map<String, String> map = new HashMap<>();
//			map.put("repay_type", "3");// 还款类型 1支付宝还款 2 柜台还款 3 银联还款
//			map.put("remark", errorMsg);// 备注 注:失败时必须返回(如余额不足，卡不支持线上还款)
//			rtyResponse.setData(map);
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求异常");
//		}
//		return rtyResponse;
//
//	}
//
//	/**
//	 * 
//	 * 
//	 * @param sessionId
//	 * @param rytRequest
//	 * @return
//	 */
//	@Override
//	public RtyResponse getApprovalResult(long sessionId, RytRequest rytRequest) {
//		RtyResponse rtyResponse = new RtyResponse();
//		try {
//			String channelIdStr = RongYiTuiConstant.RYT_CHANNELID;
//			if (StringUtils.isBlank(channelIdStr)) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("渠道不存在");
//				return rtyResponse;
//			}
//			Integer channelId = Integer.valueOf(channelIdStr);
//			String mobile = rytRequest.getMobile();
//			if (StringUtils.isBlank(mobile)) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("手机号码为空");
//				return rtyResponse;
//			}
//			BwOrder bwOrder = bwOrderService.findBwOrderByPhoneAndChannel(mobile, channelId);
//			if (null == bwOrder) {
//				rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//				rtyResponse.setMsg("订单不存在");
//				return rtyResponse;
//			}
//			Map<String, Object> map = new HashMap<>();
//			map.put("mobile", mobile);
//			long statusId = bwOrder.getStatusId();
//			if (statusId >= 4 && 7 != statusId && 8 != statusId && 13 != statusId) {
//				map.put("conclusion", 10);// 通过
//				map.put("term_unit", 1);// 1=（按天计息） 2=（按月计息）
//				map.put("term_type", 1);// 1=多个期限（如7天、14天等);0=固定期限；
//				map.put("approval_term", 28);// 1=多个期限（如7天、14天等);0=固定期限；
//				// map.put("loan_term_option", new int[] { 7, 14, 21, 28 });// 1=多个期限（如7天、14天等);0=固定期限；
//				map.put("amount_type", 1);// 1=金额范围（如500-1000元）;0=固定金额；
//				// map.put("approval_amount", bwOrder.getBorrowAmount() * 100);// 即审批通过金额, 单位分；
//				map.put("max_loan_amount", 5000 * 100);// 审批金额最大值
//				map.put("min_loan_amount", 1000 * 100);// 审批金额最小值
//				// map.put("receive_amount", bwOrder.getBorrowAmount() * 100 * 0.29);// 实际打款到银行卡的金额；单位分；
//				// map.put("pay_amount", 1);// 1. 用户的总还款额（包括本金利息管理费手续费等一切费用）；单位为分；
//				// map.put("period_amount", 1);// 1. 每期应还金额单位为分；
//				map.put("approved_at", bwOrder.getUpdateTime().getTime() / 1000);// 审批通过时间时间戳10位数字
//				map.put("expired_at", RongYiTuiUtil.getPreDay(bwOrder.getUpdateTime(), 3).getTime() / 1000);// 审批过期时间时间戳10位数字
//			} else {
//				// BwRejectRecord record = bwRejectRecordService.findBwRejectRecordByBid(bwOrder.getBorrowerId());
//				// if(null == record){
//				map.put("refuse_time", bwOrder.getUpdateTime().getTime() / 1000);// 审批过期时间时间戳10位数字
//				// } else {
//				// map.put("refuse_time", record.getCreateTime().getTime() / 1000);//审批过期时间时间戳10位数字
//				// }
//				map.put("conclusion", "40");
//				map.put("remark", "用户资质不足");//
//			}
//			rtyResponse.setCode(RtyResponse.CODE_SUCCESS);
//			rtyResponse.setMsg("success");
//			rtyResponse.setData(map);
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求异常");
//		}
//		return rtyResponse;
//	}
//
//	/**
//	 * 
//	 */
//	@Override
//	public RtyResponse savesBindCardReady(long sessionId, BindCardRequest bindCardRequest) {
//		RtyResponse rtyResponse = new RtyResponse();
//		try {
//			String errorMsg = "绑卡失败";
//			String name = bindCardRequest.getUser_name();
//			String regPhone = bindCardRequest.getUser_mobile();
//			String idCard = bindCardRequest.getId_card();
//			String bankCard = bindCardRequest.getCard_no();
//			String thirdNo = bindCardRequest.getOrder_no();
//			DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//			drainageBindCardVO.setBankCardNo(bankCard);
//			drainageBindCardVO.setBindType("2");
//			drainageBindCardVO.setChannelId(Integer.valueOf(RongYiTuiConstant.RYT_CHANNELID));
//			drainageBindCardVO.setName(name);
//			drainageBindCardVO.setIdCardNo(idCard);
//			drainageBindCardVO.setRegPhone(regPhone);
//			drainageBindCardVO.setPhone(bindCardRequest.getRegister_mobile());
//			drainageBindCardVO.setThirdOrderNo(thirdNo);
//			drainageBindCardVO.setNotify(true);
//			drainageBindCardVO.setBankCode(bindCardRequest.getBank_code());
//			DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(sessionId, drainageBindCardVO);
//			if (null != drainageRsp) {
//				if ("0000".equals(drainageRsp.getCode())) {
//					rtyResponse.setCode(RtyResponse.CODE_SUCCESS);
//					rtyResponse.setMsg("success");
//					return rtyResponse;
//				}
//				errorMsg = drainageRsp.getMessage();
//			}
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg(errorMsg);
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求异常");
//		}
//		return rtyResponse;
//	}
//
//	/**
//	 * 
//	 */
//	@Override
//	public RtyResponse savesBindCardSure(long sessionId, BindCardRequest bindCardRequest) {
//		RtyResponse rtyResponse = new RtyResponse();
//		try {
//			String errorMsg = "绑卡失败";
//			DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//			drainageBindCardVO.setBindType("2");
//			drainageBindCardVO.setChannelId(Integer.valueOf(RongYiTuiConstant.RYT_CHANNELID));
//			drainageBindCardVO.setVerifyCode(bindCardRequest.getVerifyCode());
//			drainageBindCardVO.setNotify(true);
//			drainageBindCardVO.setThirdOrderNo(bindCardRequest.getOrder_no());
//			DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(sessionId, drainageBindCardVO);
//			if (null != drainageRsp) {
//				if ("0000".equals(drainageRsp.getCode())) {
//					rtyResponse.setCode(RtyResponse.CODE_SUCCESS);
//					rtyResponse.setMsg("success");
//					return rtyResponse;
//				}
//				errorMsg = drainageRsp.getMessage();
//			}
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg(errorMsg);
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			rtyResponse.setCode(RtyResponse.CODE_FAILURE);
//			rtyResponse.setMsg("请求异常");
//		}
//		return rtyResponse;
//	}
//
//	/**
//	 * 
//	 */
//	@Override
//	public RtyResponseNew addCheckUser(long sessionId, String phone, String id_card, String name) {
//		RtyResponseNew rtyResponseNew = new RtyResponseNew();
//		try {
//			logger.info(sessionId + ">融易推 过滤接口(新)：>>>>" + phone);
//			// 是否黑名单
//			Example example = new Example(BwBlacklist.class);
//			example.createCriteria().andEqualTo("sort", 1).andEqualTo("status", 1).andEqualTo("phone",
//					phone.toUpperCase());
//			List<BwBlacklist> desList = bwBlacklistService.findBwBlacklistByExample(example);
//			if (CommUtils.isNull(desList) == false) {
//				rtyResponseNew.setStatus("500");
//				rtyResponseNew.setMsg("黑名单用户");
//				return rtyResponseNew;
//			}
//			Integer channelId = Integer.valueOf(RongYiTuiConstant.RYT_CHANNELID2);
//			// 是否是老用户
//			BwBorrower borrower = bwBorrowerService.oldUserFilter3(phone);
//			if (borrower != null) {
//				rtyResponseNew.setStatus("500");
//				rtyResponseNew.setMsg("存量用户");
//				return rtyResponseNew;
//			}
//			// 创建借款人
//			String password = ThirdUtil.getRandomPwd();
//			borrower = new BwBorrower();
//			borrower.setPhone(phone);
//			borrower.setPassword(CommUtils.getMD5(password.getBytes()));
//			borrower.setAuthStep(1);
//			borrower.setFlag(1);
//			borrower.setState(1);
//			borrower.setChannel(channelId); // 表示该借款人来源
//			borrower.setName(name);
//			borrower.setIdCard(id_card);
//			if (StringUtils.isNotBlank(id_card)) {
//				borrower.setAge(ThirdUtil.getAgeByIdCard(id_card));
//				borrower.setSex(ThirdUtil.getSexByIdCard(id_card));
//			}
//			borrower.setCreateTime(Calendar.getInstance().getTime());
//			borrower.setUpdateTime(Calendar.getInstance().getTime());
//			int count = bwBorrowerService.addBwBorrower(borrower);
//			logger.info(sessionId + ">融易推 过滤接口(新)：>>>>count>" + count);
//
//			// 发送短信
//			try {
//				if (RedisUtils.exists("tripartite:smsFilter:registerPassword:" + channelId) == false) {
//					String message = ThirdUtil.getMsg(password);
//					MessageDto messageDto = new MessageDto();
//					messageDto.setBusinessScenario("2");
//					messageDto.setPhone(phone);
//					messageDto.setMsg(message);
//					messageDto.setType("1");
//					RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
//				}
//			} catch (Exception e) {
//				logger.error(sessionId + "发送短信异常:", e);
//			}
//			if (count > 0) {
//				rtyResponseNew.setStatus("200");
//				rtyResponseNew.setMsg("success");
//				return rtyResponseNew;
//			} else {
//				rtyResponseNew.setStatus("500");
//				rtyResponseNew.setMsg("failure");
//			}
//		} catch (Exception e) {
//			logger.error("请求异常" + e.getMessage());
//			rtyResponseNew.setStatus("500");
//			rtyResponseNew.setMsg("请求异常");
//		}
//		return rtyResponseNew;
//	}
//}
