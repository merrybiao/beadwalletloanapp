package com.waterelephant.jiedianqian.entity;

import java.util.List;

/**
 * 
 * 
 * Module:
 * 
 * OrderInfoResponse.java
 * 
 * @author 张博
 * @since JDK 1.8
 * @version 1.0
 * @description: <推送订单>
 */
public class OrderInfoRequest {
	private UserInfo user_info; // 用户信息
	private BankInfo bank_info; // 银行卡信息
	private LoanInfo loan_info; // 贷款信息
	private UserLoginUploadLog user_login_upload_log; // 地址,经度,纬度
	private List<AddressBook> address_book; // 通讯录 bw_contract_list
	private UserContact user_contact; // 联系人
	private Zmxy zm; // 芝麻信用
	private String source_order_id; // 借点钱订单号
	private DeviceInfo device_info; // 设备信息
	private Operator operator;		// 运营商信息

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public DeviceInfo getDevice_info() {
		return device_info;
	}

	public void setDevice_info(DeviceInfo device_info) {
		this.device_info = device_info;
	}

	public UserInfo getUser_info() {
		return user_info;
	}

	public void setUser_info(UserInfo user_info) {
		this.user_info = user_info;
	}

	public BankInfo getBank_info() {
		return bank_info;
	}

	public void setBank_info(BankInfo bank_info) {
		this.bank_info = bank_info;
	}

	public LoanInfo getLoan_info() {
		return loan_info;
	}

	public void setLoan_info(LoanInfo loan_info) {
		this.loan_info = loan_info;
	}

	public UserLoginUploadLog getUser_login_upload_log() {
		return user_login_upload_log;
	}

	public void setUser_login_upload_log(UserLoginUploadLog user_login_upload_log) {
		this.user_login_upload_log = user_login_upload_log;
	}

	public List<AddressBook> getAddress_book() {
		return address_book;
	}

	public void setAddress_book(List<AddressBook> address_book) {
		this.address_book = address_book;
	}

	public UserContact getUser_contact() {
		return user_contact;
	}

	public void setUser_contact(UserContact user_contact) {
		this.user_contact = user_contact;
	}

	public Zmxy getZm() {
		return zm;
	}

	public void setZm(Zmxy zm) {
		this.zm = zm;
	}

	public String getSource_order_id() {
		return source_order_id;
	}

	public void setSource_order_id(String source_order_id) {
		this.source_order_id = source_order_id;
	}

	@Override
	public String toString() {
		return "OrderInfoRequest [user_info=" + user_info + ", bank_info=" + bank_info + ", loan_info=" + loan_info
				+ ", user_login_upload_log=" + user_login_upload_log + ", address_book=" + address_book
				+ ", user_contact=" + user_contact + ", zm=" + zm + ", source_order_id=" + source_order_id + "]";
	}

}
