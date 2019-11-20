//package com.waterelephant.sxyDrainage.entity.jdq;
//
//import com.alibaba.fastjson.annotation.JSONField;
//
//import java.util.Date;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Transient;
//
///**
// * @author xanthuim@gmail.com
// * @date 2018/6/5 15:07
// * <p>
// * 设备信息
// */
//@Table(name = "bw_jdq_device_info")
//public class DeviceInfo {
//
//    /**
//     * 设备类型
//     */
//    public enum DeviceType {
//        IOS("IOS"), Android("Android"), Other("其他平台");
//
//        @Transient
//        private String type;
//
//        DeviceType(String type) {
//            this.type = type;
//        }
//
//        public String getType() {
//            return type;
//        }
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
//    private Long id;
//
//    private Long orderId;
//
//    /**
//     * 设备号
//     */
//    @JSONField(name = "device_id")
//    private String deviceId;
//
//    /**
//     * IP地址
//     */
//    private String ip;
//    /**
//     * 设备类型如：IOS或Android
//     */
//    @JSONField(name = "device_type")
//    private String deviceType;
//    /**
//     * 手机设备型号如：vivo 、oppo、huawei等
//     */
//    @JSONField(name = "device_model")
//    private String deviceModel;
//    /**
//     * 设备操作系统版本，如
//     * ios 11.2.6 ，Android 7.0
//     */
//    @JSONField(name = "device_os")
//    private String deviceOs;
//
//    /**
//     * iOS的openudid
//     */
//    private String openudid;
//    /**
//     * iOS 是否越狱
//     */
//    @JSONField(name = "jailbreak_flag")
//    private String jailbreakFlag;
//    /**
//     * Android是否root
//     */
//    @JSONField(name = "root_flag")
//    private String rootFlag;
//    private Date gmtModified;
//    private Date gmtCreate;
//
//    public Date getGmtModified() {
//        return gmtModified;
//    }
//
//    public void setGmtModified(Date gmtModified) {
//        this.gmtModified = gmtModified;
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
//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
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
//    public String getDeviceModel() {
//        return deviceModel;
//    }
//
//    public void setDeviceModel(String deviceModel) {
//        this.deviceModel = deviceModel;
//    }
//
//    public String getDeviceOs() {
//        return deviceOs;
//    }
//
//    public void setDeviceOs(String deviceOs) {
//        this.deviceOs = deviceOs;
//    }
//
//    public String getOpenudid() {
//        return openudid;
//    }
//
//    public void setOpenudid(String openudid) {
//        this.openudid = openudid;
//    }
//
//    public String getJailbreakFlag() {
//        return jailbreakFlag;
//    }
//
//    public void setJailbreakFlag(String jailbreakFlag) {
//        this.jailbreakFlag = jailbreakFlag;
//    }
//
//    public String getRootFlag() {
//        return rootFlag;
//    }
//
//    public void setRootFlag(String rootFlag) {
//        this.rootFlag = rootFlag;
//    }
//}
