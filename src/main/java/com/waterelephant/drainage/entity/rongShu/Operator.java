package com.waterelephant.drainage.entity.rongShu;

/**
 * 榕树 - 5.3 进件推送 - 入参（运营商数据）（code0087503）
 * 
 * 
 * Module:
 * 
 * Operator.java
 * 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <榕树 - 5.3 进件推送 - 入参（运营商数据）>
 */
public class Operator {
	private String createTime; // 数据首次拉取时间

	private String operatorCity; // 运营商城市

	private String operatorName; // 运营商名称

	private String operatorProvince; // 运营商省份

	private String operatorType; // 运营商类型

	private String resourceUrl; // 数据获取URL


	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	public void setOperatorCity(String operatorCity) {
		this.operatorCity = operatorCity;
	}

	public String getOperatorCity() {
		return this.operatorCity;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorProvince(String operatorProvince) {
		this.operatorProvince = operatorProvince;
	}

	public String getOperatorProvince() {
		return this.operatorProvince;
	}


	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getResourceUrl() {
		return this.resourceUrl;
	}
}
