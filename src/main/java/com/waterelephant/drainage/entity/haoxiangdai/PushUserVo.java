/******************************************************************************
 * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.drainage.entity.haoxiangdai;

/**
 * Module: 
 * PushUser.java 
 * @author huangjin
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class PushUserVo {
	private String mobile;// 手机号
	private String ext;// 扩展信息
	private String penetrate;// 透传信息

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getPenetrate() {
		return penetrate;
	}

	public void setPenetrate(String penetrate) {
		this.penetrate = penetrate;
	}

}
