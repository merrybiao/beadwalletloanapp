package com.waterelephant.bjsms.entity;

import java.util.Date;

import org.springframework.data.elasticsearch.annotations.Document;



@Document(indexName="lianlu_info", type="es_message_info")
public class BwLianluMessageInfo {
	
  private String id;
  private String msgid;
  private String mobile;
  private String content;
  private String sign;
  private Integer count;
  private Date createTime;
  private String taskid;
  private String status;
  private String errorcode;
  private String receivetime;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getMsgid() {
	return msgid;
}
public void setMsgid(String msgid) {
	this.msgid = msgid;
}
public String getMobile() {
	return mobile;
}
public void setMobile(String mobile) {
	this.mobile = mobile;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getSign() {
	return sign;
}
public void setSign(String sign) {
	this.sign = sign;
}
public Integer getCount() {
	return count;
}
public void setCount(Integer count) {
	this.count = count;
}
public Date getCreateTime() {
	return createTime;
}
public void setCreateTime(Date createTime) {
	this.createTime = createTime;
}
public String getTaskid() {
	return taskid;
}
public void setTaskid(String taskid) {
	this.taskid = taskid;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getErrorcode() {
	return errorcode;
}
public void setErrorcode(String errorcode) {
	this.errorcode = errorcode;
}
public String getReceivetime() {
	return receivetime;
}
public void setReceivetime(String receivetime) {
	this.receivetime = receivetime;
}
  
  
}