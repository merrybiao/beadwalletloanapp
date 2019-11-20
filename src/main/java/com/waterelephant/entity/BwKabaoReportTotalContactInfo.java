// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kabao_report_total_contact_info")
// public class BwKabaoReportTotalContactInfo implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Integer callInCount;// 被叫次数
// private String account;//
// private String phone;// 电话
// private String phoneBookName;// 在通讯录中姓名(若无则不显示)
// private Integer totalCount;// 通话次数
// private Integer callOutCount;// 主叫次数
// private Long callOutTime;// 主叫时长
// private Date lastCallTime;// 最近通话时间
// private Long totalTime;// 通话时长
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
// public void setCallInCount(Integer callInCount) {
// this.callInCount = callInCount;
// }
//
// public Integer getCallInCount() {
// return callInCount;
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
// public void setPhone(String phone) {
// this.phone = phone;
// }
//
// public String getPhone() {
// return phone;
// }
//
// public void setPhoneBookName(String phoneBookName) {
// this.phoneBookName = phoneBookName;
// }
//
// public String getPhoneBookName() {
// return phoneBookName;
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
// public void setLastCallTime(Date lastCallTime) {
// this.lastCallTime = lastCallTime;
// }
//
// public Date getLastCallTime() {
// return lastCallTime;
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
