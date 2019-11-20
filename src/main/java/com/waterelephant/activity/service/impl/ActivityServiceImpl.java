/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.activity.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.beadwallet.entity.lianlian.NotifyNotice;
import com.beadwallet.service.sms.dto.MessageDto;
import com.waterelephant.activity.service.ActivityService;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.entity.ActivityDiscountDistribute;
import com.waterelephant.entity.ActivityDiscountInfo;
import com.waterelephant.entity.ActivityDiscountUseage;
import com.waterelephant.entity.ActivityInfo;
import com.waterelephant.entity.ActivityParticipate;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderRepaymentBatchDetail;
import com.waterelephant.entity.BwPaymentDetail;
import com.waterelephant.entity.BwPlatformRecord;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.ActivityDiscountDistributeService;
import com.waterelephant.service.ActivityDiscountInfoService;
import com.waterelephant.service.ActivityDiscountUseageService;
import com.waterelephant.service.ActivityInfoService;
import com.waterelephant.service.ActivityParticipateService;
import com.waterelephant.service.BwOrderRepaymentBatchDetailService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwPaymentDetailService;
import com.waterelephant.service.BwPlatformRecordService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.service.IBwRepaymentService;
import com.waterelephant.sms.service.SendMessageCommonService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.DoubleUtil;
import com.waterelephant.utils.JsonDataProcessorImpl;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.NumberUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SqlMapper;
import com.waterelephant.utils.SystemConstant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 活动处理业务类
 * 
 * Module:
 * 
 * AcvitiServiceImpl.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class ActivityServiceImpl implements ActivityService {

	private Logger logger = Logger.getLogger(ActivityServiceImpl.class);

	@Autowired
	private ActivityDiscountInfoService activityDiscountInfoService;
	@Autowired
	protected SqlMapper sqlMapper;
	@Autowired
	private ActivityInfoService activityInfoService;
	@Autowired
	private ActivityDiscountDistributeService activityDiscountDistributeService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private ActivityDiscountUseageService activityDiscountUseageService;
	@Autowired
	private IBwRepaymentService bwRepaymentService;
	@Autowired
	private BwPlatformRecordService bwPlatformRecordService;
	@Autowired
	private ActivityParticipateService activityParticipateService;
	@Autowired
	private IBwBankCardService bankCardService;
	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;
	@Autowired
	private BwPaymentDetailService bwPaymentDetailService;
	@Autowired
	private BwOrderRepaymentBatchDetailService bwOrderRepaymentBatchDetailService;
	@Autowired
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private SendMessageCommonService sendMessageCommonService;

	/**
	 * 根据活动Id查询活动
	 * 
	 * @see ActivityService#getActiveByActivityId(java.lang.String)
	 */
	@Override
	public Object getActivity(ActivityInfo entity) {

		ActivityInfo activityInfo = activityInfoService.queryActivityInfo(entity);
		List<ActivityDiscountInfo> list = new ArrayList<ActivityDiscountInfo>();
		if (activityInfo != null) {
			list = activityDiscountInfoService.getActinityDiscountInfo(activityInfo.getActivityId());
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDataProcessorImpl("yyyy-MM-dd"));
		JSONObject json = JSONObject.fromObject(activityInfo, jsonConfig);
		JSONArray jr = JSONArray.fromObject(list, jsonConfig);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activityInfo", json);
		map.put("list", jr);
		return map;
	}

	/**
	 * 还款
	 * 
	 * @see com.waterelephant.activity.service.ActivityService#participationActivity(java.lang.String)
	 */
	@Override
	public NotifyNotice addParticipationActivity(BwOrder bwOrder, BwPlatformRecord bwPlatformRecord,
			boolean isDelRedis) {
		NotifyNotice notice = new NotifyNotice();
		try {
			// 扣款金额
			String trade_amount = bwPlatformRecord.getTradeAmount().toString();
			// 计划还款金额
			String repay_money = activityDiscountDistributeService.findNewBwRepaymentPlanMoney(bwOrder.getId());
			if (CommUtils.isNull(repay_money)) {
				notice.setRet_code("103");
				notice.setRet_msg("计划还款金额不能为空");
				return notice;
			}
			BwRepaymentPlan plan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(bwOrder.getId());
			logger.info("工单Id：" + bwOrder.getId() + "，最近还款计划id：" + plan.getId() + "，计划还款金额：" + repay_money + "请求借款扣款金额："
					+ trade_amount);
			BwPaymentDetail bwPaymentDetail = bwPaymentDetailService.queryByRedisOrDB(plan.getId());
			BwOrderRepaymentBatchDetail lastBatchDetail = bwOrderRepaymentBatchDetailService
					.getLastBatchDetail(bwOrder.getId());

			logger.info("工单Id：" + bwOrder.getId() + "，还款计划ID：" + plan.getId() + "，bwPaymentDetail："
					+ JSON.toJSONString(bwPaymentDetail));
			logger.info("工单Id：" + bwOrder.getId() + "，还款计划ID：" + plan.getId() + "，lastBatchDetail："
					+ JSON.toJSONString(lastBatchDetail));
			if ((null == bwPaymentDetail || null == bwPaymentDetail.getOrderId())
					&& (null == lastBatchDetail || null == lastBatchDetail.getOrderId())) {
				logger.info("工单Id：" + bwOrder.getId() + "，还款计划ID：" + plan.getId() + "支付明细和分批还款明细同时为空");
			}

			Boolean isLastRepayment = false;// 是否最后一次还款并结清
			if (lastBatchDetail != null) {
				isLastRepayment = lastBatchDetail.getLastRepayment();
			} else if (null != bwPaymentDetail && null != bwPaymentDetail.getOrderId()) {
				isLastRepayment = true;
			}
			// 是否使用优惠券
			boolean useCoupon = false;
			if (bwPaymentDetail == null) {
				// 金额不一致使用现金券（旧方法）
				if (lastBatchDetail == null && !repay_money.equals(trade_amount)) {
					useCoupon = true;
				}
			} else if (bwPaymentDetail.getCouponAmount() > 0.0) {
				useCoupon = true;
			}
			// 支付明细优惠券金额大于0使用现金券
			if (useCoupon) {
				// 使用优惠券
				updateAndUseCoupon(bwOrder, repay_money);
			}

			if (isLastRepayment) {
				bwRepaymentService.updateOrderStatus(bwOrder.getId());// 修改订单状态
			}

			// 已还款金额
			Double alreadyRepayMoney = bwOrderRepaymentBatchDetailService.getBatchDetailTotal(bwOrder.getId());
			if (alreadyRepayMoney == 0.0 && bwPaymentDetail != null) {
				Double realOverdueAmount = bwPaymentDetail.getRealOverdueAmount();
				Double borrowAmount = bwPaymentDetail.getBorrowAmount();
				if (realOverdueAmount == null) {
					realOverdueAmount = 0.0;
				}
				if (borrowAmount == null) {
					borrowAmount = 0.0;
				}
				alreadyRepayMoney = DoubleUtil.add(realOverdueAmount, borrowAmount);
			}

			// 修改还款计划状态
			// 已还款金额
			BwRepaymentPlan updatePlan = new BwRepaymentPlan();
			updatePlan.setId(plan.getId());
			updatePlan.setAlreadyRepayMoney(alreadyRepayMoney);
			updatePlan.setUpdateTime(new Date());
			bwRepaymentPlanService.update(updatePlan);

			if (bwPaymentDetail != null) {// 老用户没有bwPaymentDetail不执行清零操作
				// 还款已扣除逾期罚息，清理逾期记录金额，续贷会在定时任务XuDaiJob执行
				bwOverdueRecordService.updateBwOverdueRecordMoney(bwOrder);
			}
			logger.info("扣款金额：" + trade_amount + "，是否插入划拨记录：" + !NumberUtil.isFushu(trade_amount));
			if (!NumberUtil.isFushu(trade_amount)) {
				int plantFormInt = bwPlatformRecordService.saveOrUpdateByTradeNo(bwPlatformRecord);
				logger.info("划拨记录新增条数=====" + plantFormInt);
			}

			if (isDelRedis) {
				RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + bwOrder.getId());
				logger.info("删除连连redis成功============");
			}

			BwBorrower borrower = bwBorrowerService.findBwBorrowerById(bwOrder.getBorrowerId());
			// MsgReqData msgReqData = new MsgReqData();
			// msgReqData.setPhone(borrower.getPhone());
			// String msg = "【水象借点花】尊敬的" + borrower.getName() + ",您于"
			// + DateUtil.getDateString(new Date(), DateUtil.yyyy_MM_dd) + "成功还款"
			// + DoubleUtil.toTwoDecimal(Double.valueOf(trade_amount)) + "元。点滴信用，弥足珍贵，水象祝您生活愉快！";
			// msgReqData.setMsg(msg);
			// msgReqData.setType(SystemConstant.MESSAGE_CIRCUMSTANCE);
			// Response<Object> rsp = BeadWalletSendMsgService.sendMsg(msgReqData);
			String phones = borrower.getPhone();
			String msg = "尊敬的" + borrower.getName() + ",您于" + DateUtil.getDateString(new Date(), DateUtil.yyyy_MM_dd)
					+ "成功还款" + DoubleUtil.toTwoDecimal(Double.valueOf(trade_amount)) + "元。点滴信用，弥足珍贵，水象祝您生活愉快！";
			boolean bo = sendMessageCommonService.dhstSendMessage(phones, msg);
			// if (bo) {
			// logger.info("还款短信发送成功,工单Id:" + bwOrder.getId());
			// } else {
			// logger.info("还款短信发送失败,工单Id:" + bwOrder.getId());
			// }
			MessageDto messageDto = new MessageDto();
			messageDto.setBusinessScenario("2");
			messageDto.setPhone(borrower.getPhone());
			messageDto.setMsg(msg);
			messageDto.setType("1");
			RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
			// if (rsp.getRequestCode().equals("200")) {
			// logger.info("还款短信发送成功,工单Id:" + bwOrder.getId());
			// } else {
			// logger.info("还款短信发送失败,工单Id:" + bwOrder.getId());
			// }
			logger.info("====================交易成功======================");
			notice.setRet_code("000");
			notice.setRet_msg("交易成功");
			return notice;
		} catch (Exception e) {
			logger.error("还款回调异常", e);
			notice.setRet_code("103");
			notice.setRet_msg("交易失败");
			throw new RuntimeException("还款回调异常", e);
		}
	}

	/**
	 * 
	 * @param bwOrder
	 * @param repayMoney
	 */
	@Override
	public void updateAndUseCoupon(BwOrder bwOrder, String repayMoney) {
		// 获取最大使用劵
		ActivityDiscountDistribute activityDiscountDistribute = activityDiscountDistributeService
				.findMaxActivityDiscountDistribute(bwOrder.getBorrowerId());
		// 如果用户有优惠券则使用
		if (!CommUtils.isNull(activityDiscountDistribute)
				&& !CommUtils.isNull(activityDiscountDistribute.getDiscountId())) {
			// 获得可使用的优惠券，并修改优惠券的使用数量
			Integer canUserNum = activityDiscountDistribute.getTotalNumber()
					- activityDiscountDistribute.getUseNumber();
			if (canUserNum - 1 > 0) {
				activityDiscountDistribute.setNumber(activityDiscountDistribute.getNumber() - 1);
				activityDiscountDistribute.setUseNumber(activityDiscountDistribute.getUseNumber() + 1);
			} else {
				activityDiscountDistribute.setNumber(0);
				activityDiscountDistribute.setUseNumber(activityDiscountDistribute.getTotalNumber());
			}
			logger.info("工单Id" + bwOrder.getId() + ",优惠券distributeId：" + activityDiscountDistribute.getDistributeId()
					+ "，总共有：" + activityDiscountDistribute.getTotalNumber() + "张，使用后剩余："
					+ activityDiscountDistribute.getNumber() + "张");
			// 修改优惠派发表
			activityDiscountDistributeService.updateActivityDiscountDistribute(activityDiscountDistribute);
			// 优惠券使用记录
			ActivityDiscountUseage activityDiscountUseage = new ActivityDiscountUseage();
			activityDiscountUseage.setDistributeId(activityDiscountDistribute.getDistributeId());// 红包派发表主键
			activityDiscountUseage.setUseageType(1);// 设置正常还款1；续贷2
			activityDiscountUseage.setCreateTime(new Date());// 设置创建时间
			activityDiscountUseage.setUseageTime(activityDiscountUseage.getCreateTime());// 设置使用时间
			activityDiscountUseage.setOrderId(Integer.parseInt(bwOrder.getId().toString()));// 关联流水号表ID
			// 计划还款金额大于红包金额
			if (NumberUtil.isLarge(repayMoney, activityDiscountDistribute.getAmount().toString())) {
				// 红包金额
				activityDiscountUseage.setUseAmount(activityDiscountDistribute.getAmount());
			} else {
				// 计划还款金额
				activityDiscountUseage.setUseAmount(Double.parseDouble(repayMoney));
			}
			activityDiscountUseageService.addActivityDiscountUseageService(activityDiscountUseage);
		}
	}

	/**
	 * 查询我的邀请
	 * 
	 * @see ActivityService#getMyInvitation(com.waterelephant.entity.ActivityParticipate)
	 */
	@Override
	public Object getMyInvitation(ActivityDiscountDistribute entity) {
		try {
			// 借款人Id
			Integer borrowId = entity.getBorrowId();
			// 查询邀请总人数
			String totalPeopleSql = "select count(a.id) from (select * from bw_borrower  where borrower_id=" + borrowId
					+ ") a left join(select * from bw_order  where borrower_id in (select id from bw_borrower  where borrower_id="
					+ borrowId + ")) b on a.id=b.borrower_id where b.status_id in(6,9,13)";
			Integer totalPeople = sqlMapper.selectOne(totalPeopleSql, Integer.class);
			// 查询邀请总收益
			String totalMoneySql = "select ifnull(sum(amount),0.00) from activity_discount_distribute where distribute_type=2 and borrow_id='"
					+ entity.getBorrowId() + "'";

			Double totalMoney = sqlMapper.selectOne(totalMoneySql, Double.class);
			String detailsSql = "select DISTINCT(a.phone)  , if(b.status_id in (6,9,13),'成功','失败')  as status_name  from "
					+ "(select * from bw_borrower  where borrower_id=" + borrowId + ") a left join"
					+ "(select * from bw_order  where borrower_id in (select id from bw_borrower  where borrower_id="
					+ borrowId + ")) b" + " on	a.id=b.borrower_id 	 ";

			// 查询收益明细
			List<Map<String, Object>> details = sqlMapper.selectList(detailsSql);

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("totalPeople", totalPeople);// 总人数
			map.put("totalMoney", totalMoney);// 总收益
			map.put("details", details);
			return map;
		} catch (Exception e) {
			logger.error("查询我的邀请失败", e);
			return null;
		}
	}

	/**
	 * 查询用户所有劵
	 * 
	 * @see ActivityService#getCoupon(com.waterelephant.entity.ActivityDiscountInfo)
	 */
	@Override
	public Object getCoupon(ActivityDiscountDistribute entity) {
		try {
			String sql = "select * from activity_discount_distribute b where 1=1 ";

			StringBuffer sqlBuffer = new StringBuffer(sql);

			if (!CommUtils.isNull(entity.getBorrowId())) {
				sqlBuffer.append(" and b.borrow_id=" + entity.getBorrowId());
			}
			List<ActivityDiscountDistribute> detail = sqlMapper.selectList(sqlBuffer.toString(),
					ActivityDiscountDistribute.class);

			if (!CommUtils.isNull(entity.getDistributeType())) {
				sqlBuffer.append(" and b.distribute_type=" + entity.getDistributeType());
			}
			List<ActivityDiscountDistribute> details = sqlMapper.selectList(sqlBuffer.toString(),
					ActivityDiscountDistribute.class); // 查询所有优惠券信息
			Double sumAmount = 0.00;
			Integer sum = 0;

			for (ActivityDiscountDistribute activityDiscountDistribute : detail) {
				Double amount = activityDiscountDistribute.getAmount();
				Integer number = activityDiscountDistribute.getNumber();
				sumAmount += amount * number;
				sum += number;
			}

			if (CollectionUtils.isEmpty(details)) {
				details = new ArrayList<ActivityDiscountDistribute>();
			}
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDataProcessorImpl("yyyy-MM-dd"));
			JSONArray jr = JSONArray.fromObject(details, jsonConfig);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("details", jr);
			map.put("sumAmount", sumAmount);
			map.put("sum", sum);
			return map;
		} catch (Exception e) {
			logger.error("查询用户所有劵失败", e);
			return null;
		}
	}

	/**
	 * 查询用户最大能使用劵
	 * 
	 * @see ActivityService#getMaxCoupon(com.waterelephant.entity.ActivityDiscountInfo)
	 */
	@Override
	public Object getMaxCoupon(ActivityDiscountDistribute entity) {

		ActivityDiscountDistribute activityDiscountDistribute = activityDiscountDistributeService
				.findMaxActivityDiscountDistribute(Integer.valueOf(entity.getBorrowId()).longValue());
		if (activityDiscountDistribute == null) {
			activityDiscountDistribute = new ActivityDiscountDistribute();
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDataProcessorImpl("yyyy-MM-dd"));
		JSONObject json = JSONObject.fromObject(activityDiscountDistribute, jsonConfig);
		return json;
	}

	@Override
	public void updateAndDistributeToInviteFriends() {
		// 1.查询最新的邀请好友活动信息
		ActivityInfo paramActivityInfo = new ActivityInfo();
		paramActivityInfo.setActivityType(ActivityConstant.ACTIVITY_TYPE.INVITE_FRIENDS);
		paramActivityInfo.setStatus(1);
		ActivityInfo queryActivityInfo = activityInfoService.queryLastActivityInfo(paramActivityInfo);
		if (queryActivityInfo != null && queryActivityInfo.getActivityId() != null) {
			Integer activityId = queryActivityInfo.getActivityId();
			// 2.查询最新邀请好友活动对应的优惠信息列表
			List<ActivityDiscountInfo> discountInfoList = activityDiscountInfoService
					.getActinityDiscountInfo(activityId);
			if (discountInfoList != null && !discountInfoList.isEmpty()) {
				Collections.sort(discountInfoList, new Comparator<ActivityDiscountInfo>() {
					@Override
					public int compare(ActivityDiscountInfo o1, ActivityDiscountInfo o2) {
						return o1.getInvitedNumber().compareTo(o2.getInvitedNumber());
					}
				});
				// 获取邀请人数量invitedNumber最小的活动优惠信息
				ActivityDiscountInfo minNumDiscountInfo = discountInfoList.get(0);
				Integer minInvitedNumber = minNumDiscountInfo.getInvitedNumber();
				// 3.统计借款人邀请人数量
				List<Map<String, Object>> invitedNumStatisList = bwBorrowerService
						.getInvitedNumStatis(queryActivityInfo, minInvitedNumber);
				if (invitedNumStatisList != null && !invitedNumStatisList.isEmpty()) {
					Date nowDate = new Date();
					for (Map<String, Object> invitedNumStatisMap : invitedNumStatisList) {
						// 借款人ID
						int borrowId = Integer.parseInt(String.valueOf(invitedNumStatisMap.get("borrower_id")));
						// 借款人邀请好友数量
						int invited_num = Integer.parseInt(String.valueOf(invitedNumStatisMap.get("invited_num")));
						// 4.可发放给用户最高的优惠券
						ActivityDiscountInfo activityDiscountInfo = activityDiscountInfoService
								.getCanInvitedMaxActinityDiscountInfo(discountInfoList, invited_num);
						if (activityDiscountInfo != null) {
							// 5.发放优惠券
							ActivityDiscountDistribute activityDiscountDistribute = new ActivityDiscountDistribute();
							activityDiscountDistribute.setBorrowId(borrowId);
							activityDiscountDistribute.setActivityId(activityId);
							activityDiscountDistribute.setDiscountId(activityDiscountInfo.getDiscountId());
							activityDiscountDistribute.setNumber(1);
							activityDiscountDistribute.setAmount(activityDiscountInfo.getBonusAmount());
							activityDiscountDistribute.setEffective(1);
							activityDiscountDistribute.setCreateTime(nowDate);
							activityDiscountDistribute
									.setExpiryTime(activityInfoService.getExpiryTime(nowDate, queryActivityInfo));
							activityDiscountDistribute.setDistributeType("2");
							activityDiscountDistributeService.addActivityDiscountDistribute(activityDiscountDistribute);
							logger.info("派发优惠券奖励给老用户邀请好友：【" + activityDiscountDistribute.toString() + "】");
						}
					}
				}
			}
		}
	}

	// =====================================================王坤
	@Override
	public String getRealityTrandeAmount(String isUseCoupon, String amount, Long borrowerId) {
		logger.info("-----------------计划还款金额为" + amount + "-----------------");
		if (isUseCoupon.equals("1")) {// 使用优惠券
			ActivityDiscountDistribute activityDiscountDistribute = activityDiscountDistributeService
					.findMaxActivityDiscountDistribute(borrowerId);
			if (!CommUtils.isNull(activityDiscountDistribute) && activityDiscountDistribute.getActivityId() != null) {
				logger.info("-----------------最大优惠券金额为" + activityDiscountDistribute.getAmount() + "-----------------");
				// 实际支付金额为
				Double relpay = DoubleUtil.sub(Double.parseDouble(amount),
						Double.parseDouble(activityDiscountDistribute.getAmount().toString()));
				if (relpay <= 0) {
					logger.info("-----------------实际还款金额为0.00-----------------");
					return "0";
				}
				logger.info("-----------------实际支付金额为" + relpay + "-----------------");
				return relpay.toString();
			}
		}
		return amount;
	}

	/**
	 * 新手注册
	 */
	@Override
	public void addActivityDistribution(BwOrder order) {
		try {
			// 查询新用户注册活动
			ActivityInfo queryEntity = new ActivityInfo();
			queryEntity.setActivityType(ActivityConstant.ACTIVITY_TYPE.NEW_REGISTER);
			ActivityInfo activityInfo = activityInfoService.queryActivityInfo(queryEntity);
			logger.info("活动类型" + activityInfo.getActivityType() + ",活动编号：" + activityInfo.getActivityId());
			// 查询借款人
			BwBorrower borrower = bwBorrowerService.findBwBorrowerByOrderId(order.getId());
			Integer borrowId = Integer.parseInt(borrower.getId().toString());
			// 判断是否已经参加活动
			boolean flag = activityParticipateService.isAlreadyParticipate(borrowId, activityInfo.getActivityId());
			if (flag) {
				logger.info(ActivityConstant.MSG_BEFORE + "借贷人id：" + borrowId + ",已参加过新用户注册活动");
				return;
			}

			if (CommUtils.isNull(activityInfo)) {
				logger.info(ActivityConstant.MSG_BEFORE + "新用户活动不存在");
				return;
			}
			List<ActivityDiscountInfo> activityDiscountInfos = activityDiscountInfoService
					.getActinityDiscountInfo(activityInfo.getActivityId());
			if (CollectionUtils.isEmpty(activityDiscountInfos)) {
				logger.info(ActivityConstant.MSG_BEFORE + "活动Id:" + activityInfo.getActivityId() + ",活动配置信息不存在");
				return;
			}
			Date now = new Date();// 当前时间
			if (DateUtil.isBeforeTime(now, activityInfo.getStartTime())) {
				logger.info(ActivityConstant.MSG_BEFORE + "新用户活动还未开始");
				return;
			}
			if (DateUtil.isBeforeTime(activityInfo.getEndTime(), now)) {
				logger.info(ActivityConstant.MSG_BEFORE + "新用户活动已过期");
				return;
			}

			// 注册时间
			Date createTime = borrower.getCreateTime();
			int timeInterval = DateUtil.intervalMinute(createTime, now);
			logger.info(ActivityConstant.MSG_BEFORE + "用户认证时间为：" + timeInterval + "分钟");
			if (timeInterval > activityInfo.getLimitedTime()) {
				logger.info(ActivityConstant.MSG_BEFORE + "用户完成认证时间为：" + timeInterval + "分钟,大于活动限定时间："
						+ activityInfo.getLimitedTime() + "分钟");
				return;
			}

			ActivityParticipate activityParticipate = new ActivityParticipate();
			activityParticipate.setBorrowId(borrowId);
			activityParticipate.setActivityId(activityInfo.getActivityId());
			activityParticipate.setCreateTime(new Date());
			activityParticipate.setParticipationTime(new Date());
			activityParticipateService.addParticipate(activityParticipate);
			for (ActivityDiscountInfo discountInfo : activityDiscountInfos) {
				ActivityDiscountDistribute activityDiscountDistribute = new ActivityDiscountDistribute();// 创建活动优惠派送信息
				activityDiscountDistribute.setBorrowId(borrowId);// 设置借款人id
				activityDiscountDistribute.setDiscountId(discountInfo.getDiscountId());// 设置优惠信息主键
				activityDiscountDistribute.setActivityId(discountInfo.getActivityId());// 设置活动id
				activityDiscountDistribute.setDistributeType(ActivityConstant.ACTIVITY_TYPE.NEW_REGISTER);
				activityDiscountDistribute.setTotalNumber(discountInfo.getNumber());// 设置优惠券总数
				activityDiscountDistribute.setUseNumber(0);// 已使用券为0
				activityDiscountDistribute.setNumber(discountInfo.getNumber());// 设置剩余张数是总数
				activityDiscountDistribute.setAmount(discountInfo.getBonusAmount());// 设置奖励金额
				activityDiscountDistribute.setLoanAmount(discountInfo.getLoanAmount());// 设置限定金额
				activityDiscountDistribute.setCreateTime(discountInfo.getCreateTime());// 设置创建时间
				activityDiscountDistribute.setExpiryTime(activityInfoService.getExpiryTime(new Date(), activityInfo));// 设置截止时间
				activityDiscountDistribute.setEffective(1);
				activityDiscountDistributeService.addActivityDiscountDistribute(activityDiscountDistribute);
			}
			logger.info(ActivityConstant.MSG_BEFORE + "借贷人id：" + borrowId + ",参加新手活动成功");
		} catch (Exception e) {
			logger.error(ActivityConstant.MSG_BEFORE + "参加新手活动失败", e);
		}
	}

	/**
	 * 初始化参数：1、添加用户信息，2、四要素认证，3、添加工单（草稿）
	 * 
	 * @see ActivityService#addInitBorrow()
	 */
	@Override
	public BwBorrower addInitBorrow() {
		// 添加用户信息
		// BwBorrower borrower = new BwBorrower();
		// borrower.setFuiouAcct("15926352710");
		// borrower.setPhone("15926352710");
		// borrower.setPhone("e10adc3949ba59abbe56e057f20f883e");
		// borrower.setName("张三");
		// borrower.setIdCard("420113198905020933");
		// borrower.setState(1);
		// borrower.setAuthStep(4);
		// borrower.setCreateTime(new Date());
		// bwBorrowerService.insert(borrower);
		// 绑定银行卡
		BwBankCard bankCard = new BwBankCard();
		bankCard.setBorrowerId(15926352726l);
		bankCard.setCardNo(getRandom(19));
		bankCard.setProvinceCode("3");
		bankCard.setCityCode("5210");
		bankCard.setBankCode("0102");
		bankCard.setBankName("中国工商银行");
		bankCard.setSignStatus(2);
		bankCard.setCreateTime(new Date());
		bankCardService.addBwBankCard(bankCard);
		// 初始化工单
		// BwOrder order = new BwOrder();
		// order.setOrderNo("A" + getRandom(14));
		// order.setMark(getRandom(2));
		// order.setCreditLimit(500l);
		// order.setBorrowAmount(500.00);
		// order.setBorrowUse("生活消费");
		// order.setRepayTerm(1);
		// order.setRejectType(1);
		// order.setBorrowRate(0.09000000);
		// order.setContractRate(0.00000000);
		// order.setContractMonthRate(0.00000000);
		// order.setContractAmount(500.00);
		// order.setStatusId(1l);
		// order.setBorrowerId(borrower.getId());
		// order.setAvoidFineDate(3);
		// order.setCreateTime(new Date());
		// order.setFlag(1);
		// order.setApplyPayStatus(0);
		// order.setChannel(3);
		// order.setProductId(1);
		// order.setRank(1);
		// // 四要素认证
		// for (int i = 1; i <= 4; i++) {
		// BwOrderAuth orderAuth = new BwOrderAuth();
		// orderAuth.setOrderId(order.getId());
		// orderAuth.setAuth_type(i);
		// orderAuth.setAuth_channel(1);
		// orderAuth.setCreateTime(new Date());
		// }
		return null;
	}

	public String getRandom(int s) {
		String result = "";
		int max = 9;
		int min = 0;
		Random random = new Random();
		for (int i = 0; i < s; i++) {
			Integer k = random.nextInt(max) % (max - min + 1) + min;
			result += k.toString();
		}
		return result;
	}

	/**
	 * 待还款
	 * 
	 * @see ActivityService#pendingRepayment()
	 */
	@Override
	public void pendingRepayment() {
	}

	@Override
	public Object findListCoupon(Integer borrowId) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sql = "select * from activity_discount_distribute b where 1=1 ";
			StringBuffer sqlBuffer = new StringBuffer(sql);
			if (!CommUtils.isNull(borrowId)) {
				sqlBuffer.append(" and b.borrow_id=" + borrowId);
			}
			List<ActivityDiscountDistribute> detail = sqlMapper.selectList(sqlBuffer.toString(),
					ActivityDiscountDistribute.class);// 查询出该借款人所有的优惠券
			Double sumAmount = 0.00;
			Integer sum = 0;
			if (CollectionUtils.isEmpty(detail)) {
				detail = new ArrayList<ActivityDiscountDistribute>();
				map.put("details", detail);
				map.put("sumAmount", sumAmount);
				map.put("sum", sum);
				return map;
			}
			for (ActivityDiscountDistribute activityDiscountDistribute : detail) {
				Date expiryTime = activityDiscountDistribute.getExpiryTime();// 过期时间
				int daySpace = MyDateUtils.getDaySpace(expiryTime, new Date());
				if (daySpace > 0) {
					activityDiscountDistribute.setIsOverTime(1);
				} else {
					activityDiscountDistribute.setIsOverTime(0);
				}

				Double amount = activityDiscountDistribute.getAmount();
				Integer number = activityDiscountDistribute.getNumber();
				sumAmount += amount * number;
				sum += number;
			}
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDataProcessorImpl("yyyy-MM-dd"));
			JSONArray jr = JSONArray.fromObject(detail, jsonConfig);
			map.put("details", jr);
			map.put("sumAmount", sumAmount);
			map.put("sum", sum);
			return map;
		} catch (Exception e) {
			logger.error("查询用户所有劵失败", e);
			return null;
		}
	}
}
