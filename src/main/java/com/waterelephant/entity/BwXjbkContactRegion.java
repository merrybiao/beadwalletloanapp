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
// * BwXjbkContactRegion.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Table(name = "bw_xjbk_contact_region")
// public class BwXjbkContactRegion implements Serializable {
//
// /** */
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
// private Long id;
// private Long orderId;
// /**
// * // string 地区名称 联系人的号码归属地 contact_region
// */
// private String regionLoc;
// /**
// * // int 号码数量 去重后的联系人号码数量 contact_region
// */
// private Integer regionUniqNumCnt;
// /**
// * // int 电话呼入次数 电话呼入次数 contact_region
// */
// private Integer regionCallInCnt;
// /**
// * // int 电话呼出次数 电话呼出次数 contact_region
// */
// private Integer regionCallOutCnt;
// /**
// * // float 电话呼入时间 电话呼入总时间（分） contact_region
// */
// private Double regionCallInTime;
// /**
// * // float 电话呼出时间 电话呼出总时间（分） contact_region
// */
// private Double regionCallOutTime;
// /**
// * // float 平均电话呼入时间 平均电话呼入时间（分） contact_region
// */
// private Double regionAvgCallInTime;
// /**
// * // float 平均电话呼出时间 平均电话呼出时间（分） contact_region
// */
// private Double regionAvgCallOutTime;
// /**
// * // float 电话呼入次数百分比 电话呼入次数百分比 contact_region
// */
// private Double regionCallInCntPct;
// /**
// * // float 电话呼出次数百分比 电话呼出次数百分比 contact_region
// */
// private Double regionCallOutCntPct;
// /**
// * // float 电话呼入时间百分比 电话呼入时间百分比 contact_region
// */
// private Double regionCallInTimePct;
// /**
// * // float 电话呼出时间百分比 电话呼出时间百分比 contact_region
// */
// private Double regionCallOutTimePct;
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
// public String getRegionLoc() {
// return regionLoc;
// }
//
// public void setRegionLoc(String regionLoc) {
// this.regionLoc = regionLoc;
// }
//
// public Integer getRegionUniqNumCnt() {
// return regionUniqNumCnt;
// }
//
// public void setRegionUniqNumCnt(Integer regionUniqNumCnt) {
// this.regionUniqNumCnt = regionUniqNumCnt;
// }
//
// public Integer getRegionCallInCnt() {
// return regionCallInCnt;
// }
//
// public void setRegionCallInCnt(Integer regionCallInCnt) {
// this.regionCallInCnt = regionCallInCnt;
// }
//
// public Integer getRegionCallOutCnt() {
// return regionCallOutCnt;
// }
//
// public void setRegionCallOutCnt(Integer regionCallOutCnt) {
// this.regionCallOutCnt = regionCallOutCnt;
// }
//
// public Double getRegionCallInTime() {
// return regionCallInTime;
// }
//
// public void setRegionCallInTime(Double regionCallInTime) {
// this.regionCallInTime = regionCallInTime;
// }
//
// public Double getRegionCallOutTime() {
// return regionCallOutTime;
// }
//
// public void setRegionCallOutTime(Double regionCallOutTime) {
// this.regionCallOutTime = regionCallOutTime;
// }
//
// public Double getRegionAvgCallInTime() {
// return regionAvgCallInTime;
// }
//
// public void setRegionAvgCallInTime(Double regionAvgCallInTime) {
// this.regionAvgCallInTime = regionAvgCallInTime;
// }
//
// public Double getRegionAvgCallOutTime() {
// return regionAvgCallOutTime;
// }
//
// public void setRegionAvgCallOutTime(Double regionAvgCallOutTime) {
// this.regionAvgCallOutTime = regionAvgCallOutTime;
// }
//
// public Double getRegionCallInCntPct() {
// return regionCallInCntPct;
// }
//
// public void setRegionCallInCntPct(Double regionCallInCntPct) {
// this.regionCallInCntPct = regionCallInCntPct;
// }
//
// public Double getRegionCallOutCntPct() {
// return regionCallOutCntPct;
// }
//
// public void setRegionCallOutCntPct(Double regionCallOutCntPct) {
// this.regionCallOutCntPct = regionCallOutCntPct;
// }
//
// public Double getRegionCallInTimePct() {
// return regionCallInTimePct;
// }
//
// public void setRegionCallInTimePct(Double regionCallInTimePct) {
// this.regionCallInTimePct = regionCallInTimePct;
// }
//
// public Double getRegionCallOutTimePct() {
// return regionCallOutTimePct;
// }
//
// public void setRegionCallOutTimePct(Double regionCallOutTimePct) {
// this.regionCallOutTimePct = regionCallOutTimePct;
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
// }
