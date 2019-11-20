package com.waterelephant.fudata.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author dinglinhao
 *
 */
public class DefaultFudataResponse implements Serializable {
  
  private static final long serialVersionUID = -359084019386021155L;

  @JSONField(name="return_code")
  private int code=0;

  @JSONField(name="return_message")
  private String msg="";
  
  public DefaultFudataResponse() {}
  
  public DefaultFudataResponse(int code,String msg) {
	  this.code = code;
	  this.msg = msg;
  }

public int getCode() {
	return code;
}

public void setCode(int code) {
	this.code = code;
}

public String getMsg() {
	return msg;
}

public void setMsg(String msg) {
	this.msg = msg == null ? "" : msg;
}
  
}