package com.waterelephant.drainage.entity.fqgj;

public class RepayFeedBackResp extends BaseResp{
	private String is_api_v3_order_repaymentfeedback_responese;

	public String getIs_api_v3_order_repaymentfeedback_responese() {
		return is_api_v3_order_repaymentfeedback_responese;
	}

	public void setIs_api_v3_order_repaymentfeedback_responese(String is_api_v3_order_repaymentfeedback_responese) {
		this.is_api_v3_order_repaymentfeedback_responese = is_api_v3_order_repaymentfeedback_responese;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("RepayFeedBackResp [");
		if (is_api_v3_order_repaymentfeedback_responese != null) {
			builder.append("is_api_v3_order_repaymentfeedback_responese=");
			builder.append(is_api_v3_order_repaymentfeedback_responese);
		}
		builder.append("]");
		return builder.toString();
	}
}