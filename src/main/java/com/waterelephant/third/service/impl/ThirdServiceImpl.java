package com.waterelephant.third.service.impl;

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
import com.waterelephant.dto.SystemAuditDto;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwContactList;
import com.waterelephant.entity.BwIdentityCard2;
import com.waterelephant.entity.BwOperateBasic;
import com.waterelephant.entity.BwOperateVoice;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderChannel;
import com.waterelephant.entity.BwOrderProcessRecord;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.entity.BwZmxyGrade;
import com.waterelephant.service.BwOperateBasicService;
import com.waterelephant.service.BwOperateVoiceService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.BwZmxyGradeService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwIdentityCardService;
import com.waterelephant.service.IBwOrderChannelService;
import com.waterelephant.service.IBwPersonInfoService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.IBwWorkInfoService;
import com.waterelephant.service.impl.BwContactListService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.third.entity.ThirdRequest;
import com.waterelephant.third.entity.ThirdResponse;
import com.waterelephant.third.entity.request.BasicInfo;
import com.waterelephant.third.entity.request.CallRecord;
import com.waterelephant.third.entity.request.CompanyInfo;
import com.waterelephant.third.entity.request.Contact;
import com.waterelephant.third.entity.request.IdentifyInfo;
import com.waterelephant.third.entity.request.Operator;
import com.waterelephant.third.entity.request.RequestApproveConfirm;
import com.waterelephant.third.entity.request.RequestBankCardInfo;
import com.waterelephant.third.entity.request.RequestCheckUser;
import com.waterelephant.third.entity.request.RequestDefer;
import com.waterelephant.third.entity.request.RequestDeferInfo;
import com.waterelephant.third.entity.request.RequestPullOrderStatus;
import com.waterelephant.third.entity.request.RequestPullRepaymentPlan;
import com.waterelephant.third.entity.request.RequestPush;
import com.waterelephant.third.entity.request.RequestRepayInfo;
import com.waterelephant.third.entity.request.RequestRepayment;
import com.waterelephant.third.entity.request.ResquestContract;
import com.waterelephant.third.entity.response.DeferAmountOption;
import com.waterelephant.third.entity.response.DeferOption;
import com.waterelephant.third.entity.response.RepaymentPlan;
import com.waterelephant.third.entity.response.ResponseCheckUser;
import com.waterelephant.third.entity.response.ResponseContract;
import com.waterelephant.third.entity.response.ResponseDeferInfo;
import com.waterelephant.third.entity.response.ResponsePullOrderStatus;
import com.waterelephant.third.entity.response.ResponsePullRepaymentPlan;
import com.waterelephant.third.entity.response.ResponsePush;
import com.waterelephant.third.entity.response.ResponseRepayInfo;
import com.waterelephant.third.service.CheckUserService;
import com.waterelephant.third.service.ThirdCommonService;
import com.waterelephant.third.service.ThirdService;
import com.waterelephant.third.utils.ThirdConstant;
import com.waterelephant.third.utils.ThirdUtil;
import com.waterelephant.utils.AESUtil;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.JsonUtils;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.OrderUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;
import com.waterelephant.utils.UploadToCssUtils;

