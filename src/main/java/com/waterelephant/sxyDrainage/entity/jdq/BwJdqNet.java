//package com.waterelephant.sxyDrainage.entity.jdq;
//
//import java.util.Date;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Table(name = "bw_jdq_net")
//public class BwJdqNet {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
//    private Long id;
//    private java.util.Date gmtCreate;
//    private Date updateTime;//数据获取时间
//    private Date startTime;//数据获取时间
//    private Double useTime;//使用时长（秒）
//    private Double subflow;//流量使用量（KB）
//    private Double subtotal;//本次流量花费（元）
//    private String netType;//流量类型
//    private String place;//流量使用地点
//    private java.util.Date gmtModified;
//    private String cellPhone;//本机号码
//    private Long orderId;
//
//    public Date getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(Date startTime) {
//        this.startTime = startTime;
//    }
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
//    public Date getUpdateTime() {
//        return this.updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    public Double getUseTime() {
//        return this.useTime;
//    }
//
//    public void setUseTime(Double useTime) {
//        this.useTime = useTime;
//    }
//
//    public Double getSubflow() {
//        return this.subflow;
//    }
//
//    public void setSubflow(Double subflow) {
//        this.subflow = subflow;
//    }
//
//    public Double getSubtotal() {
//        return this.subtotal;
//    }
//
//    public void setSubtotal(Double subtotal) {
//        this.subtotal = subtotal;
//    }
//
//    public String getNetType() {
//        return this.netType;
//    }
//
//    public void setNetType(String netType) {
//        this.netType = netType;
//    }
//
//    public String getPlace() {
//        return this.place;
//    }
//
//    public void setPlace(String place) {
//        this.place = place;
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
