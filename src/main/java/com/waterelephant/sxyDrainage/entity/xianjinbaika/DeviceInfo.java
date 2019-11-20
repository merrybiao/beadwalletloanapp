//package com.waterelephant.sxyDrainage.entity.xianjinbaika;
//
///**
// * (code:xjbk)
// *
// * @Author: ZhangChong
// * @Description:
// * @Date: Created in 11:21 2018/6/19
// * @Modified By:
// */
//public class DeviceInfo {
//    private String device_id;    //	设备标识 7d50dcb97e654de6890ae8000bb4558a
//    private String idfa;//	iOS设备专用（同时受App Store影响可能缺失）IDFA标识 BAFA623B-A87F-4F69-85C8-52F76F1464BF
//    private String idfv;//	identifierForVendor
//    private String device_info;//设备名称 iPhone 6 Plus
//    private String os_type;//设备类型 iOS
//    private String os_version;//	系统版本号 9.300000
//    private String last_login_time;//	最后登录时间 1476692044
//    private String gps_longitude;//	经度 121.5103238932292
//    private String gps_latitude;//	纬度 31.30658718532986
//    private String gps_address;//	具体地址 上海市杨浦区政学路靠近金芭蕾舞蹈(五角场店)
//    private String ip;//	登录设备IP地址 127.0.0.1
//    private String memory;//	内存容量
//    private String storage;//	总内部存储
//    private String unuse_storage;//	可使用内部存储
//    private String wifi;//	是否使用 wifi
//    private String wifi_name;//	wifi 名称
//    private String bettary;//	电量
//    private String carrier;//运营商
//    private String tele_num;//	运营商编码
//    private String app_market;//	渠道
//    private String is_root;//是否 ROOT 或越狱
//    private String dns;//	DNS
//    private String is_simulator;//	是否为模拟器
//    private String pic_count;//	图片数量
//    private String android_id;//	android_id
//    private String udid;//	设备的唯一设备识别符
//    private String uuid;//	设备标识符UUID
//    private String imsi;//	国际移动用户识别码
//    private String mac;//	MAC地址
//    private String sdcard;//	内存卡容量
//    private String unuse_sdcard;//	已经使用容量
//
//    public String getDevice_id() {
//        return device_id;
//    }
//
//    public void setDevice_id(String device_id) {
//        this.device_id = device_id;
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
//    public String getDevice_info() {
//        return device_info;
//    }
//
//    public void setDevice_info(String device_info) {
//        this.device_info = device_info;
//    }
//
//    public String getOs_type() {
//        return os_type;
//    }
//
//    public void setOs_type(String os_type) {
//        this.os_type = os_type;
//    }
//
//    public String getOs_version() {
//        return os_version;
//    }
//
//    public void setOs_version(String os_version) {
//        this.os_version = os_version;
//    }
//
//    public String getLast_login_time() {
//        return last_login_time;
//    }
//
//    public void setLast_login_time(String last_login_time) {
//        this.last_login_time = last_login_time;
//    }
//
//    public String getGps_longitude() {
//        return gps_longitude;
//    }
//
//    public void setGps_longitude(String gps_longitude) {
//        this.gps_longitude = gps_longitude;
//    }
//
//    public String getGps_latitude() {
//        return gps_latitude;
//    }
//
//    public void setGps_latitude(String gps_latitude) {
//        this.gps_latitude = gps_latitude;
//    }
//
//    public String getGps_address() {
//        return gps_address;
//    }
//
//    public void setGps_address(String gps_address) {
//        this.gps_address = gps_address;
//    }
//
//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
//    }
//
//    public String getMemory() {
//        return memory;
//    }
//
//    public void setMemory(String memory) {
//        this.memory = memory;
//    }
//
//    public String getStorage() {
//        return storage;
//    }
//
//    public void setStorage(String storage) {
//        this.storage = storage;
//    }
//
//    public String getUnuse_storage() {
//        return unuse_storage;
//    }
//
//    public void setUnuse_storage(String unuse_storage) {
//        this.unuse_storage = unuse_storage;
//    }
//
//    public String getWifi() {
//        return wifi;
//    }
//
//    public void setWifi(String wifi) {
//        this.wifi = wifi;
//    }
//
//    public String getWifi_name() {
//        return wifi_name;
//    }
//
//    public void setWifi_name(String wifi_name) {
//        this.wifi_name = wifi_name;
//    }
//
//    public String getBettary() {
//        return bettary;
//    }
//
//    public void setBettary(String bettary) {
//        this.bettary = bettary;
//    }
//
//    public String getCarrier() {
//        return carrier;
//    }
//
//    public void setCarrier(String carrier) {
//        this.carrier = carrier;
//    }
//
//    public String getTele_num() {
//        return tele_num;
//    }
//
//    public void setTele_num(String tele_num) {
//        this.tele_num = tele_num;
//    }
//
//    public String getApp_market() {
//        return app_market;
//    }
//
//    public void setApp_market(String app_market) {
//        this.app_market = app_market;
//    }
//
//    public String getIs_root() {
//        return is_root;
//    }
//
//    public void setIs_root(String is_root) {
//        this.is_root = is_root;
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
//    public String getIs_simulator() {
//        return is_simulator;
//    }
//
//    public void setIs_simulator(String is_simulator) {
//        this.is_simulator = is_simulator;
//    }
//
//    public String getPic_count() {
//        return pic_count;
//    }
//
//    public void setPic_count(String pic_count) {
//        this.pic_count = pic_count;
//    }
//
//    public String getAndroid_id() {
//        return android_id;
//    }
//
//    public void setAndroid_id(String android_id) {
//        this.android_id = android_id;
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
//    public String getUuid() {
//        return uuid;
//    }
//
//    public void setUuid(String uuid) {
//        this.uuid = uuid;
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
//    public String getMac() {
//        return mac;
//    }
//
//    public void setMac(String mac) {
//        this.mac = mac;
//    }
//
//    public String getSdcard() {
//        return sdcard;
//    }
//
//    public void setSdcard(String sdcard) {
//        this.sdcard = sdcard;
//    }
//
//    public String getUnuse_sdcard() {
//        return unuse_sdcard;
//    }
//
//    public void setUnuse_sdcard(String unuse_sdcard) {
//        this.unuse_sdcard = unuse_sdcard;
//    }
//}
