// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kaniu_lbs")
// public class BwKaniuLbs implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;//
// private String userkn90dChangecityNum;// 最近三月更换城市次数
// private String userkn90dChangeprovinceNum;// 最近三月跨省次数
// private String userknLastcity;// 最后登录城市
// private String userknLastprovince;// 最后登录省份
// private String userknPhoneCity;// 用户号码归属城市
// private String userknPhoneProvince;// 用户号码归属省
// private String userknLastlocation;// 设备位置
// private String userknWorkLocation;// 工作时间活跃地点
// private String userknNoworkLocation;// 休息时间活跃地点
// private Date createTime;//
//
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public Long getId() {
// return id;
// }
//
// public void setOrderId(Long orderId) {
// this.orderId = orderId;
// }
//
// public Long getOrderId() {
// return orderId;
// }
//
// public void setUserkn90dChangecityNum(String userkn90dChangecityNum) {
// this.userkn90dChangecityNum = userkn90dChangecityNum;
// }
//
// public String getUserkn90dChangecityNum() {
// return userkn90dChangecityNum;
// }
//
// public void setUserkn90dChangeprovinceNum(String userkn90dChangeprovinceNum) {
// this.userkn90dChangeprovinceNum = userkn90dChangeprovinceNum;
// }
//
// public String getUserkn90dChangeprovinceNum() {
// return userkn90dChangeprovinceNum;
// }
//
// public void setUserknLastcity(String userknLastcity) {
// this.userknLastcity = userknLastcity;
// }
//
// public String getUserknLastcity() {
// return userknLastcity;
// }
//
// public void setUserknLastprovince(String userknLastprovince) {
// this.userknLastprovince = userknLastprovince;
// }
//
// public String getUserknLastprovince() {
// return userknLastprovince;
// }
//
// public void setUserknPhoneCity(String userknPhoneCity) {
// this.userknPhoneCity = userknPhoneCity;
// }
//
// public String getUserknPhoneCity() {
// return userknPhoneCity;
// }
//
// public void setUserknPhoneProvince(String userknPhoneProvince) {
// this.userknPhoneProvince = userknPhoneProvince;
// }
//
// public String getUserknPhoneProvince() {
// return userknPhoneProvince;
// }
//
// public void setUserknLastlocation(String userknLastlocation) {
// this.userknLastlocation = userknLastlocation;
// }
//
// public String getUserknLastlocation() {
// return userknLastlocation;
// }
//
// public void setUserknWorkLocation(String userknWorkLocation) {
// this.userknWorkLocation = userknWorkLocation;
// }
//
// public String getUserknWorkLocation() {
// return userknWorkLocation;
// }
//
// public void setUserknNoworkLocation(String userknNoworkLocation) {
// this.userknNoworkLocation = userknNoworkLocation;
// }
//
// public String getUserknNoworkLocation() {
// return userknNoworkLocation;
// }
//
// public void setCreateTime(Date createTime) {
// this.createTime = createTime;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
// }
