package com.waterelephant.rong360.controller;   // code0002

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.beadwallet.entity.lianlian.SignalLess;
import com.beadwallet.servcie.LianLianPayService;
import com.beadwallet.service.rong360.entity.request.OrderFeedBackReq;
import com.beadwallet.service.rong360.entity.request.RepayFeedBackReq;
import com.beadwallet.service.rong360.entity.request.RepaymentFeedBackReq;
import com.beadwallet.service.rong360.entity.response.OrderFeedBackResp;
import com.beadwallet.service.rong360.entity.response.RepayFeedBackResp;
import com.beadwallet.service.rong360.entity.response.RepaymentFeedBackResp;
import com.beadwallet.service.rong360.service.BeadWalletRong360Service;
import com.beadwallet.service.sms.dto.MessageDto;
import com.beadwallet.utils.RSAUtil;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBlacklist;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderProcessRecord;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwPlatformRecord;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.rong360.entity.OrderInfo;
import com.waterelephant.rong360.entity.ReLoanApplyDetail;
import com.waterelephant.rong360.entity.ReLoanOrderInfo;
import com.waterelephant.rong360.entity.ReLoanResp;
import com.waterelephant.rong360.entity.Rong360Req;
import com.waterelephant.rong360.entity.Rong360Resp;
import com.waterelephant.rong360.entity.RongAddInfoBizData;
import com.waterelephant.rong360.entity.RongApproveConfirmData;
import com.waterelephant.rong360.entity.RongApproveStatusData;
import com.waterelephant.rong360.entity.RongBindCardBizData;
import com.waterelephant.rong360.entity.RongConfirmBankCardData;
import com.waterelephant.rong360.entity.RongContractBizData;
import com.waterelephant.rong360.entity.RongDetailBizData;
import com.waterelephant.rong360.entity.RongExtendData;
import com.waterelephant.rong360.entity.RongExtendInfoData;
import com.waterelephant.rong360.entity.RongOrderStatusData;
import com.waterelephant.rong360.entity.RongReLoanData;
import com.waterelephant.rong360.entity.RongReLoanPushData;
import com.waterelephant.rong360.entity.RongRepayInfoData;
import com.waterelephant.rong360.entity.RongRepaymentBizData;
import com.waterelephant.rong360.entity.RongRepaymentPlanData;
import com.waterelephant.rong360.util.LogUtil;
import com.waterelephant.rong360.util.Rong360Util;
import com.waterelephant.rong360.util.ThreadLocalUtil;
import com.waterelephant.service.BwBlacklistService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwPlatformRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.IBwRepaymentService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DoubleUtil;
import com.waterelephant.utils.GenerateSerialNumber;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

import tk.mybatis.mapper.entity.Example;

/**
 * 融360对外接口
 * 
 * @author LIYUN
 */
@Deprecated
@Controller
@RequestMapping("/app/rong360")
public class AppRong360Controller {
	private static LogUtil logger = new LogUtil(AppRong360Controller.class);

	@Autowired
	private BwOrderRongService bwOrderRongService;

	@Autowired
	private IBwBorrowerService bwBorrowerService;

	@Autowired
	private IBwBankCardService bwBankCardService;

	@Autowired
	private BwRejectRecordService bwRejectRecordService;

	@Autowired
	private IBwAdjunctService bwAdjunctService;

	@Autowired
	private IBwOrderService bwOrderService;

	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlanService;

	@Autowired
	private BwPlatformRecordService bwPlatformRecordService;

	@Autowired
	private IBwRepaymentService bwRepaymentService;

	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;

	@Autowired
	private BwBlacklistService bwBlacklistService;

	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;
	
	@Autowired
	private BwOrderProcessRecordService bwOrderProcessRecordService;
	
	private static String APPROVE_STATUS_PASS = "10";
	private static String APPROVE_STATUS_REFUSED = "40";

	private static String ADD_INFO_REDIS = "rong360:addInfo:list";
	private static String BASE_INFO_REDIS = "rong360:orderPush";

	private static String RONG_360_XUDAI = "xudai:order_id";

	private static Map<String, String> NOTIFY_MAP = new HashMap<String, String>();
	private static Map<String, String> DEFER_RATE_MAP = new HashMap<String, String>();
	
	private static String ORDER_STATUS_REDIS = "rong360:orderStatus:";

	static {
		NOTIFY_MAP.put("repay", "/app/rong360/repaymentNotify.do");
		NOTIFY_MAP.put("defer", "/app/rong360/deferNotify.do");

		DEFER_RATE_MAP.put("1", "0.27");
		DEFER_RATE_MAP.put("2", "0.30");
		DEFER_RATE_MAP.put("3", "0.33");
	}

