package com.waterelephant.vo;

/**
 * 传递借款记录详情是实体类
 * 
 * @Title: BorrowRcordVO.java
 * @Description:
 * @author wangkun
 * @date 2017年4月20日 下午2:02:53
 * @version V1.0
 */
public class BorrowRcordVO {
	/**
	 * 借款金额
	 * 
	 */
	private String borrowAmount;
	/** 到款金额 */
	private String arrivalAmount;
	/** 综合费用 */
	private String loanAmount;
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
	/** 订单申请时间 */
	private String submitTime;
	/** 贷款次数 */
	private Integer xuDaiCount;
	/** 续贷费用 */
	private String xuDaiAmount;
	/** 续贷申请时间 */
	private String xudaiCreateTime;
	/** 到期还款时间 */
	private String repayTime;
	/** 逾期天数 */
	private Integer daySpace;
	/** 逾期金额 */
	private String overAmont;
	/** 还款金额 */
	private String repayMoney;
	/** 还款时间 */
	private String updateTime;
	/** 最大优惠券 */
	private String maxAmount;
	/** 银行名称 */
	private String bankName;
	/** 银行卡后四位 */
	private String cardNoEnd;
	/** 还款总金额（还款金额+逾期金额-最大优惠券金额） */
	private String totalAmount;
	/** 还款总金额（还款金额+逾期金额），不使用优惠券 */
	private String totalNotCouponAmount;
	/** 总服务费（逾期金额+续贷金额） */
	private String totalXudaiAmount;
	/** 工单状态id（1 草稿 2 初审 3 终审 4 待签约 5 待放款 6 结束 7 拒绝 8 撤回 9 还款中 11 待生成合同 12 待债匹 13 逾期 14 债匹中） */
	private Integer statusId;
	/** 产品期限 */
	private String term;
	/** 距离还款天数 */
	private Integer distanceRepayTime;
	/** 使用的优惠券（已还款） */
	private String userAmount;
	/** 续贷到期还款时间 */
	private String xuDaiRepayTime;
	/** 是否可以续贷 */
	private Boolean canXuDai;
	/** 不可以续贷提示信息 */
	private String xuDaiErrMsg;
	/**
	 * 是否分批(0、否，1、是)
	 */
	private Integer isBatch;
	/**
	 * 已分批还款金额
	 */
	private String batchTotal;
	/**
	 * 湛江委金额
	 */
	private String zjwAmount;

	/**
	 * @return 获取 是否分批(0、否，1、是)
	 */
	public Integer getIsBatch() {
		return isBatch;
	}

	/**
	 * @param 设置是否分批(0、否，1、是)
	 */
	public void setIsBatch(Integer isBatch) {
		this.isBatch = isBatch;
	}

	/**
	 * @return 获取 borrowAmount属性值
	 */
	public String getBorrowAmount() {
		return borrowAmount;
	}

