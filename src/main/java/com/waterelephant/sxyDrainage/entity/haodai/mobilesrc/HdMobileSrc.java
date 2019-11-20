///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.haodai.mobilesrc;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * HdMobileSrc.java
// *
// * @author: 王亚楠
// * @since: JDK 1.8
// * @version: 1.0
// * @date: 2018年5月10日
// * @Description: <运营商基本信息字段>
// * 
// */
//public class HdMobileSrc {
//	private String mobile;// 本机号码
//	private String name;// 机主姓名
//	private String idcard;// 机主身份证
//	private String carrier;// 本机号码归属运营商标识，详见[数据字典，carrier(运营商标识)取值]
//	private String province;// 本机号码归属省份
//	private String city;// 本机号码归属城市
//	private Date open_time;// 入网时间，格式：yyyy-MM-dd
//	private String level;// 用户星级
//	private String package_name;// 本机号码当前套餐名称
//	private Integer state;// 本机号码状态, 详见[数据字典，state(手机状态)取值说明]
//	private Integer available_balance;// 本机号码当前可用余额（单位: 分）
//	private Date last_modify_time;// 数据获取时间，格式: yyyy-MM-dd HH:mm:ss
//	private String code;// 状态码，详见[数据字典，code(状态码)取值]
//	private String message;// 状态描述
//
//	// List<HdPackage> packages; // 套餐
//	// private List<HdFamily> families;// 亲情网
//	// private List<HdRecharge> recharges;// 充值记录
//	// private List<HdBill> bills;// 账单信息
//	private List<HdCall> calls;// 语音详情
//	// private List<HdSms> smses;// 短信详情
//	// private List<HdNet> nets;// 流量详情
//
//	public String getMobile() {
//		return mobile;
//	}
//
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getIdcard() {
//		return idcard;
//	}
//
//	public void setIdcard(String idcard) {
//		this.idcard = idcard;
//	}
//
//	public String getCarrier() {
//		return carrier;
//	}
//
//	public void setCarrier(String carrier) {
//		this.carrier = carrier;
//	}
//
//	public String getProvince() {
//		return province;
//	}
//
//	public void setProvince(String province) {
//		this.province = province;
//	}
//
//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public Date getOpen_time() {
//		return open_time;
//	}
//
//	public void setOpen_time(Date open_time) {
//		this.open_time = open_time;
//	}
//
//	public String getLevel() {
//		return level;
//	}
//
//	public void setLevel(String level) {
//		this.level = level;
//	}
//
//	public String getPackage_name() {
//		return package_name;
//	}
//
//	public void setPackage_name(String package_name) {
//		this.package_name = package_name;
//	}
//
//	public Integer getState() {
//		return state;
//	}
//
//	public void setState(Integer state) {
//		this.state = state;
//	}
//
//	public Integer getAvailable_balance() {
//		return available_balance;
//	}
//
//	public void setAvailable_balance(Integer available_balance) {
//		this.available_balance = available_balance;
//	}
//
//	public Date getLast_modify_time() {
//		return last_modify_time;
//	}
//
//	public void setLast_modify_time(Date last_modify_time) {
//		this.last_modify_time = last_modify_time;
//	}
//
//	public String getCode() {
//		return code;
//	}
//
//	public void setCode(String code) {
//		this.code = code;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//	public List<HdCall> getCalls() {
//		return calls;
//	}
//
//	public void setCalls(List<HdCall> calls) {
//		this.calls = calls;
//	}
//
//}
