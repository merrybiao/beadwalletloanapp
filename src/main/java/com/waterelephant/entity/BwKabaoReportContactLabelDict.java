// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kabao_report_contact_label_dict")
// public class BwKabaoReportContactLabelDict implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private String account;//
// private String phone;// 联系号码
// private Integer callInCount;// 被叫次数
// private Integer callInTime;// 被叫时长
// private Integer callOutCount;// 主叫次数
// private Integer callOutTime;// 主叫时长
// private Date lastCallTime;// 最近通话时间
// private String phoneSensitiveInfo;// 标注信息
// private Integer totalCount;// 通话次数
// private Long totalTime;// 通话时长
// private String type;// 风险信息类型
// private String haveCallInfo;//
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
// public void setPhone(String phone) {
// this.phone = phone;
// }
//
// public String getPhone() {
// return phone;
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
// public void setCallInTime(Integer callInTime) {
// this.callInTime = callInTime;
// }
//
// public Integer getCallInTime() {
// return callInTime;
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
// public void setCallOutTime(Integer callOutTime) {
// this.callOutTime = callOutTime;
// }
//
// public Integer getCallOutTime() {
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
// public void setPhoneSensitiveInfo(String phoneSensitiveInfo) {
// this.phoneSensitiveInfo = phoneSensitiveInfo;
// }
//
// public String getPhoneSensitiveInfo() {
// return phoneSensitiveInfo;
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
// public void setTotalTime(Long totalTime) {
// this.totalTime = totalTime;
// }
//
// public Long getTotalTime() {
// return totalTime;
// }
//
// public void setType(String type) {
// this.type = type;
// }
//
// public String getType() {
// return type;
// }
//
// public void setHaveCallInfo(String haveCallInfo) {
// this.haveCallInfo = haveCallInfo;
// }
//
// public String getHaveCallInfo() {
// return haveCallInfo;
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
