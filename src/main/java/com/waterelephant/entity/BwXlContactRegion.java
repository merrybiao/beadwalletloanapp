// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_xl_contact_region")
// public class BwXlContactRegion implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;// 订单id
// private String regionLoc;// 地区名称 联系人的号码归属地
// private Integer regionUniqNumCnt;// 号码数量 去重后的联系人号码数量
// private Integer regionCallInCnt;// 电话呼入次数
// private Integer regionCallOutCnt;// 电话呼出次数
// private Double regionCallInTime;// 电话呼入总时间（分）
// private Double regionCallOutTime;// 电话呼出总时间（分）
// private Double regionAvgCallInTime;// 平均电话呼入时间（分）
// private Double regionAvgCallOutTime;// 平均电话呼出时间（分）
// private Double regionCallInCntPct;// 电话呼入次数百分比
// private Double regionCallOutCntPct;// 电话呼出次数百分比
// private Double regionCallInTimePct;// 电话呼入时间百分比
// private Double regionCallOutTimePct;// 电话呼出时间百分比
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
// public void setRegionLoc(String regionLoc) {
// this.regionLoc = regionLoc;
// }
//
// public String getRegionLoc() {
// return regionLoc;
// }
//
// public void setRegionUniqNumCnt(Integer regionUniqNumCnt) {
// this.regionUniqNumCnt = regionUniqNumCnt;
// }
//
// public Integer getRegionUniqNumCnt() {
// return regionUniqNumCnt;
// }
//
// public void setRegionCallInCnt(Integer regionCallInCnt) {
// this.regionCallInCnt = regionCallInCnt;
// }
//
// public Integer getRegionCallInCnt() {
// return regionCallInCnt;
// }
//
// public void setRegionCallOutCnt(Integer regionCallOutCnt) {
// this.regionCallOutCnt = regionCallOutCnt;
// }
//
// public Integer getRegionCallOutCnt() {
// return regionCallOutCnt;
// }
//
// public void setRegionCallInTime(Double regionCallInTime) {
// this.regionCallInTime = regionCallInTime;
// }
//
// public Double getRegionCallInTime() {
// return regionCallInTime;
// }
//
// public void setRegionCallOutTime(Double regionCallOutTime) {
// this.regionCallOutTime = regionCallOutTime;
// }
//
// public Double getRegionCallOutTime() {
// return regionCallOutTime;
// }
//
// public void setRegionAvgCallInTime(Double regionAvgCallInTime) {
// this.regionAvgCallInTime = regionAvgCallInTime;
// }
//
// public Double getRegionAvgCallInTime() {
// return regionAvgCallInTime;
// }
//
// public void setRegionAvgCallOutTime(Double regionAvgCallOutTime) {
// this.regionAvgCallOutTime = regionAvgCallOutTime;
// }
//
// public Double getRegionAvgCallOutTime() {
// return regionAvgCallOutTime;
// }
//
// public void setRegionCallInCntPct(Double regionCallInCntPct) {
// this.regionCallInCntPct = regionCallInCntPct;
// }
//
// public Double getRegionCallInCntPct() {
// return regionCallInCntPct;
// }
//
// public void setRegionCallOutCntPct(Double regionCallOutCntPct) {
// this.regionCallOutCntPct = regionCallOutCntPct;
// }
//
// public Double getRegionCallOutCntPct() {
// return regionCallOutCntPct;
// }
//
// public void setRegionCallInTimePct(Double regionCallInTimePct) {
// this.regionCallInTimePct = regionCallInTimePct;
// }
//
// public Double getRegionCallInTimePct() {
// return regionCallInTimePct;
// }
//
// public void setRegionCallOutTimePct(Double regionCallOutTimePct) {
// this.regionCallOutTimePct = regionCallOutTimePct;
// }
//
// public Double getRegionCallOutTimePct() {
// return regionCallOutTimePct;
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
