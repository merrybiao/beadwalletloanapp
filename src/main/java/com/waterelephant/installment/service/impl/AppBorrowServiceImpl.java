/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.installment.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.dto.QueryRepayInfo;
import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderProcessRecord;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwOrderTem;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.installment.dto.QueryBorrowInfo;
import com.waterelephant.installment.dto.QueryBorrowInfo.InstallmentInfo;
import com.waterelephant.installment.service.AppBorrowService;
import com.waterelephant.service.BwOrderProcessRecordService;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwOrderTemService;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.service.IBwBankCardService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DoubleUtil;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.utils.MyJSONUtil;
import com.waterelephant.utils.NumberUtil;
import com.waterelephant.utils.OrderUtil;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SqlMapper;
import com.waterelephant.utils.StringUtil;

import cn.jpush.api.utils.StringUtils;
import net.sf.json.JSONObject;

/**
 * 
 * 
 * Module:
 * 
 * AppBorrowServiceImpl.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class AppBorrowServiceImpl implements AppBorrowService {
	private Logger logger = Logger.getLogger(AppBorrowServiceImpl.class);
	@Autowired
	protected SqlMapper sqlMapper;
	@Autowired
	protected IBwBorrowerService bwBorrowerService;
	@Autowired
	protected IBwOrderService bwOrderService;
	@Autowired
	protected BwRejectRecordService bwRejectRecordService;
	@Autowired
	protected BwOrderTemService bwOrderTemService;
	@Autowired
	protected BwOrderRongService bwOrderRongService;
	@Autowired
	protected ProductService productService;
	@Autowired
	protected IBwBankCardService bwBankCardService;
	@Autowired
	protected BwOrderProcessRecordService bwOrderProcessRecordService;

	/**
	 * 提交借款信息
	 * 
	 * @see com.waterelephant.installment.service.AppBorrowService#updateAndCommitBorrowInfo(java.util.Map)
	 */
	@Override
	public AppResponseResult updateAndCommitBorrowInfo(Map<String, String> paramMap) {
		AppResponseResult result = new AppResponseResult();
		Long borrowerId = NumberUtil.parseLong(paramMap.get("borrowerId"), null);
		Integer channel = NumberUtil.parseInteger(paramMap.get("channel"), null);
		Integer productType = NumberUtil.parseInteger(paramMap.get("productType"), null);
		Double expectMoney = NumberUtil.parseDouble(paramMap.get("expectMoney"), 0.0);// 预借金额
		Integer borrowNumber = NumberUtil.parseInteger(paramMap.get("borrowNumber"), null);// 预借期数
		if (borrowerId == null || productType == null || expectMoney == null
				|| (productType == 2 && borrowNumber == null)) {
			logger.info("参数错误borrowerId" + borrowerId + paramMap);
			result.setCode("101");
			result.setMsg("参数错误");
			result.setResult("");
			return result;
		}

		if (productType == 2) {
			result.setCode("102");
			result.setMsg("今天额度已满");
			result.setResult("");
			return result;
		}

		if (productType == 1) {
			result.setCode("102");
			result.setMsg("请下载最新版本");
			result.setResult("");
			return result;
		}

		BwProductDictionary product = productService.selectCurrentByProductType(productType);
		if (product == null) {
			logger.info("找不到对应产品borrowerId" + borrowerId + ",productType" + productType);
			result.setCode("106");
			result.setMsg("参数错误");
			result.setResult("");
			return result;
		}
		if (productType == 1 && (expectMoney < 500.0 || expectMoney > 5000.0)) {
			result.setCode("102");
			logger.info("借款金额在500~5000元之间");
			result.setMsg("借款金额在500~5000元之间");
			result.setResult("");
			return result;
		}
		if (productType == 2 && (expectMoney < 2000.0 || expectMoney > 10000.0)) {
			result.setCode("103");
			logger.info("借款金额在2000~10000元之间");
			result.setMsg("借款金额在2000~10000元之间");
			result.setResult("");
			return result;
		}

		BwBorrower queryBorrower = bwBorrowerService.findBwBorrowerById(borrowerId);
		if (queryBorrower == null) {
			result.setCode("104");
			logger.info("借款人不存在");
			result.setMsg("借款人不存在");
			result.setResult("");
			return result;
		}

		Date nowDate = new Date();
		// 查询当前借款人是否存在被拒工单，如果是永久被拒则永不创建工单，非永久被拒则三个月之后再次创建工单
		BwOrder rejectedOrder = bwOrderService.selectLastOrder(borrowerId, productType, Arrays.asList(7));
		boolean whiteBool = true;// 是否白名单或灰名单超过一个月
		boolean generateBool = true;// 是否生成新的草稿工单(白名单、灰名单并超过一个月)
		if (rejectedOrder != null) {
			Long rejectedOrderId = rejectedOrder.getId();
			BwRejectRecord rejectRecord = bwRejectRecordService.findBwRejectRecordByOrderId(rejectedOrderId);
			if (rejectRecord != null) {
				Integer rejectType = rejectRecord.getRejectType();
				if (rejectType == 1) {// 非永久被拒
					Date blackTime = rejectRecord.getCreateTime();// 黑名单时间
					Date whiteTime = MyDateUtils.addDays(blackTime, ActivityConstant.BLACKTIME);
					if (!nowDate.after(whiteTime)) {
						whiteBool = false;
						generateBool = false;
					}
					logger.info("【AppBorrowServiceImpl.updateAndCommitBorrowInfo】borrowerId：" + borrowerId
							+ "，临时被拒rejectedOrderId：" + rejectedOrderId);
				} else {// 永久被拒
					logger.info("【AppBorrowServiceImpl.updateAndCommitBorrowInfo】borrowerId：" + borrowerId
							+ "，永久被拒rejectedOrderId：" + rejectedOrderId);
					whiteBool = false;
					generateBool = false;
				}
			}
		}

		if (whiteBool) {// 白名单、灰名单过一个月
			// (1, 2, 3, 4, 5, 8, 9, 11, 12, 13, 14)
			BwOrder oldOrder = bwOrderService.selectLastOrder(borrowerId, productType, null);
			if (oldOrder != null) {
				Long statusId = oldOrder.getStatusId();
				if (statusId == null || statusId == 6 || statusId == 7) {// 已结束、拒绝都会生成草稿工单
				} else {
					generateBool = false;
					oldOrder.setExpectMoney(expectMoney);
					oldOrder.setExpectNumber(borrowNumber);
					oldOrder.setUpdateTime(new Date());
					bwOrderService.update(oldOrder);
				}
			}
		}

		if (generateBool) {// 可以生成新的草稿工单
			BwOrder insertOrder = new BwOrder();
			if (product != null) {
				insertOrder.setProductId(Integer.parseInt(product.getId().toString()));
			}
			String orderNo = OrderUtil.generateOrderNo();
			if (productType != null && productType == 2) {
				insertOrder.setRepayType(2);// 等额本息
				orderNo = OrderUtil.generateInstallmentOrderNo();
			} else {
				insertOrder.setRepayType(1);// 先息后本
			}
			insertOrder.setOrderNo(orderNo);
			insertOrder.setBorrowerId(borrowerId);
			insertOrder.setStatusId(1L);
			insertOrder.setCreateTime(new Date());
			insertOrder.setChannel(channel);
			insertOrder.setAvoidFineDate(0);
			insertOrder.setProductType(productType);
			insertOrder.setExpectMoney(expectMoney);
			insertOrder.setExpectNumber(borrowNumber);
			insertOrder.setApplyPayStatus(0);
			logger.info("【AppBorrowServiceImpl.updateAndCommitBorrowInfo】borrowerId：" + borrowerId + "，添加的草稿工单："
					+ insertOrder.toString());
			bwOrderService.addBwOrder(insertOrder);

			// 记录草稿时间
			BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord().setOrderId(insertOrder.getId());
			bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);

			// TODO
			if (channel == 81) {
				BwOrderRong bwOrderRong = new BwOrderRong();
				bwOrderRong.setOrderId(insertOrder.getId());
				BwOrderTem bwOrderTem = bwOrderTemService.getByPhonne(queryBorrower.getPhone(), "haodai");
				if (bwOrderTem != null) {
					bwOrderRong.setThirdOrderNo(bwOrderTem.getThirdOrderNo());
					bwOrderRong.setChannelId((long) channel);
					bwOrderRong.setCreateTime(new Date());
					bwOrderRongService.save(bwOrderRong);
					bwOrderTemService.deleteBwOrderTem(bwOrderTem);
				}
			}

			if (channel == 289) {
				try {
					BwOrderRong bwOrderRong = new BwOrderRong();
					bwOrderRong.setOrderId(insertOrder.getId());
					BwOrderTem bwOrderTem = bwOrderTemService.getByPhonne(queryBorrower.getPhone(), "bajie");
					if (bwOrderTem != null) {
						bwOrderRong.setThirdOrderNo(bwOrderTem.getThirdOrderNo());
						bwOrderRong.setChannelId((long) channel);
						bwOrderRong.setCreateTime(new Date());
						bwOrderRongService.save(bwOrderRong);
						bwOrderTemService.deleteBwOrderTem(bwOrderTem);
					}

					RedisUtils.lpush("notify:orderState", String.valueOf(insertOrder.getId()));
				} catch (Exception e) {
					logger.error(e);
				}
			}

			if (channel == 274) {
				try {
					BwOrderRong bwOrderRong = new BwOrderRong();
					bwOrderRong.setOrderId(insertOrder.getId());
					BwOrderTem bwOrderTem = bwOrderTemService.getByPhonne(queryBorrower.getPhone(), "cashloan");
					if (bwOrderTem != null) {
						bwOrderRong.setThirdOrderNo(bwOrderTem.getThirdOrderNo());
						bwOrderRong.setChannelId((long) channel);
						bwOrderRong.setCreateTime(new Date());
						bwOrderRongService.save(bwOrderRong);
						bwOrderTemService.deleteBwOrderTem(bwOrderTem);
					}
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
		result.setCode("000");
		result.setMsg("SUCCESS");
		result.setResult("");
		return result;
	}

	/**
	 * 
	 * @see com.waterelephant.installment.service.AppBorrowService#queryBorrowInfo(java.util.Map)
	 */
	@Override
	public AppResponseResult queryBorrowInfo(Map<String, String> paramMap) {
		AppResponseResult result = new AppResponseResult();
		Long orderId = NumberUtil.parseLong(paramMap.get("orderId"), null);
		if (orderId == null) {
			result.setCode("101");
			result.setMsg("参数错误");
			result.setResult("");
			return result;
		}
		BwOrder bwOrder = bwOrderService.findBwOrderById(orderId.toString());
		logger.info("【AppBorrowServiceImpl.queryBorrowInfo】orderId：" + orderId + ",工单bwOrder：" + bwOrder);
		if (bwOrder == null) {
			result.setCode("102");
			result.setMsg("工单不存在");
			result.setResult("");
			return result;
		}
		Long borrowerId = bwOrder.getBorrowerId();
		Integer productType = bwOrder.getProductType();
		BwBankCard card = bwBankCardService.findBwBankCardByBorrowerId(borrowerId);
		if (CommUtils.isNull(card)) {
			result.setCode("103");
			result.setMsg("银行卡不存在");
			result.setResult("");
			return result;
		}
		// 费用产品信息从产品表中获取
		QueryBorrowInfo queryBorrowInfo = new QueryBorrowInfo();
		BwProductDictionary product = productService.queryByOrderId(orderId);
		if (CommUtils.isNull(product)) {
			result.setCode("104");
			result.setMsg("工单未绑定产品信息");
			result.setResult("");
			return result;
		}
		Double borrowAmount = bwOrder.getBorrowAmount();// 借款金额
		if (borrowAmount == null) {
			borrowAmount = bwOrder.getExpectMoney();
		}
		Double getpFastReviewCost = product.getpFastReviewCost();// 快速审核费
		Double getpPlatformUseCost = product.getpPlatformUseCost();// 平台使用费
		Double getpNumberManageCost = product.getpNumberManageCost();// 账户管理费
		Double getpCollectionPassagewayCost = product.getpCollectionPassagewayCost();// 代收通道费
		Double getpCapitalUseCost = product.getpCapitalUseCost();// 资金使用费
		QueryRepayInfo queryRepayInfo = productService.calcBorrowCost(borrowAmount, product);
		Double loanAmount = Double.valueOf(queryRepayInfo.getLoanAmount());// 借款工本费
		Double arrivalAmount = DoubleUtil.sub(borrowAmount, loanAmount);// 到账金额
		Date nowDate = new Date();
		String cardNoEnd = card.getCardNo().substring(card.getCardNo().length() - 4, card.getCardNo().length());
		String bankName = RedisUtils.hget(RedisKeyConstant.FUIOU_BANK, card.getBankCode());
		if (StringUtils.isNotEmpty(bankName) && bankName.contains("公司") && bankName.contains("银行")) {
			bankName = bankName.substring(0, bankName.indexOf("银行") + 2);
		}
		queryBorrowInfo.setCardNoEnd(cardNoEnd);
		queryBorrowInfo.setBankName(bankName);

		// 计算还款日
		String term = product.getpTerm();// 借款期限
		String termType = product.getpTermType();// '产品类型（1：月；2：天）',
		if (productType == 1) {// 单期
			queryBorrowInfo.setRepayTime(MyDateUtils.DateToString(productService.calcRepayTime(nowDate, term, termType),
					MyDateUtils.DATE_TO_STRING_SHORT_PATTERN));
		} else if (productType == 2) {// 分期
			Integer borrowNumber = bwOrder.getBorrowNumber();
			Double interestAmount = queryRepayInfo.getInterestAmount();// 分期利息
			List<InstallmentInfo> installmentInfos = new ArrayList<InstallmentInfo>();
			// 每期金额
			double eachAmount = Math.floor(DoubleUtil.div(borrowAmount, Double.parseDouble(borrowNumber + "")));
			// 最后一期金额
			double lastAmount = eachAmount;
			for (int i = 1; i <= borrowNumber; i++) {
				InstallmentInfo installmentInfo = queryBorrowInfo.new InstallmentInfo();
				if (borrowNumber == i) {
					lastAmount = DoubleUtil.sub(borrowAmount, DoubleUtil.mul(eachAmount, (borrowNumber - 1)));
					installmentInfo.setAmountDue(DoubleUtil.toTwoDecimal(DoubleUtil.add(interestAmount, lastAmount)));
				} else {
					installmentInfo.setAmountDue(DoubleUtil.toTwoDecimal(DoubleUtil.add(interestAmount, eachAmount)));
				}
				installmentInfo.setNumber(i);
				installmentInfo.setRepaymentDate(MyDateUtils.DateToString(
						productService.calcRepayTime(nowDate, Integer.parseInt(term) * i + "", termType),
						MyDateUtils.DATE_TO_STRING_SHORT_PATTERN));
				installmentInfos.add(installmentInfo);
			}
			queryBorrowInfo.setInstallmentInfos(installmentInfos);
		}

		// 湛江委
		Double zjwCost = product.getZjwCost();
		if (zjwCost == null) {
			zjwCost = 0.0;
		}
		double zjwAmount = DoubleUtil.round(DoubleUtil.mul(borrowAmount, zjwCost), 2);
		queryBorrowInfo.setZjwAmount(DoubleUtil.toTwoDecimal(zjwAmount));

		queryBorrowInfo.setProductType(productType);
		queryBorrowInfo.setTotalAmount(DoubleUtil.toTwoDecimal(DoubleUtil.add(borrowAmount, zjwAmount)));
		queryBorrowInfo.setBorrowAmount(DoubleUtil.toTwoDecimal(borrowAmount));
		queryBorrowInfo.setpCapitalUseCost(DoubleUtil.toTwoDecimal(DoubleUtil.mul(borrowAmount, getpCapitalUseCost)));
		queryBorrowInfo.setpCollectionPassagewayCost(
				DoubleUtil.toTwoDecimal(DoubleUtil.mul(borrowAmount, getpCollectionPassagewayCost)));
		queryBorrowInfo.setpFastReviewCost(DoubleUtil.toTwoDecimal(DoubleUtil.mul(borrowAmount, getpFastReviewCost)));
		queryBorrowInfo
				.setpNumberManageCost(DoubleUtil.toTwoDecimal(DoubleUtil.mul(borrowAmount, getpNumberManageCost)));
		queryBorrowInfo.setpPlatformUseCost(DoubleUtil.toTwoDecimal(DoubleUtil.mul(borrowAmount, getpPlatformUseCost)));
		queryBorrowInfo.setLoanAmount(DoubleUtil.toTwoDecimal(loanAmount));
		queryBorrowInfo.setReceivedAmount(DoubleUtil.toTwoDecimal(arrivalAmount));
		queryBorrowInfo.setVoiceTel("95213141");// 语音验证码电话号码
		queryBorrowInfo.setIsFirst(isFirst(StringUtil.toString(orderId)));
		JSONObject objectToJson = MyJSONUtil.objectToJson(queryBorrowInfo, new String[0], true);
		logger.info("【AppBorrowServiceImpl.queryBorrowInfo】orderId：" + orderId + ",objectToJson：" + objectToJson);
		result.setCode("000");
		result.setMsg("SUCCESS");
		result.setResult(objectToJson);
		return result;
	}

	private String isFirst(String orderId) {
		// 存在则不是第一次
		if (RedisUtils.hexists("arbitration:isFirst", orderId)) {
			return "0";
		}
		logger.info("添加第一次点击确认拿钱标识=============" + orderId);
		return "1";
	}
}