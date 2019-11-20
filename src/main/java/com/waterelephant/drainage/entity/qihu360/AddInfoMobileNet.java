package com.waterelephant.drainage.entity.qihu360;

/**
 * 
 * 
 * 
 * Module: 用户流量信息
 * 
 * AddInfoMobileNet.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class AddInfoMobileNet {
	private String fee; // 通信费
	private String net_type; // 网络类型(4g,3g,2g)
	private String net_way; // 上网方式(CMNET等)
	private String preferential_fee; // 优惠项/套餐优惠
	private String start_time; // 起始时间(精确到秒)
	private String total_time; // 总时长
	private String tital_traffic; // 总流量
	private String trade_addr; // 通信地址

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getNet_type() {
		return net_type;
	}

	public void setNet_type(String net_type) {
		this.net_type = net_type;
	}

	public String getNet_way() {
		return net_way;
	}

	public void setNet_way(String net_way) {
		this.net_way = net_way;
	}

	public String getPreferential_fee() {
		return preferential_fee;
	}

	public void setPreferential_fee(String preferential_fee) {
		this.preferential_fee = preferential_fee;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getTotal_time() {
		return total_time;
	}

	public void setTotal_time(String total_time) {
		this.total_time = total_time;
	}

	public String getTital_traffic() {
		return tital_traffic;
	}

	public void setTital_traffic(String tital_traffic) {
		this.tital_traffic = tital_traffic;
	}

	public String getTrade_addr() {
		return trade_addr;
	}

	public void setTrade_addr(String trade_addr) {
		this.trade_addr = trade_addr;
	}

}
