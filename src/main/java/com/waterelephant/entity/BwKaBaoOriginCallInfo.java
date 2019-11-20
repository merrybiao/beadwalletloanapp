// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kabao_origin_call_info")
// public class BwKaBaoOriginCallInfo implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private String account;//
// private String month;// 通话月份yyyyMM
// private String commPlac;// 通话地
// private Date startTime;// 通话开始时间 yyyy-MM-dd HH:mm:sss
// private Long commTime;// 通话时长
// private String anotherNm;// 对方号码
// private Double commFee;// 通话费用0.0
// private String commMode;// 通话模式，主叫，被叫
// private String commType;// 通话类型，本地通话，国内漫游通话
// private Long orderId;//
// private Date createTime;//
// private Date updateTime;//
//
//
// public String getAccount() {
// return account;
// }
//
// public void setAccount(String account) {
// this.account = account;
// }
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public Long getId() {
// return id;
// }
//
// public void setMonth(String month) {
// this.month = month;
// }
//
// public String getMonth() {
// return month;
// }
//
// public void setCommPlac(String commPlac) {
// this.commPlac = commPlac;
// }
//
// public String getCommPlac() {
// return commPlac;
// }
//
// public void setStartTime(Date startTime) {
// this.startTime = startTime;
// }
//
// public Date getStartTime() {
// return startTime;
// }
//
// public Long getCommTime() {
// return commTime;
// }
//
// public void setCommTime(Long commTime) {
// this.commTime = commTime;
// }
//
// public void setAnotherNm(String anotherNm) {
// this.anotherNm = anotherNm;
// }
//
// public String getAnotherNm() {
// return anotherNm;
// }
//
// public void setCommFee(Double commFee) {
// this.commFee = commFee;
// }
//
// public Double getCommFee() {
// return commFee;
// }
//
// public void setCommMode(String commMode) {
// this.commMode = commMode;
// }
//
// public String getCommMode() {
// return commMode;
// }
//
// public void setCommType(String commType) {
// this.commType = commType;
// }
//
// public String getCommType() {
// return commType;
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
// public void setCreateTime(Date createTime) {
// this.createTime = createTime;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
//
// public void setUpdateTime(Date updateTime) {
// this.updateTime = updateTime;
// }
//
// public Date getUpdateTime() {
// return updateTime;
// }
// }
