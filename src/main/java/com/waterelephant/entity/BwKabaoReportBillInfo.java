// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kabao_report_bill_info")
// public class BwKabaoReportBillInfo implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private String account;//
// private String billStartDate;// 账单起始日
// private String billEndDate;// 账单结束日
// private Double billFee;// 账单消费
// private String billMonth;// 账单月份
// private String itemName;//
// private Double itemValue;//
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
// public void setAccount(String account) {
// this.account = account;
// }
//
// public String getAccount() {
// return account;
// }
//
// public String getBillStartDate() {
// return billStartDate;
// }
//
// public void setBillStartDate(String billStartDate) {
// this.billStartDate = billStartDate;
// }
//
// public String getBillEndDate() {
// return billEndDate;
// }
//
// public void setBillEndDate(String billEndDate) {
// this.billEndDate = billEndDate;
// }
//
// public void setBillFee(Double billFee) {
// this.billFee = billFee;
// }
//
// public Double getBillFee() {
// return billFee;
// }
//
// public void setBillMonth(String billMonth) {
// this.billMonth = billMonth;
// }
//
// public String getBillMonth() {
// return billMonth;
// }
//
// public void setItemName(String itemName) {
// this.itemName = itemName;
// }
//
// public String getItemName() {
// return itemName;
// }
//
// public void setItemValue(Double itemValue) {
// this.itemValue = itemValue;
// }
//
// public Double getItemValue() {
// return itemValue;
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
