// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kaniu_hitrules")
// public class BwKaniuHitrules implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;//
// private String score;//
// private String decision;//
// private String name;//命中规则的中文名称
// private String hid;//命中规则的id
// private String uuid;//
// private String parentuuid;//
// private Date createTime;//
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
// public void setScore(String score) {
// this.score = score;
// }
//
// public String getScore() {
// return score;
// }
//
// public void setDecision(String decision) {
// this.decision = decision;
// }
//
// public String getDecision() {
// return decision;
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
// public void setHid(String hid) {
// this.hid = hid;
// }
//
// public String getHid() {
// return hid;
// }
//
// public void setUuid(String uuid) {
// this.uuid = uuid;
// }
//
// public String getUuid() {
// return uuid;
// }
//
// public void setParentuuid(String parentuuid) {
// this.parentuuid = parentuuid;
// }
//
// public String getParentuuid() {
// return parentuuid;
// }
//
// public void setCreateTime(Date createTime) {
// this.createTime = createTime;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
// }
