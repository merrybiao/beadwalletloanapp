//package com.waterelephant.sxyDrainage.entity.jdq;
//
//import java.util.Date;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Table(name = "bw_jdq_call")
//public class BwJdqCall {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
//    private Long id;
//    private java.util.Date gmtCreate;
//    private Date startTime;//通话时间
//    private Date updateTime;//数据获取时间
//    private Double useTime;//通话时长（秒）
//    private Double subtotal;//本次通话花费（元）
//    private String place;//通话地点
//    private Date gmtModified;
//    private String initType;//呼叫类型
//    private String callType;//通话类型
//    private String otherCellPhone;//对方号码
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
//    public Date getStartTime() {
//        return this.startTime;
//    }
//
//    public void setStartTime(Date startTime) {
//        this.startTime = startTime;
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
//    public Double getSubtotal() {
//        return this.subtotal;
//    }
//
//    public void setSubtotal(Double subtotal) {
//        this.subtotal = subtotal;
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
//    public String getInitType() {
//        return this.initType;
//    }
//
//    public void setInitType(String initType) {
//        this.initType = initType;
//    }
//
//    public String getCallType() {
//        return this.callType;
//    }
//
//    public void setCallType(String callType) {
//        this.callType = callType;
//    }
//
//    public String getOtherCellPhone() {
//        return this.otherCellPhone;
//    }
//
//    public void setOtherCellPhone(String otherCellPhone) {
//        this.otherCellPhone = otherCellPhone;
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
