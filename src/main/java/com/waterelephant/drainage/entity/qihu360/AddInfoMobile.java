package com.waterelephant.drainage.entity.qihu360;

import java.util.List;

/**
 * 
 * 
 * 
 * Module:运营商（mobile）
 * 
 * AddInfoMobile.java
 * 
 * @author zhangchong
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class AddInfoMobile {
	private AddInfoMobileUser user; // 用户运营商基础信息
	private AddInfoMobileTel tel; // 通话记录
	private AddInfoMobileMsg msg; // 短信信息
	private List<AddInfoMobileBill> bill; // 月账单记录
	private List<AddInfoMobileNet> net; // 流量值记录
	private List<AddInfoMobileRecharge> recharge; // 充值记录

	public AddInfoMobileUser getUser() {
		return user;
	}

	public void setUser(AddInfoMobileUser user) {
		this.user = user;
	}

	public AddInfoMobileTel getTel() {
		return tel;
	}

	public void setTel(AddInfoMobileTel tel) {
		this.tel = tel;
	}

	public AddInfoMobileMsg getMsg() {
		return msg;
	}

	public void setMsg(AddInfoMobileMsg msg) {
		this.msg = msg;
	}

	public List<AddInfoMobileBill> getBill() {
		return bill;
	}

	public void setBill(List<AddInfoMobileBill> bill) {
		this.bill = bill;
	}

	public List<AddInfoMobileNet> getNet() {
		return net;
	}

	public void setNet(List<AddInfoMobileNet> net) {
		this.net = net;
	}

	public List<AddInfoMobileRecharge> getRecharge() {
		return recharge;
	}

	public void setRecharge(List<AddInfoMobileRecharge> recharge) {
		this.recharge = recharge;
	}

}
