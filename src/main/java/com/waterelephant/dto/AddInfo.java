package com.waterelephant.dto;

import com.beadwallet.service.entity.response.Zhima;

public class AddInfo {

	private Zhima zhima;
	private Mobile mobile;
	private Contacts contacts;
	public Zhima getZhima() {
		return zhima;
	}
	public void setZhima(Zhima zhima) {
		this.zhima = zhima;
	}
	public Mobile getMobile() {
		return mobile;
	}
	public void setMobile(Mobile mobile) {
		this.mobile = mobile;
	}
	public Contacts getContacts() {
		return contacts;
	}
	public void setContacts(Contacts contacts) {
		this.contacts = contacts;
	}
	
}
