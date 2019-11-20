package com.waterelephant.jiufu.entity;

/**
 * 
 * @author 张诚
 * @since JDK 1.8
 * @version 1.0
 * @description: <玖富请求信息>
 *
 */
public class JiufuReq {
	private String channel_id;			//渠道编号（叮当分配给渠道的ID）
	private String dingdang_id;			//叮当编号（渠道分配给叮当的编号）
	private String serial_number;		//流水号
	private String mobile;				//用户手机号
	private String name;				//用户姓名
	private String cert_id;				//用户身份证号
	public String getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	public String getDingdang_id() {
		return dingdang_id;
	}
	public void setDingdang_id(String dingdang_id) {
		this.dingdang_id = dingdang_id;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCert_id() {
		return cert_id;
	}
	public void setCert_id(String cert_id) {
		this.cert_id = cert_id;
	}
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("JiufuReq [");
		if (channel_id != null) {
			builder.append("channel_id=");
			builder.append(channel_id);
			builder.append(", ");
		}
		if (dingdang_id != null) {
			builder.append("dingdang_id=");
			builder.append(dingdang_id);
			builder.append(", ");
		}
		if (serial_number != null) {
			builder.append("serial_number=");
			builder.append(serial_number);
			builder.append(", ");
		}
		if (mobile != null) {
			builder.append("mobile=");
			builder.append(mobile);
			builder.append(", ");
		}
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (cert_id != null) {
			builder.append("cert_id=");
			builder.append(cert_id);
		}
		builder.append("]");
		
		return builder.toString();
		
	}
	
}
