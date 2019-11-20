package com.waterelephant.rong360.entity;
@Deprecated
public class OrderInfo {

	private String order_No;
	private String user_name;
	private String user_mobile;
	private String application_amount;
	private Integer application_term;
	private Integer status;
	private Long order_time;
	private String city;
	private String bank;
	private String product;
	private String platform;
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
	public Integer getApplication_term() {
		return application_term;
	}
	public void setApplication_term(Integer application_term) {
		this.application_term = application_term;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getOrder_time() {
		return order_time;
	}
	public void setOrder_time(Long order_time) {
		this.order_time = order_time;
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
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
}
