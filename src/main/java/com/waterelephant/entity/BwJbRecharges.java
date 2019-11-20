// package com.waterelephant.entity;
//
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
// import java.io.Serializable;
//
/// **
// * (code:jb001)
// */
// @Table(name = "bw_jb_recharges")
// public class BwJbRecharges implements Serializable {
//
// /** */
// private static final long serialVersionUID = 1L;
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;
// /**
// * //详情标识
// */
// private String detailsId;
// /**
// * //充值金额(分)
// */
// private Integer amount;
// /**
// * //更新时间
// */
// private java.util.Date updateTime;
// /**
// * //充值时间
// */
// private String rechargeTime;
// /**
// * //创建时间
// */
// private java.util.Date createTime;
// /**
// * //充值方式
// */
// private String type;
// /**
// * //订单id
// */
// private Long orderId;
//
// public Long getId() {
// return this.id;
// }
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public String getDetailsId() {
// return this.detailsId;
// }
//
// public void setDetailsId(String detailsId) {
// this.detailsId = detailsId;
// }
//
// public Integer getAmount() {
// return this.amount;
// }
//
// public void setAmount(Integer amount) {
// this.amount = amount;
// }
//
// public java.util.Date getUpdateTime() {
// return this.updateTime;
// }
//
// public void setUpdateTime(java.util.Date updateTime) {
// this.updateTime = updateTime;
// }
//
// public String getRechargeTime() {
// return this.rechargeTime;
// }
//
// public void setRechargeTime(String rechargeTime) {
// this.rechargeTime = rechargeTime;
// }
//
// public java.util.Date getCreateTime() {
// return this.createTime;
// }
//
// public void setCreateTime(java.util.Date createTime) {
// this.createTime = createTime;
// }
//
// public String getType() {
// return this.type;
// }
//
// public void setType(String type) {
// this.type = type;
// }
//
// public Long getOrderId() {
// return this.orderId;
// }
//
// public void setOrderId(Long orderId) {
// this.orderId = orderId;
// }
//
// }
