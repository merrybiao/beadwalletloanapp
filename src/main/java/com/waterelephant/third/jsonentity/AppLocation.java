package com.waterelephant.third.jsonentity;

import com.alibaba.fastjson.annotation.JSONField;

public class AppLocation {

	@JSONField(name = "lat")
	private Double lat;
	
	@JSONField(name = "address")
	private String address;
	
	@JSONField(name = "lon")
	private Double lon;

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}
	
}
