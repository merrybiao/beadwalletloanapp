/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.installment.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.ParameterConstant;
import com.waterelephant.dto.QueryRepayInfo;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwProductDictionary;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.installment.constants.InstallmentConstant;
import com.waterelephant.installment.service.InstallmentService;
import com.waterelephant.installment.service.OrderService;
import com.waterelephant.service.BwProductDictionaryService;
import com.waterelephant.service.ExtraConfigService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwRepaymentPlanService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.DoubleUtil;
import com.waterelephant.utils.NumberUtil;
import com.waterelephant.utils.StringUtil;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * 
 * Module:
 * 
 * InstallmentServiceImpl.java
 * 
 * @author 程盼
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
@Service
public class InstallmentServiceImpl implements InstallmentService {
	private Logger logger = Logger.getLogger(InstallmentServiceImpl.class);
	@Resource
	private ExtraConfigService extraConfigService;
	@Resource
	private BwProductDictionaryService bwProductDictionaryService;
	@Resource
	private IBwOrderService bwOrderService;
	@Resource
	private IBwRepaymentPlanService bwRepaymentPlanService;
	@Resource
	private OrderService orderService;
	@Resource
	private ProductService productService;

	/**
	 * 查询分期信息
	 * 
	 * @see com.waterelephant.installment.service.InstallmentService#getInstallementInfo()
	 */
	@Override
	public Map<String, Object> getInstallementInfo(String borrowerId, String productType) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 预借金额
		map.put("expectMoney", "");
		// 多期工单的预借期数
		map.put("number", "");
		// 查询是否有草稿工单
		BwOrder order = orderService.getOrderByProductType(Long.parseLong(borrowerId), Integer.parseInt(productType));
		logger.info("【InstallmentServiceImpl.getInstallementInfo】borrowerId：" + borrowerId + "，草稿工单order：" + order);
		if (!CommUtils.isNull(order) && StringUtil.toInteger(order.getStatusId()) == 1) {
			map.put("expectMoney", NumberUtil.parseInt(order.getExpectMoney(), null));
			map.put("number", StringUtil.toString(order.getExpectNumber()));
		}
		// 借款最低，最高限额
		String minPayment = "";
		String maxPayment = "";
		if (productType.equals(InstallmentConstant.PRODUCT_TYPE.SINGLE.toString())) {
			minPayment = InstallmentConstant.BORROWER_LIMIT.DINGLE_MIN;
			maxPayment = InstallmentConstant.BORROWER_LIMIT.DINGLE_MAX;
			// 查询单期产品
			BwProductDictionary singleProduct = productService.selectCurrentByProductType(1);
			int singleTerm = 0; // 放款天数
			int pTermType = Integer.valueOf(singleProduct.getpTermType()); // 产品类型（1：月 2：天）
			int pTerm = Integer.valueOf(singleProduct.getpTerm()); // 产品期限
			if (1 == pTermType) {
				singleTerm = pTerm * 30; // 产品期限为月时，乘以30，换算为天
			} else if (2 == pTermType) {
				singleTerm = pTerm;
			}
			// 单期借款期限
			map.put("term", singleTerm);
			map.put("interesRate", "");
			map.put("numbers", "");
		}

		if (productType.equals(InstallmentConstant.PRODUCT_TYPE.MULTI.toString())) {
			minPayment = InstallmentConstant.BORROWER_LIMIT.MULTI_MIN;
			maxPayment = InstallmentConstant.BORROWER_LIMIT.MULTI_MAX;
			// 查询分期期数
			String installment = extraConfigService.findCountExtraConfigByCode(ParameterConstant.INSTALLMENT_NUMBER);
			// 查询分期产品
			BwProductDictionary multiProduct = productService.selectCurrentByProductType(2);
			int multiTerm = 0; // 放款天数
			int pTermType = Integer.valueOf(multiProduct.getpTermType()); // 产品类型（1：月 2：天）
			int pTerm = Integer.valueOf(multiProduct.getpTerm()); // 产品期限
			if (1 == pTermType) {
				multiTerm = pTerm * 30; // 产品期限为月时，乘以30，换算为天
			} else if (2 == pTermType) {
				multiTerm = pTerm;
			}
			// 多期借款期限
			map.put("term", multiTerm);
			QueryRepayInfo queryRepayInfo = productService.calcBorrowCost(0.0, multiProduct);
			// 利息
			Double interesRate = queryRepayInfo.getInterestRate();
			// 产品利率
			map.put("interesRate", interesRate);
			if (StringUtil.isEmpty(installment)) {
				map.put("numbers", "");
			} else {
				map.put("numbers", installment.split(","));
			}
		}
		map.put("maxPayment", maxPayment);
		map.put("minPayment", minPayment);
		return map;
	}

	/**
	 * 查询借款记录-现金分期-分期信息
	 * 
	 * @see com.waterelephant.installment.service.InstallmentService#getInstallmentList(java.lang.Long)
	 */
	@Override
	public List<Map<String, Object>> getInstallmentList(Long orderId) {

		// 查询分期工单
		// BwOrder order = bwOrderService.findBwOrderById(orderId.toString());
		Example example = new Example(BwRepaymentPlan.class);
		example.createCriteria().andEqualTo("orderId", orderId);
		List<BwRepaymentPlan> planList = bwRepaymentPlanService.findRepaymentPlanByExample(example);
		logger.info("【bwRepaymentPlanService.findRepaymentPlanByExample】根据工单orderId ： " + orderId + "   查询分期信息的还款记录为 ： "
				+ planList);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		logger.info("工单id ： " + orderId + "   [productService.calcTotalCostRateByType]分期");

		if (!CollectionUtils.isEmpty(planList)) {
			int totalNumber = planList.size();
			for (BwRepaymentPlan plan : planList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("number", StringUtil.toInteger(plan.getNumber()));// 期数
				map.put("numberStr", plan.getNumber() + "/" + totalNumber + "期");// 期数
				Double realityRepayMoney = plan.getRealityRepayMoney();
				map.put("borrowAmount", DoubleUtil.toTwoDecimal(realityRepayMoney));// 还款金额（本金+利息）
				map.put("repayTime", DateUtil.getDateString(plan.getRepayTime(), DateUtil.yyyy_MM_dd));// 还款时间
				map.put("repayStatus", StringUtil.toInteger(plan.getRepayStatus()));// 还款状态(1 待还款 2 已结清 3逾期)
				list.add(map);
			}
		}
		return list;
	}

}
