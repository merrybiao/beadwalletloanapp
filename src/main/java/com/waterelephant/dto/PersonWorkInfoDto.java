package com.waterelephant.dto;

public class PersonWorkInfoDto {

	private String comName;// 公司名称
	private String industry;// 行业
	private String workYears;// 工作年限
	private String cityName;// 所在城市
	private String relationName;// 亲属联系人姓名
	private String relationPhone;// 亲属联系人手机号
	private String unrelationName;// 好友联系人姓名
	private String unrelationPhone;// 好友联系人手机号
	private String email;// 邮箱
	private String address;// 现居地址

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PersonWorkInfoDto [comName=" + comName + ", industry=" + industry + ", workYears=" + workYears
				+ ", cityName=" + cityName + ", relationName=" + relationName + ", relationPhone=" + relationPhone
				+ ", unrelationName=" + unrelationName + ", unrelationPhone=" + unrelationPhone + ", email=" + email
				+ ", address=" + address + "]";
	}

	/**
	 * @return 获取 comName属性值
	 */
	public String getComName() {
		return comName;
	}

	/**
	 * @param comName 设置 comName 属性值为参数值 comName
	 */
	public void setComName(String comName) {
		this.comName = comName;
	}

	/**
	 * @return 获取 industry属性值
	 */
	public String getIndustry() {
		return industry;
	}

	/**
	 * @param industry 设置 industry 属性值为参数值 industry
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	/**
	 * @return 获取 workYears属性值
	 */
	public String getWorkYears() {
		return workYears;
	}

	/**
	 * @param workYears 设置 workYears 属性值为参数值 workYears
	 */
	public void setWorkYears(String workYears) {
		this.workYears = workYears;
	}

	/**
	 * @return 获取 cityName属性值
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName 设置 cityName 属性值为参数值 cityName
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return 获取 relationName属性值
	 */
	public String getRelationName() {
		return relationName;
	}

	/**
	 * @param relationName 设置 relationName 属性值为参数值 relationName
	 */
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	/**
	 * @return 获取 relationPhone属性值
	 */
	public String getRelationPhone() {
		return relationPhone;
	}

	/**
	 * @param relationPhone 设置 relationPhone 属性值为参数值 relationPhone
	 */
	public void setRelationPhone(String relationPhone) {
		this.relationPhone = relationPhone;
	}

	/**
	 * @return 获取 unrelationName属性值
	 */
	public String getUnrelationName() {
		return unrelationName;
	}

	/**
	 * @param unrelationName 设置 unrelationName 属性值为参数值 unrelationName
	 */
	public void setUnrelationName(String unrelationName) {
		this.unrelationName = unrelationName;
	}

	/**
	 * @return 获取 unrelationPhone属性值
	 */
	public String getUnrelationPhone() {
		return unrelationPhone;
	}

	/**
	 * @param unrelationPhone 设置 unrelationPhone 属性值为参数值 unrelationPhone
	 */
	public void setUnrelationPhone(String unrelationPhone) {
		this.unrelationPhone = unrelationPhone;
	}

	/**
	 * @return 获取 email属性值
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email 设置 email 属性值为参数值 email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return 获取 address属性值
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address 设置 address 属性值为参数值 address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

}
