/// ******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 本软件为武汉水象科技有限公司开发研制。
// * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
// package com.waterelephant.entity;
//
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kaniu_operatorbasicinfo")
// public class BwKaniuOperatorBasicInfo {
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;
// private Long orderId;
// private String phone;
// private String business;
// private String crawlname;
// private String crawlidcardno;
// private String entername;
// private String enteridcardno;
// private String age;
// private String sex;
// private String origin;
// private String province;
// private String place;
// private String linkaddr;
// private String isrealname;
// private Double onlinetime;
// private Double totalcontacttime;
// private Double threemonthcontacttime;
// private Double threamonthcontactcount;
// private String ischeckname;
// private String isidcardno;
// private String iscallfriendone;
// private String iscallfriendtwo;
// private String iscallfriendthree;
// private String friendprovince;
// private String provincepercent;
// private String concatmacao;
// private Integer contact110;
// private Integer contact120;
// private Integer isblacklist;
// private Integer isphoneoneblacklist;
// private Integer isphonetwoblacklist;
// private Integer isphonethreeblacklist;
// private Integer mutualcontactcount;
// private String stateone;
// private String statetwo;
// private String statethree;
// private Double aveincall;
// private Double aveoutcall;
// private Double aveincallcount;
// private Double aveoutcallcount;
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
// public String getPhone() {
// return phone;
// }
//
// public void setPhone(String phone) {
// this.phone = phone;
// }
//
// public String getBusiness() {
// return business;
// }
//
// public void setBusiness(String business) {
// this.business = business;
// }
//
// public String getCrawlname() {
// return crawlname;
// }
//
// public void setCrawlname(String crawlname) {
// this.crawlname = crawlname;
// }
//
// public String getCrawlidcardno() {
// return crawlidcardno;
// }
//
// public void setCrawlidcardno(String crawlidcardno) {
// this.crawlidcardno = crawlidcardno;
// }
//
// public String getEntername() {
// return entername;
// }
//
// public void setEntername(String entername) {
// this.entername = entername;
// }
//
// public String getEnteridcardno() {
// return enteridcardno;
// }
//
// public void setEnteridcardno(String enteridcardno) {
// this.enteridcardno = enteridcardno;
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
// public String getSex() {
// return sex;
// }
//
// public void setSex(String sex) {
// this.sex = sex;
// }
//
// public String getOrigin() {
// return origin;
// }
//
// public void setOrigin(String origin) {
// this.origin = origin;
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
// public String getPlace() {
// return place;
// }
//
// public void setPlace(String place) {
// this.place = place;
// }
//
// public String getLinkaddr() {
// return linkaddr;
// }
//
// public void setLinkaddr(String linkaddr) {
// this.linkaddr = linkaddr;
// }
//
// public String getIsrealname() {
// return isrealname;
// }
//
// public void setIsrealname(String isrealname) {
// this.isrealname = isrealname;
// }
//
// public Double getOnlinetime() {
// return onlinetime;
// }
//
// public void setOnlinetime(Double onlinetime) {
// this.onlinetime = onlinetime;
// }
//
// public Double getTotalcontacttime() {
// return totalcontacttime;
// }
//
// public void setTotalcontacttime(Double totalcontacttime) {
// this.totalcontacttime = totalcontacttime;
// }
//
// public Double getThreemonthcontacttime() {
// return threemonthcontacttime;
// }
//
// public void setThreemonthcontacttime(Double threemonthcontacttime) {
// this.threemonthcontacttime = threemonthcontacttime;
// }
//
// public Double getThreamonthcontactcount() {
// return threamonthcontactcount;
// }
//
// public void setThreamonthcontactcount(Double threamonthcontactcount) {
// this.threamonthcontactcount = threamonthcontactcount;
// }
//
// public String getIscheckname() {
// return ischeckname;
// }
//
// public void setIscheckname(String ischeckname) {
// this.ischeckname = ischeckname;
// }
//
// public String getIsidcardno() {
// return isidcardno;
// }
//
// public void setIsidcardno(String isidcardno) {
// this.isidcardno = isidcardno;
// }
//
// public String getIscallfriendone() {
// return iscallfriendone;
// }
//
// public void setIscallfriendone(String iscallfriendone) {
// this.iscallfriendone = iscallfriendone;
// }
//
// public String getIscallfriendtwo() {
// return iscallfriendtwo;
// }
//
// public void setIscallfriendtwo(String iscallfriendtwo) {
// this.iscallfriendtwo = iscallfriendtwo;
// }
//
// public String getIscallfriendthree() {
// return iscallfriendthree;
// }
//
// public void setIscallfriendthree(String iscallfriendthree) {
// this.iscallfriendthree = iscallfriendthree;
// }
//
// public String getFriendprovince() {
// return friendprovince;
// }
//
// public void setFriendprovince(String friendprovince) {
// this.friendprovince = friendprovince;
// }
//
// public String getProvincepercent() {
// return provincepercent;
// }
//
// public void setProvincepercent(String provincepercent) {
// this.provincepercent = provincepercent;
// }
//
// public String getConcatmacao() {
// return concatmacao;
// }
//
// public void setConcatmacao(String concatmacao) {
// this.concatmacao = concatmacao;
// }
//
// public Integer getContact110() {
// return contact110;
// }
//
// public void setContact110(Integer contact110) {
// this.contact110 = contact110;
// }
//
// public Integer getContact120() {
// return contact120;
// }
//
// public void setContact120(Integer contact120) {
// this.contact120 = contact120;
// }
//
// public Integer getIsblacklist() {
// return isblacklist;
// }
//
// public void setIsblacklist(Integer isblacklist) {
// this.isblacklist = isblacklist;
// }
//
// public Integer getIsphoneoneblacklist() {
// return isphoneoneblacklist;
// }
//
// public void setIsphoneoneblacklist(Integer isphoneoneblacklist) {
// this.isphoneoneblacklist = isphoneoneblacklist;
// }
//
// public Integer getIsphonetwoblacklist() {
// return isphonetwoblacklist;
// }
//
// public void setIsphonetwoblacklist(Integer isphonetwoblacklist) {
// this.isphonetwoblacklist = isphonetwoblacklist;
// }
//
// public Integer getIsphonethreeblacklist() {
// return isphonethreeblacklist;
// }
//
// public void setIsphonethreeblacklist(Integer isphonethreeblacklist) {
// this.isphonethreeblacklist = isphonethreeblacklist;
// }
//
// public Integer getMutualcontactcount() {
// return mutualcontactcount;
// }
//
// public void setMutualcontactcount(Integer mutualcontactcount) {
// this.mutualcontactcount = mutualcontactcount;
// }
//
// public String getStateone() {
// return stateone;
// }
//
// public void setStateone(String stateone) {
// this.stateone = stateone;
// }
//
// public String getStatetwo() {
// return statetwo;
// }
//
// public void setStatetwo(String statetwo) {
// this.statetwo = statetwo;
// }
//
// public String getStatethree() {
// return statethree;
// }
//
// public void setStatethree(String statethree) {
// this.statethree = statethree;
// }
//
// public Double getAveincall() {
// return aveincall;
// }
//
// public void setAveincall(Double aveincall) {
// this.aveincall = aveincall;
// }
//
// public Double getAveoutcall() {
// return aveoutcall;
// }
//
// public void setAveoutcall(Double aveoutcall) {
// this.aveoutcall = aveoutcall;
// }
//
// public Double getAveincallcount() {
// return aveincallcount;
// }
//
// public void setAveincallcount(Double aveincallcount) {
// this.aveincallcount = aveincallcount;
// }
//
// public Double getAveoutcallcount() {
// return aveoutcallcount;
// }
//
// public void setAveoutcallcount(Double aveoutcallcount) {
// this.aveoutcallcount = aveoutcallcount;
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
//
// }
