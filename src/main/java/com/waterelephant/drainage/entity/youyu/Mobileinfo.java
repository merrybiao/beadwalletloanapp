/**
  * Copyright 2017 bejson.com 
  */
package com.waterelephant.drainage.entity.youyu;
import java.util.ArrayList;

/***
 * s
 * 
 * 
 * Module: 手机信息实体类
 * 
 * Mobileinfo.java 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class Mobileinfo {
	
    private Userdata userdata;//用户基本信息
    
    private ArrayList<Teldata> tel;//通话
    private ArrayList<Msgdata> msg;//短信
    private ArrayList<Billdata> bill;//账单
	public Userdata getUserdata() {
		return userdata;
	}
	public void setUserdata(Userdata userdata) {
		this.userdata = userdata;
	}
	public ArrayList<Teldata> getTel() {
		return tel;
	}
	public void setTel(ArrayList<Teldata> tel) {
		this.tel = tel;
	}
	public ArrayList<Msgdata> getMsg() {
		return msg;
	}
	public void setMsg(ArrayList<Msgdata> msg) {
		this.msg = msg;
	}
	public ArrayList<Billdata> getBill() {
		return bill;
	}
	public void setBill(ArrayList<Billdata> bill) {
		this.bill = bill;
	}

}