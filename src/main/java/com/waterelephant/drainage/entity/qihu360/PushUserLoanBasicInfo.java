/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.entity.qihu360;

/**
 * 
 * 
 * Module: 推送用户借款基本信息
 * 
 * PushUserLoanBasicInfo.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class PushUserLoanBasicInfo {
	private OrderInfo orderInfo;// 订单基本信息
	private BaseInfo applyDetail;// 用户填写的基本信息
	private AddInfo addInfo;// 抓取的数据，包含1.信用分（credit）,2.运营商数据（mobile）

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public BaseInfo getApplyDetail() {
		return applyDetail;
	}

	public void setApplyDetail(BaseInfo applyDetail) {
		this.applyDetail = applyDetail;
	}

	public AddInfo getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(AddInfo addInfo) {
		this.addInfo = addInfo;
	}

}
