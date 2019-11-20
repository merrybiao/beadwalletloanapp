/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.entity.qihu360;

/**
 * 
 * 
 * Module: 推送用户绑定银行卡
 * 
 * PushUserBankCard.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class PushUserBankCard {
	private String order_no;// 奇虎360订单编号
	private String bank_card;// 绑卡卡号
	private String open_bank;// 绑卡开户行编码
	private String bind_card_src;// 新旧卡标识 1=绑定新卡 2=确认旧卡
	private String card_id;// 用户在机构绑定过旧卡银行卡编号 确认旧卡时有值，新卡为空值为机构在获取银行卡列表 接口里回传的值
	private String user_name;// 姓名 新绑卡时有值，选择已有卡时可能为空
	private String id_number;// 身份证 新绑卡时有值，选择已有卡时可能为空
	private String user_mobile;// 预留手机号 新绑卡时有值，选择已有卡时可能为空
	private String bank_address;// 开户行地址（省市区）
	private String return_url;// 返回 360 的回调地址

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getBank_card() {
		return bank_card;
	}

	public void setBank_card(String bank_card) {
		this.bank_card = bank_card;
	}

	public String getOpen_bank() {
		return open_bank;
	}

	public void setOpen_bank(String open_bank) {
		this.open_bank = open_bank;
	}

	public String getBind_card_src() {
		return bind_card_src;
	}

	public void setBind_card_src(String bind_card_src) {
		this.bind_card_src = bind_card_src;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getId_number() {
		return id_number;
	}

	public void setId_number(String id_number) {
		this.id_number = id_number;
	}

	public String getUser_mobile() {
		return user_mobile;
	}

	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}

	public String getBank_address() {
		return bank_address;
	}

	public void setBank_address(String bank_address) {
		this.bank_address = bank_address;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

}
