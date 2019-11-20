package com.waterelephant.fudata.entity.vo;

public class FudataCrawlerProgressVO {

	private String task_id;
	
	private String captcha_mobile;//短信验证码
	
	private String captcha_picture;//图片验证码

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getCaptcha_mobile() {
		return captcha_mobile;
	}

	public void setCaptcha_mobile(String captcha_mobile) {
		this.captcha_mobile = captcha_mobile;
	}

	public String getCaptcha_picture() {
		return captcha_picture;
	}

	public void setCaptcha_picture(String captcha_picture) {
		this.captcha_picture = captcha_picture;
	}
	
	
}
