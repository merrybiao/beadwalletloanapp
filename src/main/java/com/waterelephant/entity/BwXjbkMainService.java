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
// * BwXjbkMainService.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Table(name = "bw_xjbk_main_service")
// public class BwXjbkMainService implements Serializable {
//
// /** */
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
// private Long id;
// private Long orderId;
// /**
// * // string 企业名称 main_service
// */
// private String companyName;
// /**
// * // string 服务企业类型 main_service
// */
// private String companyType;
// /**
// * // int 总互动次数 main_service
// */
// private Integer totalServiceCnt;
// /**
// * // int 月互动次数 service_details
// */
// private Integer detailsInteractCnt;
// /**
// * // string 互动月份 service_details
// */
// private String detailsInteractMth;
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
// public String getCompanyName() {
// return companyName;
// }
//
// public void setCompanyName(String companyName) {
// this.companyName = companyName;
// }
//
// public String getCompanyType() {
// return companyType;
// }
//
// public void setCompanyType(String companyType) {
// this.companyType = companyType;
// }
//
// public Integer getTotalServiceCnt() {
// return totalServiceCnt;
// }
//
// public void setTotalServiceCnt(Integer totalServiceCnt) {
// this.totalServiceCnt = totalServiceCnt;
// }
//
// public Integer getDetailsInteractCnt() {
// return detailsInteractCnt;
// }
//
// public void setDetailsInteractCnt(Integer detailsInteractCnt) {
// this.detailsInteractCnt = detailsInteractCnt;
// }
//
// public String getDetailsInteractMth() {
// return detailsInteractMth;
// }
//
// public void setDetailsInteractMth(String detailsInteractMth) {
// this.detailsInteractMth = detailsInteractMth;
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
