package com.waterelephant.rong360.entity;
@Deprecated
public class RongOrderStatusData {
	private String order_no;

	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RongOrderStatusData [");
		if (order_no != null) {
			builder.append("order_no=");
			builder.append(order_no);
		}
		builder.append("]");
		return builder.toString();
	}
}