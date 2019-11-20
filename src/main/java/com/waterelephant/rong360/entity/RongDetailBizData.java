package com.waterelephant.rong360.entity;
@Deprecated
public class RongDetailBizData {

	private OrderInfo orderInfo;
	private ApplyDetail applyDetail;
	private AddInfo addInfo;
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}
	public ApplyDetail getApplyDetail() {
		return applyDetail;
	}
	public void setApplyDetail(ApplyDetail applyDetail) {
		this.applyDetail = applyDetail;
	}
	public AddInfo getAddInfo() {
		return addInfo;
	}
	public void setAddInfo(AddInfo addInfo) {
		this.addInfo = addInfo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RongDetailBizData [");
		if (orderInfo != null) {
			builder.append("orderInfo=");
			builder.append(orderInfo);
			builder.append(", ");
		}
		if (applyDetail != null) {
			builder.append("applyDetail=");
			builder.append(applyDetail);
			builder.append(", ");
		}
		if (addInfo != null) {
			builder.append("addInfo=");
			builder.append(addInfo);
		}
		builder.append("]");
		return builder.toString();
	}
}