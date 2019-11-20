package com.waterelephant.drainage.entity.fqgj;

public class RepayFeedBackReq {
	private String order_no;
	private String period_nos;
	private String repay_status;
	private String repay_place;
	private String remark;
	
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
	public String getRepay_status() {
		return repay_status;
	}
	public void setRepay_status(String repay_status) {
		this.repay_status = repay_status;
	}
	public String getRepay_place() {
		return repay_place;
	}
	public void setRepay_place(String repay_place) {
		this.repay_place = repay_place;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RepayFeedBackReq [");
		if (order_no != null) {
			builder.append("order_no=");
			builder.append(order_no);
			builder.append(", ");
		}
		if (period_nos != null) {
			builder.append("period_nos=");
			builder.append(period_nos);
			builder.append(", ");
		}
		if (repay_status != null) {
			builder.append("repay_status=");
			builder.append(repay_status);
			builder.append(", ");
		}
		if (repay_place != null) {
			builder.append("repay_place=");
			builder.append(repay_place);
			builder.append(", ");
		}
		if (remark != null) {
			builder.append("remark=");
			builder.append(remark);
		}
		builder.append("]");
		return builder.toString();
	}
}