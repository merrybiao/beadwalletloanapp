package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 三方订单
 * @author dengyan
 *
 */
public class ThirdOrder {

	@JSONField(name = "order_info")
	private OrderInfo orderInfo;
	
	@JSONField(name = "apply_detail")
	private ApplyDetail applyDetail;
	
	@JSONField(name = "add_info")
	private AddInfo addInfo;

	@JSONField(name = "id_card_photoes")
	private IdCardPhotoes idCardPhotoes;

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public ApplyDetail getApplyDetail() {
		return applyDetail;
	}

	public void setApplyDetail(ApplyDetail applyDetail) {
		this.applyDetail = applyDetail;
	}

	public AddInfo getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(AddInfo addInfo) {
		this.addInfo = addInfo;
	}

	public IdCardPhotoes getIdCardPhotoes() {
		return idCardPhotoes;
	}

	public void setIdCardPhotoes(IdCardPhotoes idCardPhotoes) {
		this.idCardPhotoes = idCardPhotoes;
	}
}
