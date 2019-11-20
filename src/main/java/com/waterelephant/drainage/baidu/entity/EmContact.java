package com.waterelephant.drainage.baidu.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 紧急联系人
 * @author dengyan
 *
 */
public class EmContact {
	
	@JSONField(name="name")
	private String name; // 姓名
	
	@JSONField(name="mobile")
	private String mobile; // 手机
	
	@JSONField(name="relationship")
	private int relationship; // 与联系人关系1、父母，2、配偶，3、子女，4、兄弟姐妹，5、同事，6、同学，7、朋友

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

	public int getRelationship() {
		return relationship;
	}

	public void setRelationship(int relationship) {
		this.relationship = relationship;
	}

}
