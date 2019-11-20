package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 设备信息
 * Created by dengyan on 2017/7/20.
 */
public class DeviceInfoAll {
    @JSONField(name = "platform")
    private String platform; // 设备类型

    @JSONField(name = "tele_num")
    private String teleNum; // 运营商号（国家编码+运营商号）

    @JSONField(name = "tele_name")
    private String teleName; // 数据服务商名字:移动/联通/电信/Wifi

    @JSONField(name = "imei")
    private String imei; // 手机imei(Android)

    @JSONField(name = "imsi")
    private String imsi; // 手机imsi(Android)

    @JSONField(name = "seria_no")
    private String seriaNo; // 手机序列号

    @JSONField(name = "android_id")
    private String androidId; // 手机androidId(android)

    @JSONField(name = "udid")
    private String udid; // 安卓ID(UDID)(android)

    @JSONField(name = "mac")
    private String mac; // 手机mac地址(android/ios)

    @JSONField(name = "idfa")
    private String idfa; // 手机广告标识符idfa(ios)

    @JSONField(name = "idfv")
    private String idfv; // idfv(ios)

    @JSONField(name = "ios_plat")
    private String iosPlat; // 手机平台

    @JSONField(name = "ios_ver")
    private String iosVer; // 手机版本(iOS)

    @JSONField(name = "uuid")
    private String uuid; // UUID(iOS)

    @JSONField(name = "is_root")
    private String isRoot; // 设备状态

    @JSONField(name = "dns")
    private String dns; // DNS

    @JSONField(name = "mem_size")
    private String memSize; // 手机内存

    @JSONField(name = "storage_size")
    private String storageSize; // 手机存储空间

    @JSONField(name = "ava_storage_size")
    private  String avaStorageSize; // 手机可用存储空间

    @JSONField(name= "phone_brand")
    private String phoneBrand; // 手机品牌

    @JSONField(name = "android_ver")
    private String androidVer; // 操作系统版本

    @JSONField(name = "device_model")
    private String deviceModel; // （安卓）厂商/（苹果）型号


    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTeleNum() {
        return teleNum;
    }

    public void setTeleNum(String teleNum) {
        this.teleNum = teleNum;
    }

    public String getTeleName() {
        return teleName;
    }

    public void setTeleName(String teleName) {
        this.teleName = teleName;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getSeriaNo() {
        return seriaNo;
    }

    public void setSeriaNo(String seriaNo) {
        this.seriaNo = seriaNo;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getIdfv() {
        return idfv;
    }

    public void setIdfv(String idfv) {
        this.idfv = idfv;
    }

    public String getIosPlat() {
        return iosPlat;
    }

    public void setIosPlat(String iosPlat) {
        this.iosPlat = iosPlat;
    }

    public String getIosVer() {
        return iosVer;
    }

    public void setIosVer(String iosVer) {
        this.iosVer = iosVer;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(String isRoot) {
        this.isRoot = isRoot;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public String getMemSize() {
        return memSize;
    }

    public void setMemSize(String memSize) {
        this.memSize = memSize;
    }

    public String getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(String storageSize) {
        this.storageSize = storageSize;
    }

    public String getAvaStorageSize() {
        return avaStorageSize;
    }

    public void setAvaStorageSize(String avaStorageSize) {
        this.avaStorageSize = avaStorageSize;
    }

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    public String getAndroidVer() {
        return androidVer;
    }

    public void setAndroidVer(String androidVer) {
        this.androidVer = androidVer;
    }
}
