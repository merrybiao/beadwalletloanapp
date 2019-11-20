// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_xl_check_black_info")
// public class BwXlCheckBlackInfo implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Integer contactsClass1Cnt;// 直接联系人人数
// private Integer contactsClass1BlacklistCnt;// 直接联系人中黑名单人数
// private Integer contactsClass2BlacklistCnt;// 间接联系人中黑名单人数
// private Integer contactsRouterCnt;// 引起间接黑名单人数
// private Float contactsRouterRatio;// 直接联系人中引起间接黑名单占比
// private Float phoneGrayScore;// 用户号码联系黑中介分数
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
// public void setContactsClass1Cnt(Integer contactsClass1Cnt) {
// this.contactsClass1Cnt = contactsClass1Cnt;
// }
//
// public Integer getContactsClass1Cnt() {
// return contactsClass1Cnt;
// }
//
// public void setContactsClass1BlacklistCnt(Integer contactsClass1BlacklistCnt) {
// this.contactsClass1BlacklistCnt = contactsClass1BlacklistCnt;
// }
//
// public Integer getContactsClass1BlacklistCnt() {
// return contactsClass1BlacklistCnt;
// }
//
// public void setContactsClass2BlacklistCnt(Integer contactsClass2BlacklistCnt) {
// this.contactsClass2BlacklistCnt = contactsClass2BlacklistCnt;
// }
//
// public Integer getContactsClass2BlacklistCnt() {
// return contactsClass2BlacklistCnt;
// }
//
// public void setContactsRouterCnt(Integer contactsRouterCnt) {
// this.contactsRouterCnt = contactsRouterCnt;
// }
//
// public Integer getContactsRouterCnt() {
// return contactsRouterCnt;
// }
//
// public void setContactsRouterRatio(Float contactsRouterRatio) {
// this.contactsRouterRatio = contactsRouterRatio;
// }
//
// public Float getContactsRouterRatio() {
// return contactsRouterRatio;
// }
//
// public void setPhoneGrayScore(Float phoneGrayScore) {
// this.phoneGrayScore = phoneGrayScore;
// }
//
// public Float getPhoneGrayScore() {
// return phoneGrayScore;
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
