package com.waterelephant.drainage.entity.xianJinCard;

/**
 * Module: BasicVo.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class BasicVo {
	private String update_time;// update_time string 是 数据获取时间 basic
	private String cell_phone;// cell_phone string 是 本机号码 basic
	private String idcard;// idcard string 是 登记身份证号 basic
	private String reg_time;// reg_time string 是 入网时间 basic
	private String real_name;// real_name string 是 登记姓名 basic

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getCell_phone() {
		return cell_phone;
	}

	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getReg_time() {
		return reg_time;
	}

	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

}