/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.entity.qihu360;

import java.util.List;

/**
 * 
 * 
 * Module: 设备基本信息
 * 
 * ExtraInfoContacts.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class ExtraInfoContacts {
	private String device_num;// string 手机设备号
	private String platform;// string 手机平台
	private ExtraInfoContactsAppLocation app_location;// 手机 GPS 信息
	private List<ExtraInfoContactsPhoneItem> phone_list;// 手机通讯录

	private String device_info;// string 手机型号
	private String installed_apps;// 手机内的应用安装列表
	private String installed_apps_version;// 安装应用的版本号
	private List<ExtraInfoContactsCallLog> call_log;// 手机通话记录

	public String getDevice_num() {
		return device_num;
	}

	public void setDevice_num(String device_num) {
		this.device_num = device_num;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public ExtraInfoContactsAppLocation getApp_location() {
		return app_location;
	}

	public void setApp_location(ExtraInfoContactsAppLocation app_location) {
		this.app_location = app_location;
	}

	public List<ExtraInfoContactsPhoneItem> getPhone_list() {
		return phone_list;
	}

	public void setPhone_list(List<ExtraInfoContactsPhoneItem> phone_list) {
		this.phone_list = phone_list;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
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

	public List<ExtraInfoContactsCallLog> getCall_log() {
		return call_log;
	}

	public void setCall_log(List<ExtraInfoContactsCallLog> call_log) {
		this.call_log = call_log;
	}

}
