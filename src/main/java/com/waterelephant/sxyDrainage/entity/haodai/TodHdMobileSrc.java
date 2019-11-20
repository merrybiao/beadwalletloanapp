///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.haodai;
//
//import java.util.Date;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
///**
// * TodHdMobileSrc.java
// *
// * @author: 王亚楠
// * @since: JDK 1.8
// * @version: 1.0
// * @date: 2018年5月18日
// * @Description: <运营商基本信息字段>
// * 
// */
//@Table(name = "bw_haodai_mobile")
//public class TodHdMobileSrc {
//
//	/**
//	 * 主键id
//	 */
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	private Long orderId;// 我方订单号
//	private Date createTime;
//	private Date updateTime;
//
//	private String mobile;// 本机号码
//	private String name;// 机主姓名
//	private String idcard;// 机主身份证
//	private String carrier;// 本机号码归属运营商标识，详见[数据字典，carrier(运营商标识)取值]
//	private String province;// 本机号码归属省份
//	private String city;// 本机号码归属城市
//	private Date openTime;// 入网时间，格式：yyyy-MM-dd
//	private String level;// 用户星级
//	private String packageName;// 本机号码当前套餐名称
//	private Integer state;// 本机号码状态, 详见[数据字典，state(手机状态)取值说明]
//	private Integer availableBalance;// 本机号码当前可用余额（单位: 分）
//	private Date lastModifyTime;// 数据获取时间，格式: yyyy-MM-dd HH:mm:ss
//	private String code;// 状态码，详见[数据字典，code(状态码)取值]
//	private String message;// 状态描述
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Long getOrderId() {
//		return orderId;
//	}
//
//	public void setOrderId(Long orderId) {
//		this.orderId = orderId;
//	}
//
//	public Date getCreateTime() {
//		return createTime;
//	}
//
//	public void setCreateTime(Date createTime) {
//		this.createTime = createTime;
//	}
//
//	public Date getUpdateTime() {
//		return updateTime;
//	}
//
//	public void setUpdateTime(Date updateTime) {
//		this.updateTime = updateTime;
//	}
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
//	public Date getOpenTime() {
//		return openTime;
//	}
//
//	public void setOpenTime(Date openTime) {
//		this.openTime = openTime;
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
//	public String getPackageName() {
//		return packageName;
//	}
//
//	public void setPackageName(String packageName) {
//		this.packageName = packageName;
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
//	public Integer getAvailableBalance() {
//		return availableBalance;
//	}
//
//	public void setAvailableBalance(Integer availableBalance) {
//		this.availableBalance = availableBalance;
//	}
//
//	public Date getLastModifyTime() {
//		return lastModifyTime;
//	}
//
//	public void setLastModifyTime(Date lastModifyTime) {
//		this.lastModifyTime = lastModifyTime;
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
//}
