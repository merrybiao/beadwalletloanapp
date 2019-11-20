package com.waterelephant.jiedianqian.entity;

/**
 * 
 * 
 * Module: 
 * 
 * Basic.java 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <基本信息>
 */
public class Basic {
	private String update_time;				//数据获取时间

	private String idcard;					//登记身份证号

	private String reg_time;				//入网时间

	private String real_name;				//登记姓名

	private String cell_phone;				//本机号码

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getUpdate_time() {
		return this.update_time;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getIdcard() {
		return this.idcard;
	}

	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}

	public String getReg_time() {
		return this.reg_time;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getReal_name() {
		return this.real_name;
	}

	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}

	public String getCell_phone() {
		return this.cell_phone;
	}
}
