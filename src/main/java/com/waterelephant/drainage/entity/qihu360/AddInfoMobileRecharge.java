package com.waterelephant.drainage.entity.qihu360;

/**
 *
 * Module: 用户充值记录
 * 
 * AddInfoMobileRecharge.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class AddInfoMobileRecharge {
	private String fee; // 充值金额
	private String recharge_time; // 充值时间(2017-01-01 11:11:11)(精确到秒)
	private String recharge_way; // 充值方式

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getRecharge_time() {
		return recharge_time;
	}

	public void setRecharge_time(String recharge_time) {
		this.recharge_time = recharge_time;
	}

	public String getRecharge_way() {
		return recharge_way;
	}

	public void setRecharge_way(String recharge_way) {
		this.recharge_way = recharge_way;
	}

}
