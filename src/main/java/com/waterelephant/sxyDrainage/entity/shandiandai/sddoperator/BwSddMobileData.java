//package com.waterelephant.sxyDrainage.entity.shandiandai.sddoperator;
//
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.util.Date;
//
///**
// * 闪电贷手机信息
// *
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/11
// * @since JDK 1.8
// */
//@Table(name = "bw_sdd_mobile_data")
//public class BwSddMobileData {
//
//
//    @Id
//    private Long id;
//    /** 我方订单号 */
//    private Long orderId;
//
//    /** 手机系统(android、ios) */
//    private String mobileSystem;
//    /** 手机型号 */
//    private String mobileType;
//    /** imei */
//    private String imeiCode;
//    /** wifi mac地址 */
//    private String wifiMac;
//    /** 经度 */
//    private String lng;
//    /** 纬度 */
//    private String lat;
//    /** app列表（仅android） */
//    private String appList;
//
//    private Date createTime;
//    private Date updateTime;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
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
//    public String getMobileSystem() {
//        return mobileSystem;
//    }
//
//    public void setMobileSystem(String mobileSystem) {
//        this.mobileSystem = mobileSystem;
//    }
//
//    public String getMobileType() {
//        return mobileType;
//    }
//
//    public void setMobileType(String mobileType) {
//        this.mobileType = mobileType;
//    }
//
//    public String getImeiCode() {
//        return imeiCode;
//    }
//
//    public void setImeiCode(String imeiCode) {
//        this.imeiCode = imeiCode;
//    }
//
//    public String getWifiMac() {
//        return wifiMac;
//    }
//
//    public void setWifiMac(String wifiMac) {
//        this.wifiMac = wifiMac;
//    }
//
//    public String getLng() {
//        return lng;
//    }
//
//    public void setLng(String lng) {
//        this.lng = lng;
//    }
//
//    public String getLat() {
//        return lat;
//    }
//
//    public void setLat(String lat) {
//        this.lat = lat;
//    }
//
//    public String getAppList() {
//        return appList;
//    }
//
//    public void setAppList(String appList) {
//        this.appList = appList;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//}
