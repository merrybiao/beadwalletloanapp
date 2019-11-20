// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_xl_deliver_address")
// public class BwXlDeliverAddress implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;// 订单id
// private String address;// 收货地址
// private Double lng;// 经度
// private Double lat;// 纬度
// private String predictAddrType;// 地址类型
// private String beginDate;// 开始送货时间
// private String endDate;// 结束送货时间
// private Double totalAmount;// 总送货金额
// private Integer totalCount;// 总送货次数
// private String receiver;// 收货人列表
// private Integer count;// 收获次数
// private Double amount;// 收获金额
// private String name;// 收货人名称
// private String phoneNumList;// 收货人号码
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
// public void setAddress(String address) {
// this.address = address;
// }
//
// public String getAddress() {
// return address;
// }
//
// public void setLng(Double lng) {
// this.lng = lng;
// }
//
// public Double getLng() {
// return lng;
// }
//
// public void setLat(Double lat) {
// this.lat = lat;
// }
//
// public Double getLat() {
// return lat;
// }
//
// public void setPredictAddrType(String predictAddrType) {
// this.predictAddrType = predictAddrType;
// }
//
// public String getPredictAddrType() {
// return predictAddrType;
// }
//
// public void setBeginDate(String beginDate) {
// this.beginDate = beginDate;
// }
//
// public String getBeginDate() {
// return beginDate;
// }
//
// public void setEndDate(String endDate) {
// this.endDate = endDate;
// }
//
// public String getEndDate() {
// return endDate;
// }
//
// public void setTotalAmount(Double totalAmount) {
// this.totalAmount = totalAmount;
// }
//
// public Double getTotalAmount() {
// return totalAmount;
// }
//
// public void setTotalCount(Integer totalCount) {
// this.totalCount = totalCount;
// }
//
// public Integer getTotalCount() {
// return totalCount;
// }
//
// public void setReceiver(String receiver) {
// this.receiver = receiver;
// }
//
// public String getReceiver() {
// return receiver;
// }
//
// public void setCount(Integer count) {
// this.count = count;
// }
//
// public Integer getCount() {
// return count;
// }
//
// public void setAmount(Double amount) {
// this.amount = amount;
// }
//
// public Double getAmount() {
// return amount;
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
// public void setPhoneNumList(String phoneNumList) {
// this.phoneNumList = phoneNumList;
// }
//
// public String getPhoneNumList() {
// return phoneNumList;
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
