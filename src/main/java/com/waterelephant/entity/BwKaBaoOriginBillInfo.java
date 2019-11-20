// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kabao_origin_bill_info")
// public class BwKaBaoOriginBillInfo implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private String account;//
// private String month;// 账单月份yyyyMM
// private Double fee;// 月消费0.00
// private String name;// 套餐名
// private Double value;// 套餐金额0.00
// private Long orderId;//
// private Date createTime;//
// private Date updateTime;//
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
// public void setFee(Double fee) {
// this.fee = fee;
// }
//
// public Double getFee() {
// return fee;
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
// public void setValue(Double value) {
// this.value = value;
// }
//
// public Double getValue() {
// return value;
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
