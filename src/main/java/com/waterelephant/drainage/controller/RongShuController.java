package com.waterelephant.drainage.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.entity.lianlian.Agreement;
import com.beadwallet.entity.lianlian.CardQueryResult;
import com.beadwallet.entity.lianlian.NotifyNotice;
import com.beadwallet.entity.lianlian.NotifyResult;
import com.beadwallet.entity.lianlian.PlanResult;
import com.beadwallet.entity.lianlian.RepayRequest;
import com.beadwallet.entity.lianlian.RepaymentPlan;
import com.beadwallet.entity.lianlian.RepaymentResult;
import com.beadwallet.entity.lianlian.SignLess;
import com.beadwallet.entity.lianlian.SignalLess;
import com.beadwallet.servcie.LianLianPayService;
import com.beadwallet.utils.RSAUtil;
import com.waterelephant.drainage.entity.rongShu.BankCardInfo;
import com.waterelephant.drainage.entity.rongShu.BwRepaymentPlanVo;
import com.waterelephant.drainage.entity.rongShu.CheckResponse;
import com.waterelephant.drainage.entity.rongShu.OrderPushRequest;
import com.waterelephant.drainage.entity.rongShu.RongShuResponse;
import com.waterelephant.drainage.service.RongShuService;
import com.waterelephant.drainage.util.DrainageUtils;
import com.waterelephant.drainage.util.rongshu.BankUtil;
import com.waterelephant.drainage.util.rongshu.RongShuConstant;
import com.waterelephant.drainage.util.rongshu.RongShuUtil;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwPlatformRecord;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.loanwallet.utils.LoanWalletUtils;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwPlatformRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.IBwRepaymentService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.service.impl.BwOrderService;
import com.waterelephant.third.entity.ThirdResponse;
import com.waterelephant.third.service.ThirdCommonService;
import com.waterelephant.third.service.ThirdService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.GenerateSerialNumber;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

/**
 * 榕树（code0087）
 * 
 * Module:
 * 
 * RongShuController.java
 *
 * @author liuDaodao
 * @version 1.0
 * @description: <榕树>
 * @since JDK 1.8
 */
@Controller
public class RongShuController {

	private Logger logger = Logger.getLogger(RongShuController.class);
	// private static final int CHANNELID = 551;
	private static String RONGSHU_XUDAI = "xudai:order_id";
	// private static String APPID = "1009";
	private static String BASE_USER_INFO_REDIS = "rongshu:userInfo";

	@Autowired
	private RongShuService rongShuService;
	@Autowired
	private BwOrderService bwOrderService;
	@Autowired
	private IBwRepaymentService bwRepaymentService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private BwBorrowerService bwBorrowerService;
	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;
	@Autowired
	private ThirdCommonService thirdCommonService;
	@Autowired
	private BwPlatformRecordService bwPlatformRecordService;
	@Autowired
	private ThirdService thirdService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;

