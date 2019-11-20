// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kabao_origin_base_info")
// public class BwKaBaoOriginBaseInfo implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private String account;//
// private String starLevel;// 星级
// private String address;// 地址
// private String level;// 用户等级信息
// private String sex;// 性别
// private String name;// 姓名
// private String idCard;// 身份证号码
// private String innetDate;// 入网时间yyyyMMddHHmmss
// private String realnameInfo;// 实名认证信息：未登记,已登记,已审核
// private String brand;// 所属品牌
// private String email;// 邮箱
// private String zipCode;// 邮编
// private String status;// 状态，正常，冻结，欠费等
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
// public void setStarLevel(String starLevel) {
// this.starLevel = starLevel;
// }
//
// public String getStarLevel() {
// return starLevel;
// }
//
// public void setAddress(String address) {
// this.address = address;
// }
//
// public String getAddress() {
// return address;
// }
//
// public void setLevel(String level) {
// this.level = level;
// }
//
// public String getLevel() {
// return level;
// }
//
// public void setSex(String sex) {
// this.sex = sex;
// }
//
// public String getSex() {
// return sex;
// }
//
// public void setName(String name) {
// this.name = name;
// }
//
// public String getName() {
// return name;
// }
//
// public void setIdCard(String idCard) {
// this.idCard = idCard;
// }
//
// public String getIdCard() {
// return idCard;
// }
//
// public String getInnetDate() {
// return innetDate;
// }
//
// public void setInnetDate(String innetDate) {
// this.innetDate = innetDate;
// }
//
// public void setRealnameInfo(String realnameInfo) {
// this.realnameInfo = realnameInfo;
// }
//
// public String getRealnameInfo() {
// return realnameInfo;
// }
//
// public void setBrand(String brand) {
// this.brand = brand;
// }
//
// public String getBrand() {
// return brand;
// }
//
// public void setEmail(String email) {
// this.email = email;
// }
//
// public String getEmail() {
// return email;
// }
//
// public void setZipCode(String zipCode) {
// this.zipCode = zipCode;
// }
//
// public String getZipCode() {
// return zipCode;
// }
//
// public void setStatus(String status) {
// this.status = status;
// }
//
// public String getStatus() {
// return status;
// }
//
// public Long getOrderId() {
// return orderId;
// }
//
// public void setOrderId(Long orderId) {
// this.orderId = orderId;
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
