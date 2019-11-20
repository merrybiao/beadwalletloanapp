/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.baidu.entity;

import java.io.Serializable;

/**
 * 
 * 
 * Module:
 * 
 * DataEntity.java
 * 
 * @author 甘立思
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class DataEntity implements Serializable {
	/**  */
	private static final long serialVersionUID = 1L;
	private String merchant_code;
	private String order_id;
	private String mobile;

	/**
	 * @return 获取 merchant_code属性值
	 */
	public String getMerchant_code() {
		return merchant_code;
	}

	/**
	 * @param merchant_code 设置 merchant_code 属性值为参数值 merchant_code
	 */
	public void setMerchant_code(String merchant_code) {
		this.merchant_code = merchant_code;
	}

	/**
	 * @return 获取 order_id属性值
	 */
	public String getOrder_id() {
		return order_id;
	}

	/**
	 * @param order_id 设置 order_id 属性值为参数值 order_id
	 */
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	/**
	 * @return 获取 mobile属性值
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile 设置 mobile 属性值为参数值 mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
