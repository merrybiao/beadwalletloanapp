package com.waterelephant.gxb.dto;

import java.io.Serializable;
/**
 * 滴滴打车用户常用地址信息
 * @author dinglinhao
 *
 */
public class TaxiCommonAddressDto implements Serializable {
	
	private static final long serialVersionUID = -4353702509441712889L;
	
	private String addrName	;//常用地址名称（如家 公司）
	private String toAddress;//常用地址
	private String toName;//常用地址名
	private String addr;//聚合toAddress和toName
	private String cityName;//城市
	private Float lat;//纬度
	private Float lng;//经度
	
	public String getAddrName() {
		return addrName;
	}
	public void setAddrName(String addrName) {
		this.addrName = addrName;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public Float getLng() {
		return lng;
	}
	public void setLng(Float lng) {
		this.lng = lng;
	}

}
