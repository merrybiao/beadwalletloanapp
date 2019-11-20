//package com.waterelephant.sxyDrainage.entity.wacai.table;
//
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.util.Date;
//
///**
// * @author 王亚楠
// * @version 1.0
// * @date 2018/7/5
// * @since JDK 1.8
// */
//@Table(name = "bw_wc_device")
//public class BwWcDevice {
//    @Id
//    private Long id;
//    /** 我方订单号 */
//    private Long orderId;
//    /** 手机 imei , */
//    private String deviceId;
//    /** 标示符1，ios-idfa／android-imei */
//    private String identifier1;
//    /** 标示符2，ios-idfv／android-imsi */
//    private String identifier2;
//    /** 手机序列号 */
//    private String seriaNo;
//    /** uuid */
//    private String uuid;
//    /** 设备类型 */
//    private String deviceType;
//    /** 枚举：ios／android */
//    private String platform;
//    /** 是否模拟器，0：否 1：是 */
//    private Integer simulator;
//    /** 是否越狱，0：否 1：是 */
//    private Integer root;
//    /** 手机内存大小 */
//    private String memorySize;
//    /** 手机存储空间 */
//    private String storageSize;
//    /** 手机可用存储空间 */
//    private String avaStorageSize;
//    /** 操作系统版本 */
//    private String systemVersion;
//    /** 本应用名称 */
//    private String appName;
//    /** 本应用版本 */
//    private String appVersion;
//    /** 挖财更新时间 */
//    private Date updatedTime;
//    /** 创建时间 */
//    private Date createTime;
//    /** 更新时间 */
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
//    public String getDeviceId() {
//        return deviceId;
//    }
//
//    public void setDeviceId(String deviceId) {
//        this.deviceId = deviceId;
//    }
//
//    public String getIdentifier1() {
//        return identifier1;
//    }
//
//    public void setIdentifier1(String identifier1) {
//        this.identifier1 = identifier1;
//    }
//
//    public String getIdentifier2() {
//        return identifier2;
//    }
//
//    public void setIdentifier2(String identifier2) {
//        this.identifier2 = identifier2;
//    }
//
//    public String getSeriaNo() {
//        return seriaNo;
//    }
//
//    public void setSeriaNo(String seriaNo) {
//        this.seriaNo = seriaNo;
//    }
//
//    public String getUuid() {
//        return uuid;
//    }
//
//    public void setUuid(String uuid) {
//        this.uuid = uuid;
//    }
//
//    public String getDeviceType() {
//        return deviceType;
//    }
//
//    public void setDeviceType(String deviceType) {
//        this.deviceType = deviceType;
//    }
//
//    public String getPlatform() {
//        return platform;
//    }
//
//    public void setPlatform(String platform) {
//        this.platform = platform;
//    }
//
//    public Integer getSimulator() {
//        return simulator;
//    }
//
//    public void setSimulator(Integer simulator) {
//        this.simulator = simulator;
//    }
//
//    public Integer getRoot() {
//        return root;
//    }
//
//    public void setRoot(Integer root) {
//        this.root = root;
//    }
//
//    public String getMemorySize() {
//        return memorySize;
//    }
//
//    public void setMemorySize(String memorySize) {
//        this.memorySize = memorySize;
//    }
//
//    public String getStorageSize() {
//        return storageSize;
//    }
//
//    public void setStorageSize(String storageSize) {
//        this.storageSize = storageSize;
//    }
//
//    public String getAvaStorageSize() {
//        return avaStorageSize;
//    }
//
//    public void setAvaStorageSize(String avaStorageSize) {
//        this.avaStorageSize = avaStorageSize;
//    }
//
//    public String getSystemVersion() {
//        return systemVersion;
//    }
//
//    public void setSystemVersion(String systemVersion) {
//        this.systemVersion = systemVersion;
//    }
//
//    public String getAppName() {
//        return appName;
//    }
//
//    public void setAppName(String appName) {
//        this.appName = appName;
//    }
//
//    public String getAppVersion() {
//        return appVersion;
//    }
//
//    public void setAppVersion(String appVersion) {
//        this.appVersion = appVersion;
//    }
//
//    public Date getUpdatedTime() {
//        return updatedTime;
//    }
//
//    public void setUpdatedTime(Date updatedTime) {
//        this.updatedTime = updatedTime;
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
