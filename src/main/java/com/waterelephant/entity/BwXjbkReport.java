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
// * BwXjbkReport.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//
// @Table(name = "bw_xjbk_report")
// public class BwXjbkReport implements Serializable {
//
// /** */
// private static final long serialVersionUID = 1L;
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;
// private Long orderId;
// /**
// * 报告更新时间
// */
// private String reportTime;
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
// public String getReportTime() {
// return reportTime;
// }
//
// public void setReportTime(String reportTime) {
// this.reportTime = reportTime;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
//
// public void setCreateTime(Date date) {
// this.createTime = date;
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
