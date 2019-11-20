package com.waterelephant.bajie.entity;

public class BaJieResp {
	private boolean success;
	private String errorMsg;
	private String url;
	
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaJieResp [success=");
		builder.append(success);
		builder.append(", ");
		if (errorMsg != null) {
			builder.append("errorMsg=");
			builder.append(errorMsg);
			builder.append(", ");
		}
		if (url != null) {
			builder.append("url=");
			builder.append(url);
		}
		builder.append("]");
		return builder.toString();
	}
}