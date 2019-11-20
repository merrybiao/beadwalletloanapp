// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_xl_application_check")
// public class BwXlApplicationCheck implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;// 订单id
// private String appPoint;// 申请表数据点
// private String keyValue;// 申请表数据值
// private String gender;// 性别
// private String age;// 年龄
// private String province;// 省份
// private String city;// 城市
// private String region;// 区县
// private String regTime;// 注册时间
// private String checkName;// 姓名检查
// private String checkIdcard;// 身份证号检查
// private String checkEbusiness;// 电商使用号码检查
// private String checkAddr;// 地址检查
// private String relationship;// 联系人关系
// private String contactName;// 联系人姓名
// private String checkXiaohao;// 临时小号检查
// private String checkMobile;// 运营商联系号码检查
// private Integer courtArised;// 法院黑名单 0:没有，1:有
// private String courtBlackType;// 法院黑名单机构类型
// private Integer financialArised;// 金融服务类机构黑名单 0:没有，1:有
// private String financialBlackType;// 金融服务类机构黑名单机构类型
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
// public void setAppPoint(String appPoint) {
// this.appPoint = appPoint;
// }
//
// public String getAppPoint() {
// return appPoint;
// }
//
// public void setKeyValue(String keyValue) {
// this.keyValue = keyValue;
// }
//
// public String getKeyValue() {
// return keyValue;
// }
//
// public void setGender(String gender) {
// this.gender = gender;
// }
//
// public String getGender() {
// return gender;
// }
//
// public void setAge(String age) {
// this.age = age;
// }
//
// public String getAge() {
// return age;
// }
//
// public void setProvince(String province) {
// this.province = province;
// }
//
// public String getProvince() {
// return province;
// }
//
// public void setCity(String city) {
// this.city = city;
// }
//
// public String getCity() {
// return city;
// }
//
// public void setRegion(String region) {
// this.region = region;
// }
//
// public String getRegion() {
// return region;
// }
//
// public void setRegTime(String regTime) {
// this.regTime = regTime;
// }
//
// public String getRegTime() {
// return regTime;
// }
//
// public void setCheckName(String checkName) {
// this.checkName = checkName;
// }
//
// public String getCheckName() {
// return checkName;
// }
//
// public void setCheckIdcard(String checkIdcard) {
// this.checkIdcard = checkIdcard;
// }
//
// public String getCheckIdcard() {
// return checkIdcard;
// }
//
// public void setCheckEbusiness(String checkEbusiness) {
// this.checkEbusiness = checkEbusiness;
// }
//
// public String getCheckEbusiness() {
// return checkEbusiness;
// }
//
// public void setCheckAddr(String checkAddr) {
// this.checkAddr = checkAddr;
// }
//
// public String getCheckAddr() {
// return checkAddr;
// }
//
// public void setRelationship(String relationship) {
// this.relationship = relationship;
// }
//
// public String getRelationship() {
// return relationship;
// }
//
// public void setContactName(String contactName) {
// this.contactName = contactName;
// }
//
// public String getContactName() {
// return contactName;
// }
//
// public void setCheckXiaohao(String checkXiaohao) {
// this.checkXiaohao = checkXiaohao;
// }
//
// public String getCheckXiaohao() {
// return checkXiaohao;
// }
//
// public void setCheckMobile(String checkMobile) {
// this.checkMobile = checkMobile;
// }
//
// public String getCheckMobile() {
// return checkMobile;
// }
//
// public void setCourtArised(Integer courtArised) {
// this.courtArised = courtArised;
// }
//
// public Integer getCourtArised() {
// return courtArised;
// }
//
// public void setCourtBlackType(String courtBlackType) {
// this.courtBlackType = courtBlackType;
// }
//
// public String getCourtBlackType() {
// return courtBlackType;
// }
//
// public void setFinancialArised(Integer financialArised) {
// this.financialArised = financialArised;
// }
//
// public Integer getFinancialArised() {
// return financialArised;
// }
//
// public void setFinancialBlackType(String financialBlackType) {
// this.financialBlackType = financialBlackType;
// }
//
// public String getFinancialBlackType() {
// return financialBlackType;
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
