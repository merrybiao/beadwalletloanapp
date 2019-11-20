package com.waterelephant.drainage.entity.rongShu;
/**
 * @author xiaoXingWu
 * @time 2017年8月24日
 * @since JDK1.8
 * @description 电话手机号码信息
 */
public class PhoneInfo {
	
	private String province;//省份
	private String belongArea ;//手机号归属地
	private String pricePlanName;//套餐名称
	private String availableBalance;//可用余额
	private String frozenBalance;//冻结余额
	private String currentState;//当前状态.
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getBelongArea() {
		return belongArea;
	}
	public void setBelongArea(String belongArea) {
		this.belongArea = belongArea;
	}
	public String getPricePlanName() {
		return pricePlanName;
	}
	public void setPricePlanName(String pricePlanName) {
		this.pricePlanName = pricePlanName;
	}
	public String getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}
	public String getFrozenBalance() {
		return frozenBalance;
	}
	public void setFrozenBalance(String frozenBalance) {
		this.frozenBalance = frozenBalance;
	}
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	

}

