package com.waterelephant.cashloan.entity;

public class CashLoanReq {		//请求
	private String appid;		//商户号
	private String signkey;		//数据签名串
	private String data;		//业务数据(借款人)
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getSignkey() {
		return signkey;
	}
	public void setSignkey(String signkey) {
		this.signkey = signkey;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CashLoanReq [");
		if (appid != null) {
			builder.append("appid=");
			builder.append(appid);
			builder.append(", ");
		}
		if (signkey != null) {
			builder.append("signkey=");
			builder.append(signkey);
			builder.append(", ");
		}
		if (data != null) {
			builder.append("data=");
			builder.append(data);
		}
		builder.append("]");
		return builder.toString();
	}
}