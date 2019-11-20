package com.waterelephant.drainage.entity.fqgj;

public class OrderFeedBackReq {
	private String order_no;
	private int order_status;
	
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	
	@Override
	public String toString() {
		return "OrderFeedBackReq [" + (order_no != null ? "order_no=" + order_no + ", " : "") + "order_status="
				+ order_status + "]";
	}
}