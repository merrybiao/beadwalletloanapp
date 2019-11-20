package com.waterelephant.controller;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.beadwallet.service.serve.BeadWalletHaoDaiService;
import com.fuiou.data.AppTransReqData;
import com.waterelephant.annotation.LockAndSyncRequest;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.dto.BwOverdueRecordDto;
import com.waterelephant.dto.QueryOrderRepayInfo;
import com.waterelephant.dto.QueryRepayInfo;
import com.waterelephant.entity.ActivityInfo;
import com.waterelephant.entity.BwAheadRepay;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwCheckRecord;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwPersonRecord;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.ActivityInfoService;
import com.waterelephant.service.BwCheckRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwPersonRecordService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.FuYouService;
import com.waterelephant.service.IBwAheadRepayService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwOverdueRecordDtoService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.impl.BwBorrowerService;
import com.waterelephant.service.impl.BwRepaymentPlanService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.ControllerUtil;
import com.waterelephant.utils.DoubleUtil;
import com.waterelephant.utils.GenerateSerialNumber;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.MyJSONUtil;
import com.waterelephant.utils.NumberUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app/repay")
public class BwRepaymentController {
	private Logger logger = Logger.getLogger(BwRepaymentController.class);

	@Autowired
	private IBwOverdueRecordDtoService bwOverdueRecordDtoService;

	@Autowired
	private IBwAheadRepayService bwAheadRepayService;

	@Autowired
	private IBwRepaymentPlanService repaymentPlanService;

	@Autowired
	private IBwOrderService bwOrderService;

	@Autowired
	private BwRepaymentPlanService bwRepaymentPlanService;

	@Autowired
	private BwPersonRecordService bwPersonRecordService;

	@Autowired
	private BwBorrowerService bwBorrowerService;

	@Autowired
	private BwOrderRongService bwOrderRongService;

	@Autowired
	private FuYouService fuYouService;

	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;
	@Autowired
	private BwCheckRecordService bwCheckRecordService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ActivityInfoService activityInfoService;

