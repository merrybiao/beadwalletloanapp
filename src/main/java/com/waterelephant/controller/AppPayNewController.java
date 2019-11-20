package com.waterelephant.controller;

import com.alibaba.fastjson.JSON;
import com.beadwallet.entity.baofu.WithHodingResult;
import com.beadwallet.entity.baofu.WithLessRequest;
import com.beadwallet.entity.lianlian.*;
import com.beadwallet.servcie.BaoFuService;
import com.beadwallet.servcie.LianLianPayService;
import com.beadwallet.utils.RSAUtil;
import com.google.gson.Gson;
import com.waterelephant.activity.service.ActivityService;
import com.waterelephant.annotation.LockAndSyncRequest;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.constants.BaofuConstant;
import com.waterelephant.constants.ParameterConstant;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.dto.LoanInfo;
import com.waterelephant.dto.RepaymentBatch;
import com.waterelephant.dto.RepaymentDto;
import com.waterelephant.entity.*;
import com.waterelephant.installment.service.AppPaymentService;
import com.waterelephant.service.*;
import com.waterelephant.service.impl.BwOrderXuDaiService;
import com.waterelephant.utils.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 支付控制层
 * 
 * 
 * Module:
 * 
 * AppPayController.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Controller
@RequestMapping("/pay/new/")
public class AppPayNewController {
	private Logger logger = Logger.getLogger(AppPayNewController.class);
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;
	@Autowired
	private ActivityService activityService;
	/*
	 * @Autowired private ChannelService channelService;
	 */
	@Autowired
	private ProductService productService;
	@Autowired
	private BwOrderStatusRecordService bwOrderStatusRecordService;
	@Autowired
	private BwPaymentDetailService bwPaymentDetailService;
	@Autowired
	private ActivityDiscountDistributeService activityDiscountDistributeService;
	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;
	@Autowired
	private BwOrderRepaymentBatchDetailService bwOrderRepaymentBatchDetailService;
	@Autowired
	private ExtraConfigService extraConfigService;
	@Autowired
	private BwOrderXuDaiService bwOrderXudaiService;
	@Autowired
	private AppPaymentService appPaymentService;
	@Autowired
	private IBwOrderPushInfoService bwOrderPushInfoService;
	// 是否测试
	private boolean testBool = SystemConstant.WITHHOLD_TEST_BOOL;

	/**
	 * 扣款，1.还款涉及本金用宝付，2.续贷判断是否连连签约，连连签约走连连，否则走宝付<br />
	 * 分批还款：1.根据还款金额确定全额一次还款或分批还款，如果已分批还款，则直接分批还款<br />
	 * 2.分批还款不能使用优惠券
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("repayment.do")
	@LockAndSyncRequest
	public AppResponseResult repayment(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		try {
			String borrower_id = request.getParameter("borrower_id");
			String orderId = request.getParameter("orderId");
			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBorrowerId(Long.valueOf(borrower_id));
			Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
			logger.info("【AppPayNewController.repayment】borrowerId：" + borrower_id + "，paramMap=" + paramMap);
			Integer app_request = NumberUtil.parseInteger(request.getParameter("app_request"), null);
			if (app_request == null) {
				result.setCode("101");
				result.setMsg("终端类型不能为空");
				logger.info("orderId" + orderId + ",app_request不能为空");
				return result;
			}
			if (CommUtils.isNull(bwBankCard)) {
				result.setCode("111");
				result.setMsg("您还未签约,请先签约!");
			}
			String type = StringUtil.toString(request.getParameter("type"));// 1正常还款 2续贷
			if (!"1".equals(type) && !"2".equals(type)) {
				result.setCode("102");
				result.setMsg("type异常");
				result.setResult("");
				return result;
			}

			// 判断是否口袋用户，是：调用口袋代扣
			BwOrderPushInfo koudaiPushInfo = bwOrderPushInfoService.getOrderPushInfo(Long.parseLong(orderId), 2);
			if (koudaiPushInfo != null) {// 口袋用户
				RepaymentDto repaymentDto = new RepaymentDto();
				repaymentDto.setOrderId(Long.parseLong(orderId));
				repaymentDto.setTerminalType(app_request);
				String isUseCoupon = request.getParameter("isUseCoupon");
				Double batchMoney = NumberUtil.parseDouble(request.getParameter("batchMoney"), 0.0);
				if ("1".equals(type) && batchMoney <= 0) {
					result.setCode("101");
					result.setMsg("请输入正确的金额");
					return result;
				}
				if ("1".equals(isUseCoupon)) {
					repaymentDto.setUseCoupon(true);
				} else {
					repaymentDto.setUseCoupon(false);
				}
				repaymentDto.setAmount(batchMoney);
				repaymentDto.setType(Integer.parseInt(type));
				result = appPaymentService.updateAndKouDaiWithhold(repaymentDto);
				return result;
			}

			if ("2".equals(type) && StringUtil.toInteger(bwBankCard.getSignStatus()) == 2) {// 续贷并且连连签约用连连支付
				result = signalAndBankPay(request);
			} else {// 还款或签约宝付用宝付
				result = withHold(request);
			}
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("失败");
			result.setResult("");
			logger.error(e, e);
			return result;
		}
		if (StringUtil.isEmpty(result.getResult())) {
			result.setResult("");
		}
		return result;
	}

	// ============================连连start============================//

	/**
	 * 连连授权和卡扣接口
	 * 
	 * @param request
	 * @return
	 */
	public AppResponseResult signalAndBankPay(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		try {
			Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
			String isUseCoupon = request.getParameter("isUseCoupon");// 优惠券是否使用
			String orderId = request.getParameter("orderId");
			String type = request.getParameter("type");// 1正常还款 2续贷
			String app_request = request.getParameter("app_request");// 1-Android 2-ios 3-WAP
			logger.info("===================连连卡扣isUseCoupon:=============================" + isUseCoupon);
			logger.info("==========================连连卡扣=============orderId" + orderId);
			logger.info("========================连连卡扣==========================type:" + type);
			logger.info("===================连连卡扣app_request:=============================" + app_request);
			logger.info("【AppPayNewController.signalAndBankPay】orderId：" + orderId + "，paramMap=" + paramMap);
			if ("2".equals(type)) {// 续贷不使用优惠券
				isUseCoupon = "0";
			}
			if (CommUtils.isNull(orderId)) {
				result.setCode("101");
				result.setMsg("工单id不能为空");
				logger.info("==========================连连卡扣=============工单id不能为空");
				return result;
			}

			String amount = "";
			BwOrder order1 = bwOrderService.findBwOrderById(orderId);
			if (CommUtils.isNull(order1)) {
				result.setCode("103");
				result.setMsg("工单不存在");
				return result;
			}

			// 处理中
			if (RedisUtils.hexists(SystemConstant.WEIXIN_ORDER_ID, orderId)
					|| RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId)
					|| RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId)) {
				result.setCode("104");
				if ("1".equals(type)) {
					result.setMsg("此工单还款正在处理中..");
				} else {
					result.setMsg("此工单展期正在处理中..");
				}
				return result;
			}

