package com.waterelephant.drainage.entity.fqgj;

public class OrderFeedBackResp extends BaseResp{
	private String is_api_v3_order_orderfeedback_response;

	public String isIs_api_v3_order_orderfeedback_response() {
		return is_api_v3_order_orderfeedback_response;
	}
	public void setIs_api_v3_order_orderfeedback_response(String is_api_v3_order_orderfeedback_response) {
		this.is_api_v3_order_orderfeedback_response = is_api_v3_order_orderfeedback_response;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("OrderFeedBackResp [");
		if (is_api_v3_order_orderfeedback_response != null) {
			builder.append("is_api_v3_order_orderfeedback_response=");
			builder.append(is_api_v3_order_orderfeedback_response);
		}
		builder.append("]");
		return builder.toString();
	}
	
	
}