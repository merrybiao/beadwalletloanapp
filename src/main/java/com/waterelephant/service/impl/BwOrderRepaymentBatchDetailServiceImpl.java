/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.waterelephant.mapper.BwOrderRepaymentBatchDetailMapper;
import com.waterelephant.utils.*;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.dto.LoanInfo;
import com.waterelephant.dto.RepaymentBatch;
import com.waterelephant.dto.RepaymentBatchDetail;
import com.waterelephant.entity.ActivityDiscountDistribute;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderRepaymentBatchDetail;
import com.waterelephant.entity.BwOverdueRecord;
import com.waterelephant.entity.BwPaymentDetail;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.installment.service.OrderService;
import com.waterelephant.service.ActivityDiscountDistributeService;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOrderRepaymentBatchDetailService;
import com.waterelephant.service.BwOverdueRecordService;
import com.waterelephant.service.BwPaymentDetailService;
import com.waterelephant.service.BwPlatformRecordService;
import com.waterelephant.service.IBwBankCardService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 
 * 
 * Module:
 * 
 * BwOrderRepaymentBatchDetailImpl.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class BwOrderRepaymentBatchDetailServiceImpl extends BaseService<BwOrderRepaymentBatchDetail, Long>
		implements BwOrderRepaymentBatchDetailService {
	private Logger logger = Logger.getLogger(BwOrderRepaymentBatchDetailServiceImpl.class);
	@Autowired
	private BwPaymentDetailService bwPaymentDetailService;
	@Autowired
	private BwRepaymentPlanService bwRepaymentPlanService;
	@Autowired
	private ProductService productService;
	@Autowired
	private BwOrderService bwOrderService;
	@Autowired
	private BwOverdueRecordService bwOverdueRecordService;
	@Autowired
	private ActivityDiscountDistributeService activityDiscountDistributeService;
	@Autowired
	private IBwBankCardService bwBankCardService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private BwPlatformRecordService bwPlatformRecordService;
	@Autowired
	private BwOrderRepaymentBatchDetailMapper bwOrderRepaymentBatchDetailMapper;

	/**
	 * 保存分批还款明细
	 * 
	 * @see com.waterelephant.service.BwOrderRepaymentBatchDetailService#saveBwOrderRepaymentBatchDetail(com.waterelephant.entity.BwOrderRepaymentBatchDetail,
	 *      java.lang.Long)
	 */
	@Override
	public void saveBwOrderRepaymentBatchDetail(BwOrderRepaymentBatchDetail entity, Long orderId) {
		// 查询分批还款明细
		RepaymentBatch repaymentBatch = getRepaymentBatch(orderId);
		// 获取当前期数
		entity.setNumber(repaymentBatch.getCurrentNumber());
		// 设置工单Id
		entity.setOrderId(orderId);
		mapper.insert(entity);
	}

	/**
	 * 查询分批还款信息
	 * 
	 * @see com.waterelephant.service.BwOrderRepaymentBatchDetailService#getRepaymentBatch(java.lang.Long)
	 */
	@Override
	public RepaymentBatch getRepaymentBatch(Long orderId) {
		RepaymentBatch repaymentBatch = bwOrderRepaymentBatchDetailMapper.getRepaymentBatch(orderId);
		Double alreadyTotalBatchMoney = selectHasRepaymentAmount(orderId);
		if (repaymentBatch == null) {
			repaymentBatch = new RepaymentBatch();
			repaymentBatch.setCurrentNumber(1);
			repaymentBatch.setTotalNumber(0);
		}
		// 分批记录总金额大于还款计划已还款金额时，取分批金额（一般两者一样）
		if (repaymentBatch != null && repaymentBatch.getAlreadyTotalBatchMoney() != null
				&& repaymentBatch.getAlreadyTotalBatchMoney() > alreadyTotalBatchMoney) {
			alreadyTotalBatchMoney = repaymentBatch.getAlreadyTotalBatchMoney();
		}
		repaymentBatch.setAlreadyTotalBatchMoney(alreadyTotalBatchMoney);
		return repaymentBatch;
	}

	/**
	 * 根据工单还款计划已还金额字段查询总共已还金额
	 *
	 * @param orderId
	 * @return
	 */
	private Double selectHasRepaymentAmount(Long orderId) {
		Double hasRepaymentAmount = bwOrderRepaymentBatchDetailMapper.selectHasRepaymentMoney(orderId);
		// 展期中
		if (orderId == null || hasRepaymentAmount == null || RedisUtils.hexists(SystemConstant.WEIXIN_ORDER_ID, orderId.toString())) {
			hasRepaymentAmount = 0.0;
		}
		return hasRepaymentAmount;
	}

	/**
	 * 查询分批还款明细
	 * 
	 * @see com.waterelephant.service.BwOrderRepaymentBatchDetailService#getRepaymentBatchDetailList(java.lang.Long)
	 */
	@Override
	public List<RepaymentBatchDetail> getRepaymentBatchDetailList(Long orderId) {
		List<RepaymentBatchDetail> reBatchDetails = bwOrderRepaymentBatchDetailMapper.getRepaymentBatchDetailList(orderId);
		return CollectionUtils.isEmpty(reBatchDetails) ? new ArrayList<>() : reBatchDetails;
	}

	@Override
	public BwPaymentDetail saveBatchDetailAndRepaymentDetailByRedis(Long orderId, Long repayId, boolean isDelRedis) {
		String bwOrderRepaymentBatchDetailStr = RedisUtils.hget(RedisKeyConstant.BATCH_REPAYMENT_DETAIL,
				String.valueOf(orderId));
		logger.info("===============orderId：" + orderId + "，repayId：" + repayId + "，保存批量还款信息："
				+ bwOrderRepaymentBatchDetailStr);
		if (StringUtils.isNotEmpty(bwOrderRepaymentBatchDetailStr)) {
			BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail = JSON.parseObject(bwOrderRepaymentBatchDetailStr,
					BwOrderRepaymentBatchDetail.class);
			if (bwOrderRepaymentBatchDetail != null) {
				bwOrderRepaymentBatchDetail.setTradeTime(new Date());
				saveOrUpdateByOrderAndNumber(bwOrderRepaymentBatchDetail);
			}
		}
		BwPaymentDetail paymentDetail = bwPaymentDetailService.saveOrUpdateByRedis(repayId, isDelRedis);
		if (isDelRedis) {
			RedisUtils.hdel(RedisKeyConstant.BATCH_REPAYMENT_DETAIL, String.valueOf(orderId));
		}
		return paymentDetail;
	}

	@Override
	public void deleteBatchDetailAndRepaymentDetailRedis(Long orderId, Long repayId) {
		RedisUtils.hdel(RedisKeyConstant.BATCH_REPAYMENT_DETAIL, String.valueOf(orderId));
		bwPaymentDetailService.deleteRedis(repayId);
	}

	@Override
	public BwOrderRepaymentBatchDetail getLastBatchDetail(Long orderId) {
		BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail = null;
		if (orderId != null) {
			Example example = new Example(BwOrderRepaymentBatchDetail.class);
			example.setOrderByClause("number DESC");
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("orderId", orderId);
			List<BwOrderRepaymentBatchDetail> list = mapper.selectByExample(example);
			if (list != null && !list.isEmpty()) {
				bwOrderRepaymentBatchDetail = list.get(0);
			}
		}
		return bwOrderRepaymentBatchDetail;
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwOrderRepaymentBatchDetailService#getBatchInfo(java.lang.Long)
	 */
	@Override
	public Object getBatchInfo(Long orderId) {
		BwOrder bwOrder = bwOrderService.selectByPrimaryKey(orderId);
		Integer productType = bwOrder.getProductType();
		if (productType == null) {
			productType = 1;
		}
		Double batchDetailTotal = null;// 已还总额
		if (productType == 2) {
			batchDetailTotal = bwPlatformRecordService.getAlreadyTotal(orderId);
		} else {
			batchDetailTotal = getBatchDetailTotal(orderId);// 已还总额
		}
		List<RepaymentBatchDetail> batchDetails = getRepaymentBatchDetailList(orderId);
		int isBatch = 0;// 是否分批
		if (batchDetails != null && !batchDetails.isEmpty()) {
			isBatch = 1;
			if (batchDetails.size() == 1) {
				boolean selectIsBatch = selectIsBatch(orderId);
				if (!selectIsBatch) {
					isBatch = 0;
				}
			}
			if (isBatch == 0) {
				batchDetails.clear();
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("batchDetails", batchDetails);// 分批详情
		map.put("isBatch", isBatch);// 是否分批
		map.put("statusId", bwOrder.getStatusId());// 工单状态
		Double totalNotCouponAmount = 0.0;
		if (productType == 2) {// 分期
			BwProductDictionary product = productService.selectCurrentByProductType(productType);
			Map<String, Object> multiOrderRepayMap = orderService.getMultiOrderRepay(orderId);
			multiOrderRepayMap.get("totalOverAmount");
			List<BwRepaymentPlan> planList = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(orderId);
			for (BwRepaymentPlan plan : planList) {
				LoanInfo loanInfo = new LoanInfo();
				loanInfo.setAmt(bwOrder.getBorrowAmount().toString());
				Double realityRepayMoney = plan.getRealityRepayMoney();
				productService.calcRepaymentCost(realityRepayMoney, orderId, plan.getId(), product, loanInfo);
				double realOverdueAmount = NumberUtil.parseDouble(loanInfo.getRealOverdueAmount(), 0.0);
				totalNotCouponAmount = DoubleUtil.add(DoubleUtil.add(totalNotCouponAmount, realityRepayMoney),
						realOverdueAmount);
			}
			Double overAmount = NumberUtil.parseDouble(multiOrderRepayMap.get("overAmount") + "", 0.0);
			double realityRepayMoney = DoubleUtil
					.add(NumberUtil.parseDouble(multiOrderRepayMap.get("realityRepayMoney") + "", 0.0), overAmount);
			map.put("realityRepayMoney", DoubleUtil.toTwoDecimal(realityRepayMoney));// 分期当期应还金额
			map.put("overAmont", DoubleUtil.toTwoDecimal(overAmount));
		} else {
			// 查询还款计划
			BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(orderId);
			if (batchDetailTotal == null || batchDetailTotal <= 0.0) {
				batchDetailTotal = bwRepaymentPlan.getAlreadyRepayMoney();
			}
			Double borrowAmount = bwRepaymentPlan.getRealityRepayMoney();// 借款金额
			Double overAmont = productService.calcOverdueCost(orderId, bwRepaymentPlan.getId(), null);// 实际应扣逾期金额(减去免罚息)
			// 湛江委
			Double zjw = bwRepaymentPlan.getZjw();
			if (zjw == null || zjw < 0) {
				zjw = 0.0;
			}
			totalNotCouponAmount = DoubleUtil.add(borrowAmount, overAmont); // 应还总额
			map.put("zjwAmount", DoubleUtil.toTwoDecimal(zjw));
			map.put("overAmont", DoubleUtil.toTwoDecimal(overAmont));
		}
		if (batchDetailTotal == null) {
			batchDetailTotal = 0.0;
		}
		map.put("borrowAmount", DoubleUtil.toTwoDecimal(bwOrder.getBorrowAmount()));
		map.put("remainTotal", DoubleUtil.toTwoDecimal(DoubleUtil.sub(totalNotCouponAmount, batchDetailTotal)));// 剩余总额
		map.put("totalNotCouponAmount", DoubleUtil.toTwoDecimal(totalNotCouponAmount));// 应还总额
		map.put("batchTotal", DoubleUtil.toTwoDecimal(batchDetailTotal));// 已还总额
		map.put("productType", productType);// 1.单期 2.多期
		Double maxAmount = 0.0;
		if (batchDetailTotal <= 0.0 && productType == 1) {// 未分批且单期才能使用优惠券
			// 查询最大的优惠金额
			ActivityDiscountDistribute activityDiscountDistribute = activityDiscountDistributeService
					.findMaxActivityDiscountDistribute(bwOrder.getBorrowerId());
			if (activityDiscountDistribute.getAmount() != null) {
				maxAmount = activityDiscountDistribute.getAmount();
			}
		}
		map.put("maxAmount", DoubleUtil.toTwoDecimal(maxAmount));// 最大可以使用优惠券金额
		map.put("totalAmount", DoubleUtil.toTwoDecimal(DoubleUtil.sub(totalNotCouponAmount, maxAmount)));// 扣除优惠券还款总额
		if (productType == null || productType == 1) {
			map.put("realityRepayMoney", map.get("remainTotal"));// 单期应还金额
		}
		// 银行卡
		BwBankCard bankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
		if (bankCard != null) {
			String cardNo = bankCard.getCardNo();
			String bankName = RedisUtils.hget(RedisKeyConstant.FUIOU_BANK, bankCard.getBankCode());
			if (StringUtils.isNotEmpty(bankName) && bankName.contains("公司") && bankName.contains("银行")) {
				bankName = bankName.substring(0, bankName.indexOf("银行") + 2);
			}
			map.put("cardNoEnd", cardNo.substring(cardNo.length() - 4));// 银行卡后四位
			map.put("bankName", bankName);// 银行卡名称
		}
		logger.info("【BwOrderRepaymentBatchDetailServiceImpl.getBatchInfo】borrowerId：" + bwOrder.getBorrowerId()
				+ "，orderId：" + orderId + "，productType：" + productType + "，查询信息：" + JSON.toJSONString(map));
		return map;
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwOrderRepaymentBatchDetailService#getBatchDetailTotal(java.lang.Long)
	 */
	@Override
	public Double getBatchDetailTotal(Long orderId) {
		RepaymentBatch repaymentBatch = getRepaymentBatch(orderId);
		return repaymentBatch.getAlreadyTotalBatchMoney();
	}

	@Override
	public void saveOrUpdateByOrderAndNumber(BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail) {
		if (bwOrderRepaymentBatchDetail == null) {
			return;
		}
		Long orderId = bwOrderRepaymentBatchDetail.getOrderId();
		Integer number = bwOrderRepaymentBatchDetail.getNumber();
		Date nowDate = new Date();
		BwOrderRepaymentBatchDetail queryBatchDetail = null;
		if (orderId != null && orderId > 0L && number != null) {
			BwOrderRepaymentBatchDetail paramBatchDetail = new BwOrderRepaymentBatchDetail();
			paramBatchDetail.setOrderId(orderId);
			paramBatchDetail.setNumber(number);
			queryBatchDetail = mapper.selectOne(paramBatchDetail);
		}
		bwOrderRepaymentBatchDetail.setUpdateTime(nowDate);
		if (queryBatchDetail == null) {
			bwOrderRepaymentBatchDetail.setCreateTime(nowDate);
			mapper.insertSelective(bwOrderRepaymentBatchDetail);
		} else {
			bwOrderRepaymentBatchDetail.setId(queryBatchDetail.getId());
			mapper.updateByPrimaryKeySelective(bwOrderRepaymentBatchDetail);
		}
	}

	public AppResponseResult saveOrUpdate4BatchRepayment(Long orderId, Double batchAmount, Integer repaymentChannel,
			boolean isSaveRedis) {
		BwOrder queryBwOrder = bwOrderService.findBwOrderById(orderId + "");
		AppResponseResult result = new AppResponseResult();
		if (queryBwOrder == null) {
			result.setCode("201");
			result.setMsg("查不到工单");
			return result;
		}
		BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(orderId);
		if (CommUtils.isNull(bwRepaymentPlan)) {
			result.setCode("202");
			result.setMsg("还款计划不存在");
			return result;
		}
		// 逾期记录
		BwOverdueRecord paramOverdueRecord = new BwOverdueRecord();
		paramOverdueRecord.setOrderId(orderId);
		paramOverdueRecord.setRepayId(bwRepaymentPlan.getId());
		BwOverdueRecord bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(paramOverdueRecord);
		Integer overdueDay = 0;
		if (bwOverdueRecord != null) {
			overdueDay = bwOverdueRecord.getOverdueDay();
		}
		BwProductDictionary dict = productService.queryByOrderId(orderId);
		Double realityRepayMoney = bwRepaymentPlan.getRealityRepayMoney();
		// Long statusId = queryBwOrder.getStatusId();
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setAvoidFineDate(queryBwOrder.getAvoidFineDate());
		// 总金额（应还款金额+逾期金额）
		Double totalAmount = productService.calcRepaymentCost(realityRepayMoney, orderId, bwRepaymentPlan.getId(), dict,
				loanInfo);

		RepaymentBatch repaymentBatch = getRepaymentBatch(orderId);
		Double alreadyTotalBatchMoney = repaymentBatch.getAlreadyTotalBatchMoney();
		boolean isLastRepayment = true;
		// 还剩多少金额需要还款
		double leftAmount = DoubleUtil.sub(totalAmount, alreadyTotalBatchMoney);
		if (batchAmount < leftAmount) {
			isLastRepayment = false;
		}

		Date nowDate = new Date();
		BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail = new BwOrderRepaymentBatchDetail();
		bwOrderRepaymentBatchDetail.setOrderId(orderId);
		bwOrderRepaymentBatchDetail.setAmount(batchAmount);
		bwOrderRepaymentBatchDetail.setNumber(repaymentBatch.getCurrentNumber());
		bwOrderRepaymentBatchDetail.setRepaymentChannel(repaymentChannel);
		bwOrderRepaymentBatchDetail.setOverdueDay(overdueDay);
		bwOrderRepaymentBatchDetail.setOverdueAmount(NumberUtils.toDouble(loanInfo.getOverdueAmount(), 0.0));
		bwOrderRepaymentBatchDetail.setResidualAmount(DoubleUtil.sub(leftAmount, batchAmount));
		bwOrderRepaymentBatchDetail.setTotalAmount(totalAmount);
		bwOrderRepaymentBatchDetail.setCreateTime(nowDate);
		bwOrderRepaymentBatchDetail.setUpdateTime(nowDate);
		bwOrderRepaymentBatchDetail.setLastRepayment(isLastRepayment);
		if (isSaveRedis) {
			RedisUtils.hset(RedisKeyConstant.BATCH_REPAYMENT_DETAIL, String.valueOf(orderId),
					JSON.toJSONString(bwOrderRepaymentBatchDetail));
		} else {
			saveOrUpdateByOrderAndNumber(bwOrderRepaymentBatchDetail);
		}
		result.setCode("000");
		result.setMsg("SUCCESS");
		return result;
	}

	/**
	 * 
	 * @see com.waterelephant.service.BwOrderRepaymentBatchDetailService#deleteBwOrderRepaymentBatchDetail(java.lang.Long)
	 */
	@Override
	public void deleteBwOrderRepaymentBatchDetail(Long orderId) {
		sqlMapper.delete("delete from bw_order_repayment_batch_detail where order_id=" + orderId);
	}

	@Override
	public boolean selectIsBatch(Long orderId) {
		boolean isBatch = false;// 是否分批
		BwOrderRepaymentBatchDetail lastBatchDetail = getLastBatchDetail(orderId);
		if (lastBatchDetail != null) {
			Boolean lastRepayment = lastBatchDetail.getLastRepayment();
			Integer number = lastBatchDetail.getNumber();
			if (lastRepayment != null && number != null && number == 1 && lastRepayment) {// 一次还完
				isBatch = false;
			} else {
				isBatch = true;
			}
		}
		return isBatch;
	}
}