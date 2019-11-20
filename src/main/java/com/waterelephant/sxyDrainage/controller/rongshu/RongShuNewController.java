//package com.waterelephant.sxyDrainage.controller.rongshu;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.beadwallet.utils.JsonUtils;
//import com.waterelephant.drainage.entity.rongShu.RongShuResponse;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.entity.BwProductDictionary;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.BwPlatformRecordService;
//import com.waterelephant.service.BwProductDictionaryService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.service.IBwRepaymentPlanService;
//import com.waterelephant.service.IBwRepaymentService;
//import com.waterelephant.service.impl.BwBorrowerService;
//import com.waterelephant.service.impl.BwOrderService;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.rongshu.BankCardNewInfo;
//import com.waterelephant.sxyDrainage.entity.rongshu.RsCheckUserInfo;
//import com.waterelephant.sxyDrainage.entity.rongshu.RsRequest;
//import com.waterelephant.sxyDrainage.entity.rongshu.RsResponse;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.RongShuNewService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.rongshu.RongShuConstant;
//import com.waterelephant.sxyDrainage.utils.rongshu.RongShuUtils;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.third.service.ThirdService;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//
///**
// * 榕树
// * 
// * Module:
// * 
// * RongShuNewController.java
// *
// * @author zhangyuan
// * @version 1.0
// * @description: <榕树>
// * @since JDK 1.8
// */
//@Controller
//public class RongShuNewController {
//
//	private Logger logger = Logger.getLogger(RongShuNewController.class);
//	// private static final int CHANNELID = 551;
//	//private static String RONGSHU_XUDAI = "xudai:order_id";
//	// private static String APPID = "1009";
//	//private static String BASE_USER_INFO_REDIS = "rongshu:userInfo";
//	private static String PUBKEY = RongShuConstant.PUBKEY;
//
//	@Autowired
//	private RongShuNewService rongShuNewService;
//	@Autowired
//	private BwOrderService bwOrderService;
//	@Autowired
//	private IBwRepaymentService bwRepaymentService;
//	@Autowired
//	private BwOrderRongService bwOrderRongService;
//	@Autowired
//	private IBwBankCardService bwBankCardService;
//	@Autowired
//	private BwBorrowerService bwBorrowerService;
//	@Autowired
//	private IBwRepaymentPlanService bwRepaymentPlanService;
//	@Autowired
//	private BwOverdueRecordService bwOverdueRecordService;
//	@Autowired
//	private ThirdCommonService thirdCommonService;
//	@Autowired
//	private BwPlatformRecordService bwPlatformRecordService;
//	@Autowired
//	private ThirdService thirdService;
//	@Autowired
//	private BwProductDictionaryService bwProductDictionaryService;
//	@Autowired
//	private CommonService commonService;
//	@Autowired
//	private BwOperateBasicService bwOperateBasicService;
//	@Autowired
//	private IBwPersonInfoService bwPersonInfoService;
//
//	/**
//	 * 榕树 - 存量用户检验接口
//	 *
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/sxy/rongShu/userCheck.do")
//	@ResponseBody
//	public RsResponse checkUser(@RequestBody RsRequest request) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入榕树 存量用户检验接口");
//		RsCheckUserInfo checkUserInfo = new RsCheckUserInfo();
//		//RsRequest rsRequest = new RsRequest();
//		RsResponse rsResponse = new RsResponse();
//		try {
//
//			if (null==request) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("接收参数对象为空");
//				logger.info(sessionId + "结束榕树 存量用户检验接口，参数对象为空，返回结果：" + request);
//				return rsResponse;
//			}
//			String sign = request.getSign();
//			String channelId = request.getChannelId();
//			String requestData = request.getRequest();
//			Long timestamp = request.getTimestamp();
//			if (StringUtils.isEmpty(channelId)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数channelId为空");
//				logger.info(sessionId + "结束榕树 存量用户检验接口，参数channelId为空：" + channelId);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(sign)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数sign为空");
//				logger.info(sessionId + "结束榕树 存量用户检验接口，参数sign为空：" + sign);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(requestData)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数request为空");
//				logger.info(sessionId + "结束榕树 存量用户检验接口，参数request为空：" + requestData);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数timestamp为空");
//				logger.info(sessionId + "结束榕树 存量用户检验接口，参数timestamp为空：" + timestamp);
//				return rsResponse;
//			}
//			Map<String, String> paramMap = new TreeMap<String, String>();
//			paramMap.put("channelId", channelId);
//			paramMap.put("request", requestData);
//			paramMap.put("timestamp", String.valueOf(timestamp));
//			// 验签
//			boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//			logger.info("存量用户检验接口验签结果：" + isOk); 
//			if (!isOk) {
//				rsResponse.setCode(RsResponse.CODE_SIGNERR);
//				rsResponse.setMessage("验签不通过");
//				logger.info(sessionId + "结束榕树存量用户检验接口接口异常，返回结果验签不通过,参数sign:"+sign);
//				return rsResponse;
//			}
//			
//			//解开三元信息
//			Map<String,String> map = JSON.parseObject(requestData, Map.class);
//			String name = String.valueOf(map.get("name"));
//			String phone = String.valueOf(map.get("phone"));
//			String cid = String.valueOf(map.get("cid"));
//			if(null!=phone && phone.length()>7){
//				phone = phone.substring(0, 7);
//			}
//			if(null!=cid && cid.length()>13){
//				cid = cid.substring(0,13);
//			}
//			//判断增量用户
//			BwBorrower bw = bwBorrowerService.oldUserFilter(phone,cid,name);
//			if(null!=bw){
//				//老用户
//				checkUserInfo.setIsStock(1);//增量用户0否1是
//				// 是否绑卡
//				BwBankCard bwBankCard = new BwBankCard();
//					bwBankCard.setBorrowerId(bw.getId());
//					bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
//					// 对于已绑卡用户要回传银行名、卡号、预留手机号
//					if (bwBankCard != null && (bwBankCard.getSignStatus() == 1)) {
//						checkUserInfo.setBankName(bwBankCard.getBankName());
//						checkUserInfo.setBankCardNum(bwBankCard.getCardNo());
//						checkUserInfo.setPhone(bwBankCard.getPhone());
//					}
//			}else{
//				checkUserInfo.setIsStock(0);
//			}
//			
//			// 第三步：用户检验
//			RsResponse checkResult = rongShuNewService.checkUser(sessionId, name, phone+"%", cid+"%");
//			int resultCode = checkResult.getCode();
//			
//			if(0!=resultCode){
//				if(10002==resultCode){
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("用户三元信息为空");
//					logger.info(sessionId + "结束榕树 存量用户检验接口，返回结果：用户三元信息为空");
//					return rsResponse;
//				}
//				resultCode = RongShuUtils.getRejectReason(resultCode);
//				String errorMsg = checkResult.getMessage();
//				logger.info(sessionId + "结束榕树 存量用户检验接口，返回结果：用户三元信息验证不通过,返回code:"+resultCode+"返回错误信息:"+errorMsg);
//				rsResponse.setCode(RsResponse.CODE_SUCCESS);
//				rsResponse.setMessage(errorMsg);
//				checkUserInfo.setIsCanLoan(0);//是否可以借款0否1是
//				if(1==resultCode){
//				checkUserInfo.setIsBlackList(1);//黑名单0否1是
//				}else{
//					checkUserInfo.setIsBlackList(0);//黑名单0否1是
//				}
//				checkUserInfo.setRejectReason(resultCode);//0-不拒绝;1-黑名单;2-在贷;3-短时拒绝;4-其他
//				checkUserInfo.setPeriodUnit(3);//1-天；2-月 3周
//				checkUserInfo.setRemark(checkResult.getMessage());
//				rsResponse.setResponse(JSON.toJSONString(checkUserInfo));
//				return rsResponse;
//			}else{
//				checkUserInfo.setIsCanLoan(1);//是否可以借款0否1是
//				checkUserInfo.setIsBlackList(0);//黑名单0否1是
//				checkUserInfo.setRejectReason(0);//0-不拒绝;1-黑名单;2-在贷;3-短时拒绝;4-其他
//				checkUserInfo.setPeriodUnit(3);//1-天；2-月 3周
//				//金额额度
//				Map<String,Object> amountLimitMap = new HashMap<String, Object>();
//				amountLimitMap.put("maxLoan", 50000);// 最高额度
//				amountLimitMap.put("minLoan", 2000);// 最低额度
//				amountLimitMap.put("maxPeriod", 4);// 最大和最小时间都是4
//				amountLimitMap.put("minPeriod", 4);// 最大和最小时间都是4
//				String amountLimit = JSON.toJSONString(amountLimitMap);
//				checkUserInfo.setAmountLimit(amountLimit);
//				//贷款周期
//				List<Map<String,Object>> amountOptionList = new ArrayList<Map<String,Object>>();
//				Map<String,Object> amountOptionMap = new HashMap<String,Object>();
//				amountOptionMap.put("max", 50000);
//				amountOptionMap.put("min", 2000);
//				amountOptionMap.put("step", 100);
//				amountOptionMap.put("periods","[4]");
//				amountOptionList.add(amountOptionMap);
//				String amountOption = JSON.toJSONString(amountOptionList);
//				checkUserInfo.setAmountOption(amountOption);
//			}
//			
//		} catch (Exception e) {
//			logger.error(sessionId + "结束榕树 存量用户检验接口异常", e);
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("接口调用异常，请稍后再试");
//		}
//		rsResponse.setCode(RsResponse.CODE_SUCCESS);
//		rsResponse.setResponse(JSON.toJSONString(checkUserInfo));
//		logger.info(sessionId + "结束榕树  存量用户检验接口，返回结果：" + JSON.toJSONString(rsResponse));
//		return rsResponse;
//	}
//
//	/**
//	 * 榕树 -贷款试算器接口
//	 *
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/sxy/rongShu/loanCalculation.do")
//	@ResponseBody
//	public RsResponse loanCalculation(@RequestBody RsRequest request) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入榕树 贷款试算期接口");
//		RsResponse rsResponse = new RsResponse();
//		try {
//			// 第一步：取参数
//			String sign = request.getSign();
//			String channelId = request.getChannelId();
//			String requestData = request.getRequest();
//			Long timestamp = request.getTimestamp();
//			// 第二步：参数验证
//			if (StringUtils.isEmpty(channelId)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数channelId为空");
//				logger.info(sessionId + "结束榕树  贷款试算期接口，参数channelId为空：" + channelId);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(sign)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数sign为空");
//				logger.info(sessionId + "结束榕树  贷款试算期接口，参数sign为空：" + sign);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(requestData)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数request为空");
//				logger.info(sessionId + "结束榕树  贷款试算期接口，参数request为空：" + requestData);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数timestamp为空");
//				logger.info(sessionId + "结束榕树  贷款试算期接口，参数timestamp为空：" + timestamp);
//				return rsResponse;
//			}
//
//			Map<String, String> paramMap = new TreeMap<String, String>();
//			paramMap.put("channelId", channelId);
//			paramMap.put("request", requestData);
//			paramMap.put("timestamp", String.valueOf(timestamp));
//			// 第三步验签
//			logger.info("贷款试算期接口-------开始验签-------");
//			boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//			if (!isOk) {
//				rsResponse.setCode(RsResponse.CODE_SIGNERR);
//				rsResponse.setMessage("验签不通过");
//				logger.info(sessionId + "结束榕树贷款试算期接口异常，返回结果验签不通过,参数sign:"+sign);
//				return rsResponse;
//			}
//			
//			Map<String,String> map = JSON.parseObject(requestData, Map.class);
//			Double loanAmount = Double.parseDouble(map.get("loanAmount"));//借款金额
//			//int loanPeriod = Integer.parseInt(map.get("loanPeriod"));//借款周期
//			//String periodUnit = String.valueOf(map.get("periodUnit"));//周期单位
//			//查询水象云产品
//			BwProductDictionary bwProductDictionary = bwProductDictionaryService
//								.findBwProductDictionaryById(Integer.valueOf(RongShuConstant.PRODUCTID));
//			// 第四步，获取放款金额限制
//			Integer maxLoanAmount = bwProductDictionary.getMaxAmount();
//			Integer minLoanAmount = bwProductDictionary.getMinAmount();
//			Double interestRate = bwProductDictionary.getInterestRate();// 分期利息率
//
//			if (loanAmount > maxLoanAmount) {
//				//rsResponse.setCode(RsResponse.CODE_PARMERR);
//				//rsResponse.setMessage("借款金额不通过");
//				//logger.info(sessionId + "结束榕树贷款试算期接口异常，返回结果借款金额过大,参数loanAmount:"+loanAmount);
//				//return rsResponse;
//			} else if (loanAmount < minLoanAmount) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("借款金额不通过");
//				logger.info(sessionId + "结束榕树贷款试算期接口异常，返回结果借款金额过小,参数loanAmount:"+loanAmount);
//				return rsResponse;
//			}
//			// 每期应还本金
//			//Double eachAmount = Double.valueOf(loanAmount) / 4;
//			// 每期应还利息
//			Double periodOne = RongShuUtils.calculateRepayMoney(loanAmount, 1, interestRate);
//			Double periodTwo = RongShuUtils.calculateRepayMoney(loanAmount, 2, interestRate);
//			Double periodThree = RongShuUtils.calculateRepayMoney(loanAmount, 3, interestRate);
//			Double periodFour = RongShuUtils.calculateRepayMoney(loanAmount, 4, interestRate);
//
//			Double periodAll = periodOne + periodTwo + periodThree + periodFour;	
//			Double amount = Double.valueOf(loanAmount) + periodAll;
//			
//			Map<String,Object> mapData = new TreeMap<String, Object>(); 
//			mapData.put("refundAmount", amount);//还款总金额
//			mapData.put("actualAmount", loanAmount);//实际到账金额
//			mapData.put("remark", "到账金额"+loanAmount+"元，手续费"+periodAll+"元，预计还款总金额"+amount+"元");//到账金额描述
//			
//			rsResponse.setCode(RsResponse.CODE_SUCCESS);
//			rsResponse.setMessage("查询贷款试算接口成功");
//			rsResponse.setResponse(JSON.toJSONString(mapData));
//					
//		} catch (Exception e) {
//			logger.error(sessionId + "执行榕树 贷款试算期接口异常", e);
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("接口调用异常，请稍后再试");
//		}
//		logger.info(sessionId + "结束榕树 贷款试算期接口，返回结果：" + JSON.toJSONString(rsResponse));
//		return rsResponse;
//	}
//
//	/**
//	 * 榕树 - 进件推送接口
//	 *
//	 * @return
//	 */
//	@RequestMapping("/sxy/rongShu/pushOrder.do")
//	@ResponseBody
//	public RsResponse pushOrder(@RequestBody RsRequest request) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入榕树 进件推送接口");
//		RsResponse rsResponse = new RsResponse();
//		try {
//			// 第一步：取参数
//			String sign = request.getSign();
//			String channelId = request.getChannelId();
//			String requestData = request.getRequest();
//			Long timestamp = request.getTimestamp();
//			// 第二步：参数验证
//			if (StringUtils.isEmpty(channelId)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数channelId为空");
//				logger.info(sessionId + "结束榕树 进件推送检验接口，参数channelId为空：" + channelId);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(sign)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数sign为空");
//				logger.info(sessionId + "结束榕树 进件推送检验接口，参数sign为空：" + sign);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(requestData)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数request为空");
//				logger.info(sessionId + "结束榕树 进件推送检验接口，参数request为空：" + requestData);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数timestamp为空");
//				logger.info(sessionId + "结束榕树 进件推送检验接口，参数timestamp为空：" + timestamp);
//				return rsResponse;
//			}
//
//			Map<String, String> paramMap = new TreeMap<String, String>();
//			paramMap.put("channelId", channelId);
//			paramMap.put("request", requestData);
//			paramMap.put("timestamp", String.valueOf(timestamp));
//			// 第三步验签
//			logger.info("进件推送接口-------开始验签-------");
//			boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//			if (!isOk) {
//				rsResponse.setCode(RsResponse.CODE_SIGNERR);
//				rsResponse.setMessage("验签不通过");
//				logger.info(sessionId + "结束榕树进件推送接口异常，返回结果验签不通过,参数sign:"+sign);
//				return rsResponse;
//			}
//			rsResponse = rongShuNewService.savePushOrder(sessionId, request);
//
//			
//		} catch (Exception e) {
//			logger.error(sessionId + "执行榕树进件推送接口异常", e);
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("进件推送接口调用异常，请稍后再试");
//		}
//		logger.info(sessionId + "结束榕树进件推送接口，返回结果：" + JSON.toJSONString(rsResponse));
//		return rsResponse;
//	}
//
//	// 审核结果查询接口
//	@RequestMapping("/sxy/rongShu/queryAuditResult.do")
//	@ResponseBody
//	public RsResponse queryAuditResult(@RequestBody RsRequest request) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入榕树 进件推送接口");
//		RsResponse rsResponse = new RsResponse();
//		try {
//			// 第一步：取参数
//			String sign = request.getSign();
//			String channelId = request.getChannelId();
//			String requestData = request.getRequest();
//			Long timestamp = request.getTimestamp();
//			// 第二步：参数验证
//			if (StringUtils.isEmpty(channelId)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数channelId为空");
//				logger.info(sessionId + "结束榕树 审核结果查询接口，参数channelId为空：" + channelId);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(sign)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数sign为空");
//				logger.info(sessionId + "结束榕树 审核结果查询接口，参数sign为空：" + sign);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(requestData)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数request为空");
//				logger.info(sessionId + "结束榕树 审核结果查询接口，参数request为空：" + requestData);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数timestamp为空");
//				logger.info(sessionId + "结束榕树 审核结果查询接口，参数timestamp为空：" + timestamp);
//				return rsResponse;
//			}
//
//			Map<String, String> paramMap = new TreeMap<String, String>();
//			paramMap.put("channelId", channelId);
//			paramMap.put("request", requestData);
//			paramMap.put("timestamp", String.valueOf(timestamp));
//			// 第三步验签
//			logger.info("进件推送接口-------开始验签-------");
//			boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//			if (!isOk) {
//				rsResponse.setCode(RsResponse.CODE_SIGNERR);
//				rsResponse.setMessage("验签不通过");
//				logger.info(sessionId + "结束审核结果查询接口异常，返回结果验签不通过,参数sign:"+sign);
//				return rsResponse;
//			}
//			
//			Map<String,String> map = JSON.parseObject(requestData, Map.class);
//			rsResponse = rongShuNewService.getApprovalResult(sessionId, map);
//		} catch (Exception e) {
//			logger.info("榕树拉取订单状态异常" + e);
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("接口调用异常，请稍后再试");
//		}
//		logger.info(sessionId + "结束 审核结果查询接口，返回结果：" + JSON.toJSONString(rsResponse));
//		return rsResponse;
//	}
//
//	// 5.19.绑定银行卡接口
//	@RequestMapping("/sxy/rongShu/bindCard.do")
//	@ResponseBody
//	public RsResponse bindCard(@RequestBody RsRequest request) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入榕树 绑卡接口");
//		RsResponse rsResponse = new RsResponse();
//		try {
//			// 第一步：取参数
//			String sign = request.getSign();
//			String channelId = request.getChannelId();
//			String requestData = request.getRequest();
//			Long timestamp = request.getTimestamp();
//			// 第二步：参数验证
//			if (StringUtils.isEmpty(channelId)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数channelId为空");
//				logger.info(sessionId + "结束榕树 绑卡接口，参数channelId为空：" + channelId);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(sign)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数sign为空");
//				logger.info(sessionId + "结束榕树 绑卡接口，参数sign为空：" + sign);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(requestData)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数request为空");
//				logger.info(sessionId + "结束榕树 绑卡接口，参数request为空：" + requestData);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数timestamp为空");
//				logger.info(sessionId + "结束榕树 绑卡接口，参数timestamp为空：" + timestamp);
//				return rsResponse;
//			}
//
//			Map<String, String> paramMap = new TreeMap<String, String>();
//			paramMap.put("channelId", channelId);
//			paramMap.put("request", requestData);
//			paramMap.put("timestamp", String.valueOf(timestamp));
//			// 第三步验签
//			logger.info("绑卡接口-------开始验签-------");
//			boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//			if (!isOk) {
//				rsResponse.setCode(RsResponse.CODE_SIGNERR);
//				rsResponse.setMessage("验签不通过");
//				logger.info(sessionId + "结束榕树绑卡接口异常，返回结果验签不通过,参数sign:"+sign);
//				return rsResponse;
//			}
//			BankCardNewInfo bankcardinfo = JSONObject.parseObject(requestData, BankCardNewInfo.class);
//			//String bankName = bankcardinfo.getBankName();
//			String idNumber = bankcardinfo.getCid();
//			//String bankCode = bankcardinfo.getBankCode();
//			String thirdOrderNo = String.valueOf(bankcardinfo.getOrderId());
//			String phone = bankcardinfo.getRegisterPhone();
//			// 获取借款人信息
//			BwBorrower borrower = new BwBorrower();
//			borrower.setIdCard(idNumber);
//			borrower.setPhone(phone);
//			borrower.setFlag(1);// 未删除的
//			borrower = findBwBorrowerByAttrProxy(borrower);
//			logger.info("借款人查询结果：" + JSONObject.toJSONString(borrower));
//			// 查找订单orderNO
//			BwOrderRong bwOrderRong = new BwOrderRong();
//			bwOrderRong.setThirdOrderNo(thirdOrderNo);
//			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//			logger.info("订单查询结果：" + JSONObject.toJSONString(bwOrderRong));
//			//String userId = String.valueOf(borrower.getId());// 借款人id
//			String accName = bankcardinfo.getName();// 姓名
//			String cardNo = bankcardinfo.getBankCardNum();// 银行卡号
//			Map<String, Object> map = new HashMap<String, Object>();
//			Map<String, Object> mapResp = new HashMap<String, Object>();
//			String errorMsg ="";
//			if (!CommUtils.isNull(bwOrderRong)) {
//				BwOrder bwOrder = new BwOrder();
//				bwOrder.setId(bwOrderRong.getOrderId());
//				bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
//		
//				// 公共绑卡接口
//				DrainageRsp drainageRsp = commonService.saveBindCard(sessionId, idNumber, 
//						cardNo, accName, phone, thirdOrderNo);
//						if (null != drainageRsp) {
//							if ("0000".equals(drainageRsp.getCode())) {
//								
//								map.put("orderId", bwOrder.getId());
//								map.put("bankCardNum",cardNo);
//								RedisUtils.lpush("tripartite:bindCardNotify:" + channelId,JSON.toJSONString(map));
//								mapResp.put("status", 3001);//绑卡状态3001	绑卡成功 3002	验证码发送成功3003	绑卡失败3004	H5绑卡
//								mapResp.put("remark", "绑卡成功");
//								rsResponse.setCode(RsResponse.CODE_SUCCESS);
//								rsResponse.setMessage("绑卡成功");
//								rsResponse.setResponse(JSON.toJSONString(mapResp));
//								
//								bwOrder.setStatusId(2L);
//								bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//								bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//								bwOrderService.updateBwOrder(bwOrder);
//								// 修改订单状态
//								//int status = bwOrderService.updateStatusByOrderId(bwOrder.getId(),2);
//								logger.info(sessionId + ">>> 绑卡成功后修改订单状态bworder:"+JSON.toJSONString(bwOrder));
//								
//								
//								HashMap<String, String> hm = new HashMap<String, String>();
//								hm.put("channelId", RongShuConstant.CHANNELID + "");
//								hm.put("orderId", String.valueOf(bwOrder.getId()));
//								hm.put("orderStatus", "2");
//								hm.put("result", "");
//								String hmData = JSON.toJSONString(hm);
//								RedisUtils.rpush("tripartite:orderStatusNotify:" + RongShuConstant.CHANNELID, hmData);
//								// 审核 放入redis
//								SystemAuditDto systemAuditDto = new SystemAuditDto();
//								systemAuditDto.setIncludeAddressBook(1);
//								systemAuditDto.setOrderId(bwOrder.getId());
//								systemAuditDto.setBorrowerId(borrower.getId());
//								systemAuditDto.setName(borrower.getName());
//								systemAuditDto.setPhone(borrower.getPhone());
//								systemAuditDto.setIdCard(borrower.getIdCard());
//								systemAuditDto.setChannel(Integer.parseInt(RongShuConstant.CHANNELID));
//								systemAuditDto.setThirdOrderId(thirdOrderNo);
//								systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//								RedisUtils.hset(SystemConstant.AUDIT_KEY, bwOrder.getId() + "", JsonUtils.toJson(systemAuditDto));
//								logger.info(sessionId + ">>> 修改订单状态，并放入redis,参数:"+JSON.toJSONString(systemAuditDto));
//								return rsResponse;
//							}
//							errorMsg = drainageRsp.getMessage();
//						}
//						mapResp.put("status", 3003);//3001	绑卡成功 3002	验证码发送成功3003	绑卡失败3004	H5绑卡
//						mapResp.put("remark", errorMsg);
//						rsResponse.setCode(RsResponse.CODE_SUCCESS);
//						rsResponse.setMessage(errorMsg);
//						rsResponse.setResponse(JSON.toJSONString(mapResp));
//			} else {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("未找到该工单");
//			}
//
//		} catch (Exception e) {
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("发生异常");
//			logger.info(sessionId + "榕树绑定银行卡接口发生异常"+e);
//		}
//
//		return rsResponse;
//
//	}
//
//	//绑卡 结果 查询 接口
//	@ResponseBody
//	@RequestMapping("/sxy/rongShu/queryBindCardResult.do")
//	public RsResponse queryBindCardResult(@RequestBody RsRequest request) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入榕树 绑卡结果查询接口");
//		RsResponse rsResponse = new RsResponse();
//		try {
//			// 第一步：取参数
//			String sign = request.getSign();
//			String channelId = request.getChannelId();
//			String requestData = request.getRequest();
//			Long timestamp = request.getTimestamp();
//			// 第二步：参数验证
//			if (StringUtils.isEmpty(channelId)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数channelId为空");
//				logger.info(sessionId + "结束榕树 绑卡结果查询接口，参数channelId为空：" + channelId);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(sign)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数sign为空");
//				logger.info(sessionId + "结束榕树 绑卡结果查询接口，参数sign为空：" + sign);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(requestData)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数request为空");
//				logger.info(sessionId + "结束榕树 绑卡结果查询接口，参数request为空：" + requestData);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数timestamp为空");
//				logger.info(sessionId + "结束榕树 绑卡结果查询接口，参数timestamp为空：" + timestamp);
//				return rsResponse;
//			}
//
//			Map<String, String> paramMap = new TreeMap<String, String>();
//			paramMap.put("channelId", channelId);
//			paramMap.put("request", requestData);
//			paramMap.put("timestamp", String.valueOf(timestamp));
//			// 第三步验签
//			logger.info("绑卡结果查询接口-------开始验签-------");
//			boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//			if (!isOk) {
//				rsResponse.setCode(RsResponse.CODE_SIGNERR);
//				rsResponse.setMessage("验签不通过");
//				logger.info(sessionId + "结束榕树绑卡结果查询接口异常，返回结果验签不通过,参数sign:"+sign);
//				return rsResponse;
//			}
//			
//			Map<String,String> map = JSON.parseObject(requestData, Map.class);
//			String thirdOrderNo = map.get("orderId");
//			if(StringUtils.isBlank(thirdOrderNo)){
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数orderId为空");
//				logger.info(sessionId + "结束榕树绑卡结果查询接口异常，参数orderId为空,参数orderId:"+thirdOrderNo);
//				return rsResponse;
//			}
//			BwOrderRong bwRong = new BwOrderRong();
//			bwRong.setThirdOrderNo(thirdOrderNo);
//			bwRong = bwOrderRongService.findBwOrderRongByAttr(bwRong);
//			if (CommUtils.isNull(bwRong)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("工单不存在");
//				logger.info(sessionId + "结束榕树绑卡结果查询接口，返回结果：" + JSON.toJSONString(rsResponse));
//				return rsResponse;
//			}
//			BwOrder bwOrder = new BwOrder();
//			bwOrder.setId(bwRong.getOrderId());
//			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
//			if (CommUtils.isNull(bwOrder)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("工单不存在");
//				logger.info(sessionId + "结束榕树绑卡结果查询接口，返回结果：" + JSON.toJSONString(rsResponse));
//				return rsResponse;
//			}
//
//			// 查询银行卡信息
//			BwBankCard bwBankCard = new BwBankCard();
//			bwBankCard.setBorrowerId(bwOrder.getBorrowerId());
//			bwBankCard = findBwBankCardByAttrProxy(bwBankCard);
//			if (bwBankCard == null) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("银行卡信息不存在");
//				logger.info(sessionId + "结束榕树绑卡结果查询接口，返回结果：" + JSON.toJSONString(rsResponse));
//				return rsResponse;
//			}
//
//			Map<String, String> data = new HashMap<>();
//			if (bwBankCard.getSignStatus() >=1) {
//				data.put("status","3001");//绑卡成功
//			} else {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("绑卡状态为未绑定");
//				logger.info(sessionId + "结束榕树绑卡结果查询接口，返回结果：" + JSON.toJSONString(rsResponse));
//				return rsResponse;
//			}
//			String bankCode = bwBankCard.getBankCode();
//			if(!StringUtils.isBlank(bankCode)){
//				bankCode = DrainageUtils.convertFuiouBankCodeToBaofu(bankCode);
//				if("GDB".equals(bankCode)){
//					bankCode = "CGB";
//				}
//				else if("BCOM".equals(bankCode)){
//					bankCode = "BCM";
//				}
//				
//			}
//			data.put("bankCode", bankCode);
//			data.put("bankName", bwBankCard.getBankName());
//			data.put("bankCardNum", bwBankCard.getCardNo());
//			data.put("phone", bwBankCard.getPhone());
//
//			rsResponse.setCode(RsResponse.CODE_SUCCESS);
//			rsResponse.setMessage("查询成功");
//			rsResponse.setResponse(JSON.toJSONString(data));
//		} catch (Exception e) {
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("榕树绑卡发生异常");
//			logger.debug(sessionId + "榕树绑卡结果查询接口异常," + e);
//		}
//		logger.info(sessionId + "结束榕树绑卡结果查询接口，返回结果：" + JSON.toJSONString(rsResponse));
//		return rsResponse;
//	}
//
//	
//	// 放款结果查询接口
//		@RequestMapping("/sxy/rongShu/queryFk.do")
//		@ResponseBody
//		public RsResponse queryFkResult(@RequestBody RsRequest request) {
//			long sessionId = System.currentTimeMillis();
//			logger.info(sessionId + "进入榕树 放款结果查询接口");
//			RsResponse rsResponse = new RsResponse();
//			try {
//				// 第一步：取参数
//				String sign = request.getSign();
//				String channelId = request.getChannelId();
//				String requestData = request.getRequest();
//				Long timestamp = request.getTimestamp();
//				// 第二步：参数验证
//				if (StringUtils.isEmpty(channelId)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数channelId为空");
//					logger.info(sessionId + "结束榕树 放款结果查询接口，参数channelId为空：" + channelId);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(sign)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数sign为空");
//					logger.info(sessionId + "结束榕树 放款结果查询接口，参数sign为空：" + sign);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(requestData)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数request为空");
//					logger.info(sessionId + "结束榕树 放款结果查询接口，参数request为空：" + requestData);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数timestamp为空");
//					logger.info(sessionId + "结束榕树 放款结果查询接口，参数timestamp为空：" + timestamp);
//					return rsResponse;
//				}
//
//				Map<String, String> paramMap = new TreeMap<String, String>();
//				paramMap.put("channelId", channelId);
//				paramMap.put("request", requestData);
//				paramMap.put("timestamp", String.valueOf(timestamp));
//				// 第三步验签
//				logger.info("放款结果查询接口-------开始验签-------");
//				boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//				if (!isOk) {
//					rsResponse.setCode(RsResponse.CODE_SIGNERR);
//					rsResponse.setMessage("验签不通过");
//					logger.info(sessionId + "结束榕树放款结果查询接口异常，返回结果验签不通过,参数sign:"+sign);
//					return rsResponse;
//				}
//				
//				Map<String,String> map = JSON.parseObject(requestData, Map.class);
//				rsResponse = rongShuNewService.queryFk(sessionId, map);
//
//			} catch (Exception e) {
//				logger.error("放款结果查询接口异常", e);
//				rsResponse.setCode(RsResponse.CODE_FAILERR);
//				rsResponse.setMessage("接口调用异常，请稍后再试");
//			}
//			logger.info(sessionId + "结束放款结果查询接口，返回结果：" + JSON.toJSONString(rsResponse));
//			return rsResponse;
//		}
//
//		
//		// 还款计划查询接口
//		@RequestMapping("/sxy/rongShu/getRepayPlan.do")
//		@ResponseBody
//		public RsResponse getRepayPlan(@RequestBody RsRequest request) {
//			long sessionId = System.currentTimeMillis();
//			logger.info(sessionId + "进入榕树 还款计划查询接口");
//			RsResponse rsResponse = new RsResponse();
//			try {
//				// 第一步：取参数
//				String sign = request.getSign();
//				String channelId = request.getChannelId();
//				String requestData = request.getRequest();
//				Long timestamp = request.getTimestamp();
//				// 第二步：参数验证
//				if (StringUtils.isEmpty(channelId)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数channelId为空");
//					logger.info(sessionId + "结束榕树 还款计划查询接口，参数channelId为空：" + channelId);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(sign)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数sign为空");
//					logger.info(sessionId + "结束榕树 还款计划查询接口，参数sign为空：" + sign);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(requestData)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数request为空");
//					logger.info(sessionId + "结束榕树 还款计划查询接口，参数request为空：" + requestData);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数timestamp为空");
//					logger.info(sessionId + "结束榕树 还款计划查询接口，参数timestamp为空：" + timestamp);
//					return rsResponse;
//				}
//
//				Map<String, String> paramMap = new TreeMap<String, String>();
//				paramMap.put("channelId", channelId);
//				paramMap.put("request", requestData);
//				paramMap.put("timestamp", String.valueOf(timestamp));
//				// 第三步验签
//				logger.info("榕树 还款计划查询接口-------开始验签-------");
//				boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//				if (!isOk) {
//					rsResponse.setCode(RsResponse.CODE_SIGNERR);
//					rsResponse.setMessage("验签不通过");
//					logger.info(sessionId + "结束榕树 还款计划查询接口异常，返回结果验签不通过,参数sign:"+sign);
//					return rsResponse;
//				}
//				
//				Map<String,String> map = JSON.parseObject(requestData, Map.class);
//				rsResponse = rongShuNewService.getRepayPlan(sessionId, map); 
//
//			} catch (Exception e) {
//				logger.error("榕树 还款计划查询接口异常", e);
//				rsResponse.setCode(RsResponse.CODE_FAILERR);
//				rsResponse.setMessage("接口调用异常，请稍后再试");
//			}
//			logger.info(sessionId + "结束榕树 还款计划查询接口，返回结果：" + JSON.toJSONString(rsResponse));
//			return rsResponse;
//		}
//	
//		// 主动还款试算
//		@RequestMapping("/sxy/rongShu/getPaymentCell.do")
//		@ResponseBody
//		public RsResponse getPaymentCell(@RequestBody RsRequest request) {
//			long sessionId = System.currentTimeMillis();
//			logger.info(sessionId + "进入榕树 主动还款试算接口");
//			RsResponse rsResponse = new RsResponse();
//			try {
//				// 第一步：取参数
//				String sign = request.getSign();
//				String channelId = request.getChannelId();
//				String requestData = request.getRequest();
//				Long timestamp = request.getTimestamp();
//				// 第二步：参数验证
//				if (StringUtils.isEmpty(channelId)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数channelId为空");
//					logger.info(sessionId + "结束榕树 主动还款试算接口，参数channelId为空：" + channelId);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(sign)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数sign为空");
//					logger.info(sessionId + "结束榕树 主动还款试算接口，参数sign为空：" + sign);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(requestData)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数request为空");
//					logger.info(sessionId + "结束榕树 主动还款试算接口，参数request为空：" + requestData);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数timestamp为空");
//					logger.info(sessionId + "结束榕树 主动还款试算接口，参数timestamp为空：" + timestamp);
//					return rsResponse;
//				}
//
//				Map<String, String> paramMap = new TreeMap<String, String>();
//				paramMap.put("channelId", channelId);
//				paramMap.put("request", requestData);
//				paramMap.put("timestamp", String.valueOf(timestamp));
//				// 第三步验签
//				logger.info("榕树 主动还款试算接口-------开始验签-------");
//				boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//				if (!isOk) {
//					rsResponse.setCode(RsResponse.CODE_SIGNERR);
//					rsResponse.setMessage("验签不通过");
//					logger.info(sessionId + "结束榕树 主动还款试算异常，返回结果验签不通过,参数sign:"+sign);
//					return rsResponse;
//				}
//				
//				Map<String,String> map = JSON.parseObject(requestData, Map.class);
//				rsResponse = rongShuNewService.getPaymentCell(sessionId, map);
//
//			} catch (Exception e) {
//				logger.error("榕树 主动还款试算接口异常", e);
//				rsResponse.setCode(RsResponse.CODE_FAILERR);
//				rsResponse.setMessage("接口调用异常，请稍后再试");
//			}
//			logger.info(sessionId + "结束榕树 主动还款试算接口，返回结果：" + JSON.toJSONString(rsResponse));
//			return rsResponse;
//		}
//		
//		
//		//主动还款接口
//		@RequestMapping("/sxy/rongShu/repayment.do")
//		@ResponseBody
//		public RsResponse repayment(@RequestBody RsRequest request) {
//			long sessionId = System.currentTimeMillis();
//			logger.info(sessionId + "进入榕树 主动还款接口");
//			RsResponse rsResponse = new RsResponse();
//			try {
//				// 第一步：取参数
//				String sign = request.getSign();
//				String channelId = request.getChannelId();
//				String requestData = request.getRequest();
//				Long timestamp = request.getTimestamp();
//				// 第二步：参数验证
//				if (StringUtils.isEmpty(channelId)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数channelId为空");
//					logger.info(sessionId + "结束榕树 主动还款接口，参数channelId为空：" + channelId);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(sign)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数sign为空");
//					logger.info(sessionId + "结束榕树 主动还款接口，参数sign为空：" + sign);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(requestData)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数request为空");
//					logger.info(sessionId + "结束榕树 主动还款接口，参数request为空：" + requestData);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数timestamp为空");
//					logger.info(sessionId + "结束榕树 主动还款接口，参数timestamp为空：" + timestamp);
//					return rsResponse;
//				}
//
//				Map<String, String> paramMap = new TreeMap<String, String>();
//				paramMap.put("channelId", channelId);
//				paramMap.put("request", requestData);
//				paramMap.put("timestamp", String.valueOf(timestamp));
//				// 第三步验签
//				logger.info("榕树 主动还款接口-------开始验签-------");
//				boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//				if (!isOk) {
//					rsResponse.setCode(RsResponse.CODE_SIGNERR);
//					rsResponse.setMessage("验签不通过");
//					logger.info(sessionId + "结束榕树 主动还款接口异常，返回结果验签不通过,参数sign:"+sign);
//					return rsResponse;
//				}
//				
//				Map<String,String> map = JSON.parseObject(requestData, Map.class);
//				rsResponse = rongShuNewService.updateRepayment(sessionId, map);
//
//			} catch (Exception e) {
//				rsResponse.setCode(RsResponse.CODE_FAILERR);
//				rsResponse.setMessage("发生异常");
//				logger.info(sessionId + "榕树 主动还款接口，返回结果：" + JSON.toJSONString(rsResponse));
//			}
//			logger.info(sessionId + "榕树 主动还款接口调用成功，返回结果：" + JSON.toJSONString(rsResponse));
//			return rsResponse;
//
//		}
//		
//	
//	//还款结果
//	@RequestMapping("/sxy/rongShu/getRepaymentResult.do")
//	@ResponseBody
//	public RsResponse getRepaymentResult(@RequestBody RsRequest request) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入榕树 还款结果接口");
//		RsResponse rsResponse = new RsResponse();
//		try {
//			// 第一步：取参数
//			String sign = request.getSign();
//			String channelId = request.getChannelId();
//			String requestData = request.getRequest();
//			Long timestamp = request.getTimestamp();
//			// 第二步：参数验证
//			if (StringUtils.isEmpty(channelId)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数channelId为空");
//				logger.info(sessionId + "结束榕树 还款结果接口，参数channelId为空：" + channelId);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(sign)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数sign为空");
//				logger.info(sessionId + "结束榕树 还款结果接口，参数sign为空：" + sign);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(requestData)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数request为空");
//				logger.info(sessionId + "结束榕树 还款结果接口，参数request为空：" + requestData);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数timestamp为空");
//				logger.info(sessionId + "结束榕树 还款结果接口，参数timestamp为空：" + timestamp);
//				return rsResponse;
//			}
//
//			Map<String, String> paramMap = new TreeMap<String, String>();
//			paramMap.put("channelId", channelId);
//			paramMap.put("request", requestData);
//			paramMap.put("timestamp", String.valueOf(timestamp));
//			// 第三步验签
//			logger.info("榕树 还款结果接口-------开始验签-------");
//			boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//			if (!isOk) {
//				rsResponse.setCode(RsResponse.CODE_SIGNERR);
//				rsResponse.setMessage("验签不通过");
//				logger.info(sessionId + "结束榕树 还款结果接口异常，返回结果验签不通过,参数sign:"+sign);
//				return rsResponse;
//			}
//			
//			Map<String,String> map = JSON.parseObject(requestData, Map.class);
//			rsResponse = rongShuNewService.getRepaymentResult(sessionId, map);
//
//		} catch (Exception e) {
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("发生异常");
//			logger.info(sessionId + "榕树 还款结果接口异常，返回结果：" + JSON.toJSONString(rsResponse));
//		}
//		logger.info(sessionId + "榕树 还款结果接口，返回结果：" + JSON.toJSONString(rsResponse));
//		return rsResponse;
//
//	}
//	
//	
//	//合同列表接口
//	@RequestMapping("/sxy/rongShu/getContracts.do")
//	@ResponseBody
//	public RsResponse getContracts(@RequestBody RsRequest request) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入榕树 合同列表接口");
//		RsResponse rsResponse = new RsResponse();
//		try {
//			// 第一步：取参数
//			String sign = request.getSign();
//			String channelId = request.getChannelId();
//			String requestData = request.getRequest();
//			Long timestamp = request.getTimestamp();
//			// 第二步：参数验证
//			if (StringUtils.isEmpty(channelId)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数channelId为空");
//				logger.info(sessionId + "结束榕树 合同列表接口，参数channelId为空：" + channelId);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(sign)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数sign为空");
//				logger.info(sessionId + "结束榕树 合同列表接口，参数sign为空：" + sign);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(requestData)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数request为空");
//				logger.info(sessionId + "结束榕树 合同列表接口，参数request为空：" + requestData);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数timestamp为空");
//				logger.info(sessionId + "结束榕树 合同列表接口，参数timestamp为空：" + timestamp);
//				return rsResponse;
//			}
//
//			Map<String, String> paramMap = new TreeMap<String, String>();
//			paramMap.put("channelId", channelId);
//			paramMap.put("request", requestData);
//			paramMap.put("timestamp", String.valueOf(timestamp));
//			// 第三步验签
//			logger.info("榕树 合同列表接口-------开始验签-------");
//			boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//			if (!isOk) {
//				rsResponse.setCode(RsResponse.CODE_SIGNERR);
//				rsResponse.setMessage("验签不通过");
//				logger.info(sessionId + "结束榕树 合同列表接口异常，返回结果验签不通过,参数sign:"+sign);
//				return rsResponse;
//			}
//			
//			Map<String,String> map = JSON.parseObject(requestData, Map.class);
//			rsResponse = rongShuNewService.getContracts(sessionId, map);
//
//		} catch (Exception e) {
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("发生异常");
//			logger.info(sessionId + "结束榕树 合同列表接口，返回结果：" + JSON.toJSONString(rsResponse));
//		}
//		logger.info(sessionId + "结束榕树 合同列表接口，返回结果：" + JSON.toJSONString(rsResponse));
//		return rsResponse;
//	}
//	
//	
//	//确认贷款(签约)
//	@RequestMapping("/sxy/rongShu/confirmLoan.do")
//	@ResponseBody
//	public RsResponse confirmLoan(@RequestBody RsRequest request) {
//		long sessionId = System.currentTimeMillis();
//		logger.info(sessionId + "进入榕树 确认贷款接口");
//		RsResponse rsResponse = new RsResponse();
//		try {
//			// 第一步：取参数
//			String sign = request.getSign();
//			String channelId = request.getChannelId();
//			String requestData = request.getRequest();
//			Long timestamp = request.getTimestamp();
//			// 第二步：参数验证
//			if (StringUtils.isEmpty(channelId)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数channelId为空");
//				logger.info(sessionId + "结束榕树 确认贷款接口，参数channelId为空：" + channelId);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(sign)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数sign为空");
//				logger.info(sessionId + "结束榕树 确认贷款接口，参数sign为空：" + sign);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(requestData)) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数request为空");
//				logger.info(sessionId + "结束榕树 确认贷款接口，参数request为空：" + requestData);
//				return rsResponse;
//			}
//			if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//				rsResponse.setCode(RsResponse.CODE_PARMERR);
//				rsResponse.setMessage("参数timestamp为空");
//				logger.info(sessionId + "结束榕树 确认贷款接口，参数timestamp为空：" + timestamp);
//				return rsResponse;
//			}
//
//			Map<String, String> paramMap = new TreeMap<String, String>();
//			paramMap.put("channelId", channelId);
//			paramMap.put("request", requestData);
//			paramMap.put("timestamp", String.valueOf(timestamp));
//			// 第三步验签
//			logger.info("榕树 确认贷款接口-------开始验签-------");
//			boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//			if (!isOk) {
//				rsResponse.setCode(RsResponse.CODE_SIGNERR);
//				rsResponse.setMessage("验签不通过");
//				logger.info(sessionId + "结束榕树 确认贷款接口异常，返回结果验签不通过,参数sign:"+sign);
//				return rsResponse;
//			}
//			
//			Map<String,String> map = JSON.parseObject(requestData, Map.class);
//			rsResponse = rongShuNewService.confirmLoan(sessionId, map);
//
//		} catch (Exception e) {
//			rsResponse.setCode(RsResponse.CODE_FAILERR);
//			rsResponse.setMessage("发生异常");
//			logger.info(sessionId + "结束榕树 确认贷款接口，返回结果：" + JSON.toJSONString(rsResponse));
//		}
//		logger.info(sessionId + "结束榕树 确认贷款接口，返回结果：" + JSON.toJSONString(rsResponse));
//		return rsResponse;
//	}
//	
//	
//	    // 6.1.协议绑卡预绑卡接口
//		@RequestMapping("/sxy/rongShu/bindCardReady.do")
//		@ResponseBody
//		public RsResponse saveBindCardReady(@RequestBody RsRequest request) {
//			long sessionId = System.currentTimeMillis();
//			logger.info(sessionId + "进入榕树 绑卡接口");
//			RsResponse rsResponse = new RsResponse();
//			try {
//				// 第一步：取参数
//				String sign = request.getSign();
//				String channelId = request.getChannelId();
//				String requestData = request.getRequest();
//				Long timestamp = request.getTimestamp();
//				// 第二步：参数验证
//				if (StringUtils.isEmpty(channelId)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数channelId为空");
//					logger.info(sessionId + "结束榕树预绑卡接口，参数channelId为空：" + channelId);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(sign)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数sign为空");
//					logger.info(sessionId + "结束榕树预绑卡接口，参数sign为空：" + sign);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(requestData)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数request为空");
//					logger.info(sessionId + "结束榕树预绑卡接口，参数request为空：" + requestData);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数timestamp为空");
//					logger.info(sessionId + "结束榕树预绑卡接口，参数timestamp为空：" + timestamp);
//					return rsResponse;
//				}
//
//				Map<String, String> paramMap = new TreeMap<String, String>();
//				paramMap.put("channelId", channelId);
//				paramMap.put("request", requestData);
//				paramMap.put("timestamp", String.valueOf(timestamp));
//				// 第三步验签
//				logger.info("绑卡接口-------开始验签-------");
//				boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//				if (!isOk) {
//					rsResponse.setCode(RsResponse.CODE_SIGNERR);
//					rsResponse.setMessage("验签不通过");
//					logger.info(sessionId + "结束榕树预绑卡接口异常，返回结果验签不通过,参数sign:"+sign);
//					return rsResponse;
//				}
//				BankCardNewInfo bankcardinfo = JSONObject.parseObject(requestData, BankCardNewInfo.class);
//				//String bankName = bankcardinfo.getBankName();
//				String idNumber = bankcardinfo.getCid();
//				String bankCode = bankcardinfo.getBankCode();
//				if("CGB".equals(bankCode)){
//					bankCode = "GDB";
//				}
//				else if("BCM".equals(bankCode)){
//					bankCode = "BCOM";
//				}
//				String thirdOrderNo = String.valueOf(bankcardinfo.getOrderId());
//				String phone = bankcardinfo.getRegisterPhone();
//				// 获取借款人信息
//				BwBorrower borrower = new BwBorrower();
//				borrower.setIdCard(idNumber);
//				borrower.setPhone(phone);
//				borrower.setFlag(1);// 未删除的
//				borrower = findBwBorrowerByAttrProxy(borrower);
//				logger.info("借款人查询结果：" + JSONObject.toJSONString(borrower));
//				// 查找订单orderNO
//				BwOrderRong bwOrderRong = new BwOrderRong();
//				bwOrderRong.setThirdOrderNo(thirdOrderNo);
//				bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//				logger.info("订单查询结果：" + JSONObject.toJSONString(bwOrderRong));
//				//String userId = String.valueOf(borrower.getId());// 借款人id
//				String accName = bankcardinfo.getName();// 姓名
//				String cardNo = bankcardinfo.getBankCardNum();// 银行卡号
//				String ptChannelId = RongShuConstant.CHANNELID;//平台渠道号
//				Map<String, Object> map = new HashMap<String, Object>();
//				Map<String, Object> mapResp = new HashMap<String, Object>();
//				if (!CommUtils.isNull(bwOrderRong)) {
//					/*BwOrder bwOrder = new BwOrder();
//					bwOrder.setId(bwOrderRong.getOrderId());
//					bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);*/
//					DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//					drainageBindCardVO.setBankCardNo(cardNo);
//					drainageBindCardVO.setBankCode(bankCode);
//					drainageBindCardVO.setChannelId(Integer.parseInt(ptChannelId));
//					drainageBindCardVO.setName(accName);
//					drainageBindCardVO.setPhone(phone);
//					drainageBindCardVO.setThirdOrderNo(thirdOrderNo);
//					drainageBindCardVO.setIdCardNo(idNumber);
//					drainageBindCardVO.setRegPhone(phone);
//					logger.info("榕树调用预绑卡接口入参:"+JSON.toJSONString(drainageBindCardVO));
//					// 公共绑卡接口
//					DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(sessionId, drainageBindCardVO);
//					logger.info("榕树调用预绑卡接口结果:"+JSON.toJSONString(drainageRsp));
//							if (null != drainageRsp) {
//								if ("0000".equals(drainageRsp.getCode())) {
//									//预绑卡成功
//									rsResponse.setCode(RsResponse.CODE_SUCCESS);
//									rsResponse.setMessage("预绑卡成功");
//									mapResp.put("status", "3002");
//									mapResp.put("remark", "预绑卡成功");
//									rsResponse.setResponse(JSON.toJSONString(mapResp));
//								}else{
//									//预绑卡成功
//									rsResponse.setCode(RsResponse.CODE_SUCCESS);
//									rsResponse.setMessage("预绑卡失败");
//									mapResp.put("status", "3003");
//									mapResp.put("remark", drainageRsp.getMessage());
//									rsResponse.setResponse(JSON.toJSONString(mapResp));
//								}
//							}
//				} else {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("未找到该工单");
//				}
//
//			} catch (Exception e) {
//				rsResponse.setCode(RsResponse.CODE_FAILERR);
//				rsResponse.setMessage("发生异常");
//				logger.info(sessionId + "榕树绑定银行卡接口发生异常"+e);
//			}
//
//			return rsResponse;
//
//		}
//	
//		
//		
//	    // 6.1.协议绑卡提交接口
//		@RequestMapping("/sxy/rongShu/bindCardSure.do")
//		@ResponseBody
//		public RsResponse saveBindCardSure(@RequestBody RsRequest request) {
//			long sessionId = System.currentTimeMillis();
//			logger.info(sessionId + "进入榕树绑卡提交接口");
//			RsResponse rsResponse = new RsResponse();
//			try {
//				// 第一步：取参数
//				String sign = request.getSign();
//				String channelId = request.getChannelId();
//				String requestData = request.getRequest();
//				Long timestamp = request.getTimestamp();
//				// 第二步：参数验证
//				if (StringUtils.isEmpty(channelId)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数channelId为空");
//					logger.info(sessionId + "结束榕树绑卡接口，参数channelId为空：" + channelId);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(sign)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数sign为空");
//					logger.info(sessionId + "结束榕树绑卡接口，参数sign为空：" + sign);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(requestData)) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数request为空");
//					logger.info(sessionId + "结束榕树绑卡接口，参数request为空：" + requestData);
//					return rsResponse;
//				}
//				if (StringUtils.isEmpty(String.valueOf(timestamp))) {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("参数timestamp为空");
//					logger.info(sessionId + "结束榕树绑卡接口，参数timestamp为空：" + timestamp);
//					return rsResponse;
//				}
//
//				Map<String, String> paramMap = new TreeMap<String, String>();
//				paramMap.put("channelId", channelId);
//				paramMap.put("request", requestData);
//				paramMap.put("timestamp", String.valueOf(timestamp));
//				// 第三步验签
//				logger.info("绑卡接口-------开始验签-------");
//				boolean isOk = RongShuUtils.checkSign(paramMap, sign, PUBKEY);
//				if (!isOk) {
//					rsResponse.setCode(RsResponse.CODE_SIGNERR);
//					rsResponse.setMessage("验签不通过");
//					logger.info(sessionId + "结束榕树绑卡接口异常，返回结果验签不通过,参数sign:"+sign);
//					return rsResponse;
//				}
//				Map<String,Object> map = JSONObject.parseObject(requestData, Map.class);
//				//String bankName = bankcardinfo.getBankName();
//				String captcha = String.valueOf(map.get("captcha"));
//				String thirdOrderNo = String.valueOf(map.get("orderId"));
//				String phone = String.valueOf(map.get("registerPhone"));
//				String ptChannelId = RongShuConstant.CHANNELID;//平台渠道号
//				String idNumber = String.valueOf(map.get("cid"));
//				// 获取借款人信息
//				BwBorrower borrower = new BwBorrower();
//				borrower.setIdCard(idNumber);
//				borrower.setPhone(phone);
//				borrower.setFlag(1);// 未删除的
//				borrower = findBwBorrowerByAttrProxy(borrower);
//				logger.info("借款人查询结果：" + JSONObject.toJSONString(borrower));
//				// 查找订单orderNO
//				BwOrderRong bwOrderRong = new BwOrderRong();
//				bwOrderRong.setThirdOrderNo(thirdOrderNo);
//				bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//				logger.info("订单查询结果：" + JSONObject.toJSONString(bwOrderRong));
//
//				Map<String, Object> mapResp = new HashMap<String, Object>();
//				if (!CommUtils.isNull(bwOrderRong)) {
//					BwOrder bwOrder = new BwOrder();
//					bwOrder.setId(bwOrderRong.getOrderId());
//					bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
//					
//					Long orderId = bwOrder.getId();
//					BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//					if(CommUtils.isNull(bwPersonInfo)){
//						logger.info("榕树调用绑卡接口紧急联系人为空:"+JSON.toJSONString(bwPersonInfo));
//						rsResponse.setCode(RsResponse.CODE_FAILERR);
//						rsResponse.setMessage("紧急联系人为空,绑卡失败");
//						return rsResponse;
//					}
//					
//					DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//					drainageBindCardVO.setVerifyCode(captcha);
//					drainageBindCardVO.setChannelId(Integer.parseInt(ptChannelId));
//					drainageBindCardVO.setPhone(phone);
//					drainageBindCardVO.setThirdOrderNo(thirdOrderNo);
//					drainageBindCardVO.setNotify(true);//存redis
//					logger.info("榕树调用绑卡接口入参:"+JSON.toJSONString(drainageBindCardVO));
//					// 公共绑卡接口
//					DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(sessionId, drainageBindCardVO);
//					logger.info("榕树调用绑卡接口结果:"+JSON.toJSONString(drainageRsp));
//							if (null != drainageRsp) {
//								if ("0000".equals(drainageRsp.getCode())) {
//									//绑卡成功
//									rsResponse.setCode(RsResponse.CODE_SUCCESS);
//									rsResponse.setMessage("绑卡成功");
//									mapResp.put("status", "3001");
//									mapResp.put("remark", "绑卡成功");
//									rsResponse.setResponse(JSON.toJSONString(mapResp));
//									
//									/**
//									 * 更新订单进件状态
//									 */
//									bwOrder.setStatusId(2L);
//									bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//									bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//									bwOrderService.updateBwOrder(bwOrder);
//									
//									/**
//									 * 订单状态写入redis
//									 */
//									HashMap<String, String> hm = new HashMap<String, String>();
//									hm.put("channelId", RongShuConstant.CHANNELID + "");
//									hm.put("orderId", String.valueOf(bwOrder.getId()));
//									hm.put("orderStatus", "2");
//									hm.put("result", "");
//									String hmData = JSON.toJSONString(hm);
//									RedisUtils.rpush("tripartite:orderStatusNotify:" + RongShuConstant.CHANNELID, hmData);
//									// 审核 放入redis
//									SystemAuditDto systemAuditDto = new SystemAuditDto();
//									systemAuditDto.setIncludeAddressBook(1);
//									systemAuditDto.setOrderId(bwOrder.getId());
//									systemAuditDto.setBorrowerId(borrower.getId());
//									systemAuditDto.setName(borrower.getName());
//									systemAuditDto.setPhone(borrower.getPhone());
//									systemAuditDto.setIdCard(borrower.getIdCard());
//									systemAuditDto.setChannel(Integer.parseInt(RongShuConstant.CHANNELID));
//									systemAuditDto.setThirdOrderId(thirdOrderNo);
//									systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//									RedisUtils.hset(SystemConstant.AUDIT_KEY, bwOrder.getId() + "", JsonUtils.toJson(systemAuditDto));
//									logger.info(sessionId + ">>> 修改订单状态，并放入redis,参数:"+JSON.toJSONString(systemAuditDto));
//									
//								}
//							}
//				} else {
//					rsResponse.setCode(RsResponse.CODE_PARMERR);
//					rsResponse.setMessage("未找到该工单");
//				}
//
//			} catch (Exception e) {
//				rsResponse.setCode(RsResponse.CODE_FAILERR);
//				rsResponse.setMessage("发生异常");
//				logger.info(sessionId + "榕树绑定银行卡接口发生异常"+e);
//				return rsResponse;
//			}
//
//			return rsResponse;
//
//		}
//	
//	
//
//	/**
//	 * 查询银行卡信息
//	 * 
//	 * @param bwBankCard
//	 * @return
//	 */
//	private BwBankCard findBwBankCardByAttrProxy(BwBankCard bwBankCard) {
//		bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
//		logger.info("银行卡信息查询结果：bwBankCard=" + JSONObject.toJSONString(bwBankCard));
//		return bwBankCard;
//	}
//
//	/**
//	 * 查找借款人
//	 * 
//	 * @param borrower
//	 * @return
//	 */
//	private BwBorrower findBwBorrowerByAttrProxy(BwBorrower borrower) {
//		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//		return borrower;
//	}
//
//}
