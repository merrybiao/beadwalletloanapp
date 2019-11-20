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
// @Table(name = "bw_jb_nets")
// public class BwJbNets implements Serializable {
//
// /** */
// private static final long serialVersionUID = 1L;
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;
// /**
// * //流量使用时长
// */
// private Integer duration;
// /**
// * //详情标识
// */
// private String detailsId;
// /**
// * //更新时间
// */
// private java.util.Date updateTime;
// /**
// * //创建时间
// */
// private java.util.Date createTime;
// /**
// * //流量使用量（kb）
// */
// private Integer subflow;
// /**
// * //网络类型
// */
// private String netType;
// /**
// * //业务名称
// */
// private String serviceName;
// /**
// * //通信费（分）
// */
// private Integer fee;
// /**
// * //流量使用地点
// */
// private String location;
// /**
// * //流量使用时间
// */
// private String time;
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
// public Integer getDuration() {
// return this.duration;
// }
//
// public void setDuration(Integer duration) {
// this.duration = duration;
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
// public java.util.Date getUpdateTime() {
// return this.updateTime;
// }
//
// public void setUpdateTime(java.util.Date updateTime) {
// this.updateTime = updateTime;
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
// public Integer getSubflow() {
// return this.subflow;
// }
//
// public void setSubflow(Integer subflow) {
// this.subflow = subflow;
// }
//
// public String getNetType() {
// return this.netType;
// }
//
// public void setNetType(String netType) {
// this.netType = netType;
// }
//
// public String getServiceName() {
// return this.serviceName;
// }
//
// public void setServiceName(String serviceName) {
// this.serviceName = serviceName;
// }
//
// public Integer getFee() {
// return this.fee;
// }
//
// public void setFee(Integer fee) {
// this.fee = fee;
// }
//
// public String getLocation() {
// return this.location;
// }
//
// public void setLocation(String location) {
// this.location = location;
// }
//
// public String getTime() {
// return this.time;
// }
//
// public void setTime(String time) {
// this.time = time;
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
