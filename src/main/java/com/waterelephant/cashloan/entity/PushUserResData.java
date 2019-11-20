package com.waterelephant.cashloan.entity;

public class PushUserResData {		//响应结果, 回调的data
	private String referrer_url;
	private String is_succeed;
	
	public String getReferrer_url() {
		return referrer_url;
	}
	public void setReferrer_url(String referrer_url) {
		this.referrer_url = referrer_url;
	}
	public String getIs_succeed() {
		return is_succeed;
	}
	public void setIs_succeed(String is_succeed) {
		this.is_succeed = is_succeed;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PushUserResData [");
		if (referrer_url != null) {
			builder.append("referrer_url=");
			builder.append(referrer_url);
			builder.append(", ");
		}
		if (is_succeed != null) {
			builder.append("is_succeed=");
			builder.append(is_succeed);
		}
		builder.append("]");
		return builder.toString();
	}
}