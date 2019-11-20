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
// * BwXjbkApplicationCheck.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
// @Table(name = "bw_xjbk_application_check")
// public class BwXjbkApplicationCheck implements Serializable {
//
// /** */
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
// private Long id;
// private Long orderId;
// /**
// * string 申请表数据点 application_check
// */
// private String appPoint;
// /**
// * string 申请表数据值 check_points
// */
// private String keyValue;
// /**
// * string 性别 check_points
// */
// private String gender;
// /**
// * 年龄 check_points
// */
// private String age;
// /**
// * string 省份 check_points
// */
// private String province;
// /**
// * string 城市 check_points
// */
// private String city;
// /**
// * string 区县 check_points
// */
// private String region;
// /**
// * string 注册时间 check_points
// */
// private String regTime;
// /**
// * string 姓名检查 用户姓名与运营商提供的姓名[]匹配失败; 用户姓名与运营商提供的姓名[]匹配成功; 运营商未提供姓名 check_points
// */
// private String checkName;
// /**
// * string 身份证号检查 用户身份证号与运营商提供的身份证号码[]匹配成功;用户身份证号与运营商提供的身份证号码[]匹配失败 check_points
// */
// private String checkIdcard;
// /**
// * string 电商使用号码检查 该号码在电商里面使用过[]个月，共[]次 ;该号码未在电商中使用过 ; 无法判断该号码的电商使用情况(无电商数据) check_points
// * ;无法判断该号码的电商使用情况(无号码) check_points
// */
// private String checkEbusiness;
// /**
// * string 地址检查 居住地址可通过地图定位技术精确定位到，坐标(E，N) ;居住地址无法通过地图定位技术精确定位到 ; 无法定位居住地址(未提供居住地址) check_points
// */
// private String checkAddr;
// /**
// * string 联系人关系 check_points
// */
// private String relationship;
// /**
// * string 联系人姓名 check_points
// */
// private String contactName;
// /**
// * string 临时小号检查 该联系人号码为临时小号; 该联系人号码非临时小号 check_points
// */
// private String checkXiaohao;
// /**
// * string 运营商联系号码检查 有该联系人电话的通话记录，[*]天内[]次[]分钟，按时长计算排名第[]位 ;没有该联系人电话的通话记录 check_points ;
// * 无法判断该%s的通话情况(无运营商数据) ;无法判断该%s的通话情况(无家庭电话) application_check
// */
// private String checkMobile;
// /**
// * 法院黑名单检查 bool 是否出现
// */
// private Integer courtArised;
// /**
// * 法院黑名单检查 list 黑名单机构类型
// */
// private String courtBlackType;
// /**
// * 金融服务类机构黑名单检查 bool 是否出现
// */
// private Integer financialArised;
// /**
// * 金融服务类机构黑名单检查 list 黑名单机构类型
// */
// private String financialBlackType;
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
// public String getAppPoint() {
// return appPoint;
// }
//
// public void setAppPoint(String appPoint) {
// this.appPoint = appPoint;
// }
//
// public String getKeyValue() {
// return keyValue;
// }
//
// public void setKeyValue(String keyValue) {
// this.keyValue = keyValue;
// }
//
// public String getGender() {
// return gender;
// }
//
// public void setGender(String gender) {
// this.gender = gender;
// }
//
// public String getAge() {
// return age;
// }
//
// public void setAge(String age) {
// this.age = age;
// }
//
// public String getProvince() {
// return province;
// }
//
// public void setProvince(String province) {
// this.province = province;
// }
//
// public String getCity() {
// return city;
// }
//
// public void setCity(String city) {
// this.city = city;
// }
//
// public String getRegion() {
// return region;
// }
//
// public void setRegion(String region) {
// this.region = region;
// }
//
// public String getRegTime() {
// return regTime;
// }
//
// public void setRegTime(String regTime) {
// this.regTime = regTime;
// }
//
// public String getCheckName() {
// return checkName;
// }
//
// public void setCheckName(String checkName) {
// this.checkName = checkName;
// }
//
// public String getCheckIdcard() {
// return checkIdcard;
// }
//
// public void setCheckIdcard(String checkIdcard) {
// this.checkIdcard = checkIdcard;
// }
//
// public String getCheckEbusiness() {
// return checkEbusiness;
// }
//
// public void setCheckEbusiness(String checkEbusiness) {
// this.checkEbusiness = checkEbusiness;
// }
//
// public String getCheckAddr() {
// return checkAddr;
// }
//
// public void setCheckAddr(String checkAddr) {
// this.checkAddr = checkAddr;
// }
//
// public String getRelationship() {
// return relationship;
// }
//
// public void setRelationship(String relationship) {
// this.relationship = relationship;
// }
//
// public String getContactName() {
// return contactName;
// }
//
// public void setContactName(String contactName) {
// this.contactName = contactName;
// }
//
// public String getCheckXiaohao() {
// return checkXiaohao;
// }
//
// public void setCheckXiaohao(String checkXiaohao) {
// this.checkXiaohao = checkXiaohao;
// }
//
// public String getCheckMobile() {
// return checkMobile;
// }
//
// public void setCheckMobile(String checkMobile) {
// this.checkMobile = checkMobile;
// }
//
// public Integer getCourtArised() {
// return courtArised;
// }
//
// public void setCourtArised(Integer courtArised) {
// this.courtArised = courtArised;
// }
//
// public String getCourtBlackType() {
// return courtBlackType;
// }
//
// public void setCourtBlackType(String courtBlackType) {
// this.courtBlackType = courtBlackType;
// }
//
// public Integer getFinancialArised() {
// return financialArised;
// }
//
// public void setFinancialArised(Integer financialArised) {
// this.financialArised = financialArised;
// }
//
// public String getFinancialBlackType() {
// return financialBlackType;
// }
//
// public void setFinancialBlackType(String financialBlackType) {
// this.financialBlackType = financialBlackType;
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
