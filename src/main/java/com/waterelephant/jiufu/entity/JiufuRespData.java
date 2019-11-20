package com.waterelephant.jiufu.entity;

/**
 * @author 张诚
 * @since JDK 1.8
 * @version 1.0
 * @description: <联合注册响应data详细信息>
 *
 */
public class JiufuRespData {
	private String channel_id;				//叮当分配给渠道的ID
	private String serial_number;			//进行联合登录流水号
	private String regist_code;				//已注册为1,未注册为0
	private String url;						//登录后跳转页面的URL
	public String getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getRegist_code() {
		return regist_code;
	}
	public void setRegist_code(String regist_code) {
		this.regist_code = regist_code;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("JiufuRespData [");
		if (channel_id != null) {
			builder.append("channel_id=");
			builder.append(channel_id);
			builder.append(", ");
		}
		if (serial_number != null) {
			builder.append("serial_number=");
			builder.append(serial_number);
			builder.append(", ");
		}
		if (regist_code != null) {
			builder.append("regist_code=");
			builder.append(regist_code);
			builder.append(", ");
		}
		if (url != null) {
			builder.append("url=");
			builder.append(url);
		}
		builder.append("]");
		return builder.toString();
	}
	

}
