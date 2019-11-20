// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kaniu_userphonewithmark")
// public class BwKaniuUserphonewithmark implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;//
// private Long pid;//
// private String phonevalue;// 联系人号码
// private String remark;// 联系人备注
// private String username;// 根据联系人备注，按照关键词匹配直系亲属/旁系亲戚/配偶 3个标签
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
// public void setPid(Long pid) {
// this.pid = pid;
// }
//
// public Long getPid() {
// return pid;
// }
//
// public void setPhonevalue(String phonevalue) {
// this.phonevalue = phonevalue;
// }
//
// public String getPhonevalue() {
// return phonevalue;
// }
//
// public void setRemark(String remark) {
// this.remark = remark;
// }
//
// public String getRemark() {
// return remark;
// }
//
// public void setUsername(String username) {
// this.username = username;
// }
//
// public String getUsername() {
// return username;
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
