package com.waterelephant.jiedianqian.entity;

public class Calls {
	private String start_time;					//通话时间
	
	private String update_time;					//数据获取时间

	private String use_time;					//通话时长（秒）

	private String subtotal;					//本次通话花费（元）

	private String place;						//通话地点 

	private String call_type;					//通话类型

	private String init_type;					//呼叫类型 

	private String other_cell_phone;			//对方号码

	private String cell_phone;					//本机号码

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getStart_time() {
		return this.start_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	@Override
	public String toString() {
		return "Calls [start_time=" + start_time + ", update_time=" + update_time + ", use_time=" + use_time
				+ ", subtotal=" + subtotal + ", place=" + place + ", call_type=" + call_type + ", init_type="
				+ init_type + ", other_cell_phone=" + other_cell_phone + ", cell_phone=" + cell_phone + "]";
	}

	public String getUpdate_time() {
		return this.update_time;
	}

	public void setUse_time(String use_time) {
		this.use_time = use_time;
	}

	public String getUse_time() {
		return this.use_time;
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

	public void setCall_type(String call_type) {
		this.call_type = call_type;
	}

	public String getCall_type() {
		return this.call_type;
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
}
