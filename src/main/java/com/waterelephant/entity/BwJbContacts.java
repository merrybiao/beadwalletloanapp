// package com.waterelephant.entity;
//
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
// import java.io.Serializable;
//
/// **
// * (code:jb001)
// */
// @Table(name = "bw_jb_contacts")
// public class BwJbContacts implements Serializable {
//
// /** */
// private static final long serialVersionUID = 1L;
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;
// /**
// * //手机型号
// */
// private String devNo;
// /**
// * //安装应用的版本号
// */
// private String devVersion;
// /**
// * //更新时间
// */
// private java.util.Date updateTime;
// /**
// * //创建时间
// */
// private java.util.Date createTime;
// /**
// * //经纬度用下划线拼接，经度_纬度
// */
// private String appLocation;
// /**
// * //手机设备号（iOS是IDFA，Andr是IMEI）
// */
// private String devNum;
// /**
// * //订单id
// */
// private Long orderId;
// /**
// * //手机平台 1表示android,2表示ios
// */
// private String platform;
//
// public Long getId() {
// return this.id;
// }
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public String getDevNo() {
// return this.devNo;
// }
//
// public void setDevNo(String devNo) {
// this.devNo = devNo;
// }
//
// public String getDevVersion() {
// return this.devVersion;
// }
//
// public void setDevVersion(String devVersion) {
// this.devVersion = devVersion;
// }
//
// public java.util.Date getUpdateTime() {
// return this.updateTime;
// }
//
// public void setUpdateTime(java.util.Date updateTime) {
// this.updateTime = updateTime;
// }
//
// public java.util.Date getCreateTime() {
// return this.createTime;
// }
//
// public void setCreateTime(java.util.Date createTime) {
// this.createTime = createTime;
// }
//
// public String getAppLocation() {
// return this.appLocation;
// }
//
// public void setAppLocation(String appLocation) {
// this.appLocation = appLocation;
// }
//
// public String getDevNum() {
// return this.devNum;
// }
//
// public void setDevNum(String devNum) {
// this.devNum = devNum;
// }
//
// public Long getOrderId() {
// return this.orderId;
// }
//
// public void setOrderId(Long orderId) {
// this.orderId = orderId;
// }
//
// public String getPlatform() {
// return this.platform;
// }
//
// public void setPlatform(String platform) {
// this.platform = platform;
// }
//
// }
