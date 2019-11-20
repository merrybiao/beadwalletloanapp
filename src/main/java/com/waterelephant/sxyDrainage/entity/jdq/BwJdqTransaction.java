//package com.waterelephant.sxyDrainage.entity.jdq;
//
//import java.util.Date;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Table(name = "bw_jdq_transaction")
//public class BwJdqTransaction {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
//    private Long id;
//    private java.util.Date gmtCreate;
//    private Double totalAmt;//总费用（元）
//    private Date updateTime;//数据获取时间
//    private Double payAmt;//实际缴费金额（元）
//    private Double planAmt;//套餐及固定费（元）
//    private Date billCycle;//账单月份
//    private java.util.Date gmtModified;
//    private String cellPhone;//本机号码
//    private Long orderId;
//
//    public Long getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(Long orderId) {
//        this.orderId = orderId;
//    }
//
//    public Long getId() {
//        return this.id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public java.util.Date getGmtCreate() {
//        return this.gmtCreate;
//    }
//
//    public void setGmtCreate(java.util.Date gmtCreate) {
//        this.gmtCreate = gmtCreate;
//    }
//
//    public Double getTotalAmt() {
//        return this.totalAmt;
//    }
//
//    public void setTotalAmt(Double totalAmt) {
//        this.totalAmt = totalAmt;
//    }
//
//    public Date getUpdateTime() {
//        return this.updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    public Double getPayAmt() {
//        return this.payAmt;
//    }
//
//    public void setPayAmt(Double payAmt) {
//        this.payAmt = payAmt;
//    }
//
//    public Double getPlanAmt() {
//        return this.planAmt;
//    }
//
//    public void setPlanAmt(Double planAmt) {
//        this.planAmt = planAmt;
//    }
//
//    public Date getBillCycle() {
//        return this.billCycle;
//    }
//
//    public void setBillCycle(Date billCycle) {
//        this.billCycle = billCycle;
//    }
//
//    public java.util.Date getGmtModified() {
//        return this.gmtModified;
//    }
//
//    public void setGmtModified(java.util.Date gmtModified) {
//        this.gmtModified = gmtModified;
//    }
//
//    public String getCellPhone() {
//        return this.cellPhone;
//    }
//
//    public void setCellPhone(String cellPhone) {
//        this.cellPhone = cellPhone;
//    }
//
//}
