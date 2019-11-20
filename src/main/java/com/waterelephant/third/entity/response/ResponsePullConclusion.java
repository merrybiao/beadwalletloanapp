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
 * ResponsePullConclusion.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class ResponsePullConclusion {
	private String thirdOrderNo; // 机构订单流水号
	private int conclusion;// 审批结论(10=审批通过,40=审批不通过)
	private String approvalTime;// 审批通过时间
	private int amountType;// 审批金额是否固定(0=固定金额；默认为0)
	private double approvalAmount;// 审批金额
	private int termUnit;// 期限类型(1=单期产品（按天计息），默认为1)
	private int termType;// 审批期限是否固定(0=固定期限；默认为0)
	private int approvalTerm;// 审批天（月）数-固定
	private double payAmount;// 总还款额
	private double receiveAmount;// 总到账金额
	private String remark;// 总还款额组成说明

	public String getThirdOrderNo() {
		return thirdOrderNo;
	}

	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}

	public int getConclusion() {
		return conclusion;
	}

	public void setConclusion(int conclusion) {
		this.conclusion = conclusion;
	}

	public String getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(String approvalTime) {
		this.approvalTime = approvalTime;
	}

	public int getAmountType() {
		return amountType;
	}

	public void setAmountType(int amountType) {
		this.amountType = amountType;
	}

	public double getApprovalAmount() {
		return approvalAmount;
	}

	public void setApprovalAmount(double approvalAmount) {
		this.approvalAmount = approvalAmount;
	}

	public int getTermUnit() {
		return termUnit;
	}

	public void setTermUnit(int termUnit) {
		this.termUnit = termUnit;
	}

	public int getTermType() {
		return termType;
	}

	public void setTermType(int termType) {
		this.termType = termType;
	}

	public int getApprovalTerm() {
		return approvalTerm;
	}

	public void setApprovalTerm(int approvalTerm) {
		this.approvalTerm = approvalTerm;
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	public double getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
