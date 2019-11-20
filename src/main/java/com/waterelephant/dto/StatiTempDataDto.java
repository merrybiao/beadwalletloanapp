package com.waterelephant.dto;

import java.util.List;
import java.util.Map;

public class StatiTempDataDto {

	private Long orderCount;// 当天总单量
	private Double orderAmount;// 当天总放款额
	private Long signCount;// 当天未签约量
	private Long pushCount;// 当天未推送成功或未满标返回的单量
	private Long checkCount;//初审单量
	private Long repayCount;//还款单量
	private List<Map<String, Object>> list;// 最近一周的总单量

	public Long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getSignCount() {
		return signCount;
	}

	public void setSignCount(Long signCount) {
		this.signCount = signCount;
	}

	public Long getPushCount() {
		return pushCount;
	}

	public void setPushCount(Long pushCount) {
		this.pushCount = pushCount;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public Long getCheckCount() {
		return checkCount;
	}

	public void setCheckCount(Long checkCount) {
		this.checkCount = checkCount;
	}

	public Long getRepayCount() {
		return repayCount;
	}

	public void setRepayCount(Long repayCount) {
		this.repayCount = repayCount;
	}

}
