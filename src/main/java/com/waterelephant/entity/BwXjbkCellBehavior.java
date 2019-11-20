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
// * BwXjbkCellBehavior.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Table(name = "bw_xjbk_cell_behavior")
// public class BwXjbkCellBehavior implements Serializable {
//
// /** */
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
// private Long id;
// private Long orderId;
// /**
// * // int 号码 手机号码 cell_behavior
// */
// private String phoneNum;
// /**
// * // int 短信数目 短信数量 behavior
// */
// private Integer smsCnt;
// /**
// * // string 号码 号码 behavior
// */
// private String cellPhoneNum;
// /**
// * // float 流量 流量（MB） behavior
// */
// private Double netFlow;
// /**
// * // float 话费消费 话费消费(元) behavior
// */
// private Double totalAmount;
// /**
// * // float 主叫时间 主叫时间（分） behavior
// */
// private Double callOutTime;
// /**
// * // string 月份 月份 behavior
// */
// private String cellMth;
// /**
// * // string 归属地 归属地 behavior
// */
// private String cellLoc;
// /**
// * // int 呼叫次数 呼叫次数 behavior
// */
// private Integer callCnt;
// /**
// * // string 运营商（中文） 运营商（中文） behavior
// */
// private String cellOperatorZh;
// /**
// * // int 主叫次数 主叫次数 behavior
// */
// private Integer callOutCnt;
// /**
// * // string 运营商 运营商 behavior
// */
// private String cellOperator;
// /**
// * // float 被叫时间 被叫时间（分） behavior
// */
// private Double callInTime;
// /**
// * // int 被叫次数 被叫次数 behavior
// */
// private Integer callInCnt;
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
// public String getPhoneNum() {
// return phoneNum;
// }
//
// public void setPhoneNum(String phoneNum) {
// this.phoneNum = phoneNum;
// }
//
// public Integer getSmsCnt() {
// return smsCnt;
// }
//
// public void setSmsCnt(Integer smsCnt) {
// this.smsCnt = smsCnt;
// }
//
// public String getCellPhoneNum() {
// return cellPhoneNum;
// }
//
// public void setCellPhoneNum(String cellPhoneNum) {
// this.cellPhoneNum = cellPhoneNum;
// }
//
// public Double getNetFlow() {
// return netFlow;
// }
//
// public void setNetFlow(Double netFlow) {
// this.netFlow = netFlow;
// }
//
// public Double getTotalAmount() {
// return totalAmount;
// }
//
// public void setTotalAmount(Double totalAmount) {
// this.totalAmount = totalAmount;
// }
//
// public Double getCallOutTime() {
// return callOutTime;
// }
//
// public void setCallOutTime(Double callOutTime) {
// this.callOutTime = callOutTime;
// }
//
// public String getCellMth() {
// return cellMth;
// }
//
// public void setCellMth(String cellMth) {
// this.cellMth = cellMth;
// }
//
// public String getCellLoc() {
// return cellLoc;
// }
//
// public void setCellLoc(String cellLoc) {
// this.cellLoc = cellLoc;
// }
//
// public Integer getCallCnt() {
// return callCnt;
// }
//
// public void setCallCnt(Integer callCnt) {
// this.callCnt = callCnt;
// }
//
// public String getCellOperatorZh() {
// return cellOperatorZh;
// }
//
// public void setCellOperatorZh(String cellOperatorZh) {
// this.cellOperatorZh = cellOperatorZh;
// }
//
// public Integer getCallOutCnt() {
// return callOutCnt;
// }
//
// public void setCallOutCnt(Integer callOutCnt) {
// this.callOutCnt = callOutCnt;
// }
//
// public String getCellOperator() {
// return cellOperator;
// }
//
// public void setCellOperator(String cellOperator) {
// this.cellOperator = cellOperator;
// }
//
// public Double getCallInTime() {
// return callInTime;
// }
//
// public void setCallInTime(Double callInTime) {
// this.callInTime = callInTime;
// }
//
// public Integer getCallInCnt() {
// return callInCnt;
// }
//
// public void setCallInCnt(Integer callInCnt) {
// this.callInCnt = callInCnt;
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
