package com.waterelephant.dto;

import java.util.List;

public class Contacts {

	private String id;
	private String uid;
	private String device_num;
	private String device_info;
	private String platform;
	private String installed_apps;
	private String installed_apps_version;
	private String update_time;
	private String create_time;
	private String delete_time;
	private AppLocation app_location;
	private List<PhoneList> phone_list;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getDevice_num() {
		return device_num;
	}
	public void setDevice_num(String device_num) {
		this.device_num = device_num;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getInstalled_apps() {
		return installed_apps;
	}
	public void setInstalled_apps(String installed_apps) {
		this.installed_apps = installed_apps;
	}
	public String getInstalled_apps_version() {
		return installed_apps_version;
	}
	public void setInstalled_apps_version(String installed_apps_version) {
		this.installed_apps_version = installed_apps_version;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getDelete_time() {
		return delete_time;
	}
	public void setDelete_time(String delete_time) {
		this.delete_time = delete_time;
	}
	public AppLocation getApp_location() {
		return app_location;
	}
	public void setApp_location(AppLocation app_location) {
		this.app_location = app_location;
	}
	public List<PhoneList> getPhone_list() {
		return phone_list;
	}
	public void setPhone_list(List<PhoneList> phone_list) {
		this.phone_list = phone_list;
	}
	
}
