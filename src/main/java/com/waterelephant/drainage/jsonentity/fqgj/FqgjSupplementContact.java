package com.waterelephant.drainage.jsonentity.fqgj;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/28 10:05
 */
public class FqgjSupplementContact {
	
	@JSONField(name = "device_info")
    private String  device_info;
	
	@JSONField(name = "platform")
    private String  platform;
	
	@JSONField(name = "installed_apps")
    private String  installed_apps;
	
	@JSONField(name = "installed_apps_version")
    private String  installed_apps_version;
	
	@JSONField(name = "phone_list")
    private List<FqgjSupplementPhone> phone_list;
	
	@JSONField(name = "call_log")
    private List<FqgjSupplementCallLog> call_log;

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

    public List<FqgjSupplementPhone> getPhone_list() {
        return phone_list;
    }

    public void setPhone_list(List<FqgjSupplementPhone> phone_list) {
        this.phone_list = phone_list;
    }

    public List<FqgjSupplementCallLog> getCall_log() {
        return call_log;
    }

    public void setCall_log(List<FqgjSupplementCallLog> call_log) {
        this.call_log = call_log;
    }
}
