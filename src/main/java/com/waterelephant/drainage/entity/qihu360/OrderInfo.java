/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.entity.qihu360;

import java.util.Date;

/**
 * 
 * 
 * Module: 订单基本信息
 * 
 * OrderInfo.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class OrderInfo {
	private String order_no;// 奇虎360订单编号
	private Integer is_reloan;// 是否复贷流程订单 1=是, 0=否
	private String user_name;// 用户姓名
	private String user_mobile;// 用户手机号
	private String application_amount;// 申请金额（元）
	private Integer application_term;// 申请期限
	private Integer term_unit;// 期限单位，1=天，2=月
	private Date order_time;// 订单创建时间（精确到秒）
	private String bank;// 机构名
	private String product;// 申请的产品名
	private String product_id;// 产品 id，由 360分配给机构产品的 ID号

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public Integer getIs_reloan() {
		return is_reloan;
	}

	public void setIs_reloan(Integer is_reloan) {
		this.is_reloan = is_reloan;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_mobile() {
		return user_mobile;
	}

	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}

	public String getApplication_amount() {
		return application_amount;
	}

	public void setApplication_amount(String application_amount) {
		this.application_amount = application_amount;
	}

	public Integer getApplication_term() {
		return application_term;
	}

	public void setApplication_term(Integer application_term) {
		this.application_term = application_term;
	}

	public Integer getTerm_unit() {
		return term_unit;
	}

	public void setTerm_unit(Integer term_unit) {
		this.term_unit = term_unit;
	}

	public Date getOrder_time() {
		return order_time;
	}

	public void setOrder_time(Date order_time) {
		this.order_time = order_time;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

}
