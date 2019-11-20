package com.waterelephant.cashloan.entity;

public class PullOrderReqData {		//请求
	private String sid;
	private String send_time;
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PullOrderReqData [");
		if (sid != null) {
			builder.append("sid=");
			builder.append(sid);
			builder.append(", ");
		}
		if (send_time != null) {
			builder.append("send_time=");
			builder.append(send_time);
		}
		builder.append("]");
		return builder.toString();
	}
}