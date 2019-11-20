package com.waterelephant.rongCarrier.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bw_data_external")
public class BwDataExternal implements Serializable{
	
	private static final long serialVersionUID = 6751404892658097484L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // ID主键
	//授权类型	：运营商报告（mobile）京东报告（jd）支付宝报告（alipay）网银信用卡报告（credit）	邮箱信用卡报告（credit_email）
	private String type;
	private String platform;//请求的平台，可取值范围是api,web
	private String userId;//调用方生成的用户ID（调用方定义，长度最大为64位）
	private String outUniqueId;//调用方生成的会话唯一标识id，建议使用流水号生成（调用方定义，长度最大为64位）
	private String name;//姓名
	private String idNumber;//身份证号码
	private String phone;//手机号(运营商报告请与传给抓取服务的手机号一致)
	private String authChannel;//授权渠道
	private String notifyUrl;//通知地址
	private String returnUrl;//返回地址
	private String state;//状态
	private String searchId;//查询Id
	private String account;//抓取的账户名
	private String accountType;//账户类型
	private String errorReasonDetail;//错误详情
	private Date createdTime;//创建时间
	private Date lastModifyTime;//最后操作时间
	private String emergencyName1;//紧急联系人
	private String emergencyPhone1;//紧急联系人电话
	private String emergencyRelation1;//关系
	private String emergencyName2;//紧急联系人
	private String emergencyPhone2;//紧急联系人电话
	private String emergencyRelation2;//关系
	private String exts;//透传、扩展字段
	
	public BwDataExternal() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOutUniqueId() {
		return outUniqueId;
	}
	public void setOutUniqueId(String outUniqueId) {
		this.outUniqueId = outUniqueId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAuthChannel() {
		return authChannel;
	}
	public void setAuthChannel(String authChannel) {
		this.authChannel = authChannel;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSearchId() {
		return searchId;
	}
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public String getEmergencyName1() {
		return emergencyName1;
	}
	public void setEmergencyName1(String emergencyName1) {
		this.emergencyName1 = emergencyName1;
	}
	public String getEmergencyPhone1() {
		return emergencyPhone1;
	}
	public void setEmergencyPhone1(String emergencyPhone1) {
		this.emergencyPhone1 = emergencyPhone1;
	}
	public String getEmergencyName2() {
		return emergencyName2;
	}
	public void setEmergencyName2(String emergencyName2) {
		this.emergencyName2 = emergencyName2;
	}
	public String getEmergencyPhone2() {
		return emergencyPhone2;
	}
	public void setEmergencyPhone2(String emergencyPhone2) {
		this.emergencyPhone2 = emergencyPhone2;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getErrorReasonDetail() {
		return errorReasonDetail;
	}
	public void setErrorReasonDetail(String errorReasonDetail) {
		this.errorReasonDetail = errorReasonDetail;
	}
	public String getEmergencyRelation1() {
		return emergencyRelation1;
	}
	public void setEmergencyRelation1(String emergencyRelation1) {
		this.emergencyRelation1 = emergencyRelation1;
	}
	public String getEmergencyRelation2() {
		return emergencyRelation2;
	}
	public void setEmergencyRelation2(String emergencyRelation2) {
		this.emergencyRelation2 = emergencyRelation2;
	}
	public String getExts() {
		return exts;
	}
	public void setExts(String exts) {
		this.exts = exts;
	}
	
}
