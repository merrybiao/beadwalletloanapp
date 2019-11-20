//package com.waterelephant.sxyDrainage.entity.dkdh360;
//
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.util.Date;
//
///**
// * (code:dkdh001)
// *
// * @Author: zhangchong
// * @Date: 2018/7/27 14:43
// * @Description: 设备信息
// */
//@Table(name = "bw_dkdh360_device_info")
//public class BwDkdh360DeviceInfo {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
//    private Long id;
//    private Long orderId;
//    /**
//     * 手机设备号
//     */
//    private String deviceNum;
//    /**
//     * 手机平台
//     */
//    private String platform;
//    /**
//     * 经度
//     */
//    private String lat;
//    /**
//     * 纬度
//     */
//    private String lon;
//    /**
//     * 定位地址
//     */
//    private String address;
//    /**
//     * 手机型号
//     */
//    private String deviceInfo;
//    /**
//     * 运营商号
//     */
//    private String teleNum;
//    /**
//     * 数据服务商名称1=移动2=联通3=电信4=WiFi
//     */
//    private String teleName;
//    /**
//     * 设备状态1=已 root2=未 root
//     */
//    private String isRoot;
//    /**
//     * DNS
//     */
//    private String dns;
//    /**
//     * 手机内存
//     */
//    private String memSize;
//    /**
//     * 手机存储空间
//     */
//    private String storageSize;
//    /**
//     * 手机可用存储空间
//     */
//    private String avaStorageSize;
//    /**
//     * 手机品牌
//     */
//    private String phoneBrand;
//    /**
//     * 安卓厂商/苹果型号
//     */
//    private String deviceModel;
//    /**
//     * 手机 IMEI
//     */
//    private String imei;
//    /**
//     * 手机 IMSI
//     */
//    private String imsi;
//    /**
//     * 手机序列号
//     */
//    private String seriaNo;
//    /**
//     * 手机 androidID
//     */
//    private String androidId;
//    /**
//     * UDID 号
//     */
//    private String udid;
//    /**
//     * 手机 mac 地址
//     */
//    private String mac;
//    /**
//     * 操作系统版本
//     */
//    private String androidVer;
//    /**
//     * 手机 idfa
//     */
//    private String idfa;
//    /**
//     * 手机 idfv
//     */
//    private String idfv;
//    /**
//     * 手机平台
//     */
//    private String iosPlat;
//    /**
//     * 手机版本
//     */
//    private String iosVer;
//    /**
//     * UUID 号
//     */
//    private String uuid;
//    /**
//     * 是否模拟器 1=设备是模拟器0=设备非模拟器
//     */
//    private String isSimulator;
//    private Date createTime;
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
//    public String getDeviceNum() {
//        return deviceNum;
//    }
//
//    public void setDeviceNum(String deviceNum) {
//        this.deviceNum = deviceNum;
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
//    public String getLat() {
//        return lat;
//    }
//
//    public void setLat(String lat) {
//        this.lat = lat;
//    }
//
//    public String getLon() {
//        return lon;
//    }
//
//    public void setLon(String lon) {
//        this.lon = lon;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getDeviceInfo() {
//        return deviceInfo;
//    }
//
//    public void setDeviceInfo(String deviceInfo) {
//        this.deviceInfo = deviceInfo;
//    }
//
//    public String getTeleNum() {
//        return teleNum;
//    }
//
//    public void setTeleNum(String teleNum) {
//        this.teleNum = teleNum;
//    }
//
//    public String getTeleName() {
//        return teleName;
//    }
//
//    public void setTeleName(String teleName) {
//        this.teleName = teleName;
//    }
//
//    public String getIsRoot() {
//        return isRoot;
//    }
//
//    public void setIsRoot(String isRoot) {
//        this.isRoot = isRoot;
//    }
//
//    public String getDns() {
//        return dns;
//    }
//
//    public void setDns(String dns) {
//        this.dns = dns;
//    }
//
//    public String getMemSize() {
//        return memSize;
//    }
//
//    public void setMemSize(String memSize) {
//        this.memSize = memSize;
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
//    public String getPhoneBrand() {
//        return phoneBrand;
//    }
//
//    public void setPhoneBrand(String phoneBrand) {
//        this.phoneBrand = phoneBrand;
//    }
//
//    public String getDeviceModel() {
//        return deviceModel;
//    }
//
//    public void setDeviceModel(String deviceModel) {
//        this.deviceModel = deviceModel;
//    }
//
//    public String getImei() {
//        return imei;
//    }
//
//    public void setImei(String imei) {
//        this.imei = imei;
//    }
//
//    public String getImsi() {
//        return imsi;
//    }
//
//    public void setImsi(String imsi) {
//        this.imsi = imsi;
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
//    public String getAndroidId() {
//        return androidId;
//    }
//
//    public void setAndroidId(String androidId) {
//        this.androidId = androidId;
//    }
//
//    public String getUdid() {
//        return udid;
//    }
//
//    public void setUdid(String udid) {
//        this.udid = udid;
//    }
//
//    public String getMac() {
//        return mac;
//    }
//
//    public void setMac(String mac) {
//        this.mac = mac;
//    }
//
//    public String getAndroidVer() {
//        return androidVer;
//    }
//
//    public void setAndroidVer(String androidVer) {
//        this.androidVer = androidVer;
//    }
//
//    public String getIdfa() {
//        return idfa;
//    }
//
//    public void setIdfa(String idfa) {
//        this.idfa = idfa;
//    }
//
//    public String getIdfv() {
//        return idfv;
//    }
//
//    public void setIdfv(String idfv) {
//        this.idfv = idfv;
//    }
//
//    public String getIosPlat() {
//        return iosPlat;
//    }
//
//    public void setIosPlat(String iosPlat) {
//        this.iosPlat = iosPlat;
//    }
//
//    public String getIosVer() {
//        return iosVer;
//    }
//
//    public void setIosVer(String iosVer) {
//        this.iosVer = iosVer;
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
//    public String getIsSimulator() {
//        return isSimulator;
//    }
//
//    public void setIsSimulator(String isSimulator) {
//        this.isSimulator = isSimulator;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//}
