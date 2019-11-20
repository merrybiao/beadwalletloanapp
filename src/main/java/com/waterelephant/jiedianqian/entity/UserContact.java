package com.waterelephant.jiedianqian.entity;

/**
 * 
 * 
 * Module: 
 * 
 * UserContact.java 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <联系人>
 */
public class UserContact {
	private String name; 				// 第一联系人姓名
	private String mobile; 				// 第一联系人电话号
	private String relation; 			// 第一联系人关系（父母、配偶、兄弟、姐妹）
	private String name_spare;			 // 第二联系人姓名
	private String mobile_spare; 		// 第二联系人电话号
	private String relation_spare;		 // 第二联系人关系（同事、同学、朋友）

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
