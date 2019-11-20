package com.waterelephant.baiqishi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.baiqishi.entity.ServiceResult91;
import com.beadwallet.service.baiqishi.service.BaiQiShiServiceSDK;
import com.waterelephant.baiqishi.entity.BqsDecision;
import com.waterelephant.baiqishi.entity.BqsHitRule;
import com.waterelephant.baiqishi.entity.BqsStrategy;
import com.waterelephant.baiqishi.json.DecisionJSON;
import com.waterelephant.baiqishi.json.HitRuleJSON;
import com.waterelephant.baiqishi.json.StrategyJSON;
import com.waterelephant.baiqishi.service.BaiQiShiService;
import com.waterelephant.baiqishi.service.BqsDecisionService;
import com.waterelephant.baiqishi.service.BqsHitRuleService;
import com.waterelephant.baiqishi.service.BqsStrategyService;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.faceID.entity.IdentityCard;
import com.waterelephant.faceID.service.IdentityCardService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwPersonInfoService;
import com.waterelephant.service.IBwWorkInfoService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;

/**
 * 白骑士
 *
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/23 10:40
 */
@Service
public class BaiQiShiServiceImpl implements BaiQiShiService {

	private Logger logger = Logger.getLogger(BaiQiShiServiceImpl.class);

	@Autowired
	private BqsDecisionService bqsDecisionService;
	@Autowired
	private BqsHitRuleService bqsHitRuleService;
	@Autowired
	private BqsStrategyService bqsStrategyService;
	@Autowired
	private BwRejectRecordService bwRejectRecordService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private IBwWorkInfoService bwWorkInfoService;
	@Autowired
	private IdentityCardService bwIdentityCardService;
	@Autowired
	private IBwPersonInfoService bwPersonInfoService;
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private BwBorrowerService bwBorrowerService;
	private boolean isIn;

	@Override
	public AppResponseResult decision(Map params) throws Exception {
		AppResponseResult appResponseResult = new AppResponseResult();

		try {
			String eventType = params.get("eventType") + ""; // 事件类型
			if (CommUtils.isNull(eventType)) {
				appResponseResult.setCode("9201");
				appResponseResult.setMsg("类型为空");
				return appResponseResult;
			}

			ServiceResult91 serviceResult = BaiQiShiServiceSDK.decision(params); // 请求SDK
			JSONObject obj = (JSONObject) serviceResult.getObj();
			String s = JSONObject.toJSONString(obj);
			// String objStr = obj.toString();
			DecisionJSON decisionReturnEntity = JSONObject.parseObject(s, DecisionJSON.class);
			if ("BQS000".equals(decisionReturnEntity.getResultCode())) {
				String finalDecision = decisionReturnEntity.getFinalDecision();
				appResponseResult.setCode("200");
				appResponseResult.setMsg("ok");
				appResponseResult.setResult(finalDecision);
				BqsDecision bqsDecision = new BqsDecision();
				bqsDecision.setEventType(params.get("eventType") + "");
				// bqsDecision.setBorrowerId(params.get("borrowerId"+""));
			} else {
				appResponseResult.setCode(decisionReturnEntity.getResultCode());
				appResponseResult.setResult(decisionReturnEntity.getResultDesc());
			}
		} catch (Exception e) {
			logger.error("  ~~~~~~~~~~~~~~~  白骑士请求：", e);
		}
		return appResponseResult;

	}