	/**
	 * 逾期记录查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/queryOverdueRecord.do")
	public AppResponseResult queryOverdueRecord(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		if (CommUtils.isNull(orderId)) {
			result.setCode("901");
			result.setMsg("工单号为空");
		}
		BwOverdueRecordDto bo = new BwOverdueRecordDto();
		bo.setOrderId(Long.parseLong(orderId));
		bo = bwOverdueRecordDtoService.findBwOverdueRecordDtoByAttr(bo);
		result.setCode("000");
		result.setMsg("逾期记录查询成功");
		result.setResult(bo);
		return result;
	}

	/**
	 * 提前还款
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/advanceRepay.do")
	public AppResponseResult advanceRepay(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		String repayMoney = request.getParameter("repayMoney");
		if (CommUtils.isNull(orderId)) {
			result.setCode("901");
			result.setMsg("订单id为空");
			return result;
		}
		if (CommUtils.isNull(repayMoney)) {
			result.setCode("902");
			result.setMsg("提前还款金额为空");
			return result;
		}
		BwOrder bw = new BwOrder();
		bw.setId(Long.parseLong(orderId));
		bw = bwOrderService.findBwOrderByAttr(bw);
		bw.setApplyPayStatus(1);
		bw.setUpdateTime(new Date());
		int num = bwOrderService.updateBwOrder(bw);
		if (num > 0) {
			result.setCode("000");
			result.setMsg("提前还款申请成功");
			return result;
		} else {
			result.setCode("903");
			result.setMsg("提前还款申请失败");
			return result;
		}
	}

	/**
	 * 修改工单状态 wrh 确认马上拿钱
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/updOrder.do")
	@LockAndSyncRequest
	public AppResponseResult updOrder(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
		Long orderId = NumberUtil.parseLong(request.getParameter("orderId"), null);
		logger.info("【BwRepaymentController.updOrder】orderId：" + orderId + "，paramMap=" + paramMap);
		Integer repayType = NumberUtil.parseInteger(request.getParameter("repayType"), null);
		logger.info("工单Id========" + orderId);
		logger.info("获取到repayType：" + repayType);
		if (orderId == null || orderId <= 0L) {
			result.setCode("1001");
			result.setMsg("工单ID为空");
			return result;
		}
		if (repayType == null || repayType <= 0) {
			result.setCode("1003");
			result.setMsg("工单还款方式为空");
			return result;
		}
		try {
			result = bwOrderService.updateAndTakeMoney(orderId, repayType);
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("系统异常!");
			logger.error("马上拿钱updOrder系统异常", e);
		}
		return result;
	}

	/**
	 * 最新马上拿钱接口，先验证语音验证码
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/takeMoney.do")
	@LockAndSyncRequest
	public AppResponseResult takeMoney(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
		String orderId = request.getParameter("orderId");
		logger.info("【BwRepaymentController.takeMoney】orderId：" + orderId + "，paramMap=" + paramMap);
		String appToken = (String) session.getAttribute(SystemConstant.APP_SESSION_LOGIN_TOKEN);
		Long borrowerId = (Long) session.getAttribute(appToken);
		if (borrowerId == null || borrowerId <= 0L) {
			if (orderId != null) {
				BwOrder order = bwOrderService.findBwOrderById(orderId);
				if (order != null) {
					borrowerId = order.getBorrowerId();
				}
			}
		}
		AppResponseResult result = verifyVoiceCode(request, borrowerId);
		if ("000".equals(result.getCode())) {
			result = updOrder(request);
			// 验证成功，删除redis验证码
			if ("000".equals(result.getCode())) {
				RedisUtils.del(RedisKeyConstant.VOICE_CODE_PRE + borrowerId);
			}

			// 判断是否是审核通过30分钟内完成的签约，是就给redis中的抽奖机会+1，给这个方式获得的数量+1；这个活动此种方式只能获得一次机会====start
			// 查询终审通过的时间
			ActivityInfo activity = new ActivityInfo();
			activity.setActivityType("3");// 抽奖
			activity = activityInfoService.queryActivityInfo(activity);
			if (!CommUtils.isNull(activity)) {
				Long start_time = activity.getStartTime().getTime();
				Long end_time = activity.getEndTime().getTime();
				Long cur_time = new Date().getTime();
				if (start_time <= cur_time && cur_time <= end_time) {// 判断是否在活动时间内
					BwCheckRecord bwCheckRecord = new BwCheckRecord();
					bwCheckRecord.setOrderId(Long.parseLong(orderId));
					bwCheckRecord.setStatusId(4L);
					bwCheckRecord.setResult(3);
					Date audit_pass_time = bwCheckRecordService.findCreateTimeByOrderId(bwCheckRecord);
					if (!CommUtils.isNull(audit_pass_time)) {
						// 比较相隔
						long between = (new Date().getTime() - audit_pass_time.getTime()) / 1000 / 60;
						if (between <= 30) {
							int addit_pass_count = 0;
							if (RedisUtils.hexists("activity:addit_pass:count", String.valueOf(borrowerId))) {
								addit_pass_count = Integer.parseInt(
										RedisUtils.hget("activity:addit_pass:count", String.valueOf(borrowerId)));
							}
							if (addit_pass_count < 1) {
								int chance_count = 0;
								if (RedisUtils.hexists("activity:chance:count", String.valueOf(borrowerId))) {
									chance_count = Integer.parseInt(
											RedisUtils.hget("activity:chance:count", String.valueOf(borrowerId)));
								}
								RedisUtils.hset("activity:chance:count", String.valueOf(borrowerId),
										String.valueOf(chance_count + 1));
							}
							RedisUtils.hset("activity:addit_pass:count", String.valueOf(borrowerId),
									String.valueOf(addit_pass_count + 1));
						}
					}
				}
			}
			// ====end

		}
		logger.info("【BwRepaymentController.takeMoney】orderId：" + orderId + ",borrowerId:" + borrowerId + "返回"
				+ JSON.toJSONString(result));
		return result;
	}

	/**
	 * 验证语音验证码
	 * 
	 * @param request
	 * @return
	 */
	private AppResponseResult verifyVoiceCode(HttpServletRequest request, Long borrowerId) {
		String requestVoiceCode = request.getParameter("voiceCode");
		AppResponseResult result = new AppResponseResult();
		String voiceCode = null;
		if (borrowerId != null && borrowerId > 0L) {
			voiceCode = RedisUtils.get(RedisKeyConstant.VOICE_CODE_PRE + borrowerId);
		}
		logger.info("【BwRepaymentController.takeMoney】borrowerId:" + borrowerId + ",redis验证码voiceCode:" + voiceCode
				+ ",requestVoiceCode:" + requestVoiceCode);
		if (requestVoiceCode != null && requestVoiceCode.equals(voiceCode)) {
			result.setCode("000");
			result.setMsg("SUCCESS");
			result.setResult(true);
			return result;
		}
		result.setCode("108");
		result.setMsg("验证码错误");
		result.setResult(false);
		return result;
	}

