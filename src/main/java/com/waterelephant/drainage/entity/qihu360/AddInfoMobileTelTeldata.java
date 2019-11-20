package com.waterelephant.drainage.entity.qihu360;

/**
 * 
 * 
 * 
 * Module:通话记录
 * 
 * AddInfoMobileTelTeldata.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class AddInfoMobileTelTeldata {
	private String trade_type; // 通信类型（1=本地,2=漫游国内,3=其他）
	private String trade_time; // 通话时长
	private String call_time; // 通话时间(精确到秒)
	private String trade_addr; // 通话地点
	private String receive_phone; // 对方号码
	private String call_type; // 呼叫类型（1=主叫,2=被叫,3=未识别状态）
	private String business_name; // 业务类型
	private String fee; // 费用
	private String special_offer; // 特殊费用

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getTrade_time() {
		return trade_time;
	}

	public void setTrade_time(String trade_time) {
		this.trade_time = trade_time;
	}

	public String getCall_time() {
		return call_time;
	}

	public void setCall_time(String call_time) {
		this.call_time = call_time;
	}

	public String getTrade_addr() {
		return trade_addr;
	}

	public void setTrade_addr(String trade_addr) {
		this.trade_addr = trade_addr;
	}

	public String getReceive_phone() {
		return receive_phone;
	}

	public void setReceive_phone(String receive_phone) {
		this.receive_phone = receive_phone;
	}

	public String getCall_type() {
		return call_type;
	}

	public void setCall_type(String call_type) {
		this.call_type = call_type;
	}

	public String getBusiness_name() {
		return business_name;
	}

	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getSpecial_offer() {
		return special_offer;
	}

	public void setSpecial_offer(String special_offer) {
		this.special_offer = special_offer;
	}

}
