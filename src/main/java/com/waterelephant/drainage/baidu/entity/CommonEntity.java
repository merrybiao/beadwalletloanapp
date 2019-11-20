package com.waterelephant.drainage.baidu.entity;

import org.hibernate.validator.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 百度金融json实体
 * 
 * @author DIY
 *
 */
public class CommonEntity {

	@JSONField(name = "event")
	@NotBlank(message = "请求类型不能为空")
	private String event; // 请求类型

	@JSONField(name = "timestamp")
	@NotBlank(message = "时间戳不能为空")
	private String timestamp; // 请求时间戳

	@JSONField(name = "sign")
	@NotBlank(message = "签名不能为空")
	private String sign; // 请求签名

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * @return 获取 timestamp属性值
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp 设置 timestamp 属性值为参数值 timestamp
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
