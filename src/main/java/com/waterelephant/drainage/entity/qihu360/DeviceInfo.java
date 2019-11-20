/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.entity.qihu360;

/**
 * 
 * 
 * Module: 其他设备信息
 * 
 * DeviceInfo.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class DeviceInfo {
	private String tele_num;// 运营商号
	private String tele_name;// 数据服务商名称1=移动2=联通3=电信4=WiFi
	private String is_root;// 设备状态1=已 root2=未 root
	private String dns;// DNS
	private String mem_size;// 手机内存
	private String storage_size;// 手机存储空间
	private String ava_storage_size;// 手机可用存储空间
	private String phone_brand;// 手机品牌
	private String device_model;// 安卓厂商/苹果型号

	private String imei;// 手机 IMEI
	private String imsi;// 手机 IMSI
	private String seria_no;// 手机序列号
	private String android_id;// 手机 androidID
	private String udid;// UDID 号
	private String mac;// 手机 mac 地址
	private String android_ver;// 操作系统版本

	private String idfa;// 手机 idfa
	private String idfv;// 手机 idfv
	private String ios_plat;// 手机平台
	private String ios_ver;// 手机版本
	private String uuid;// UUID 号

	public String getTele_num() {
		return tele_num;
	}

	public void setTele_num(String tele_num) {
		this.tele_num = tele_num;
	}

	public String getTele_name() {
		return tele_name;
	}

	public void setTele_name(String tele_name) {
		this.tele_name = tele_name;
	}

	public String getIs_root() {
		return is_root;
	}

	public void setIs_root(String is_root) {
		this.is_root = is_root;
	}

	public String getDns() {
		return dns;
	}

	public void setDns(String dns) {
		this.dns = dns;
	}

	public String getMem_size() {
		return mem_size;
	}

	public void setMem_size(String mem_size) {
		this.mem_size = mem_size;
	}

	public String getStorage_size() {
		return storage_size;
	}

	public void setStorage_size(String storage_size) {
		this.storage_size = storage_size;
	}

	public String getAva_storage_size() {
		return ava_storage_size;
	}

	public void setAva_storage_size(String ava_storage_size) {
		this.ava_storage_size = ava_storage_size;
	}

	public String getPhone_brand() {
		return phone_brand;
	}

	public void setPhone_brand(String phone_brand) {
		this.phone_brand = phone_brand;
	}

	public String getDevice_model() {
		return device_model;
	}

	public void setDevice_model(String device_model) {
		this.device_model = device_model;
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

	public String getSeria_no() {
		return seria_no;
	}

	public void setSeria_no(String seria_no) {
		this.seria_no = seria_no;
	}

	public String getAndroid_id() {
		return android_id;
	}

	public void setAndroid_id(String android_id) {
		this.android_id = android_id;
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

	public String getAndroid_ver() {
		return android_ver;
	}

	public void setAndroid_ver(String android_ver) {
		this.android_ver = android_ver;
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

	public String getIos_plat() {
		return ios_plat;
	}

	public void setIos_plat(String ios_plat) {
		this.ios_plat = ios_plat;
	}

	public String getIos_ver() {
		return ios_ver;
	}

	public void setIos_ver(String ios_ver) {
		this.ios_ver = ios_ver;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
