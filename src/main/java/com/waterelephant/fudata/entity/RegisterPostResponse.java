/**
 * @author dinglinhao
 *
 */
package com.waterelephant.fudata.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class RegisterPostResponse extends DefaultFudataResponse {

  private static final long serialVersionUID = 8014420218060655484L;

  @JSONField(name="open_id")
  private String openId;
  
  public RegisterPostResponse() {}
  
	public String getOpenId() {
		return openId;
	}
	
	public void setOpenId(String openId) {
		this.openId = openId;
	}

  
}