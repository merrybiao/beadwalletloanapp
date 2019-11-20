package com.waterelephant.fudata.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class TokenPostResponse extends DefaultFudataResponse {

  private static final long serialVersionUID = -5546448113532240880L;

  @JSONField(name="token")
  private String token;

  @JSONField(name="token_expiry")
  private Date tokenExpiry;

  public String getToken()
  {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Date getTokenExpiry() {
    return this.tokenExpiry;
  }

  public void setTokenExpiry(Date tokenExpiry) {
    this.tokenExpiry = tokenExpiry;
  }
}
