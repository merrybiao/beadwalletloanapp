package com.waterelephant.gxb.dto;

import java.io.Serializable;
import java.util.Date;

public class AuthInfoDto implements Serializable {
	
	private static final long serialVersionUID = 8536537920925788455L;
	
	private String appId;
      private String authItem;
      private Date authTime;
      private String idcard;
      private String phone;
      private String sequenceNo;
      private String token;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAuthItem() {
		return authItem;
	}
	public void setAuthItem(String authItem) {
		this.authItem = authItem;
	}
	public Date getAuthTime() {
		return authTime;
	}
	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