	/**
	 * 根据传参封装请求参数
	 *
	 * @param borrowerId
	 * @param orderId
	 * @param eventType
	 * @param otherMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean saveBaiQiShi(Long borrowerId, Long orderId, String eventType, String tokenKey,
			Map<String, String> otherMap) throws Exception {
		boolean isIn = false;
		try {
			Map<String, String> reqMap = new HashMap<>();
			reqMap.put("eventType", eventType); // 事件信息

			// if(!CommUtils.isNull(orderId)) {
			// tokenKey = RedisUtils.get("BaiQiShi:tokenKey:" + orderId);
			// if (!CommUtils.isNull(tokenKey)) {
			// reqMap.put("tokenKey", tokenKey);
			// }
			// }

			// 设备指纹
			if (!CommUtils.isNull(tokenKey)) {
				reqMap.put("tokenKey", tokenKey);
			}
			// 个人信息 银行卡信息
			if (!CommUtils.isNull(borrowerId)) {
				BwBorrower bwBorrowerQ = new BwBorrower();
				bwBorrowerQ.setId(borrowerId);
				BwBorrower borrower = bwBorrowerService.findBwBorrowerByAttr(bwBorrowerQ);
				reqMap = getBorrowerInfo(borrower, orderId, reqMap); // 个人信息
				reqMap = getBankInfo(borrower, reqMap); // 银行信息
			}

			StringBuffer stringBuffer = new StringBuffer();
			Set set = reqMap.entrySet();
			Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);
			for (int i = 0; i < entries.length; i++) {
				stringBuffer.append("Key:" + entries[i].getKey() + ";　");
				stringBuffer.append("Value:" + entries[i].getValue() + ";   ");
			}
			logger.info(" ~~~~~~~~~~~~~~~~~~~~ 白骑士反欺诈认 证事件入参：" + stringBuffer);
			ServiceResult91 serviceResult = BaiQiShiServiceSDK.decision(reqMap); // 请求SDK
			String obj = (String) serviceResult.getObj();
			logger.info(" ~~~~~~~~~~~~~~~~~~~~~~~　白骑士反欺诈认 证事件出参：" + obj);
			JSONObject jsonObject = (JSONObject) JSONObject.parse(obj);
			String result = (String) jsonObject.get("obj");
			DecisionJSON decisionReturnEntity = JSONObject.parseObject(result, DecisionJSON.class);
			if (decisionReturnEntity != null) {
				if ("BQS000".equals(decisionReturnEntity.getResultCode())) {
					String finalDecision = decisionReturnEntity.getFinalDecision();
					boolean sd = saveBaiqishiReturn(decisionReturnEntity, borrowerId, orderId, eventType); // 返回数据入库
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isIn;
	}

	/**
	 * 封装个人信息
	 *
	 * @param bwBorrower
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	private Map getBorrowerInfo(BwBorrower bwBorrower, Long orderId, Map<String, String> reqMap) {
		BwWorkInfo bwWorkInfo = null;
		IdentityCard bwIdentityCard = null;
		BwPersonInfo bwPersonInfo = null;
		try {
			if (!CommUtils.isNull(orderId)) {
				bwWorkInfo = bwWorkInfoService.findBwWorkInfoByOrderId(orderId);
				bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
			}
			bwIdentityCard = bwIdentityCardService.queryIdentityCard(bwBorrower.getBorrowerId());
		} catch (Exception e) {
			logger.error("白骑士查询本地数据库异常：", e);
		}
		if (bwBorrower != null) {
			if (!CommUtils.isNull(bwBorrower.getFuiouAcct())) {
				reqMap.put("account", bwBorrower.getFuiouAcct());
			}
			if (!CommUtils.isNull(bwBorrower.getName())) {
				reqMap.put("name", bwBorrower.getName());
			}
			if (!CommUtils.isNull(bwBorrower.getPhone())) {
				reqMap.put("mobile", bwBorrower.getPhone());
			}
			if (!CommUtils.isNull(bwBorrower.getIdCard())) {
				reqMap.put("certNo", bwBorrower.getIdCard());
			}
			if (!CommUtils.isNull(bwBorrower.getRegisterAddr())) {
				reqMap.put("address", bwBorrower.getRegisterAddr());
			}
		}
		if (bwPersonInfo != null) {
			if (!CommUtils.isNull(bwPersonInfo.getRelationName())) {
				reqMap.put("contactsName", bwPersonInfo.getRelationName());
			}
			if (!CommUtils.isNull(bwPersonInfo.getRelationPhone())) {
				reqMap.put("contactsMobile", bwPersonInfo.getRelationPhone());
			}
			if (!CommUtils.isNull(bwPersonInfo.getMarryStatus())) {
				reqMap.put("marriage", bwPersonInfo.getMarryStatus() + "");
			}
		}
		if (bwWorkInfo != null) {
			if (!CommUtils.isNull(bwWorkInfo.getComName())) {
				reqMap.put("organization", bwWorkInfo.getComName());
			}
		}
		if (bwIdentityCard != null) {
			if (!CommUtils.isNull(bwIdentityCard.getAddress())) {
				reqMap.put("residence", bwIdentityCard.getAddress());
			}
		}
		return reqMap;
	}

	/**
	 * 封装银行卡信息
	 *
	 * @param bwBorrower
	 * @return
	 * @throws Exception
	 */
	private Map getBankInfo(BwBorrower bwBorrower, Map<String, String> reqMap) {
		BwBankCard bwBankCard = null;
		try {
			bwBankCard = bwBankCardService.findBwBankCardByBorrowerId(bwBorrower.getId());
		} catch (Exception e) {
			logger.error("白骑士查询本地数据库异常：", e);
		}

		if (bwBankCard != null) {
			// 银行卡信息
			if (!CommUtils.isNull(bwBankCard.getBankName())) {
				reqMap.put("bankCardName", bwBankCard.getBankName());
			}
			if (!CommUtils.isNull(bwBankCard.getCardNo())) {
				reqMap.put("bankCardNo", bwBankCard.getCardNo());
			}
			if (!CommUtils.isNull(bwBankCard.getBankCode())) {
				reqMap.put("bankCardCode", bwBankCard.getBankCode());
			}
		}
		return reqMap;
	}

