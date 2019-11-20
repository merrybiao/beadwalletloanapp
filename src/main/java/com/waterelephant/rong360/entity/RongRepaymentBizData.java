package com.waterelephant.rong360.entity;
@Deprecated
public class RongRepaymentBizData {
	private String order_no;
	private String period_nos;
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getPeriod_nos() {
		return period_nos;
	}
	public void setPeriod_nos(String period_nos) {
		this.period_nos = period_nos;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RongRepaymentBizData [");
		if (order_no != null) {
			builder.append("order_no=");
			builder.append(order_no);
			builder.append(", ");
		}
		if (period_nos != null) {
			builder.append("period_nos=");
			builder.append(period_nos);
		}
		builder.append("]");
		return builder.toString();
	}
}