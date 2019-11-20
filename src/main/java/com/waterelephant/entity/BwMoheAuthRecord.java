package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_mohe_auth_record")
public class BwMoheAuthRecord implements Serializable {

	private static final long serialVersionUID = 5278712333859641716L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;//主键自增ID
	private String productNo;//产品来源
	private String reqId;//请求ID业务流水号
	private String realName;//姓名
	private String idcardNum;//身份证号
	private String userMobile;//手机号
	private String authType;//授权类型(运营商，淘宝，京东，社保)
	private String authReturnUrl;//同步地址
	private String authNotifyUrl;//通知地址
	private Date createTime;//创建时间
	private String taskId;//任务ID
	private String notifyEvent;//通知事件
	private String notifyType;//通知类型
	private Date notifyTime;//通知时间
	private Date updateTime;//更新时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdcardNum() {
		return idcardNum;
	}
	public void setIdcardNum(String idcardNum) {
		this.idcardNum = idcardNum;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public String getAuthReturnUrl() {
		return authReturnUrl;
	}
	public void setAuthReturnUrl(String authReturnUrl) {
		this.authReturnUrl = authReturnUrl;
	}
	public String getAuthNotifyUrl() {
		return authNotifyUrl;
	}
	public void setAuthNotifyUrl(String authNotifyUrl) {
		this.authNotifyUrl = authNotifyUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getNotifyEvent() {
		return notifyEvent;
	}
	public void setNotifyEvent(String notifyEvent) {
		this.notifyEvent = notifyEvent;
	}
	public String getNotifyType() {
		return notifyType;
	}
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}
	public Date getNotifyTime() {
		return notifyTime;
	}
	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
