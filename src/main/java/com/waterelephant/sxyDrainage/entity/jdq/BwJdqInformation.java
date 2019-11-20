//package com.waterelephant.sxyDrainage.entity.jdq;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Table(name = "bw_jdq_information")
//public class BwJdqInformation {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
//    private Long id;
//    private java.util.Date gmtCreate;
//    private String datasource;//数据源
//    private String juid;//系统用户juid
//    private java.util.Date gmtModified;
//    private String version;//接口版本
//    private String token;//系统用户id
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
//    public String getDatasource() {
//        return this.datasource;
//    }
//
//    public void setDatasource(String datasource) {
//        this.datasource = datasource;
//    }
//
//    public String getJuid() {
//        return this.juid;
//    }
//
//    public void setJuid(String juid) {
//        this.juid = juid;
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
//    public String getVersion() {
//        return this.version;
//    }
//
//    public void setVersion(String version) {
//        this.version = version;
//    }
//
//    public String getToken() {
//        return this.token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//}
