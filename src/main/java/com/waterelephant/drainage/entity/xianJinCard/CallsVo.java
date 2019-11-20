package com.waterelephant.drainage.entity.xianJinCard;

import org.apache.commons.lang3.StringUtils;

/**
 * Module: CallsVo.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class CallsVo {

	private String update_time; // 数据获取时间 calls
	private String start_time; // 通话时间 calls
	private String init_type; // 呼叫类型 calls
	private int use_time; // 通话时长（秒） calls
	private String place; // 通话地点 calls
	private String other_cell_phone;// 对方号码 calls
	private String cell_phone; // 本机号码 calls
	private String call_type; // 通话类型 calls
	private int subtotal;// 本次通话花费（元） calls

	public int getcallType() {
		if (StringUtils.isNotBlank(init_type)) {
			if (init_type.indexOf("主叫") != -1) {
				return 1;
			} else if (init_type.indexOf("被叫") != -1) {
				return 2;
			}
		}
		return 0;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getInit_type() {
		return init_type;
	}

	public void setInit_type(String init_type) {
		this.init_type = init_type;
	}

	public int getUse_time() {
		return use_time;
	}

	public void setUse_time(int use_time) {
		this.use_time = use_time;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getOther_cell_phone() {
		return other_cell_phone;
	}

	public void setOther_cell_phone(String other_cell_phone) {
		this.other_cell_phone = other_cell_phone;
	}

	public String getCell_phone() {
		return cell_phone;
	}

	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}

	public String getCall_type() {
		return call_type;
	}

	public void setCall_type(String call_type) {
		this.call_type = call_type;
	}

	public int getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}

}