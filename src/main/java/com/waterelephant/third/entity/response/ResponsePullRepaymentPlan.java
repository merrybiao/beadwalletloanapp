/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.third.entity.response;

import java.util.List;

/**
 * 
 * 
 * Module:
 * 
 * ResponsePullRepaymentPlan.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class ResponsePullRepaymentPlan {
	private String thirdOrderNo; // 机构订单流水号
	private String openBank;// 银行名称
	private String bankCard;// 银行卡号
	private List<RepaymentPlan> repaymentPlanList;// 还款计划

	public String getThirdOrderNo() {
		return thirdOrderNo;
	}

	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}

	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public List<RepaymentPlan> getRepaymentPlanList() {
		return repaymentPlanList;
	}

	public void setRepaymentPlanList(List<RepaymentPlan> repaymentPlanList) {
		this.repaymentPlanList = repaymentPlanList;
	}

}
