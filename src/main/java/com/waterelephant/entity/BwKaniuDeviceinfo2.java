// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kaniu_deviceinfo2")
// public class BwKaniuDeviceinfo2 implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;//
// private String cpuabi;//
// private String appnum;//
// private String attached;//
// private String batterytemp;//
// private String bssid;//
// private String cputype;//
// private String cpuhardware;//
// private String cpufrequency;//
// private String cpuabi2;//
// private String createtime;//
// private String devicedrive;//
// private String deviceip;//
// private String deviceua;//
// private String env;//
// private String display;//
// private String dnsaddress;//
// private String fonthash;//
// private String fnam;//
// private String gateway;//
// private String hardware;//
// // private String helicalaccelerator;//
// // private String havephoto;//
// private String host;//
// private String did;//
// private String iccid;//
// private String androidid;//
// private String apkmd5;//
// private Date createTime;//
// private String updatetime;//
// private String userid;//
// private String userId;//
// // private String virtualmachine;//
// private String uid;//
// private String tmp1;//
// private String tmp2;//
// private String tmp3;//
// private String tmp4;//
// private String tags;//
// private String sdkmd5;//
// private String sdkversion;//
// private String serialno;//
// private String signmd5;//
// private String proxyinfo;//
// private String proxytype;//
// private String proxyurl;//
// private String product;//
// private String partner;//
// // private String jailbreak;//
// private String kernelversion;//
// private String operatecode;//
// private String vpnip;//
// private String wifinetmask;//
// private String vpnnetmask;//
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
// public void setCpuabi(String cpuabi) {
// this.cpuabi = cpuabi;
// }
//
// public String getCpuabi() {
// return cpuabi;
// }
//
// public void setAppnum(String appnum) {
// this.appnum = appnum;
// }
//
// public String getAppnum() {
// return appnum;
// }
//
// public void setAttached(String attached) {
// this.attached = attached;
// }
//
// public String getAttached() {
// return attached;
// }
//
// public void setBatterytemp(String batterytemp) {
// this.batterytemp = batterytemp;
// }
//
// public String getBatterytemp() {
// return batterytemp;
// }
//
// public void setBssid(String bssid) {
// this.bssid = bssid;
// }
//
// public String getBssid() {
// return bssid;
// }
//
// public void setCputype(String cputype) {
// this.cputype = cputype;
// }
//
// public String getCputype() {
// return cputype;
// }
//
// public void setCpuhardware(String cpuhardware) {
// this.cpuhardware = cpuhardware;
// }
//
// public String getCpuhardware() {
// return cpuhardware;
// }
//
// public void setCpufrequency(String cpufrequency) {
// this.cpufrequency = cpufrequency;
// }
//
// public String getCpufrequency() {
// return cpufrequency;
// }
//
// public void setCpuabi2(String cpuabi2) {
// this.cpuabi2 = cpuabi2;
// }
//
// public String getCpuabi2() {
// return cpuabi2;
// }
//
// public void setCreatetime(String createtime) {
// this.createtime = createtime;
// }
//
// public String getCreatetime() {
// return createtime;
// }
//
// public void setDevicedrive(String devicedrive) {
// this.devicedrive = devicedrive;
// }
//
// public String getDevicedrive() {
// return devicedrive;
// }
//
// public void setDeviceip(String deviceip) {
// this.deviceip = deviceip;
// }
//
// public String getDeviceip() {
// return deviceip;
// }
//
// public void setDeviceua(String deviceua) {
// this.deviceua = deviceua;
// }
//
// public String getDeviceua() {
// return deviceua;
// }
//
// public void setEnv(String env) {
// this.env = env;
// }
//
// public String getEnv() {
// return env;
// }
//
// public void setDisplay(String display) {
// this.display = display;
// }
//
// public String getDisplay() {
// return display;
// }
//
// public void setDnsaddress(String dnsaddress) {
// this.dnsaddress = dnsaddress;
// }
//
// public String getDnsaddress() {
// return dnsaddress;
// }
//
// public void setFonthash(String fonthash) {
// this.fonthash = fonthash;
// }
//
// public String getFonthash() {
// return fonthash;
// }
//
// public void setFnam(String fnam) {
// this.fnam = fnam;
// }
//
// public String getFnam() {
// return fnam;
// }
//
// public void setGateway(String gateway) {
// this.gateway = gateway;
// }
//
// public String getGateway() {
// return gateway;
// }
//
// public void setHardware(String hardware) {
// this.hardware = hardware;
// }
//
// public String getHardware() {
// return hardware;
// }
//
// // public void setHelicalaccelerator(String helicalaccelerator) {
// // this.helicalaccelerator = helicalaccelerator;
// // }
// //
// // public String getHelicalaccelerator() {
// // return helicalaccelerator;
// // }
// //
// // public void setHavephoto(String havephoto) {
// // this.havephoto = havephoto;
// // }
// //
// // public String getHavephoto() {
// // return havephoto;
// // }
//
// public void setHost(String host) {
// this.host = host;
// }
//
// public String getHost() {
// return host;
// }
//
// public void setDid(String did) {
// this.did = did;
// }
//
// public String getDid() {
// return did;
// }
//
// public void setIccid(String iccid) {
// this.iccid = iccid;
// }
//
// public String getIccid() {
// return iccid;
// }
//
// public void setAndroidid(String androidid) {
// this.androidid = androidid;
// }
//
// public String getAndroidid() {
// return androidid;
// }
//
// public void setApkmd5(String apkmd5) {
// this.apkmd5 = apkmd5;
// }
//
// public String getApkmd5() {
// return apkmd5;
// }
//
// public void setCreateTime(Date createTime) {
// this.createTime = createTime;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
//
// public void setUpdatetime(String updatetime) {
// this.updatetime = updatetime;
// }
//
// public String getUpdatetime() {
// return updatetime;
// }
//
// public void setUserid(String userid) {
// this.userid = userid;
// }
//
// public String getUserid() {
// return userid;
// }
//
// public void setUserId(String userId) {
// this.userId = userId;
// }
//
// public String getUserId() {
// return userId;
// }
//
// // public void setVirtualmachine(String virtualmachine) {
// // this.virtualmachine = virtualmachine;
// // }
// //
// // public String getVirtualmachine() {
// // return virtualmachine;
// // }
//
// public void setUid(String uid) {
// this.uid = uid;
// }
//
// public String getUid() {
// return uid;
// }
//
// public void setTmp1(String tmp1) {
// this.tmp1 = tmp1;
// }
//
// public String getTmp1() {
// return tmp1;
// }
//
// public void setTmp2(String tmp2) {
// this.tmp2 = tmp2;
// }
//
// public String getTmp2() {
// return tmp2;
// }
//
// public void setTmp3(String tmp3) {
// this.tmp3 = tmp3;
// }
//
// public String getTmp3() {
// return tmp3;
// }
//
// public void setTmp4(String tmp4) {
// this.tmp4 = tmp4;
// }
//
// public String getTmp4() {
// return tmp4;
// }
//
// public void setTags(String tags) {
// this.tags = tags;
// }
//
// public String getTags() {
// return tags;
// }
//
// public void setSdkmd5(String sdkmd5) {
// this.sdkmd5 = sdkmd5;
// }
//
// public String getSdkmd5() {
// return sdkmd5;
// }
//
// public void setSdkversion(String sdkversion) {
// this.sdkversion = sdkversion;
// }
//
// public String getSdkversion() {
// return sdkversion;
// }
//
// public void setSerialno(String serialno) {
// this.serialno = serialno;
// }
//
// public String getSerialno() {
// return serialno;
// }
//
// public void setSignmd5(String signmd5) {
// this.signmd5 = signmd5;
// }
//
// public String getSignmd5() {
// return signmd5;
// }
//
// public void setProxyinfo(String proxyinfo) {
// this.proxyinfo = proxyinfo;
// }
//
// public String getProxyinfo() {
// return proxyinfo;
// }
//
// public void setProxytype(String proxytype) {
// this.proxytype = proxytype;
// }
//
// public String getProxytype() {
// return proxytype;
// }
//
// public void setProxyurl(String proxyurl) {
// this.proxyurl = proxyurl;
// }
//
// public String getProxyurl() {
// return proxyurl;
// }
//
// public void setProduct(String product) {
// this.product = product;
// }
//
// public String getProduct() {
// return product;
// }
//
// public void setPartner(String partner) {
// this.partner = partner;
// }
//
// public String getPartner() {
// return partner;
// }
//
// // public void setJailbreak(String jailbreak) {
// // this.jailbreak = jailbreak;
// // }
// //
// // public String getJailbreak() {
// // return jailbreak;
// // }
//
// public void setKernelversion(String kernelversion) {
// this.kernelversion = kernelversion;
// }
//
// public String getKernelversion() {
// return kernelversion;
// }
//
// public void setOperatecode(String operatecode) {
// this.operatecode = operatecode;
// }
//
// public String getOperatecode() {
// return operatecode;
// }
//
// public void setVpnip(String vpnip) {
// this.vpnip = vpnip;
// }
//
// public String getVpnip() {
// return vpnip;
// }
//
// public void setWifinetmask(String wifinetmask) {
// this.wifinetmask = wifinetmask;
// }
//
// public String getWifinetmask() {
// return wifinetmask;
// }
//
// public void setVpnnetmask(String vpnnetmask) {
// this.vpnnetmask = vpnnetmask;
// }
//
// public String getVpnnetmask() {
// return vpnnetmask;
// }
// }
