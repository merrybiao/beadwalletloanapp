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
// * BwXjbkBehaviorCheck.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Table(name = "bw_xjbk_behavior_check")
// public class BwXjbkBehaviorCheck implements Serializable {
//
// /** */
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
// private Long id;
// private Long orderId;
// /**
// * // string 分析点 检查项目 behavior_check
// */
// private String checkPoint;
// /**
// * // string 分析点中文 检查项目 behavior_check
// */
// private String checkPointCn;
// /**
// * // string 检查结果 检查结果 behavior_check
// */
// private String result;
// /**
// * // string 证据 证据 behavior_check
// */
// private String evidence;
// /**
// * // int 标记 0:无数据, 1:通过, 2:不通过 behavior_check
// */
// private Integer score;
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
// public String getCheckPoint() {
// return checkPoint;
// }
//
// public void setCheckPoint(String checkPoint) {
// this.checkPoint = checkPoint;
// }
//
// public String getCheckPointCn() {
// return checkPointCn;
// }
//
// public void setCheckPointCn(String checkPointCn) {
// this.checkPointCn = checkPointCn;
// }
//
// public String getResult() {
// return result;
// }
//
// public void setResult(String result) {
// this.result = result;
// }
//
// public String getEvidence() {
// return evidence;
// }
//
// public void setEvidence(String evidence) {
// this.evidence = evidence;
// }
//
// public Integer getScore() {
// return score;
// }
//
// public void setScore(Integer score) {
// this.score = score;
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
