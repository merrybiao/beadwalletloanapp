// package com.waterelephant.entity;
//
// import javax.persistence.*;
// import java.io.Serializable;
//
/// **
// * (code:jb001)
// */
// @Table(name = "bw_jb_bills")
// public class BwJbBills implements Serializable {
//
// /** */
// private static final long serialVersionUID = 1L;
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;
// /**
// * 账期起始日期
// */
// private String billStartDate;
// /**
// * 备注
// */
// private String notes;
// /**
// * 本手机关联号码，多个手机号已逗号分割
// */
// private String relatedMobiles;
// /**
// * 创建时间
// */
// private java.util.Date createTime;
// /**
// * 优惠费（分）
// */
// private Integer discount;
// /**
// * 本期已付费用（分）
// */
// private Integer paidFee;
// /**
// * 网络流量费（分）
// */
// private Integer webFee;
// /**
// * 短彩信费（分）
// */
// private Integer smsFee;
// /**
// * 本期可用积分
// */
// private Integer point;
// /**
// * 本机号码套餐及固定费（分）
// */
// private Integer baseFee;
// /**
// * 更新时间
// */
// private java.util.Date updateTime;
// /**
// * 其他优惠（分）
// */
// private Integer extraDiscount;
// /**
// * 账单月
// */
// private String billMonth;
// /**
// * 增值业务费（分）
// */
// private Integer extraServiceFee;
// /**
// * 本月总费用（分）
// */
// private Integer totalFee;
// /**
// * 上期可用积分
// */
// private Integer lastPoint;
// /**
// * 其他费用（分）
// */
// private Integer extraFee;
// /**
// * 订单id
// */
// private Long orderId;
// /**
// * 个人实际费用（分）
// */
// @Column(name = "actualFee")
// private Integer actualFee;
// /**
// * 账期结束日期
// */
// private String billEndDate;
// /**
// * 语音费（分）
// */
// private Integer voiceFee;
// /**
// * 本期未付费用（分）
// */
// private Integer unpaidFee;
//
// public Long getId() {
// return this.id;
// }
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public String getBillStartDate() {
// return this.billStartDate;
// }
//
// public void setBillStartDate(String billStartDate) {
// this.billStartDate = billStartDate;
// }
//
// public String getNotes() {
// return this.notes;
// }
//
// public void setNotes(String notes) {
// this.notes = notes;
// }
//
// public String getRelatedMobiles() {
// return this.relatedMobiles;
// }
//
// public void setRelatedMobiles(String relatedMobiles) {
// this.relatedMobiles = relatedMobiles;
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
// public Integer getDiscount() {
// return this.discount;
// }
//
// public void setDiscount(Integer discount) {
// this.discount = discount;
// }
//
// public Integer getPaidFee() {
// return this.paidFee;
// }
//
// public void setPaidFee(Integer paidFee) {
// this.paidFee = paidFee;
// }
//
// public Integer getWebFee() {
// return this.webFee;
// }
//
// public void setWebFee(Integer webFee) {
// this.webFee = webFee;
// }
//
// public Integer getSmsFee() {
// return this.smsFee;
// }
//
// public void setSmsFee(Integer smsFee) {
// this.smsFee = smsFee;
// }
//
// public Integer getPoint() {
// return this.point;
// }
//
// public void setPoint(Integer point) {
// this.point = point;
// }
//
// public Integer getBaseFee() {
// return this.baseFee;
// }
//
// public void setBaseFee(Integer baseFee) {
// this.baseFee = baseFee;
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
// public Integer getExtraDiscount() {
// return this.extraDiscount;
// }
//
// public void setExtraDiscount(Integer extraDiscount) {
// this.extraDiscount = extraDiscount;
// }
//
// public String getBillMonth() {
// return this.billMonth;
// }
//
// public void setBillMonth(String billMonth) {
// this.billMonth = billMonth;
// }
//
// public Integer getExtraServiceFee() {
// return this.extraServiceFee;
// }
//
// public void setExtraServiceFee(Integer extraServiceFee) {
// this.extraServiceFee = extraServiceFee;
// }
//
// public Integer getTotalFee() {
// return this.totalFee;
// }
//
// public void setTotalFee(Integer totalFee) {
// this.totalFee = totalFee;
// }
//
// public Integer getLastPoint() {
// return this.lastPoint;
// }
//
// public void setLastPoint(Integer lastPoint) {
// this.lastPoint = lastPoint;
// }
//
// public Integer getExtraFee() {
// return this.extraFee;
// }
//
// public void setExtraFee(Integer extraFee) {
// this.extraFee = extraFee;
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
// public Integer getActualFee() {
// return this.actualFee;
// }
//
// public void setActualFee(Integer actualFee) {
// this.actualFee = actualFee;
// }
//
// public String getBillEndDate() {
// return this.billEndDate;
// }
//
// public void setBillEndDate(String billEndDate) {
// this.billEndDate = billEndDate;
// }
//
// public Integer getVoiceFee() {
// return this.voiceFee;
// }
//
// public void setVoiceFee(Integer voiceFee) {
// this.voiceFee = voiceFee;
// }
//
// public Integer getUnpaidFee() {
// return this.unpaidFee;
// }
//
// public void setUnpaidFee(Integer unpaidFee) {
// this.unpaidFee = unpaidFee;
// }
//
// }
