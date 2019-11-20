package com.waterelephant.third.jsonentity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 运营商信息
 * @author DIY
 *
 */
public class Mobile {

    @JSONField(name = "user")
    private User user; // 基础信息

    @JSONField(name = "tel")
    private List<Tel> tel; // 通话记录

    @JSONField(name = "msg")
    private Msg msg; // 短信信息

    @JSONField(name = " mobile_bill")
    private List<Bill> mobileBillList; // 月账单记录

    @JSONField(name = "mobile_net")
    private  List<Net> mobileNetList; // 流量值记录

    @JSONField(name = "mobile_recharge")
    private List<Recharge> mobileRechargeList; // 充值记录

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

	public Msg getMsg() {
		return msg;
	}

	public void setMsg(Msg msg) {
		this.msg = msg;
	}

	public List<Bill> getMobileBillList() {
		return mobileBillList;
	}

	public void setMobileBillList(List<Bill> mobileBillList) {
		this.mobileBillList = mobileBillList;
	}

	public List<Net> getMobileNetList() {
		return mobileNetList;
	}

	public void setMobileNetList(List<Net> mobileNetList) {
		this.mobileNetList = mobileNetList;
	}

	public List<Recharge> getMobileRechargeList() {
		return mobileRechargeList;
	}

	public void setMobileRechargeList(List<Recharge> mobileRechargeList) {
		this.mobileRechargeList = mobileRechargeList;
	}

}
