/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.entity.xianJinCard;

/**
 * Module: XianJinCardBindCardRequest.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class XianJinCardBindCardRequest {

	private String order_sn;// 借款订单唯一编号, 推单前绑卡则为空
	private String user_name;// 用户真实姓名
	private String user_phone;// 用户手机号
	private String user_idcard;// 用户身份证号码
	private String echo_data;// 回显数据字段, 此字段仅要求在绑卡结果回调中回传即可
	private String last_card_number;// 用户最近一次使用的银行卡号
	private String last_card_phone;// 用户最近一次使用的银行卡号对应的预留手机号
	private String return_url;// 机构绑卡完成后的回跳平台地址
	private String sign;// 签名(签名规则参考文档1.5.3)

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "XianJinCardBindCardRequest [order_sn=" + order_sn + ", user_name=" + user_name + ", user_phone="
				+ user_phone + ", user_idcard=" + user_idcard + ", echo_data=" + echo_data + ", last_card_number="
				+ last_card_number + ", last_card_phone=" + last_card_phone + ", return_url=" + return_url + ", sign="
				+ sign + "]";
	}

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getUser_idcard() {
		return user_idcard;
	}

	public void setUser_idcard(String user_idcard) {
		this.user_idcard = user_idcard;
	}

	public String getEcho_data() {
		return echo_data;
	}

	public void setEcho_data(String echo_data) {
		this.echo_data = echo_data;
	}

	public String getLast_card_number() {
		return last_card_number;
	}

	public void setLast_card_number(String last_card_number) {
		this.last_card_number = last_card_number;
	}

	public String getLast_card_phone() {
		return last_card_phone;
	}

	public void setLast_card_phone(String last_card_phone) {
		this.last_card_phone = last_card_phone;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
