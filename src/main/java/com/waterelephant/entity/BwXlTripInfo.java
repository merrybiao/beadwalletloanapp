// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_xl_trip_info")
// public class BwXlTripInfo implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;// 订单id
// private String tripLeave;// 出发地
// private String tripDest;// 目的地
// private String tripType;// 出行时间类型
// private String tripStartTime;// 出行开始时间
// private String tripEndTime;// 出行结束时间
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
// public void setTripLeave(String tripLeave) {
// this.tripLeave = tripLeave;
// }
//
// public String getTripLeave() {
// return tripLeave;
// }
//
// public void setTripDest(String tripDest) {
// this.tripDest = tripDest;
// }
//
// public String getTripDest() {
// return tripDest;
// }
//
// public void setTripType(String tripType) {
// this.tripType = tripType;
// }
//
// public String getTripType() {
// return tripType;
// }
//
// public void setTripStartTime(String tripStartTime) {
// this.tripStartTime = tripStartTime;
// }
//
// public String getTripStartTime() {
// return tripStartTime;
// }
//
// public void setTripEndTime(String tripEndTime) {
// this.tripEndTime = tripEndTime;
// }
//
// public String getTripEndTime() {
// return tripEndTime;
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
