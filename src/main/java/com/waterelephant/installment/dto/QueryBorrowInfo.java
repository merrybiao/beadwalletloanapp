/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.installment.dto;

import java.util.List;

/**
 * 
 * 
 * Module:
 * 
 * QueryBorrowInfo.java
 * 
 * @author 毛恩奇
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class QueryBorrowInfo {
	/** 借款金额 */
	private String borrowAmount;
	/** 到账金额 */
	private String receivedAmount;
	/** 还款时间 */
	private String repayTime;
	/** 银行卡后4位 */
	private String cardNoEnd;
	/** 银行卡名 */
	private String bankName;
	/** 快速审核费 */
	private String pFastReviewCost;
	/** 平台使用费 */
	private String pPlatformUseCost;
	/** 账户管理费 */
	private String pNumberManageCost;
	/** 代收通道费 */
	private String pCollectionPassagewayCost;
	/** 资金使用费 */
	private String pCapitalUseCost;
	/** 综合费用 */
	private String loanAmount;
	/** 产品类型(1、单期，2、分期) */
	private Integer productType;
	/** 语音验证码电话 */
	private String voiceTel;
	/** 分期详情列表 */
	private List<InstallmentInfo> installmentInfos;
	/** 是否第一次点击(0否1是) */
	private String isFirst;
	/** 湛江委金额 */
	private String zjwAmount;
	/** 总共应还金额（包含借款金额、湛江委金额） */
	private String totalAmount;

	public String getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(String borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	public String getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(String receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public String getCardNoEnd() {
		return cardNoEnd;
	}

	public void setCardNoEnd(String cardNoEnd) {
		this.cardNoEnd = cardNoEnd;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getpFastReviewCost() {
		return pFastReviewCost;
	}

	public void setpFastReviewCost(String pFastReviewCost) {
		this.pFastReviewCost = pFastReviewCost;
	}

	public String getpPlatformUseCost() {
		return pPlatformUseCost;
	}

	public void setpPlatformUseCost(String pPlatformUseCost) {
		this.pPlatformUseCost = pPlatformUseCost;
	}

	public String getpNumberManageCost() {
		return pNumberManageCost;
	}

	public void setpNumberManageCost(String pNumberManageCost) {
		this.pNumberManageCost = pNumberManageCost;
	}

	public String getpCollectionPassagewayCost() {
		return pCollectionPassagewayCost;
	}

	public void setpCollectionPassagewayCost(String pCollectionPassagewayCost) {
		this.pCollectionPassagewayCost = pCollectionPassagewayCost;
	}

	public String getpCapitalUseCost() {
		return pCapitalUseCost;
	}

	public void setpCapitalUseCost(String pCapitalUseCost) {
		this.pCapitalUseCost = pCapitalUseCost;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String getVoiceTel() {
		return voiceTel;
	}

	public void setVoiceTel(String voiceTel) {
		this.voiceTel = voiceTel;
	}

	public List<InstallmentInfo> getInstallmentInfos() {
		return installmentInfos;
	}

	public void setInstallmentInfos(List<InstallmentInfo> installmentInfos) {
		this.installmentInfos = installmentInfos;
	}

	/**
	 * @return 获取 isFirst属性值
	 */
	public String getIsFirst() {
		return isFirst;
	}

	/**
	 * @param isFirst 设置 isFirst 属性值为参数值 isFirst
	 */
	public void setIsFirst(String isFirst) {
		this.isFirst = isFirst;
	}

	public String getZjwAmount() {
		return zjwAmount;
	}

	public void setZjwAmount(String zjwAmount) {
		this.zjwAmount = zjwAmount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * 分期信息
	 * 
	 * Module:
	 * 
	 * QueryOrderRepayInfo.java
	 * 
	 * @author 毛恩奇
	 * @since JDK 1.8
	 * @version 1.0
	 * @description: <描述>
	 */
	public class InstallmentInfo {
		/**
		 * 期数
		 */
		private Integer number;
		/**
		 * 每期还款金额
		 */
		private String amountDue;
		/**
		 * 还款日
		 */
		private String repaymentDate;

		public Integer getNumber() {
			return number;
		}

		public void setNumber(Integer number) {
			this.number = number;
		}

		public String getAmountDue() {
			return amountDue;
		}

		public void setAmountDue(String amountDue) {
			this.amountDue = amountDue;
		}

		public String getRepaymentDate() {
			return repaymentDate;
		}

		public void setRepaymentDate(String repaymentDate) {
			this.repaymentDate = repaymentDate;
		}
	}

}
