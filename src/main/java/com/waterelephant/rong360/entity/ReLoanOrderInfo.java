package com.waterelephant.rong360.entity;
@Deprecated
public class ReLoanOrderInfo {
	private String order_No;
	private String user_name;
	private String user_mobile;
	private String application_amount;
	private String application_term;
	private String order_time;
	private String status;
	private String city;
	private String bank;
	private String product;
	private String user_id;
	
	public String getOrder_No() {
		return order_No;
	}
	public void setOrder_No(String order_No) {
		this.order_No = order_No;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_mobile() {
		return user_mobile;
	}
	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}
	public String getApplication_amount() {
		return application_amount;
	}
	public void setApplication_amount(String application_amount) {
		this.application_amount = application_amount;
	}
	public String getApplication_term() {
		return application_term;
	}
	public void setApplication_term(String application_term) {
		this.application_term = application_term;
	}
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReLoanOrderInfo [");
		if (order_No != null) {
			builder.append("order_No=");
			builder.append(order_No);
			builder.append(", ");
		}
		if (user_name != null) {
			builder.append("user_name=");
			builder.append(user_name);
			builder.append(", ");
		}
		if (user_mobile != null) {
			builder.append("user_mobile=");
			builder.append(user_mobile);
			builder.append(", ");
		}
		if (application_amount != null) {
			builder.append("application_amount=");
			builder.append(application_amount);
			builder.append(", ");
		}
		if (application_term != null) {
			builder.append("application_term=");
			builder.append(application_term);
			builder.append(", ");
		}
		if (order_time != null) {
			builder.append("order_time=");
			builder.append(order_time);
			builder.append(", ");
		}
		if (status != null) {
			builder.append("status=");
			builder.append(status);
			builder.append(", ");
		}
		if (city != null) {
			builder.append("city=");
			builder.append(city);
			builder.append(", ");
		}
		if (bank != null) {
			builder.append("bank=");
			builder.append(bank);
			builder.append(", ");
		}
		if (product != null) {
			builder.append("product=");
			builder.append(product);
			builder.append(", ");
		}
		if (user_id != null) {
			builder.append("user_id=");
			builder.append(user_id);
		}
		builder.append("]");
		return builder.toString();
	}
}