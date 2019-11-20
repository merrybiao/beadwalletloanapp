// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kaniu_allphonecategory")
// public class BwKaniuAllphonecategory implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;//
// private Long pid;// 父节点Id
// private Double allcallduration;// 号码总通话时长
// private Double allcalltimes;// 号码总通次数
// private Double maxcallduration;// 单个号码最高通话时长
// private Double maxcalltimes;// 单个号码最高通话次数
// private Double ninetydayscallduration;// 近90天通话时长汇总
// private Double ninetydayscalltimes;// 近90天通话次数汇总
// private Double phonecategorynumber;// 号码个数
// private Double sevendayscallduration;// 近7天通话时长汇总
// private Double sevendayscalltimes;// 近7天通话次数汇总
// private Double thirtydayscallduration;// 近30天通话时长汇总
// private Double thirtydayscalltimes;// 近30天通话次数汇总
// private String type;/*
// * 卡牛号码库类型 "1 TELEPHONE(""手机号"") 2 NORMAL_PHONE(""座机号"") 3 KINSHIP_SHORT_PHONE(""亲情短号"") 4
// * PUBLIC_SERVICE_PHONE(""公共服务号""), 5 NOTE_CONTAIN_SENSITIVE_WORD_PHONE(""备注命中贷款业务敏感词号码""), 6
// * NOTE_CONTAIN_LOANS_NUMBER_PHONE(""备注命中贷款平台词号码"") 7
// * CONTAIN_INTERMEDIARY_AGENT_NUMBER_PHONE(""命中中介电话号码""), 8 HAS_KN_ACCOUNT_PHONE(""有卡牛账户号码""), 9
// * HAS_SSJ_ACCOUNT_PHONE(""有随手记账户号码"") 10 HAS_LOANS_RECORD_PHONE(""有贷款放款记录的号码"") 11
// * HAS_OVERDUE_RECORD_PHONE(""有逾期记录的号码"");"
// */
// private Date createTime;//
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
// public Long getPid() {
// return pid;
// }
//
// public void setPid(Long pid) {
// this.pid = pid;
// }
//
// public Double getAllcallduration() {
// return allcallduration;
// }
//
// public void setAllcallduration(Double allcallduration) {
// this.allcallduration = allcallduration;
// }
//
// public Double getAllcalltimes() {
// return allcalltimes;
// }
//
// public void setAllcalltimes(Double allcalltimes) {
// this.allcalltimes = allcalltimes;
// }
//
// public Double getMaxcallduration() {
// return maxcallduration;
// }
//
// public void setMaxcallduration(Double maxcallduration) {
// this.maxcallduration = maxcallduration;
// }
//
// public Double getMaxcalltimes() {
// return maxcalltimes;
// }
//
// public void setMaxcalltimes(Double maxcalltimes) {
// this.maxcalltimes = maxcalltimes;
// }
//
// public Double getNinetydayscallduration() {
// return ninetydayscallduration;
// }
//
// public void setNinetydayscallduration(Double ninetydayscallduration) {
// this.ninetydayscallduration = ninetydayscallduration;
// }
//
// public Double getNinetydayscalltimes() {
// return ninetydayscalltimes;
// }
//
// public void setNinetydayscalltimes(Double ninetydayscalltimes) {
// this.ninetydayscalltimes = ninetydayscalltimes;
// }
//
// public Double getPhonecategorynumber() {
// return phonecategorynumber;
// }
//
// public void setPhonecategorynumber(Double phonecategorynumber) {
// this.phonecategorynumber = phonecategorynumber;
// }
//
// public Double getSevendayscallduration() {
// return sevendayscallduration;
// }
//
// public void setSevendayscallduration(Double sevendayscallduration) {
// this.sevendayscallduration = sevendayscallduration;
// }
//
// public Double getSevendayscalltimes() {
// return sevendayscalltimes;
// }
//
// public void setSevendayscalltimes(Double sevendayscalltimes) {
// this.sevendayscalltimes = sevendayscalltimes;
// }
//
// public Double getThirtydayscallduration() {
// return thirtydayscallduration;
// }
//
// public void setThirtydayscallduration(Double thirtydayscallduration) {
// this.thirtydayscallduration = thirtydayscallduration;
// }
//
// public Double getThirtydayscalltimes() {
// return thirtydayscalltimes;
// }
//
// public void setThirtydayscalltimes(Double thirtydayscalltimes) {
// this.thirtydayscalltimes = thirtydayscalltimes;
// }
//
// public String getType() {
// return type;
// }
//
// public void setType(String type) {
// this.type = type;
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
// }
