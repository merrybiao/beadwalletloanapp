//package com.waterelephant.sxyDrainage.sina.service.impl;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
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
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.math.NumberUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.parser.ParserConfig;
//import com.waterelephant.entity.BwAdjunct;
//import com.waterelephant.entity.BwBankCard;
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
//import com.waterelephant.entity.BwProductDictionary;
//import com.waterelephant.entity.BwRepaymentPlan;
//import com.waterelephant.entity.BwWorkInfo;
//import com.waterelephant.entity.BwXlDeviceinfo;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwOperateVoiceService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.BwProductDictionaryService;
//import com.waterelephant.service.BwXlDeviceinfoService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.IBwMerchantOrderService;
//import com.waterelephant.service.IBwOrderChannelService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.service.IBwRepaymentPlanService;
//import com.waterelephant.service.IBwRepaymentService;
//import com.waterelephant.service.IBwWorkInfoService;
//import com.waterelephant.service.impl.BwAdjunctServiceImpl;
//import com.waterelephant.service.impl.BwBorrowerService;
//import com.waterelephant.service.impl.BwContactListService;
//import com.waterelephant.service.impl.BwIdentityCardServiceImpl;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageEnum;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.sina.entity.Basic;
//import com.waterelephant.sxyDrainage.sina.entity.BasicInfo;
//import com.waterelephant.sxyDrainage.sina.entity.Behavior;
//import com.waterelephant.sxyDrainage.sina.entity.Calls;
//import com.waterelephant.sxyDrainage.sina.entity.CellBehavior;
//import com.waterelephant.sxyDrainage.sina.entity.DeviceInfo;
//import com.waterelephant.sxyDrainage.sina.entity.Report;
//import com.waterelephant.sxyDrainage.sina.entity.SinaBasicInfo;
//import com.waterelephant.sxyDrainage.sina.entity.SinaBindCard;
//import com.waterelephant.sxyDrainage.sina.entity.SinaCompanyInfo;
//import com.waterelephant.sxyDrainage.sina.entity.SinaContact;
//import com.waterelephant.sxyDrainage.sina.entity.SinaIdentifyInfo;
//import com.waterelephant.sxyDrainage.sina.entity.SinaOperator;
//import com.waterelephant.sxyDrainage.sina.entity.SinaPushOrderReq;
//import com.waterelephant.sxyDrainage.sina.entity.SinaResponse;
//import com.waterelephant.sxyDrainage.sina.service.AsyncSinaTaskService;
//import com.waterelephant.sxyDrainage.sina.service.SinaService;
//import com.waterelephant.sxyDrainage.sina.utils.AESUtil;
//import com.waterelephant.sxyDrainage.sina.utils.SinaConstant;
//import com.waterelephant.sxyDrainage.sina.utils.SinaUtils;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.interfaceLog.SxyThirdInterfaceLogUtils;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.Base64Utils;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.DateUtil;
//import com.waterelephant.utils.DoubleUtil;
//import com.waterelephant.utils.IdcardValidator;
//import com.waterelephant.utils.MyDateUtils;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//
//import tk.mybatis.mapper.entity.Example;
//
///**
// * 
// * <p>
// * Title: SinaServiceImpl
// * </p>
// * <p>
// * Description: 新浪
// * </p>
// * 
// * @since JDK 1.8
// * @author YANHUI
// */
//@Service
//public class SinaServiceImpl implements SinaService {
//	private Logger logger = LoggerFactory.getLogger(SinaServiceImpl.class);
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
//	private IBwRepaymentService bwRepaymentService;
//	
//	@Autowired
//	private AsyncSinaTaskService asyncSinaTaskService;
//	
//	@Autowired
//	private BwProductDictionaryService bwProductDictionaryService;
//	
//	
//	
//	@Autowired
//	private BwXlDeviceinfoService bwXlDeviceInfoService;
//	
//	@Autowired
//	private BwAdjunctServiceImpl bwAdjunctService;
//
//	/**
//	 * 4.1.1用户准入检验接口
//	 */
//	@Override
//	public SinaResponse checkUser(long sessionId, String appId, String request) {
//		logger.info(sessionId + ":新浪>>>准入用户检验接口>>>进入checkUser()方法");
//		SinaResponse sinaResponse = new SinaResponse();
//		Map<String, Object> responseMap = new HashMap<>();
//		try {
//			// 根据appId 获取channelId
//			logger.info(sessionId + ":新浪>>>checkUser()方法>>>获取orderChannel");
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道不存在");
//				return sinaResponse;
//			}
//
//			boolean isCorrect = checkChannelId(orderChannel, SinaConstant.CHANNELID);
//			if (!isCorrect) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道错误!");
//				return sinaResponse;
//			}
//
//			// AES解密
//			String requestParam = AESUtil.Decrypt(request, SinaConstant.AESKEY);
//			logger.info(sessionId + ":新浪>>>准入用户检验接口>>>request请求数据:" + requestParam);
//			if (StringUtils.isBlank(requestParam)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				return sinaResponse;
//			}
//
//			String phone = null;
//			// 用户检验
//			JSONObject parseObject = JSON.parseObject(requestParam);
//			if (null != parseObject) {
//				phone = parseObject.getString("phone");
//				if (StringUtils.isBlank(phone)) {
//					sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//					sinaResponse.setMsg("手机号为空");
//					return sinaResponse;
//				}
//			}
//			logger.info(sessionId + ":新浪>>>准入用户检验接口>>>用户mobile = " + phone);
//			BwBorrower borrower = new BwBorrower();
//			borrower.setPhone(phone);
//			BwBorrower borrowerByPhone = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			if (null == borrowerByPhone) {
//				sinaResponse.setCode(SinaResponse.RESULT_SUCCESS);
//				sinaResponse.setMsg("允许用户通过！");
//				logger.info(sessionId + ":新浪>>>准入用户检验接口>>>用户通过>>>phone = " + phone);
//				responseMap.put("isStock", 0);
//				responseMap.put("isCanLoan", 1);
//				// 金额限制
//				responseMap.put("maxLimit", 50000 * 100); // 最高额度 单位:分
//				responseMap.put("minLimit", 2000 * 100); // 最低额度
//				responseMap.put("step", 100 * 100); // 步长 100
//				responseMap.put("number", 4); // 期数
//				responseMap.put("periodNum", 28); // 可贷期限
//				responseMap.put("periodUnit", 1); // 期限单位
//
//				// 对响应参数AES加密
//				logger.info(sessionId + ":新浪>>>准入用户检验接口>>>response加密前数据:" + JSON.toJSONString(responseMap));
//				String response = AESUtil.Encrypt(JSON.toJSONString(responseMap), SinaConstant.AESKEY);
//				sinaResponse.setResponse(response);
//				return sinaResponse;
//			}
//			String name = borrowerByPhone.getName();
//			String idCard = borrowerByPhone.getIdCard();
//			logger.info(sessionId + ":新浪>>>准入用户检验接口>>>开始checkUser()用户检验>>>mobile = " + phone);
//			// DrainageRsp drainageRsp = commonService.checkUser(sessionId,
//			// name, mobile,
//			// idCard,String.valueOf(orderChannel.getId()));
//			DrainageRsp drainageRsp = commonService.checkUser(sessionId, name, phone, idCard);
//
//			// 0000 规则已过，可以借款
//			// 2001 命中黑名单规则
//			// 2002 存在进行中的订单
//			// 2003 命中被拒规则
//			// 2004 用户年龄超限
//			String resultCode = drainageRsp.getCode();
//			if (!DrainageEnum.CODE_SUCCESS.getCode().equals(resultCode)) {
//				responseMap.put("isCanLoan", 0); // 是否可以借款0否1是
//				if (DrainageEnum.CODE_RULE_BLACKLIST.getCode().equals(resultCode)) {
//					responseMap.put("rejectReason", 1); // 命中黑名单
//
//				} else if (DrainageEnum.CODE_RULE_ISPROCESSING.getCode().equals(resultCode)) {
//					responseMap.put("rejectReason", 2); // 存在进行中的订单
//
//				} else if (DrainageEnum.CODE_RULE_ISREJECT.getCode().equals(resultCode)) {
//					responseMap.put("rejectReason", 3); // 命中被拒规则
//
//				} else {
//					responseMap.put("rejectReason", 4); // 其他
//				}
//				responseMap.put("remark", drainageRsp.getMessage()); // 备注
//				sinaResponse.setCode(SinaResponse.RESULT_SUCCESS);
//				sinaResponse.setMsg("禁止用户通过！");
//
//			} else {
//				responseMap.put("isCanLoan", 1); // 是否可以借款0否1是
//				sinaResponse.setCode(SinaResponse.RESULT_SUCCESS);
//				sinaResponse.setMsg("允许用户通过！");
//			}
//			logger.info(sessionId + ":新浪>>>准入用户检验接口>>>结束checkUser()用户检验>>>");
//
//			// 判断增量用户
//			logger.info(sessionId + ":新浪>>>准入用户检验接口>>>开始oldUserFilter()增量用户判断>>>");
//			BwBorrower bw = bwBorrowerService.oldUserFilter(phone, idCard, name);
//			if (null != bw) {
//				// 老用户
//				responseMap.put("isStock", 1); // 增量用户0否1是
//				// 是否绑卡
//				BwBankCard bwBankCard = new BwBankCard();
//				bwBankCard.setBorrowerId(bw.getId());
//				bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
//				// 对于已绑卡用户要回传银行名、卡号、预留手机号
//				if (bwBankCard != null && bwBankCard.getSignStatus() > 0) {
//					responseMap.put("bank", bwBankCard.getBankName()); // 已绑银行
//					responseMap.put("bankCardNum", bwBankCard.getCardNo()); // 已绑银行卡号
//					responseMap.put("phone", bwBankCard.getPhone()); // 银行预留手机号
//				}
//			} else {
//				// 新用户
//				responseMap.put("isStock", 0); // 增量用户0否1是
//			}
//			logger.info(sessionId + ":新浪>>>准入用户检验接口>>>结束oldUserFilter()增量用户判断>>>");
//
//			// 金额限制
//			responseMap.put("maxLimit", 50000 * 100); // 最高额度 单位:分
//			responseMap.put("minLimit", 2000 * 100); // 最低额度
//			responseMap.put("step", 100 * 100); // 步长 100
//			responseMap.put("number", 4); // 期数
//			responseMap.put("periodNum", 28); // 可贷期限
//			responseMap.put("periodUnit", 1); // 期限单位
//
//			// 对响应参数AES加密
//			logger.info(sessionId + ":新浪>>>准入用户检验接口>>>response加密前数据:" + JSON.toJSONString(responseMap));
//			String response = AESUtil.Encrypt(JSON.toJSONString(responseMap), SinaConstant.AESKEY);
//			sinaResponse.setResponse(response);
//			return sinaResponse;
//
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>执行准入用户检验接口Service异常", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("系统异常,请稍后再试");
//			return sinaResponse;
//		}
//	}
//
//	/**
//	 * 4.1.2银行卡预绑卡接口（API）
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	@Override
//	public SinaResponse saveBindCardReady(long sessionId, String appId, String request) {
//		logger.info(sessionId + ":新浪>>>预绑卡接口>>>进入saveBindCardReady()方法");
//		SinaResponse sinaResponse = new SinaResponse();
//		SinaBindCard sinaBindCard = null;
//		try {
//			// AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道不存在");
//				return sinaResponse;
//			}
//
//			boolean isCorrect = checkChannelId(orderChannel, SinaConstant.CHANNELID);
//			if (!isCorrect) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道错误!");
//				return sinaResponse;
//			}
//
//			String requestJson = AESUtil.Decrypt(request, SinaConstant.AESKEY);
//			logger.info(sessionId + ":新浪>>>预绑卡接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>预绑卡接口requestJson为空>>>" + JSON.toJSONString(requestJson));
//				return sinaResponse;
//			}
//			// 获取绑卡基本参数
//			sinaBindCard = JSON.parseObject(requestJson, SinaBindCard.class);
//			if (null == sinaBindCard) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>预绑卡接口request为空>>>" + JSON.toJSONString(sinaBindCard));
//				return sinaResponse;
//			}
//
//			String idCard = sinaBindCard.getIdCard();
//			// 对身份证进行校验
//			boolean validate = IdcardValidator.isValidatedAllIdcard(idCard);
//			if (!validate) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("身份证不合法");
//				logger.info(sessionId + ":新浪>>>结束saveBindCardReady方法，返回结果:身份证为" + JSON.toJSONString(idCard));
//				return sinaResponse;
//			}
//
//			Integer channelId = orderChannel.getId();
//			logger.info(sessionId + ":新浪>>>开始预绑卡saveBindCard_NewReady()方法channelId:" + channelId);
//			DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//			drainageBindCardVO.setIdCardNo(sinaBindCard.getIdCard()); // 身份证
//			drainageBindCardVO.setName(sinaBindCard.getName()); // 持卡人姓名
//			drainageBindCardVO.setBankCardNo(sinaBindCard.getBankCardNo()); // 银行卡
//			drainageBindCardVO.setBankCode(sinaBindCard.getBankCode()); // 银行编码
//			drainageBindCardVO.setPhone(sinaBindCard.getPhone());
//			drainageBindCardVO.setRegPhone(sinaBindCard.getBankPhone()); // 银行预留手机
//			drainageBindCardVO.setBindType("1"); // 绑卡类型，1 前置 其他为后置 // 前置绑卡
//													// 传phone 后置绑卡 传
//													// thirdOrderNo
//			drainageBindCardVO.setChannelId(channelId); // 渠道号
//			drainageBindCardVO.setNotify(true); // 是否需要通知
//
//			DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(sessionId, drainageBindCardVO); // 新的预绑卡
//			if (null != drainageRsp) {
//				if (DrainageEnum.CODE_SUCCESS.getCode().equals(drainageRsp.getCode())) {
//					sinaResponse.setCode(SinaResponse.RESULT_SUCCESS);
//					sinaResponse.setMsg("预绑卡申请成功");
//					logger.info(sessionId + ":新浪>>>预绑卡申请成功" + JSON.toJSONString(drainageRsp));
//				} else {
//					sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//					sinaResponse.setMsg(drainageRsp.getMessage());
//					logger.info(sessionId + ":新浪>>>预绑卡申请失败" + JSON.toJSONString(drainageRsp));
//				}
//			} else {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("预绑卡申请失败");
//				logger.info(sessionId + ":新浪>>>预绑卡申请失败drainageRsp为空" + JSON.toJSONString(drainageRsp));
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>银行预绑卡接口Service异常", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("系统异常,请稍后再试");
//		} finally {
//			SxyThirdInterfaceLogUtils.setSxyLog(SinaConstant.CHANNELID, sinaBindCard.getPhone(), sinaResponse.getCode(),
//					sinaResponse.getMsg(), "注册手机号");
//		}
//		return sinaResponse;
//	}
//
//	/**
//	 * 4.1.3银行卡确认绑卡接口（API）
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	@Override
//	public SinaResponse saveBindCardSure(long sessionId, String appId, String request) {
//		logger.info(sessionId + ":新浪>>>确认绑卡接口>>>进入saveBindCardSure()方法");
//		SinaResponse sinaResponse = new SinaResponse();
//		SinaBindCard sinaBindCard = null;
//		try {
//			// AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":新浪>>>确认绑卡接口orderChannel为空>>>" + JSON.toJSONString(orderChannel));
//				return sinaResponse;
//			}
//
//			boolean isCorrect = checkChannelId(orderChannel, SinaConstant.CHANNELID);
//			if (!isCorrect) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道错误!");
//				return sinaResponse;
//			}
//
//			String requestJson = AESUtil.Decrypt(request, SinaConstant.AESKEY);
//			logger.info(sessionId + ":新浪>>>确认绑卡接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>确认绑卡接口requestJson为空>>>" + JSON.toJSONString(requestJson));
//				return sinaResponse;
//			}
//			// 获取绑卡基本参数
//			sinaBindCard = JSON.parseObject(requestJson, SinaBindCard.class);
//			if (null == sinaBindCard) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>确认绑卡接口request为空>>>" + JSON.toJSONString(sinaBindCard));
//				return sinaResponse;
//			}
//
//			Integer channelId = orderChannel.getId();
//			logger.info(sessionId + ":新浪>>>开始确认绑卡saveBindCard_NewReady()方法channelId:" + channelId);
//			DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//			drainageBindCardVO.setBindType("1"); // 绑卡类型，1 前置 其他为后置 // 前置绑卡 传
//													// phone 后置绑卡 传 thirdOrderNo
//			drainageBindCardVO.setChannelId(channelId);
//			drainageBindCardVO.setPhone(sinaBindCard.getPhone());
//			drainageBindCardVO.setNotify(false); // 不需要通知
//			drainageBindCardVO.setVerifyCode(sinaBindCard.getVerifyCode()); // 验证码
//
//			DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(sessionId, drainageBindCardVO); // 新的绑卡
//
//			// 绑卡通知
//			Map<String, Object> map = new HashMap<>();
//			map.put("channelId", channelId);
//			map.put("userPhone", sinaBindCard.getPhone());
//			map.put("result", "绑卡成功");
//			String json = JSON.toJSONString(map);
//			RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
//
//			if (null != drainageRsp) {
//				if (DrainageEnum.CODE_SUCCESS.getCode().equals(drainageRsp.getCode())) {
//					sinaResponse.setCode(SinaResponse.RESULT_SUCCESS);
//					sinaResponse.setMsg("确认绑卡成功");
//					logger.info(sessionId + ":新浪>>>确认绑卡成功" + JSON.toJSONString(drainageRsp));
//				} else {
//					sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//					sinaResponse.setMsg(drainageRsp.getMessage());
//					logger.info(sessionId + ":新浪>>>确认绑卡失败" + JSON.toJSONString(drainageRsp));
//				}
//			} else {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("确认绑卡失败");
//				logger.info(sessionId + ":新浪>>>确认绑卡失败drainageRsp为空" + JSON.toJSONString(drainageRsp));
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>银行确认绑卡接口Service异常", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("系统异常,请稍后再试");
//		} finally {
//			SxyThirdInterfaceLogUtils.setSxyLog(SinaConstant.CHANNELID, sinaBindCard.getPhone(), sinaResponse.getCode(),
//					sinaResponse.getMsg(), "注册手机号");
//		}
//		return sinaResponse;
//	}
//
//	/**
//	 * 4.1.4进件推送接口
//	 */
//	@Override
//	public SinaResponse savePushOrder(long sessionId, String appId, String request) {
//		logger.info(sessionId + ":新浪>>>进件推送接口>>>进入pushOrder()方法");
//		SinaResponse sinaResponse = new SinaResponse();
//		String orderNo = null;
//		try {
//			// 根据appId 获取channelId
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>获取orderChannel");
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道不存在");
//				return sinaResponse;
//			}
//			Integer channelId = orderChannel.getId();
//
//			boolean isCorrect = checkChannelId(orderChannel, SinaConstant.CHANNELID);
//			if (!isCorrect) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道错误!");
//				return sinaResponse;
//			}
//
//			// AES解密
//			String requestJson = AESUtil.Decrypt(request, SinaConstant.AESKEY);
//			if (StringUtils.isBlank(requestJson)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求入参为空！");
//				logger.info("解密request入参JSON数据为空!");
//				return sinaResponse;
//			}
//			ParserConfig.getGlobalInstance().setAsmEnable(false);
//			// 获取请求参数
//			SinaPushOrderReq sinaPushOrderReq = JSON.parseObject(requestJson, SinaPushOrderReq.class);
//			if (null == sinaPushOrderReq) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空！");
//				logger.info(sessionId + ":新浪>>>进件推送接口sinaPushOrderReq为空>>>" + JSON.toJSONString(sinaPushOrderReq));
//				return sinaResponse;
//			}
//
////			logger.info("新浪>>>进件推送接口请求入参：" + JSON.toJSONString(sinaPushOrderReq));
//			// 基本信息
//			SinaBasicInfo basicInfo = sinaPushOrderReq.getBasicInfo();
//			// 实名认证信息
//			SinaIdentifyInfo identifyInfo = sinaPushOrderReq.getIdentifyInfo();
//			// 公司信息
//			SinaCompanyInfo companyInfo = sinaPushOrderReq.getCompanyInfo();
//			// 通讯录
//			List<SinaContact> contacts = sinaPushOrderReq.getContacts();
//			// 设备信息
//			DeviceInfo deviceInfo = sinaPushOrderReq.getDeviceInfo();
//			// 运营商数据
//			SinaOperator operator = sinaPushOrderReq.getOperator();
//			
//			
//			logger.info("新浪>>>进件接口【运营商】：" + JSON.toJSONString(operator));
//			logger.info("新浪>>>进件接口【通讯录】：" + JSON.toJSONString(contacts));
//			logger.info("新浪>>>进件接口【公司信息】：" + JSON.toJSONString(companyInfo));
//			logger.info("新浪>>>进件接口【基本信息】：" + JSON.toJSONString(basicInfo));
//			logger.info("新浪>>>进件接口【设备信息】：" + JSON.toJSONString(deviceInfo));
//			
//
//			if (null == basicInfo || null == identifyInfo || null == companyInfo || null == deviceInfo
//					|| null == operator) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("必要参数为空！");
//				logger.info("缺少必要信息!");
//				return sinaResponse;
//			}
//		
//			logger.info(sessionId + ">>> 处理设备信息 ");
//			// 新增或更新借款人
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>开始新增或更新借款人:" + JSON.toJSONString(basicInfo));
//			String userName = basicInfo.getName();
//			String idCard = basicInfo.getIdCard();
//			String phone = basicInfo.getPhone();
//
//			// 对身份证进行校验
//			boolean validate = IdcardValidator.isValidatedAllIdcard(idCard);
//			if (!validate) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("身份证不合法！");
//				logger.info(sessionId + ":新浪>>>结束pushOrder方法，返回结果:身份证为" + JSON.toJSONString(idCard));
//				return sinaResponse;
//			}
//
//			orderNo = basicInfo.getOrderNo();
//
//			if (StringUtils.isBlank(userName)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("姓名为空");
//				logger.info(sessionId + ":新浪>>>结束pushOrder方法，返回结果:姓名为空" + JSON.toJSONString(userName));
//				return sinaResponse;
//			}
//			if (StringUtils.isBlank(idCard)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("身份证号码为空");
//				logger.info(sessionId + ":新浪>>>结束pushOrder方法，返回结果:身份证号码为空" + JSON.toJSONString(idCard));
//				return sinaResponse;
//			}
//			if (StringUtils.isBlank(phone)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("手机号为空");
//				logger.info(sessionId + ":新浪>>>结束pushOrder方法，返回结果:手机号为空" + JSON.toJSONString(phone));
//				return sinaResponse;
//			}
//			if (CommUtils.isNull(channelId)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道编码为空");
//				logger.info(sessionId + ":新浪>>>结束pushOrder方法，返回结果:渠道编码为空" + JSON.toJSONString(channelId));
//				return sinaResponse;
//			}
//			if (StringUtils.isBlank(orderNo)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("三方订单号为空");
//				logger.info(sessionId + ":新浪>>>结束pushOrder()方法，返回结果:三方订单号为空" + JSON.toJSONString(orderNo));
//				return sinaResponse;
//			}
//
//			// 返回结果封装
//			Map<String, Object> mapRes = new HashMap<String, Object>();
//			/**
//			 * 判断一个身份证是否有多个手机号码申请贷款
//			 */
//			boolean flag = thirdCommonService.checkUserAccountProgressOrder(sessionId, idCard);
//			if (flag) {
//				mapRes.put("push_status", "302");
//				mapRes.put("push_msg", "该用户已存在进行中的订单");
//				logger.info(sessionId + ":进件失败>>>response加密前数据:" + JSON.toJSONString(mapRes));
//				String response = AESUtil.Encrypt(JSON.toJSONString(mapRes), SinaConstant.AESKEY);
//				sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//				sinaResponse.setMsg("进件失败！");
//				sinaResponse.setResponse(response);
//				return sinaResponse;
//			}
//
//			BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, userName, idCard, phone, channelId);
//			long borrowerId = borrower.getId();
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>结束新增或更新借款人:borrowerId[" + borrowerId + "]");
//
//			// 判断该渠道是否有撤回的订单
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>开始判断该渠道是否有撤回的订单:borrowerId[" + borrowerId + "]");
//			BwOrder order = new BwOrder();
//			order.setBorrowerId(borrowerId);
//			order.setStatusId(7L); // 7:拒絕
//			order.setChannel(channelId);
//			order = bwOrderService.findBwOrderByAttr(order);
//			if (order == null) {
//				// 查询是否有进行中的订单
//				long count = bwOrderService.findProOrder(borrowerId + "");
//				logger.info(sessionId + ">>>进行中的订单校验：" + count);
//				if (count > 0) {
//					mapRes.put("push_status", "302");
//					mapRes.put("push_msg", "该用户已存在进行中的订单");
//					// 对响应参数AES加密
//					logger.info(sessionId + ":进件失败>>>response加密前数据:" + JSON.toJSONString(mapRes));
//					String response = AESUtil.Encrypt(JSON.toJSONString(mapRes), SinaConstant.AESKEY);
//					sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//					sinaResponse.setMsg("进件失败！");
//					sinaResponse.setResponse(response);
//					return sinaResponse;
//				}
//			}
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>结束新增或更新借款人:borrowerId[" + borrowerId + "]");
//			// 判断是否有草稿状态的订单
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>开始判断是否有草稿状态的订单");
//			Integer productId = Integer.valueOf(SinaConstant.PRODUCTID);
//			BwOrder bwOrder = new BwOrder();
//			bwOrder.setBorrowerId(borrowerId);
//			bwOrder.setStatusId(1L); // 当前工单状态 1草稿
//			bwOrder.setProductType(2);// 产品类型(1、单期，2、分期)
//			bwOrder.setChannel(channelId);
//			bwOrder.setProductId(productId);
//			List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);// 先查询草稿状态的订单
//			bwOrder.setStatusId(7L);
//			List<BwOrder> boList7 = bwOrderService.findBwOrderListByAttr(bwOrder);// 再查询撤回状态的订单
//			boList.addAll(boList7); // 第一次进件被审批撤回后，再次进件时，更新第一次的订单
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
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>结束判断是否有草稿状态的订单orderId:" + orderId);
//
//			// 判断是否有订单
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>开始判断是否有订单orderId:" + orderId);
//			BwOrderRong bwOrderRong = new BwOrderRong();
//			bwOrderRong.setThirdOrderNo(orderNo);
//			bwOrderRong.setChannelId(Long.valueOf(channelId));
//			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//
//			if (bwOrderRong == null) {
//				bwOrderRong = new BwOrderRong();
//				bwOrderRong.setOrderId(orderId);
//				bwOrderRong.setThirdOrderNo(orderNo);
//				bwOrderRong.setChannelId(Long.valueOf(channelId));
//				bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//				bwOrderRongService.save(bwOrderRong);
//			} else {
//				bwOrderRong.setChannelId(Long.valueOf(channelId));
//				bwOrderRong.setThirdOrderNo(orderNo);
//				bwOrderRong.setCreateTime(new Date());
//				bwOrderRongService.update(bwOrderRong);
//			}
//
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>结束判断是否有订单");
//
//			// 判断是否有商户订单信息
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>开始判断是否有商户订单信息orderId:" + orderId);
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
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>结束判断是否有商户订单信息");
//
//			// 判断是否有工作信息
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>开始判断是否有工作信息orderId:" + orderId);
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
//			//判断是否有设备信息
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>开始判断是否有设备信息orderId:" + orderId);
//		
//			bwXlDeviceInfoService.deleteByOrderId(orderId);
//			if (null != sinaPushOrderReq) {
//				String text = JSON.toJSONString(sinaPushOrderReq.getDeviceInfo());
//				BwXlDeviceinfo bwXlDeviceinfo = JSON.parseObject(text, BwXlDeviceinfo.class);
//				if (null != bwXlDeviceinfo) {
//					bwXlDeviceinfo.setOrderId(orderId);
//					bwXlDeviceinfo.setCreateTime(new Date());
//					bwXlDeviceinfo.setCreateTime(new Date());
//					bwXlDeviceInfoService.save(bwXlDeviceinfo);
//				}
//			}
//			/**
//			 * 插入个人认证记录 bw_order_auth 认证类型: 1：运营商 2：个人信息 3：拍照 4：芝麻信用 5：社保 6：公积金
//			 * 7：邮箱 8：淘宝 9：京东 10:单位信息 11：用款确认
//			 */
//			thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, channelId);
//
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>结束判断是否有工作信息");
//
//			// 通讯录
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>开始处理通讯录信息");
//			if (CollectionUtils.isNotEmpty(contacts)) {
//				List<BwContactList> listConS = new ArrayList<BwContactList>();
//				for (SinaContact sinaContact : contacts) {
//					if (CommUtils.isNull(sinaContact.getName()) || CommUtils.isNull(sinaContact.getPhone())) {
//						continue;
//					}
//					// 借款人通讯录列表
//					BwContactList bwContactList = new BwContactList();
//					bwContactList.setBorrowerId(borrowerId);
//					bwContactList.setPhone(sinaContact.getPhone());
//					bwContactList.setName(sinaContact.getName());
//					listConS.add(bwContactList);
//				}
//				bwContactListService.addOrUpdateBwContactLists(listConS, borrowerId);
//				logger.info(sessionId + ":新浪>>>pushOrder()方法>>>结束处理通讯录信息");
//			}
//
//			try {
//				// 运营商 通话记录 公共表操作
//				addOrUpdateOperate(sessionId, orderId, operator, borrowerId, channelId);
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//
//			// 认证图片
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>开始处理认证图片");
//
//			String backFile = identifyInfo.getBackFile();
//			String frontFile = identifyInfo.getFrontFile();
//			String natureFile = identifyInfo.getNatureFile();
//
//			if (StringUtils.isNotBlank(frontFile)) {
//				String frontImgPath = getImgUrl(frontFile, orderId + "_01");// 正面照
//				logger.info(sessionId + ">>> 身份证正面 " + frontFile);
//				boolean result = thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImgPath, null, orderId,
//						borrowerId, 0); // 保存身份证正面照
//				logger.info(sessionId + ">>> 身份证正面保存 " + result);
//			}
//			if (StringUtils.isNotBlank(backFile)) {
//				String backImgPath = getImgUrl(backFile, orderId + "_02");// 反面照
//				logger.info(sessionId + ">>> 身份证反面 " + backFile);
//				boolean result = thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImgPath, null, orderId,
//						borrowerId, 0); // 保存身份证反面照
//				logger.info(sessionId + ">>> 身份证反面保存 " + result);
//			}
//			if (StringUtils.isNotBlank(natureFile)) {
//				String natureImgPath = getImgUrl(natureFile, orderId + "_03");// 手持照
//				logger.info(sessionId + ">>> 手持照/人脸 " + natureFile);
//				boolean result = thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, natureImgPath, null, orderId,
//						borrowerId, 0); // 保存手持照
//				logger.info(sessionId + ">>> 手持照/人脸保存 " + result);
//			}
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>结束处理认证图片");
//
//			// 保存身份证信息
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>开始处理身份证信息");
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
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>结束处理身份证信息");
//
//			// 亲属联系人
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>开始处理亲属联系人信息");
//			BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//			if (null == bwPersonInfo) {
//				bwPersonInfo = new BwPersonInfo();
//				bwPersonInfo.setCreateTime(new Date());
//				bwPersonInfo.setOrderId(orderId);
//				bwPersonInfo.setUpdateTime(new Date());
//				bwPersonInfo.setAddress(basicInfo.getHouseAddress());
//				bwPersonInfo.setRelationName(basicInfo.getFirstName());
//				bwPersonInfo.setRelationPhone(basicInfo.getFirstPhone());
//				bwPersonInfo.setUnrelationName(basicInfo.getSecondName());
//				bwPersonInfo.setUnrelationPhone(basicInfo.getSecondPhone());
//				bwPersonInfo.setMarryStatus(basicInfo.getMarriage()); // 婚姻状况
//																		// 0：未婚，1：已婚
//				bwPersonInfo.setEmail(basicInfo.getEmail());
//				bwPersonInfo.setHouseStatus(basicInfo.getHaveHouse());
//				bwPersonInfo.setCarStatus(basicInfo.getHaveCar());
//				bwPersonInfo.setColleagueName(basicInfo.getColleagueName());
//				bwPersonInfo.setColleaguePhone(basicInfo.getColleaguePhone());
//				bwPersonInfo.setFriend1Name(basicInfo.getFriend1Name());
//				bwPersonInfo.setFriend1Phone(basicInfo.getFriend1Phone());
//				bwPersonInfo.setFriend2Name(basicInfo.getFriend2Name());
//				bwPersonInfo.setFriend2Phone(basicInfo.getFriend2Phone());
//				bwPersonInfo.setQqchat(basicInfo.getQqchat());
//				bwPersonInfo.setWechat(basicInfo.getWechat());
//				bwPersonInfoService.add(bwPersonInfo);
//			} else {
//				bwPersonInfo.setAddress(basicInfo.getHouseAddress());
//				bwPersonInfo.setRelationName(basicInfo.getFirstName());
//				bwPersonInfo.setRelationPhone(basicInfo.getFirstPhone());
//				bwPersonInfo.setUnrelationName(basicInfo.getSecondName());
//				bwPersonInfo.setUnrelationPhone(basicInfo.getSecondPhone());
//				bwPersonInfo.setMarryStatus(basicInfo.getMarriage());
//				bwPersonInfo.setEmail(basicInfo.getEmail());
//				bwPersonInfo.setHouseStatus(basicInfo.getHaveHouse());
//				bwPersonInfo.setCarStatus(basicInfo.getHaveCar());
//				bwPersonInfo.setUpdateTime(new Date());
//				bwPersonInfo.setColleagueName(basicInfo.getColleagueName());
//				bwPersonInfo.setColleaguePhone(basicInfo.getColleaguePhone());
//				bwPersonInfo.setFriend1Name(basicInfo.getFriend1Name());
//				bwPersonInfo.setFriend1Phone(basicInfo.getFriend1Phone());
//				bwPersonInfo.setFriend2Name(basicInfo.getFriend2Name());
//				bwPersonInfo.setFriend2Phone(basicInfo.getFriend2Phone());
//				bwPersonInfo.setQqchat(basicInfo.getQqchat());
//				bwPersonInfo.setWechat(basicInfo.getWechat());
//				bwPersonInfoService.update(bwPersonInfo);
//			}
//			logger.info(sessionId + ":新浪>>>pushOrder()方法>>>结束处理亲属联系人信息");
//
//			// 异步处理运营商数据
//			asyncSinaTaskService.addOperator(sessionId, bwOrder, borrower, operator);
//
//			mapRes.put("push_status", "301");
//			mapRes.put("push_msg", "进件成功");
//
//			// 对响应参数AES加密
//			logger.info(sessionId + ":进件成功>>>response加密前数据:" + JSON.toJSONString(mapRes));
//			String response = AESUtil.Encrypt(JSON.toJSONString(mapRes), SinaConstant.AESKEY);
//			sinaResponse.setResponse(response);
//			sinaResponse.setCode(SinaResponse.RESULT_SUCCESS);
//			sinaResponse.setMsg("进件成功！");
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>进件推送接口Service异常", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("系统异常,请稍后再试");
//		} finally {
//			SxyThirdInterfaceLogUtils.setSxyLog(SinaConstant.CHANNELID, orderNo, sinaResponse.getCode(),
//					sinaResponse.getMsg(), "三方工单号");
//		}
//		return sinaResponse;
//	}
//
//	/**
//	 * 4.1.7 主动还款接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	@Override
//	public SinaResponse updateActiveRepayment(long sessionId, String appId, String request) {
//		logger.info(sessionId + ":新浪>>>主动还款接口>>>进入updateActiveRepayment()方法");
//		SinaResponse sinaResponse = new SinaResponse();
//		String orderNO = null;
//		try {
//			// AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":新浪>>>主动还款接口orderChannel为空>>>" + JSON.toJSONString(orderChannel));
//				return sinaResponse;
//			}
//
//			boolean isCorrect = checkChannelId(orderChannel, SinaConstant.CHANNELID);
//			if (!isCorrect) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道错误!");
//				return sinaResponse;
//			}
//
//			String requestJson = AESUtil.Decrypt(request, SinaConstant.AESKEY);
//			logger.info(sessionId + ":新浪>>>主动还款接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>主动还款接口requestJson为空>>>" + JSON.toJSONString(requestJson));
//				return sinaResponse;
//			}
//			// 获取基本参数
//			JSONObject parseObject = JSON.parseObject(requestJson);
//			if (null != parseObject) {
//				orderNO = parseObject.getString("orderNo");
//			}
//			if (null == orderNO) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>主动还款接口thirdOrderNo为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//
//			logger.info(sessionId + ":新浪>>>开始主动还款updateRepayment()方法>>>");
//			DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, orderNO); // 新的支付
//			Map<String, Object> mapRes = new HashMap<String, Object>();
//
//			if (null != drainageRsp) {
//				if ("000".equals(drainageRsp.getCode())) {
//					sinaResponse.setCode(SinaResponse.RESULT_SUCCESS);
//					sinaResponse.setMsg("还款申请成功");
//					mapRes.put("repay_status", "601");
//					mapRes.put("repay_msg", "还款申请成功");
//					logger.info(sessionId + ":新浪>>>还款申请成功" + JSON.toJSONString(mapRes));
//
//				} else {
//					sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//					sinaResponse.setMsg("还款申请失败");
//					mapRes.put("repay_status", "605");
//					mapRes.put("repay_msg", drainageRsp.getMessage());
//					logger.info(sessionId + ":新浪>>>主动还款失败" + JSON.toJSONString(mapRes));
//				}
//
//				String response = AESUtil.Encrypt(JSON.toJSONString(mapRes), SinaConstant.AESKEY);
//				sinaResponse.setResponse(response);
//			} else {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("操作失败");
//				logger.info(sessionId + ":新浪>>>主动还款失败drainageRsp为空" + JSON.toJSONString(drainageRsp));
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>主动还款接口Service异常", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("系统异常,请稍后再试");
//		} finally {
//			SxyThirdInterfaceLogUtils.setSxyLog(SinaConstant.CHANNELID, orderNO, sinaResponse.getCode(),
//					sinaResponse.getMsg(), "三方工单号");
//		}
//		return sinaResponse;
//	}
//
//	private boolean checkChannelId(BwOrderChannel channel, String equalsChannelId) {
//		return Objects.equals(String.valueOf(channel.getId()), equalsChannelId) ? true : false;
//	}
//
//	/**
//	 * 添加基本的运营商数据 通话记录
//	 * 
//	 * @throws Exception
//	 */
//
//	private void addOrUpdateOperate(long sessionId, Long orderId, SinaOperator operator, Long borrowerId,
//			Integer channelId) throws Exception {
//		BasicInfo basicInfo = operator.getBasicInfo(); // 用户基本信息
//		Report report = operator.getReport();
//
//		List<Calls> callList = null;
//		String cell_operator = null;
//		String cell_loc = null;
//		if (null != report) {
//			List<CellBehavior> cell_behaviors = report.getCell_behavior();
//			for (CellBehavior cell_behavior : cell_behaviors) {
//				List<Behavior> behaviors = cell_behavior.getBehavior();
//				for (Behavior behavior : behaviors) {
//					cell_operator = behavior.getCell_operator(); // 号码类型
//					cell_loc = behavior.getCell_loc(); // 归属地
//				}
//			}
//		}
//
//		if (null != basicInfo) {
//			callList = basicInfo.getCalls(); // 获取通话记录
//			logger.info(sessionId + ":新浪>>>开始处理运营商基础信息borrowerId:" + borrowerId);
//			// 基本的运营商数据
//			BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
//			Basic basic = basicInfo.getBasic();
//			Assert.notNull(basic, "运营商基础信息中BasicInfo下Basic节点数据为空!");
//			if (bwOperateBasic == null) {
//				bwOperateBasic = new BwOperateBasic();
//				bwOperateBasic.setBorrowerId(borrowerId);
//				bwOperateBasic.setUserSource(cell_operator);
//				bwOperateBasic.setIdCard(basic.getIdcard());
//				bwOperateBasic.setAddr(cell_loc);
//				bwOperateBasic.setRealName(basic.getReal_name());
//				bwOperateBasic.setPhone(basic.getCell_phone());
//				bwOperateBasic.setRegTime(DateUtil.stringToDate(basic.getReg_time(), DateUtil.yyyy_MM_dd_HHmmss));
//				bwOperateBasic.setCreateTime(new Date());
//				bwOperateBasic.setUpdateTime(new Date());
//				bwOperateBasicService.save(bwOperateBasic);
//			} else {
//				bwOperateBasic.setBorrowerId(borrowerId);
//				bwOperateBasic.setUserSource(cell_operator);
//				bwOperateBasic.setIdCard(basic.getIdcard());
//				bwOperateBasic.setAddr(cell_loc);
//				bwOperateBasic.setRealName(basic.getReal_name());
//				bwOperateBasic.setPhone(basic.getCell_phone());
//				bwOperateBasic.setRegTime(DateUtil.stringToDate(basic.getReg_time(), DateUtil.yyyy_MM_dd_HHmmss));
//				bwOperateBasic.setUpdateTime(new Date());
//				bwOperateBasicService.update(bwOperateBasic);
//			}
//			logger.info(sessionId + ":新浪>>>结束处理运营商基础信息");
//		}
//
//		// 处理通话记录
//		if (CollectionUtils.isNotEmpty(callList)) {
//			logger.info(sessionId + ":新浪>>>开始更新通话记录......");
//			SimpleDateFormat sdf_hms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
//			for (Calls call : callList) {
//				Date jsonCallDate = sdf_hms.parse(call.getStart_time());
//				try {
//					if (null == callDate || jsonCallDate.after(callDate)) { // 通话记录采取最新追加的方式
//						BwOperateVoice bwOperateVoice = new BwOperateVoice();
//						bwOperateVoice.setUpdateTime(Calendar.getInstance().getTime());
//						bwOperateVoice.setBorrower_id(borrowerId);
//						// 检验日期格式
//						String callTime = sdf_hms.format(sdf_hms.parse(call.getStart_time()));
//						bwOperateVoice.setCall_time(callTime); // 通话时间
//						bwOperateVoice.setCall_type("主叫".equals(call.getInit_type()) ? 1 : 2); // 呼叫类型
//						bwOperateVoice.setReceive_phone(call.getOther_cell_phone()); // 对方号码
//						bwOperateVoice.setTrade_addr(CommUtils.isNull(call.getPlace()) ? "" : call.getPlace()); // 通话地点
//						bwOperateVoice.setTrade_time(String.valueOf(call.getUse_time())); // 通话时长
//						bwOperateVoice.setTrade_type("本地通话".equals(call.getCall_type()) ? 1 : 2); // 通信类型
//																									// 1.本地通话,国内长途
//						bwOperateVoiceService.save(bwOperateVoice);
//					}
//				} catch (Exception e) {
//					logger.error(sessionId + ":新浪>>>保存通话记录异常,忽略此条通话记录...", e);
//					continue;
//				}
//			}
//			logger.info(sessionId + ":新浪>>>更新通话记录结束......");
//		} else {
//			logger.info(sessionId + ":新浪>>>通话记录为空......");
//		}
//		thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 1, channelId);// 插入运营商认证记录
//	}
//
//	/**
//	 * 拉取订单状态接口
//	 * 
//	 * @throws Exception
//	 */
//	@Override
//	public SinaResponse getOrderStatus(long sessionId, String appId, String request) {
//		logger.info(sessionId + ":新浪>>>拉取订单状态接口>>>进入getOrderStatus()方法");
//		SinaResponse sinaResponse = new SinaResponse();
//		try {
//			// AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":新浪>>>拉取订单状态接口orderChannel为空>>>" + JSON.toJSONString(orderChannel));
//				return sinaResponse;
//			}
//
//			boolean isCorrect = checkChannelId(orderChannel, SinaConstant.CHANNELID);
//			if (!isCorrect) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道错误!");
//				return sinaResponse;
//			}
//
//			String requestJson = AESUtil.Decrypt(request, SinaConstant.AESKEY);
//			logger.info(sessionId + ":新浪>>>拉取订单状态接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>拉取订单状态接口requestJson为空>>>" + JSON.toJSONString(requestJson));
//				return sinaResponse;
//			}
//			// 获取基本参数
//			String orderNo = null;
//			JSONObject parseObject = JSON.parseObject(requestJson);
//			if (null != parseObject) {
//				orderNo = parseObject.getString("orderNo");
//			}
//			if (StringUtils.isBlank(orderNo)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>拉取订单状态接口thirdOrderNo为空>>>" + JSON.toJSONString(orderNo));
//				return sinaResponse;
//			}
//
//			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
//			if (CommUtils.isNull(bwOrderRong)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("三方订单为空");
//				logger.info(sessionId + ":新浪>>>拉取订单状态接口bwOrderRong为空>>>" + JSON.toJSONString(bwOrderRong));
//				return sinaResponse;
//			}
//
//			Long orderId = bwOrderRong.getOrderId();
//			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
//			if (null == bwOrder) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("订单为空");
//				logger.info(sessionId + ":新浪>>>拉取订单状态接口bwOrder为空>>>" + JSON.toJSONString(orderId));
//				return sinaResponse;
//			}
//
//			// 1 查询借款人
//			BwBorrower borrower = new BwBorrower();
//			borrower.setId(bwOrder.getBorrowerId());
//			borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//			if (null == borrower) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("用户不存在");
//				logger.info(sessionId + ":新浪>>>拉取订单状态接口borrower为空>>>" + JSON.toJSONString(borrower));
//				return sinaResponse;
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
//					sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//					sinaResponse.setMsg("还款计划不存在");
//					logger.info(sessionId + ":新浪>>>拉取订单状态接口planList不存在>>>" + JSON.toJSONString(planList));
//					return sinaResponse;
//				}
//
//				// 2 封装还款计划
//				logger.info(sessionId + ":新浪>>>getOrderStatus()>>>开始封装还款计划>>>");
//				List<Map<String, Object>> plans = new ArrayList<Map<String, Object>>();
//				boolean isProcessing = bwRepaymentService.isProcessing(orderId);
//				boolean flag = false;
//
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
//						if (isProcessing && !flag) {
//							// 处理中
//							planMap.put("state", "603");
//							flag = true;
//						} else {
//							// 未还款
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
//							Double advance = bwOverdueRecord.getAdvance() == null ? 0.0D : bwOverdueRecord.getAdvance();
//							Double overdueFee = DoubleUtil.sub(overdueAccrualMoney, advance);
//							planMap.put("overdue_days", bwOverdueRecord.getOverdueDay());
//							planMap.put("overdue_fee", new Double(overdueFee * 100).intValue());
//							if (plan.getRepayStatus() != null && plan.getRepayStatus() == 1) { // 逾期并且未还款
//								planMap.put("repay_money", new Double(plan.getRepayMoney() * 100).intValue()
//										+ new Double(overdueFee * 100).intValue()); // 应还金额
//								planMap.put("state", "602"); // 逾期
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
//
//				if (Objects.equals(statusIdStr, "6") || Objects.equals(statusIdStr, "9")
//						|| Objects.equals(statusIdStr, "13")) {
//					Integer totalRepayMoney = 0; // 应还总金额
//					Integer totalPrincipal = 0; // 总本金
//					Integer totalInterest = 0; // 总利息
//					Integer totalAlreadyPaid = 0; // 已还总金额
//					Integer totalOverdueFee = 0; // 逾期总费用
//					for (Map<String, Object> data : plans) {
//						totalRepayMoney += (Integer) data.get("repay_money");
//						totalPrincipal += (Integer) data.get("principal");
//						totalInterest += (Integer) data.get("interest");
//						totalAlreadyPaid += (Integer) data.get("already_paid");
//						totalOverdueFee += (Integer) data.get("overdue_fee");
//					}
//					map.put("total_principal", totalPrincipal);
//					map.put("total_interest", totalInterest);
//					map.put("total_repay_money", totalRepayMoney);
//					map.put("total_already_paid", totalAlreadyPaid);
//					map.put("total_overdue_fee", totalOverdueFee);
//				}
//				logger.info(sessionId + ":新浪>>>getOrderStatus()>>>结束封装还款计划>>>");
//
//				System.out.println(JSON.toJSONString(map, true));
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
//			map.put("orderNo", orderNo); // 订单唯一编号
//			String convertOrderStatus = SinaUtils.convertOrderStatus(Long.valueOf(statusIdStr));
//			map.put("status", null == convertOrderStatus ? "" : convertOrderStatus);
//
//			Integer orderStat = Integer.valueOf(statusIdStr);
//			if (orderStat >= 4 && orderStat != 7 && orderStat != 8) {
//				map.put("approve_amount", bwOrder.getBorrowAmount() * 100); // 审批金额,单位：分
//				String approve_date = DateUtil.getDateString(bwOrder.getSubmitTime(), DateUtil.yyyy_MM_dd_HHmmss);
//				map.put("approve_date", approve_date); // 审核时间
//			}
//			// ================================================================================================
//			// 对响应参数AES加密
//			logger.info(sessionId + ":新浪>>>拉取订单状态接口>>>response加密前数据:" + JSON.toJSONString(map));
//			String response = AESUtil.Encrypt(JSON.toJSONString(map), SinaConstant.AESKEY);
//			sinaResponse.setResponse(response);
//			sinaResponse.setCode(SinaResponse.RESULT_SUCCESS);
//			sinaResponse.setMsg("拉取订单状态成功");
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>执行拉取订单状态接口Service异常", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("系统异常,请稍后再试");
//		}
//		logger.info(sessionId + ":新浪>>>执行service层拉取订单状态接口成功");
//		return sinaResponse;
//	}
//
//	/**
//	 * 4.1.9 还款试算接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	@Override
//	public SinaResponse loanCalculation(long sessionId, String appId, String request) {
//		logger.info(sessionId + ":新浪>>>贷款试算接口>>>进入loanCalculation()方法");
//		SinaResponse sinaResponse = new SinaResponse();
//		try {
//			// 第一步 : AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":新浪>>>贷款试算接口orderChannel为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			Integer channelId = orderChannel.getId();
//			String requestJson = AESUtil.Decrypt(request, SinaConstant.AESKEY);
//			logger.info(sessionId + ":新浪>>>贷款试算接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>贷款试算接口requestJson为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			// 解开request
//			Map map = JSON.parseObject(requestJson, Map.class);
//			if (null == map) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>贷款试算接口request为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//
//			logger.info(sessionId + ":新浪>>>贷款试算接口channelId:" + channelId);
//			// code:180706 ===== 修改
//			String thirdOrderNo = map.get("orderNo").toString();
//			BwOrderRong bwOrderRong = new BwOrderRong();
//			bwOrderRong.setThirdOrderNo(thirdOrderNo);
//			BwOrderRong bor = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//			Long orderId = bor.getOrderId();
//			logger.info("====================试算接口orderId：" + orderId);
//
//			Example example = new Example(BwRepaymentPlan.class);
//			example.createCriteria().andEqualTo("orderId", orderId);
//			example.setOrderByClause("id  asc");
//			List<BwRepaymentPlan> planList = bwRepaymentPlanService.findRepaymentPlanByExample(example);
//			if (CollectionUtils.isEmpty(planList)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("还款计划不存在");
//				logger.info(sessionId + ":新浪>>>拉取订单状态接口planList不存在>>>" + JSON.toJSONString(planList));
//				return sinaResponse;
//			}
//
//			// 2 封装还款计划
//			logger.info(sessionId + ":新浪>>>getOrderStatus()>>>开始封装还款计划>>>");
//			boolean isProcessing = bwRepaymentService.isProcessing(orderId);
//			boolean flag = false;
//			// 得到每一期的还款计划 并封装
//			Map<String, Object> planMap = new HashMap<String, Object>();
//			for (BwRepaymentPlan plan : planList) {
//				if (plan.getRepayStatus() == 2) {
//					continue;
//				}
//				planMap.put("orderNo", map.get("orderNo").toString());// 三方工单号
//				planMap.put("number", plan.getNumber()); // 期数
//				planMap.put("principal", new Double(plan.getRepayCorpusMoney() * 100).intValue()); // 还款本金
//				planMap.put("interest", new Double(plan.getRepayAccrualMoney() * 100).intValue()); // 利息
//				planMap.put("repay_money", new Double(plan.getRepayMoney() * 100).intValue()); // 应还金额
//
//				if (plan.getRepayStatus() == 2) {
//					// 已还款
//					planMap.put("state", "601");
//				} else {
//					if (isProcessing && !flag) {
//						planMap.put("state", "603");// 交易处理中
//						flag = true;
//					} else {
//						// 未还款
//						planMap.put("state", "602");
//					}
//				}
//				planMap.put("overdue_days", 0); // 逾期天数
//				planMap.put("overdue_fee", 0); // 逾期费用
//				if (null != plan.getRepayType() && plan.getRepayType() == 2) { // 逾期
//					// 计算逾期
//					BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//					bwOverdueRecord.setRepayId(plan.getId());
//					bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//					if (null != bwOverdueRecord) {
//						Double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0.0D
//								: bwOverdueRecord.getOverdueAccrualMoney();
//						Double advance = bwOverdueRecord.getAdvance() == null ? 0.0D : bwOverdueRecord.getAdvance();
//						Double overdueFee = DoubleUtil.sub(overdueAccrualMoney, advance);
//						planMap.put("overdue_days", bwOverdueRecord.getOverdueDay());
//						planMap.put("overdue_fee", new Double(overdueFee * 100).intValue());
//						if (plan.getRepayStatus() != null && plan.getRepayStatus() == 1) { // 逾期并且未还款
//							planMap.put("repay_money", new Double(plan.getRepayMoney() * 100).intValue()
//									+ new Double(overdueFee * 100).intValue()); // 应还金额
//						}
//						if (plan.getRepayStatus() != null && plan.getRepayStatus() == 2) { // 逾期并且已还款
//							planMap.put("repay_money", new Double(plan.getRepayMoney() * 100).intValue()); // 应还金额
//						}
//						planMap.put("remark", "逾期");
//					} else {
//						// 没有逾期
//						planMap.put("overdue_days", 0); // 逾期天数
//						planMap.put("overdue_fee", 0); // 逾期费用
//					}
//				}
//			}
//			String response = AESUtil.Encrypt(JSON.toJSONString(planMap), SinaConstant.AESKEY);
//			sinaResponse.setCode(SinaResponse.RESULT_SUCCESS);
//			sinaResponse.setMsg("还款试算接口调用成功");
//			sinaResponse.setResponse(response);
//			return sinaResponse;
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>还款试算接口Service异常", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("系统异常,请稍后再试");
//			return sinaResponse;
//		}
//
//	}
//
//	/**
//	 * 4.1.7 签约提现提现接口
//	 * 
//	 * @param sessionId
//	 * @param appId
//	 * @param request
//	 * @return
//	 */
//	@Override
//	public SinaResponse updateSignContract(long sessionId, String appId, String request) {
//		logger.info(sessionId + ":新浪>>>签约提现接口>>>进入updateActiveRepayment()方法");
//		SinaResponse sinaResponse = new SinaResponse();
//		String orderNO = null;
//		try {
//			// AES 解密
//			// 根据appId 获取channelId
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道不存在");
//				logger.info(sessionId + ":新浪>>>签约提现接口orderChannel为空>>>" + JSON.toJSONString(orderChannel));
//				return sinaResponse;
//			}
//
//			boolean isCorrect = checkChannelId(orderChannel, SinaConstant.CHANNELID);
//			if (!isCorrect) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("渠道错误!");
//				return sinaResponse;
//			}
//
//			String requestJson = AESUtil.Decrypt(request, SinaConstant.AESKEY);
//			logger.info(sessionId + ":新浪>>>签约提现接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>签约提现接口requestJson为空>>>" + JSON.toJSONString(requestJson));
//				return sinaResponse;
//			}
//			// 获取基本参数
//			Map parseObject = JSON.parseObject(requestJson, Map.class);
//			if (null != parseObject) {
//				orderNO = String.valueOf(parseObject.get("orderNo"));
//			}
//			if (null == orderNO) {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>签约提现接口basicInfo为空>>>" + JSON.toJSONString(sinaResponse));
//				return sinaResponse;
//			}
//			// orderNO = String.valueOf(basicInfo.get("orderNo"));
//			logger.info(sessionId + ":新浪>>>开始签约提现updateRepayment()方法>>>");
//			DrainageRsp drainageRsp = commonService.updateSignContract(sessionId, orderNO); // 签约提现
//			Map<String, Object> mapRes = new HashMap<String, Object>();
//
//			if (null != drainageRsp) {
//				if ("0000".equals(drainageRsp.getCode())) {
//					sinaResponse.setCode(SinaResponse.RESULT_SUCCESS);
//					sinaResponse.setMsg("签约提现成功");
//					mapRes.put("sign_status", "701");
//					mapRes.put("sign_msg", "签约提现成功");
//					logger.info(sessionId + ":新浪>>>签约提现成功" + JSON.toJSONString(mapRes));
//
//				} else {
//					sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//					sinaResponse.setMsg("签约提现失败");
//					mapRes.put("sign_status", "702");
//					mapRes.put("sign_msg", "签约提现失败");
//					logger.info(sessionId + ":新浪>>>签约提现失败" + JSON.toJSONString(mapRes));
//				}
//
//				String response = AESUtil.Encrypt(JSON.toJSONString(mapRes), SinaConstant.AESKEY);
//				sinaResponse.setResponse(response);
//			} else {
//				sinaResponse.setCode(SinaResponse.RESULT_PARMERR);
//				sinaResponse.setMsg("操作失败");
//				logger.info(sessionId + ":新浪>>>签约提现失败drainageRsp为空" + JSON.toJSONString(drainageRsp));
//			}
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>签约提现提现接口Service异常", e);
//			sinaResponse.setCode(SinaResponse.RESULT_FAILERR);
//			sinaResponse.setMsg("系统异常,请稍后再试");
//		} finally {
//			SxyThirdInterfaceLogUtils.setSxyLog(SinaConstant.CHANNELID, orderNO, sinaResponse.getCode(),
//					sinaResponse.getMsg());
//		}
//		return sinaResponse;
//	}
//
//	/**
//	 * 获取签约信息
//	 * 
//	 * @see com.waterelephant.sxyDrainage.sina.service.SinaService#getSignInfo(java.lang.String, java.lang.String, long)
//	 */
//	@Override
//	public SinaResponse getSignInfo(String request, String appId, long sessionId) {
//		SinaResponse response = new SinaResponse();
//		String orderNo = null;
//		try {
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				response.setCode(SinaResponse.RESULT_PARMERR);
//				response.setMsg("渠道不存在");
//				logger.info(sessionId + ":新浪>>>获取签约信息接口orderChannel为空>>>");
//				return response;
//			}
//			boolean isCorrect = checkChannelId(orderChannel, SinaConstant.CHANNELID);
//			if (!isCorrect) {
//				response.setCode(SinaResponse.RESULT_PARMERR);
//				response.setMsg("渠道错误!");
//				return response;
//			}
//
//			String requestJson = AESUtil.Decrypt(request, SinaConstant.AESKEY);
//			logger.info(sessionId + ":新浪>>>获取签约信息接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				response.setCode(SinaResponse.RESULT_PARMERR);
//				response.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>获取签约信息接口requestJson为空>>>");
//				return response;
//			}
//
//			JSONObject parseObject = JSON.parseObject(requestJson);
//			if (null != parseObject) {
//				orderNo = parseObject.getString("orderNo");
//			}
//			if (StringUtils.isEmpty(orderNo)) {
//				response.setCode(SinaResponse.RESULT_PARMERR);
//				response.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>获取签约信息接口orderNo为空>>>");
//				return response;
//			}
//			List<Object> list = new ArrayList<>();
//			Map<String, Object> map = null;
//			Map<String, Object> hm = new HashMap<>();
//			String twoDecimal = null;
//			Long roundAmount = null;
//			BwOrder bwOrder = bwOrderService.findOrderNoByThirdOrderNo(orderNo);
//			Long orderId = bwOrder.getId();
//			
//			
//			
//			BwProductDictionary product = bwProductDictionaryService.queryByOrderId(orderId);
//			
//			
//			
//			Integer borrowNumber = bwOrder.getBorrowNumber();
//			Double borrowAmount = bwOrder.getBorrowAmount();
//			Date nowDate = new Date();
//			// 每期金额
//			double eachAmount = Math.floor(DoubleUtil.div(borrowAmount, Double.parseDouble(borrowNumber + "")));
//			// 最后一期金额
//			double lastAmount = eachAmount;
//			// 每期剩余本金
//			double leftAmount = borrowAmount;
//
//			for (int i = 1; i <= borrowNumber; i++) {
//				map = new HashMap<>();
//				if (borrowNumber == i) {
//					lastAmount = DoubleUtil.sub(borrowAmount, DoubleUtil.mul(eachAmount, (borrowNumber - 1)));
//					twoDecimal = DoubleUtil.toTwoDecimal(lastAmount + leftAmount * product.getInterestRate());
//					roundAmount = Math.round(NumberUtils.toDouble(twoDecimal));
//					map.put("repay_money", roundAmount * 100);
//				} else {
//					twoDecimal = DoubleUtil.toTwoDecimal(eachAmount + leftAmount * product.getInterestRate());
//					roundAmount = Math.round(NumberUtils.toDouble(twoDecimal));
//					map.put("repay_money", roundAmount * 100);
//				}
//				Date addDays = MyDateUtils.addDays(nowDate, 7 * i);
//				map.put("number", i);
//				map.put("repay_date", DateUtil.getDateString(addDays, "yyyy-MM-dd"));
//				list.add(map);
//				leftAmount = leftAmount - eachAmount;
//			}
//			hm.put("orderNo", orderNo);
//			hm.put("signInfors", list);
//
//			String jsonString = JSON.toJSONString(hm);
//			String data = AESUtil.Encrypt(jsonString, SinaConstant.AESKEY);
//
//			response.setMsg("SUCCESS");
//			response.setResponse(data);
//			response.setCode(SinaResponse.RESULT_SUCCESS);
//
//
//
//			logger.info(sessionId + ":新浪>>>执行service层拉取签约信息成功");
//
//			logger.info(JSON.toJSONString(hm, true));
//		} catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>获取签约信息接口Service异常", e);
//			response.setCode(SinaResponse.RESULT_SIGNERR);
//			response.setMsg("系统异常,请稍后再试");
//		}
//		return response;
//	}
//
//	
//	/**
//	 * 获取合同信息
//	 * 
//	 * @see com.waterelephant.sxyDrainage.sina.service.SinaService#getContractInfo(java.lang.String, java.lang.String, long)
//	 */
//	@Override
//	public SinaResponse getContractInfo(String request, String appId, long sessionId) {
//		SinaResponse response = new SinaResponse();
//		String orderNo = null;
//		Map<String, Object> hm = new HashMap<>();
//		try {
//			BwOrderChannel orderChannel = bwOrderChannelService.getOrderChannelByCode(appId);
//			if (null == orderChannel) {
//				response.setCode(SinaResponse.RESULT_PARMERR);
//				response.setMsg("渠道不存在");
//				logger.info(sessionId + ":新浪>>>获取合同信息接口orderChannel为空>>>");
//				return response;
//			}
//			boolean isCorrect = checkChannelId(orderChannel, SinaConstant.CHANNELID);
//			if (!isCorrect) {
//				response.setCode(SinaResponse.RESULT_PARMERR);
//				response.setMsg("渠道错误!");
//				return response;
//			}
//
//			String requestJson = AESUtil.Decrypt(request, SinaConstant.AESKEY);
//			logger.info(sessionId + ":新浪>>>获取合同信息接口>>>request请求数据:" + requestJson);
//			if (StringUtils.isBlank(requestJson)) {
//				response.setCode(SinaResponse.RESULT_PARMERR);
//				response.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>获取合同信息接口requestJson为空>>>");
//				return response;
//			}
//			JSONObject parseObject = JSON.parseObject(requestJson);
//			if(null != parseObject){
//				orderNo = parseObject.getString("orderNo");
//			}
//			if (StringUtils.isEmpty(orderNo)) {
//				response.setCode(SinaResponse.RESULT_PARMERR);
//				response.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>获取合同信息接口orderNo为空>>>");
//				return response;
//			}
//			BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
//			if (bwOrderRong == null) {
//				response.setCode(SinaResponse.RESULT_PARMERR);
//				response.setMsg("请求参数为空");
//				logger.info(sessionId + ":新浪>>>拉取合同接口bwOrderRong为空>>>");
//				return response;
//			}
//			Map<String, Object> contractMap = null;
//			List<Map<String, Object>> contractList = new ArrayList<Map<String, Object>>();
//			Long orderId = bwOrderRong.getOrderId();
//			List<BwAdjunct> bwAdjunctList = bwAdjunctService.findBwAdjunctByOrderId(orderId);
//			if (!CommUtils.isNull(bwAdjunctList)) {
//				logger.info(sessionId + ":新浪>>>开始封装合同>>>"+JSON.toJSONString(bwAdjunctList));
//				for (BwAdjunct bwAdjunct : bwAdjunctList) {
//					if (bwAdjunct.getAdjunctType() == 29) {
//						contractMap = new HashMap<>();
//						String adjunctPath = bwAdjunct.getAdjunctPath();
//						String conUrl = SystemConstant.PDF_URL + adjunctPath;
//						contractMap.put("name","小微金融水象分期信息咨询及信用管理服务合同");
//						contractMap.put("link",conUrl);
//						contractList.add(contractMap);
//					}
//					if (bwAdjunct.getAdjunctType() == 30) {
//						contractMap = new HashMap<>();
//						String adjunctPath = bwAdjunct.getAdjunctPath();
//						String conUrl = SystemConstant.PDF_URL + adjunctPath;
//						contractMap.put("name","征信及信息披露授权书");
//						contractMap.put("link",conUrl);
//						contractList.add(contractMap);
//					}
//				}
//				logger.info(sessionId + ":新浪>>>结束封装合同>>>");
//				
//			} else {
//				response.setCode(SinaResponse.RESULT_PARMERR);
//				response.setMsg("合同列表不存在");
//				logger.info(sessionId + ":新浪>>>拉取合同接口bwAdjunctList不存在>>>");
//				return response;
//			}
//			hm.put("orderNo", orderNo);
//			hm.put("contractUrls", contractList);
//			// 对响应参数AES加密
//			logger.info(sessionId + ":新浪>>>拉取合同接口>>>response加密前数据:" + JSON.toJSONString(contractList));
//			String data = AESUtil.Encrypt(JSON.toJSONString(hm), SinaConstant.AESKEY);
//			response.setResponse(data);
//			response.setCode(SinaResponse.RESULT_SUCCESS);
//			response.setMsg("拉取合同成功");
//			logger.info(sessionId + ":新浪>>>执行service层拉取合同成功");
//			logger.info(JSON.toJSONString(hm,true));
//		}catch (Exception e) {
//			logger.error(sessionId + ":新浪>>>获取合同信息接口Service异常", e);
//			response.setCode(SinaResponse.RESULT_SIGNERR);
//			response.setMsg("系统异常,请稍后再试");
//		}
//		return response;
//	}
//	
//	
//	
//	/**
//	 * 获取图片
//	 * 
//	 * @return
//	 */
//	public String getImgUrl(String base64Img, String fileName) {
//		/**
//		 * 取认证照片SinaIdentifyInfo
//		 */
//
//		InputStream inputStream = null;
//		try {
//			byte[] decode = Base64Utils.decode(base64Img);
//			inputStream = new ByteArrayInputStream(decode);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		try {
//			String str = SinaUtils.urlUpload(inputStream, fileName);
//			return str;
//		} catch (Exception e) {
//			logger.error("新浪取图片异常,异常原因:" + e);
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//}
