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
//import java.util.Map;
//
//import com.waterelephant.drainage.entity.common.PushOrderRequest;
//import com.waterelephant.mapper.BwOperateVoiceMapper;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.fenqiguanjia.*;
//import com.waterelephant.sxyDrainage.utils.fenling.FenLingConstant;
//import com.waterelephant.sxyDrainage.utils.interfaceLog.SxyThirdInterfaceLogUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.log4j.Logger;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.waterelephant.drainage.service.DrainageService;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.BwAdjunct;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwContactList;
//import com.waterelephant.entity.BwMerchantOrder;
//import com.waterelephant.entity.BwOperateBasic;
//import com.waterelephant.entity.BwOperateVoice;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderProcessRecord;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwOverdueRecord;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.entity.BwProductDictionary;
//import com.waterelephant.entity.BwRepaymentPlan;
//import com.waterelephant.entity.BwWorkInfo;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwOperateVoiceService;
//import com.waterelephant.service.BwOrderAuthService;
//import com.waterelephant.service.BwOrderProcessRecordService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.BwProductDictionaryService;
//import com.waterelephant.service.BwTripartiteOperaterDataService;
//import com.waterelephant.service.BwZmxyGradeService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.IBwBorrowerService;
//import com.waterelephant.service.IBwContactListService;
//import com.waterelephant.service.IBwMerchantOrderService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.service.IBwRepaymentPlanService;
//import com.waterelephant.service.IBwWorkInfoService;
//import com.waterelephant.service.impl.BwAdjunctServiceImpl;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.service.BqsCheckService;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.FenQiGuanJiaService;
//import com.waterelephant.sxyDrainage.service.FqgjOperatorService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.SxyDrainageConstant;
//import com.waterelephant.sxyDrainage.utils.fenqiguanjia.FenQiGuanJiaConstant;
//import com.waterelephant.sxyDrainage.utils.fenqiguanjia.FenQiGuanJiaUtils;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.DoubleUtil;
//import com.waterelephant.utils.JsonUtils;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//import com.waterelephant.utils.UploadToCssUtils;
//
///**
// * 
// * 
// * Module:
// * 
// * FenQiGuanJiaServiceImpl.java
// * 
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//@Service
//public class FenQiGuanJiaServiceImpl implements FenQiGuanJiaService {
//
//	private Logger logger = Logger.getLogger(FenQiGuanJiaServiceImpl.class);
//
//	private static final String FQGJ_CHANNELID = "channelId";
//
//	private static final String LOAN_TREM = "loan_term";
//
//	private static final String PRODUCT_ID = "productId";
//
//	@Autowired
//	private CommonService commonService;
//
//	@Autowired
//	private BwProductDictionaryService bwProductDictionaryService;
//
//	@Autowired
//	private DrainageService drainageService;
//
//	@Autowired
//	private BwOrderRongService bwOrderRongService;
//
//	@Autowired
//	private ThirdCommonService thirdCommonService;
//
//	@Autowired
//	private IBwOrderService bwOrderService;
//
//	@Autowired
//	private IBwMerchantOrderService bwMerchantOrderService;
//
//	@Autowired
//	private IBwWorkInfoService bwWorkInfoService;
//	@Autowired
//	private IBwRepaymentPlanService bwRepaymentPlanService;
//
//	@Autowired
//	private IBwBankCardService bwBankCardService;
//
//	@Autowired
//	private BwOverdueRecordService bwOverdueRecordService;
//
//	@Autowired
//	private BwOperateBasicService bwOperateBasicService;
//
//	@Autowired
//	private IBwPersonInfoService bwPersonInfoService;
//
//	@Autowired
//	private IBwBorrowerService bwBorrowerService;
//
//	@Autowired
//	private BwOrderProcessRecordService bwOrderProcessRecordService;
//
//	@Autowired
//	private BwAdjunctServiceImpl bwAdjunctService;
//
//	@Autowired
//	private FqgjOperatorService fqgjOperatorService;
//
//	@Autowired
//	private BqsCheckService bqsCheckService;
//
//	@Autowired
//	private BwOperateVoiceService bwOperateVoiceService;
//
//	@Autowired
//	private SqlSessionTemplate sqlSessionTemplate;
//
//	/**
//	 * 3.1 用户过滤接口
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.FenQiGuanJiaService#checkUser(long, java.lang.String,
//	 *      java.lang.String, java.lang.String)
//	 */
//	@Override
//	public FenQiGuanJiaResponse checkUser(long sessionId, String user_name, String user_mobile, String id_card) {
//		logger.info(sessionId + " 开始FenQiGuanJiaServiceImpl.checkUser()方法{user_name=" + user_name + ",user_mobile="
//				+ user_mobile + ",id_card=" + id_card + "}");
//
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		DrainageRsp checkUser = commonService.checkUser(sessionId, user_name, user_mobile, id_card);
//		// 获取公共接口返回的信息判断给三方返回的数据
//		String drainageRspCode = checkUser.getCode();
//		// 返回数据
//		// 验证数据是否为空
//		if (drainageRspCode.equals("1002")) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//			fenQiGuanJiaResponse.setMsg(checkUser.getMessage());
//			logger.info("系统异常,用户" + user_name + ":" + checkUser.getMessage());
//			return fenQiGuanJiaResponse;
//
//		}
//		// 验证是否符合申请要求
//		if (drainageRspCode.equals("2001") || drainageRspCode.equals("2002") || drainageRspCode.equals("2003")
//				|| drainageRspCode.equals("2004")) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_NOTLOAN);
//			fenQiGuanJiaResponse.setMsg(checkUser.getMessage());
//			logger.info(user_name + "不可申请," + checkUser.getMessage());
//			return fenQiGuanJiaResponse;
//		}
//		UserCheckResp userCheckResp = new UserCheckResp();
//		// 判断新老用户
//		boolean oldUserBoolean = drainageService.oldUserFilter2(user_name, user_mobile, id_card);
//
//		if (oldUserBoolean) {
//			userCheckResp.setType(1);
//		} else {
//			userCheckResp.setType(2);
//		}
//		// 设置默认产品
//		BwProductDictionary dictionary = bwProductDictionaryService
//				.findById(Long.valueOf(FenQiGuanJiaConstant.get(PRODUCT_ID)));
//		Integer minAmount = dictionary.getMinAmount();
//
//		Integer maxAmount = dictionary.getMaxAmount();
//		userCheckResp.setIs_reloan(0);
//		userCheckResp.setMax_approval_amount(Float.valueOf(maxAmount));
//		userCheckResp.setMin_approval_amount(Float.valueOf(minAmount));
//		fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//		fenQiGuanJiaResponse.setMsg("该用户允许通过");
//		fenQiGuanJiaResponse.setData(userCheckResp);
//		logger.info("检查用户接口返回数据为：" + JSONObject.toJSONString(fenQiGuanJiaResponse));
//
//		return fenQiGuanJiaResponse;
//	}
//
//	/**
//	 * 3.2 订单基本信息推送接口
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.FenQiGuanJiaService#saveOrder(long,
//	 *      com.waterelephant.sxyDrainage.entity.fenqiguanjia.OrderInfoRequest)
//	 */
//	@Override
//	public FenQiGuanJiaResponse saveOrder(long sessionId, OrderInfoRequest orderInfoRequest) {
//		logger.info(sessionId + " 开始FenQiGuanJiaServiceImpl.saveOrder()方法");
//
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		String order_no=null;
//		try {
//			if (CommUtils.isNull(orderInfoRequest)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg(sessionId + "接收推送用户信息orderInfoRequest为空");
//				return fenQiGuanJiaResponse;
//			}
//			Apply_detail apply_detail = orderInfoRequest.getApply_detail();
//			Add_info add_info = orderInfoRequest.getAdd_info();
//			Order_info order_info = orderInfoRequest.getOrder_info();
//			String channelId = FenQiGuanJiaConstant.get(FQGJ_CHANNELID);
//			// 验证addInfo非空
//			if (CommUtils.isNull(add_info)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg(sessionId + "接收推送用户信息add_info为空");
//				return fenQiGuanJiaResponse;
//			}
//			// 验证mobile非空
//			Mobile mobile = add_info.getMobile();
//			if (CommUtils.isNull(mobile)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg(sessionId + "接收推送用户信息mobile为空");
//				return fenQiGuanJiaResponse;
//			}
//
//			// 验证orderInfoRequest非空
//			if (CommUtils.isNull(order_info)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg(sessionId + "接收推送用户信息order_info为空");
//				return fenQiGuanJiaResponse;
//			}
//			// 验证apply_detail非空
//			if (CommUtils.isNull(apply_detail)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg(sessionId + "接收推送用户信息apply_detail为空");
//				return fenQiGuanJiaResponse;
//			}
//
//            List<Tel> tels = mobile.getTel();
//            if (CommUtils.isNull(tels)) {
//                logger.info("addOperateVoice occured exception:tels is null");
//                fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//                fenQiGuanJiaResponse.setMsg("通话记录为空,请检查数据！");
//                return fenQiGuanJiaResponse;
//            }
//
//			// 手机号
//			String user_mobile = order_info.getUser_mobile();
//			// 姓名
//			String user_name = order_info.getUser_name();
//			// 身份证号
//			String id_card = apply_detail.getId_card();
//			// 分期管家订单号
//			 order_no = order_info.getOrder_no();
//
//			boolean progressOrder = thirdCommonService.checkUserAccountProgressOrder(sessionId, id_card);
//			// 根据身份证号查询进行中的工单
//			if (progressOrder) {
//				logger.info(sessionId + "存在进行中的订单");
//				fenQiGuanJiaResponse.setMsg("存在进行中订单,不做重复处理");
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_DUPLICATECALL);
//				return fenQiGuanJiaResponse;
//			}
//
//			BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, user_name, id_card, user_mobile,
//					Integer.valueOf(channelId));
//			long borrowerId = borrower.getId();
//			logger.info(sessionId + ">>> 新增/更新借款人ID：" + borrowerId);
//
//			// 判断该渠道是否有撤回的订单
//			BwOrder order = new BwOrder();
//			order.setBorrowerId(borrowerId);
//			order.setStatusId(8L);
//			order.setChannel(Integer.valueOf(channelId));
//			order = bwOrderService.findBwOrderByAttr(order);
//
//			if (order == null) {
//				// 查询是否有进行中的订单
//				long count = bwOrderService.findProOrder(borrowerId + "");
//
//				logger.info(sessionId + ">>> 进行中的订单校验：" + count);
//				if (count > 0) {
//					logger.info(sessionId + " 结束FenQiGuanJiaServiceImpl.saveOrder()方法，返回结果：存在进行中的订单，请勿重复推送");
//					fenQiGuanJiaResponse.setMsg("我方订单已存在,不做重复处理");
//					fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_DUPLICATECALL);
//					return fenQiGuanJiaResponse;
//				}
//			}
//			Integer productId = Integer.valueOf(FenQiGuanJiaConstant.get(PRODUCT_ID));
//			// 判断是否有草稿状态的订单
//			BwOrder bwOrder = new BwOrder();
//			bwOrder.setBorrowerId(borrowerId);
//			bwOrder.setStatusId(1L);
//			bwOrder.setProductType(2);
//			bwOrder.setChannel(Integer.valueOf(channelId));
//			bwOrder.setProductId(productId);
//			// 先查询草稿状态的订单
//			List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);
//			bwOrder.setStatusId(8L);
//			// 再查询撤回状态的订单
//			List<BwOrder> boList8 = bwOrderService.findBwOrderListByAttr(bwOrder);
//			// 第一次进件被审批撤回后，再次进件时，更新第一次的订单
//			boList.addAll(boList8);
//			// 临时状态，重复利用status_id=16
//			bwOrder.setStatusId(16L);
//			// 再查询临时的订单
//			List<BwOrder> boList16 = bwOrderService.findBwOrderListByAttr(bwOrder);
//			// 第一次进件为临时状态后，再次进件时，更新订单
//			boList.addAll(boList16);
//
//			if ( boList.size() > 0) {
//				bwOrder = boList.get(boList.size() - 1);
//				bwOrder.setStatusId(1L);
//				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwOrder.setProductType(2);
//				bwOrder.setExpectMoney(order_info.getApplication_amount());
//				bwOrder.setExpectNumber(4);
//				bwOrder.setRepayType(2);
//				bwOrderService.updateBwOrder(bwOrder);
//			} else {
//				bwOrder = new BwOrder();
//				bwOrder.setOrderNo(FenQiGuanJiaUtils.generateOrderNo());
//				bwOrder.setBorrowerId(borrower.getId());
//				bwOrder.setStatusId(1L);
//				bwOrder.setCreateTime(Calendar.getInstance().getTime());
//				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwOrder.setChannel(Integer.valueOf(channelId));
//				bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//				bwOrder.setApplyPayStatus(0);
//				bwOrder.setProductId(productId);
//				bwOrder.setProductType(2);
//				bwOrder.setRepayType(2);
//				bwOrder.setExpectMoney(order_info.getApplication_amount());
//				bwOrder.setExpectNumber(4);
//				bwOrderService.addBwOrder(bwOrder);
//			}
//			long orderId = bwOrder.getId();
//			logger.info(sessionId + ">>> 判断是否有草稿状态的订单：ID = " + orderId);
//
//			//添加征信接口redis验证
//			String  key  =  "phone_apply";
//			Map<String,Object>  params  =  new  HashMap<>();
//			params.put("mobile",  user_mobile);
//			params.put("order_id",  orderId);
//			params.put("borrower_id",  borrowerId);
//			String  value  =  JSON.toJSONString(params);
//			RedisUtils.rpush(key,  value);
//
//			// 判断是否有融360订单
//			BwOrderRong bwOrderRong = new BwOrderRong();
//			bwOrderRong.setOrderId(orderId);
//			bwOrderRong.setChannelId(Long.valueOf(FenQiGuanJiaConstant.get(FQGJ_CHANNELID)));
//			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//			if (bwOrderRong == null) {
//				bwOrderRong = new BwOrderRong();
//				bwOrderRong.setOrderId(orderId);
//				bwOrderRong.setThirdOrderNo(order_no);
//				bwOrderRong.setChannelId(Long.valueOf(channelId));
//				bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//				bwOrderRongService.save(bwOrderRong);
//			} else {
//				if (null == bwOrderRong.getChannelId()) {
//					bwOrderRong.setChannelId(Long.valueOf(channelId));
//				}
//				bwOrderRong.setThirdOrderNo(order_no);
//				bwOrderRongService.update(bwOrderRong);
//			}
//			logger.info(sessionId + ">>> 存入分期管家三方订单:"+bwOrderRong.getThirdOrderNo());
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
//			BwWorkInfo bwWorkInfo = new BwWorkInfo();
//			bwWorkInfo.setOrderId(orderId);
//			bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//			if (!CommUtils.isNull(bwWorkInfo)) {
//				logger.info("已经存在，开始修改工作信息");
//				updateBwWorkInfo(bwOrder.getId(), apply_detail.getWork_period() + "",
//						apply_detail.getIs_op_type() + "");
//			} else {
//				logger.info("不存在，开始新增工作信息");
//				saveBwWorkInfo(bwOrder.getId(), apply_detail.getWork_period() + "", apply_detail.getIs_op_type() + "",
//						borrower.getId());
//			}
//			// 插入个人认证记录
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, Integer.valueOf(channelId));
//			logger.info(sessionId + ">>> 判断是否有工作信息");
//			SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
//
//			//添加通话记录
//			addOperateVoice(mobile, borrowerId,sqlSession);
//
//			// 运营商
//			 logger.info(sessionId+"开始更新运营商......");
//			addOrUpdateOperate(sessionId, bwOrder.getId(), add_info.getMobile(), borrower.getId(), channelId);
//
//			fqgjOperatorService.saveOperator(JSON.toJSONString(add_info), orderId , borrowerId);
//
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//			fenQiGuanJiaResponse.setMsg("接收信息成功！");
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("系统异常，请稍后重试！");
//			logger.info("FenQiGuanJiaServiceImpl.saveOrder()方法出现异常", e);
//		}finally {
//			SxyThirdInterfaceLogUtils.setSxyLog(FenQiGuanJiaConstant.get(FQGJ_CHANNELID), order_no, fenQiGuanJiaResponse.getCode()+"", fenQiGuanJiaResponse.getMsg(), "三方订单号");
//		}
//		logger.info("推送基本信息接口返回数据：" + JSONObject.toJSONString(fenQiGuanJiaResponse));
//		return fenQiGuanJiaResponse;
//	}
//
//	/**
//	 * 3.3 补充信息推送接口
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.FenQiGuanJiaService#saveAddOrder(long,
//	 *      com.waterelephant.sxyDrainage.entity.fenqiguanjia.OrderAddInfoReq)
//	 */
//	@Override
//	public FenQiGuanJiaResponse saveAddOrder(long sessionId, OrderAddInfoReq orderAddInfoReq) {
//		logger.info(sessionId + " 开始FenQiGuanJiaServiceImpl.saveAddOrder()方法");
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		String thirdOrderNo=null;
//		try {
//
//			// 获取第三方订单
//			 thirdOrderNo = orderAddInfoReq.getOrder_no();
//
//			if (StringUtils.isEmpty(thirdOrderNo)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订编号不存在");
//				logger.info(sessionId + "结束FenQiGuanJiaServiceImpl.saveAddOrder方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//			if (bwOrderRong == null) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订单不存在");
//				logger.info(sessionId + "：结束FenQiGuanJiaServiceImpl.saveAddOrder 方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//			// 如果当前订单不存在表示订单基本信息未推送
//			if (CommUtils.isNull(bwOrder)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("我方不存在该订单");
//				logger.info(sessionId + "：结束FenQiGuanJiaServiceImpl.saveAddOrder method："
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			// 处理身份证信息
//			Long orderId = bwOrder.getId();
//			Long borrowerId = bwOrder.getBorrowerId();
//			long count = bwOrderService.findProOrder(borrowerId + "");
//			logger.info(thirdOrderNo + ">>> 进行中的订单校验：" + count);
//			if (count > 0) {
//				logger.info(sessionId + " 结束FenQiGuanJiaServiceImpl.saveAddOrder()方法，返回结果：存在进行中的订单，请勿重复推送");
//				fenQiGuanJiaResponse.setMsg("补充信息接口我方订单已存在,不做重复处理");
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//				return fenQiGuanJiaResponse;
//			}
//			// 身份证正面
//			List<String> frontFiles = orderAddInfoReq.getID_Positive();
//			String frontFile = frontFiles == null ? null : frontFiles.get(frontFiles.size() - 1);
//			// 身份证反面
//			List<String> backFiles = orderAddInfoReq.getID_Negative();
//			String backFile = backFiles == null ? null : backFiles.get(backFiles.size() - 1);
//			// 手持身份证照片
//			List<String> natureFiles = orderAddInfoReq.getPhoto_hand_ID();
//			String natureFile = natureFiles == null ? null : natureFiles.get(natureFiles.size() - 1);
//
//			// 通讯录
//			logger.info("------开始添加通讯录------");
//			Contacts contacts = orderAddInfoReq.getContacts();
//			if(CommUtils.isNull(contacts)){
//				logger.info(sessionId + " 结束FenQiGuanJiaServiceImpl.saveAddOrder()方法，返回结果：通讯录信息为空");
//				fenQiGuanJiaResponse.setMsg("通讯录信息为空");
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//				return fenQiGuanJiaResponse;
//			}
//			//addOrUpdateConcast(contacts, orderId);
//
//			Device_info_all deviceInfoAll = orderAddInfoReq.getDevice_info_all();
//
//			PushOrderRequest pushOrderRequest = new PushOrderRequest();
//			pushOrderRequest.setIdCardFrontImage(frontFile);
//			pushOrderRequest.setIdCardBackImage(backFile);
//			pushOrderRequest.setIdCardHanderImage(natureFile);
//			if(!CommUtils.isNull(deviceInfoAll) ){
//				deviceInfoAll.setInstalled_apps(contacts.getInstalled_apps());
//				//储存设备信息
//				pushOrderRequest.setOperaterData(deviceInfoAll);
//			}
//
//			logger.info(thirdOrderNo+"开始处理身份证信息----");
//
//			// 亲属联系人
//			logger.info(thirdOrderNo+"----开始添加亲属联系人");
//			saveOrUpdatePersonInfo(orderAddInfoReq, orderId);
//
//			// 添加工作信息
//			logger.info(thirdOrderNo+"开始更新公司的名称");
//
//			BwWorkInfo bwi = new BwWorkInfo();
//			bwi.setOrderId(bwOrderRong.getOrderId());
//			bwi = bwWorkInfoService.findBwWorkInfoByAttr(bwi);
//			if(!CommUtils.isNull(bwi)){
//				bwi.setComName(orderAddInfoReq.getCompany_name());
//				bwi.setUpdateTime(new Date());
//				bwWorkInfoService.update(bwi);
//			}
//
//
//
//
//			// 修改工单状态为1L
//			bwOrder.setStatusId(1L);
//			bwOrder.setCreateTime(new Date());
//			bwOrder.setUpdateTime(new Date());
//			bwOrderService.updateBwOrder(bwOrder);
//
//			fqgjOperatorService.savePhotoes(pushOrderRequest,orderId,borrowerId,sessionId,contacts);
//
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//			fenQiGuanJiaResponse.setMsg("请求成功");
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaServiceImpl.saveAddOrder方法出现异常");
//			logger.error("FenQiGuanJiaServiceImpl.saveAddOrder方法出现异常", e);
//		}finally {
//			SxyThirdInterfaceLogUtils.setSxyLog(FenQiGuanJiaConstant.get(FQGJ_CHANNELID), thirdOrderNo, fenQiGuanJiaResponse.getCode()+"", fenQiGuanJiaResponse.getMsg(), "三方订单号");
//		}
//		logger.info("推送补充信息接口返回数据：" + JSONObject.toJSONString(fenQiGuanJiaResponse));
//		return fenQiGuanJiaResponse;
//	}
//
//	/**
//	 * 3.4 绑卡接口(旧接口)
//	 *
//	 * @see com.waterelephant.sxyDrainage.service.FenQiGuanJiaService#saveBindCard(long,
//	 *      com.waterelephant.sxyDrainage.entity.fenqiguanjia.UserBindCardData)
//	 */
//	@Override
//	public FenQiGuanJiaResponse saveBindCard(long sessionId, UserBindCardData userBindCardData) {
//		logger.info(sessionId + " 开始FenQiGuanJiaServiceImpl.saveBindCard()方法");
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//
//		try {
//			// 三方订单
//			String thirdOrderNo = userBindCardData.getOrder_no();
//			logger.info("第三方订单为：" + thirdOrderNo);
//			// 身份证号码
//			String id_number = userBindCardData.getId_number();
//			// 用户姓名
//			String user_name = userBindCardData.getUser_name();
//			// 银行预留手机号
//			String user_mobile = userBindCardData.getUser_mobile();
//			// 银行卡号码
//			String bank_card = userBindCardData.getBank_card();
//			if (StringUtils.isEmpty(thirdOrderNo)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订编号不存在");
//				logger.info(sessionId + "结束FenQiGuanJiaServiceImpl.saveBindCard方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//			if (bwOrderRong == null) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订单不存在");
//				logger.info(sessionId + "：结束FenQiGuanJiaServiceImpl.saveBindCard 方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//			// 如果当前订单不存在表示订单基本信息未推送
//			if (CommUtils.isNull(bwOrder)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("我方不存在该订单");
//				logger.info(sessionId + "：结束FenQiGuanJiaServiceImpl.saveBindCard method："
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			DrainageRsp drainageRsp = commonService.saveBindCard(sessionId, id_number, bank_card, user_name,
//					user_mobile, thirdOrderNo);
//			logger.info("绑卡返回的信息：code" + drainageRsp.getCode() + " msg:" + drainageRsp.getMessage());
//
//			if (drainageRsp.getCode().equals("0000")) {
//				BwBorrower borrower = new BwBorrower();
//				borrower.setId(bwOrder.getBorrowerId());
//				borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//				borrower.setAuthStep(4);
//				bwBorrowerService.updateBwBorrower(borrower);
//
//				long count = bwOrderService.findProOrder(borrower.getId() + "");
//				logger.info(sessionId + ">>> 进行中的订单校验：" + count);
//				if (count > 0) {
//					logger.info(sessionId + " 结束FenQiGuanJiaServiceImpl.saveBindCard()方法，返回结果：存在进行中的订单，请勿重复推送");
//					fenQiGuanJiaResponse.setMsg("绑卡接口我方订单已存在,不做重复处理");
//					fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//					return fenQiGuanJiaResponse;
//				}
//
//				try {
//					String doBqsCheck = bqsCheckService.doBqsCheck(sessionId, bwOrder.getId() + "");
//
//					logger.info(thirdOrderNo + "白骑士返回结果：" + doBqsCheck);
//
//				} catch (Exception e) {
//					logger.error(thirdOrderNo + "白骑士验证异常", e);
//
//				}
//
//				// 修改订单提交时间
//				bwOrder.setStatusId(2L);
//				bwOrder.setUpdateTime(new Date());
//				bwOrder.setSubmitTime(new Date());
//				bwOrderService.update(bwOrder);
//
//				// 第三方通知-------------code0093
//				logger.info("初审===" + bwOrder.getId());
//				HashMap<String, String> hm = new HashMap<>();
//				hm.put("channelId", CommUtils.toString(bwOrder.getChannel()));
//				hm.put("orderId", String.valueOf(bwOrder.getId()));
//				hm.put("orderStatus", "2");
//				hm.put("result", "审核");
//				String hmData = JSON.toJSONString(hm);
//				RedisUtils.rpush("tripartite:orderStatusNotify:" + bwOrder.getChannel(), hmData);
//
//				// 修改订单提交时间
//				BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//				bwOrderProcessRecord.setOrderId(bwOrder.getId());
//				bwOrderProcessRecord.setSubmitTime(new Date());
//				bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//
//				SystemAuditDto systemAuditDto = new SystemAuditDto();
//				systemAuditDto.setIncludeAddressBook(0);
//				systemAuditDto.setOrderId(bwOrder.getId());
//				systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
//				systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//				systemAuditDto.setName(borrower.getName());
//				systemAuditDto.setPhone(borrower.getPhone());
//				systemAuditDto.setIdCard(borrower.getIdCard());
//				systemAuditDto.setChannel(bwOrderRong.getChannelId().intValue());
//				systemAuditDto.setThirdOrderId(bwOrderRong.getThirdOrderNo());
//
//				Long reLong = RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
//						JsonUtils.toJson(systemAuditDto));
//
//				logger.info(sessionId + ">>> 修改订单状态，并放入" + reLong + "条redis");
//
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//				fenQiGuanJiaResponse.setMsg(drainageRsp.getMessage());
//
//			} else {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//				fenQiGuanJiaResponse.setMsg(drainageRsp.getMessage());
//			}
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaServiceImpl.saveBindCard方法出现异常");
//			logger.error("FenQiGuanJiaServiceImpl.saveBindCard方法出现异常", e);
//		}
//		logger.info("绑卡接口返回数据：" + JSONObject.toJSONString(fenQiGuanJiaResponse));
//
//		return fenQiGuanJiaResponse;
//	}
//
//
//
//	/**
//	 * 1.4 预绑卡接口
//	 * @param sessionId
//	 * @param userBindCardData
//	 * @return
//	 */
//	@Override
//	public FenQiGuanJiaResponse saveNewBindCard(long sessionId, UserBindCardData userBindCardData) {
//
//		logger.info(sessionId + " 开始FenQiGuanJiaServiceImpl.saveNewBindCard()方法"+JSON.toJSONString(userBindCardData));
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		String orderNo=null;
//
//		try {
//			String bankCard = userBindCardData.getBank_card();
//			String idNumber = userBindCardData.getId_number();
//			String openBank = userBindCardData.getOpen_bank();
//			orderNo = userBindCardData.getOrder_no();
//			String userMobile = userBindCardData.getUser_mobile();
//			String userName = userBindCardData.getUser_name();
//			String bankCode = FenQiGuanJiaUtils.convertFqgjBankCodeToFuiou(openBank);
//
//			DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//			drainageBindCardVO.setBindType("2");
//			drainageBindCardVO.setBankCardNo(bankCard);
//			drainageBindCardVO.setIdCardNo(idNumber);
//			drainageBindCardVO.setThirdOrderNo(orderNo);
//			drainageBindCardVO.setName(userName);
//			drainageBindCardVO.setRegPhone(userMobile);
//			drainageBindCardVO.setBankCode(bankCode);
//			drainageBindCardVO.setChannelId(Integer.valueOf(FenQiGuanJiaConstant.get(FQGJ_CHANNELID)));
//			logger.info("绑卡之前数据" + JSON.toJSONString(drainageBindCardVO));
//			DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(sessionId, drainageBindCardVO);
//
//			if(drainageRsp != null){
//				if("0000".equals(drainageRsp.getCode())){
//					fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//					fenQiGuanJiaResponse.setMsg("预绑卡成功");
//				}else{
//					fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//					fenQiGuanJiaResponse.setMsg(drainageRsp.getMessage());
//				}
//			}else{
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//				fenQiGuanJiaResponse.setMsg("预绑卡接口返回参数为空");
//
//			}
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("预绑卡接口系统异常");
//			logger.error("FenQiGuanJiaServiceImpl.saveNewBindCard()方法出现异常",e);
//		}finally {
//            SxyThirdInterfaceLogUtils.setSxyLog(FenQiGuanJiaConstant.get(FQGJ_CHANNELID), orderNo, fenQiGuanJiaResponse.getCode()+"", fenQiGuanJiaResponse.getMsg(), "三方订单号");
//        }
//		logger.info("结束FenQiGuanJiaServiceImpl.saveNewBindCard()方法，返回参数"+JSON.toJSONString(fenQiGuanJiaResponse));
//		return fenQiGuanJiaResponse;
//	}
//
//
//	/**
//	 * 3.4 验证码（新绑卡接口）
//	 * @param sessionId
//	 * @param userBindCardData
//	 * @return
//	 */
//	@Override
//	public FenQiGuanJiaResponse saveBindCardWithCode(long sessionId, UserBindCardData userBindCardData) {
//		logger.info(sessionId + " 开始FenQiGuanJiaServiceImpl.saveBindCardWithCode()方法"+JSON.toJSONString(userBindCardData));
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		String orderNo=null;
//
//		try {
//			orderNo = userBindCardData.getOrder_no();
//
//			if (StringUtils.isEmpty(orderNo)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订编号不存在");
//				logger.info(sessionId + "结束FenQiGuanJiaServiceImpl.saveBindCard方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
//			if (bwOrderRong == null) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订单不存在");
//				logger.info(sessionId + "：结束FenQiGuanJiaServiceImpl.saveBindCard 方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//			// 如果当前订单不存在表示订单基本信息未推送
//			if (CommUtils.isNull(bwOrder)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("我方不存在该订单");
//				logger.info(sessionId + "：结束FenQiGuanJiaServiceImpl.saveBindCard method："
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//
//			String verifyCode = userBindCardData.getVerify_code();
//
//			DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//			drainageBindCardVO.setBindType("2");
//			drainageBindCardVO.setThirdOrderNo(orderNo);
//			drainageBindCardVO.setChannelId(Integer.valueOf(FenQiGuanJiaConstant.get(FQGJ_CHANNELID)));
//			drainageBindCardVO.setVerifyCode(verifyCode);
//			logger.info("分期管家绑卡之前数据" + JSON.toJSONString(drainageBindCardVO));
//			DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(sessionId, drainageBindCardVO);
//			logger.info("分期管家绑卡返回参数"+JSON.toJSONString(drainageRsp));
//
//
//			if(drainageRsp != null){
//				if("0000".equals(drainageRsp.getCode())){
//					BwBorrower borrower = new BwBorrower();
//					borrower.setId(bwOrder.getBorrowerId());
//					borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//					borrower.setAuthStep(4);
//					bwBorrowerService.updateBwBorrower(borrower);
//
//					if(bwOrder.getStatusId() != 1){
//						logger.info(orderNo + " 结束FenQiGuanJiaServiceImpl.saveBindCard()方法，返回结果：请检查订单状态");
//						fenQiGuanJiaResponse.setMsg("请检查订单状态");
//						fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//						return fenQiGuanJiaResponse;
//					}
//
//					try {
//						String doBqsCheck = bqsCheckService.doBqsCheck(sessionId, bwOrder.getId() + "");
//
//						logger.info(orderNo + "白骑士返回结果：" + doBqsCheck);
//
//					} catch (Exception e) {
//						logger.error(orderNo + "白骑士验证异常", e);
//
//					}
//
//					// 修改订单提交时间
//					bwOrder.setStatusId(2L);
//					bwOrder.setUpdateTime(new Date());
//					bwOrder.setSubmitTime(new Date());
//					bwOrderService.update(bwOrder);
//
//					// 第三方通知-------------code0093
//					logger.info("初审===" + bwOrder.getId());
//					HashMap<String, String> hm = new HashMap<>();
//					hm.put("channelId", CommUtils.toString(bwOrder.getChannel()));
//					hm.put("orderId", String.valueOf(bwOrder.getId()));
//					hm.put("orderStatus", "2");
//					hm.put("result", "审核");
//					String hmData = JSON.toJSONString(hm);
//					RedisUtils.rpush("tripartite:orderStatusNotify:" + bwOrder.getChannel(), hmData);
//
//					// 修改订单提交时间
//					BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//					bwOrderProcessRecord.setOrderId(bwOrder.getId());
//					bwOrderProcessRecord.setSubmitTime(new Date());
//					bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//
//					SystemAuditDto systemAuditDto = new SystemAuditDto();
//					systemAuditDto.setIncludeAddressBook(0);
//					systemAuditDto.setOrderId(bwOrder.getId());
//					systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
//					systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//					systemAuditDto.setName(borrower.getName());
//					systemAuditDto.setPhone(borrower.getPhone());
//					systemAuditDto.setIdCard(borrower.getIdCard());
//					systemAuditDto.setChannel(bwOrderRong.getChannelId().intValue());
//					systemAuditDto.setThirdOrderId(bwOrderRong.getThirdOrderNo());
//
//					Long reLong = RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
//							JsonUtils.toJson(systemAuditDto));
//
//					logger.info(sessionId + ">>> 修改订单状态，并放入" + reLong + "条redis");
//					logger.info(orderNo+"分期管家成功进件");
//
//					fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//					fenQiGuanJiaResponse.setMsg(drainageRsp.getMessage());
//				}else{
//					fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//					fenQiGuanJiaResponse.setMsg(drainageRsp.getMessage());
//				}
//			}else{
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//				fenQiGuanJiaResponse.setMsg("绑卡接口返回参数为空");
//
//			}
//		} catch (Exception e) {
//			logger.error("FenQiGuanJiaServiceImpl.saveBindCardWithCode()方法出现异常",e);
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("预绑卡接口系统异常");
//		}finally {
//            SxyThirdInterfaceLogUtils.setSxyLog(FenQiGuanJiaConstant.get(FQGJ_CHANNELID), orderNo, fenQiGuanJiaResponse.getCode()+"", fenQiGuanJiaResponse.getMsg(), "三方订单号");
//        }
//		logger.info("结束FenQiGuanJiaServiceImpl.saveBindCardWithCode()方法，返回参数："+JSON.toJSONString(fenQiGuanJiaResponse));
//		return fenQiGuanJiaResponse;
//	}
//
//	/**
//	 * 3.8 预授信接口(暂时保留)
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.FenQiGuanJiaService#queryPreApproavl(long,
//	 *      com.waterelephant.sxyDrainage.entity.fenqiguanjia.PreApprovalReq)
//	 */
//	@Override
//	public FenQiGuanJiaResponse queryPreApproavl(long sessionId, PreApprovalReq preApprovalReq) {
//		logger.info(sessionId + " 开始FenQiGuanJiaServiceImpl.queryPreApproavl()方法");
//		Map<String, Object> result = new HashMap<>();
//
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		try {
//
//			if (preApprovalReq == null) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("FenQiGuanJiaServiceImpl.queryPreApproavl方法接收信息为空");
//				logger.info(sessionId + "结束FenQiGuanJiaServiceImpl.queryPreApproavl方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//			String thirdOrderNo = preApprovalReq.getOrder_no();
//			if (StringUtils.isEmpty(thirdOrderNo)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订编号不存在");
//				logger.info(sessionId + "结束FenQiGuanJiaServiceImpl.queryPreApproavl方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//			if (bwOrderRong == null) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订单不存在");
//				logger.info(sessionId + "：结束FenQiGuanJiaServiceImpl.queryPreApproavl 方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			// 返回数据
//			BwProductDictionary dictionary = bwProductDictionaryService
//					.findById(Long.valueOf(SxyDrainageConstant.productId));
//			int loanTerm = Integer.parseInt(FenQiGuanJiaConstant.get(LOAN_TREM));
//
//			int[] loan_term = { loanTerm };
//			result.put("max_loan_amount", Float.valueOf(dictionary.getMaxAmount()));
//			result.put("min_loan_amount", Float.valueOf(dictionary.getMinAmount()));
//			result.put("range_amount", 100);
//			result.put("loan_term", loan_term);
//			result.put("loan_term_type", 1);
//			result.put("loan_unit", 1);
//			result.put("status", 2);
//
//			fenQiGuanJiaResponse.setData(result);
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//			fenQiGuanJiaResponse.setMsg("授信成功");
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaServiceImpl.queryPreApproavl方法出现异常");
//			logger.error("FenQiGuanJiaServiceImpl.queryPreApproavl方法出现异常", e);
//		}
//		logger.info("预授信接口返回数据：" + JSONObject.toJSONString(fenQiGuanJiaResponse));
//
//		return fenQiGuanJiaResponse;
//	}
//
//	/**
//	 * 3.9 试算接口(暂时保留)
//	 *
//	 * @see com.waterelephant.sxyDrainage.service.FenQiGuanJiaService#queryCalculate(long,
//	 *      com.waterelephant.sxyDrainage.entity.fenqiguanjia.CalculateFeeReq)
//	 */
//	@Override
//	public FenQiGuanJiaResponse queryCalculate(long sessionId, CalculateFeeReq calculateFeeReq) {
//		logger.info(sessionId + " 开始FenQiGuanJiaServiceImpl.queryCalculate()方法");
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		try {
//
//			if (calculateFeeReq == null) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("FenQiGuanJiaServiceImpl.queryCalculate方法接收信息为空");
//				logger.info(sessionId + "结束FenQiGuanJiaServiceImpl.queryCalculate方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//			// 三方订单
//			String thirdOrderNo = calculateFeeReq.getOrder_no();
//			// 用户选择金额
//			Float amount = calculateFeeReq.getAmount();
//
//			// 用户选择的期限
//			// int period = calculateFeeReq.getPeriod();
//			if (StringUtils.isEmpty(thirdOrderNo)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订编号不存在");
//				logger.info(sessionId + "结束FenQiGuanJiaServiceImpl.queryCalculate方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//			if (bwOrderRong == null) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订单不存在");
//				logger.info(sessionId + "：结束FenQiGuanJiaServiceImpl.queryCalculate 方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			int loanAmount = Float.valueOf(amount).intValue();
//			// 第一步， 查询水象云产品
//			BwProductDictionary bwProductDictionary = bwProductDictionaryService
//					.findBwProductDictionaryById(Integer.valueOf(SxyDrainageConstant.productId));
//
//			// 第二步，获取放款金额限制
//			Integer maxLoanAmount = bwProductDictionary.getMaxAmount();
//			Integer minLoanAmount = bwProductDictionary.getMinAmount();
//			// 分期利息率
//			Double interestRate = bwProductDictionary.getInterestRate();
//
//			if (loanAmount > maxLoanAmount) {
//				logger.info(sessionId + "：本次借款金额：" + loanAmount + "，大于最大借款金额：" + maxLoanAmount);
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("本次借款金额：" + loanAmount + "，大于最大借款金额：" + maxLoanAmount);
//				return fenQiGuanJiaResponse;
//			} else if (loanAmount < minLoanAmount) {
//				logger.info(sessionId + "：本次借款金额：" + loanAmount + "，小于最小借款金额：" + minLoanAmount);
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("本次借款金额：" + loanAmount + "，小于最小借款金额：" + minLoanAmount);
//				return fenQiGuanJiaResponse;
//			}
//
//			// 第三步，计算每期还款金额
//			HashMap<String, Object> hm = new HashMap<>();
//			// 到账金额
//			hm.put("receive_amount", amount);
//			// 服务费
//			hm.put("service_fee", 0.00f);
//			// 费用描述
//			hm.put("fee_desc", "利息");
//
//			Map<String, Object> map = new HashMap<>();
//			map.put("1",
//					loanAmount / 4 + DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 1, interestRate));
//			map.put("2",
//					loanAmount / 4 + DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 2, interestRate));
//			map.put("3",
//					loanAmount / 4 + DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 3, interestRate));
//			map.put("4",
//					loanAmount / 4 + DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 4, interestRate));
//
//			Double totalAmount = Double.valueOf(loanAmount)
//					+ DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 1, interestRate)
//					+ DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 2, interestRate)
//					+ DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 3, interestRate)
//					+ DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 4, interestRate);
//
//			// 每期应还本金
//			Double eachAmount = Double.valueOf(amount) / 4;
//			// 每期应还利息
//			Double periodOne = DrainageUtils.calculateRepayMoney(Double.valueOf(amount), 1, interestRate);
//			Double periodTwo = DrainageUtils.calculateRepayMoney(Double.valueOf(amount), 2, interestRate);
//			Double periodThree = DrainageUtils.calculateRepayMoney(Double.valueOf(amount), 3, interestRate);
//			Double periodFour = DrainageUtils.calculateRepayMoney(Double.valueOf(amount), 4, interestRate);
//
//			Map<String, Object> periodMap = new HashMap<>();
//			periodMap.put("1", DoubleUtil.add(eachAmount, periodOne));
//			periodMap.put("2", DoubleUtil.add(eachAmount, periodTwo));
//			periodMap.put("3", DoubleUtil.add(eachAmount, periodThree));
//			periodMap.put("4", DoubleUtil.add(eachAmount, periodFour));
//			// 每期还款金额
//			hm.put("period_amount", periodMap);
//			// 总还款金额
//			hm.put("total_amount", totalAmount);
//			hm.put("amount", amount);
//
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//			fenQiGuanJiaResponse.setMsg("接收数据成功");
//			fenQiGuanJiaResponse.setData(hm);
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaServiceImpl.queryCalculate方法出现异常");
//			logger.error("FenQiGuanJiaServiceImpl.queryCalculate方法出现异常", e);
//		}
//		logger.info(sessionId + "试算接口返回数据：" + JSONObject.toJSONString(fenQiGuanJiaResponse));
//
//		return fenQiGuanJiaResponse;
//	}
//
//	/**
//	 * 合同接口
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.FenQiGuanJiaService#queryContractUrl(long,
//	 *      com.waterelephant.sxyDrainage.entity.fenqiguanjia.ContractUrlReq)
//	 */
//	@Override
//	public FenQiGuanJiaResponse queryContractUrl(long sessionId, ContractUrlReq contractUrlReq) {
//		logger.info(sessionId + " 开始FenQiGuanJiaServiceImpl.queryContractUrl()方法");
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//
//		try {
//			if (contractUrlReq == null) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("FenQiGuanJiaServiceImpl.queryContractUrl方法接收信息为空");
//				logger.info(sessionId + "结束FenQiGuanJiaServiceImpl.queryContractUrl方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//			String thirdOrderNo = contractUrlReq.getOrder_no();
//
//			if (StringUtils.isEmpty(thirdOrderNo)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订编号不存在");
//				logger.info(sessionId + "结束FenQiGuanJiaServiceImpl.queryContractUrl方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//			if (bwOrderRong == null) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订单不存在");
//				logger.info(sessionId + "：结束FenQiGuanJiaServiceImpl.queryContractUrl 方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			Long orderId = bwOrderRong.getOrderId();
//
//			Map<String, Object> result = new HashMap<>();
//
//			BwAdjunct bwAdjunct = new BwAdjunct();
//			bwAdjunct.setOrderId(orderId);
//			bwAdjunct.setAdjunctType(29);
//			bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
//			if (CommUtils.isNull(bwAdjunct)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("找不到对应的工单信息附件");
//				logger.info(sessionId + "结束FenQiGuanJiaServiceImpl.queryContractUrl方法"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//			String contractUrl = SystemConstant.PDF_URL + bwAdjunct.getAdjunctPath();
//			result.put("contract_url", contractUrl);
//
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//			fenQiGuanJiaResponse.setMsg("获取合同地址成功");
//			fenQiGuanJiaResponse.setData(result);
//
//		} catch (Exception e) {
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("FenQiGuanJiaServiceImpl.queryContractUrl方法出现异常");
//			logger.error("FenQiGuanJiaServiceImpl.queryContractUrl方法出现异常", e);
//		}
//		logger.info(sessionId + "合同地址接口返回数据：" + JSONObject.toJSONString(fenQiGuanJiaResponse));
//		return fenQiGuanJiaResponse;
//	}
//
//	/**
//	 * 保存紧急联系人
//	 * 
//	 * @param orderAddInfo
//	 * @param orderId
//	 */
//	private FenQiGuanJiaResponse saveOrUpdatePersonInfo(OrderAddInfoReq orderAddInfo, Long orderId) {
//		logger.info("######保存紧急联系人#######");
//		FenQiGuanJiaResponse resp = new FenQiGuanJiaResponse();
//		if (CommUtils.isNull(orderAddInfo)) {
//			logger.info("orderAddInfo is null");
//			resp.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//			resp.setMsg("紧急联系人信息为空");
//			return resp;
//		}
//
//		String addr = orderAddInfo.getPerson_address(); // 居住地址
//
//		logger.info("工单id orderId=" + orderId);
//		BwOrder bo = bwOrderService.findBwOrderById(String.valueOf(orderId));
//		if (CommUtils.isNull(bo)) {
//			resp.setCode(102);
//			resp.setMsg("工单不存在");
//			return resp;
//		}
//		logger.info("借款人id=" + bo.getBorrowerId());
//		BwBorrower borrower = bwBorrowerService.findBwBorrowerByOrderId(orderId);
//		if (CommUtils.isNull(borrower)) {
//			resp.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//			resp.setMsg("借款人不存在");
//			return resp;
//		}
//		String cityName = borrower.getRegisterAddr();
//		String haveMarry = orderAddInfo.getUser_marriage();
//		// 亲属联系人
//		String relationName = orderAddInfo.getEmergency_contact_personA_name();
//		String relationPhone = orderAddInfo.getEmergency_contact_personA_phone();
//		String unrelationName = orderAddInfo.getEmergency_contact_personB_name();
//		String unrelationPhone = orderAddInfo.getEmergency_contact_personB_phone();
//		String wechat_number = orderAddInfo.getWechat_number();
//		String workmate_name = orderAddInfo.getWorkmate_name();
//		String workmate_phone = orderAddInfo.getWorkmate_phone();
//		String friend_first_name = orderAddInfo.getFriend_first_name();
//		String friend_first_phone = orderAddInfo.getFriend_first_phone();
//		String friend_second_name = orderAddInfo.getFriend_second_name();
//		String friend_second_phone = orderAddInfo.getFriend_second_phone();
//		String qq = orderAddInfo.getQq();
//		if (StringUtils.isEmpty(relationName) || StringUtils.isEmpty(unrelationName)
//				|| StringUtils.isEmpty(relationPhone) || StringUtils.isEmpty(unrelationPhone)) {
//			resp.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//			resp.setMsg("联系人姓名或手机号为空");
//			logger.info("联系人姓名或手机号为空");
//			return resp;
//		}
//
//		logger.info("根据工单号"+orderId+"查询个人信息");
//		BwPersonInfo bpi = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//
//		if (CommUtils.isNull(bpi)) {
//			// 添加
//			bpi = new BwPersonInfo();
//			bpi.setOrderId(orderId);
//			if ("1".equals(haveMarry)) {
//				// 1.表示未婚
//				bpi.setMarryStatus(0);
//			} else {
//				bpi.setMarryStatus(1);
//			}
//			bpi.setOrderId(orderId);
//			bpi.setRelationName(CommUtils.isNull(relationName) ? "" : relationName);
//			bpi.setRelationPhone(CommUtils.isNull(relationPhone) ? "" : relationPhone);
//			bpi.setUnrelationName(CommUtils.isNull(unrelationName) ? "" : unrelationName);
//			bpi.setUnrelationPhone(CommUtils.isNull(unrelationPhone) ? "" : unrelationPhone);
//			bpi.setEmail(orderAddInfo.getUser_email());
//			bpi.setFriend1Name(CommUtils.isNull(friend_first_name) ? "" : friend_first_name);
//			bpi.setFriend1Phone(CommUtils.isNull(friend_first_phone) ? "" : friend_first_phone);
//			bpi.setFriend2Name(CommUtils.isNull(friend_second_name) ? "" : friend_second_name);
//			bpi.setFriend2Phone(CommUtils.isNull(friend_second_phone) ? "" : friend_second_phone);
//			bpi.setQqchat(CommUtils.isNull(qq) ? "" : qq);
//			bpi.setWechat(CommUtils.isNull(wechat_number) ? "" : wechat_number);
//			bpi.setColleagueName(CommUtils.isNull(workmate_name) ? "" : workmate_name);
//			bpi.setColleaguePhone(CommUtils.isNull(workmate_phone) ? "" : workmate_phone);
//
//			bpi.setCreateTime(Calendar.getInstance().getTime());
//			bpi.setUpdateTime(Calendar.getInstance().getTime());
//			bpi.setAddress(addr);
//			bpi.setCityName(cityName);
//
//			bwPersonInfoService.save(bpi);
//		} else {
//			// 更新
//			// logger.info("城市名称=" + cityName);
//			bpi.setOrderId(orderId);
//			if ("1".equals(haveMarry)) {
//				// 1.表示未婚
//				bpi.setMarryStatus(0);
//			} else {
//				bpi.setMarryStatus(1);
//			}
//			bpi.setAddress(addr);
//			bpi.setOrderId(orderId);
//			bpi.setRelationName(relationName);
//			bpi.setRelationPhone(relationPhone);
//			bpi.setUnrelationName(unrelationName);
//			bpi.setUnrelationPhone(unrelationPhone);
//			bpi.setFriend1Name(CommUtils.isNull(friend_first_name) ? "" : friend_first_name);
//			bpi.setFriend1Phone(CommUtils.isNull(friend_first_phone) ? "" : friend_first_phone);
//			bpi.setFriend2Name(CommUtils.isNull(friend_second_name) ? "" : friend_second_name);
//			bpi.setFriend2Phone(CommUtils.isNull(friend_second_phone) ? "" : friend_second_phone);
//			bpi.setQqchat(CommUtils.isNull(qq) ? "" : qq);
//			bpi.setWechat(CommUtils.isNull(wechat_number) ? "" : wechat_number);
//			bpi.setColleagueName(CommUtils.isNull(workmate_name) ? "" : workmate_name);
//			bpi.setColleaguePhone(CommUtils.isNull(workmate_phone) ? "" : workmate_phone);
//			bpi.setUpdateTime(Calendar.getInstance().getTime());
//			bpi.setCityName(cityName);
//			bwPersonInfoService.update(bpi);
//		}
//		resp.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//		resp.setMsg("联系人信息保存成功！");
//		logger.info("联系人信息保存成功！");
//		return resp;
//	}
//
//	/**
//	 * 添加运营商基本信息
//	 * 
//	 * @param authOrderId
//	 * @param
//	 * @param borrowerId
//	 * @throws Exception
//	 */
//	private void addOrUpdateOperate(long sessionId, Long authOrderId, Mobile mobile, Long borrowerId, String channelId)
//			throws Exception {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//		if (CommUtils.isNull(mobile)) {
//			logger.info("order_info is null");
//			return;
//		}
//		User user = mobile.getUser();
//		if (CommUtils.isNull(user)) {
//			logger.info("addInfo_mobile.user is null");
//			return;
//		}
//		String phone = user.getPhone();
//		if (CommUtils.isNull(phone)) {
//			logger.info("addInfo_mobile.user.phone is null");
//			return;
//		}
//
//		logger.info("开始查询运营商记录,borrowerId=" + borrowerId);
//		BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
//		if (CommUtils.isNull(bwOperateBasic)) {
//			// 添加基本信息
//			logger.info("运营商记录不存在，开始新增");
//			bwOperateBasic = new BwOperateBasic();
//			bwOperateBasic.setUserSource(user.getUser_source());
//			bwOperateBasic.setIdCard(CommUtils.isNull(user.getId_card()) ? "" : user.getId_card());
//			bwOperateBasic.setAddr(CommUtils.isNull(user.getAddr()) ? "" : user.getAddr());
//			bwOperateBasic.setPhone(user.getPhone());
//			bwOperateBasic.setPhoneRemain(CommUtils.isNull(user.getPhone_remain()) ? "" : user.getPhone_remain());
//			bwOperateBasic.setRealName(CommUtils.isNull(user.getReal_name()) ? "" : user.getReal_name());
//			bwOperateBasic.setStarLevel(CommUtils.isNull(user.getStar_level()) ? "" : user.getStar_level());
//			bwOperateBasic.setPackageName(CommUtils.isNull(user.getPackage_name()) ? "" : user.getPackage_name());
//			bwOperateBasic
//					.setAuthentication(CommUtils.isNull(user.getAuthentication()) ? "" : user.getAuthentication());
//			bwOperateBasic.setScore(CommUtils.isNull(user.getScore()) ? "" : user.getScore());
//			bwOperateBasic.setContactPhone(CommUtils.isNull(user.getContact_phone()) ? "" : user.getContact_phone());
//			bwOperateBasic.setPhoneStatus(CommUtils.isNull(user.getPhone_status()) ? "" : user.getPhone_status());
//			bwOperateBasic.setCreateTime(new Date());
//			bwOperateBasic.setUpdateTime(new Date());
//
//			if (!CommUtils.isNull(user.getReg_time())) {
//				try {
//					bwOperateBasic.setRegTime(sdf.parse(user.getReg_time()));
//				} catch (Exception e) {
//					logger.info("reg_time为" + user.getReg_time() + "===用户注册时间格式错误，无法存储！");
//				}
//			}
//			bwOperateBasic.setBorrowerId(borrowerId);
//			bwOperateBasicService.save(bwOperateBasic);
//		} else {
//			// 修改基本信息
//			logger.info("运营商记录已存在，开始修改");
//			bwOperateBasic.setUserSource(user.getUser_source());
//			bwOperateBasic.setIdCard(CommUtils.isNull(user.getId_card()) ? "" : user.getId_card());
//			bwOperateBasic.setAddr(CommUtils.isNull(user.getAddr()) ? "" : user.getAddr());
//			bwOperateBasic.setPhone(user.getPhone());
//			bwOperateBasic.setPhoneRemain(CommUtils.isNull(user.getPhone_remain()) ? "" : user.getPhone_remain());
//			bwOperateBasic.setRealName(CommUtils.isNull(user.getReal_name()) ? "" : user.getReal_name());
//			bwOperateBasic.setUpdateTime(new Date());
//			if (!CommUtils.isNull(user.getReg_time())) {
//				try {
//					bwOperateBasic.setRegTime(sdf.parse(user.getReg_time()));
//				} catch (Exception e) {
//					logger.info("reg_time为" + user.getReg_time() + "===用户注册时间格式错误，无法存储！");
//				}
//			}
//			bwOperateBasic.setStarLevel(CommUtils.isNull(user.getStar_level()) ? "" : user.getStar_level());
//			bwOperateBasic.setPackageName(CommUtils.isNull(user.getPackage_name()) ? "" : user.getPackage_name());
//			bwOperateBasic
//					.setAuthentication(CommUtils.isNull(user.getAuthentication()) ? "" : user.getAuthentication());
//			bwOperateBasic.setScore(CommUtils.isNull(user.getScore()) ? "" : user.getScore());
//			bwOperateBasic.setContactPhone(CommUtils.isNull(user.getContact_phone()) ? "" : user.getContact_phone());
//			bwOperateBasic.setPhoneStatus(CommUtils.isNull(user.getPhone_status()) ? "" : user.getPhone_status());
//			bwOperateBasic.setBorrowerId(borrowerId);
//			bwOperateBasicService.update(bwOperateBasic);
//		}
//		thirdCommonService.addOrUpdateBwOrderAuth(sessionId, authOrderId, 1, Integer.valueOf(channelId));
//
//	}
//
//
//
//
//	/**
//	 * 新增公司信息
//	 * 
//	 * @param orderId
//	 * @param workPeriod
//	 * @param borrowerId
//	 */
//	private void saveBwWorkInfo(Long orderId, String workPeriod, String workType, Long borrowerId) throws Exception {
//		BwWorkInfo bwi = new BwWorkInfo();
//		bwi.setOrderId(orderId);
//		bwi.setCallTime("10:00 - 12:00");// 默认值（融360没有提供相关数据）
//		bwi.setCreateTime(Calendar.getInstance().getTime());
//		bwi.setWorkYears(FenQiGuanJiaUtils.getWorkPeriod(workPeriod));
//		bwi.setIndustry(FenQiGuanJiaUtils.getWorkType(workType));
//		bwWorkInfoService.save(bwi, borrowerId);
//	}
//
//	/**
//	 * 修改工作信息表
//	 * 
//	 * @param orderId
//	 * @param workPeriod
//	 */
//	private void updateBwWorkInfo(Long orderId, String workPeriod, String workType) throws Exception {
//		// 更新公司信息
//		BwWorkInfo bwi = new BwWorkInfo();
//		bwi.setOrderId(orderId);
//		bwi = bwWorkInfoService.findBwWorkInfoByAttr(bwi);
//		bwi.setCallTime("10:00 - 12:00");// 默认值（融360没有提供相关数据）
//		bwi.setUpdateTime(Calendar.getInstance().getTime());
//		bwi.setWorkYears(FenQiGuanJiaUtils.getWorkPeriod(workPeriod));
//		bwi.setIndustry(FenQiGuanJiaUtils.getWorkType(workType));
//		bwWorkInfoService.update(bwi);
//	}
//
//	/**
//	 * 添加通话记录
//	 *
//	 * @author
//	 * @param
//	 * @param  borrowerId
//	 * @throws Exception
//	 */
//	private void addOperateVoice(Mobile mobile, Long borrowerId,SqlSession sqlSession) throws Exception {
//		logger.info("开始进入addOperateVoice方法"+borrowerId);
//		SimpleDateFormat sdf_hms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		BwOperateVoiceMapper bwOperateVoiceMapper = sqlSession.getMapper(BwOperateVoiceMapper.class);
//		Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
//		List<Tel> tels = mobile.getTel();
//		if (CommUtils.isNull(callDate)) {
//			for (Tel tel : tels) {
//				List<Teldata> lists = tel.getTeldata();
//				if (!CommUtils.isNull(lists)) {
//					for (Teldata telData : lists) {
//						try {
//							BwOperateVoice bwOperateVoice = new BwOperateVoice();
//							bwOperateVoice.setUpdateTime(Calendar.getInstance().getTime());
//							bwOperateVoice.setBorrower_id(borrowerId);
//							// 检验日期格式
//							String callTime = null;
//							if (telData == null) {
//								continue;
//							}
//							if ("未知".equals(telData.getCall_time())||CommUtils.isNull(telData.getReceive_phone())) {
//								continue;
//							}
//							if(telData.getReceive_phone().length()>19){
//							    continue;
//                            }
//							Date jsonCallData=sdf_hms.parse(telData.getCall_time());
//							if(callDate == null || jsonCallData.after(callDate)){
//								callTime = telData.getCall_time();
//								bwOperateVoice.setCall_time(callTime);
//								bwOperateVoice.setCall_type(Integer.parseInt(telData.getCall_type()));
//								bwOperateVoice.setReceive_phone(telData.getReceive_phone());
//								bwOperateVoice.setTrade_addr(
//										CommUtils.isNull(telData.getTrade_addr()) ? "" : telData.getTrade_addr());
//								bwOperateVoice.setTrade_time(telData.getTrade_time());
//								bwOperateVoice.setTrade_type(Integer.parseInt(telData.getTrade_type()));
//								bwOperateVoiceMapper.insert(bwOperateVoice);
//							}
//
//						} catch (Exception e) {
//							logger.error("保存通话记录异常,忽略此条通话记录...", e);
//						}
//					}
//				}
//			}
//		}
//		sqlSession.commit();
//		sqlSession.clearCache();
//
//	}
//
//	/**
//	 * 3.7 拉取审批结论
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.FenQiGuanJiaService#pullApprovalResult(long,
//	 *      com.waterelephant.sxyDrainage.entity.fenqiguanjia.PullApprovalResult)
//	 */
//	@Override
//	public FenQiGuanJiaResponse pullApprovalResult(long sessionId, PullApprovalResult pullApprovalResult) {
//		logger.info(sessionId + ":开始3.7 拉取审批结论接口");
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		try {
//			String thirdOrderNo = pullApprovalResult.getOrder_no();
//			if (StringUtils.isEmpty(thirdOrderNo)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订单编号为空！");
//				logger.info(sessionId + "：结束3.7 拉取审批结论接口：" + JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			// 根据第三方订单编号获取订单id
//			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//			if (CommUtils.isNull(bwOrderRong)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订单为空！");
//				logger.info(sessionId + "：结束3.7 拉取审批结论接口：" + JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//			Long orderId = bwOrderRong.getOrderId();
//
//			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
//			if (CommUtils.isNull(bwOrder)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("订单为空！");
//				logger.info(sessionId + "：结束3.7 拉取审批结论接口：" + JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			String approveStatus = FenQiGuanJiaUtils.convertApproveStatus(bwOrder.getStatusId());
//			logger.info("映射第三方审批状态为：" + approveStatus);
//
//			Map<String, Object> map = new HashMap<>();
//			if ("10".equals(approveStatus)) {
//
//				BwProductDictionary bwProductDictionary = bwProductDictionaryService
//						.findBwProductDictionaryById(bwOrder.getProductId());
//				// 利息率
//				Double interestRate = bwProductDictionary.getInterestRate();
//				Double borrowAmount = bwOrder.getBorrowAmount();
//
//				map.put("order_no", thirdOrderNo);
//				map.put("conclusion", approveStatus);
//				map.put("term_unit", 4);
//				map.put("term_type", 0);
//				map.put("approval_term", 28);
//				map.put("approval_term_unit", 4);
//				map.put("approval_amount", borrowAmount);
//				map.put("receive_amount", borrowAmount);
//
//				// 每期应还本金
//				Double eachAmount = borrowAmount / 4;
//				// 每期应还利息
//				Double periodOne = DrainageUtils.calculateRepayMoney(borrowAmount, 1, interestRate);
//				Double periodTwo = DrainageUtils.calculateRepayMoney(borrowAmount, 2, interestRate);
//				Double periodThree = DrainageUtils.calculateRepayMoney(borrowAmount, 3, interestRate);
//				Double periodFour = DrainageUtils.calculateRepayMoney(borrowAmount, 4, interestRate);
//
//				Map<String, Object> periodMap = new HashMap<>();
//				periodMap.put("1", DoubleUtil.add(eachAmount, periodOne));
//				periodMap.put("2", DoubleUtil.add(eachAmount, periodTwo));
//				periodMap.put("3", DoubleUtil.add(eachAmount, periodThree));
//				periodMap.put("4", DoubleUtil.add(eachAmount, periodFour));
//
//				Double payAmount = borrowAmount + periodOne + periodTwo + periodThree + periodFour;
//
//				map.put("pay_amount", payAmount);
//				map.put("period_amount_str", periodMap);
//				map.put("approval_time", bwOrder.getUpdateTime().getTime());
//
//			} else if ("40".equals(approveStatus)) {
//				map.put("order_no", thirdOrderNo);
//				map.put("conclusion", approveStatus);
//				map.put("remark", "信用评分过低#拒绝客户");
//				map.put("refuse_time", bwOrder.getUpdateTime().getTime());
//			}
//
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//			fenQiGuanJiaResponse.setMsg("success");
//			fenQiGuanJiaResponse.setData(map);
//		} catch (Exception e) {
//			logger.error(sessionId + "：执行3.7 拉取审批结论接口异常：", e);
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("fail");
//		}
//		logger.info(sessionId + "：结束3.7 拉取审批结论接口：" + JSON.toJSONString(fenQiGuanJiaResponse));
//		return fenQiGuanJiaResponse;
//	}
//
//	/**
//	 * 3.13 拉取还款计划接口
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.FenQiGuanJiaService#pullRepaymentPlan(long,
//	 *      com.waterelephant.sxyDrainage.entity.fenqiguanjia.PullRepaymentPlan)
//	 */
//	@Override
//	public FenQiGuanJiaResponse pullRepaymentPlan(long sessionId, PullRepaymentPlan pullRepaymentPlan) {
//		logger.info(sessionId + ":开始3.13 拉取还款计划接口");
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		try {
//			String thirdOrderNo = pullRepaymentPlan.getOrder_no();
//			if (StringUtils.isEmpty(thirdOrderNo)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订单编号为空！");
//				logger.info(sessionId + "：结束3.13 拉取还款计划接口：" + JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			// 根据第三方订单编号获取订单id
//			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//			if (CommUtils.isNull(bwOrderRong)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订单为空！");
//				logger.info(sessionId + "：结束3.13 拉取还款计划接口：" + JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//			Long orderId = bwOrderRong.getOrderId();
//
//			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
//			if (CommUtils.isNull(bwOrder)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("订单为空！");
//				logger.info(sessionId + "：结束3.13 拉取还款计划接口：" + JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			// 获取银行卡信息
//			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
//			if (CommUtils.isNull(bwBankCard)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("银行卡信息为空！");
//				logger.info(sessionId + "：结束3.13 拉取还款计划接口：" + JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			// 获取还款计划
//			List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanService
//					.listBwRepaymentPlanByOrderId(bwOrder.getId());
//			if (CommUtils.isNull(bwRepaymentPlans)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("还款计划为空！");
//				logger.info(sessionId + "：结束3.13 拉取还款计划接口：" + JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			List<Map<String, Object>> planMaps = new ArrayList<Map<String, Object>>();
//
//			for (BwRepaymentPlan plan : bwRepaymentPlans) {
//				Map<String, Object> planMap = new HashMap<>();
//				planMap.put("period_no", plan.getNumber());// 期数
//				planMap.put("due_time", plan.getRepayTime().getTime() / 1000);// 到期时间
//				planMap.put("amount", plan.getRealityRepayMoney());// 总还款金额
//
//				BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//				bwOverdueRecord.setRepayId(plan.getId());
//				bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//				if (bwOverdueRecord != null) {
//
//					Double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0.0D
//							: bwOverdueRecord.getOverdueAccrualMoney();
//					Double advance = bwOverdueRecord.getAdvance();
//					Double overdueFee = DoubleUtil.sub(overdueAccrualMoney, advance);
//
//					planMap.put("overdue_fee", overdueFee);// 逾期费用
//				}
//
//				planMap.put("bill_status", plan.getRepayStatus());// 账单状态 1 未到期 2 已还款 3 逾期
//				planMap.put("pay_type", 5);// 还款方式 1主动还款；2跳转H5；4银行代扣；可加和，5=1+4.表示主动还款+银行代扣
//
//				if (plan.getRepayStatus() == 2) {
//					planMap.put("success_time", plan.getUpdateTime().getTime() / 1000);// 成功还款时间
//				}
//
//				planMap.put("can_repay_time", plan.getCreateTime().getTime() / 1000);// 可以还款的日期
//
//				planMaps.add(planMap);
//			}
//
//			Map<String, String> requestParam = new HashMap<>();
//
//			requestParam.put("order_no", bwOrderRong.getThirdOrderNo());// 订单编号
//			requestParam.put("open_bank", bwBankCard.getBankName());// 银行名称 还款银行名
//			requestParam.put("bank_card", bwBankCard.getCardNo());// 银行卡号 还款银行卡号
//			requestParam.put("repayment_plan", JSON.toJSONString(planMaps));// 还款计划
//
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//			fenQiGuanJiaResponse.setMsg("success");
//			fenQiGuanJiaResponse.setData(requestParam);
//		} catch (Exception e) {
//			logger.error(sessionId + "：执行3.13 拉取还款计划接口异常：", e);
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("fail");
//		}
//		logger.info(sessionId + "：结束3.13 拉取还款计划接口：" + JSON.toJSONString(fenQiGuanJiaResponse));
//		return fenQiGuanJiaResponse;
//	}
//
//	/**
//	 * 3.15 拉取订单状态接口
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.FenQiGuanJiaService#pullOrderStatus(long,
//	 *      com.waterelephant.sxyDrainage.entity.fenqiguanjia.PullOrderStatus)
//	 */
//	@Override
//	public FenQiGuanJiaResponse pullOrderStatus(long sessionId, PullOrderStatus pullOrderStatus) {
//		logger.info(sessionId + "：开始3.15 拉取订单状态接口");
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		try {
//			String thirdOrderNo = pullOrderStatus.getOrder_no();
//			if (StringUtils.isEmpty(thirdOrderNo)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订单编号为空！");
//				logger.info(sessionId + "-结束3.15 拉取订单状态接口-" + JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			// 根据第三方订单编号获取订单id
//			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//			if (CommUtils.isNull(bwOrderRong)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("第三方订单为空！");
//				logger.info(sessionId + "-结束3.15 拉取该[" + thirdOrderNo + "]订单状态接口-"
//						+ JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//			Long orderId = bwOrderRong.getOrderId();
//
//			BwOrder bwOrder1 = new BwOrder();
//			bwOrder1.setId(orderId);
//			bwOrder1.setChannel(Integer.valueOf(FenQiGuanJiaConstant.get(FQGJ_CHANNELID)));
//			List<BwOrder> bwOrders = bwOrderService.findBwOrderListByAttr(bwOrder1);
//			if (CommUtils.isNull(bwOrders) && bwOrders.size() == 0) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("我方订单为空！");
//				logger.info(sessionId + "：结束3.15 拉取订单状态接口：" + JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//			BwOrder bwOrder = bwOrders.get(0);
//
//			if (CommUtils.isNull(bwOrder)) {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_PARAMETER);
//				fenQiGuanJiaResponse.setMsg("订单为空！");
//				logger.info(sessionId + "：结束3.15 拉取订单状态接口：" + JSON.toJSONString(fenQiGuanJiaResponse));
//				return fenQiGuanJiaResponse;
//			}
//
//			String orderStatus = FenQiGuanJiaUtils.convertOrderStatus(bwOrder.getStatusId());
//			logger.info("映射第三方订单状态为：" + orderStatus);
//
//			Map<String, Object> map = new HashMap<>();
//			map.put("order_no", thirdOrderNo);
//			map.put("order_status", orderStatus);
//			map.put("update_time", bwOrder.getUpdateTime().getTime());
//
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//			fenQiGuanJiaResponse.setMsg("获取成功");
//			fenQiGuanJiaResponse.setData(map);
//		} catch (Exception e) {
//			logger.error(sessionId + "-执行3.15 拉取订单状态接口异常-" + e);
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("请求失败");
//		}
//		logger.info(sessionId + "-结束3.15 拉取订单状态接口-" + JSON.toJSONString(fenQiGuanJiaResponse));
//		return fenQiGuanJiaResponse;
//	}
//
//	/**
//	 * 3.16 主动还款接口
//	 * 
//	 * @see
//	 *      com.waterelephant.sxyDrainage.entity.fenqiguanjia.ActiveRepayment)
//	 */
//
//	@Override
//	public FenQiGuanJiaResponse updateActiveRepayment(long sessionId, ActiveRepayment activeRepayment) {
//		logger.info(sessionId + ":开始3.16 主动还款接口");
//		FenQiGuanJiaResponse fenQiGuanJiaResponse = new FenQiGuanJiaResponse();
//		try {
//			String thirdOrderNo = activeRepayment.getOrder_no();
//			DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, thirdOrderNo);
//			logger.info(thirdOrderNo + ":返回还款结果" + JSONObject.toJSONString(drainageRsp));
//
//			if (drainageRsp != null) {
//				if (drainageRsp.getCode().equals("000")) {
//					fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_SUCCESS);
//					fenQiGuanJiaResponse.setMsg(drainageRsp.getMessage());
//				} else {
//					fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//					fenQiGuanJiaResponse.setMsg(drainageRsp.getMessage());
//				}
//			} else {
//				fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//				fenQiGuanJiaResponse.setMsg("接口返回信息为空");
//			}
//
//		} catch (Exception e) {
//			logger.error(sessionId + "-执行3.16 主动还款接口异常-" + e);
//			fenQiGuanJiaResponse.setCode(FenQiGuanJiaResponse.CODE_FAILURE);
//			fenQiGuanJiaResponse.setMsg("请求失败");
//		}
//		logger.info(sessionId + "-结束3.16 主动还款接口-" + JSON.toJSONString(fenQiGuanJiaResponse));
//		return fenQiGuanJiaResponse;
//	}
//
//}
