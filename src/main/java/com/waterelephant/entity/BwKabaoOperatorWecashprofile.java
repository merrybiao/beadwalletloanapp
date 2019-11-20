// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kabao_operator_wecashprofile")
// public class BwKabaoOperatorWecashprofile implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Integer contactsWecashOverdueCount;//
// private String phone;//
// private Integer contactsWecashHighriskUserCount;//
// private Integer contactsWecashUserCount;//
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
// public Integer getContactsWecashOverdueCount() {
// return contactsWecashOverdueCount;
// }
//
// public void setContactsWecashOverdueCount(Integer contactsWecashOverdueCount) {
// this.contactsWecashOverdueCount = contactsWecashOverdueCount;
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
// public Integer getContactsWecashHighriskUserCount() {
// return contactsWecashHighriskUserCount;
// }
//
// public void setContactsWecashHighriskUserCount(Integer contactsWecashHighriskUserCount) {
// this.contactsWecashHighriskUserCount = contactsWecashHighriskUserCount;
// }
//
// public Integer getContactsWecashUserCount() {
// return contactsWecashUserCount;
// }
//
// public void setContactsWecashUserCount(Integer contactsWecashUserCount) {
// this.contactsWecashUserCount = contactsWecashUserCount;
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
//
// @Override
// public String toString() {
// return "BwKabaoOperatorWecashprofile [id=" + id + ", contactsWecashOverdueCount=" + contactsWecashOverdueCount + ",
// phone=" + phone + ", contactsWecashHighriskUserCount="
// + contactsWecashHighriskUserCount + ", contactsWecashUserCount=" + contactsWecashUserCount + ", orderId=" + orderId +
// ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
// }
// }