/**
 * 统一对外接口（code0091）
 * 
 * 
 * Module:
 * 
 * ThirdServiceImpl.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class ThirdServiceImpl implements ThirdService {
	private Logger logger = LoggerFactory.getLogger(ThirdServiceImpl.class);
	private static String AES_KEY = "third.key.channel_";

	@Autowired
	private CheckUserService checkUserServiceImpl;
	@Autowired
	private IBwOrderChannelService bwOrderChannelService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private ThirdCommonService thirdCommonService;
	@Autowired
	private BwOrderService bwOrderService;
	@Autowired
	private IBwWorkInfoService bwWorkInfoService;
	@Autowired
	private BwOrderProcessRecordService bwOrderProcessRecordService;
	@Autowired
	private BwContactListService bwContactListService;
	@Autowired
	private BwOperateBasicService bwOperateBasicService;
	@Autowired
	private BwOperateVoiceService bwOperateVoiceService;
	@Autowired
	private BwZmxyGradeService bwZmxyGradeService;
	@Autowired
	private IBwPersonInfoService bwPersonInfoService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private IBwIdentityCardService bwIdentityCardServiceImpl;
	@Autowired
	private IBwAdjunctService bwAdjunctService;
	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;

	/**
	 * 统一对外接口 - 检查用户（code0091）
	 * 
	 * @see com.waterelephant.third.service.ThirdService#checkUser(com.waterelephant.third.entity.ThirdRequest)
	 */
	@Override
	public ThirdResponse checkUser(long sessionId, ThirdRequest thirdRequest) {
		ThirdResponse thirdResponse = new ThirdResponse();
		logger.info(sessionId + "：开始service层检查用户接口");
		logger.info(sessionId + "检查用户接口" + JSON.toJSONString(thirdRequest));
		try {
			ResponseCheckUser responseCheckUser = new ResponseCheckUser();

			String requests = thirdRequest.getRequest();
			if (requests == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空");
				logger.info(sessionId + "结束service层检查用户接口" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// AES解密请求参数
			String channelCode = thirdRequest.getAppId();
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(channelCode);
			String AESKey = ThirdConstant.THIRD_CONFIG.getString(AES_KEY + orderChannel.getId());
			String AESRequest = AESUtil.Decrypt(requests, AESKey);

			RequestCheckUser requestCheckUser = JSON.parseObject(AESRequest, RequestCheckUser.class);
			String name = requestCheckUser.getName();
			String idCard = requestCheckUser.getIdCard();
			String mobile = requestCheckUser.getMobile();
			if (StringUtils.isEmpty(name)) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("name is null");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			if (StringUtils.isEmpty(idCard)) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("idCard is null");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			if (StringUtils.isEmpty(mobile)) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("mobile is null");
				logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			idCard = idCard.replace("*", "%");
			mobile = mobile.replace("*", "%");
			// 第一步：是否是老用户
			boolean isOldUser = checkUserServiceImpl.isOldUser(idCard, name, mobile);
			responseCheckUser.setIsStock(isOldUser == true ? 1 : 0); // 存量用户（0：否，1：是）

			// 是否绑卡
			BwBorrower bwBorrower = bwBorrowerService.oldUserFilter2(name, mobile, idCard);
			BwBankCard bwBankCard = new BwBankCard();
			if (!(bwBorrower == null)) {
				bwBankCard.setBorrowerId(bwBorrower.getId());
				bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
				// 对于已绑卡用户要回传银行名、卡号、预留手机号
				if (bwBankCard != null && (bwBankCard.getSignStatus() == 1 || bwBankCard.getSignStatus() == 2)) {
					responseCheckUser.setBank(bwBankCard.getBankName());
					responseCheckUser.setBankCardNum(bwBankCard.getCardNo());
					responseCheckUser.setPhone(bwBankCard.getPhone());
				}
			}

			// 第二步：是否黑名单
			boolean isBlackUser = checkUserServiceImpl.isBlackUser(idCard, name, mobile);
			if (isBlackUser == true) {
				responseCheckUser.setIsCanLoan(0); // 是否可以借款（0：否，1：是）
				responseCheckUser.setIsBlackList(1); // 是否命中黑名单（0：否，1：是）
				responseCheckUser.setRejectReason(1); // 拒绝原因（1 黑名单，2 在贷，3 其他）

				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("命中黑名单规则");
				String AESResponseCheckUser = AESUtil.Encrypt(JSON.toJSONString(responseCheckUser), AESKey);
				thirdResponse.setResponse(AESResponseCheckUser);
				logger.info(sessionId + "结束ThirdServiceImpl.checkUser()方法，返回结果：" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			// 第三步：是否进行中的订单
			boolean isProcessingOrder = checkUserServiceImpl.isPocessingOrder(idCard, name, mobile);
			if (isProcessingOrder == true) {
				responseCheckUser.setIsCanLoan(0); // 是否可以借款（0：否，1：是）
				responseCheckUser.setIsBlackList(0); // 是否命中黑名单（0：否，1：是）
				responseCheckUser.setRejectReason(2); // 拒绝原因（1 黑名单，2 在贷，3 其他）

				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("命中在贷规则");
				String AESResponseCheckUser = AESUtil.Encrypt(JSON.toJSONString(responseCheckUser), AESKey);
				thirdResponse.setResponse(AESResponseCheckUser);
				logger.info(sessionId + "结束ThirdServiceImpl.userCheck()方法，返回结果：" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			// 第四步：是否有被拒记录
			boolean isRejectRecord = checkUserServiceImpl.isRejectRecord(idCard, name, mobile);
			if (isRejectRecord == true) {
				responseCheckUser.setIsCanLoan(0); // 是否可以借款（0：否，1：是）
				responseCheckUser.setIsBlackList(0); // 是否命中黑名单（0：否，1：是）
				responseCheckUser.setRejectReason(3); // 拒绝原因（1 黑名单，2 在贷，3 其他）
				responseCheckUser.setRemark("3：被机构审批拒绝中");

				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("命中被机构审批拒绝中规则");
				String AESResponseCheckUser = AESUtil.Encrypt(JSON.toJSONString(responseCheckUser), AESKey);
				thirdResponse.setResponse(AESResponseCheckUser);
				logger.info(sessionId + "结束ThirdServiceImpl.userCheck()方法，返回结果：" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			// 第五步：查询借款信息（二三四规则已过，可以借款）
			responseCheckUser.setIsCanLoan(1); // 是否可以借款（0：否，1：是）
			responseCheckUser.setIsBlackList(0); // 是否命中黑名单（0：否，1：是）
			responseCheckUser.setRejectReason(0); // 拒绝原因（1 黑名单，2 在贷，3 其他）
			responseCheckUser.setRemark("0：未被拒绝");

			// 最高、低额度；最大、小可借周期后期是否改为从产品表中获取
			BwProductDictionary bwProductDictionary = bwProductDictionaryService.findById(5L);// 默认查询产品ID为5的产品

			responseCheckUser.setMaxLimit(bwProductDictionary.getMaxAmount());// 最高额度（单位：元）
			responseCheckUser.setMinLimit(bwProductDictionary.getMinAmount());// 最低额度（单位：元）
			responseCheckUser.setMaxPeriod(30);// 最大可借周期
			responseCheckUser.setMinPeriod(Integer.valueOf(bwProductDictionary.getpTerm()));// 最小可借周期
			responseCheckUser.setPeriodUnit(Integer.valueOf(bwProductDictionary.getpTermType()) == 1 ? 2 : 1);// 周期单位（1：天，2：月）

			thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
			thirdResponse.setMsg("成功");
			String AESResponseCheckUser = AESUtil.Encrypt(JSON.toJSONString(responseCheckUser), AESKey);
			thirdResponse.setResponse(AESResponseCheckUser);

		} catch (Exception e) {
			logger.error(sessionId + "执行service层检查用户接口异常", e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("失败");
		}
		logger.info(sessionId + "结束service层检查用户接口：" + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 统一对外接口 - 保存工单信息（code0091）
	 * 
	 * @see com.waterelephant.third.service.ThirdService#savePushOrder(com.waterelephant.third.entity.ThirdRequest)
	 */
	@Override
	public ThirdResponse savePushOrder(long sessionId, ThirdRequest thirdRequest) {
		ThirdResponse thirdResponse = new ThirdResponse();
		logger.info(sessionId + "：开始service层保存工单信息接口");
		try {
			// 第一步：转换数据
			String requestJson = thirdRequest.getRequest();
			if (requestJson == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空");
				logger.info(sessionId + "：结束service层订单推送接口：" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			// AES解密请求参数
			String channelCode = thirdRequest.getAppId();
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(channelCode);
			String AESKey = ThirdConstant.THIRD_CONFIG.getString(AES_KEY + orderChannel.getId());
			requestJson = AESUtil.Decrypt(requestJson, AESKey);

			RequestPush requestPush = JSON.parseObject(requestJson, RequestPush.class);
			logger.debug(sessionId + "：成功执行service层（第一步：转换数据）");

			// 第二步：判断订单是否已存在
			BasicInfo basicInfo = requestPush.getBasicInfo();
			String thirdOrderNo = basicInfo.getThirdOrderNo();
			// BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			// if (bwOrderRong != null) {
			// thirdResponse.setCode(ThirdResponse.CODE_DUPLICATECALL);
			// thirdResponse.setMsg("订单已存在，请勿重复推送");
			// logger.info(sessionId + "：结束service层订单推送接口：" + JSON.toJSONString(thirdResponse));
			// return thirdResponse;
			// }
			// logger.debug(sessionId + "：成功执行service层（第二步：判断订单是否已存在）");

			// 第三步：新增或修改借款人
			int channelId = orderChannel.getId(); // 渠道ID
			String name = basicInfo.getName(); // 姓名
			String idCard = basicInfo.getIdCard(); // 身份证号
			String phone = basicInfo.getRegisterPhone(); // 注册手机号

			boolean flag = thirdCommonService.checkUserAccountProgressOrder(sessionId, idCard);
			if (flag) {
				thirdResponse.setCode(ThirdResponse.CODE_DUPLICATECALL);
				thirdResponse.setMsg("存在进行中的订单，请勿重复推送");
				logger.info(sessionId + "：结束service层订单推送接口：" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			// 获得产品
			BwProductDictionary bwProductDictionary = thirdCommonService.getProduct(name, idCard, phone, basicInfo.getPeriod());
			logger.info("========productId=" + bwProductDictionary.getId());

			BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, name, idCard, phone, channelId);
			logger.debug(sessionId + "：成功执行service层（第三步：新增或修改借款人）");

			// 判断该渠道是否有撤回的订单
			BwOrder order = new BwOrder();
			order.setBorrowerId(borrower.getId());
			order.setStatusId(8L);
			order.setChannel(channelId);
			order = bwOrderService.findBwOrderByAttr(order);

			if (order == null) {
				// 第四步：查询是否有进行中的订单
				long count = bwOrderService.findProOrder(String.valueOf(borrower.getId()));
				if (count > 0) {
					thirdResponse.setCode(ThirdResponse.CODE_DUPLICATECALL);
					thirdResponse.setMsg("存在进行中的订单，请勿重复推送");
					logger.info(sessionId + "：结束service层订单推送接口：" + JSON.toJSONString(thirdResponse));
					return thirdResponse;
				}
			}
			logger.debug(sessionId + "：成功执行service层（第四步：订单状态不为“1”是略过）");

			// 第五步：判断是否有草稿状态的订单
			BwOrder bwOrder = new BwOrder();
			bwOrder.setBorrowerId(borrower.getId());
			bwOrder.setStatusId(1L);
			bwOrder.setProductType(1);
			List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);// 先查询草稿状态的订单

			bwOrder.setStatusId(8L);
			bwOrder.setChannel(channelId);
			List<BwOrder> boList8 = bwOrderService.findBwOrderListByAttr(bwOrder);// 再查询撤回状态的订单
			boList.addAll(boList8); // 第一次进件被审批撤回后，再次进件时，更新第一次的订单
			if (boList != null && boList.size() > 0) {
				bwOrder = boList.get(boList.size() - 1);
				bwOrder.setChannel(channelId);
				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
				bwOrder.setProductType(1);
				bwOrder.setProductId(bwProductDictionary.getId().intValue());
				bwOrder.setExpectMoney(basicInfo.getLoanAmount() / 100D);
				bwOrder.setExpectNumber(basicInfo.getPeriod());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				bwOrder.setSubmitTime(simpleDateFormat.parse(basicInfo.getApplyDate()));
				bwOrder.setBorrowUse(basicInfo.getDesc());
				bwOrderService.updateBwOrder(bwOrder);
			} else {
				bwOrder = new BwOrder();
				bwOrder.setOrderNo(OrderUtil.generateOrderNo());
				bwOrder.setBorrowerId(borrower.getId());
				bwOrder.setStatusId(1L);
				bwOrder.setCreateTime(Calendar.getInstance().getTime());
				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
				bwOrder.setChannel(channelId);
				bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
				bwOrder.setApplyPayStatus(0);
				bwOrder.setProductId(bwProductDictionary.getId().intValue());
				bwOrder.setProductType(1);
				bwOrder.setExpectMoney(basicInfo.getLoanAmount() / 100D);
				bwOrder.setExpectNumber(basicInfo.getPeriod());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				bwOrder.setSubmitTime(simpleDateFormat.parse(basicInfo.getApplyDate()));
				bwOrder.setBorrowUse(basicInfo.getDesc());
				bwOrderService.addBwOrder(bwOrder);
			}
			logger.debug(sessionId + "：成功执行service层（第五步：判断是否有草稿状态的订单）");

			// 第六步：判断是否有融360订单
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
			logger.debug(sessionId + "：成功执行service层（第六步：判断是否有融360订单）");

			// 第七步：判断是否有工作信息
			CompanyInfo companyInfo = requestPush.getCompanyInfo(); // 公司
			BwWorkInfo bwWorkInfo = new BwWorkInfo();
			bwWorkInfo.setOrderId(bwOrder.getId());
			bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
			if (bwWorkInfo == null) {
				bwWorkInfo = new BwWorkInfo();
				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
				bwWorkInfo.setCreateTime(Calendar.getInstance().getTime());
				bwWorkInfo.setWorkYears(companyInfo.getJobTime());
				bwWorkInfo.setComName(companyInfo.getCompanyName());
				bwWorkInfo.setIncome(companyInfo.getIncome());
				bwWorkInfo.setIndustry(companyInfo.getIndustry());
				bwWorkInfo.setOrderId(bwOrder.getId());
				bwWorkInfoService.save(bwWorkInfo, borrower.getId());
			} else {
				bwWorkInfo.setCallTime("10:00 - 12:00");// 默认值
				bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
				bwWorkInfo.setWorkYears(companyInfo.getJobTime());
				bwWorkInfo.setComName(companyInfo.getCompanyName());
				bwWorkInfo.setIncome(companyInfo.getIncome());
				bwWorkInfo.setIndustry(companyInfo.getIndustry());
				bwWorkInfoService.update(bwWorkInfo);
			}
			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, bwOrder.getId(), 2, channelId); // 插入个人认证记录
			logger.debug(sessionId + "：成功执行service层（第七步：判断是否有工作信息）");

			// 第八步：通讯录
			List<Contact> contactList = requestPush.getContacts();
			List<BwContactList> list = new ArrayList<BwContactList>();
			if (contactList != null && contactList.size() > 0) {
				for (Contact contact : contactList) {
					if (CommUtils.isNull(contact.getName())) {
						continue;
					}
					if (CommUtils.isNull(contact.getPhone())) {
						continue;
					}
					BwContactList bwContactList = new BwContactList();
					bwContactList.setBorrowerId(borrower.getId());
					bwContactList.setPhone(contact.getPhone());
					bwContactList.setName(contact.getName());
					list.add(bwContactList);
				}
				bwContactListService.addOrUpdateBwContactLists(list, borrower.getId());
			}
			logger.debug(sessionId + "：成功执行service层（第八步：通讯录）");

			// 第九步：运营商
			Operator operator = requestPush.getOperator();
			BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrower.getId());
			if (bwOperateBasic == null) {
				bwOperateBasic = new BwOperateBasic();
				bwOperateBasic.setBorrowerId(borrower.getId());
				bwOperateBasic.setUserSource(operator.getSource());
				bwOperateBasic.setIdCard(CommUtils.isNull(operator.getIdCard()) ? "" : operator.getIdCard());
				bwOperateBasic.setAddr(CommUtils.isNull(operator.getAddr()) ? "" : operator.getAddr());
				bwOperateBasic.setPhone(operator.getPhone());
				bwOperateBasic.setPhoneRemain(operator.getBalance());
				bwOperateBasic.setRealName(CommUtils.isNull(operator.getRealName()) ? "" : operator.getRealName());
				bwOperateBasic.setCreateTime(new Date());
				bwOperateBasic.setUpdateTime(new Date());
				if (CommUtils.isNull(operator.getRegTime()) == false) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					bwOperateBasic.setRegTime(sdf.parse(operator.getRegTime()));
				}

				bwOperateBasicService.save(bwOperateBasic);
			} else {
				bwOperateBasic.setBorrowerId(borrower.getId());
				bwOperateBasic.setUserSource(operator.getSource());
				bwOperateBasic.setIdCard(CommUtils.isNull(operator.getIdCard()) ? "" : operator.getIdCard());
				bwOperateBasic.setAddr(CommUtils.isNull(operator.getAddr()) ? "" : operator.getAddr());
				bwOperateBasic.setPhone(operator.getPhone());
				bwOperateBasic.setPhoneRemain(operator.getBalance());
				bwOperateBasic.setRealName(CommUtils.isNull(operator.getRealName()) ? "" : operator.getRealName());
				bwOperateBasic.setUpdateTime(new Date());
				if (CommUtils.isNull(operator.getRegTime()) == false) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					bwOperateBasic.setRegTime(sdf.parse(operator.getRegTime()));
				}
				bwOperateBasicService.update(bwOperateBasic);
			}
			logger.debug(sessionId + "：成功执行service层（第九步：运营商）");

			// 第十步：通话记录
			List<CallRecord> callRecordList = requestPush.getCallRecords();
			Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrower.getId());
			if (callRecordList != null) {
				SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
				for (CallRecord callRecord : callRecordList) {
					try {
						Date jsonCallData = sdf_hms.parse(callRecord.getCallTime());
						if (callDate == null || jsonCallData.after(callDate)) { // 通话记录采取最新追加的方式
							BwOperateVoice bwOperateVoice = new BwOperateVoice();
							bwOperateVoice.setUpdateTime(new Date());
							bwOperateVoice.setBorrower_id(borrower.getId());
							bwOperateVoice.setCall_time(callRecord.getCallTime());
							bwOperateVoice.setCall_type(callRecord.getCallType());
							bwOperateVoice.setReceive_phone(callRecord.getReceivePhone());
							bwOperateVoice.setTrade_addr(callRecord.getTradeAddr());
							bwOperateVoice.setTrade_time(callRecord.getTradeTime());
							bwOperateVoice.setTrade_type(callRecord.getTradeType());

							bwOperateVoiceService.save(bwOperateVoice);
						}
					} catch (Exception e) {
						logger.error(sessionId + "插入单条通话记录异常，忽略该条记录");
					}
				}
			}
			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, bwOrder.getId(), 1, channelId);// 插入运营商认证记录
			logger.debug(sessionId + "：成功执行service层（第十步：通话记录）");

			// 第十一步：芝麻信用
			int sesameScore = basicInfo.getSesameScore();
			if (sesameScore > 0) {
				BwZmxyGrade bwZmxyGrade = bwZmxyGradeService.findZmxyGradeByBorrowerId(borrower.getId());
				if (bwZmxyGrade == null) {
					bwZmxyGrade = new BwZmxyGrade();
					bwZmxyGrade.setBorrowerId(borrower.getId());
					bwZmxyGrade.setZmScore(sesameScore);
					bwZmxyGrade.setCreateTime(Calendar.getInstance().getTime());
					bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
					bwZmxyGradeService.saveBwZmxyGrade(bwZmxyGrade);
				} else {
					bwZmxyGrade.setZmScore(sesameScore);
					bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
					bwZmxyGradeService.updateBwZmxyGrade(bwZmxyGrade);
				}
				thirdCommonService.addOrUpdateBwOrderAuth(sessionId, bwOrder.getId(), 4, channelId);// 插入芝麻认证记录
			}
			logger.debug(sessionId + "：成功执行service层（第十一步：芝麻信用）");

			// 第十二步：身份证图片
			IdentifyInfo identifyInfo = requestPush.getIdentifyInfo();
			String frontImage = UploadToCssUtils.urlUpload(identifyInfo.getFrontFile(), bwOrder.getId() + "_01"); // 身份证正面照
			logger.info("===========1" + identifyInfo.getFrontFile());
			String backImage = UploadToCssUtils.urlUpload(identifyInfo.getBackFile(), bwOrder.getId() + "_02"); // 身份证反面照
			logger.info("===========2" + identifyInfo.getBackFile());
			String handerImage = UploadToCssUtils.urlUpload(identifyInfo.getNatureFile(), bwOrder.getId() + "_03"); // 手持照
			logger.info("===========3" + identifyInfo.getNatureFile());
			thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, bwOrder.getId(), borrower.getId(), 0); // 保存身份证正面照
			thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, bwOrder.getId(), borrower.getId(), 0); // 保存身份证反面照
			thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, bwOrder.getId(), borrower.getId(), 0); // 保存手持照
			// 保存身份证信息
			BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
			bwIdentityCard.setBorrowerId(borrower.getId());
			bwIdentityCard = bwIdentityCardServiceImpl.findBwIdentityCardByAttr(bwIdentityCard);

			String idCardNum = identifyInfo.getIdCard();
			String year = idCardNum.substring(6, 10);
			String month = idCardNum.substring(10, 12);
			String day = idCardNum.substring(12, 14);
			if ("0".equals(month.substring(0, 1))) {
				month = month.replace("0", "-");
			} else {
				month = "-" + month;
			}
			if ("0".equals(day.substring(0, 1))) {
				day = day.replace("0", "-");
			} else {
				day = "-" + day;
			}
			String birthday = year + month + day;

			int sexNum = ThirdUtil.getSexByIdCard(idCardNum);
			String gender;
			if (sexNum == 0) {
				gender = "女";
			} else {
				gender = "男";
			}

			if (bwIdentityCard == null) {
				bwIdentityCard = new BwIdentityCard2();
				bwIdentityCard.setAddress(identifyInfo.getAddress());
				bwIdentityCard.setBirthday(birthday);
				bwIdentityCard.setBorrowerId(borrower.getId());
				bwIdentityCard.setCreateTime(new Date());
				bwIdentityCard.setGender(gender);
				bwIdentityCard.setIdCardNumber(identifyInfo.getIdCard());
				bwIdentityCard.setIssuedBy(identifyInfo.getIssuedBy());
				bwIdentityCard.setName(identifyInfo.getName());
				bwIdentityCard.setRace(identifyInfo.getNation());
				bwIdentityCard.setUpdateTime(new Date());
				bwIdentityCard.setValidDate(identifyInfo.getValidDate());
				bwIdentityCardServiceImpl.saveBwIdentityCard(bwIdentityCard);
			} else {
				bwIdentityCard.setAddress(identifyInfo.getAddress());
				bwIdentityCard.setBirthday(birthday);
				bwIdentityCard.setGender(gender);
				bwIdentityCard.setIdCardNumber(identifyInfo.getIdCard());
				bwIdentityCard.setIssuedBy(identifyInfo.getIssuedBy());
				bwIdentityCard.setName(identifyInfo.getName());
				bwIdentityCard.setRace(identifyInfo.getNation());
				bwIdentityCard.setUpdateTime(new Date());
				bwIdentityCard.setValidDate(identifyInfo.getValidDate());
				bwIdentityCardServiceImpl.updateBwIdentityCard(bwIdentityCard);
			}

			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, bwOrder.getId(), 3, channelId);// 插入身份认证记录
			logger.debug(sessionId + "：成功执行service层（第十二步：身份证图片）");

			// 第十三步：亲属联系人
			BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(bwOrder.getId());
			if (bwPersonInfo == null) {
				bwPersonInfo = new BwPersonInfo();
				bwPersonInfo.setAddress(basicInfo.getHouseProvince() + basicInfo.getHouseCity() + basicInfo.getHouseDistrict()
						+ basicInfo.getHouseAddress());
				bwPersonInfo.setCarStatus(basicInfo.getHaveCar());
				bwPersonInfo.setCityName(basicInfo.getHouseCity());
				bwPersonInfo.setCreateTime(new Date());
				bwPersonInfo.setEmail(basicInfo.getEmail());
				bwPersonInfo.setHouseStatus(basicInfo.getHaveHouse());
				bwPersonInfo.setMarryStatus(basicInfo.getMarriage());
				bwPersonInfo.setOrderId(bwOrder.getId());
				bwPersonInfo.setRelationName(basicInfo.getFirstName());
				bwPersonInfo.setRelationPhone(basicInfo.getFirstPhone());
				bwPersonInfo.setUnrelationName(basicInfo.getSecondName());
				bwPersonInfo.setUnrelationPhone(basicInfo.getSecondPhone());
				bwPersonInfo.setUpdateTime(new Date());

				bwPersonInfoService.add(bwPersonInfo);
			} else {
				bwPersonInfo.setAddress(basicInfo.getHouseAddress());
				bwPersonInfo.setCarStatus(basicInfo.getHaveCar());
				bwPersonInfo.setCityName(basicInfo.getHouseProvince() + basicInfo.getHouseCity() + basicInfo.getHouseDistrict());
				bwPersonInfo.setEmail(basicInfo.getEmail());
				bwPersonInfo.setHouseStatus(basicInfo.getHaveHouse());
				bwPersonInfo.setMarryStatus(basicInfo.getMarriage());
				bwPersonInfo.setOrderId(bwOrder.getId());
				bwPersonInfo.setRelationName(basicInfo.getFirstName());
				bwPersonInfo.setRelationPhone(basicInfo.getFirstPhone());
				bwPersonInfo.setUnrelationName(basicInfo.getSecondName());
				bwPersonInfo.setUnrelationPhone(basicInfo.getSecondPhone());
				bwPersonInfo.setUpdateTime(new Date());

				bwPersonInfoService.update(bwPersonInfo);
			}
			logger.debug(sessionId + "：成功执行service层（第十三步：亲属联系人）");

			// 第十四步：银行卡
			BwBankCard bwBankCard = new BwBankCard();
			bwBankCard.setBorrowerId(borrower.getId());
			bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
			if (bwBankCard != null && (bwBankCard.getSignStatus() == 1 || bwBankCard.getSignStatus() == 2)) {
				// 第十五步：修改工单状态为2
				bwOrder.setStatusId(2L);
				bwOrder.setUpdateTime(Calendar.getInstance().getTime());
				bwOrder.setSubmitTime(Calendar.getInstance().getTime());
				bwOrderService.updateBwOrder(bwOrder);
				logger.debug(sessionId + "：成功执行service层（第十五步：修改工单状态为2）");

				// 第三方通知-------------code0093
				logger.info("初审===" + bwOrder.getId());
				HashMap<String, String> hm = new HashMap<>();
				hm.put("channelId", CommUtils.toString(channelId));
				hm.put("orderId", String.valueOf(bwOrder.getId()));
				hm.put("orderStatus", "2");
				hm.put("result", "审核");
				String hmData = JSON.toJSONString(hm);
				RedisUtils.rpush("tripartite:orderStatusNotify:" + channelId, hmData);

				// 第十六步：放入redis
				SystemAuditDto systemAuditDto = new SystemAuditDto();
				systemAuditDto.setIncludeAddressBook(0);
				systemAuditDto.setOrderId(bwOrder.getId());
				systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
				systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
				systemAuditDto.setName(borrower.getName());
				systemAuditDto.setPhone(borrower.getPhone());
				systemAuditDto.setIdCard(borrower.getIdCard());
				systemAuditDto.setChannel(channelId);
				systemAuditDto.setThirdOrderId(thirdOrderNo);
				RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
						JsonUtils.toJson(systemAuditDto));
				logger.debug(sessionId + "：成功执行service层（第十六步：放入redis）");
			} else if (bwBankCard != null) {
				bwBankCard.setBankName(basicInfo.getBankName());
				String bankCode = ThirdUtil.convertToBankCode(basicInfo.getBankName());
				bwBankCard.setBankCode(bankCode);
				bwBankCard.setCardNo(basicInfo.getBankCardNum());
				bwBankCard.setPhone(basicInfo.getBankPhone());
				bwBankCard.setProvinceCode(basicInfo.getBankProvince());
				bwBankCard.setCityCode(basicInfo.getBankCity());
				bwBankCard.setSignStatus(0);
				bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
				bwBankCardService.updateBwBankCard(bwBankCard);
			} else {
				bwBankCard = new BwBankCard();
				bwBankCard.setBankName(basicInfo.getBankName());

				String bankCode = ThirdUtil.convertToBankCode(basicInfo.getBankName());
				bwBankCard.setBankCode(bankCode);

				bwBankCard.setCardNo(basicInfo.getBankCardNum());
				bwBankCard.setPhone(basicInfo.getBankPhone());
				bwBankCard.setBorrowerId(borrower.getId());
				bwBankCard.setProvinceCode(basicInfo.getBankProvince());
				bwBankCard.setCityCode(basicInfo.getBankCity());
				bwBankCard.setSignStatus(0);
				bwBankCard.setCreateTime(Calendar.getInstance().getTime());
				bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
				bwBankCardService.saveBwBankCard(bwBankCard, borrower.getId());
			}
			logger.debug(sessionId + "：成功执行service层（第十四步：银行卡）");

			// 第十七步 ：更改订单进行时间
			BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
			bwOrderProcessRecord.setSubmitTime(new Date());
			bwOrderProcessRecord.setOrderId(bwOrder.getId());
			bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
			logger.debug(sessionId + "：成功执行service层（第十七步 ：更改订单进行时间）");

			// 第十八步：成功返回
			ResponsePush responsePush = new ResponsePush();
			responsePush.setThirdOrderNo(thirdOrderNo);
			responsePush.setUserId(String.valueOf(borrower.getId()));

			thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
			thirdResponse.setMsg("成功");
			thirdResponse.setResponse(AESUtil.Encrypt(JSON.toJSONString(responsePush), AESKey));

			logger.debug(sessionId + "：成功执行service层（第十八步：成功返回）");

		} catch (Exception e) {
			logger.error(sessionId + "执行service层保存工单信息异常", e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("系统异常");
		}
		logger.info(sessionId + "结束service层保存工单信息接口：" + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 统一对外接口 - 修改绑卡回调（code0091）
	 * 
	 * @see com.waterelephant.third.service.ThirdService#updateSignCallback(long, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateBindCardCallback(long sessionId, String status, String result) {
		logger.info(sessionId + "：开始service层修改绑卡回调接口");
		try {
			if ("0000".equals(status)) {
				JSONObject jsonObject = JSONObject.parseObject(result);
				String userId = jsonObject.getString("user_id");
				String orderNo = RedisUtils.hget("third:bindCard", userId);
				bwBankCardService.updateSignStatusByBorrowerId(Long.valueOf(userId)); // 更新银行卡为已绑卡

				// 判断订单是否为空
				BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(orderNo);
				if (bwOrder == null) {
					logger.info(sessionId + "：结束service层修改绑卡回调接口，订单【000" + orderNo + "000】不存在");
					return false;
				}
				BwOrderRong bwOrderRong = bwOrderRongService.findBwOrderRongByOrderId(bwOrder.getId());
				if (bwOrderRong == null) {
					logger.info(sessionId + "：结束service层修改绑卡回调接口，融订单不存在");
					return false;
				}

				String bindCardFront = RedisUtils.get("tripartite:bindCard:front:" + bwOrder.getId()); // 前置绑卡的标识
				if (bwOrder.getStatusId() == 1 && bindCardFront == null) {

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
				RedisUtils.hdel("third:bindCard", userId);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(sessionId + "执行service层检查用户接口异常", e);
			return false;
		}
	}

	/**
	 * 统一对外接口 - 修改银行卡信息（code0091）
	 * 
	 * @see com.waterelephant.third.service.ThirdService#updateBankCard(long,
	 *      com.waterelephant.third.entity.ThirdRequest)
	 */
	@Override
	public ThirdResponse updateBankCard(long sessionId, ThirdRequest thirdRequest) {
		logger.info(sessionId + "-开始ThirdServiceImpl.updateBankCard method......");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.转换参数
			String requests = thirdRequest.getRequest();
			if (requests == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.updateBankCard method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// AES解密请求参数
			String channelCode = thirdRequest.getAppId();
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(channelCode);
			String AESKey = ThirdConstant.THIRD_CONFIG.getString(AES_KEY + orderChannel.getId());
			requests = AESUtil.Decrypt(requests, AESKey);

			RequestBankCardInfo requestBankCardInfo = JSON.parseObject(requests, RequestBankCardInfo.class);

			String thirdOrderNo = requestBankCardInfo.getThirdOrderNo();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.updateBankCard method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 2.根据第三方订单编号获取订单id
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("第三方订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.updateBankCard method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			Long orderId = bwOrderRong.getOrderId();

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.updateBankCard method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			// 获取银行卡信息
			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
			if (CommUtils.isNull(bwBankCard)) {
				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("银行卡信息为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.updateBankCard method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			if (0 == bwBankCard.getSignStatus()) {
				bwBankCard.setBankName(requestBankCardInfo.getBankName());
				String bankCode = ThirdUtil.convertToBankCode(requestBankCardInfo.getBankName());
				bwBankCard.setBankCode(bankCode);
				bwBankCard.setCardNo(requestBankCardInfo.getBankCardNum());
				bwBankCard.setPhone(requestBankCardInfo.getBankPhone());
				bwBankCard.setProvinceCode(requestBankCardInfo.getBankProvince());
				bwBankCard.setCityCode(requestBankCardInfo.getBankCity());
				bwBankCard.setSignStatus(0);
				bwBankCard.setUpdateTime(Calendar.getInstance().getTime());
				bwBankCardService.updateBwBankCard(bwBankCard);

				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("成功");
			} else if (2 == bwBankCard.getSignStatus()) {
				thirdResponse.setCode(ThirdResponse.CODE_DUPLICATECALL);
				thirdResponse.setMsg("银行卡已绑定，请勿修改！！！");
			}
		} catch (Exception e) {
			logger.error(sessionId + "-结束ThirdServiceImpl.updateBankCard method-" + e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdServiceImpl.updateBankCard method-" + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 统一对外接口-获取合同地址（code0091）
	 * 
	 * @see com.waterelephant.third.service.ThirdService#contractUrl(long, com.waterelephant.third.entity.ThirdRequest)
	 */
	@Override
	public ThirdResponse contractUrl(long sessionId, ThirdRequest thirdRequest) {
		logger.info(sessionId + "-开始ThirdServiceImpl.contractUrl method......");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.转换参数
			String requests = thirdRequest.getRequest();
			if (requests == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.contractUrl method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// AES解密请求参数
			String channelCode = thirdRequest.getAppId();
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(channelCode);
			String AESKey = ThirdConstant.THIRD_CONFIG.getString(AES_KEY + orderChannel.getId());
			requests = AESUtil.Decrypt(requests, AESKey);

			ResquestContract resquestContract = JSON.parseObject(requests, ResquestContract.class);

			String thirdOrderNo = resquestContract.getThirdOrderNo();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.contractUrl method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 2.根据第三方订单编号获取订单id
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("第三方订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.contractUrl method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			Long orderId = bwOrderRong.getOrderId();

			// 3.获取合同地址
			List<BwAdjunct> bwAdjunctList = bwAdjunctService.findBwAdjunctByOrderIdNew(orderId);
			if (CommUtils.isNull(bwAdjunctList)) {
				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("附件信息为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.contractUrl method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			Map<String, String> contractUrls = new HashMap<>();
			for (BwAdjunct bwAdjunct : bwAdjunctList) {
				if (bwAdjunct.getAdjunctType() == 11) {
					String adjunctPath = bwAdjunct.getAdjunctPath();
					String conUrl = SystemConstant.PDF_URL + adjunctPath;
					contractUrls.put("loanAgreement", conUrl);
				}
				if (bwAdjunct.getAdjunctType() == 13) {
					String adjunctPath = bwAdjunct.getAdjunctPath();
					String conUrl = SystemConstant.PDF_URL + adjunctPath;
					contractUrls.put("loanContract", conUrl);
				}
			}

			ResponseContract responseContract = new ResponseContract();
			responseContract.setContractUrls(contractUrls);

			thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
			thirdResponse.setMsg("获取成功");
			thirdResponse.setResponse(AESUtil.Encrypt(JSON.toJSONString(responseContract), AESKey));
		} catch (Exception e) {
			logger.error(sessionId + "-结束ThirdServiceImpl.contractUrl method-" + e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdServiceImpl.contractUrl method-" + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 统一对外接口-拉取订单状态（code0091）
	 * 
	 * @see com.waterelephant.third.service.ThirdService#orderStatus(long, com.waterelephant.third.entity.ThirdRequest)
	 */
	@Override
	public ThirdResponse orderStatus(long sessionId, ThirdRequest thirdRequest) {
		logger.info(sessionId + "-开始ThirdServiceImpl.orderStatus method......");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.转换参数
			String requests = thirdRequest.getRequest();
			if (requests == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.orderStatus method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// AES解密请求参数
			String channelCode = thirdRequest.getAppId();
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(channelCode);
			String AESKey = ThirdConstant.THIRD_CONFIG.getString(AES_KEY + orderChannel.getId());
			requests = AESUtil.Decrypt(requests, AESKey);

			RequestPullOrderStatus requestPullOrderStatus = JSON.parseObject(requests, RequestPullOrderStatus.class);

			String thirdOrderNo = requestPullOrderStatus.getThirdOrderNo();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.orderStatus method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 2.根据第三方订单编号获取订单
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("第三方订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.orderStatus method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			Long orderId = bwOrderRong.getOrderId();

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.orderStatus method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 获取订单状态
			Long statusId = bwOrder.getStatusId();

			ResponsePullOrderStatus responsePullOrderStatus = new ResponsePullOrderStatus();
			responsePullOrderStatus.setThirdOrderNo(thirdOrderNo);

			// 获取利率字典表信息
			BwProductDictionary bwProductDictionary = bwProductDictionaryService.findById(bwOrder.getProductId().longValue());
			Double fee = bwProductDictionary.getpFastReviewCost() + bwProductDictionary.getpPlatformUseCost()
					+ bwProductDictionary.getpNumberManageCost() + bwProductDictionary.getpCapitalUseCost()
					+ bwProductDictionary.getpCollectionPassagewayCost();
			Double zjw = bwProductDictionary.getZjwCost();

			if (4 == statusId) {
				responsePullOrderStatus.setOrderStatus(4);
				responsePullOrderStatus.setApprovalAmount(bwOrder.getBorrowAmount());
				responsePullOrderStatus.setApprovalTime(Integer.valueOf(bwProductDictionary.getpTerm()));
				responsePullOrderStatus.setApprovalTimeUnit(1);
				responsePullOrderStatus.setArrivaAmount(bwOrder.getBorrowAmount() - bwOrder.getBorrowAmount() * fee);
				responsePullOrderStatus.setOverallCost(bwOrder.getBorrowAmount() * (fee + zjw));
				responsePullOrderStatus.setRepaymentAmount(bwOrder.getBorrowAmount() + bwOrder.getBorrowAmount() * zjw);
				responsePullOrderStatus.setOverallCostExplain(
						"服务费：" + bwOrder.getBorrowAmount() * fee + "元，" + "仲裁费：" + bwOrder.getBorrowAmount() * zjw + "元");
			} else if (12 == statusId || 14 == statusId || 11 == statusId || 5 == statusId) {
				responsePullOrderStatus.setOrderStatus(5);
			} else {
				responsePullOrderStatus.setOrderStatus(statusId.intValue());
			}
			logger.info(JSON.toJSONString(responsePullOrderStatus));
			thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
			thirdResponse.setMsg("拉取订单状态成功！");
			thirdResponse.setResponse(AESUtil.Encrypt(JSON.toJSONString(responsePullOrderStatus), AESKey));
		} catch (Exception e) {
			logger.error(sessionId + "-结束ThirdServiceImpl.orderStatus method-" + e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdServiceImpl.orderStatus method-" + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	// /**
	// * 统一对外接口-审批结论（code0091）
	// *
	// * @see com.waterelephant.third.service.ThirdService#conclusion(long, com.waterelephant.third.entity.ThirdRequest)
	// */
	// @Override
	// public ThirdResponse conclusion(long sessionId, ThirdRequest thirdRequest) {
	// logger.info(sessionId + "-开始ThirdServiceImpl.conclusion method-");
	// ThirdResponse thirdResponse = new ThirdResponse();
	// try {
	// // 1.转换参数
	// String requests = thirdRequest.getRequest();
	// if (requests == null) {
	// thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
	// thirdResponse.setMsg("请求参数为空！");
	// logger.info(sessionId + "-结束ThirdServiceImpl.conclusion method-" + JSON.toJSONString(thirdResponse));
	// return thirdResponse;
	// }
	// // AES解密请求参数
	// String channelCode = thirdRequest.getAppId();
	// BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(channelCode);
	// String AESKey = ThirdConstant.THIRD_CONFIG.getString(AES_KEY + orderChannel.getId());
	// requests = AESUtil.Decrypt(requests, AESKey);
	//
	// RequestPullConclusion requestPullConclusion = JSON.parseObject(requests, RequestPullConclusion.class);
	//
	// String thirdOrderNo = requestPullConclusion.getThirdOrderNo();
	// if (StringUtils.isEmpty(thirdOrderNo)) {
	// thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
	// thirdResponse.setMsg("第三方订单编号为空！");
	// logger.info(sessionId + "-结束ThirdServiceImpl.conclusion method-" + JSON.toJSONString(thirdResponse));
	// return thirdResponse;
	// }
	// // 2.根据第三方订单编号获取订单
	// BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
	// if (CommUtils.isNull(bwOrderRong)) {
	// thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
	// thirdResponse.setMsg("第三方订单为空！");
	// logger.info(sessionId + "-结束ThirdServiceImpl.conclusion method-" + JSON.toJSONString(thirdResponse));
	// return thirdResponse;
	// }
	// Long orderId = bwOrderRong.getOrderId();
	//
	// BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
	// if (CommUtils.isNull(bwOrder)) {
	// thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
	// thirdResponse.setMsg("订单为空！");
	// logger.info(sessionId + "-结束ThirdServiceImpl.conclusion method-" + JSON.toJSONString(thirdResponse));
	// return thirdResponse;
	// }
	//
	// // 获取订单状态
	// Long statusId = bwOrder.getStatusId();
	//
	// ResponsePullConclusion responsePullConclusion = new ResponsePullConclusion();
	// if (4 == statusId) {
	// responsePullConclusion.setOrderNo(bwOrder.getOrderNo());
	// responsePullConclusion.setAmountType(0);
	// responsePullConclusion.setApprovalAmount(bwOrder.getCreditLimit());
	// responsePullConclusion.setApprovalTerm(30);
	// responsePullConclusion.setApprovalTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	// responsePullConclusion.setConclusion(statusId.intValue());
	// // responsePullConclusion.setPayAmount(payAmount);
	// // responsePullConclusion.setReceiveAmount(receiveAmount);
	// responsePullConclusion.setRemark("还款金额=本金+利息");
	// responsePullConclusion.setTermType(0);
	// responsePullConclusion.setTermUnit(1);
	// }

	// thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
	// thirdResponse.setMsg("拉取审批状态成功！");
	// thirdResponse.setResponse(AESUtil.Encrypt(JSON.toJSONString(responsePullConclusion), AESKey));
	// } catch (Exception e) {
	// logger.error(sessionId + "-结束ThirdServiceImpl.conclusion method-" + e);
	// thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
	// thirdResponse.setMsg("运行异常");
	// }
	// logger.info(sessionId + "-结束ThirdServiceImpl.conclusion method-" + JSON.toJSONString(thirdResponse));
	// return thirdResponse;
	// }

	/**
	 * 统一对外接口-签约（code0091）
	 * 
	 * @see com.waterelephant.third.service.ThirdService#signContract(long, com.waterelephant.third.entity.ThirdRequest)
	 */
	@Override
	public ThirdResponse updateSignContract(long sessionId, ThirdRequest thirdRequest) {
		logger.info(sessionId + "-开始ThirdServiceImpl.signContract method-");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.转换参数
			String requests = thirdRequest.getRequest();
			if (requests == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.signContract method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// AES解密请求参数
			String channelCode = thirdRequest.getAppId();
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(channelCode);
			String AESKey = ThirdConstant.THIRD_CONFIG.getString(AES_KEY + orderChannel.getId());
			requests = AESUtil.Decrypt(requests, AESKey);

			RequestApproveConfirm requestApproveConfirm = JSON.parseObject(requests, RequestApproveConfirm.class);

			String thirdOrderNo = requestApproveConfirm.getThirdOrderNo();

			if (StringUtils.isEmpty(thirdOrderNo)) {
				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.signContract method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			// 2.根据第三方订单编号获取订单
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("第三方订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.signContract method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			Long orderId = bwOrderRong.getOrderId();

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
				thirdResponse.setMsg("订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.signContract method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			// 获取审批金额
			// Double borrowAmount = bwOrder.getBorrowAmount();

			// 获取利率字典表信息
			BwProductDictionary bwProductDictionary = bwProductDictionaryService.findById(bwOrder.getProductId().longValue());

			// 获取审批期限
			String pTerm = bwProductDictionary.getpTerm();

			// 等额本息
			// 合同月利率
			Double contractMonthRate = bwProductDictionary.getpBorrowRateMonth();
			// 借款月利率
			Double pInvestRateMonth = bwProductDictionary.getpInvestRateMonth();
			// // 计算还款金额
			// Double repayAmount = DoubleUtil
			// .round(((borrowAmount / Integer.parseInt(pTerm)) + (borrowAmount * pInvestRateMonth)), 2);
			// // 计算合同金额
			// Double contractAmount = DoubleUtil
			// .round((repayAmount * (Math.pow(1 + contractMonthRate, Integer.parseInt(pTerm)) - 1))
			// / (contractMonthRate * (Math.pow(1 + contractMonthRate, Integer.parseInt(pTerm)))), 2);

			if (bwOrder.getStatusId() == 4) {// 待签约
				bwOrder.setRepayTerm(Integer.parseInt(pTerm));
				bwOrder.setRepayType(Integer.parseInt("1"));
				bwOrder.setBorrowRate(pInvestRateMonth);
				bwOrder.setContractRate(bwProductDictionary.getpInvesstRateYear());
				bwOrder.setContractMonthRate(contractMonthRate);
				bwOrder.setStatusId(11L);
				// bwOrder.setContractAmount(contractAmount);
				bwOrder.setUpdateTime(new Date());
				int num = bwOrderService.updateBwOrder(bwOrder);
				logger.info("修改工单条数:" + num);

				if (num == 0) {
					thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
					thirdResponse.setMsg("修改工单失败");
					logger.info(sessionId + "-结束ThirdServiceImpl.signContract method-" + JSON.toJSONString(thirdResponse));
					return thirdResponse;
				}

				// 第三方通知-------------code0093
				logger.info("签约成功===" + bwOrder.getId());
				HashMap<String, String> hm = new HashMap<>();
				hm.put("channelId", CommUtils.toString(orderChannel.getId()));
				hm.put("orderId", String.valueOf(bwOrder.getId()));
				hm.put("orderStatus", "11");
				hm.put("result", "签约成功");
				String hmData = JSON.toJSONString(hm);
				RedisUtils.rpush("tripartite:orderStatusNotify:" + orderChannel.getId(), hmData);

				// 生成合同
				RedisUtils.rpush("system:contract", String.valueOf(bwOrder.getId()));
				BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
				bwOrderProcessRecord.setOrderId(bwOrder.getId());
				bwOrderProcessRecord.setContractTime(new Date());
				bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
			}
			thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
			thirdResponse.setMsg("操作成功");
		} catch (Exception e) {
			logger.error(sessionId + "-结束ThirdServiceImpl.signContract method-" + e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdServiceImpl.signContract method-" + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 统一对外接口-还款计划（code0091）
	 * 
	 * @see com.waterelephant.third.service.ThirdService#repaymentPlan(long,
	 *      com.waterelephant.third.entity.ThirdRequest)
	 */
	@Override
	public ThirdResponse repaymentPlan(long sessionId, ThirdRequest thirdRequest) {
		logger.info(sessionId + "-开始ThirdServiceImpl.repaymentPlan method-");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.转换参数
			String requests = thirdRequest.getRequest();
			if (requests == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repaymentPlan method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// AES解密请求参数
			String channelCode = thirdRequest.getAppId();
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(channelCode);
			String AESKey = ThirdConstant.THIRD_CONFIG.getString(AES_KEY + orderChannel.getId());
			requests = AESUtil.Decrypt(requests, AESKey);

			RequestPullRepaymentPlan requestPullRepaymentPlan = JSON.parseObject(requests, RequestPullRepaymentPlan.class);

			String thirdOrderNo = requestPullRepaymentPlan.getThirdOrderNo();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repaymentPlan method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 2.根据第三方订单编号获取订单
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("第三方订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repaymentPlan method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			Long orderId = bwOrderRong.getOrderId();

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repaymentPlan method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 获取银行卡信息
			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
			if (CommUtils.isNull(bwBankCard)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("银行卡信息为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repaymentPlan method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 获取还款计划
			BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(bwOrder.getId());
			if (CommUtils.isNull(bwRepaymentPlan)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("还款计划为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repaymentPlan method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			Long statusId = bwOrder.getStatusId();

			// 获取逾期记录
			BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
			bwOverdueRecord.setOrderId(orderId);
			bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);

			RepaymentPlan repaymentPlan = new RepaymentPlan();
			if ("13".equals(statusId)) {
				if (!CommUtils.isNull(bwOverdueRecord) && bwOverdueRecord.getOverdueAccrualMoney() != null) {
					Double overdueFee = bwOverdueRecord.getOverdueAccrualMoney();
					repaymentPlan.setAmount(bwOrder.getBorrowAmount() + overdueFee);
				}
			}
			repaymentPlan.setAmount(bwOrder.getBorrowAmount());
			repaymentPlan.setBillStatus(statusId.intValue());
			repaymentPlan.setDueTime(bwRepaymentPlan.getRepayTime());
			repaymentPlan.setPayType(5);
			repaymentPlan.setPeriodNo(1);
			List<RepaymentPlan> repaymentPlanList = new ArrayList<>();
			repaymentPlanList.add(repaymentPlan);

			ResponsePullRepaymentPlan responsePullRepaymentPlan = new ResponsePullRepaymentPlan();
			responsePullRepaymentPlan.setBankCard(bwBankCard.getCardNo());
			responsePullRepaymentPlan.setOpenBank(bwBankCard.getBankName());
			responsePullRepaymentPlan.setThirdOrderNo(thirdOrderNo);
			responsePullRepaymentPlan.setRepaymentPlanList(repaymentPlanList);

			thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
			thirdResponse.setMsg("拉取还款计划成功！");
			thirdResponse.setResponse(AESUtil.Encrypt(JSON.toJSONString(responsePullRepaymentPlan), AESKey));
		} catch (Exception e) {
			logger.error(sessionId + "-结束ThirdServiceImpl.repaymentPlan method-" + e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdServiceImpl.repaymentPlan method-" + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 统一对外接口-还款详情（code0091）
	 * 
	 * @see com.waterelephant.third.service.ThirdService#repayInfo(long, com.waterelephant.third.entity.ThirdRequest)
	 */
	@Override
	public ThirdResponse repayInfo(long sessionId, ThirdRequest thirdRequest) {
		logger.info(sessionId + "-开始ThirdServiceImpl.repayInfo method......");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.转换参数
			String requests = thirdRequest.getRequest();
			if (StringUtils.isEmpty(requests) == true) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repayInfo method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// AES解密请求参数
			String channelCode = thirdRequest.getAppId();
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(channelCode);
			String AESKey = ThirdConstant.THIRD_CONFIG.getString(AES_KEY + orderChannel.getId());
			requests = AESUtil.Decrypt(requests, AESKey);

			RequestRepayInfo requestRepayInfo = JSON.parseObject(requests, RequestRepayInfo.class);

			String thirdOrderNo = requestRepayInfo.getThirdOrderNo();
			if (CommUtils.isNull(thirdOrderNo)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repayInfo method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			String periodNos = requestRepayInfo.getPeriodNos();
			if (CommUtils.isNull(periodNos)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repayInfo method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			// 2.根据第三方订单编号获取订单
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("融360订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repayInfo method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			Long orderId = bwOrderRong.getOrderId();
			// 3.获取还款计划
			BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(orderId);
			if (CommUtils.isNull(bwRepaymentPlan)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("还款计划为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repayInfo method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 获取逾期记录
			BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
			bwOverdueRecord.setOrderId(orderId);
			bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);

			ResponseRepayInfo responseRepayInfo = new ResponseRepayInfo();
			responseRepayInfo.setAmount(bwRepaymentPlan.getRealityRepayMoney());
			if (!CommUtils.isNull(bwOverdueRecord)) {
				responseRepayInfo.setAmount(bwRepaymentPlan.getRealityRepayMoney() + bwOverdueRecord.getOverdueAccrualMoney());
			}

			thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
			thirdResponse.setMsg("调用还款详情接口成功！");
			thirdResponse.setResponse(AESUtil.Encrypt(JSON.toJSONString(responseRepayInfo), AESKey));

		} catch (Exception e) {
			logger.error(sessionId + "-结束ThirdServiceImpl.repayInfo method-" + e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdServiceImpl.repayInfo method-" + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 统一对外接口-主动还款（code0091）
	 * 
	 * @see com.waterelephant.third.service.ThirdService#repayment(long, com.waterelephant.third.entity.ThirdRequest)
	 */
	@Override
	public ThirdResponse repayment(long sessionId, ThirdRequest thirdRequest) {
		logger.info(sessionId + "-开始ThirdServiceImpl.repayment method-");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.转换参数
			String requests = thirdRequest.getRequest();
			if (requests == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repayment method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// AES解密请求参数
			String channelCode = thirdRequest.getAppId();
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(channelCode);
			String AESKey = ThirdConstant.THIRD_CONFIG.getString(AES_KEY + orderChannel.getId());
			requests = AESUtil.Decrypt(requests, AESKey);

			RequestRepayment requestRepayment = JSON.parseObject(requests, RequestRepayment.class);

			String thirdOrderNo = requestRepayment.getThirdOrderNo();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repayment method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 2.根据第三方订单编号获取订单
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("第三方订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repayment method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			Long orderId = bwOrderRong.getOrderId();

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repayment method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 3.获取银行卡信息
			Long borrowerId = bwOrder.getBorrowerId();
			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBorrowerId(borrowerId);
			if (CommUtils.isNull(bwBankCard)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("银行卡信息为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repayment method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 4.还款
			thirdResponse = thirdCommonService.thirdRepay(sessionId, bwOrder, bwBankCard, null, "thirdRepay");
		} catch (Exception e) {
			logger.error(sessionId + "-结束ThirdServiceImpl.repayInfo method-" + e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdServiceImpl.repayInfo method-" + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 统一对外接口-展期详情（code0091）
	 * 
	 * @see com.waterelephant.third.service.ThirdService#deferInfo(long, com.waterelephant.third.entity.ThirdRequest)
	 */
	@Override
	public ThirdResponse deferInfo(long sessionId, ThirdRequest thirdRequest) {
		logger.info(sessionId + "-开始ThirdServiceImpl.deferInfo method-");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			// 1.转换参数
			String requests = thirdRequest.getRequest();
			if (requests == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.deferInfo method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// AES解密请求参数
			String channelCode = thirdRequest.getAppId();
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(channelCode);
			String AESKey = ThirdConstant.THIRD_CONFIG.getString(AES_KEY + orderChannel.getId());
			requests = AESUtil.Decrypt(requests, AESKey);

			RequestDeferInfo requestDeferInfo = JSON.parseObject(requests, RequestDeferInfo.class);

			String thirdOrderNo = requestDeferInfo.getThirdOrderNo();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.deferInfo method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 2.根据第三方订单编号获取订单
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("第三方订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.deferInfo method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			Long orderId = bwOrderRong.getOrderId();

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.deferInfo method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 3.获取还款计划
			BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(bwOrder.getId());
			if (CommUtils.isNull(bwRepaymentPlan)) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("还款计划为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.deferInfo method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 获取下个还款日
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			String repayTime = sdf.format(bwRepaymentPlan.getRepayTime());
			logger.info("还款时间为：" + repayTime);
			Date repayDate = sdf.parse(repayTime);
			// 离还款日的天数
			int day = MyDateUtils.getDaySpace(new Date(), repayDate);// 间隔时间
			logger.info("离还款时间相隔：" + day + "天");
			if (day > 10) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("到期时间十天内方可续贷");

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(repayDate);
				calendar.add(Calendar.DAY_OF_MONTH, -10);
				Long deferBeginTime = calendar.getTime().getTime();
				String deferBeginDate = sdf.format(deferBeginTime);

				ResponseDeferInfo responseDeferInfo = new ResponseDeferInfo();
				responseDeferInfo.setDeferBeginDate(deferBeginDate);
				thirdResponse.setResponse(AESUtil.Encrypt(JSON.toJSONString(responseDeferInfo), AESKey));
				logger.info(sessionId + "-结束ThirdServiceImpl.deferInfo method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			Integer deferNum = bwRepaymentPlan.getRolloverNumber();
			if (deferNum != null && deferNum >= 3) {
				thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
				thirdResponse.setMsg("展期次数不能超过3！");
				logger.info(sessionId + "-结束ThirdServiceImpl.deferInfo method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 获取逾期记录
			BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
			bwOverdueRecord.setOrderId(orderId);
			bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);

			Integer rolloverNumber = deferNum == null ? 1 : deferNum + 1;

			Double manageFee = ThirdUtil.getDeferFee(bwOrder, bwOverdueRecord, "manage_fee", rolloverNumber);
			Double interest = ThirdUtil.getDeferFee(bwOrder, bwOverdueRecord, "interest", rolloverNumber);
			Double overdueFee = ThirdUtil.getDeferFee(bwOrder, bwOverdueRecord, "overdue_fee", rolloverNumber);
			Double otherFee = ThirdUtil.getDeferFee(bwOrder, bwOverdueRecord, "other_fee", rolloverNumber);
			Double deferAmount = ThirdUtil.getDeferFee(bwOrder, bwOverdueRecord, "defer_amount", rolloverNumber);

			Double afterDeferAmount = ThirdUtil.getDeferFee(bwOrder, bwOverdueRecord, "after_defer_amount", rolloverNumber);
			Double afterManageFee = ThirdUtil.getDeferFee(bwOrder, bwOverdueRecord, "after_manage_fee", rolloverNumber);
			Double afterInterest = ThirdUtil.getDeferFee(bwOrder, bwOverdueRecord, "after_interest", rolloverNumber);
			Double afterOtherFee = ThirdUtil.getDeferFee(bwOrder, bwOverdueRecord, "after_other_fee", rolloverNumber);
			// 展期当前需还款金额=利息（interest）+管理费（manage_fee）+逾期费（overdue_fee）+其他费用（other_fee）
			DeferAmountOption deferAmountOption = new DeferAmountOption();
			deferAmountOption.setDeferAmount(deferAmount);
			deferAmountOption.setInterest(interest);
			deferAmountOption.setManageFee(manageFee);
			deferAmountOption.setOtherFee(otherFee);
			deferAmountOption.setOverdueFee(overdueFee);
			List<DeferAmountOption> deferAmountOptionList = new ArrayList<>();
			deferAmountOptionList.add(deferAmountOption);

			Date d = MyDateUtils.addMonths(bwRepaymentPlan.getRepayTime(), 1);
			Long deferDueTime = d.getTime() / 1000;
			// 展期应还款金额=利息（interest）+管理费（manage_fee）+逾期费（overdue_fee）+其他费用（other_fee）
			DeferOption deferOption = new DeferOption();
			deferOption.setAfterDeferAmount(afterDeferAmount);
			deferOption.setDeferDay(30);
			deferOption.setDeferDueTime(deferDueTime);
			deferOption.setInterest(afterInterest);
			deferOption.setManageFee(afterManageFee);
			deferOption.setOtherFee(afterOtherFee);
			List<DeferOption> deferOptionList = new ArrayList<>();
			deferOptionList.add(deferOption);

			ResponseDeferInfo responseDeferInfo = new ResponseDeferInfo();
			responseDeferInfo.setDeferAmountOptionList(deferAmountOptionList);
			responseDeferInfo.setDeferOptionList(deferOptionList);
			responseDeferInfo.setDeferAomountType(0);
			responseDeferInfo.setThirdOrderNo(thirdOrderNo);

			thirdResponse.setCode(ThirdResponse.CODE_SUCCESS);
			thirdResponse.setMsg("调用接口成功");
			thirdResponse.setResponse(AESUtil.Encrypt(JSON.toJSONString(responseDeferInfo), AESKey));

		} catch (Exception e) {
			logger.error(sessionId + "-结束ThirdServiceImpl.deferInfo method-" + e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdServiceImpl.deferInfo method-" + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

	/**
	 * 统一对外接口-展期（code0091）
	 * 
	 * @see com.waterelephant.third.service.ThirdService#defer(long, com.waterelephant.third.entity.ThirdRequest)
	 */
	@Override
	public ThirdResponse defer(long sessionId, ThirdRequest thirdRequest) {
		logger.info(sessionId + "-开始ThirdServiceImpl.defer method-");
		ThirdResponse thirdResponse = new ThirdResponse();
		try {
			String requests = thirdRequest.getRequest();
			if (requests == null) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("请求参数为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.defer method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// AES解密请求参数
			String channelCode = thirdRequest.getAppId();
			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(channelCode);
			String AESKey = ThirdConstant.THIRD_CONFIG.getString(AES_KEY + orderChannel.getId());
			requests = AESUtil.Decrypt(requests, AESKey);

			// 1.转换参数
			RequestDefer requestDefer = JSON.parseObject(requests, RequestDefer.class);
			if (CommUtils.isNull(requestDefer)) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("参数转换对象为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.defer method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			String thirdOrderNo = requestDefer.getThirdOrderNo();
			if (StringUtils.isEmpty(thirdOrderNo)) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("第三方订单编号为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.defer method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			// 2.根据第三方订单编号获取订单
			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
			if (CommUtils.isNull(bwOrderRong)) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("第三方订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.defer method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			Long orderId = bwOrderRong.getOrderId();

			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("订单为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.defer method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 获取银行卡信息
			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
			if (CommUtils.isNull(bwBankCard)) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("银行卡信息为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repaymentPlan method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}
			// 获取还款计划
			BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(bwOrder.getId());
			if (CommUtils.isNull(bwRepaymentPlan)) {
				thirdResponse.setCode(ThirdResponse.CODE_PARAMETER);
				thirdResponse.setMsg("还款计划为空！");
				logger.info(sessionId + "-结束ThirdServiceImpl.repaymentPlan method-" + JSON.toJSONString(thirdResponse));
				return thirdResponse;
			}

			// 获取逾期记录
			BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
			bwOverdueRecord.setOrderId(orderId);
			bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
			int rolloverNumber = bwRepaymentPlan.getRolloverNumber() == null ? 1
					: bwRepaymentPlan.getRolloverNumber().intValue() + 1;
			Double deferAmount = ThirdUtil.getDeferFee(bwOrder, bwOverdueRecord, "defer_amount", rolloverNumber);
			// 还款
			thirdResponse = thirdCommonService.thirdRepay(sessionId, bwOrder, bwBankCard, String.valueOf(deferAmount), "defer");

		} catch (Exception e) {
			logger.error(sessionId + "-结束ThirdServiceImpl.defer method-" + e);
			thirdResponse.setCode(ThirdResponse.CODE_NETERROR);
			thirdResponse.setMsg("运行异常");
		}
		logger.info(sessionId + "-结束ThirdServiceImpl.defer method-" + JSON.toJSONString(thirdResponse));
		return thirdResponse;
	}

}
