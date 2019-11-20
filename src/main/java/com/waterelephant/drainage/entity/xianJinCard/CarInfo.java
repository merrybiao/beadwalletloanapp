package com.waterelephant.drainage.entity.xianJinCard;

/**
 * Module: CarInfo.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class CarInfo {
	private String car_status; // car_status int 是 车辆状况 1：有车贷，2.无抵押，3.无车贷，4.无抵押，无车贷
	private String car_price;// car_price string 是 车辆价格
	private String car_life_time;// car_life_time string 是 车辆使用年限

	public String getCar_status() {
		return car_status;
	}

	public void setCar_status(String car_status) {
		this.car_status = car_status;
	}

	public String getCar_price() {
		return car_price;
	}

	public void setCar_price(String car_price) {
		this.car_price = car_price;
	}

	public String getCar_life_time() {
		return car_life_time;
	}

	public void setCar_life_time(String car_life_time) {
		this.car_life_time = car_life_time;
	}

}