	/**
	 * @param borrowAmount 设置 borrowAmount 属性值为参数值 borrowAmount
	 */
	public void setBorrowAmount(String borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	/**
	 * @return 获取 arrivalAmount属性值
	 */
	public String getArrivalAmount() {
		return arrivalAmount;
	}

	/**
	 * @param arrivalAmount 设置 arrivalAmount 属性值为参数值 arrivalAmount
	 */
	public void setArrivalAmount(String arrivalAmount) {
		this.arrivalAmount = arrivalAmount;
	}

	/**
	 * @return 获取 loanAmount属性值
	 */
	public String getLoanAmount() {
		return loanAmount;
	}

	/**
	 * @param loanAmount 设置 loanAmount 属性值为参数值 loanAmount
	 */
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	/**
	 * @return 获取 pFastReviewCost属性值
	 */
	public String getpFastReviewCost() {
		return pFastReviewCost;
	}

	/**
	 * @param pFastReviewCost 设置 pFastReviewCost 属性值为参数值 pFastReviewCost
	 */
	public void setpFastReviewCost(String pFastReviewCost) {
		this.pFastReviewCost = pFastReviewCost;
	}

	/**
	 * @return 获取 pPlatformUseCost属性值
	 */
	public String getpPlatformUseCost() {
		return pPlatformUseCost;
	}

	/**
	 * @param pPlatformUseCost 设置 pPlatformUseCost 属性值为参数值 pPlatformUseCost
	 */
	public void setpPlatformUseCost(String pPlatformUseCost) {
		this.pPlatformUseCost = pPlatformUseCost;
	}

	/**
	 * @return 获取 pNumberManageCost属性值
	 */
	public String getpNumberManageCost() {
		return pNumberManageCost;
	}

	/**
	 * @param pNumberManageCost 设置 pNumberManageCost 属性值为参数值 pNumberManageCost
	 */
	public void setpNumberManageCost(String pNumberManageCost) {
		this.pNumberManageCost = pNumberManageCost;
	}

	/**
	 * @return 获取 pCollectionPassagewayCost属性值
	 */
	public String getpCollectionPassagewayCost() {
		return pCollectionPassagewayCost;
	}

	/**
	 * @param pCollectionPassagewayCost 设置 pCollectionPassagewayCost 属性值为参数值 pCollectionPassagewayCost
	 */
	public void setpCollectionPassagewayCost(String pCollectionPassagewayCost) {
		this.pCollectionPassagewayCost = pCollectionPassagewayCost;
	}

	/**
	 * @return 获取 pCapitalUseCost属性值
	 */
	public String getpCapitalUseCost() {
		return pCapitalUseCost;
	}

	/**
	 * @param pCapitalUseCost 设置 pCapitalUseCost 属性值为参数值 pCapitalUseCost
	 */
	public void setpCapitalUseCost(String pCapitalUseCost) {
		this.pCapitalUseCost = pCapitalUseCost;
	}

	/**
	 * @return 获取 submitTime属性值
	 */
	public String getSubmitTime() {
		return submitTime;
	}

	/**
	 * @param submitTime 设置 submitTime 属性值为参数值 submitTime
	 */
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	/**
	 * @return 获取 xuDaiCount属性值
	 */
	public Integer getXuDaiCount() {
		return xuDaiCount;
	}

	/**
	 * @param xuDaiCount 设置 xuDaiCount 属性值为参数值 xuDaiCount
	 */
	public void setXuDaiCount(Integer xuDaiCount) {
		this.xuDaiCount = xuDaiCount;
	}

	/**
	 * @return 获取 xuDaiAmount属性值
	 */
	public String getXuDaiAmount() {
		return xuDaiAmount;
	}

	/**
	 * @param xuDaiAmount 设置 xuDaiAmount 属性值为参数值 xuDaiAmount
	 */
	public void setXuDaiAmount(String xuDaiAmount) {
		this.xuDaiAmount = xuDaiAmount;
	}

	/**
	 * @return 获取 xudaiCreateTime属性值
	 */
	public String getXudaiCreateTime() {
		return xudaiCreateTime;
	}

	/**
	 * @param xudaiCreateTime 设置 xudaiCreateTime 属性值为参数值 xudaiCreateTime
	 */
	public void setXudaiCreateTime(String xudaiCreateTime) {
		this.xudaiCreateTime = xudaiCreateTime;
	}

	/**
	 * @return 获取 repayTime属性值
	 */
	public String getRepayTime() {
		return repayTime;
	}

	/**
	 * @param repayTime 设置 repayTime 属性值为参数值 repayTime
	 */
	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	/**
	 * @return 获取 daySpace属性值
	 */
	public Integer getDaySpace() {
		return daySpace;
	}

	/**
	 * @param daySpace 设置 daySpace 属性值为参数值 daySpace
	 */
	public void setDaySpace(Integer daySpace) {
		this.daySpace = daySpace;
	}

	/**
	 * @return 获取 overAmont属性值
	 */
	public String getOverAmont() {
		return overAmont;
	}

	/**
	 * @param overAmont 设置 overAmont 属性值为参数值 overAmont
	 */
	public void setOverAmont(String overAmont) {
		this.overAmont = overAmont;
	}

	/**
	 * @return 获取 repayMoney属性值
	 */
	public String getRepayMoney() {
		return repayMoney;
	}

	/**
	 * @param repayMoney 设置 repayMoney 属性值为参数值 repayMoney
	 */
	public void setRepayMoney(String repayMoney) {
		this.repayMoney = repayMoney;
	}

	/**
	 * @return 获取 updateTime属性值
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime 设置 updateTime 属性值为参数值 updateTime
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return 获取 maxAmount属性值
	 */
	public String getMaxAmount() {
		return maxAmount;
	}

	/**
	 * @param maxAmount 设置 maxAmount 属性值为参数值 maxAmount
	 */
	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	/**
	 * @return 获取 bankName属性值
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName 设置 bankName 属性值为参数值 bankName
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return 获取 cardNoEnd属性值
	 */
	public String getCardNoEnd() {
		return cardNoEnd;
	}

	/**
	 * @param cardNoEnd 设置 cardNoEnd 属性值为参数值 cardNoEnd
	 */
	public void setCardNoEnd(String cardNoEnd) {
		this.cardNoEnd = cardNoEnd;
	}

	/**
	 * @return 获取 totalAmount属性值
	 */
	public String getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount 设置 totalAmount 属性值为参数值 totalAmount
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalNotCouponAmount() {
		return totalNotCouponAmount;
	}

	public void setTotalNotCouponAmount(String totalNotCouponAmount) {
		this.totalNotCouponAmount = totalNotCouponAmount;
	}

	/**
	 * @return 获取 totalXudaiAmount属性值
	 */
	public String getTotalXudaiAmount() {
		return totalXudaiAmount;
	}

	/**
	 * @param totalXudaiAmount 设置 totalXudaiAmount 属性值为参数值 totalXudaiAmount
	 */
	public void setTotalXudaiAmount(String totalXudaiAmount) {
		this.totalXudaiAmount = totalXudaiAmount;
	}

	/**
	 * @return 获取 statusId属性值
	 */
	public Integer getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId 设置 statusId 属性值为参数值 statusId
	 */
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return 获取 term属性值
	 */
	public String getTerm() {
		return term;
	}

	/**
	 * @param term 设置 term 属性值为参数值 term
	 */
	public void setTerm(String term) {
		this.term = term;
	}

	/**
	 * @return 获取 distanceRepayTime属性值
	 */
	public Integer getDistanceRepayTime() {
		return distanceRepayTime;
	}

	/**
	 * @param distanceRepayTime 设置 distanceRepayTime 属性值为参数值 distanceRepayTime
	 */
	public void setDistanceRepayTime(Integer distanceRepayTime) {
		this.distanceRepayTime = distanceRepayTime;
	}

	/**
	 * @return 获取 userAmount属性值
	 */
	public String getUserAmount() {
		return userAmount;
	}

	/**
	 * @param userAmount 设置 userAmount 属性值为参数值 userAmount
	 */
	public void setUserAmount(String userAmount) {
		this.userAmount = userAmount;
	}

	/**
	 * @return 获取 xuDaiRepayTime属性值
	 */
	public String getXuDaiRepayTime() {
		return xuDaiRepayTime;
	}

	/**
	 * @param xuDaiRepayTime 设置 xuDaiRepayTime 属性值为参数值 xuDaiRepayTime
	 */
	public void setXuDaiRepayTime(String xuDaiRepayTime) {
		this.xuDaiRepayTime = xuDaiRepayTime;
	}

	public Boolean getCanXuDai() {
		return canXuDai;
	}

	public void setCanXuDai(Boolean canXuDai) {
		this.canXuDai = canXuDai;
	}

	public String getXuDaiErrMsg() {
		return xuDaiErrMsg;
	}

	public void setXuDaiErrMsg(String xuDaiErrMsg) {
		this.xuDaiErrMsg = xuDaiErrMsg;
	}

	public String getBatchTotal() {
		return batchTotal;
	}

	public void setBatchTotal(String batchTotal) {
		this.batchTotal = batchTotal;
	}

	public String getZjwAmount() {
		return zjwAmount;
	}

	public void setZjwAmount(String zjwAmount) {
		this.zjwAmount = zjwAmount;
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BorrowRcordVO [borrowAmount=" + borrowAmount + ", arrivalAmount=" + arrivalAmount + ", loanAmount="
				+ loanAmount + ", pFastReviewCost=" + pFastReviewCost + ", pPlatformUseCost=" + pPlatformUseCost
				+ ", pNumberManageCost=" + pNumberManageCost + ", pCollectionPassagewayCost="
				+ pCollectionPassagewayCost + ", pCapitalUseCost=" + pCapitalUseCost + ", submitTime=" + submitTime
				+ ", xuDaiCount=" + xuDaiCount + ", xuDaiAmount=" + xuDaiAmount + ", xudaiCreateTime=" + xudaiCreateTime
				+ ", repayTime=" + repayTime + ", daySpace=" + daySpace + ", overAmont=" + overAmont + ", repayMoney="
				+ repayMoney + ", updateTime=" + updateTime + ", maxAmount=" + maxAmount + ", bankName=" + bankName
				+ ", cardNoEnd=" + cardNoEnd + ", totalAmount=" + totalAmount + ", totalXudaiAmount=" + totalXudaiAmount
				+ ", statusId=" + statusId + ", term=" + term + ", distanceRepayTime=" + distanceRepayTime
				+ ", userAmount=" + userAmount + ", xuDaiRepayTime=" + xuDaiRepayTime + ", canXuDai=" + canXuDai
				+ ", xuDaiErrMsg=" + xuDaiErrMsg + "]";
	}

}
