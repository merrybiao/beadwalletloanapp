package com.waterelephant.sms.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 短信发送配置表实体
 * @author dengyan
 *
 */
@Table(name = "sms_config")
public class SmsConfig {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	private int id; // 主键
	private int chenal; // 发送短信的渠道：1，亿美 2，大汉三通
	private String sign; // 短信签名
	private int state; // 渠道使用状态，1为启用，数据库中有且只有一个状态为1
	private int type; // 发送短信方式:1,文字 1，语音
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getChenal() {
		return chenal;
	}
	public void setChenal(int chenal) {
		this.chenal = chenal;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "SmsConfig [id=" + id + ", chenal=" + chenal + ", sign=" + sign
				+ ", state=" + state + ", type=" + type + "]";
	}
}
