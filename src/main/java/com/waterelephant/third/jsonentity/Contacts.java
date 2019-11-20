package com.waterelephant.third.jsonentity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class Contacts {

	@JSONField(name = "platform")
	private String platform; // 系统平台
	
    @JSONField(name = "installed_apps")
    private String installedApps; // 手机内的应用安装列表

    @JSONField(name = "installed_apps_version")
    private String installedAppsVersion; // 安装应用的版本号

    @JSONField(name = "app_location")
    private AppLocation appLocation; // 手机GPS信息

    @JSONField(name = "phone_list")
    private List<PhoneList> phoneList; // 手机通讯录
    
    @JSONField(name = "call_log")
    private List<CallLog> callLogList; // 呼叫日志

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getInstalledApps() {
		return installedApps;
	}

	public void setInstalledApps(String installedApps) {
		this.installedApps = installedApps;
	}

	public String getInstalledAppsVersion() {
		return installedAppsVersion;
	}

	public void setInstalledAppsVersion(String installedAppsVersion) {
		this.installedAppsVersion = installedAppsVersion;
	}

	public AppLocation getAppLocation() {
		return appLocation;
	}

	public void setAppLocation(AppLocation appLocation) {
		this.appLocation = appLocation;
	}

	public List<PhoneList> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<PhoneList> phoneList) {
		this.phoneList = phoneList;
	}

	public List<CallLog> getCallLogList() {
		return callLogList;
	}

	public void setCallLogList(List<CallLog> callLogList) {
		this.callLogList = callLogList;
	}
    
}
