package com.waterelephant.bjsms.entity;

public class DefaultResponse
{
  private String requestCode;
  private String requestMsg;
  
  public DefaultResponse(String requestCode, String requestMsg)
  {
    this.requestCode = requestCode;
    this.requestMsg = requestMsg;
  }
  
  public String getRequestCode()
  {
    return this.requestCode;
  }
  
  public void setRequestCode(String requestCode)
  {
    this.requestCode = requestCode;
  }
  
  public String getRequestMsg()
  {
    return this.requestMsg;
  }
  
  public void setRequestMsg(String requestMsg)
  {
    this.requestMsg = requestMsg;
  }
}
