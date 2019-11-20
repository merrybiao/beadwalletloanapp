// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_xl_check_search_info")
// public class BwXlCheckSearchInfo implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private String phoneWithOtherIdcards;// 电话号码组合过其他身份证
// private String phoneWithOtherNames;// 电话号码组合过其他姓名
// private String arisedOpenWeb;// 电话号码出现过的公开网站
// private Long registerOrgCnt;// 电话号码注册过的相关企业数量
// private String idcardWithOtherPhones;// 身份证组合过其他电话
// private Integer searchedOrgCnt;// 查询过该用户的相关企业数量
// private String searchedOrgType;// 查询过该用户的相关企业类型
// private String registerOrgType;// 电话号码注册过的相关企业类型
// private String idcardWithOtherNames;// 身份证组合过的其他姓名
// private Long orderId;//
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
// public void setPhoneWithOtherIdcards(String phoneWithOtherIdcards) {
// this.phoneWithOtherIdcards = phoneWithOtherIdcards;
// }
//
// public String getPhoneWithOtherIdcards() {
// return phoneWithOtherIdcards;
// }
//
// public void setPhoneWithOtherNames(String phoneWithOtherNames) {
// this.phoneWithOtherNames = phoneWithOtherNames;
// }
//
// public String getPhoneWithOtherNames() {
// return phoneWithOtherNames;
// }
//
// public void setArisedOpenWeb(String arisedOpenWeb) {
// this.arisedOpenWeb = arisedOpenWeb;
// }
//
// public String getArisedOpenWeb() {
// return arisedOpenWeb;
// }
//
// public void setRegisterOrgCnt(Long registerOrgCnt) {
// this.registerOrgCnt = registerOrgCnt;
// }
//
// public Long getRegisterOrgCnt() {
// return registerOrgCnt;
// }
//
// public void setIdcardWithOtherPhones(String idcardWithOtherPhones) {
// this.idcardWithOtherPhones = idcardWithOtherPhones;
// }
//
// public String getIdcardWithOtherPhones() {
// return idcardWithOtherPhones;
// }
//
// public void setSearchedOrgCnt(Integer searchedOrgCnt) {
// this.searchedOrgCnt = searchedOrgCnt;
// }
//
// public Integer getSearchedOrgCnt() {
// return searchedOrgCnt;
// }
//
// public void setSearchedOrgType(String searchedOrgType) {
// this.searchedOrgType = searchedOrgType;
// }
//
// public String getSearchedOrgType() {
// return searchedOrgType;
// }
//
// public void setRegisterOrgType(String registerOrgType) {
// this.registerOrgType = registerOrgType;
// }
//
// public String getRegisterOrgType() {
// return registerOrgType;
// }
//
// public void setIdcardWithOtherNames(String idcardWithOtherNames) {
// this.idcardWithOtherNames = idcardWithOtherNames;
// }
//
// public String getIdcardWithOtherNames() {
// return idcardWithOtherNames;
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
