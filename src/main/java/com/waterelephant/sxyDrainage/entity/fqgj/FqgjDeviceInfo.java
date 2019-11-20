//package com.waterelephant.sxyDrainage.entity.fqgj;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.io.Serializable;
//import java.util.Date;
//
///**
// * @author wangfei
// * @version 1.0
// * @date 2018/6/14
// * @Description <分期管家设备信息表>
// * @since JDK 1.8
// */
//@Table(name = "bw_fqgj_device_info")
//public class FqgjDeviceInfo implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    /**
//     * 主键id
//     */
//    private Long id;
//    /**
//     * 订单Id
//     */
//    private Long orderId;
//    /**
//     * 设备类型(iphone/Android)
//     */
//    private String platform;
//    /**
//     * 运营商号
//     */
//    private String teleNum;
//    /**
//     * 数据服务商名字
//     */
//    private String teleName;
//    /**
//     * 手机imei(Android)
//     */
//    private String imei;
//    /**
//     * 手机imsi(Android)
//     */
//    private String imsi;
//    /**
//     * 手机序列号
//     */
//    private String seriaNo;
//    /**
//     * 手机androidId(android)
//     */
//    private String androidId;
//    /**
//     * 安卓ID(UDID)(android)
//     */
//    private String udid;
//    /**
//     * 手机mac地址(android/ios)
//     */
//    private String mac;
//    /**
//     * 手机广告标识符idfa(ios)
//     */
//    private String idfa;
//    /**
//     * idfv(ios)
//     */
//    private String idfv;
//    /**
//     * 手机平台(iOS)
//     */
//    private String iosPlat;
//    /**
//     * 手机版本(iOS)
//     */
//    private String iosVer;
//    /**
//     * UUID(iOS)
//     */
//    private String uuid;
//    /**
//     * 设备状态)
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
//     * 手机存储空间)
//     */
//    private String storageSize;
//    /**
//     * 手机可用存储空间
//     */
//    private String avaStorageSize;
//    /**
//     * 手机品牌)
//     */
//    private String phoneBrand;
//    /**
//     * 操作系统版本
//     */
//    private String androidVer;
//    /**
//     * （安卓）厂商/（苹果）型号
//     */
//    private String deviceModel;
//
//    /**
//     *手机内的应用安装列表
//     */
//    private String installedApps;
//    /**
//     * 手机GPS信息
//     */
//    private String appLocation;
//    /**
//     * 创建时间
//     */
//
//    private Date createTime;
//    /**
//     * 更新时间
//     */
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
//    public String getPlatform() {
//        return platform;
//    }
//
//    public void setPlatform(String platform) {
//        this.platform = platform;
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
//    public String getAndroidVer() {
//        return androidVer;
//    }
//
//    public void setAndroidVer(String androidVer) {
//        this.androidVer = androidVer;
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
//    public String getInstalledApps() {
//        return installedApps;
//    }
//
//    public void setInstalledApps(String installedApps) {
//        this.installedApps = installedApps;
//    }
//
//    public String getAppLocation() {
//        return appLocation;
//    }
//
//    public void setAppLocation(String appLocation) {
//        this.appLocation = appLocation;
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
