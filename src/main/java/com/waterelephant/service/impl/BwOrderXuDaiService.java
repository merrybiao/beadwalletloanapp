package com.waterelephant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.service.IBwOrderXuDaiService;
import com.waterelephant.utils.StringUtil;

@Service
public class BwOrderXuDaiService implements IBwOrderXuDaiService {

	@Autowired
	private BwRepaymentPlanService bwRepaymentPlanService;

	@Override
	public int updOrderXudai(Long orderId) {
		return 0;
	}

	/**
	 * 当前续贷次数
	 * 
	 * @see com.waterelephant.service.IBwOrderXuDaiService#queryXudaiTerm(java.lang.Long)
	 */
	@Override
	public int queryXudaiTerm(Long orderIdOld) {
		int xudaiTerm = 0;
		BwRepaymentPlan plan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(orderIdOld);
		if (null != plan) {
			xudaiTerm = StringUtil.toInteger(plan.getRolloverNumber());
		}
		return xudaiTerm;
	}

	/**
	 * 当前续贷次数
	 * 
	 * @see com.waterelephant.service.IBwOrderXuDaiService#queryCurrentXudaiTermByOrderId(java.lang.Long)
	 */
	@Override
	public int queryCurrentXudaiTermByOrderId(Long orderId) {
		int currentXudaiTerm = 0;
		BwRepaymentPlan plan = bwRepaymentPlanService.getLastRepaymentPlanByOrderId(orderId);
		if (null != plan) {
			currentXudaiTerm = StringUtil.toInteger(plan.getRolloverNumber());
		}
		return currentXudaiTerm;
	}

}
