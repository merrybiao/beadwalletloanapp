package com.waterelephant.drainage.entity.rongShu;
/**
 * @author xiaoXingWu
 * @time 2017年10月19日
 * @since JDK1.8
 * @description
 */
public class DeviceContact {
	
	private  String names[];
	private  String phones[] ;
	private  String events;
	private  String emails;
	private  String callSize;
	private  String updateTime;
	
	
	public String[] getNames() {
		return names;
	}
	public void setNames(String[] names) {
		this.names = names;
	}
	public String[] getPhones() {
		return phones;
	}
	public void setPhones(String[] phones) {
		this.phones = phones;
	}
	public String getEvents() {
		return events;
	}
	public void setEvents(String events) {
		this.events = events;
	}
	public String getEmails() {
		return emails;
	}
	public void setEmails(String emails) {
		this.emails = emails;
	}
	public String getCallSize() {
		return callSize;
	}
	public void setCallSize(String callSize) {
		this.callSize = callSize;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	

}

