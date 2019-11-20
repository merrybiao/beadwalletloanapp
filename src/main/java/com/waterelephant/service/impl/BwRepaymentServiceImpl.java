package com.waterelephant.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.entity.baofu.WithHodingResult;
import com.beadwallet.entity.baofu.WithLessRequest;
import com.beadwallet.servcie.BaoFuService;
import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.rong360.entity.request.OrderFeedBackReq;
import com.beadwallet.service.rong360.entity.response.OrderFeedBackResp;
import com.beadwallet.service.rong360.service.BeadWalletRong360Service;
import com.beadwallet.service.serve.BeadWalletHaoDaiService;
import com.beadwallet.service.serve.BeadWalletYqhService;
import com.beadwallet.service.sms.dto.MessageDto;
import com.beadwallet.service.utils.HttpRequest;
import com.beadwallet.utils.CommUtils;
import com.beadwallet.utils.PayUtil;
import com.waterelephant.activity.service.ActivityService;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.*;
import com.waterelephant.dto.LoanInfo;
import com.waterelephant.dto.RepaymentBatch;
import com.waterelephant.dto.RepaymentDto;
import com.waterelephant.dto.pay.OrderWithholdRequest;
import com.waterelephant.dto.pay.OrderWithholdResponse;
import com.waterelephant.entity.*;
import com.waterelephant.installment.service.AppPaymentService;
import com.waterelephant.mapper.BwCapitalWithholdDetailMapper;
import com.waterelephant.service.*;
import com.waterelephant.utils.*;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liven on 2017/1/17.
 */
@Service
public class BwRepaymentServiceImpl extends BaseService<BwAheadRepay, Long> implements IBwRepaymentService {
	private Logger logger = Logger.getLogger(BwRepaymentServiceImpl.class);

	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private BwPlatformRecordService bwPlatformRecordService;
	@Autowired
	private IBwOrderZhanqiInfoService bwOrderZhanqiInfoService;
	@Autowired
	private BwPaymentDetailService bwPaymentDetailService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private IBwOrderXuDaiService bwOrderXudaiService;
	@Autowired
	private ProductService productService;
	@Autowired
	private BwProductDictionaryService bwProductDictionaryService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private BwOrderRepaymentBatchDetailService bwOrderRepaymentBatchDetailService;
	@Autowired
	private BwOrderProcessRecordService bwOrderProcessRecordService;
	@Autowired
	private IBwOrderPushInfoService bwOrderPushInfoService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private BwOrderStatusRecordService bwOrderStatusRecordService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private ExtraConfigService extraConfigService;
	@Autowired
	private AppPaymentService appPaymentService;
	@Autowired
	private BwCapitalWithholdService bwCapitalWithholdService;
	@Autowired
	private BwCollectionUserService bwCollectionUserService;
	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;
	@Autowired
	private SysDeptUserService sysDeptUserService;
	@Autowired
	private BwPaymentRecordService bwPaymentRecordService;
	@Autowired
	private BwCapitalWithholdDetailMapper bwCapitalWithholdDetailMapper;

	@Override
	public void updateOrderStatus(Long orderId) throws Exception {
		updateOrderStatusCommon(orderId);
		// updateBwOrderXuiDai(orderId);
		// repayNotifyYqh(orderId, 1);
		// 通知一起好
		// repayNotifyYqh(orderId, 1);
		// 判断是否提前还款
		int value = checkNormalRepayment(orderId);
		if (value >= 0) {
			// 正常还款
			// repayOrZhanqiYqh(orderId, 20);
			logger.info("===============一起好存入redis开始==============：" + orderId);
			RedisUtils.lpush(SystemConstant.YIQIHAO_ZHANQI, orderId + "-" + 20);
			logger.info("===============一起好存入redis结束==============：" + orderId);
		} else {
			// 提前还款
			// repayOrZhanqiYqh(orderId, 15);
			logger.info("===============一起好存入redis开始==============：" + orderId);
			RedisUtils.lpush(SystemConstant.YIQIHAO_ZHANQI, orderId + "-" + 15);
			logger.info("===============一起好存入redis结束==============：" + orderId);
		}
	}

	private void updateOrderStatusCommon(Long orderId) throws Exception {
		// 修改工单状态
		String sql = "update bw_order set status_id = 6,update_time = now() where id = " + orderId;

		// 修改还款计划状态
		BwRepaymentPlan plan = bwRepaymentPlanService.getPlan(orderId, 1);
		plan.getRepayTime();
		plan.setUpdateTime(new Date());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date repayTime = dateFormat.parse(dateFormat.format(plan.getRepayTime()));
		Date now = dateFormat.parse(dateFormat.format(new Date()));
		if (now.after(repayTime)) {
			plan.setRepayType(2);// 逾期还款
		} else if (now.before(repayTime)) {
			plan.setRepayType(3);// 提前还款
		} else {
			plan.setRepayType(1);// 正常还款
		}
		plan.setRepayStatus(2);// 已还款
		bwRepaymentPlanService.update(plan);

		this.sqlMapper.update(sql);

		// 更新还款时间
		BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord().setOrderId(orderId)
				.setEndTime(new Date());
		bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
	}

	private void updateBwOrderXuiDai(Long orderId) {
		// 修改续贷状态
		String sql = "update bw_order_xudai set settle_status = 1,update_time = now() where order_id = " + orderId;
		this.sqlMapper.update(sql);
	}

	private int checkNormalRepayment(Long orderId) {
		String sql = "select (to_days(now())-to_days(p.repay_time))day from bw_repayment_plan p where p.order_id = "
				+ orderId + " order by p.repay_time desc limit 1";
		return sqlMapper.selectOne(sql, Integer.class);
	}

	/**
	 * 一起好还款通知
	 * 
	 * @param orderId 工单Id
	 * @param repayType 还款类型。1：还款，2：续贷
	 */
	private void repayNotifyYqh(Long orderId, int repayType) {
		try {
			BwOrder order = bwOrderService.findBwOrderById(orderId.toString());
			if (repayType == 1) {
				BwRepaymentPlan plan = bwRepaymentPlanService.getPlan(orderId, 1);
				int repayTypeYqh = 12;// 默认为收到本金还款
				if (plan.getRepayType().intValue() == 3) {
					repayTypeYqh = 20;// 提前还款
				}
				logger.info("发送还款通知给一起好...");
				BeadWalletYqhService.reapyNotify(order.getOrderNo(), repayTypeYqh);
			} else {
				logger.info("发送还款通知给一起好...");
				BeadWalletYqhService.reapyNotify(order.getOrderNo(), 5);
			}
		} catch (Exception e) {
			logger.error("发送还款通知给一起好发生错误：");
			logger.error(e.getMessage(), e);
		}
	}

