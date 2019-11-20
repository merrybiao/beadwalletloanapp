package com.waterelephant.drainage.baidu.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 设备信息
 * @author dengyan
 *
 */
public class DeviceInfo {

	@JSONField(name="model")
	private String model; // 设备型号
	
	@JSONField(name="version")
	private String version; // 系统版本号
	
	@JSONField(name="cuid")
	private String cuid; // 设备号
	
	@JSONField(name="imei")
	private String imei; // 手机串码
	
	@JSONField(name="os")
	private String os; // 手机系统
	
	@JSONField(name="brand")
	private String brand; // 手机品牌
	
	@JSONField(name="ip")
	private String ip; // Ip地址

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCuid() {
		return cuid;
	}

	public void setCuid(String cuid) {
		this.cuid = cuid;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	} // Ip地址
}