	/**
	 * 新app：易妙贷 审核
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/getOrderRepayInfo.do") // /appCheckLogin/getOrderRepayInfo.do
	public AppResponseResult getOrderRepayInfo(HttpServletRequest request) {
		AppResponseResult respResult = new AppResponseResult();
		try {
			logger.info("工单Id========" + request.getParameter("orderId"));
			logger.info("借款人Id========" + request.getParameter("bwId"));
			long orderId = NumberUtils.toLong(request.getParameter("orderId"), 0L);
			long bwId = NumberUtils.toLong(request.getParameter("bwId"), 0L);
			BwOrder bwOrder = null;
			if (orderId > 0L) {
				bwOrder = new BwOrder();
				bwOrder.setId(orderId);
				bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
			}
			if (bwOrder == null) {
				respResult.setCode("101");
				respResult.setMsg("工单不存在！");
				return respResult;
			}
			if (bwId <= 0L) {
				respResult.setCode("102");
				respResult.setMsg("借款人ID不能为空！");
				return respResult;
			}
			Double borrowAmount = bwOrder.getBorrowAmount();// 借款金额
			BwBankCard card = bwBankCardService.findBwBankCardByBorrowerId(bwId);
			if (CommUtils.isNull(card)) {
				respResult.setCode("103");
				respResult.setMsg("银行卡不存在");
				return respResult;
			}

			// 费用产品信息从产品表中获取
			QueryOrderRepayInfo repayInfo = new QueryOrderRepayInfo();
			Integer productId = bwOrder.getProductId();// 获得该工单的产品id
			BwProductDictionary bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(productId);
			if (CommUtils.isNull(bwProductDictionary)) {
				respResult.setCode("111");
				respResult.setMsg("工单未绑定产品信息");
				return respResult;
			}
			Double getpFastReviewCost = bwProductDictionary.getpFastReviewCost();// 快速审核费
			Double getpPlatformUseCost = bwProductDictionary.getpPlatformUseCost();// 平台使用费
			Double getpNumberManageCost = bwProductDictionary.getpNumberManageCost();// 账户管理费
			Double getpCollectionPassagewayCost = bwProductDictionary.getpCollectionPassagewayCost();// 代收通道费
			Double getpCapitalUseCost = bwProductDictionary.getpCapitalUseCost();// 资金使用费
			DecimalFormat df = new DecimalFormat("######0.00");
			Double loanAmount = Double.valueOf(df.format(borrowAmount * (getpFastReviewCost + getpPlatformUseCost
					+ getpNumberManageCost + getpCollectionPassagewayCost + getpCapitalUseCost)));// 借款费用总额
			Double arrivalAmount = DoubleUtil.sub(borrowAmount, loanAmount);// 到账金额

			String term = bwProductDictionary.getpTerm();// 借款期限
			String termType = bwProductDictionary.getpTermType();// '产品类型（1：月；2：天）',
			Date repTime = null;
			if ("2".equals(termType)) {
				repTime = MyDateUtils.addDays(new Date(), Integer.parseInt(term));
			}
			if ("1".equals(termType)) {
				repTime = DateUtils.addMonths(new Date(), Integer.parseInt(term));
			}
			String repayTime = MyDateUtils.DateToString(repTime, MyDateUtils.DATE_TO_STRING_SHORT_PATTERN);
			String cardNum = card.getCardNo().substring(card.getCardNo().length() - 4, card.getCardNo().length());
			String cardString = "尾号" + cardNum + "(" + card.getBankName() + ")";

			repayInfo.setBorrowAmount(borrowAmount);
			repayInfo.setCardString(cardString);
			repayInfo.setpCapitalUseCost(DoubleUtil.mul(borrowAmount, getpCapitalUseCost));
			repayInfo.setpCollectionPassagewayCost(DoubleUtil.mul(borrowAmount, getpCollectionPassagewayCost));
			repayInfo.setpFastReviewCost(DoubleUtil.mul(borrowAmount, getpFastReviewCost));
			repayInfo.setpNumberManageCost(DoubleUtil.mul(borrowAmount, getpNumberManageCost));
			repayInfo.setpPlatformUseCost(DoubleUtil.mul(borrowAmount, getpPlatformUseCost));
			repayInfo.setLoanAmount(loanAmount);
			repayInfo.setReceivedAmount(arrivalAmount);
			repayInfo.setRepayTime(repayTime);
			JSONObject objectToJson = MyJSONUtil.objectToJson(repayInfo, new String[0], true);
			logger.info("==============审核完界面显示信息--");
			respResult.setCode("000");
			respResult.setMsg("成功");
			respResult.setResult(objectToJson);
			return respResult;

		} catch (Exception e) {
			respResult.setCode("111");
			respResult.setMsg("系统异常!");
			logger.error("获取审核界面信息异常", e);
			return respResult;
		}

	}

	/**
	 * 易妙贷 审核
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/queryRepayInfo.do")
	public AppResponseResult queryRepayInfo(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		String bwId = request.getParameter("bwId");
		logger.info("工单Id========" + orderId);
		logger.info("借款人Id========" + bwId);
		try {
			BwOrder bw = new BwOrder();
			bw.setId(Long.parseLong(orderId));
			bw = bwOrderService.findBwOrderByAttr(bw);
			if (bw == null) {
				result.setCode("101");
				result.setMsg("工单不存在！");
				return result;
			}
			Double borrowAmount = bw.getBorrowAmount();
			BwBankCard card = bwBankCardService.findBwBankCardByBorrowerId(Long.valueOf(bwId));
			if (CommUtils.isNull(card)) {
				result.setCode("103");
				result.setMsg("银行卡不存在");
				return result;
			}
			BwProductDictionary product = productService.queryByOrderId(Long.valueOf(orderId));
			QueryRepayInfo queryRepayInfo = productService.calcBorrowCost(borrowAmount, product);
			queryRepayInfo
					.setCardNo(card.getCardNo().substring(card.getCardNo().length() - 4, card.getCardNo().length()));
			queryRepayInfo.setRepayTerm(bw.getRepayTerm());
			logger.info("==============审核完界面显示信息--" + queryRepayInfo.toString());
			result.setCode("000");
			result.setMsg("成功");
			result.setResult(queryRepayInfo);
			return result;

		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("系统异常!");
			logger.error("获取审核界面信息异常", e);
			return result;
		}

	}

	/**
	 * 查询工单被拒信息
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/queryCheckRecord.do")
	public AppResponseResult queryCheckRecord(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		logger.info("工单Id========" + orderId);
		try {
			BwOrder bw = new BwOrder();
			bw.setId(Long.parseLong(orderId));
			bw = bwOrderService.findBwOrderByAttr(bw);
			if (bw == null) {
				result.setCode("101");
				result.setMsg("工单不存在！");
				return result;
			}
			List<BwCheckRecord> list = bwCheckRecordService.queryCheck(orderId);
			if (!CommUtils.isNull(list)) {
				result.setResult(list.get(0).getComment());
			}
			result.setCode("000");
			result.setMsg("成功");
			return result;
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("系统异常!");
			return result;
		}

	}

	/**
	 * 查询是否能够申请提前还款
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/checkWhetherOrNotRepay.do")
	public AppResponseResult checkWhetherOrNotRepay(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		if (CommUtils.isNull(orderId)) {
			result.setCode("901");
			result.setMsg("订单id为空");
			return result;
		}
		BwAheadRepay ba = new BwAheadRepay();
		ba.setOrderId(Long.parseLong(orderId));
		ba = bwAheadRepayService.findBwAheadRepayByAttr(ba);
		if (!CommUtils.isNull(ba)) {
			result.setCode("000");
			result.setMsg("不能够提前还款");
			result.setResult(false);
		} else {
			result.setCode("000");
			result.setMsg("能够提前还款");
			result.setResult(true);
		}
		return result;
	}

	/**
	 * 统计已还款的本金和利息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/sumCorpusAndAccrual.do")
	public AppResponseResult sumCorpusAndAccrual(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		logger.info("工单id为：" + orderId);
		if (CommUtils.isNull(orderId)) {
			result.setCode("902");
			result.setMsg("工单id为空");
			return result;
		}
		Map<String, Object> map = repaymentPlanService.sumCorpusAndAccrualByOrderId(Long.parseLong(orderId));
		logger.info("统计结果map为：" + map);
		if (CommUtils.isNull(map)) {
			map = new HashMap<>();
			map.put("corpus", 0);
			map.put("accrual", 0);
		}
		result.setCode("000");
		result.setMsg("成功");
		result.setResult(map);
		return result;
	}

	/**
	 * 根据工单id和还款状态统计还款本息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/sumRepayMoneyByOrderIdAndStatusId.do")
	public AppResponseResult sumRepayMoneyByOrderIdAndStatusId(HttpServletRequest request,
			HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		String repayStatus = request.getParameter("repayStatus");
		if (CommUtils.isNull(orderId)) {
			result.setCode("1031");
			result.setMsg("订单号为空");
			return result;
		}
		if (CommUtils.isNull(repayStatus)) {
			result.setCode("1032");
			result.setMsg("还款状态为空");
			return result;
		}
		Map<String, Object> map = repaymentPlanService.sumRepayMoneyByOrderIdAndStatusId(Long.parseLong(orderId),
				Integer.parseInt(repayStatus));
		if (CommUtils.isNull(map)) {
			map = new HashMap<String, Object>();
			map.put("repayMoney", 0);
		}
		result.setCode("000");
		result.setMsg("统计还款本息成功");
		result.setResult(map);
		return result;
	}

	/**
	 * 查询是否逾期和富有账户余额
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/queryOrderParam.do")
	public AppResponseResult queryOrderParam(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		logger.info("查询富有账户余额是否充足 接口---开始执行");
		try {
			String userId = request.getParameter("userId");
			String orderId = request.getParameter("orderId");
			logger.info("查询富有账户余额是否充足接口---userId=" + userId);
			logger.info("查询富有账户余额是否充足接口---orderId" + orderId);

			if (RedisUtils.hexists(SystemConstant.WEIXIN_ORDER_ID, orderId)) {
				result.setCode("108");
				result.setMsg("此工单续贷正在处理中..");
				return result;
			}

			BwOrder bwOrder = new BwOrder();
			if (!CommUtils.isNull(orderId)) {
				bwOrder.setId(Long.valueOf(orderId));
			}
			BwOrder order = new BwOrder();
			order.setId(Long.parseLong(orderId));
			order = bwOrderService.findBwOrderByAttr(order);
			if (CommUtils.isNull(order)) {
				result.setCode("108");
				result.setMsg("工单不存在");
				return result;
			}
			Long statusId = order.getStatusId();// 工单状态
			logger.info("工单状态==========statusId==================" + statusId);
			if (!(statusId == 9 || statusId == 13)) {// 还款中 逾期
				result.setCode("108");
				result.setMsg("此工单续贷正在处理中..");// 提示信息方便前端显示 让用户去手动刷新界面
				// 实际提示信息(工单只有还款中和逾期中才可还款 )
				return result;
			}
			// 查询用户
			BwBorrower bwBorrower = new BwBorrower();
			bwBorrower.setId(Long.parseLong(userId));
			BwBorrower bwBorrow = bwBorrowerService.selectOne(bwBorrower);
			if (CommUtils.isNull(bwBorrow)) {
				result.setCode("999");
				result.setMsg("借款用户不存在");
				return result;
			}

			// 查询还款计划
			BwRepaymentPlan paramBwRepaymentPlan = new BwRepaymentPlan();
			paramBwRepaymentPlan.setOrderId(Long.parseLong(orderId));
			BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.findBwRepaymentPlanByAttr(paramBwRepaymentPlan);
			if (CommUtils.isNull(bwRepaymentPlan)) {
				result.setCode("999");
				result.setMsg("还款计划不存在");
				return result;
			}

			// 富有账户余额
			HashMap<String, Double> amountTotal = fuYouService.getFuiouAmount(userId);
			if (amountTotal.size() == 0) {
				result.setCode("9999");
				result.setMsg("富有查询接口失败");
				return result;
			}
			Double totalMoney = amountTotal.get("ct_balance");// 总金额 以分为单位
			Double freezeMoney = amountTotal.get("cf_balance");// 冻结金额 以分为单位
			logger.info("查询富有账户余额是否充足接口---富有账户总金额" + totalMoney);
			logger.info("查询富有账户余额是否充足接口---富有账户解冻金额" + freezeMoney);

			// *********************
			double totalAmt = 0L;// 还款金额初始化
			if (freezeMoney > 0) {
				totalAmt = bwRepaymentPlan.getRepayMoney();// 正常还款金额
			} else {
				totalAmt = bwRepaymentPlan.getRealityRepayMoney();// 实际还款金额
			}
			logger.info("查询富有账户余额是否充足接口---------还款计划还款金额为:" + totalAmt);

			// *********************
			if ((totalMoney / 100) >= totalAmt) {// 够钱直接解冻划拨一系列操作
				// ****************
				if (freezeMoney > 0) {
					// 解冻
					String mchnt_txn_ssn = GenerateSerialNumber.getSerialNumber();
					logger.info("查询富有账户余额是否充足接口---解冻动作开始查询");
					AppResponseResult appResult = fuYouService.removeFreeze(userId, freezeMoney / 100.0, mchnt_txn_ssn);
					logger.info("查询富有账户余额是否充足接口---解冻动作查询完成Code=" + appResult.getCode());
					logger.info("查询富有账户余额是否充足接口---解冻动作查询完成Msg=" + appResult.getMsg());
					// 存储解冻的信息
					// 存入日志
					// 还款 --- 充值到账户
					BwPersonRecord bwPersonRecord = new BwPersonRecord();
					bwPersonRecord.setOrderId(Long.parseLong(orderId));
					if (!CommUtils.isNull(bwBorrow)) {
						bwPersonRecord.setPersonName(bwBorrow.getName());
					}
					bwPersonRecord.setTradeTime(new Date());
					bwPersonRecord.setTradeAmount(freezeMoney / 100);
					bwPersonRecord.setTradeType(2);
					bwPersonRecord.setTradeRemark("解冻");
					bwPersonRecord.setPersonAccount(bwBorrow.getFuiouAcct());
					bwPersonRecord.setTradeChannel(1);
					// 交易流水号 private String tradeNo; 第三方返回的流水号
					bwPersonRecord.setTradeNo(mchnt_txn_ssn);
					bwPersonRecordService.saveBwPersonRecord(bwPersonRecord);
					logger.info("查询富有账户余额是否充足接口---存储日志类完成");
					if (!appResult.getCode().equals("0000")) {
						result.setCode(appResult.getCode());
						result.setMsg(appResult.getMsg());
						return result;
					}
					logger.info("查询富有账户余额解冻动作完成");
				}
				// *******************
				logger.info("查询富有账户余额是否充足接口---个人转到风险备用金开始执行");
				// 扣款
				result = bwOrderService.initbwTransferBu(orderId, userId, Double.toString(totalAmt), bwBorrow);
				result.setCode("000");
				result.setMsg("还款成功");

				try {
					String channel = String.valueOf(bwOrderService.findPathByChannel(Long.parseLong(orderId)));
					if ("12".equals(channel) || "81".equals(channel)) {
						// 获取对应好贷 订单
						String thirdOrderId = bwOrderRongService.findThirdOrderNoByOrderId(orderId);
						BeadWalletHaoDaiService.sendOrderStatus(thirdOrderId, "4");
					}
					// channelService.sendOrderStatus(CommUtils.toString(order.getChannel()), orderId, "6");
				} catch (Exception e) {
					logger.info("====================渠道同步工单状态，回调失败======================");
				}

				return result;
			} else {
				logger.info("查询富有账户余额是否充足接口---不足需要充值");
				result.setCode("104");
				result.setMsg("富友账号余额不足,请先充值!");
				result.setResult(totalAmt - (totalMoney / 100));
				return result;
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.setCode("9999");
			result.setMsg("操作异常联系管理员");
			return result;
		}
	}

	/**
	 * 逾期记录查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/appCheckLogin/queryIsOverdueRecord.do")
	public AppResponseResult queryIsOverdueRecord(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		if (CommUtils.isNull(orderId)) {
			result.setCode("901");
			result.setMsg("工单号为空");
		}
		BwOverdueRecord bo = new BwOverdueRecord();
		bo.setOrderId(Long.parseLong(orderId));
		bo.setOverdueStatus(1);
		bo = bwOverdueRecordService.findBwOverdueRecordByAttr(bo);
		result.setCode("000");
		result.setMsg("逾期记录查询成功");
		result.setResult(bo);
		return result;
	}

	/**
	 * 执行---我要还款 微信
	 * 
	 * "userId":userId, "mchntSsn":mchntSsn, "orderId":orderId, "realityRepayMoney":amt
	 * 
	 */
	@ResponseBody
	@RequestMapping("/repayment.do")
	public AppResponseResult repayment(HttpServletRequest request, HttpServletResponse response,
			AppTransReqData appTransReqData) {
		AppResponseResult appResponseResult = new AppResponseResult();
		try {
			logger.info("开始执行我要还款");

			String param = request.getParameter("param") == null ? "" : request.getParameter("param");
			if (CommUtils.isNull(param)) {
				appResponseResult.setCode("999");
				appResponseResult.setMsg("数据调用异常--param自定义参数为空");
				return appResponseResult;
			}
			logger.info("开始执行我要还款自定义参数为:param==" + param);
			String userId = param.split("_")[0];
			String orderId = param.split("_")[1];

			// 查询用户
			BwBorrower bwBorrower = new BwBorrower();
			bwBorrower.setId(Long.parseLong(userId));
			BwBorrower bwBorrow = bwBorrowerService.selectOne(bwBorrower);
			// 用户不能为空
			if (CommUtils.isNull(bwBorrow)) {
				appResponseResult.setCode("999");
				appResponseResult.setMsg("数据调用异常--用户为空");
				return appResponseResult;
			}
			logger.info("开始执行我要还款---用户查询完成");
			// 充值是否成功
			String resp_code = request.getParameter("resp_code");
			if (CommUtils.isNull(resp_code) || !resp_code.equals("0000")) {
				logger.info("开始执行我要还款---充值异常" + request.getParameter("resp_desc"));
				appResponseResult.setCode("999");
				appResponseResult.setMsg("数据调用异常--充值失败" + request.getParameter("resp_desc"));
				return appResponseResult;
			}
			logger.info("开始执行我要还款---充值完成");
			logger.info("开始执行我要还款---还款 --- 充值到账户开始执行");

			// 查询还款计划
			BwRepaymentPlan paramBwRepaymentPlan = new BwRepaymentPlan();
			paramBwRepaymentPlan.setOrderId(Long.parseLong(orderId));
			BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.findBwRepaymentPlanByAttr(paramBwRepaymentPlan);

			// 查询富友账号
			HashMap<String, Double> amountTotal = fuYouService.getFuiouAmount(userId);
			if (amountTotal.size() == 0) {
				appResponseResult.setCode("9999");
				appResponseResult.setMsg("富有查询接口失败");
				return appResponseResult;
			}
			Double freezeMoney = amountTotal.get("cf_balance");// 冻结金额 以分为单位

			double totalAmt = 0l;
			if (!CommUtils.isNull(bwRepaymentPlan)) {
				if (freezeMoney > 0) {
					totalAmt = bwRepaymentPlan.getRepayMoney();// 正常还款金额
				} else {
					totalAmt = bwRepaymentPlan.getRealityRepayMoney();// 实际还款金额
				}
				logger.info("我要还款 -----划拨的金额为:" + totalAmt);

			}
			// ****************
			if (freezeMoney > 0) {
				// 解冻
				String mchnt_txn_ssn = GenerateSerialNumber.getSerialNumber();
				logger.info("查询富有账户余额是否充足接口---解冻动作开始查询");
				AppResponseResult appResult = fuYouService.removeFreeze(userId, freezeMoney / 100.0, mchnt_txn_ssn);
				logger.info("查询富有账户余额是否充足接口---解冻动作查询完成Code=" + appResult.getCode());
				logger.info("查询富有账户余额是否充足接口---解冻动作查询完成Msg=" + appResult.getMsg());
				// 存储解冻的信息
				// 存入日志
				// 还款 --- 充值到账户
				BwPersonRecord bwPersonRecord = new BwPersonRecord();
				bwPersonRecord.setOrderId(Long.parseLong(orderId));
				if (!CommUtils.isNull(bwBorrow)) {
					bwPersonRecord.setPersonName(bwBorrow.getName());
				}
				bwPersonRecord.setTradeTime(new Date());
				bwPersonRecord.setTradeAmount(freezeMoney / 100);
				bwPersonRecord.setTradeType(2);
				bwPersonRecord.setTradeRemark("解冻");
				bwPersonRecord.setPersonAccount(bwBorrow.getFuiouAcct());
				bwPersonRecord.setTradeChannel(1);
				// 交易流水号 private String tradeNo; 第三方返回的流水号
				bwPersonRecord.setTradeNo(mchnt_txn_ssn);
				bwPersonRecordService.saveBwPersonRecord(bwPersonRecord);
				logger.info("查询富有账户余额是否充足接口---存储日志类完成");
				if (!appResult.getCode().equals("0000")) {
					appResponseResult.setCode(appResult.getCode());
					appResponseResult.setMsg(appResult.getMsg());
					return appResponseResult;
				}
				logger.info("查询富有账户余额解冻动作完成");
			}
			// *******************

			String temp = request.getParameter("amt");// 交易金额
			Double rechargeAmt = Double.valueOf(temp) / 100;
			logger.info("开始执行我要还款充值完成金额为:---" + rechargeAmt);
			// 存入日志
			// 还款 --- 充值到账户
			BwPersonRecord bwPersonRecord = new BwPersonRecord();
			bwPersonRecord.setOrderId(Long.parseLong(orderId));
			if (!CommUtils.isNull(bwBorrow)) {
				bwPersonRecord.setPersonName(bwBorrow.getName());
			}
			bwPersonRecord.setTradeTime(new Date());
			bwPersonRecord.setTradeAmount(rechargeAmt);
			bwPersonRecord.setTradeType(5);
			bwPersonRecord.setTradeRemark("充值");
			bwPersonRecord.setPersonAccount(bwBorrow.getFuiouAcct());
			bwPersonRecord.setTradeChannel(1);
			// 交易流水号 private String tradeNo; 第三方返回的流水号
			bwPersonRecord.setTradeNo(appTransReqData.getMchnt_txn_ssn());
			bwPersonRecordService.saveBwPersonRecord(bwPersonRecord);
			logger.info("开始执行我要还款---还款 --- 充值到账户执行完成");
			return bwOrderService.initbwTransferBu(orderId, userId, Double.toString(totalAmt), bwBorrow);
		} catch (Exception e) {
			appResponseResult.setCode("999");
			appResponseResult.setMsg("数据调用异常--" + e.getMessage());
			return appResponseResult;
		}
	}
}