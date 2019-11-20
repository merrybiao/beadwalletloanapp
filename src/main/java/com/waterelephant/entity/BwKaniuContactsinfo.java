// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kaniu_contactsinfo")
// public class BwKaniuContactsinfo implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;//
// private Double allcontactsnumber;// 通讯录号码总数
// private Double anomalynotecontactnumber;// 异常备注号码数
// private String usercalllogsource;// 通话记录来源，1：运营商爬取的通话详单，2：本地通话记录
// private Double repeatcontactsnumber;// 通讯录号码&通话记录号码重合个数
// private Double interflowcontactsnumber;// 通讯录互通号码数
// private Double onlyacceptcontactsnumber;// 通讯录单向（被叫）通话号码数
// private Double onlysendcontactsnumber;// 通讯录单向（主叫）通话号码数
// private Double intimacycontactsnumbers;// 通讯录中符合亲密通话特征的号码数
// private Double maxcalltimescontactsnumber;// 通讯录号码中单个号码最高通话次数
// private Double maxcalldurationcontactsnumber;// 通讯录号码中单个号码最高通话时长
// private Double allcalltimescontactsnumber;// 通讯录号码中所有号码总通话次数
// private Double allcalldurationcontactsnumber;// 通讯录号码中所有号码总通话时长
// private Date createTime;//
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
// public Double getAllcontactsnumber() {
// return allcontactsnumber;
// }
//
// public void setAllcontactsnumber(Double allcontactsnumber) {
// this.allcontactsnumber = allcontactsnumber;
// }
//
// public Double getAnomalynotecontactnumber() {
// return anomalynotecontactnumber;
// }
//
// public void setAnomalynotecontactnumber(Double anomalynotecontactnumber) {
// this.anomalynotecontactnumber = anomalynotecontactnumber;
// }
//
// public Double getRepeatcontactsnumber() {
// return repeatcontactsnumber;
// }
//
// public void setRepeatcontactsnumber(Double repeatcontactsnumber) {
// this.repeatcontactsnumber = repeatcontactsnumber;
// }
//
// public Double getInterflowcontactsnumber() {
// return interflowcontactsnumber;
// }
//
// public void setInterflowcontactsnumber(Double interflowcontactsnumber) {
// this.interflowcontactsnumber = interflowcontactsnumber;
// }
//
// public Double getOnlyacceptcontactsnumber() {
// return onlyacceptcontactsnumber;
// }
//
// public void setOnlyacceptcontactsnumber(Double onlyacceptcontactsnumber) {
// this.onlyacceptcontactsnumber = onlyacceptcontactsnumber;
// }
//
// public Double getOnlysendcontactsnumber() {
// return onlysendcontactsnumber;
// }
//
// public void setOnlysendcontactsnumber(Double onlysendcontactsnumber) {
// this.onlysendcontactsnumber = onlysendcontactsnumber;
// }
//
// public Double getIntimacycontactsnumbers() {
// return intimacycontactsnumbers;
// }
//
// public void setIntimacycontactsnumbers(Double intimacycontactsnumbers) {
// this.intimacycontactsnumbers = intimacycontactsnumbers;
// }
//
// public Double getMaxcalltimescontactsnumber() {
// return maxcalltimescontactsnumber;
// }
//
// public void setMaxcalltimescontactsnumber(Double maxcalltimescontactsnumber) {
// this.maxcalltimescontactsnumber = maxcalltimescontactsnumber;
// }
//
// public Double getMaxcalldurationcontactsnumber() {
// return maxcalldurationcontactsnumber;
// }
//
// public void setMaxcalldurationcontactsnumber(Double maxcalldurationcontactsnumber) {
// this.maxcalldurationcontactsnumber = maxcalldurationcontactsnumber;
// }
//
// public Double getAllcalltimescontactsnumber() {
// return allcalltimescontactsnumber;
// }
//
// public void setAllcalltimescontactsnumber(Double allcalltimescontactsnumber) {
// this.allcalltimescontactsnumber = allcalltimescontactsnumber;
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
// public String getUsercalllogsource() {
// return usercalllogsource;
// }
//
// public void setUsercalllogsource(String usercalllogsource) {
// this.usercalllogsource = usercalllogsource;
// }
//
// public Double getAllcalldurationcontactsnumber() {
// return allcalldurationcontactsnumber;
// }
//
// public void setAllcalldurationcontactsnumber(Double allcalldurationcontactsnumber) {
// this.allcalldurationcontactsnumber = allcalldurationcontactsnumber;
// }
//
// }
