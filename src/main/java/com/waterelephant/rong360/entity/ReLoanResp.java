package com.waterelephant.rong360.entity;
@Deprecated
public class ReLoanResp extends Rong360Resp{
	private String reason;

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("ReLoanResp [");
		if (reason != null) {
			builder.append("reason=");
			builder.append(reason);
		}
		builder.append("]");
		return builder.toString();
	}
}