// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kaniu_deviceinfo1")
// public class BwKaniuDeviceinfo1 implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;//
// private String accuracy;// 定位精度（水平）
// private String activetime;// 从开机到目前的毫秒数不包括休眠时间
// private String allowmocklocation;// 是否允许位置模拟 true 允许 fase 不允许
// private String altitude;// 海拔
// private String altitudeaccuracy;// 海拔精度（垂直）
// private String apkversion;// 应用版本号 卡牛或者随手记的产品版本号
// private String appos;// 设备类型如：“android”
// private String availablememory;// 可用内存字节数
// private String availablestorage;// 可用存储空间字节数
// private String basebandversion;// 基带版本
// private String batterylevel;// 电池电量 百分比
// private String batterystatus;// 当前充电状态 0未知 1未充电 2正在充电
// private String bluemac;// 蓝牙MAC地址
// private String boottime;// 开机时刻的时间戳(毫秒)
// private String brand;// 获取设备品牌
// private String brightness;// 当前屏幕亮度 单位 cd/m2
// private String bulename;// 蓝牙名称
// private String bundleid;// 应用的BundleId
// private String businesscode;// 业务产品
// private String cellip;// 2G/3G/4G网络连接的本地IP地址
// private String city;// 城市
// private String country;// 区县
// private String currenttime;// 采集时的当前时间戳(毫秒)
// private String deviceid;// 设备ID
// private String devicename;// 出厂时的设备名称
// private String devicesv;// 设备的软件版本号
// private String diskmemory;// 磁盘容量 （单位：kb）
// private String factorytime;// 出厂时间
// private String fingerprint;// 生成指纹唯一标示
// private String fpversion;// 集成sdk版本号
// private String gpslocation;// 当前GPS坐标
// private String idfa;// 广告追踪标示符 ios才有
// private String idfv;// 厂商追踪标示符 ios 才有
// private String isdebug;// 手机是否调试状态 true 调试状态 fase 非调试状态
// private String isemulator;// 是否模拟器 true 是 fase 启
// private String isusb;// 是否开启usb调试 true 开启 fase 未开启
// private String isvpn;// 是否使用vpn true 是 fase 启
// private String language;// 当前配置的语言
// private String latitude;// 客户LBS 纬度
// private String longitude;// 客户LBS 经度
// private String model;// 获取手机的型号设备名称
// private String networktype;// 当前使用的网络连接类型
// private String os;// 操作系统的类型
// private String osversion;// 操作系统发行版本
// private String ownername;// 设备所有者名称
// private String packagename;// 应用包名 卡牛或者随手记的产品名称
// private String pixelx;// 屏幕x方向每英寸像素点数
// private String pixely;// 屏幕y方向每英寸像素点数
// private String province;// 省份
// private String root;// 是否ROOT true 是 fase 启
// private String screenheight;// 屏幕分辨率长度 单位 mm
// private String screenwidth;// 屏幕分辨率宽度 单位 mm
// private String ssid;// 当前连接的无线网络名称
// private String street;// 详细街道
// private String streetnumber;// 门牌号
// private String timezone;// 当前时区
// private String tokenid;// 采集请求唯一标示
// private String totalmemory;// 总内存字节数
// private String totalstorage;// 总存储空间字节数
// private String transducer;// 电容传感器
// private String trueip;// sdk采集的IP地址
// private String udid;// deviceudid取不到时使用自己生成的id（固定不变））的md5值
// private String uptime;// 从开机到目前的毫秒数包括休眠时间
// private String wifiip;// 当前连接的无线网络的本地IP地址
// private String wifimac;// 无线网卡的mac地址
// private String jailbreak;// 是否越狱true 是 fase 启
// private String havephoto;// 是否有摄像头 true 是 fase 启
// private String virtualmachine;// 是否虚拟机 true 是 fase 启
// private String helicalaccelerator;// 螺旋加速器
// private Date createTime;//
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public Long getId() {
// return id;
// }
//
// public void setOrderId(Long orderId) {
// this.orderId = orderId;
// }
//
// public Long getOrderId() {
// return orderId;
// }
//
// public void setAccuracy(String accuracy) {
// this.accuracy = accuracy;
// }
//
// public String getAccuracy() {
// return accuracy;
// }
//
// public void setActivetime(String activetime) {
// this.activetime = activetime;
// }
//
// public String getActivetime() {
// return activetime;
// }
//
// public void setAllowmocklocation(String allowmocklocation) {
// this.allowmocklocation = allowmocklocation;
// }
//
// public String getAllowmocklocation() {
// return allowmocklocation;
// }
//
// public void setAltitude(String altitude) {
// this.altitude = altitude;
// }
//
// public String getAltitude() {
// return altitude;
// }
//
// public void setAltitudeaccuracy(String altitudeaccuracy) {
// this.altitudeaccuracy = altitudeaccuracy;
// }
//
// public String getAltitudeaccuracy() {
// return altitudeaccuracy;
// }
//
// public void setApkversion(String apkversion) {
// this.apkversion = apkversion;
// }
//
// public String getApkversion() {
// return apkversion;
// }
//
// public void setAppos(String appos) {
// this.appos = appos;
// }
//
// public String getAppos() {
// return appos;
// }
//
// public void setAvailablememory(String availablememory) {
// this.availablememory = availablememory;
// }
//
// public String getAvailablememory() {
// return availablememory;
// }
//
// public void setAvailablestorage(String availablestorage) {
// this.availablestorage = availablestorage;
// }
//
// public String getAvailablestorage() {
// return availablestorage;
// }
//
// public void setBasebandversion(String basebandversion) {
// this.basebandversion = basebandversion;
// }
//
// public String getBasebandversion() {
// return basebandversion;
// }
//
// public void setBatterylevel(String batterylevel) {
// this.batterylevel = batterylevel;
// }
//
// public String getBatterylevel() {
// return batterylevel;
// }
//
// public void setBatterystatus(String batterystatus) {
// this.batterystatus = batterystatus;
// }
//
// public String getBatterystatus() {
// return batterystatus;
// }
//
// public void setBluemac(String bluemac) {
// this.bluemac = bluemac;
// }
//
// public String getBluemac() {
// return bluemac;
// }
//
// public void setBoottime(String boottime) {
// this.boottime = boottime;
// }
//
// public String getBoottime() {
// return boottime;
// }
//
// public void setBrand(String brand) {
// this.brand = brand;
// }
//
// public String getBrand() {
// return brand;
// }
//
// public void setBrightness(String brightness) {
// this.brightness = brightness;
// }
//
// public String getBrightness() {
// return brightness;
// }
//
// public void setBulename(String bulename) {
// this.bulename = bulename;
// }
//
// public String getBulename() {
// return bulename;
// }
//
// public void setBundleid(String bundleid) {
// this.bundleid = bundleid;
// }
//
// public String getBundleid() {
// return bundleid;
// }
//
// public void setBusinesscode(String businesscode) {
// this.businesscode = businesscode;
// }
//
// public String getBusinesscode() {
// return businesscode;
// }
//
// public void setCellip(String cellip) {
// this.cellip = cellip;
// }
//
// public String getCellip() {
// return cellip;
// }
//
// public void setCity(String city) {
// this.city = city;
// }
//
// public String getCity() {
// return city;
// }
//
// public void setCountry(String country) {
// this.country = country;
// }
//
// public String getCountry() {
// return country;
// }
//
// public void setCurrenttime(String currenttime) {
// this.currenttime = currenttime;
// }
//
// public String getCurrenttime() {
// return currenttime;
// }
//
// public void setDeviceid(String deviceid) {
// this.deviceid = deviceid;
// }
//
// public String getDeviceid() {
// return deviceid;
// }
//
// public void setDevicename(String devicename) {
// this.devicename = devicename;
// }
//
// public String getDevicename() {
// return devicename;
// }
//
// public void setDevicesv(String devicesv) {
// this.devicesv = devicesv;
// }
//
// public String getDevicesv() {
// return devicesv;
// }
//
// public void setDiskmemory(String diskmemory) {
// this.diskmemory = diskmemory;
// }
//
// public String getDiskmemory() {
// return diskmemory;
// }
//
// public void setFactorytime(String factorytime) {
// this.factorytime = factorytime;
// }
//
// public String getFactorytime() {
// return factorytime;
// }
//
// public void setFingerprint(String fingerprint) {
// this.fingerprint = fingerprint;
// }
//
// public String getFingerprint() {
// return fingerprint;
// }
//
// public void setFpversion(String fpversion) {
// this.fpversion = fpversion;
// }
//
// public String getFpversion() {
// return fpversion;
// }
//
// public void setGpslocation(String gpslocation) {
// this.gpslocation = gpslocation;
// }
//
// public String getGpslocation() {
// return gpslocation;
// }
//
// public void setIdfa(String idfa) {
// this.idfa = idfa;
// }
//
// public String getIdfa() {
// return idfa;
// }
//
// public void setIdfv(String idfv) {
// this.idfv = idfv;
// }
//
// public String getIdfv() {
// return idfv;
// }
//
// public void setIsdebug(String isdebug) {
// this.isdebug = isdebug;
// }
//
// public String getIsdebug() {
// return isdebug;
// }
//
// public void setIsemulator(String isemulator) {
// this.isemulator = isemulator;
// }
//
// public String getIsemulator() {
// return isemulator;
// }
//
// public void setIsusb(String isusb) {
// this.isusb = isusb;
// }
//
// public String getIsusb() {
// return isusb;
// }
//
// public void setIsvpn(String isvpn) {
// this.isvpn = isvpn;
// }
//
// public String getIsvpn() {
// return isvpn;
// }
//
// public void setLanguage(String language) {
// this.language = language;
// }
//
// public String getLanguage() {
// return language;
// }
//
// public void setLatitude(String latitude) {
// this.latitude = latitude;
// }
//
// public String getLatitude() {
// return latitude;
// }
//
// public void setLongitude(String longitude) {
// this.longitude = longitude;
// }
//
// public String getLongitude() {
// return longitude;
// }
//
// public void setModel(String model) {
// this.model = model;
// }
//
// public String getModel() {
// return model;
// }
//
// public void setNetworktype(String networktype) {
// this.networktype = networktype;
// }
//
// public String getNetworktype() {
// return networktype;
// }
//
// public void setOs(String os) {
// this.os = os;
// }
//
// public String getOs() {
// return os;
// }
//
// public void setOsversion(String osversion) {
// this.osversion = osversion;
// }
//
// public String getOsversion() {
// return osversion;
// }
//
// public void setOwnername(String ownername) {
// this.ownername = ownername;
// }
//
// public String getOwnername() {
// return ownername;
// }
//
// public void setPackagename(String packagename) {
// this.packagename = packagename;
// }
//
// public String getPackagename() {
// return packagename;
// }
//
// public void setPixelx(String pixelx) {
// this.pixelx = pixelx;
// }
//
// public String getPixelx() {
// return pixelx;
// }
//
// public void setPixely(String pixely) {
// this.pixely = pixely;
// }
//
// public String getPixely() {
// return pixely;
// }
//
// public void setProvince(String province) {
// this.province = province;
// }
//
// public String getProvince() {
// return province;
// }
//
// public void setRoot(String root) {
// this.root = root;
// }
//
// public String getRoot() {
// return root;
// }
//
// public void setScreenheight(String screenheight) {
// this.screenheight = screenheight;
// }
//
// public String getScreenheight() {
// return screenheight;
// }
//
// public void setScreenwidth(String screenwidth) {
// this.screenwidth = screenwidth;
// }
//
// public String getScreenwidth() {
// return screenwidth;
// }
//
// public void setSsid(String ssid) {
// this.ssid = ssid;
// }
//
// public String getSsid() {
// return ssid;
// }
//
// public void setStreet(String street) {
// this.street = street;
// }
//
// public String getStreet() {
// return street;
// }
//
// public void setStreetnumber(String streetnumber) {
// this.streetnumber = streetnumber;
// }
//
// public String getStreetnumber() {
// return streetnumber;
// }
//
// public void setTimezone(String timezone) {
// this.timezone = timezone;
// }
//
// public String getTimezone() {
// return timezone;
// }
//
// public void setTokenid(String tokenid) {
// this.tokenid = tokenid;
// }
//
// public String getTokenid() {
// return tokenid;
// }
//
// public void setTotalmemory(String totalmemory) {
// this.totalmemory = totalmemory;
// }
//
// public String getTotalmemory() {
// return totalmemory;
// }
//
// public void setTotalstorage(String totalstorage) {
// this.totalstorage = totalstorage;
// }
//
// public String getTotalstorage() {
// return totalstorage;
// }
//
// public void setTransducer(String transducer) {
// this.transducer = transducer;
// }
//
// public String getTransducer() {
// return transducer;
// }
//
// public void setTrueip(String trueip) {
// this.trueip = trueip;
// }
//
// public String getTrueip() {
// return trueip;
// }
//
// public void setUdid(String udid) {
// this.udid = udid;
// }
//
// public String getUdid() {
// return udid;
// }
//
// public void setUptime(String uptime) {
// this.uptime = uptime;
// }
//
// public String getUptime() {
// return uptime;
// }
//
// public void setWifiip(String wifiip) {
// this.wifiip = wifiip;
// }
//
// public String getWifiip() {
// return wifiip;
// }
//
// public void setWifimac(String wifimac) {
// this.wifimac = wifimac;
// }
//
// public String getWifimac() {
// return wifimac;
// }
//
// public String getJailbreak() {
// return jailbreak;
// }
//
// public void setJailbreak(String jailbreak) {
// this.jailbreak = jailbreak;
// }
//
// public String getHavephoto() {
// return havephoto;
// }
//
// public void setHavephoto(String havephoto) {
// this.havephoto = havephoto;
// }
//
// public String getVirtualmachine() {
// return virtualmachine;
// }
//
// public void setVirtualmachine(String virtualmachine) {
// this.virtualmachine = virtualmachine;
// }
//
// public String getHelicalaccelerator() {
// return helicalaccelerator;
// }
//
// public void setHelicalaccelerator(String helicalaccelerator) {
// this.helicalaccelerator = helicalaccelerator;
// }
//
// public void setCreateTime(Date createTime) {
// this.createTime = createTime;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
// }
