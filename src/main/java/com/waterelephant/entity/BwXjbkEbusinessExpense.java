/// ******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 本软件为武汉水象科技有限公司开发研制。
// * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
// package com.waterelephant.entity;
//
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
// import java.io.Serializable;
// import java.util.Date;
//
/// **
// * Module:(code:xjbk001)
// * <p>
// * BwXjbkEbusinessExpense.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Table(name = "bw_xjbk_ebusiness_expense")
// public class BwXjbkEbusinessExpense implements Serializable {
//
// /** */
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
// private Long id;
// private Long orderId;
// /**
// * // string 月份 ebusiness_expense
// */
// private String transMth;
// /**
// * // float 全部消费金额 ebusiness_expense
// */
// private Double allAmount;
// /**
// * // int 全部消费次数 ebusiness_expense
// */
// private Integer allCount;
// /**
// * // list.string 本月商品品类 ebusiness_expense
// */
// private String allCategory;
// private Date createTime;
// private Date updateTime;
//
// public Long getId() {
// return id;
// }
//
// public void setId(Long id) {
// this.id = id;
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
// public String getTransMth() {
// return transMth;
// }
//
// public void setTransMth(String transMth) {
// this.transMth = transMth;
// }
//
// public Double getAllAmount() {
// return allAmount;
// }
//
// public void setAllAmount(Double allAmount) {
// this.allAmount = allAmount;
// }
//
// public Integer getAllCount() {
// return allCount;
// }
//
// public void setAllCount(Integer allCount) {
// this.allCount = allCount;
// }
//
// public String getAllCategory() {
// return allCategory;
// }
//
// public void setAllCategory(String allCategory) {
// this.allCategory = allCategory;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
//
// public void setCreateTime(Date createTime) {
// this.createTime = createTime;
// }
//
// public Date getUpdateTime() {
// return updateTime;
// }
//
// public void setUpdateTime(Date updateTime) {
// this.updateTime = updateTime;
// }
//
// }
