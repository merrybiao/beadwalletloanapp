/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.entity.xianJinCard;

/**
 * Module:
 * 
 * ContactInfo.java
 * 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class ContactInfo {
	private String name;// 第一联系人姓名
	private String mobile;// 第一联系人手机号
	private String relation;// 第一联系人与用户关系
	private String name_spare;// 第二联系人姓名
	private String mobile_spare;// 第二联系人手机号
	private String relation_spare;// 第二联系人与用户关系

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getName_spare() {
		return name_spare;
	}

	public void setName_spare(String name_spare) {
		this.name_spare = name_spare;
	}

	public String getMobile_spare() {
		return mobile_spare;
	}

	public void setMobile_spare(String mobile_spare) {
		this.mobile_spare = mobile_spare;
	}

	public String getRelation_spare() {
		return relation_spare;
	}

	public void setRelation_spare(String relation_spare) {
		this.relation_spare = relation_spare;
	}

}
