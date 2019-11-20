package com.waterelephant.rong360.entity;
@Deprecated
public class RongExtendData {
	private String order_no;
	private String defer_day;
	
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getDefer_day() {
		return defer_day;
	}
	public void setDefer_day(String defer_day) {
		this.defer_day = defer_day;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RongExtendData [");
		if (order_no != null) {
			builder.append("order_no=");
			builder.append(order_no);
			builder.append(", ");
		}
		if (defer_day != null) {
			builder.append("defer_day=");
			builder.append(defer_day);
		}
		builder.append("]");
		return builder.toString();
	}
}