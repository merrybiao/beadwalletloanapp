package com.waterelephant.sctx.entity;

import com.waterelephant.bjsms.entity.DefaultResponse;

public class SctxResponse extends DefaultResponse{

	public SctxResponse(String requestCode, String requestMsg) {
		super(requestCode, requestMsg);
	}
	
	private String phone_apply;

	public String getPhone_apply() {
		return phone_apply;
	}
	
	

	public SctxResponse(String requestCode, String requestMsg,
			String phone_apply) {
		super(requestCode, requestMsg);
		this.phone_apply = phone_apply;
	}


	public void setPhone_apply(String phone_apply) {
		this.phone_apply = phone_apply;
	}
}
