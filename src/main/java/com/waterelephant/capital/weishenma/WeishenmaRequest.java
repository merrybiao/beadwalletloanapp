package com.waterelephant.capital.weishenma;

import java.util.List;

public class WeishenmaRequest {
	public List <String> orderNo;
	public String timestamp;
	public String sign;
	protected List<String> getOrderNo() {
		return orderNo;
	}
	protected void setOrderNo(List<String> orderNo) {
		this.orderNo = orderNo;
	}
	protected String getTimestamp() {
		return timestamp;
	}
	protected void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	protected String getSign() {
		return sign;
	}
	protected void setSign(String sign) {
		this.sign = sign;
	}
	
	
	
}
