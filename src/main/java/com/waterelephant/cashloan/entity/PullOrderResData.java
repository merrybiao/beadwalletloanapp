package com.waterelephant.cashloan.entity;

public class PullOrderResData {		
	private String mobile;
	private String status;		//  订单状态（1申请中  2申请失败  3审核中 4审核失败  5已放款  6放款失败）
	private String product_name;
	private String realname;
	private String scureid;
	private String amount;
	private String period;
	private String rate;
	private String send_time;
	private String update_time;
	private String sid;
	private String repayment;
	private String message;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
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
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getRepayment() {
		return repayment;
	}
	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PullOrderResData [");
		if (mobile != null) {
			builder.append("mobile=");
			builder.append(mobile);
			builder.append(", ");
		}
		if (status != null) {
			builder.append("status=");
			builder.append(status);
			builder.append(", ");
		}
		if (product_name != null) {
			builder.append("product_name=");
			builder.append(product_name);
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
		if (rate != null) {
			builder.append("rate=");
			builder.append(rate);
			builder.append(", ");
		}
		if (send_time != null) {
			builder.append("send_time=");
			builder.append(send_time);
			builder.append(", ");
		}
		if (update_time != null) {
			builder.append("update_time=");
			builder.append(update_time);
			builder.append(", ");
		}
		if (sid != null) {
			builder.append("sid=");
			builder.append(sid);
			builder.append(", ");
		}
		if (repayment != null) {
			builder.append("repayment=");
			builder.append(repayment);
			builder.append(", ");
		}
		if (message != null) {
			builder.append("message=");
			builder.append(message);
		}
		builder.append("]");
		return builder.toString();
	}
}