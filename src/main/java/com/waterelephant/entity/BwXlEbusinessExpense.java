// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_xl_ebusiness_expense")
// public class BwXlEbusinessExpense implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;// 订单id
// private String transMth;// 月份
// private Double allAmount;// 全部消费金额
// private Integer allCount;// 全部消费次数
// private String allCategory;// 全部消费次数
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
// public void setOrderId(Long orderId) {
// this.orderId = orderId;
// }
//
// public Long getOrderId() {
// return orderId;
// }
//
// public void setTransMth(String transMth) {
// this.transMth = transMth;
// }
//
// public String getTransMth() {
// return transMth;
// }
//
// public void setAllAmount(Double allAmount) {
// this.allAmount = allAmount;
// }
//
// public Double getAllAmount() {
// return allAmount;
// }
//
// public void setAllCount(Integer allCount) {
// this.allCount = allCount;
// }
//
// public Integer getAllCount() {
// return allCount;
// }
//
// public void setAllCategory(String allCategory) {
// this.allCategory = allCategory;
// }
//
// public String getAllCategory() {
// return allCategory;
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
