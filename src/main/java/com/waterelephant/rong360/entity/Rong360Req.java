package com.waterelephant.rong360.entity;
@Deprecated
public class Rong360Req {

	private String biz_data;
	private String sign;
	public String getBiz_data() {
		return biz_data;
	}
	public void setBiz_data(String biz_data) {
		this.biz_data = biz_data;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	@Override
	public String toString() {
		return "Rong360Req [" + (biz_data != null ? "biz_data=" + biz_data + ", " : "")
				+ (sign != null ? "sign=" + sign : "") + "]";
	}
}
