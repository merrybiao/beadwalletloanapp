package com.waterelephant.jiedianqian.entity;

public class RepayResponse {
	private String order_id; 		 // 合作方订单id（如果在进件的时候已经返回我方会记录）

	private String successReturnUrl; // 操作成功跳转页面

	private String errorReturnUrl;   // 操作失败跳转页面

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getSuccessReturnUrl() {
		return successReturnUrl;
	}

	public void setSuccessReturnUrl(String successReturnUrl) {
		this.successReturnUrl = successReturnUrl;
	}

	public String getErrorReturnUrl() {
		return errorReturnUrl;
	}

	public void setErrorReturnUrl(String errorReturnUrl) {
		this.errorReturnUrl = errorReturnUrl;
	}

	@Override
	public String toString() {
		return "RepayResponse [order_id=" + order_id + ", successReturnUrl=" + successReturnUrl + ", errorReturnUrl="
				+ errorReturnUrl + "]";
	}
	
}