	/**
	 * 榕树 - 5.1. 存量用户检验接口
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/rongShu/userCheck.do")
	@ResponseBody
	public RongShuResponse userCheck(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtil.getSessionId();
		logger.info(sessionId + "进入5.1 存量用户检验接口");
		RongShuResponse rongShuResponse = new RongShuResponse();
		try {
			request.setCharacterEncoding("UTF-8");
			// 第一步：取参数
			String cid = request.getParameter("cid"); // 身份证号
			String phone = request.getParameter("phone"); // 手机号
			String name = request.getParameter("name"); // 用户姓名
			String channelId = request.getParameter("channelId");
			String sign = request.getParameter("sign");
			// 第二步：参数非空验证
			if (StringUtils.isEmpty(cid)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数cid为空");
				logger.info(sessionId + "结束5.1 存量用户检验接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			if (StringUtils.isEmpty(channelId)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数channelId为空");
				logger.info(sessionId + "结束5.1 存量用户检验接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			if (StringUtils.isEmpty(phone)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数phone为空");
				logger.info(sessionId + "结束5.1 存量用户检验接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			if (StringUtils.isEmpty(name)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数name为空");
				logger.info(sessionId + "结束5.1 存量用户检验接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			if (StringUtils.isEmpty(sign)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数sign为空");
				logger.info(sessionId + "结束5.1 存量用户检验接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			logger.info("5.1存量用户接口接受参数~~~cid=" + cid + "phone=" + phone + "name=" + name + "channelId=" + channelId
					+ "sign=" + sign);

			sign = URLDecoder.decode(sign);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("phone", phone);
			paramMap.put("cid", cid);
			paramMap.put("name", name);
			paramMap.put("channelId", channelId);
			// 验签
			boolean isOk = RongShuUtil.checkSign(sign, paramMap);
			logger.info("存量用户检验接口验签结果：" + isOk);
			if (!isOk) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("验签不通过");
				logger.info(sessionId + "结束5.1 存量用户检验接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}

			cid = cid.substring(10, cid.length());
			phone = phone.substring(7, phone.length());
			// 第三步：用户检验
			rongShuResponse = rongShuService.userCheck(sessionId, cid, phone, name);
		} catch (Exception e) {
			logger.error(sessionId + "执行5.1 存量用户检验接口异常", e);
			rongShuResponse.setCode(RongShuResponse.CODE_FAIL);
			rongShuResponse.setMessage("接口调用异常，请稍后再试");
		}
		logger.info(sessionId + "结束5.1 存量用户检验接口，返回结果：" + JSON.toJSONString(rongShuResponse));
		return rongShuResponse;
	}

	/**
	 * 榕树 - 5.2. 贷款试算器接口
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/rongShu/loanCalculation.do")
	@ResponseBody
	public RongShuResponse loanCalculation(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtil.getSessionId();
		logger.info(sessionId + "进入5.2 贷款试算期接口");
		RongShuResponse rongShuResponse = new RongShuResponse();
		try {
			// 第一步：取参数
			String loanAmount = request.getParameter("loanAmount"); // 贷款金额
			String loanPeriod = request.getParameter("loanPeriod"); // 贷款周期
			String periodUnit = request.getParameter("periodUnit"); // 周期单位
			String channelId = request.getParameter("channelId");// 渠道号
			String sign = request.getParameter("sign");

			// 第二步：参数验证
			if (DrainageUtils.isInt(loanAmount) == false) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数loanAmount不合法");
				logger.info(sessionId + "结束5.2 贷款试算期接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			if (DrainageUtils.isInt(loanPeriod) == false) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数loanPeriod不合法");
				logger.info(sessionId + "结束5.2 贷款试算期接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			if (DrainageUtils.isInt(periodUnit) == false) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数periodUnit不合法");
				logger.info(sessionId + "结束5.2 贷款试算期接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			if (StringUtils.isEmpty(sign)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数sign为空");
				logger.info(sessionId + "结束5.1 存量用户检验接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}

			sign = URLDecoder.decode(sign);
			logger.info("存量用户接口接受参数~~~loanAmount=" + loanAmount + "loanPeriod=" + loanPeriod + "periodUnit="
					+ periodUnit + "channelId=" + channelId + "sign=" + sign);

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("loanAmount", loanAmount);
			paramMap.put("loanPeriod", loanPeriod);
			paramMap.put("periodUnit", periodUnit);
			paramMap.put("channelId", channelId);
			// 验签
			logger.info("贷款试算期接口-------开始验签-------");
			boolean isOk = RongShuUtil.checkSign(sign, paramMap);
			if (!isOk) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("验签不通过");
				logger.info(sessionId + "结束5.2 贷款试算期接口异常，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			// 第三步：开始计算
			int loanAmountNum = Integer.parseInt(loanAmount);
			int loanPeriodNum = Integer.parseInt(loanPeriod);
			int periodUnitNum = Integer.parseInt(periodUnit);
			rongShuResponse = rongShuService.loanCalculation(sessionId, loanAmountNum, loanPeriodNum, periodUnitNum);
		} catch (Exception e) {
			logger.error(sessionId + "执行5.2 贷款试算期接口异常", e);
			rongShuResponse.setCode(RongShuResponse.CODE_FAIL);
			rongShuResponse.setMessage("接口调用异常，请稍后再试");
		}
		logger.info(sessionId + "结束5.2 贷款试算期接口，返回结果：" + JSON.toJSONString(rongShuResponse));
		return rongShuResponse;
	}

	/**
	 * 榕树 - 5.3. 进件推送接口
	 *
	 * @return
	 */
	@RequestMapping("/rongShu/pushOrder.do")
	@ResponseBody
	public RongShuResponse pushOrder(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtil.getSessionId();
		logger.info(sessionId + "进入5.3 进件推送接口");
		RongShuResponse rongShuResponse = new RongShuResponse();
		try {
			String data = request.getParameter("data");
			String channelId = request.getParameter("channelId");
			String sign = request.getParameter("sign");

			// 参数非空验证
			if (CommUtils.isNull(data)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数data不合法");
				return rongShuResponse;
			}
			if (CommUtils.isNull(channelId)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数channelId不合法");
				return rongShuResponse;
			}
			if (CommUtils.isNull(sign)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数sign不合法");
				return rongShuResponse;
			}

			sign = URLDecoder.decode(sign);
			// 验签
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("data", data);
			paramMap.put("channelId", channelId);
			// 验签
			boolean isOK = RongShuUtil.checkSign(sign, paramMap);
			if (!isOK) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("验签不通过");
				logger.info(sessionId + "结束5.3 进件推送接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}

			OrderPushRequest orderPushRequest = JSONObject.parseObject(data, OrderPushRequest.class);
			rongShuResponse = rongShuService.saveUserInfo(orderPushRequest);
			logger.info("返回的code:" + rongShuResponse.getCode());

			// 判断
			if (rongShuResponse.getCode() == 0) {
				// REDIS
				Long resultNum = RedisUtils.rpush(BASE_USER_INFO_REDIS, JSONObject.toJSONString(orderPushRequest));
				if (!CommUtils.isNull(resultNum)) {
					logger.info("存放redis-list[" + BASE_USER_INFO_REDIS + "]成功");
					rongShuResponse.setCode(RongShuResponse.CODE_SUCCESS);
					rongShuResponse.setMessage("成功");
				} else {
					logger.info("存放redis-list[" + BASE_USER_INFO_REDIS + "]失败");
					rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
					rongShuResponse.setMessage("内部错误");
				}
			}
		} catch (Exception e) {
			logger.error(sessionId + "执行5.3 进件推送接口异常", e);
			rongShuResponse.setCode(RongShuResponse.CODE_FAIL);
			rongShuResponse.setMessage("接口调用异常，请稍后再试");
		}
		logger.info(sessionId + "结束5.3 进件推送接口，返回结果：" + JSON.toJSONString(rongShuResponse));
		return rongShuResponse;
	}

	// 5.9 审核结果查询接口
	@RequestMapping("/rongShu/queryAuditResult.do")
	@ResponseBody
	public RongShuResponse queryAuditResult(HttpServletRequest request, HttpServletResponse response) {
		RongShuResponse resp = new RongShuResponse();
		String sessionId = DateUtil.getSessionId();
		// 获取平台传过来的工单号
		String thridOrerId = request.getParameter("orderId");
		String sign = request.getParameter("sign");

		try {
			// 非空验证
			if (CommUtils.isNull(thridOrerId)) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("参数orderId为空");
				logger.info("结束5.9 审核结果查询接口，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}
			if (StringUtils.isEmpty(sign)) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("参数sign为空");
				logger.info(sessionId + "结束5.1 存量用户检验接口，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}

			sign = URLDecoder.decode(sign);
			// 验签
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("orderId", thridOrerId);

			boolean isOk = RongShuUtil.checkSign(sign, paramMap);
			if (!isOk) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("验签不通过");
				logger.info("结束5.9 审核结果查询接口，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}
			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setThirdOrderNo(thridOrerId);
			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("参数不合法,未找到该工单号");
				return resp;
			}
			// 根据工单号查询
			BwOrder bwOrder = new BwOrder();
			bwOrder.setId(bwOrderRong.getOrderId());
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
			if (CommUtils.isNull(bwOrder)) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("参数不合法,未找到该工单号");
				return resp;
			}

			// 获取利率字典表信息
			BwProductDictionary bwProductDictionary = bwProductDictionaryService
					.findById(bwOrder.getProductId().longValue());
			Double fee = bwProductDictionary.getpFastReviewCost() + bwProductDictionary.getpPlatformUseCost()
					+ bwProductDictionary.getpNumberManageCost() + bwProductDictionary.getpCapitalUseCost()
					+ bwProductDictionary.getpCollectionPassagewayCost();
			Double zjw = bwProductDictionary.getZjwCost();

			String status = String.valueOf(bwOrder.getStatusId());
			CheckResponse checkResponse = new CheckResponse();
			checkResponse.setAppId(RongShuConstant.appId);
			checkResponse.setOrderId(thridOrerId);
			// 判断状态
			if ("4".equals(status) || "11".equals(status) || "12".equals(status) || "14".equals(status)
					|| "5".equals(status) || "9".equals(status) || "13".equals(status) || "6".equals(status)) {
				checkResponse.setStatus(String.valueOf(RongShuResponse.CODE_CHECKTHROUGH));
			} else if ("7".equals(status) || "8".equals(status)) {
				checkResponse.setStatus(String.valueOf(RongShuResponse.CODE_CHECKREJECT));
			}
			checkResponse.setLoanAmount(bwOrder.getBorrowAmount() + "");
			checkResponse.setPeriod(bwProductDictionary.getpTerm());
			checkResponse.setActualAmount(String.valueOf(bwOrder.getBorrowAmount() - bwOrder.getBorrowAmount() * fee));
			checkResponse.setRefundAmount(String.valueOf(bwOrder.getBorrowAmount() + bwOrder.getBorrowAmount() * zjw));

			resp.setCode(RongShuResponse.CODE_SUCCESS);
			resp.setMessage("成功");
			resp.setResponse(checkResponse);
		} catch (Exception e) {
			logger.info("榕树拉取订单状态异常" + e);
			resp.setCode(RongShuResponse.CODE_FAIL);
			resp.setMessage("接口调用异常，请稍后再试");
		}
		logger.info(sessionId + "结束5.9 审核结果查询接口，返回结果：" + JSON.toJSONString(resp));
		return resp;
	}

	// 5.11.放款结果查询接口
	@RequestMapping("/rongShu/queryFk.do")
	@ResponseBody
	public RongShuResponse queryFkResult(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtil.getSessionId();
		logger.info(sessionId + "进入5.11 放款结果查询接口");
		RongShuResponse resp = new RongShuResponse();
		// 获取平台传过来的工单号
		String thridOrerId = request.getParameter("orderId");
		String sign = request.getParameter("sign");

		try {
			// 非空验证
			if (CommUtils.isNull(thridOrerId)) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("参数orderId为空");
				logger.info("放款结果查询接口，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}
			if (CommUtils.isNull(sign)) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("参数sign为空");
				logger.info("放款结果查询接口，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}
			sign = URLDecoder.decode(sign);
			// 验签
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("orderId", thridOrerId);
			boolean isOk = RongShuUtil.checkSign(sign, paramMap);
			if (!isOk) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("验签不通过");
				logger.info("结束5.11放款结果查询接口，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}
			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setThirdOrderNo(thridOrerId);
			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("参数不合法,未找到该工单号");
				return resp;
			}

			// 根据工单号查询
			BwOrder bwOrder = new BwOrder();
			bwOrder.setId(bwOrderRong.getOrderId());
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
			if (CommUtils.isNull(bwOrder)) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("参数不合法,未找到该工单号");
				logger.info("结束5.11放款结果查询接口，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}
			if (bwOrder.getStatusId() != 9) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("还未放款");
				logger.info("结束5.11放款结果查询接口，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}

			// 查询还款计划
			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
			bwRepaymentPlan.setOrderId(bwOrder.getId());
			bwRepaymentPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(bwRepaymentPlan.getOrderId());
			if (bwRepaymentPlan == null) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("无还款计划");
				logger.info("结束5.11放款结果查询接口，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}

			// 获取逾期记录
			// BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
			// bwOverdueRecord.setOrderId(bwOrder.getId());
			// bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);

			// 获取利率字典表信息
			BwProductDictionary bwProductDictionary = bwProductDictionaryService
					.findById(bwOrder.getProductId().longValue());
			Double fee = bwProductDictionary.getpFastReviewCost() + bwProductDictionary.getpPlatformUseCost()
					+ bwProductDictionary.getpNumberManageCost() + bwProductDictionary.getpCapitalUseCost()
					+ bwProductDictionary.getpCollectionPassagewayCost();

			BwRepaymentPlanVo bwRepaymentPlanvo = new BwRepaymentPlanVo();
			bwRepaymentPlanvo.setAmount(String.valueOf(bwRepaymentPlan.getRealityRepayMoney()));
			bwRepaymentPlanvo.setPeriodNo("1");
			bwRepaymentPlanvo.setCanRepayTime(String.valueOf(bwRepaymentPlan.getCreateTime().getTime()));
			bwRepaymentPlanvo.setDueTime(String.valueOf(bwRepaymentPlan.getRepayTime().getTime()));
			bwRepaymentPlanvo.setPayType("5");

			List<BwRepaymentPlanVo> plans = new ArrayList<>();
			plans.add(bwRepaymentPlanvo);

			Map<String, Object> map = new HashMap<>();
			map.put("appId", RongShuConstant.appId);
			map.put("orderId", thridOrerId);
			map.put("contractId", String.valueOf(bwOrder.getId()));
			map.put("status", String.valueOf(RongShuResponse.CODE_LOANSUCCESS));
			map.put("loanAmount", bwOrder.getBorrowAmount() - bwOrder.getBorrowAmount() * fee);
			map.put("refundAmount", bwRepaymentPlan.getRealityRepayMoney());
			map.put("updateTime", bwOrder.getUpdateTime().getTime());
			map.put("repayPlan", plans);

			resp.setCode(RongShuResponse.CODE_SUCCESS);
			resp.setMessage("成功");
			resp.setResponse(map);

		} catch (Exception e) {
			logger.error("放款结果查询接口异常", e);
			resp.setCode(RongShuResponse.CODE_FAIL);
			resp.setMessage("接口调用异常，请稍后再试");
		}
		logger.info(sessionId + "结束5.11放款结果查询接口，返回结果：" + JSON.toJSONString(resp));
		return resp;
	}

	// 5.13.主动还款试算器接口
	@RequestMapping("/rongShu/paymentCell.do")
	@ResponseBody
	public RongShuResponse paymentCell(HttpServletRequest request, HttpServletResponse response) {

		String sessionId = DateUtil.getSessionId();
		logger.info(sessionId + "进入5.13 主动还款试算器期接口");
		RongShuResponse rongShuResponse = new RongShuResponse();
		try {
			// 第一步：取参数
			String orderId = request.getParameter("orderId"); // 流水号
			String period = request.getParameter("period"); // 贷款周期
			String sign = request.getParameter("sign");
			String channelId = request.getParameter("channelId");

			// 第二步：参数验证
			if (CommUtils.isNull(orderId)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数orderId为空");
				logger.info(sessionId + "结束5.13 主动还款试算期接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			if (CommUtils.isNull(period)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数loanPeriod为空");
				logger.info(sessionId + "结束5.13 主动还款试算期接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			if (StringUtils.isEmpty(sign)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数sign为空");
				logger.info(sessionId + "结束5.13 主动还款接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			sign = URLDecoder.decode(sign);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("orderId", orderId);
			paramMap.put("period", period);
			paramMap.put("channelId", channelId);
			// 验签
			logger.info(sessionId + "5.13.主动还款试算器接口-------开始验签-------");
			boolean isOk = RongShuUtil.checkSign(sign, paramMap);
			if (!isOk) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("验签不通过");
				logger.info(sessionId + "结束5.13贷款试算期接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setThirdOrderNo(orderId);
			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
			if (CommUtils.isNull(bwOrderRong)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("订单不存在");
				logger.info(sessionId + "结束5.15主动还款接口，OrderRong工单为空，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			// 第三步：开始计算
			rongShuResponse = rongShuService.figureRepayMent(sessionId, String.valueOf(bwOrderRong.getOrderId()),
					period);
		} catch (Exception e) {
			logger.error(sessionId + "执行5.13 贷款试算期接口异常", e);
			rongShuResponse.setCode(RongShuResponse.CODE_FAIL);
			rongShuResponse.setMessage("接口调用异常，请稍后再试");
		}
		logger.info(sessionId + "结束5.13 贷款试算期接口，返回结果：" + JSON.toJSONString(rongShuResponse));
		return rongShuResponse;
	}

	// 5.14.主动还款接口
	@RequestMapping("/rongShu/repayment.do")
	@ResponseBody
	public RongShuResponse repayment(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtil.getSessionId();
		logger.info(sessionId + "进入5.14主动还款接口------");
		RongShuResponse resp = new RongShuResponse();
		try {
			String thirdOrderId = request.getParameter("orderId");
			String outTradeNo = request.getParameter("outTradeNo");
			String period = request.getParameter("period");
			String channelId = request.getParameter("channelId");
			String sign = request.getParameter("sign");

			// 非空判断
			if (CommUtils.isNull(thirdOrderId)) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("参数 orderId 不合法");
				logger.info(sessionId + "结束5.14主动还款接口，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}
			if (CommUtils.isNull(outTradeNo)) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("参数 outTradeNo 不合法");
				logger.info(sessionId + "结束5.14主动还款接口，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}
			if (CommUtils.isNull(period)) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("参数 orderId 不合法");
				logger.info(sessionId + "结束5.14主动还款接口，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}
			if (CommUtils.isNull(sign)) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("参数 sign 不合法");
				logger.info(sessionId + "结束5.14主动还款接口，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}
			sign = URLDecoder.decode(sign);
			// 验签
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("orderId", thirdOrderId);
			paramMap.put("outTradeNo", outTradeNo);
			paramMap.put("period", period);
			paramMap.put("channelId", channelId);
			logger.info(sessionId + "5.14主动还款接口-------开始验签-------");
			boolean isOk = RongShuUtil.checkSign(sign, paramMap);
			if (!isOk) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("验签不通过");
				logger.info(sessionId + "结束5.14主动还款接口，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}
			BwOrderRong bwOrderRong = getByOrderNoProxy(thirdOrderId);
			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("系统异常");
				logger.info(sessionId + "结束5.14主动还款接口，OrderRong工单为空，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}
			Long orderId = bwOrderRong.getOrderId();
			BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
			if (CommUtils.isNull(bwOrder)) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("系统异常");
				logger.info(sessionId + "结束5.14主动还款接口，工单为空，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}
			// 查询银行卡信息
			BwBankCard bankCard = findBwBankCardByBorrowerIdProxy(bwOrder.getBorrowerId());
			if (CommUtils.isNull(bankCard)) {
				resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
				resp.setMessage("系统异常");
				logger.info(sessionId + "结束5.14主动还款接口，银行卡信息为空，返回结果：" + JSON.toJSONString(resp));
				return resp;
			}
			// 查询还款计划
			resp = commonRepay(bwOrder, bankCard, "repay");

		} catch (Exception e) {
			resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
			resp.setMessage("发生异常");
			logger.info(sessionId + "结束5.14主动还款接口，返回结果：" + JSON.toJSONString(resp));
		}

		return resp;

	}

	/**
	 * 还款
	 * 
	 * @param bwOrder
	 * @param bankCard
	 * @param string
	 * @return
	 */
	private RongShuResponse commonRepay(BwOrder bwOrder, BwBankCard bwBankCard, String string) throws Exception {
		RongShuResponse resp = new RongShuResponse();
		logger.info("~~~~~~~~~~~~~~~~~~ 榕树拉取还款计划接口~~~~~~~~~~~~~~~~~~~~~~~");
		String orderId = String.valueOf(bwOrder.getId());
		if (RedisUtils.hexists(RONGSHU_XUDAI, orderId)) {
			resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
			resp.setMessage("系统异常");
			logger.info("此工单正在续贷中");
			return resp;
		}

		if (RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId)) {
			resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
			resp.setMessage("系统异常");
			logger.info("此工单正在宝付支付中");
			return resp;
		}

		if (RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId)) {
			resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
			resp.setMessage("系统异常");
			logger.info("此工单正在连连支付中");
			return resp;
		}
		Long statusId = bwOrder.getStatusId();// 工单状态
		logger.info("工单状态:" + statusId);
		if (!(statusId.intValue() == 9 || statusId.intValue() == 13)) {
			resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
			resp.setMessage("工单只有还款中或逾期中才可还款");
			logger.info("工单只有还款中或逾期中才可还款");
			return resp;
		}
		BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
		bwRepaymentPlan.setOrderId(bwOrder.getId());
		bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);
		if (CommUtils.isNull(bwRepaymentPlan)) {
			resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
			resp.setMessage("系统异常");
			logger.info("没有符合条件的还款计划");
			return resp;
		}
		// 判断该用户是否签约
		Long borrowerId = bwOrder.getBorrowerId();
		logger.info("开始调用连连签约查询接口,borrowerId=" + borrowerId + ",cardNo=" + bwBankCard.getCardNo());
		CardQueryResult cardQueryResult = LianLianPayService.cardBindQuery(borrowerId.toString(),
				bwBankCard.getCardNo());

		logger.info("结束调用连连签约查询接口,cardQueryResult=" + JSONObject.toJSONString(cardQueryResult));

		if (CommUtils.isNull(cardQueryResult)) {
			resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
			resp.setMessage("未签约");
			return resp;
		}

		if (!"0000".equals(cardQueryResult.getRet_code())) {
			resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
			resp.setMessage(LoanWalletUtils.convertLian2Msg(cardQueryResult.getRet_code()));
			logger.info("调用连连签约查询接口返回结果失败，ret_code != 0000");
			return resp;
		}
		List<Agreement> agreements = cardQueryResult.getAgreement_list();
		if (CommUtils.isNull(agreements)) {
			resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
			resp.setMessage("未签约");
			logger.info("调用连连签约查询接口返回结果失败,Agreement_list is null");
			return resp;
		}
		String agreeNo = agreements.get(0).getNo_agree();

		logger.info("调用连连签约查询接口返回结果成功，连连支付协议号:" + agreeNo);

		List<RepaymentPlan> repays = new ArrayList<RepaymentPlan>();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		String amount = String.valueOf(bwRepaymentPlan.getRealityRepayMoney());
		BwOverdueRecord overdueRecord = new BwOverdueRecord();
		overdueRecord.setOrderId(bwOrder.getId());
		overdueRecord = findBwOverdueRecordByAttrProxy(overdueRecord);
		if (!CommUtils.isNull(overdueRecord)) {
			amount = new BigDecimal(amount).add(new BigDecimal(overdueRecord.getOverdueAccrualMoney()))
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}
		logger.info("amount=" + amount);

		RepaymentPlan repay = new RepaymentPlan();
		repay.setAmount(amount);
		repay.setDate(dateFormat2.format(bwRepaymentPlan.getRepayTime()));
		repays.add(repay);
		BwBorrower bwBorrower = findBwBorrowerByIdProxy(bwOrder.getBorrowerId());
		SignalLess signalLess = new SignalLess();
		signalLess.setAcct_name(bwBorrower.getName());
		// signalLess.setApp_request(app_request);
		signalLess.setCard_no(bwBankCard.getCardNo());
		signalLess.setId_no(bwBorrower.getIdCard());
		signalLess.setNo_agree(agreeNo);
		signalLess.setUser_id(borrowerId.toString());
		logger.info("开始调用连连授权接口,signalLess=" + JSONObject.toJSONString(signalLess) + ",orderNo=" + bwOrder.getOrderNo()
				+ ",repays=" + JSONObject.toJSONString(repays));
		PlanResult planResult = LianLianPayService.sigalAccreditPay(signalLess, bwOrder.getOrderNo(), repays);
		logger.info("结束调用连连授权接口，planResult=" + JSONObject.toJSONString(planResult));

		if (CommUtils.isNull(planResult)) {
			resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
			resp.setMessage("支付授权失败");
			logger.info("调用连连授权接口返回结果为空");
			return resp;
		}

		if (!"0000".equals(planResult.getRet_code())) {
			resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
			resp.setMessage(LoanWalletUtils.convertLian2Msg(planResult.getRet_code()));
			logger.info("调用连连授权接口返回结果失败,ret_code != 0000");
			return resp;
		}
		logger.info("调用连连授权接口成功");
		// 支付
		RepayRequest repayRequest = new RepayRequest();
		repayRequest.setNo_order(GenerateSerialNumber.getSerialNumber().substring(8) + bwOrder.getId());
		repayRequest.setUser_id(borrowerId.toString());
		repayRequest.setNo_agree(agreeNo);
		repayRequest.setDt_order(dateFormat.format(new Date()));
		repayRequest.setName_goods("易秒贷");
		repayRequest.setMoney_order(amount);
		repayRequest.setSchedule_repayment_date(repays.get(0).getDate());
		repayRequest.setRepayment_no(bwOrder.getOrderNo());
		repayRequest.setUser_info_bind_phone(bwBorrower.getPhone());
		repayRequest.setUser_info_dt_register(dateFormat.format(bwBorrower.getCreateTime()));
		repayRequest.setUser_info_full_name(bwBorrower.getName());
		repayRequest.setUser_info_id_no(bwBorrower.getIdCard());
		repayRequest.setNotify_url(SystemConstant.NOTIRY_URL + "/rongShu/repaymentNotify.do");

		// 存入连连redis中，有效时间15分钟
		if (!RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId())) {
			RedisUtils.setex(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId(), bwOrder.getId().toString(), 900);
			logger.info("存redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId().toString() + "]");
		}

		logger.info("开始调用连连支付接口，repayRequest=" + JSONObject.toJSONString(repayRequest));
		RepaymentResult repaymentResult = LianLianPayService.bankRepay(repayRequest);
		logger.info("结束调用连连支付接口，repaymentResult=" + JSONObject.toJSONString(repaymentResult));

		if (CommUtils.isNull(repaymentResult)) {
			resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
			resp.setMessage("支付失败");
			logger.info("调用连连支付接口返回结果为空");
			return resp;
		}

		if (!"0000".equals(repaymentResult.getRet_code()) && !"1003".equals(repaymentResult.getRet_code())) {
			resp.setCode(RongShuResponse.CODE_PARAMETERERROR);
			resp.setMessage(LoanWalletUtils.convertLian2Msg(repaymentResult.getRet_code()) == null
					? repaymentResult.getRet_msg() : LoanWalletUtils.convertLian2Msg(repaymentResult.getRet_code()));
			// 删除连连REDIS
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId() + "]");
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId());
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId() + "]成功");
			logger.info("调用连连支付接口返回结果失败,ret_code != 0000");
			return resp;
		}
		resp.setCode(RongShuResponse.CODE_SUCCESS);
		resp.setMessage("支付成功");
		return resp;
	}

	// 5.16.订单列表数据接口
	// @RequestMapping("/rongShu/getOrderList.do")
	// @ResponseBody
	// public RongShuResponse getOrderList(HttpServletRequest request, HttpServletResponse response) {
	// String sessionId = DateUtil.getSessionId();
	// logger.info(sessionId + "进入5.16.订单列表数据接口");
	// RongShuResponse rongShuResponse = new RongShuResponse();
	// String beginTime = request.getParameter("beginTime");
	// String endTime = request.getParameter("endTime");
	// try {
	// Map<String, String> paramap = new HashMap<String, String>();
	// paramap.put("appId", APPID);
	// paramap.put("beginTime", beginTime);
	// paramap.put("endTime", endTime);
	// paramap.put("timestamp", sessionId);
	// String checkSign = SignUtil.rsaSign(paramap, RongShuConstant.PRI_KEY, "UTF-8");// 加签
	// checkSign = URLEncoder.encode(checkSign, "utf-8");
	// // 发送请求
	// StringBuffer buffer = new StringBuffer();
	// buffer.append("appId=" + APPID);
	// buffer.append("&beginTime=" + beginTime);
	// buffer.append("&endTime=" + endTime);
	// buffer.append("&timestamp=" + sessionId);
	// buffer.append("&sign=" + checkSign);
	// String httpUrl = RongShuResponse.DEFAULT_URL + "/order/list.do";
	// String callback = HttpRequest.doPost(httpUrl, buffer.toString());
	// logger.info("5.16.订单列表数据接口" + callback);
	// CallbackData callbackData = JSONObject.parseObject(callback, CallbackData.class);
	// if ("0".equals(callbackData.getCode())) {
	//
	// }
	//
	// } catch (Exception e) {
	//
	// }
	// return rongShuResponse;
	// }

	// 5.19.绑定银行卡接口
	@RequestMapping("/rongShu/bindCard.do")
	@ResponseBody
	public RongShuResponse bindCard(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtil.getSessionId();
		logger.info(sessionId + "进入5.19 绑定银行卡接口");
		RongShuResponse rongShuResponse = new RongShuResponse();
		try {
			String data = request.getParameter("data");
			String channelId = request.getParameter("channelId");
			String sign = request.getParameter("sign");
			String thirdOrderNo = request.getParameter("orderId");
			// 参数非空验证
			logger.info("开始验证参数data=" + data + "~~~~channelId=" + channelId + "~~~~sign=" + sign);
			if (CommUtils.isNull(data)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数data不合法");
				return rongShuResponse;
			}
			if (CommUtils.isNull(channelId)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数channelId不合法");
				return rongShuResponse;
			}
			if (CommUtils.isNull(sign)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数sign不合法");
				return rongShuResponse;
			}
			if (CommUtils.isNull(thirdOrderNo)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数sign不合法");
				return rongShuResponse;
			}
			sign = URLDecoder.decode(sign);
			// 验签
			logger.info("绑定银行卡接口-------开始验签--------");
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("data", data);
			paramMap.put("channelId", channelId);
			paramMap.put("orderId", thirdOrderNo);
			boolean isOK = RongShuUtil.checkSign(sign, paramMap);
			logger.info("绑定银行卡接口验签结果：" + isOK);
			if (!isOK) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("验签不通过");
				logger.info(sessionId + "结束5.19 绑定银行卡接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			BankCardInfo bankcardinfo = JSONObject.parseObject(data, BankCardInfo.class);
			// 根据bankCode从工具类中获取对应的bankName
			String bankName = BankUtil.getname(bankcardinfo.getBankCardNum());
			if (CommUtils.isNull(bankName)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("暂不支持，请更换银行卡");
				logger.info(sessionId + "结束5.19 绑定银行卡接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			String idNumber = bankcardinfo.getCid();
			String bankCode = BankUtil.convertToBankCode(bankName);
			// 获取借款人信息
			BwBorrower borrower = new BwBorrower();
			borrower.setIdCard(idNumber);
			borrower.setPhone(bankcardinfo.getRegisterPhone());
			borrower.setFlag(1);// 未删除的
			borrower = findBwBorrowerByAttrProxy(borrower);
			logger.info("借款人查询结果：" + JSONObject.toJSONString(borrower));
			// 查找订单orderNO
			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setThirdOrderNo(thirdOrderNo);
			bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
			logger.info("订单查询结果：" + JSONObject.toJSONString(bwOrderRong));
			if (!CommUtils.isNull(bwOrderRong)) {
				BwOrder bwOrder = new BwOrder();
				bwOrder.setId(bwOrderRong.getOrderId());
				bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
				// 查询银行卡信息
				BwBankCard bbc = saveOrUpdateBBC(borrower, bankcardinfo, bankCode, bankName);
				logger.info("rongShuController查询银行卡信息完毕");
				Map<String, String> map = new HashMap<String, String>();
				String userId = String.valueOf(borrower.getId());// 借款人id
				// String idNo = bankcardinfo.getCid();// 证件号码 18位
				// String accName = bankcardinfo.getName();// 姓名
				String cardNo = bbc.getCardNo();// 银行卡号
				String orderNo = bwOrder.getOrderNo();// 订单号
				String callbackurl = bankcardinfo.getCallbackurl();
				RedisUtils.hset("third:bindCard:successReturnUrl:" + channelId, "orderNO_" + orderNo,
						callbackurl + "&status=1");
				RedisUtils.hset("third:bindCard:failReturnUrl:" + channelId, "orderNO_" + orderNo,
						callbackurl + "&status=0");
				map.put("url", SystemConstant.NOTIRY_URL + "/rongShu/bindCard/common/bindCard.do?borrowerId=" + userId
						+ "&bankCardNO=" + cardNo + "&orderNO=" + orderNo);
				rongShuResponse.setCode(RongShuResponse.CODE_BINDINGSUCCESS);
				rongShuResponse.setMessage("成功");
				rongShuResponse.setResponse(map);

			} else {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("未找到该工单");
			}

		} catch (Exception e) {
			rongShuResponse.setCode(RongShuResponse.CODE_BINDINGFAIL);
			rongShuResponse.setMessage("发生异常");
			logger.info(sessionId + "5.19 绑定银行卡接口发生异常");
		}

		return rongShuResponse;

	}

	/**
	 * 绑卡 - 无界面 - 绑卡（code0095）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/rongShu/bindCard/common/bindCard.do")
	public void rongShubindCard(HttpServletRequest request, HttpServletResponse response) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "：开始controller层绑卡接口");
		try {
			// 第一步：获取参数
			String borrowerID = request.getParameter("borrowerId"); // 用户ID
			String orderNO = request.getParameter("orderNO"); // 订单编号
			String bankCardNO = request.getParameter("bankCardNO"); // 银行卡号

			BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(orderNO);
			BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerByOrderId(bwOrder.getId());

			RedisUtils.hset("third:bindCard", borrowerID, orderNO); // 放入redis用于回调使用

			// 第二步：连连签约
			String urlReturn = SystemConstant.NOTIRY_URL + "/rongShu/bindCardCallback.do";
			SignLess signLess = new SignLess();
			signLess.setUser_id(borrowerID);
			signLess.setId_no(bwBorrower.getIdCard());
			signLess.setAcct_name(bwBorrower.getName());
			signLess.setCard_no(bankCardNO);
			signLess.setUrl_return(urlReturn);
			logger.info(sessionId + "开始调用连连绑卡接口,signLess=" + JSONObject.toJSONString(signLess));
			LianLianPayService.signAccreditPay(signLess, response);
			logger.info(sessionId + "结束调用连连绑卡接口");
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层绑卡接口异常", e);
		}
		logger.info(sessionId + "结束controller层绑卡接口");
	}

	/**
	 * 统一对外接口 - 绑卡 - 回调（code0091）
	 * 
	 * @param thirdRequest
	 * @return
	 */
	@RequestMapping("/rongShu/bindCardCallback.do")
	public String rongShubindCardCallback(HttpServletRequest request, HttpServletResponse response) {
		long sessionId = System.currentTimeMillis();
		logger.info(sessionId + "：开始controller层绑卡回调接口");

		// 第一步：获取参数
		String status = request.getParameter("status"); // 状态
		String result = request.getParameter("result"); // 返回结果
		// JSONObject jsonObject = JSONObject.parseObject(result);
		String userId = request.getParameter("user_id");// 用户ID
		logger.info("返回状态" + status);
		if (userId == null) {
			JSONObject jsonObject = JSONObject.parseObject(result);
			userId = jsonObject.getString("user_id");
		}
		// String userId = jsonObject.getString("user_id");
		String orderNo = RedisUtils.hget("third:bindCard", userId);
		BwOrder bwOrder = bwOrderService.findBwOrderByOrderNo(orderNo);
		Integer channelId = bwOrder.getChannel();
		Long orderId = bwOrder.getId();
		Map<String, Object> map = new HashMap<>();
		map.put("channelId", channelId);
		map.put("orderId", orderId);
		map.put("result", result);
		String json = JSON.toJSONString(map);
		logger.info("存入redis的数据：" + json);
		String successUrl = RedisUtils.hget("third:bindCard:successReturnUrl:" + channelId, "orderNO_" + orderNo);
		logger.info("榕树绑卡接口返回成功页面：" + successUrl);
		// 失败跳转
		String failReturnUrl = RedisUtils.hget("third:bindCard:failReturnUrl:" + channelId, "orderNO_" + orderNo); // 失败后的跳转URL
		logger.info("榕树绑卡接口返回失败页面：" + failReturnUrl);
		try {
			// 第二步：验证参数
			if (CommUtils.isNull(status) || CommUtils.isNull(result)) {
				// 绑卡状态通知
				logger.info(sessionId + "：结束controller层绑卡回调接口，入参为空");
				RedisUtils.hdel("third:bindCard:failReturnUrl:" + channelId, "orderNO_" + orderNo);
				RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
				return "redirect:" + failReturnUrl;
			}
			// 判断是否重复绑卡
			// 获取银行卡信息
			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
			if (!CommUtils.isNull(bwBankCard) && 2 == bwBankCard.getSignStatus()) {
				RedisUtils.hdel("third:bindCard:successReturnUrl:" + channelId, "orderNO_" + orderNo);
				RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
				if (!CommUtils.isNull(successUrl)) {
					return "redirect:" + successUrl;
				} else {
					return "sign_success_third";
				}
			}
			boolean isSuccess = thirdService.updateBindCardCallback(sessionId, status, result);
			RedisUtils.hdel("third:bindCard:successReturnUrl:" + channelId, "orderNO_" + orderNo);
			if (isSuccess == true) {
				logger.info(sessionId + "结束controller层绑卡回调接口");
				RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
				if (!CommUtils.isNull(successUrl)) {
					return "redirect:" + successUrl;
				} else {
					return "sign_success_third";
				}
			} else {
				logger.info(sessionId + ".........结束controller层绑卡回调接口");
				RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
				if (!CommUtils.isNull(failReturnUrl)) {
					return "redirect:" + failReturnUrl;
				} else {
					return "sign_fail_third";
				}
			}
		} catch (Exception e) {
			logger.error(sessionId + "执行controller层绑卡回调接口异常", e);
			RedisUtils.lpush("tripartite:bindCardNotify:" + channelId, json);
			return "sign_fail_third";
		}
	}

	// 5.22.用户确认借款接口
	@ResponseBody
	@RequestMapping("/rongShu/confirm.do")
	public RongShuResponse confirm(HttpServletRequest request, HttpServletResponse response) {

		String sessionId = DateUtil.getSessionId();
		logger.info(sessionId + "进入5.22.用户确认借款接口~~~~");
		RongShuResponse rongShuResponse = new RongShuResponse();
		try {
			String channelId = request.getParameter("channelId");
			String sign = request.getParameter("sign");
			String thirdOrderNo = request.getParameter("orderId");
			String uid = request.getParameter("uid");
			String loanAmount = request.getParameter("loanAmount");
			String period = request.getParameter("period");
			String periodUnit = request.getParameter("periodUnit");

			// 参数非空验证
			if (CommUtils.isNull(thirdOrderNo)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数orderId不合法");
				return rongShuResponse;
			}
			if (CommUtils.isNull(channelId)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数channelId不合法");
				return rongShuResponse;
			}
			if (CommUtils.isNull(sign)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数sign不合法");
				return rongShuResponse;
			}
			sign = URLDecoder.decode(sign);
			// 验签
			logger.info("用户确认借款接口-------开始验签--------");
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("orderId", thirdOrderNo);
			paramMap.put("channelId", channelId);
			paramMap.put("uid", uid);
			paramMap.put("loanAmount", loanAmount);
			paramMap.put("period", period);
			paramMap.put("periodUnit", periodUnit);
			boolean isOK = RongShuUtil.checkSign(sign, paramMap);
			logger.info("5.22确认借款接口验签结果：" + isOK);
			if (!isOK) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("验签不通过");
				logger.info(sessionId + "结束5.22确认借款接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			BwOrderRong bwRong = new BwOrderRong();
			bwRong.setThirdOrderNo(thirdOrderNo);
			bwRong = bwOrderRongService.findBwOrderRongByAttr(bwRong);
			if (CommUtils.isNull(bwRong)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("工单不存在");
				logger.info(sessionId + "结束5.22确认借款接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			BwOrder bwOrder = new BwOrder();
			bwOrder.setId(bwRong.getOrderId());
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
			if (CommUtils.isNull(bwOrder)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("系统错误");
				logger.info(sessionId + "结束5.22确认借款接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			double loan_amountBD = Double.parseDouble(loanAmount);
			double borrow_amountBD = bwOrder.getBorrowAmount().doubleValue();
			if (loan_amountBD - borrow_amountBD > 0) {
				logger.info("开始验证金额：" + loan_amountBD + "," + borrow_amountBD);
				rongShuResponse.setCode(-1);
				rongShuResponse.setMessage("贷款金额超出范围");
				return rongShuResponse;
			}
			ThirdResponse tResponse = thirdCommonService.updateSignContract(thirdOrderNo, Integer.parseInt(channelId));
			if (tResponse.getCode() == 200) {
				rongShuResponse.setCode(RongShuResponse.CODE_SUCCESS);
				rongShuResponse.setMessage("成功");
			} else {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("失败");
			}

		} catch (Exception e) {
			rongShuResponse.setMessage("发生异常");
			logger.info(sessionId + "5.22确认借款接口发生异常");

		}

		return rongShuResponse;

	}

	// 5.28. H5 绑卡 结果 查询 接口
	@ResponseBody
	@RequestMapping("/rongShu/queryBindCardResult.do")
	public RongShuResponse queryBindCardResult(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = DateUtil.getSessionId();
		logger.info(sessionId + "进入5.28. H5 绑卡 结果 查询 接口~~~~");
		RongShuResponse rongShuResponse = new RongShuResponse();
		try {
			String sign = request.getParameter("sign");
			String thirdOrderNo = request.getParameter("orderId");

			// 参数非空验证
			if (CommUtils.isNull(thirdOrderNo)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数orderId不合法");
				return rongShuResponse;
			}
			if (CommUtils.isNull(sign)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("参数sign不合法");
				return rongShuResponse;
			}
			sign = URLDecoder.decode(sign);
			// 验签
			logger.info("5.28. H5 绑卡 结果 查询 接口-------开始验签--------");
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("orderId", thirdOrderNo);
			boolean isOK = RongShuUtil.checkSign(sign, paramMap);
			logger.info("5.28. H5 绑卡 结果 查询 接口验签结果：" + isOK);
			if (!isOK) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("验签不通过");
				logger.info(sessionId + "结束5.28. H5 绑卡 结果 查询 接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			BwOrderRong bwRong = new BwOrderRong();
			bwRong.setThirdOrderNo(thirdOrderNo);
			bwRong = bwOrderRongService.findBwOrderRongByAttr(bwRong);
			if (CommUtils.isNull(bwRong)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("工单不存在");
				logger.info(sessionId + "结束5.28. H5 绑卡 结果 查询 接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}
			BwOrder bwOrder = new BwOrder();
			bwOrder.setId(bwRong.getOrderId());
			bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
			if (CommUtils.isNull(bwOrder)) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("工单不存在");
				logger.info(sessionId + "结束5.28. H5 绑卡 结果 查询 接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}

			// 查询银行卡信息
			BwBankCard bwBankCard = new BwBankCard();
			bwBankCard.setBorrowerId(bwOrder.getBorrowerId());
			bwBankCard = findBwBankCardByAttrProxy(bwBankCard);
			if (bwBankCard == null) {
				rongShuResponse.setCode(RongShuResponse.CODE_PARAMETERERROR);
				rongShuResponse.setMessage("银行卡信息不存在");
				logger.info(sessionId + "结束5.28. H5 绑卡 结果 查询 接口，返回结果：" + JSON.toJSONString(rongShuResponse));
				return rongShuResponse;
			}

			Map<String, String> data = new HashMap<>();
			if (bwBankCard.getSignStatus() == 2) {
				data.put("status", RongShuResponse.CODE_BINDINGSUCCESS + "");
			} else {
				data.put("status", RongShuResponse.CODE_BINDINGFAIL + "");
				// data.put("remark", value);
			}
			data.put("bank", bwBankCard.getBankName());
			data.put("bankCardNum", bwBankCard.getCardNo());
			data.put("phone", bwBankCard.getPhone());

			rongShuResponse.setCode(RongShuResponse.CODE_SUCCESS);
			rongShuResponse.setMessage("查询成功");
			rongShuResponse.setResponse(data);
		} catch (Exception e) {
			rongShuResponse.setCode(RongShuResponse.CODE_FAIL);
			rongShuResponse.setMessage("发生异常");
			logger.debug(sessionId + "5.28. H5 绑卡 结果 查询 接口," + e);
		}
		logger.info(sessionId + "结束5.28. H5 绑卡 结果 查询 接口，返回结果：" + JSON.toJSONString(rongShuResponse));
		return rongShuResponse;
	}

	// 还款回调
	@ResponseBody
	@RequestMapping(value = "/rongShu/repaymentNotify.do")
	public NotifyNotice repaymentNotify(HttpServletRequest request) {
		String methodName = "LoanWalletController.repaymentNotify";
		logger.info(methodName + " start");
		NotifyNotice notice = new NotifyNotice();

		try {
			NotifyResult notifyResult = getNotifyResult(request);
			logger.info("notifyResult=" + JSONObject.toJSONString(notifyResult));

			if (CommUtils.isNull(notifyResult)) {
				notice.setRet_code("101");
				notice.setRet_msg("异步还款通知为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			if (StringUtils.isBlank(notifyResult.getSign())) {
				notice.setRet_code("101");
				notice.setRet_msg("异步还款通知签名为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			if (StringUtils.isBlank(notifyResult.getNo_order())) {
				notice.setRet_code("101");
				notice.setRet_msg("工单id为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			logger.info("开始验证签名...");

			boolean checkSign = checkLianLianSign(notifyResult);
			if (!checkSign) {
				notice.setRet_code("101");
				notice.setRet_msg("验签未通过");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}
			String orderId = notifyResult.getNo_order().substring(20);

			// 验证是否成功
			if (!"SUCCESS".equals(notifyResult.getResult_pay())) {
				notice.setRet_code("102");
				notice.setRet_msg("交易失败");

				try {
					// 还款状态反馈
				} catch (Exception e) {
					logger.error("调用贷款钱包还款状态反馈接口异常", e);
				}
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setOrderId(Long.parseLong(orderId));
			bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);
			if (CommUtils.isNull(bwOrderRong)) {
				notice.setRet_code("101");
				notice.setRet_msg("榕树工单不存在");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			BwOrder order = findBwOrderByIdProxy(Long.parseLong(orderId));

			if (CommUtils.isNull(order)) {
				notice.setRet_code("101");
				notice.setRet_msg("工单不存在");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			if (order.getStatusId().intValue() == 6) {
				notice.setRet_code("0000");
				notice.setRet_msg("交易成功");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			// 查询还款人信息
			BwBorrower borrower = findBwBorrowerByIdProxy(order.getBorrowerId());
			if (CommUtils.isNull(borrower)) {
				notice.setRet_code("101");
				notice.setRet_msg("借款人为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			// 查询银行卡信息
			BwBankCard card = findBwBankCardByBoorwerIdProxy(borrower.getId());
			if (CommUtils.isNull(card)) {
				notice.setRet_code("101");
				notice.setRet_msg("银行卡信息为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			// 记录流水
			logger.info("记录交易流水");
			int platFormInt = savePlatformRecord(notifyResult, card, borrower, order);
			logger.info("记录交易流水结束,新增条数：" + platFormInt);

			// 删除连连REDIS
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId() + "]");
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId());
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId() + "]成功");

			// 修改订单状态
			bwRepaymentService.updateOrderStatus(order.getId());

			try {
				logger.info("还款成功===" + order.getId());
				HashMap<String, String> hm = new HashMap<>();
				hm.put("channelId", CommUtils.toString(order.getChannel()));
				hm.put("orderId", String.valueOf(order.getId()));
				hm.put("orderStatus", "6");
				hm.put("result", "还款成功");
				String hmData = JSON.toJSONString(hm);
				RedisUtils.rpush("tripartite:orderStatusNotify:" + order.getChannel(), hmData);
				// RedisUtils.lpush("tripartite:orderStatusNotify" + CHANNELID, String.valueOf(order.getId()));
			} catch (Exception e) {
				logger.error("调用贷款钱包反馈接口异常", e);
			}

			notice.setRet_code("0000");
			notice.setRet_msg("交易成功");
		} catch (Exception e) {
			logger.error("还款回调异常", e);
			notice.setRet_code("103");
			notice.setRet_msg("交易失败");
		}

		logger.info(methodName + " end,resp=" + JSONObject.toJSONString(notice));
		return notice;
	}

	private boolean checkLianLianSign(NotifyResult notifyResult) {
		Map<String, String> map = new HashMap<>();
		map.put("oid_partner", notifyResult.getOid_partner());
		map.put("sign_type", notifyResult.getSign_type());
		map.put("dt_order", notifyResult.getDt_order());
		map.put("no_order", notifyResult.getNo_order());
		map.put("oid_paybill", notifyResult.getOid_paybill());
		map.put("money_order", notifyResult.getMoney_order());
		map.put("result_pay", notifyResult.getResult_pay());
		map.put("settle_date", notifyResult.getSettle_date());
		map.put("info_order", notifyResult.getInfo_order());
		map.put("pay_type", notifyResult.getPay_type());
		map.put("bank_code", notifyResult.getBank_code());
		map.put("no_agree", notifyResult.getNo_agree());
		map.put("id_type", notifyResult.getId_type());
		map.put("id_no", notifyResult.getId_no());
		map.put("acct_name", notifyResult.getAcct_name());
		map.put("card_no", notifyResult.getCard_no());
		String osign = RSAUtil.sortParams(map);

		return RSAUtil.checksign(SystemConstant.YT_PUB_KEY, osign, notifyResult.getSign());
	}

	private BwBankCard findBwBankCardByBoorwerIdProxy(Long borrowerId) {
		// logger.info("开始查询银行卡信息,borrowerId=" + borrowerId);
		BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
		// logger.info("结束查询银行卡信息,bwBankCard=" +
		// JSONObject.toJSONString(bwBankCard));
		return bwBankCard;
	}

	private int savePlatformRecord(NotifyResult notifyResult, BwBankCard card, BwBorrower borrower, BwOrder order) {
		BwPlatformRecord bwPlatformRecord = new BwPlatformRecord();
		bwPlatformRecord.setTradeNo(notifyResult.getOid_paybill());
		bwPlatformRecord.setTradeAmount(Double.valueOf(notifyResult.getMoney_order()));// 交易金额
		bwPlatformRecord.setTradeType(1);// 1划拨2转账
		bwPlatformRecord.setOutAccount(card.getCardNo());
		bwPlatformRecord.setOutName(borrower.getName());
		bwPlatformRecord.setInAccount("上海水象金融信息服务有限公司-连连支付");
		bwPlatformRecord.setInName("上海水象金融信息服务有限公司-连连支付");
		bwPlatformRecord.setOrderId(order.getId());
		bwPlatformRecord.setTradeTime(new Date());
		bwPlatformRecord.setTradeRemark("连连还款扣款");
		bwPlatformRecord.setTradeChannel(3);// 连连支付
		int platFormInt = bwPlatformRecordService.saveBwPlatFormRecord(bwPlatformRecord);
		return platFormInt;
	}

	private BwOrder findBwOrderByIdProxy(Long orderId) {
		// logger.info("开始查询工单,orderId=" + orderId);
		BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
		// logger.info("结束查询工单,bwOrder=" + JSONObject.toJSONString(bwOrder));
		return bwOrder;
	}

	private NotifyResult getNotifyResult(HttpServletRequest request) throws Exception {
		NotifyResult notifyResult = null;

		if (CommUtils.isNull(request)) {
			return null;
		}

		request.setCharacterEncoding("utf-8");

		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
		StringBuilder sbuff = new StringBuilder();
		String tmp = "";
		while ((tmp = br.readLine()) != null) {
			sbuff.append(tmp);
		}

		br.close();

		String data = sbuff.toString();

		if (StringUtils.isBlank(data)) {
			return null;
		}

		notifyResult = JSONObject.parseObject(data, NotifyResult.class);

		return notifyResult;
	}

	// /**
	// * 获取连连签约
	// *
	// * @param signLess
	// * @return
	// */
	// @SuppressWarnings("unused")
	// public String signAccreditPay(SignLess signLess) {
	// SignResponse signResponse = new SignResponse();
	// SignRequest signRequest = new SignRequest();
	// try {
	// signRequest.setAcct_name(signLess.getAcct_name());
	// signRequest.setApp_request(signLess.getApp_request());
	// signRequest.setCard_no(signLess.getCard_no());
	// signRequest.setId_no(signLess.getId_no());
	// signRequest.setUser_id(signLess.getUser_id());
	// signRequest.setUrl_return(signLess.getUrl_return());
	//
	// // 一起的三个参数，全部不要只是签约 不绑定还款计划
	// /*
	// * signRequest.setRepayment_no(request.getParameter("repayment_no"))
	// * ; signRequest.setRepayment_plan(request.getParameter(
	// * "repayment_plan"));
	// * signRequest.setSms_param(request.getParameter("sms_param"));
	// */
	//
	// // 初始化为空的参数
	// if (CommUtils.isNull(signRequest.getVersion()) |
	// "null".equals(signRequest.getVersion())) {
	// signRequest.setVersion("1.1");
	// }
	// if (CommUtils.isNull(signRequest.getId_type()) |
	// "null".equals(signRequest.getId_type())) {
	// signRequest.setId_type("0");
	// }
	// if (CommUtils.isNull(signRequest.getApp_request()) |
	// "null".equals(signRequest.getApp_request())) {
	// signRequest.setApp_request("3");
	// }
	// if (CommUtils.isNull(signRequest.getOid_partner()) |
	// "null".equals(signRequest.getOid_partner())) {
	// signRequest.setOid_partner("201608101001022519");
	// }
	// if (CommUtils.isNull(signRequest.getPay_type()) |
	// "null".equals(signRequest.getPay_type())) {
	// signRequest.setPay_type("I");
	// }
	// if (CommUtils.isNull(signRequest.getSign_type()) |
	// "null".equals(signRequest.getSign_type())) {
	// signRequest.setSign_type("RSA");
	// }
	//
	// // 签名
	// Map<String, String> map = new HashMap<>();
	// map.put("acct_name", signRequest.getAcct_name());
	// map.put("app_request", signRequest.getApp_request());
	// map.put("card_no", signRequest.getCard_no());
	// map.put("id_no", signRequest.getId_no());
	// map.put("id_type", signRequest.getId_type());
	// map.put("oid_partner", signRequest.getOid_partner());
	// map.put("pay_type", signRequest.getPay_type());
	// map.put("risk_item", signRequest.getRisk_item());
	// map.put("sign_type", signRequest.getSign_type());
	// map.put("url_return", signRequest.getUrl_return());
	// map.put("user_id", signRequest.getUser_id());
	// map.put("version", signRequest.getVersion());
	// final String TRADER_PRI_KEY =
	// "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKZGXpmfgya2gGh6UdFPqPqi6e2z/HX4aIlMH394FOXTVwErnSGY5S0YFw5WskJrQLU2RHwFiA5P9Yt8VPxwgLDpdIm1/a7NlyjvWNvBd8V7wyITH8teJA1Ae5yWmRRrWFcVRSjpBq3xfwv76lVl+Nq/jR08p/ugVYJgtYEIM53JAgMBAAECgYA17SarPj+j45a7y8gTUXmlaAbkb/ZWMG1+8fBZQBHPA/74wzNf/R1+xYxcuyNvRSekXehSLN0WfzpMtdM+WCJ0ODqHRFsbAxmi784hzBZHOAxoJV49P8PVy6HIPthXxiSNUcacSt/HKJrUI6zACpymJLiVxMb9GqQAyx3BJl7rjQJBANG+RDszZYl3J1z1AtD0WggycrH2YOB7v5o3qKOz2AQ6CHWApSN6cuvqFwaUtHK9gMpDhvWR6zbYVRP+f4AxoQ8CQQDK8fTkpHNrHc011E8jjk3Uq5PWTJ0jAvcqk4rqZa4eV9953YSJYtJ2Fk2JnL3Ba7AU+qStnyD6MvSIpwIPSaOnAkEAptbFaZ4Jn55jdmMC2Xn1f925NGx6RTbKg37Qq18sbrhG8Ejjk2QctCIiLL7vBvJM1xd97CslQhw1GNFxVGSl6wJAQzwFtfoFgudMpRjBXzY18s8lG0omhQLmf+SBkUY+eS8Diowo7Jsgvp6E8aJL+1iB7XFcPWkKs9lNyjgKJqZu4QJAM22ULfWKrNIqaBJaYDmQSupUkHR/WL5rQJtAuVo8Zg3+rBrtMTXfIHJpR0MNpMgRSsPK6pZ3n4i+VvC5WxKUzA==";
	// String osign = RSAUtil.sortParams(map);
	// logger.info("排序参数：" + osign);
	// String sign = RSAUtil.sign(TRADER_PRI_KEY, osign);
	// logger.info("RSA加密后的密文：" + sign);
	// map.put("sign", sign);
	// String value = new Gson().toJson(map);
	// String html = "<html><head><meta charset='utf-8'></head><div
	// style='display:none;'><form id='signForm' name='signForm' type='post' "
	// + "action='https://wap.lianlianpay.com/signApply.htm'><input
	// name='req_data' value='" + value
	// + "'></form>"
	// + "</div><script
	// type='text/javascript'>document.forms['signForm'].submit();<l/script></html>";
	//
	// return html;
	// } catch (Exception e) {
	// logger.error(e, e);
	// e.printStackTrace();
	// }
	// return null;
	// }

	// 新增或更新银行卡信息
	private BwBankCard saveOrUpdateBBC(BwBorrower borrower, BankCardInfo bankcardInfo, String bankCode,
			String bankName) {
		BwBankCard bbc = new BwBankCard();
		// 查询银行卡信息
		bbc.setBorrowerId(borrower.getId());
		bbc = findBwBankCardByAttrProxy(bbc);
		// 如果不存在就新增，如果存在就修改
		if (CommUtils.isNull(bbc)) {
			logger.info("银行卡信息不存在，开始新增");
			bbc = new BwBankCard();
			bbc.setBorrowerId(borrower.getId());
			bbc.setCardNo(bankcardInfo.getBankCardNum());
			bbc.setBankCode(bankCode);
			bbc.setBankName(bankName);
			bbc.setPhone(bankcardInfo.getPhone());
			bbc.setSignStatus(0);
			bbc.setCreateTime(Calendar.getInstance().getTime());
			bbc.setUpdateTime(Calendar.getInstance().getTime());
			bwBankCardService.saveBwBankCard(bbc, borrower.getId());
			logger.info("添加完毕");
		} else {
			logger.info("银行卡信息已存在，开始修改");
			bbc.setBorrowerId(borrower.getId());
			bbc.setCardNo(bankcardInfo.getBankCardNum());
			bbc.setBankCode(bankCode);
			bbc.setBankName(bankName);
			bbc.setPhone(bankcardInfo.getPhone());
			bbc.setSignStatus(0);
			bbc.setUpdateTime(Calendar.getInstance().getTime());
			bwBankCardService.update(bbc);
			logger.info("更新完毕");
		}

		return bbc;

	}

	private BwOrderRong findBwOrderRongByAttrProxy(BwOrderRong bwOrderRong) {
		// logger.info("开始查询贷款钱包工单,bwOrderRong=" +
		// JSONObject.toJSONString(bwOrderRong));
		bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
		// logger.info("结束查询贷款钱包工单,bwOrderRong=" +
		// JSONObject.toJSONString(bwOrderRong));
		return bwOrderRong;
	}

	// /**
	// * 发送短信
	// *
	// * @param password
	// * @param phone
	// */
	// private void sendPwdMsg(String password, String phone) {
	// // 发送短信
	// try {
	// String message = LoanWalletUtils.getMsg(password);
	// MessageDto messageDto = new MessageDto();
	// messageDto.setBusinessScenario("1");
	// messageDto.setPhone(phone);
	// messageDto.setMsg(message);
	// messageDto.setType("1");
	// RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
	// } catch (Exception e) {
	// logger.error("发送短信异常:", e);
	// }
	// }

	/**
	 * 查询银行卡信息
	 * 
	 * @param bwBankCard
	 * @return
	 */
	private BwBankCard findBwBankCardByAttrProxy(BwBankCard bwBankCard) {
		bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
		logger.info("银行卡信息查询结果：bwBankCard=" + JSONObject.toJSONString(bwBankCard));
		return bwBankCard;
	}

	/**
	 * 查找借款人
	 * 
	 * @param borrower
	 * @return
	 */
	private BwBorrower findBwBorrowerByAttrProxy(BwBorrower borrower) {
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		return borrower;
	}

	private BwOrderRong getByOrderNoProxy(String orderNo) {
		BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
		return bwOrderRong;
	}

	private BwBankCard findBwBankCardByBorrowerIdProxy(Long borrowerId) {
		BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBorrowerId(borrowerId);
		return bwBankCard;
	}

	private BwRepaymentPlan findBwRepaymentPlanByAttrProxy(BwRepaymentPlan bwRepaymentPlan) {
		bwRepaymentPlan = bwRepaymentPlanService.findBwRepaymentPlanByAttr(bwRepaymentPlan);
		return bwRepaymentPlan;
	}

	private BwOverdueRecord findBwOverdueRecordByAttrProxy(BwOverdueRecord overdueRecord) {
		overdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(overdueRecord);
		return overdueRecord;
	}

	private BwBorrower findBwBorrowerByIdProxy(Long borrowerId) {
		BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
		return bwBorrower;
	}
}
