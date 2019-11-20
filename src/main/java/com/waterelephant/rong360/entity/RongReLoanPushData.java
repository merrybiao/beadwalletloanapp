package com.waterelephant.rong360.entity;
@Deprecated
public class RongReLoanPushData {
	private String is_reloan;
	private ReLoanOrderInfo orderInfo;
	private ReLoanApplyDetail applyDetail;
	
	public String getIs_reloan() {
		return is_reloan;
	}
	public void setIs_reloan(String is_reloan) {
		this.is_reloan = is_reloan;
	}
	public ReLoanOrderInfo getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(ReLoanOrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}
	public ReLoanApplyDetail getApplyDetail() {
		return applyDetail;
	}
	public void setApplyDetail(ReLoanApplyDetail applyDetail) {
		this.applyDetail = applyDetail;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RongReLoanPushData [");
		if (is_reloan != null) {
			builder.append("is_reloan=");
			builder.append(is_reloan);
			builder.append(", ");
		}
		if (orderInfo != null) {
			builder.append("orderInfo=");
			builder.append(orderInfo);
			builder.append(", ");
		}
		if (applyDetail != null) {
			builder.append("applyDetail=");
			builder.append(applyDetail);
		}
		builder.append("]");
		return builder.toString();
	}
}
