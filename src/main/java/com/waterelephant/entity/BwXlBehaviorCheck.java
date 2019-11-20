// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_xl_behavior_check")
// public class BwXlBehaviorCheck implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;// 订单id
// private String checkPoint;// 分析点 检查项目
// private String checkPointCn;// 分析点中文 检查项目
// private String result;// 检查结果
// private String evidence;// 证据
// private Integer score;// 标记 0:无数据, 1:通过, 2:不通过
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
// public void setCheckPoint(String checkPoint) {
// this.checkPoint = checkPoint;
// }
//
// public String getCheckPoint() {
// return checkPoint;
// }
//
// public void setCheckPointCn(String checkPointCn) {
// this.checkPointCn = checkPointCn;
// }
//
// public String getCheckPointCn() {
// return checkPointCn;
// }
//
// public void setResult(String result) {
// this.result = result;
// }
//
// public String getResult() {
// return result;
// }
//
// public void setEvidence(String evidence) {
// this.evidence = evidence;
// }
//
// public String getEvidence() {
// return evidence;
// }
//
// public void setScore(Integer score) {
// this.score = score;
// }
//
// public Integer getScore() {
// return score;
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
