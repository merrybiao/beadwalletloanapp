// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_xl_transactions")
// public class BwXlTransactions implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private String billCycle;//
// private String cellPhone;//
// private Integer planAmt;//
// private Integer totalAmt;//
// private String updateTime;//
// private Long orderId;//
// private Date createTime;//
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
// public void setBillCycle(String billCycle) {
// this.billCycle = billCycle;
// }
//
// public String getBillCycle() {
// return billCycle;
// }
//
// public void setCellPhone(String cellPhone) {
// this.cellPhone = cellPhone;
// }
//
// public String getCellPhone() {
// return cellPhone;
// }
//
// public void setPlanAmt(Integer planAmt) {
// this.planAmt = planAmt;
// }
//
// public Integer getPlanAmt() {
// return planAmt;
// }
//
// public void setTotalAmt(Integer totalAmt) {
// this.totalAmt = totalAmt;
// }
//
// public Integer getTotalAmt() {
// return totalAmt;
// }
//
// public void setUpdateTime(String updateTime) {
// this.updateTime = updateTime;
// }
//
// public String getUpdateTime() {
// return updateTime;
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
// }
