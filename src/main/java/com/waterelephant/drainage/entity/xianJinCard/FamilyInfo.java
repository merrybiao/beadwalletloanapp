package com.waterelephant.drainage.entity.xianJinCard;

/**
 * Module: CarInfo.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class FamilyInfo {
	private String home_areas; // home_areas string 是 省,市,区 (英文逗号区隔)
	private String home_address;// home_address string 是 居住街道与门牌号
	private String live_time;// live_time int 是 居住时长
	private String house_type; // house_type int 是 住宅类型:1.租房,2.自有住房,3.父母住房,4.公司宿舍,5.房改房,6.其它
	private String permanent_areas;// permanent_areas string 是 户籍省市区
	private String permanent_address;// permanent_address string 是 户籍街道与门牌号

	public String getHome_areas() {
		return home_areas;
	}

	public void setHome_areas(String home_areas) {
		this.home_areas = home_areas;
	}

	public String getHome_address() {
		return home_address;
	}

	public void setHome_address(String home_address) {
		this.home_address = home_address;
	}

	public String getLive_time() {
		return live_time;
	}

	public void setLive_time(String live_time) {
		this.live_time = live_time;
	}

	public String getHouse_type() {
		return house_type;
	}

	public void setHouse_type(String house_type) {
		this.house_type = house_type;
	}

	public String getPermanent_areas() {
		return permanent_areas;
	}

	public void setPermanent_areas(String permanent_areas) {
		this.permanent_areas = permanent_areas;
	}

	public String getPermanent_address() {
		return permanent_address;
	}

	public void setPermanent_address(String permanent_address) {
		this.permanent_address = permanent_address;
	}

}