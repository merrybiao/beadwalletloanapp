//package com.waterelephant.sxyDrainage.entity.geinihua;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Transient;
//
//import lombok.Data;
//
///**
// * 设备信息
// *
// * @author xanthuim
// */
//@Table(name = "bw_gnh_device")
//public class DeviceInfo {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Date gmtCreate;
//
//    private Date gmtModified;
//
//    private Long orderId;
//
//    /**
//     * 手机设备号（iOS是IDFA，Android是IMEI） string 是
//     * 有些手机刷机后或者一些模拟器会缺失
//     */
//    private String deviceNo;
//    /**
//     * 手机型号 string 是
//     */
//    private String type;
//    /**
//     * 手机品牌 string 是
//     */
//    private String brand;
//    /**
//     * 手机平台 string 是 Android、iOS
//     */
//    private String platform;
//    /**
//     * 手机序列号 string 是
//     */
//    private String serialNumber;
//    /**
//     * wifimac string 是
//     */
//    private String wifiMac;
//    /**
//     * bssId string 是
//     */
//    private String bssId;
//    /**
//     * mobileMac string 是
//     */
//    private String mobileMac;
//    /**
//     * imsi string 是
//     */
//    private String imsi;
//    /**
//     * androidId string 是
//     */
//    private String androidId;
//    /**
//     * UUID string 是
//     */
//    @Column(name = "uuid")
//    private String UUID;
//    /**
//     * deviceUUID string 是
//     */
//    @Column(name = "device_uuid")
//    private String deviceUUID;
//    /**
//     * idfv string 是
//     */
//    private String idfv;
//
//    /**
//     * 通讯录
//     */
//    @Transient
//    private List<Phone> phoneList;
//
//    @Transient
//    private Gps gps;
//
//    /**
//     * APP列表
//     */
//    @Transient
//    private List<App> appList;
//    @Transient
//    private List<CallRecord> callRecord;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Date getGmtCreate() {
//        return gmtCreate;
//    }
//
//    public void setGmtCreate(Date gmtCreate) {
//        this.gmtCreate = gmtCreate;
//    }
//
//    public Date getGmtModified() {
//        return gmtModified;
//    }
//
//    public void setGmtModified(Date gmtModified) {
//        this.gmtModified = gmtModified;
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
//    public String getDeviceNo() {
//        return deviceNo;
//    }
//
//    public void setDeviceNo(String deviceNo) {
//        this.deviceNo = deviceNo;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getBrand() {
//        return brand;
//    }
//
//    public void setBrand(String brand) {
//        this.brand = brand;
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
//    public String getSerialNumber() {
//        return serialNumber;
//    }
//
//    public void setSerialNumber(String serialNumber) {
//        this.serialNumber = serialNumber;
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
//    public String getBssId() {
//        return bssId;
//    }
//
//    public void setBssId(String bssId) {
//        this.bssId = bssId;
//    }
//
//    public String getMobileMac() {
//        return mobileMac;
//    }
//
//    public void setMobileMac(String mobileMac) {
//        this.mobileMac = mobileMac;
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
//    public String getAndroidId() {
//        return androidId;
//    }
//
//    public void setAndroidId(String androidId) {
//        this.androidId = androidId;
//    }
//
//    public String getUUID() {
//        return UUID;
//    }
//
//    public void setUUID(String UUID) {
//        this.UUID = UUID;
//    }
//
//    public String getDeviceUUID() {
//        return deviceUUID;
//    }
//
//    public void setDeviceUUID(String deviceUUID) {
//        this.deviceUUID = deviceUUID;
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
//    public List<Phone> getPhoneList() {
//        return phoneList;
//    }
//
//    public void setPhoneList(List<Phone> phoneList) {
//        this.phoneList = phoneList;
//    }
//
//    public Gps getGps() {
//        return gps;
//    }
//
//    public void setGps(Gps gps) {
//        this.gps = gps;
//    }
//
//    public List<App> getAppList() {
//        return appList;
//    }
//
//    public void setAppList(List<App> appList) {
//        this.appList = appList;
//    }
//
//    public List<CallRecord> getCallRecord() {
//        return callRecord;
//    }
//
//    public void setCallRecord(List<CallRecord> callRecord) {
//        this.callRecord = callRecord;
//    }
//
//    public static class Phone {
//        /**
//         * 通讯录电话 string 是
//         */
//        private String phone;
//        /**
//         * 通讯录姓名 string 是
//         */
//        private String name;
//
//        public String getPhone() {
//            return phone;
//        }
//
//        public void setPhone(String phone) {
//            this.phone = phone;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//    }
//
//    public static class Gps {
//        /**
//         * 经度 string 是 用户不开启的情况会缺失
//         */
//        private String longtiude;
//        /**
//         * 纬度 string 是
//         */
//        private String latitude;
//
//        public String getLongtiude() {
//            return longtiude;
//        }
//
//        public void setLongtiude(String longtiude) {
//            this.longtiude = longtiude;
//        }
//
//        public String getLatitude() {
//            return latitude;
//        }
//
//        public void setLatitude(String latitude) {
//            this.latitude = latitude;
//        }
//    }
//
//    static class CallRecord {
//        /**
//         * 通话时间 string 是 未开启权限或者无通话记录可能为空
//         */
//        private String dialTime;
//        /**
//         * 通话类型 string 是 来电 in, 拨出 out, 未接 miss
//         */
//        private String dialType;
//        /**
//         * 通话时长（秒) string 是
//         */
//        private String duration;
//        /**
//         * 姓名 string 是
//         */
//        private String name;
//        /**
//         * simId string 是
//         */
//        private String simID;
//        /**
//         * 拨出电话 string 是
//         */
//        private String targetPhone;
//
//        public String getDialTime() {
//            return dialTime;
//        }
//
//        public void setDialTime(String dialTime) {
//            this.dialTime = dialTime;
//        }
//
//        public String getDialType() {
//            return dialType;
//        }
//
//        public void setDialType(String dialType) {
//            this.dialType = dialType;
//        }
//
//        public String getDuration() {
//            return duration;
//        }
//
//        public void setDuration(String duration) {
//            this.duration = duration;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getSimID() {
//            return simID;
//        }
//
//        public void setSimID(String simID) {
//            this.simID = simID;
//        }
//
//        public String getTargetPhone() {
//            return targetPhone;
//        }
//
//        public void setTargetPhone(String targetPhone) {
//            this.targetPhone = targetPhone;
//        }
//    }
//
//    @Table(name = "bw_gnh_app")
//    public static class App {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
//        private Long id;
//
//        private Date gmtCreate;
//
//        private Date gmtModified;
//
//        private Long orderId;
//
//        /**
//         * 应用名称 string 是 用户未安装任何应用会缺失
//         */
//        private String applicationName;
//        /**
//         * 包名称 string 是
//         */
//        private String packageName;
//        /**
//         * 版本号 string 是
//         */
//        private String version;
//        /**
//         * 安装时间 string 是
//         */
//        private String installTime;
//        /**
//         * 更新时间 string 是
//         */
//        private String updateTime;
//
//        public Long getId() {
//            return id;
//        }
//
//        public void setId(Long id) {
//            this.id = id;
//        }
//
//        public Date getGmtCreate() {
//            return gmtCreate;
//        }
//
//        public void setGmtCreate(Date gmtCreate) {
//            this.gmtCreate = gmtCreate;
//        }
//
//        public Date getGmtModified() {
//            return gmtModified;
//        }
//
//        public void setGmtModified(Date gmtModified) {
//            this.gmtModified = gmtModified;
//        }
//
//        public Long getOrderId() {
//            return orderId;
//        }
//
//        public void setOrderId(Long orderId) {
//            this.orderId = orderId;
//        }
//
//        public String getApplicationName() {
//            return applicationName;
//        }
//
//        public void setApplicationName(String applicationName) {
//            this.applicationName = applicationName;
//        }
//
//        public String getPackageName() {
//            return packageName;
//        }
//
//        public void setPackageName(String packageName) {
//            this.packageName = packageName;
//        }
//
//        public String getVersion() {
//            return version;
//        }
//
//        public void setVersion(String version) {
//            this.version = version;
//        }
//
//        public String getInstallTime() {
//            return installTime;
//        }
//
//        public void setInstallTime(String installTime) {
//            this.installTime = installTime;
//        }
//
//        public String getUpdateTime() {
//            return updateTime;
//        }
//
//        public void setUpdateTime(String updateTime) {
//            this.updateTime = updateTime;
//        }
//    }
//}