			// 续贷次数限制
			// Integer maxTermNum = 0;
			// 已续贷期数
			Integer hasXudaiTerm = bwOrderXudaiService.queryXudaiTerm(Long.parseLong(orderId));
			// 5月13号以后续贷次数
			Integer hasAfterXudaiTerm = bwRepaymentPlanService.getXuDaiCountAfterDate(Long.parseLong(orderId));
			// 获取产品信息
			BwProductDictionary dict = bwProductDictionaryService.findById(Long.valueOf(order1.getProductId()));
			if (type.equals("2")) {// 续贷
				logger.info("================进入连连续贷================orderId：" + orderId + "，已续贷" + hasXudaiTerm
						+ "次，计算续贷次数" + hasAfterXudaiTerm + "次");

				Long statusId = order1.getStatusId();// 工单状态
				logger.info("工单状态==========statusId==================" + statusId);
				if (!(statusId == 9 || statusId == 13)) {// 还款中 逾期
					result.setCode("106");
					result.setMsg("此工单续贷正在处理中..");// 提示信息方便前端显示 让用户去手动刷新界面
					// 实际提示信息(工单只有还款中和逾期中才可还款 )
					return result;
				}

				// TODO Redis保存不能继续续贷用户ID，还款后清除(预留)
				if (RedisUtils.hexists(RedisKeyConstant.NOT_CAN_XUDAI, order1.getBorrowerId().toString())) {
					result.setCode("118");
					result.setMsg("不能续贷");
					return result;
				}

				// 续贷次数限制
				// maxTermNum =
				// productService.selectMaxTermNumById(dict.getId());
				// if (hasXudaiTerm >= maxTermNum) {// 最大续贷次数
				// result.setCode("105");
				// result.setMsg("续贷" + maxTermNum + "次必须结清本金，否则不允许再次续贷!");
				// return result;
				// }

				// 判断是否可以续期
				AppResponseResult canXuDaiResult = productService.canXuDai(Long.parseLong(orderId));
				if (!"000".equals(canXuDaiResult.getCode())) {
					RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId);
					return canXuDaiResult;
				}
			}
			if (type.equals("1")) {// 正常还款
				logger.info("===========================进入连连还款====================================");

				Long statusId = order1.getStatusId();// 工单状态
				logger.info("工单状态==========statusId==================" + statusId);
				if (!(statusId == 9 || statusId == 13)) {// 还款中 逾期
					result.setCode("106");
					result.setMsg("此工单还款正在处理中..");// 提示信息方便前端显示 让用户去手动刷新界面
					// 实际提示信息(工单只有还款中和逾期中才可还款 )
					return result;
				}
			}

			// 查询最近一期还款计划
			BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService
					.getLastRepaymentPlanByOrderId(Long.parseLong(orderId));
			if (CommUtils.isNull(bwRepaymentPlan)) {
				result.setCode("108");
				result.setMsg("还款计划不存在");
				return result;
			}
			Long repayId = bwRepaymentPlan.getId();

			Double repayMoney = bwRepaymentPlan.getRealityRepayMoney();
			Double borrowAmount = order1.getBorrowAmount();
			// 逾期罚息金额
			double overdueAmount = 0.0;
			double noOverdueAmount = 0.0;// 免罚息金额
			double realOverdueAmount = 0.0;// 真实罚息金额
			// 续贷工本费金额
			double xudaiAmount = 0.0;
			LoanInfo loanInfo = new LoanInfo();
			loanInfo.setAvoidFineDate(order1.getAvoidFineDate());
			if (type.equals("1")) {// 正常还款
				// 正常还款金额
				amount = NumberUtil.formatDecimal(
						productService.calcRepaymentCost(repayMoney, Long.parseLong(orderId), repayId, dict, loanInfo));
			} else {// 续贷
				amount = NumberUtil.formatDecimal(productService.calcXudaiCost(borrowAmount, Long.parseLong(orderId),
						repayId, dict, hasAfterXudaiTerm + 1, loanInfo));
			}
			overdueAmount = NumberUtils.toDouble(loanInfo.getOverdueAmount(), 0.0);
			noOverdueAmount = NumberUtils.toDouble(loanInfo.getNoOverdueAmount(), 0.0);
			realOverdueAmount = NumberUtils.toDouble(loanInfo.getRealOverdueAmount(), 0.0);
			xudaiAmount = NumberUtils.toDouble(loanInfo.getServiceAmount(), 0.0);

			if (CommUtils.isNull(amount)) {
				result.setCode("109");
				result.setMsg("金额不能为空");
				logger.info("==========================连连卡扣=============金额不能为空");
				return result;
			}
			// 根据工单id获取用户信息
			BwBorrower borrower = bwBorrowerService.findBwBorrowerByOrderId(Long.valueOf(orderId));
			if (CommUtils.isNull(borrower)) {
				result.setCode("110");
				result.setMsg("该工单对应的用户不存在");
				logger.info("==========================连连卡扣=============该工单对应的用户不存在");
				return result;
			}

			// 根据用户Id获取银行卡信息
			BwBankCard card = bwBankCardService.findBwBankCardByBorrowerId(borrower.getId());
			if (CommUtils.isNull(card)) {
				result.setCode("112");
				result.setMsg("该用户对应的银行卡信息不存在");
				logger.info("==========================连连卡扣=============该用户对应的银行卡信息不存在");
				return result;
			}

			Long borrowerId = borrower.getId();

			// 连连支付签约查询接口
			logger.info(
					"=====================连连签约查询接口开始--参数：borrowerId--" + borrowerId + " ,cardNo--" + card.getCardNo());
			CardQueryResult cardResult = LianLianPayService.cardBindQuery(borrowerId.toString(), card.getCardNo());
			logger.info("================连连签约查询接口返回值：" + new Gson().toJson(cardResult));
			if (CommUtils.isNull(cardResult.getAgreement_list())) {
				result.setCode("113");
				result.setMsg("该用户未签约连连支付");
				logger.info("==========================连连卡扣=============该用户未签约连连支付");
				return result;
			}

			String no_agree = cardResult.getAgreement_list().get(0).getNo_agree();
			logger.info("==========================连连卡扣============用户id:" + borrower.getId() + "的连连支付协议号:" + no_agree);

			// 连连支付单独授权接口
			List<Object> list = new ArrayList<>();
			list.add(1);
			list.add(3);
			Example example = new Example(BwRepaymentPlan.class);
			example.createCriteria().andEqualTo("orderId", orderId).andIn("repayStatus", list);
			List<BwRepaymentPlan> plans = bwRepaymentPlanService.findBwRepaymentPlanByExample(example);
			BwOrder order = bwOrderService.findBwOrderById(orderId);
			List<RepaymentPlan> repays = new ArrayList<>();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
			// 获取 isUseCoupon 是否支付 借贷人划款计划金额和借贷人id 返回借贷人实际支付金额
			logger.info(
					ActivityConstant.MSG_BEFORE + "用户计划还款金额是：" + amount + "借贷人ID：" + borrowerId + "订单ID：" + orderId);

			// 分批还款
			RepaymentBatch repaymentBatch = bwOrderRepaymentBatchDetailService
					.getRepaymentBatch(Long.parseLong(orderId));
			// 分批已还款金额
			Double alreadyTotalBatchMoney = repaymentBatch.getAlreadyTotalBatchMoney();
			if (alreadyTotalBatchMoney > 0.0 && !"1".equals(type)) {// 已分批还款不能续贷
				result.setCode("123");
				result.setMsg("已分批还款不能展期");
				logger.info("工单" + orderId + "已分批还款不能展期");
				return result;
			}
			boolean isFullRepayment = false;// 是否全额一次还款
			String batchMoneyStr = request.getParameter("batchMoney");
			if (StringUtils.isEmpty(batchMoneyStr)) {// 兼容老版本
				if (alreadyTotalBatchMoney == 0.0) {
					isFullRepayment = true;
					batchMoneyStr = amount;// 未分批还款并batchMoney未空，全额一次还款
				} else {// 已分过批，未传batchMoney则直接还剩余金额
					batchMoneyStr = DoubleUtil.sub(NumberUtils.toDouble(amount, 0.0), alreadyTotalBatchMoney) + "";
				}
			} else {
				if (!batchMoneyStr.matches("^\\d+(\\.\\d{1,2})?$")) {
					result.setCode("011");
					result.setMsg("金额格式不对");
					logger.info("【AppPayNewController.signalAndBankPay】orderId：" + orderId + "，金额batchMoney："
							+ batchMoneyStr + "格式不对");
					return result;
				}
			}
			double batchMoney = NumberUtils.toDouble(batchMoneyStr, 0.0);
			if ("1".equals(type)) {
				if (batchMoney <= 0.0) {
					result.setCode("119");
					result.setMsg("分期还款金额必须大于0");
					return result;
				}
				if ("1".equals(type) && NumberUtils.toDouble(amount, 0.0) == batchMoney) {
					isFullRepayment = true;
				}
				if (!isFullRepayment) {// 非全额一次还款不可以使用优惠券
					isUseCoupon = "0";
				}
			}

			// 优惠金额
			double couponAmount = 0.0;
			if ("1".equals(isUseCoupon)) {
				ActivityDiscountDistribute activityDiscountDistribute = activityDiscountDistributeService
						.findMaxActivityDiscountDistribute(order1.getBorrowerId());
				if (activityDiscountDistribute != null) {
					couponAmount = activityDiscountDistribute.getAmount();
				}
			}
			amount = activityService.getRealityTrandeAmount(isUseCoupon, amount, borrowerId);
			String totalTradeAmount = amount;// 总交易金额(分批为分批记录总金额)

			// 是否是最后一次还款
			double leftAmount = 0.0;
			boolean isLastRepayment = true;
			if ("1".equals(type) && !isFullRepayment) {// 还款（分批）
				// 剩余还款金额
				leftAmount = DoubleUtil.sub(NumberUtils.toDouble(amount, 0.0), alreadyTotalBatchMoney);
				if (leftAmount <= 0.0) {
					result.setCode("120");
					result.setMsg("该工单已分批还清");
					logger.info("工单" + orderId + "已分批还清");
					return result;
				}
				// 最少分期还款金额
				String minBatchRepaymentAmountStr = extraConfigService
						.findCountExtraConfigByCode(ParameterConstant.MIN_BATCH_REPAYMENT_AMOUNT);
				double minBatchRepaymentAmount = NumberUtils.toDouble(minBatchRepaymentAmountStr, 0.0);
				if (batchMoney < leftAmount) {
					isLastRepayment = false;
				} else if (batchMoney > leftAmount) {
					result.setCode("121");
					result.setMsg("还款金额不能大于剩余还款金额元");
					logger.info("==========================连连卡扣=============" + result.getMsg());
					return result;
				}
				if (minBatchRepaymentAmount >= leftAmount && batchMoney < leftAmount) {// 剩余还款金额小于最小金额，且还款金额小于剩余还款金额
					result.setCode("122");
					result.setMsg("需一次还完剩下的欠款！");
					logger.info("==========================连连卡扣=============" + result.getMsg());
					return result;
				} else if (!isLastRepayment && batchMoney < minBatchRepaymentAmount) {// 非最后一次还款，且还款金额小于最小还款金额
					result.setCode("122");
					result.setMsg("每次还款金额不能低于" + minBatchRepaymentAmountStr + "元");
					logger.info("==========================连连卡扣=============" + result.getMsg());
					return result;
				}
				amount = String.valueOf(batchMoney);
			}

			if (RedisUtils.hexists(SystemConstant.WEIXIN_ORDER_ID, orderId)
					|| RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId)
					|| RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId)) {
			} else {
				logger.info("===================redis中不存在该工单=====================orderId:" + orderId);
				// 存入redis,有效期10分钟
				RedisUtils.setex(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId, orderId.toString(), 600);
				logger.info("=======存redis======lianlian===================" + orderId.toString());
			}

			// 当前工单续贷次数
			Integer xudaiTimes = hasXudaiTerm;
			if ("2".equals(type)) {// 续贷
				xudaiTimes = xudaiTimes + 1;
			}
			// 逾期记录
			BwOverdueRecord paramOverdueRecord = new BwOverdueRecord();
			paramOverdueRecord.setOrderId(Long.parseLong(orderId));
			paramOverdueRecord.setRepayId(repayId);
			BwOverdueRecord bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(paramOverdueRecord);
			Integer overdueDay = 0;
			if (bwOverdueRecord != null) {
				overdueDay = bwOverdueRecord.getOverdueDay();
			}
			// 银行卡
			BwBankCard bankCard = bwBankCardService.findBwBankCardByBorrowerId(borrowerId);
			Double zjwAmount = bwRepaymentPlan.getZjw();
			// 连连判断还款金额
			if (NumberUtil.isFushu(amount)) {
				BwPlatformRecord bwPlatformRecord = new BwPlatformRecord();
				bwPlatformRecord.setTradeAmount(Double.valueOf(amount));
				if ("1".equals(type)) {// 正常还款，续贷不能使用优惠券
					bwPlatformRecord.setTradeRemark("连连还款未扣款");
				}

				// 优惠券抵扣所有支付金额也保存支付明细，必须先保存明细再执行下面的配发优惠券并还款
				BwPaymentDetail bwPaymentDetail = new BwPaymentDetail(null, Long.parseLong(orderId),
						bwRepaymentPlan.getId(), Integer.parseInt(type), NumberUtils.toDouble(amount, 0.0), xudaiAmount,
						repayMoney, overdueAmount, couponAmount, new Date(), null);
				bwPaymentDetail.setNoOverdueAmount(noOverdueAmount);
				bwPaymentDetail.setRealOverdueAmount(realOverdueAmount);
				bwPaymentDetail.setBorrowerId(borrowerId);
				bwPaymentDetail.setPayChannel(2);
				bwPaymentDetail.setPayStatus(1);
				bwPaymentDetail.setTradeTime(new Date());
				bwPaymentDetail.setOverdueDay(overdueDay);
				bwPaymentDetail.setXudaiTimes(xudaiTimes);
				bwPaymentDetail.setZjwAmount(zjwAmount);
				if (StringUtils.isNotEmpty(app_request) && app_request.matches("\\d+")) {
					bwPaymentDetail.setTerminalType(NumberUtils.toInt(app_request));
				}
				if (bankCard != null) {
					bwPaymentDetail.setCardNo(bankCard.getCardNo());
					bwPaymentDetail.setBankCode(bankCard.getBankCode());
				}
				bwPaymentDetailService.saveOrUpdateByRepayId(bwPaymentDetail);

				// 派发优惠券并还款
				NotifyNotice notice = activityService.addParticipationActivity(order, bwPlatformRecord, true);
				// 往BwOrderStatusRecord表插入记录用于弹窗
				bwOrderStatusRecordService.insertRecord(order1,
						"您于" + DateUtil.getCurrentDateString(DateUtil.YMD) + "已经成功还款" + Double.valueOf(amount) + "元",
						ActivityConstant.BWORDERSTATUSRECORD_DIALOGSTYLE.DIALOGSTYLE_REPAYSUCCESS);

				result.setCode(notice.getRet_code());
				result.setMsg(notice.getRet_msg());
				return result;
			}
			logger.info(
					ActivityConstant.MSG_BEFORE + "用户实际支付的金额是：" + amount + "借贷人ID：" + borrowerId + "订单ID：" + orderId);
			// 设置实际支付金额
			for (BwRepaymentPlan plan : plans) {
				RepaymentPlan repay = new RepaymentPlan();
				repay.setAmount(amount);
				repay.setDate(dateFormat2.format(plan.getRepayTime()));
				repays.add(repay);
			}

			SignalLess signalLess = new SignalLess();
			signalLess.setAcct_name(borrower.getName());
			signalLess.setApp_request(app_request);
			signalLess.setCard_no(card.getCardNo());
			signalLess.setId_no(borrower.getIdCard());
			signalLess.setNo_agree(no_agree);
			signalLess.setUser_id(borrowerId.toString());

			PlanResult planResult = LianLianPayService.sigalAccreditPay(signalLess, order.getOrderNo(), repays);
			logger.info("=======================连连支付单独授权接口返回值：" + new Gson().toJson(planResult));
			if (!"0000".equals(planResult.getRet_code())) {
				result.setCode("114");
				result.setMsg(planResult.getRet_msg());
				return result;
			} else {// 授权成功，去卡扣
				RepayRequest repayRequest = new RepayRequest();
				repayRequest.setNo_order(GenerateSerialNumber.getSerialNumber().substring(8) + repayId);
				repayRequest.setUser_id(borrowerId.toString());
				repayRequest.setNo_agree(no_agree);
				// repayRequest.setDt_order(dateFormat.format(order.getCreateTime()));
				repayRequest.setDt_order(dateFormat.format(new Date()));
				repayRequest.setName_goods("易秒贷");
				// TODO 测试金额，上线前改回来
				if (testBool) {
					repayRequest.setMoney_order("0.01");// 测试金额0.01
				} else {
					repayRequest.setMoney_order(amount);
				}
				repayRequest.setSchedule_repayment_date(repays.get(0).getDate());
				repayRequest.setRepayment_no(order.getOrderNo());
				repayRequest.setUser_info_bind_phone(borrower.getPhone());
				repayRequest.setUser_info_dt_register(dateFormat.format(borrower.getCreateTime()));
				repayRequest.setUser_info_full_name(borrower.getName());
				repayRequest.setUser_info_id_no(borrower.getIdCard());
				if (type.equals("1")) {
					repayRequest.setNotify_url(SystemConstant.NOTIRY_URL + "/pay/new/repayNotify.do");// 正常还款回调
				} else {
					repayRequest.setNotify_url(SystemConstant.NOTIRY_URL + "/pay/new/loanNotify.do");// 续贷回调
				}

				logger.info("==============连连异步回调地址=================：" + repayRequest.getNotify_url());

				if (("1".equals(type) && isFullRepayment) || ("1".equals(type) && isLastRepayment)
						|| "2".equals(type)) {// 全额还款或最后一次分批还款
					// 扣款明细存redis，回调时取并存入BwPaymentDetail，先保存再掉支付接口，防止还没保存回调取不到值，支付失败删除
					BwPaymentDetail bwPaymentDetail = new BwPaymentDetail(null, Long.parseLong(orderId),
							bwRepaymentPlan.getId(), Integer.parseInt(type),
							NumberUtils.toDouble(totalTradeAmount, 0.0), xudaiAmount, repayMoney, overdueAmount,
							couponAmount, new Date(), null);
					bwPaymentDetail.setNoOverdueAmount(noOverdueAmount);
					bwPaymentDetail.setRealOverdueAmount(realOverdueAmount);
					bwPaymentDetail.setBorrowerId(borrowerId);
					bwPaymentDetail.setPayChannel(2);
					bwPaymentDetail.setOverdueDay(overdueDay);
					bwPaymentDetail.setXudaiTimes(xudaiTimes);
					bwPaymentDetail.setZjwAmount(zjwAmount);
					if (bankCard != null) {
						bwPaymentDetail.setCardNo(bankCard.getCardNo());
						bwPaymentDetail.setBankCode(bankCard.getBankCode());
					}
					if (StringUtils.isNotEmpty(app_request) && app_request.matches("\\d+")) {
						bwPaymentDetail.setTerminalType(NumberUtils.toInt(app_request));
					}
					RedisUtils.hset(RedisKeyConstant.PAYMENT_DETAIL, repayId.toString(),
							JSON.toJSONString(bwPaymentDetail));
				}
				// 分批还款添加分批明细到redis
				if ("1".equals(type) && !isFullRepayment) {
					Date nowDate = new Date();
					BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail = new BwOrderRepaymentBatchDetail();
					bwOrderRepaymentBatchDetail.setOrderId(Long.parseLong(orderId));
					bwOrderRepaymentBatchDetail.setAmount(NumberUtils.toDouble(amount));
					bwOrderRepaymentBatchDetail.setNumber(repaymentBatch.getCurrentNumber());
					bwOrderRepaymentBatchDetail.setRepaymentChannel(2);
					bwOrderRepaymentBatchDetail.setOverdueDay(overdueDay);
					bwOrderRepaymentBatchDetail.setOverdueAmount(overdueAmount);
					bwOrderRepaymentBatchDetail.setResidualAmount(DoubleUtil.sub(leftAmount, batchMoney));
					bwOrderRepaymentBatchDetail.setTotalAmount(Double.parseDouble(totalTradeAmount));
					bwOrderRepaymentBatchDetail.setCreateTime(nowDate);
					bwOrderRepaymentBatchDetail.setUpdateTime(nowDate);
					bwOrderRepaymentBatchDetail.setLastRepayment(isLastRepayment);
					if (StringUtils.isNotEmpty(app_request) && app_request.matches("\\d+")) {
						bwOrderRepaymentBatchDetail.setTerminalType(NumberUtils.toInt(app_request));
					}
					RedisUtils.hset(RedisKeyConstant.BATCH_REPAYMENT_DETAIL, String.valueOf(orderId),
							JSON.toJSONString(bwOrderRepaymentBatchDetail));
				}

				RepaymentResult repaymentResult = LianLianPayService.bankRepay(repayRequest);
				logger.info("================================连连卡扣接口返回值：orderId:" + Long.parseLong(orderId) + ",json:"
						+ new Gson().toJson(repaymentResult));
				if (!"0000".equals(repaymentResult.getRet_code())) {
					RedisUtils.hdel(RedisKeyConstant.PAYMENT_DETAIL, repayId.toString());
					RedisUtils.hdel(RedisKeyConstant.BATCH_REPAYMENT_DETAIL, orderId);
					RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId);
					result.setCode("115");
					result.setMsg(repaymentResult.getRet_msg());
					return result;
				} else {
					result.setCode("000");
					result.setMsg("还款成功");
				}
			}
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("服务器后台异常");
			logger.error(e, e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 连连还款回调
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "repayNotify.do")
	public NotifyNotice repayNotify(HttpServletRequest request) {

		logger.info("=====================进入连连还款异步回调开始===========================");
		try {
			String receiveJsonStr = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("==================连连还款回调接收参数=========" + receiveJsonStr);

			NotifyResult notifyResult = JSON.parseObject(receiveJsonStr, NotifyResult.class);

			logger.info("=====================还款异步通知返回工单号===============" + notifyResult.getNo_order());
			logger.info("=====================还款异步通知返回状态码===============" + notifyResult.getResult_pay());
			logger.info("【AppPayNewController.repayNotify】no_order：" + notifyResult.getNo_order() + "，NotifyResult="
					+ JSON.toJSONString(notifyResult));

			NotifyNotice notice = new NotifyNotice();
			// 验签
			if (CommUtils.isNull(notifyResult)) {
				logger.info("==================异步通知为空========================");
				notice.setRet_code("101");
				notice.setRet_msg("异步通知为空");
				return notice;
			} else {
				logger.info("==================异步通知验签开始========================");
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

				if (!testBool) {
					// TODO 连连验签测试不执行
					String osign = RSAUtil.sortParams(map);
					if (!RSAUtil.checksign(SystemConstant.YT_PUB_KEY, osign, notifyResult.getSign())) {// 未通过验签
						logger.info("==================异步通知未通过========================");
						notice.setRet_code("101");
						notice.setRet_msg("验签未通过");
						return notice;
					}
				}

				logger.info("==================异步通知通过========================");
			}

			// 先验证订单状态是否修改，避免重复修改
			if (CommUtils.isNull(notifyResult.getNo_order())) {// 订单号
				logger.info("==================工单id为空========================");
				notice.setRet_code("101");
				notice.setRet_msg("工单id为空");
				return notice;
			}

			String repayId = notifyResult.getNo_order().substring(20);
			BwRepaymentPlan plan = bwRepaymentPlanService.getBwRepaymentPlanByPlanId(Long.parseLong(repayId));
			Long orderId = plan.getOrderId();
			if ("SUCCESS".equals(notifyResult.getResult_pay())) {
				logger.info("==============orderId：" + orderId + "，repayId：" + repayId
						+ "，连连还款回调先保存redis中分批或支付明细到数据库，并不删除redis");
				// 先保存到数据库，不删除redis
				bwOrderRepaymentBatchDetailService.saveBatchDetailAndRepaymentDetailByRedis(orderId,
						Long.parseLong(repayId), false);
			}

			notice = bwRepaymentPlanService.updateForLianlianPaymentNotify(notifyResult);
			logger.info("=====================进入连连还款异步回调结束===========================");
			return notice;
		} catch (Exception e) {
			NotifyNotice notice = new NotifyNotice();
			logger.error("连连还款异步回调异常", e);
			e.printStackTrace();
			notice.setRet_code("103");
			notice.setRet_msg("交易失败");
			return notice;
		}

	}

	/**
	 * 连连续贷回调
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "loanNotify.do")
	public NotifyNotice loanNotify(HttpServletRequest request) {
		logger.info("=====================进入连连续贷异步回调===========================");
		NotifyNotice notice = new NotifyNotice();
		try {
			String receiveJsonStr = IOUtils.toString(request.getInputStream(), "utf-8");
			logger.info("==================连连续贷回调接收参数=========" + receiveJsonStr);

			NotifyResult notifyResult = JSON.parseObject(receiveJsonStr, NotifyResult.class);

			logger.info("=====================续贷异步通知返回工单号===============" + notifyResult.getNo_order());
			logger.info("=====================续贷异步通知返回状态码===============" + notifyResult.getResult_pay());
			logger.info("【AppPayNewController.loanNotify】no_order：" + notifyResult.getNo_order() + "，NotifyResult="
					+ JSON.toJSONString(notifyResult));
			// 验签
			if (CommUtils.isNull(notifyResult)) {
				logger.info("==================异步通知为空========================");
				notice.setRet_code("101");
				notice.setRet_msg("异步通知为空");
				return notice;
			} else {
				logger.info("==================异步通知验签开始========================");
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

				if (!testBool) {
					// TODO 连连验签测试不执行
					if (!RSAUtil.checksign(SystemConstant.YT_PUB_KEY, osign, notifyResult.getSign())) {// 未通过验签
						logger.info("==================异步通知未通过========================");
						notice.setRet_code("101");
						notice.setRet_msg("验签未通过");
						return notice;
					}
				}

				logger.info("==================异步通知通过========================");
			}

			String repayId = notifyResult.getNo_order().substring(20);

			logger.info("【AppPayNewController.loanNotify】repayId:" + repayId);
			notice = bwRepaymentPlanService.updateForLianlianXudaiNotify(notifyResult);
			logger.info("=====================进入连连续贷异步回调结束===========================");
		} catch (Exception e) {
			logger.error("连连续贷回调异常", e);
			notice.setRet_code("103");
			notice.setRet_msg("交易失败");
			return notice;
		}
		return notice;
	}

	// ============================连连end============================//

	// ============================宝付start============================

	/**
	 * 宝付扣款接口
	 * 
	 * @param request
	 * @return
	 */
	public AppResponseResult withHold(HttpServletRequest request) {
		Map<String, String> paramMap = ControllerUtil.getRequestParamMap(request);
		AppResponseResult result = new AppResponseResult();
		String isUseCoupon = request.getParameter("isUseCoupon");// 优惠券是否使用
		String orderId = request.getParameter("orderId");
		String type = request.getParameter("type");// 1正常还款 2续贷
		String app_request = request.getParameter("app_request");// 1-Android 2-ios 3-WAP
		logger.info("【AppPayNewController.withHold】orderId：" + orderId + "，paramMap=" + paramMap);
		try {
			if ("2".equals(type)) {// 续贷不使用优惠券
				isUseCoupon = "0";
			}
			if (CommUtils.isNull(orderId)) {
				result.setCode("101");
				result.setMsg("工单id不能为空");
				return result;
			}
			BwOrder order = bwOrderService.findBwOrderById(orderId);
			if (CommUtils.isNull(order)) {
				result.setCode("103");
				result.setMsg("工单不存在");
				return result;
			}
			// 正在处理
			if (RedisUtils.hexists(SystemConstant.WEIXIN_ORDER_ID, orderId)
					|| RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId)
					|| RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId)) {
				result.setCode("104");
				if ("1".equals(type)) {
					result.setMsg("此工单还款正在处理中..");
				} else {
					result.setMsg("此工单展期正在处理中..");
				}
				return result;
			}

			Long borrowerId = order.getBorrowerId();
			String amount = "";
			String withholdRemark = "正常付款";// 注释
			// 获取产品信息
			BwProductDictionary dict = bwProductDictionaryService.findById(Long.valueOf(order.getProductId()));
			// 续贷次数限制
			// Integer maxTermNum = 0;
			// 已续贷期数
			Integer hasXudaiTerm = bwOrderXudaiService.queryXudaiTerm(Long.parseLong(orderId));
			// 5月13号以后续贷次数
			Integer hasAfterXudaiTerm = bwRepaymentPlanService.getXuDaiCountAfterDate(Long.parseLong(orderId));
			if (type.equals("2")) {// 续贷
				withholdRemark = "续贷付款";
				Long statusId = order.getStatusId();// 工单状态
				logger.info("工单状态==========statusId==================" + statusId + "，orderId：" + orderId + "，已续贷"
						+ hasXudaiTerm + "次，计算续贷次数" + hasAfterXudaiTerm + "次");
				if (!(statusId == 9 || statusId == 13)) {// 还款中 逾期
					result.setCode("106");
					result.setMsg("此工单续贷正在处理中..");// 提示信息方便前端显示 让用户去手动刷新界面
					// 实际提示信息(工单只有还款中和逾期中才可还款 )
					return result;
				}

				// TODO Redis保存不能继续续贷用户ID，还款后清除(预留)
				if (RedisUtils.hexists(RedisKeyConstant.NOT_CAN_XUDAI, order.getBorrowerId().toString())) {
					result.setCode("118");
					result.setMsg("不能续贷");
					return result;
				}

				// 续次数限制
				// maxTermNum =
				// productService.selectMaxTermNumById(dict.getId());
				// if (hasXudaiTerm >= maxTermNum) {// 最大续贷次数
				// result.setCode("105");
				// result.setMsg("续贷" + maxTermNum + "次必须结清本金，否则不允许再次续贷!");
				// return result;
				// }

				// 判断是否可以续期
				AppResponseResult canXuDaiResult = productService.canXuDai(Long.parseLong(orderId));
				if (!"000".equals(canXuDaiResult.getCode())) {
					RedisUtils.hdel(SystemConstant.NOTIFY_BAOFU, orderId);
					return canXuDaiResult;
				}
			}

			if (type.equals("1")) {// 正常还款
				Long statusId = order.getStatusId();// 工单状态
				logger.info("工单状态==========statusId==================" + statusId);
				if (!(statusId == 9 || statusId == 13)) {// 还款中 逾期
					result.setCode("106");
					result.setMsg("此工单还款正在处理中..");// 提示信息方便前端显示 让用户去手动刷新界面
					// 实际提示信息(工单只有还款中和逾期中才可还款 )
					return result;
				}
			}

			// 查询最近一期还款计划
			BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService
					.getLastRepaymentPlanByOrderId(Long.parseLong(orderId));
			if (CommUtils.isNull(bwRepaymentPlan)) {
				result.setCode("108");
				result.setMsg("还款计划不存在");
				return result;
			}
			Long repayId = bwRepaymentPlan.getId();
			Double repayMoney = bwRepaymentPlan.getRealityRepayMoney();// 还款金额
			Double borrowAmount = order.getBorrowAmount();
			double overdueAmount = 0.0;// 逾期金额，实际罚息要减去免罚息金额
			double noOverdueAmount = 0.0;// 免罚息金额
			double realOverdueAmount = 0.0;// 真实罚息金额
			double xudaiAmount = 0.0;// 续贷服务工本费
			LoanInfo loanInfo = new LoanInfo();
			loanInfo.setAvoidFineDate(order.getAvoidFineDate());
			if (type.equals("1")) {// 正常还款
				// 正常还款金额
				amount = NumberUtil.formatDecimal(
						productService.calcRepaymentCost(repayMoney, Long.parseLong(orderId), repayId, dict, loanInfo));
			} else {// 续贷
				amount = NumberUtil.formatDecimal(productService.calcXudaiCost(borrowAmount, Long.parseLong(orderId),
						repayId, dict, hasAfterXudaiTerm + 1, loanInfo));
			}
			overdueAmount = NumberUtils.toDouble(loanInfo.getOverdueAmount(), 0.0);
			noOverdueAmount = NumberUtils.toDouble(loanInfo.getNoOverdueAmount(), 0.0);
			realOverdueAmount = NumberUtils.toDouble(loanInfo.getRealOverdueAmount(), 0.0);
			xudaiAmount = NumberUtils.toDouble(loanInfo.getServiceAmount(), 0.0);

			if (CommUtils.isNull(amount)) {
				result.setCode("109");
				result.setMsg("金额不能为空");
				logger.info("删除redis中的数据开始=====================：" + orderId);
				RedisUtils.hdel(SystemConstant.NOTIFY_BAOFU, orderId);// 删除redis
				logger.info("删除redis中的数据结束=====================：" + orderId);
				logger.info("==========================宝付支付=============金额不能为空");
				return result;
			}
			logger.info(ActivityConstant.MSG_BEFORE + "用户计划还款金额是：" + amount + "借贷人ID：" + order.getBorrowerId() + "订单ID："
					+ orderId);

			// 分批还款
			RepaymentBatch repaymentBatch = bwOrderRepaymentBatchDetailService
					.getRepaymentBatch(Long.parseLong(orderId));
			// 分批已还款金额
			Double alreadyTotalBatchMoney = repaymentBatch.getAlreadyTotalBatchMoney();
			if (alreadyTotalBatchMoney > 0.0 && !"1".equals(type)) {// 已分批还款不能续贷
				result.setCode("123");
				result.setMsg("已分批还款不能展期");
				logger.info("工单" + orderId + "已分批还款不能展期");
				return result;
			}
			boolean isFullRepayment = false;// 是否全额一次还款
			String batchMoneyStr = request.getParameter("batchMoney");
			if (StringUtils.isEmpty(batchMoneyStr)) {// 兼容老版本
				if (alreadyTotalBatchMoney == 0.0) {
					isFullRepayment = true;
					batchMoneyStr = amount;// 未分批还款并batchMoney未空，全额一次还款
				} else {// 已分过批，未传batchMoney则直接还剩余金额
					batchMoneyStr = DoubleUtil.sub(NumberUtils.toDouble(amount, 0.0), alreadyTotalBatchMoney) + "";
				}
			}
			double batchMoney = NumberUtils.toDouble(batchMoneyStr, 0.0);
			if ("1".equals(type)) {
				if (batchMoney <= 0.0) {
					result.setCode("119");
					result.setMsg("分期还款金额必须大于0");
					return result;
				}
				if ("1".equals(type) && NumberUtils.toDouble(amount, 0.0) == batchMoney) {
					isFullRepayment = true;
				}
				if (!isFullRepayment) {// 非全额一次还款不可以使用优惠券
					isUseCoupon = "0";
				}
			}

			// 优惠金额
			double couponAmount = 0.0;
			if ("1".equals(isUseCoupon)) {
				ActivityDiscountDistribute activityDiscountDistribute = activityDiscountDistributeService
						.findMaxActivityDiscountDistribute(order.getBorrowerId());
				if (activityDiscountDistribute != null) {
					couponAmount = activityDiscountDistribute.getAmount();
				}
			}
			amount = activityService.getRealityTrandeAmount(isUseCoupon, amount, order.getBorrowerId());
			String totalTradeAmount = amount;// 总交易金额(分批为分批记录总金额)

			double leftAmount = 0.0;
			// 是否是最后一次还款
			boolean isLastRepayment = true;
			if ("1".equals(type) && !isFullRepayment) {// 还款（分批）
				// 剩余还款金额
				leftAmount = DoubleUtil.sub(NumberUtils.toDouble(amount, 0.0), alreadyTotalBatchMoney);
				if (leftAmount <= 0.0) {
					result.setCode("120");
					result.setMsg("该工单已分批还清");
					logger.info("工单" + orderId + "已分批还清");
					return result;
				}
				// 最少分期还款金额
				String minBatchRepaymentAmountStr = extraConfigService
						.findCountExtraConfigByCode(ParameterConstant.MIN_BATCH_REPAYMENT_AMOUNT);
				double minBatchRepaymentAmount = NumberUtils.toDouble(minBatchRepaymentAmountStr, 0.0);
				if (batchMoney < leftAmount) {
					isLastRepayment = false;
				} else if (batchMoney > leftAmount) {
					result.setCode("121");
					result.setMsg("还款金额不能大于剩余还款金额元");
					logger.info("==========================连连卡扣=============" + result.getMsg());
					return result;
				}
				if (minBatchRepaymentAmount >= leftAmount && batchMoney < leftAmount) {// 剩余还款金额小于最小金额，且还款金额小于剩余还款金额
					result.setCode("122");
					result.setMsg("需一次还完剩下的欠款！");
					logger.info("==========================宝付支付=============" + result.getMsg());
					return result;
				} else if (!isLastRepayment && batchMoney < minBatchRepaymentAmount) {// 非最后一次还款，且还款金额小于最小还款金额
					result.setCode("122");
					result.setMsg("每次还款金额不能低于" + minBatchRepaymentAmountStr + "元");
					logger.info("==========================宝付支付=============" + result.getMsg());
					return result;
				}
				amount = String.valueOf(batchMoney);
			}

			synchronized (this) {
				if (RedisUtils.hexists(SystemConstant.WEIXIN_ORDER_ID, orderId)
						|| RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId)
						|| RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId)) {
				} else {
					RedisUtils.hset(SystemConstant.NOTIFY_BAOFU, orderId.toString(),
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}
			}

			BwOverdueRecord paramOverdueRecord = new BwOverdueRecord();
			paramOverdueRecord.setOrderId(Long.valueOf(orderId));
			paramOverdueRecord.setRepayId(repayId);
			// 逾期记录
			BwOverdueRecord bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(paramOverdueRecord);
			Integer overdueDay = 0;
			if (bwOverdueRecord != null) {
				overdueDay = bwOverdueRecord.getOverdueDay();
			}
			// 当前工单续贷次数
			Integer xudaiTimes = hasXudaiTerm;
			if ("2".equals(type)) {// 续贷
				xudaiTimes = xudaiTimes + 1;
			}
			// 银行卡
			BwBankCard bankCard = bwBankCardService.findBwBankCardByBorrowerId(borrowerId);
			Double zjwAmount = bwRepaymentPlan.getZjw();
			// 宝付判断还款金额
			if (NumberUtil.isFushu(amount)) {
				BwPlatformRecord bwPlatformRecord = new BwPlatformRecord();
				bwPlatformRecord.setTradeAmount(Double.valueOf(amount));

				// 优惠券抵扣所有支付金额也保存支付明细，必须先保存明细再执行下面的配发优惠券并还款
				BwPaymentDetail bwPaymentDetail = new BwPaymentDetail(null, Long.parseLong(orderId),
						bwRepaymentPlan.getId(), Integer.parseInt(type), NumberUtils.toDouble(amount, 0.0), xudaiAmount,
						repayMoney, overdueAmount, couponAmount, new Date(), null);
				bwPaymentDetail.setNoOverdueAmount(noOverdueAmount);
				bwPaymentDetail.setRealOverdueAmount(realOverdueAmount);
				bwPaymentDetail.setBorrowerId(borrowerId);
				bwPaymentDetail.setPayChannel(1);
				bwPaymentDetail.setPayStatus(1);
				bwPaymentDetail.setTradeTime(new Date());
				bwPaymentDetail.setOverdueDay(overdueDay);
				bwPaymentDetail.setXudaiTimes(xudaiTimes);
				bwPaymentDetail.setZjwAmount(zjwAmount);
				if (bankCard != null) {
					bwPaymentDetail.setCardNo(bankCard.getCardNo());
					bwPaymentDetail.setBankCode(bankCard.getBankCode());
				}
				if (StringUtils.isNotEmpty(app_request) && app_request.matches("\\d+")) {
					bwPaymentDetail.setTerminalType(NumberUtils.toInt(app_request));
				}
				bwPaymentDetailService.saveOrUpdateByRepayId(bwPaymentDetail);

				// 派发优惠券并还款
				NotifyNotice notice = activityService.addParticipationActivity(order, bwPlatformRecord, false);
				result.setCode(notice.getRet_code());
				result.setMsg(notice.getRet_msg());
				logger.info("删除redis中的数据开始=====================：" + orderId);
				RedisUtils.hdel(SystemConstant.NOTIFY_BAOFU, orderId);// 删除redis
				logger.info("删除redis中的数据结束=====================：" + orderId);
				return result;
			}

			// 根据工单id获取用户信息
			BwBorrower borrower = bwBorrowerService.findBwBorrowerByOrderId(Long.valueOf(orderId));
			if (CommUtils.isNull(borrower)) {
				result.setCode("110");
				result.setMsg("该工单对应的用户不存在");
				logger.info("删除redis中的数据开始=====================：" + orderId);
				RedisUtils.hdel(SystemConstant.NOTIFY_BAOFU, orderId);// 删除redis
				logger.info("删除redis中的数据结束=====================：" + orderId);
				logger.info("==========================连连卡扣=============该工单对应的用户不存在");
				return result;
			}
			// 根据用户Id获取银行卡信息
			BwBankCard card = bwBankCardService.findBwBankCardByBorrowerId(borrower.getId());
			if (CommUtils.isNull(card)) {
				result.setCode("112");
				result.setMsg("该用户对应的银行卡信息不存在");
				logger.info("删除redis中的数据开始=====================：" + orderId);
				RedisUtils.hdel(SystemConstant.NOTIFY_BAOFU, orderId);// 删除redis
				logger.info("删除redis中的数据结束=====================：" + orderId);
				logger.info("==========================连连卡扣=============该用户对应的银行卡信息不存在");
				return result;
			}

			String name = borrower.getName();
			String idCard = borrower.getIdCard();
			String phone = borrower.getPhone();
			String cardNo = card.getCardNo();
			String bankCode = card.getBankCode();
			logger.info("========================宝付付款入参orderId:" + orderId + "====持卡人姓名=========" + name);
			logger.info("========================宝付付款入参:" + orderId + "====身份证号=============" + idCard);
			logger.info("========================宝付付款入参:" + orderId + "====银行预留手机号=============" + phone);
			logger.info("========================宝付付款入参:" + orderId + "====卡号=============" + cardNo);
			logger.info("========================宝付付款入参:" + orderId + "====银行编码=============" + bankCode);
			logger.info("========================宝付付款入参:" + orderId + "====扣款金额=============" + amount);
			logger.info(withholdRemark + "宝付扣款开始...");
			String mchnt_txn_ssn = "";
			BwRepaymentPlan plan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(Long.parseLong(orderId));
			if ("1".equals(type)) {// 还款
				mchnt_txn_ssn = GenerateSerialNumber.getSerialNumber().substring(8) + "A" + plan.getId();
			} else {
				mchnt_txn_ssn = GenerateSerialNumber.getSerialNumber().substring(8) + "B" + plan.getId();
			}

			logger.info("========================宝付付款入参====生成序号=============" + mchnt_txn_ssn);
			logger.info(withholdRemark + "宝付扣款流水号：" + mchnt_txn_ssn);
			WithLessRequest withLess = new WithLessRequest();
			withLess.setAcc_no(cardNo);// 卡号
			withLess.setId_card(idCard);// 身份证号
			withLess.setId_holder(name);// 持卡人姓名
			withLess.setMobile(phone);// 银行预留手机号
			String bank_code = BaofuConstant.convertFuiouBankCodeToBaofu(bankCode);
			logger.info(withholdRemark + "银行编码：" + bankCode + "，对应宝付银行编码：" + bank_code);
			withLess.setPay_code(bank_code);// 银行编码
			// withLess.setTxn_amt("1");// 测试交易金额,分
			withLess.setTxn_amt(amount);// 交易金额,分
			withLess.setRepayId(mchnt_txn_ssn);// 还款计划id
			withLess.setOrderNo(order.getOrderNo());

			logger.info("是否全额还款isFullRepayment：" + isFullRepayment);
			if (("1".equals(type) && isFullRepayment) || ("1".equals(type) && isLastRepayment) || "2".equals(type)) {// 全额还款或最后一次分批还款
				// 扣款明细存redis，回调时取并存入BwPaymentDetail，先保存再掉支付接口，防止还没保存回调取不到值，支付失败删除
				BwPaymentDetail bwPaymentDetail = new BwPaymentDetail(null, Long.parseLong(orderId),
						bwRepaymentPlan.getId(), Integer.parseInt(type), NumberUtils.toDouble(totalTradeAmount, 0.0),
						xudaiAmount, repayMoney, overdueAmount, couponAmount, new Date(), null);
				bwPaymentDetail.setNoOverdueAmount(noOverdueAmount);
				bwPaymentDetail.setRealOverdueAmount(realOverdueAmount);
				bwPaymentDetail.setBorrowerId(borrowerId);
				bwPaymentDetail.setPayChannel(1);
				bwPaymentDetail.setOverdueDay(overdueDay);
				bwPaymentDetail.setXudaiTimes(xudaiTimes);
				bwPaymentDetail.setZjwAmount(zjwAmount);
				if (bankCard != null) {
					bwPaymentDetail.setCardNo(bankCard.getCardNo());
					bwPaymentDetail.setBankCode(bankCard.getBankCode());
				}
				if (StringUtils.isNotEmpty(app_request) && app_request.matches("\\d+")) {
					bwPaymentDetail.setTerminalType(NumberUtils.toInt(app_request));
				}
				logger.info("AppPayNewController工单Id：" + orderId + "，还款计划ID：" + plan.getId() + ",bwPaymentDetail:"
						+ JSON.toJSONString(bwPaymentDetail));
				Long hsetResult = RedisUtils.hset(RedisKeyConstant.PAYMENT_DETAIL, repayId.toString(),
						JSON.toJSONString(bwPaymentDetail));
				if (hsetResult == null) {
					logger.info("AppPayNewController工单Id:" + orderId + "，还款计划ID:" + plan.getId() + "保存支付明细失败");
					result.setCode("111");
					result.setMsg("还款失败");
					return result;
				}
			}
			// 分批还款添加分批明细到redis
			if ("1".equals(type) && !isFullRepayment) {
				Date nowDate = new Date();
				BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail = new BwOrderRepaymentBatchDetail();
				bwOrderRepaymentBatchDetail.setOrderId(Long.parseLong(orderId));
				bwOrderRepaymentBatchDetail.setAmount(NumberUtils.toDouble(amount));
				bwOrderRepaymentBatchDetail.setNumber(repaymentBatch.getCurrentNumber());
				bwOrderRepaymentBatchDetail.setRepaymentChannel(1);
				bwOrderRepaymentBatchDetail.setOverdueDay(overdueDay);
				bwOrderRepaymentBatchDetail.setOverdueAmount(overdueAmount);
				bwOrderRepaymentBatchDetail.setResidualAmount(DoubleUtil.sub(leftAmount, batchMoney));
				bwOrderRepaymentBatchDetail.setTotalAmount(Double.parseDouble(totalTradeAmount));
				bwOrderRepaymentBatchDetail.setCreateTime(nowDate);
				bwOrderRepaymentBatchDetail.setUpdateTime(nowDate);
				bwOrderRepaymentBatchDetail.setLastRepayment(isLastRepayment);
				if (StringUtils.isNotEmpty(app_request) && app_request.matches("\\d+")) {
					bwOrderRepaymentBatchDetail.setTerminalType(NumberUtils.toInt(app_request));
				}
				logger.info("AppPayNewController工单Id：" + orderId + "，还款计划ID：" + plan.getId()
						+ ",bwOrderRepaymentBatchDetail:" + JSON.toJSONString(bwOrderRepaymentBatchDetail));
				Long hsetResult = RedisUtils.hset(RedisKeyConstant.BATCH_REPAYMENT_DETAIL, orderId,
						JSON.toJSONString(bwOrderRepaymentBatchDetail));
				if (hsetResult == null) {
					logger.info("AppPayNewController工单Id:" + orderId + "，还款计划ID:" + plan.getId() + "保存分批信息失败");
					result.setCode("111");
					result.setMsg("还款失败");
					RedisUtils.hdel(RedisKeyConstant.PAYMENT_DETAIL, repayId.toString());
					return result;
				}
			}

			String payDetailStr = RedisUtils.hget(RedisKeyConstant.PAYMENT_DETAIL, repayId.toString());
			String paymentDetailStr = RedisUtils.hget(RedisKeyConstant.BATCH_REPAYMENT_DETAIL, orderId.toString());
			logger.info("AppPayNewController工单Id：" + orderId + "，还款计划ID：" + plan.getId() + ",payDetailStr:"
					+ StringUtil.toString(payDetailStr) + ",paymentDetailStr：" + StringUtil.toString(paymentDetailStr));
			if (null == payDetailStr && null == paymentDetailStr) {
				logger.info("AppPayNewController工单Id：" + orderId + "，还款计划ID：" + plan.getId() + "支付明细和分批还款明细同时为空");
				result.setCode("111");
				result.setMsg("还款失败");
				return result;
			}

			WithHodingResult res = null;
			if (testBool) {// TODO 测试
				res = new WithHodingResult();
				res.setResp_code("0000");
				res.setResp_msg("测试通过");
			} else {
				res = BaoFuService.withHold(withLess);
			}

			logger.info("=================宝付代扣返回值=============：" + new Gson().toJson(res));
			withholdRemark += "orderId:" + orderId;
			String respCode = res.getResp_code();
			// 测试时宝付成功
			if (testBool) {
				respCode = "0000";
			}
			if ("0000".equals(respCode)) {
				logger.info(withholdRemark + "宝付扣款成功");
				result.setCode("000");
				result.setMsg("扣款成功");
				if (testBool) {// 测试模拟回调
					appPaymentService.requestBaofooNotifyTest(Long.parseLong(orderId), mchnt_txn_ssn,
							Double.parseDouble(amount));
				}
				return result;
			}

			// 处理中
			if (!CommUtils.isNull(respCode) && SystemConstant.baofuCode.contains(respCode)) {
				result.setCode("000");
				logger.info(withholdRemark + "宝付处理中，返回消息：[" + respCode + "]" + res.getResp_msg());
				return result;
			}

			// 宝付扣款失败
			logger.error(withholdRemark + "宝付扣款失败，返回消息：[" + respCode + "]" + res.getResp_msg());
			logger.info("宝付扣款失败---删除redis中的数据开始=====================：" + orderId);
			RedisUtils.hdel(SystemConstant.NOTIFY_BAOFU, orderId.toString());// 删除redis
			RedisUtils.hdel(RedisKeyConstant.PAYMENT_DETAIL, repayId.toString());// 删除redis支付明细
			RedisUtils.hdel(RedisKeyConstant.BATCH_REPAYMENT_DETAIL, orderId);
			logger.info("宝付扣款失败---删除redis中的数据结束=====================：" + orderId);
			result.setCode("122");
			result.setMsg(res.getResp_msg());
			return result;
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("系统异常,请稍后重试");
			logger.error(e, e);
			e.printStackTrace();
			return result;
		}

	}

	// ============================宝付end============================//

	/**
	 * 查询分批还款信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBatchInfo.do")
	public AppResponseResult getBatchInfo(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			// 工单Id
			String orderId = request.getParameter("orderId");
			logger.info("【AppPayNewController.getBatchInfo】orderId：" + orderId);
			logger.info("接收到的参数:orderId=" + orderId);
			if (CommUtils.isNull(orderId)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg("orderId不能为空");
				result.setResult(false);// 处理失败
				logger.info("orderId不能为空");
				return result;
			}
			Object object = bwOrderRepaymentBatchDetailService.getBatchInfo(Long.parseLong(orderId));
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
			result.setResult(object);
			logger.info("操作成功");
			return result;
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg("分批还款查询失败");
			result.setResult(false);// 处理失败
			logger.error("分批还款查询失败", e);
			return result;
		}
	}

	@ResponseBody
	@RequestMapping("/appCheckLogin/canZhanqi.do")
	public AppResponseResult canZhanqi(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			Long orderId = NumberUtil.parseLong(request.getParameter("orderId"), null);
			if (orderId == null) {
				result.setCode("101");
				result.setMsg("工单ID不能为空");
				result.setResult(false);
				return result;
			}
			result = productService.calcZhanQiCost(orderId);
			if ("000".equals(result.getCode())) {
				result.setResult(true);
			} else {
				result.setResult(false);
			}
			return result;
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg("分批还款查询失败");
			result.setResult(false);// 处理失败
			logger.error("分批还款查询失败", e);
			return result;
		}
	}

	@ResponseBody
	@RequestMapping("/appCheckLogin/canRepayment.do")
	public AppResponseResult canRepayment(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			Long orderId = NumberUtil.parseLong(request.getParameter("orderId"), null);
			Double batchMoney = NumberUtil.parseDouble(request.getParameter("batchMoney"), null);
			if (orderId == null) {
				result.setCode("101");
				result.setMsg("工单ID不能为空");
				result.setResult(false);
				return result;
			}
			if (batchMoney == null) {
				result.setCode("102");
				result.setMsg("还款金额不能为空");
				result.setResult(false);
				return result;
			}
			result = productService.calcRepaymentCost(orderId, true, batchMoney);
			if ("000".equals(result.getCode())) {
				result.setResult(true);
			} else {
				result.setResult(false);
			}
			return result;
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg("分批还款查询失败");
			result.setResult(false);// 处理失败
			logger.error("分批还款查询失败", e);
			return result;
		}
	}
}