package com.waterelephant.jiedianqian.entity;

/**
 * 
 * 
 * Module:
 * 
 * Smses.java
 * 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <运营商>
 */
public class Smses {

	private String start_time;

	private String update_time;

	private String subtotal;

	private String place;

	private String init_type;

	private String other_cell_phone;

	private String cell_phone;

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getStart_time() {
		return this.start_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getUpdate_time() {
		return this.update_time;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

	public String getSubtotal() {
		return this.subtotal;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPlace() {
		return this.place;
	}

	public void setInit_type(String init_type) {
		this.init_type = init_type;
	}

	public String getInit_type() {
		return this.init_type;
	}

	public void setOther_cell_phone(String other_cell_phone) {
		this.other_cell_phone = other_cell_phone;
	}

	public String getOther_cell_phone() {
		return this.other_cell_phone;
	}

	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}

	public String getCell_phone() {
		return this.cell_phone;
	}

	@Override
	public String toString() {
		return "Smses [start_time=" + start_time + ", update_time=" + update_time + ", subtotal=" + subtotal
				+ ", place=" + place + ", init_type=" + init_type + ", other_cell_phone=" + other_cell_phone
				+ ", cell_phone=" + cell_phone + "]";
	}

}
