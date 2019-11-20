// package com.waterelephant.entity;
//
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
// import java.io.Serializable;
// import java.util.Date;
//
/// **
// * (code:xjbk001)
// *
// * @Author: ZhangChong
// * @Description:
// * @Date: Created in 11:58 2018/6/19
// * @Modified By:
// */
// @Table(name = "bw_xjbk_device_info")
// public class BwXjbkDeviceInfo implements Serializable {
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
// private Long id;
// private Long orderId;
// /**
// * // 设备标识 7d50dcb97e654de6890ae8000bb4558a
// */
// private String deviceId;
// /**
// * // iOS设备专用（同时受App Store影响可能缺失）IDFA标识 BAFA623B-A87F-4F69-85C8-52F76F1464BF
// */
// private String idfa;
// /**
// * // identifierForVendor
// */
// private String idfv;
// /**
// * //设备名称 iPhone 6 Plus
// */
// private String deviceInfo;
// /**
// * //设备类型 iOS
// */
// private String osType;
// /**
// * // 系统版本号 9.300000
// */
// private String osVersion;
// /**
// * // 最后登录时间 1476692044
// */
// private String lastLoginTime;
// /**
// * // 经度 121.5103238932292
// */
// private String gpsLongitude;
// /**
// * // 纬度 31.30658718532986
// */
// private String gpsLatitude;
// /**
// * // 具体地址 上海市杨浦区政学路靠近金芭蕾舞蹈(五角场店)
// */
// private String gpsAddress;
// /**
// * // 登录设备IP地址 127.0.0.1
// */
// private String ip;
// /**
// * // 内存容量
// */
// private String memory;
// /**
// * // 总内部存储
// */
// private String storage;
// /**
// * // 可使用内部存储
// */
// private String unuseStorage;
// /**
// * // 是否使用 wifi
// */
// private String wifi;
// /**
// * // wifi 名称
// */
// private String wifiName;
// /**
// * // 电量
// */
// private String bettary;
// /**
// * //运营商
// */
// private String carrier;
// /**
// * // 运营商编码
// */
// private String teleNum;
// /**
// * // 渠道
// */
// private String appMarket;
// /**
// * //是否 ROOT 或越狱
// */
// private String isRoot;
// /**
// * // DNS
// */
// private String dns;
// /**
// * // 是否为模拟器
// */
// private String isSimulator;
// /**
// * // 图片数量
// */
// private String picCount;
// /**
// * // android_id
// */
// private String androidId;
// /**
// * // 设备的唯一设备识别符
// */
// private String udid;
// /**
// * // 设备标识符UUID
// */
// private String uuid;
// /**
// * // 国际移动用户识别码
// */
// private String imsi;
// /**
// * // MAC地址
// */
// private String mac;
// /**
// * // 内存卡容量
// */
// private String sdcard;
// /**
// * // 已经使用容量
// */
// private String unuseSdcard;
//
// private Date createTime;
// private Date updateTime;
//
// public Long getId() {
// return id;
// }
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public Long getOrderId() {
// return orderId;
// }
//
// public void setOrderId(Long orderId) {
// this.orderId = orderId;
// }
//
// public String getDeviceId() {
// return deviceId;
// }
//
// public void setDeviceId(String deviceId) {
// this.deviceId = deviceId;
// }
//
// public String getIdfa() {
// return idfa;
// }
//
// public void setIdfa(String idfa) {
// this.idfa = idfa;
// }
//
// public String getIdfv() {
// return idfv;
// }
//
// public void setIdfv(String idfv) {
// this.idfv = idfv;
// }
//
// public String getDeviceInfo() {
// return deviceInfo;
// }
//
// public void setDeviceInfo(String deviceInfo) {
// this.deviceInfo = deviceInfo;
// }
//
// public String getOsType() {
// return osType;
// }
//
// public void setOsType(String osType) {
// this.osType = osType;
// }
//
// public String getOsVersion() {
// return osVersion;
// }
//
// public void setOsVersion(String osVersion) {
// this.osVersion = osVersion;
// }
//
// public String getLastLoginTime() {
// return lastLoginTime;
// }
//
// public void setLastLoginTime(String lastLoginTime) {
// this.lastLoginTime = lastLoginTime;
// }
//
// public String getGpsLongitude() {
// return gpsLongitude;
// }
//
// public void setGpsLongitude(String gpsLongitude) {
// this.gpsLongitude = gpsLongitude;
// }
//
// public String getGpsLatitude() {
// return gpsLatitude;
// }
//
// public void setGpsLatitude(String gpsLatitude) {
// this.gpsLatitude = gpsLatitude;
// }
//
// public String getGpsAddress() {
// return gpsAddress;
// }
//
// public void setGpsAddress(String gpsAddress) {
// this.gpsAddress = gpsAddress;
// }
//
// public String getIp() {
// return ip;
// }
//
// public void setIp(String ip) {
// this.ip = ip;
// }
//
// public String getMemory() {
// return memory;
// }
//
// public void setMemory(String memory) {
// this.memory = memory;
// }
//
// public String getStorage() {
// return storage;
// }
//
// public void setStorage(String storage) {
// this.storage = storage;
// }
//
// public String getUnuseStorage() {
// return unuseStorage;
// }
//
// public void setUnuseStorage(String unuseStorage) {
// this.unuseStorage = unuseStorage;
// }
//
// public String getWifi() {
// return wifi;
// }
//
// public void setWifi(String wifi) {
// this.wifi = wifi;
// }
//
// public String getWifiName() {
// return wifiName;
// }
//
// public void setWifiName(String wifiName) {
// this.wifiName = wifiName;
// }
//
// public String getBettary() {
// return bettary;
// }
//
// public void setBettary(String bettary) {
// this.bettary = bettary;
// }
//
// public String getCarrier() {
// return carrier;
// }
//
// public void setCarrier(String carrier) {
// this.carrier = carrier;
// }
//
// public String getTeleNum() {
// return teleNum;
// }
//
// public void setTeleNum(String teleNum) {
// this.teleNum = teleNum;
// }
//
// public String getAppMarket() {
// return appMarket;
// }
//
// public void setAppMarket(String appMarket) {
// this.appMarket = appMarket;
// }
//
// public String getIsRoot() {
// return isRoot;
// }
//
// public void setIsRoot(String isRoot) {
// this.isRoot = isRoot;
// }
//
// public String getDns() {
// return dns;
// }
//
// public void setDns(String dns) {
// this.dns = dns;
// }
//
// public String getIsSimulator() {
// return isSimulator;
// }
//
// public void setIsSimulator(String isSimulator) {
// this.isSimulator = isSimulator;
// }
//
// public String getPicCount() {
// return picCount;
// }
//
// public void setPicCount(String picCount) {
// this.picCount = picCount;
// }
//
// public String getAndroidId() {
// return androidId;
// }
//
// public void setAndroidId(String androidId) {
// this.androidId = androidId;
// }
//
// public String getUdid() {
// return udid;
// }
//
// public void setUdid(String udid) {
// this.udid = udid;
// }
//
// public String getUuid() {
// return uuid;
// }
//
// public void setUuid(String uuid) {
// this.uuid = uuid;
// }
//
// public String getImsi() {
// return imsi;
// }
//
// public void setImsi(String imsi) {
// this.imsi = imsi;
// }
//
// public String getMac() {
// return mac;
// }
//
// public void setMac(String mac) {
// this.mac = mac;
// }
//
// public String getSdcard() {
// return sdcard;
// }
//
// public void setSdcard(String sdcard) {
// this.sdcard = sdcard;
// }
//
// public String getUnuseSdcard() {
// return unuseSdcard;
// }
//
// public void setUnuseSdcard(String unuseSdcard) {
// this.unuseSdcard = unuseSdcard;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
//
// public void setCreateTime(Date createTime) {
// this.createTime = createTime;
// }
//
// public Date getUpdateTime() {
// return updateTime;
// }
//
// public void setUpdateTime(Date updateTime) {
// this.updateTime = updateTime;
// }
// }
