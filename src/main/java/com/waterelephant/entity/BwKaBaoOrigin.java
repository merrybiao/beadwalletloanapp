// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kabao_origin")
// public class BwKaBaoOrigin implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private String phone;// 手机号码
// private String type;// 运营商类别
// private Long originBillInfoId;//
// private Long originBaseInfoId;//
// private Long originCallInfoId;//
// private Long orderId;//
// private Date createTime;//
// private Date updateTime;// 更新时间，示例：yyyy-MM-dd HH:mm:ss.SSS
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
// public void setPhone(String phone) {
// this.phone = phone;
// }
//
// public String getPhone() {
// return phone;
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
// public void setOriginBillInfoId(Long originBillInfoId) {
// this.originBillInfoId = originBillInfoId;
// }
//
// public Long getOriginBillInfoId() {
// return originBillInfoId;
// }
//
// public void setOriginBaseInfoId(Long originBaseInfoId) {
// this.originBaseInfoId = originBaseInfoId;
// }
//
// public Long getOriginBaseInfoId() {
// return originBaseInfoId;
// }
//
// public Long getOriginCallInfoId() {
// return originCallInfoId;
// }
//
// public void setOriginCallInfoId(Long originCallInfoId) {
// this.originCallInfoId = originCallInfoId;
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
