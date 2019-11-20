package com.waterelephant.jiedianqian.entity;

public class DeviceInfo {
	private String device_id; // 设备号
	private String ip; // ip地址
	private String device_type; // 设备类型
	private String device_model; // 设备型号
	private String device_os; // 操作系统
	private String openudid; // iOS的openudid
	private String jailbreak_flag; // iOS 是否越狱
	private String root_flag; // Android是否root

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public String getDevice_model() {
		return device_model;
	}

	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}

	public String getDevice_os() {
		return device_os;
	}

	public void setDevice_os(String device_os) {
		this.device_os = device_os;
	}

	public String getOpenudid() {
		return openudid;
	}

	public void setOpenudid(String openudid) {
		this.openudid = openudid;
	}

	public String getJailbreak_flag() {
		return jailbreak_flag;
	}

	public void setJailbreak_flag(String jailbreak_flag) {
		this.jailbreak_flag = jailbreak_flag;
	}

	public String getRoot_flag() {
		return root_flag;
	}

	public void setRoot_flag(String root_flag) {
		this.root_flag = root_flag;
	}

}
