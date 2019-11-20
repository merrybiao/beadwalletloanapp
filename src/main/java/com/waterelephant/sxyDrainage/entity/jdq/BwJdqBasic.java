//package com.waterelephant.sxyDrainage.entity.jdq;
//
//import java.util.Date;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Table(name = "bw_jdq_basic")
//public class BwJdqBasic {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
//    private Long id;
//    private java.util.Date gmtCreate;
//    private Date updateTime;//数据获取时间
//    private Date regTime;//入网时间
//    private String idcard;//登记身份证号
//    private String realName;//登记姓名
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
//    public Date getUpdateTime() {
//        return this.updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    public Date getRegTime() {
//        return this.regTime;
//    }
//
//    public void setRegTime(Date regTime) {
//        this.regTime = regTime;
//    }
//
//    public String getIdcard() {
//        return this.idcard;
//    }
//
//    public void setIdcard(String idcard) {
//        this.idcard = idcard;
//    }
//
//    public String getRealName() {
//        return this.realName;
//    }
//
//    public void setRealName(String realName) {
//        this.realName = realName;
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
