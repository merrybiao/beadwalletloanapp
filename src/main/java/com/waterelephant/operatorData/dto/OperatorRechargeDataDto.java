package com.waterelephant.operatorData.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class OperatorRechargeDataDto {
	
	@JSONField(ordinal=1)
	private String fee;
	@JSONField(name="recharge_time",ordinal=2)
	private String rechargeTime;
	@JSONField(name="recharge_way",ordinal=3)
	private String rechargeWay;
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getRechargeWay() {
		return rechargeWay;
	}
	public void setRechargeWay(String rechargeWay) {
		this.rechargeWay = rechargeWay;
	}
	public String getRechargeTime() {
		return rechargeTime;
	}
	public void setRechargeTime(String rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	
		
}
