/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.entity.qihu360;

/**
 * 
 * 
 * Module:推送用户还款信息
 * 
 * PushUserRepayInfo.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class PushUserRepayInfo {
	private String order_no;// 奇虎360订单编号
	private String period_nos;// 还款期数

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getPeriod_nos() {
		return period_nos;
	}

	public void setPeriod_nos(String period_nos) {
		this.period_nos = period_nos;
	}
}
