package com.waterelephant.cashloan.entity;

public class PushUserReqData {			//请求	
	private String mobile;				//手机号	   	 使用
	private String realname;			//真实信命		   使用
	private String scureid;				//身份证		使用
	private String amount;				//借款金额
	private String period;				//借款期限
	private String send_time;			//发送时间
	private String sid;					//
	private String source_device;		//设备信息
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getScureid() {
		return scureid;
	}
	public void setScureid(String scureid) {
		this.scureid = scureid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSource_device() {
		return source_device;
	}
	public void setSource_device(String source_device) {
		this.source_device = source_device;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PushUserReqData [");
		if (mobile != null) {
			builder.append("mobile=");
			builder.append(mobile);
			builder.append(", ");
		}
		if (realname != null) {
			builder.append("realname=");
			builder.append(realname);
			builder.append(", ");
		}
		if (scureid != null) {
			builder.append("scureid=");
			builder.append(scureid);
			builder.append(", ");
		}
		if (amount != null) {
			builder.append("amount=");
			builder.append(amount);
			builder.append(", ");
		}
		if (period != null) {
			builder.append("period=");
			builder.append(period);
			builder.append(", ");
		}
		if (send_time != null) {
			builder.append("send_time=");
			builder.append(send_time);
			builder.append(", ");
		}
		if (sid != null) {
			builder.append("sid=");
			builder.append(sid);
			builder.append(", ");
		}
		if (source_device != null) {
			builder.append("source_device=");
			builder.append(source_device);
		}
		builder.append("]");
		return builder.toString();
	}
}