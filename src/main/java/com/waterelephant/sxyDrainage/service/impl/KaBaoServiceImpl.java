//
//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.parser.ParserConfig;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBlacklist;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwContactList;
//import com.waterelephant.entity.BwIdentityCard2;
//import com.waterelephant.entity.BwMerchantOrder;
//import com.waterelephant.entity.BwOperateBasic;
//import com.waterelephant.entity.BwOperateVoice;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderChannel;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwOverdueRecord;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.entity.BwRejectRecord;
//import com.waterelephant.entity.BwRepaymentPlan;
//import com.waterelephant.entity.BwWorkInfo;
//import com.waterelephant.service.BwBlacklistService;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwOperateVoiceService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.BwRejectRecordService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.IBwMerchantOrderService;
//import com.waterelephant.service.IBwOrderChannelService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.service.IBwRepaymentPlanService;
//import com.waterelephant.service.IBwRepaymentService;
//import com.waterelephant.service.IBwWorkInfoService;
//import com.waterelephant.service.impl.BwBorrowerService;
//import com.waterelephant.service.impl.BwContactListService;
//import com.waterelephant.service.impl.BwIdentityCardServiceImpl;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageEnum;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.kabao.CallInfoDetails;
//import com.waterelephant.sxyDrainage.entity.kabao.DataBaseInfo;
//import com.waterelephant.sxyDrainage.entity.kabao.DataCallInfo;
//import com.waterelephant.sxyDrainage.entity.kabao.KaBaoBasicInfo;
//import com.waterelephant.sxyDrainage.entity.kabao.KaBaoBindCard;
//import com.waterelephant.sxyDrainage.entity.kabao.KaBaoCompanyInfo;
//import com.waterelephant.sxyDrainage.entity.kabao.KaBaoContact;
//import com.waterelephant.sxyDrainage.entity.kabao.KaBaoIdentifyInfo;
//import com.waterelephant.sxyDrainage.entity.kabao.KaBaoOperator;
//import com.waterelephant.sxyDrainage.entity.kabao.KaBaoPushOrderReq;
//import com.waterelephant.sxyDrainage.entity.kabao.KaBaoResponse;
//import com.waterelephant.sxyDrainage.entity.kabao.Origin;
//import com.waterelephant.sxyDrainage.entity.kabao.Transportation;
//import com.waterelephant.sxyDrainage.service.AsyncKaBaoTask;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.KaBaoService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.interfaceLog.SxyThirdInterfaceLogUtils;
//import com.waterelephant.sxyDrainage.utils.kabao.ConvertSecondUtil;
//import com.waterelephant.sxyDrainage.utils.kabao.KaBaoConstant;
//import com.waterelephant.sxyDrainage.utils.kabao.KaBaoUtils;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.AESUtil;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.DateUtil;
//import com.waterelephant.utils.DoubleUtil;
//import com.waterelephant.utils.IdcardValidator;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//import com.waterelephant.utils.UploadToCssUtils;
//
//import tk.mybatis.mapper.entity.Example;
//
///**
// * 
// * @ClassName: KaBaoServiceImpl
// * @Description:
// * @author liwanliang
// *
// */
//@Service
//public class KaBaoServiceImpl implements KaBaoService {
//
//	private Logger logger = LoggerFactory.getLogger(KaBaoServiceImpl.class);
//
//	@Autowired
//	private IBwOrderChannelService bwOrderChannelService;
//
//	@Autowired
//	private BwBorrowerService bwBorrowerService;
//
//	@Autowired
//	private CommonService commonService;
//
//	@Autowired
//	private ThirdCommonService thirdCommonService;
//
//	@Autowired
//	private IBwBankCardService bwBankCardService;
//
//	@Autowired
//	private IBwOrderService bwOrderService;
//
//	@Autowired
//	private BwOrderRongService bwOrderRongService;
//
//	@Autowired
//	private IBwMerchantOrderService bwMerchantOrderServiceImpl;
//
//	@Autowired
//	private IBwWorkInfoService bwWorkInfoService;
//
//	@Autowired
//	private BwOperateBasicService bwOperateBasicService;
//
//	@Autowired
//	private BwContactListService bwContactListService;
//
//	@Autowired
//	private BwIdentityCardServiceImpl bwIdentityCardServiceImpl;
//
//	@Autowired
//	private IBwPersonInfoService bwPersonInfoService;
//
//	@Autowired
//	private IBwRepaymentPlanService bwRepaymentPlanService;
//
//	@Autowired
//	private BwOverdueRecordService bwOverdueRecordService;
//
//	@Autowired
//	private BwOperateVoiceService bwOperateVoiceService;
//
//	@Autowired
//	private AsyncKaBaoTask asyncKaBaoTask;
//
//	@Autowired
//	private IBwRepaymentService bwRepaymentService;
//
//	@Autowired
//	private BwRejectRecordService bwRejectRecordService;
//	
//	@Autowired
//	private BwBlacklistService bwBlacklistService;
//	
//	
//
//	/**
//	 * TODO 4.1.1 准入用户检验接口.
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.KaBaoService#checkUser(long, java.lang.String, java.lang.String)
//	 */
//	@Override
//	public KaBaoResponse checkUser(long sessionId, String appId, String request) {
//		logger.info(sessionId + ":51卡宝>>>准入用户检验接口>>>进入【KaBaoServiceImpl.checkUser方法】");
//		KaBaoResponse kaBaoResponse = new KaBaoResponse();
//		Map<String, Object> responseMap = new HashMap<>();
//		try {
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			logger.info(sessionId + ":51卡宝>>>【KaBaoServiceImpl.checkUser方法】>>>获取orderChannel");
//			if (null == orderChannel) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("渠道不存在");
//				return kaBaoResponse;
//			}
//
//			boolean isCorrect = checkChannelId(orderChannel, KaBaoConstant.CHANNELID);
//			if (!isCorrect) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("渠道错误!");
//				return kaBaoResponse;
//			}
//
//			// AES解密
//			String requestParam = AESUtil.Decrypt(request, KaBaoConstant.AESKEY);
//			logger.info(sessionId + ":51卡宝>>>【KaBaoServiceImpl.checkUser方法】>>>request请求数据:" + requestParam);
//			if (StringUtils.isBlank(requestParam)) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("请求参数为空");
//				return kaBaoResponse;
//			}
//
//			String mobile = null;
//			JSONObject parseObject = JSON.parseObject(requestParam);
//			if (null != parseObject) {
//				mobile = parseObject.getString("mobile");
//				if (StringUtils.isBlank(mobile)) {
//					logger.info(sessionId + ":51卡宝>>>【KaBaoServiceImpl.checkUser方法】>>>用户手机号为空");
//					kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//					kaBaoResponse.setMsg("手机号为空");
//					return kaBaoResponse;
//				}
//			}
//			
//			// 金额限制
//			responseMap.put("maxLimit", 20000 * 100); // 最高额度 单位:分
//			responseMap.put("minLimit", 2000 * 100); // 最低额度
//			responseMap.put("step", 100 * 100); // 步长 100
//			responseMap.put("number", 4); // 期数
//			responseMap.put("periodNum", 28); // 可贷期限
//			responseMap.put("periodUnit", 1); // 期限单位
//			
//			
//			BwBorrower borrowerByMobile = bwBorrowerService.oldUserFilter3(mobile);
//			//黑名单
//			Example example = new Example(BwBlacklist.class);
//			example.createCriteria().andEqualTo("sort", 1).andEqualTo("status", 1).andEqualTo("phone",mobile.toUpperCase());
//			List<BwBlacklist> blackList = bwBlacklistService.findBwBlacklistByExample(example);
//			if (!CommUtils.isNull(blackList)) {
//				logger.info(sessionId +":51卡宝>>>【KaBaoServiceImpl.checkUser方法】>>>命中黑名单>>>mobile=" + mobile);
//				responseMap.put("isCanLoan", 0); 	// 是否可以借款  0否  1是
//				responseMap.put("rejectReason", 1); // 命中黑名单
//				responseMap.put("remark", "命中黑名单");
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("禁止用户通过！");
//				String response = AESUtil.Encrypt(JSON.toJSONString(responseMap), KaBaoConstant.AESKEY);
//				kaBaoResponse.setResponse(response);
//				SxyThirdInterfaceLogUtils.setSxyLog(KaBaoConstant.CHANNELID, mobile, kaBaoResponse.getCode(),"命中黑名单", "注册手机号");
//				return kaBaoResponse;
//			}
//			//新老用户
//			if(CommUtils.isNull(borrowerByMobile)){
//				//新用户
//				logger.info(sessionId + ":51卡宝>>>【KaBaoServiceImpl.checkUser方法】>>>用户通过>>>mobile=" + mobile);
//				responseMap.put("isStock", 0);
//				responseMap.put("isCanLoan", 1);
//				// 对响应参数AES加密
//				logger.info(sessionId + ":51卡宝>>>【KaBaoServiceImpl.checkUser方法】>>>response加密前数据:" + JSON.toJSONString(responseMap));
//				String response = AESUtil.Encrypt(JSON.toJSONString(responseMap), KaBaoConstant.AESKEY);
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_SUCCESS);
//				kaBaoResponse.setMsg("允许用户通过！");
//				kaBaoResponse.setResponse(response);
//				return kaBaoResponse;
//			}else{
//				//老用户：
//				responseMap.put("isStock", 1); 	//增量用户0否1是
//				// 是否进行中的订单
//				Long count = bwOrderService.findProOrder(borrowerByMobile.getId() + "");
//				if (count != null && count.intValue() > 0) {
//					responseMap.put("isCanLoan", 0);// 是否可以借款（0：否，1：是）
//					responseMap.put("rejectReason", 2);// 拒绝原因（1 黑名单，2 在贷，3 命中被拒规则）
//					responseMap.put("remark", "存在进行中订单");
//					// 对响应参数AES加密
//					logger.info(sessionId + ":51卡宝>>>【KaBaoServiceImpl.checkUser方法】>>>存在进行中的工单!>>>mobile=" + mobile);
//					String response = AESUtil.Encrypt(JSON.toJSONString(responseMap), KaBaoConstant.AESKEY);
//					kaBaoResponse.setResponse(response);
//					kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//					kaBaoResponse.setMsg("禁止用户通过！");
//					SxyThirdInterfaceLogUtils.setSxyLog(KaBaoConstant.CHANNELID, mobile, kaBaoResponse.getCode(),"存在进行中的工单", "注册手机号");
//					return kaBaoResponse;
//				}
//				// 是否有被拒记录
//				BwRejectRecord record = bwRejectRecordService.findBwRejectRecordByBid(borrowerByMobile.getId());
//				if(isRejected(record)){
//					responseMap.put("rejectReason", 3);		// 拒绝原因（1 黑名单，2 在贷，3 命中被拒规则）
//					responseMap.put("isCanLoan", 0);		// 是否可以借款（0：否，1：是）
//					responseMap.put("remark", "命中被拒规则");
//					// 对响应参数AES加密
//					logger.info(sessionId + ":51卡宝>>>【KaBaoServiceImpl.checkUser方法】>>>用户存在被拒记录!>>>mobile=" + mobile);
//					String response = AESUtil.Encrypt(JSON.toJSONString(responseMap), KaBaoConstant.AESKEY);
//					kaBaoResponse.setResponse(response);
//					kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//					kaBaoResponse.setMsg("禁止用户通过！");
//					SxyThirdInterfaceLogUtils.setSxyLog(KaBaoConstant.CHANNELID, mobile, kaBaoResponse.getCode(),"命中被拒规则", "注册手机号");
//					return kaBaoResponse;
//				}
//				
//				//查身份证,若身份证存在,则对年龄做判断 <=21 || >50
//				String idCard = borrowerByMobile.getIdCard();
//				if(!StringUtils.isBlank(idCard)){
//					//校验年龄
//					Calendar cal = Calendar.getInstance();
//					String year = idCard.substring(6, 10);
//					if (year.indexOf("%") == -1) {
//						int iCurrYear = cal.get(Calendar.YEAR);
//						int age = iCurrYear - Integer.valueOf(year);
//						if (age > 55 || age <= 21) {
//							responseMap.put("isCanLoan", 0);		// 是否可以借款（0：否，1：是）
//							responseMap.put("rejectReason", 4);		// 拒绝原因（1 黑名单，2 在贷，3 命中被拒规则）
//							responseMap.put("remark", "用户年龄超限");
//							// 对响应参数AES加密
//							logger.info(sessionId + ":51卡宝>>>【KaBaoServiceImpl.checkUser方法】>>>用户年龄超限!>>>mobile=" + mobile);
//							String response = AESUtil.Encrypt(JSON.toJSONString(responseMap), KaBaoConstant.AESKEY);
//							kaBaoResponse.setResponse(response);
//							kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//							kaBaoResponse.setMsg("禁止用户通过！");
//							SxyThirdInterfaceLogUtils.setSxyLog(KaBaoConstant.CHANNELID, mobile, kaBaoResponse.getCode(),"用户年龄超限", "注册手机号");
//							return kaBaoResponse;
//						}
//					}
//					
//				}
//				
//				//符合借款条件的老用户
//				// 是否绑卡
//				BwBankCard bwBankCard = new BwBankCard();
//				bwBankCard.setBorrowerId(borrowerByMobile.getId());
//				bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
//				// 对于已绑卡用户要回传银行名、卡号、预留手机号
//				if (bwBankCard != null && bwBankCard.getSignStatus() > 0) {
//					responseMap.put("bank", bwBankCard.getBankName()); // 已绑银行
//					responseMap.put("bankCardNum", bwBankCard.getCardNo()); // 已绑银行卡号
//					responseMap.put("phone", bwBankCard.getPhone()); // 银行预留手机号
//				}
//				responseMap.put("isCanLoan", 1); // 是否可以借款（0：否，1：是）
//				// 对响应参数AES加密
//				logger.info(sessionId + ":51卡宝>>>【KaBaoServiceImpl.checkUser方法】>>>response加密前数据:" + JSON.toJSONString(responseMap));
//				String response = AESUtil.Encrypt(JSON.toJSONString(responseMap), KaBaoConstant.AESKEY);
//				kaBaoResponse.setResponse(response);
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_SUCCESS);
//				kaBaoResponse.setMsg("允许用户通过!");
//				return kaBaoResponse;
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ":51卡宝>>>执行准入用户检验接口Service异常", e);
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_FAILERR);
//			kaBaoResponse.setMsg("系统异常,请稍后再试");
//			return kaBaoResponse;
//		}
//	}
//
//	/**
//	 * TODO 4.1.4 进件推送接口
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.KaBaoService#pushOrder(long, java.lang.String, java.lang.String)
//	 */
//	@Override
//	public KaBaoResponse savePushOrder(long sessionId, String appId, String request) {
//		logger.info(sessionId + ":51卡宝>>>进件推送接口>>>进入pushOrder()方法");
//		KaBaoResponse kaBaoResponse = new KaBaoResponse();
//		Map<String, Object> mapRes = new HashMap<String, Object>();	//	封装响应数据
//		String thirdOrderNo = null;
//		try {
//			// 根据appId 获取channelId
//			logger.info(sessionId + ":51卡宝>>>pushOrder()方法>>>获取orderChannel");
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("渠道不存在");
//				return kaBaoResponse;
//			}
//			Integer channelId = orderChannel.getId();
//
//			boolean isCorrect = checkChannelId(orderChannel, KaBaoConstant.CHANNELID);
//			if (!isCorrect) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("渠道错误!");
//				return kaBaoResponse;
//			}
//
//			// AES解密
//			String requestJson = AESUtil.Decrypt(request, KaBaoConstant.AESKEY);
//			if (StringUtils.isBlank(requestJson)) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("请求入参为空！");
//				logger.info("解密request入参JSON数据为空!");
//				return kaBaoResponse;
//			}
//			ParserConfig.getGlobalInstance().setAsmEnable(false);
//			// 获取请求参数
//			KaBaoPushOrderReq kaBaoPushOrderReq = JSON.parseObject(requestJson, KaBaoPushOrderReq.class);
//			if (null == kaBaoPushOrderReq) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("请求参数为空！");
//				logger.info(sessionId + ":51卡宝>>>进件推送接口KaBaoPushOrderReq为空>>>");
//				return kaBaoResponse;
//			}
//			// logger.info("51卡宝>>>进件推送接口请求入参：" + JSON.toJSONString(kaBaoPushOrderReq));
//			// 基本信息
//			KaBaoBasicInfo basicInfo = kaBaoPushOrderReq.getBasicInfo();
//			// 实名认证信息
//			KaBaoIdentifyInfo identifyInfo = kaBaoPushOrderReq.getIdentifyInfo();
//			// 公司信息
//			KaBaoCompanyInfo companyInfo = kaBaoPushOrderReq.getCompanyInfo();
//			// 通讯录
//			List<KaBaoContact> contacts = kaBaoPushOrderReq.getContacts(); // 非必须
//			// 运营商数据
//			KaBaoOperator operator = kaBaoPushOrderReq.getOperator();
//			if (null == basicInfo || null == identifyInfo || null == companyInfo || null == operator) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("进件必要参数为空！");
//				logger.info("数据映射实体类存在空!");
//				return kaBaoResponse;
//			}
//
//			// 新增或更新借款人
//			logger.info(sessionId + ":51卡宝>>>pushOrder()方法>>>开始新增或更新借款人");
//			String userName = basicInfo.getName();
//			String idCard = basicInfo.getIdCard();
//			String phone = basicInfo.getPhone();
//
//			// 对身份证进行校验
//			boolean validate = IdcardValidator.isValidatedAllIdcard(idCard);
//			if (!validate) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("身份证不合法！");
//				logger.info(sessionId + ":51卡宝>>>结束pushOrder方法，返回结果:身份证为" + JSON.toJSONString(idCard));
//				return kaBaoResponse;
//			}
//
//			thirdOrderNo = basicInfo.getThirdOrderNo();
//
//			if (StringUtils.isBlank(userName)) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("姓名为空");
//				logger.info(sessionId + ":51卡宝>>>结束pushOrder方法，返回结果:姓名为空");
//				return kaBaoResponse;
//			}
//			if (StringUtils.isBlank(idCard)) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("身份证号码为空");
//				logger.info(sessionId + ":51卡宝>>>结束pushOrder方法，返回结果:身份证号码为空");
//				return kaBaoResponse;
//			}
//			if (StringUtils.isBlank(phone)) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("手机号为空");
//				logger.info(sessionId + ":51卡宝>>>结束pushOrder方法，返回结果:手机号为空");
//				return kaBaoResponse;
//			}
//			if (CommUtils.isNull(channelId)) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("渠道编码为空");
//				logger.info(sessionId + ":51卡宝>>>结束pushOrder方法，返回结果:渠道编码为空");
//				return kaBaoResponse;
//			}
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("机构订单流水号为空");
//				logger.info(sessionId + ":51卡宝>>>结束pushOrder()方法，返回结果:三方订单号为空");
//				return kaBaoResponse;
//			}
//
//			//判断一个身份证是否有多个手机号码申请贷款,是否有进行中的工单
//			boolean flag = thirdCommonService.checkUserAccountProgressOrder(sessionId, idCard);
//			if (flag) {
//				mapRes.put("status", "302");
//				mapRes.put("remark", "该用户已存在进行中的订单,请勿重复推送！");
//				logger.info(
//						sessionId + ":进件失败>>>三方工单号:" + thirdOrderNo + ">>>response加密前数据:" + JSON.toJSONString(mapRes));
//				String response = AESUtil.Encrypt(JSON.toJSONString(mapRes), KaBaoConstant.AESKEY);
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_FAILERR);
//				kaBaoResponse.setMsg("进件失败！");
//				kaBaoResponse.setResponse(response);
//				return kaBaoResponse;
//			}
//
//			BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, userName, idCard, phone, channelId);
//			long borrowerId = borrower.getId();
//			logger.info(sessionId + ":51卡宝>>>pushOrder()方法>>>结束新增或更新借款人:borrowerId[" + borrowerId + "]");
//
//			// 判断该渠道是否有撤回的订单
//			logger.info(sessionId + ":51卡宝>>>pushOrder()方法>>>开始判断该渠道是否有撤回的订单:borrowerId[" + borrowerId + "]");
//			BwOrder example = new BwOrder();
//			example.setBorrowerId(borrowerId);
//			example.setStatusId(8L); // 8:撤回
//			example.setChannel(channelId);
//			BwOrder order = bwOrderService.findBwOrderByAttr(example);
//			if (order != null) {
//				mapRes.put("status", "302");
//				mapRes.put("remark", "该用户命中被拒规则！");
//				// 对响应参数AES加密
//				logger.info(sessionId + ":进件失败,存在被测回的工单>>>进件三方工单号:" + thirdOrderNo + ">>>response加密前数据:"
//						+ JSON.toJSONString(mapRes));
//				String response = AESUtil.Encrypt(JSON.toJSONString(mapRes), KaBaoConstant.AESKEY);
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_FAILERR);
//				kaBaoResponse.setMsg("进件失败！");
//				kaBaoResponse.setResponse(response);
//				return kaBaoResponse;
//			}
//
//			// 判断是否有草稿状态的订单
//			logger.info(sessionId + ":51卡宝>>>pushOrder()方法>>>开始判断是否有草稿状态的订单");
//			Integer productId = Integer.valueOf(KaBaoConstant.PRODUCTID);
//			BwOrder bwOrder = new BwOrder();
//			bwOrder.setBorrowerId(borrowerId);
//			bwOrder.setStatusId(1L); // 当前工单状态 1草稿
//			bwOrder.setProductType(2);// 产品类型(1、单期，2、分期)
//			bwOrder.setChannel(channelId);
//			bwOrder.setProductId(productId);
//			List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);// 先查询草稿状态的订单
//			bwOrder.setStatusId(8L);
//			List<BwOrder> boList8 = bwOrderService.findBwOrderListByAttr(bwOrder);// 再查询撤回状态的订单
//			boList.addAll(boList8); // 第一次进件被审批撤回后，再次进件时，更新第一次的订单
//			if (boList != null && boList.size() > 0) {
//				bwOrder = boList.get(boList.size() - 1);
//				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwOrder.setProductType(2); // 产品类型(1、单期，2、分期)
//				bwOrder.setStatusId(1L); // 当前工单状态 1草稿
//				bwOrder.setExpectMoney(basicInfo.getLoanAmount() != null ? basicInfo.getLoanAmount() / 100 : 0.0); // 用户预期借款金额
//				bwOrder.setExpectNumber(4);
//				bwOrder.setRepayType(2); // // 还款方式 1:先息后本 2:等额本息
//				bwOrderService.updateBwOrder(bwOrder);
//			} else {
//				bwOrder = new BwOrder();
//				bwOrder.setOrderNo(DrainageUtils.generateOrderNo());
//				bwOrder.setBorrowerId(borrower.getId());
//				bwOrder.setStatusId(1L);
//				bwOrder.setCreateTime(Calendar.getInstance().getTime());
//				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//				bwOrder.setChannel(channelId);
//				bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE)); // 免罚息期
//				bwOrder.setApplyPayStatus(0); // 申请还款状态 0:未申请 1:申请
//				bwOrder.setProductId(productId);
//				bwOrder.setProductType(2);
//				bwOrder.setRepayType(2);
//				bwOrder.setExpectMoney(basicInfo.getLoanAmount() != null ? basicInfo.getLoanAmount() / 100 : 0.0);
//				bwOrder.setExpectNumber(4);
//				bwOrderService.addBwOrder(bwOrder);
//			}
//			long orderId = bwOrder.getId();
//
//			try {
//				// ============拉取征信数据======================
//				String key = "phone_apply";
//				Map<String, Object> params = new HashMap<>();
//				params.put("mobile", phone);
//				params.put("order_id", orderId);
//				params.put("borrower_id", borrowerId);
//				String value = JSON.toJSONString(params);
//				RedisUtils.rpush(key, value);
//				// ============拉取征信数据======================
//			} catch (Exception e) {
//				logger.info("拉取征信数据发生异常：=====>>>" + e);
//			}
//
//			logger.info(sessionId + ":51卡宝>>>pushOrder()方法>>>结束判断是否有草稿状态的订单orderId:" + orderId);
//
//			// 判断是否有三方定单
//			logger.info(sessionId + ":51卡宝>>>pushOrder()方法>>>开始判断是否有三方订单thirdOrderNo:" + thirdOrderNo);
//			BwOrderRong bwOrderRong = new BwOrderRong();
//			bwOrderRong.setThirdOrderNo(thirdOrderNo);
//			bwOrderRong.setChannelId(Long.valueOf(channelId));
//			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//			if (null != bwOrderRong) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_SIGNERR);
//				kaBaoResponse.setMsg("订单已存在，不能重复进件");
//				mapRes.put("status", "302");
//				logger.info(
//						sessionId + ":进件失败>>>三方工单号:" + thirdOrderNo + ">>>response加密前数据:" + JSON.toJSONString(mapRes));
//				String response = AESUtil.Encrypt(JSON.toJSONString(mapRes), KaBaoConstant.AESKEY);
//				kaBaoResponse.setResponse(response);
//				return kaBaoResponse;
//			}
//			bwOrderRong = bwOrderRongService.findBwOrderRongByOrderId(orderId);
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
//				bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//				bwOrderRongService.update(bwOrderRong);
//			}
//
//			// 判断是否有商户订单信息
//			logger.info(sessionId + ":51卡宝>>>pushOrder()方法>>>开始判断是否有商户订单信息orderId:" + orderId);
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
//			logger.info(sessionId + ":51卡宝>>>pushOrder()方法>>>开始判断是否有工作信息orderId:" + orderId);
//			BwWorkInfo bwWorkInfo = new BwWorkInfo();
//			bwWorkInfo.setOrderId(orderId);
//			bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//			if (null == bwWorkInfo) {
//				bwWorkInfo = new BwWorkInfo();
//				bwWorkInfo.setOrderId(orderId);
//				bwWorkInfo.setIncome(companyInfo.getIncome()); // 月收入
//				bwWorkInfo.setComName(companyInfo.getCompanyName()); // 公司名称
//				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值 方便接听电话时间
//				bwWorkInfo.setIndustry(companyInfo.getIndustry()); // 行业
//
//				bwWorkInfo.setWorkYears(companyInfo.getJobTime()); // 工作年限
//				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//				bwWorkInfo.setCreateTime(Calendar.getInstance().getTime());
//				bwWorkInfoService.save(bwWorkInfo, borrowerId);
//				logger.info("保存借款人" + borrowerId + "工作信息成功");
//			} else {
//				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
//				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//				bwWorkInfo.setIncome(companyInfo.getIncome());
//				bwWorkInfo.setComName(companyInfo.getCompanyName());
//				bwWorkInfo.setIndustry(companyInfo.getIndustry()); // 行业
//				bwWorkInfo.setWorkYears(companyInfo.getJobTime()); // 工作年限
//				bwWorkInfoService.update(bwWorkInfo);
//				logger.info("更新借款人" + borrowerId + "工作信息成功");
//			}
//			
//			//插入个人认证记录 bw_order_auth 认证类型: 1：运营商 2：个人信息 3：拍照 4：芝麻信用 5：社保 6：公积金 7：邮箱 8：淘宝 9：京东 10:单位信息 11：用款确认
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, channelId);
//
//			// 通讯录
//			if (CollectionUtils.isNotEmpty(contacts)) {
//				logger.info(sessionId + ":51卡宝>>>pushOrder()方法>>>开始处理通讯录信息");
//				List<BwContactList> listConS = new ArrayList<BwContactList>();
//				for (KaBaoContact kaBaoContact : contacts) {
//					if (CommUtils.isNull(kaBaoContact.getName()) || CommUtils.isNull(kaBaoContact.getPhone())) {
//						continue;
//					}
//					// 借款人通讯录列表
//					BwContactList bwContactList = new BwContactList();
//					bwContactList.setBorrowerId(borrowerId);
//					bwContactList.setPhone(kaBaoContact.getPhone());
//					bwContactList.setName(kaBaoContact.getName());
//					listConS.add(bwContactList);
//				}
//				bwContactListService.addOrUpdateBwContactLists(listConS, borrowerId);
//			}
//
//			try {
//				// 运营商 通话记录 公共表操作
//				addOrUpdateOperate(sessionId, orderId, operator, borrowerId);
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//
//			// 认证图片
//			logger.info(sessionId + ":51卡宝>>>pushOrder()方法>>>开始处理认证图片");
//			String frontFile = identifyInfo.getFrontFile(); // 身份证正面照片url
//			String backFile = identifyInfo.getBackFile(); // 身份证反面照片url
//			String natureFile = identifyInfo.getNatureFile(); // 生活照url/手持照
//			if (StringUtils.isNotBlank(frontFile)) {
//				String frontImage = UploadToCssUtils.urlUpload(frontFile, orderId + "_01"); // 身份证正面照
//				logger.info(sessionId + ">>>身份证正面 " + frontImage);
//				thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, orderId, borrowerId, 0); // 保存身份证正面照
//			}
//			if (StringUtils.isNotBlank(backFile)) {
//				String backImage = UploadToCssUtils.urlUpload(backFile, orderId + "_02"); // 身份证反面照
//				logger.info(sessionId + ">>>身份证反面 " + backImage);
//				thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, orderId, borrowerId, 0); // 保存身份证反面照
//			}
//			if (StringUtils.isNotBlank(natureFile)) {
//				String handerImage = UploadToCssUtils.urlUpload(natureFile, orderId + "_03"); // 手持照
//				logger.info(sessionId + ">>>手持照/人脸 " + handerImage);
//				thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, orderId, borrowerId, 0); // 保存手持照
//			}
//
//			// 保存身份证信息
//			logger.info(sessionId + ":51卡宝>>>pushOrder()方法>>>开始处理身份证信息");
//			BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
//			bwIdentityCard.setBorrowerId(borrowerId);
//			bwIdentityCard = bwIdentityCardServiceImpl.findBwIdentityCardByAttr(bwIdentityCard);
//			if (null == bwIdentityCard) {
//				bwIdentityCard = new BwIdentityCard2();
//				bwIdentityCard.setAddress(identifyInfo.getAddress());
//				bwIdentityCard.setIdCardNumber(basicInfo.getIdCard());
//				bwIdentityCard.setName(basicInfo.getName());
//				bwIdentityCard.setIssuedBy(identifyInfo.getIssuedBy()); // 身份证发证处
//				bwIdentityCard.setRace(identifyInfo.getNation()); // 名族
//				bwIdentityCard.setValidDate(identifyInfo.getValidDate());// 身份证有效期
//				bwIdentityCard.setBorrowerId(borrowerId);
//				bwIdentityCard.setCreateTime(new Date());
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCardServiceImpl.saveBwIdentityCard(bwIdentityCard);
//				logger.info(sessionId + ":保存身份证信息 ");
//			} else {
//				bwIdentityCard.setAddress(identifyInfo.getAddress());
//				bwIdentityCard.setIdCardNumber(basicInfo.getIdCard());
//				bwIdentityCard.setName(basicInfo.getName());
//				bwIdentityCard.setIssuedBy(identifyInfo.getIssuedBy());
//				bwIdentityCard.setRace(identifyInfo.getNation());
//				bwIdentityCard.setValidDate(identifyInfo.getValidDate());
//				bwIdentityCard.setUpdateTime(new Date());
//				bwIdentityCardServiceImpl.updateBwIdentityCard(bwIdentityCard);
//				logger.info(sessionId + ":更新身份证信息 ");
//			}
//			// 插入身份认证记录
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, channelId);
//
//			// 亲属联系人
//			logger.info(sessionId + ":51卡宝>>>pushOrder()方法>>>开始处理亲属联系人信息");
//			BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//			if (null == bwPersonInfo) {
//				bwPersonInfo = new BwPersonInfo();
//				bwPersonInfo.setCreateTime(new Date());
//				bwPersonInfo.setOrderId(orderId);
//				bwPersonInfo.setUpdateTime(new Date());
//				bwPersonInfo.setAddress(basicInfo.getHouseAddress());
//				bwPersonInfo.setRelationName(basicInfo.getFirstName());
//				bwPersonInfo.setRelationPhone(subPhone(basicInfo.getFirstPhone()));
//				bwPersonInfo.setUnrelationName(basicInfo.getSecondName());
//				bwPersonInfo.setUnrelationPhone(subPhone(basicInfo.getSecondPhone()));
//				bwPersonInfo.setMarryStatus(basicInfo.getMarriage()); // 婚姻状况
//																		// 0：未婚，1：已婚
//				bwPersonInfo.setEmail(basicInfo.getEmail());
//				bwPersonInfo.setHouseStatus(basicInfo.getHaveHouse());
//				bwPersonInfo.setCarStatus(basicInfo.getHaveCar());
//				bwPersonInfo.setQqchat(basicInfo.getQqchat());
//				bwPersonInfo.setWechat(basicInfo.getWechat());
//
//				// ======================================================
//				// bwPersonInfo.setColleagueName(basicInfo.getColleagueName());
//				// bwPersonInfo.setColleaguePhone(subPhone(basicInfo.getColleaguePhone()));
//				// bwPersonInfo.setFriend1Name(basicInfo.getFriend1Name());
//				// bwPersonInfo.setFriend1Phone(subPhone(basicInfo.getFriend1Phone()));
//				// bwPersonInfo.setFriend2Name(basicInfo.getFriend2Name());
//				// bwPersonInfo.setFriend2Phone(subPhone(basicInfo.getFriend2Phone()));
//				// ======================================================
//				bwPersonInfoService.add(bwPersonInfo);
//			} else {
//				bwPersonInfo.setAddress(basicInfo.getHouseAddress());
//				bwPersonInfo.setRelationName(basicInfo.getFirstName());
//				bwPersonInfo.setRelationPhone(subPhone(basicInfo.getFirstPhone()));
//				bwPersonInfo.setUnrelationName(basicInfo.getSecondName());
//				bwPersonInfo.setUnrelationPhone(subPhone(basicInfo.getSecondPhone()));
//				bwPersonInfo.setMarryStatus(basicInfo.getMarriage());
//				bwPersonInfo.setEmail(basicInfo.getEmail());
//				bwPersonInfo.setHouseStatus(basicInfo.getHaveHouse());
//				bwPersonInfo.setCarStatus(basicInfo.getHaveCar());
//				bwPersonInfo.setUpdateTime(new Date());
//				bwPersonInfo.setQqchat(basicInfo.getQqchat());
//				bwPersonInfo.setWechat(basicInfo.getWechat());
//
//				// ========================================================
//				// bwPersonInfo.setColleagueName(basicInfo.getColleagueName());
//				// bwPersonInfo.setColleaguePhone(subPhone(basicInfo.getColleaguePhone()));
//				// bwPersonInfo.setFriend1Name(basicInfo.getFriend1Name());
//				// bwPersonInfo.setFriend1Phone(subPhone(basicInfo.getFriend1Phone()));
//				// bwPersonInfo.setFriend2Name(basicInfo.getFriend2Name());
//				// bwPersonInfo.setFriend2Phone(subPhone(basicInfo.getFriend2Phone()));
//				// ========================================================
//				bwPersonInfoService.update(bwPersonInfo);
//			}
//			
//			// 异步处理运营商数据
//			asyncKaBaoTask.addOperator(sessionId, bwOrder, borrower, operator);
//
//			//运营商认证
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 1, channelId);
//			
//			bwOrder.setStatusId(2L); // 2初审
//			bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//			bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//			bwOrderService.updateBwOrder(bwOrder);
//
//			mapRes.put("status", "301");
//			mapRes.put("remark", "进件成功");
//
//			// 对响应参数AES加密
//			logger.info(sessionId + ":进件成功>>>response加密前数据:" + JSON.toJSONString(mapRes));
//			String response = AESUtil.Encrypt(JSON.toJSONString(mapRes), KaBaoConstant.AESKEY);
//			kaBaoResponse.setResponse(response);
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_SUCCESS);
//			kaBaoResponse.setMsg("进件成功！");
//		} catch (Exception e) {
//			logger.error(sessionId + ":51卡宝>>>进件推送接口Service异常", e);
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_FAILERR);
//			kaBaoResponse.setMsg("系统异常,请稍后再试");
//		} finally {
//			SxyThirdInterfaceLogUtils.setSxyLog(KaBaoConstant.CHANNELID, thirdOrderNo, kaBaoResponse.getCode(),mapRes.get("remark")+"", "三方工单号");
//		}
//		return kaBaoResponse;
//	}
//
//	/**
//	 * TODO 4.1.2 银行卡预绑卡接口（API）
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.KaBaoService#saveBindCardSure(long, java.lang.String,
//	 *      java.lang.String)
//	 */
//	public KaBaoResponse saveBindCardReady(long sessionId, String appId, String request) {
//		logger.info(sessionId + ":51卡宝>>>预绑卡接口>>>进入saveBindCardReady()方法");
//		KaBaoResponse kaBaoResponse = new KaBaoResponse();
//		KaBaoBindCard kaBaoBindCard = null;
//		try {
//			// AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("渠道不存在");
//				return kaBaoResponse;
//			}
//
//			boolean isCorrect = checkChannelId(orderChannel, KaBaoConstant.CHANNELID);
//			if (!isCorrect) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("渠道错误!");
//				return kaBaoResponse;
//			}
//
//			String requestJson = AESUtil.Decrypt(request, KaBaoConstant.AESKEY);
//			logger.info(sessionId + ":51卡宝>>>预绑卡接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":51卡宝>>>预绑卡接口requestJson为空>>>");
//				return kaBaoResponse;
//			}
//			// 获取绑卡基本参数
//			kaBaoBindCard = JSON.parseObject(requestJson, KaBaoBindCard.class);
//			if (null == kaBaoBindCard) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":51卡宝>>>预绑卡接口request为空>>>");
//				return kaBaoResponse;
//			}
//
//			String idCard = kaBaoBindCard.getIdCard();
//			// 对身份证进行校验
//			boolean validate = IdcardValidator.isValidatedAllIdcard(idCard);
//			if (!validate) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("身份证不合法");
//				logger.info(sessionId + ":51卡宝>>>结束pushOrder方法，返回结果:身份证为" + JSON.toJSONString(idCard));
//				return kaBaoResponse;
//			}
//
//			Integer channelId = orderChannel.getId();
//			logger.info(sessionId + ":51卡宝>>>开始预绑卡saveBindCard_NewReady()方法channelId:" + channelId);
//			DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//			drainageBindCardVO.setIdCardNo(kaBaoBindCard.getIdCard()); // 身份证
//			drainageBindCardVO.setName(kaBaoBindCard.getName()); // 持卡人姓名
//			drainageBindCardVO.setBankCardNo(kaBaoBindCard.getBankCardNo()); // 银行卡
//			drainageBindCardVO.setBankCode(kaBaoBindCard.getBankCode()); // 银行编码
//			drainageBindCardVO.setPhone(kaBaoBindCard.getPhone());
//			drainageBindCardVO.setRegPhone(kaBaoBindCard.getBankPhone()); // 银行预留手机
//			drainageBindCardVO.setBindType("1"); // 绑卡类型，1 前置 其他为后置 // 前置绑卡 传phone 后置绑卡 传 thirdOrderNo
//			drainageBindCardVO.setChannelId(channelId); // 渠道号
//			drainageBindCardVO.setNotify(true); // 是否需要通知
//
//			DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(sessionId, drainageBindCardVO); // 新的预绑卡
//			if (null != drainageRsp) {
//				if (DrainageEnum.CODE_SUCCESS.getCode().equals(drainageRsp.getCode())) {
//					kaBaoResponse.setCode(KaBaoResponse.RESULT_SUCCESS);
//					kaBaoResponse.setMsg("预绑卡申请成功");
//					logger.info(sessionId + ":51卡宝>>>预绑卡申请成功");
//				} else {
//					kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//					kaBaoResponse.setMsg(drainageRsp.getMessage());
//					logger.info(sessionId + ":51卡宝>>>预绑卡申请失败");
//				}
//			} else {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("预绑卡申请失败");
//				logger.info(sessionId + ":51卡宝>>>预绑卡申请失败drainageRsp为空");
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ":51卡宝>>>银行预绑卡接口Service异常", e);
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_FAILERR);
//			kaBaoResponse.setMsg("系统异常,请稍后再试");
//		} finally {
//			SxyThirdInterfaceLogUtils.setSxyLog(KaBaoConstant.CHANNELID, kaBaoBindCard.getPhone(),
//					kaBaoResponse.getCode(), kaBaoResponse.getMsg(), "注册手机号");
//		}
//		return kaBaoResponse;
//	}
//
//	/**
//	 * 
//	 * TODO 4.1.3 银行卡确认绑卡接口（API）
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.KaBaoService#saveBindCardSure(long, java.lang.String,
//	 *      java.lang.String)
//	 */
//	@Override
//	public KaBaoResponse saveBindCardSure(long sessionId, String appId, String request) {
//		logger.info(sessionId + ":51卡宝>>>确认绑卡接口>>>进入saveBindCardSure()方法");
//		KaBaoResponse kaBaoResponse = new KaBaoResponse();
//		KaBaoBindCard kaBaoBindCard = null;
//		try {
//			// AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":51卡宝>>>确认绑卡接口orderChannel为空>>>");
//				return kaBaoResponse;
//			}
//
//			boolean isCorrect = checkChannelId(orderChannel, KaBaoConstant.CHANNELID);
//			if (!isCorrect) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("渠道错误!");
//				return kaBaoResponse;
//			}
//
//			String requestJson = AESUtil.Decrypt(request, KaBaoConstant.AESKEY);
//			logger.info(sessionId + ":51卡宝>>>确认绑卡接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":51卡宝>>>确认绑卡接口requestJson为空>>>");
//				return kaBaoResponse;
//			}
//			// 获取绑卡基本参数
//			kaBaoBindCard = JSON.parseObject(requestJson, KaBaoBindCard.class);
//			if (null == kaBaoBindCard) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":51卡宝>>>确认绑卡接口kaBaoBindCard为空>>>");
//				return kaBaoResponse;
//			}
//
//			Integer channelId = orderChannel.getId();
//			logger.info(sessionId + ":51卡宝>>>开始确认绑卡saveBindCard_NewReady()方法,phone:" + kaBaoBindCard.getPhone());
//			DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//			drainageBindCardVO.setBindType("1"); // 绑卡类型，1 前置 其他为后置 // 前置绑卡 传 phone 后置绑卡 传 thirdOrderNo
//			drainageBindCardVO.setChannelId(channelId);
//			drainageBindCardVO.setPhone(kaBaoBindCard.getPhone());
//			drainageBindCardVO.setNotify(false); // 不需要通知
//			drainageBindCardVO.setVerifyCode(kaBaoBindCard.getVerifyCode()); // 验证码
//
//			DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(sessionId, drainageBindCardVO); // 新的绑卡
//
//			// 绑卡通知
//			Map<String, Object> map = new HashMap<>();
//			map.put("channelId", channelId);
//			map.put("userPhone", kaBaoBindCard.getPhone());
//			map.put("result", "绑卡成功");
//			String json = JSON.toJSONString(map);
//			RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
//
//			if (null != drainageRsp) {
//				if (DrainageEnum.CODE_SUCCESS.getCode().equals(drainageRsp.getCode())) {
//					kaBaoResponse.setCode(KaBaoResponse.RESULT_SUCCESS);
//					kaBaoResponse.setMsg("确认绑卡成功");
//					logger.info(sessionId + ":51卡宝>>>确认绑卡成功");
//				} else {
//					kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//					kaBaoResponse.setMsg(drainageRsp.getMessage());
//					logger.info(sessionId + ":51卡宝>>>确认绑卡失败");
//				}
//			} else {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("确认绑卡失败");
//				logger.info(sessionId + ":51卡宝>>>确认绑卡失败drainageRsp为空");
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ":51卡宝>>>银行确认绑卡接口Service异常", e);
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_FAILERR);
//			kaBaoResponse.setMsg("系统异常,请稍后再试");
//		} finally {
//			SxyThirdInterfaceLogUtils.setSxyLog(KaBaoConstant.CHANNELID, kaBaoBindCard.getPhone(),
//					kaBaoResponse.getCode(), kaBaoResponse.getMsg(), "注册手机号");
//		}
//		return kaBaoResponse;
//	}
//
//	/**
//	 * TODO 4.1.6 主动还款接口
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.KaBaoService#updateActiveRepayment(long, java.lang.String,
//	 *      java.lang.String)
//	 */
//	@Override
//	public KaBaoResponse updateActiveRepayment(long sessionId, String appId, String request) {
//		logger.info(sessionId + ":51卡宝>>>主动还款接口>>>进入updateActiveRepayment()方法");
//		KaBaoResponse kaBaoResponse = new KaBaoResponse();
//		String thirdOrderNo = null;
//		try {
//			// AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":51卡宝>>>主动还款接口orderChannel为空>>>");
//				return kaBaoResponse;
//			}
//
//			boolean isCorrect = checkChannelId(orderChannel, KaBaoConstant.CHANNELID);
//			if (!isCorrect) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("渠道错误!");
//				return kaBaoResponse;
//			}
//
//			String requestJson = AESUtil.Decrypt(request, KaBaoConstant.AESKEY);
//			logger.info(sessionId + ":51卡宝>>>主动还款接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":51卡宝>>>主动还款接口requestJson为空>>>");
//				return kaBaoResponse;
//			}
//			// 获取基本参数
//			JSONObject parseObject = JSON.parseObject(requestJson);
//			if (null != parseObject) {
//				thirdOrderNo = parseObject.getString("thirdOrderNo");
//			}
//			if (null == thirdOrderNo) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":51卡宝>>>主动还款接口thirdOrderNo为空>>>" + JSON.toJSONString(kaBaoResponse));
//				return kaBaoResponse;
//			}
//
//			logger.info(sessionId + ":51卡宝>>>开始主动还款updateRepayment()方法>>>");
//			DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, thirdOrderNo); // 新的支付
//			Map<String, Object> mapRes = new HashMap<String, Object>();
//
//			if (null != drainageRsp) {
//				if ("000".equals(drainageRsp.getCode())) {
//					kaBaoResponse.setCode(KaBaoResponse.RESULT_SUCCESS);
//					kaBaoResponse.setMsg("还款申请成功");
//					mapRes.put("status", "601");
//					mapRes.put("remark", "还款申请成功");
//					logger.info(sessionId + ":51卡宝>>>还款申请成功,thirdOrderNo:" + thirdOrderNo);
//
//				} else if ("106".equals(drainageRsp.getCode())) {
//					kaBaoResponse.setCode(KaBaoResponse.RESULT_SIGNERR);
//					kaBaoResponse.setMsg("正在交易中");
//					mapRes.put("status", "606");
//					mapRes.put("remark", drainageRsp.getMessage());
//					logger.info(sessionId + ":51卡宝>>>主动还款正在交易中,thirdOrderNo:" + thirdOrderNo);
//				} else {
//					kaBaoResponse.setCode(KaBaoResponse.RESULT_FAILERR);
//					kaBaoResponse.setMsg("还款申请失败");
//					mapRes.put("status", "603");
//					mapRes.put("remark", drainageRsp.getMessage());
//					logger.info(sessionId + ":51卡宝>>>主动还款失败,thirdOrderNo:" + thirdOrderNo);
//				}
//
//				String response = AESUtil.Encrypt(JSON.toJSONString(mapRes), KaBaoConstant.AESKEY);
//				kaBaoResponse.setResponse(response);
//			} else {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("操作失败");
//				logger.info(sessionId + ":51卡宝>>>主动还款失败drainageRsp为空,thirdOrderNo:" + thirdOrderNo);
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ":51卡宝>>>主动还款接口Service异常", e);
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_FAILERR);
//			kaBaoResponse.setMsg("系统异常,请稍后再试");
//		} finally {
//			SxyThirdInterfaceLogUtils.setSxyLog(KaBaoConstant.CHANNELID, thirdOrderNo, kaBaoResponse.getCode(),
//					kaBaoResponse.getMsg());
//		}
//		return kaBaoResponse;
//	}
//
//	/**
//	 * TODO 拉取订单状态接口（可选）.
//	 * 
//	 * @see com.waterelephant.sxyDrainage.service.KaBaoService#getOrderStatus(long, java.lang.String, java.lang.String)
//	 */
//	@Override
//	public KaBaoResponse getOrderStatus(long sessionId, String appId, String request) {
//		logger.info(sessionId + ":51卡宝>>>拉取订单状态接口>>>进入getOrderStatus()方法");
//		KaBaoResponse kaBaoResponse = new KaBaoResponse();
//		try {
//			// AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":51卡宝>>>拉取订单状态接口orderChannel为空>>>");
//				return kaBaoResponse;
//			}
//
//			boolean isCorrect = checkChannelId(orderChannel, KaBaoConstant.CHANNELID);
//			if (!isCorrect) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("渠道错误!");
//				return kaBaoResponse;
//			}
//
//			String requestJson = AESUtil.Decrypt(request, KaBaoConstant.AESKEY);
//			logger.info(sessionId + ":51卡宝>>>拉取订单状态接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":51卡宝>>>拉取订单状态接口requestJson为空>>>");
//				return kaBaoResponse;
//			}
//			// 获取基本参数
//			String thirdOrderNo = null;
//			JSONObject parseObject = JSON.parseObject(requestJson);
//			if (null != parseObject) {
//				thirdOrderNo = parseObject.getString("orderNo");
//			}
//			if (StringUtils.isBlank(thirdOrderNo)) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":51卡宝>>>拉取订单状态接口thirdOrderNo为空>>>");
//				return kaBaoResponse;
//			}
//
//			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//			if (CommUtils.isNull(bwOrderRong)) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("三方订单为空");
//				logger.info(sessionId + ":51卡宝>>>拉取订单状态接口bwOrderRong为空>>>thirdOrderNo:" + thirdOrderNo);
//				return kaBaoResponse;
//			}
//
//			Long orderId = bwOrderRong.getOrderId();
//			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
//			if (null == bwOrder) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("订单不存在");
//				logger.info(sessionId + ":51卡宝>>>拉取订单状态接口bwOrder为空>>>thirdOrderNo:" + thirdOrderNo);
//				return kaBaoResponse;
//			}
//			// ==============================================================
//			// 1 查询借款人
//			BwBorrower borrower = new BwBorrower();
//			borrower.setId(bwOrder.getBorrowerId());
//			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			if (null == borrower) {
//				kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//				kaBaoResponse.setMsg("用户不存在");
//				logger.info(sessionId + ":51卡宝>>>拉取订单状态接口borrower为空>>>thirdOrderNo:" + thirdOrderNo);
//				return kaBaoResponse;
//			}
//
//			Map<String, Object> map = new HashMap<String, Object>();
//			String statusIdStr = String.valueOf(bwOrder.getStatusId());
//			// 查询还款计划
//			if (Objects.equals(statusIdStr, "6") || Objects.equals(statusIdStr, "9")
//					|| Objects.equals(statusIdStr, "13")) {
//				Example example = new Example(BwRepaymentPlan.class);
//				example.createCriteria().andEqualTo("orderId", orderId);
//				example.setOrderByClause("id  asc");
//				List<BwRepaymentPlan> planList = bwRepaymentPlanService.findRepaymentPlanByExample(example);
//				if (CollectionUtils.isEmpty(planList)) {
//					kaBaoResponse.setCode(KaBaoResponse.RESULT_PARMERR);
//					kaBaoResponse.setMsg("还款计划不存在");
//					logger.info(sessionId + ":51卡宝>>>拉取订单状态接口planList不存在>>>thirdOrderNo:" + thirdOrderNo);
//					return kaBaoResponse;
//				}
//
//				// 2 封装还款计划
//				logger.info(sessionId + ":51卡宝>>>getOrderStatus()>>>开始封装还款计划>>>");
//				List<Map<String, Object>> plans = new ArrayList<Map<String, Object>>();
//				boolean isProcessing = bwRepaymentService.isProcessing(orderId);
//				logger.info("订单交易状态：isProcessing--->" + isProcessing);
//				boolean flag = false;
//				// 得到每一期的还款计划 并封装
//				for (BwRepaymentPlan plan : planList) {
//					Map<String, Object> planMap = new HashMap<String, Object>();
//					planMap.put("number", plan.getNumber()); // 期数
//					planMap.put("principal", new Double(plan.getRepayCorpusMoney() * 100).intValue()); // 还款本金
//					planMap.put("interest", new Double(plan.getRepayAccrualMoney() * 100).intValue()); // 利息
//					planMap.put("repay_money", new Double(plan.getRepayMoney() * 100).intValue()); // 应还金额
//					planMap.put("already_paid", new Double(plan.getAlreadyRepayMoney() * 100).intValue()); // 已还金额
//
//					if (plan.getRepayStatus() == 2) {
//						// 已还款
//						planMap.put("state", "601");
//						planMap.put("realRepay_date",
//								DateUtil.getDateString(plan.getUpdateTime(), DateUtil.yyyy_MM_dd_HHmmss)); // 实际还款日期
//					} else {
//						// 未还款
//						if (isProcessing && !flag) {
//							planMap.put("state", "606");
//							flag = true;
//						} else {
//							planMap.put("state", "602");
//						}
//					}
//					planMap.put("repay_date", DateUtil.getDateString(plan.getRepayTime(), DateUtil.yyyy_MM_dd_HHmmss)); // 还款日期
//					if (null != plan.getRepayType() && plan.getRepayType() == 2) { // 逾期
//						// 计算逾期
//						BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//						bwOverdueRecord.setRepayId(plan.getId());
//						bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//						if (null != bwOverdueRecord) {
//							Double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0.0D
//									: bwOverdueRecord.getOverdueAccrualMoney();
//							Double advance = bwOverdueRecord.getAdvance();
//							Double overdueFee = DoubleUtil.sub(overdueAccrualMoney, advance);
//							planMap.put("overdue_days", bwOverdueRecord.getOverdueDay());
//							planMap.put("overdue_fee", new Double(overdueFee * 100).intValue());
//							if (plan.getRepayStatus() != null && plan.getRepayStatus() == 1) { // 逾期并且未还款
//								planMap.put("repay_money", new Double(plan.getRepayMoney() * 100).intValue()
//										+ new Double(overdueFee * 100).intValue()); // 应还金额
//								planMap.put("state", "604"); // 逾期
//								planMap.put("remark", "逾期未还款");
//							}
//							if (plan.getRepayStatus() != null && plan.getRepayStatus() == 2) { // 逾期并且已还款
//								planMap.put("repay_money", new Double(plan.getRepayMoney() * 100).intValue()); // 应还金额
//								planMap.put("state", "601"); // 还款成功
//								planMap.put("remark", "逾期已还款");
//							}
//						}
//					} else {
//						// 没有逾期
//						planMap.put("overdue_days", 0); // 逾期天数
//						planMap.put("overdue_fee", 0); // 逾期费用
//					}
//					// 还款计划添加到集合
//					plans.add(planMap);
//				}
//				// 封装还款计划
//				map.put("repayPlans", plans);
//				logger.info(sessionId + ":51卡宝>>>getOrderStatus()>>>结束封装还款计划>>>");
//			}
//
//			// 3 是否绑卡,对于已绑卡用户要回传银行名、卡号、预留手机号
//			BwBankCard bwBankCard = new BwBankCard();
//			bwBankCard.setBorrowerId(borrower.getId());
//			bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
//			if (bwBankCard != null && bwBankCard.getSignStatus() > 0) {
//				Map<String, Object> bankCardMap = new HashMap<>();
//				bankCardMap.put("bank", bwBankCard.getBankName()); // 已绑银行
//				bankCardMap.put("bankCardNum", bwBankCard.getCardNo()); // 已绑银行卡号
//				bankCardMap.put("bankPhone", bwBankCard.getPhone()); // 银行预留手机号
//				// 封装银行卡信息
//				map.put("bankCardInfo", bankCardMap);
//			}
//
//			// 封装订单状态
//			map.put("orderNo", thirdOrderNo); // 订单唯一编号
//			String convertOrderStatus = KaBaoUtils.convertOrderStatus(Long.valueOf(statusIdStr));
//			map.put("status", null == convertOrderStatus ? "" : convertOrderStatus);
//			if (Objects.equals(statusIdStr, "7")) {
//				BwRejectRecord record = bwRejectRecordService.findBwRejectRecordByBid(borrower.getId());
//				if (!CommUtils.isNull(record)) {
//					Date rejectTime = record.getCreateTime();
//					String reject_date = DateUtil.getDateString(rejectTime, DateUtil.yyyy_MM_dd_HHmmss);
//					map.put("reject_date", reject_date); // 审核失败时间
//				}
//			}
//
//			Integer orderStat = Integer.valueOf(statusIdStr);
//			if (orderStat >= 4 && orderStat != 7 && orderStat != 8) {
//				map.put("approve_amount", bwOrder.getBorrowAmount() * 100); // 审批金额,单位：分
//				String approve_date = DateUtil.getDateString(bwOrder.getSubmitTime(), DateUtil.yyyy_MM_dd_HHmmss);
//				map.put("approve_date", approve_date); // 审核时间
//			}
//			// ================================================================================================
//			// 对响应参数AES加密
//			logger.info(sessionId + ":51卡宝>>>拉取订单状态接口>>>response加密前数据:" + JSON.toJSONString(map));
//			String response = AESUtil.Encrypt(JSON.toJSONString(map), KaBaoConstant.AESKEY);
//			kaBaoResponse.setResponse(response);
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_SUCCESS);
//			kaBaoResponse.setMsg("拉取订单状态成功");
//		} catch (Exception e) {
//			logger.error(sessionId + ":51卡宝>>>执行拉取订单状态接口Service异常", e);
//			kaBaoResponse.setCode(KaBaoResponse.RESULT_FAILERR);
//			kaBaoResponse.setMsg("系统异常,请稍后再试");
//		}
//		return kaBaoResponse;
//	}
//
//	/**
//	 * 添加基本的运营商数据 通话记录
//	 * 
//	 * @throws Exception
//	 */
//	private void addOrUpdateOperate(long sessionId, long orderId, KaBaoOperator operator, long borrowerId)
//			throws Exception {
//
//		Transportation transportation = operator.getData().getTransportation().get(0);
//		Origin origin = transportation.getOrigin(); // 运营商原始数据
//		DataBaseInfo dataBaseInfo = transportation.getOrigin().getBaseInfo().getData(); // 运营商基本数据
//		JSONObject result = transportation.getReport().getJSONObject("result").getJSONObject("operator_protype_data");
//
//		if (null != dataBaseInfo) {
//			// 基本的运营商数据
//			BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
//			if (bwOperateBasic == null) {
//				logger.info("51卡宝处理运营商基础信息>>>新增运营商基础信息>>>orderId:" + orderId);
//				bwOperateBasic = new BwOperateBasic();
//				bwOperateBasic.setBorrowerId(borrowerId);
//				bwOperateBasic.setUserSource(origin.getType());
//				bwOperateBasic.setIdCard(dataBaseInfo.getIdCard());
//				bwOperateBasic.setAddr(dataBaseInfo.getAddress());
//				bwOperateBasic.setRealName(dataBaseInfo.getName());
//				bwOperateBasic.setPhone(origin.getId());
//				Date regDate = result.getDate("register_time"); // 注册时间
//				bwOperateBasic.setRegTime(regDate);
//				bwOperateBasic.setPhoneStatus(Objects.equals(dataBaseInfo.getStatus(), "00") ? "1" : "");// 0,欠费;1,正常
//				if (Objects.equals(dataBaseInfo.getRealnameInfo(), "已审核")
//						|| Objects.equals(dataBaseInfo.getRealnameInfo(), "已登记")) {
//					bwOperateBasic.setAuthentication("1");
//				}
//				bwOperateBasic.setPackageName(dataBaseInfo.getBrand());
//				bwOperateBasic.setStarLevel(dataBaseInfo.getStarLevel());
//				bwOperateBasic.setCreateTime(new Date());
//				bwOperateBasic.setUpdateTime(new Date());
//				bwOperateBasicService.save(bwOperateBasic);
//			} else {
//				logger.info("51卡宝处理运营商基础信息>>>更新运营商基础信息>>>orderId:" + orderId);
//				bwOperateBasic.setBorrowerId(borrowerId);
//				bwOperateBasic.setUserSource(origin.getType());
//				bwOperateBasic.setIdCard(dataBaseInfo.getIdCard());
//				bwOperateBasic.setAddr(dataBaseInfo.getAddress());
//				bwOperateBasic.setRealName(dataBaseInfo.getName());
//				bwOperateBasic.setPhone(origin.getId());
//				Date regDate = result.getDate("register_time"); // 注册时间
//				bwOperateBasic.setRegTime(regDate);
//				bwOperateBasic.setPhoneStatus(Objects.equals(dataBaseInfo.getStatus(), "00") ? "1" : "");// 0,欠费;1,正常
//				if (Objects.equals(dataBaseInfo.getRealnameInfo(), "已审核")
//						|| Objects.equals(dataBaseInfo.getRealnameInfo(), "已登记")) {
//					bwOperateBasic.setAuthentication("1");
//				}
//				bwOperateBasic.setPackageName(dataBaseInfo.getBrand());
//				bwOperateBasic.setStarLevel(dataBaseInfo.getStarLevel());
//				bwOperateBasic.setCreateTime(new Date());
//				bwOperateBasic.setUpdateTime(new Date());
//				bwOperateBasicService.update(bwOperateBasic);
//			}
//			logger.info(sessionId + ":51卡宝>>>结束处理运营商基础信息>>>orderId:" + orderId);
//		}
//
//		/**
//		 * 处理通话记录
//		 */
//		if (Objects.equals(origin.getCallInfo().getCode(), "E000000")) { // 有通话记录
//			List<DataCallInfo> callInfoList = origin.getCallInfo().getData(); // 通话详情
//			if (CollectionUtils.isNotEmpty(callInfoList)) {
//				Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
//				BwOperateVoice bwOperateVoice = null;
//				SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//				for (DataCallInfo dataCallInfo : callInfoList) {
//					List<CallInfoDetails> details = dataCallInfo.getDetails(); // 通话详细
//					if (CollectionUtils.isNotEmpty(details)) {
//						for (CallInfoDetails callInfoDetails : details) {
//							if (StringUtils.isNotBlank(callInfoDetails.getAnother_nm())
//									&& callInfoDetails.getAnother_nm().length() > 20) {
//								logger.info("51卡宝>>>通话记录中，对方号码过长，略过次条记录...............ReceivePhone = "
//										+ callInfoDetails.getAnother_nm());
//								continue;
//							}
//							try {
//								bwOperateVoice = new BwOperateVoice();
//								Date start_time = sdf.parse(callInfoDetails.getStart_time());
//								if (callDate == null || start_time.after(callDate)) {
//									if (StringUtils.isNotBlank(callInfoDetails.getComm_type())) {
//										bwOperateVoice
//												.setTrade_type("本地通话".equals(callInfoDetails.getComm_type()) ? 1 : 2); // 通话类型，本地通话，国内漫游通话
//									}
//									bwOperateVoice.setCall_type("主叫".equals(callInfoDetails.getComm_mode()) ? 1 : 2); // 通话模式
//																														// 主叫，被叫
//
//									long trade_time = ConvertSecondUtil.getSecond(callInfoDetails.getComm_time());
//									bwOperateVoice.setTrade_time(String.valueOf(trade_time)); // 通话时长
//									bwOperateVoice.setReceive_phone(callInfoDetails.getAnother_nm()); // 对方号码
//									bwOperateVoice.setTrade_addr(callInfoDetails.getComm_plac()); // 通话地
//									bwOperateVoice.setCall_time(callInfoDetails.getStart_time()); // 通话开始时间
//									bwOperateVoice.setUpdateTime(new Date());
//									bwOperateVoice.setBorrower_id(borrowerId);
//									bwOperateVoiceService.save(bwOperateVoice);
//								}
//							} catch (Exception e) {
//								logger.error(sessionId + ":51卡宝>>>保存通话记录异常,忽略此条通话记录...", e);
//							}
//						}
//					}
//				}
//			}
//		}
//	}
//
//	private boolean checkChannelId(BwOrderChannel channel, String equalsChannelId) {
//		return Objects.equals(String.valueOf(channel.getId()), equalsChannelId) ? true : false;
//	}
//
//	/**
//	 * 截取手机号码
//	 * 
//	 * @param phone
//	 * @return
//	 */
//	private String subPhone(String phone) {
//		String sPhone = null;
//		if (StringUtils.isBlank(phone)) {
//			return sPhone;
//		}
//		if (phone.contains(",")) {
//			sPhone = phone.substring(0, phone.indexOf(","));
//		} else {
//			sPhone = phone;
//		}
//		if(StringUtils.isNotBlank(sPhone)){
//			sPhone = sPhone.replace(" ", "").replace(" ", "");
//		}
//		return sPhone;
//	}
//	
//	/**
//	 * 判断是否有被拒记录
//	 * 
//	 * @param record 
//	 * @return
//	 */
//	public Boolean isRejected(BwRejectRecord record){
//		if(!CommUtils.isNull(record) && !CommUtils.isNull(record.getRejectType())){
//			// 永久拒绝
//			if ("0".equals(record.getRejectType()+"")) {
//				return true;
//			} else {
//				// 判断临时拒绝是否到期
//				Date rejectDate = record.getCreateTime();
//				long day = (Calendar.getInstance().getTime().getTime() - rejectDate.getTime()) / (24 * 60 * 60 * 1000);
//				if (day <= 7) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//}
