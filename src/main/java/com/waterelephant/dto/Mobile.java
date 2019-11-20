package com.waterelephant.dto;

import java.util.List;

import com.beadwallet.service.entiyt.middle.Bill;
import com.beadwallet.service.entiyt.middle.Msg;
import com.beadwallet.service.entiyt.middle.Tel;

public class Mobile {

	private User user;
	//tel
	private List<Tel> tel;
	//msg
	private List<Msg> msg;
	//bill
	private List<Bill> bill;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Tel> getTel() {
		return tel;
	}
	public void setTel(List<Tel> tel) {
		this.tel = tel;
	}
	public List<Msg> getMsg() {
		return msg;
	}
	public void setMsg(List<Msg> msg) {
		this.msg = msg;
	}
	public List<Bill> getBill() {
		return bill;
	}
	public void setBill(List<Bill> bill) {
		this.bill = bill;
	}
	
}
