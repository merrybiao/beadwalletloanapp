package com.waterelephant.rong360.entity;
@Deprecated
public class RongBindCardBizData {
	private String contract_return_url;
	private String order_no;
	private String bank_card;
	private String open_bank;
	private String user_name;
	private String id_number;
	private String user_mobile;
	private String bank_address;
	
	public String getContract_return_url() {
		return contract_return_url;
	}
	public void setContract_return_url(String contract_return_url) {
		this.contract_return_url = contract_return_url;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getBank_card() {
		return bank_card;
	}
	public void setBank_card(String bank_card) {
		this.bank_card = bank_card;
	}
	public String getOpen_bank() {
		return open_bank;
	}
	public void setOpen_bank(String open_bank) {
		this.open_bank = open_bank;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getId_number() {
		return id_number;
	}
	public void setId_number(String id_number) {
		this.id_number = id_number;
	}
	public String getUser_mobile() {
		return user_mobile;
	}
	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}
	public String getBank_address() {
		return bank_address;
	}
	public void setBank_address(String bank_address) {
		this.bank_address = bank_address;
	}
	
	@Override
	public String toString() {
		return "RongBindCard ["
				+ (contract_return_url != null ? "contract_return_url=" + contract_return_url + ", " : "")
				+ (order_no != null ? "order_no=" + order_no + ", " : "")
				+ (bank_card != null ? "bank_card=" + bank_card + ", " : "")
				+ (open_bank != null ? "open_bank=" + open_bank + ", " : "")
				+ (user_name != null ? "user_name=" + user_name + ", " : "")
				+ (id_number != null ? "id_number=" + id_number + ", " : "")
				+ (user_mobile != null ? "user_mobile=" + user_mobile + ", " : "")
				+ (bank_address != null ? "bank_address=" + bank_address : "") + "]";
	}
}