	/**
	 * 推工单基本信息
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/orderPush.do")
	public Rong360Resp orderPush(@RequestBody Rong360Req req) {
		ThreadLocalUtil.set("BASEINFO");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "AppRong360Controller.orderPush";
		logger.info(methodName + " start");
		// logger.info(req.getBiz_data());
		Rong360Resp resp = new Rong360Resp();

		try {
			// 校验请求参数
			String check = Rong360Util.checkOrderPush(req);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			String biz_data = req.getBiz_data();
			RongDetailBizData rongDetailBizData = JSONObject.parseObject(biz_data, RongDetailBizData.class);

			if (rongDetailBizData == null) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "rongOrderDetail is null", resp);
				return resp;
			}

			OrderInfo orderInfo = rongDetailBizData.getOrderInfo();

			if (orderInfo == null) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "orderInfo is null", resp);
				return resp;
			}

			String orderNo = orderInfo.getOrder_No();
			ThreadLocalUtil.set("BASEINFO-" + orderNo);

			logger.info("参数校验通过");

			logger.info("融360推单的订单号为：" + orderNo);

			// 放入REDIS
			logger.info("准备放入redis-list[" + BASE_INFO_REDIS + "]");
			Long resultNum = RedisUtils.rpush(BASE_INFO_REDIS, biz_data);
			if (!CommUtils.isNull(resultNum)) {
				logger.info("订单基本信息存入redis-list[" + BASE_INFO_REDIS + "]成功");
				resp.setCode("200");
				resp.setMsg("成功");
			} else {
				logger.info("订单基本信息存入redis-list[" + BASE_INFO_REDIS + "]失败");
				resp.setCode("103");
				resp.setMsg("系统异常，请稍后再试");
			}
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("102");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 推工单补充信息
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/orderAddInfoPush.do")
	public Rong360Resp orderAddInfoPush(@RequestBody Rong360Req req) {
		ThreadLocalUtil.set("ADDINFO");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "AppRong360Controller.orderAddInfoPush";
		logger.info(methodName + " start");
		// logger.info(req.getBiz_data());
		Rong360Resp resp = new Rong360Resp();

		try {
			// 校验请求参数
			String check = Rong360Util.checkOrderAddInfo(req);
 
			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			String bizData = req.getBiz_data();

			RongAddInfoBizData orderAddInfo = JSONObject.parseObject(bizData, RongAddInfoBizData.class);

			if (orderAddInfo == null) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "orderAddInfo is null", resp);
				return resp;
			}
			ThreadLocalUtil.set("ADDINFO-" + orderAddInfo.getOrder_no());
			logger.info("参数校验通过");
			logger.info("准备放入redis-list[" + ADD_INFO_REDIS + "]");
			Long resultNum = RedisUtils.rpush(ADD_INFO_REDIS, bizData);

			if (!CommUtils.isNull(resultNum)) {
				logger.info("订单补充信息存入redis-list[" + ADD_INFO_REDIS + "]成功");
				resp.setCode("200");
				resp.setMsg("成功");
			} else {
				logger.info("订单补充信息存入redis-list[" + ADD_INFO_REDIS + "]失败");
				resp.setCode("103");
				resp.setMsg("系统异常，请稍后再试");
			}
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("102");
			resp.setMsg("系统异常，请稍后再试");
		}
		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 获取合同地址
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getContract.do")
	public Rong360Resp getContract(@RequestBody Rong360Req req) {
		ThreadLocalUtil.set("CONTRACT");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Rong360Resp resp = new Rong360Resp();
		String methodName = "AppRong360Controller.getContract";
		logger.info(methodName + " start," + req);
		try {
			String check = Rong360Util.checkContract(req);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("102");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			RongContractBizData contractBizData = JSONObject.parseObject(req.getBiz_data(), RongContractBizData.class);
			if (CommUtils.isNull(contractBizData)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "contractBizData为空", resp);
				return resp;
			}
			String orderNo = contractBizData.getOrder_no();
			if (StringUtils.isBlank(orderNo)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "order_no参数为空", resp);
				return resp;
			}
			ThreadLocalUtil.set("CONTRACT-" + orderNo);
			logger.info("参数校验通过");
			// 根据订单号查询到工单号
			BwOrderRong bwOrderRong = getByOrderNoProxy(orderNo);

			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "融360工单为空", resp);
				return resp;
			}

			Long orderId = bwOrderRong.getOrderId();
			logger.info("工单号" + orderId);

			BwAdjunct bwAdjunct = new BwAdjunct();
			bwAdjunct.setOrderId(orderId);
			bwAdjunct.setAdjunctType(13);

			bwAdjunct = findBwAdjunctByAttrProxy(bwAdjunct);

			if (CommUtils.isNull(bwAdjunct)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "bwAdjunct为空", resp);
				return resp;
			}

			String conUrl = SystemConstant.PDF_URL + bwAdjunct.getAdjunctPath();
			logger.info("查询到的合同地址是：" + conUrl);
			Map<String, String> map = new HashMap<String, String>();
			map.put("contract_url", conUrl);
			resp.setCode("200");
			resp.setMsg("成功");
			resp.setData(map);
		} catch (Exception e) {
			logger.error("查询合同异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 展期详细
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deferInfo.do")
	public Rong360Resp deferInfo(@RequestBody Rong360Req req) {
		ThreadLocalUtil.set("DEFERINFO");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Rong360Resp resp = new Rong360Resp();
		String methodName = "AppRong360Controller.deferInfo";
		logger.info(methodName + " start," + req);
		try {
			String check = Rong360Util.checkDeferInfo(req);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("102");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			RongExtendInfoData extendInfoData = JSONObject.parseObject(req.getBiz_data(), RongExtendInfoData.class);
			if (CommUtils.isNull(extendInfoData)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "extendInfoData为空", resp);
				return resp;
			}

			String orderNo = extendInfoData.getOrder_no();
			if (StringUtils.isBlank(orderNo)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "order_no参数为空", resp);
				return resp;
			}

			ThreadLocalUtil.set("DEFERINFO-" + orderNo);
			logger.info("参数校验通过");

			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setThirdOrderNo(orderNo);
			bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);

			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "融360工单为空", resp);
				return resp;
			}

			Long orderId = bwOrderRong.getOrderId();
			BwOrder bwOrder = findBwOrderByIdProxy(orderId);

			if (CommUtils.isNull(bwOrder)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "工单为空", resp);
				return resp;
			}

			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
			bwRepaymentPlan.setOrderId(orderId);
			bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);

			if (CommUtils.isNull(bwRepaymentPlan)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "还款计划为空", resp);
				return resp;
			}

			Map<String, Object> data = new HashMap<String, Object>();

			// 获取下个还款日
			// 下个还款日
			String repayTime = "";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
			repayTime = formatter.format(bwRepaymentPlan.getRepayTime());
			logger.info("还款时间为：" + repayTime);
			Date repayDate = formatter.parse(repayTime);
			int day = MyDateUtils.getDaySpace(new Date(), repayDate);// 间隔时间
			logger.info("离还款时间相隔：" + day + "天");
			if (day > 10) {
				resp.setCode("202");
				resp.setMsg("到期时间十天内方可续贷");
				logger.info("到期时间十天内方可续贷");

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(repayDate);
				calendar.add(Calendar.DAY_OF_MONTH, -10);
				Long deferBeginTime = calendar.getTime().getTime() / 1000;
				data.put("defer_begin_time", deferBeginTime);
				resp.setData(data);
				methodEnd(stopWatch, methodName, "success", resp);
				return resp;
			}

			if (bwRepaymentPlan.getRolloverNumber() != null && bwRepaymentPlan.getRolloverNumber().intValue() >= 3) {
				resp.setCode("201");
				resp.setMsg("展期次数不可超过3次");
				data.put("defer_limit", "3");
				resp.setData(data);
				methodEnd(stopWatch, methodName, "success", resp);
				return resp;
			}

			BwOverdueRecord overdueRecord = new BwOverdueRecord();
			overdueRecord.setOrderId(orderId);
			overdueRecord = findBwOverdueRecordByAttrProxy(overdueRecord);

			Long beginTime = bwRepaymentPlan.getCreateTime().getTime() / 1000;
			Long dueTime = System.currentTimeMillis() / 1000;
			int rolloverNumber = bwRepaymentPlan.getRolloverNumber() == null ? 1
					: bwRepaymentPlan.getRolloverNumber().intValue() + 1;
			Double manageFee = getDeferFee(bwOrder, overdueRecord, "manage_fee", rolloverNumber);
			Double interest = getDeferFee(bwOrder, overdueRecord, "interest", rolloverNumber);
			Double overdueFee = getDeferFee(bwOrder, overdueRecord, "overdue_fee", rolloverNumber);
			Double otherFee = getDeferFee(bwOrder, overdueRecord, "other_fee", rolloverNumber);
			Double deferAmount = getDeferFee(bwOrder, overdueRecord, "defer_amount", rolloverNumber);

			Double afterDeferAmount = getDeferFee(bwOrder, overdueRecord, "after_defer_amount", rolloverNumber);
			Double afterManageFee = getDeferFee(bwOrder, overdueRecord, "after_manage_fee", rolloverNumber);
			Double afterInterest = getDeferFee(bwOrder, overdueRecord, "after_interest", rolloverNumber);
			Double afterOtherFee = getDeferFee(bwOrder, overdueRecord, "after_other_fee", rolloverNumber);

			Date d = MyDateUtils.addDays(bwRepaymentPlan.getRepayTime(), 7);
			Long defer_due_time = d.getTime() / 1000;

			Map<String, Object> deferOption = new HashMap<String, Object>();
			Map<String, Object> deferAmountOption = new HashMap<String, Object>();
			deferOption.put("defer_day", 7);
			deferOption.put("after_defer_amount", afterDeferAmount);
			deferOption.put("defer_due_time", defer_due_time);
			deferOption.put("manage_fee", afterManageFee);
			deferOption.put("interest", afterInterest);
			deferOption.put("other_fee", afterOtherFee);
			List<Map<String, Object>> deferOptionList = new ArrayList<Map<String, Object>>();

			List<Map<String, Object>> deferAmountOptionList = new ArrayList<Map<String, Object>>();
			deferAmountOptionList.add(deferAmountOption);
			deferOptionList.add(deferOption);
			deferAmountOption.put("manage_fee", manageFee);
			deferAmountOption.put("interest", interest);
			deferAmountOption.put("overdue_fee", overdueFee);
			deferAmountOption.put("other_fee", otherFee);
			deferAmountOption.put("begin_time", beginTime);
			deferAmountOption.put("due_time", dueTime);
			deferAmountOption.put("defer_amount", deferAmount);

			data.put("order_no", orderNo);
			data.put("defer_amount_option", deferAmountOptionList);
			data.put("defer_option", deferOptionList);
			data.put("defer_amount_type", "0");

			resp.setCode("200");
			resp.setMsg("成功");
			resp.setData(data);
		} catch (Exception e) {
			logger.error("查询展期详情异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 展期
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/defer.do")
	public Rong360Resp defer(@RequestBody Rong360Req req) {
		ThreadLocalUtil.set("DEFER");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Rong360Resp resp = new Rong360Resp();
		String methodName = "AppRong360Controller.defer";
		logger.info(methodName + " start," + req);
		try {
			String check = Rong360Util.checkDefer(req);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("102");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			RongExtendData extendData = JSONObject.parseObject(req.getBiz_data(), RongExtendData.class);
			if (CommUtils.isNull(extendData)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "extendInfoData为空", resp);
				return resp;
			}
			String orderNo = extendData.getOrder_no();
			if (StringUtils.isBlank(orderNo)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "order_no参数为空", resp);
				return resp;
			}
			ThreadLocalUtil.set("DEFER-" + orderNo);
			logger.info("参数校验通过");

			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setThirdOrderNo(orderNo);
			bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);

			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "融360工单为空", resp);
				return resp;
			}

			Long orderId = bwOrderRong.getOrderId();
			BwOrder bwOrder = findBwOrderByIdProxy(orderId);

			if (CommUtils.isNull(bwOrder)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "工单为空", resp);
				return resp;
			}

			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
			bwRepaymentPlan.setOrderId(orderId);
			bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);

			if (CommUtils.isNull(bwRepaymentPlan)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "还款计划为空", resp);
				return resp;
			}

			Long borrowerId = bwOrder.getBorrowerId();
			BwBankCard bwBankCard = findBwBankCardByBoorwerIdProxy(borrowerId);

			if (CommUtils.isNull(bwBankCard)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "银行卡信息为空", resp);
				return resp;
			}

			BwOverdueRecord overdueRecord = new BwOverdueRecord();
			overdueRecord.setOrderId(orderId);
			overdueRecord = findBwOverdueRecordByAttrProxy(overdueRecord);
			int rolloverNumber = bwRepaymentPlan.getRolloverNumber() == null ? 1
					: bwRepaymentPlan.getRolloverNumber().intValue() + 1;
			Double deferAmount = getDeferFee(bwOrder, overdueRecord, "defer_amount", rolloverNumber);
			resp = commonRepay(bwOrder, bwBankCard, String.valueOf(deferAmount), "defer");

		} catch (Exception e) {
			logger.error("展期异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	private Double getDeferFee(BwOrder bwOrder, BwOverdueRecord overdueRecord, String type, int rolloverNumber) {
		BigDecimal credit = new BigDecimal(bwOrder.getCreditLimit());
		BigDecimal manageFee = new BigDecimal(0);

		if ("manage_fee".equals(type)) {
			return manageFee.doubleValue();
		}

		BigDecimal interest = credit.multiply(new BigDecimal(DEFER_RATE_MAP.get(String.valueOf(rolloverNumber))));

		if ("interest".equals(type)) {
			return interest.doubleValue();
		}

		BigDecimal overdueFee = CommUtils.isNull(overdueRecord) ? new BigDecimal(0)
				: new BigDecimal(overdueRecord.getOverdueAccrualMoney());

		if ("overdue_fee".equals(type)) {
			return overdueFee.doubleValue();
		}

		BigDecimal otherFee = new BigDecimal(0);

		if ("other_fee".equals(type)) {
			return otherFee.doubleValue();
		}

		BigDecimal defeAmount = manageFee.add(interest).add(overdueFee).add(otherFee);

		if ("defer_amount".equals(type)) {
			return defeAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}

		BigDecimal afterManageFee = new BigDecimal(0);

		if ("after_manage_fee".equals(type)) {
			return afterManageFee.doubleValue();
		}

		BigDecimal afterInterest = new BigDecimal(0);

		if ("after_interest".equals(type)) {
			return afterInterest.doubleValue();
		}

		BigDecimal afterOtherFee = new BigDecimal(0);

		if ("after_other_fee".equals(type)) {
			return afterOtherFee.doubleValue();
		}

		BigDecimal afterDeferAmount = credit.add(afterInterest).add(afterManageFee).add(afterOtherFee);
		if ("after_defer_amount".equals(type)) {
			return afterDeferAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}

		return 0D;
	}

	/**
	 * 拉取订单状态
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pullOrderStatus.do")
	public Rong360Resp pullOrderStatus(@RequestBody Rong360Req req) {
		ThreadLocalUtil.set("PULLORDERSTATUS");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Rong360Resp resp = new Rong360Resp();
		String methodName = "AppRong360Controller.pullOrderStatus";
		logger.info(methodName + " start," + req);
		try {
			String check = Rong360Util.checkOrderStatus(req);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("102");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			RongOrderStatusData orderStatusData = JSONObject.parseObject(req.getBiz_data(), RongOrderStatusData.class);
			if (CommUtils.isNull(orderStatusData)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "orderStatusData为空", resp);
				return resp;
			}

			String orderNo = orderStatusData.getOrder_no();
			if (StringUtils.isBlank(orderNo)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "order_no参数为空", resp);
				return resp;
			}

			ThreadLocalUtil.set("PULLORDERSTATUS-" + orderNo);
			logger.info("参数校验通过");
			
			String orderStatus = null;
			logger.info("判断是否有缓存["+ORDER_STATUS_REDIS+orderNo+"]");
			if (RedisUtils.exists(ORDER_STATUS_REDIS+orderNo)) {
				logger.info("缓存["+ORDER_STATUS_REDIS+orderNo+"]已存在");
				orderStatus = RedisUtils.get(ORDER_STATUS_REDIS+orderNo);
				if (StringUtils.isBlank(orderStatus)) {
					resp.setCode("102");
					resp.setMsg("系统异常");
					methodEnd(stopWatch, methodName, "当前状态无法映射为融360订单状态", resp);
					return resp;
				}
			}else {
				logger.info("缓存["+ORDER_STATUS_REDIS+orderNo+"]不存在,开始查询数据库");
				// 根据订单号查询到工单号
				BwOrderRong bwOrderRong = getByOrderNoProxy(orderNo);
				if (CommUtils.isNull(bwOrderRong)) {
					resp.setCode("102");
					resp.setMsg("系统异常");
					methodEnd(stopWatch, methodName, "融360工单为空", resp);
					return resp;
				}

				Long orderId = bwOrderRong.getOrderId();

				BwOrder bwOrder = findBwOrderByIdProxy(orderId);
				if (CommUtils.isNull(bwOrder)) {
					resp.setCode("102");
					resp.setMsg("系统异常");
					methodEnd(stopWatch, methodName, "工单为空", resp);
					return resp;
				}

				orderStatus = Rong360Util.convertOrderStatus(bwOrder.getStatusId());
				logger.info("映射为融360订单状态为：" + orderStatus);

				if (StringUtils.isBlank(orderStatus)) {
					resp.setCode("102");
					resp.setMsg("系统异常");
					methodEnd(stopWatch, methodName, "当前状态[" + bwOrder.getStatusId() + "]无法映射为融360订单状态", resp);
					return resp;
				}
				
				logger.info("开始存入缓存["+ORDER_STATUS_REDIS + orderNo+"]");
				RedisUtils.setex(ORDER_STATUS_REDIS + orderNo, orderStatus, 60 * 10);
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("order_no", orderNo);
			map.put("order_status", orderStatus);
			map.put("update_time", String.valueOf(System.currentTimeMillis() / 1000));
			resp.setCode("200");
			resp.setMsg("成功");
			resp.setData(map);
		} catch (Exception e) {
			logger.error("拉取订单状态异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 拉取审核结论
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pullApproveStatus.do")
	public Rong360Resp pullApproveStatus(@RequestBody Rong360Req req) {
		ThreadLocalUtil.set("PULLAPPROVESTATUS");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Rong360Resp resp = new Rong360Resp();
		String methodName = "AppRong360Controller.pullApproveStatus";
		logger.info(methodName + " start," + req);
		try {
			String check = Rong360Util.checkApproveStatus(req);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("102");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			RongApproveStatusData approveStatusData = JSONObject.parseObject(req.getBiz_data(),
					RongApproveStatusData.class);
			if (CommUtils.isNull(approveStatusData)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "approveStatusData为空", resp);
				return resp;
			}

			if (StringUtils.isBlank(approveStatusData.getOrder_no())) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "order_no参数为空", resp);
				return resp;
			}
			String orderNo = approveStatusData.getOrder_no();
			ThreadLocalUtil.set("PULLAPPROVESTATUS-" + orderNo);
			logger.info("参数校验通过");

			// 根据订单号查询到工单号
			BwOrderRong bwOrderRong = getByOrderNoProxy(orderNo);

			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "融360工单为空", resp);
				return resp;
			}

			Long orderId = bwOrderRong.getOrderId();
			BwOrder bwOrder = findBwOrderByIdProxy(orderId);

			if (CommUtils.isNull(bwOrder)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "工单为空", resp);
				return resp;
			}

			String approveStatus = Rong360Util.convertApproveStatus(bwOrder.getStatusId());
			logger.info("映射为融360审批状态为：" + approveStatus);

			if (StringUtils.isBlank(approveStatus)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "当前状态[" + bwOrder.getStatusId() + "]无法映射为融360审批状态", resp);
				return resp;
			}

			Map<String, String> map = new HashMap<String, String>();

			if (APPROVE_STATUS_PASS.equals(approveStatus)) {
				map.put("order_no", orderNo);
				map.put("conclusion", approveStatus);
				map.put("approval_amount", String.valueOf(bwOrder.getCreditLimit()));
				map.put("approval_term", "1");
				map.put("term_unit", "2");
				map.put("service_fee", String.valueOf(bwOrder.getCreditLimit() * Float.parseFloat("0.18")));
				map.put("extra_fee", "0");
				map.put("period_amount", String.valueOf(bwOrder.getCreditLimit()));
				map.put("fee_desc", "还款金额=本金+利息");
				map.put("remark", "审核通过");
				map.put("approval_time",
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
				map.put("amount_type", "0");
			} else if (APPROVE_STATUS_REFUSED.equals(approveStatus)) {
				map.put("order_no", orderNo);
				map.put("conclusion", approveStatus);
				map.put("remark", "信用评分过低#拒绝客户");
			}

			resp.setCode("200");
			resp.setMsg("成功");
			resp.setData(map);
		} catch (Exception e) {
			logger.error("拉取审批结论异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 拉取账单
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pullRepaymentPlan.do")
	public Rong360Resp pullRepaymentPlan(@RequestBody Rong360Req req) {
		ThreadLocalUtil.set("PULLREPAYMENTPLAN");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Rong360Resp resp = new Rong360Resp();
		String methodName = "AppRong360Controller.pullRepaymentPlan";
		logger.info(methodName + " start," + req);
		try {
			String check = Rong360Util.checkRepaymentPlan(req);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("102");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			RongRepaymentPlanData repaymentPlanData = JSONObject.parseObject(req.getBiz_data(),
					RongRepaymentPlanData.class);
			if (CommUtils.isNull(repaymentPlanData)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "repaymentPlanData为空", resp);
				return resp;
			}

			String orderNo = repaymentPlanData.getOrder_no();
			if (StringUtils.isBlank(orderNo)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "order_no参数为空", resp);
				return resp;
			}
			ThreadLocalUtil.set("PULLREPAYMENTPLAN-" + orderNo);
			logger.info("参数校验通过");
			// 根据订单号查询到工单号
			BwOrderRong bwOrderRong = getByOrderNoProxy(orderNo);

			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "融360工单为空", resp);
				return resp;
			}

			Long orderId = bwOrderRong.getOrderId();
			logger.info("工单号" + orderId);

			BwOrder bwOrder = findBwOrderByIdProxy(orderId);

			if (CommUtils.isNull(bwOrder)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "工单为空", resp);
				return resp;
			}

			BwBankCard bankCard = findBwBankCardByBorrowerIdProxy(bwOrder.getBorrowerId());

			if (CommUtils.isNull(bankCard)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "银行卡信息为空", resp);
				return resp;
			}
			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
			bwRepaymentPlan.setOrderId(bwOrder.getId());
			bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);
			if (CommUtils.isNull(bwRepaymentPlan)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "还款计划为空", resp);
				return resp;
			}

			String billStatus = Rong360Util.convertBillStatus(bwOrder.getStatusId());

			logger.info("映射融360的账单状态为:" + billStatus);

			if (StringUtils.isBlank(billStatus)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "当前状态[" + bwOrder.getStatusId() + "]无法映射为融360的账单状态", resp);
				return resp;
			}

			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, String> planMap = new HashMap<String, String>();
			BigDecimal allAmount = new BigDecimal(bwRepaymentPlan.getRealityRepayMoney());
			logger.info("金额:" + allAmount);

			if ("3".equals(billStatus)) {
				BwOverdueRecord bov = new BwOverdueRecord();
				bov.setRepayId(bwRepaymentPlan.getId()); // 根据还款计划id获取逾期记录
				bov = findBwOverdueRecordByAttrProxy(bov);
				if (!CommUtils.isNull(bov) && bov.getOverdueAccrualMoney() != null) {
					allAmount = allAmount.add(new BigDecimal(bov.getOverdueAccrualMoney()));
					planMap.put("overdue_fee", String.valueOf(bov.getOverdueAccrualMoney()));
				}
			}

			planMap.put("amount", allAmount.toString());
			String timestamp = String.valueOf(bwRepaymentPlan.getRepayTime().getTime() / 1000);
			planMap.put("due_time", String.valueOf(Integer.valueOf(timestamp)));
			planMap.put("pay_type", "5");
			planMap.put("period_no", "1");
			planMap.put("remark", "本金:" + bwOrder.getBorrowAmount());
			planMap.put("bill_status", billStatus);

			List<Map<String, String>> planMaps = new ArrayList<Map<String, String>>();
			planMaps.add(planMap);

			map.put("order_no", orderNo);
			map.put("open_bank", bankCard.getBankName());
			map.put("bank_card", bankCard.getCardNo());
			map.put("repayment_plan", planMaps);

			resp.setCode("200");
			resp.setMsg("成功");
			resp.setData(map);
		} catch (Exception e) {
			logger.error("拉取账单异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 绑卡
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bindCard.do")
	public Rong360Resp bindCard(@RequestBody Rong360Req req) {
		ThreadLocalUtil.set("BINDCARD");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "AppRong360Controller.bindCard";
		logger.info(methodName + " start,request=" + req);
		Rong360Resp resp = new Rong360Resp();

		try {
			// 校验请求参数
			String check = Rong360Util.checkBindCard(req);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			String bizData = req.getBiz_data();
			RongBindCardBizData rongBindCard = JSONObject.parseObject(bizData, RongBindCardBizData.class);

			if (rongBindCard == null) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "RongBindCardBizData is null", resp);
				return resp;
			}

			ThreadLocalUtil.set("BINDCARD-" + rongBindCard.getOrder_no());
			logger.info("参数校验通过");
			// 将contractReturnUrl保存至REDIS
			RedisUtils.hset("third:url", "rong360", rongBindCard.getContract_return_url());

			String bankCode = Rong360Util.convertToBankCode(rongBindCard.getOpen_bank());

			if (StringUtils.isBlank(bankCode) || "0000".equals(bankCode)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "该银行卡所在地区暂未开通服务", resp);
				return resp;
			}

			// 根据bankCode从REDIS中获取对应的bankName
			String bankName = RedisUtils.hget("fuiou:bank", bankCode);
			String phone = rongBindCard.getUser_mobile();
			String idNumber = rongBindCard.getId_number();
			String name = rongBindCard.getUser_name();

			// 获取借款人信息
			BwBorrower borrower = new BwBorrower();
			borrower.setIdCard(idNumber);
			borrower.setFlag(1);// 未删除的

			borrower = findBwBorrowerByAttrProxy(borrower);

			logger.info("借款人查询结果：" + JSONObject.toJSONString(borrower));

			if (CommUtils.isNull(borrower)) {
				
				logger.info("根据身份证没有查到借款人,开始根据手机号查询,phone="+phone);
				BwBorrower myBorrower = new BwBorrower();
				myBorrower.setPhone(phone);
				borrower = bwBorrowerService.findBwBorrowerByAttr(myBorrower);
				logger.info("根据手机号查询借款人结果:"+JSONObject.toJSONString(borrower));
				
				if (!CommUtils.isNull(borrower) && StringUtils.isBlank(borrower.getIdCard())) {
					logger.info("借款人手机号存在,身份证为空,开始修改借款人身份证");
					borrower.setPhone(phone);
					borrower.setAuthStep(1);
					borrower.setFlag(1);
					borrower.setState(1);
//					borrower.setChannel(11); // 表示该借款人来源于融360
					borrower.setIdCard(idNumber);
					borrower.setName(name);
					borrower.setAge(Rong360Util.getAgeByIdCard(idNumber));
					borrower.setSex(Rong360Util.getSexByIdCard(idNumber));
					borrower.setUpdateTime(Calendar.getInstance().getTime());
					bwBorrowerService.updateBwBorrower(borrower);
					logger.info("修改借款人身份证结束");
				}
				else {
				
				logger.info("借款人不存在，开始创建借款人...");
				String password = Rong360Util.getRandomPwd();
				// 创建借款人
				borrower = new BwBorrower();
				borrower.setPhone(phone);
				borrower.setPassword(CommUtils.getMD5(password.getBytes()));
				borrower.setCreateTime(Calendar.getInstance().getTime());
				borrower.setAuthStep(1);
				borrower.setFlag(1);
				borrower.setState(1);
				borrower.setChannel(11); // 表示该借款人来源于融360
				borrower.setIdCard(idNumber);
				borrower.setName(rongBindCard.getUser_name());
				borrower.setAge(Rong360Util.getAgeByIdCard(idNumber));
				borrower.setSex(Rong360Util.getSexByIdCard(idNumber));
				bwBorrowerService.addBwBorrower(borrower);
				logger.info("生成的借款人id：" + borrower.getId());

				// 发送初始密码短信
				sendPwdMsg(password, phone);
				}
			}

			// 查询银行卡信息
			BwBankCard bbc = saveOrUpdateBBC(borrower, rongBindCard, bankCode, bankName);

			Map<String, String> map = new HashMap<String, String>();

			String userId = String.valueOf(borrower.getId());// 借款人id
			String idNo = borrower.getIdCard();// 证件号码 18位
			String accName = borrower.getName();// 姓名
			String cardNo = bbc.getCardNo();// 银行卡号
			String orderNo = rongBindCard.getOrder_no();

			resp.setCode("200");
			resp.setMsg("成功");
			map.put("bind_card_url", SystemConstant.NOTIRY_URL + "/app/rong360/signRongCard.do?userId=" + userId
					+ "&idNo=" + idNo + "&accName=" + accName + "&cardNo=" + cardNo + "&orderNo=" + orderNo);
			resp.setData(map);
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("102");
			resp.setMsg("系统异常，请稍后再试");
		}
		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	private void sendPwdMsg(String password, String phone) {
		// 发送短信
		try {
			String message = Rong360Util.getMsg(password);
//			MsgReqData msg = new MsgReqData();
//			msg.setPhone(phone);
//			msg.setMsg(message);
//			msg.setType("0");
//			logger.info("开始发送密码短信,phone=" + phone);
//			Response<Object> response = BeadWalletSendMsgService.sendMsg(msg);
//			logger.info("发送完成,发送结果：" + JSONObject.toJSONString(response));
//			boolean bo = sendMessageCommonService.commonSendMessage(phone, message);
//			if (bo) {
//				logger.info("短信发送成功！");
//			}else {
//				logger.info("短信发送失败！");
//			}
			MessageDto messageDto = new MessageDto();
			messageDto.setBusinessScenario("1");
			messageDto.setPhone(phone);
			messageDto.setMsg(message);
			messageDto.setType("1");
			RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
		} catch (Exception e) {
			logger.error("发送短信异常:", e);
		}
	}

	/**
	 * 修改OR新增银行卡
	 * 
	 * @param borrower
	 * @param rongBindCard
	 * @param bankCode
	 * @param bankName
	 * @return
	 * @throws InterruptedException
	 */
	private BwBankCard saveOrUpdateBBC(BwBorrower borrower, RongBindCardBizData rongBindCard, String bankCode,
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
			bbc.setCardNo(rongBindCard.getBank_card());
			bbc.setBankCode(bankCode);
			bbc.setBankName(bankName);
			bbc.setPhone(rongBindCard.getUser_mobile());
			bbc.setSignStatus(0);
			bbc.setCreateTime(Calendar.getInstance().getTime());
			bwBankCardService.saveBwBankCard(bbc, borrower.getId());
		} else {
			logger.info("银行卡信息已存在，开始修改");

			bbc.setBorrowerId(borrower.getId());
			bbc.setCardNo(rongBindCard.getBank_card());
			bbc.setBankCode(bankCode);
			bbc.setBankName(bankName);
			bbc.setPhone(rongBindCard.getUser_mobile());
			bbc.setSignStatus(0);
			bbc.setUpdateTime(Calendar.getInstance().getTime());
			bwBankCardService.update(bbc);
		}

