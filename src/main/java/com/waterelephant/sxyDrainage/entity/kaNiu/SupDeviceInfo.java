///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.entity.kaNiu;
//
///**
// * 
// * 
// * Module:
// * 
// * SupDeviceInfo.java
// * 
// * @author huangjin
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//public class SupDeviceInfo {
//	private String accuracy;// 定位精度（水平）
//	private String activeTime;// 从开机到目前的毫秒数不包括休眠时间
//	private String allowMockLocation;// allowMockLocation string 是否允许位置模拟 true 允许 fase 不允许
//	private String altitude;// 海拔
//	private String altitudeaccuracy;// 海拔精度（垂直）
//	private String androidId;// 571245754545
//	private String apkMD5;// asdfaerqqwerqwerasdf
//	private String apkVersion;// 应用版本号 卡牛或者随手记的产品版本号
//	private String appNum;// 25
//	private String appOs;// 设备类型如：“android”
//	private String attached;// xss
//	private String availableMemory;// 可用内存字节数
//	private String availableStorage;// 可用存储空间字节数
//	private String basebandVersion;// 基带版本
//	private String batteryLevel;// 电池电量 百分比
//	private String batteryStatus;// 当前充电状态 0未知 1未充电 2正在充电
//	private String batteryTemp;// 35°C
//	private String blueMac;// 蓝牙MAC地址
//	private String bootTime;// 开机时刻的时间戳(毫秒)
//	private String brand;// 获取设备品牌
//	private String brightness;// 当前屏幕亮度 单位 cd/m2
//	private String bssid;// ssj00000
//	private String buleName;// 蓝牙名称
//	private String bundleId;// 应用的BundleId
//	private String businessCode;// 业务产品
//	private String cellIp;// 2G/3G/4G网络连接的本地IP地址
//	private String city;// 城市
//	private String country;// 区县
//	private String cpuAbi;// arm64-v8a
//	private String cpuAbi2;// arm64-v8a
//	private String cpuFrequency;// 5 GHZ
//	private String cpuHardware;// x86
//	private String cpuType;// MT6592
//	private String createTime;// 2018-05-08
//	private String currentTime;// 采集时的当前时间戳(毫秒)
//	private String deviceDrive;// hwG750-T01
//	private String deviceId;// 设备ID
//	private String deviceIp;// 127.0.0.1
//	private String deviceName;// 出厂时的设备名称
//	private String deviceSV;// 设备的软件版本号
//	private String deviceUA;// asdfasdf
//	private String diskMemory;// 磁盘容量 （单位：kb）
//	private String display;// asdfasd
//	private String dnsAddress;// 9.9.9.9
//	private String env;// location.dll
//	private String factoryTime;// 出厂时间
//	private String fingerPrint;// 生成指纹唯一标示
//	private String fnam;// u_pge1l@kd.ssj
//	private String fontHash;// asdfasdfasdfasdfasdf
//	private String fpVersion;// 集成sdk版本号
//	private String gateway;// 172.0.0.1
//	private String gpsLocation;// 当前GPS坐标
//	private String hardware;// mt6592
//	private String havePhoto;// true
//	private String helicalAccelerator;// aer_ad
//	private String host;// 127.0.0.1
//	private String iccid;//
//	private String id;// 1
//	private String idfa;// 广告追踪标示符 ios才有
//	private String idfv;// 厂商追踪标示符 ios 才有
//	private String isDebug;// 手机是否调试状态 true 调试状态 fase 非调试状态
//	private String isEmulator;// 是否模拟器 true 是 fase 启
//	private String isUsb;// 是否开启usb调试 true 开启 fase 未开启
//	private String isVpn;// 是否使用vpn true 是 fase 启
//	private String jailBreak;// true
//	private String kernelVersion;// centos
//	private String language;// 当前配置的语言
//	private String latitude;// 客户LBS 纬度
//	private String longitude;// 客户LBS 经度
//	private String model;// 获取手机的型号设备名称
//	private String networkType;// 当前使用的网络连接类型
//	private String operateCode;// smrz
//	private String os;// 操作系统的类型
//	private String osVersion;// 操作系统发行版本
//	private String ownerName;// 设备所有者名称
//	private String packageName;// 应用包名 卡牛或者随手记的产品名称
//	private String partner;// 360
//	private String pixelX;// 屏幕x方向每英寸像素点数
//	private String pixelY;// 屏幕y方向每英寸像素点数
//	private String product;// G750-T01
//	private String province;// 省份
//	private String proxyInfo;// 127.0.0.1
//	private String proxyType;// http
//	private String proxyUrl;// http:// 172.10.44.11:8080/proxy
//	private String root;// 是否ROOT true 是 fase 启
//	private String screenHeight;// 屏幕分辨率长度 单位 mm
//	private String screenWidth;// 屏幕分辨率宽度 单位 mm
//	private String sdkMD5;// asdfqwerae
//	private String sdkVersion;//
//	private String serialNo;// YGKBBBB5C1711949
//	private String signMD5;// 1575765756 asdfasdf
//	private String ssid;// 当前连接的无线网络名称
//	private String street;// 详细街道
//	private String streetNumber;// streetNumber string 门牌号
//	private String tags;// release-keys
//	private String timeZone;// 当前时区
//	private String tmp1;//
//	private String tmp2;//
//	private String tmp3;//
//	private String tmp4;//
//	private String tokenId; // 采集请求唯一标示
//	private String totalMemory;// 总内存字节数
//	private String totalStorage;// totalStorage string 总存储空间字节数
//	private String transducer;// 电容传感器
//	private String trueIp;// sdk采集的IP地址
//	private String udid;// deviceudid取不到时使用自己生成的id（固定不变））的md5值
//	private String uid;// 2015474
//	private String upTime;// 从开机到目前的毫秒数包括休眠时间
//	private String updateTime;// 2018-07-05
//	private String userId;// 25277915
//	private String user_id;// 2018050900116585010
//	private String virtualMachine;// true
//	private String vpnIp;// 127.0.0.1
//	private String vpnNetmask;// 0.0.0.0
//	private String wifiIp;// 当前连接的无线网络的本地IP地址
//	private String wifiMac;// 无线网卡的mac地址
//	private String wifiNetmask;// 0.0.0.0
//	// private String jail_break; // 是否越狱 true 是 fase 启
//	// private String have_photo; // 是否有摄像头 true 是 fase 启
//	// private String virtual_machine; // 是否虚拟机 true 是 fase 启
//	// private String helical_accelerator; // 螺旋加速器
//
//	public String getAccuracy() {
//		return accuracy;
//	}
//	//
//	// public String getJail_break() {
//	// return jail_break;
//	// }
//	//
//	// public void setJail_break(String jail_break) {
//	// this.jail_break = jail_break;
//	// }
//	//
//	// public String getHave_photo() {
//	// return have_photo;
//	// }
//	//
//	// public void setHave_photo(String have_photo) {
//	// this.have_photo = have_photo;
//	// }
//	//
//	// public String getVirtual_machine() {
//	// return virtual_machine;
//	// }
//	//
//	// public void setVirtual_machine(String virtual_machine) {
//	// this.virtual_machine = virtual_machine;
//	// }
//	//
//	// public String getHelical_accelerator() {
//	// return helical_accelerator;
//	// }
//	//
//	// public void setHelical_accelerator(String helical_accelerator) {
//	// this.helical_accelerator = helical_accelerator;
//	// }
//
//	public void setAccuracy(String accuracy) {
//		this.accuracy = accuracy;
//	}
//
//	public String getActiveTime() {
//		return activeTime;
//	}
//
//	public void setActiveTime(String activeTime) {
//		this.activeTime = activeTime;
//	}
//
//	public String getAllowMockLocation() {
//		return allowMockLocation;
//	}
//
//	public void setAllowMockLocation(String allowMockLocation) {
//		this.allowMockLocation = allowMockLocation;
//	}
//
//	public String getAltitude() {
//		return altitude;
//	}
//
//	public void setAltitude(String altitude) {
//		this.altitude = altitude;
//	}
//
//	public String getAltitudeaccuracy() {
//		return altitudeaccuracy;
//	}
//
//	public void setAltitudeaccuracy(String altitudeaccuracy) {
//		this.altitudeaccuracy = altitudeaccuracy;
//	}
//
//	public String getAndroidId() {
//		return androidId;
//	}
//
//	public void setAndroidId(String androidId) {
//		this.androidId = androidId;
//	}
//
//	public String getApkMD5() {
//		return apkMD5;
//	}
//
//	public void setApkMD5(String apkMD5) {
//		this.apkMD5 = apkMD5;
//	}
//
//	public String getApkVersion() {
//		return apkVersion;
//	}
//
//	public void setApkVersion(String apkVersion) {
//		this.apkVersion = apkVersion;
//	}
//
//	public String getAppNum() {
//		return appNum;
//	}
//
//	public void setAppNum(String appNum) {
//		this.appNum = appNum;
//	}
//
//	public String getAppOs() {
//		return appOs;
//	}
//
//	public void setAppOs(String appOs) {
//		this.appOs = appOs;
//	}
//
//	public String getAttached() {
//		return attached;
//	}
//
//	public void setAttached(String attached) {
//		this.attached = attached;
//	}
//
//	public String getAvailableMemory() {
//		return availableMemory;
//	}
//
//	public void setAvailableMemory(String availableMemory) {
//		this.availableMemory = availableMemory;
//	}
//
//	public String getAvailableStorage() {
//		return availableStorage;
//	}
//
//	public void setAvailableStorage(String availableStorage) {
//		this.availableStorage = availableStorage;
//	}
//
//	public String getBasebandVersion() {
//		return basebandVersion;
//	}
//
//	public void setBasebandVersion(String basebandVersion) {
//		this.basebandVersion = basebandVersion;
//	}
//
//	public String getBatteryLevel() {
//		return batteryLevel;
//	}
//
//	public void setBatteryLevel(String batteryLevel) {
//		this.batteryLevel = batteryLevel;
//	}
//
//	public String getBatteryStatus() {
//		return batteryStatus;
//	}
//
//	public void setBatteryStatus(String batteryStatus) {
//		this.batteryStatus = batteryStatus;
//	}
//
//	public String getBatteryTemp() {
//		return batteryTemp;
//	}
//
//	public void setBatteryTemp(String batteryTemp) {
//		this.batteryTemp = batteryTemp;
//	}
//
//	public String getBlueMac() {
//		return blueMac;
//	}
//
//	public void setBlueMac(String blueMac) {
//		this.blueMac = blueMac;
//	}
//
//	public String getBootTime() {
//		return bootTime;
//	}
//
//	public void setBootTime(String bootTime) {
//		this.bootTime = bootTime;
//	}
//
//	public String getBrand() {
//		return brand;
//	}
//
//	public void setBrand(String brand) {
//		this.brand = brand;
//	}
//
//	public String getBrightness() {
//		return brightness;
//	}
//
//	public void setBrightness(String brightness) {
//		this.brightness = brightness;
//	}
//
//	public String getBssid() {
//		return bssid;
//	}
//
//	public void setBssid(String bssid) {
//		this.bssid = bssid;
//	}
//
//	public String getBuleName() {
//		return buleName;
//	}
//
//	public void setBuleName(String buleName) {
//		this.buleName = buleName;
//	}
//
//	public String getBundleId() {
//		return bundleId;
//	}
//
//	public void setBundleId(String bundleId) {
//		this.bundleId = bundleId;
//	}
//
//	public String getBusinessCode() {
//		return businessCode;
//	}
//
//	public void setBusinessCode(String businessCode) {
//		this.businessCode = businessCode;
//	}
//
//	public String getCellIp() {
//		return cellIp;
//	}
//
//	public void setCellIp(String cellIp) {
//		this.cellIp = cellIp;
//	}
//
//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public String getCountry() {
//		return country;
//	}
//
//	public void setCountry(String country) {
//		this.country = country;
//	}
//
//	public String getCpuAbi() {
//		return cpuAbi;
//	}
//
//	public void setCpuAbi(String cpuAbi) {
//		this.cpuAbi = cpuAbi;
//	}
//
//	public String getCpuAbi2() {
//		return cpuAbi2;
//	}
//
//	public void setCpuAbi2(String cpuAbi2) {
//		this.cpuAbi2 = cpuAbi2;
//	}
//
//	public String getCpuFrequency() {
//		return cpuFrequency;
//	}
//
//	public void setCpuFrequency(String cpuFrequency) {
//		this.cpuFrequency = cpuFrequency;
//	}
//
//	public String getCpuHardware() {
//		return cpuHardware;
//	}
//
//	public void setCpuHardware(String cpuHardware) {
//		this.cpuHardware = cpuHardware;
//	}
//
//	public String getCpuType() {
//		return cpuType;
//	}
//
//	public void setCpuType(String cpuType) {
//		this.cpuType = cpuType;
//	}
//
//	public String getCreateTime() {
//		return createTime;
//	}
//
//	public void setCreateTime(String createTime) {
//		this.createTime = createTime;
//	}
//
//	public String getCurrentTime() {
//		return currentTime;
//	}
//
//	public void setCurrentTime(String currentTime) {
//		this.currentTime = currentTime;
//	}
//
//	public String getDeviceDrive() {
//		return deviceDrive;
//	}
//
//	public void setDeviceDrive(String deviceDrive) {
//		this.deviceDrive = deviceDrive;
//	}
//
//	public String getDeviceId() {
//		return deviceId;
//	}
//
//	public void setDeviceId(String deviceId) {
//		this.deviceId = deviceId;
//	}
//
//	public String getDeviceIp() {
//		return deviceIp;
//	}
//
//	public void setDeviceIp(String deviceIp) {
//		this.deviceIp = deviceIp;
//	}
//
//	public String getDeviceName() {
//		return deviceName;
//	}
//
//	public void setDeviceName(String deviceName) {
//		this.deviceName = deviceName;
//	}
//
//	public String getDeviceSV() {
//		return deviceSV;
//	}
//
//	public void setDeviceSV(String deviceSV) {
//		this.deviceSV = deviceSV;
//	}
//
//	public String getDeviceUA() {
//		return deviceUA;
//	}
//
//	public void setDeviceUA(String deviceUA) {
//		this.deviceUA = deviceUA;
//	}
//
//	public String getDiskMemory() {
//		return diskMemory;
//	}
//
//	public void setDiskMemory(String diskMemory) {
//		this.diskMemory = diskMemory;
//	}
//
//	public String getDisplay() {
//		return display;
//	}
//
//	public void setDisplay(String display) {
//		this.display = display;
//	}
//
//	public String getDnsAddress() {
//		return dnsAddress;
//	}
//
//	public void setDnsAddress(String dnsAddress) {
//		this.dnsAddress = dnsAddress;
//	}
//
//	public String getEnv() {
//		return env;
//	}
//
//	public void setEnv(String env) {
//		this.env = env;
//	}
//
//	public String getFactoryTime() {
//		return factoryTime;
//	}
//
//	public void setFactoryTime(String factoryTime) {
//		this.factoryTime = factoryTime;
//	}
//
//	public String getFingerPrint() {
//		return fingerPrint;
//	}
//
//	public void setFingerPrint(String fingerPrint) {
//		this.fingerPrint = fingerPrint;
//	}
//
//	public String getFnam() {
//		return fnam;
//	}
//
//	public void setFnam(String fnam) {
//		this.fnam = fnam;
//	}
//
//	public String getFontHash() {
//		return fontHash;
//	}
//
//	public void setFontHash(String fontHash) {
//		this.fontHash = fontHash;
//	}
//
//	public String getFpVersion() {
//		return fpVersion;
//	}
//
//	public void setFpVersion(String fpVersion) {
//		this.fpVersion = fpVersion;
//	}
//
//	public String getGateway() {
//		return gateway;
//	}
//
//	public void setGateway(String gateway) {
//		this.gateway = gateway;
//	}
//
//	public String getGpsLocation() {
//		return gpsLocation;
//	}
//
//	public void setGpsLocation(String gpsLocation) {
//		this.gpsLocation = gpsLocation;
//	}
//
//	public String getHardware() {
//		return hardware;
//	}
//
//	public void setHardware(String hardware) {
//		this.hardware = hardware;
//	}
//
//	public String getHavePhoto() {
//		return havePhoto;
//	}
//
//	public void setHavePhoto(String havePhoto) {
//		this.havePhoto = havePhoto;
//	}
//
//	public String getHelicalAccelerator() {
//		return helicalAccelerator;
//	}
//
//	public void setHelicalAccelerator(String helicalAccelerator) {
//		this.helicalAccelerator = helicalAccelerator;
//	}
//
//	public String getHost() {
//		return host;
//	}
//
//	public void setHost(String host) {
//		this.host = host;
//	}
//
//	public String getIccid() {
//		return iccid;
//	}
//
//	public void setIccid(String iccid) {
//		this.iccid = iccid;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getIdfa() {
//		return idfa;
//	}
//
//	public void setIdfa(String idfa) {
//		this.idfa = idfa;
//	}
//
//	public String getIdfv() {
//		return idfv;
//	}
//
//	public void setIdfv(String idfv) {
//		this.idfv = idfv;
//	}
//
//	public String getIsDebug() {
//		return isDebug;
//	}
//
//	public void setIsDebug(String isDebug) {
//		this.isDebug = isDebug;
//	}
//
//	public String getIsEmulator() {
//		return isEmulator;
//	}
//
//	public void setIsEmulator(String isEmulator) {
//		this.isEmulator = isEmulator;
//	}
//
//	public String getIsUsb() {
//		return isUsb;
//	}
//
//	public void setIsUsb(String isUsb) {
//		this.isUsb = isUsb;
//	}
//
//	public String getIsVpn() {
//		return isVpn;
//	}
//
//	public void setIsVpn(String isVpn) {
//		this.isVpn = isVpn;
//	}
//
//	public String getJailBreak() {
//		return jailBreak;
//	}
//
//	public void setJailBreak(String jailBreak) {
//		this.jailBreak = jailBreak;
//	}
//
//	public String getKernelVersion() {
//		return kernelVersion;
//	}
//
//	public void setKernelVersion(String kernelVersion) {
//		this.kernelVersion = kernelVersion;
//	}
//
//	public String getLanguage() {
//		return language;
//	}
//
//	public void setLanguage(String language) {
//		this.language = language;
//	}
//
//	public String getLatitude() {
//		return latitude;
//	}
//
//	public void setLatitude(String latitude) {
//		this.latitude = latitude;
//	}
//
//	public String getLongitude() {
//		return longitude;
//	}
//
//	public void setLongitude(String longitude) {
//		this.longitude = longitude;
//	}
//
//	public String getModel() {
//		return model;
//	}
//
//	public void setModel(String model) {
//		this.model = model;
//	}
//
//	public String getNetworkType() {
//		return networkType;
//	}
//
//	public void setNetworkType(String networkType) {
//		this.networkType = networkType;
//	}
//
//	public String getOperateCode() {
//		return operateCode;
//	}
//
//	public void setOperateCode(String operateCode) {
//		this.operateCode = operateCode;
//	}
//
//	public String getOs() {
//		return os;
//	}
//
//	public void setOs(String os) {
//		this.os = os;
//	}
//
//	public String getOsVersion() {
//		return osVersion;
//	}
//
//	public void setOsVersion(String osVersion) {
//		this.osVersion = osVersion;
//	}
//
//	public String getOwnerName() {
//		return ownerName;
//	}
//
//	public void setOwnerName(String ownerName) {
//		this.ownerName = ownerName;
//	}
//
//	public String getPackageName() {
//		return packageName;
//	}
//
//	public void setPackageName(String packageName) {
//		this.packageName = packageName;
//	}
//
//	public String getPartner() {
//		return partner;
//	}
//
//	public void setPartner(String partner) {
//		this.partner = partner;
//	}
//
//	public String getPixelX() {
//		return pixelX;
//	}
//
//	public void setPixelX(String pixelX) {
//		this.pixelX = pixelX;
//	}
//
//	public String getPixelY() {
//		return pixelY;
//	}
//
//	public void setPixelY(String pixelY) {
//		this.pixelY = pixelY;
//	}
//
//	public String getProduct() {
//		return product;
//	}
//
//	public void setProduct(String product) {
//		this.product = product;
//	}
//
//	public String getProvince() {
//		return province;
//	}
//
//	public void setProvince(String province) {
//		this.province = province;
//	}
//
//	public String getProxyInfo() {
//		return proxyInfo;
//	}
//
//	public void setProxyInfo(String proxyInfo) {
//		this.proxyInfo = proxyInfo;
//	}
//
//	public String getProxyType() {
//		return proxyType;
//	}
//
//	public void setProxyType(String proxyType) {
//		this.proxyType = proxyType;
//	}
//
//	public String getProxyUrl() {
//		return proxyUrl;
//	}
//
//	public void setProxyUrl(String proxyUrl) {
//		this.proxyUrl = proxyUrl;
//	}
//
//	public String getRoot() {
//		return root;
//	}
//
//	public void setRoot(String root) {
//		this.root = root;
//	}
//
//	public String getScreenHeight() {
//		return screenHeight;
//	}
//
//	public void setScreenHeight(String screenHeight) {
//		this.screenHeight = screenHeight;
//	}
//
//	public String getScreenWidth() {
//		return screenWidth;
//	}
//
//	public void setScreenWidth(String screenWidth) {
//		this.screenWidth = screenWidth;
//	}
//
//	public String getSdkMD5() {
//		return sdkMD5;
//	}
//
//	public void setSdkMD5(String sdkMD5) {
//		this.sdkMD5 = sdkMD5;
//	}
//
//	public String getSdkVersion() {
//		return sdkVersion;
//	}
//
//	public void setSdkVersion(String sdkVersion) {
//		this.sdkVersion = sdkVersion;
//	}
//
//	public String getSerialNo() {
//		return serialNo;
//	}
//
//	public void setSerialNo(String serialNo) {
//		this.serialNo = serialNo;
//	}
//
//	public String getSignMD5() {
//		return signMD5;
//	}
//
//	public void setSignMD5(String signMD5) {
//		this.signMD5 = signMD5;
//	}
//
//	public String getSsid() {
//		return ssid;
//	}
//
//	public void setSsid(String ssid) {
//		this.ssid = ssid;
//	}
//
//	public String getStreet() {
//		return street;
//	}
//
//	public void setStreet(String street) {
//		this.street = street;
//	}
//
//	public String getStreetNumber() {
//		return streetNumber;
//	}
//
//	public void setStreetNumber(String streetNumber) {
//		this.streetNumber = streetNumber;
//	}
//
//	public String getTags() {
//		return tags;
//	}
//
//	public void setTags(String tags) {
//		this.tags = tags;
//	}
//
//	public String getTimeZone() {
//		return timeZone;
//	}
//
//	public void setTimeZone(String timeZone) {
//		this.timeZone = timeZone;
//	}
//
//	public String getTmp1() {
//		return tmp1;
//	}
//
//	public void setTmp1(String tmp1) {
//		this.tmp1 = tmp1;
//	}
//
//	public String getTmp2() {
//		return tmp2;
//	}
//
//	public void setTmp2(String tmp2) {
//		this.tmp2 = tmp2;
//	}
//
//	public String getTmp3() {
//		return tmp3;
//	}
//
//	public void setTmp3(String tmp3) {
//		this.tmp3 = tmp3;
//	}
//
//	public String getTmp4() {
//		return tmp4;
//	}
//
//	public void setTmp4(String tmp4) {
//		this.tmp4 = tmp4;
//	}
//
//	public String getTokenId() {
//		return tokenId;
//	}
//
//	public void setTokenId(String tokenId) {
//		this.tokenId = tokenId;
//	}
//
//	public String getTotalMemory() {
//		return totalMemory;
//	}
//
//	public void setTotalMemory(String totalMemory) {
//		this.totalMemory = totalMemory;
//	}
//
//	public String getTotalStorage() {
//		return totalStorage;
//	}
//
//	public void setTotalStorage(String totalStorage) {
//		this.totalStorage = totalStorage;
//	}
//
//	public String getTransducer() {
//		return transducer;
//	}
//
//	public void setTransducer(String transducer) {
//		this.transducer = transducer;
//	}
//
//	public String getTrueIp() {
//		return trueIp;
//	}
//
//	public void setTrueIp(String trueIp) {
//		this.trueIp = trueIp;
//	}
//
//	public String getUdid() {
//		return udid;
//	}
//
//	public void setUdid(String udid) {
//		this.udid = udid;
//	}
//
//	public String getUid() {
//		return uid;
//	}
//
//	public void setUid(String uid) {
//		this.uid = uid;
//	}
//
//	public String getUpTime() {
//		return upTime;
//	}
//
//	public void setUpTime(String upTime) {
//		this.upTime = upTime;
//	}
//
//	public String getUpdateTime() {
//		return updateTime;
//	}
//
//	public void setUpdateTime(String updateTime) {
//		this.updateTime = updateTime;
//	}
//
//	public String getUserId() {
//		return userId;
//	}
//
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
//
//	public String getUser_id() {
//		return user_id;
//	}
//
//	public void setUser_id(String user_id) {
//		this.user_id = user_id;
//	}
//
//	public String getVirtualMachine() {
//		return virtualMachine;
//	}
//
//	public void setVirtualMachine(String virtualMachine) {
//		this.virtualMachine = virtualMachine;
//	}
//
//	public String getVpnIp() {
//		return vpnIp;
//	}
//
//	public void setVpnIp(String vpnIp) {
//		this.vpnIp = vpnIp;
//	}
//
//	public String getVpnNetmask() {
//		return vpnNetmask;
//	}
//
//	public void setVpnNetmask(String vpnNetmask) {
//		this.vpnNetmask = vpnNetmask;
//	}
//
//	public String getWifiIp() {
//		return wifiIp;
//	}
//
//	public void setWifiIp(String wifiIp) {
//		this.wifiIp = wifiIp;
//	}
//
//	public String getWifiMac() {
//		return wifiMac;
//	}
//
//	public void setWifiMac(String wifiMac) {
//		this.wifiMac = wifiMac;
//	}
//
//	public String getWifiNetmask() {
//		return wifiNetmask;
//	}
//
//	public void setWifiNetmask(String wifiNetmask) {
//		this.wifiNetmask = wifiNetmask;
//	}
//
//}
