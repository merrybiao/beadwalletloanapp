// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kabao_report_address_list")
// public class BwKabaoReportAddressList implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private String account;//
// private Integer callInCount;// 被叫次数
// private Integer totalCount;// 通话次数
// private Integer callOutCount;// 主叫次数
// private Long callOutTime;// 主叫时长
// private Long totalTime;// 通话时长
// private String callAddr;//
// private Long callInTime;// 被叫时长
// private Long orderId;//
// private Date createTime;//
// private Date updateTime;//
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
// public void setAccount(String account) {
// this.account = account;
// }
//
// public String getAccount() {
// return account;
// }
//
// public void setCallInCount(Integer callInCount) {
// this.callInCount = callInCount;
// }
//
// public Integer getCallInCount() {
// return callInCount;
// }
//
// public void setTotalCount(Integer totalCount) {
// this.totalCount = totalCount;
// }
//
// public Integer getTotalCount() {
// return totalCount;
// }
//
// public void setCallOutCount(Integer callOutCount) {
// this.callOutCount = callOutCount;
// }
//
// public Integer getCallOutCount() {
// return callOutCount;
// }
//
// public void setCallOutTime(Long callOutTime) {
// this.callOutTime = callOutTime;
// }
//
// public Long getCallOutTime() {
// return callOutTime;
// }
//
// public void setTotalTime(Long totalTime) {
// this.totalTime = totalTime;
// }
//
// public Long getTotalTime() {
// return totalTime;
// }
//
// public void setCallAddr(String callAddr) {
// this.callAddr = callAddr;
// }
//
// public String getCallAddr() {
// return callAddr;
// }
//
// public void setCallInTime(Long callInTime) {
// this.callInTime = callInTime;
// }
//
// public Long getCallInTime() {
// return callInTime;
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