		return bbc;
	}

	/**
	 * 复贷&老用户过滤
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reLoan.do")
	public ReLoanResp reLoan(@RequestBody Rong360Req req) {
		ThreadLocalUtil.set("RELOAN");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "AppRong360Controller.reLoan";

		logger.info(methodName + " start,req=" + req);
		ReLoanResp resp = new ReLoanResp();

		try {
			// 校验请求参数
			String check = Rong360Util.checkReLoan(req);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			String bizData = req.getBiz_data();
			RongReLoanData reLoan = JSONObject.parseObject(bizData, RongReLoanData.class);

			if (CommUtils.isNull(reLoan)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "RongReLoanData is null", resp);
				return resp;
			}

			String idCard = reLoan.getId_card();
			String phone = reLoan.getUser_mobile();
			String name = reLoan.getUser_name();
			ThreadLocalUtil.set("RELOAN-" + name + "|" + idCard + "|" + phone);
			logger.info("参数校验通过");
			if (StringUtils.isBlank(idCard)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "idCard is null", resp);
				return resp;
			}

			if (StringUtils.isBlank(phone)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "phone is null", resp);
				return resp;
			}

			if (StringUtils.isBlank(name)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "name is null", resp);
				return resp;
			}

			Map<String, String> data = new HashMap<String, String>();

			// 1判断是否存在借款人信息，如果不存在则是新用户,返回200+is_reloan=0，否则查询绑卡信息
			logger.info("开始查询借款人信息,idCard:" + idCard + ",name=" + name);
			BwBorrower borrower = bwBorrowerService.oldUserFilter(idCard.substring(0, idCard.length() - 5), name);
			logger.info("查询结果:" + JSONObject.toJSONString(borrower));

			if (CommUtils.isNull(borrower)) {
				resp.setCode("200");
				resp.setMsg("新用户");
				data.put("is_reloan", "0");
				resp.setData(data);
				methodEnd(stopWatch, methodName, "新用户", resp);
				return resp;
			}

			logger.info("开始验证姓名");
			if (!name.equals(borrower.getName())) {
				resp.setCode("400");
				resp.setMsg("姓名不一致");
				resp.setReason("C003");
				methodEnd(stopWatch, methodName, "姓名不一致", resp);
				return resp;
			}

			logger.info("开始验证身份证");
			String phoneRong = phone.substring(0, phone.length() - 4);
			String myPhone = borrower.getPhone().substring(0, borrower.getPhone().length() - 4);
			if (!phoneRong.equals(myPhone)) {
				resp.setCode("400");
				resp.setMsg("手机号不一致");
				resp.setReason("C003");
				methodEnd(stopWatch, methodName, "手机号不一致", resp);
				return resp;
			}

			logger.info("开始验证身份证");
			String idCardRong = idCard.substring(0, idCard.length() - 5);
			String myIdCard = borrower.getIdCard().substring(0, idCard.length() - 5);
			if (!idCardRong.equals(myIdCard)) {
				resp.setCode("400");
				resp.setMsg("身份证不一致");
				resp.setReason("C003");
				methodEnd(stopWatch, methodName, "身份证不一致", resp);
				return resp;
			}

			logger.info("开始查询银行卡信息");
			// 2判断是否有签约成功，如果没有则是新用户,返回200+is_reloan=0，如果有则查询是否有黑名单，正在进行中的订单，被拒绝记录
			BwBankCard bankCard = new BwBankCard();
			bankCard.setBorrowerId(borrower.getId());
			bankCard.setSignStatus(2);
			bankCard = findBwBankCardByAttrProxy(bankCard);

			if (CommUtils.isNull(bankCard)) {
				logger.info("银行卡信息不存在");
				resp.setCode("200");
				resp.setMsg("新用户");
				data.put("is_reloan", "0");
				resp.setData(data);
				methodEnd(stopWatch, methodName, "新用户", resp);
				return resp;
			}
			// 3判断是否是黑名单，如果存在则返回400，如果没有则查询是否有进行中的订单
			logger.info("开始验证系统平台黑名单");
			Example example = new Example(BwBlacklist.class);
			String idNo = borrower.getIdCard();
			example.createCriteria().andEqualTo("sort", 1).andEqualTo("status", 1).andEqualTo("card",
					idNo.toUpperCase());
			List<BwBlacklist> desList = bwBlacklistService.findBwBlacklistByExample(example);
			if (!CommUtils.isNull(desList)) {
				resp.setCode("400");
				resp.setMsg("有不良贷款记录");
				resp.setReason("C002");
				methodEnd(stopWatch, methodName, "有不良贷款记录", resp);
				return resp;
			}

			// 4查询是否有进行中的订单，如果存在则返回400，否则查询是否有被拒记录
			logger.info("开始查询进行中的订单:borrowerId=" + borrower.getId());
			Long count = bwOrderService.findProOrder(String.valueOf(borrower.getId()));
			logger.info("结束查询进行中的订单:count:" + count);
			if (!CommUtils.isNull(count) && count.intValue() > 0) {
				resp.setCode("400");
				resp.setMsg("有进行中的贷款");
				resp.setReason("C001");
				methodEnd(stopWatch, methodName, "有进行中的贷款", resp);
				return resp;
			}

			// 5查询是否有被拒记录，如果有则判断被拒类型，如果是永久拒绝则返回400，如果是临时拒绝则判断是否到期
			logger.info("开始查询拒绝记录");
			BwRejectRecord record = bwRejectRecordService.findBwRejectRecordByBid(borrower.getId());
			logger.info("结束查询拒绝记录,rejectRecord=" + JSONObject.toJSONString(record));
			if (!CommUtils.isNull(record)) {
				// 永久拒绝
				if ("0".equals(String.valueOf(record.getRejectType()))) {
					logger.info("有永久拒绝记录");
					resp.setCode("400");
					resp.setMsg("该用户被永久拒绝");
					resp.setReason("C003");
					methodEnd(stopWatch, methodName, "该用户被永久拒绝", resp);
					return resp;
				} else {
					Date rejectDate = record.getCreateTime();
					long day = (Calendar.getInstance().getTime().getTime() - rejectDate.getTime())
							/ (24 * 60 * 60 * 1000);
					if (day <= 7) {																				//  黑名单时间改动 30到7天
						resp.setCode("400");
						resp.setMsg("7天内被机构审批拒绝过");
						resp.setReason("C003");
						methodEnd(stopWatch, methodName, "临时拒绝还未过期", resp);
						return resp;
					}
				}
			}
			// 6判断临时拒绝是否到期，如果没到期则返回400，否则返回200+is_reloan=1
			data.put("is_reloan", "0");
			data.put("approval_amount", "5000");
			data.put("approval_term", "1");
			data.put("term_unit", "2");
			data.put("bank_card", bankCard.getCardNo());
			data.put("open_bank", bankCard.getBankName());
			data.put("user_name", borrower.getName());
			data.put("user_mobile", borrower.getPhone());

			resp.setCode("200");
			resp.setMsg("复贷用户");
			resp.setData(data);
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 还款详情
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/repayInfo.do")
	public Rong360Resp repayInfo(@RequestBody Rong360Req req) {
		ThreadLocalUtil.set("REPAYINFO");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "AppRong360Controller.repayInfo";

		logger.info(methodName + " start,req=" + req);
		Rong360Resp resp = new Rong360Resp();
		Map<String, String> data = new HashMap<String, String>();
		resp.setData(data);
		try {
			// 校验请求参数
			String check = Rong360Util.checkRepayInfo(req);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				data.put("reason", "参数校验失败");
				return resp;
			}

			String bizData = req.getBiz_data();
			RongRepayInfoData repayInfo = JSONObject.parseObject(bizData, RongRepayInfoData.class);

			if (CommUtils.isNull(repayInfo)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "RongRepayInfoData is null", resp);
				data.put("reason", "参数为空");
				return resp;
			}

			String orderNo = repayInfo.getOrder_no();

			if (CommUtils.isNull(orderNo)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "Order_no is null", resp);
				data.put("reason", "订单号为空");
				return resp;
			}

			ThreadLocalUtil.set("REPAYINFO-" + orderNo);

			String periodNos = repayInfo.getPeriod_nos();
			if (CommUtils.isNull(periodNos)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "Period_nos is null", resp);
				data.put("reason", "还款期数为空");
				return resp;
			}

			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setThirdOrderNo(orderNo);
			bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);
			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "bwOrderRong is null", resp);
				data.put("reason", "融360订单为空");
				return resp;
			}

			BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
			bwRepaymentPlan.setOrderId(bwOrderRong.getOrderId());
			bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);
			if (CommUtils.isNull(bwRepaymentPlan)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "bwRepaymentPlan is null", resp);
				data.put("reason", "还款为空");
				return resp;
			}

			data.put("period_nos", "1");
			data.put("amount", String.valueOf(bwRepaymentPlan.getRealityRepayMoney()));

			BwOverdueRecord overdueRecord = new BwOverdueRecord();
			overdueRecord.setOrderId(bwOrderRong.getOrderId());
			overdueRecord = findBwOverdueRecordByAttrProxy(overdueRecord);

			if (!CommUtils.isNull(overdueRecord)) {
				data.put("overdue_amount", String.valueOf(overdueRecord.getOverdueAccrualMoney()));
			}

			resp.setCode("200");
			resp.setMsg("成功");
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
			data.put("reason", "系统异常");
		}

		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 复贷&老用户过滤
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reLoanPush.do")
	public Rong360Resp reLoanPush(@RequestBody Rong360Req req) {
		ThreadLocalUtil.set("RELOANPUSH");
		Rong360Resp resp = new Rong360Resp();
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "AppRong360Controller.reLoanPush";

		try {
			logger.info(methodName + " start,req=" + req);

			// 校验请求参数
			String check = Rong360Util.checkReLoanPush(req);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			String bizData = req.getBiz_data();
			RongReLoanPushData reLoan = JSONObject.parseObject(bizData, RongReLoanPushData.class);

			if (CommUtils.isNull(reLoan)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "RongReLoanPushData is null", resp);
				return resp;
			}

			if (!"1".equals(reLoan.getIs_reloan())) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "Is_reloan is not 1", resp);
				return resp;
			}

			ReLoanOrderInfo orderInfo = reLoan.getOrderInfo();
			ReLoanApplyDetail applyDetail = reLoan.getApplyDetail();

			if (CommUtils.isNull(orderInfo)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "OrderInfo is null", resp);
				return resp;
			}

			if (CommUtils.isNull(applyDetail)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "ApplyDetail is null", resp);
				return resp;
			}

			ThreadLocalUtil.set("RELOANPUSH-" + orderInfo.getOrder_No());
			logger.info("参数校验通过");

			resp.setCode("200");
			resp.setMsg("成功");
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}
		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 复贷&老用户过滤
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/confirmBankCard.do")
	public Rong360Resp confirmBankCard(@RequestBody Rong360Req req) {
		ThreadLocalUtil.set("CONFIRMBC");
		Rong360Resp resp = new Rong360Resp();
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "AppRong360Controller.confirmBankCard";
		try {
			logger.info(methodName + " start,req=" + req);

			// 校验请求参数
			String check = Rong360Util.checkConfirmBankCard(req);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			String bizData = req.getBiz_data();
			RongConfirmBankCardData confirmBankCardData = JSONObject.parseObject(bizData,
					RongConfirmBankCardData.class);

			if (CommUtils.isNull(confirmBankCardData)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "RongConfirmBankCardData is null", resp);
				return resp;
			}

			String orderNo = confirmBankCardData.getOrder_no();
			String bankCard = confirmBankCardData.getBank_card();

			if (CommUtils.isNull(orderNo)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "orderNo is null", resp);
				return resp;
			}

			if (CommUtils.isNull(bankCard)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "bankCard is null", resp);
				return resp;
			}

			ThreadLocalUtil.set("CONFIRMBC-" + orderNo);

			resp.setCode("200");
			resp.setMsg("成功");
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 审批确认
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/approveConfirm.do")
	public Rong360Resp approveConfirm(@RequestBody Rong360Req req) {
		ThreadLocalUtil.set("APPROVECONFIRM");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		String methodName = "AppRong360Controller.approveConfirm";

		logger.info(methodName + " start,req=" + req);
		Rong360Resp resp = new Rong360Resp();

		try {
			// 校验请求参数
			String check = Rong360Util.checkApproveConfirm(req);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("101");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			String bizData = req.getBiz_data();
			RongApproveConfirmData approveConfirm = JSONObject.parseObject(bizData, RongApproveConfirmData.class);

			if (CommUtils.isNull(approveConfirm)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "RongApproveConfirmData is null", resp);
				return resp;
			}
			String thirdOrderNo = approveConfirm.getOrder_no();
			ThreadLocalUtil.set("APPROVECONFIRM-" + thirdOrderNo);
			logger.info("参数校验通过");
			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setThirdOrderNo(thirdOrderNo);
			bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);

			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "融360工单不存在", resp);
				return resp;
			}

			Long orderId = bwOrderRong.getOrderId();
			String orderTerm = approveConfirm.getLoan_term();

			// 获取利率字典表信息
			BwProductDictionary bwProductDictionary = bwProductDictionaryService.findById(2l);// 固定查询产品id为2的合同利率
			Double contractMonthRate = 0.0;
			Double repayAmount = 0.0;
			Double contractAmount = 0.0;

			// 等额本息
			// 计算合同月利率
			contractMonthRate = bwProductDictionary.getpBorrowRateMonth();
			// 计算合同金额
			BwOrder order = findBwOrderByIdProxy(orderId);

			if (CommUtils.isNull(order)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "工单不存在", resp);
				return resp;
			}

			if (CommUtils.isNull(order.getBorrowAmount())) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "借款金额为空", resp);
				return resp;
			}

			// 计算还款金额
			repayAmount = DoubleUtil.round(((order.getBorrowAmount() / Integer.parseInt(orderTerm))
					+ (order.getBorrowAmount() * bwProductDictionary.getpInvestRateMonth())), 2);
			// 计算合同金额
			contractAmount = DoubleUtil
					.round((repayAmount * (Math.pow(1 + contractMonthRate, Integer.parseInt(orderTerm)) - 1))
							/ (contractMonthRate * (Math.pow(1 + contractMonthRate, Integer.parseInt(orderTerm)))), 2);
			BwOrder bo = new BwOrder();
			bo.setId(orderId);
			bo = bwOrderService.findBwOrderByAttr(bo);

			bo.setRepayTerm(Integer.parseInt(orderTerm));
			bo.setRepayType(Integer.parseInt("1"));// 待确认
			bo.setBorrowRate(bwProductDictionary.getpInvestRateMonth());
			bo.setContractRate(bwProductDictionary.getpInvesstRateYear());
			bo.setContractMonthRate(contractMonthRate);
			// 将产品类型更新成2
			bo.setStatusId(11L);
			bo.setContractAmount(contractAmount);
			// 工单修改时间
			bo.setUpdateTime(new Date());

			if (order.getStatusId() == 4) {// 签约
				int num = bwOrderService.updateBwOrder(bo);
				logger.info("修改工单条数:" + num);
				if (num == 0) {
					resp.setCode("1004");
					resp.setMsg("修改工单失败");
					methodEnd(stopWatch, methodName, "修改工单失败", resp);
					return resp;
				}
				
				// 生成合同
				try {
					RedisUtils.rpush("system:contract", String.valueOf(orderId));
					
					BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
					bwOrderProcessRecord.setOrderId(order.getId());
					bwOrderProcessRecord.setSignTime(new Date());
					bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
				} catch (Exception e) {
					logger.error("occured exception:", e);
				}
			}

			resp.setCode("200");
			resp.setMsg("成功");
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "success", resp);
		return resp;
	}

	/**
	 * 主动还款接口
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/repayment.do")
	public Rong360Resp repayment(@RequestBody Rong360Req req) {
		ThreadLocalUtil.set("REPAYMENT");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Rong360Resp resp = new Rong360Resp();
		String methodName = "AppRong360Controller.repayment";
		logger.info(methodName + " start," + req);

		try {
			String check = Rong360Util.checkRepayment(req);

			if (StringUtils.isNotBlank(check)) {
				resp.setCode("102");
				resp.setMsg(check);
				methodEnd(stopWatch, methodName, check, resp);
				return resp;
			}

			RongRepaymentBizData repaymentBizData = JSONObject.parseObject(req.getBiz_data(),
					RongRepaymentBizData.class);

			if (CommUtils.isNull(repaymentBizData)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "repaymentBizData is null", resp);
				return resp;
			}
			ThreadLocalUtil.set("REPAYMENT-" + repaymentBizData.getOrder_no());
			logger.info("参数校验通过");
			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setThirdOrderNo(repaymentBizData.getOrder_no());
			bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);

			if (CommUtils.isNull(bwOrderRong)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "融360工单为空", resp);
				return resp;
			}

			BwOrder bwOrder = findBwOrderByIdProxy(bwOrderRong.getOrderId());

			if (CommUtils.isNull(bwOrder)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "工单为空", resp);
				return resp;
			}

			Long borrowerId = bwOrder.getBorrowerId();
			BwBankCard bwBankCard = findBwBankCardByBoorwerIdProxy(borrowerId);

			if (CommUtils.isNull(bwBankCard)) {
				resp.setCode("102");
				resp.setMsg("系统异常");
				methodEnd(stopWatch, methodName, "银行卡信息为空", resp);
				return resp;
			}

			resp = commonRepay(bwOrder, bwBankCard, "repay");
		} catch (Exception e) {
			logger.error(methodName + " 异常", e);
			resp.setCode("103");
			resp.setMsg("系统异常，请稍后再试");
		}

		methodEnd(stopWatch, methodName, "", resp);
		return resp;
	}

	private Rong360Resp commonRepay(BwOrder bwOrder, BwBankCard bwBankCard, String type) throws Exception {
		return commonRepay(bwOrder, bwBankCard, null, type);
	}

	private Rong360Resp commonRepay(BwOrder bwOrder, BwBankCard bwBankCard, String amount, String type)
			throws Exception {
		Rong360Resp resp = new Rong360Resp();

		String orderId = String.valueOf(bwOrder.getId());

		if (RedisUtils.hexists(RONG_360_XUDAI, orderId)) {
			resp.setCode("102");
			resp.setMsg("系统异常");
			logger.info("此工单正在续贷中");
			return resp;
		}

		if (RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId)) {
			resp.setCode("102");
			resp.setMsg("系统异常");
			logger.info("此工单正在宝付支付中");
			return resp;
		}

		if (RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId)) {
			resp.setCode("102");
			resp.setMsg("系统异常");
			logger.info("此工单正在连连支付中");
			return resp;
		}

		Long statusId = bwOrder.getStatusId();// 工单状态
		logger.info("工单状态:" + statusId);
		if (!(statusId.intValue() == 9 || statusId.intValue() == 13)) {
			resp.setCode("102");
			resp.setMsg("工单只有还款中或逾期中才可还款");
			logger.info("工单只有还款中或逾期中才可还款");
			return resp;
		}

		BwRepaymentPlan bwRepaymentPlan = new BwRepaymentPlan();
		bwRepaymentPlan.setOrderId(bwOrder.getId());
		bwRepaymentPlan = findBwRepaymentPlanByAttrProxy(bwRepaymentPlan);
		if (CommUtils.isNull(bwRepaymentPlan)) {
			resp.setCode("102");
			resp.setMsg("系统异常");
			logger.info("没有符合条件的还款计划");
			return resp;
		}

		if (statusId.intValue() == 9 && "defer".equals(type)) {
			// 获取下个还款日
			// 下个还款日
			String repayTime = "";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
			repayTime = formatter.format(bwRepaymentPlan.getRepayTime());
			logger.info("下个还款日:" + repayTime);
			Date repayDate = formatter.parse(repayTime);
			int day = MyDateUtils.getDaySpace(new Date(), repayDate);// 间隔时间
			logger.info("还款中状态下到期间隔天数:" + day);
			if (day > 10) {
				resp.setCode("102");
				resp.setMsg("到期时间十天内方可续贷");
				logger.info("到期时间十天内方可续贷");
				return resp;
			}
		}

		// 判断该用户是否签约
		Long borrowerId = bwOrder.getBorrowerId();
		logger.info("开始调用连连签约查询接口,borrowerId=" + borrowerId + ",cardNo=" + bwBankCard.getCardNo());
		CardQueryResult cardQueryResult = LianLianPayService.cardBindQuery(borrowerId.toString(),
				bwBankCard.getCardNo());

		logger.info("结束调用连连签约查询接口,cardQueryResult=" + JSONObject.toJSONString(cardQueryResult));

		if (CommUtils.isNull(cardQueryResult)) {
			resp.setCode("102");
			resp.setMsg("未签约");
			logger.info("调用连连签约查询接口返回结果为空");
			return resp;
		}

		if (!"0000".equals(cardQueryResult.getRet_code())) {
			resp.setCode("102");
			resp.setMsg(Rong360Util.convertLian2Msg(cardQueryResult.getRet_code()));
			logger.info("调用连连签约查询接口返回结果失败，ret_code != 0000");
			return resp;
		}

		List<Agreement> agreements = cardQueryResult.getAgreement_list();
		if (CommUtils.isNull(agreements)) {
			resp.setCode("102");
			resp.setMsg("未签约");
			logger.info("调用连连签约查询接口返回结果失败,Agreement_list is null");
			return resp;
		}

		String agreeNo = agreements.get(0).getNo_agree();

		logger.info("调用连连签约查询接口返回结果成功，连连支付协议号:" + agreeNo);

		List<RepaymentPlan> repays = new ArrayList<RepaymentPlan>();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		// 设置实际支付金额
		if ("repay".equals(type)) {
			amount = String.valueOf(bwRepaymentPlan.getRealityRepayMoney());
			BwOverdueRecord overdueRecord = new BwOverdueRecord();
			overdueRecord.setOrderId(bwOrder.getId());
			overdueRecord = findBwOverdueRecordByAttrProxy(overdueRecord);
			if (!CommUtils.isNull(overdueRecord)) {
				logger.info("有逾期记录,累加逾期金额");
				amount = new BigDecimal(amount).add(new BigDecimal(overdueRecord.getOverdueAccrualMoney()))
						.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			}
			logger.info("amount=" + amount);
		}

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
			resp.setCode("102");
			resp.setMsg("支付授权失败");
			logger.info("调用连连授权接口返回结果为空");
			return resp;
		}

		if (!"0000".equals(planResult.getRet_code())) {
			resp.setCode("102");
			resp.setMsg(Rong360Util.convertLian2Msg(planResult.getRet_code()));
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
		// 测试
		// repayRequest.setMoney_order("0.01");
		repayRequest.setSchedule_repayment_date(repays.get(0).getDate());
		repayRequest.setRepayment_no(bwOrder.getOrderNo());
		repayRequest.setUser_info_bind_phone(bwBorrower.getPhone());
		repayRequest.setUser_info_dt_register(dateFormat.format(bwBorrower.getCreateTime()));
		repayRequest.setUser_info_full_name(bwBorrower.getName());
		repayRequest.setUser_info_id_no(bwBorrower.getIdCard());
		repayRequest.setNotify_url(SystemConstant.NOTIRY_URL + NOTIFY_MAP.get(type));

		// 存入连连redis中，有效时间15分钟
		synchronized (this) {
			if (RedisUtils.hexists(RONG_360_XUDAI, orderId)) {
                resp.setCode("102");
                resp.setMsg("系统异常");
                logger.info("此工单正在续贷中");
                return resp;
            }

			if (RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId)) {
                resp.setCode("102");
                resp.setMsg("系统异常");
                logger.info("此工单正在宝付支付中");
                return resp;
            }

			if (RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId)) {
                resp.setCode("102");
                resp.setMsg("系统异常");
                logger.info("此工单正在连连支付中");
                return resp;
            }
			if (!RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId())) {
                RedisUtils.setex(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId(), bwOrder.getId().toString(), 900);
                logger.info("存redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId().toString() + "]");
            }
		}

		logger.info("开始调用连连支付接口，repayRequest=" + JSONObject.toJSONString(repayRequest));
		RepaymentResult repaymentResult = LianLianPayService.bankRepay(repayRequest);
		logger.info("结束调用连连支付接口，repaymentResult=" + JSONObject.toJSONString(repaymentResult));

		if (CommUtils.isNull(repaymentResult)) {
			resp.setCode("102");
			resp.setMsg("支付失败");
			logger.info("调用连连支付接口返回结果为空");
			return resp;
		}

		if (!"0000".equals(repaymentResult.getRet_code()) && !"1003".equals(repaymentResult.getRet_code())) {
			resp.setCode("102");
			resp.setMsg(Rong360Util.convertLian2Msg(repaymentResult.getRet_code()));
			// 删除连连REDIS
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId() + "]");
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId());
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId() + "]成功");
			logger.info("调用连连支付接口返回结果失败,ret_code != 0000");
			return resp;
		}

		logger.info("调用连连支付接口成功");
		resp.setCode("200");
		resp.setMsg("支付成功");
		return resp;
	}

	/**
	 * 还款回调
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "/repaymentNotify.do")
	public NotifyNotice repaymentNotify(HttpServletRequest request) {
		ThreadLocalUtil.set("REPAYNOTIFY");
		String methodName = "AppRong360Controller.repaymentNotify";
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
			logger.info("验证签名通过");

			String orderId = notifyResult.getNo_order().substring(20);

			// 验证是否成功
			if (!"SUCCESS".equals(notifyResult.getResult_pay())) {
				notice.setRet_code("102");
				notice.setRet_msg("交易失败");

				try {
					String thirdOrderNo = bwOrderRongService.findThirdOrderNoByOrderId(orderId);
					// 还款状态
					RepayFeedBackReq repayFeedBackReq = new RepayFeedBackReq();
					repayFeedBackReq.setOrder_no(thirdOrderNo);
					repayFeedBackReq.setPeriod_nos("1");
					repayFeedBackReq.setRepay_status("2");
					repayFeedBackReq.setRepay_place("1");
					repayFeedBackReq.setRemark(notifyResult.getResult_pay());
					logger.info("开始调用融360还款状态反馈接口," + repayFeedBackReq);
					RepayFeedBackResp repayFeedBackResp = BeadWalletRong360Service.repayFeedBack(repayFeedBackReq);
					logger.info("结束调用融360还款状态反馈接口," + repayFeedBackResp);
				} catch (Exception e) {
					logger.error("调用融360还款状态反馈接口异常", e);
				}
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setOrderId(Long.parseLong(orderId));
			bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);
			if (CommUtils.isNull(bwOrderRong)) {
				notice.setRet_code("101");
				notice.setRet_msg("融360工单不存在");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			ThreadLocalUtil.set("REPAYNOTIFY-" + bwOrderRong.getThirdOrderNo());
			logger.info("参数校验通过");

			BwOrder order = findBwOrderByIdProxy(Long.parseLong(orderId));

			if (CommUtils.isNull(order)) {
				notice.setRet_code("101");
				notice.setRet_msg("工单不存在");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			if (order.getStatusId().intValue() == 6) {
				notice.setRet_code("0000");
				notice.setRet_msg("交易成功");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			// 查询还款人信息
			BwBorrower borrower = findBwBorrowerByIdProxy(order.getBorrowerId());
			if (CommUtils.isNull(borrower)) {
				notice.setRet_code("101");
				notice.setRet_msg("借款人为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			// 查询银行卡信息
			BwBankCard card = findBwBankCardByBoorwerIdProxy(borrower.getId());
			if (CommUtils.isNull(card)) {
				notice.setRet_code("101");
				notice.setRet_msg("银行卡信息为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
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

			/**
			 * 融360反馈
			 */
			try {
				String thirdOrderNo = bwOrderRongService.findThirdOrderNoByOrderId(orderId);
				// 订单状态
				OrderFeedBackReq orderFeedBackReq = new OrderFeedBackReq();
				orderFeedBackReq.setOrder_no(thirdOrderNo);
				orderFeedBackReq.setOrder_status(200);
				logger.info("开始调用融360订单状态反馈接口," + orderFeedBackReq);
				OrderFeedBackResp orderFeedBackResp = BeadWalletRong360Service.orderFeedBack(orderFeedBackReq);
				logger.info("结束调用融360订单状态反馈接口," + orderFeedBackResp);

				// 账单状态
				RepaymentFeedBackReq repaymentFeedBackReq = new RepaymentFeedBackReq();
				repaymentFeedBackReq.setOrder_no(thirdOrderNo);
				repaymentFeedBackReq.setPeriod_no("1");
				repaymentFeedBackReq.setAmount(notifyResult.getMoney_order());
				repaymentFeedBackReq.setBill_status("2");
				repaymentFeedBackReq.setRemark("还款金额：" + notifyResult.getMoney_order());
				logger.info("开始调用融360账单状态反馈接口," + repaymentFeedBackReq);
				RepaymentFeedBackResp repaymentFeedBackResp = BeadWalletRong360Service
						.repaymentFeedBack(repaymentFeedBackReq);
				logger.info("结束调用融360账单状态反馈接口," + repaymentFeedBackResp);

				// 还款状态
				RepayFeedBackReq repayFeedBackReq = new RepayFeedBackReq();
				repayFeedBackReq.setOrder_no(thirdOrderNo);
				repayFeedBackReq.setPeriod_nos("1");
				repayFeedBackReq.setRepay_status("1");
				repayFeedBackReq.setRepay_place("1");
				logger.info("开始调用融360还款状态反馈接口," + repayFeedBackReq);
				RepayFeedBackResp repayFeedBackResp = BeadWalletRong360Service.repayFeedBack(repayFeedBackReq);
				logger.info("结束调用融360还款状态反馈接口," + repayFeedBackResp);

			} catch (Exception e) {
				logger.error("调用融360反馈接口异常", e);
			}

			// try {
			// logger.info("开始异步通知");
			// Map<String, Object> resp = channelService.sendOrderStatusMQ("11", orderId, "6");
			// logger.info("结束异步通知,resp="+JSONObject.toJSONString(resp));
			// } catch (Exception e) {
			// logger.error("异步通知异常", e);
			// }

			notice.setRet_code("0000");
			notice.setRet_msg("交易成功");
		} catch (Exception e) {
			logger.error("还款回调异常", e);
			notice.setRet_code("103");
			notice.setRet_msg("交易失败");
		}

		logger.info(methodName + " end,resp=" + JSONObject.toJSONString(notice));
		ThreadLocalUtil.remove();
		return notice;
	}

	/**
	 * 还款回调
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "/deferNotify.do")
	public NotifyNotice deferNotify(HttpServletRequest request) {
		ThreadLocalUtil.set("DEFERNOTIFY");
		String methodName = "AppRong360Controller.deferNotify";
		logger.info(methodName + " start");
		NotifyNotice notice = new NotifyNotice();

		try {
			NotifyResult notifyResult = getNotifyResult(request);
			logger.info("notifyResult=" + JSONObject.toJSONString(notifyResult));

			// 判断是否存在该笔交易 code dy0001
			BwPlatformRecord bwPlatformRecord = new BwPlatformRecord();
			bwPlatformRecord.setTradeNo(notifyResult.getOid_paybill());
			int count = bwPlatformRecordService.getBwPlatformRecordCount(bwPlatformRecord);
			if (count > 0) {
				notice.setRet_code("0000");
				notice.setRet_msg("交易成功");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}
			
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
			logger.info("验证签名通过");

			// 验证是否成功
			if (!"SUCCESS".equals(notifyResult.getResult_pay())) {
				notice.setRet_code("102");
				notice.setRet_msg("交易失败");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			String orderId = notifyResult.getNo_order().substring(20);

			BwOrderRong bwOrderRong = new BwOrderRong();
			bwOrderRong.setOrderId(Long.parseLong(orderId));
			bwOrderRong = findBwOrderRongByAttrProxy(bwOrderRong);

			if (CommUtils.isNull(bwOrderRong)) {
				notice.setRet_code("101");
				notice.setRet_msg("融360工单不存在");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				return notice;
			}

			ThreadLocalUtil.set("DEFERNOTIFY-" + bwOrderRong.getThirdOrderNo());

			boolean b = RedisUtils.hexists(RONG_360_XUDAI, orderId);
			if (b) {
				logger.info("该工单:" + orderId + " 已经存在redis[" + RONG_360_XUDAI + "]中");
				notice.setRet_code("0000");
				notice.setRet_msg("交易成功");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}
			boolean c = RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId);
			if (c) {
				logger.info("该工单:" + orderId + " 已经存在redis[" + SystemConstant.NOTIFY_BAOFU + "]中");
				notice.setRet_code("0000");
				notice.setRet_msg("交易成功");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			BwOrder order = findBwOrderByIdProxy(Long.parseLong(orderId));

			if (CommUtils.isNull(order)) {
				notice.setRet_code("101");
				notice.setRet_msg("工单不存在");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			if (order.getStatusId().intValue() == 6) {
				notice.setRet_code("0000");
				notice.setRet_msg("交易成功");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			// 查询还款人信息
			BwBorrower borrower = bwBorrowerService.findBwBorrowerByOrderId(Long.valueOf(orderId));
			if (CommUtils.isNull(borrower)) {
				notice.setRet_code("101");
				notice.setRet_msg("借款人为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			// 查询银行卡信息
			BwBankCard card = findBwBankCardByBorrowerIdProxy(borrower.getId());
			if (CommUtils.isNull(card)) {
				notice.setRet_code("101");
				notice.setRet_msg("银行卡信息为空");
				logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
				ThreadLocalUtil.remove();
				return notice;
			}

			// 记录流水
			logger.info("记录交易流水");
			int platFormInt = savePlatformRecord(notifyResult, card, borrower, order);
			logger.info("记录交易流水结束,新增条数：" + platFormInt);

			logger.info("存续贷redis[" + RONG_360_XUDAI + "]开始");
			RedisUtils.hset("xudai:order_id", orderId, orderId);
			logger.info("存续贷redis[" + RONG_360_XUDAI + "]结束");

			// 删除连连REDIS
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId() + "]");
			RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId());
			logger.info("删除redis[" + SystemConstant.NOTIFY_LIANLIAN_PRE + order.getId() + "]成功");

			notice.setRet_code("0000");
			notice.setRet_msg("交易成功");
		} catch (Exception e) {
			logger.error("还款回调异常", e);
			notice.setRet_code("103");
			notice.setRet_msg("交易失败");
		}

		logger.info(methodName + " end, resp=" + JSONObject.toJSONString(notice));
		ThreadLocalUtil.remove();
		return notice;
	}
	
	@ResponseBody
	@RequestMapping(value = "/setMapRedis.do")
	public Map<String, String> setMapRedis(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String key = request.getParameter("key");
		String field = request.getParameter("field");
		String value = request.getParameter("value");
		Long resultNum = RedisUtils.hset(key, field, value);
		if (!CommUtils.isNull(resultNum)) {
			map.put("result", "成功");
		}else {
			map.put("result", "失败");
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMapRedis.do")
	public Map<String, String> getMapRedis(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String key = request.getParameter("key");
		String field = request.getParameter("field");
		String value = RedisUtils.hget(key, field);
		map.put("key", key);
		map.put("field", field);
		map.put("value", value);
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/countMapRedis.do")
	public Map<String, String> countMapRedis(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String key = request.getParameter("key");
		Map<String, String> value = RedisUtils.hgetall(key);
		if (CommUtils.isNull(value)) {
			map.put("key", key);
			map.put("count", "0");
			return map;
		}
		map.put("key", key);
		map.put("count", String.valueOf(value.keySet().size()));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/setStringRedis.do")
	public Map<String, String> setStringRedis(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String key = request.getParameter("key");
		String value = request.getParameter("value");
		String resultNum = RedisUtils.set(key, value);
		if (!CommUtils.isNull(resultNum)) {
			map.put("result", "成功");
		}else {
			map.put("result", "失败");
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getStringRedis.do")
	public Map<String, String> getStringRedis(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String key = request.getParameter("key");
		String value = RedisUtils.get(key);
		map.put("key", key);
		map.put("value", value);
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/setListRedis.do")
	public Map<String, String> setListRedis(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String key = request.getParameter("key");
		String value = request.getParameter("value");
		Long resultNum = RedisUtils.lpush(key, value);
		if (!CommUtils.isNull(resultNum)) {
			map.put("result", "成功");
		}else {
			map.put("result", "失败");
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/countListRedis.do")
	public Map<String, String> countListRedis(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String key = request.getParameter("key");
		Long value = RedisUtils.llen(key);
		if (CommUtils.isNull(value)) {
			map.put("key", key);
			map.put("count", "0");
			return map;
		}
		map.put("key", key);
		map.put("count", String.valueOf(value));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getListRedis.do")
	public List<String> getListRedis(HttpServletRequest request) {
		List<String> list = new ArrayList<String>();
		String key = request.getParameter("key");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		
		list = RedisUtils.lrange(key, Long.parseLong(start), Long.parseLong(end));
		return list;
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

	/**
	 * 解绑
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "/unBind.do")
	public void unBind(HttpServletRequest request) {
		String methodName = "AppRong360Controller.unBind";
		String borrowerId = request.getParameter("borrowerId");
		String cardNo = request.getParameter("cardNo");

		logger.info(methodName + " start,userId=" + borrowerId + ",cardNo=" + cardNo);

		try {
			// 判断该用户是否签约
			logger.info("开始调用连连签约查询接口,borrowerId=" + borrowerId + ",cardNo=" + cardNo);
			CardQueryResult cardQueryResult = LianLianPayService.cardBindQuery(borrowerId.toString(), cardNo);

			logger.info("cardQueryResult=" + JSONObject.toJSONString(cardQueryResult));

			List<Agreement> agreements = cardQueryResult.getAgreement_list();
			if (CommUtils.isNull(agreements)) {
				logger.info("调用连连签约查询接口返回结果失败，该用户未签约连连支付");
				return;
			}

			String agreeNo = agreements.get(0).getNo_agree();
			String unbind = LianLianPayService.unBind(borrowerId, agreeNo);
			logger.info("unbind=" + unbind);
		} catch (Exception e) {
			logger.error(methodName + " occured exception", e);
		}
	}

	public void unBind(String borrowerId, String cardNo) {
		try {
			// 判断该用户是否签约
			logger.info("开始调用连连签约查询接口,borrowerId=" + borrowerId + ",cardNo=" + cardNo);
			CardQueryResult cardQueryResult = LianLianPayService.cardBindQuery(borrowerId.toString(), cardNo);

			logger.info("cardQueryResult=" + JSONObject.toJSONString(cardQueryResult));

			List<Agreement> agreements = cardQueryResult.getAgreement_list();
			if (CommUtils.isNull(agreements)) {
				logger.info("调用连连签约查询接口返回结果失败，该用户未签约连连支付");
				return;
			}
			logger.info("开始解绑");
			String agreeNo = agreements.get(0).getNo_agree();
			String unbind = LianLianPayService.unBind(borrowerId, agreeNo);
			logger.info("unbind=" + unbind);
		} catch (Exception e) {
			logger.error("解绑异常", e);
		}
	}

	private void methodEnd(StopWatch stopWatch, String methodName, String message, Rong360Resp resp) {
		stopWatch.stop();
		logger.info(methodName + " end,costTime=" + stopWatch.getTotalTimeMillis() + "," + message + ",resp=" + resp);
		ThreadLocalUtil.remove();
	}

	private BwOrderRong getByOrderNoProxy(String orderNo) {
		logger.info("开始查询融360工单,third_order_no=" + orderNo);
		BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
		logger.info("结束查询融360工单,bwOrderRong=" + JSONObject.toJSONString(bwOrderRong));
		return bwOrderRong;
	}

	private BwOrderRong findBwOrderRongByAttrProxy(BwOrderRong bwOrderRong) {
		logger.info("开始查询融360工单,bwOrderRong=" + JSONObject.toJSONString(bwOrderRong));
		bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
		logger.info("结束查询融360工单,bwOrderRong=" + JSONObject.toJSONString(bwOrderRong));
		return bwOrderRong;
	}

	private BwAdjunct findBwAdjunctByAttrProxy(BwAdjunct bwAdjunct) {
		logger.info("开始查询附件表,bwAdjunct=" + JSONObject.toJSONString(bwAdjunct));
		bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
		logger.info("结束查询附件表,bwAdjunct=" + JSONObject.toJSONString(bwAdjunct));
		return bwAdjunct;
	}

	private BwBorrower findBwBorrowerByAttrProxy(BwBorrower borrower) {
		logger.info("开始查询借款人信息,borrower=" + JSONObject.toJSONString(borrower));
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		logger.info("结束查询借款人信息,borrower=" + JSONObject.toJSONString(borrower));
		return borrower;
	}

	private BwBankCard findBwBankCardByBoorwerIdProxy(Long borrowerId) {
		logger.info("开始查询银行卡信息,borrowerId=" + borrowerId);
		BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
		logger.info("结束查询银行卡信息,bwBankCard=" + JSONObject.toJSONString(bwBankCard));
		return bwBankCard;
	}

	private BwBankCard findBwBankCardByBorrowerIdProxy(Long borrowerId) {
		logger.info("开始查询银行卡信息,borrowerId=" + borrowerId);
		BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBorrowerId(borrowerId);
		logger.info("结束查询银行卡信息,bwBankCard=" + JSONObject.toJSONString(bwBankCard));
		return bwBankCard;
	}

	private BwBorrower findBwBorrowerByIdProxy(Long borrowerId) {
		logger.info("开始查询借款人信息,borrowerId=" + borrowerId);
		BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
		logger.info("结束查询借款人信息,borrower=" + JSONObject.toJSONString(bwBorrower));
		return bwBorrower;
	}

	private BwOrder findBwOrderByIdProxy(Long orderId) {
		logger.info("开始查询工单,orderId=" + orderId);
		BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
		logger.info("结束查询工单,bwOrder=" + JSONObject.toJSONString(bwOrder));
		return bwOrder;
	}

	private BwRepaymentPlan findBwRepaymentPlanByAttrProxy(BwRepaymentPlan bwRepaymentPlan) {
		logger.info("开始查询还款计划,bwRepaymentPlan=" + JSONObject.toJSONString(bwRepaymentPlan));
		bwRepaymentPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(bwRepaymentPlan.getOrderId());
		logger.info("结束查询还款计划,bwRepaymentPlan=" + JSONObject.toJSONString(bwRepaymentPlan));
		return bwRepaymentPlan;
	}

	private BwOverdueRecord findBwOverdueRecordByAttrProxy(BwOverdueRecord overdueRecord) {
		logger.info("开始查询逾期记录,overdueRecord=" + JSONObject.toJSONString(overdueRecord));
		overdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(overdueRecord);
		logger.info("结束查询逾期记录,overdueRecord=" + JSONObject.toJSONString(overdueRecord));
		return overdueRecord;
	}

	private BwBankCard findBwBankCardByAttrProxy(BwBankCard bwBankCard) {
		logger.info("开始查询银行卡信息:bwBankCard=" + JSONObject.toJSONString(bwBankCard));
		bwBankCard = bwBankCardService.findBwBankCardByAttr(bwBankCard);
		logger.info("银行卡信息查询结果：bwBankCard=" + JSONObject.toJSONString(bwBankCard));
		return bwBankCard;
	}

	public static void main(String[] args) throws ParseException {
		// double f = 111231.5585;
		// BigDecimal b = new BigDecimal(f);
		// //保留2位小数
		// double f1 = b.setScale(3, BigDecimal.ROUND_UP).doubleValue();
		// System.out.println(f1);

		String repayTime = "2017年05月16日";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		// repayTime = formatter.format(new Date());
		logger.info("下个还款日============================" + repayTime);
		Date repayDate = formatter.parse(repayTime);
		int day = MyDateUtils.getDaySpace(new Date(), repayDate);// 间隔时间
		logger.info("还款中状态下到期间隔天数============================" + day);

		// 5 6 7 8 9 10 11 12 13 14 15 16
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(new Date());
		// calendar.add(Calendar.DAY_OF_MONTH, -10);
		// Date date = calendar.getTime();
		// System.out.println(date.getTime() / 1000);
		// System.out.println(formatter.format(date));

		Long ll = System.currentTimeMillis();
		ll = ll / 1000;
		System.out.println(formatter.format(new Date(ll * 1000)));

		Date d = MyDateUtils.addMonths(new Date(), 1);
		System.out.println(formatter.format(d.getTime()));

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 30);
		Long defer_due_time = calendar.getTime().getTime() / 1000;
		System.out.println(formatter.format(defer_due_time * 1000));

		logger.info("开始验证身份证");
		String phoneRong = "1507824****".substring(0, "1507824****".length() - 4);
		String myPhone = "15078231111".substring(0, "15078231111".length() - 4);
		if (!phoneRong.equals(myPhone)) {
			System.out.println("not");
		} else {
			System.out.println("yes");
		}
	}
}