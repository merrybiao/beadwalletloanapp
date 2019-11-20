package com.waterelephant.bajie.entity;

public class BajieRepaymentData {
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BajieRepaymentData [");
		if (orderNo != null) {
			builder.append("orderNo=");
			builder.append(orderNo);
		}
		builder.append("]");
		return builder.toString();
	}
}