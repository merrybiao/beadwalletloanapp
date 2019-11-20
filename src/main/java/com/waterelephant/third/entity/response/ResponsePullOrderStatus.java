/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.third.entity.response;

/**
 * 
 * 
 * Module:
 * 
 * ResponsePullOrderStatus.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class ResponsePullOrderStatus {
	private String thirdOrderNo; // 机构订单流水号
	private int orderStatus;// 订单状态
	private Double approvalAmount; // 审批金额
	private int approvalTime;// 审批期限
	private int approvalTimeUnit;// 期限单位（1=天，2=月）
	private Double arrivaAmount;// 到账金额
	private Double overallCost;// 综合费用
	private Double repaymentAmount;// 应还金额（审批金额加综合费用）
	private String overallCostExplain;// 综合费用说明

	public String getThirdOrderNo() {
		return thirdOrderNo;
	}

	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getApprovalAmount() {
		return approvalAmount;
	}

	public void setApprovalAmount(Double approvalAmount) {
		this.approvalAmount = approvalAmount;
	}

	public int getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(int approvalTime) {
		this.approvalTime = approvalTime;
	}

	public int getApprovalTimeUnit() {
		return approvalTimeUnit;
	}

	public void setApprovalTimeUnit(int approvalTimeUnit) {
		this.approvalTimeUnit = approvalTimeUnit;
	}

	public Double getArrivaAmount() {
		return arrivaAmount;
	}

	public void setArrivaAmount(Double arrivaAmount) {
		this.arrivaAmount = arrivaAmount;
	}

	public Double getOverallCost() {
		return overallCost;
	}

	public void setOverallCost(Double overallCost) {
		this.overallCost = overallCost;
	}

	public Double getRepaymentAmount() {
		return repaymentAmount;
	}

	public void setRepaymentAmount(Double repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}

	public String getOverallCostExplain() {
		return overallCostExplain;
	}

	public void setOverallCostExplain(String overallCostExplain) {
		this.overallCostExplain = overallCostExplain;
	}

}
