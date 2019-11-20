// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_xl_main_service")
// public class BwXlMainService implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;// 订单id
// private String companyName;// 企业名称
// private String companyType;// 服务企业类型
// private Integer totalServiceCnt;// 总互动次数
// private Integer detailsInteractCnt;// 月互动次数
// private String detailsInteractMth;// 互动月份
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
// public void setCompanyName(String companyName) {
// this.companyName = companyName;
// }
//
// public String getCompanyName() {
// return companyName;
// }
//
// public void setCompanyType(String companyType) {
// this.companyType = companyType;
// }
//
// public String getCompanyType() {
// return companyType;
// }
//
// public void setTotalServiceCnt(Integer totalServiceCnt) {
// this.totalServiceCnt = totalServiceCnt;
// }
//
// public Integer getTotalServiceCnt() {
// return totalServiceCnt;
// }
//
// public void setDetailsInteractCnt(Integer detailsInteractCnt) {
// this.detailsInteractCnt = detailsInteractCnt;
// }
//
// public Integer getDetailsInteractCnt() {
// return detailsInteractCnt;
// }
//
// public void setDetailsInteractMth(String detailsInteractMth) {
// this.detailsInteractMth = detailsInteractMth;
// }
//
// public String getDetailsInteractMth() {
// return detailsInteractMth;
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