	private void repayOrZhanqiYqh(Long orderId, int repayType) {// 5:展期 20:正常还款 15:提前还款
		try {
			String orderNo = getOrignalOrderNo(orderId);
			Response<Object> response = null;
			int i = 0;
			do {
				response = BeadWalletYqhService.createZhanqi(orderNo, repayType);
				BwOrderZhanqiInfo bwOrderZhanqiInfo = new BwOrderZhanqiInfo();
				bwOrderZhanqiInfo.setOrderNo(orderNo);
				bwOrderZhanqiInfo.setCreateTime(new Date());
				if (response.getRequestCode().equals("000")) {
					// 展期成功
					bwOrderZhanqiInfo.setZhanqiStatus(1);
					if (repayType == 5) {
						bwOrderZhanqiInfo.setType(2);// 展期
						bwOrderZhanqiInfo.setZhanqiRemark("展期成功");
					}
					if (repayType == 20) {
						bwOrderZhanqiInfo.setType(1);// 正常还款
						bwOrderZhanqiInfo.setZhanqiRemark("还款成功");
					}
					if (repayType == 15) {
						bwOrderZhanqiInfo.setType(3);// 提前还款
						bwOrderZhanqiInfo.setZhanqiRemark("还款成功");
					}

				} else {
					// 展期或还款失败
					if (repayType == 5) {
						bwOrderZhanqiInfo.setType(2);// 展期
					}
					if (repayType == 20) {
						bwOrderZhanqiInfo.setType(1);// 正常还款
					}
					if (repayType == 15) {
						bwOrderZhanqiInfo.setType(3);// 提前还款
					}
					bwOrderZhanqiInfo.setZhanqiStatus(0);
					bwOrderZhanqiInfo.setZhanqiRemark(response.getRequestMsg());
				}
				try {
					bwOrderZhanqiInfoService.saveOrderZhanqiInfo(bwOrderZhanqiInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				i++;
			} while (!response.getRequestCode().equals("000") && i < 3);
		} catch (Exception e) {
			logger.error(e, e);
			e.printStackTrace();
		}
	}

	private String getOrignalOrderNo(Long orderId) {
		String sql = "select o.order_no from bw_order o where o.id = ( select case when (select count(1) from bw_order_xudai bx "
				+ " where bx.order_id = " + orderId + " and bx.original_order_id is not null)>0  then"
				+ " (select bo.original_order_id from bw_order_xudai bo where bo.order_id = " + orderId + ") else "
				+ orderId + " end )";
		return sqlMapper.selectOne(sql, String.class);
	}

	// Long orderId, String tradeNo, Integer terminalType,
	// double tradeMoney, Integer payType, Integer payChannel, Date tradeTime
	@Override
	public AppResponseResult updateOrderByTradeMoney(RepaymentDto repaymentDto) throws Exception {
		Long orderId = repaymentDto.getOrderId();
		Double tradeMoney = repaymentDto.getAmount();
		String tradeNo = repaymentDto.getTradeNo();
		Integer payType = repaymentDto.getType();// 1.还款 2.展期
		Integer payChannel = repaymentDto.getPayChannel();
		logger.info("【BwRepaymentServiceImpl.updateOrderByPayMoney】开始orderId:" + orderId + ",repaymentDto="
				+ JSON.toJSONString(repaymentDto));
		AppResponseResult result = new AppResponseResult();
		if (orderId == null || payType == null || (payType == 1 && tradeMoney <= 0.0) || payChannel == null) {
			result.setCode("101");
			result.setMsg("参数错误");
			return result;
		}
		BwOrder bwOrder = bwOrderService.findBwOrderById(orderId.toString());
		if (bwOrder == null) {
			result.setCode("102");
			result.setMsg("没有此工单");
			return result;
		}

		// 重复回调
		if (StringUtils.isNotEmpty(tradeNo)) {
			BwPlatformRecord entity = new BwPlatformRecord();
			entity.setOrderId(orderId);
			entity.setTradeNo(tradeNo);
			// 记录流水
			int count = bwPlatformRecordService.getBwPlatformRecordCount(entity);
			if (count > 0) {
				result.setCode("000");
				result.setMsg("SUCCESS");
				return result;
			}
		}

		Long borrowerId = bwOrder.getBorrowerId();
		BwBorrower borrower = bwBorrowerService.findBwBorrowerById(borrowerId);
		Long statusId = bwOrder.getStatusId();
		Integer productType = bwOrder.getProductType();// 1.单期 2.分期
		BwBankCard bankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
		// 记录流水
		BwPlatformRecord bwPlatformRecord = savePlatformRecord(bwOrder, borrower, bankCard, repaymentDto);

		if (statusId != 9 && statusId != 13) {
			result.setCode("103");
			result.setMsg("该工单不是还款状态");
			return result;
		}
		if (productType != null && productType == 2 && payType == 2) {
			result.setCode("104");
			result.setMsg("分期不能展期");
			return result;
		}
		BwProductDictionary product = bwProductDictionaryService.findBwProductDictionaryById(bwOrder.getProductId());
		RepaymentDto redisPayDto = queryRedisPayInfo(orderId);
		if (redisPayDto != null && repaymentDto.getTerminalType() == null) {
			repaymentDto.setTerminalType(redisPayDto.getTerminalType());
		}

		logger.info("【BwRepaymentServiceImpl.updateOrderByPayMoney】还款或展期orderId:" + orderId + ",payType=" + payType);
		if (payType == 1) {// 还款
			updateRepaymentNotify(bwOrder, borrower, product, repaymentDto, bwPlatformRecord);
			repaymentOkNotifyThree(orderId);
		} else if (payType == 2) {// 展期
			updateZhanqiNotify(bwOrder, borrower, product, repaymentDto, bwPlatformRecord);
		}
		result.setCode("000");
		result.setMsg("SUCCESS");
		return result;
	}

	/**
	 * @throws Exception
	 * 
	 */
	private void updateRepaymentNotify(BwOrder bwOrder, BwBorrower borrower, BwProductDictionary product,
			RepaymentDto repaymentDto, BwPlatformRecord bwPlatformRecord) throws Exception {
		Long orderId = bwOrder.getId();
		Integer productType = bwOrder.getProductType();
		Double tradeMoney = repaymentDto.getAmount();
		// 是否使用优惠券
		Boolean useCoupon = repaymentDto.getUseCoupon();
		logger.info("【BwRepaymentServiceImpl.updateRepaymentNotify】还款orderId:" + orderId);

		List<BwRepaymentPlan> allPlanList = bwRepaymentPlanService.selectNotZhanqiPlan(orderId, productType);
		if (allPlanList == null || allPlanList.isEmpty()) {
			return;
		}

		RepaymentBatch repaymentBatch = bwOrderRepaymentBatchDetailService.getRepaymentBatch(orderId);
		Double alreadyTotalBatchMoney = repaymentBatch.getAlreadyTotalBatchMoney();
		if (alreadyTotalBatchMoney == null) {
			alreadyTotalBatchMoney = 0.0;
		}
		if (useCoupon == null || alreadyTotalBatchMoney > 0.0 || productType == null || productType != 1) {
			useCoupon = false;
		}
		logger.info("【BwRepaymentServiceImpl.updateRepaymentNotify】是否使用优惠券orderId:" + orderId + ",useCoupon:"
				+ useCoupon + ",alreadyTotalBatchMoney:" + alreadyTotalBatchMoney);
		repaymentDto.setUseCoupon(useCoupon);

		// 计算还款总金额
		Map<Long, LoanInfo> loanInfoMap = new HashMap<Long, LoanInfo>();
		// 未还款还款计划，根据还款时间升序排序
		List<BwRepaymentPlan> notRepayPlanList = new ArrayList<BwRepaymentPlan>();
		Double totalRepayMoney = bwOrder.getBorrowAmount();// 总共还款金额，不算利息
		double totalAmount = 0.0;// 总共应还金额
		for (BwRepaymentPlan plan : allPlanList) {
			LoanInfo loanInfo = new LoanInfo();
			loanInfo.setAvoidFineDate(bwOrder.getAvoidFineDate());
			loanInfo.setAmt(totalRepayMoney.toString());
			Double realityRepayMoney = plan.getRealityRepayMoney();
			Long repayId = plan.getId();
			Integer repayStatus = plan.getRepayStatus();
			Double calcRepaymentCost = productService.calcRepaymentCost(realityRepayMoney, orderId, repayId, product,
					loanInfo);
			totalAmount = DoubleUtil.add(totalAmount, calcRepaymentCost);// 总共还款金额
			loanInfoMap.put(repayId, loanInfo);
			if (repayStatus == 1 || repayStatus == 3) {// 未还款
				notRepayPlanList.add(plan);
			}
		}
		if (notRepayPlanList.isEmpty()) {// 没有未还款的还款计划后面不执行
			logger.info("【BwRepaymentServiceImpl.updateRepaymentNotify】没有未还款的还款计划orderId:" + orderId);
			return;
		}

		// 总剩余还款金额
		double totalLeftAmount = DoubleUtil.sub(totalAmount, alreadyTotalBatchMoney);
		// 使用优惠券后金额
		double totalLeftUseCouponAmount = totalLeftAmount;
		if (useCoupon) {// 使用优惠券
			totalLeftUseCouponAmount = NumberUtil.parseDouble(
					activityService.getRealityTrandeAmount("1", totalLeftUseCouponAmount + "", bwOrder.getBorrowerId()),
					totalAmount);
		} else {
			useCoupon = false;
			totalLeftUseCouponAmount = totalLeftAmount;
		}

		// 剩余还款金额
		double leftBatchMoney = tradeMoney;

		boolean isBatch = true;
		if (alreadyTotalBatchMoney <= 0.0 && tradeMoney >= totalLeftUseCouponAmount) {// 未分批并一次还款
			isBatch = false;
			if (useCoupon) {
				activityService.updateAndUseCoupon(bwOrder, allPlanList.get(0).getRealityRepayMoney() + "");
			}
		}

		// 保存还款分批信息
		BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail = saveOrUpdateRepaymentBatchDetail(bwOrder,
				notRepayPlanList, loanInfoMap, repaymentBatch, totalAmount, repaymentDto, totalLeftUseCouponAmount);

		// 已还清的还款计划保存支付明细，并更新还款计划状态
		Map<String, String> resultMap = updatePlanAndRecordPayInfo(bwOrder, notRepayPlanList, loanInfoMap,
				bwPlatformRecord, bwOrderRepaymentBatchDetail, leftBatchMoney, repaymentDto, totalLeftUseCouponAmount);
		String paymentRecordIds = resultMap.get("paymentRecordIds");

		// 工单是否已结束
		Boolean lastRepayment = bwOrderRepaymentBatchDetail.getLastRepayment();
		logger.info("【BwRepaymentServiceImpl.updateRepaymentNotify】orderId:" + orderId + ",lastRepayment:"
				+ lastRepayment + ",totalLeftAmount:" + totalLeftAmount + ",totalLeftUseCouponAmount:"
				+ totalLeftUseCouponAmount + "useCoupon:" + useCoupon);
		if (lastRepayment) {
			if (productType != null && productType == 1) {// 单期和还款计划一起更新
				// updateOrderStatusEndAndYiqihao(orderId);
			} else {
				updateOrderStatusEnd(orderId);
			}

			// 结清，保存redis定时修改用户状态
			RedisUtils.rpush(RedisKeyConstant.USER_TYPE_CHANGE_KEY, "" + orderId);
		} else if (productType != null && productType == 2) {// 工单处于还款或逾期中，还款计划有一个在逾期中则修改工单未逾期中状态，否则还款中状态
			String nowTime = CommUtils.convertDateToString(new Date(), "yyyy-MM-dd") + " 00:00:00";
			String sql = "SELECT count(*) FROM bw_repayment_plan " + "WHERE order_id=" + orderId
					+ " and repay_status in (1,3) " + "AND repay_time < '" + nowTime + "'";
			// 未还款并逾期中还款计划
			Integer notRepayPlanCount = sqlMapper.selectOne(sql, Integer.class);
			int updateStatusId = 0;
			if (notRepayPlanCount > 0) {
				updateStatusId = 13;
			} else {
				updateStatusId = 9;
			}
			sqlMapper.update("update bw_order set status_id=" + updateStatusId + " where id=" + orderId);
			logger.info("【BwRepaymentServiceImpl.updateRepaymentNotify】现金分期回调orderId：" + orderId + "，更新工单状态为"
					+ updateStatusId);
		}

		if (StringUtils.isNotEmpty(paymentRecordIds)) {
			sqlMapper.update("update bw_payment_record set status_id=(select status_id from bw_order where id="
					+ orderId + ") where id in (" + paymentRecordIds + ")");
			logger.info("【BwRepaymentServiceImpl.updateRepaymentNotify】orderId:" + orderId + "更新" + paymentRecordIds
					+ "拆分付款记录状态");
		}

		// 弹窗、发送短信
		// addOrderStatusRecord(isBatch, bwOrder, tradeMoney, DoubleUtil.sub(totalLeftUseCouponAmount, tradeMoney));
		if (!lastRepayment) {// 主动还款
			sendMessage(borrower, tradeMoney, bwOrder);
		}
	}

	@Override
	public boolean isProcessing(Long orderId) {
		if (orderId == null) {
			return false;
		}
		// 处理中
		if (RedisUtils.hexists(SystemConstant.WEIXIN_ORDER_ID, orderId.toString())
				|| RedisUtils.hexists(SystemConstant.NOTIFY_BAOFU, orderId.toString())
				|| RedisUtils.exists(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId.toString())) {
			return true;
		}
		return false;
	}

	@Override
	public AppResponseResult updateAndPaymentThirdByOrderId(Long orderId) {
		AppResponseResult result = null;
		try {
			result = productService.calcRepaymentCost(orderId);
			if (!"000".equals(result.getCode())) {
				logger.info("【BwRepaymentService.updateAndPaymentThirdByOrderId】orderId:" + orderId + ",不满足支付条件,返回:"
						+ JSON.toJSONString(result));
				result.setResult(null);
				return result;
			}
			Integer payType = 1;
			Integer terminalType = 4;
			Map<String, Object> resultMap = (Map<String, Object>) result.getResult();
			Double firstTotalLeftAmount = (Double) resultMap.get("firstTotalLeftAmount");
			if (firstTotalLeftAmount <= 0.0) {
				result.setCode("130");
				result.setMsg("金额不对");
				logger.info("【BwRepaymentService.updateAndPaymentThirdByOrderId】orderId:" + orderId + ",金额不对,返回:"
						+ JSON.toJSONString(result));
				return result;
			}

			BwOrder bwOrder = bwOrderService.findBwOrderById(orderId.toString());
			Integer productId = bwOrder.getProductId();
			// 口袋工单走口袋代扣
			Integer useKoudaiWithhold = NumberUtil.parseInteger(
					extraConfigService.findCountExtraConfigByCode(ParameterConstant.USE_KOUDAI_WITHHOLD), 1);
			BwOrderPushInfo koudaiPushInfo = null;
			if (useKoudaiWithhold != null && useKoudaiWithhold == 1) {// 口袋代扣开关
				koudaiPushInfo = bwOrderPushInfoService.getOrderPushInfo(orderId, 2);
			}

			if (koudaiPushInfo != null && productId != null && productId == 7) {// 水象云新产品走口袋
				RepaymentDto repaymentDto = new RepaymentDto();
				repaymentDto.setOrderId(orderId);
				repaymentDto.setTerminalType(terminalType);
				repaymentDto.setUseCoupon(false);
				repaymentDto.setAmount(firstTotalLeftAmount);
				repaymentDto.setType(payType);
				repaymentDto.setPayWay(1);
				result = appPaymentService.updateAndKouDaiWithhold(repaymentDto);
				if (!"000".equals(result.getCode())) {
					RedisUtils.hdel(RedisKeyConstant.KOUDAI_PROCESS, orderId.toString());
				}
				return result;
			}

			RedisUtils.hset(SystemConstant.NOTIFY_BAOFU, orderId.toString(),
					DateFormatUtils.format(new Date(), SystemConstant.YMD_HMS));
			String orderNo = bwOrder.getOrderNo();
			Long borrowerId = bwOrder.getBorrowerId();
			BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
			BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
			String payPhone = getPayPhone(bwBorrower, bwBankCard);
			String bankCode = bwBankCard.getBankCode();
			String name = bwBorrower.getName();
			String idCard = bwBorrower.getIdCard();
			String cardNo = bwBankCard.getCardNo();
			BwCapitalWithhold bwCapitalWithhold = new BwCapitalWithhold();
			bwCapitalWithhold.setCapitalId(13);
			bwCapitalWithhold.setCardNo(cardNo);
			bwCapitalWithhold.setOrderId(orderId);
			bwCapitalWithhold.setOrderNo(orderNo);
			bwCapitalWithhold.setCreateTime(new Date());
			bwCapitalWithhold.setPushStatus(0);
			bwCapitalWithhold.setMoney(new BigDecimal(firstTotalLeftAmount));
			bwCapitalWithhold.setPayType(payType);
			bwCapitalWithhold.setTerminalType(terminalType);
			bwCapitalWithholdService.save(bwCapitalWithhold);
			String transId = new StringBuilder(CommUtils.convertDateToString(new Date(), "yyMMddHHmmssSSS"))
					.append(RandomStringUtils.randomNumeric(5)).append("T").append(bwCapitalWithhold.getId())
					.toString();
			bwCapitalWithhold.setOtherOrderNo(transId);
			bwCapitalWithhold.setUpdateTime(new Date());
			bwCapitalWithholdService.updateBwCapitalWithhold(bwCapitalWithhold);

			BwCapitalWithholdDetail bwCapitalWithholdDetail = new BwCapitalWithholdDetail();
			bwCapitalWithholdDetail.setCapitalWithholdId(bwCapitalWithhold.getId());
			bwCapitalWithholdDetail.setMchOrderNo(transId);
			bwCapitalWithholdDetail.setPayChannel(1);
			bwCapitalWithholdDetail.setPayProductId(1L);
			bwCapitalWithholdDetail.setPayChannelId(1L);
			bwCapitalWithholdDetail.setPayWay(1);
			bwCapitalWithholdDetail.setCapitalWithholdId(bwCapitalWithhold.getId());
			bwCapitalWithholdDetail.setCreateTime(new Date());
			bwCapitalWithholdDetail.setUpdateTime(new Date());
			bwCapitalWithholdDetailMapper.insertSelective(bwCapitalWithholdDetail);
			RedisUtils.hset("withhold_id_query", bwCapitalWithhold.getId().toString(), com.waterelephant.utils.CommUtils.convertDateToString(new Date(), SystemConstant.YMD_HMS));

			WithLessRequest withLess = new WithLessRequest();
			withLess.setAcc_no(cardNo);// 卡号
			withLess.setId_card(idCard);// 身份证号
			withLess.setId_holder(name);// 持卡人姓名
			withLess.setMobile(payPhone);// 银行预留手机号
			String bank_code = BaofuConstant.convertFuiouBankCodeToBaofu(bankCode);
			logger.info("【BwRepaymentService.updateAndPaymentThirdByOrderId】orderId:" + orderId + "银行编码：" + bankCode
					+ "，对应宝付银行编码：" + bank_code);
			withLess.setPay_code(bank_code);// 银行编码
			// withLess.setTxn_amt("1");// 测试交易金额,分
			withLess.setTxn_amt(firstTotalLeftAmount.toString());// 交易金额
			withLess.setRepayId(transId);// 还款计划id
			withLess.setOrderNo(orderNo);
			WithHodingResult res = BaoFuService.withHold(withLess);
			logger.info("【BwRepaymentService.updateAndPaymentThirdByOrderId】orderId:" + orderId + "宝付代扣返回结果:"
					+ JSON.toJSONString(res));
			String respCode = res.getResp_code();
			result.setMsg(res.getResp_msg());
			bwCapitalWithhold.setMsg(res.getResp_msg());
			bwCapitalWithhold.setPushStatus(1);
			RedisUtils.lpush("withhold_id_queue", bwCapitalWithhold.getId().toString());
			if ("0000".equals(respCode)) {// 成功
				result.setCode("000");
				result.setMsg("支付申请成功");
				result.setResult(null);
				return result;
			} else if (!CommUtils.isNull(respCode) && SystemConstant.baofuCode.contains(respCode)) {// 处理中
				result.setCode("106");
				result.setMsg(res.getResp_msg());
			} else {// 失败(可能超时)
				result.setCode("108");
				RedisUtils.hdel(SystemConstant.NOTIFY_BAOFU, orderId.toString());
				bwCapitalWithhold.setPushStatus(3);
			}
			bwCapitalWithholdService.updateBwCapitalWithhold(bwCapitalWithhold);
		} catch (Exception e) {
			result = new AppResponseResult();
			RedisUtils.hdel(RedisKeyConstant.KOUDAI_PROCESS, orderId.toString());
			RedisUtils.hdel(SystemConstant.NOTIFY_BAOFU, orderId.toString());
			logger.error("【BwRepaymentService.updateAndPaymentThirdByOrderId】orderId:" + orderId + "代扣异常", e);
			result.setCode("111");
			result.setMsg("支付异常");
		}
		return result;
	}

	@Override
	public AppResponseResult updateAndPaymentThirdByOrderIdNew(Long orderId) {
		AppResponseResult result = null;
		try {
			result = productService.calcRepaymentCost(orderId);
			if (!"000".equals(result.getCode())) {
				logger.info("【BwRepaymentService.updateAndPaymentThirdByOrderId】orderId:" + orderId + ",不满足支付条件,返回:"
						+ JSON.toJSONString(result));
				result.setResult(null);
				return result;
			}
			Integer payType = 1;
			Integer terminalType = 4;
			Map<String, Object> resultMap = (Map<String, Object>) result.getResult();
			Double firstTotalLeftAmount = (Double) resultMap.get("firstTotalLeftAmount");
			if (firstTotalLeftAmount <= 0.0) {
				result.setCode("130");
				result.setMsg("金额不对");
				logger.info("【BwRepaymentService.updateAndPaymentThirdByOrderIdNew】orderId:" + orderId + ",金额不对,返回:"
						+ JSON.toJSONString(result));
				return result;
			}
			OrderWithholdRequest orderWithholdRequest = new OrderWithholdRequest();
			orderWithholdRequest.setOrderId(orderId);
			orderWithholdRequest.setPayMoney(firstTotalLeftAmount);
			orderWithholdRequest.setPayType(payType);
			orderWithholdRequest.setTerminalType(terminalType);
			orderWithholdRequest.setPayChannel(15);
			String sign = PayUtil.getMd5Sign(orderWithholdRequest, PayApiConstant.ORDER_WITHHOLD_MD5_KEY, Arrays.asList("sign"), false);
			orderWithholdRequest.setSign(sign);
			String paramStr = PayUtil.getSignOrigStr(orderWithholdRequest, null, null, false);
			logger.info(StringUtils.join("【BwRepaymentService.updateAndPaymentThirdByOrderIdNew】orderId:", orderId, "请求参数:", paramStr));
			String resultStr = HttpRequest.doPost2(PayApiConstant.ORDER_WITHHOLD_URL, paramStr);
			logger.info(StringUtils.join("【BwRepaymentService.updateAndPaymentThirdByOrderIdNew】orderId:", orderId, "扣款返回:", resultStr));
			JSONObject resultJson = JSON.parseObject(resultStr);
			if ("300".equals(resultJson.getString("code"))) {
				result.setCode("106");
				result.setMsg("支付处理中，请稍后再试");
				logger.info(StringUtils.join("【BwRepaymentService.updateAndPaymentThirdByOrderIdNew】orderId:", orderId, "重复操作"));
				return result;
			}
			OrderWithholdResponse orderWithholdResponse = JSON.parseObject(resultStr, OrderWithholdResponse.class);
			Integer status = orderWithholdResponse.getStatus();
			result.setMsg(orderWithholdResponse.getErrMsg());
			result.setResult(null);
			if (status == 2) {
				result.setCode("000");
				result.setMsg("支付申请成功");
				return result;
			} else if (status == 1) {// 处理中
				result.setCode("106");
				result.setMsg(orderWithholdResponse.getErrMsg());
			} else {// 失败(可能超时)
				result.setCode("108");
			}
		} catch (Exception e) {
			result = new AppResponseResult();
			RedisUtils.hdel(RedisKeyConstant.KOUDAI_PROCESS, orderId.toString());
			RedisUtils.hdel(SystemConstant.NOTIFY_BAOFU, orderId.toString());
			logger.error("【BwRepaymentService.updateAndPaymentThirdByOrderIdNew】orderId:" + orderId + "扣款异常", e);
			result.setCode("111");
			result.setMsg("支付异常");
		}
		return result;
	}

	/**
	 * platformrecord和paymentdetail、batch支付渠道类型标记不一致
	 * 
	 * @param payChannel
	 * @return
	 */
	private Integer getTradeChannel(Integer payChannel) {
		Integer tradeChannel = 0;
		if (payChannel != null) {
			switch (payChannel) {
			case 1:// 宝付
				tradeChannel = 2;
				break;
			case 2:// 连连
				tradeChannel = 3;
				break;
			case 5:// 支付宝
				tradeChannel = 5;
				break;
			case 6:// 微信
				tradeChannel = 6;
				break;
			case 7:// 口袋
				tradeChannel = 7;
				break;
			default:
				tradeChannel = payChannel;
				break;
			}
		}
		return tradeChannel;
	}

	/**
	 * 更新工单状态结束
	 * 
	 * @param orderId
	 */
	private void updateOrderStatusEnd(Long orderId) {
		// 修改工单状态
		String sql = "update bw_order set status_id = 6,update_time = now() where id = " + orderId;
		this.sqlMapper.update(sql);
		logger.info("【BwRepaymentServiceImpl.updateOrderStatusEnd】orderId:" + orderId + "更新工单状态为6");
		// 更新还款时间
		BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord().setOrderId(orderId)
				.setEndTime(new Date());
		bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
	}

	/**
	 * 更新工单状态结束并通知一起好
	 * 
	 * @param orderId
	 */
	private void updateOrderStatusEndAndYiqihao(Long orderId) {
		updateOrderStatusEnd(orderId);
		// 判断是否提前还款
		int value = checkNormalRepayment(orderId);
		if (value >= 0) {
			// 正常还款
			// repayOrZhanqiYqh(orderId, 20);
			logger.info("===============一起好存入redis开始==============：" + orderId);
			RedisUtils.lpush(SystemConstant.YIQIHAO_ZHANQI, orderId + "-" + 20);
			logger.info("===============一起好存入redis结束==============：" + orderId);
		} else {
			// 提前还款
			// repayOrZhanqiYqh(orderId, 15);
			logger.info("===============一起好存入redis开始==============：" + orderId);
			RedisUtils.lpush(SystemConstant.YIQIHAO_ZHANQI, orderId + "-" + 15);
			logger.info("===============一起好存入redis结束==============：" + orderId);
		}
	}

	private String payChannelStr(Integer payChannel) {
		String payChannelStr = "";
		if (payChannel != null) {
			switch (payChannel) {
			case 1:
				payChannelStr = "宝付支付";
				break;
			case 2:
				payChannelStr = "连连支付";
				break;
			case 5:
				payChannelStr = "支付宝支付";
				break;
			case 6:
				payChannelStr = "微信支付";
				break;
			case 7:
				payChannelStr = "口袋支付";
				break;
			case 9:
				payChannelStr = "易宝支付";
				break;
			case 10:
				payChannelStr = "合利宝支付";
				break;
			case 11:
				payChannelStr = "快捷通支付";
				break;
			default:
				break;
			}
		}
		return payChannelStr;
	}

	/**
	 * 更新还款计划、记录支付明细<br />
	 * 已还清的还款计划保存支付明细，并更新还款计划状态
	 * 
	 * @param bwOrder
	 * @param notRepayPlanList
	 * @param loanInfoMap
	 * @param bwPlatformRecord
	 * @param leftBatchMoney
	 * @param repaymentDto
	 * @param totalLeftUseCouponAmount
	 */
	private Map<String, String> updatePlanAndRecordPayInfo(BwOrder bwOrder, List<BwRepaymentPlan> notRepayPlanList,
			Map<Long, LoanInfo> loanInfoMap, BwPlatformRecord bwPlatformRecord,
			BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail, double leftBatchMoney, RepaymentDto repaymentDto,
			double totalLeftUseCouponAmount) {
		Long orderId = bwOrder.getId();
		Integer productType = bwOrder.getProductType();
		Long borrowerId = bwOrder.getBorrowerId();
		Date tradeTime = repaymentDto.getTradeTime();
		Integer payType = repaymentDto.getType();// 1.还款 2.展期
		Integer terminalType = repaymentDto.getTerminalType();
		Integer payChannel = repaymentDto.getPayChannel();
		BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBorrowerId(borrowerId);
		// 已续贷期数
		Integer hasXudaiTerm = 0;
		if (productType == null || productType == 1) {
			hasXudaiTerm = bwOrderXudaiService.queryXudaiTerm(orderId);
		}
		Date nowDate = new Date();
		Map<String, String> resultMap = new HashMap<>();
		StringBuilder paymentRecordIdSB = new StringBuilder();
		for (int i = 0; i < notRepayPlanList.size(); i++) {
			BwRepaymentPlan bwRepaymentPlan = notRepayPlanList.get(i);
			Long repayId = bwRepaymentPlan.getId();
			Date repayTime = bwRepaymentPlan.getRepayTime();
			// Double zjw = bwRepaymentPlan.getZjw();
			LoanInfo loanInfo = loanInfoMap.get(repayId);
			double totalPlanAmount = NumberUtils.toDouble(loanInfo.getAmt(), 0.0);// 计算后的总金额
			Double alreadyRepayMoney = bwRepaymentPlan.getAlreadyRepayMoney();// 已还金额
			if (alreadyRepayMoney == null) {
				alreadyRepayMoney = 0.0;
			}
			// 该还款计划剩余还款金额
			double leftPlanAmount = DoubleUtil.sub(totalPlanAmount, alreadyRepayMoney);
			boolean isLastPlanRepayment = false;// 该还款计划是否结束
			logger.info("【BwRepaymentServiceImpl.updatePlanAndRecordPayInfo】单期工单orderId:" + orderId + ",productType="
					+ productType + ",batchMoney=" + leftBatchMoney + ",totalLeftUseCouponAmount="
					+ totalLeftUseCouponAmount);
			double repayPlanMoney = leftBatchMoney;
			if (productType == 1 && leftBatchMoney >= totalLeftUseCouponAmount) {// 单期有可能用优惠券
				isLastPlanRepayment = true;
			} else if (leftBatchMoney < leftPlanAmount) {// 未还清
			} else if (leftBatchMoney == leftPlanAmount) {// 刚好还清
				isLastPlanRepayment = true;
			} else if (notRepayPlanList.size() == (i + 1)) {// 还清有多余且最后一个还款计划
				isLastPlanRepayment = true;
			} else {// 还清并有多余，剩下的钱继续下一个还款计划
				isLastPlanRepayment = true;
				repayPlanMoney = leftPlanAmount;
			}
			bwRepaymentPlan.setAlreadyRepayMoney(DoubleUtil.add(repayPlanMoney, alreadyRepayMoney));

			if (isLastPlanRepayment) {// 记录支付明细、更新还款计划状态、不清零罚息
				double couponAmount = 0.0;
				if (productType == 1) {// 单期
					Double totalAmount = NumberUtil.parseDouble(loanInfo.getAmt(), totalLeftUseCouponAmount);
					couponAmount = DoubleUtil.sub(totalAmount, totalLeftUseCouponAmount);
				}
				// 记录支付明细
				BwPaymentDetail bwPaymentDetail = new BwPaymentDetail(null, orderId, repayId, payType,
						bwRepaymentPlan.getAlreadyRepayMoney(), 0.0, bwOrder.getBorrowAmount(),
						NumberUtils.toDouble(loanInfo.getOverdueAmount(), 0.0), couponAmount, nowDate, nowDate);
				bwPaymentDetail.setNoOverdueAmount(NumberUtils.toDouble(loanInfo.getNoOverdueAmount(), 0.0));
				bwPaymentDetail.setRealOverdueAmount(NumberUtils.toDouble(loanInfo.getRealOverdueAmount(), 0.0));
				bwPaymentDetail.setBorrowerId(borrowerId);
				bwPaymentDetail.setPayChannel(payChannel);
				bwPaymentDetail.setOverdueDay(loanInfo.getOverdueDay());
				bwPaymentDetail.setXudaiTimes(hasXudaiTerm);
				bwPaymentDetail.setTradeTime(tradeTime);
				bwPaymentDetail.setPayStatus(1);
				Double zjwAmount = bwRepaymentPlan.getZjw();
				if (zjwAmount == null) {
					zjwAmount = 0.0;
				}
				bwPaymentDetail.setZjwAmount(zjwAmount);
				if (bwBankCard != null) {
					bwPaymentDetail.setCardNo(bwBankCard.getCardNo());
					bwPaymentDetail.setBankCode(bwBankCard.getBankCode());
				}
				if (terminalType != null) {
					bwPaymentDetail.setTerminalType(terminalType);
				}
				String bwPaymentDetailJson = JSON.toJSONString(bwPaymentDetail);
				logger.info("【BwRepaymentServiceImpl.updateRepaymentNotify】orderId:" + orderId + ",保存BwPaymentDetail:"
						+ bwPaymentDetailJson);
				bwPaymentDetailService.saveOrUpdateByRepayId(bwPaymentDetail);

				// 逾期天数
				int overdueDay = MyDateUtils.getDaySpace(repayTime, nowDate);
				// 更新还款计划状态
				if (overdueDay > 0) {
					bwRepaymentPlan.setRepayType(2);// 逾期还款
				} else if (overdueDay < 0) {
					bwRepaymentPlan.setRepayType(3);// 提前还款
				} else {
					bwRepaymentPlan.setRepayType(1);// 正常还款
				}
				bwRepaymentPlan.setRepayStatus(2);

				logger.info(
						"【BwRepaymentServiceImpl.updateRepaymentNotify】orderId:" + orderId + ",更新还款计划状态==>repayType:"
								+ bwRepaymentPlan.getRepayType() + ",repayStatus:" + bwRepaymentPlan.getRepayStatus());

				if (productType != null && productType == 1) {// 单期(比分期多了存入一起好Redis)
					updateOrderStatusEndAndYiqihao(orderId);
				}
				// 分期还款计划结清
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("orderId", orderId + "");
				jsonObject.put("typeId", bwRepaymentPlan.getNumber() + "");
				RedisUtils.rpush("insurance:order", jsonObject.toJSONString());
			}
			bwRepaymentPlan.setUpdateTime(nowDate);
			bwRepaymentPlanService.update(bwRepaymentPlan);

			// 分批还款或展期保存拆分还款记录
			BwPaymentRecord bwPaymentRecord = saveBwPaymentRecordByPlan(repaymentDto, bwOrder, bwRepaymentPlan,
					bwOrderRepaymentBatchDetail, null, bwPlatformRecord, repayPlanMoney,
					NumberUtil.parseDouble(loanInfo.getRealOverdueAmount(), 0.0));
			if (bwPaymentRecord != null) {
				paymentRecordIdSB.append(bwPaymentRecord.getId()).append(",");
			}

			leftBatchMoney = DoubleUtil.sub(leftBatchMoney, leftPlanAmount);// 还款后剩余分批金额
			if (leftBatchMoney <= 0.0) {
				break;
			}
		}
		resultMap.put("paymentRecordIds", paymentRecordIdSB.length() > 0
				? paymentRecordIdSB.substring(0, paymentRecordIdSB.lastIndexOf(",")) : paymentRecordIdSB.toString());
		return resultMap;
	}

	/**
	 * 保存还款分批信息
	 */
	private BwOrderRepaymentBatchDetail saveOrUpdateRepaymentBatchDetail(BwOrder bwOrder,
			List<BwRepaymentPlan> notRepayPlanList, Map<Long, LoanInfo> loanInfoMap, RepaymentBatch repaymentBatch,
			Double totalAmount, RepaymentDto repaymentDto, double totalLeftUseCouponAmount) {
		Long orderId = bwOrder.getId();
		BwRepaymentPlan earlyPlan = notRepayPlanList.get(0);
		Double tradeMoney = repaymentDto.getAmount();
		Date tradeTime = repaymentDto.getTradeTime();
		Integer terminalType = repaymentDto.getTerminalType();
		Integer payChannel = repaymentDto.getPayChannel();
		double totalOverdueAmount = 0.0;// 总逾期金额
		for (BwRepaymentPlan notPlan : notRepayPlanList) {
			LoanInfo loanInfo = loanInfoMap.get(notPlan.getId());
			totalOverdueAmount = DoubleUtil.add(totalOverdueAmount,
					NumberUtils.toDouble(loanInfo.getOverdueAmount(), 0.0));
		}
		LoanInfo earlyLoanInfo = loanInfoMap.get(earlyPlan.getId());
		// 终端类型：0系统后台 1Android 2ios 3WAP 4外部渠道
		// 总剩余金额
		// double totalLeftAmount = DoubleUtil.sub(totalAmount,
		// repaymentBatch.getAlreadyTotalBatchMoney());
		Date nowDate = new Date();
		BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail = new BwOrderRepaymentBatchDetail();
		bwOrderRepaymentBatchDetail.setAmount(tradeMoney);
		bwOrderRepaymentBatchDetail.setResidualAmount(DoubleUtil.sub(totalLeftUseCouponAmount, tradeMoney));
		// 开始保存
		bwOrderRepaymentBatchDetail.setOrderId(orderId);
		bwOrderRepaymentBatchDetail.setNumber(repaymentBatch.getCurrentNumber());
		bwOrderRepaymentBatchDetail.setRepaymentChannel(payChannel);
		bwOrderRepaymentBatchDetail.setOverdueDay(earlyLoanInfo.getOverdueDay());
		bwOrderRepaymentBatchDetail.setOverdueAmount(totalOverdueAmount);
		bwOrderRepaymentBatchDetail.setTotalAmount(totalAmount);
		bwOrderRepaymentBatchDetail.setCreateTime(nowDate);
		bwOrderRepaymentBatchDetail.setUpdateTime(nowDate);
		if (tradeMoney < totalLeftUseCouponAmount) {
			bwOrderRepaymentBatchDetail.setLastRepayment(false);
		} else {
			bwOrderRepaymentBatchDetail.setLastRepayment(true);
		}
		if (terminalType != null) {
			bwOrderRepaymentBatchDetail.setTerminalType(terminalType);
		}
		bwOrderRepaymentBatchDetail.setTradeTime(tradeTime);
		bwOrderRepaymentBatchDetailService.saveOrUpdateByOrderAndNumber(bwOrderRepaymentBatchDetail);
		logger.info("【BwRepaymentServiceImpl.updateRepaymentNotify】orderId:" + orderId
				+ ",保存bwOrderRepaymentBatchDetail:" + JSON.toJSONString(bwOrderRepaymentBatchDetail));
		return bwOrderRepaymentBatchDetail;
	}

	/**
	 * 展期，添加流水、支付明细，添加展期工单ID到redis
	 */
	private void updateZhanqiNotify(BwOrder bwOrder, BwBorrower borrower, BwProductDictionary product,
			RepaymentDto repaymentDto, BwPlatformRecord bwPlatformRecord) {
		Long orderId = bwOrder.getId();
		Long borrowerId = bwOrder.getBorrowerId();
		Integer productType = bwOrder.getProductType();
		Integer payChannel = repaymentDto.getPayChannel();
		Double tradeMoney = repaymentDto.getAmount();
		Date tradeTime = repaymentDto.getTradeTime();
		Integer payType = repaymentDto.getType();// 1.还款 2.展期
		Integer terminalType = repaymentDto.getTerminalType();
		BwBankCard bankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
		logger.info("【BwRepaymentServiceImpl.updateOrderByPayMoney】展期orderId:" + orderId);

		// 已续贷期数
		Integer hasXudaiTerm = 0;
		// 5月13号以后续贷次数
		Integer hasAfterXudaiTerm = 0;
		if (productType == null || productType == 1) {
			hasXudaiTerm = bwOrderXudaiService.queryXudaiTerm(orderId);
			hasAfterXudaiTerm = bwRepaymentPlanService.getXuDaiCountAfterDate(orderId);
		}

		BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(orderId);
		Long repayId = bwRepaymentPlan.getId();
		Double zjwAmount = bwRepaymentPlan.getZjw();
		if (zjwAmount == null) {
			zjwAmount = 0.0;
		}

		Double borrowAmount = bwOrder.getBorrowAmount();
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setAvoidFineDate(bwOrder.getAvoidFineDate());
		productService.calcXudaiCost(borrowAmount, orderId, repayId, product, hasAfterXudaiTerm + 1, loanInfo);
		double overdueAmount = NumberUtils.toDouble(loanInfo.getOverdueAmount(), 0.0);
		double noOverdueAmount = NumberUtils.toDouble(loanInfo.getNoOverdueAmount(), 0.0);
		double realOverdueAmount = NumberUtils.toDouble(loanInfo.getRealOverdueAmount(), 0.0);
		double xudaiAmount = NumberUtils.toDouble(loanInfo.getServiceAmount(), 0.0);
		Integer overdueDay = loanInfo.getOverdueDay();

		// 记录流水
		savePlatformRecord(bwOrder, borrower, bankCard, repaymentDto);

		// 记录支付明细
		BwPaymentDetail bwPaymentDetail = new BwPaymentDetail();
		bwPaymentDetail.setOrderId(orderId);
		bwPaymentDetail.setRepayId(repayId);
		bwPaymentDetail.setBorrowerId(borrowerId);
		bwPaymentDetail.setBorrowAmount(bwOrder.getBorrowAmount());
		bwPaymentDetail.setCouponAmount(0.0);
		bwPaymentDetail.setTradeAmount(tradeMoney);
		bwPaymentDetail.setTradeTime(tradeTime);
		bwPaymentDetail.setPayChannel(payChannel);
		bwPaymentDetail.setTradeType(payType);
		bwPaymentDetail.setXudaiTimes(hasXudaiTerm);
		bwPaymentDetail.setXudaiAmount(xudaiAmount);
		bwPaymentDetail.setOverdueDay(overdueDay);
		if (bankCard != null) {
			bwPaymentDetail.setCardNo(bankCard.getCardNo());
			bwPaymentDetail.setBankCode(bankCard.getBankCode());
		}
		bwPaymentDetail.setNoOverdueAmount(noOverdueAmount);
		bwPaymentDetail.setOverdueAmount(overdueAmount);
		bwPaymentDetail.setRealOverdueAmount(realOverdueAmount);
		bwPaymentDetail.setTerminalType(terminalType);
		bwPaymentDetail.setZjwAmount(zjwAmount);
		bwPaymentDetailService.saveOrUpdateByRepayId(bwPaymentDetail);

		Double alreadyRepayMoney = bwRepaymentPlan.getAlreadyRepayMoney();// 已支付金额
		if (alreadyRepayMoney == null) {
			alreadyRepayMoney = 0.0;
		}

		// 更新展期已支付金额
		BwRepaymentPlan updatePlan = new BwRepaymentPlan();
		updatePlan.setId(repayId);
		updatePlan.setAlreadyRepayMoney(DoubleUtil.add(tradeMoney, alreadyRepayMoney));
		updatePlan.setUpdateTime(new Date());
		bwRepaymentPlanService.update(updatePlan);

		saveBwPaymentRecordByPlan(repaymentDto, bwOrder, bwRepaymentPlan, null, bwPaymentDetail, bwPlatformRecord,
				bwPaymentDetail.getTradeAmount(), NumberUtil.parseDouble(loanInfo.getRealOverdueAmount(), 0.0));

		logger.info("【BwRepaymentServiceImpl.updateZhanqiNotify】orderId:" + orderId + ",保存支付明细bwPaymentDetail:"
				+ JSON.toJSONString(bwPaymentDetail));
		RedisUtils.hset(SystemConstant.WEIXIN_ORDER_ID, orderId.toString(), orderId.toString());
		logger.info("【BwRepaymentServiceImpl.updateZhanqiNotify】orderId:" + orderId + "展期存redis结束===========");
	}

	/**
	 * 记录流水
	 * 
	 * @param bwOrder
	 * @param borrower
	 * @param bankCard
	 * @param repaymentDto
	 */
	private BwPlatformRecord savePlatformRecord(BwOrder bwOrder, BwBorrower borrower, BwBankCard bankCard,
			RepaymentDto repaymentDto) {
		Double tradeMoney = repaymentDto.getAmount();
		String tradeNo = repaymentDto.getTradeNo();
		Date tradeTime = repaymentDto.getTradeTime();
		Integer payType = repaymentDto.getType();// 1.还款 2.展期
		Integer terminalType = repaymentDto.getTerminalType();
		Integer payChannel = repaymentDto.getPayChannel();
		String payChannelStr = payChannelStr(payChannel);
		BwPlatformRecord bwPlatformRecord = new BwPlatformRecord();
		bwPlatformRecord.setTradeNo(tradeNo);
		bwPlatformRecord.setTradeAmount(tradeMoney);// 交易金额
		if (payChannel != null && (payChannel == 5 || payChannel == 6)) {
			bwPlatformRecord.setTradeType(2);// 1划拨2转账
		} else {
			bwPlatformRecord.setTradeType(1);// 1划拨2转账
		}
		if (bankCard != null) {
			bwPlatformRecord.setOutAccount(bankCard.getCardNo());
		}
		bwPlatformRecord.setOutName(borrower.getName());
		bwPlatformRecord.setInAccount("上海水象金融信息服务有限公司-" + payChannelStr);
		bwPlatformRecord.setInName("上海水象金融信息服务有限公司-" + payChannelStr);
		bwPlatformRecord.setOrderId(bwOrder.getId());
		bwPlatformRecord.setTradeTime(tradeTime);
		bwPlatformRecord.setTradeCode(repaymentDto.getTradeCode());
		bwPlatformRecord.setTradeType(repaymentDto.getTradeType());
		if (payType == 1) {
			bwPlatformRecord.setTradeRemark(payChannelStr + "还款扣款");
		} else if (payType == 2) {
			bwPlatformRecord.setTradeRemark(payChannelStr + "展期扣款");
		}
		bwPlatformRecord.setTradeChannel(getTradeChannel(payChannel));
		bwPlatformRecord.setTerminalType(terminalType);// 终端类型
		bwPlatformRecordService.saveOrUpdateByTradeNo(bwPlatformRecord);
		return bwPlatformRecord;
	}

	private void addOrderStatusRecord(boolean isBatch, BwOrder bwOrder, double tradeAmount, double leftAmount) {
		if (isBatch) {
			// 记录弹窗(分批)
			bwOrderStatusRecordService.insertRecord(bwOrder,
					"您于" + DateUtil.getCurrentDateString(DateUtil.YMD) + "已经成功还款" + tradeAmount + "元" + ",剩余还款金额"
							+ (leftAmount > 0 ? leftAmount : 0.0) + "元",
					ActivityConstant.BWORDERSTATUSRECORD_DIALOGSTYLE.DIALOGSTYLE_BATCHREPAYSUCCESS);
		} else {
			// 记录弹窗(不分批)
			bwOrderStatusRecordService.insertRecord(bwOrder,
					"您于" + DateUtil.getCurrentDateString(DateUtil.YMD) + "已经成功还款" + tradeAmount + "元",
					ActivityConstant.BWORDERSTATUSRECORD_DIALOGSTYLE.DIALOGSTYLE_REPAYSUCCESS);
		}
	}

	/**
	 * 发送短信
	 * 
	 * @param borrower
	 * @param tradeAmount
	 * @param bwOrder
	 */
	private void sendMessage(BwBorrower borrower, Double tradeAmount, BwOrder bwOrder) {
		if (!RedisUtils.exists("tripartite:smsFilter:registerPassword:" + bwOrder.getChannel())) {
			String msg = "尊敬的${name}先生/女士，您本次的需还款项已划扣成功。感谢您对速秒钱包的支持，祝您生活愉快。";
			String appName = getAppName(borrower.getAppId());
			msg = StringUtils.join("【", appName, "】", msg.replace("速秒钱包", appName));
			String sex = String.valueOf(borrower.getSex());
			if ("1".equals(sex)) {
				msg = msg.replace("先生/女士", "先生");
			} else if ("0".equals(sex)) {
				msg = msg.replace("先生/女士", "女士");
			}
			String name = borrower.getName();
			String surname = null;
			if (name.length() < 4) {
				surname = name.substring(0,1);
			}else {
				surname = name.substring(0,2);
			}
			msg = msg.replace("${name}", surname);
			MessageDto messageDto = new MessageDto();
			messageDto.setBusinessScenario("2");
			messageDto.setPhone(borrower.getPhone());
			messageDto.setMsg(msg);
			messageDto.setType("1");
			RedisUtils.rpush("system:cutPaySendMessage", JSON.toJSONString(messageDto));
		}
	}

	private String getAppName(Integer appId) {
		String appName = "速秒钱包";
		if (appId != null) {
			appName = RedisUtils.hget("borrower_app_id", appId.toString());
			if (StringUtils.isNotEmpty(appName)) {
				return appName;
			}
			switch (appId) {
				case 1:
					appName = "速秒钱包";
					break;
				case 2:
					appName = "77分期";
					break;
				case 3:
					appName = "乐分期";
					break;
				default:
					break;
			}
		}
		return appName;
	}

	/**
	 * 推送第三方
	 * 
	 * @param orderId
	 */
	private void sendThirdOrder(Long orderId) {
		String channel = null;
		try {
			channel = bwOrderService.findPathByChannel(orderId) + "";
			// channelService.sendOrderStatus(channel, orderId, "6");
			if ("12".equals(channel) || "81".equals(channel)) {
				// 好贷
				String thirdOrderId = bwOrderRongService.findThirdOrderNoByOrderId(orderId.toString());
				if (!CommUtils.isNull(thirdOrderId)) {
					BeadWalletHaoDaiService.sendOrderStatus(thirdOrderId, "4");
				}
			}
		} catch (Exception e) {
			logger.info("====================渠道同步工单状态，回调失败======================");
		}

		try {
			if ("11".equals(String.valueOf(channel))) {
				String thirdOrderId = bwOrderRongService.findThirdOrderNoByOrderId(orderId.toString());
				OrderFeedBackReq orderFeedBackReq = new OrderFeedBackReq();
				orderFeedBackReq.setOrder_no(thirdOrderId);
				orderFeedBackReq.setOrder_status(200);
				logger.info("分期开始调用融360订单反馈接口，" + orderFeedBackReq);
				OrderFeedBackResp orderFeedBackResp = BeadWalletRong360Service.orderFeedBack(orderFeedBackReq);
				logger.info("分期结束调用融360订单反馈接口，" + orderFeedBackResp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用融360订单反馈接口异常:", e);
		}
	}

	/**
	 * 还款完成后通知
	 *
	 * @param orderId
	 */
	private void repaymentOkNotifyThree(Long orderId) {
		try {
			BwOrder bwOrder = bwOrderService.findBwOrderById(orderId.toString());
			Integer channel = bwOrder.getChannel();
			HashMap<String, String> hm = new HashMap<>();
			hm.put("channelId", channel + "");
			hm.put("orderId", String.valueOf(bwOrder.getId()));
			hm.put("orderStatus", String.valueOf(bwOrder.getStatusId()));
			hm.put("result", "还款成功");
			String hmData = JSON.toJSONString(hm);
			RedisUtils.rpush("tripartite:orderStatusNotify:" + channel, hmData);
		} catch (Exception e) {
			logger.error("repaymentOkNotifyThree,orderId:" + orderId + "通知三方异常", e);
		}
	}

	public RepaymentDto queryRedisPayInfo(Long orderId) {
		String payInfoJson = RedisUtils.hget("pay_info", orderId.toString());
		RepaymentDto redisPayDto = null;
		if (StringUtils.isNotEmpty(payInfoJson)) {
			redisPayDto = JSON.parseObject(payInfoJson, RepaymentDto.class);
		}
		return redisPayDto;
	}

	private String getPayPhone(BwBorrower bwBorrower, BwBankCard bwBankCard) {
		String phone = bwBorrower.getPhone();
		if (bwBankCard != null && StringUtils.isNotEmpty(bwBankCard.getPhone())) {
			phone = bwBankCard.getPhone();
		}
		return phone;
	}

	/**
	 * 分批还款或展期保存拆分还款记录
	 *
	 * @param repaymentDto
	 * @param bwOrder
	 * @param bwRepaymentPlan
	 * @param bwOrderRepaymentBatchDetail
	 * @param bwPaymentDetail
	 * @param bwPlatformRecord
	 * @param repayPlanMoney
	 * @param realOverdueAmount
	 * @return
	 */
	@Override
	public BwPaymentRecord saveBwPaymentRecordByPlan(RepaymentDto repaymentDto, BwOrder bwOrder,
			BwRepaymentPlan bwRepaymentPlan, BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail,
			BwPaymentDetail bwPaymentDetail, BwPlatformRecord bwPlatformRecord, Double repayPlanMoney,
			Double realOverdueAmount) {
		Integer payType = repaymentDto.getType();
		Integer payWay = repaymentDto.getPayWay();
		Integer terminalType = repaymentDto.getTerminalType();
		Integer payChannel = repaymentDto.getPayChannel();
		if (payWay == null) {
			payWay = queryPayWayByTerminalType(terminalType, payChannel, repaymentDto.getTradeNo());
		}
		repaymentDto.setPayWay(payWay);
		Long orderId = bwRepaymentPlan.getOrderId();
		Long repayId = bwRepaymentPlan.getId();
		Integer repayStatus = bwRepaymentPlan.getRepayStatus();
		Double alreadyRepayMoney = bwRepaymentPlan.getAlreadyRepayMoney();
		if (alreadyRepayMoney == null) {
			alreadyRepayMoney = 0.0;
		}
		Long repaymentBatchId = bwOrderRepaymentBatchDetail != null ? bwOrderRepaymentBatchDetail.getId() : null;
		Long platformRecordId = bwPlatformRecord.getId();
		Date tradeTime = repaymentDto.getTradeTime();
		BwPaymentRecord bwPaymentRecord = new BwPaymentRecord(orderId, repayId, platformRecordId, repaymentBatchId,
				new Date(), tradeTime);
		// 查询催收员
		BwCollectionUser bwCollectionUser = bwCollectionUserService.findLastByRepayId(repayId, tradeTime);

		if (bwCollectionUser != null) {
			Long userId = bwCollectionUser.getUserId();
			bwPaymentRecord.setUserId(userId);
			// 查询部门ID
			Long deptId = sysDeptUserService.findLastDeptIdByUserId(userId);
			bwPaymentRecord.setDeptId(deptId);
			if ((repayStatus != null && repayStatus == 2) || payType == 2) {// 结清或展期
				bwPaymentRecord.setSettleUserId(userId);
				bwPaymentRecord.setSettleDeptId(deptId);
				if (payType != 2) {// 非展期
					BwPaymentRecord updateRecord = new BwPaymentRecord();
					updateRecord.setSettleUserId(userId);
					updateRecord.setSettleDeptId(deptId);
					updateRecord.setUpdateTime(new Date());
					bwPaymentRecordService.updateByRepayId(repayId, updateRecord);
				}
			}
		}
		bwPaymentRecord.setAlreadyRepayMoney(alreadyRepayMoney).setPayType(payType).setState(1)
				.setStatusId(Integer.parseInt(bwOrder.getStatusId().toString()))
				.setRepayType(bwRepaymentPlan.getRepayType()).setRepayStatus(bwRepaymentPlan.getRepayStatus())
				.setTerminalType(terminalType).setPayWay(payWay).setTradeAmount(repayPlanMoney)
				.setPayChannel(repaymentDto.getPayChannel());
		BwOverdueRecord bwOverdueRecord = bwOverdueRecordService.queryBwOverdueByRepayId(repayId);
		if (bwOverdueRecord != null) {
			bwPaymentRecord.setOverdueDay(bwOverdueRecord.getOverdueDay());
			bwPaymentRecord.setOverdueMoney(bwOverdueRecord.getOverdueAccrualMoney());
			bwPaymentRecord.setAdvance(bwOverdueRecord.getAdvance() == null ? 0.0 : bwOverdueRecord.getAdvance());
		}
		if (payType == 1) {// 还款
			double preAlreadyRepayMoney = DoubleUtil.sub(alreadyRepayMoney, repayPlanMoney);
			Double planRepayCorpusMoney = bwRepaymentPlan.getRepayCorpusMoney();// 还款本金
			Double planRepayAccrualMoney = bwRepaymentPlan.getRepayAccrualMoney() == null ? 0.0
					: bwRepaymentPlan.getRepayAccrualMoney();// 还款利息
			Double planZjw = bwRepaymentPlan.getZjw() == null ? 0.0 : bwRepaymentPlan.getZjw();
			double leftRepayCorpusMoney = DoubleUtil.sub(planRepayCorpusMoney, preAlreadyRepayMoney);// 剩余还款本金
			double leftRepayAccrualMoney = planRepayAccrualMoney;// 剩余利息
			double leftZjw = planZjw;// 剩余湛江委
			double leftOverdueMoney = realOverdueAmount;// 剩余逾期
			if (leftRepayCorpusMoney < 0.0) {
				leftRepayAccrualMoney = DoubleUtil.add(leftRepayAccrualMoney, leftRepayCorpusMoney);
				leftRepayCorpusMoney = 0.0;
			}
			if (leftRepayAccrualMoney < 0.0) {
				leftZjw = DoubleUtil.add(leftZjw, leftRepayAccrualMoney);
				leftRepayAccrualMoney = 0.0;
			}
			if (leftZjw < 0.0) {
				leftOverdueMoney = DoubleUtil.add(leftOverdueMoney, leftZjw);
				leftZjw = 0.0;
			}
			logger.info(new StringBuilder("【BwRepaymentPlanService.saveBwPaymentRecordByPlan】orderId:").append(orderId)
					.append(",repayId").append(repayId).append(",剩余本金:").append(leftRepayCorpusMoney).append(",剩余利息:")
					.append(leftRepayAccrualMoney).append(",剩余湛江委:").append(leftZjw).append(",剩余逾期:")
					.append(leftOverdueMoney));
			bwPaymentRecord.setRepayCorpusMoney(repayPlanMoney);
			if (repayPlanMoney > leftRepayCorpusMoney) {// 大于剩余本金
				bwPaymentRecord.setRepayCorpusMoney(leftRepayCorpusMoney);
				double leftMoney = DoubleUtil.sub(repayPlanMoney, leftRepayCorpusMoney);
				bwPaymentRecord.setRepayAccrualMoney(leftMoney);
				if (leftMoney > leftRepayAccrualMoney) {// 利息
					leftMoney = DoubleUtil.sub(leftMoney, leftRepayAccrualMoney);
					bwPaymentRecord.setRepayAccrualMoney(leftRepayAccrualMoney);
					bwPaymentRecord.setRepayZjwMoney(leftMoney);
					if (leftMoney > leftZjw) {// 湛江委
						leftMoney = DoubleUtil.sub(leftMoney, leftZjw);
						bwPaymentRecord.setRepayZjwMoney(leftZjw);
						bwPaymentRecord.setRepayOverdueMoney(leftMoney);
						if (leftMoney > leftOverdueMoney) {
							bwPaymentRecord.setRepayOverdueMoney(leftOverdueMoney);
							bwPaymentRecord.setRepayExtraMoney(DoubleUtil.sub(leftMoney, leftOverdueMoney));
						}
					}
				}
			}
		} else if (payType == 2) {// 展期
			Double zjwAmount = bwPaymentDetail.getZjwAmount() == null ? 0.0 : bwPaymentDetail.getZjwAmount();
			realOverdueAmount = bwPaymentDetail.getRealOverdueAmount() == null ? 0.0
					: bwPaymentDetail.getRealOverdueAmount();
			Double tradeAmount = bwPaymentDetail.getTradeAmount();
			bwPaymentRecord
					.setRepayAccrualMoney(DoubleUtil.sub(DoubleUtil.sub(tradeAmount, realOverdueAmount), zjwAmount))
					.setRepayZjwMoney(zjwAmount).setRepayOverdueMoney(bwPaymentDetail.getRealOverdueAmount());
			bwPaymentRecord.setStatusId(9);
			if (bwOverdueRecord != null) {
				BwProductDictionary product = bwProductDictionaryService
						.findBwProductDictionaryById(bwOrder.getProductId());
				Date repayTime = productService.calcExtendRepayTime(bwRepaymentPlan.getRepayTime(), product.getpTerm(),
						product.getpTermType());
				if (new Date().after(repayTime)) {
					bwPaymentRecord.setStatusId(13);
				}
			}
		}
		bwPaymentRecordService.insertSelective(bwPaymentRecord);
		return bwPaymentRecord;
	}

	private Integer queryPayWayByTerminalType(Integer terminalType, Integer payChannel, String tradeNo) {
		Integer payWay = null;
		if (terminalType != null) {
			if (terminalType == 5) {// 自动代扣
				payWay = 3;
			} else if ((terminalType >= 1 && terminalType <= 4)
					|| (payChannel != null && (payChannel == 5 || payChannel == 6))) {
				payWay = 1;
			} else if (terminalType == 0) {
				payWay = 2;
			}
		}
		if (payWay == null) {
			if (payChannel == 1 && tradeNo != null && tradeNo.length() > 21) {
				char type = tradeNo.charAt(20);
				if (type == 'A' || type == 'B') {
					payWay = 1;
				} else if (type == 'H' || type == 'D') {
					payWay = 2;
				}
			}
		}
		return payWay;
	}
}
