// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kaniu_relationshipstatistical")
// public class BwKaniuRelationshipstatistical implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;//
// private Long pid;// 父节点id
// private Double relationship;// 关系 "1 DIRECT_PERSON(""直系亲属"")2 COUPLE(""配偶"")3 SECOND_PERSON(""旁系亲属"")"
// private Double phonenum;// 号码个数
// private Double callnum7day;// 近7天通话次数汇总
// private Double calltime7day;// 近7天通话时长汇总
// private Double callnum30day;// 近30天通话次数汇总
// private Double calltime30day;// 近30天通话时长汇总
// private Double callnum90day;// 近90天通话次数汇总
// private Double calltime90day;// 近90天通话时长汇总
// private Double sumcallnum;// 号码总通话次数
// private Double sumcalltime;// 号码总通话时长
// private Double singlephonehighnum;// 单个号码最高通话次数
// private Double singlephonehightime;// 单个号码最高通话时长
// private Double phoneonecalltime;// 号均每次通话时长
// private Double phonesumcalltime;// 号均总通话时长
// private Double phonesumcallnum;// 号均总通话次数
// private Double phoneintervalday;// 号均首末次通话间隔天数均值
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
// public void setPid(Long pid) {
// this.pid = pid;
// }
//
// public Long getPid() {
// return pid;
// }
//
// public Double getRelationship() {
// return relationship;
// }
//
// public void setRelationship(Double relationship) {
// this.relationship = relationship;
// }
//
// public Double getPhonenum() {
// return phonenum;
// }
//
// public void setPhonenum(Double phonenum) {
// this.phonenum = phonenum;
// }
//
// public Double getCallnum7day() {
// return callnum7day;
// }
//
// public void setCallnum7day(Double callnum7day) {
// this.callnum7day = callnum7day;
// }
//
// public Double getCalltime7day() {
// return calltime7day;
// }
//
// public void setCalltime7day(Double calltime7day) {
// this.calltime7day = calltime7day;
// }
//
// public Double getCallnum30day() {
// return callnum30day;
// }
//
// public void setCallnum30day(Double callnum30day) {
// this.callnum30day = callnum30day;
// }
//
// public Double getCalltime30day() {
// return calltime30day;
// }
//
// public void setCalltime30day(Double calltime30day) {
// this.calltime30day = calltime30day;
// }
//
// public Double getCallnum90day() {
// return callnum90day;
// }
//
// public void setCallnum90day(Double callnum90day) {
// this.callnum90day = callnum90day;
// }
//
// public Double getCalltime90day() {
// return calltime90day;
// }
//
// public void setCalltime90day(Double calltime90day) {
// this.calltime90day = calltime90day;
// }
//
// public Double getSumcallnum() {
// return sumcallnum;
// }
//
// public void setSumcallnum(Double sumcallnum) {
// this.sumcallnum = sumcallnum;
// }
//
// public Double getSumcalltime() {
// return sumcalltime;
// }
//
// public void setSumcalltime(Double sumcalltime) {
// this.sumcalltime = sumcalltime;
// }
//
// public Double getSinglephonehighnum() {
// return singlephonehighnum;
// }
//
// public void setSinglephonehighnum(Double singlephonehighnum) {
// this.singlephonehighnum = singlephonehighnum;
// }
//
// public Double getSinglephonehightime() {
// return singlephonehightime;
// }
//
// public void setSinglephonehightime(Double singlephonehightime) {
// this.singlephonehightime = singlephonehightime;
// }
//
// public Double getPhoneonecalltime() {
// return phoneonecalltime;
// }
//
// public void setPhoneonecalltime(Double phoneonecalltime) {
// this.phoneonecalltime = phoneonecalltime;
// }
//
// public Double getPhonesumcalltime() {
// return phonesumcalltime;
// }
//
// public void setPhonesumcalltime(Double phonesumcalltime) {
// this.phonesumcalltime = phonesumcalltime;
// }
//
// public Double getPhonesumcallnum() {
// return phonesumcallnum;
// }
//
// public void setPhonesumcallnum(Double phonesumcallnum) {
// this.phonesumcallnum = phonesumcallnum;
// }
//
// public Double getPhoneintervalday() {
// return phoneintervalday;
// }
//
// public void setPhoneintervalday(Double phoneintervalday) {
// this.phoneintervalday = phoneintervalday;
// }
//
// public void setCreateTime(Date createTime) {
// this.createTime = createTime;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
// }