	/**
	 * 保存白骑士数据
	 *
	 * @param decisionJSON
	 * @return
	 */
	private boolean saveBaiqishiReturn(DecisionJSON decisionJSON, Long borrowerId, Long orderId, String eventType) {
		try {

			BqsDecision bqsDecision = new BqsDecision();
			bqsDecision.setBorrowerId(borrowerId);
			bqsDecision.setOrderId(orderId);
			bqsDecision.setEventType(eventType);
			bqsDecisionService.deleteBqsDecision(bqsDecision);
			bqsDecision.setCreateTime(new Date());
			bqsDecision.setFinalDecision(decisionJSON.getFinalDecision());
			bqsDecision.setFinalScore(decisionJSON.getFinalScore());
			bqsDecision.setFlowNo(decisionJSON.getFlowNo());
			bqsDecision.setResultCode(decisionJSON.getResultCode());
			bqsDecision.setResultDesc(decisionJSON.getResultDesc());
			bqsDecision.setEventType(eventType);
			bqsDecision.setOrderId(orderId);
			bqsDecisionService.saveBqsDecision(bqsDecision);

			// BqsStrategy bqsStrategyD = new BqsStrategy();
			// bqsStrategyD.setBorrowerId(borrowerId);
			// bqsStrategyService.deleteBqsStrategy(bqsStrategyD);
			// BqsHitRule bqsHitRuleD = new BqsHitRule();
			// bqsHitRuleD.setBorrowerId(borrowerId);
			// bqsHitRuleService.deleteBqsHitRule(bqsHitRuleD);
			List<StrategyJSON> strategyJSONList = decisionJSON.getStrategySet();
			if (strategyJSONList != null && strategyJSONList.size() > 0) {
				for (StrategyJSON strategyJSON : strategyJSONList) {
					BqsStrategy bqsStrategy = new BqsStrategy();
					bqsStrategy.setBorrowerId(borrowerId);
					bqsStrategy.setCreateTime(new Date());
					bqsStrategy.setDecisionId(bqsDecision.getId());
					bqsStrategy.setRejectValue(strategyJSON.getRejectValue());
					bqsStrategy.setReviewValue(strategyJSON.getReviewValue());
					bqsStrategy.setRiskType(strategyJSON.getRiskType());
					bqsStrategy.setStrategyDecision(strategyJSON.getStrategyDecision());
					bqsStrategy.setStrategyId(strategyJSON.getStrategyId());
					bqsStrategy.setStrategyMode(strategyJSON.getStrategyMode());
					bqsStrategy.setStrategyName(strategyJSON.getStrategyName());
					bqsStrategy.setStrategyScore(strategyJSON.getStrategyScore());
					bqsStrategy.setTips(strategyJSON.getTips());
					bqsStrategyService.saveBqsStrategy(bqsStrategy);
					List<HitRuleJSON> hitRuleJSONSList = strategyJSON.getHitRules();
					if (hitRuleJSONSList != null && hitRuleJSONSList.size() > 0) {
						for (HitRuleJSON hitRuleJSON : hitRuleJSONSList) {
							BqsHitRule bqsHitRule = new BqsHitRule();
							bqsHitRule.setBorrowerId(borrowerId);
							bqsHitRule.setCreateTime(new Date());
							bqsHitRule.setStrategyId(bqsStrategy.getId());
							bqsHitRule.setRuleId(hitRuleJSON.getRuleId());
							bqsHitRule.setRuleName(hitRuleJSON.getRuleName());
							bqsHitRule.setScore(hitRuleJSON.getScore());
							bqsHitRule.setMemo(hitRuleJSON.getMemo());
							bqsHitRule.setDecision(hitRuleJSON.getDecision());
							bqsHitRuleService.saveBqsHitRule(bqsHitRule);
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("白骑士保存回参数据出错：", e);
			return false;
		}

	}

	/**
	 * 获取所有的信息
	 *
	 * @param request
	 * @param reqMap
	 * @return
	 */
	private Map<String, String> getReqMap(HttpServletRequest request, Map<String, String> reqMap) {

		String tokenKey = request.getParameter("tokenKey"); // 当前会话标识，用于事件中关联设备信息
		String occurTime = request.getParameter("occurTime"); // 事件发生时间，格式yyyy-MM-dd HH:mm:ss
		String platform = request.getParameter("platform"); // 应用平台类型，h5/web/ios/android

		if (!CommUtils.isNull(tokenKey)) {
			reqMap.put("tokenKey", tokenKey);
		}
		if (!CommUtils.isNull(occurTime)) {
			reqMap.put("occurTime", occurTime);
		}
		if (!CommUtils.isNull(platform)) {
			reqMap.put("platform", platform);
		}

		// 用户信息
		String account = request.getParameter("account"); // 用户账号
		String name = request.getParameter("name"); // 用户姓名
		String email = request.getParameter("email"); // 用户邮箱
		String mobile = request.getParameter("mobile"); // 用户手机号
		String certNo = request.getParameter("certNo"); // 用户身份证号
		String address = request.getParameter("address"); // 用户住址
		String addressCity = request.getParameter("addressCity"); // 用户所在城市
		String contactsName = request.getParameter("contactsName"); // 用户联系人姓名
		String contactsMobile = request.getParameter("contactsMobile"); // 用户联系人电话
		String organization = request.getParameter("organization"); // 用户工作单位名称
		String organizationAddress = request.getParameter("organizationAddress"); // 用户工作单位地址
		String education = request.getParameter("education"); // 学历
		String graduateSchool = request.getParameter("graduateSchool"); // 毕业院校名称
		String graduateCity = request.getParameter("graduateCity"); // 毕业院校城市
		String marriage = request.getParameter("marriage"); // 是否已婚
		String residence = request.getParameter("residence"); // 户籍所在地
		if (!CommUtils.isNull(account)) {
			reqMap.put("account", account);
		}
		if (!CommUtils.isNull(name)) {
			reqMap.put("name", name);
		}
		if (!CommUtils.isNull(email)) {
			reqMap.put("email", email);
		}
		if (!CommUtils.isNull(mobile)) {
			reqMap.put("mobile", mobile);
		}
		if (!CommUtils.isNull(certNo)) {
			reqMap.put("certNo", certNo);
		}
		if (!CommUtils.isNull(address)) {
			reqMap.put("address", address);
		}
		if (!CommUtils.isNull(addressCity)) {
			reqMap.put("addressCity", addressCity);
		}
		if (!CommUtils.isNull(contactsName)) {
			reqMap.put("contactsName", contactsName);
		}
		if (!CommUtils.isNull(contactsMobile)) {
			reqMap.put("contactsMobile", contactsMobile);
		}
		if (!CommUtils.isNull(organization)) {
			reqMap.put("organization", organization);
		}
		if (!CommUtils.isNull(organizationAddress)) {
			reqMap.put("organizationAddress", organizationAddress);
		}
		if (!CommUtils.isNull(education)) {
			reqMap.put("education", education);
		}
		if (!CommUtils.isNull(graduateSchool)) {
			reqMap.put("graduateSchool", graduateSchool);
		}
		if (!CommUtils.isNull(graduateCity)) {
			reqMap.put("graduateCity", graduateCity);
		}
		if (!CommUtils.isNull(marriage)) {
			reqMap.put("marriage", marriage);
		}
		if (!CommUtils.isNull(residence)) {
			reqMap.put("residence", residence);
		}

		// 收货人
		String deliverName = request.getParameter("deliverName"); // 收货人
		String deliverMobileNo = request.getParameter("deliverMobileNo"); // 收货人手机号
		String deliverAddressStreet = request.getParameter("deliverAddressStreet"); // 收货人街道地址信息
		String deliverAddressCounty = request.getParameter("deliverAddressCounty"); // 收货人县或区信息
		String deliverAddressCity = request.getParameter("deliverAddressCity"); // 收货人城市信息
		String deliverAddressProvince = request.getParameter("deliverAddressProvince"); // 收货人省份信息
		String deliverAddressCountry = request.getParameter("deliverAddressCountry"); // 收货人国家信息

		if (!CommUtils.isNull(deliverName)) {
			reqMap.put("deliverName", deliverName);
		}
		if (!CommUtils.isNull(deliverMobileNo)) {
			reqMap.put("deliverMobileNo", deliverMobileNo);
		}
		if (!CommUtils.isNull(deliverAddressStreet)) {
			reqMap.put("deliverAddressStreet", deliverAddressStreet);
		}
		if (!CommUtils.isNull(deliverAddressCounty)) {
			reqMap.put("deliverAddressCounty", deliverAddressCounty);
		}
		if (!CommUtils.isNull(deliverAddressCity)) {
			reqMap.put("deliverAddressCity", deliverAddressCity);
		}
		if (!CommUtils.isNull(deliverAddressProvince)) {
			reqMap.put("deliverAddressProvince", deliverAddressProvince);
		}
		if (!CommUtils.isNull(deliverAddressCountry)) {
			reqMap.put("deliverAddressCountry", deliverAddressCountry);
		}

		// 订单信息
		String orderId = request.getParameter("orderId"); // 订单号
		String payeeAccount = request.getParameter("payeeAccount"); // 卖家或收款人账号
		String payeeName = request.getParameter("payeeName"); // 卖家或收款人姓名
		String payeeEmail = request.getParameter("payeeEmail"); // 卖家或收款人邮箱
		String payeeMobile = request.getParameter("payeeMobile"); // 卖家或收款人手机
		String payeePhone = request.getParameter("payeePhone"); // 卖家或收款人座机
		String payeeIdNumber = request.getParameter("payeeIdNumber"); // 卖家或收款人身份证
		String payeeCardNumber = request.getParameter("payeeCardNumber"); // 卖家或收款人银行卡号
		String amount = request.getParameter("amount"); // 金额（通用）
		String items = request.getParameter("items"); // 商品详情/清单
		String itemNum = request.getParameter("itemNum"); // 商品数量
		String itemAmount = request.getParameter("itemAmount"); // 商品金额
		String payMethod = request.getParameter("payMethod"); // 支付方式
		String payAmount = request.getParameter("payAmount"); // 支付金额
		String payAccount = request.getParameter("payAccount"); // 支付卡号
		String merchantName = request.getParameter("merchantName"); // 商户名称
		String bizLicense = request.getParameter("bizLicense"); // 商户工商注册号
		String orgCode = request.getParameter("orgCode"); // 组织机构代码

		if (!CommUtils.isNull(orderId)) {
			reqMap.put("orderId", orderId);
		}
		if (!CommUtils.isNull(payeeAccount)) {
			reqMap.put("payeeAccount", payeeAccount);
		}
		if (!CommUtils.isNull(payeeName)) {
			reqMap.put("payeeName", payeeName);
		}
		if (!CommUtils.isNull(payeeEmail)) {
			reqMap.put("payeeEmail", payeeEmail);
		}
		if (!CommUtils.isNull(payeeMobile)) {
			reqMap.put("payeeMobile", payeeMobile);
		}
		if (!CommUtils.isNull(payeePhone)) {
			reqMap.put("payeePhone", payeePhone);
		}
		if (!CommUtils.isNull(payeeIdNumber)) {
			reqMap.put("payeeIdNumber", payeeIdNumber);
		}
		if (!CommUtils.isNull(payeeCardNumber)) {
			reqMap.put("payeeCardNumber", payeeCardNumber);
		}
		if (!CommUtils.isNull(amount)) {
			reqMap.put("amount", amount);
		}
		if (!CommUtils.isNull(items)) {
			reqMap.put("items", items);
		}
		if (!CommUtils.isNull(itemNum)) {
			reqMap.put("itemNum", itemNum);
		}
		if (!CommUtils.isNull(itemAmount)) {
			reqMap.put("itemAmount", itemAmount);
		}
		if (!CommUtils.isNull(payMethod)) {
			reqMap.put("payMethod", payMethod);
		}
		if (!CommUtils.isNull(payAmount)) {
			reqMap.put("payAmount", payAmount);
		}
		if (!CommUtils.isNull(payAccount)) {
			reqMap.put("payAccount", payAccount);
		}
		if (!CommUtils.isNull(merchantName)) {
			reqMap.put("merchantName", merchantName);
		}
		if (!CommUtils.isNull(bizLicense)) {
			reqMap.put("bizLicense", bizLicense);
		}
		if (!CommUtils.isNull(orgCode)) {
			reqMap.put("orgCode", orgCode);
		}

		// 银行卡号信息
		String userBankBin = request.getParameter("userBankBin"); // BIN卡号
		String bankCardNo = request.getParameter("bankCardNo"); // 银行卡卡号
		String bankCardType = request.getParameter("bankCardType"); // 银行卡类型
		String bankCardCode = request.getParameter("bankCardCode"); // 银行编码
		String bankCardName = request.getParameter("bankCardName"); // 银行户名
		String bankCardMobile = request.getParameter("bankCardMobile"); // 银行卡预留手机号
		String creditCardNo = request.getParameter("creditCardNo"); // 信用卡卡号
		String creditCardName = request.getParameter("creditCardName"); // 信用卡户名
		String creditCardMobile = request.getParameter("creditCardMobile"); // 信用卡预留手机号

		if (!CommUtils.isNull(userBankBin)) {
			reqMap.put("userBankBin", userBankBin);
		}
		if (!CommUtils.isNull(bankCardNo)) {
			reqMap.put("bankCardNo", bankCardNo);
		}
		if (!CommUtils.isNull(bankCardType)) {
			reqMap.put("bankCardType", bankCardType);
		}
		if (!CommUtils.isNull(bankCardCode)) {
			reqMap.put("bankCardCode", bankCardCode);
		}
		if (!CommUtils.isNull(bankCardName)) {
			reqMap.put("bankCardName", bankCardName);
		}
		if (!CommUtils.isNull(bankCardMobile)) {
			reqMap.put("bankCardMobile", bankCardMobile);
		}
		if (!CommUtils.isNull(creditCardNo)) {
			reqMap.put("creditCardNo", creditCardNo);
		}
		if (!CommUtils.isNull(creditCardName)) {
			reqMap.put("creditCardName", creditCardName);
		}
		if (!CommUtils.isNull(creditCardMobile)) {
			reqMap.put("creditCardMobile", creditCardMobile);
		}

		// 设备信息
		String userAgentCust = request.getParameter("userAgentCust"); // 浏览器UA
		String referCust = request.getParameter("referCust"); // Referer
		String ip = request.getParameter("ip"); // IP地址
		String mac = request.getParameter("mac"); // MAC地址
		String imei = request.getParameter("imei"); // IMEI
		String longitude = request.getParameter("longitude"); // 经度
		String latitude = request.getParameter("latitude"); // 纬度
		String bizResult = request.getParameter("bizResult"); // 客户业务处理结果
		if (!CommUtils.isNull(userAgentCust)) {
			reqMap.put("userAgentCust", userAgentCust);
		}
		if (!CommUtils.isNull(referCust)) {
			reqMap.put("referCust", referCust);
		}
		if (!CommUtils.isNull(ip)) {
			reqMap.put("ip", ip);
		}
		if (!CommUtils.isNull(mac)) {
			reqMap.put("mac", mac);
		}
		if (!CommUtils.isNull(imei)) {
			reqMap.put("imei", imei);
		}
		if (!CommUtils.isNull(longitude)) {
			reqMap.put("longitude", longitude);
		}
		if (!CommUtils.isNull(latitude)) {
			reqMap.put("latitude", latitude);
		}
		if (!CommUtils.isNull(bizResult)) {
			reqMap.put("bizResult", bizResult);
		}

		return reqMap;
	}

	@Override
	public Long saveBaiqishiExternal(DecisionJSON decisionJSON, Long borrowerId, Long orderId, String eventType) {
		Long id = 0L;
		try {

			BqsDecision bqsDecision = new BqsDecision();
			bqsDecision.setBorrowerId(borrowerId);
			bqsDecision.setOrderId(orderId);
			bqsDecision.setEventType(eventType);
			bqsDecisionService.deleteBqsDecision(bqsDecision);
			bqsDecision.setCreateTime(new Date());
			bqsDecision.setFinalDecision(decisionJSON.getFinalDecision());
			bqsDecision.setFinalScore(decisionJSON.getFinalScore());
			bqsDecision.setFlowNo(decisionJSON.getFlowNo());
			bqsDecision.setResultCode(decisionJSON.getResultCode());
			bqsDecision.setResultDesc(decisionJSON.getResultDesc());
			bqsDecision.setEventType(eventType);
			bqsDecision.setOrderId(orderId);
			bqsDecisionService.saveBqsDecision(bqsDecision);

			id = bqsDecision.getId();

			List<StrategyJSON> strategyJSONList = decisionJSON.getStrategySet();
			if (strategyJSONList != null && strategyJSONList.size() > 0) {
				for (StrategyJSON strategyJSON : strategyJSONList) {
					BqsStrategy bqsStrategy = new BqsStrategy();
					bqsStrategy.setBorrowerId(borrowerId);
					bqsStrategy.setCreateTime(new Date());
					bqsStrategy.setDecisionId(bqsDecision.getId());
					bqsStrategy.setRejectValue(strategyJSON.getRejectValue());
					bqsStrategy.setReviewValue(strategyJSON.getReviewValue());
					bqsStrategy.setRiskType(strategyJSON.getRiskType());
					bqsStrategy.setStrategyDecision(strategyJSON.getStrategyDecision());
					bqsStrategy.setStrategyId(strategyJSON.getStrategyId());
					bqsStrategy.setStrategyMode(strategyJSON.getStrategyMode());
					bqsStrategy.setStrategyName(strategyJSON.getStrategyName());
					bqsStrategy.setStrategyScore(strategyJSON.getStrategyScore());
					bqsStrategy.setTips(strategyJSON.getTips());
					bqsStrategyService.saveBqsStrategy(bqsStrategy);
					List<HitRuleJSON> hitRuleJSONSList = strategyJSON.getHitRules();
					if (hitRuleJSONSList != null && hitRuleJSONSList.size() > 0) {
						for (HitRuleJSON hitRuleJSON : hitRuleJSONSList) {
							BqsHitRule bqsHitRule = new BqsHitRule();
							bqsHitRule.setBorrowerId(borrowerId);
							bqsHitRule.setCreateTime(new Date());
							bqsHitRule.setStrategyId(bqsStrategy.getId());
							bqsHitRule.setRuleId(hitRuleJSON.getRuleId());
							bqsHitRule.setRuleName(hitRuleJSON.getRuleName());
							bqsHitRule.setScore(hitRuleJSON.getScore());
							bqsHitRule.setMemo(hitRuleJSON.getMemo() == null ? "1" : hitRuleJSON.getMemo());
							bqsHitRule.setDecision(hitRuleJSON.getDecision());
							bqsHitRuleService.saveBqsHitRule(bqsHitRule);
						}
					}
				}
			}
			return id;
		} catch (Exception e) {
			logger.error("白骑士保存回参数据出错：", e);
			return id;
		}
	}

	public static void main(String[] args) {
		String str = "{ \"finalDecision\": \"Reject\", \"resultCode\": \"BQS000\", \"flowNo\": \"16042017097EBDFC08063246F5B2E8006578F87F11\", \"finalScore\":\"25\", \"strategySet\": [ { \"riskType\": \"creditRisk\", \"strategyDecision\": \"Reject\", \"strategyId\": \"88d8b4785e99467393e559464c8b8540\", \"strategyMode\": \"WorstMode\", \"strategyName\": \"失信风险策略\", \"strategyScore\": 0, \"tips\": \"自定义击中提示语 1\", \"hitRules\": [ {  \"decision\": \"Review\", \"detail\": [ { \"firstType\":\"信贷行业\", \"grade\":\"中风险\", \"secondType \":\"曾经逾期(0 ~ 30 天)\" } ],  \"ruleId\": \"126\",  \"ruleName\": \"身份证比对信贷行业曾经逾期30天内中风险名单\", \"score\": 0, \"memo\":\"中风险-信贷行业-曾经逾期(0 ~ 30 天)\", \"template\": \"compare\" }, {  \"decision\": \"Reject\", \"detail\": [ { \"name\":\"总数\", \"type\":\"all\", \"value \":\"10\" }, { \"name\":\"持牌消费金融\", \"type\":\"single\", \"value \":\"4\" }, { \"name\":\"助贷机构\", \"type\":\"single\", \"value \":\"6\" } ],  \"ruleId\": \"125\", \"ruleName\": \"多头借贷\", \"score\": 0, \"memo\":\"总数:10,持牌消费金融:4, 助贷机构:6\", \"template\": \"multiLoan\" } ] }, { \"riskType\": \"garbageRegister\", \"strategyDecision\": \"Review\", \"strategyId\": \"01175495d32c4ae2b963d26cbd248633\", \"strategyMode\": \"WeightMode\", \"strategyName\": \"垃圾注册风险策略\", \"rejectValue\":80, \"reviewValue\":20, \"strategyScore\":25, \"tips\": \"自定义击中提示语 2\", \"hitRules\": [ {  \"ruleId\": \"124\",  \"ruleName\": \"1 天内同设备关联过多的 IP 进行注册\", \"score\": 25 } ] } ] }";
		DecisionJSON decisionReturnEntity = JSONObject.parseObject(str, DecisionJSON.class);
		System.out.println(decisionReturnEntity.getStrategySet().get(0).getHitRules().get(0).getDetail());
	}
}
