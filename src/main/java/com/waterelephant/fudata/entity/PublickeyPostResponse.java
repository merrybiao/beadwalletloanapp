package com.waterelephant.fudata.entity;


import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class PublickeyPostResponse extends DefaultFudataResponse{

  private static final long serialVersionUID = -6838502665506538307L;

  @JSONField(name="publickey")
  private String publickey;

  @JSONField(name="publickey_expiry")
  private Date publickeyExpiry;

  @JSONField(name="publickey_id")
  private String publickeyId;

  public String getPublickey()
  {
    return this.publickey;
  }

  public void setPublickey(String publickey) {
    this.publickey = publickey;
  }

  public Date getPublickeyExpiry() {
    return this.publickeyExpiry;
  }

  public void setPublickeyExpiry(Date publickeyExpiry) {
    this.publickeyExpiry = publickeyExpiry;
  }

  public String getPublickeyId() {
    return this.publickeyId;
  }

  public void setPublickeyId(String publickeyId) {
    this.publickeyId = publickeyId;
  }
}
