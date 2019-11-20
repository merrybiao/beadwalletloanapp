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
 * ResponseDeferInfo.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class ResponseDeferInfo {
	private String deferBeginDate;// 展期开始日
	private String thirdOrderNo; // 机构订单流水号
	private int deferAomountType;// 展期金额类型(0：展期详情中展期费用固定, 1：展期详情中展期费用可变)
	private List<DeferAmountOption> deferAmountOptionList;// 展期期限选项对应的当前支付金额选项
	private List<DeferOption> deferOptionList;// 展期期限及相关费用

	public String getDeferBeginDate() {
		return deferBeginDate;
	}

	public void setDeferBeginDate(String deferBeginDate) {
		this.deferBeginDate = deferBeginDate;
	}

	public String getThirdOrderNo() {
		return thirdOrderNo;
	}

	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}

	public int getDeferAomountType() {
		return deferAomountType;
	}

	public void setDeferAomountType(int deferAomountType) {
		this.deferAomountType = deferAomountType;
	}

	public List<DeferAmountOption> getDeferAmountOptionList() {
		return deferAmountOptionList;
	}

	public void setDeferAmountOptionList(List<DeferAmountOption> deferAmountOptionList) {
		this.deferAmountOptionList = deferAmountOptionList;
	}

	public List<DeferOption> getDeferOptionList() {
		return deferOptionList;
	}

	public void setDeferOptionList(List<DeferOption> deferOptionList) {
		this.deferOptionList = deferOptionList;
	}

}
