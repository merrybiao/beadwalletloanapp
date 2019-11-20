package com.waterelephant.drainage.baidu.entity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 用户信息
 * 
 * @author dengyan
 *
 */
public class UserInfo {

	@JSONField(name = "em_contact")
	private List<EmContact> em_contact; // 紧急联系人

	@JSONField(name = "basic_info")
	private BasicInfo basic_info; // 用户基本信息

	@JSONField(name = "operator")
	private Operator operator; // 运营商信息

	@JSONField(name = "housing_fund")
	private HousingFund housing_fund; // 公积金信息

	@JSONField(name = "rms")
	private Rms rms; // 风控信息

	@JSONField(name = "device_info")
	private DeviceInfo device_info; // 设备信息

	/**
	 * @return 获取 em_contact属性值
	 */
	public List<EmContact> getEm_contact() {
		return em_contact;
	}

	/**
	 * @param em_contact 设置 em_contact 属性值为参数值 em_contact
	 */
	public void setEm_contact(List<EmContact> em_contact) {
		this.em_contact = em_contact;
	}

	/**
	 * @return 获取 basic_info属性值
	 */
	public BasicInfo getBasic_info() {
		return basic_info;
	}

	/**
	 * @param basic_info 设置 basic_info 属性值为参数值 basic_info
	 */
	public void setBasic_info(BasicInfo basic_info) {
		this.basic_info = basic_info;
	}

	/**
	 * @return 获取 operator属性值
	 */
	public Operator getOperator() {
		return operator;
	}

	/**
	 * @param operator 设置 operator 属性值为参数值 operator
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/**
	 * @return 获取 housing_fund属性值
	 */
	public HousingFund getHousing_fund() {
		return housing_fund;
	}

	/**
	 * @param housing_fund 设置 housing_fund 属性值为参数值 housing_fund
	 */
	public void setHousing_fund(HousingFund housing_fund) {
		this.housing_fund = housing_fund;
	}

	/**
	 * @return 获取 rms属性值
	 */
	public Rms getRms() {
		return rms;
	}

	/**
	 * @param rms 设置 rms 属性值为参数值 rms
	 */
	public void setRms(Rms rms) {
		this.rms = rms;
	}

	/**
	 * @return 获取 device_info属性值
	 */
	public DeviceInfo getDevice_info() {
		return device_info;
	}

	/**
	 * @param device_info 设置 device_info 属性值为参数值 device_info
	 */
	public void setDevice_info(DeviceInfo device_info) {
		this.device_info = device_info;
	}

}
